# Research: Enchanting and Gear Overhaul Mods

A survey of enchanting overhauls and gear-ability mods, to sit beside [Ascendant Arcana](ascendant-arcana.md) and [Tool Levelling](tool-levelling.md) (which already covers Tinkers' Construct, Tetra and a short Silent Gear and Apotheosis sockets entry). Gathered November 2026 from Modrinth and CurseForge listings, official wikis, minecraft.wiki, and source and data files on GitHub:

- Apothic Enchanting at commit `4d167fd` (6 October 2026, version 2.1.0 for 26.1.2)
- Quark `master` at `b868e5c` (18 November 2026)
- Enchanting Infuser branch `26.3.x`
- Ars Nouveau `main` recipe data
- Botania `1.21.1` lang file (the in-game Lexicon text)

Statements marked *(source)* were read directly from code or data files. Statements marked *(unverified)* come from one secondary source only, or the sources disagree. Reddit and forum opinions are marked *(community)* and are secondary sources.

## Vanilla baseline (1.21 to 26.4)

### The table, anvil and grindstone

- The table offers three slots that cost 1, 2 or 3 levels and Lapis. Up to 15 bookshelves count, and the bottom slot reaches level 30 at 15 shelves. *(minecraft.wiki, Enchanting mechanics)*
- **Offers are fixed per player.** Each player has an `XpSeed`. A table with the same shelf count offers the same enchantments for the same item until that player enchants something, which rerolls the seed. *(minecraft.wiki)* So "reroll by enchanting a book" is the vanilla trick, and every player sees different offers at the same table.
- **Enchantability** decides how many bonus enchantments are likely: Gold armor 25, Gold tools 22, Netherite, Leather, Wood and Mace 15, Iron tools 14, Copper tools 13, Chainmail 12, Diamond 10, Iron armor 9, Copper armor 8, Stone 5, and Book, Bow, Crossbow, Fishing Rod and Trident 1. *(minecraft.wiki)*
- The anvil combines items and books for levels. The cost grows with each use (prior work penalty). The grindstone strips non-curse enchantments and gives back some XP. *(minecraft.wiki)*

### Data-driven enchantments (1.21+)

Since **1.21 (24w18a)** enchantments are JSON files in `data/<namespace>/enchantment`. *(minecraft.wiki, Enchantment definition)*

- **Top-level fields:** `description`, `exclusive_set`, `supported_items`, `primary_items` (the items the table offers it on), `weight`, `max_level` (1 to 255), `min_cost` and `max_cost`, `anvil_cost`, `slots`, `effects`.
- **Value effects:** damage, protection, armor effectiveness, knockback, equipment drops, ammo use, projectile count and spread, piercing, fishing luck and speed, `repair_with_xp`, `item_damage`, `block_experience`, `mob_experience`, and others.
- **Entity effects:** `hit_block`, `tick`, `projectile_spawned`, `post_attack`, `post_piercing_attack`, `location_changed`. Actions include `apply_mob_effect`, `apply_impulse`, `damage_entity`, `explode`, `ignite`, `replace_block`, `replace_disk`, `summon_entity`, `spawn_particles`, `play_sound` and `run_function`. Components such as `attributes`, `damage_immunity` and `prevent_equipment_drop` are also available.
- **Additions since then:** `projectile_spawned` (1.21.2); `exponent`, `post_piercing_attack` and `apply_impulse` (1.21.11, 25w41a); `apply_exhaustion` (25w43a). In 26.1, `post_piercing_attack` stopped needing a food level. In 26.4 snapshot 4, `explode`'s particle and sound fields became optional.
- **Limit:** the vocabulary is fixed. Anything outside these components needs code. With `run_function` and attributes, though, most of Ascendant Arcana's behaviour enchantments could be data.

### Recent vanilla changes (26.1 to 26.4)

Read from the version pages' wikitext. *(minecraft.wiki)*

- **No changes to how the table, anvil or grindstone work in 26.1, 26.2, 26.3 or the 26.4 snapshots.**
- **26.1** made **villager trades data-driven**, with `given_item_modifiers` and `double_trade_price_enchantments`. Enchanted-book trades are now data. The master librarian swaps Name Tags for red and yellow candles, and under the Villager Trade Rebalance experiment always has an enchanted-book trade. The table now shows a closed book when idle.
- **26.3:** enchantments now apply to items damaged while protecting an undead mob from the sun. A new `enchantment_level` context provider was added, and `enchant_with_levels` and `set_enchantments` accept context providers.
- **26.4** ("Deep Freeze", in development, planned Q4 2026): only the `explode` change above, plus accessibility work on the table UI (keyboard navigation and narration).
- Anything we build is still a variant on a stable vanilla base. Mojang hasn't touched the table's design since 1.8's clue tooltip.

## Apothic Enchanting (Apotheosis's enchanting module)

**The vanilla table, extended with three stats from the blocks around it.** It is NeoForge only, for 26.1.2 and 1.21.1, MIT licensed, and needs a client install. *(Modrinth; `gradle.properties`)*

### The three stats

Each block around the table contributes stats from a data file (`enchanting_stats/*.json`). *(source)*

- **Eterna is power.** In 2.1.0 the top slot's level equals the table's Eterna, clamped to 0 to 100. *(source: `getEnchantmentCost`, `EnchantmentTableStats`)* Older guides say each point of Eterna gives two levels. *(unverified: probably an older version)*
  - **Each shelf has its own Eterna cap (`maxEterna`).** A shelf adds Eterna only until the table reaches that cap. This forces players up the shelf ladder: you cannot reach 100 with 200 Hellshelves.
- **Quanta is variance.** The real power is level × (1 + Quanta × r / 100), clamped to 1 to 200. Normally r follows a bell curve between −1 and 1. A "stable" table (Bookshelf of Stability) makes r uniform between 0 and 1, so outcomes only go up. *(source: `getQuantaFactor`)*
- **Arcana is rarity and count.** It reweights enchantments by rarity in ten steps, from 0 (Common 10, Uncommon 5, Rare 2, Very Rare 1) to 99 (1, 2, 5, 10). *(source: `Arcana.java`)* One enchantment is guaranteed, a second at 25 Arcana and a third at 75. *(source)* The changelog says 2.1.0 fixed the thresholds, which had been 33 and 66.
- **Secondary stats:**
  - **Clues** reveal more of the offered enchantments (Sightshelf +1, tier 2 +2).
  - **Stability** is described above.
  - **Filtering shelves** blacklist enchantments.
  - **Treasure shelves** allow treasure enchantments.
- **Negative shelves** let players shape a table: the Melonshelf lowers Quanta, the Stoneshelf lowers Arcana, and the Beeshelf gives +100 Quanta and −30 Eterna. *(source)*
- **Enchantability no longer feeds Arcana in 2.1.0.** It is now a % chance per enchantment to roll one level higher, up to 80% and then diminishing. This can push enchantments past the configured cap, and those are marked with a star. *(source: changelog, `selectEnchantment`)*

### The shelf ladder is the era ladder

Selected values from the data files. *(source)*

| Shelf | Eterna (cap) | Quanta | Arcana | Gate |
|---|---|---|---|---|
| Vanilla shelves | to 30 | | | Overworld |
| Hellshelf / Seashelf | 3 (45) | 3 / 0 | 0 / 3 | Nether / Ocean |
| Infused Hellshelf / Seashelf | 5 (60) | 5 / 0 | 0 / 5 | Infusion at 45 Eterna |
| Deepshelf | 5 (70) | 5 | 5 | Infused from Dormant Deepshelf at 60/40/40 |
| Echoing / Soul-Touched Sculkshelf | 10 (80) | 10 to 15 | 10 to 15 | Warden drops *(per guide)* |
| Endshelf / Pearl Endshelf | 5 to 10 (90) | 5 to 7.5 | 5 to 7.5 | Dragon's Breath *(per guide)* |
| Draconic Endshelf | 20 (100) | | | Dragon Head from End Ships *(per guide)* |

- Vanilla blocks count too. Skulls add Quanta (Wither skull +10) and Amethyst Clusters add Eterna up to 40. *(source)*
- **Infusion** is a second recipe type on the same table. An item in the table with stats in a set window turns into another item. *(source: `recipe/infusion/*.json`)*
  - Hellshelf to Infused Hellshelf needs Eterna 45 and Quanta 30.
  - An Echo Shard turns into 4 Echo Shards at 70/50/50, a renewable route to an otherwise finite item.
  - Some recipes have a *maximum* too. The Tome of Extraction needs Quanta between 25 and 75. Hitting a window means building a specific table, not just the biggest one.
- **Max Eterna attribute (2.0.0):** a per-player cap on Eterna, which Apotheosis's World Tiers use. *(source: changelog)* Progression can belong to the player or the world rather than only to the shelves.
- **Enchanting Table of the Raven (2.0.0):** an endgame table where players set the stats by hand. It ships without a recipe. *(source: changelog)*

### Max levels, library and tomes

- **Max levels rise.** By default each multi-level enchantment's cap is raised to the highest level whose minimum power stays under 200. Single-level enchantments stay at 1. Data maps can set `max_level`, `max_loot_level` and `max_anvil_combine_level` per enchantment. *(source: `computeDefaultMaxLevel`, `EnchantmentInfo`)*
- **Enchantment Library.**
  - Each book deposited adds 2^(level−1) points to that enchantment. You can withdraw any level up to the highest you have ever deposited, paying its points.
  - The Library holds up to level 16. The Ender Library ("Library of Alexandria"), infused at 100/45 to 50/100, holds up to level 31. *(source: `EnchLibraryTile`)*
  - Per guide, it strips curses on deposit. *(unverified)*
  - **This turns many low books into one high book, and makes every duplicate useful.**
- **Tomes.** Scrapping Tome: half the enchantments onto a book, item destroyed. Superior Scrapping: all of them, item destroyed. Extraction: all of them, item kept. *(source: lang file)* That is a clear early, middle and late ladder for removal.
- **XP is still the currency.** The menu still charges XP and Lapis. *(source: `ApothEnchantmentMenu`)*

### Learnability and community

- An info screen (the "?" button) lists every enchantment possible at the current stats and its power range, and blocks show their stats in tooltips. *(source: `EnchantingInfoScreen`; per guide)* That makes a numeric system readable without a wiki.
- *(community)* People call it a better mechanic than vanilla, but the stats are hard to follow without a guide. An addon (HB's Enchanting) exists to "simplify" the stats. A r/feedthebeast poster called Apotheosis "very unbalanced". Guides have to warn that Infusion gives no feedback on which stat is missing, and that infused shelves look like their base versions.
- Every guide says to build an XP farm before going past level 60. *(per guide)*
- **Multiplayer:**
  - Tables are physical builds. One group build serves everyone.
  - Offers still come from each player's own seed.
  - XP is personal, so the table is shared but the currency is not.

## Quark: Matrix Enchanting

**The enchanting table becomes a 5×5 puzzle board.** It is in Quark (Oddities) on NeoForge, with a separate Fabric port ("Matrix Enchanting"). By default it replaces the vanilla table in place (`automaticallyConvert = true`). *(source: `MatrixEnchantingModule`)*

### How a session works

- Lapis is stored as charge, 4 pieces per Lapis. *(source)*
- **You pay to generate pieces.** The + button spends XP for a random **piece**: one enchantment at level 1, in one of 11 shapes of 5 or 6 cells, randomly rotated. *(source: `EnchantmentMatrix`)*
- **The price creeps.** Each piece costs `1 + count / 9` levels. You also need a minimum level that rises about 1.2 per piece, then 1 per piece after the 8th. *(source)*
- **The number of pieces is capped** at `3 + (shelves + 1) / 2 + enchantability bonus`, with shelves capped at 15. Books get 1 plus 1 per 7 shelves. *(source)*
- **You place pieces on the board.** Only placed pieces count. They cannot overlap or leave the grid, so a big piece crowds others out. *(source)*
- **Duplicates merge.** Dropping a duplicate onto a placed piece adds XP to it, and it levels up when that XP reaches its current level. *(source)*
- Incompatible enchantments never appear in the same session (`incompatibleMultiplier = 0`). Seeing Fortune means no Silk Touch. Duplicates are 1.4× more likely (`dupeMultiplier`). *(source)*

### Candle influence

- Up to 4 lit candles of one colour near the shelves multiply their enchantments' weight by 1.125 each. *(source: `influencePower`, `influenceMax`)*
- The mapping is a 16-entry list: Yellow for Fortune, Looting and Luck of the Sea; Pink for Silk Touch and Channeling; Blue for Efficiency, Sharpness and Power; Red for Fire Aspect and Flame; and so on. *(source)*
- A Soul Sand block under a candle inverts it, so the candle *dampens* those enchantments instead. *(source: `soulCandlesInvert`)*
- Other blocks can be configured as influencers, for example Sea Pickles for water enchantments. *(source)*
- The colour mapping is invisible in-game unless a player reads the config or a wiki. *(inference)*

### Community and multiplayer

- *(community)* "really love" it, but it is "too expensive to get the full potential". One player noted a sword can take 16 pieces at a level each, against 3 levels in vanilla. Others thought vanilla was "too cheap" and the Matrix result better. A 2022 thread asks how to nerf it as "too strong". Most of the discussion is one 2020 r/QuarkMod thread.
- **Multiplayer:** the board lives in the table's block entity *(source: `MatrixEnchantingTableBlockEntity`)*, so one player's half-built board waits for them. Two players would fight over one table; a group needs several.

## Enchanting Infuser (Fuzss)

**A new table where you pick enchantments and levels from a full list.** Nothing is random. It runs on Fabric, Forge and NeoForge, 1.19 to 26.3, licensed MPL-2.0, and needs both client and server. *(Modrinth)*

- **The cost is anvil cost × level.** It is scaled so the most expensive full combination for an item costs `maximumCost = 30` levels. Treasure enchantments count double, and every level costs at least 1. *(source: `EnchantmentCostHelper`, `ServerConfig`)*
- **Bookshelves still gate max levels.** Fifteen are needed for maximum level (`maximumBookshelves = 15`). *(source)*
- **Two tiers:**
  - The **Infuser** enchants unenchanted items only.
  - The **Advanced Infuser** can modify or remove existing enchantments, repair for levels (25% per step at 2× level cost) and disenchant for XP.
  - Each tier has its own config. *(source; Modrinth)*
- Optional settings allow books, anvil-only enchantments (Sharpness on axes) and treasure. All are off by default. *(source)*
- Descriptions show in the UI if Enchantment Insights is installed. *(Modrinth)*
- **Lesson:** a full list of choices is the most readable design possible, but on its own it collapses straight to "everyone picks the best set". The only brakes are cost and shelves. It has no trade-off.

## Easy Magic (Fuzss)

**Quality-of-life fixes for the vanilla table.** Fabric, Forge and NeoForge, 1.19 to 26.3, MPL-2.0, needs client and server. It is listed as incompatible with Apotheosis and the Quark matrix. *(Modrinth)*

- Items and Lapis stay in the table after you close it, and float above it.
- A **reroll** button costs a little Lapis and some XP points (not levels). The amounts were not checked. *(unverified)*
- It previews more than one of the offered enchantments (configurable; default 1).
- Fewer shelves can be required for max level, and non-colliding blocks such as torches don't block shelves.
- **Lesson:** a paid reroll is the honest version of vanilla's enchant-a-book trick. It keeps randomness but makes it a visible spend.

## Grindstone and disenchanting mods

- **Grind Enchantments** (Fabric; GPL-3.0 on Modrinth, LGPL on the author's docs, so the licence is unclear):
  - Item + Book in a grindstone moves *all* enchantments to the book and returns the item clean.
  - Book + Book moves the top enchantment from one to the other, which splits mixed books.
  - XP cost is configurable *(per docs)*. Key names such as `disenchant_cost_factor` come from a third-party wiki. *(unverified)*
  - About 4.2M downloads, which shows how much players want lossless extraction. *(Modrinth)*
- **The wider pattern.** Easy Disenchanting (anvil, vanilla combine cost), Enchant Unbinder (one chosen enchantment per use, level-scaled XP; curses 10 levels), Enchant Extract (5 levels each), and several more. The genre is crowded because vanilla offers no way to move an enchantment. *(Modrinth listings, read via search results only; unverified individually)*
- **Ascendant Arcana** goes the other way: grinding destroys the item for scrap. Apothic Enchanting puts both options on a ladder, from destructive scrapping early to lossless extraction late.

## Enchantment Descriptions (Darkhax)

- A client-side mod that adds a one-line description of each enchantment to enchanted book tooltips. It covers all vanilla enchantments in 13+ languages and most modded ones. Every loader, 1.9.4 to 26.3, LGPL-2.1. *(Modrinth)*
- It is in almost every modpack because **vanilla never says what an enchantment does.** Apothic Enchanting had to add its own inline option because its tooltips break this mod. *(source: changelog 1.4.2)*
- Data-driven enchantments since 1.21 have a `description` text component, but that is the name, not an explanation. *(minecraft.wiki)*
- **For us:** every enchantment needs an in-game explanation in its tooltip from day one. Without it, "learnable without a wiki" fails at the first book.

## Ritual-style enchanting

### Ars Nouveau: Enchanting Apparatus

- **A fixed recipe for each enchantment level.** The apparatus sits on an Arcane Core with up to 8 pedestals within 3 blocks and a Source Jar nearby. *(ars.guide)*
- **Levels must be climbed one at a time.** "To apply a level 2 or higher enchantment, the item must already have the previous level." *(ars.guide)*
- **Example recipes:** *(source: `recipe/fortune_*.json`, `silk_touch_1.json`)*
  - Fortune I: Earth Essence, 1 Diamond, 6 Source Blocks, 6,000 Source.
  - Fortune II: 2 Earth Essence, 4 Diamonds, 2 Source Blocks, 8,000 Source.
  - Fortune III: 3 Earth Essence, Diamond Block, 2 Lapis Blocks, 2 Source Blocks, 9,000 Source.
  - Silk Touch: 2 Emeralds, 2 Diamonds, 2 Lapis Blocks, 2 Source Blocks, 9,000 Source.
- Source is a generated resource (from Sourcelinks fed by players' farms and mobs), so **the cost is an automatable flow, not a personal XP pool.** That suits a shared base. *(inference from mod design; Source generation was not read in source)*
- It is very close to Ascendant Arcana's per-level recipes. The difference is the physical altar with items on display, and the "previous level first" rule, which turns each enchantment into a short ladder.
- Recipes are learned from the in-game Worn Notebook (Patchouli). *(unverified for current version)*

### Botania: Mana Enchanter

- **A multiblock.** About 11×7, built from 17 Obsidian, 10 Mystical Flowers, 6 Mana Pylons and a Lapis Block, then activated with a Wand. *(source: Lexicon text)*
- **It applies dropped Enchanted Books without consuming them.** Only the first enchantment on each book counts, and pre-enchanted items are refused. *(source)*
- Mana cost scales with the number, rarity and level of enchantments. Breaking any part of the structure mid-process loses all the Mana. *(source)*
- **A book becomes a reusable blueprint, not a consumable.** That is the strongest "library" idea in this survey. One copy of Fortune III serves a whole server forever, paid for in a shared, automatable resource.

## Gear-ability mods (accessories and relics)

### Artifacts

- Uncraftable accessories found only by exploring (the count wasn't checked). They come from structure chests, archaeology, the rare mob wearing one, and **Mimics** in a new campsite structure, which always drop one. Fabric, Forge and NeoForge, up to 26.1, MIT. *(Modrinth; GitHub wiki Items)*
- **Each item is tied to a structure:** Digging Claws in Mineshafts and toolsmiths, Cloud in a Bottle in outposts and End Cities, Night Vision Goggles in shipwrecks and Mineshafts, Bunny Hoppers in jungle temples, the Chorus Totem in End Cities and mansions, Power Glove (+4 damage) in Strongholds and outposts.
- Effects are mostly pure upside. A few have flavour drawbacks: the Warp Drive costs hunger, and the Whoopee Cushion makes noise. Every effect can be tuned or made cosmetic through game rules (1.19.3 to 1.20.4) or config (1.20.6+). *(GitHub wiki)*
- **Lesson:** a set of named, horizontal trinkets, each with one clear verb (double jump, no fall damage, faster digging), reads instantly. Tying each to a structure gives exploration a goal. Their only source is chest luck, though, and the first player to reach a chest takes it, unless loot is per player as ours will be.

### Enigmatic Legacy

- Forge only, up to 1.20.1, last main release 2.30.1 in August 2024. It aims to "contribute to vanilla progression": gear tiers past Netherite, plus Curios rings, charms and spellstones. *(CurseForge)*
- **Discovery:** recipes unlock in the vanilla recipe book from key ingredients, and a Patchouli guidebook (the Enigmatinomicon) is craftable. Spellstones can't be crafted; each comes from the dungeon of its element, such as the Blazing Core in Nether Fortresses.
- **The Ring of the Seven Curses** *(official wiki)*:
  - It cannot be removed once worn.
  - **Seven curses:** double damage taken, neutral mobs hostile, armor 30% weaker, 50% less damage to monsters, fire lasts until water, death leaves a soul crystal and costs 10% max health until recovered, and no sleep (phantoms).
  - **Seven blessings:** +1 Looting, +1 Fortune, 4× XP, +10 enchanting power, inventory Ender Chest, unique mob drops, and access to bearer-only items.
- **Lesson:** an opt-in, permanent, *player-level* trade-off makes a memorable identity. It's a "hard mode with rewards" that some players choose and others don't. Bearer-only tooltips are hidden from non-bearers. That is the opposite of discoverable, and it works only because the item is a deliberate secret.

### Relics (Octo Studios)

- About 25 Curios relics with "MMO-like" random stats and active abilities. Forge and NeoForge, up to 1.21.1. Licence ARR. *(Modrinth)* Found in structure-specific loot: some only in desert pyramids, some in Mineshafts, some in farmer or hunter houses. *(GitHub wiki)*
- **Levelling** *(per minecraft-guides.com; unverified against source)*:
  - Relics level from *using their abilities*: 100 Relic XP for level 1, +100 per level, 5,500 total for level 10.
  - Each level gives a point to raise an ability.
  - Upgrades, resets, rerolls and hints are paid in player XP. A 1.21.1 reroll costs 50 XP, and a worse result adds "Luck" that makes later rerolls dearer.
- **Research:** in 1.21.1 some abilities are locked behind a constellation puzzle (join the stars to match a picture). In the beta every ability has one, and research is saved to the *player*, so later copies arrive pre-researched. *(per guide)*
- **Lesson:** random stat rolls plus paid rerolls is slot-machine play (see [Tool Levelling](tool-levelling.md)). The good parts:
  - Leveling comes from *using the ability*, not from generic XP.
  - Knowledge (research) lives on the player, so it isn't lost with the item, the same pattern as our Codex.

## Material-driven stats

### Silent Gear

- Tools, weapons, armor, elytra and curios built from parts: a main part (head, blade, plates) plus a rod, binding, grip, cord or lining. Forge and NeoForge, 1.12.2 to 26.2, MIT. *(Modrinth)*
- **Stats come from the parts' materials.** Each material has stats and **traits** tied to the part type it is used in. *(GitHub wiki Traits (1.21.x))*
  - Most traits cap at level 5. Some cap at 1, 3 or 7.
  - Examples: Malleable / Brittle (10% per level chance of less or more wear), Soft (slower as it wears), Jagged and Sharp (stronger as it wears), Bastion (+1 to +5 armor), Lucky (+0.5 to +5 Luck), Venom (poison).
  - The wiki warns that its generated "found on" lists are inaccurate.
- **Synergy** (the wiki marks this page outdated):
  - More distinct materials in a part raise its stats, with diminishing returns.
  - Mismatched tier or rarity lowers them, possibly below 100%.
- **Upgrades are capped at one per type:** a tip, a coating (smithing table), a binding, a grip. A material grader assigns grades E to MAX through catalysts. *(Modrinth)*
- **Lesson:** "this material makes your tool *Magnetic* or *Jagged*" turns ores into personalities, not just a tier number. The cost is a combinatorial space that players can't read without a guide or recipe viewer. *(inference; matches the Ascendant Arcana playtest note about relics)*

### Spartan Weaponry and Better Combat (brief)

- Spartan Weaponry (Forge and NeoForge, up to 1.20.1, Apache-2.0) gives **weapon types** fixed traits and **materials** the damage tier. *(Modrinth)*
  - Reach I/II: spear, lance, pike.
  - Sweep: longsword.
  - Armour Piercing: warhammer.
  - Two-Handed: mining fatigue with an offhand item.
  - Conditional damage: katana 2× without chestplate, rapier 3× unarmoured, dagger 3× from behind, lance 2× mounted.
- **Splitting type (verb) from material (tier) is the useful idea.** It gives horizontal choice inside any era. Better Combat is animations and combos and isn't about stats; it was not researched further.

## Patterns across all of them

| Mechanism | Who does it | Creates a choice? | Long horizon? | Shared? |
|---|---|---|---|---|
| Stats from surrounding blocks with per-shelf caps | Apothic Enchanting | Yes: shape a table for power, variance or rarity, and hit infusion windows | Yes: shelf ladder through the Nether, Ocean, Ancient City, Warden, End | **Yes:** one built table serves all |
| Library: books pooled as points | Apothic Enchanting | Weak | Yes: level 16, then 31 | **Yes:** one library per base |
| Book as reusable blueprint | Botania Mana Enchanter | No | Moderate | **Yes, strongly** |
| Per-level fixed recipes, previous level required | Ars Nouveau, Ascendant Arcana | Moderate (what to spend on) | Yes, through era materials | Partly (shared resource flow) |
| Puzzle board with spatial limits | Quark Matrix | **Yes:** which pieces fit | No | No (per session) |
| Pick from a full list | Enchanting Infuser | Only with a budget | No | No |
| Paid reroll | Easy Magic, Relics | No (it's a gamble) | No | No |
| Lossless extraction | Grind Enchantments, Apothic Extraction tome | Enables experiments | No | Enables gifting |
| Structure-tied trinkets | Artifacts | Yes, if slots are limited | Moderate (a collection) | Per-player loot needed |
| Permanent opt-in curse/blessing | Enigmatic Legacy | **Yes:** an identity choice | No | No |
| Material traits | Silent Gear, Spartan (type traits) | Yes | Moderate | No |

## Fit with SuperAwesomeCraft

**What fits:**

- **Shelf ladders are era progression in physical form.** Apothic's per-shelf Eterna caps (45 Nether, 60 infused, 70 to 80 Ancient City and Warden, 90 to 100 End) are exactly "the next horizon needs the next dimension's materials", and the table *is* a build.
  - A **shared Enchanting Hall** at the Settlement, upgraded with each Era's shelf, lets one player's Nether trip raise everyone's table. That serves "Progress is shared" better than any personal unlock.
  - Shelves are blocks, so they can be shown and explained in tooltips.
- **Turning many books into one high book via a library** gives Collectors a long-horizon project that helps everyone. Combine Apothic's library (pool duplicates into higher levels, capped by tier) with Botania's rule that a book is not consumed. A server could then *own* "Fortune III" as knowledge, while each application still costs era materials. This matches the "Chiseled Bookshelf library" Leaning in [vision.md](../vision.md#gear).
- **Infusion windows make real table-shaping choices.** Requiring Quanta *between* two values, or negative shelves (Melonshelf, Stoneshelf), means the best table depends on the goal. That's a puzzle, not a maximum, and it fits "no single max-gear endpoint".
- **Removal on a ladder.** Destructive scrapping is cheap and early; lossless extraction comes later. That is better than Ascendant Arcana's destroy-only grindstone, and it lets gear be handed down to new players, which is our catch-up path.
- **Per-level recipes with "previous level first"** (Ars Nouveau) fit Catalyst enchanting. Each level's recipe teaches the next Era's material, one step at a time.
- **Opt-in identity trade-offs.** A "Ring of Seven Curses"-style pact is a strong idea for the *Mischief and challenge* motivation, as an optional side system.
- **Weapon type traits** (Spartan) offer horizontal Power within an Era if the raw-stats question goes the tool-tier route (option 2 in vision.md).

**What doesn't fit, or needs changes:**

- **XP is the currency in almost all of these** (Apothic, Quark, Infuser, Easy Magic, Relics). With no XP, every cost has to move to materials or a shared resource like Source or Mana. Ars Nouveau and Botania show that a generated, automatable flow works and suits a shared base.
- **Randomness:** Quanta, Matrix pieces, Easy Magic and Relics rerolls are all gambles. They fail "learnable" and "earned". If any randomness stays, make it Apothic's *stable* variant: it only ever goes up, and the range is shown.
- **Uncapped max levels** (Apothic raises caps toward power 200, the library to 31) break Capacity budgets unless Capacity scales with level. If levels rise, cost per level in Capacity has to rise faster.
- **Hidden rules fail our learnability bar:** Quark's candle colours, Apothic's numbers without the info screen, Silent Gear's synergy, Enigmatic's bearer-only tooltips. Anything kept needs an in-table explanation, as Apothic's "?" screen does. Every enchantment needs an Enchantment Descriptions-style line in its tooltip.
- **Pick-from-list with no trade-off** (Infuser) is a straight path back to "everyone rushes the same set". It works only with Capacity.
- **Drop-only gear** (Artifacts, Relics, Spellstones, the Draconic shelf's Dragon Head) is luck-gated and first-come. Per-player loot fixes the first-come part; a crafted fallback should fix the luck part.
- **Parity:** Quark converts the vanilla table in place, and Easy Magic and Apothic change it in place. Ours must be a new block. vision.md already says the vanilla table is replaced, so contraptions don't depend on it.

**Multiplayer notes:**

- Built tables and libraries are the strongest shared progression in this survey. Personal seeds, personal XP and personal boards are the weakest.
- Per-session block-entity state (the Quark board) makes one table a queue. A shared hall needs several stations, or state stored per player.
- Apothic's **Max Eterna attribute** shows a hybrid: the table is shared, but each player's ceiling is a separate number. That's useful if we ever want catch-up easing at the table (raise newcomers' ceiling to the world's), but it is against "Progress is shared" if used as a personal gate.

## Recommendations

1. Make the new table's power come from **era shelves with per-shelf caps** (Apothic) and build it as a shared Settlement hall. One player's trip to a new dimension raises the table for everyone.
2. Make the **library** a server-wide knowledge store. Books pool into higher levels (Apothic) and are not consumed when applied (Botania). Each application still costs era Catalysts and Capacity.
3. Use **per-level recipes with "previous level first"** (Ars Nouveau) as the Catalyst structure, priced in materials and never XP.
4. Give **removal a ladder**: destructive and cheap early, lossless later. That keeps experiments and hand-me-downs cheap.
5. If any randomness remains, only allow **stable, upward-only, shown** variance. No paid rerolls.
6. Put an **explanation in every tooltip** and an **info screen at the table** listing what's possible at its current stats, so nothing needs a wiki or a recipe viewer.
7. Keep **opt-in pacts** (Enigmatic) and **structure-tied trinkets** (Artifacts, per-player loot) on the design map as Ideas for later Seasons.

## Sources

- Apothic Enchanting source (`gradle.properties`, `changelog.md`, `table/ApothEnchantmentHelper.java`, `table/Arcana.java`, `table/EnchantmentTableStats.java`, `table/ApothEnchantmentMenu.java`, `library/EnchLibraryTile.java`, `EnchantmentInfo.java`, `ApothicEnchanting.java`, `data/apothic_enchanting/apothic_enchanting/enchanting_stats/*.json`, `recipe/infusion/*.json`, `lang/en_us.json`): https://github.com/Shadows-of-Fire/Apothic-Enchanting
- Apothic Enchanting on Modrinth: https://modrinth.com/mod/apothic-enchanting
- Apothic Enchanting guide: https://www.minecraft-guides.com/mod-guides/apothic-enchanting-guide/
- Apotheosis guide (adventure module): https://www.minecraft-guides.com/mod/apotheosis/
- HB's Enchanting (Apotheosis simplification addon): https://www.modpackindex.com/mod/213995/hbs-enchanting-apotheosis-overhaul
- Apotheosis review (community): https://moddex.gg/mod/apotheosis/reviews/1858
- Telegram repost of r/feedthebeast "Apotheosis is very unbalanced" (community, unverified): https://t.me/s/MinecraftModded/48533
- Quark source (`addons/oddities/module/MatrixEnchantingModule.java`, `inventory/EnchantmentMatrix.java`, `block/be/MatrixEnchantingTableBlockEntity.java`): https://github.com/VazkiiMods/Quark
- Quark site: https://quarkmod.net/
- Matrix Enchanting (Fabric port): https://modrinth.com/project/matrix-enchanting
- r/QuarkMod, "Balance of Matrix Enchanting" (community): https://redlib.ssps.io/r/QuarkMod/comments/hslesa/balance_of_matrix_enchanting/fyyk8g6/?context=3
- r/QuarkMod, "How to nerf matrix enchanting" (community): https://axebps-redlib.hf.space/r/QuarkMod/comments/y0e6g7/how_to_nerf_matrix_enchanting
- Enchanting Infuser: https://modrinth.com/mod/enchanting-infuser
- Enchanting Infuser source, branch 26.3.x (`common/config/ServerConfig.java`, `common/util/EnchantmentCostHelper.java`): https://github.com/Fuzss/enchanting-infuser/tree/26.3.x
- Easy Magic: https://modrinth.com/mod/easy-magic
- Grind Enchantments: https://modrinth.com/mod/grind-enchantments
- Grind Enchantments docs: https://docs.mschae23.de/Grind_enchantments
- Grind Enchantments third-party wiki (config keys, unverified): https://www.9minecraft.net/grind-enchantments-mod/wiki/
- Easy Disenchanting: https://modrinth.com/mod/oytHeCkq
- Enchant Unbinder: https://modrinth.com/mod/enchant-unbinder
- Enchant Extract: https://modrinth.com/mod/enchant-extract
- Enchantment Descriptions: https://modrinth.com/mod/enchantment-descriptions
- Ars Nouveau, How to Enchant: https://ars.guide/book/enchanting/how_to_enchant/
- Ars Nouveau, Enchanting Apparatus: https://ars.guide/book/crafting/enchanting_apparatus/
- Ars Nouveau recipe data (`fortune_1..3.json`, `silk_touch_1.json`): https://github.com/baileyholl/Ars-Nouveau/tree/main/src/generated/resources/data/ars_nouveau/recipe
- Ars Nouveau Enchanting Apparatus guide (costs): https://www.minecraft-guides.com/mod/ars-nouveau/wiki/enchanting-apparatus/
- Botania Lexicon text, "Enchanting with Mana" (`lang/en_us.json`, branch 1.21.1): https://github.com/VazkiiMods/Botania/blob/1.21.1/Xplat/src/main/resources/assets/botania/lang/en_us.json
- Artifacts: https://modrinth.com/mod/artifacts
- Artifacts wiki, Items: https://github.com/ochotonida/artifacts/wiki/Items
- Artifacts wiki, Game Rules: https://github.com/ochotonida/artifacts/wiki/Game-Rules
- Enigmatic Legacy: https://www.curseforge.com/minecraft/mc-mods/enigmatic-legacy
- Enigmatic Legacy wiki, Ring of the Seven Curses: https://github.com/Aizistral-Studios/Enigmatic-Legacy/wiki/Ring-of-the-Seven-Curses
- Relics: https://modrinth.com/mod/relics-mod
- Relics wiki: https://github.com/Octo-Studios/relics/wiki
- How Relics Work (guide): https://www.minecraft-guides.com/mod/relics/wiki/how-relics-work/
- Silent Gear: https://modrinth.com/mod/silent-gear
- Silent Gear wiki, Traits (1.21.x): https://github.com/SilentChaos512/Silent-Gear/wiki/Traits-(1.21.x)
- Silent Gear wiki, Synergy: https://github.com/SilentChaos512/Silent-Gear/wiki/Synergy
- Spartan Weaponry: https://modrinth.com/mod/spartan-weaponry
- minecraft.wiki, Enchanting: https://minecraft.wiki/w/Enchanting
- minecraft.wiki, Enchanting mechanics: https://minecraft.wiki/w/Enchanting_mechanics
- minecraft.wiki, Enchantment definition: https://minecraft.wiki/w/Enchantment_definition
- minecraft.wiki, Java Edition 26.1: https://minecraft.wiki/w/Java_Edition_26.1
- minecraft.wiki, Java Edition 26.2: https://minecraft.wiki/w/Java_Edition_26.2
- minecraft.wiki, Java Edition 26.3: https://minecraft.wiki/w/Java_Edition_26.3
- minecraft.wiki, Java Edition 26.4: https://minecraft.wiki/w/Java_Edition_26.4
