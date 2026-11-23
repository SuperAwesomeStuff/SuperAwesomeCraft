# Candidate B: The Archive

> **Retired.** Superseded by [the library design](library.md), which was itself replaced by [the slot design](../README.md). Kept for reference.

**Status: Idea.** One of three Season 1 candidates; see the [comparison](README.md). The [shared ground rules](README.md#shared-ground-rules) apply.

**In two sentences:** every enchantment is known at level I from day one, and the world funds **research** together up to one level below each enchantment's top, so those levels and the world's base stats (**world Sharpness, Protection and Power**) belong to everyone. You choose which known enchantments fill your item's few **slots**, and **Masterwork** your favourite items with far-off materials to take them the last step.

This is enchanting as the server's shared project, following [ADR-0003](../../../adr/0003-world-progression-through-portals.md) ("progression belongs to the world"), Kanai's Cube's extract-to-library, Factorio's shared research and Guild Wars 2's guild donations.

**Its bet for the Season 1 question:** the horizon past full Netherite comes from two layers at once, the world's research (shared) and Masterwork (personal), and the playtest measures them separately so a result can be credited to the right one.

## How a player experiences it

1. **First evening.** Every Enchanting Table opens the **Archive**, a book of every enchantment in the game. Convenience is known at every level, so you put Efficiency and Silk Touch on your pick for free; everything else is known at level I, so you slot Looting I on your sword.
2. **First week.** The **Research** page shows bills. *Fire Aspect II* needs Blaze Rods, Magma Cream and Coal. You drop in your spare Coal, someone adds the Blaze Rods, and when the bar fills everyone's slotted Fire Aspect becomes Fire Aspect II. The **World Sharpness** bill slowly fills with stacks of iron and gold that would otherwise sit in chests.
3. **After full Netherite.** Your sword has 2 slots and World Sharpness is III. You **Masterwork** it with Echo Shards: everything on it goes up a step (Sharpness IV, Looting III), it becomes silent to Sculk, and it carries your name. A Heart of the Sea later, it also swims faster.

## Rules

### The Archive: one system, two kinds of entry

The Archive is **world data**, so every Enchanting Table opens the same one; a builder 5,000 blocks away has what the settlement has. It holds two kinds of entry, researched the same way:

- **Enchantment entries** (Looting, Fire Aspect, Thorns, …): known levels can be put on items, in slots.
- **World stat entries** (World Sharpness, World Protection, World Power): known levels apply to every player's gear automatically (see [World stats](#world-stats-where-raw-stats-come-from)).

### What's known on day one

- **Convenience entries are known at every level**, including Soul Speed, Swift Sneak and Frost Walker. Applying them is free.
- **Every other enchantment is known at level I** (apart from the three world stats, which start at none). Fishing would turn up nearly every enchantment in an evening anyway, so a discovery step would hold nothing back; the celebration moves to bills filling.
- **Wind Burst** is the one exception: it's known once someone puts an ominous vault's Wind Burst book in a table (each ominous vault rewards each player once, so anyone can get one; the book is used up).
- **Other enchanted books pay toward research:** a book can be put into its own enchantment's next bill (Sharpness, Protection and Power books pay into the matching world stat's bill), worth up to a third of one tier 1 or 2 line in total, per bill (its tooltip says *"Counts toward Looting II research"*), so a dungeon find or a catch is never junk, while a fish farm still can't fill a bill or touch a far-off line. Found gear works as found (below). Fishers and explorers push the world forward with bill materials (Nautilus Shells, Prismarine, treasure) and far-off lines instead.

### Research

- **Research stops one level short of vanilla's maximum for every entry with three or more levels** (Looting II, Smite IV, Fire Protection III, World Sharpness IV); the last level of each is personal, through [Masterwork](#masterwork-the-personal-horizon). Two-level enchantments (Fire Aspect, Knockback, Punch, Venom, Frostbite) research to their maximum. Every researchable level above I, and every world stat level, has a **research bill**. Bills don't change size during play, except through the prepared lever below.
- **Every bill is open for contribution at any table**, and the Research page **features three**: the next three in a suggested order (by tier, then level), so effort gathers instead of scattering across dozens of half-filled bars. The bar is visible to everyone; when a bill fills, the whole world knows that level and the next suggested bill becomes featured. The page also lists which bills take which materials, so a player holding Rotten Flesh can see where it helps.
- **How a bill is valued:** each material line is an equal share. Within a line, contributions count in proportion (half of a line's Bones is half of that line). Anything past what a line still needs is handed back. A bill is complete when every line is.
- **Every bill has at least three lines from at least two tiers**, so no single farm can fill one. **The last researchable level of every entry with four or more levels, and of each world stat, has a far-off line**; three-level entries (Looting, Fortune, Sweeping Edge, Thorns, Steadfast and so on) research level II with tier 3 lines, so the mid-game never waits on an Ancient City, and their top level is Masterwork's far-off step. Each far-off line accepts **either of two** far-off materials, and they can be mixed (for example 4 Echo Shards, 4 Shulker Shells, or 2 of each), so one unpopular trip can't stall the server.
- **Credit:** each completed level lists **everyone** who contributed, with the top three highlighted, and the chat announcement when a bill fills names every contributor. Bringing the far-off line of a three-line bill is a third of the credit, however few items it was, and the credit goes to whoever puts the items in.
- **Books pay only a little.** At most a third of one tier 1 or 2 line per bill, so fish farms can't do the research.
- **A fast player can fund research alone.** That's catch-up for everyone else and shows in the credits; whether it crowds others out is one of the things the playtest measures (below), with a per-player share limit per line as the prepared lever if it does (far-off lines are exempt, so the player able to bring Echo Shards is never blocked from finishing a top bill).

| Research | Bill |
|---|---|
| Looting II | 2 Gold Blocks, 64 Bones, 8 Ghast Tears |
| Fortune II | 32 Quartz, 2 Gold Blocks, 8 Lapis Blocks |
| Fire Aspect II | 8 Blaze Rods, 16 Magma Cream, 32 Coal |
| Smite IV | 4 Echo Shards or 4 Shulker Shells, 64 Bones, 32 Rotten Flesh |
| World Sharpness I | 64 Iron Ingots, 32 Amethyst Shards, 8 Gold Ingots |
| World Sharpness IV | 1 Nether Star or 4 Echo Shards, 8 Diamond Blocks, 16 Breeze Rods |

**Total size.** Every entry fully researched is aimed at roughly **55 player-hours** of gathering, about what the whole group played last season, so it fits the two weeks only if players stay longer, which is the point. Milestones (below) let the prediction fail early. That is about **49 bills** (38 enchantment levels: every level above I, except the top of each three-plus-level entry, which is Masterwork's; the five two-level enchantments research to their maximum. Plus 11 world stat levels.) **13** of them have a far-off line: the last researchable level of the 10 enchantments with four or more levels, and of the 3 world stats. The full bill list is written once this candidate is chosen and checked against that target.

### World stats: where raw stats come from

The stat enchantments (Sharpness, Protection and Power) are not put on items. Instead the world researches them once for everyone, each stopping **one level short of vanilla's maximum**; the last level is personal, through Masterwork.

| Entry | Applies to | Effect | World levels | With Masterwork |
|---|---|---|---|---|
| **World Sharpness** | Items in `#enchantable/sharp_weapon` held by a player | Counts as Sharpness at the entry's level | I to IV | +1, up to V |
| **World Protection** | Items in `#enchantable/armor` worn by a player | Each piece counts as Protection at the entry's level | I to III | +1, up to IV |
| **World Power** | Items in `#enchantable/bow` held by a player | Counts as Power at the entry's level | I to IV | +1, up to V |

- **World stats follow vanilla's exclusivity, so specialising is a real choice.** A weapon carrying Smite or Bane of Arthropods (slotted or found) doesn't get World Sharpness; an armour piece carrying Fire, Blast or Projectile Protection or Warding (slotted or found) doesn't get World Protection. Slotting one asks you to confirm, with the numbers (*"Loses Sharpness III (+2 damage); gains Smite IV (+10 damage to undead)"*). Smite, Bane and the specialised Protections follow the same one-short rule, so specialising never loses the Masterwork step.
- **Players only.** Mobs, armour stands and dispensers are unaffected (technical parity, and no surprise for mob farms).
- **Found gear:** if an item already has its own Sharpness, Protection or Power (from loot, so at most level II), the higher of its level and the world's counts, not both. These don't use slots, and removing them at the table clears them.
- **Visible:** the tooltip shows the effective value, e.g. `Sharpness III (world)` or `Sharpness IV (world +1 Masterwork)`. Sharpness, Protection and Power appear greyed out on the table with *"Everyone's swords already have Sharpness III. Raise it on the Research page"*.
- Maces and crossbows can't take Sharpness or Power in vanilla, so world stats don't touch them. Spears follow whatever `#enchantable/sharp_weapon` contains on 26.3 *(check)*.
- **Materials still matter.** World stats add to each material's base stats, so diamond and netherite stay better than iron.
- **Implementation:** two hooks, in `EnchantmentHelper.modifyDamage` and the armour protection calculation, add the world level for players. For melee and sweep attacks the check is the attacking player's held weapon; for arrows it's the arrow's owner (must be a player) and the arrow's stored weapon item. World stat levels are synced to clients on join and on change.
- **Bosses:** the world stops one level short of vanilla's maximum and the top level needs a personal Masterwork, so the Dragon and Wither are never easier than with vanilla's best enchantments.

### Slots: the personal choice

- **Every item has slots.** Each slot holds one Power or Yield enchantment, by default at the **highest level the world knows**. Under the hood a small `slots` component lists which enchantments are slotted and the Archive version last applied, and only those are written into the item's ordinary `enchantments` component at the current world level, **+1 if the item is Masterworked** (capped at vanilla's maximum), so Looting, Fortune, Thorns and the rest read their real level from the item. Only the three world stats use the two hooks, which add the *value* of the stat at the effective level (the world's plus Masterwork, or the item's own if higher) minus its value at the item's own level, so Sharpness's uneven curve never overshoots and vanilla still applies the item's own (found gear's own enchantments are never raised by the world or by Masterwork), so every vanilla path (Looting drops, Fire Aspect, Silk Touch checks, glint, tooltips) works unchanged. The mod rewrites them, only when the item's Archive version is out of date (so the per-tick check is cheap), whenever the item enters or is ticked in a player's inventory or a container holding it is opened, and when a bill fills it rewrites every online player's inventory (including the stack on their cursor and any open menu) and reapplies their equipment effects. Items sitting in chests or frames may show an old level until touched, which is harmless because they aren't in use. Archive levels are synced to clients so the table and tooltips match. Convenience doesn't use slots.

| Item | Slots |
|---|---|
| Swords, axes, spears: wood, stone, copper, gold, iron | 1 |
| Swords, axes, spears: diamond | 2 |
| Swords, axes, spears: netherite | 3 |
| Bow, Mace | 2 |
| Crossbow, Trident | 1 |
| Armour (netherite: 2) | 1: Thorns, Steadfast, or a specialised Protection in place of World Protection |
| Pickaxe, shovel, hoe, fishing rod (any material) | 1: Fortune or Luck of the Sea |

Shields and Elytra have no slots. **Netherite weapons and armour get one more slot than diamond**, so a fighter's own mid-game step, before any Masterwork, is the netherite upgrade: a personal build choice the world can't hand out. Fortune on an axe uses one of its weapon slots.

- **Applying and removing are free.** Changing a build is a click at any table, and a slot can hold any level the world knows, not only the highest (Knockback I if you don't want II).
- **Tooltip:** `Slots ●●○○` with each slotted enchantment, then `Familiarity 3 · Techniques 1/2` for tools. They are separate budgets.
- **Example, diamond sword (2 slots):** Looting II and Fire Aspect II for the mob farm; Smite IV and Knockback II for a skeleton spawner (Smite replaces World Sharpness on this sword); Venom II and Sweeping Edge II for a raid (Venom does nothing to spiders or undead).
- **Found gear** keeps its enchantments as found. Slotting an enchantment it already has replaces the found one, with a confirm prompt if that would lower it (a found Smite II sword while the world only knows Smite I). To change it at a table you first remove enough to fit its slots ([shared rule](README.md#random-sources-stay-below-the-ladder)).

### Masterwork: the personal horizon

- **Each item can be Masterworked up to twice**, each time with a **different** one of [the shared five far-off materials](README.md#tier-4-fair-access-and-shared-upgrade-materials). Each Masterwork gives the item that material's small Convenience effect (silent to Sculk, faster swimming, a drop that can't be destroyed, free Ender Pearls, higher Wind Charge jumps). **The first Masterwork also takes the item the last step** (below); the second adds a second effect. Slots stay fixed per item type, so the Archive's collection grows while the budget doesn't, as in Kanai's Cube.
- **A Masterworked item counts the enchantments slotted when it was Masterworked, and its world stats, one level higher, up to vanilla's maximum** (re-slotting moves the last step to the new enchantments for a tier 2 material, so one Masterworked item can't cover every loadout for free) (found gear's own enchantments stay as found; level I of a world stat if the world hasn't researched any). Two-level enchantments (Fire Aspect, Knockback and so on) are already researched to their top, and their tooltip says Masterwork doesn't change them. One rule: the world takes everything to one below the top, and your own trip takes your item the last step, whether it's World Sharpness V, Smite V, Looting III or Protection IV.
- **Tools** can be Masterworked too: the first Masterwork takes their slotted Fortune or Luck of the Sea to its top level, so Fortune III is a pickaxe's far-off step (a Heart of the Sea works, no combat needed). Axes count as weapons. Shields and Elytra get the effect only.
- The tooltip stamps it: *"Masterworked by <player> with Echo Shards and a Heart of the Sea"*. Smithing up to netherite keeps Masterworks (smithing copies item components).

### Books, anvils and grindstones

- The anvil only renames. Books matter only for Wind Burst. The grindstone still strips enchantments as in vanilla, and clears the item's slots with them; Masterworks stay.

### Villagers

- Librarians sell **no** enchanted books: Power and Yield are never sold ([shared rule](README.md#convenience-costs-no-budget)), and Convenience is already known. Their paper, Bookshelf and other trades stay. Smiths' equipment trades roll at most level II.

## Design lenses

- **MDA.** Mechanics: world knowledge, shared bills, world stats, free slots, Masterwork. Dynamics: players dump surplus into the Archive, cheer when a bar fills, talk over what to fill first, and plan Masterwork trips for their favourite items. Aesthetics: **belonging** (we built this), **anticipation** (a bar nearly full) and **pride** (my name on this sword, and Sharpness V nobody gave me).
- **Lodestar.** The vision statement taken literally: miners fill World Sharpness and World Protection, fishers and explorers bring treasure and far-off lines, fighters bring far-off materials, builders give surplus. Pillar: Progress is shared, with Convenience is cheap underneath and Masterwork keeping the top of Power earned.
- **Borrowed.** Kanai's Cube works because the collection only grows while slots stay fixed; same here. Factorio's research works because a team shares one goal; our six players have different goals, so bills have several lines from different tiers. Guild Wars 2 donations work when contributors get credit; the Archive names them.
- **Economy.** Taps: mining, mobs, fishing and loot. Converter: research. Drain: bills, large and permanent, the replacement for durability's ore sink; Masterwork materials. Inventory: the world's Archive, slots on items. The group's decision is which bills to keep open and where to put surplus. Exploit checks: books pay at most a third of one tier 1 or 2 line; bills need three lines including a far-off one at the top; villagers sell nothing past the research ladder's level II.
- **Snowball.** A fast player can't out-gear everyone with the shared layer, because what they fund is everyone's. Their personal edge is Masterwork: the last level of everything on an item, earned by trips. The world snowballing is the goal. Players who fall behind get everything the world knows on day one. Getting ahead is never worse.
- **Optimise the fun out.** Most efficient boring play: one player farms one material and dumps it. Bills with three lines from two tiers block that. Free-riding is possible and acceptable: a slower player using the Archive is catch-up working.
- **Presence.** The Archive is the Season's centrepiece and gets the biggest screen. Slots are small and simple; Masterwork is one action with a visible stamp.
- **Skill floor and ceiling.** Floor: the least experienced player contributes Bones and iron, sees their name in the credits, and gets the whole Archive. Ceiling: far-off trips for top research and Masterwork, and loadouts for specific fights.

## Playtest prediction

Written before the playtest. The measures are the [shared ones](README.md#measuring-the-playtest); the two layers are logged separately.

- **Primary (personal layer):** at least **3** of the players who reach their milestone Masterwork **two or more items** (first and second Masterworks counted separately, since only the first adds levels), and at least **30%** of their sessions after the milestone include a paid operation as the README defines it (contributing or Masterworking), the same measure as A and C. **The deciding number for the personal layer is the Masterwork-only share: at least 15% of post-milestone sessions include a Masterwork**, together with tier 4 spend, so the personal layer can fail on its own; free slot swaps are reported separately.
- **Primary (shared layer):** milestones of **World Sharpness II by day 3**, **the first far-off line filled by day 10** and **World Sharpness IV by day 14**. Missing two of these means the bills are too big for the group, and the prepared lever is that every bill not yet filled drops by a third, pulled at the first missed milestone.
- **Horizon estimate:** the Archive lasts about **2 weeks** of group play (the 55 player-hour target). The fastest player's personal horizon after full Netherite is about **15 hours**: two Masterworks each on a sword, a bow and a chestplate, and one on the other armour (about 9 trips, no Heavy Cores needed; six take an item the last step, three add a second effect).
- **Easier mobs:** damage taken per player-hour is logged before and after each World stat level, so a rise in comfort can be told apart from enchanting effort.
- **Spread:** no single player supplies more than **40%** of the value of completed bills, and at least **4 of 6** supply at least 5% each. If the first fails by day 7, the per-player share limit (per line; far-off lines exempt) turns on; it's one of the two pre-registered in-playtest levers.
- **Top-step spend on later items** ([measure 4](README.md#measuring-the-playtest)): Masterwork spend on each player's second and later items is at least **50%** of their first item's.
- **Sink floor:** at least **2 stacks of tier 1 and 2 materials per player per week** go into bills in weeks 1 and 2, with a week-3 reading to show what's left once the Archive nears completion.
- In a fixed post-playtest question ("Did your effort feel like it mattered to you, to the group, or neither?"), gear-focused players don't answer "neither".

**Lever discipline:** only two levers may fire during the playtest, both pre-registered and both on the shared layer: the bill-size cut at the first missed milestone, and the per-player share limit if the spread check fails by day 7. Every other prepared lever below is a recommendation for after the playtest, so the result can still be credited to the design as written. First and second Masterworks are counted separately, and only first Masterworks count for the power reading.

## Risks

- **Gear-focused players may still feel diluted.** Masterwork (the top level of everything on the item, two effects and their name) and credit are the answer; the fixed question tests it. If gear-focused players answer "neither", or tier 4 spend after the milestone (logged separately for research and for Masterwork) falls short of the prediction, the prepared lever is **+1 slot per Masterwork**, which also gives the second Masterwork a power reason.
- **Stats rise for everyone,** so vanilla mobs get easier for all, capped one level below vanilla's maximum. Eras tune difficulty later.
- **"Power is earned" is read at the group level here.** A player who never fights still gets World Sharpness IV and World Protection III, because someone in the group earned them. That is a deliberate reading of [ADR-0003](../../../adr/0003-world-progression-through-portals.md) (the world progresses, not the player); only the top level is personal. If it feels wrong in play, the prepared lever is that world stats apply only to players who have contributed at least 1% of the value of completed bills.
- **Hearts of the Sea could become everyone's first Masterwork.** Any of the five materials takes an item the last step, and a Heart of the Sea is the cheapest and safest. The log shows which material each first Masterwork uses; if they cluster on Tidal, the prepared lever is that the last step on weapons and armour needs a material other than a Heart of the Sea (tools keep it, so Fortune III never needs combat).
- **The ore sink ends with the Archive.** Once research completes, slot swaps are free and Masterwork uses only far-off materials, so tier 1 and 2 demand falls; the week-3 reading shows it, and the prepared lever is that re-slotting costs a small tier 2 material once the Archive is complete.
- **Free swapping makes slots a loadout rather than a build.** A real trade against A and C.
- **A finished Archive is a shared endpoint.** The 55-hour target is meant to last the playtest; later Seasons add entries with each Era.
- **World state and a research screen are new tech.** B is the largest build of the three: world data, the bill and credit screens, item rewrites when touched, and two combat hooks.

## Build size

Large. World saved data for knowledge, bills and credit; the Archive screen with research bars and credits; three featured bills in a suggested order; contribution handling (exact items per line, overflow handed back, proportional credit); chat broadcasts naming contributors; two world stat hooks and client sync; slot levels rewritten into items when they're touched and when a bill fills; Masterwork, effects and tooltips; level clamp for random sources; Mending and Unbreaking removal; four new enchantments as JSON; cartographer trade data; operation and contribution log; game tests.
