# Research: Colony Survival

Background for the Settlements design (Villagers move into player-built Settlements, Prosperity unlocks trades and services, Raids scale with Prosperity and never break blocks). The designer played Colony Survival and liked recruiting colonists as archers and other guards to defend the colony, so this note records how its guards and night attacks actually work, how they changed over nine years of Early Access, and what players say about them. Gathered November 2026 from the Steam store page; the developers' own Steam announcements (the weekly "Friday Blog" and update posts by Pipliz, read through Steam's news feed); the community Colony Survival wiki on Miraheze (which the old wiki says most maintainers moved to, and which has current recipe and patch-note pages up to 0.18); the older Fandom wiki (mostly marked outdated, used only for mechanics that haven't changed and for history); two Steam discussion threads; and the top-rated negative and most recent positive English Steam reviews (10 each, the only ones the review pages would load). Reddit wasn't searched successfully and Steam's review API refused automated requests, so player opinion is a small sample. Anything marked *Unverified* couldn't be confirmed from these sources.

## What it is

- **A first-person voxel colony builder by Pipliz** (two Dutch developers, a designer and the programmer Zun). Steam calls it "a first-person strategy game with voxel-based colony building": you recruit colonists such as guards, farmers and miners, and "defend against monsters that attack every night". Early Access since 16 June 2017, still Early Access in November 2026. 91% positive across about 6,000 English Steam reviews.
- **The player is a mayor, not a worker.** The player can mine and place blocks, but production is done by colonists. The wiki's Player page jokes that the player "telepathically manipulate[s] colonists into doing their work". The colony's stockpile is one infinite inventory, reachable from anywhere near the colony and shared by every crate, so there's no hauling.
- **Version history that matters here:**
  - 0.4.0 (October 2017): several monster types and the guard tiers.
  - 0.6.x (2018): fixed monster counts per night, the builder and digger.
  - 0.8.0 (November 2020): Happiness replaced by Colony Points.
  - **0.9.0 (22 December 2022):** "the largest update we've ever released". New tech tree, Threat Level, outposts, traps, new guards. Old worlds are incompatible.
  - 0.10 to 0.18 (2023 to August 2026): doors, fences and rails, top-down view and blueprints (0.15), decoration (0.16), quests and a players list (0.17), split screen and "request to join" a colony (0.18).
- **Multiplayer.** Co-op servers from the main menu or a dedicated server. Steam: friends can be invited to a shared colony, or "players can run their own colonies and trade items and science". In 0.18, clicking another player's banner offers to request joining that colony. The colony menu has "Manage Owners" and "Diplomacy" tabs. *Unverified:* what owners and diplomacy rules actually allow; the wiki doesn't say.

## Founding a colony

- **A banner starts it.** The Banner Tool places the banner anywhere there's room, outside other players' colonies. The banner is the colony's heart: colonists spawn on it, the player respawns at it, and monsters walk to it.
- **Safe zone: a cube 30 blocks in every direction** from the banner, which Colony Points can raise to 100 (the last step costs 100,000 points). Monsters never spawn inside it and spawn outside it at night. The banner tool shows the zone as green and the spawn area as red.
- **Starting kit.** The banner comes with beds and a Mill (a crafting block for the player). The Fandom wiki's older guide lists 12 beds, 3 crates, 20 torches, 20 berry meals and 200 planks. *Unverified:* the current kit's exact contents.
- **Outposts (0.9.0).** An Outpost Banner, bought for 1,500 Colony Points after the Outposts research, founds an outpost that shares the main colony's stockpile and science. Outposts are needed: late-game resources only grow in swamps, heaths, mountains and under mammoth trees.
- **A truly separate second colony** needs a Colony Kit, craftable only after all five Prestige researches, the very end of the tech tree. It has its own stockpile, threat and research.

## Guards

### How a guard is made

- **A guard is a job block, not a unit.** Each guard type is a block ("Orders a colonist to guard this position") crafted by a colonist from the weapon itself. The player places it where the guard should stand, and an unemployed colonist automatically takes the job. Recipes from the Miraheze wiki (0.9+):

| Guard | Job block made at | Job block recipe | Weapon recipe | Ammo recipe | Unlocked by |
| --- | --- | --- | --- | --- | --- |
| Slinger | Tinkerer | 1 Sling | nothing at all | 1 Stone Rubble → 10 Sling Bullets | start |
| Bow | Fletcher | 1 Bow + 1 Leather | 3 Planks + 2 Flax | 1 Copper Ingot + 2 Planks → 10 Copper Arrows | Archery (1 Copper Tools, needs Anvil) |
| Crossbow | Fletcher | *Unverified* | 1 Bow + 1 Leather | *Unverified* | Crossbows |
| Musket | Advanced Job-block Crafter | 1 Musket + 1 Steel Ingot | Steel Parts + Brass Precision Parts + Vial of mineral oil | 2 Lead Ingot + 2 Gunpowder → 25 Musket Ammo | Musket |
| Handcannon | Gunsmith | 1 Handcannon + 2 Wrought Iron | *Unverified* | *Unverified* | Handcannon |
| Grenade Launcher | Gunsmith | 1 Grenade Launcher + 1 Steel Ingot | *Unverified* | *Unverified* | Grenade Launcher |
| Poison | Job-block Crafter | 1 Planks + 1 Hemp Fibre + 1 Wisteria Flower | n/a | Poison Darts | Poison |

- **Recruiting the colonist is a separate cost.** Every colonist needs a free bed and costs meals to recruit (the older wiki says the first 10 are free and then 4 meals each). After that each colonist eats one meal a day. Guards fetch their meal from a Grocery Store at the end of their shift. They don't use tools.
- **Day and night variants.** Every guard type has a Day and a Night job block. Night guards sleep by day. The wiki pages disagree on exact hours (night shift roughly 17:00 to 08:00, day shift roughly 04:30 to 19:00), so shifts overlap at dusk and dawn. A full day is 12 real minutes, about 7.5 of day and 4.5 of night.

### How a guard fights

- **Stationary, ranged, automatic.** "When assigned, they will remain stationary at their post. If a zombie comes within their radius, they will turn and shoot it." Range is a sphere, so guards shoot up and down from towers. They need line of sight from their head to the monster's head.
- **Ammo comes straight from the stockpile.** Guards are "the only job that does not need be near a crate". Each shot uses one round from the colony's shared stockpile. No ammo, no shooting.
- **Target choice is built in.** Guards go for the strongest monster they can kill in one shot, so a musket guard doesn't waste a shot on a 25 HP monster when a 1,500 HP one is in range.
- **Stats (0.9+, both wikis):**

| Guard | Range (blocks) | Damage | Reload (s) | Max ammo per shift |
| --- | ---: | ---: | ---: | ---: |
| Slinger | 12 | 25 | 3 | 150 |
| Bow | 18 to 20 | 75 | 5 | 90 |
| Crossbow | 25 | 200 | 8 | 56 |
| Musket | 30 | 500 | 7 | 64 |
| Grenade Launcher | 18 | 250 in a 3-block radius | 12 | 38 |
| Handcannon | 10 | 1,300 | 10 | 45 |
| Poison | 10 | 1,800 over 45 s | 12 | 38 |

  The two wikis give the Bow 18 and 20 blocks. Poison guards only fire at monsters with at least 1,000 HP left. The short range of the strongest weapons forces them close to the path, behind longer-range guards.

### How strong guards are compared to the player

- **The player uses the same weapons.** Any guard weapon works for the player with its ammo in the hotbar, at the same damage. Players also punch (35 damage when it was added in 2017). Player health starts at 100 and Colony Points can raise it to 200.
- **So a guard is roughly one player-with-a-gun that never gets bored.** The difference is numbers: colonies run dozens to hundreds of guards. One negative review says "You don't get a weapon to use other than clicking the zombies", which shows that personal combat matters little and that some players never found the weapons.
- **Guards are fragile.** Monsters attack the nearest colonist, guards included, and the older wiki warns they can be "quickly" killed at night. Players can also shoot their own colonists by accident.

### Other defences

- **Traps (0.9.0).** Static blocks that fire at monsters in front of or below them: Dropper (500 damage), Gunpowder (750 in a radius; hurts colonists too), Projectile (2,500, only at monsters of 1,000+ HP), Cannon (12,500, same trigger), Caltrops (slow to 60%) and Rope (freeze for 10 s). A **Trap Fixer** colonist reloads traps within 50 blocks, **only by day**, so traps must be reachable from inside the colony. The devs proposed traps because they "solve the player-control-over-prioritization problem" and give a last line that tells you clearly when guards aren't enough (Blog 206).
- **Sacred Failsafe (0.9.0).** Colonists at a Sacred Altar turn meals into Sacred Meals, which earn Sacred Points when eaten. When monsters breach the defences, the Failsafe spends the points to deal "large amounts of damage to all monsters in the world, starting with the monsters it's physically closest to". In the devs' words it's for when "you run out of copper ingots, your ammo storage is depleted and monsters walk right into your colony".
- **Alarm Bell.** Rings when a monster walks over it.

### The defence loop: walls, mazes and siege mode

- **Monsters never break blocks.** The devs considered wall-breaking monsters and gates in 2019 and rejected both (Blog 95):
  - A "dumb" wall-breaker would make monsters "akin to termites" and leave spreading guards around the perimeter as "the only valid solution".
  - A smart siege AI that reads any player-built fortress is "bordering on the impossible".
  - Gates would need queuing, which their movement model doesn't support (monsters and colonists walk through each other), and players would spam them ("a maze with 50 gates").
- **Doors and fences count as walls.** Since 0.10 monsters also use doors, and fences can't be walked over.
- **There must always be an open path to the banner.** If walls fully enclose the colony, **Siege Mode** starts. Colonists then eat straight from the stockpile at about 10 times the normal rate (0.4 meals per hour instead of about 0.04). Since 0.9.1 the penalty scales with the outpost's threat. The rule turns "wall everything off" into "leave one entrance and defend it".
- **So the standard layout is a funnel or maze.** Walls at least 2 blocks high or a ditch 2 deep, one entrance, a long winding path past towers of guards, and traps along it. The wiki tips: "A winding maze that goes back and forth in front of the guardsmen before going to your flag".
- **The maze has been patched against several times.**
  - In 2017 monsters died at sunrise, so a long enough maze needed no guards at all. One Steam player needed "15 layers of 20 long path to last me all night". The devs removed death at sunrise and stopped spawning a few minutes before dawn.
  - In 2018 (0.6.x) a fixed number of monsters spawns per night, because killing them near the spawn just respawned them. "All colonies with the same colonist count will encounter the same amount of monsters, regardless of path length and guard placement."
  - Very long mazes (a few hundred blocks) break monster pathing and trigger siege mode by themselves.
  - Monsters only turn aside to attack guards or colonists if the path to them is shorter than about 100 blocks (2018). Otherwise they head for the banner.
- **The player's role is architect and quartermaster.** You design the funnel, place guard posts and traps, keep the ammo and food chains running, and upgrade weapons before the next jump in threat. You can fight, but you don't have to. A developer replied to a "the zig zag thing is dumb" complaint: "You're meant to have fun, if you're having fun, you're playing it the way it's meant to be played :)"

## Monsters and how attacks scale

- **One enemy type in six health tiers.** Zombies, named by health on the current wiki. Bigger ones are slightly slower and hit harder:

| Monster | HP | Speed (blocks/s) | Punch damage | First appears at Threat |
| --- | ---: | ---: | ---: | ---: |
| Grey | 25 | 2.0 | 30 | 1 |
| Green | 100 | 1.95 | 40 | 39 |
| Red | 250 | 1.9 | 60 | 79 |
| Armored | 1,500 | 1.85 | 75 | 167 |
| Strong | 5,000 | 1.8 | 75 | 353 |
| Armored Strong | 25,000 | 1.7 | 75 | 526 |

  An Armored Strong zombie takes 334 bow shots or 50 musket shots, so each tier makes the previous weapon obsolete. Monster numbers and type thresholds come from community testing. *Unverified:* the exact spawn-count formula.
- **What they target.** The banner, colonists and players, whichever is closest (subject to the 100-block rule). "If a zombie reaches the banner, random colonists will begin to take damage", and they keep attacking until the colonists are dead. Zombies don't despawn at dawn, so stragglers still arrive by day.
- **Monsters only start after 25 colonists** (current wiki).
- **Threat Level (0.9.0) decides how many come and how strong they are.** It's shown in the top-right corner, and comes from:
  - **Colonists:** 0.3 threat each above 25, rising per colonist (formula unknown). A 100-colonist colony with no research has about 22.5.
  - **Research milestones:** printed under each research, permanent. Some examples: Merchant's Hub +10, Anvil +30, Bronze Age +50, Iron Age +100, Gunpowder +50, Metal Lathe +75, then Prestige I to V at +250, 350, 500, 750 and 1,000.
  - **Some blocks, while placed:** Colony Point lockboxes +3 to +25 each, the Sacred Failsafe +10.
- **Why they changed it.** Before 0.9 monster count tracked colonist count directly. The devs' reasoning (Blogs 154, 158 and 203):
  - It punished growth and inefficient play. "If you recruit a bunch of colonists but you don't put them to work efficiently, you'll have made the game quite difficult", the Skyrim levelling problem.
  - Efficient players, meanwhile, "could reach the endgame with a pretty small number of colonists".
  - Players who built "a beautiful colony instead of a hyper efficient one" were the ones hurt.
  - Guards and ammo crafters raised the very threat they fought ("a vicious cycle").
  - Tying threat to milestones lets the devs "determine the pace" and makes defence "preparing for a big event" instead of "small improvements to your defenses every couple of nights".
  - An earlier idea (2018) was a visible threat level that creeps up a point a day at a fixed population, and could be lowered by late-game rituals. Only the visibility shipped.
- **Threat is shared between outposts by colonist count.** An outpost with 10% of the colonists gets 10% of the threat. A **Threat Banner** (+10 threat) counts as 150 colonists for this split, so players can build "dedicated 'superfortresses' that lure monsters away from other colonies". You can't push another populated outpost's share to zero.
- **Difficulty settings.** In 2018 new worlds offered no monsters, standard, double monsters and day monsters, and the devs planned a slider. *Unverified:* which world settings exist in 0.18.

## Colony growth

- **Beds and food gate population.** You can't recruit without a free bed. Recruits cost meals, then eat one meal a day, and starving colonists slowly die. Colonist Capacity per colony or outpost starts low (25 on the old wiki) and is raised in steps with Colony Points.
- **Jobs by placing things.** Craft jobs are job blocks (each needs a nearby crate, plus a Grocery Store and Tool Shop). Farms, foresters and construction are areas drawn with the Command Tool. Unemployed colonists fill open jobs automatically, and a Manage Jobs screen caps how many of each.
- **The science tree.** Research costs items from the stockpile (tools, ingots, scrolls, books, Colony Points), runs from the Stone Age through Bronze and Iron to muskets, printing presses and tabulating machines, and ends in Prestige science, which "doesn't have a true functional benefit, but it demands you scale up your production massively" and adds a lot of threat. Each new guard type is a research.
- **Happiness, then Colony Points.** Up to 0.7 colonists needed luxury items for Happiness, and each colonist added "+1 Unhappiness by Overpopulation". The devs called it "complicated math and weird equilibriums" that "strongly disincentivized population growth". 0.8.0 replaced it with Colony Points, earned by distributing meals. Since 0.9.0 points are earned by selling goods at the Merchant's Hub. Points buy upgrades (colonist capacity, safe-zone radius, player health, builder and digger area size), outpost banners and imports, and some research needs them.
- **No single colony level.** Progress gates are research (which itself needs Colony Points and outpost resources), point-bought capacity upgrades, and the threat each milestone brings. Late colonies run hundreds to thousands of colonists. One beta tester ran "many thousands".
- **"Colony Survival is not an idle game."** The 0.9.0 notes: "When certain things feel slow, that's an incentive to expand and recruit more colonists, not an incentive to wait."

## What players praise and criticise about defence

The sample is small (see intro), so treat these as recurring themes, not proportions.

- **Praise:**
  - Designing killzones. "I enjoy building different killzones atm" (2017). "Once you get late game you can have a giant wall with archers."
  - Building freedom, including underground cities and "a castle in the sky".
  - A calm, readable rhythm where "the more you build the bigger you grow... the more the 'threat'" (November 2026, 63 hours).
  - The designer's own memory: recruiting and posting guards is satisfying in itself.
- **Set-and-forget, and nights are dull.**
  - "Nightime is boring because your colonists are asleep, with guards only standing in one spot" (2021).
  - "Zombies are really dumb, just standing at your banner doing nothing."
  - Defence comes down to "a maze for the walking dead to go down while being shot at, or a large perimeter with many guards" (2023).
- **Too easy, or threat that doesn't feel real.**
  - Early Access players beat it by day 3 with a maze (2017), and others found the monsters "pretty wimpy".
  - After 0.9: "there's no real threat in this game" (2023).
  - Another reviewer says zombies only scale "after base upgrades… ONLY THEN do more zombies come to be killed by those weapons", "the zombies just get more health", and "the only way you can 'fail' Colony Survival is if your colony runs out of food and ammo in the night".
  - This is the flip side of milestone threat: the player controls when difficulty rises, so it feels scripted.
- **The ammo chain.** Few reviews complain about it directly. The criticism is that running out is the only real failure. The devs themselves found 0.4.0 so hard that "my production of bronze arrows could not keep up with the monsters". The 0.17 update let players choose which item counters to show (ammo by default), because ammo tracking had become confusing. Guard staffing gets one complaint (2024, from search results): having to re-job workers as guards at night and back by day. *Unverified:* the full context of that post.
- **Monster variety.** "Zombies in different colours" (2018). Every review asking for change wants monsters that do something other than walk and absorb damage (climbing walls, ranged attackers). The devs have floated healer monsters and ranged monsters to "change the current maze-meta" (2019), and an offensive mode with "MonsterForts" in another dimension (2021). Neither has shipped.
- **Gates.** "You cannot build gates for your walls" (2025). Doors arrived in 0.10, but monsters use them too.

## Relevance

- **A guard post the player places is the right shape for our guards.** In Colony Survival the player decides where defence stands (a block crafted from the weapon) and the colony decides who staffs it (any unemployed colonist). For us that could be a Guard Post block placed in a Settlement and staffed by a Villager who lives there, as a new guard Villager variant or entity. Vanilla Villager AI, Iron Golems and Illager Raids stay untouched, per technical parity. It splits defence across playstyles:
  - Builders design walls, towers and the approach.
  - Gatherers stock arrows and food.
  - Fighters show up for the big nights.
  - Nobody has to fight for the Settlement to be defended.
- **Stationary guards with sphere range and line of sight make layout matter.** This is what makes Colony Survival's walls and towers count: a guard on a tower covers more and survives longer, and a funnel keeps monsters in range. It directly serves our "walls and layout matter". The built-in target choice (the strongest monster you can one-shot) is a nice touch that removes micromanagement.
- **Our "never break blocks" rule has the same hole Colony Survival had, and it needed two fixes:**
  - **Total enclosure.** If raiders can't break blocks, a sealed wall wins. Colony Survival's answer is Siege Mode, a food penalty unless one path stays open. We need an equivalent, for example raiders that spawn or climb inside, a Raid target (a bell, the Settlement centre) that must stay reachable, or a Prosperity or trade penalty while sealed. Without one, builders seal the Settlement and Raids stop mattering.
  - **Maze stalling.** A continuous spawn rewards stalling mazes. Colony Survival switched to a fixed number of monsters per night "regardless of path length". Our Raids should be a fixed number of waves, each with a fixed size, and should end when that's beaten, not when a timer runs out. Vanilla-style pathfinding also gives up on very long routes, so don't let a Raid depend on long mazes working.
- **Scale Raids with explicit milestones, and show the number.** Pipliz spent three years moving off "monsters scale with colonist count" because it punished slow, inefficient and pretty colonies: exactly the builders on our server. Their fix (threat from milestones, shown in the corner and printed on each unlock) matches our "Raids scale with Prosperity" if Prosperity is a visible score that rises by things players choose to do. Each Prosperity unlock should state the extra Raid strength it brings, the way Colony Survival prints threat on each research. Avoid per-Villager scaling: it makes recruiting Villagers feel like a cost.
- **But milestone threat can feel scripted.** The main post-0.9 complaint is that the player controls when difficulty rises, so it never feels dangerous. On our server that's partly fine (builders want predictability), but fighters need Raids that sometimes test them. Options: a gentle daily drift on top of the milestone (the 2018 idea), or opt-in harder Raids for groups who ask for them.
- **The Threat Banner is a ready answer to two open questions.** Players can found as many villages as they like, so threat needs to belong to each village (Colony Survival splits it by population) or someone will spread out to dodge it. The Threat Banner lets a group deliberately pull threat into one "superfortress". That's a physical, opt-in way to answer "Are Raids opt-in?": a village that wants Raids raises a banner, and a quiet builder's hamlet stays small and quiet. It pairs with TekTopia's rotate-the-marker opt-out ([tektopia.md](tektopia.md)).
- **Shared supply is a good contribution route, but shortages must degrade, not kill.** Guards drawing ammo from a shared store gives non-fighters a clear, felt contribution (arrows and food keep the town safe). But Colony Survival's only failure condition is "ran out of food and ammo in the night", and on a server where the person who stocks arrows is offline, that failure lands on everyone. Cap upkeep low, let unsupplied guards fall back to a weak melee attack, and add a stored safety net like the **Sacred Failsafe** (points banked by everyday activity, spent automatically in an emergency). This protects absent players and fits "building never waits on combat".
- **Guards should hold the line; players should make the difference.** Reviews call Colony Survival's nights boring because the player has nothing to do once the maze works. Our Raids are meant to be group encounters, so guards should handle routine pressure and buy time, and the climax waves should need players: give guards clear limits (Colony Survival's are short range for the big guns and fragility), not damage scaling that solves everything. Guard power should come from building (towers, walls, posts) and supply, not from grinding kills, which also avoids TekTopia's catch-22 where safe villages never improve their guards.
- **Weapon tiers that make old guards obsolete don't fit us.** Colony Survival's progression is "unlock the next gun before the next zombie tier". Our Power progression belongs to Eras and players ([vision.md](../vision.md)), so Villager guards should improve horizontally (post types such as archer, spear or bell-ringer, and placement) or with Prosperity, not with a parallel weapons ladder players have to keep feeding.
- **Traps: vanilla already has them.** Colony Survival's traps fill a gap that Minecraft's dispensers, redstone and lava already fill, and technical parity keeps those exact. The Trap Fixer idea (a Villager who refills dispensers by day) is worth noting as a possible Villager service, but it isn't needed.
- **Monster variety is the most-requested fix.** Colony Survival's single zombie type with bigger health bars is its weakest point in reviews. Our Raids should use new entities with distinct behaviour (climbers that bypass enclosure without breaking blocks, ranged attackers that punish exposed guards, healers), all as new mobs or variants, not changes to vanilla AI.
- **What won't transfer:**
  - **The code.** Closed-source Unity; only the moddable JSON and C# API is open.
  - **The infinite magic stockpile.** It removes all hauling. In Minecraft, guards drawing from a specific chest or barrel near the post is more readable, and Villagers already have pathing limits.
  - **The scale.** Thousands of colonists on one server is a performance budget we don't have, and a 5 to 10 player server should expect villages of tens of Villagers.
  - **The top-down, mayor-only role.** Our players live in the world, so the Settlement has to feed back into what they do.
  - **Gates.** Colony Survival never solved gates because monsters can't queue. Minecraft mobs do push against each other, and vanilla zombies already break wooden doors on Hard, so new raider entities would need an explicit rule for gates and doors that fits "never break player blocks".

## Sources

- Steam store page: https://store.steampowered.com/app/366090/Colony_Survival/
- Official Steam announcements (read through https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/?appid=366090 ):
  - "Putting the 'Survival' back into Colony Survival" (27 Oct 2017): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/2140759684347114637
  - "Work on 0.6.0 has started & the monster threat level!" (2 Feb 2018): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/2363696743803728079
  - "Builder Video and the Tower Defense & Missile Controversy" (1 Mar 2018): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/2363699280657355205
  - Blog 48, "Monsters might Overrun your Colony in Two Weeks!" (18 May 2018): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/2391853754538759997
  - Blog 95, "Gates and Wall-Breaking?" (12 Apr 2019): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/3763229740645942309
  - Blog 154, "Criticizing CS" (29 May 2020): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/3142896212091996741
  - Blog 158, "Uncoupling Difficulty from Colonist Count" (26 Jun 2020): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/3444639924498365491
  - Blog 180, "Update 0.8.0 Is Live! From 'Happiness' to 'Colony Points'" (27 Nov 2020): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/3856733252009651441
  - Blog 192, "Recruiting Massive Armies to Defeat MonsterForts" (19 Feb 2021): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/4059402835313160011
  - Blog 203, "Monster Threat Level" (10 Sep 2021): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/4020014731798651728
  - Blog 206, "A Potential Solution: Traps!" (15 Oct 2021): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/4698935641759114813
  - "Update 0.9.0 is available right now!" (22 Dec 2022): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/5000716037442665845
  - Blog 268, "The Shape of the Next Update: QoL Improvements" (29 May 2026): https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/1833968530891289
- Colony Survival Wiki (Miraheze), https://colonysurvival.miraheze.org/wiki/ : `Guard`, `Monsters`, `Threat`, `Banner_Tool`, `Colonist`, `Outposts`, `Getting_Started`, `Tips`, `Slinger`, `Day_Slinger_Guard`, `Day_Bow_Guard`, `Bow`, `Copper_Arrow`, `Fletcher's_Bench`, `Archery`, `Musket`, `Musket_Ammo`, `Poison_Guard`, `Handcannon`, `Grenade_Launcher`, `Alarm_Bell`, `Sacred_Failsafe`, `Sacred_Altar`, `Trap_Fixer`, `Points`, `Menu`, `Grocery_Store`, `Bed`, `Player`, `Command_Tool_&_Banner_Tool`, `Prestige_I`, and patch notes `0.10.0.0`, `0.16.0.0`, `0.17.0.0`, `0.18.0.0`
- Colony Survival Wiki (Fandom, largely outdated), https://colonysurvival.fandom.com/wiki/ : `Guard`, `Zombie`, `Threat_Level`, `Siege_mode`, `Banner`, `Jobs`, `Colony_Points`, `Ammunition`, `Traps`, `Food`, `Day_cycle`, `0.9.0`, `0.9.1`, `Updating_Roadmap`
- Steam discussions: "Invincible base... by Day 3" (June 2017, with a developer reply): https://steamcommunity.com/app/366090/discussions/0/2333276539606475742/ ; "A basic guide." (June 2017): https://steamcommunity.com/app/366090/discussions/0/2333276539612427437/
- Steam reviews: https://steamcommunity.com/app/366090/negativereviews/?browsefilter=toprated ; https://steamcommunity.com/app/366090/positivereviews/?browsefilter=mostrecent ; https://steamcommunity.com/app/366090/reviews/?browsefilter=toprated
