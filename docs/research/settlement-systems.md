# Research: Settlement Systems

Background for the Settlements design ("Villagers move into Settlements that players build and use their buildings. A Settlement's prosperity grows with what's been built and how it's used, which unlocks trades and services") and for the open question "How is Settlement prosperity measured without dictating how people build?" It covers how other building games recognise player-built rooms and turn them into town progress, the Minecraft villager and town mods other than MineColonies and TekTopia (covered separately), and the vanilla village mechanics a mod would build on. Gathered November 2026 from the Dragon Quest Wiki (dragon-quest.org), the Valheim and Palworld Fandom wikis (read through the MediaWiki API), Nookipedia, minecraft.wiki, Modrinth pages and API, millenaire.org (home and downloads pages) and the Millénaire source on GitHub (its in-game help files and reputation tables), the MCA Reborn source (building types, rank tasks, building scanner, config), the Village Quest docs in its repository, and a few game guides where no wiki page loaded. The target is Java 26.3. Anything marked *Unverified* couldn't be confirmed from a primary source.

## Dragon Quest Builders 1

- **What counts as a room.** "All rooms require walls 2 blocks high and at least 1 door," built from any block, with any number of doors, and "all rooms also require some type of light source" (Dragon Quest Wiki, DQB1 room recipes). An enclosed space with a door and a light is an "empty room". Placing the right furnishings inside turns it into a named room.
- **Room recipes.** Each named room is a short list of furnishings plus a light. Examples from the wiki, with their listed score:
  - Basic bedroom: 2 Straw mattresses, 1 light. "Increases residents' maximum HP by 10%." Score 100.
  - Crude kitchen: 1 Cookfire, 1 Chest. "Residents will cook food and put it in the chest." Score 150.
  - Carpenter's workshop: 1 Carpenter's workstation, 1 Chest, 1 light. "Residents will build furniture and materials and put them in the chest." Score 200.
  - Flower garden (1 Bench, 5 Flowers, 1 light) and Treasure room (1 Treasure chest, 1 light): "No effect", score 100 each.
- **Base level comes from room score.** Guides describe the base's level as driven by a score from its rooms. The score of an empty room comes from the value of its door, light and wall and floor blocks, and a named room is worth more. The score toward the next level resets after each level. *Unverified:* the exact formula and thresholds. The wiki's Banner of Hope page and Prima's guide didn't load, so this rests on guide summaries.
- **Where rooms count.** Rooms only count inside the base boundary around the Banner of Hope. A chime plays when you walk out of it (guide summaries; *Unverified* against the game's own text).
- **What residents give back.** Working rooms make residents do things: cook into the chest, craft furniture into the chest, and later fight better (bedroom HP). The base is a production loop as well as a score.

## Dragon Quest Builders 2

- **What counts as a room.** A room needs "a minimum of 4 squares and a maximum of 150 squares of space within a walled enclosure". Walls must be two blocks high with one door of any type, or one block high if the opening is a Wooden Gate, Picket Gate or Saloon Door. A walled space with a Doormat instead of a door is a **plaza**. Light isn't a general rule any more. It appears only inside recipes.
- **Room recipes with alternatives.** Recipes accept substitutes, for example a Simple Kitchen is 1 Chest plus "3 Bonfire (Any) or 2 Fiery Frying Pan (Set) or 1 Brick Barbecue". Multi-block "sets" (Dining Table, Cocktail Counter) count as one furnishing. Examples:
  - Basic Bedroom: 2 Beds (any), 1 Light Source (any). Residents sleep in the beds.
  - Private Bedroom: 1 Resident's Sign, 1 Bed, 1 Light, 1 Chair. Can be assigned to one resident.
  - Toilet: 1 Pot or Wooden Toilet, 1 Towel. Residents use it.
  - Shared Bathroom: 2 bathing fixtures, 2 Towels, 2 Washtubs, 1 Light.
  - Chapel: 1 Altar, 2 Tabletop Light Sources, 1 Goddess Statue, 4 Chairs. Nuns worship there.
  - Adjacency: a Basic Bedroom "will create a Hotel when built next to a Reception Room."
  - *Unverified:* which recipe wins when a room matches more than one. The wiki doesn't say.
- **Room preference: free quality on top of the recipe.** Each resident has a preference for a room's **size**, **fanciness** and **ambience**, which the room info panel shows. Named NPCs have fixed preferences. Ones recruited from Explorer's Shores get random ones. Matching five residents' rooms to their preferences unlocks a recipe (the Emblematic Clock). So there are two layers: a recipe says what the room is, and a free score says how nice it is.
- **Gratitude (hearts).** Residents release hearts when they're happy. The bigger the heart, the more gratitude it's worth, and the player walks over to collect it.
  - Sources named by guides: finishing residents' requests, building the rooms they ask for, tending fields, sleeping in beds (residents "asleep on beds will release a heart upon waking"), clearing rubble and beating monster waves.
  - Hitting the town bell collects hearts you missed.
  - *Unverified:* whether everyday use of toilets, baths and meals also drops hearts. It's widely said, but no page that loaded confirmed it.
- **Base level and the bell.** On the story islands, gratitude raises the base level (0 to a maximum of 4). Once there's enough, the player rings the bell to level up, which "unlocks new blueprints and recipes" (Kotaku), and reaching the maximum is needed to move the story on. On the sandbox Isle of Awakening, gratitude is spent instead, on recipes and new Explorer's Shores (RPG Site).
- **The whole loop.** Build rooms that meet a recipe, residents use them and drop hearts, the player collects the hearts, the level goes up, and new recipes and residents arrive. Usage is visible as a resident doing the thing, and the reward is a physical pickup.

## Valheim

- **Comfort, not rooms.** Valheim never recognises rooms. Comfort sets how long the Rested buff lasts: 8 minutes at comfort 1, plus 1 minute per level, up to 22 comfort (29 minutes) with normal items.
- **Categories, highest only.** Furniture falls into categories: fire, bed, chair, table, rug, banner, garland, lantern, stand, hot tub or barber station, pot. "Only the highest level item of a category will be used." Duplicates never stack, so one throne, one bed and one rug is the most you can get, and the best items come from later biomes (Meadows can reach 5, Ashlands 22).
- **Radius.** Items must be within 10 metres of the player to count.
- **Shelter and the roof check.** Being "sheltered" needs a roof and 80% **cover**. Cover casts 17 rays from the structure (straight up, 8 horizontal, 8 at 45 degrees), each up to 30 metres, and counts how many are blocked. The roof check looks for a non-leaky material directly above, within 100 metres. Sheltered and near a fire gives base comfort 2 and the Rested buff.
  - Cover also gates stations. Workbench, forge and fermenter need 70% cover and a roof to work, beds need 80%, and beehives need *less* than 60% (they want open air).
  - The wiki lists quirks: rays start outside thin walls when you stand close, the same building turned a different way gets a different score, and very large open halls can count as uncovered.
- **Workbench.** It defines the base: build radius 20 metres, no monster spawns inside. It's upgraded by building specific extension stations within 2 metres (Chopping Block, Tanning Rack, Adze, Tool Shelf). Each upgrade raises its level, adds 4 metres of radius and unlocks recipes. Each extension needs its own unblocked line to the bench.
- **Why it works.** Players can build any shape. The game only checks "is there a roof and some cover here" and "which categories of comfort item are near". Layout is free, and the set of item categories tells players what's wanted without dictating a floor plan.

## Enshrouded and Palworld (short)

- **Enshrouded** uses a Valheim-style comfort score: the best item in each furniture category counts, near the player, and the Rested buff lasts longer the higher it is. Guides disagree on the cap (69 or 100), and one reports a chair-stacking exploit. *Unverified:* the official wiki didn't load, so neither the shelter rule nor the category list could be checked. It has no notable twist on Valheim for our purposes.
- **Palworld base level is a checklist.** The Palbox defines a base. Its level goes up by completing listed "missions" at the Palbox, which are mostly "build this structure" (a Wooden Chest and a deployed Pal at level 1, a Primitive Workbench and a Shoddy Bed at level 2, per guides). Each level adds a working Pal slot, level 10 allows a second base and level 15 a third (Palworld wiki). This is pure block counting: it tells you exactly what to build, with nothing about how. The cap varies by version (20 in early guides, 30 in Game8's Feybreak notes; *Unverified*).

## Animal Crossing: New Horizons (short)

- **Island rating.** Five stars, from two hidden totals that both have to clear a threshold: **development** (buildings, residents, bridges, placed outdoor items, fences, few weeds) and **scenery** (trees, flowers, bushes, crops, DIY furniture). Five stars needs 665+ development and 450+ scenery. The player never sees the numbers. Isabelle gives the star rating and a hint about what to improve.
- **Anti-spam rule.** Six or more of the same item in one 8×8 area loses 0.5 development points per duplicate, and eleven or more loses 0.75 each. Trees past 220 scenery points stop the island reaching five stars ("overgrown"). Variety scores better than repetition.
- **What it unlocks.** Three stars brings K.K. Slider's concert and the terraforming permit. Five stars gives the Golden Watering Can recipe. In the first game, town rating also raised the chance of a villager moving in (40% at rank 0 up to 100% at "perfect town").
- **Home score.** The Happy Home Academy scores the inside of the house from item values and bonuses each week (B, A or S in New Horizons). New Leaf added optional theme challenges that score items by theme.
- **Takeaway.** Free decoration with a hidden score, a duplicate penalty and spoken hints. It works for one player decorating. It says nothing about whether anything gets used.

## Minecraft mods

### Minecraft Comes Alive Reborn (MCA Reborn)

- **Status.** Fabric, Forge, NeoForge and Quilt. The Modrinth file list has Fabric builds for 26.1.2 and **26.2** and a NeoForge build for 26.2 (8.1.11, August 2026). **No 26.3 build yet.** GPL-3.0, about 4.3 million downloads, actively updated (November 2026).
- **Buildings are recognised from player builds.** Building types are data files (`data/mca/building_types/*.json`), each a list of required blocks and a priority:
  - house: 1 bed. big_house: 4 beds. inn: 4 beds, a jukebox and a smoker.
  - library: 32 bookshelves and a lectern. storage: a lectern and 4 chests.
  - infirmary: a brewing stand, a cauldron, a bed and a flower pot. armory: a smithing table, a grindstone and an anvil.
  - blacksmith: a grindstone, 2 blast furnaces and an anvil. prison: 8 iron bars, an iron door and a bed. bakery: a cake and a smoker.
  - Grouped "buildings" with no walls: town_center (a town-center block), graveyard (3 tombstones).
  - The highest-priority type whose blocks are all present wins.
- **How a building is found** (`Building.java`). A flood fill starts inside and spreads through air. An air block only counts as interior if there is a non-air, non-leaves block above it within 16 blocks, which is the roof check. Doors end the fill, and the building needs at least one.
  - Results: too small (32 or fewer blocks scanned by default), too big (8,192 blocks, or a radius of 320), no door, or overlapping another building.
  - Automatic scanning is off by default ("High CPU usage"). Players register buildings by hand.
  - Any shape passes, as long as it's roofed, has a door and holds the blocks.
- **Population cap = beds.** A village's maximum population is its count of bed points of interest.
- **Player rank is per player.** Peasant, Merchant, Noble, Mayor, Monarch, from `data/mca/tasks/*.json`. Each rank needs a reputation total, sometimes a population, and specific buildings:
  - Merchant: 400 reputation, a big house and storage.
  - Noble: 800 reputation, a graveyard, an inn and an infirmary.
  - Mayor: 1,200 reputation, 20 villagers, a library and an armory.
  - Monarch: 2,500 reputation, 30 villagers, a prison, a blacksmith and the Grim Reaper kill.
  - Reputation is the sum of the player's hearts with every resident.
- **What rank gives.** Taxes are collected on an interval scaled by population and tax rate, and a library multiplies them by 1.5. Merchants and above get tax notices. Residents react to the tax rate (happy, angry, riot).
- **Interesting for us.** It's the clearest working example of "required blocks inside any enclosed, roofed shape", in data. It also shows the cost: building types read like shopping lists (32 bookshelves), and rank is per player, which suits a solo player more than a shared town.

### Millénaire

- **Status.** Rewritten from scratch for 9.0 (August 2026). **9.1.1 stable for 1.21.1 on NeoForge**, with **alpha builds for 26.3 (NeoForge 26.3.0.100)** and 1.20.1 (Forge), all November 15, 2026. **No Fabric build.** Downloads are on millenaire.org. A public repository exists at github.com/Kinniken/Millenaire. *Unverified:* its licence and whether it holds the 9.x code.
- **Villages grow by themselves.** Each village starts around a central building and picks new buildings and upgrades from its culture's plans (a Norman Manor goes from level 0 to 10, a Farm from 0 to 4). Villagers gather some materials and need the player for others. The town hall lists "their material needs for the planned construction", and selling those first speeds things up. Growth stops when every plan is built or there's no room left.
- **Culture-based trade.** Seven cultures. Each has its own buildings, villager roles, products and trade goods, and villages can be specialised (Norman agricultural, military, religious or artisan villages). Villagers only sell what's in stock and only buy what the village needs, in a separate currency (deniers). Some goods only appear once the village has built the workshop and a craftsman has moved in (Norman timber frames, for example).
- **Reputation.** Reputation is per village and per culture, and goes up "in proportion to the price" of everything you buy and sell, plus quests. Named tiers in the Norman file run from Stranger (0) through Known Face (256), Regular visitor (1,024), Favourite Trader (4,096), Friend of the village (8,192) and One of us (32,768) to Natural leader (131,072), with negative tiers down to Public enemy. Reputation unlocks: buying the village parchment, buying a house or a plot the village builds for you, the Creation Quest (at Friend), and leading your own village (at Natural leader, or by finishing the quest).
- **Player-led villages.** You place buildings with a wand (9.0 adds a translucent preview) and turn upgrades on or off, and villagers take materials straight from chests instead of trading.
- **Interesting for us.** Village growth is driven by supplying what the village says it needs, and specialised buildings unlock specialised trades. But the village builds from fixed plans, and inside its territory it will knock down player builds ("The territory of the village belongs to them"). That's the opposite of "Villagers move into what players build".

### Village Quest (Living Kingdoms)

- **Status.** **Fabric, 26.3** (2.6.4, also 26.2 and 1.21.11), LGPL-3.0-or-later, source on GitHub. Small, about 12,000 downloads, first released May 2026.
- **Anchor blocks plus a checklist scan.** Four craftable anchors (Council Seat, Inn Barrel, Market Desk, Storehouse Anchor). Placing one opens a building checklist that shows "found/required/missing quantities" and the targets it found. The scan box is 17×17×9 by default and 25×25×17 extended. A roof is required for a council or inn. Rules are spelled out loosely:
  - A seat is a bottom-half stair facing a table 1 to 3 blocks away.
  - A table can be any fence with a pressure plate, closed trapdoor or carpet on top.
  - An inn bed needs both halves, and there has to be a reachable path from the entrance.
- **Recognition from vanilla behaviour.** An "inhabited village" needs three adults, three distinct assigned reachable beds and at least one assigned workplace. "Only actual loaded Vanilla assignments count." Recognition is earned once and survives residents dying.
- **Prosperity as investment.** Completing a village project (Apiary, Forge, Market, Pasture Charters, Watch Bell) opens a Prosperity branch. Each branch has three ranks bought with its currency, with capped permanent discounts and services (road patrols, festivals). Bounded, non-stacking effects.
- **Interesting for us.** It's Fabric 26.3 and readable code, and it does "a small anchor, a forgiving checklist and an in-game explanation of what's missing". Counting vanilla bed and job claims as proof of use is directly reusable.

### Live Villages

- **Status.** Fabric, 26.1.1 and 26.1.2 only (1.0.3, August 2026), CC-BY-4.0, source on the author's Git server. Very small (under 600 downloads) and self-described as a work in progress.
- **What it does.** Villages keep persistent "population, housing, stock, wealth, security, comfort, growth, projects, and routes". A **Trade Board** shows what the village needs, its surplus and its projects. Villagers start staged construction that players can help finish. Roads, harbours, lighthouses and guard posts raise connection and security. When unloaded, the village keeps running as an abstract simulation.
- **Interesting for us.** The **needs board** (shortages and surpluses visible in one place) and the idea that placed infrastructure (a road, a guard post) changes a stat without a fixed recipe.

### Totem Villagers

- **Status.** Fabric, 26.2 and **26.3**, Apache-2.0, source on GitHub, about 640 downloads.
- **What it does.** Trades are backed by real goods. Villagers have a work inventory, offers only exist while the stock exists ("sold-out rows disappear instead of receiving free restocks"), and workers make goods using real workstations and the server's recipes (Librarians enchant at a reachable Enchanting Table using real Lapis). Builders build vanilla house templates from supplied materials. Guards build Iron Golems from real Iron Blocks.
- **Interesting for us.** A strong model of "usage" that can't be faked: a Settlement's output depends on stations actually being supplied and used. It's also a big change to vanilla trading behaviour, which our technical parity rule would need to weigh.

### Real Working Villagers

- **Status.** Fabric, 26.1.2 to **26.3**, MIT, no public source, about 6,000 downloads.
- **What it does.** Visible job routines: Fishermen fish and fill barrels, Butchers cull pens while leaving breeding stock, Shepherds shear, Cartographers walk off and hang maps in item frames, smiths dress armour stands near their workstation.
- **Interesting for us.** Cosmetic, but it shows how much "being used" reads as life in a village. That's the feedback side of a usage-based score.

### Others checked

- **Guard Villagers.** Guards spawn in villages, and unemployed villagers become guards when given a sword or crossbow. Guards gossip with villagers and trust players with high reputation or Hero of the Village. Official builds are **NeoForge 26.3** (custom licence). The Fabric/Quilt port (`guard-villagers-fabricquilt`, MIT) stops at 26.2-pre-4. Relevant as a defence layer for Raids, not for prosperity.
- **Easy Villagers.** Picks villagers up as items and puts trading, breeding, iron farms and curing into single blocks. **NeoForge only** (26.3), All Rights Reserved. It removes the physical village, which is the opposite of what we want, but it shows which villager chores players find annoying.
- **VillagersPlus.** Five new professions (Horticulturist, Oceanographer, Alchemist, Occultist, Miner), each with a workstation, outfit and generated house. **Fabric 26.3**, GPL-3.0, about 9.3 million downloads. A model for themed workstations and trades.
- **Bountiful.** Bounty boards in villages ask for items or mob kills. "Decrees" placed in the board set its theme, and each board's reputation goes up with turned-in bounties, unlocking rarer bounties. Fabric up to **1.21.1** (8.0.0-beta.2), LGPL-3.0. A good model for per-building reputation and themed requests.
- **Villager Recruits** and its **Villager Workers** add-on. Hire villagers as soldiers, or as workers given placed "Work Areas" (crop farm, lumber, mining, building). Forge and NeoForge 1.21.1 only, All Rights Reserved. Interesting idea: the work area defines the job, not the building shape.
- **Townstead.** An MCA Reborn add-on adding hunger, thirst and energy needs and tiered kitchens. NeoForge 1.21.1 only.
- **Countered's Settlement Roads** (roads between villages, world generation, up to 1.21.10) and **Towns and Towers** (structure pack, Fabric 26.3). Structure and world-generation mods, so not relevant to prosperity.
- **"Villagers Plus"** as named in the brief is a different, tiny NeoForge 1.21.1 mod (`villagers-plus`). The relevant one is `villagersplus` above.

## Vanilla Minecraft facts

From minecraft.wiki unless noted. The Java numbers are what a mod would build on.

- **A village is just claimed points of interest.** Villagers claim three kinds: a bed, a job site block and a bell (gathering site), one slot each. In Java, villages have no defined centre or radius. Any chunk section containing a claimed bed, bell or job site counts as a village centre, and the 3×3×3 sections around it count as the village. Proximity uses a 17×17×17 cube. The old village object and "popularity" were removed in Java 1.14 "in favor of per-villager reputation". Bedrock still uses a boundary grown from the first bed.
- **Claiming.** An unemployed Java villager searches a 48-block sphere for the nearest unclaimed job site, must reach it within 60 seconds, and claims it once within 2 blocks. Breeding fails without an unclaimed reachable bed within 48 blocks. Babies need beds with two empty blocks above them.
- **Breeding.** Each villager needs 12 food points (bread 4, carrots, potatoes or beetroot 1), and food pickup needs `mobGriefing`.
- **Iron golems (Java).** A golem spawns when at least 5 villagers (3 if panicking) within 10 blocks are gossiping or panicking, have slept in the past day and haven't seen a golem recently.
- **Gossip and reputation.** Reputation is per villager and per player, from gossip types:
  - trading: +4 per trade, max 25.
  - major_positive (curing): 20, max 20, ×5, never decays and never spreads.
  - minor_positive (curing): 25, max 25.
  - minor_negative (attacking): 25 per hit, max 200, ×−1.
  - major_negative (killing a nearby villager): 25, max 100, ×−5.
  - Gossip decays every 20 minutes and spreads when villagers talk, losing value as it does.
  - Reputation moves prices, and at −100 or below, naturally spawned golems turn hostile.
- **Raids.** Bad Omen becomes Raid Omen on entering a village, and a raid starts 30 seconds later. Defending gives Hero of the Village (discounts) in Java.
- **Villager Trade Rebalance experiment.**
  - **Introduced** in Java snapshot 23w31a (1.20.2, August 2023) as an experimental data pack, and in Bedrock 1.20.30 previews. Mojang's reason: Librarian trades were "too overpowered" compared with enchanting or finding books.
  - **What it changes:** Librarians sell different books depending on the village's biome, with one special book per biome only from a master Librarian. Trident, crossbow and fishing rod enchantments are removed from trading. Diamond armour from Armorers needs some Diamonds. Chainmail is only sold in jungle and swamp villages. Seven new Cartographer maps. Wandering Traders get cheaper, more trades and can buy from the player. Structure loot gets more of certain books.
  - **Status:** The Cartographer and Wandering Trader parts left the experiment and became normal in Java 1.21.5 (25w07a) and Bedrock 1.21.70. The latest history entry, Java 26.1-pre2, gave master Librarians three trades so a book is always offered. The Librarian, Armorer and loot changes are **still experimental**. *Unverified:* any 26.2 or 26.3 changes. The page lists none.
- **Trades are data-driven on 26.3.** See [Super Awesome Villagers](super-awesome-villagers.md): `villager_trade` and `trade_set` registries, with 68 hardcoded trade set IDs.

## At a glance

| Game or mod | How a building is recognised | What measures progress | What players see |
|---|---|---|---|
| DQB1 | Enclosed, 2-high walls, door, light; furnishings make a recipe | Room score from blocks, door, light and recipe | Base level, score to next level |
| DQB2 | Enclosed 4–150 tiles, door; recipe furnishings with alternatives | Gratitude hearts from residents using rooms, requests, farming | Hearts to collect, bell to ring |
| Valheim | None; roof plus 80% cover rays | Comfort: best item per category near you | Comfort number, Rested time |
| Palworld | None; checklist of structures | Missions at the Palbox | Next mission list |
| ACNH | None; counts items and plants | Hidden development and scenery totals, duplicate penalty | Stars and a hint |
| MCA Reborn | Flood fill: roofed, door, required blocks | Per-player rank: reputation, population, building types | Building map, rank tasks |
| Millénaire | Village builds from its own plans | Village supplies, per-village and per-culture reputation | Needs list at the town hall |
| Village Quest | Anchor block plus checklist scan, roof, reachable beds | Projects, vanilla bed and job claims, Prosperity ranks | Found/required/missing checklist |
| Live Villages | Infrastructure blocks, staged projects | Simulated stock, comfort, security, routes | Trade Board needs and surplus |
| Totem Villagers | Real workstations | Real goods made and stocked | Offers appear or vanish with stock |
| Vanilla | Claimed beds, job sites, bells | Nothing; per-villager gossip only | Green particles on claim |

## Relevance

Four ways of measuring progress turn up, and they differ mainly in how much they tell players to build.

- **Counting required blocks (MCA Reborn, Palworld).**
  - **Strengths:** Cheap to compute and easy to explain. MCA's flood fill only asks for a roof, a door and a block list, so the shape is free.
  - **Weakness:** The list becomes the design. "A library is 32 bookshelves and a lectern" makes every library a bookshelf wall, and it tends toward hoarding the cheapest qualifying blocks.
  - **Verdict:** Fails "without dictating how people build" whenever the counts are large. Small counts (one bed, one workstation) are fine.
- **Room recipes (DQB1 and DQB2).**
  - **Strengths:** A recipe names a purpose (bedroom, kitchen, chapel), not a look. Alternatives ("3 Bonfires or 1 Brick Barbecue") and DQB2's free size, fanciness and ambience layer leave room for style.
  - **Weaknesses:** Minecraft builds rarely look like small enclosed rooms. Open halls, outdoor workshops, farms and markets don't fit "walls, door, ceiling". DQB2's 150-tile cap would reject many Minecraft builds. Valheim's cover quirks (big open halls read as outdoors, orientation changes the score) show where enclosure checks go wrong.
  - **Verdict:** Fine for a few small rooms players expect to make anyway (a bedroom), bad as the main measure.
- **Usage (DQB2 hearts, Village Quest's vanilla claims, Totem Villagers' real stock, vanilla gossip).**
  - **Strengths:** Measures what happens in a building, not what it looks like. A bed that a villager claims and sleeps in, a job site that is claimed and worked, a market that gets traded at, a kitchen chest that gets filled. Vanilla already records most of this (bed, job and bell claims, sleep, work and gossip), so a mod can read it without changing it, which fits technical parity. Usage also gives prosperity a reason to keep a building in use, not just built once.
  - **Weaknesses:** It can be farmed: a dense villager box claims beds and job sites fast. Caps per Settlement or diminishing returns per job type (like ACNH's duplicate penalty or Valheim's "highest item per category") limit that. It needs visible feedback, or players can't tell why prosperity moved. DQB2 makes the hearts physical, and Real Working Villagers shows work as animation.
  - **Verdict:** The best fit for "without dictating how people build".
- **Free decoration scoring (ACNH, Valheim comfort, DQB2 fanciness).**
  - **Strengths:** Any layout works, and Valheim's category rule ("one of each kind counts") rewards variety without a floor plan.
  - **Weaknesses:** Hidden scores frustrate (ACNH hides the numbers and gives vague hints). Value-based scoring rewards expensive blocks over good building.
  - **Verdict:** Works as a small bonus layer. It can't be the core, because builders on a shared server have different tastes, and a score that ranks taste will annoy someone.
- **A combination fits our vision best.**
  1. A Settlement is anchored by a placed block (a bell, as in vanilla, or a Village Quest-style anchor) and covers the claimed POIs around it.
  2. A building "counts" through the vanilla POIs and stations it contains and the villagers who claim them, with minimal shape rules (perhaps a roof over a bed, as MCA and Valheim check).
  3. Prosperity grows from **usage** and **breadth**: distinct professions working, beds slept in, trades made, stations used, with diminishing returns for repeats of one kind.
  4. A Millénaire or Live Villages-style **needs board** says what the Settlement lacks. Village Quest's found/required/missing checklist explains why something doesn't count.
- **What suits a shared 5–10 player server.**
  - **Shared progress, not per-player rank.** MCA's rank is per player, and vanilla gossip and Millénaire reputation are per player too. Settlement prosperity should belong to the Settlement so that any player's building, supplying or trading pushes it forward, in line with "progress is shared by building and giving".
  - **Everyone can contribute.** Builders add buildings, farmers and miners supply needs, fighters defend Raids. Usage-based measures let each playstyle add to the same total without one route being required. That fits our rule that Villager trades are supplemental, never a required route.
  - **No hard shape rules.** With several builders and styles on one world, recipe checks that reject someone's build will cause friction. Prefer rules a player can satisfy in any style: a POI and a roof.
  - **Server cost.** MCA turns automatic scanning off because flood fills are expensive. Reading vanilla POI claims (already tracked by the game) and scanning only on demand around an anchor (Village Quest's fixed box) is cheaper than scanning whole towns.
  - **Unloaded Settlements.** Players will be away from the central Settlement. Live Villages keeps unloaded villages running through abstract catch-up, and Village Quest refuses to count unloading as loss. One of these is needed so prosperity doesn't decay while nobody is there.
- **Mods we could use or study on Fabric 26.3.** Village Quest (LGPL-3.0, source), Totem Villagers (Apache-2.0, source), VillagersPlus (GPL-3.0, source) and Real Working Villagers (MIT, no source). MCA Reborn (GPL-3.0) has Fabric up to 26.2 and is the best code to study for building recognition. Millénaire has no Fabric build. Guard Villagers' Fabric port lags behind (26.2-pre-4).

## Sources

- https://dragon-quest.org/wiki/List_of_room_recipes_in_Dragon_Quest_Builders (DQB1 room rules and recipes)
- https://dragon-quest.org/wiki/List_of_room_recipes_in_Dragon_Quest_Builders_2 (DQB2 room rules and recipes)
- https://dragon-quest.org/wiki/Room_preference (DQB2 size, fanciness, ambience)
- https://kotaku.com/helping-people-in-dragon-quest-builders-2-gives-me-so-m-1836663948 (hearts, bell, unlocks)
- https://www.thegamer.com/dragon-quest-builders-2-quickly-upgrade-base-furrowfield-guide/ (heart sources)
- https://www.rpgsite.net/feature/8770-dragon-quest-builders-2-explorers-shores-guide (gratitude on main islands vs Isle of Awakening)
- https://primagames.com/eguides/dragon-quest-builders-eguide/builder-basics/building-your-base (DQB1 base level; page blocked automated access, so only seen through search summaries)
- https://valheim.fandom.com/wiki/Comfort, https://valheim.fandom.com/wiki/Cover, https://valheim.fandom.com/wiki/Shelter, https://valheim.fandom.com/wiki/Workbench (read through the MediaWiki API)
- https://www.bisecthosting.com/blog/enshrouded-max-comfort-guide-buff-furniture and https://steamcommunity.com/app/1203620/discussions/0/4142816719796366569 (Enshrouded comfort, through search summaries)
- https://palworld.fandom.com/wiki/Palbox (read through the MediaWiki API), https://twinfinite.net/guides/full-palworld-base-missions-list/, https://game8.co/games/Palworld/archives/440428
- https://nookipedia.com/wiki/Island_evaluation and https://nookipedia.com/wiki/Happy_Home_Academy (read through the MediaWiki API)
- https://minecraft.wiki/w/Village_mechanics, https://minecraft.wiki/w/Villager (gossip, job site claiming, beds), https://minecraft.wiki/w/Villager_Trade_Rebalance (including History)
- https://modrinth.com/mod/minecraft-comes-alive-reborn and https://github.com/Luke100000/minecraft-comes-alive (branch 1.21.1: `data/mca/building_types/*.json`, `data/mca/tasks/*.json`, `server/world/data/Building.java`, `Village.java`, `villageComponents/VillageTaxesManager.java`, `resources/Rank.java`, `resources/Tasks.java`, `Config.java`)
- https://millenaire.org/ and https://millenaire.org/downloads (versions, 9.0 news), https://millenaire.org/encyclopedia/culture/norman, https://github.com/Kinniken/Millenaire (`millenaire/languages/en/help/help_1.txt` to `help_13.txt`, `norman_reputation.txt`)
- https://modrinth.com/mod/village-quest and https://github.com/topasmusic/village-quest (`26.3/docs/wiki/kingdom-buildings.md`, `prosperity-and-economy.md`, `core-progression.md`)
- https://modrinth.com/mod/live-villages
- https://modrinth.com/mod/totem-villagers-workforce and https://github.com/Yunitrish006006/TotemVillagers
- https://modrinth.com/mod/real-working-villagers
- https://modrinth.com/mod/guard-villagers, https://modrinth.com/mod/guard-villagers-fabricquilt, https://github.com/seymourimadeit/guardvillagers
- https://modrinth.com/mod/easy-villagers, https://modrinth.com/mod/villagersplus, https://modrinth.com/mod/villagers-plus, https://modrinth.com/mod/bountiful, https://modrinth.com/mod/villager-recruits, https://modrinth.com/mod/villager-workers, https://modrinth.com/mod/townstead, https://modrinth.com/mod/countereds-settlement-roads, https://modrinth.com/mod/towns-and-towers
- Modrinth API (`api.modrinth.com/v2/project/<slug>` and `/version`) for loaders, game versions, licences and download counts, checked November 26, 2026
