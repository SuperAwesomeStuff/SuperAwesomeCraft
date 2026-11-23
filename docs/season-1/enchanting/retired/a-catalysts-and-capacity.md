# Candidate A: Catalysts and Capacity

> **Retired.** Superseded by [the library design](library.md), which was itself replaced by [the slot design](../README.md). Kept for reference.

**Status: Idea.** One of three Season 1 candidates; see the [comparison](README.md). The [shared ground rules](README.md#shared-ground-rules) apply.

**In two sentences:** pay a fixed recipe for each enchantment level and fit it into the item's **Capacity**, a Power budget printed on every weapon and piece of armour. Books **found** in the world and registered with the **library** at any table unlock levels above II for everyone, and far-off materials **Attune** an item to raise its Capacity.

This is the closest candidate to Ascendant Arcana and Super Awesome Enchanting, with what failed taken out: no Relics, no XP, no item-destroying grindstone, no random rolls, and Capacity spent only on Power and Yield on weapons and armour (axes included, where Fortune spends Capacity too).

## How a player experiences it

1. **First evening.** You craft an Enchanting Table. Its screen lists every enchantment your item can take, each with its recipe, its Capacity cost and a one-line description. You put Efficiency II and Silk Touch on your iron pick with Redstone and Amethyst Shards. Tools have no Capacity, so there's nothing to think about.
2. **First week.** Your diamond sword shows **Capacity 0/10**. Sharpness V would cost 5 and Looting III 9, so you can't have both; you make a Looting sword for the mob farm and a Sharpness and Fire Aspect sword for fighting. Sharpness III is greyed out: *"Levels above II unlock when the library has a Sharpness book found in a structure chest, or 3 other Sharpness books from 2 players (1/3 so far)."* A friend finds one in a dungeon and registers it at a table; chat says *"<friend> added Sharpness to the library (9/22)"* straight away. Sharpness III and IV are open to everyone, at every table; Sharpness V also needs its tier 3 recipe.
3. **After full Netherite.** Looting III needs Echo Shards, so you buy an Ancient City map from a Cartographer and go. You bring back extra shards and **Attune** your diamond chestplate (Capacity 8 to 10), which now fits Protection IV and Steadfast II. The Echoing Attunement also makes you silent to Sculk while you wear it.

## Rules

### The table

- **Pick, pay, done.** Put in the item. The screen lists every valid enchantment and level with its recipe: a Catalyst amount, plus 1 Lapis at level I. Click to apply, if you can pay and it fits. No randomness, no XP, no bookshelf ceiling. Convenience enchantments cost only tier 1 or 2 Catalysts.
- **Buying a level costs that level's recipe plus the recipes of any lower levels the item doesn't have yet.** The ladder can't be skipped, with no extra clicks.
- **Books** come only from loot, librarians (Convenience books) and Extract; the table doesn't enchant plain Books. No book holds the top level of a Power or Yield enchantment with three or more levels (Convenience books, such as a librarian's Efficiency V, are unaffected). An enchanted book is applied at the table (the anvil only renames): you pick which of its enchantments to apply; those leave the book, and the book is used up when it's empty. Each goes on only if it fits the item's Capacity and doesn't conflict. If the item already has that enchantment at any level, the table refuses it (*"Extract Sharpness III first"*), so nothing paid for is lost and there's no swap rule to learn. Levels never combine (two Sharpness IV books don't make V).
- **Extract** one chosen enchantment from an item onto a plain Book (extracted books carry a hidden *Extracted* mark and never count for the library; never onto a book that already holds enchantments; the *Found*, *Counted* and *Extracted* marks are cleared whenever a book holds no stored enchantments, including grindstone output, and the table refuses Found books as targets, so a Found book can't launder another enchantment into the library; a game test covers it) (applying a book and extracting cost nothing but the Book). The item keeps everything else. Nothing is destroyed or duplicated. This is how you change a build, move an expensive enchantment to a new item, or give a build to a new player. The Extract tab previews the result before you confirm (*"Comes off as Looting II; the top step is paid again on the next item"*), and an item being smithed to netherite never needs it, since smithing keeps enchantments.
- **Attune** (below) is the table's third tab.
- **Concurrency:** the table holds nothing between uses. Each player's item sits in their own copy of the screen, so any number of players can use one table.

### The library (the shared layer)

- **The library is the world's record of which enchantments have been found.** It lives in world saved data and counts at every table, so a builder 5,000 blocks away benefits as much as the settlement.
- **A Found book registered with the library unlocks every level above II of its enchantment, for everyone. The top level also needs its tier 3 or 4 recipe.** That's the whole rule; the greyed-out line names whichever is missing. Levels I and II and every Convenience enchantment never need the library. **Applying a book never checks the library** (the book already carries its level), so handing down a Sharpness IV book always works.
- **Registering is one click:** drop a book on the table's Library tab and it comes straight back, stamped *Counted*; the unlock is immediate, permanent and announced in chat with your name ([shared rule](README.md#shared-unlocks-are-permanent-and-announced)). **Putting a Found book in the table to apply it registers it first**, so nobody throws away the server's unlock by using a book on their own sword. Chiseled Bookshelves stay vanilla decoration; a library room at the settlement is for show.
- **Only books found in structure chests are Found** (marked by a loot function the mod adds to every `chests/` and vault loot table, not archaeology, trial spawners or mob equipment, so it works whichever per-player loot mod fills the container; with per-player chests, every player gets their own rolls). The tooltip says *"Found: unlocks for the library"*, or on any other Power or Yield book *"3 of these, from 2 different players, count as one Found book"*: **three non-Found books of one enchantment, registered by at least two different players, count as one Found book**, so fishers and mob farmers push the library forward too, just more slowly. The Library list shows each enchantment's progress on that route. Extracted books never count, and a *Counted* book never counts again. Filling the library means exploring dungeons, mineshafts, shipwrecks, strongholds, Bastions and Ancient Cities, and it's what turns one player's find into everyone's Sharpness IV. A Found book with several enchantments counts for each of them (the marks are tracked per enchantment on the book).
- **Far-off finds keep helping after the milestone.** A Found book from a far-off chest (an Ancient City, an End City or an ominous vault) also **halves that enchantment's top-step recipe for everyone**, announced in chat, the first time one is registered. So a fighter's late trips still push the world forward, not only their own gear.
- **Wind Burst** (the one treasure Power enchantment) can't be made at all until a Wind Burst book from an ominous vault (level I; each ominous vault rewards each player once, so anyone can get one, though nobody is guaranteed one) is registered; that one book unlocks every level.
- The table shows **Library 9/22**: how many of the 22 Power and Yield enchantments with a level III or higher (Wind Burst included) the library has unlocked, which are missing, the structures their books usually turn up in, and who registered each one.

### Capacity

- **Weapons and armour have a Capacity**, printed in the tooltip as `Capacity 7/10`. Only Power and Yield enchantments spend it. Convenience costs 0. The item stores only its Attunements; its Capacity is worked out from its material when read, so smithing diamond to netherite picks up netherite's numbers automatically.
- **Tools don't have Capacity.** Pickaxes, shovels, hoes and fishing rods have one Yield option each (Fortune or Luck of the Sea), and their choices come from Techniques; Fortune III and Luck of the Sea III are gated by their recipes (4 Echo Shards *or* 1 Heart of the Sea, so a builder never has to go to an Ancient City) and the library. **Axes are weapons too:** the rule players learn is *anything you hit things with has Capacity*. An axe uses the plain weapon row, and on an axe Fortune spends Capacity like Looting (3 per level), so a diamond axe chooses between a damage build (Sharpness V and Venom II, 9) and a wood-and-log build with Fortune III (9). A fully Attuned netherite axe holds everything, which is accepted for a side weapon.

| Material | Weapons | Armour |
|---|---|---|
| Wood, Stone, Leather, Chainmail, Copper | 4 | 3 |
| Iron, Gold, Turtle Shell | 7 | 6 |
| Diamond | 10 | 8 |
| Netherite | 12 | 9 |

| Ranged and special | Capacity |
|---|---|
| Mace | 10 |
| Bow | 7 |
| Trident | 6 |
| Crossbow | 4 |

Capacity cost per level:

| Cost per level | Enchantments | Example at max |
|---|---|---|
| 1 | Sharpness, Smite, Bane of Arthropods, Sweeping Edge, Knockback, Fire/Blast/Projectile Protection, Warding, Steadfast, Power, Punch, Piercing, Quick Charge, Impaling, Density, Lunge | Sharpness V = 5 |
| 2 | Protection, Fire Aspect, Thorns, Breach, Wind Burst, Venom, Frostbite | Protection IV = 8 |
| 3 | Looting, Flame, Channeling | Looting III = 9 |
| 4 | Infinity, Multishot | Infinity = 4 |

General Protection costs twice what a specialised Protection does, so on armour the choice is real: one broad defence, or a cheaper specialised one with room for Thorns or Steadfast.

**On swords, spears, maces, bows and armour, every budget stays below what the item could usefully hold, even fully Attuned netherite**, so those items always keep a choice and every Attunement matters:

| Item | Everything it could usefully hold | Diamond | Fully Attuned netherite |
|---|---|---|---|
| Sword | Sharpness V, Fire Aspect II, Looting III, Knockback II, Sweeping Edge III, Venom II = 27 | 10 | 18 |
| Mace | Breach IV, Wind Burst III, Fire Aspect II = 18 | 10 | 16 |
| Bow | Power V, Punch II, Flame, Infinity = 14 | 7 | 13 |
| Any armour piece | Protection IV, Thorns III, Steadfast III = 17 | 8 | 15 |

Crossbows and tridents have so few Power enchantments (Multishot or Piercing plus Quick Charge; Impaling and Channeling) that their small budget is their whole choice; Attuning them is rarely worth it.

**Example builds:**

- Diamond sword (10): Sharpness V, Fire Aspect II, Knockback I = 10 (fighting); Looting III, Knockback I = 10 (farming); Sharpness III, Venom II, Knockback I = 8 (raids and players; Venom does nothing to spiders or undead).
- Diamond chestplate (8): Protection IV (8); Fire Protection IV and Thorns II (8) for the Nether; Protection II, Thorns I and Steadfast II (8) as an all-rounder; Warding IV and Steadfast III (7) for raids with witches and evokers.

### Attunement (the personal horizon)

- **Each weapon or armour piece can be Attuned up to three times** (tools can be Attuned up to three times too, for the effect only, so a pickaxe can carry Tidal or Voidtouched), each time with a **different** one of [the shared five far-off materials](README.md#tier-4-fair-access-and-shared-upgrade-materials). Each Attunement gives the item that material's Convenience effect (silent to Sculk, faster swimming, a drop that can't be destroyed, free Ender Pearls, higher Wind Charge jumps) and a mark, and **+2 Capacity**. The Attune tab shows the effect first and then what the extra Capacity now fits (*"now fits Thorns II"*), so the trip's reward is concrete.
- A fully Attuned netherite sword (18) holds Sharpness V, Looting III and Fire Aspect II, and still can't have Knockback, Sweeping Edge and Venom too. With no durability, several specialised items are worth owning, so the horizon is wide (many items, three places each, each with its own effect) rather than tall.
- Smithing an item up to netherite keeps its Attunements (smithing copies item components).

### Recipes

**Each level names its materials on screen**, adapted from Super Awesome Enchanting so the material suggests the effect and climbs the source ladder as the level rises: tier 1 at the bottom, tier 2 and 3 in the middle, and a tier 3 or 4 material at the top level of every Power or Yield enchantment with three or more levels (tier 4 for Yield, per the shared rule). Nobody memorises the table; the screen shows each recipe before you pay. Convenience never goes past tier 2.

| Enchantment | I | II | III | IV | V |
|---|---|---|---|---|---|
| Efficiency | 2 Redstone | 4 Redstone | 1 Redstone Block | 2 Redstone Blocks | 4 Redstone Blocks |
| Silk Touch | 4 Amethyst Shards, 2 Redstone | | | | |
| Feather Falling | 4 String | 8 Wool | 4 Hay Bales | 2 Diamonds | |
| Fortune | 4 Copper, 2 Iron | 4 Quartz, 2 Gold | 4 Echo Shards or 1 Heart of the Sea | | |
| Looting | 2 Gold, 4 Bones | 1 Gold Block, 2 Ghast Tears | 4 Echo Shards | | |
| Sharpness | 4 Amethyst Shards | 8 Amethyst Shards | 2 Diamonds | 2 Breeze Rods | 1 Wither Skeleton Skull, 4 Quartz |
| Protection | 4 Iron | 2 Diamonds | 1 Nautilus Shell, 4 Prismarine | 1 Netherite Scrap, 2 Diamonds | |
| Fire Aspect | 2 Blaze Powder | 1 Blaze Rod, 2 Magma Cream | | | |
| Warding | 2 Glass Bottles, 2 Redstone | 4 Glowstone Dust | 1 Ghast Tear | 1 Echo Shard | |
| Venom | 4 Spider Eyes | 2 Fermented Spider Eyes, 1 Pufferfish | | | |

The full table follows this rule and is written once this candidate is chosen.

### Villagers and trading

- Librarian enchanted-book trades are replaced (trades are data since 26.1) with **Convenience books**, plus Bookshelves and Convenience Catalysts. (Bought books never count for the library.) **No Power or Yield books are sold**, and smiths' equipment trades roll at most level II. The Emerald route buys comfort, not power.
- **The grindstone refuses items with any Power or Yield enchantment** (*"Use Extract at the Enchanting Table"*), so a misclick can't strip anything paid for; Extract is the free, lossless way to remove one.
- **The top step is paid per item.** Extracting a top-level enchantment of three or more levels (Looting III, Sharpness V, Protection IV and so on) gives a book **one level lower** (Looting II, Sharpness IV); the top step is paid again with its recipe on whichever item it goes on next. Nothing else is lost, and no book can hold a top level. Without this, one Looting III could rotate between players on a book and the Echo Shard sink would collapse; with it, every top level in the world is one paid recipe. Lower levels and one-level enchantments (Infinity, Flame) extract whole and can be given away, which keeps the catch-up path. The top level's recipe line says *"Top step: paid per item"* before you pay. A finished item can still be lent, so a Looting III sword can be passed around; that's ordinary gear-sharing, and the operation log shows it. This is A's identity against C: in A the top of every build is consumed; in C it's a rune you keep.

### Familiarity and Techniques

Weapons and armour show `Capacity 9/10`. Pickaxes, axes and shovels show `Familiarity 3 · Techniques 1/2`; an axe shows Capacity on a separate line, and the two never trade.

## Design lenses

- **MDA.** Mechanics: fixed recipes, a printed budget, a library of found books, Attunement. Dynamics: players plan builds, own or swap several specialised builds, explore structures to fill the library for everyone, and travel to far places for named materials and effects. Aesthetics: **mastery** (knowing your build) and **expedition** (a named trip with an effect to show for it).
- **Lodestar.** Fighters bring back tier 4 materials; explorers fill the library from structure chests, which unlocks levels above II at every table and is announced in chat; builders get every Convenience enchantment on night one. Pillars: Convenience is cheap, Power is earned; the library adds Progress is shared.
- **Borrowed.** Ascendant Arcana's capacity is a single-player design; the shared library (Apothic's library, Botania's unconsumed books, Ascendant Arcana's own treasure-book rule) makes it social here. Zelda's Great Fairies tie upgrades to places; Attunement copies that.
- **Economy.** Taps: mining, mobs, structures (Found books), fishing (books to apply). Converter: the table. Drain: Catalysts, consumed once per level, and the top step again on every item it goes on. Inventory: Capacity on each item, found books in the library. Each level's materials are shown on screen, so the hard decisions are about Capacity, not currencies. Exploit checks: random books cap at II; only structure-chest books count for the library (or three ordinary books from two players, each stamped once counted); no book holds a top level, and extracting one steps it down; Echo Shards come from per-player chests; extraction can't duplicate.
- **Snowball.** Fortune leading to diamonds leading to more enchanting is slowed by Fortune III needing Echo Shards and a found Fortune book. Players who fall behind get every Convenience enchantment at full strength, every level the library has unlocked wherever they live, and lower-level books handed down. Getting ahead is never the worse choice.
- **Optimise the fun out.** Most efficient boring play: one maxed sword and one maxed armour set. Capacity stays below every sword's, spear's, mace's, bow's and armour piece's useful total even fully Attuned, so no single main item does everything. In practice a fully Attuned sword fits the three enchantments that matter most, so the remaining choice is small; Capacity and cost numbers are the lever if second builds don't appear.
- **Presence.** The table is Season 1's centrepiece and gets a full custom screen with four tabs (enchant, extract, attune, library). The library is a tab and chat announcements; a library room at the settlement is optional decoration.
- **Skill floor and ceiling.** Floor: the screen shows everything, including what's locked and why; Convenience is cheap; late arrivals inherit everything the library has unlocked. Ceiling: build planning within Capacity, and surviving an Ancient City or the End.

## Playtest prediction

Written before the playtest. The measures are the [shared ones](README.md#measuring-the-playtest).

- **Headline: second items** ([measure 4](README.md#measuring-the-playtest)), the real test of A: each milestone player builds at least **2 more items carrying a paid top step** by day 14. If they don't, a consumed top step is deterring second builds rather than creating them. Library unlocks after each player's milestone are logged too, to show whether the shared layer still matters by then.
- **Primary:** players who reach their milestone average **3 or more Attunements** each, and at least **30%** of their sessions after the milestone include a paid enchanting operation.
- **Horizon estimate:** for the fastest player, about **20 hours of play after full Netherite** to Attune a sword, a chestplate and leggings fully and a helmet and boots once each (about 11 Attunements, no Heavy Cores needed), and to get Looting III and Fortune III.
- **Sink floor:** at least **1 stack of tier 1 and 2 Catalysts per player per week** in weeks 1 and 2.
- At least **4 of the 6** regular players run **two different builds for the same slot** in the same week by day 7 (two swords or chestplates, or one item rebuilt by extracting and re-applying).
- The library unlocks **14 or more** of its 22 enchantments by day 14 **through Found books** (unlocks are logged by route, Found or tally, and only the Found route counts here) (per-player chests make books common, so the bar is set high enough that time alone shouldn't pass it), from books registered by at least 3 different players. Protection IV as the default chestplate build is watched in the log; if almost every chestplate carries it, Protection's cost is the lever.

If players stop after one maxed set, Capacity isn't creating demand for second builds; the next horizon then belongs to Era content, not more enchanting.

## Risks

- **A fast player finishes their items and is done.** Accepted for Season 1; the estimate above is the test. The real fix is Eras.
- **A missing book blocks a branch.** If nobody finds a Looting book in a structure for a week, Looting III waits. Per-player chests make books common, so this should be rare; the log shows how long each unlock took, and lowering the three-book rule to two is the prepared lever. The opposite risk, an AFK fish farm filling the library through the tally route, has the opposite lever: raise it to 5 books from 3 players.
- **Farm books skip the Catalyst sink at levels I and II.** Fished books, mob-farm gear and smiths' equipment trades (Sharpness II, Looting II) can be applied directly, replacing the tier 1 and 2 recipes those levels would cost. The sink floor tests it; if it fails, the prepared lever is that non-Found books can only be registered, not applied, above level I.
- **The first registrant gets the chat credit**, even though per-player chests give everyone their own books. It's a harmless race for a name; the measure (books from at least 3 different players) shows how widely it spreads.
- **A is close to C in shape.** Both have a per-level cost, an item budget and a top level behind far-off materials. The playtest choice between them is **consumed against reusable** (A's top step is paid on every item; C's Grand runes come out intact), and **a library that unlocks levels** (A) against **giftable runes** (C).
- **Recipe sprawl.** About 45 enchantments at up to 5 levels is roughly 130 recipes, though the screen shows each one, so nothing needs memorising.

## Build size

Large, the lightest of the three. The library is a table tab plus permanent unlocks in saved data; the shared base (clamps, new enchantments, upgrade effects, table screen) is most of the work. Capacity data component and tooltip; a four-tab table screen (enchant, extract, attune, library); the Found mark on structure-chest books; top-step rule on extraction; the library tab with Found, Counted and Extracted marks, a non-Found tally and permanent unlocks in world saved data, with chat announcements; Capacity read from mod item tags per material (covering spears, the mace and turtle shells); level clamp for random sources; Mending and Unbreaking removal; four new enchantments as JSON; the five shared upgrade effects and item-model marks; librarian and cartographer trade data; operation log; game tests for budget limits, incompatibilities, extraction, the Found, Counted and Extracted marks, and the level clamp.
