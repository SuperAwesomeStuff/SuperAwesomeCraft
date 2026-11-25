# Season 1 enchanting: slots

**Status: Leaning.** The problem statement and the shape of the design (slots, Era-gated prices, durability with easy repair) are agreed. The slot rules and recipes are agreed; the numbers are a first pass to tune in the playtest. The list of new enchantments is still being researched. It replaces [the library design](retired/library.md) and three earlier candidates, all kept in [retired/](retired/).

## The problem

Season 1 is built on what happened in the September 2026 playtest, not on theory. That server didn't run vanilla enchanting. It ran the Super Awesome Enchanting catalyst table (pick an enchantment, pay one catalyst per level and 1–5 XP levels), Netherite was Unbreakable by default, any item could be made Unbreakable for a Netherite Ingot, and Villagers sold the catalysts for Emeralds. Four problems showed up:

1. **It ran out.** The fastest player had full Netherite with maxed enchantments at about 6 hours of play. After that enchanting offered nothing to work toward, and the players who maxed out first left soonest.
2. **Everyone ended up with the same gear.** The two players who went furthest had the same armour, enchantment for enchantment. One put Unbreaking III on 14 items that were already Unbreakable. With no limit, enchanting was a checklist, not a set of decisions.
3. **It could be bought.** Villagers sold the catalysts, so enchanting was one more thing to spend Emeralds on (the vision's "one currency" root cause). The top trader made over 2,400 trades.
4. **It needs a new cost.** XP is gone, so XP levels can't be the price any more.

Not on the list:

- **Randomness.** The catalyst table already removed it and nobody missed the vanilla table: no one earned the "Enchanter" advancement.
- **Convenience tied to Power.** Builders were pulled into the grind by Unbreakable sitting behind Netherite, not by enchanting. Durability with easy repair (below) removes that pull.
- **Shared progress.** It's a pillar of the whole game. Enchanting only has to avoid harming it, not drive it.
- **Material sink.** Players never ran short of ore; they ran short of goals. A sink that matters follows from fixing problem 1.

## What counts as fixed

Written down before the playtest so it can actually fail:

- The fastest player takes at least **three times as long** to reach max Power gear (about 18 hours instead of 6).
- Most players are **still spending materials on enchanting in their second week**.
- Players end up with **visibly different enchantments** on the same kind of item.

## How it works

### The table

- The Enchanting Table uses the vanilla shape with **Amethyst Blocks for the Obsidian and Lapis Lazuli for the Diamonds** (1 Book, 2 Lapis, 4 Amethyst Blocks), so enchanting starts in the iron age, before diamonds.
- **All enchanting happens at the table.** Crafting stays vanilla: no tool parts, no smeltery. The screen copies Ascendant Arcana's: the item goes in on the left, you scroll a list of enchantment tiles in the middle, the selected one is described (with its numbers) top right, and you put its materials into two slots bottom right, which lights up the Enchant button. Removing an enchantment needs a second click to confirm. Books can't go in the item slot; found books go in a material slot as vouchers (below).
- Enchanted books are never applied at the anvil. The anvil only renames, for free.

### Slots

Every item has a limited number of slots, so you have to choose what goes on it.

- **Every enchantment uses one slot, at any level.** Efficiency I and Efficiency V both take one.
- **The material sets the slots:** wood, stone, gold and leather 1; copper, iron, chainmail, bows, crossbows, shields, fishing rods and turtle shells 2; diamond, tridents and maces 3; Netherite 4. Smithing an item to Netherite keeps its enchantments and gains the extra slot.
- **Three one-off upgrades add a slot each**, once per item: a Diamond Block with a Gold Block (Overworld), a Wither Skeleton Skull (Nether) and a Chorus Flower (End). That caps a Netherite item at 7.
- **Removing an enchantment at the table (or a grindstone) frees its slot, but the materials are lost.** A bad choice is never permanent, but swapping back and forth isn't free.

A diamond pickaxe fits Efficiency, Unbreaking and one of Silk Touch, Fortune or Excavating, so builder comfort fits on a diamond tool, and the third slot is the real choice.

### Mixing damage and protection enchantments

Vanilla makes the damage enchantments and the four Protections exclusive. Here **any of them can go on the same item**, because each one already takes a slot: a sword with Sharpness and Smite gives up a slot that could hold Looting or Unbreaking. There is no shared level cap; playtesting showed the slot cost is enough.

- **Damage:** Sharpness, Smite, Bane of Arthropods, Bane of Illagers, Breach and Density.
- **Protection:** Protection, Blast, Fire and Projectile. Vanilla's 80% cap on enchantment protection still applies.
- **Exclusivity stays only where effects contradict:** Silk Touch with Fortune or Smelting, Fire Aspect with Frost Aspect, Riptide with Loyalty or Channeling, Depth Strider with Frost Walker, Multishot with Piercing.

### Prices

Every recipe is in [slots and recipes](recipes.md), which also has the one-screen summary for the wiki.

- **Each enchantment has one material, and each level costs more of it**: 16 for level I, then 16 more per level (Redstone for Efficiency, Amethyst for Sharpness, Lapis for Fortune, Copper for Unbreaking). A few rare materials cost a flat 4 per level (Blaze Rods for Fire Aspect, Ender Pearls for Reach, TNT for Excavating). This follows how Tinkers' Construct prices its stat modifiers (45 Redstone a level for Haste, 36 Quartz for Sharpness), with a rising cost so farms make low levels cheap but not high ones.
- **Levels IV and V also need 4 Blaze Rods each**, so the top levels wait for the Nether. Efficiency is the exception: mining speed is Convenience.
- **The End lives in the last slot upgrade** (a Chorus Flower), so maxed gear still needs the End.
- Materials are mined, dropped, found or simple to craft (TNT), and share themes so they're easy to remember: redstone is speed, lapis is luck, slime is push, blaze rods are fire. Where possible no material repeats on one item's list.
- **Villagers can sell the bulk materials, but never Blaze Rods, Wither Skeleton Skulls, Chorus Flowers, books for combat or loot enchantments, or enchanted gear.** The climb is protected by slots and the Era materials, not by every ingredient.

### Breakpoints

Some thresholds matter more than the levels between them. They're placed on purpose:

- **Instant-mining stone stays as in vanilla:** a diamond pickaxe with Efficiency V reaches 34 mining speed, stone needs 45, and Haste II from a full beacon lifts it to about 48. Deepslate never instant-mines. Efficiency V is all Overworld, but Haste II needs the Wither, so this lands on the group's beacon: a block players build, which helps whoever stands near it.
- **Protection IV and Feather Falling IV** need Blaze Rods, so they arrive with the Nether.
- **Excavating** costs TNT, so builders get it early.
- **Fortune III and Looting III** only go to III, so they're Overworld: 96 Lapis each. Fortune III will be the first thing people chase, and it shouldn't send anyone rushing to a blaze spawner. Watch them in the playtest, since they multiply income.

### Durability

Durability returns, but it's never a big deal:

- **Gear never breaks.** At 0 durability it stops working until it's repaired. Nothing is lost.
- **Repair in the crafting grid**: the item plus the material it was made from, in any arrangement, free and with no XP. **Each item restores 50%.** Netherite gear repairs with **Diamonds**, since Netherite Ingots are too dear to spend on repairs.
- Items keep **vanilla durability** (a diamond pickaxe has 1,561 uses). Every repairable item's tooltip says what repairs it. Gear vanilla can't repair gets a material: bows, crossbows and fishing rods use String, tridents Prismarine Shards, shields, shears and flint and steel Iron Ingots, brushes Copper Ingots. Elytra use Popped Chorus Fruit instead of Phantom Membrane.
- **Unbreaking returns** and uses a slot, so fewer repairs competes with other enchantments.
- **Mending is gone.** It would cancel the repair cost, the way Unbreakable did in the playtest.

### New enchantments

- **Excavating** (picks and shovels): digs the 3×3 around the face of the block you hit. Every block costs durability and digging is slower, so it can't instant-mine. It only kicks in (and only slows you down) on blocks the tool is good at, so a pickaxe on dirt digs one block at normal speed. **Crouch to dig a single block.**
- **Felling** (axes): cuts a whole tree. Every log costs durability. Crouch to cut one log.

Two rules for anything new: **it must do something no existing enchantment does** (nothing that's a better or sideways copy of a vanilla one), and **if it does the same thing as a vanilla enchantment, it uses that name** and is just allowed on more items. Season 1 ships a broad list and the playtest shows which ones get used. Candidates and sources: [enchantment candidates](../../research/enchantment-candidates.md).

| Enchantment | Items | Max | Group | What it does |
| --- | --- | --- | --- | --- |
| **Excavating** | Pick, shovel | I | Convenience | Digs 3×3 (above) |
| **Felling** | Axe | I | Convenience | Cuts a whole tree (above) |
| **Reach** | Chestplate | II | Convenience | +1 block reach per level for breaking and placing blocks. Never affects hitting mobs |
| **Magnetism** | Any tool, weapon | I | Convenience | Pulls the drops from *your own* breaks and kills to you, not every item nearby, so hoppers and farms behave as in vanilla |
| **Smelting** | Pick, shovel, axe | I | Convenience | Mined blocks drop their smelted form (sand to glass, ore to ingot). No bonus drops. Exclusive with Silk Touch |
| **Harvesting** | Hoe | I | Convenience | Tills 3×3, and right-clicking a ripe crop harvests and replants it |
| **Step Assist** | Boots | I | Convenience | Step up full blocks without jumping |
| **Beheading** | Sword, axe | III | Convenience | 10% chance per level for a player's kill to drop the mob's head. Vanilla heads (zombie, skeleton, wither skeleton, creeper, piglin, dragon) always work. With the Vanilla Tweaks *More Mob Heads* data pack installed, every mob it covers works too, with the right variant ([research](../../research/more-mob-heads.md)) |
| **Bane of Illagers** | Sword, axe, spear, mace | V | Power | Extra damage against illagers. Can sit beside the other damage enchantments |
| **Frost Aspect** | Sword, axe, spear, mace | II | Power | Slows the target instead of burning it. Exclusive with Fire Aspect |
| **Rebounding** | Chestplate | III | Power | Knocks melee attackers away. Can sit beside Thorns, which damages instead |
| **Bulwark** | Shield | II | Power | No knockback while blocking, and a chance to disable the attacker's weapon. Shields' first real choice |

**Vanilla enchantments on more items** (same name, same effect, nothing new to learn):

- **Breach** on swords, axes and spears as well as maces: armour piercing. It takes its own slot beside Sharpness: strong against armoured targets, wasted against unarmoured ones.

Left out on purpose: Icy Thorns (too close to Thorns), Air Affinity (no useful way to fly and mine in a vanilla client), and anything the research flags as overpowered (bonus ore from stone, extra fishing loot, a second Looting, raw max health).

### Found books, Villagers and curses

- **A found enchanted book is a voucher.** Put it in a material slot and it pays one level's material of an enchantment it holds, then loses that level (a used-up book becomes a plain Book). Blaze Rods for levels IV and V are still needed, and the enchantment still needs a slot.
- **Random sources (loot, fishing, mob drops) cap every enchantment at level III**, the highest level that doesn't need Blaze Rods, so found books are an early boost without skipping the Nether.
- **Librarians sell Convenience books only.**
- **Curses come only from loot, don't use a slot, and can be removed at the table for a material cost.**

## Who it serves

| Motivation | What it gets |
| --- | --- |
| Gear and power | A climb with a step in every Era, and slot upgrades as long goals |
| Growth with no cap | Slot upgrades and Era ceilings keep growing through the whole game, up to a cap |
| Huge projects | Cheap early Efficiency and Silk Touch, Excavating and Felling, and the instant-mining beacon |
| Pure building | Convenience can be bought, and repairs are cheap and never lose an item |
| Collecting | Many enchantments to try, swap and keep different tools for |
| Being near the group | The beacon breakpoint is a shared build. Enchanting doesn't force sharing, but doesn't get in its way |

## Playtest measures

1. **Pace:** hours to max Power gear for the fastest player, and how many reach it.
2. **Spending:** materials spent on enchanting, repairs and slot upgrades per player per week.
3. **Variety:** how different players' enchantments are on the same kind of item, how often they remove and swap, and whether mixed damage and protection builds appear. Watch that general Protection still gets used, and that Breach with Sharpness isn't the only sword anyone makes.
4. **Usage:** which enchantments get picked and which never do, especially new ones.
5. **Repairs:** how often gear hits 0, and whether repairing feels like a chore.
6. **Retention:** players still enchanting in week two.

## Building it

**Built (mod 0.1.9, branch `season-1-enchanting`):** the slot rules, the table screen, every recipe, the slot upgrades, found books, durability that never breaks gear with grid repair, the loot and Villager limits, and these new enchantments: Excavating, Felling, Smelting, Beheading, Reach, Step Assist, Bane of Illagers, Frost Aspect, Rebounding, Bulwark (knockback resistance only) and Breach on more weapons. **Not built yet:** Magnetism, Harvesting, and Bulwark's weapon disable. Recipes live in `EnchantingRecipes.java` and slot counts in the `superawesomecraft:enchant_slots/1..4` item tags.

- The table screen is ours (client and server): item slot, enchantment list, recipe with missing items in red, and a slot display.
- Slots and slot upgrades are item data components. Starting slots come from a data file keyed by item tag, so materials can be tuned without code.
- Every cost (each enchantment's material, the Blaze Rods for high levels, curse removal and the slot upgrades) comes from `config/superawesomecraft/enchanting.json`. The mod writes it with the numbers in [recipes.md](recipes.md) on first start; edit it and run `/reload`. The server sends it to players so the screen matches. Leaving an enchantment out means the table can't make it.
- Recipes are data: one file per enchantment, with per-level costs and the slot type.
- Durability-at-zero: items at 0 stop working instead of breaking (hook the break event and the item's use and attack paths). Repair is a shapeless crafting recipe type.
- Random-source caps hook the vanilla loot functions and enchanting helpers (`EnchantRandomlyFunction`, `SetEnchantmentsFunction`, `EnchantmentHelper`).
- Excavating and Felling are Java. Crouching falls back to vanilla single-block behaviour.

## Open questions

- **Amounts:** are 32 and 4 per level right? The [recipes](recipes.md) draft is a first pass to tune in the playtest.
- **Fortune III and Looting III** in the Overworld: is 96 lapis enough of a brake on income multipliers?
- **Mixing:** with no shared cap, does a sword with three damage enchantments or armour with every Protection turn out too strong, or is the slot cost enough?
- **New enchantments:** which ones need Java and can wait if time runs short (Magnetism, Smelting, Harvesting, Excavating, Felling and Bulwark's weapon disable all need code).
- **More vanilla on more items:** Looting and Quick Charge on bows, Multishot on bows, Infinity on crossbows, Thorns on shields?
- **Repairs:** is 50% per item right for every material, Netherite included?

## Sources

- [Tinkers' Construct](../../research/tinkers-construct.md) and [tool levelling](../../research/tool-levelling.md): slots, modifier prices, area tools
- [Durability](../../research/durability.md): broken-not-destroyed and repair models
- [Enchanting mods](../../research/enchanting-mods.md) and [Ascendant Arcana](../../research/ascendant-arcana.md)
- [Game Design 101](../../research/game-design-101.md)
- The retired designs in [retired/](retired/)
