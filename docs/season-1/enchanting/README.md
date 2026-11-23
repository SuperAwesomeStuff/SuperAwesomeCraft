# Season 1 enchanting: slots

**Status: Leaning.** The problem statement and the shape of the design (slots, Era-gated prices, durability with easy repair) are agreed. Every number here is an untuned example. The list of new enchantments is still being researched. It replaces [the library design](retired/library.md) and three earlier candidates, all kept in [retired/](retired/).

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

- The Enchanting Table is **1 Book, 2 Lapis Lazuli and 4 Amethyst Blocks**, so enchanting starts in the iron age, before diamonds.
- **All enchanting happens at the table.** Crafting stays vanilla: no tool parts, no smeltery. Put an item in, pick an enchantment, see its recipe (missing items in red) and the slots it will use, then pay.
- Enchanted books are never applied at the anvil. The anvil only renames, for free.

### Slots

Every item has a limited number of slots, so you have to choose what goes on it.

- **Upgrade slots** hold stat enchantments. **One level uses one slot**, so Sharpness III takes three.
- **Ability slots** hold enchantments that change what the item does: Silk Touch, Fortune, Looting, Excavating, Felling and so on. Most items have one, so these compete with each other.
- **The material sets the starting upgrade slots** (untuned): iron and copper 2, diamond 3, Netherite 4. Every item starts with 1 ability slot. Smithing an item to Netherite keeps its enchantments and gains the extra slot.
- **Extra slots are one-off, expensive upgrades tied to an Era**, roughly one more upgrade slot per Era from a material only that Era has. A second ability slot comes only from something very far off (a Nether Star or a Heavy Core, for example). There is a hard cap, so there's still no "everything maxed" item.
- **Removing an enchantment at the table frees its slot, but the materials are lost.** A bad choice is never permanent, but swapping back and forth isn't free.

### Mixing damage and protection enchantments

Vanilla makes the damage enchantments (Sharpness, Smite, Bane of Arthropods, Breach, Density) and the four Protections exclusive so they can't all be stacked for free. Slots already stop that, because every level uses one. So **any of the damage enchantments can go on the same weapon, and any of the Protections on the same armour piece**, sharing its upgrade slots.

- A diamond sword with 3 slots can be Sharpness III (+2 damage against everything), Smite III (+7.5 against undead only), or a mix like Sharpness I and Smite II (+1 against everything, +6 against undead).
- An armour set with Protection II and Blast Protection II on every piece gets 32% reduction against everything and reaches vanilla's 80% cap against explosions. That's a specialist set, traded against general Protection IV (64% against everything).
- Vanilla's 80% cap on enchantment protection stays, so a specialist set can't go past it.
- **Exclusivity stays only where effects contradict:** Silk Touch with Fortune or Smelting, Fire Aspect with Frost Aspect, Riptide with Loyalty or Channeling, Depth Strider with Frost Walker, Multishot with Piercing, Infinity with Mending (which is gone anyway).
- The table screen says that these can be mixed, since vanilla players will expect them not to.

### Prices

- **Stat levels cost a pile of common materials** for each level, like Tinkers' Construct's modifiers. Higher levels also need an Era material, so the ceiling rises with each Era: for example Sharpness III with Overworld materials, IV with Nether materials, V with far-off ones.
- **Abilities cost a small recipe on a theme** (something soft for Silk Touch, something heavy for Excavating), so each one is a memorable milestone.
- **Power and Yield recipes use materials Villagers don't sell**, so a trading hall can't skip the climb. Convenience can be bought.
- **Lapis gets at least one real job.** It's the obvious fit for Fortune and Looting.

| Group | Examples | Where its ceiling sits |
| --- | --- | --- |
| **Convenience** | Efficiency, Silk Touch, Unbreaking, Feather Falling, Aqua Affinity, Respiration, Depth Strider, Excavating, Felling | Mostly Overworld. Efficiency V needs Nether materials (see breakpoints). |
| **Yield** | Fortune, Looting, Luck of the Sea | Top levels need far-off materials. |
| **Power** | Sharpness, Protection, Power, and the other combat enchantments | Each Era raises the ceiling by about one level. |

Example recipes (untuned, to show the shape):

| Enchantment | Slot | Cost |
| --- | --- | --- |
| Efficiency I–IV | Upgrade, per level | 32 Redstone, 4 Lapis |
| Efficiency V | Upgrade | 32 Redstone, 4 Lapis, plus a Nether material |
| Silk Touch | Ability | 16 White Wool, 8 Amethyst Shards, 1 Slime Block |
| Fortune I–III | Ability (levels II and III use no extra slot) | Lapis and Gold, plus a far-off material for III |
| Excavating | Ability | Iron Blocks, Pistons, Obsidian |
| Sharpness I–III | Upgrade, per level | Iron and Amethyst; IV and V need Nether and far-off materials |

### Breakpoints

Some thresholds matter more than the levels between them. They're placed on purpose:

- **Instant-mining stone stays as in vanilla:** a diamond pickaxe with Efficiency V reaches 34 mining speed, stone needs 45, and Haste II from a full beacon lifts it to about 48. Deepslate never instant-mines. Efficiency V needs Nether materials and Haste II needs the Wither, so this lands on the group's beacon: a block players build, which helps whoever stands near it. Efficiency IV stays cheap so building isn't slow before then.
- Other breakpoints to place deliberately as recipes are tuned: Fortune III on ores, Feather Falling IV, a full set of Protection IV, and Excavating.

### Durability

Durability returns, but it's never a big deal:

- **Gear never breaks.** At 0 durability it stops working until it's repaired. Nothing is lost.
- **Repair in the crafting grid**: the item plus the material it was made from, in any arrangement, free and with no XP. **Each item restores 50%.**
- Items keep **vanilla durability** (a diamond pickaxe has 1,561 uses).
- **Unbreaking returns** as an upgrade-slot enchantment, so fewer repairs competes with more Efficiency or Sharpness.
- **Mending is gone.** It would cancel the repair cost, the way Unbreakable did in the playtest.

### New enchantments

- **Excavating** (picks and shovels, ability): digs 3×3. Every block costs durability and digging is slower, so it can't instant-mine. **Crouch to dig a single block.**
- **Felling** (axes, ability): cuts a whole tree. Every log costs durability. Crouch to cut one log.
Two rules for anything new: **it must do something no existing enchantment does** (nothing that's a better or sideways copy of a vanilla one), and **if it does the same thing as a vanilla enchantment, it uses that name** and is just allowed on more items. Season 1 ships a broad list and the playtest shows which ones get used. Candidates and sources: [enchantment candidates](../../research/enchantment-candidates.md).

| Enchantment | Items | Slot | Group | What it does |
| --- | --- | --- | --- | --- |
| **Excavating** | Pick, shovel | Ability | Convenience | Digs 3×3 (above) |
| **Felling** | Axe | Ability | Convenience | Cuts a whole tree (above) |
| **Reach** | Any tool | Upgrade (I–II) | Convenience | +1 block reach per level, for blocks only, never for hitting mobs |
| **Magnetism** | Any tool, weapon | Ability | Convenience | Pulls the drops from *your own* breaks and kills to you, not every item nearby, so hoppers and farms behave as in vanilla |
| **Smelting** | Pick, shovel, axe | Ability | Convenience | Mined blocks drop their smelted form (sand to glass, ore to ingot). No bonus drops. Exclusive with Silk Touch |
| **Harvesting** | Hoe | Ability | Convenience | Tills 3×3, and right-clicking a ripe crop harvests and replants it |
| **Step Assist** | Boots | Ability | Convenience | Step up full blocks without jumping |
| **Beheading** | Sword, axe | Upgrade (I–III) | Yield | Chance for a kill to drop the mob's head. No bonus to Wither Skeleton skulls (that's a Power shortcut to the Wither) |
| **Bane of Illagers** | Sword, axe, spear, mace | Upgrade (I–V) | Power | Extra damage against illagers. Mixes with the other damage enchantments |
| **Frost Aspect** | Sword, axe, spear, mace | Upgrade (I–II) | Power | Slows the target instead of burning it. Exclusive with Fire Aspect |
| **Rebounding** | Chestplate, leggings | Upgrade (I–III) | Power | Knocks melee attackers away. Can sit beside Thorns, which damages instead |
| **Bulwark** | Shield | Upgrade (I–II) | Power | No knockback while blocking, and a chance to disable the attacker's weapon. Shields' first real choice |

**Vanilla enchantments on more items** (same name, same effect, nothing new to learn):

- **Breach** on swords, axes and spears as well as maces: armour piercing. Each level shares slots with Sharpness and the other damage enchantments, so it's strong against armoured targets and wasted against unarmoured ones.

Left out on purpose: Icy Thorns (too close to Thorns), Air Affinity (no useful way to fly and mine in a vanilla client), and anything the research flags as overpowered (bonus ore from stone, extra fishing loot, a second Looting, raw max health).

### Found books, Villagers and curses

- **A found enchanted book applies its enchantment at the table for no materials, but it still uses slots.**
- **Random sources (loot, fishing, mob drops) cap Power and Yield at level II**, so found books are an early boost without skipping the Era climb. Convenience books can roll at any level.
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

- The table screen is ours (client and server): item slot, enchantment list, recipe with missing items in red, and a slot display.
- Slots and slot upgrades are item data components. Starting slots come from a data file keyed by item tag, so materials can be tuned without code.
- Recipes are data: one file per enchantment, with per-level costs and the slot type.
- Durability-at-zero: items at 0 stop working instead of breaking (hook the break event and the item's use and attack paths). Repair is a shapeless crafting recipe type.
- Random-source caps hook the vanilla loot functions and enchanting helpers (`EnchantRandomlyFunction`, `SetEnchantmentsFunction`, `EnchantmentHelper`).
- Excavating and Felling are Java. Crouching falls back to vanilla single-block behaviour.

## Open questions

- **Starting slots:** are 2, 3 and 4 right? What do wood, stone, gold, leather, chainmail, bows, tridents, maces and spears get?
- **Slot upgrades:** which material for each Era, how many in total, and what's the cap?
- **Recipes:** all of them, with the breakpoints placed first.
- **Lapis:** Fortune and Looting, or a wider job?
- **Mixing:** do mixed swords (Breach with Sharpness especially) turn out too strong? Does every player end up with a set for each danger instead of one general set?
- **Grindstone:** keep it as a way to strip an item, or leave removal only at the table?
- **New enchantments:** which ones need Java and can wait if time runs short (Magnetism, Smelting, Harvesting, Excavating, Felling and Bulwark's weapon disable all need code).
- **More vanilla on more items:** Looting and Quick Charge on bows, Multishot on bows, Infinity on crossbows, Thorns on shields?
- **Repairs:** is 50% per item right for every material, Netherite included?

## Sources

- [Tinkers' Construct](../../research/tinkers-construct.md) and [tool levelling](../../research/tool-levelling.md): slots, modifier prices, area tools
- [Durability](../../research/durability.md): broken-not-destroyed and repair models
- [Enchanting mods](../../research/enchanting-mods.md) and [Ascendant Arcana](../../research/ascendant-arcana.md)
- [Game Design 101](../../research/game-design-101.md)
- The retired designs in [retired/](retired/)
