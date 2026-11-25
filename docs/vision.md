# Vision

## Vision statement

**Decided: Every playstyle pushes the world forward.**

Every mechanic should serve this line. When judging a feature, ask what it lets someone contribute that others will feel. Three pillars sit under it:

- **Progress is shared by building and giving.** What one player achieves helps others through things that exist in the world: an opened portal, a beacon, items handed over. There are no hidden server-wide unlocks, and nobody has to follow the group: a player who goes off alone can, and pays their own way. (The one shared requirement so far is the materials for the reset ritual.)
- **Always a next horizon.** There is no single max-gear endpoint.
- **Convenience is cheap, Power is earned.** Building never waits on combat.

Features that serve the statement only weakly, such as PvP arenas, are side content, not central systems. Background: [Game Design 101](research/game-design-101.md#the-vision-statement).

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
| Gear and power | Strongholds, encounter loot, horizontal gear choices |
| Collecting | A Collection Log with rewards for breadth |
| Exploration and challenge | Dungeons and areas that unlock in sequence; structures found by learnable rules, not luck |
| Running a settlement | Villagers who live in, use and grow with what players build |
| Mischief and PvP | Opt-in arenas and minigame spaces |
| Pure building | Cheap convenience and plentiful blocks; building never requires combat |

## Structure

### Eras

The game advances through a small number of **Eras**. Each Era ends at a **Stronghold**, named for the dimension it leads to: the Nether Stronghold holds the first portal to the Nether, and so on. It's a dungeon with group encounters, ending in a climax fight in an arena built into it. Clearing it gives:

- the Stronghold's portal opens: the first way into the next dimension, for the whole world (see [ADR-0003](adr/0003-world-progression-through-portals.md)),
- new materials, Catalysts and trades,
- the next tier of power.

Progression belongs to the world. Once a portal to a new dimension exists, anyone can walk through it and do anything there. Nothing checks whether a player personally cleared a Stronghold.

The journey: travel to the next Stronghold, fight through it, win the climax fight, and step through its portal. Inside the new dimension, gather the materials to **build new portals** back home, so nobody has to trek back to the Stronghold every time.

- **Several Strongholds per Era** exist in each world, placed by learnable rules (see Readable world generation below), so they're consistently findable.
- **Fighting a Stronghold again:** find another uncleared one, or perform a ritual at a cleared one to reset its climax fight, like respawning the Ender Dragon. Loot is per player.
- **Catch-up easing:** players who've been to a dimension bring back blocks, materials and crafting stations for others. Nothing unlocks automatically for the whole server. **Idea:** a Villager starts trading an Era's materials once a player gives it some, so catch-up trades come from something a player did in one place.

**Decided: one Era per dimension.** Overworld → Nether → a new sky dimension (Aether-like) → End. Each dimension has its own ores, tool tier, Heart upgrades and Stronghold. The End is rebuilt as a real dungeon with a climax fight, not just the Dragon.

**Idea: Heart upgrades.** Like Terraria's Life Crystals, players raise their max health with items found while exploring each dimension, not only by clearing its Stronghold. Later encounters can hit harder while feeling about as hard. Healing probably needs to scale with max health, because vanilla regenerates at a fixed rate.

**Decided: Era-gated blocks.** Each Era's new ores, dungeon walls and structure blocks need that Era's tools to mine, like Terraria's pickaxe power. This never applies to vanilla building blocks or to anything players place, because mining speed is Convenience.

### Convenience and Power

| Convenience (cheap, early, no combat needed) | Power (earned through Eras) |
| --- | --- |
| Mining speed, Silk Touch, builder tools, storage (early Shulkers), mobility, reach, bulk block access | Damage, protection, survivability, Heart upgrades, encounter-specific gear |

Gear tiers should offer horizontal choices with distinct abilities, not one best set.

### Gear

Everything here except no XP is preliminary. The final shape gets settled when Season 1 is designed and playtested.

- **Decided: no XP.** Experience, the XP bar and XP orbs are removed. Nothing costs levels. Mobs still carry their hidden XP value so Sculk Catalysts spread exactly as in vanilla (technical parity). The anvil keeps renaming and combining enchanted books, both free; combining enchantments moves to the new enchanting.
- **Leaning: durability that's never a big deal.** Gear keeps vanilla durability but never breaks: at 0 it stops working until repaired. Repair is free in the crafting grid, the item plus its own material, 50% per item. Unbreaking stays as an enchantment; Mending is gone. Repairs are a gentle, steady use for materials, and no investment is ever lost. Background: [durability research](research/durability.md).
- **Leaning: enchanting with slots.** Built on four problems from the September 2026 playtest: gear maxed out within hours, everyone ended up with the same gear, enchanting could be bought with Emeralds, and XP is gone as a cost. The Enchanting Table is a Book, 2 Lapis and 4 Amethyst Blocks, so it starts early, and all enchanting happens there. Each item has a few **slots** set by its material (iron 2, diamond 3, Netherite 4), and every enchantment uses one at any level. Three one-off upgrades (Diamond and Gold Blocks, a Wither Skeleton Skull, Shulker Shells) add a slot each. Each enchantment has one material, as Tinkers' Construct prices its modifiers, and each level costs more of it; levels IV and V also need Blaze Rods, except Efficiency. Villagers never sell the Era materials, combat or loot books, or enchanted gear. Enchantments can be removed to free the slot, losing the materials. Damage enchantments can be mixed on one weapon and Protections on one armour piece, each in its own slot; exclusivity stays only where effects contradict (Silk Touch and Fortune, for example). About a dozen new enchantments, drawn from Tinkers' Construct and other mods, each doing something no vanilla enchantment does; where one does the same thing as a vanilla enchantment it keeps the vanilla name (Breach on swords, for example). Details: [Season 1 enchanting](season-1/enchanting/README.md). Background: [Tinkers' Construct](research/tinkers-construct.md).
- **Removed for now: tool levelling** (Familiarity, Techniques, Codex, Mastery). Background if it comes back: [tool levelling](research/tool-levelling.md), [skills systems](research/skills-systems.md).
- **Open: where raw stats come from** (speed, damage, protection). Slotted **Augments**, like Ascendant Arcana's Relics, were the plan, but a creative playtest of Ascendant Arcana made them look like a second system doing enchanting's job with different materials, and their recipes can't be found without a recipe viewer. Options:
  1. Stat enchantments stay, with fixed recipes like any other (the Season 1 draft assumes this).
  2. Stats come from each Era's tool tier, and enchanting covers only behaviour.
  3. A stat upgrade stays, but it is discoverable in-game: in the vanilla recipe book, with tooltips that explain it.

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

## Seasons

The game is built one Season at a time (see Constraints). Only the next Season is designed in detail.

- **Season 0 (no playtest):** a Fabric client-and-server mod skeleton, a one-click modpack (Modrinth pack), a dev server on the home server PC, and the playtest scripts pointed at it. Targets the latest Minecraft version Fabric supports when work starts.
- **Season 1, Gear:** no XP, durability that never breaks gear, and slot enchanting that starts early. Everything else stays vanilla. The question: when enchanting is a long climb with real choices that runs through every Era, do people keep playing past where they quit last time?
- **Later, in some order:** rails and the Travel cart, the first Stronghold and the Nether Era, Settlements and Raids, the sky dimension, the rebuilt End.

## Constraints

- Players install the mod as a **modpack** on both client and server. See [ADR-0001](adr/0001-fabric-client-and-server-mod.md).
- Balance for 5 to 10 players of varying skill on one shared world.
- Avoid first-come finite resources that the first player can claim. Generated loot is per player.
- **Technical parity:** how vanilla mechanics behave (physics, redstone timing, mob AI, spawning, item movement) stays exactly vanilla. It doesn't protect whether a resource exists: XP farms stop mattering because XP is gone, and dispensers using shears never run out. When we want different behavior, we add a separate variant instead of changing the original.
- **Seasons:** the game is built one Season at a time. Each replaces one part of vanilla and gets a short playtest that answers one question; anything not built yet stays vanilla.

## Open questions

- What is the setting or theme that gives Eras a reason to exist?
- How many Eras, and what does each one unlock?
- What does each Stronghold look like as a dungeon, and how is the climax arena protected?
- How is Settlement prosperity measured without dictating how people build?
- What replaces the Emerald economy, and what are the limits on buyer income?
- What are the target hours played for each Era?
- Where do raw stats come from: Augments, enchantments, or tool tiers?
- How much health do Heart upgrades give per dimension, and how does healing scale?
- Which of Super Awesome Enchanting and Super Awesome Villagers carry over, and how do they change?
