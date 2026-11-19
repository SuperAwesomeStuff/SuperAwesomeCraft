# Vision

## The problem

Super Awesome Datapacks reworked Enchanting and Villager trading. They were played on a 26.3 server with six regular players in September 2026. The reworks worked as designed, but the server lasted about ten days and roughly 54 player-hours.

- **Every route ended at the same activity.** Players sold raw ore and food to Villagers for Emeralds, bought Diamond gear, enchanted it (Fortune first), then strip-mined Netherrack with an unbreakable Efficiency V pick until they had full Netherite.
- **Players left when their goals ran out.** The fastest player reached full Netherite in about 6 hours of play and left soon after. Another left the day after killing the Dragon.
- **Slower players fell behind and drifted away.** The two players who barely traded never reached the Nether.
- **Builders were pulled into the grind.** The best convenience (Unbreakable, instant mining) sat at the top of the combat ladder, so building comfortably meant climbing it.
- **Large parts of the game went unused:** fishing, brewing, Trial Chambers and Bastions.

Root causes:

1. One universal currency turned playstyles into exchange rates.
2. Each player climbed a private ladder with a fixed end.
3. Nothing gave the server shared goals.
4. Convenience was tied to power.

## Who we are designing for

The design targets **motivations**, not fixed archetypes, because every real player combines several. The motivations seen in the group:

| Motivation | What serves it |
| --- | --- |
| Being near the group | A central Settlement worth living in or near; shared events |
| Having space of their own | Travel tools that keep distant homes connected to the group |
| Huge long-term projects | Bulk materials and builder tools that make large projects possible to finish, and systems that give those projects a function |
| Growth with no cap, as long as it's useful | Settlement expansion, deep resource layers, large-scale scaling systems |
| Gear and power | Era Gates, encounter loot, horizontal gear choices |
| Collecting | A Collection Log with rewards for breadth |
| Exploration and challenge | Dungeons and areas that unlock in sequence; structures found by learnable rules, not luck |
| Running a settlement | Villagers who live in, use and grow with what players build |
| Mischief and PvP | Opt-in arenas and minigame spaces |
| Pure building | Cheap convenience and plentiful blocks; building never requires combat |

## Structure

### Eras

The game advances through a small number of **Eras**. Each Era has an **Era Gate**: a dungeon chain with group encounters, ending in a climax fight in an arena built into the dungeon. Clearing it gives the player:

- the means to open the next Era's dimension, for the whole world (see [ADR-0003](adr/0003-world-progression-through-portals.md)),
- new materials, Catalysts and trades,
- the next tier of power.

Progression belongs to the world. Once a portal to a new dimension exists, anyone can walk through it and do anything there. Nothing checks whether a player personally cleared a Gate.

**Decided: one Era per dimension.** Overworld → Nether → a new sky dimension (Aether-like) → End. Each dimension has its own ores, tool tier, Heart upgrades and Era Gate. The End is rebuilt as a real dungeon with a climax fight, not just the Dragon.

**Idea: Heart upgrades.** Like Terraria's Life Crystals, players raise their max health with items found while exploring each dimension, not only by clearing its Gate. Later encounters can hit harder while feeling about as hard. Healing probably needs to scale with max health, because vanilla regenerates at a fixed rate.

**Decided: Era-gated blocks.** Each Era's new ores, dungeon walls and structure blocks need that Era's tools to mine, like Terraria's pickaxe power. This never applies to vanilla building blocks or to anything players place, because mining speed is Convenience.

### Convenience and Power

| Convenience (cheap, early, no combat needed) | Power (earned through Eras) |
| --- | --- |
| Mining speed, Silk Touch, Techniques (vein-mining, tree-felling, builder modes), storage (early Shulkers), mobility, reach, bulk block access | Damage, protection, survivability, Heart upgrades, encounter-specific gear |

Gear tiers should offer horizontal choices with distinct abilities, not one best set.

### Gear

- **Decided: no XP.** Experience, the XP bar and XP orbs are removed. Nothing costs levels.
- **Decided: no durability.** Durability is stripped from every item (not marked Unbreakable). Tools, weapons and armor never wear out. Mending, Unbreaking and all repair go away. Replacement sinks: Era materials for upgrades and enchanting, owning several specialized tools, consumables in Era Gates, and Settlement building. The main risk to playtest is ore demand collapsing. Background: [durability research](research/durability.md).
- **Leaning: enchanting with fixed recipes and capacity**, after Ascendant Arcana. Each enchantment level has a fixed material recipe, with higher levels needing later-Era materials. Each item has a capacity budget that limits how much it can carry, so gear involves choices rather than one maxed loadout. Raw stats come from slotted, upgradable components. A library of enchanted books (Chiseled Bookshelves) powers the table. The vanilla Enchanting Table is replaced; contraptions don't depend on it. Background: [Ascendant Arcana research](research/ascendant-arcana.md).
- **Decided: tools level with use instead of a separate skills system.** There are no player-level skills. Background: [tool levelling](research/tool-levelling.md), [skills systems](research/skills-systems.md).
  - A tool's **Familiarity** rises to a hard cap of 5, mostly from doing new things, plus a daily allowance. Levels unlock slots for toggleable **Techniques** (Convenience only).
  - Enchantment capacity and stat slots come from materials and Eras, never from use, so grinding can't buy power.
  - Techniques learned are kept forever in the player's **Codex**. Maxing any tool of a type grants **Mastery**: new tools of that type start at level 2 and level faster. Upgrading a tool's tier keeps its progress.
  - Weapons and armor don't level; combat Power comes from Eras.
  - Catch-up: the low cap, Mastery, faster levelling once anyone on the server has mastered a tool type, and levelled tools can be given to new players.

### Settlements and Raids

Villagers move into Settlements that players build and use their buildings. A Settlement's prosperity grows with what's been built and how it's used, which unlocks trades and services. Prosperity draws Raids: wave-based group encounters that scale with the Settlement. A Raid never destroys player blocks. Walls and layout matter, fighters have a reason to defend, and everyone has a reason to show up.

### Adventuring

Exploration should be painless but not free. Vanilla's adventuring problems are real (no movement step between horse and Elytra, harsh death, early inventory clutter, unreadable structure placement), but fixes come from progression and items, not from changing base physics. Background: [The Bottom Dweller on adventuring](research/bottom-dweller-and-matcha-flavoured.md).

- **Player movement stays vanilla.** No higher jump; no automatic step-up.
- **Mounts:**
  - Breeding stays and stays rewarding, but improves faster, and every horse and donkey starts from a higher baseline.
  - Horses can carry chests, donkeys can wear armor, and mounts aren't blocked by leaves.
  - Step-up height stays vanilla.
- **A movement step between horse and Elytra,** such as a glider, as part of cheap early Convenience.
- **Two kinds of rail:** technical minecarts and rails keep exact vanilla physics for contraptions. A separate **travel cart** uses physics like the experimental minecart rework, but faster. Background: [minecarts and rails](research/minecarts-and-rails.md).
  - **Leaning:** powered rails are crafted with copper instead of gold. A new, stronger **gold rail** uses gold.
  - Suggested speeds: the vanilla cart stays at 8 m/s on both rails. The travel cart goes 16 m/s on powered rails and 24 m/s on gold rails, playtested toward 32.
- **Nether highways stay** as legitimate creative problem-solving.
- **Death:** items go into a grave that never despawns. Mid-game tools locate a grave from far away, reclaim it remotely, or teleport you back to it.
- **Inventory:** backpacks expand your inventory as progression, Shulker boxes arrive earlier, and quick-dump and sort are standard.
- **Readable world generation:** the world stays infinite and random. There's no fixed starting region. Important structures are placed by biome rules players can learn by playing, not by hidden grid algorithms (the way Ancient Cities appear under mountains). Structures carry environmental storytelling, such as a ruined castle marking the Stronghold beneath it.
- **Per-player loot:** generated loot containers (chests, End Ship Elytra, vaults) are rolled separately for each player, so arriving second never means arriving to nothing.

### Economy

There is no single currency everything converts into. Each playstyle earns rewards that are useful in their own right instead of an exchange rate that can be optimized. Buyer-side income has limits. Farms are welcome but never required.

## Constraints

- Players install the mod as a **modpack** on both client and server. See [ADR-0001](adr/0001-fabric-client-and-server-mod.md).
- Balance for 5 to 10 players of varying skill on one shared world.
- Avoid first-come finite resources that the first player can claim. Generated loot is per player.
- **Technical parity:** how vanilla mechanics behave (physics, redstone timing, mob AI, spawning, item movement) stays exactly vanilla. It doesn't protect whether a resource exists: XP farms stop mattering because XP is gone, and dispensers using shears never run out. When we want different behavior, we add a separate variant instead of changing the original.
- **Seasons:** the game is built one Season at a time. Each replaces one part of vanilla and gets a short playtest that answers one question; anything not built yet stays vanilla.

## Open questions

- What is the setting or theme that gives Eras a reason to exist?
- How many Eras, and what does each one unlock?
- What does each Era Gate look like as a dungeon, and how is the climax arena protected?
- How is Settlement prosperity measured without dictating how people build?
- What replaces the Emerald economy, and what are the limits on buyer income?
- What are the target hours played for each Era?
- What replaces durability and XP as sinks for ore and time, if durability goes?
- What are the Techniques for each tool type, including builder and explorer tools?
- How much health do Heart upgrades give per dimension, and how does healing scale?
- Which of Super Awesome Enchanting and Super Awesome Villagers carry over, and how do they change?
