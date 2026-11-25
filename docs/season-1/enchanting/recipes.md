# Season 1 enchanting: slots and recipes

**Status: Leaning (agreed November 2026).** Numbers are a first pass to tune in the playtest. One kind of slot, and one material per enchantment, the way Tinkers' Construct prices its stat modifiers (45 Redstone for Haste, 36 Quartz for Sharpness, 45 Lapis for Killager). It applies the agreed [slot design](README.md), but it changes the two slot types into one.

In the mod these numbers are defaults: the live costs are in `config/superawesomecraft/enchanting.json`, which can be edited and reloaded with `/reload` without a new build. Keep this page in step when the playtest numbers change.

## The whole system

1. **Every enchantment uses one slot**, at any level. Efficiency V and Efficiency I both take one slot.
2. **Slots come from the item's material:**

   | Material | Slots |
   | --- | --- |
   | Wood, stone, gold, leather | 1 |
   | Copper, iron, chainmail, bow, crossbow, shield, fishing rod, turtle shell | 2 |
   | Diamond, trident, mace | 3 |
   | Netherite | 4 |

3. **Each enchantment has one material.** Most cost **16 for level I and 16 more for each level after**: 16, 32, 48, 64, 80. A few rare materials cost a **flat 4** per level.
4. **Levels IV and V also need 4 Blaze Rods each**, except Efficiency.
5. **Three upgrades add one slot each**, once per item: a **Diamond Block with a Gold Block** (Overworld), a **Nether Star** (Nether) and **4 Shulker Shells** (End).
6. **Removing an enchantment** frees its slot. The materials are lost.
7. **Damage enchantments can share a weapon, and Protections can share armour**, each in its own slot. Only contradicting enchantments are exclusive, such as Silk Touch and Fortune.

That's the whole system: slots from material, one material per enchantment that gets pricier each level, and Blaze Rods for the top levels.

## Recipes

**Rising (16, then 16 more per level):**

| Material | Enchantments |
| --- | --- |
| Redstone | Efficiency, Quick Charge |
| Copper Ingots | Unbreaking |
| Iron Ingots | Protection, Felling, Magnetism, Bulwark |
| Lapis Lazuli | Fortune, Looting, Luck of the Sea |
| Emeralds | Bane of Illagers |
| Amethyst Shards | Sharpness, Power |
| Flint | Piercing |
| String | Lure, Multishot |
| Gold Ingots | Density |
| Bone Meal | Harvesting |
| Bones | Beheading |
| Rotten Flesh | Smite |
| Spider Eyes | Bane of Arthropods |
| Slime Balls | Knockback, Punch, Rebounding, Step Assist |
| Feathers | Feather Falling |
| Leather | Projectile Protection |
| Gunpowder | Blast Protection |
| Magma Blocks | Fire Protection |
| Cactus | Thorns |
| Wind Charges | Sweeping Edge |
| Kelp | Respiration, Aqua Affinity |
| Prismarine Shards | Depth Strider, Impaling |
| Snow Blocks | Frost Walker, Frost Aspect |
| Soul Soil | Soul Speed |

**Rare (a flat 4 per level):**

| Material | Enchantments |
| --- | --- |
| Blaze Rods | Fire Aspect, Flame, Smelting |
| Cobwebs | Silk Touch |
| TNT | Excavating |
| Diamonds | Breach, Infinity |
| Ender Pearls | Reach, Loyalty |
| Breeze Rods | Lunge, Wind Burst |
| Echo Shards | Swift Sneak |
| Nautilus Shells | Riptide |

**One-off:** Channeling costs 1 Heart of the Sea (it has only one level).

- **Removing a curse:** 32 Gold Ingots.
- **Found books:** vouchers. In a material slot, a book pays one level's material of an enchantment it holds and loses that level. Blaze Rods and slots still apply.
- **Repair:** in the crafting grid, the item plus its own material. Each material item restores 50%.

## What a finished item costs

- **A builder's diamond pick:** Efficiency III (96 Redstone), Unbreaking III (96 Copper) and Silk Touch (4 Cobwebs). That fills all 3 slots, and it's all Overworld.
- **The same pick, later:** Efficiency V adds 144 Redstone (64 + 80) and no Blaze Rods. A Diamond Block and a Gold Block add a fourth slot for Magnetism.
- **Fortune III:** 96 Lapis (16 + 32 + 48), all Overworld.
- **A max Netherite sword:** Sharpness V (240 Amethyst, 8 Blaze Rods), Looting III (96 Lapis), Fire Aspect II (8 Blaze Rods) and Unbreaking III (96 Copper) fill 4 slots. All three slot upgrades bring it to 7, ending with 4 Shulker Shells from the End.
- **Netherite armour with Protection IV on every piece:** 640 Iron and 16 Blaze Rods for the set.

## Main decisions in this pass

1. **One kind of slot, one slot per enchantment.** Slot count still forces a choice (a diamond pick fits Efficiency, Unbreaking and one of Silk Touch, Fortune or Excavating), and builder comfort fits on a diamond tool.
2. **Rising cost instead of a flat pile.** Level I is cheap (16), so trying an enchantment is easy, but each level asks for 16 more. A farm makes level I trivial, not level V. The user asked for this to apply to every common material.
3. **One rule for high levels:** levels IV and V need Blaze Rods. Efficiency is the exception, so mining speed (Convenience) never waits on the Nether. Instant-mining stone still needs Haste II from a beacon, which needs the Wither.
4. **Fortune stays Overworld.** It only goes to III, so it never needs Blaze Rods. It will be the first thing people chase, and it shouldn't send anyone rushing to a blaze spawner.
5. **Materials don't repeat on one item's list** where it can be avoided: Smite isn't Gold because the mace shows Density, Unbreaking moved to Copper so axes don't show Iron twice (Felling).
6. **The End lives in the last slot upgrade**, not in enchantment levels. Max gear needs Shulker Shells, which keeps the long climb.

## Things that changed from earlier decisions

- **Rising costs replace 32 per level.** High levels of common enchantments cost more than before (Sharpness V: 240 Amethyst, was 160); low levels cost less.
- **Efficiency IV and V no longer need Blaze Rods.** Instant mining still lands on the beacon (Haste II).
- **New materials:** Copper for Unbreaking, Cobwebs for Silk Touch, Emeralds for Bane of Illagers, Diamonds for Breach (it ignores armour, so it costs a rare material), Magma Blocks for Fire Protection (you need it before the Nether), Wind Charges for Sweeping Edge, TNT for Excavating (Convenience shouldn't need Diamonds), Blaze Rods for Fire Aspect, Flame and Smelting, a Heart of the Sea for Channeling.
- **Emeralds are a material for one enchantment** (Bane of Illagers), so trading can buy that one. Accepted because it's niche.
- **No separate ability slot.** Silk Touch, Fortune and Excavating compete for ordinary slots, and vanilla exclusivity still stops Silk Touch with Fortune.
