# Season 1 enchanting: slots and recipes

**Status: Leaning (agreed November 2026).** Numbers are a first pass to tune in the playtest. Simplified after the first review: one kind of slot, and one material per enchantment, the way Tinkers' Construct prices its stat modifiers (45 Redstone for Haste, 36 Quartz for Sharpness, 45 Lapis for Killager). It applies the agreed [slot design](README.md), but it changes the two slot types into one. Nothing here is on the wiki yet.

## The whole system

1. **Every enchantment uses one slot**, at any level. Efficiency V and Efficiency I both take one slot.
2. **Slots come from the item's material:**

   | Material | Slots |
   | --- | --- |
   | Wood, stone, gold, leather | 1 |
   | Copper, iron, chainmail, bow, crossbow, shield, fishing rod, turtle shell | 2 |
   | Diamond, trident, mace | 3 |
   | Netherite | 4 |

3. **Each level costs a pile of one material**: **32** of a common one, or **4** of a rare one. Pay it again for each level.
4. **Levels IV and V also need 4 Blaze Rods each.**
5. **Three upgrades add one slot each**, once per item: a **Diamond Block with a Gold Block** (Overworld), a **Nether Star** (Nether) and **4 Shulker Shells** (End).
6. **Removing an enchantment** frees its slot. The materials are lost.
7. **Damage enchantments can share a weapon, and Protections can share armour**, each in its own slot. Only contradicting enchantments are exclusive, such as Silk Touch and Fortune.

That's the whole system: slots from material, one material per enchantment, and Blaze Rods for the top levels.

## Recipes

Rare materials (4 per level) are in **bold**. Everything else is 32 per level.

| Material | Enchantments |
| --- | --- |
| Redstone | Efficiency, Quick Charge |
| Iron Ingots | Unbreaking, Protection, Magnetism, Bulwark |
| Lapis Lazuli | Fortune, Looting, Luck of the Sea, Bane of Illagers |
| Amethyst Shards | Sharpness, Power |
| Flint | Breach, Piercing |
| Wool | Silk Touch |
| String | Lure, Multishot |
| Coal | Smelting |
| Copper Ingots | Felling, Channeling |
| Gold Ingots | Density |
| Bone Meal | Harvesting |
| Bones | Beheading |
| Rotten Flesh | Smite |
| Spider Eyes | Bane of Arthropods |
| Slime Balls | Knockback, Punch, Rebounding, Step Assist |
| Feathers | Feather Falling |
| Leather | Projectile Protection |
| Gunpowder | Blast Protection |
| Cactus | Thorns |
| Iron Nuggets | Sweeping Edge |
| Kelp | Respiration, Aqua Affinity |
| Prismarine Shards | Depth Strider, Impaling |
| Snowballs | Frost Walker, Frost Aspect |
| Soul Soil | Soul Speed |
| **Diamonds** | Excavating, Infinity |
| **Ender Pearls** | Reach, Loyalty |
| **Blaze Powder** | Fire Aspect, Flame, Fire Protection |
| **Breeze Rods** | Lunge, Wind Burst |
| **Echo Shards** | Swift Sneak |
| **Nautilus Shells** | Riptide |

- **Removing a curse:** 32 Gold Ingots.
- **Found books:** free to apply, but they still use a slot.
- **Repair:** in the crafting grid, the item plus its own material. Each material item restores 50%.

## What a finished item costs

- **A builder's diamond pick:** Efficiency III (96 Redstone), Unbreaking III (96 Iron) and Silk Touch (32 Wool). That fills all 3 slots, and it's all Overworld.
- **The same pick, later:** Efficiency V adds 64 Redstone and 8 Blaze Rods. A Diamond Block and a Gold Block add a fourth slot for Magnetism.
- **A max Netherite sword:** Sharpness V (160 Amethyst, 8 Blaze Rods), Looting III (96 Lapis), Fire Aspect II (8 Blaze Powder) and Unbreaking III (96 Iron) fill 4 slots. All three slot upgrades bring it to 7, ending with 4 Shulker Shells from the End.
- **Netherite armour with Protection IV on every piece:** 512 Iron and 16 Blaze Rods for the set.

## Main decisions in this pass

1. **One kind of slot, one slot per enchantment.** No separate Convenience, upgrade and ability rules. Slot count still forces a choice (a diamond pick fits Efficiency, Unbreaking and one of Silk Touch, Fortune or Excavating), and builder comfort fits on a diamond tool.
2. **One material per enchantment, all raw or dropped**, priced like Tinkers' stat modifiers. Many share a material on a theme: redstone is speed, iron is toughness, lapis is luck, slime is push.
3. **One rule for high levels:** levels IV and V need Blaze Rods. Efficiency V stays behind the Nether, so the instant-mining breakpoint is unchanged.
4. **The End lives in the last slot upgrade**, not in enchantment levels. Max gear needs Shulker Shells, which keeps the long climb.
5. **The slot upgrades are Tinkers 1's:** Diamond plus Gold Block, and a Nether Star. The End one (Shulker Shells) is ours.

## Things that changed from earlier decisions

- **No separate ability slot.** Silk Touch, Fortune and Excavating no longer compete for a special slot. They compete for ordinary slots, and vanilla exclusivity still stops Silk Touch with Fortune.
- **Fortune III and Looting III are Overworld** (they only go to III, so the Blaze Rod rule never applies). Earlier I'd put them last because they multiply income. The lapis cost (96 for III) is the only brake. Worth watching.
- **Convenience enchantments use slots again**, but because a slot holds any level, a diamond tool still fits full comfort.
