# Season 1 Villages

**Status: Leaning.** **Decided:**

- Villages ship in Season 1.
- They extend vanilla villages rather than replace them.
- There can be any number of them.
- The bell becomes the Steward's job block.
- Everything is available from a single Village.

The core loop is **Leaning**: quest board and salary for income, Prosperity levels with requirements, building yourself or hiring a Builder, a Raid to level up, and guards. Everything marked **Idea** is a suggestion from the design-lens review, and nothing is agreed yet. It grew out of the Villager trading rework on the [playtest wishlist](../playtest-wishlist.md) and brings the vision's Settlements (now Villages) forward from a later Season.

## The problem

From the September 2026 playtest, which ran the Super Awesome Villagers datapack ([research](../../research/super-awesome-villagers.md), [vision.md](../../vision.md#the-problem)):

1. **Selling resources turned every playstyle into an exchange rate.** The Fisherman bought raw ore, Diamonds and Netherite Scrap, so every route ended at "mine, sell, buy". The top trader made over 2,400 trades. The selling side (bulk blocks for builders) hurt nothing. The buying side did the damage.
2. **Villages were trading halls, not places.** Players built boxes of Villagers or moved into an existing house. Nothing rewarded building a town.
3. **Large parts of the game went unused:** fishing, brewing, Trial Chambers and Bastions. Nothing pointed players at them.
4. **Builders and players who barely traded fell behind,** with no way to contribute that others would feel.

Philosophy carried over from the datapack: **Villager trades are a supplemental way to get resources a player would rather not gather themselves**, never the only sensible route. Tolerance for gathering differs by player and by resource, so the trade list should be broad, but it should never be required.

## What counts as fixed

Written before the playtest so it can fail:

- Emerald income is **spread across players and sources** (quests, salary, raids), not concentrated in one player selling one resource.
- **At least half the players** take part in a Village in some way: building, quests, defending or trading.
- **Quests send players to the content that went unused:** completed quests include fishing, brewing, Trial Chamber and Bastion tasks.
- Players who mostly build **reach Prosperity levels** without being forced into combat or into building styles they dislike.
- No one runs **more than two Villages purely for income.** If they do, the per-player cap failed.

## How it works

### What a Village is

**Decided.**

- **Every vanilla village counts,** and players can start their own anywhere by bringing Villagers to a bell. There's no limit per player or per world. Players can run their own Villages, or work together on one or a few.
- **Vanilla behaviour stays.** Villages are still made of the beds, job sites and bells that Villagers claim, and gossip, breeding and iron golems work as in vanilla ([technical parity](../../vision.md#constraints)). Villages add a layer on top.
- **Everything is available from one Village.** Villages can vary a little by biome (see [Biome variety](#biome-variety)), but nobody should feel forced to found new Villages to reach a resource.

### The Steward

**Decided:**

- **The bell is the Steward's job block.** An unemployed Villager who claims a bell becomes the Steward.
- **One Steward per Village.** A second bell in range doesn't create a second Steward.
- **He never strays far from the bell.**
- **The bell keeps its vanilla behaviour:** ringing, gathering Villagers, and showing raiders.

The name leaves "chief" free for players who want to roleplay as the Village's leader.

**The Steward is the Village's interface.** Talk to him to:

- collect your salary,
- see and accept quests,
- check what the next Prosperity level needs,
- see Village stats (Villagers, jobs, buildings, Prosperity, who has contributed what),
- register a building, or commission one from a Builder.

**Idea:** a bell with no Steward gives a hint ("this Village needs a Steward") instead of failing silently.

### Founding a Village

**Leaning:** bring Villagers to a spot with a bell, beds and job blocks, and a Village forms.

**Idea:** moving Villagers in vanilla is miserable (boats, minecarts, trapping). Two possible fixes:

- **Charter:** an existing Steward sells a Charter. Placing it at your bell makes a couple of Villagers move there (walk or arrive) once there are free beds. New Villages grow out of old ones, which is a nice story and a natural cost.
- **Leads work on Villagers,** as Convenience.

### Earning Emeralds

**Leaning: Emeralds stay the Village currency, but they're earned from Villages, not by selling resources.** That keeps root cause 1 (one currency turning playstyles into exchange rates) from coming back: Emeralds reward taking part in a Village, not converting ore. There are two sources.

**The quest board.** The Steward offers a rotating set of tasks. Examples:

- Gather or cook food, bring resources.
- Clear a nearby pillager outpost.
- Defend the Village from a roaming band of pillagers or mobs.
- Catch certain fish, brew a potion, bring back loot from a Trial Chamber or Bastion.

**Salary.** Players who've done a lot for a Village get a daily wage from it: the Villagers paying them for their deeds. Candidate inputs: Village size, Prosperity, defence, and time and effort spent.

**Ideas from the design-lens review** (background: [quest boards](../../research/quest-boards.md)):

- **One cap per player across all Villages.**
  - **Quests:** a player can hold a few quests at once, from any boards. More Villages means more choice, not more Emeralds. Bountiful pays every board separately with no cap, so its income grows with every board you can reach.
  - **Salary:** one per day, from the Village where your standing is highest. It counts against the same cap.
- **Standing instead of time or kill counts.**
  - **What it is:** a per-player, per-Village score that rises with deeds that are hard to farm (quests done, raids defended, buildings finished, materials donated) and fades slowly.
  - **How salary uses it:** salary = standing × Prosperity, capped.
  - **Why not time or kills:** "time spent" pays AFK players, and "mobs killed near the Village" pays for a mob farm next to it.
  - **Fits vanilla:** gossip is already a per-player reputation that decays.
- **Show the cap as a bonus.** Unused allowance builds up for a few days, so missing a day costs nothing (WoW's rest bonus, Destiny 2's catch-up challenges).
- **Only work done after accepting a quest counts** (Village Quest's rule), so a chest stockpile can't be cashed in.
- **At least two choices per board,** weighted toward fishing, brewing, Trial Chambers and Bastions.
- **The board is shared, the quest is personal.** Everyone sees the same board, and each player takes their own copy, so the first player never empties it. Completing a quest also raises your standing and adds to the Village's Prosperity.
- **Rewards beyond Emeralds:** Prosperity, Village upgrades, cosmetics, and rare items capped weekly. Never Era materials or Power.
- **Buy trades shrink.** If quests and salary are the income, the Super Awesome Villagers buyer professions (the Fisherman's ore exchange above all) become few and capped, or go entirely. *Open.*

### Growing a Village: Prosperity

**Leaning: Clash of Clans–style levels.** Each level has a visible list of conditions:

- a number of Villagers,
- buildings for particular jobs,
- some materials.

Once they're met, the Village levels up. **Prosperity levels up every Villager in the Village** (no per-Villager trading XP) and unlocks better trades.

- **Idea: 5 Prosperity levels map to vanilla's 5 Villager levels** (Novice to Master). Trades then use vanilla's level slots, which are hardcoded ([Super Awesome Villagers](../../research/super-awesome-villagers.md#implementation-notes-for-a-fabric-mod-on-263)), and the Village levels up instead of each Villager.
- **Idea: costs rise steeply.** Early levels are cheap, so new Villages and newer players can start. High levels cost a lot, so a lead doesn't snowball. Top levels could ask for a later Era's materials, tying a Village to world progress.
- **Idea: count Villagers with a bed and a job, or distinct professions,** so a breeding box doesn't meet the Villager requirement.

**Two ways to get a building (Leaning):**

1. **Build it yourself,** in any style, then register it with the Steward.
2. **Hire a Builder.** Pick the spot and the type of building, supply the materials, and a Builder Villager constructs it. People who don't like building can still grow a Village.

**Idea: what counts as a building.** Borrowed from [TekTopia](../../research/tektopia.md), [Terraria](../../research/terraria-npcs.md) and [MCA](../../research/settlement-systems.md):

- **A functional check, not a style check:** a roof, a door, light and the job block, plus a bed for a house.
- **Checked once, when the building is registered,** so it's cheap for the server.
- **The Steward names exactly what's missing.**
- **Count block types by family or tag, not exact ID,** so variants count. MineColonies rejected slabs made by another mod.
- **Allow multi-storey buildings, ladders and stairs.** TekTopia's single-floor rule chafed builders.

**Idea: how the Builder works.**

- **Catalogue:** vanilla's own village building pieces for the Village's biome, so it's cheap to make and always matches.
- **Materials:** one delivery to a chest, never a stream of requests. That request loop was MineColonies' biggest complaint ([research](../../research/minecolonies.md)).
- **Cost:** the same materials as building it yourself, plus a small fee or a wait, so neither path is strictly better.

**Leaning: a Raid at each level-up.** To reach the next Prosperity level, the Village has to survive a Raid. Rules to keep:

- **A failed Raid costs only time.** You try again later.
- **No block damage.** Raids never break player blocks.
- **Friends can help.**
- **Vanilla raids keep working unchanged.**

Background: [Colony Survival](../../research/colony-survival.md). The level-up Raid shouldn't force builders into combat; guards and walls are how a non-fighter wins it.

### Trades

**Leaning:** better trades unlock with Prosperity. The [Super Awesome Villagers](../../research/super-awesome-villagers.md) rules carry over where they still fit:

- **Fixed catalogues:** every Villager of a profession at a given Prosperity level offers the same trades.
- **Fixed prices,** with no exploit loops.
- **Lots of bulk blocks**, the Terraria-mod lesson ([research](../../research/terraria-npcs.md)). Building blocks carry no progression value, so selling them cheaply helps builders and harms no one.

**Hard limits, unchanged** ([enchanting](../enchanting/README.md), [vision.md](../../vision.md#gear)): Villagers never sell Era materials, Blaze Rods, Wither Skeleton Skulls, Shulker Shells, combat or loot books, or enchanted gear.

**Idea:** an Era's *decorative* blocks become buyable once a player hands a Village a set amount (Terraria Journey Mode: 100 of a block). The unlock belongs to that Village, so it's something a player did in one place. It never covers ores or materials, because that would get round Era-gated mining.

### Defence: guards

**Leaning:** players can assign Villagers as archers and other guards to defend the Village, like [Colony Survival](../../research/colony-survival.md).

**Ideas:**

- **Guards are placed as blocks.** A guard post, crafted from its weapon (a bow and something else), is a job block. A Guard is a new Villager profession, so vanilla Villager and iron golem AI stay untouched.
- **Guards draw ammo from a shared Village chest.** That gives non-fighters a way to help defend.
  - **When ammo runs out,** guards weaken instead of the Village falling.
  - **An emergency defence** is banked for the worst case, like Colony Survival's Sacred Failsafe.
- **Walling off has a cost.** Raids never break blocks, so a fully sealed Village would be untouchable. Colony Survival's answer is Siege Mode: sealing the colony makes upkeep jump. We need an equivalent that keeps a defended way in.
- **Fixed waves, not timed ones,** so stalling mazes don't pay.
- **Raids are visibly scaled.** Each Prosperity level says how much harder its Raids get. Colony Survival moved from population-based threat to visible milestones because population punished slow, decorative colonies.
- **Guards handle routine pressure. Players decide the big waves.**

### Biome variety

**Decided: slight variance only.** A Village's biome can flavour its catalogue: a desert Village sells sand and terracotta a little cheaper, a taiga Village spruce. Every resource stays available from any single Village. Visiting friends' Villages should be worthwhile, never required.

### New Villager types

**Idea.** Candidates so far: Steward (decided), Builder, Guard. Others can come from the quest and service ideas, such as a Courier for grave recall, or a Village service that takes players home or to spawn (see the wishlist's /home, /spawn and /tp item).

## Risks

From the [design-lens](../../../.claude/skills/design-lens/SKILL.md) review:

- **Income multiplying with Villages.** The biggest risk. Covered by the per-player cap.
- **Farmed inputs:** AFK time, mob farms next to the Village, Villager breeding boxes. Covered by standing and by counting Villagers that have a bed and a job.
- **Snowball.** Prosperity leads to better trades, which leads to faster Prosperity. Offset by steep level costs and the per-player cap. Joining a friend's Village is the catch-up route.
- **Chores.** MineColonies' requests and TekTopia's many Villager needs became work. The Steward's needs are a visible list players choose to pursue. A Village never breaks down because nobody tended it.
- **Builders forced into combat** by the level-up Raid. Covered by guards and walls.
- **First-come resources.** Quests are personal copies. Outposts respawn pillagers in vanilla, so "clear an outpost" can repeat. *Unverified* for quest purposes.
- **Scope.** This is the largest Season 1 system. The playtest log needs Village events so its effect can be told apart from Gear's.

## Playtest log

Events to add to `PlaytestLog` ([tools/playtest](../../../tools/playtest/README.md)):

- Village founded
- Steward assigned
- building registered or commissioned
- Prosperity level reached
- Raid started, won or lost
- quest accepted and completed (with type)
- salary paid
- trade made (profession and level)
- guard post placed

## Open questions

- What are the exact Prosperity requirements per level, and how many levels?
- What are the quest types and their reward sizes? What's the per-player cap, and how much can build up?
- Which inputs feed standing, and how fast does it fade?
- Do any buy trades stay, and with what caps?
- Is the level-up Raid a vanilla raid started by the Steward, or a new Raid variant? Vanilla Ravagers trample crops and break leaves: does that count as breaking player blocks?
- What is the Siege Mode equivalent?
- How do Villagers get to a new Village: Charter, leads, or vanilla means only?
- How big is a Village's area, and how close can two Villages be?
- Who can use a Village's Steward, Builder and guards? Anyone, or players with standing?

## Background

- [Super Awesome Villagers](../../research/super-awesome-villagers.md): the September 2026 datapack.
- [TekTopia](../../research/tektopia.md): free-style buildings with a marker and required blocks.
- [MineColonies](../../research/minecolonies.md): ready-made buildings, the request loop, and The Bottom Dweller's review.
- [Terraria NPCs](../../research/terraria-npcs.md): housing checks, shops gated by progress, block vendors.
- [Settlement systems](../../research/settlement-systems.md): Dragon Quest Builders, Valheim, Animal Crossing, Millénaire, MCA, Village Quest, vanilla villages.
- [Colony Survival](../../research/colony-survival.md): guards, threat and siege.
- [Quest boards](../../research/quest-boards.md): Bountiful, Village Quest, Stardew, Deep Rock Galactic, daily fatigue.
