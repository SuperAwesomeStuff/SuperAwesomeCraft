# Research: Quest Boards and Rotating Task Systems

Background for the Villager and Settlement rework, where the main way to earn Emeralds is a **village quest board** that refreshes every so often with tasks (gather resources or food, clear a nearby Pillager Outpost, defend the village from a roaming band of mobs, and more inventive ones), plus a daily salary for players who've done a lot for a village. Players may run several villages each. The lesson from the September 2026 playtest (see [vision.md](../vision.md)) is that when Villagers bought raw ore, every route collapsed into "mine, sell, buy", and fishing, brewing, Trial Chambers and Bastions went unused. This note looks at how existing boards generate tasks, how often they refresh, how they cap income, whether tasks are shared or per player, and what players complain about. Gathered November 2026 from mod pages and source code (Bountiful's GitHub `dev` branch and Kambrik docs, the Modrinth API, Village Quest's wiki in its repository), official or community wikis (Stardew Valley Wiki via an archived copy, Deep Rock Galactic, Sea of Thieves and Warcraft wikis on wiki.gg, Nookipedia, Fextralife), developer posts and interviews, and a few design articles. The Terraria Angler is covered in [Terraria NPCs](terraria-npcs.md#services-not-items) and only cross-referenced here. Anything marked *Unverified* couldn't be confirmed from these sources.

## Minecraft mods

### Bountiful (Ejektaflex)

The best-known Minecraft bounty board: about 7.9 million Modrinth downloads. Read from the Kambrik docs and from the source on the GitHub `dev` branch (last push August 2026).

- **Versions and licence.** The latest Modrinth releases are `8.0.0-beta.2` (April 2026) for 1.21–1.21.1 on Fabric and NeoForge, with older lines for 1.20.4, 1.20.1 and 1.17.1. The `dev` branch's `gradle.properties` targets `minecraft_version_range=[26.1.2]` as `8.0.0-beta.1`, so a 26.x port is in progress but unreleased. Nothing targets 26.3. Licence: Modrinth and `gradle.properties` say LGPL-3.0; GitHub's detected licence is GPL-3.0. Depends on the author's Kambrik library.
- **Boards.** One board per village by default (`villageGenFrequency` 1.0), or crafted. Each board has **21 bounty slots** and **3 Decree slots** (`BoardInventory.kt`).
- **Decrees set the theme.** A Decree is an item that maps to objective and reward pools: the Farmer Decree pulls from `farmer_objs` plus a shared `_all_objs`, and farming and gardening reward pools. A new board gets one random Decree. Up to three Decrees on one board mix their pools, so a farming bounty can pay in iron armour (mixing can be turned off with `allowDecreeMixing`). Decrees come from crafting (a blank one becomes random when placed) or as bounty rewards.
- **Generation is worth-matched.** Rewards are picked first: 1 or 2 rewards, each a random amount × a `unitWorth`. The total is split into 1 or 2 pieces, and for each piece an objective is picked whose worth is within ±25% of it. If nothing fits, it adds objectives until 50% of the reward worth is covered. Imbalance is deliberate ("some bounties are better deals than others"). Every pool entry is data (JSON in data packs or the config folder).
- **Rarity.** Rewards carry vanilla rarities (Common to Epic); rarer ones are picked less often, and the bounty takes the rarity of its rarest reward. Entries can require a minimum reputation.
- **Refresh.** Every `updateFrequencySecs` (default **45 seconds**) the board adds 1 bounty (2 if 18 or more slots are free) and, once 12 slots are filled, prunes 1 or 2 old ones, weighted towards the oldest. Catch-up after the chunk was unloaded is capped at 21 updates. A new board starts with 5 updates.
- **Timers.** Each bounty has a deadline: 750 seconds plus about 0.35 × each objective's worth × a per-entry time multiplier. Expired bounties vanish. `shouldHaveTimersAndExpire` turns this off. Advancements reward finishing fast ("Rush Order") or late ("Procrastinator").
- **Shared board, per-player copies.** Everyone sees the same bounties, but taking one only hides it for you ("won't disappear from your friend's board"). A `globalBoardState` option makes every board in the world share one state.
- **Reputation is per board, pooled across players.** Reputation is a level computed from the total completions of **all** players at that board. Higher reputation makes rare rewards more likely ("at reputation level 5, Uncommon rewards become twice as likely") and gives a discount on objective size: rep / 75, so 40% at rep 30, approaching about 66% beyond.
- **No income cap.** Nothing limits how many bounties a player turns in per day; the limit is the 45-second trickle and the 21 slots, multiplied by every board a player can reach. *Unverified:* whether anyone has written about Bountiful being farmed across many villages; no discussion was found.

### Village Quest (topasmusic)

The most relevant current mod: Fabric for 1.21.11, 26.2 and **26.3** (version 2.6.4, November 2026). Functional code LGPL-3.0, creative assets All Rights Reserved. Read from its wiki in the GitHub repository.

- **A Questmaster, not a board.** Each village has NPC roles (Questmaster, Pilgrim trader and others). The Questmaster offers **Daily**, **Weekly**, **Story** and **Special** (relic) quests, plus long-term **Village Projects** (charters).
- **One Daily per player per reset day.** Dailies are random from a non-combat pool (13 named, such as Bakehouse Help, Fuel for the Workshop, Meal from the River, Wool for the Weaver). Resets at 06:00 server time; Weeklies reset Monday 06:00. The Daily is a per-player "allowance", not a per-village one. One free reroll of an unaccepted Daily per day (two with enough reputation).
- **Combat was taken out of the rotation.** "Combat dailies still exist in code, but they are not part of the normal Questmaster daily rotation anymore", and Peaceful worlds get supply alternatives for combat quests. *Unverified:* the author's reason.
- **Fresh work, not stockpiles.** Verbs are literal: "mine, harvest, craft, smelt, breed … must happen after acceptance"; "bring, deliver" check items at hand-in. Stored goods never replace a required action. Multi-step quests (harvest, then bake) show one stage at a time.
- **Rewards.** A custom currency (Silvermarks and Crowns), reputation in five tracks (Farming, Crafting, Animals, Village Trade, Roadside Watch), XP paid in "level bars" so it means the same at level 10 and 200, relics, and Prosperity investments (permanent 5/10/15% discounts in five branches). Services are Crown sinks with bounded, non-stacking effects; the "Village Festival" bonus returns slightly more than it costs only if you then complete quests.
- **Pacing limits.** Story arcs have a one-hour real-time cooldown between them; Pilgrim rumours are "one rumour per day".
- **Multiplayer.** Supported quests use party sessions with pooled deliveries; tools, guest quests and some story choices stay personal.

### Village Bounties

MIT, Fabric and NeoForge (NeoForge 26.3, Fabric up to 26.2; version 1.3, November 2026). From the Modrinth page.

- **Boards as village structures.** Kill bounties for vanilla mobs or delivery bounties for items, in Easy, Medium, Hard and Elite tiers, with biome-specific jobs (charcoal for snowy villages).
- **Per-player active bounty**, tracked on the HUD, turned in at any board.
- **Refresh on completion.** Bounties refresh after you complete a job, and each completion grants one manual reroll. 1.3 made refreshes "less likely to repeat recent jobs". Rewards are configurable item pools with a multiplier.

### Others checked

- **Villager Quests (MidacoYT):** Forge 1.20.1, All Rights Reserved. Each Villager offers profession-based quests saved per player and per Villager, with cooldowns and a quest button in the trade screen. v1.0.1 cut quests to "1-2 objectives maximum instead of overwhelming requests".
- **VillagerQuests (Globox1997):** Fabric 1.20.1/1.21.1, MIT. Not a generator: it binds hand-made FTB Quests to a specific Villager and adds a "talk to Villager" task.
- **Alex's Village Quests:** Fabric 1.21.8–1.21.9, All Rights Reserved. Shift-right-click a Villager for a random quest from Easy to Extreme. Not examined further.
- **Mebahel's RPG Villager Quests and Companions, MCA: Quests:** story-driven, branching quests on 1.21.1; not rotating boards.
- **Simple Quests (Flemmli97):** server-side, data-pack-defined quests (item submission, kills, advancements) with cooldowns, through a command menu. Ships with no quests. A framework, not a board.
- **FTB Quests:** the standard pack questbook: a hand-authored chapter tree of tasks and rewards that guides players through a modpack. It's a fixed checklist, not a rotating board, though packs add repeatable quests.
- **Not found:** no mod called "Village Quests" in the Bountiful style beyond the above. *Unverified:* CurseForge-only mods weren't searched.

## Other games' rotating task boards

### Stardew Valley: Help Wanted and Special Orders

- **Help Wanted.** Most days a random quest may appear on Pierre's board (yellow "!" when it's new). Two days to finish, no penalty for abandoning. Five types:
  - **Item Delivery** (most common): a seasonal crop, forage or fish, a mineral matching your mine depth, or a bar if you can craft a furnace. Pays **3× the item's sell price** plus 150 friendship. Never asks for what you can't reach yet ("Sandfish will never be requested until the player has unlocked the Desert").
  - **Gathering**: items must be collected after accepting.
  - **Slay Monsters**: never a monster you can't reach yet.
  - **Fishing**: 1–5 of a seasonal fish; pays the fish's value and you keep it.
  - **Saying Hello**: talk to everyone in town.
- **Meta-reward.** Every 3 completions earn a Prize Ticket; 10 and 40 completions earn achievements, a hat and mailed gold.
- **Special Orders** (from Fall 2, Year 1). Bigger orders posted **every Monday**, with 7 to 28 days to finish, counted from posting, not acceptance. Several can be active at once. Not cancellable. Rewards mix gold with **permanent unlocks**: recipes (the Geode Crusher from "Cave Patrol", the Solar Panel from "Island Ingredients"), shop stock and new buildings. Some repeat, some are one-off. They include server-wide-style goals ("ship 100 [Crop] by the end of the season").
- **Mr. Qi's Special Orders** (Ginger Island, after 100 Golden Walnuts). **Two choices every Monday**, repeating forever, paying **Qi Gems**, a currency only Qi's shop takes. Deliberately extreme challenges: ship 500 Qi Fruit, reach floor 100 of the Skull Cavern without eating, catch five legendary fish in 3 days, ship 100,000g of freshly cooked food. *Unverified:* that the town board also offers exactly two choices.
- **Multiplayer.** *Unverified:* whether Help Wanted quests are shared (first to accept takes it) or per player in co-op. Special Orders are farm-wide.
- **Complaints.** *Unverified:* no primary discussion was gathered. Help Wanted is usually described as low-stakes and skippable.

### Monster Hunter: quest board, Investigations, Bounties

- **Quest board.** Hand-made Assigned (story) and Optional quests are always available; online Event Quests rotate on a schedule. *Unverified:* the exact rotation cadence per game.
- **Investigations (World).** Generated quests found by tracking, slaying or breaking monster parts, saved to a list. Each has random attempts (3 to 8), a time limit (15/30/50 minutes), a faint limit (1 to 5), a player cap (2 or 4), and 2 to 5 bonus reward slots in bronze, silver or gold. **Harsher conditions give more gold slots.** Tempered versions pay decorations. Players keep a list and pick the best ones.
- **Bounties (World).** Resource Center tasks ("Critical", "Limited", "Normal") paying Research Points and items. *Unverified:* refresh cadence and caps.
- **The lesson.** The difficulty dial is on the quest card itself (time, faints, gold slots), so players choose risk for reward.

### Valheim and Enshrouded

- **Valheim** has no bounty board. Hildir's Request (patch 0.217.14, August 2023) added a merchant who asks for one chest from each of three new dungeon types (Smouldering Tombs, Howling Caverns, Sealed Tower); each returned chest unlocks more of her stock. A one-off unlock chain, not rotating. *Unverified:* exact stock unlocks (wiki pages couldn't be fetched). Bounties exist only in mods (Epic Loot). *Unverified.*
- **Enshrouded** has no bounty board found. Rescued NPCs at the base give fixed quest lines.

### Deep Rock Galactic: mission rotation and Deep Dives

- **Mission rotation.** Biomes "rotated every 30 minutes, starting at 00:00:00 AM based on the host machine's system time", with 5–6 biomes open per rotation and at most one region fully closed. Missions roll random Warnings (harder, more pay) and Anomalies, such as Double XP. *Unverified:* that all players see the same missions.
- **Weekly Deep Dives.** New every Thursday 11:00 UTC: a normal and an Elite variant, each three missions back to back. Each mission's core (a Blank Matrix Core, a weapon Overclock, a cosmetic) can be earned **once per variant per week**. Repeating still pays XP, credits and minerals. The dive is "pre-seeded", and the community treats it as the same for everyone that week.
- **The lesson.** Weekly-capped special rewards on top of uncapped base pay. Doing it again is allowed but only pays the ordinary rate, so there's no reason to grind it.

### Terraria: the Angler

- One fish quest per in-game day, per character, with milestone rewards. See [Terraria NPCs](terraria-npcs.md#services-not-items). It sends players to particular biomes and heights, which is its strength (steering) and its common complaint (RNG on the requested fish). *Unverified:* complaint sources weren't gathered for this note.

### Animal Crossing: New Horizons, Nook Miles

- **Two layers.** One-time tiered achievements ("Catch 10 fish" 300 miles up to "Catch 5,000 fish" 3,000) and renewable **Nook Miles+** tasks.
- **Nook Miles+.** A small set of everyday tasks (catch 5 fish, 150 miles; catch a specific bug, 200–500 by rarity; talk to 3 neighbours, 200; chop wood 10 times, 150; seasonal ones like building a snowman). Per resident. According to guides (not Nookipedia), there are 5 tasks at a time, a finished task is replaced by a new random one, **the first five finished each day pay double**, and some tasks roll a ×5 bonus. *Unverified:* the exact count and multipliers.
- **Shape.** Uncapped, but the daily bonus front-loads the value, so a short session gets most of it. Miles buy tickets to resource islands, recipes, furniture and storage upgrades. Nookipedia notes a meta-achievement for finishing Nook Miles+ tasks ("But where will it end?").

### Sea of Thieves: Trading Company voyages

- **Voyages.** Picked at the Captain's Table and voted on by the crew (majority; solo starts at once). One active voyage at a time, up to four proposed. Since Season 11 voyages are **free**; before that they were bought from company representatives with gold. Higher company reputation unlocks harder voyages that pay more. Rewards are "generally static and do not change based on crew sizes".
- **Shared by design.** The whole crew does one voyage together, and loot is sold for gold and reputation.
- **Emissary.** Raising a company's flag (from rank 15) multiplies company loot by Grade (Grade 2 +33% up to Grade 5 +150%). Grade rises through voyages, first pickups and world events, not through selling. **Sinking loses the flag**; lowering it voluntarily pays 750 to 5,000 gold by grade. A monthly Ledger ranks Emissaries into tiers for cosmetic sails and titles.
- **The lesson.** Risk-backed multipliers: income rises the longer you keep going without failing, and failing resets it. Rewards are cosmetic at the top.

### World of Warcraft and Destiny 2 (for complaints)

Not asked for, but the clearest developer statements on daily-task fatigue.

- **WoW dailies.** Ion Hazzikostas on Mists of Pandaria's dailies (2013): "it's still started feeling a bit repetitive and players at times would feel obligated to do them". In 2015 he called going from too many dailies in Pandaria to none in Warlords a failure and said a middle ground was needed. (Quotes from interview excerpts; full interviews not read.)
- **WoW emissaries (Legion and Battle for Azeroth), the middle ground.** One new emissary a day, each lasting **3 days**, three slots. Each asks for 4 world quests (sometimes 3) for a faction and pays 1,500 reputation plus a cache. Missing a day or two costs nothing.
- **Destiny 2 bounties.** In January 2021 Bungie replaced weekly bounties with Seasonal Challenges, which stay open all season so players can catch up (coverage framed it as removing FOMO and "bounty fatigue"). In 2024, with The Final Shape, bounties, patrols and weekly vendor challenges were merged into "Pathfinder", available "anytime, anywhere". *Unverified:* Bungie's own wording (the bungie.net article text couldn't be retrieved; this is from secondary reports).

## Design writing on fetch-quest tedium and steering

- **Gathering quests need a motive that fits the character.** Davide Aversa (Game Developer, 2019): gathering quests are "a cheap, uninspired, way to make feel the player busy", but they work "if the gathering is part of the emotional experience that the player expects" (Stardew, My Time at Portia), and otherwise should be "completely optional". Better than "kill 10 goblins": put something the player wants in a goblin outpost.
- **Obligation is the problem, not repetition alone.** Hazzikostas's "feel obligated" and Bungie's catch-up challenges point the same way. Daily tasks that expire teach players to log in for a checklist. Banking (WoW emissaries' 3 days, Destiny's season-long challenges, DRG's weekly window) removes the penalty for missing a day.
- **Only ask for what the player can reach.** Stardew never asks for an unreached fish, mineral or monster. This keeps tasks fair and turns new requests into signs of progress.
- **Fresh work, not stockpiles.** Stardew's Gathering and Special Orders, and Village Quest's literal verbs, count only items gathered after accepting. That stops a board from becoming a sell-from-chest exchange, which is exactly what went wrong with Emerald selling.
- **The game-design-101 lenses.** A board is a **tap** ([§4](game-design-101.md#4-economies-video-5)); where its rewards point is where players go. A tap that multiplies with the number of boards is a broken tap. Steering works best by rewarding what you want rather than punishing the rest ([§6](game-design-101.md#6-steering-player-behaviour-video-10)): Monster Hunter's gold slots for harsher conditions and DRG's Warnings that pay more are "encourage, don't forbid" applied to quest choice.

## Relevance

What transfers to a shared 5–10 player server where one player may run several villages and the board is the main Emerald tap.

- **Cap income per player, not per board.** Bountiful has no per-player cap, so its income scales with the number of boards a player can reach. That's the "mine, sell, buy" failure again, now as "build villages, farm boards". Village Quest's model is the fix: the **Daily allowance belongs to the player** (one per reset day) wherever they take it. For us: each player gets a fixed number of paid completions per day (or per week, banked) across all villages. Extra villages add **choice** (more tasks to pick from, different themes), not more Emeralds. Owning several villages should widen variety, not multiply income.
- **Bank, don't expire.** Use WoW emissary-style banking: unused allowance accumulates for a few days (say up to 3), so a player who misses a weekend isn't behind and nobody feels obliged to log in daily. This matches vision.md's worry about slower players drifting away.
- **Shared board, personal copies, shared village credit.** Bountiful's model fits: everyone sees the same tasks at a village; taking one doesn't remove it for others; completions raise the **village's** standing (Bountiful pools all players' turn-ins into board reputation). That serves "progress is shared by building and giving": helping a friend's village improves that village for everyone, while each player's pay stays personal and capped. Bountiful's reputation discount (smaller objectives at higher rep) is a ready model for how a village's standing could pay off.
- **Group tasks need a shared shape.** Clearing an outpost or defending against a roaming band are group-sized. Follow Sea of Thieves: one task, done together, with everyone present paid (and each counting against their own allowance). Don't split one reward between participants, or helping becomes a loss.
- **Refresh cadence.** Three layers work well together:
  - **Small daily tasks** (Stardew Help Wanted, Village Quest Dailies): quick, one or two objectives (Villager Quests' fix was "1-2 objectives maximum"), refreshing at a fixed server time.
  - **Bigger weekly orders** (Special Orders, DRG Deep Dives): a few choices every Monday with a week or more to finish, paying permanent unlocks.
  - **Continuous trickle** (Bountiful's 45-second refresh) only makes sense with a per-player cap. Otherwise it's an always-on tap.
  - Fixed-time refreshes are easy to talk about on a friend server ("the board flips at 6").
- **Task types that send players to unused content.** Steer with the board, as Stardew requests a seasonal fish:
  - **Fishing:** a named fish or a treasure catch.
  - **Brewing:** deliver a particular potion. This is the only reliable way to make brewing earn anything.
  - **Trial Chambers and Bastions:** bring back a Trial Key, Ominous Bottle or Breeze Rod; a Bastion item or a Piglin barter result; explore a named structure.
  - **Outposts and roaming bands:** the requested combat.
  - **Cartography and exploration:** visit a biome, map an area.
  - **Building:** furnish a house in the village (ties into Settlements and Prosperity).
  - **Breeding, cooking and farming** with crops the village doesn't grow.
  - Rules: only request what's reachable (Stardew's rule). Weight the pool towards activities the playtest log shows are underused. Always offer at least two choices, so nobody is forced into a disliked activity (Village Quest dropped combat dailies; "no set is locked behind an activity a player might dislike" is already our gear principle).
- **Fresh work, never stock.** Require gather, craft or catch **after accepting** for resource tasks (Stardew Gathering, Village Quest's literal verbs), or the board becomes a raw-ore exchange again. Delivery tasks should avoid farmable bulk goods (the Super Awesome Villagers notes already list which inputs farms trivialise) and favour things that cost a trip.
- **Put the difficulty dial on the card.** Monster Hunter's gold slots and DRG's Warnings: harder variants (more mobs, a time limit, no armour) pay more or pay rarer items. That gives skilled players a reason to choose risk without raising everyone's income ceiling much.
- **Reward shapes beyond Emeralds** (vision.md wants no single universal currency):
  - **Permanent unlocks** (Special Orders): a trade or service, a building blueprint or a recipe for that village.
  - **Village standing or Prosperity** (Bountiful reputation, Village Quest Projects): the village gets better for everyone.
  - **A side currency for one shop** (Qi Gems, Village Quest's Crowns): a Collection or cosmetic shop that can't be exchanged into gear.
  - **Rare materials** with a weekly cap on the special part (DRG cores).
  - **Cosmetics and titles** at the top (Sea of Thieves' Ledger).
  - **Meta milestones** (Stardew's every-3 Prize Ticket, Animal Crossing's "complete 50 tasks") for players who like ticking things off.
- **The daily salary.** Precedents for "you did a lot for this village, here's a daily wage": the Terraria Tax Collector (capped, collected in person) and Village Quest's bounded services. Make it **capped and collected at the village**, and count it against the same per-player total as the board, or a player with five villages gets five salaries. One option: salary only from the player's highest-standing village, or a single salary that grows with total contribution across villages.
- **Readability.** Village Quest shows unlock requirements in green and red, and literal verbs in quest text; Bountiful shows a timer. Our board should say what counts (fresh vs carried), when it refreshes and how much of today's allowance is left. This matches the "learnable without a wiki" Idea.
- **Mod reuse.** Village Quest is LGPL code with All Rights Reserved assets on 26.3 Fabric; Bountiful is LGPL/GPL with an unreleased 26.1.2 port. Either could be read for implementation ideas (Bountiful's worth-matching in `BountyCreator.kt` is a compact, data-driven generator). Copying code needs a licence check, and neither matches our caps or reward rules as is.

## Sources

- https://modrinth.com/mod/bountiful and the Modrinth API (`api.modrinth.com/v2/project/bountiful/version`)
- https://github.com/ejektaflex/Bountiful (`dev` branch: `gradle.properties`, `common/src/main/java/io/ejekta/bountiful/config/BountifulConfigData.kt`, `content/board/BoardBlockEntity.kt`, `content/board/BoardInventory.kt`, `content/board/GlobalBoardData.kt`, `content/BountyCreator.kt`, `components/BountyInfo.kt`)
- https://kambrik.ejekta.io/mods/bountiful/ (`general/bounty-boards`, `general/reputation`, `general/decrees`, `advanced/generation`)
- https://modrinth.com/mod/village-quest and https://github.com/topasmusic/village-quest (`26.3/docs/wiki/`: `quest-reference.md`, `core-progression.md`, `prosperity-and-economy.md`, `faq.md`, `getting-started.md`)
- https://modrinth.com/mod/village-bounties
- https://modrinth.com/mod/villager-quests, https://modrinth.com/mod/villagerquests, https://modrinth.com/mod/village-quests, https://modrinth.com/mod/simple-quests, https://modrinth.com/mod/mca-quests
- https://stardewvalleywiki.com/Quests (read via a web.archive.org copy; the live site blocked automated access)
- https://monsterhunterworld.wiki.fextralife.com/Investigations and https://monsterhunterworld.wiki.fextralife.com/Bounties
- https://deeprockgalactic.wiki.gg/wiki/Deep_Dives, https://deeprockgalactic.wiki.gg/wiki/Hoxxes, https://deeprockgalactic.wiki.gg/wiki/Missions
- https://nookipedia.com/wiki/Nook_Miles, and guides via search (https://game8.co/games/Animal-Crossing-New-Horizons/archives/285213, https://www.imore.com/animal-crossing-new-horizons-nook-miles-guide) for the daily bonus
- https://seaofthieves.wiki.gg/wiki/Voyages and https://seaofthieves.wiki.gg/wiki/Emissary
- https://warcraft.wiki.gg/wiki/Emissary
- https://www.ausgamers.com/features/read/3244280 and https://blizzpro.com/2015/06/13/world-of-warcraft-qa-with-ion-hazzikostas-recap/ (Hazzikostas on dailies, via search excerpts)
- https://www.bungie.net/en/News/Article/50040 (TWAB 28 January 2021, not readable), https://gamesradar.com/destiny-2-hopes-to-remove-fomo-with-new-weekly-challenges-and-updated-bright-dust-payouts, https://www.bungie.net/7/en/News/Article/twid-05-23-24 (Pathfinder)
- https://consolepcgaming.com/valheim-reveals-patch-0-217-14-introducing-hildirs-request (Hildir's Request)
- https://www.gamedeveloper.com/design/game-design-discussion-gathering-quests-sucks (Davide Aversa, 2019)
