# Playtest tools

Scripts for reading a Minecraft server's player data after a playtest. Put each playtest's copied server data in `docs/private/playtest-<date>/serverdata/` (git-ignored, because it contains player names). For the dev server, `tools/devserver.sh pull <date>` copies it there.

- `nbt.py`: minimal reader for gzipped NBT files.
- `stats.py`, `timeline.py`, `sessions.py`: print stats, advancement timelines and play sessions.
- `build_dashboard.py`: builds a self-contained HTML dashboard.

```sh
cd docs/private/playtest-2026-09/serverdata
python -I ../../../../tools/playtest/build_dashboard.py . ../dashboard
```

`post_discussions.py` is the one-off script that posted the starter GitHub Discussions.
