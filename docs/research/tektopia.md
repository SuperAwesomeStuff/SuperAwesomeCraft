# Research: TekTopia

Background for the Season 1 Villager rework and the Settlements design (Villagers move into player-built Settlements, Prosperity unlocks trades and services, Raids scale with Prosperity). A friend remembered Tango Tek's villager mod as "quite interesting", so this note records what it actually did. The YouTube playlist the friend pointed to ("What If Minecraft had MUCH Better Villagers? Videos made by Tango Tek [TekTopia]") confirms the mod is **TekTopia**. Gathered November 2026 from the CurseForge page and file list, the official TekTopia wiki on Google Sites (every Villager, Structure, Visitor and Mechanics page, the FAQ and the full version history; the price tables are images on those pages and were read from the images), the playlist itself (17 videos, with auto-generated subtitles pulled for 7 of them and descriptions and top comments for 2), and the community Tektopia Addons page. No source code is published, so nothing was checked against code. Anything marked *Unverified* couldn't be confirmed from these sources.

## What it is

- **A Forge mod for Minecraft 1.12.2 by Tango Tek.** The CurseForge summary: "A complete villager overhaul mod!" It advertises over 20 professions, 18 structures, a custom pathfinding system "that lets villagers travel long distances", male and female versions of every Villager, and a Necromancer who threatens the village.
- **Versions.** Only two files are on CurseForge: `tektopia-1.0.93.jar` (beta, August 15, 2019) and `tektopia-1.1.0.jar` (release, August 16, 2019). The wiki's version history runs from 1.0.16 (May 28, 2019) to 1.0.93 (August 14, 2019), all from the private Patreon beta. *Unverified:* what changed in 1.1.0; the wiki has no entry for it.
- **Licence.** All Rights Reserved. No source repository was ever published. We can't port, fork or reuse its code, only learn from its design.
- **Status: abandoned since August 2019.** In the release video Tango said he needed "a break for a while", that he had plans for a 1.14 port "but not for a while", and that a "full expansion" (a research system and advancement tree for the village, new enemies, villager quirks and personalities) would only happen "if TekTopia is popular and if it takes off". Neither happened. Comments on the release video asking for an update run from 2020 to 2026 with no reply. It still has about 3.9 million CurseForge downloads, and many modpacks are built on it.
- **Community add-on.** Tektopia Addons (by viezensj and sushiyman, MIT, source at `github.com/Sushiy/TektopiaAddons`, updated 2026) adds configurability and modded-item compatibility (modded crops, ores, foods, armour, shields; a Merchant who trades from a chest under the stall marker). It patches the closed mod rather than replacing it.

## The playlist

- **17 videos, July 2018 to August 2019.**
  - Four "What If Minecraft had MUCH Better Villagers?" episodes (July 24, 2018; October 26, 2018; December 24, 2018; February 18, 2019; 2 to 5 million views each).
  - Twelve "Dev Playthrough" episodes (from March 26, 2019), one per profession or structure. Tango builds a village in creative mode and fixes bugs between episodes.
  - The download announcement (August 16, 2019).
- **How it was built.** Tango started with the Lumberjack, Farmer, Blacksmith and Miner in the first episode. The Miner forced him to replace vanilla pathfinding with "hierarchical path finding", because vanilla mobs give up on long paths. He called this "a huge setback" and the reason the video took much longer than usual.
- **Beta test.** From late May 2019 (the Rancher episode) a private server for Patreon supporters ran the mod, with every player building their own village on one survival server. Servers were reset before release. Most of the version history comes from that beta.

## Founding a village

- **The Town Hall.** A village starts when a Town Hall structure marker goes in an item frame by a door. The Town Hall is the village's centre, and the village is a fixed square **120 blocks from the Town Hall marker in every direction** (240 × 240). The page says there's "currently no way to increase or decrease it". A low wall, borrowed from world border code, appears as you approach the edge. Mineshafts stop at the border.
- **Getting the first marker.** Craft one (the recipe is a shapeless image on the wiki; a third-party mirror gives Book, Emerald and 4 Gold Ingots, *Unverified*), or take over a naturally generated village. All natural villages are converted: they get a Town Hall and other markers, and their Villagers are rerolled into TekTopia professions (on a roll of 0 to 79: Farmer 0–39, Lumberjack 40–59, Guard 60–69, Bard 70, Cleric 71, Blacksmith 72, Druid 73, Nitwit 74–79, so half become Farmers).
- **Starter kit.** A Town Hall with no residents generates a one-time chest with a Storage marker, a Home 2 marker, a Farmer token and a Lumberjack token.
- **Two permanent vendors live in the Town Hall.** The **Architect** sells structure markers and the **Tradesman** sells profession tokens, both for Emeralds. They respawn inside if they leave.
- **Markers are bound to their village.** A marker only works in the village it came from. A marker won't validate if "another village too close to you" (FAQ). *Unverified:* the minimum distance between villages.
- **No ownership.** None of the sources mention owners, permissions or which player a village belongs to. *Unverified:* whether anyone could buy markers, trade or move Villagers in another player's village on the beta server.

## How buildings are recognised

- **Door, item frame, marker.** Any building becomes a structure when a door has an item frame above it or beside its top holding the right marker. The building is scanned, and the marker glows once it's valid. Placing a home marker colours the beds: yellow if valid, red if not, green once claimed.
- **General rules for every structure:**
  - At least 4 floor tiles, at most 500.
  - At least 2 blocks tall, at most 30 (raised from 15 during the beta).
  - A roof and at least one door.
  - The interior has to be on **one y-level**, with a solid floor at the door's floor height.
  - Furniture only counts if it stands on that floor. Stacked chests aren't recognised.
- **Specialty requirements are light.** A structure validates with just the basics, but "may not work as intended" without its blocks:

| Structure | Needs | Base price (Emeralds) |
| --- | --- | ---: |
| Town Hall | Nothing extra; chairs let the vendors sit | crafted |
| Storage | Many chests, one crafting table, one furnace. One per village | in starter kit |
| Home 2 / 4 / 6 | One bed per Villager (2 air above, a free side). Chairs add happiness | 6 / 13 / 20 |
| Smithy | Anvil, furnaces, a crafting table per Blacksmith | 30 |
| Kitchen | Furnaces, a crafting table per Chef | 18 |
| Butcher | At least 8 floor tiles, optional crafting table | 25 |
| Library | Bookshelves on the floor (one reader each); crafting and enchanting table for the Enchanter | 30 |
| School | Chairs | 35 |
| Tavern | Chairs, room to walk, optional note block for the Bard | 45 |
| Barracks | Up to 10 beds and armour stands. Guards only | 35 |
| Mineshaft | A 1 × 2 opening under the marker, inside the border | 3 |
| Rancher pens (Sheep, Pig, Cow, Chicken) | Fence gate instead of a door, no roof, one animal type each | 16 to 20 |
| Guard Post, Merchant Stall | Just an item frame on any wall | 2, 20 |
| Farmland | No marker; the village finds it | free |

- **The world is sampled, not designated.** Farms, sugar cane and trees aren't marked. Every tick the village samples a few random blocks; when it finds farmland it floods out to the adjacent farmland to learn the whole farm. Tango said this takes seconds to minutes. Farmers plant whatever crop is most common around a tile, so the player seeds a new field once to say what grows there, and unbordered fields keep spreading. Lumberjacks only fell trees whose trunk stands on dirt and don't break hand-placed leaves, so decorative trees are safe if they stand on another block.
- **Space as quality: overcrowding.** Storage, Smithy, Town Hall, Butcher, Library, School, Homes, Barracks and Kitchen want **12 floor tiles per Villager**. Furniture counts as floor. Below 12 a Villager loses up to 5 happiness per visit (full penalty at 6 tiles or fewer). Rooms with ceilings averaging under 2.5 air blocks also count as overcrowded.
- **Movement limits that shape builds.** Villagers can't swim or path through water (if they fall in, the player has to push them out), don't climb ladders, and handle one-wide, spiral or low-headroom stairs badly. A stuck Villager teleports to the nearest walkable pathing node.

## Villagers arriving and growing

- **Nomads.** Each morning a pack of Nomads may walk in from one of the village's four corners. The chance is 100% with fewer than two Villagers (every day with three or fewer, from 1.0.68), otherwise 1/√(villagers): 50% at 4, 33% at 9. A pack has at least one Nomad, and each further Nomad is a 50% roll, so packs are uncapped (a commenter reports 15 to 20 arriving at once). Visitors need a walkable path from the corner to the Town Hall.
- **Recruiting.** Using a profession token on a Nomad makes it a resident with that job. Nomads arrive with random intelligence, so players pick through them for good ones. A token can also re-job an existing Villager.
- **Children.** A Villager who sleeps with high happiness may drop a **Heart** near its bed, more likely the closer it is to 100 happiness. The player places the Heart on an unclaimed bed to spawn a Child. After 4 to 6 in-game days a Child becomes a Nitwit, who does nothing (eats, slowly loses happiness, makes no Hearts) until given a profession token.
- **Intelligence and skill.** Each Villager has a skill of 1 to 100 per profession, which grows with work and makes them faster and better. Skill grows easily while below the Villager's intelligence and slowly above it. Intelligence comes from School (Children, fastest, much faster with Enchanter-made books) and the Library (adults). A Nitwit converted to a profession starts at 50% to 100% of its intelligence as skill, so schooling Children pays off in high-skill workers.
- **No hard population cap.** The sources mention none. Growth is limited by beds, food, happiness and token prices.

## Professions and what Villagers do

Every job runs on a shared **Storage** building. Villagers deliver everything they make or gather there and fetch their inputs from it. Professions depend on each other in a loop: the Lumberjack needs axes from the Blacksmith, who needs wood from the Lumberjack and ore from the Miner, who needs food from the Farmer.

- **Farmer:** tills, plants and harvests wheat, potatoes, carrots and beetroot, and gathers sugar cane. Low-skill Farmers sometimes trample farmland back to dirt.
- **Lumberjack:** fells oak, birch, spruce and jungle (no acacia or 2 × 2 trees), keeps about one log in five, replants, and collects saplings and apples within 40 blocks.
- **Miner:** claims a Mineshaft, extends a 1 × 2 tunnel, scans up to three blocks around for ore and digs it out, fills gaps with cobblestone, covers lava, and places torches. He leaves if he meets water or lava, and the player has to clear it. He gets bonus ore at y = 11 rates from stone. He delivers once at the end of the day.
- **Blacksmith:** smelts ore, makes charcoal when there's no coal, crafts iron and diamond tools, weapons and armour, buckets, shears and torches. He prioritises diamond gear.
- **Chef:** cooks meat and potatoes and crafts bread, soup, cake, cookies, pie and golden carrots and apples.
- **Rancher:** wrangles animals into pens, feeds them, shears, milks and collects eggs, and returns strays.
- **Butcher:** leads animals from pens that are at least 75% full to the Butcher building and slaughters them. He crafts leather armour.
- **Enchanter:** makes paper and books and enchants gear and books, using Lapis from the Miner. Higher skill gives better enchantments.
- **Druid:** half the day bone-meals crops and saplings, half the day "Earth Reforms" the back of the longest Mineshaft, refilling it with stone and ore (3.5% ore at skill 1, 6% at 100). This makes mines renewable.
- **Cleric:** heals hurt Villagers, prioritising Guards in combat, and blesses Villagers, adding the Cleric's skill to theirs for 5 minutes.
- **Guard:** patrols (around Guard Posts if there are any), fights with a sword and a leaping area attack, picks up Emeralds, equips the best gear in Storage, and gains health with skill (20 to 35). With a Barracks, one Guard can become **Captain of the Guard**, whose Aura of Courage gives nearby Guards Resistance.
- **Bard:** plays for unhappy Villagers by day and in the Tavern at night. He loses happiness if too few listen.
- **Teacher:** runs School at midday for Children.
- **Fallback crafting.** Most workers craft their own wooden tool when nothing better is in Storage, which also costs them happiness. Each Villager has an AI tab where the player can switch individual tasks on or off (for example "Craft: Diamond Sword" or "Chop Tree: Birch"). Eating, sleeping and Storage trips can't be switched off.
- **Thought icons.** A Villager shows an icon over its head when blocked: no pickaxe, no torches, no bucket or shears, no feed, no sword, no hoe, no axe, no note block, no School, no bed, no food, overcrowded, insomnia, or "wants a Tavern".

## What Villagers need

- **Food (hunger).** Hunger drains with walking (a 1-in-50 chance per tick of losing 1), with every work action (for example 1 per block mined, 3 per crop harvested), and with crafting (scaled by skill). Villagers eat from Storage when hunger reaches 30 or less and starve without food. Foods restore very different amounts (Potato 7, Bread 55, Steak 80).
- **Happiness.** It decides how fast Villagers work (unhappy ones work "very slowly") and whether they make Hearts.
  - **Sources:** sleep, chairs at home, the Tavern, Bard music, reading, being healed or blessed, and good food.
  - **Losses:** routine work (a small, frequent chance of losing happiness for each job action), taking damage (−8), seeing a Villager die (−25 to −40, or −50 to −80 for a Child), overcrowding, and crafting their own tools.
  - **Food variety.** Each Villager remembers its last 5 meals, and repeats lose value (+5 for something new down to −18, floored at −3). Cake, golden carrots and pie are worth much more happiness than raw crops, which are slightly negative.
- **Sleep.** One claimed bed per Villager, always in the same home. Guards and Clerics sleep by day and stagger their shifts. Seeing enemies or being woken causes insomnia. Players can sleep through the night, but Villagers who aren't in bed yet skip that night's sleep.
- **"Villager items".** Anything made entirely by Villagers, from Villager-made inputs and with Villager-made tools and fuel, has a green name. Villagers prefer these. Non-villager food gives half the hunger and no happiness, and non-villager tools are treated as worse and have less durability. Animals fed Villager food breed "villager animals", which glow and give Villager products. Tango dropped the idea early in the dev series ("you can actually help your villagers"), then brought it back in 1.0.60. In effect it stops players feeding a village from their own farms or stocking it with their own gear.

## The economy

- **The Merchant.** A visitor who arrives every day from a village corner and buys goods for Emeralds. He only buys Villager items, so players can't sell their own goods through a village. His offers are rolled daily from a weighted table, and each one only appears if the village has the profession that makes it. Some base prices (Emeralds for the amount shown):

| Goods | Amount | Base | Needs |
| --- | ---: | ---: | --- |
| Logs (4 types), Wheat, Potato, Carrot, Beetroot | 64 | 4 | Lumberjack, Farmer |
| Bread / Cooked meats, Baked Potato | 16 / 32 | 5 (later 8) / 10, 4 | Chef |
| Eggs / White Wool | 16 / 64 | 2 / 7 | Rancher |
| Redstone / Lapis / Diamonds | 64 / 16 / 4 | 5 / 5 / 12 | Miner |
| Gold Ingots | 16 | 12 | Blacksmith |
| Iron tools, armour | 1 | 4 to 10 | Blacksmith |
| Diamond tools, armour | 1 | 8 to 20 | Blacksmith |
| Leather armour | 1 | 2 to 3 | Butcher |
| Paper / Books | 16 / 8 | 3 / 8 | Enchanter |

- **Dynamic prices.** The Merchant tracks the last 30 items sold. Selling an item lowers its price and raises the others, so the wiki says "a well diversified village is key". A Merchant Stall adds two extra offers that day, and small villages get one quality-food sale a day.
- **Emerald sinks.** Structure markers (2 to 45) and profession tokens (Farmer, Lumberjack, Rancher and Captain 3; Miner and Butcher 4; Guard and Blacksmith 5; Chef 7; Teacher 8; Cleric and Druid 12; Bard and Enchanter 15). **Every 5 purchases, all marker and token prices rise permanently.** By mid-series Tango was paying 56 for a School (base 35) and 77 for the Barracks. *Unverified:* the wiki says the count is of "profession tokens", but both vendor pages say it raises both price lists, and whether marker purchases count isn't clear.
- **Other Emerald sources.** Necromancer drops, Miners finding Emerald ore, and a few in natural village chests.
- **What the player gets out of it.** The loop is closed: Villagers make goods, the Merchant turns them into Emeralds, Emeralds buy more buildings and workers. The player's payoff is a growing, self-running town to watch and design, plus whatever the village produces: Diamond gear, enchanted books and gear, food, ore, logs. Players can take these from Storage. *Unverified:* whether players on the beta routinely took gear or ore from Storage for themselves. Tango kept a hidden personal chest for gold to sell. There's **no town level, no Prosperity score and no unlock tree**: anything can be bought from day one if you have the Emeralds. The research and advancement tree was only planned for the expansion that never came.

## Threats and defence

- **The Necromancer** is the only raid. Every 3 minutes there's a chance one spawns at a village corner: villagers ÷ 10 percent, plus 2 points at night (20 Villagers: 2% by day, 4% at night). His level (1 to 5, shown by robe colour) depends on population, and **rotating the Town Hall marker opts the village out** of Necromancers.
- **What he does.**
  - Takes over undead he meets and summons more (zombies at night, husks by day), up to twice his level within 30 blocks. They walk ahead of him.
  - Is immune to damage while he controls minions.
  - Soul Links animals into orbiting shields.
  - Casts a Wither circle at players.
  - Stops using abilities once any Villager dies.
  - Minions die with him. He drops 4 Emeralds per level.
  - *Unverified:* whether raids break blocks. The pages don't say, and the minions are vanilla zombies.
- **Defence.** Guards (helped by Clerics), Iron and Snow Golems, Guard Posts to station Guards, and lighting. Villagers flee, call Guards and lose happiness from damage and deaths, and Villager deaths are announced in chat with profession and location.
- **The Guard catch-22.** Guards level by fighting, but a well-lit, safe village gives them nothing to fight. Tango added the Barracks in the last dev episode so Guards could train on armour stands, "not as good as fighting real zombies" but a way to level.

## What was fun and what was frustrating

- **The appeal.** Tango's pitch was "a living breathing town that it's just fun to sit back and watch", where the player is "mayor and chief architect": you design the buildings in any style and lay them out to suit the Villagers' routines. Commenters call it a first-person RTS. The videos have millions of views, and the release comments are overwhelmingly positive, with years of requests for a port.
- **What commenters asked for.** A town overview (population, free beds, buildings, professions; "you should add a population counter in the town hall"), clearer feedback when a building validates (glowing markers were the answer), a cap on Nomad packs, and an archer, fisherman, builder and horses.
- **Tango's own problems in the videos:**
  - **Pathfinding.** The biggest setback; he replaced vanilla's.
  - **Bugs.** Villagers teleporting onto roofs when passing through doors; Villagers stuck in water.
  - **Layout.** He placed the Town Hall without realising it fixes the 120-block square, so later buildings were at the edge. He also admitted to "mismanagement" of dead space and advised a compact village, "depends if you want to go for efficiency versus looks".
  - **Balance churn.** A skill-capped-by-intelligence change dropped every Villager's skill to near zero. Diminishing prices were briefly broken, which gave too many Emeralds.
- **Performance.** The mod claims its pathfinding has "very minimal CPU overhead". Version 1.0.23 rewrote item pickup and delivery to be "MUCH more friendly on the server (no more constant storage room inventory scans)". Villagers stop losing hunger when Storage isn't loaded. *Unverified:* any measured server cost or player reports of lag. Reddit couldn't be reached, and the hosting pages that came up are vendor material.
- **Why it ended.** Tango was burnt out after more than a year of work, the expansion was conditional on popularity, and he went back to one-off What If mods. No later statement was found. *Unverified:* any public explanation for never updating.

## Relevance for SuperAwesomeCraft

- **The structure system answers our open question.** [vision.md](../vision.md) asks: "How is Settlement prosperity measured without dictating how people build?" TekTopia's answer is a cheap, readable contract: door + roof + one floor level + a marker in an item frame, plus a few required blocks per building type (beds, furnaces, an anvil, chairs, bookshelves). Inside that, style, blocks and size are free. It gives clear feedback: the marker glows, beds turn yellow, red or green, and thought icons show what's missing. That's a good model for "Villagers live in and use what players build". The weak points to avoid:
  - **Single-level interiors.** No multi-storey buildings as one structure. On a builder-heavy server this will chafe.
  - **No water or ladders, poor stairs.** These silently dictate how people build.
  - **A fixed square around a Town Hall.** It punished Tango's own layout.
- **Floor space per Villager is a reusable quality signal.** The 12-tiles-per-Villager rule (furniture counts, low ceilings penalised) rewards generous building without prescribing style. It's a candidate input for Prosperity alongside "is each building type present and used".
- **The world-sampling trick fits a server.** TekTopia discovers farmland, sugar cane and trees by sampling a few random blocks per tick and flooding outward, instead of scanning whole areas. That's cheap enough for a shared server and needs no marking. It's worth copying for anything that measures what players have built.
- **Watch out for Villagers that produce raw progression resources.** TekTopia's Miners dig Diamonds, Druids refill mines, Blacksmiths make Diamond gear and Enchanters enchant it, all automatically. On our September 2026 playtest, Villager trading already collapsed every route into one activity ([vision.md](../vision.md)). A Settlement that mines Diamonds for players would do the same through automation. On our server Villagers should provide **services and convenience** (trades, food, repair, travel, storage) and turn player-supplied materials into things, rather than generating Era resources. This also keeps the rule that Villagers never sell Era materials.
- **"Villager items" is a clean anti-loop tool we already reinvented.** Super Awesome Villagers marked Villager-harvested crops "Haram" so they couldn't be sold ([super-awesome-villagers.md](super-awesome-villagers.md)). TekTopia does the inverse: only fully Villager-made goods count for its economy. As a mod we can tag provenance properly (a component, not a rarity hack). Decide per system which direction we need: stopping Villager output being sold, or stopping player output being laundered through Villagers.
- **TekTopia has no progression gate; we want one.** Its unlocks are just Emerald prices with inflation every 5 purchases. That limits pace but gives no sense of a town "levelling up", and the planned research tree never shipped. Our Prosperity-unlocks-trades-and-services idea fills the gap TekTopia left. The inflation curve is still a useful fallback for stopping one player buying out the town in an afternoon.
- **One shared Settlement, not one village per player.** The beta ran one village per player on one server, and markers are bound to a village with a minimum spacing. For 5 to 10 friends with "a central Settlement worth living in or near" ([vision.md](../vision.md)), the TekTopia shape is one Town Hall that everyone contributes to. That needs things TekTopia never had: shared ownership, a town overview screen (the most-requested missing feature), and a record of who built or supplied what, which the playtest log could capture.
- **Needs: keep them visible, few, and not a chore.** Hunger, happiness, food variety, sleep, schooling and intelligence made TekTopia deep to watch but heavy to manage, and much of the dev series is Tango fixing starving, sleepless or stuck Villagers. On a shared server, needs that fail quietly while players are away become nobody's job. Clear thought icons and a few readable needs (a bed, a workplace, food from the Settlement) are worth copying. Per-Villager intelligence, skill and variety maths are probably not.
- **Raids: borrow the scaling and the opt-out, fix the dead-end.** The Necromancer's spawn chance and level scale with population, which matches "Raids scale with Prosperity". Rotating the Town Hall marker to opt out is a simple, physical "not tonight" switch worth keeping for a group that isn't always online. The Guard catch-22 is a warning: if defenders only improve by fighting, a safe Settlement never improves them. Our design already gives fighters a reason to show up, but defence growth shouldn't depend on raids happening often.
- **Population growth from happiness (Hearts) is a nice visible reward.** Happy Villagers drop a Heart, and the player places it on a free bed. It turns "build good homes and a Tavern" into "the town grows", and the player chooses when. It fits Prosperity better than vanilla breeding.
- **What won't transfer:**
  - **The code.** All Rights Reserved, closed, Forge 1.12.2.
  - **The custom pathfinding.** A huge investment; on 26.3 we should design buildings and routes that vanilla pathing can handle, or keep Villager trips short.
  - **Daily corner-spawning visitors.** They fail when the path is blocked by water or terrain.
  - **The creative-mode, watch-it-run focus.** Our players want goals and progression ([vision.md](../vision.md)), so the Settlement has to feed back into what players do, not just be a diorama.

## Sources

- https://www.youtube.com/playlist?list=PL0bJ23gjvWtaCUIi6dU1P0mDP-ORyMMW8 (playlist title, video list, dates and view counts via yt-dlp)
- Auto-generated English subtitles for: "What If Minecraft had MUCH Better Villagers?" https://www.youtube.com/watch?v=GK6Vr9mcgjM ; "Dev Playthrough: The Farmer!" https://www.youtube.com/watch?v=2FJ_5wSBGRg ; "The Rancher + BETA TEST!" https://www.youtube.com/watch?v=R_W6-8S2y4Y ; "Butcher, Necro, and Library!" https://www.youtube.com/watch?v=MVnRBLC0WHQ (the available track was a machine translation and reads poorly) ; "Teacher and Enchanter!" https://www.youtube.com/watch?v=kDqAzeZZ7is ; "New Structure! The Barracks!" https://www.youtube.com/watch?v=lsEYyylriWA ; "TekTopia Villager Mod Download!" https://www.youtube.com/watch?v=jYsSNB8OY0I
- Descriptions and top comments for https://www.youtube.com/watch?v=jYsSNB8OY0I and https://www.youtube.com/watch?v=Xv_dhMQv1ME ("Dev Playthrough: Getting Started!")
- https://www.curseforge.com/minecraft/mc-mods/tektopia and https://www.curseforge.com/minecraft/mc-mods/tektopia/files
- TekTopia wiki, https://sites.google.com/view/tektopia : `home/getting-started`; `home/structures` and its subpages (`town-hall`, `storage`, `homes`, `farmland`, `mineshaft`, `Blacksmith-structure`, `kitchen`, `butcher`, `library`, `school`, `tavern`, `barracks`, `guard-post`, `merchant-stall`, `rancher-pens`); `home/villagers` and every profession subpage; `home/visitors/merchant` (trade table image), `nomad`, `necromancer`; `home/mechanics/hunger`, `happiness`, `intelligence-and-skill`, `natural-village-generation`, `profession-ai`, `villager-items`, `thought-icons`, `overcrowding`, `developer-commands`, `spawning-mechanics`; `home/villagers/architect` and `tradesman` (price table images); `home/guides/faq`; `home/guides/crafting-recipes`; `home/about/version-history`
- https://moddex.gg/mod/tektopia-addons and https://github.com/Sushiy/TektopiaAddons (community add-on)
- https://www.9minecraft.net/tektopia-mod/wiki/ (third-party mirror; only used for the *Unverified* Town Hall recipe)
