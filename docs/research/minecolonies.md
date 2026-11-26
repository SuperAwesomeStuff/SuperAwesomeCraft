# Research: MineColonies

Background for the Season 1 Villager rework and the later Settlements design, where Villagers live in and use what players build and Settlement Prosperity unlocks trades and services. MineColonies is the best-known Minecraft colony mod, and The Bottom Dweller reviewed a modpack built around it, so it shows both how far the "Villagers run a town" idea has been taken and where it frustrates players. Gathered November 2026 from the official MineColonies wiki (minecolonies.com/wiki, 1.21.1 pages), the ldtteam/minecolonies GitHub repo, its CurseForge and Modrinth pages, the CurseForge page of the Life in the Village 4 modpack, the ModDex listing of an unofficial Fabric port, and the auto-generated English subtitles and description of The Bottom Dweller's video. Anything marked *Unverified* couldn't be confirmed from these sources.

## MineColonies (the mod)

### Versions, loaders, licence

- **Forge and NeoForge only, officially.** CurseForge lists Forge and NeoForge for 1.18 to 1.21.1, with no Fabric. The newest files are `1.21.1-1.1.1426-snapshot` (NeoForge, 24 November 2026) and `1.20.1-1.1.1323-snapshot` (Forge); the main CurseForge file is still 1.20.1. The repo's default branch `version/main` builds for Forge 1.20.1 and Java 17, and `version/1.21` is the NeoForge line. There's no official 26.x branch.
- **An unofficial Fabric port for 26.2 and 26.3 exists.** "MineColonies (Unofficial Fabric Port)" by unknown_wq, GPL-3.0, ported from upstream's 1.21.1 branch to Fabric and 26.x. The latest file is `minecolonies-26.3-0.0.85.jar` (24 September 2026), with about 2,000 downloads. It isn't affiliated with LDTTeam and asks users not to report its bugs upstream. It lists known gaps, such as colony blocks not being protected from vanilla explosions. Its original CurseForge URL now returns 404. *Unverified:* whether it still exists on CurseForge, and how stable it is.
- **Licence.** The GitHub repo's `LICENSE` is GPL-3.0, and Modrinth says GPL-3.0-or-later. CurseForge shows "Custom License". It is made by Let's Dev Together (LDTTeam), a non-profit, and has about 90.9 million CurseForge downloads.
- **Dependencies:** Structurize (schematics and the build tool), BlockUI, Domum Ornamentum and Multi-Piston.

### Founding a colony

- **Supply Camp or Ship first.** Placing one (once per world by default) gives a Town Hall block and a Build Tool. The camp needs a perfectly clear flat 16×17 area: "no holes, flowers, grass, ferns". A colony can also be founded by claiming one of the abandoned colonies that generate in the world.
- **The Town Hall fixes a protected claim.** By default the claim is 4 chunks in each direction from the Town Hall block, "from bedrock to the sky limit". It can never be moved, short of an admin deleting the colony. Inside it, only the owner can place, break or interact with blocks unless other players are given ranks, and explosions are off. Colonies must be at least 8 chunks apart (`minColonyDistance`), and one player normally owns one colony (`allowinfinitecolonies` is off and described as "buggy").
- **Claims grow with buildings.** Each finished hut claims a radius around itself (Guard Towers and the Town Hall claim the most), up to 20 chunks from the Town Hall (`maxColonySize`).
- **Four citizens arrive at once.** They are not vanilla Villagers.

### Builders and schematics

- **Nothing gets built without a Builder.** "No buildings can be constructed until you have a Builder." The Builder first builds their own hut, and "can never build or upgrade a hut to a higher level than their own Builder's Hut level." Each Builder works on one order at a time. The Town Hall keeps a work-order queue the player can reorder.
- **Buildings are schematics placed with the Build Tool.** The player picks a building from a **style pack** (sets of matching schematics, more than 10,000 in all per CurseForge), previews it in place, and the Builder builds it block by block, asking for every material.
- **Player-built buildings don't count.** The wiki's own FAQ: "Can I just build my own buildings and get the colonists to move in? No. Functional buildings must be constructed by the Builder. You must either use prefabs from an existing style pack, or design your own custom huts in a separate creative world first. MineColonies is more like an RTS than classic Minecraft building."
- **Custom buildings are possible but heavy.** A player scans a build with the Scan Tool (the hut block as anchor), saves it into a style pack, and needs a variant for each building level. On a server, functional custom huts must be installed in the server's `blueprints` folder by an admin. Player-uploaded scans only work as decorations, and the Builder can't level them up later ("which will cause a crash").
- **Levels 1 to 5.** Most huts upgrade to level 5, each level being a new schematic the Builder rebuilds. Level sets capacity: a Residence houses one citizen per level, a Warehouse allows 2 Couriers per level (10 at level 5), a University one Researcher per level, a Courier's hut carries 2 to 5 stacks (unlimited at 5), and a Guard Tower patrols 80 to 200 blocks. The Tavern stops at level 3.

### Citizens, housing, food and happiness

- **Population is capped by beds.** The citizen limit is "determined based on the amount of available sleeping places in the colony, but also affected by research". After the first four are housed, new citizens appear when there's a free bed, or can be recruited at the Tavern by giving visiting travellers the items they ask for. Better Taverns attract more and better visitors.
- **Research raises the cap in steps.** Above 25 citizens the University has to research Outpost (50), Hamlet (100), Village (150, needs Town Hall 4) and City (500, needs Town Hall 5), each costing 1 Steak. The server config caps a colony at 250 by default (`maxcitizenpercolony`, range 25 to 500).
- **Food is its own production chain.** MineColonies adds biome-locked crops (cold, temperate, humid, dry) and tiered meals cooked by a Chef and served at a Dining Hall. "Using vanilla food carries a penalty." Citizens "demand higher levels of food based on their residence level" and refuse food below that tier, so upgrading houses raises food demands. Some top-tier dishes need crops from several biomes, "so obtaining them will require trading with other colonies". A citizen at zero saturation stops working.
- **Happiness has three base factors:** saturation, housing level (homes above 2.5 count positively), and security (at least two Guards for every three citizens, and Guard Towers near homes and workplaces). Each factor gives partial credit. Citizens lose happiness each night they are sick, homeless, unemployed or idle at work, and mourn dead citizens for three days. Higher colony happiness gives new colonists higher starting skills.

### Jobs and the request system

- **About 50 job buildings.** Forester, Mine, Farmer, Fisher, Sawmill, Smeltery, Stonemason, Blacksmith, Enchanter's Tower, herders, Hospital, Library, School, University and more. Each hires citizens, who level skills by working.
- **Citizens request what they need automatically.** A worker checks their own inventory and hut, then makes a request. If the item is in the Warehouse, a Courier delivers it. If not, the colony looks for a citizen who can craft it, and that crafter requests the ingredients in turn. For example, the Builder's oak stairs become a Sawmill request for planks, then for logs.
- **What the colony can't make, the player must supply.** "The request can't be filled automatically, and the citizen will wait for you, the mayor, to fulfill the request manually." Waiting citizens show a red icon. The Clipboard lists every open request, with a filter to hide "constant requests (e.g., the Smelter requesting ores)".
- **The player can use the colony as a supplier too.** A Postbox lets the player request items from the Warehouse or have workers craft them, and a Stash returns items to the Warehouse. The Warehouse can keep a minimum stock, and buildings get Courier pickup priorities.

### Research, guards and raids

- **Research trees:** Civilian, Combat, Technology and Unlockables. Column N needs a University of level N. Each research costs items from the player's inventory and real play time; Researchers can bank some offline time from University level 3. Some research is exclusive or can't be undone.
- **Basic behaviour is behind research.** "Rainman" (cost: 1 Bucket of Salmon) is what makes "Citizens Work in Rain". Until then, rain stops work. Other research adds citizen inventory slots, XP growth and similar.
- **Guards** live in Guard Towers or a Barracks with Barracks Towers. Types include Knights and Archers, trained at a Combat Academy and an Archery. Mercenaries can be hired from the Town Hall ("they're mean and will steal from citizens!").
- **Raids** spawn at nightfall outside the colony, about every 14 nights on average and at least 10 apart by default. The raider type depends on the biome: Barbarians, Nordic raiders in taiga, Mummies in desert, Amazons in jungle, and Pirates by ship on any large body of water. During a raid citizens stop working and run home. By default raiders break doors and blocks, place ladders and bridge gaps, and "any attack that hits a raider too hard will be reflected back", so cheese and modded turrets don't work. Difficulty rises as the colony grows and more raiders are killed. The formula "is not publicly known". Surviving a raid with no deaths gives a happiness boost. Raids, block breaking and difficulty are all server config options.

### What it does well and badly as a game

- **Freedom: low.** Players choose where buildings go, which style pack, and the layout of paths and decorations, but every working building is a pre-made schematic, and the wiki itself calls the mod "more like an RTS than classic Minecraft building". A player's own house doesn't count as a Residence.
- **Progress: explicit and countable.** Hut levels, Town Hall level, citizen count against the cap, research completed and claim radius. It's legible because buildings are standard. That's exactly what gives up building freedom.
- **The request loop makes the player the colony's supplier.** Early on, everything the colony can't make (most materials, food, tools) lands on the player as requests. Later the colony supplies itself and also supplies the player through the Postbox. The loop is useful once it runs, but early on it's a to-do list the citizens give the player.
- **It replaces normal play more than it supplements it.** Food, building, mining and crafting move into colony chains with their own crops, meals and research gates, and the documented early build order (Builder, housing, food, Forester, Mine, Warehouse) is a fixed checklist. A mature colony can supply most materials, which makes it a full alternative economy rather than a side option. (This is a judgement from the documented systems, plus the video below.)
- **The claim system is built for separate colonies.** One colony per player, 8-chunk spacing, owner-only building inside the claim by default, and alliances between colonies. A shared town needs an owner who grants ranks to everyone else.

## The Bottom Dweller's review of Life in the Village 4

The video "The Life in the Village 4 Modpack Made Me Enjoy Create." (uploaded 7 November 2025, 11 minutes, chapters: positives, "Issues with mine colonies", pack-specific critiques, final thoughts). Content below comes from the auto-generated subtitles.

### The modpack

- **Life in the Village 4** by dreams01 on CurseForge: Forge 1.20.1, described as "lowtech, quest-driven, vanilla-plus" and centred on MineColonies, with Create-based automation, cooking, exploration and cosmetics. It has a farming-focused peaceful option and about 248,000 downloads. In the video he says the mod list reads about 400, mostly libraries and Create add-ons. *Unverified:* which pack version he played. The CurseForge page lists 0.8 (2 November 2026) and 0.3 (May 2025), and the review predates 0.8.
- **Pack changes he names:** Busy Bees nerfed so bees are "supplemental instead of a direct infinite resource farm" (the pack page says "re-balanced productive bees"). Crop Tweaks, "so you couldn't just make a wheat farm and feed your village infinitely. You have to make meals or cook meat." Citizens cut grass by hand instead of needing hoes. *Unverified:* Crop Tweaks isn't named on the pack page.

### His verdict

- **No score.** He calls MineColonies the mod he now has "the most beef with", replacing Create. He stopped playing partway through ("I closed the modpack… I have not opened it since"), and later footage is B-roll. He says he'd skip any pack that requires it.
- **He likes the genre.** He names City Skylines, RimWorld, Dwarf Fortress, Factory Town and Banished: "This is hatred from someone who would have loved this idea." He calls the mod "an amazing concept with some of the most infuriating execution I've ever seen."
- **The pack itself is mostly praised.** A light mod list where "all the additions and changes seem to be with the goal of complementing Mine Colonies", and balance changes that aim to make bees supplemental. "Life in the Village 4 is a mod pack with a lot of potential that is unfortunately mostly held back by the mod it banks on."
- **Create became the fun part.** Its tree-breaking and cobblestone generators fed the colony's huge demand for Oak and Cobblestone ("You have no idea just how much oak I needed"). He enjoyed Create because MineColonies left him "a lot of free time".

### Criticism of MineColonies

- **Slow progression.** Supply Camp, then Town Hall, then Builder's Hut, then waiting for the Builder to build their own hut. "Buildings took literal Minecraft days to complete from one Minecraft builder."
- **Idle citizens.** "The villagers actively stand around scratching their asses until they have a dedicated finished building." The other starting citizens don't forage, defend themselves or help, so he had to feed them by hand ("Here comes the airplane"). His suggestion: "other games… make the unemployed villagers do tasks given to them at a reduced rate", for example unemployed citizens building at a quarter of the speed.
- **Tiny Builder storage.** The hut holds less than a small chest while builds need "90 different resources" in hundreds, and the Builder fills it with dirt and seeds dug up while clearing.
- **Basic behaviour locked behind research.** Not working in the rain until researched was where he quit: "I remember when early settlers would never work in the rain because they needed the reconstruction for dummies first. Oh wait, that never happened." The wiki confirms the "Rainman" research.
- **Performance and bugs.** Severe lag from opening the build menu on a high-end PC, a Fisher stuck entering and leaving bed, a Builder idling in the tavern despite an order. Most needed a reload.
- **Mobs as more friction.** Without Guards, citizens get attacked, so he switched to Peaceful "because I didn't want to be more frustrated than I already was".
- **The Town Hall couldn't be placed near world spawn.** *Unverified:* the 1.21.1 config default `mindistancefromworldspawn` is 0, so this is probably the pack's setting or an older default.

### Pack-specific criticism

- **Exact-item matching.** Architect's Cutter makes its own stair and slab variants, which MineColonies' schematics don't accept as vanilla Oak Slabs.
- **One-way integration.** Create, Busy Bees and Tinkers' Construct feed MineColonies, but MineColonies gives nothing back to them.
- **New biomes against vanilla-wood schematics.** He picked an Oak style and still needed a long trip for Spruce.
- **Worldgen structures taking build space.** He'd rather have them underground or not at all, so builders don't have to clear them and outposts aren't nearby.

### Criteria he applies

- Workers should be useful at once, even at reduced efficiency, rather than idle until their building exists.
- Basic, expected behaviour shouldn't be a progression unlock.
- Storage limits shouldn't fight the scale of what the system demands.
- A pack's added mods should work with the core mod both ways, not just feed it.
- Consistent with his earlier criteria: "slower is not harder". Long waits for one Builder are a time sink, not difficulty.

## Relevance

- **The biggest lesson is the schematic wall.** MineColonies gets readable progress (hut levels, a population cap) by requiring its own buildings, and its wiki openly says player-built houses don't count. Our "Prosperity without dictating how people build" question is exactly the trade-off it settled the other way. To keep building freedom, Prosperity needs to count what's inside and around player builds (beds, workstations, light, enclosed rooms, paths, decorations, use over time) instead of matching templates.
- **Housing as the population cap transfers well.** "One citizen per bed, more beds draws more people" is simple, needs no schematic, and fits Villagers moving into player-built Settlements. The Tavern idea (visitors who join if a player gives them something) is a natural place for player-to-Villager interaction, and close to our catch-up Idea of Villagers trading an Era's materials once a player gives them some.
- **Rising demands with house quality is a good loop to borrow carefully.** Citizens in better homes want better food, which gives upgrades a cost and a reason. On a mixed server, this should open better trades and services, not make the Settlement stop working if nobody tends it. Starving citizens who stop working would turn the Settlement into a chore for whoever cares most.
- **Don't make the player the colony's supplier.** The request loop is MineColonies' core, and in the video it is the frustration: idle citizens and constant fetch requests. Our rule that trades are supplemental argues for the reverse direction: Villagers offer things to players who'd rather not gather them, and their needs are met by what players have built, not by a stream of personal requests. If Villagers ask for things, it should be optional, rewarded and visible to the whole server, never a gate on basic function.
- **No idle Villagers, no basic behaviour behind research.** His two concrete criteria are directly useful. A Villager in a Settlement should do something useful from day one, even at reduced effect, and things players expect (working in the rain, not starving) shouldn't be unlocks. Unlocks should add trades and services, not remove friction we created.
- **Don't build a second economy.** A mature MineColonies colony supplies most materials and gives the player a Postbox to request them. That's what "replacing normal play" looks like, and the September playtest already showed that one dominant route flattens playstyles. Prosperity-unlocked trades should stay priced as a convenience, with the buyer income limits still open in the design map.
- **Shared, not owned.** MineColonies assumes one owner per colony, 8-chunk spacing and owner-only building inside the claim. For 5 to 10 friends the Settlement should be a shared place anyone can build in and contribute to, ideally one central Settlement (vision.md's "being near the group"). Claims and ranks are the wrong default here.
- **Raids: some confirmations, one conflict.** MineColonies scales raids with colony growth and kills, spaces them out (about two in-game weeks), and makes them hard to cheese. That matches our Leaning that Raids scale with Prosperity. Its raiders break blocks, doors and walls by default, which is the opposite of our Leaning that a Raid never breaks player blocks. Its happiness bonus for a raid with no deaths is a cheap, readable reward we could echo. A citizen who loses a day mourning is a penalty that lands on players who weren't online.
- **Exact-item matching bites.** The Architect's Cutter slab problem is a warning for any check of "what's in the Settlement": count block families or tags, not exact item IDs.
- **Technical:** there's no official Fabric or 26.x MineColonies, so it can't be a dependency or a straightforward reference implementation. The unofficial 26.3 Fabric port (GPL-3.0) could be read for how pathfinding, work orders and the request graph are structured, but it is a large unofficial codebase. Copying code would carry GPL obligations.

## Sources

- The Bottom Dweller, "The Life in the Village 4 Modpack Made Me Enjoy Create.": https://www.youtube.com/watch?v=FcuXRKyBplY (auto-subtitles and description via yt-dlp)
- Life in the Village 4 (CurseForge): https://www.curseforge.com/minecraft/modpacks/life-in-the-village-4
- MineColonies wiki, Getting Started: https://minecolonies.com/wiki/tutorials/getting-started/
- MineColonies wiki, Schematics (including the "Can I just build my own buildings" FAQ): https://minecolonies.com/wiki/tutorials/schematics/
- MineColonies wiki, Town Hall: https://minecolonies.com/wiki/buildings/townhall/
- MineColonies wiki, Builder's Hut: https://minecolonies.com/wiki/buildings/builder/
- MineColonies wiki, Residence: https://minecolonies.com/wiki/buildings/residence/ ; Tavern: https://minecolonies.com/wiki/buildings/tavern/
- MineColonies wiki, Warehouse: https://minecolonies.com/wiki/buildings/warehouse/ ; Courier's Hut: https://minecolonies.com/wiki/buildings/deliveryman/
- MineColonies wiki, University: https://minecolonies.com/wiki/buildings/university/ ; Guard Tower: https://minecolonies.com/wiki/buildings/guardtower/
- MineColonies wiki, Requests: https://minecolonies.com/wiki/systems/request/
- MineColonies wiki, Research (including Outpost, Hamlet, Village, City and Rainman): https://minecolonies.com/wiki/systems/research/
- MineColonies wiki, Raids: https://minecolonies.com/wiki/systems/raid/ ; Colony Border: https://minecolonies.com/wiki/systems/border/ ; Colony Protection: https://minecolonies.com/wiki/systems/protection/
- MineColonies wiki, Happiness: https://minecolonies.com/wiki/needs/happiness/ ; Food: https://minecolonies.com/wiki/needs/food/
- MineColonies wiki, configuration defaults: https://minecolonies.com/wiki/setup/configuration/minecolonies/
- GitHub repo (licence, branches, releases, `gradle.properties`): https://github.com/ldtteam/minecolonies
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/minecolonies
- Modrinth: https://modrinth.com/mod/minecolonies
- Unofficial Fabric port listing (ModDex): https://moddex.gg/mod/minecolonies-unofficial-fabric ; original CurseForge URL (404 when checked): https://www.curseforge.com/minecraft/mc-mods/minecolonies-unofficial-fabric-port
