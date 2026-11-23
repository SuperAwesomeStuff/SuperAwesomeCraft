# Season 1 enchanting: the library

> **Retired (November 2026).** Replaced by [the slot design](../README.md). A look back at the playtest showed that the library mostly served shared progress, which enchanting doesn't need to drive, and it did nothing about everyone ending up with the same gear. The Library tab, chipping in and spares are kept as Ideas in the [design map](../../../design-map.md).

**Status: Retired.** It replaced three earlier candidates, kept in this folder.

## What enchanting is for

> **Enchanting is how your gear grows through the whole game. It starts cheap in the iron age, climbs with materials from further and further away, and the library you build shows how far you've come.**

It has three jobs:

1. **Steady growth.** Instead of one late burst after diamonds, enchanting gives a long climb from the first evening to the far-off places last season skipped. Vanilla's incompatible pairs (Silk Touch or Fortune, Sharpness or Smite, the four Protections, Multishot or Piercing, Riptide or Loyalty) make owning several specialised tools worthwhile, which no durability makes affordable.
2. **Something to spend on.** With no durability and no XP, ore and time need somewhere to go ([vision.md](../../../vision.md#open-questions)). Writing books for the library takes large amounts of materials.
3. **A reason to build and explore.** A library is a building project, a landmark, and a visible record of progress, and every found book moves it forward.

### Why it starts early

Vanilla locks enchanting behind obsidian, which needs a diamond pickaxe. That gate paced XP levels, and XP is gone. It also packed everything into one late sprint (diamonds, enchanting, Fortune, Netherite grind), and it put Convenience such as Efficiency and Silk Touch behind the mining ladder, which breaks *Convenience is cheap*. So the table moves to the iron age, and only the high levels of Power enchantments wait on far-off materials.

### Progress lives in things, not flags

Nothing in this system is stored on the server or the world. A library is blocks and books. Whoever builds one uses it. The group can pool materials into one big library, and someone who wants to play alone can build their own and pay the full cost. Books are items, so they can be given, traded or lent, and that's the only way one library helps another.

## How it works

### The Enchanting Table

- **New recipe:** 1 Book, 2 Lapis Lazuli and 4 Amethyst Blocks. No diamonds, no obsidian. Finding a geode is a little exploring with no fighting.
- It looks the same but opens our screen. It's the one station for everything below. No contraption depends on it, so technical parity is safe.
- The table on its own can **write level I books** of every enchantment (except curses, Mending and Unbreaking).

### The library

- **Your library is every Chiseled Bookshelf near the table.** Shelves count up to 6 blocks away sideways and 3 up or down (vanilla bookshelves reach 2); blocks in between don't matter, so a library can be a proper room.
- **Books must be shelved to count.** A book in your inventory does nothing at the table. Progress lives on the shelves, where everyone who uses the library can see it.
- Only **enchanted books** count. Plain bookshelves are decoration; there is no "enchanting power" number.
- **Books are never used up**, and they can't be put on gear directly. The only way to enchant an item is at the table, from a book in your library.
- Several tables can share one library, and one bookshelf can serve several tables.
- A book counts at **its own level and every level below it**.
- **Book spines show what they are.** Books on Chiseled Bookshelves are coloured by group (Convenience, Yield, Power), and top-level books get a gold mark, so you can read a library at a glance (a small client asset).

### The Library tab

The table screen has a **Library tab** that works like a chest: it shows every slot of every Chiseled Bookshelf in range as one grid, and you move books between it and your inventory the way you would with any chest (click, shift-click, drag). So you never have to leave the table to shelve a book you just wrote.

- Empty slots show as empty, so you can see how much room the library has left.
- A **Sort** button arranges books across the shelves by group, enchantment and level. It's optional, so a builder who has arranged their shelves by hand isn't overruled.
- The tab only moves books in and out of shelves that exist. To grow the library, place more Chiseled Bookshelves.
- Moving a book through the tab counts as using that shelf, exactly as if a player had clicked it, so comparator outputs and other vanilla behaviour are unchanged.
- Hovering a book shows what it unlocks: "Enchant up to Efficiency III · Write Efficiency IV".

### Three things you can do at the table

| Action | Needs | Cost | Result |
|---|---|---|---|
| **Write** a new level | The level below on a shelf (nothing for level I) | Large: the book recipe, less any spares discount | A new enchanted book. Shelve it (from the Library tab) to unlock the next level. |
| **Copy** a book | That level or higher on a shelf | Half the book recipe | A spare book, to start or help another library |
| **Enchant** an item | That level or higher on a shelf | Small: the apply recipe | The item gets the enchantment. The book stays on its shelf. |

Someone who builds far away can copy books at the group's library and shelve them in their own, or write everything themselves at full price.

### Pooling a book

A Write doesn't have to be paid all at once. Anyone can put materials toward the book a table is writing, at any time, and it finishes the moment the recipe is complete.

- The part-paid book and its materials are stored in the table block, like a chest's contents. Nothing is stored on the server, and breaking the table drops what's in it.
- Materials put in can't be taken back out, so a pooled book can't be raided.
- One part-paid book per table; build another table to pool two books at once.
- The screen shows the recipe filling up, so anyone passing can see what the group is working toward and chip in.

### Spares

Every **other** book of the same enchantment on the shelves (a duplicate, or a lower level) takes **20% off the next Write's ordinary materials, up to 60%**. It never reduces the far-off (tier 4) material, and spares aren't used up.

So keeping a full set (Efficiency I to IV on the shelf while writing V) is worth doing, and duplicates from chests and fishing always have a use. A bigger library is a cheaper one to grow.

Removing an enchantment from an item is free at the table and refunds nothing. With a library, putting it back is cheap, so changing your mind is safe.

### What the costs look like

Every level has a fixed recipe, shown on the table screen before you pay, with missing items in red. Book recipes climb the source ladder; apply recipes are about an eighth of the book recipe. **The top level of every Power and Yield enchantment also costs a far-off (tier 4) material on every item**, so the sink keeps running after a library is full: without durability, materials get used up by making more things, not by maintaining one.

| Tier | Where | Example materials |
|---|---|---|
| 1. Surface and caves | The first evening | Lapis, Copper, Iron, Redstone, Amethyst, String, Bones |
| 2. Deep and structures | Deepslate, oceans, Trial Chambers, outposts | Diamonds, Gold, Prismarine, Breeze Rods, Sculk |
| 3. The Nether | Fortresses, Bastions, the wastes | Quartz, Blaze Rods, Ghast Tears, Netherite Scrap |
| 4. Far and dangerous | The places last season skipped | Echo Shards, Hearts of the Sea, Shulker Shells, Dragon's Breath, Heavy Cores |

| Example | Book recipe (write) | Apply recipe |
|---|---|---|
| Efficiency I | 16 Copper, 8 Lapis | 2 Copper, 1 Lapis |
| Efficiency V | 24 Gold, 8 Diamonds, 32 Lapis | 3 Gold, 1 Diamond, 4 Lapis |
| Silk Touch I | 32 String, 16 Amethyst, 16 Lapis | 4 String, 2 Amethyst, 2 Lapis |
| Fortune II | 32 Quartz, 16 Gold, 24 Lapis | 4 Quartz, 2 Gold, 3 Lapis |
| Fortune III | 8 Echo Shards, 16 Diamonds, 48 Lapis | 1 Echo Shard, 2 Diamonds, 6 Lapis |
| Sharpness V | 8 Shulker Shells, 16 Blaze Rods, 48 Lapis | 1 Shulker Shell, 4 Blaze Rods, 6 Lapis |

Writing books is the expensive, poolable part. Enchanting each item is cheap once the book is shelved (apart from the far-off material on top levels), which is how a group library pays off and how playing alone costs more.

### Convenience and Power

| Group | Enchantments | Rule |
|---|---|---|
| **Convenience** | Efficiency, Silk Touch, Aqua Affinity, Respiration, Depth Strider, Feather Falling, Soul Speed, Swift Sneak, Frost Walker, Lure, Loyalty, Riptide | Books cost only tier 1 and 2 materials, so a builder's own small library has Efficiency V and Silk Touch within the first few sessions. |
| **Yield** | Fortune, Looting, Luck of the Sea | The top level needs a tier 4 material, for the book and for every item. |
| **Power** | Sharpness, Smite, Bane of Arthropods, Protection (all four), Thorns, Power, Punch, Flame, Infinity, Fire Aspect, Knockback, Sweeping Edge, Multishot, Piercing, Quick Charge, Impaling, Channeling, Density, Breach, Wind Burst, Lunge | Higher levels need tier 3 materials; the top level needs a tier 4 material, for the book and for every item. |
| **Curses** | Binding, Vanishing | Loot only, as in vanilla. |
| **Removed** | Mending, Unbreaking | Gone with durability. |

**No new enchantments in Season 1.** Maximum levels and incompatibilities are vanilla's. New enchantments can come in a later Season.

This draft assumes stat enchantments (Efficiency, Sharpness, Protection, Power) stay as enchantments, which is option 1 of the [raw stats question](../../../vision.md#gear).

### Found books

- Chests, fishing, mob gear and trades still roll enchanted books and gear. **Power and Yield from random sources are capped at level II**, so an AFK fish farm can't skip the ladder. Convenience and curses roll as normal.
- A found book counts once it's shelved: it unlocks enchanting at its level and writing the next one. If the library already has it, it's a spare and makes the next Write cheaper.
- Treasure books that only come from loot (such as Wind Burst I from ominous vaults) work the same: shelve it and Wind Burst II can be written.
- Librarians sell only Convenience books.
- Generated chests are per player ([vision.md](../../../vision.md#adventuring)), using an existing mod (Lootr, SlashLoot or Phantom Loot; a 26.3 release must be confirmed).

### A library is just blocks

- Nobody owns a library, and nothing checks who wrote a book. Anyone who can reach the shelves can use them, and anyone can take a book out. Among friends that's fine, and it's part of what makes it physical. Taking a book out removes what it unlocked from that library until it's back. Someone will take one eventually, as a joke or by mistake; since copies are half price, the cheap protection is a group habit of keeping spares, which also make writing cheaper.
- If a library is lost, only the books are lost. Every enchantment already on an item stays.

## Carried over from the earlier drafts

- **No XP** (decided, built) and **no durability** (leaning, built in Season 1).
- **The anvil** only renames, for free. **The grindstone** takes one item at a time and strips enchantments without a refund.
- **Enchanted items can't be crafting ingredients** (for example a bow into a Dispenser), except in recipes that keep the item's data, such as dyeing.
- **Season 1 starts on a fresh world.** Mending and Unbreaking are removed from every loot table, trade and tag, and from any item when it's next touched.
- **Death is vanilla** until graves arrive.

The per-item far-off upgrades from the old candidates (Echoing, Tidal and so on) are dropped. The long horizon is now filling a library's top levels, which needs the same tier 4 materials.

## Who it serves

| Motivation | What they get |
|---|---|
| Pure building | Efficiency V and Silk Touch from tier 1 and 2 materials in their own library, no fighting |
| Being near the group | A shared library at the base, filled together; pooled books anyone can chip into |
| Having space of their own | Their own library at full price, or copies made at the group's library |
| Huge projects | A library is a build, and spares make a big one cheaper to grow |
| Collecting | Every level is worth keeping (spares), spines show the collection, and a full library is the long goal |
| Gear and power | Top levels behind tier 4 materials on every item, and a specialised tool for each job |
| Exploration | Found books unlock recipes; tier 4 materials send you to the places last season skipped |

## How the playtest checks it

The mod logs every write, copy, enchant and removal (who, what, cost). The playtest looks at:

1. **After each player's milestone** (first full Netherite or Dragon kill): tier 4 materials spent on books, and the share of their sessions with a paid enchanting action.
2. **Libraries:** how many get built, how many people contribute to each, and how many copies get given away.
3. **Pooling:** how many pooled books get finished, and how many different players contribute to each.
4. **Sink:** iron, gold, diamonds and lapis spent on enchanting per player per week, and tier 4 materials spent on items (not books) after each library's top levels are written.
5. **Retention:** days active after the milestone, against September 2026 (about 10 days and 54 player-hours in total).
6. **A question for builders:** "Did you feel you needed to fight to be comfortable?"

## Building it

- The table screen is ours (client and server). On open it scans the area for Chiseled Bookshelves and reads their books. That's a few thousand blocks once per open, so there's no ticking cost.
- The Library tab is a menu whose slots point at the shelves' own inventories, like a large chest spanning many blocks. The server stays authoritative, and two players using the tab at once see each other's changes. If a shelf is broken or added while the tab is open, the tab rescans.
- The table becomes a block entity that holds the part-paid book and its materials.
- Spine colours are a client model change for the Chiseled Bookshelf's book slots, driven by the stored books.
- Recipes are data: one file per enchantment level with its book and apply recipe, so tuning doesn't need code.
- Nothing extra is stored on items: they carry vanilla enchantments only.
- No world or server saved data at all.
- Random-source clamps hook the three places vanilla picks random levels (`EnchantmentHelper.getAvailableEnchantmentResults`, `EnchantRandomlyFunction`, `SetEnchantmentsFunction`), each with a game test.

## Open questions

- **Range:** is 6 sideways and 3 up or down right? Bigger allows grander libraries but blurs where one library ends.
- **Copy price:** half the book recipe is a guess. Too cheap and one library seeds everyone for free; too dear and loners can't be helped. Should spares discount copies too?
- **Spares:** are 20% each and a 60% cap right? Should they count from any shelf in range or only the same shelf?
- **Sort:** what order is clearest: by group, then enchantment, then level?
- **A max-gear endpoint is back.** Without Capacity, a player can eventually put every compatible enchantment at its top level on one item. Incompatible pairs and the far-off materials stretch the climb, but it has an end. Is that fine for Season 1, or does it need a limit later?
- **Do applied enchantments get dearer per item?** Right now the tenth Sharpness V sword costs the same as the first.
- **Every recipe** needs tuning, especially how quickly a solo builder reaches Efficiency V.

## Research behind this

- [Ascendant Arcana](../../../research/ascendant-arcana.md): fixed recipes and a library of Chiseled Bookshelves that unlocks enchantments
- [Enchanting mods](../../../research/enchanting-mods.md), [gear systems in games](../../../research/gear-systems-in-games.md), [server gear systems](../../../research/server-gear-systems.md)
- [Durability](../../../research/durability.md), [Game Design 101](../../../research/game-design-101.md)
