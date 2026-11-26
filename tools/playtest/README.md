# Playtest tools

Scripts for reading a Minecraft server's player data after a playtest. Put each playtest's copied server data in `docs/private/playtest-<date>/serverdata/` (git-ignored, because it contains player names). For the dev server, `tools/devserver.sh pull <date>` copies it there.

- `nbt.py`: minimal reader for gzipped NBT files.
- `stats.py`, `timeline.py`, `sessions.py`: print stats, advancement timelines and play sessions.
- `build_dashboard.py`: builds a self-contained HTML dashboard.
- `events.py`: summarises the mod's own playtest log into the six [Season 1 playtest measures](../../docs/season-1/enchanting/README.md#playtest-measures) (pace, spending, variety, usage, repairs, retention). Add `--json` for machine-readable output.

## The mod's playtest log

Vanilla stats can't see what was enchanted, what it cost, or when gear hit 0, so the mod writes its own log on dedicated servers: one JSON line per event in `world/superawesomecraft/playtest/<date>.jsonl`. `devserver.sh pull` copies it. Settings are in `config/superawesomecraft/playtest-log.json` (`enabled`, `singleplayer`, `snapshot_minutes`).

| `type` | When | Fields |
| --- | --- | --- |
| `join`, `leave` | a player joins or leaves | `dim`, `x`, `y`, `z` |
| `snapshot` | every 5 minutes, per online player | position, `health`, `max_health`, `food`, `mode`, `gear` (each carried item), `counts` (ability use since the last snapshot) |
| `counts` | on leave, ability use since the last snapshot | `counts` |
| `enchant` | an enchantment is added or raised at the table | `enchantment`, `from`, `to`, `via` (`materials` or `book`), `cost`, `item` |
| `unenchant` | an enchantment is removed | `enchantment`, `from`, `cost`, `item` |
| `slot_upgrade` | a slot upgrade is applied | `upgrade`, `cost`, `item` |
| `book_merge` | two books are combined at the anvil | `item` |
| `transfer` | enchantments are moved to a new item at the anvil | `from` (the old item), `item` (the new one) |
| `repair` | gear is repaired in the crafting grid | `materials`, `damage_before`, `item` |
| `gear_broken` | gear hits 0 durability | `item` |
| `death` | a player dies | position, `cause`, `killer` |
| `change_dimension` | a player changes dimension | position (in the new one), `from` |

Every line also has `t` (milliseconds since 1970), `player` (UUID) and `name`. An item is `id`, `ench`, `stored` (books), `damage`, `max_damage`, `slots_used`, `slots`, `upgrades`, and `maxed` when every slot is used and every enchantment is at its top level. `cost` entries are `{item, count}`, with `#tag` for tag costs; `creative: true` marks events by creative players, who pay nothing. Counters: `excavating_blocks`, `harvesting_blocks`, `felling_logs`, `harvesting_replants`, `harvesting_tilled`, `smelting_items`, `magnetism_items`, `beheading_heads`, `bulwark_disarms`.

New systems record through `PlaytestLog.event` (one-off actions) and `PlaytestLog.count` (frequent ones); add them to this table.

```sh
python -I tools/playtest/events.py docs/private/playtest-<date>/serverdata
```

```sh
cd docs/private/playtest-2026-09/serverdata
python -I ../../../../tools/playtest/build_dashboard.py . ../dashboard
```

`post_discussions.py` is the one-off script that posted the starter GitHub Discussions.
