#!/usr/bin/env bash
# Build the two files a GitHub release ships, into mod/build/release/:
#
#   SuperAwesomeCraft-<version>.mrpack       the client modpack (Prism Launcher, Modrinth App)
#   SuperAwesomeCraft-<version>-server.zip   a ready-to-run Fabric server with the same mods
#
# Usage: tools/release.sh            (prints the two paths when done)
#
# The server zip holds the Fabric server launcher, mods/ (this mod and Fabric API, the same files
# the modpack uses), start scripts, eula.txt set to false, and a README. The launcher downloads
# the Minecraft server itself on first start.
set -euo pipefail

REPO="$(cd "$(dirname "$0")/.." && pwd)"
MOD="$REPO/mod"
FABRIC_INSTALLER=1.1.2   # keep in step with tools/devserver.sh

prop() { grep "^$1=" "$MOD/gradle.properties" | cut -d= -f2- | tr -d '\r'; }
VERSION="$(prop version)"
MC_VERSION="$(prop minecraft_version)"
LOADER_VERSION="$(prop loader_version)"

OUT="$MOD/build/release"
STAGE="$OUT/server-$VERSION"

echo "Building Super Awesome Craft $VERSION (Minecraft $MC_VERSION, Fabric loader $LOADER_VERSION)"
(cd "$MOD" && ./gradlew -q clean modpack)
mkdir -p "$STAGE/mods"

MRPACK="$MOD/build/modpack/SuperAwesomeCraft-$VERSION.mrpack"
JAR="$MOD/build/libs/superawesomecraft-$VERSION.jar"
[ -f "$MRPACK" ] && [ -f "$JAR" ] || { echo "Build output missing: $MRPACK or $JAR" >&2; exit 1; }
cp "$MRPACK" "$OUT/"
cp "$JAR" "$STAGE/mods/"

# Every other mod comes from the modpack index, so server and clients always run the same files.
python -I - "$MOD/modpack/modrinth.index.json" "$STAGE" <<'EOF'
import hashlib, json, os, sys, urllib.request
index, stage = json.load(open(sys.argv[1])), sys.argv[2]
for f in index["files"]:
    if f.get("env", {}).get("server") == "unsupported":
        continue
    dest = os.path.join(stage, f["path"])
    os.makedirs(os.path.dirname(dest), exist_ok=True)
    data = urllib.request.urlopen(f["downloads"][0]).read()
    if hashlib.sha1(data).hexdigest() != f["hashes"]["sha1"]:
        sys.exit(f"sha1 mismatch for {f['path']}")
    open(dest, "wb").write(data)
    print("  downloaded", f["path"])
EOF

curl -fsSL -o "$STAGE/fabric-server-launch.jar" \
	"https://meta.fabricmc.net/v2/versions/loader/$MC_VERSION/$LOADER_VERSION/$FABRIC_INSTALLER/server/jar"

printf '#!/bin/sh\ncd "$(dirname "$0")"\nexec java -Xmx4G -Xms1G -jar fabric-server-launch.jar nogui\n' > "$STAGE/start.sh"
printf '@echo off\r\ncd /d "%%~dp0"\r\njava -Xmx4G -Xms1G -jar fabric-server-launch.jar nogui\r\npause\r\n' > "$STAGE/start.bat"
printf '# Read https://aka.ms/MinecraftEULA, then change this to eula=true to run the server.\neula=false\n' > "$STAGE/eula.txt"
cat > "$STAGE/README.txt" <<EOF
Super Awesome Craft $VERSION server
Minecraft $MC_VERSION, Fabric loader $LOADER_VERSION

1. Install Java 25 (for example from https://adoptium.net/).
2. Read the Minecraft EULA (https://aka.ms/MinecraftEULA). If you agree, open eula.txt
   and change eula=false to eula=true.
3. Run start.bat (Windows) or start.sh (Mac, Linux). The first start downloads the
   Minecraft server, so it needs internet and takes a minute.
4. Players connect on port 25565. Change the port and other settings in
   server.properties, which appears after the first start.

Memory: the start scripts give the server 4 GB (-Xmx4G). Change it there.

Enchanting costs live in config/superawesomecraft/enchanting.json, written on first
start. Edit it and run /reload. Delete it to get this build's defaults back (do that
once after updating from an older build).

Players need the matching modpack: SuperAwesomeCraft-$VERSION.mrpack from the same release.
EOF

ZIP="$OUT/SuperAwesomeCraft-$VERSION-server.zip"
python -I - "$STAGE" "$ZIP" <<'EOF'
import os, sys, zipfile
stage, out = sys.argv[1], sys.argv[2]
with zipfile.ZipFile(out, "w", zipfile.ZIP_DEFLATED) as z:
    for root, _, files in os.walk(stage):
        for name in sorted(files):
            path = os.path.join(root, name)
            info = zipfile.ZipInfo.from_file(path, os.path.relpath(path, stage).replace(os.sep, "/"))
            info.external_attr = (0o755 if name.endswith(".sh") else 0o644) << 16
            info.compress_type = zipfile.ZIP_DEFLATED
            z.writestr(info, open(path, "rb").read())
EOF
rm -rf "$STAGE"

echo "$OUT/SuperAwesomeCraft-$VERSION.mrpack"
echo "$ZIP"
