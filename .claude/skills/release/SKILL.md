---
name: release
description: Publish a GitHub release of the modpack with the client .mrpack and a ready-to-run server zip.
disable-model-invocation: true
---

# Release

Publishes `v<version>` on GitHub with two assets built by `tools/release.sh`: the client modpack (`SuperAwesomeCraft-<version>.mrpack`) and the server (`SuperAwesomeCraft-<version>-server.zip`). The version is `version=` in `mod/gradle.properties`. Running this skill is the user's go-ahead to publish; the repo and its releases are public.

## Steps

1. **Check the ground.** On `master`, working tree clean, `master` pushed (`git status -sb` shows no ahead/behind after `git fetch`). The tag `v<version>` doesn't exist locally or on GitHub (`gh release view v<version>` fails). If the tag exists, stop and ask the user whether to bump the version. Done when all four hold.
2. **Test.** `cd mod && ./gradlew runGametest` prints "All N required tests passed". A failure stops the release; report it with the output.
3. **Build.** `bash tools/release.sh`. Done when it prints both paths and the zip lists `fabric-server-launch.jar`, `start.sh`, `start.bat`, `eula.txt` (`eula=false`), `README.txt` and every mod in `mods/`.
4. **Write the notes.** Read `git log <previous tag>..HEAD` (or the whole history for the first release) and the matching docs and wiki pages. Write plain-language, player-facing notes in the wiki's voice:
   - a one-line summary of the build;
   - **What's new** and **What changed**, grouped by system (Enchanting, Durability, …), each item one line;
   - **Not in this build yet**, for features players will read about on the wiki but can't use;
   - **Installing**: import the `.mrpack` into Prism Launcher or the Modrinth App; for a server, unzip, set `eula=true`, run `start.bat` or `start.sh` (Java 25); link the wiki page [Playing the Test Build](https://github.com/SuperAwesomeStuff/SuperAwesomeCraft/wiki/Playing-the-Test-Build);
   - a note to delete `config/superawesomecraft/enchanting.json` when updating, if the default costs changed.

   Player names and anything from `docs/private/` stay out: the notes are public. Done when every player-visible change in the log appears in the notes.
5. **Publish.** `gh release create v<version> --target master --title "Super Awesome Craft <version>" --notes-file <notes> <mrpack> <server zip>`. Done when `gh release view v<version> --json assets` lists both assets.
6. **Report** the release URL and the two asset names to the user.
