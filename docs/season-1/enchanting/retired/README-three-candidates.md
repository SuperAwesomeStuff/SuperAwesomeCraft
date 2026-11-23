# Season 1 enchanting: three candidates

> **Retired.** Superseded by [the library design](library.md), which was itself replaced by [the slot design](../README.md). Kept for reference.

**Status: Idea.** Three complete candidate designs for the Season 1 enchanting system, written so we can pick one (or merge parts) before building. None of them is decided. Whichever is chosen gets promoted into [vision.md](../../../vision.md) as Leaning.

| | [A. Catalysts and Capacity](a-catalysts-and-capacity.md) | [B. The Archive](b-the-archive.md) | [C. Runes](c-runes.md) |
|---|---|---|---|
| **In two sentences** | Pay a fixed recipe for each enchantment level and fit it into the item's Capacity. Found books registered with the library at any table unlock levels above II for everyone, and far-off materials raise Capacity. | Every enchantment is known at level I from day one, and the world researches higher levels and base stats together. You choose which known enchantments fill your item's few slots, and Masterwork favourite items with far-off materials. | Every Power and Yield enchantment is a rune you carve, keep (one per item), and slot into your gear's sockets. Top-rank runes need sockets Gilded with far-off materials, and the first carving of each middle rank halves its catalysts for everyone. |
| **Where the cost lives** | Personal: Catalysts consumed per level | Shared: research bills anyone can fund | Personal and reusable: runes are kept until you rank them up, one per item wearing them |
| **Item budget** | Capacity points | Slots (count) | Sockets (count), some Gilded |
| **Raw stats** (the open question in vision.md) | Option 1: stat enchantments cost Capacity | A new option: stats are researched for the whole world (World Sharpness, Protection and Power), and only the last level is personal (Masterwork) | Option 1 in physical form: stat runes compete for sockets, carved at the table |
| **Changing a build** | Extract one enchantment to a book at the table | Free, at any table | Pull runes out intact at the table |
| **Shared layer** (A and C differ as packages, chiefly *consumed* against *reusable*: A's top step is paid again on every item, C's runes, Grand ones included, come out intact; Gilding is per item in C, so its reusable bet covers runes and their catalysts; the two also differ in shared layer, so a result can't be credited to this axis alone) | The library: a found book unlocks every level above II of its enchantment for everyone | The Archive: everything researched belongs to everyone | First carvings and gifting: the first rune anyone carves at each middle rank halves that rank's catalysts for everyone, and each world-first Grand rune is credited in chat, and any spare rune, Grand ones included, can be given to friends; Gilding stays per item |
| **Long horizon** | Attunements; filling the library | The world's research; Masterwork | Rune collection; Gilding |
| **Its bet for the Season 1 question** | A personal Power budget creates a goal past full Netherite | Shared research plus a personal Masterwork layer does | A growing, reusable collection does |
| **Who it serves best** | Gear and power, collecting | Being near the group, slower players, late joiners | Collecting, gear and power, mischief (trading runes), generous friends |
| **Biggest risk** | A fast player finishes their items and is done | Gear-focused players feel their effort is diluted | The collection plateaus once each player has their runes |
| **Build size** | Large (lightest) | Largest (world data, bill and credit UI, item rewrites, two combat hooks) | Large (most client work) |

## Shared ground rules

All three candidates sit inside the same Season 1 frame, so these rules apply to every one of them.

### Season 1 frame

- **Season 1 replaces Gear and nothing else** ([vision.md](../../../vision.md#seasons)). The Nether, the End, villagers, structures and mobs are vanilla. No Era content exists yet, so "later-Era materials" in Season 1 means vanilla's own ladder: surface, deep caves and structures, the Nether, then far-off or dangerous places.
- **No XP** (decided, already built). Nothing costs levels.
- **No durability** (leaning, built in Season 1). Gear is permanent, so a build is something you keep, not something you replace.
- **Familiarity and Techniques** (leaning, built in Season 1) are a separate budget on pickaxes, axes and shovels. Techniques never cost enchanting budget, and enchanting never adds Technique slots. The tool's choices (vein-mining, tree-felling and so on) come from Techniques; enchanting's choices are mostly on weapons and armour.
- **Upgraded items can't be crafting ingredients.** Vanilla recipes match by item type, so a bow crafted into a Dispenser (or a rod into a Carrot on a Stick, by hand or by Crafter) would silently eat its upgrades. A small mixin on the crafting grid and the Crafter (not smithing, which must keep carrying upgrades to netherite) refuses any item carrying a non-curse enchantment or the mod's upgrade or rune components as an ingredient, except in recipes whose result copies the input's components (dyeing leather armour, adding a banner to a shield). The same mixin keeps items carrying the mod's own upgrade, slot or rune data out of furnace fuel slots (by hand or hopper); ordinary enchanted farm bows still burn, so vanilla fuel contraptions keep working.
- **The Enchanting Table keeps its crafting recipe and look but gets our screen**, and it is the one station for everything enchanting. No contraption depends on the table, so this doesn't break technical parity. The anvil only renames, for free (as already built). The grindstone takes **one item at a time** (with no durability, merging two items never gains anything, and vanilla's merge would silently drop the second item's upgrades); otherwise it keeps its vanilla behaviour (it strips enchantments and refunds nothing), except that A makes it refuse items with any Power or Yield enchantment (*"Use Extract at the Enchanting Table"*) and C makes it refuse items with runes. The vanilla repair recipe (two of the same item in the crafting grid) is disabled (by a recipe override or a small mixin; with `max_damage` gone it probably can't match anyway, so this is a safety net), since it would wipe every upgrade and enchantment. Whenever the table's screen closes (including disconnect or death), it hands back whatever it holds, as vanilla menus do.
- **Season 1 starts on a fresh world.** As a safety net, any Mending or Unbreaking on an item, including stored enchantments on books, is removed when it's next touched, and both have their supported items emptied (the same pass that keeps items up to date: when it enters or is ticked in a player's inventory, or a container holding it is opened), and the loot tables that name Mending directly (in 26.3, the Trial Chambers' `reward_rare`) are overridden. `#non_treasure`, `#on_random_loot`, `#tradeable` and `#treasure` are replaced in full (`#tradeable` keeps only Convenience enchantments, so even a missed librarian trade can't sell Power or Yield) (`replace: true`) without Mending and Unbreaking, since a datapack can't remove one entry from a vanilla tag, and a game test scans every loot table and trade for either.
- **Death is vanilla.** Graves come in a later Season; until then an item lost in lava is lost, with whatever was on it. (C's Rune Pouch is the one exception: a dropped pouch can't burn, be blown up or despawn, which the comparison should keep in mind when reading C's retention.)

### What Season 1 can and can't fix

Full Netherite and the Dragon still exist, because Season 1 leaves the rest of the game vanilla. **No enchanting system can remove them as milestones.** What enchanting can do is put a wide, sideways horizon *after* them, so a player who reaches full Netherite has something they want next. All three candidates test the same Season 1 question with a different bet, and each estimates how long its horizon lasts.

Two honest caveats apply to all three. **Season 1 adds no new fights**, so extra Power after full Netherite mostly makes vanilla mobs easier; the pull to far-off places is as much the [upgrade effects](#tier-4-fair-access-and-shared-upgrade-materials) (silent to Sculk, free Ender Pearls and so on) as the budget, and the playtest logs which effect each player picks first and asks why they went. And all three horizons draw on the same five far-off materials for roughly 15 to 25 hours, so the playtest mostly compares **the shape of the budget and the shared layer**, not the length of the horizon. Each candidate is a package: the result picks a package, and the primary measures, not horizon length, should drive the pick.

### Convenience costs no budget

The vision says mining speed, Silk Touch and mobility are Convenience: cheap, early and never needing combat. In all three candidates, **Convenience enchantments cost no budget** (no Capacity, slot or socket), are free or cost only tier 1 or 2 materials (each candidate says which), and are available from the first table with no book or discovery needed.

| Group | Enchantments | Rule |
|---|---|---|
| **Convenience** | Efficiency, Silk Touch, Aqua Affinity, Respiration, Depth Strider, Feather Falling, Soul Speed, Swift Sneak, Frost Walker, Lure, Loyalty, Riptide | Costs no budget. Available from day one, including the vanilla treasure ones (Soul Speed, Swift Sneak, Frost Walker), so no Convenience needs combat. Librarians may sell them. |
| **Yield** | Fortune, Looting, Luck of the Sea | Top level needs a tier 4 material, because yield compounds into everything else. Whether Yield costs budget is per candidate (A exempts pickaxes, shovels, hoes and fishing rods, so Fortune and Luck of the Sea; see there). |
| **Power** | Sharpness, Smite, Bane of Arthropods, Sweeping Edge, Fire Aspect, Knockback, Protection, Fire/Blast/Projectile Protection, Thorns, Power, Punch, Flame, Infinity, Multishot, Piercing, Quick Charge, Impaling, Channeling, Density, Breach, Wind Burst, Lunge, and the four new ones below | Costs budget. **Villagers never sell Power or Yield books, and smiths' equipment trades roll Power and Yield at most level II.** Wind Burst stays a treasure enchantment (ominous vaults only). |
| **Curses** | Curse of Binding, Curse of Vanishing | Vanilla behaviour, cost nothing, only from loot. |
| **Removed** | Mending, Unbreaking | Gone with durability. |

**Incompatibilities are vanilla's** `exclusive_set` tags, extended only for the new enchantments below (Warding joins the armour set; Frostbite excludes Fire Aspect; vanilla checks both ways). Maximum levels are vanilla's `max_level`. **Shields and Elytra have no budget** in any candidate (no Power or Yield enchantments in vanilla). Spears use their material's weapon row; each candidate sets its own numbers for bows, crossbows, tridents and maces.

### Four new armour and weapon enchantments

Vanilla armour has few Power choices, so every budget system runs out of armour decisions fast. All three candidates add these four, built from vanilla's data-driven enchantment effect components:

| Enchantment | Goes on | Effect | Max |
|---|---|---|---|
| **Warding** | Any armour | A magic specialist, the way Fire Protection is for fire: reduces magic damage (witch potions, evoker fangs, Harming, poison) twice as much per level as Protection does, using `damage_protection` with a new damage type tag `superawesomecraft:is_magic` (`magic`, `indirect_magic`). Joins `#exclusive_set/armor`. Vanilla's 80% protection cap still applies, and the Warden's sonic boom bypasses all enchantment protection, so Warding doesn't touch it. | IV |
| **Steadfast** | Any armour | 5% knockback resistance per level (`attributes` effect) | III |
| **Venom** | Swords, axes, spears | Poisons the target on hit (`post_attack`; whether a spear's charge attack fires it needs checking on 26.3). Useless on spiders and undead, which are immune to Poison; strong against illagers, creepers, animals and players. | II |
| **Frostbite** | Swords, spears, tridents | Slows the target on hit (`post_attack`). Exclusive with Fire Aspect. | II |

All four join `#non_treasure`, `#in_enchanting_table`, `#on_random_loot`, `#on_mob_spawn_equipment` and `#on_traded_equipment` (not `#tradeable`, which holds only Convenience), with a vanilla-like weight (Warding and Steadfast 5, like Fire Protection; Venom and Frostbite 2, like Fire Aspect) and costs in the same range as their nearest vanilla cousin, so fishing, chest loot and mob gear produce them like any other enchantment. (Villagers still never sell them as books, per the rule above.)

### Random sources stay below the ladder

Fishing, chest loot, mob equipment and villager trades all roll random enchantments. Left alone, an AFK fish farm (which stays vanilla under technical parity) would hand out Looting III and Sharpness V, skipping every candidate's tier 4 gate.

- **Every random source rolls Power and Yield enchantments at most level II**. Vanilla picks random levels in three places, and each gets a clamp: `EnchantmentHelper.getAvailableEnchantmentResults` (used by `enchant_with_levels`, fishing, mob spawn equipment and villager equipment trades), `EnchantRandomlyFunction`, and `SetEnchantmentsFunction` (fixed structure loot). Enchantment providers that pick a fixed enchantment (`SingleEnchantment`, used for raid mobs) already stay at level II or below. Each source gets a game test, plus one that rolls every enchantment provider in the game's data. Convenience and curses roll normally. Mending and Unbreaking are taken out of every tag (`#on_random_loot`, `#on_mob_spawn_equipment`, `#on_traded_equipment`, `#tradeable`, `#in_enchanting_table`). (The ominous vault's Wind Burst book is already level I in vanilla.)
- This changes *what* fish farms produce, not *how* they work. The vision's technical parity rule protects mechanics, not whether a resource exists.
- **Found gear works as found.** If a looted item is over its budget, it keeps working, but nothing can be added until it's back within budget. Removing is always allowed.

### Tier 4: fair access and shared upgrade materials

- **Per-player chest loot** (already **Decided** in [vision.md](../../../vision.md#adventuring)) comes from an existing mod added to the modpack rather than built by us (a 26.3 release must be confirmed before building, because tier 4 fairness depends on it): each structure loot container rolls separately for each player who opens it, so Echo Shards in Ancient City chests aren't first-come. Candidates are [Lootr](https://modrinth.com/mod/lootr), the best known (a Lootr add-on has a 26.3 Fabric release, so Lootr itself probably does; check its version list), [SlashLoot](https://modrinth.com/mod/slashloot) and Phantom Loot, which list 26.3 support *(unverified beyond their listings)*. Ominous vaults already reward each player once in vanilla.
- **Finding the places:** vanilla Cartographers already sell Trial Chamber explorer maps *(check on 26.3)*. Season 1 adds an **Ancient City explorer map** to master Cartographers (trades are data since 26.1; the map needs its own decoration icon, a small client asset). Buried treasure maps already come from shipwrecks and ocean ruins.
- **Per-item upgrades** (A's Attunement, B's Masterwork, C's Gilding) **all use the same five far-off materials, and each gives the item the same small effect** in every candidate. Each item can use each material once and never needs more than three, so nobody depends on the Heavy Core (7.5% per ominous vault opening). The effects are Convenience, never damage or protection, and the same effect on two items doesn't stack (under the hood the mod applies one temporary modifier per player per effect, never the same modifier from two items, which vanilla rejects).

| Upgrade material | Where it sends you | Effect on the upgraded item |
|---|---|---|
| **Echoing:** 4 Echo Shards | Ancient City | While held or worn, your movement and the item's use don't trigger Sculk sensors or shriekers (hooked on those blocks only, tracing projectile events back to their owner; Wardens still hear you) |
| **Tidal:** 1 Heart of the Sea | Buried treasure | While held or worn, you move more easily in water, about three-quarters of a Depth Strider level (`water_movement_efficiency` 0.25; Depth Strider is about 0.33 per level, and the attribute caps at 1, so it adds nothing on top of Depth Strider III), and your breath lasts about 50% longer (`oxygen_bonus` 0.5) |
| **Voidtouched:** 4 Shulker Shells | End Cities | When dropped, the item can't burn, be blown up or despawn (cactus and the void still destroy it, and the tooltip says only fire, explosions and despawning) (its `damage_resistant` tag includes `#is_fire` and `#is_explosion`, so netherite never loses its own fire immunity; not despawning needs its own small hook on the dropped item's age) |
| **Dragonsworn:** 8 Dragon's Breath | The Ender Dragon (respawned with four End Crystals for each batch) | While held or worn, your Ender Pearls cost no damage |
| **Tempest:** 1 Heavy Core | Ominous Trial Chamber vault | While held or worn, your Wind Charges launch you 50% higher |

- Each upgrade also adds a line to the item's tooltip and a mark to its model (a custom item-model property, shown in the hand and inventory; worn armour shows it in the tooltip only, leaving vanilla armour trims alone).
- **The Nether Star is never a per-item upgrade material**, because a Wither farm (vanilla, so it stays) would make it a free upgrade on every item. It can appear in one-off world bills (B).

### The source ladder

Every candidate prices higher levels in materials from further up this ladder. Tier 4 deliberately uses the content the September 2026 group skipped.

| Tier | Where | Example materials |
|---|---|---|
| 1. Surface and caves | Anywhere in the first evening | Lapis, Copper, Iron, Redstone, Amethyst Shards, String, Slime Balls, Bones, Flint |
| 2. Deep and structures | Deepslate, oceans, Trial Chambers, Pillager outposts | Diamonds, Gold, Prismarine, Nautilus Shells, Breeze Rods, Sculk, Phantom Membranes |
| 3. The Nether | Fortresses, Bastions, the wastes | Quartz, Blaze Rods, Magma Cream, Ghast Tears, Gold Blocks, Wither Skeleton Skulls, Netherite Scrap |
| 4. Far and dangerous | The places last season skipped | Echo Shards, Heavy Cores, Hearts of the Sea, Shulker Shells, Dragon's Breath, Nether Stars |

### Shared unlocks are permanent and announced

A's library and C's first carvings unlock something for everyone. In both, the unlock **happens at once at a table and is permanent**, so there's nothing to guard and no "use it, take it back" trick to stop; each is **announced in chat** with the player's name, counts at **every table in the world**, and is synced to clients when the table screen opens.

### Learnable in-game

Every candidate must pass this check, from the research ([gear systems](../../../research/gear-systems-in-games.md#learnability-checklist-from-what-the-readable-systems-share), [enchanting mods](../../../research/enchanting-mods.md#fit-with-superawesomecraft)):

- Every enchantment's tooltip says in one line what it does.
- The budget is one visible thing on the item, with each option's cost shown before you pay.
- Locked options are visible, with what unlocks them.
- Changing your mind is cheap, so experimenting is safe. (One named exception: in A, the top step of an enchantment is paid per item, and its recipe line says so before you pay.)
- No step needs a wiki or a recipe viewer.

### Measuring the playtest

The mod writes every enchanting operation (who, what item, what change, what it cost) to a log the [playtest tools](../../../../tools/playtest/README.md) can read. Six players is too few for retention numbers alone to prove much, and no durability and free Efficiency will probably get players to full Netherite *faster* than last time, so the measures are ranked:

1. **Primary, enchanting-specific:** after each player's first full Netherite set or Dragon kill (their *milestone*), **tier 4 materials they spend on enchanting**, and **the share of their sessions that include a paid enchanting operation** (one that consumes materials, including B's bill contributions, though B's deciding personal number is its Masterwork-only share; free swaps and moves are reported separately, so the candidates stay comparable). Each candidate predicts both.
2. **Secondary, retention:** days each player keeps logging in after their milestone, against the September 2026 baseline (the fastest player reached full Netherite in about 6 hours of play and left soon after; another left the day after the Dragon; the server lasted about 10 days and roughly 54 player-hours). Success is the median regular player still active on **day 14** and milestone players active for **7 more days**.
3. **Which lock stopped them:** every time a player clicks a greyed-out option, the log records it with its lock reason (A: library, recipe or Capacity, all that apply; B: research, slots or Masterwork; C: carving, Gilding or sockets), so an unused top level can be traced to the lock that stopped it.
4. **Top-step spend on later items:** after each player's milestone, the tier 3 and 4 catalysts spent on top levels (A's top steps, B's Masterworks, C's Grand runes) for their **second and later** items, compared with their first, valued at undiscounted prices; Attunement and Gilding are reported separately. Each candidate states its own number, so the one candidate that's played can fail on its own data: A predicts second items carrying a paid top step, B at least 50% Masterwork spend on later items, and C a direct reuse share (see each candidate). Since only one candidate is played, this compares nothing between them; the three are packages, not arms of one experiment.
5. **Sink:** iron, gold, diamonds and Catalysts spent on enchanting per player per week, checking the durability risk in vision.md (ore demand collapsing). Each candidate states a floor that the playtest can fail.
6. **A fixed question for builders and casual players:** "Did you feel you needed to fight to be comfortable?" All three put Fortune III and Looting III behind a far-off material, so this checks the shared rule rather than a candidate.

## Research behind these

- [Ascendant Arcana](../../../research/ascendant-arcana.md), and the old Super Awesome Enchanting catalyst table in the SuperAwesomeDatapacks repo
- [Enchanting mods](../../../research/enchanting-mods.md): Apothic, Quark Matrix, Enchanting Infuser, Botania, Ars Nouveau, Silent Gear
- [Gear systems in games](../../../research/gear-systems-in-games.md): Kanai's Cube, Monster Hunter, Hollow Knight, Deep Rock Galactic, Minecraft Dungeons, Guild Wars 2
- [Server gear systems](../../../research/server-gear-systems.md): Hypixel SkyBlock, Wynncraft, EcoEnchants
- [Durability](../../../research/durability.md), [tool levelling](../../../research/tool-levelling.md), [Game Design 101](../../../research/game-design-101.md)
