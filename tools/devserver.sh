#!/usr/bin/env bash
# Manage the Super Awesome Craft dev server on ServerPC, from this PC.
#
#   tools/devserver.sh setup            first-time install of Fabric on the server
#   tools/devserver.sh deploy           build the mod, upload it, restart the server
#   tools/devserver.sh start|stop|restart|status
#   tools/devserver.sh console          attach to the server console (Ctrl+A then D to detach)
#   tools/devserver.sh log              follow the server log
#   tools/devserver.sh pull <date>      copy player data into docs/private/playtest-<date>/serverdata
#
# The server runs in a `screen` session on the remote machine. Needs ssh access to it.
set -euo pipefail

HOST="${SAC_SERVER_HOST:-192.168.1.100}"
DIR="${SAC_SERVER_DIR:-Servers/SuperAwesomeCraft-dev}"   # relative to the remote home folder
PORT="${SAC_SERVER_PORT:-25566}"
MEMORY="${SAC_SERVER_MEMORY:-4G}"
JAVA="${SAC_SERVER_JAVA:-\$HOME/.local/jvm/jdk-25.0.3+9-jre/bin/java}"
SESSION=sac
FABRIC_INSTALLER=1.1.2

REPO="$(cd "$(dirname "$0")/.." && pwd)"
MOD="$REPO/mod"

prop() { grep "^$1=" "$MOD/gradle.properties" | cut -d= -f2- | tr -d '\r'; }
MC_VERSION="$(prop minecraft_version)"
LOADER_VERSION="$(prop loader_version)"

remote() { ssh -n -o BatchMode=yes "$HOST" "$@"; }

# Fabric API's download URL and sha1, read from the modpack so server and clients always match.
fabric_api() {
	python -I - "$MOD/modpack/modrinth.index.json" <<'EOF'
import json, sys
index = json.load(open(sys.argv[1]))
f = next(f for f in index["files"] if "fabric-api" in f["path"])
print(f["path"].split("/")[-1], f["downloads"][0], f["hashes"]["sha1"])
EOF
}

is_running() { remote "screen -list | grep -q '\.$SESSION\s'"; }

cmd_setup() {
	echo "Setting up $HOST:~/$DIR (Minecraft $MC_VERSION, Fabric loader $LOADER_VERSION, port $PORT)"
	remote "mkdir -p ~/$DIR/mods && cd ~/$DIR \
		&& curl -fsSL -o fabric-server-launch.jar \
			https://meta.fabricmc.net/v2/versions/loader/$MC_VERSION/$LOADER_VERSION/$FABRIC_INSTALLER/server/jar \
		&& printf '#!/bin/bash\ncd \"\$(dirname \"\$0\")\"\nexec \"%s\" -Xmx$MEMORY -Xms1G -jar fabric-server-launch.jar nogui\n' \"$JAVA\" > start.sh \
		&& chmod +x start.sh \
		&& { [ -f server.properties ] || printf 'server-port=$PORT\nmotd=Super Awesome Craft (dev)\nonline-mode=true\nwhite-list=false\n' > server.properties; }"
	if ! remote "grep -qs '^eula=true' ~/$DIR/eula.txt"; then
		echo "The server needs the Minecraft EULA accepted: https://aka.ms/MinecraftEULA"
		read -r -p "Accept it for this server? [y/N] " answer
		[[ "$answer" == [yY]* ]] || { echo "Not accepted; the server won't start."; exit 1; }
		remote "echo eula=true > ~/$DIR/eula.txt"
	fi
	echo "Done. Run 'tools/devserver.sh deploy' next."
}

cmd_upload() {
	(cd "$MOD" && ./gradlew build -x test)
	local jar
	jar="$(ls "$MOD"/build/libs/superawesomecraft-*.jar | grep -v -- '-sources\.jar$')"
	read -r api_file api_url api_sha1 < <(fabric_api | tr -d '\r')

	echo "Uploading $(basename "$jar")"
	remote "rm -f ~/$DIR/mods/superawesomecraft-*.jar"
	scp -q "$jar" "$HOST:$DIR/mods/"

	if ! remote "[ -f ~/$DIR/mods/$api_file ]"; then
		echo "Downloading $api_file on the server"
		remote "cd ~/$DIR/mods && rm -f fabric-api-*.jar && curl -fsSL -o '$api_file' '$api_url' \
			&& echo '$api_sha1  $api_file' | sha1sum -c --quiet"
	fi
}

cmd_start() {
	if is_running; then echo "Already running."; return; fi
	remote "cd ~/$DIR && screen -dmS $SESSION ./start.sh"
	echo "Started in screen session '$SESSION' on $HOST, port $PORT."
}

cmd_stop() {
	if ! is_running; then echo "Not running."; return; fi
	echo "Stopping (saving the world)..."
	remote "screen -S $SESSION -p 0 -X stuff 'stop^M'"
	for _ in $(seq 60); do
		is_running || { echo "Stopped."; return; }
		sleep 1
	done
	echo "Still running after 60s; check 'tools/devserver.sh console'." >&2
	exit 1
}

cmd_status() {
	if is_running; then echo "Running on $HOST:$PORT."; else echo "Not running."; fi
}

cmd_pull() {
	local date="${1:?usage: devserver.sh pull <date, e.g. 2026-12-05>}"
	local out="$REPO/docs/private/playtest-$date/serverdata"
	mkdir -p "$out/world"
	echo "Copying player data to $out"
	scp -q -r "$HOST:$DIR/logs" "$HOST:$DIR/usercache.json" "$out/"
	scp -q -r "$HOST:$DIR/world/players" "$out/world/"
}

case "${1:-}" in
	setup) cmd_setup ;;
	deploy) cmd_upload; cmd_stop; cmd_start ;;
	start) cmd_start ;;
	stop) cmd_stop ;;
	restart) cmd_stop; cmd_start ;;
	status) cmd_status ;;
	console) ssh -t "$HOST" "screen -r $SESSION" ;;
	log) remote "tail -n 50 -f ~/$DIR/logs/latest.log" ;;
	pull) shift; cmd_pull "$@" ;;
	*) sed -n '2,12p' "$0" | sed 's/^# \{0,1\}//'; exit 1 ;;
esac
