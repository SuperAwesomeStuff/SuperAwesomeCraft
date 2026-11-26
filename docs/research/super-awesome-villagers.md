# Research: Super Awesome Villagers

Background for the Season 1 Villager trading rework, which is meant to be "in the spirit of" this datapack, and for deciding which parts carry over. Super Awesome Villagers was one of our own datapacks, run on the September 2026 playtest server alongside Super Awesome Enchanting. On that server selling everything to Villagers for Emeralds made every progression route end at the same activity, and Villagers sold enchanting catalysts. Gathered November 2026 from the pack in `SuperAwesomeDatapacks` (all paths below are relative to that folder): the generated data files, the generator and server test in `tools/`, the pack and repo READMEs, the glossary, the repo's git history and the design notebook's `Villagers.md`. Every trade below was read from the generated JSON and matches the generator and the README's generated tables. The target is Java 26.3. Anything marked *Unverified* couldn't be confirmed from these sources.

## What it is

- **A standalone data pack for Java 26.3.** `pack.mcmeta` sets min and max pack format 121, so it targets one version. No resource pack or mod is needed. It works without Super Awesome Enchanting, but its Librarian prices are generated from that pack's catalyst table.
- **Generated, not hand-written.** `tools/generate_villager_pack.py` deletes and rebuilds `SuperAwesomeVillagers/data` from Python tables, derives equipment and Book prices from the vanilla 26.3 recipes (read from a local `.cache` of the vanilla jar's data), and rewrites the tables in the pack README between `<!-- generated:... -->` markers. The README calls itself "the playtest rulebook".
- **Size.** 484 files. 14 catalogs: 13 Villager professions plus the Wandering Trader, 352 trades in all (Cleric 53, Wandering Trader 62, Librarian 40, Mason 33, Shepherd 32, Toolsmith 19, Armorer 17, Fisherman 17, Fletcher 15, Cartographer 12, Farmer 9, Weaponsmith 8, Butcher 7, Leatherworker 7).
- **Status mismatch.** The repo README's table says Playtest, but its intro and the pack README still say "In design. The first playtest build is being assembled." The pack README's open-design list (see the end) was never closed.

## History

From `git log` (dates are commit dates, all 2026):

- **September 13.** The repo README gains the design principles, including "Treat Villager trading as a secondary way to get resources, not the only sensible way."
- **September 16, first build (`0565e47`).** A different economy: every purchase cost 4 Emeralds with quantities on a 64/48/32/24/16 ladder, the Fisherman paid **16 Emeralds per Diamond** and 32 per Ancient Debris, and the Farmer bought Wheat, Beetroot and Baked Potatoes at 64 per 2 Emeralds. This matches the notebook's draft (`4 Emeralds = 64 Birch Logs`).
- **September 17, the rework (`d62f4d7`).** Prices moved to "1 Emerald, quantity on a 16/8/4/2/1 ladder", the Diamond fell to **4 Emeralds**, Ancient Debris became Netherite Scrap, the "Haram" crop marking and Wheat removal arrived, Wandering Traders got a fixed catalog, and the sort functions were added. This is the economy described below.
- **September 21, Book prices doubled (`b7849f1`).** Every Librarian Book except Fortune doubled, then rounded up to a price ladder. The generator comment calls it "the playtest price increase", so it was likely a mid-playtest reaction. Before it, most combat Books cost **1 Emerald** (see the Librarian section).
- **Unverified:** which commit was installed on the playtest server, and whether the September 21 prices were live for most of the playtest or only the end.

## Core rules (vs vanilla)

From the pack README ("Core trade rules") and the generator's `trade()` and `install_profession()`.

- **One fixed catalog per profession.** Every Villager of a profession offers exactly the same trades. Vanilla picks 2 random trades per level from a pool (the vanilla `trade_set/librarian/level_1.json` has `"amount": 2` and a `random_sequence`), and Librarian Books are random enchantments.
- **Everything at level 1.** Each profession's `level_1` trade set points at the whole catalog with `amount` equal to the catalog size. Levels 2 to 5 point at an empty tag with `amount` 0. There are no unlocks.
- **Villagers never level up.** Every trade has merchant `xp: 0`, so Villagers stay Novice forever. A side effect: in vanilla a Villager keeps its job for good once it has any trading XP; with 0 XP these Villagers never lock their profession and can be re-jobbed by breaking their workstation. With fixed catalogs, rerolling gains nothing, but switching professions is free. *Unverified:* the profession-lock rule was not checked against 26.3 code. The pack's audit function assumes it ("Changing profession clears a villager's offers").
- **Unlimited uses.** `max_uses` is 100,000 on every trade. Restocking can't be disabled from a data pack, so it still happens but has nothing to restore.
- **Fixed prices.** `reputation_discount` is 0.0 on every trade, which zeroes the per-offer price multiplier, so demand, gossip (including the curing discount) and reputation can't move prices. The server test checks that vanilla's 0.05 multiplier is absent.
- **Hero of the Village discount replaced by Emeralds.** The discount can't be controlled from data, and a discount on fixed prices makes profitable loops, so an advancement on the `hero_of_the_village` trigger runs `sav:raid_reward`. It gives **4 Emeralds × the effect level** (4 to 20, from predicates on amplifiers 0 to 4), then clears the effect. The README says "4 Emeralds per raid wave cleared"; the code actually pays by Hero of the Village level, which the generator says "stands in for waves cleared". Clearing the effect also removes vanilla's Hero gifts.
- **Player XP from trading is untouched.** The README says a data pack can't control it, so trades give XP as in vanilla, and no trade is an exact round trip, so there's no free XP loop. *Unverified:* whether writing `rewardExp:0b` into the offers (the stocking function writes offer NBT directly) would have turned it off. The notebook originally wanted no player XP from trades.
- **No loops by design.** "Every good is either bought or sold, never both", and where a bought good can be crafted into a sold one, the spread absorbs it. The only near-cycle noted is Cleric Bones → Bone Meal → Farmer crops, which loses (1 Emerald of Bones makes 12 Bone Meal, growing about 9 spare Carrots or Potatoes, which sell at 16 per Emerald).
- **Readable prices.** Trades are reduced to lowest terms (`math.gcd`), so "1 Emerald for 16 Cobblestone", never 4 for 64. Stackable purchases cost 1 Emerald and the quantity carries the value, on the ladder 16, 8, 4, 2, 1. Prices above 64 Emeralds would be quoted in Emerald Blocks; none reach that.
- **Fixed order.** Items before blocks, each cheapest first. Smiths list one-off items first, then Copper, Iron, Gold, Diamond.
- **Physical Villager play unchanged.** Workstations, breeding and job claiming stay vanilla. The pack changes the economy, not whether players build trading halls.
- **Reference good.** "1 Emerald = 16 Birch Logs = 16 ordinary bulk blocks." The Fletcher sells 16 Logs of any type for 1 Emerald, the Fisherman buys 32 Birch Logs for 1. Larger tree types aren't bought because the server's tree-capitator pack made them too productive.

## Buyer professions (Emerald faucets)

Farmers, Fishermen and Butchers give Emeralds. Everything else takes them. The design rule: income should need a non-renewable resource or recurring player involvement ("holding an input while AFK does not count"), with a limited exception for low-rate farm crops.

### Fisherman, the general exchange

| Player gives | Fisherman gives | Emeralds per unit |
| --- | ---: | ---: |
| 32 Birch Logs | 1 | 0.03 |
| 16 Cod | 1 | 0.06 |
| 16 Salmon | 1 | 0.06 |
| 8 Pufferfish | 1 | 0.125 |
| 8 Tropical Fish | 1 | 0.125 |
| 8 Raw Copper | 1 | 0.125 |
| 8 Lapis Lazuli | 1 | 0.125 |
| 8 Nether Quartz | 1 | 0.125 |
| 4 Raw Iron | 1 | 0.25 |
| 2 Raw Gold | 1 | 0.5 |
| 1 Diamond | 4 | 4 |
| 1 Echo Shard | 8 | 8 |
| 1 Netherite Scrap | 32 | 32 |
| 1 Heart of the Sea | 32 | 32 |
| 1 Enchanted Golden Apple | 64 | 64 |
| 1 Nether Star | 64 | 64 |
| 1 Dragon Egg | 64 | 64 |

- **Why these forms.** Raw ores because no farm makes them and no recipe turns a metal back. Lapis because nothing drops it. Coal is out (Wither Skeleton farms), Redstone out (Witch farms), Amethyst out (budding blocks). Vanilla's Fisherman buys Coal, String and fish and sells Campfires, boats and an enchanted fishing rod (`.cache/.../villager_trade/fisherman/`).
- **Netherite Scrap** at 32 is worth eight Diamonds, "in the expectation that players will sell it rather than use it". The four Scrap in one Netherite Ingot sell for 128 Emeralds, more than a full Diamond armour set at the Armorer (120).
- **The Dragon Egg** exists once per world, so it pays once. Each Nether Star costs a Wither fight.

### Farmer

| Player gives | Farmer gives |
| --- | ---: |
| 16 Carrots, 16 Potatoes, 16 Nether Wart, 16 Chorus Fruit | 1 each |
| 8 Beetroot, 8 Apples, 8 Pumpkin Pie | 1 each |
| 2 Torchflowers, 2 Pitcher Plants | 1 each |

- **"Haram" crops.** The pack overrides the vanilla `carrots`, `potatoes` and `beetroots` block loot tables (`data/minecraft/loot_table/blocks/`). When the breaking entity is a Villager, the crop drop gets `minecraft:rarity: uncommon` (a yellow name) and the item name "Haram Carrot" (or Potato, Beetroot). The Farmer's trade asks for `rarity: common`, so Villager-run crop farms earn nothing. Marked crops still feed and breed Villagers. Seeds and Poisonous Potatoes aren't marked.
- **Why rarity.** "A trade's component check ignores extra components, so the mark has to change one every item already has rather than add a new one" (generator comment on `VILLAGER_CROPS`).
- **Wheat and Baked Potatoes aren't bought**, because a Hay Bale or a furnace would turn marked crops back into unmarked goods. Bread and Cake aren't bought either (Milk has no cost, so any fair Cake rate outpaced other food). Cocoa Beans are too productive at any rate.
- **Torchflowers and Pitcher Plants** come from Sniffer seeds and return no seed, so each sale costs a Sniffer dig.
- **Crops broken by players, water or pistons are not marked.** *Unverified:* whether a farm where Villagers plant but water or pistons harvest produces unmarked, sellable crops. That reads like a loophole. The README's open list also asks for an in-play check that the trade screen rejects marked crops; the server test only checks the loot table.

### Butcher

| Player gives | Butcher gives |
| --- | ---: |
| 4 Raw Beef, 4 Raw Mutton, 4 Rabbit Hide | 1 each |
| 3 Steak, 3 Cooked Mutton | 1 each |
| 2 Raw Rabbit | 1 |
| 3 Cooked Rabbit | 2 |

- Leather, Porkchops, Chicken, Feathers and Eggs are deliberately excluded because farms make them cheaply. Cooking earns a small premium. The README marks the Farmer and Butcher rates as Proposed ("rescaled without play data").

## Vendor professions (Emerald sinks)

### Mason: building blocks

- **16 per Emerald:** Cobblestone, Cobbled Deepslate, Stone, Deepslate, Dirt, Gravel, Granite, Diorite, Andesite, Tuff, Netherrack, Basalt, Blackstone, End Stone.
- **8 per Emerald:** Smooth Stone, Sand, Red Sand, Clay, Soul Sand, Soul Soil, Dripstone Block, Calcite, Glass, Terracotta, Nether Bricks, Prismarine, Purpur Block, Bricks, Quartz Block, Resin Bricks, Ice.
- **4 per Emerald:** Glowstone. **2 per Emerald:** Obsidian.
- Quartz Blocks are priced below the Quartz inside them (the Fisherman pays 1 Emerald for 8 Quartz) because no recipe turns the block back. Ice is sold because collecting it needs Silk Touch.

### Fletcher: logs and ranged gear

- 16 per Emerald: Arrows and every raw Log or Stem (Acacia, Birch, Cherry, Crimson, Dark Oak, Jungle, Mangrove, Oak, Pale Oak, Poplar, Spruce, Warped). No Planks, Stripped Logs, Wood or Hyphae.
- Bow 1 Emerald, Crossbow 1 Emerald. No Tipped or Spectral Arrows.

### Shepherd

- All 16 Dyes at 8 per Emerald and all 16 Wools at 4 per Emerald. No Carpet, Beds or Banners.

### Leatherworker

- 8 Leather per Emerald. Leather Helmet 1, Boots 1, Chestplate 2, Leggings 2, Saddle 8, Leather Horse Armour 8.

### Cleric: mob drops, brewing, redstone, sculk (53 offers)

- **Mob drops and brewing, per Emerald:** 16 String, Rotten Flesh, Feathers, Flint, Redstone Dust; 8 Gunpowder, Spider Eyes, Ink Sacs; 4 Bones, Slime Balls, Glow Ink Sacs, Blaze Powder; 2 Rabbit's Feet, Phantom Membranes; 1 Ghast Tear.
- **Sculk:** 16 Sculk Veins or 8 Sculk per Emerald; Sculk Sensor 2, Calibrated Sculk Sensor 3, Sculk Shrieker 4, Sculk Catalyst 8.
- **Redstone, per Emerald:** 8 Redstone Torches, Levers, Stone Buttons, Rails; 4 Stone Pressure Plates, Tripwire Hooks, Note Blocks; 2 Repeaters, Trapped Chests, Lightning Rods, Detector Rails, Activator Rails; 1 Redstone Block, Comparator, Observer, Piston, Dispenser, Dropper, Heavy Weighted Pressure Plate, Daylight Detector, Target, Redstone Lamp, Powered Rail.
- **Redstone, single items:** Minecart 2, Sticky Piston 2, Hopper 2, Light Weighted Pressure Plate 2, Copper Bulb 2, TNT 2, Slime Block 3, Crafter 3.
- **Ender Pearl: 8 Emeralds each.** A hand-set exception, so a full set of Eyes of Ender costs about 96 Emeralds: "buying your way to the End is possible but expensive."
- No Potions (brewing from the ingredients is the intended route). No Blaze Rods, Fermented Spider Eyes, Magma Cream or Glistering Melons.

### Cartographer

- Spyglass 1, Compass 2.
- **10 Explorer Maps at 16 Emeralds each:** Jungle Pyramid, Swamp Hut, Desert, Plains, Savanna, Snowy and Taiga Village, Buried Trial Chambers, Ocean Monument, Woodland Mansion. They reuse vanilla's own `exploration_map` item modifiers, without vanilla's Compass cost. Because a map depends on where the Villager stands, the stocking function builds each one in the Villager's hand; if no structure is in range the map is dropped from that Villager's list.

### Armorer, Toolsmith, Weaponsmith: unenchanted gear

Prices are material cost (through vanilla recipes) plus 25%, rounded up, minimum 1 Emerald. Ingots are valued from the Fisherman's raw ore rates (iron 0.25, gold 0.5, copper 0.125) and Diamonds at 4.

| | Copper | Iron | Gold | Diamond |
| --- | ---: | ---: | ---: | ---: |
| Helmet | 1 | 2 | 4 | 25 |
| Chestplate | 2 | 3 | 5 | 40 |
| Leggings | 2 | 3 | 5 | 35 |
| Boots | 1 | 2 | 3 | 20 |
| Pickaxe | 1 | 1 | 2 | 16 |
| Axe (Toolsmith and Weaponsmith) | 1 | 1 | 2 | 16 |
| Shovel | 1 | 1 | 1 | 6 |
| Hoe | 1 | 1 | 2 | 11 |
| Sword | 1 | 1 | 2 | 11 |

- Also Shield 1 (Armorer), Shears 1, Flint and Steel 1, Bucket 1 (Toolsmith).
- **Nothing is enchanted**, "so that Villagers do not bypass Super Awesome Enchanting's catalyst system". Vanilla's smiths, Fletcher and Fisherman sell enchanted Diamond armour, tools, bows, crossbows and rods (`.cache/.../villager_trade/armorer/4/emerald_enchanted_diamond_boots.json` and others); none of that survives.
- **Diamond gear is sold; Netherite is not.** The README flags it: "Diamond gear makes Emeralds a route past caving, so its prices should be watched in playtesting." A full Diamond set costs 120 Emeralds, which is 30 Diamonds sold to the Fisherman (crafting it takes 24).

### Librarian: tier I Books

- **Every enchantment at level I except Mending and the two curses**, all at once, 40 Books. Mending is left out because Super Awesome Enchanting redefines the `minecraft:mending` ID as Unbreakable, and trade sets can't check whether another pack is installed.
- **No Book input.** Vanilla Librarian trades take Emeralds plus a Book and give a random `#minecraft:tradeable` enchantment with `max_uses` 12 (`.cache/.../villager_trade/librarian/1/emerald_and_book_enchanted_book.json`). Here it's Emeralds only.
- **Pricing.** The Emerald value of the Book's Super Awesome Enchanting catalyst, plus 25%, rounded up, doubled (except Fortune), then rounded up to the ladder 1, 2, 4, 8, 16, 24, 32, 40, 48, 56, 64. Catalysts no Villager trades get hand-set values in the generator (Sponge, Nautilus Shell and Turtle Scute 2, Breeze Rod and Blue Ice 1, Amethyst Shard 0.125).
- **Higher tiers** come from combining Books at a vanilla Anvil, so level V takes 16 level I Books.

| Price now (before Sept 21) | Books |
| --- | --- |
| 2 (1) | Bane of Arthropods, Blast Protection, Breach, Channeling, Efficiency, Fire Aspect, Fire Protection, Flame, Impaling, Knockback, Luck of the Sea, Lure, Multishot, Piercing, Power, Projectile Protection, Punch, Quick Charge, Sharpness, Smite, Soul Speed, Thorns |
| 4 (2) | Frost Walker, Lunge, Sweeping Edge, Wind Burst |
| 8 (3) | Aqua Affinity, Depth Strider, Feather Falling, Respiration, Riptide, Silk Touch, Swift Sneak, Unbreaking |
| 16 (5 or 6) | Protection (was 5), Density, Looting (were 6) |
| 24 (10 or 12) | Loyalty (was 10), Infinity (was 12) |
| 48 (45) | Fortune |

- **What a top Book costs.** Sharpness V or Efficiency V: 16 Books, 32 Emeralds (16 before the increase). Protection IV: 8 Books, 128 Emeralds. Fortune III: 4 Books, 192 Emeralds. With Super Awesome Enchanting installed, a Book is a catalyst-free alternative to the Workstation.

### Wandering Trader

- **Vanilla's catalog at vanilla prices**, reduced to lowest terms, all 62 offers at once with unlimited uses, sorted like Villager catalogs. Its three hardcoded trade sets are repointed: `common` holds everything, `uncommon` and `buying` are empty.
- **Anything a Villager already trades is removed**, computed from the Villager catalogs. That drops Logs, Dyes, Sand, Red Sand, Glowstone, Gunpowder, Slime Balls and vanilla's **enchanted Iron Pickaxe**.
- **All buying trades removed** (Water and Milk Buckets refill for free; the rest undercut the Farmer or were Emerald loops).
- **What's left:** 1 Emerald each for Beetroot, Melon, Pumpkin and Wheat Seeds, a Name Tag, flowers, Fern, Tall Dry Grass, Packed Ice, Podzol, Pumpkin, Sugar Cane and Wildflowers; 1 Emerald for 5 Lily Pads, 3 Mushrooms (Brown, Red, Shelf), 3 Pale Hanging Moss, 3 Vines, or 2 Moss, Pale Moss, Pointed Dripstone, Rooted Dirt, Small Dripleaf or Sulfur Spikes; 2 for a Golden Dandelion or Sea Pickle; 3 for Tropical Fish and Pufferfish Buckets, Cactus, Kelp, Firefly Bush and the five Coral Blocks; 5 for any of 10 Saplings, a **Nautilus Shell**, or a **Long Invisibility Potion** (the only Potion for sale anywhere); 6 for **Blue Ice**.

## How Emeralds flow

- **Shape.** Three buyers against ten vendors plus the Wandering Trader. Almost everything a player wants can be bought, and Emeralds can only come from a short list, so every playstyle converts to the same thing: mine or farm the few accepted goods, sell, buy the rest. That's the playtest's "every route ends at the same activity" in structural form.
- **The big faucets, by value per unit:** Nether Star, Enchanted Golden Apple and Dragon Egg (64), Netherite Scrap and Heart of the Sea (32), Echo Shard (8), Diamond (4). By likely volume (inference, not measured): **mining**, because Fortune multiplies Raw Iron, Raw Gold, Raw Copper, Lapis, Quartz and Diamond drops and all six are bought. Fortune III averages about 2.2 Diamonds per ore, or roughly 9 Emeralds. The Librarian sells Fortune I, so Emeralds buy the multiplier on Emerald income.
- **Repeatable boss income.** The Wither can be refought with 3 Wither Skeleton Skulls and 4 Soul Sand, and the Mason sells Soul Sand, so a Wither Skeleton farm turns into 64 Emeralds per Wither kill.
- **Other Emerald sources outside trades:** Emerald ore, loot, and raids. A raid farm earns vanilla Emerald drops from illagers plus the pack's 4 to 20 Emeralds per won raid. *Unverified:* whether raid farms on the playtest server triggered the reward.
- **Low faucets.** Crops (1 per 16 Carrots), fish (1 per 16 Cod), Birch (1 per 32), meat (1 per 3 or 4). The README's open list worries that "mining pays substantially better" than farming. *Unverified:* whether AFK fishing farms work on 26.3; if they do, Cod and Salmon break the "no AFK income" rule.
- **Sinks.** Bulk building blocks, mob drops and redstone (cheap, high volume), Diamond gear (20 to 40 per piece), Explorer Maps (16), Ender Pearls (8), and Books (2 to 48, and 32 to 192 for a top-level Book).

## What touches enchanting and gear

Checked against the Season 1 rule in [Season 1 enchanting](../season-1/enchanting/README.md): Villagers never sell Blaze Rods, Wither Skeleton Skulls, Shulker Shells, books for combat or loot enchantments, or enchanted gear (and, per [vision.md](../vision.md), never the Era materials).

- **Enchanted gear: no conflict.** No Villager sells enchanted gear, and the Wandering Trader's enchanted Iron Pickaxe is removed.
- **Blaze Rods, Wither Skeleton Skulls, Shulker Shells, Nether Stars, Netherite: no conflict.** None are sold. Nether Stars and Netherite Scrap are only *bought* (64 and 32). Note: the Cleric sells **Blaze Powder** (4 per Emerald), which can't become a Blaze Rod but, with the Cleric's Slime Balls, crafts Magma Cream and Magma Blocks.
- **Combat and loot Books: major conflict.** The Librarian sells level I of every combat and loot enchantment:
  - **Combat (weapons and armour):** Sharpness, Smite, Bane of Arthropods, Breach, Density, Knockback, Fire Aspect, Sweeping Edge, Lunge, Wind Burst, Power, Punch, Flame, Infinity, Multishot, Piercing, Quick Charge, Impaling, Channeling, Protection, Fire Protection, Blast Protection, Projectile Protection, Thorns.
  - **Loot:** Fortune, Looting, Luck of the Sea.
  - **Borderline (retired Season 1 drafts listed these as Convenience):** Loyalty, Riptide, Lure. Unbreaking isn't in either list.
  - **Convenience, allowed:** Efficiency, Silk Touch, Aqua Affinity, Respiration, Depth Strider, Feather Falling, Frost Walker, Soul Speed, Swift Sneak.
- **Diamond gear: possible conflict.** The smiths sell full unenchanted Diamond armour, tools and weapons. Diamond gear has 3 slots in Season 1, so Emeralds buy the slot tier without caving. Diamonds and Gold or Diamond Blocks (the Overworld slot upgrade) are not sold.
- **Catalysts it sold during the playtest** (Super Awesome Enchanting's catalyst table in `tools/generate_enchanting_pack.py`, matched to the trades above): Spider Eye (Bane), Obsidian (Blast Protection), Lightning Rod (Channeling), Redstone Block (Efficiency, Quick Charge), Piston (Knockback, Punch), Dispenser (Multishot), Slime Block (Feather Falling, Silk Touch), Sculk Sensor (Swift Sneak), Ender Pearl (Loyalty), Glowstone (Smite), Soul Sand (Soul Speed), Shield (Projectile Protection), and from the Wandering Trader Pointed Dripstone (Thorns), Nautilus Shell (Depth Strider, Riptide) and Blue Ice (Frost Walker). Magma Cream (Fire Protection) was craftable from Cleric goods, and Spectral Arrows (Infinity) from Fletcher Arrows plus Glowstone Dust from Mason Glowstone. Not sold: Amethyst Blocks, Gold, Iron or Diamond Blocks, Diamonds, Blaze Rods, Breeze Rods, Prismarine Shards, Sponges, Turtle Scutes, Netherite Ingots.
- **Season 1 enchanting materials it would sell if carried over unchanged** ([slots and recipes](../season-1/enchanting/recipes.md)): Redstone (16 per Emerald), Flint, String, Rotten Flesh, Feathers (16), Spider Eyes (8), Leather (8), Bones and Slime Balls (4, and Bone Meal from Bones), Soul Soil (8), Obsidian (2), TNT (2 Emeralds), Ender Pearls (8 Emeralds), and from the Wandering Trader Cactus (3), Nautilus Shells (5). Magma Blocks are craftable from Cleric goods. Not sold: Copper, Iron or Gold Ingots, Lapis, Amethyst, Diamonds, Prismarine, Snow, Wind Charges, Cobwebs, Pufferfish, Breeze Rods, Echo Shards, Heart of the Sea, Blaze Rods. Lapis, Pufferfish, Echo Shards and Hearts of the Sea are *bought* by the Fisherman, so selling them competes with spending them on enchantments. The "bulk materials" clause allows most of these, but Ender Pearls (Reach, Loyalty) are a flat-4 rare material.

## Implementation notes for a Fabric mod on 26.3

- **Trade data format.** 26.3 drives trades through two data registries. A `villager_trade` entry (`data/<ns>/villager_trade/...json`) has `wants` (`id`, `count`, optional `components`), optional `additional_wants`, `gives`, `max_uses`, `xp`, `reputation_discount`, and optional `given_item_modifier` (item modifier functions such as `exploration_map`, `set_potion` or `enchant_randomly`), `merchant_predicate` (vanilla uses it for biome variants) and `double_trade_price_enchantments`. A `trade_set` (`data/minecraft/trade_set/<profession>/level_N.json`) has `trades` (a `villager_trade` tag), `amount` and `random_sequence`. Vanilla trade sets always point at tags. Read from `.cache/minecraft/26.3/vanilla/data/minecraft/` and the pack's own files.
- **Hardcoded IDs.** "The 68 trade set IDs the game consults are hardcoded. New ones do nothing": 13 professions × 5 levels plus the Wandering Trader's `common`, `uncommon` and `buying`. The pack publishes its own `sav:` tags because overriding a `minecraft:` tag would merge with vanilla's entries.
- **Random order, filled lazily.** A trade set hands out its trades in random order and only when a player first opens the screen. To get a fixed order the pack runs a tick function over every unstocked Villager and Wandering Trader, writes the catalog straight into `Offers.Recipes` the tick a Villager takes a job, and reorders offers that already exist (pre-pack Villagers keep any vanilla offers). A once-a-second audit re-stocks Villagers that lost their offers. A mod can set the offers in code instead.
- **Things the data pack couldn't do that a mod can:** turn off restocking, remove the Hero of the Village discount directly (instead of clearing the effect), turn off player XP from trades, lock professions, and match trade inputs exactly (vanilla ignores extra components, which forced the rarity trick for marked crops). Season 1 has no XP, so the XP point may be moot.
- **One stack per slot.** A trade slot holds at most 64, so prices above 64 need Emerald Blocks or another currency.
- **Big catalogs.** The Cleric has 53 offers and the Wandering Trader 62 in the vanilla trading screen. *Unverified:* how usable that is. The README's first open item says it was to be tested.
- **Derived prices.** Equipment, redstone and Book prices come from walking vanilla crafting recipes down to priced base materials. A mod rework could reuse the method: `material_cost()` and `equipment_price()` in the generator.
- **Fabric API.** *Unverified:* whether Fabric API's trade-offer helpers still apply now that 26.3 trades are data-driven. Shipping JSON in the mod's data folder works either way.

## Open questions and unverified points

- Which commit ran on the playtest server, and when the Book price doubling went live.
- Whether marked crops are really rejected in the trade screen, and whether water- or piston-harvested Villager farms get around the marking.
- Whether AFK fishing and raid farms became Emerald faucets on the server.
- Whether 0-XP trades really leave Villagers free to change profession in 26.3.
- Whether `rewardExp:0b` in the written offers would have removed player trading XP.
- The pack README's own open list: the trading screen with about 50 offers, an end-to-end single-profession check, playtesting the Proposed Farmer and Butcher rates, whether farming stays viable next to mining, and rechecking the Bone and crop rates together.

## Sources

All paths are relative to `E:\SuperAwesomeStuff\SuperAwesomeDatapacks`.

- `README.md`, `GLOSSARY.md` (Trade catalog, Buyer and Vendor profession, Reference good)
- `SuperAwesomeVillagers/README.md` and `SuperAwesomeVillagers/pack.mcmeta`
- `SuperAwesomeVillagers/data/sav/villager_trade/<profession>/*.json` (every trade), `data/sav/tags/villager_trade/*/all.json` and `empty.json`, `data/minecraft/trade_set/*/*.json`
- `SuperAwesomeVillagers/data/sav/function/stock/*.mcfunction`, `sort/*.mcfunction`, `load.mcfunction`, `raid_reward.mcfunction`, `data/sav/advancement/raid_reward.json`, `data/sav/predicate/raid_level_*.json`, `data/sav/item_modifier/**`
- `SuperAwesomeVillagers/data/minecraft/loot_table/blocks/carrots.json`, `potatoes.json`, `beetroots.json`
- `tools/generate_villager_pack.py` (current, and `0565e47` for the first build), `tools/test_villager_server.py`, `tools/generate_enchanting_pack.py` (`CATALYSTS`)
- `git log` and `git show` for `8495141`, `0565e47`, `d62f4d7`, `b7849f1`
- Vanilla 26.3 data cached at `.cache/minecraft/26.3/vanilla/data/minecraft/` (`trade_set/`, `villager_trade/`)
- The design notebook's `Villagers.md` (`E:\SuperAwesomeStuff\notebook\Villagers.md`), the earlier draft
