# Super Awesome Craft

A total conversion of Minecraft Java Edition for a small server of roughly 5 to 10 friends. It uses Minecraft as an engine and keeps vanilla's familiar moment-to-moment play, but replaces its progression with **Eras**: chains of dungeon encounters that unlock new areas, materials and systems. Living **Settlements** and **Raids** give the server shared reasons to gather.

**Status:** Season 0. The mod removes XP, ships as a Prism modpack, and runs on a dev server.

It succeeds [Super Awesome Datapacks](https://github.com/SuperAwesomeStuff/SuperAwesomeDatapacks). Those packs worked well as individual system reworks, but a playtest showed that the progression problems are structural. See [the vision](docs/vision.md) for why.

## Design pillars

1. **Familiar actions, new structure.** Mining, crafting, building and fighting work the way a vanilla player expects. Progression, economy and goals are ours.
2. **Convenience is cheap; power is expensive.** Quality-of-life tools come early and don't require combat, so builders are never pushed up the combat ladder. Power is earned through encounters.
3. **Progression belongs to the world.** When anyone clears a Stronghold, the next dimension opens for everyone. Nobody is ever stopped from joining friends because they haven't met some condition.
4. **Encounters, not arena bosses.** Challenges are dungeon crawls and fights against groups of enemies, ending in climax fights inside arenas the game designed, which can't be cheesed.
5. **Every playstyle has a project that matters.** Huge builds, colonies, collections and growth with no cap all feed into the world instead of sitting beside it.
6. **Nothing destroys what players build.** Encounters and Raids never break player blocks.
7. **Technical parity.** Mechanics that contraptions rely on stay exactly vanilla. New behavior comes as a separate variant, never as a change to the original.

## Documentation

- [Vision](docs/vision.md): the problem, the pillars in detail, and the game's structure.
- [Design map](docs/design-map.md): every area of the design at a glance, with loose ideas that aren't decided yet.
- [GLOSSARY.md](GLOSSARY.md): glossary of domain terms.
- [ADRs](docs/adr/): architectural and design decisions.
- [Research](docs/research/): background reading summaries.

## Building

The mod lives in [`mod/`](mod/). It targets Minecraft 26.3 with Fabric and needs JDK 25 installed (Gradle finds it automatically).

```sh
cd mod
./gradlew build         # mod jar in mod/build/libs/
./gradlew runGametest   # automated in-game tests, headless
./gradlew runClient     # play it in a dev client
./gradlew runServer     # dev server in mod/run/
./gradlew modpack       # mod/build/modpack/SuperAwesomeCraft-<version>.mrpack
```

## Installing the modpack (Prism Launcher)

1. Get the `.mrpack` file.
2. In Prism, click **Add Instance**, choose **Import**, and pick the file.
3. Launch the new instance. Prism downloads Minecraft, Fabric and Fabric API itself.

For a new version, import the new file as a fresh instance and copy any worlds or settings across.

## Dev server

`tools/devserver.sh` manages the dev server on ServerPC over ssh. It runs in a `screen` session named `sac` on port 25566.

```sh
tools/devserver.sh setup      # once: installs Fabric in ~/Servers/SuperAwesomeCraft-dev
tools/devserver.sh deploy     # build, upload the mod, restart
tools/devserver.sh status     # also: start, stop, restart, console, log
tools/devserver.sh pull <date>  # copy player data for the playtest tools
```

Override the defaults with `SAC_SERVER_HOST`, `SAC_SERVER_DIR`, `SAC_SERVER_PORT` and `SAC_SERVER_MEMORY`.
