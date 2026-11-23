# Research: Tinkers' Construct 3 Modifiers

How Tinkers' Construct 3 (TiC 3) prices and gates its modifiers, how its area tools and weapons work, and what an enchanting redesign could borrow. Slot types, starting slots, slot caps and TiC's stance on levelling are already in [Tool Levelling](tool-levelling.md#tinkers-construct-3-116-typed-slots-from-materials-and-milestones), so this page doesn't repeat them. See also [Enchanting and Gear Overhaul Mods](enchanting-mods.md).

Gathered November 2026 from:

- The official SlimeKnights docs and FAQ on slimeknights.github.io.
- The TinkersConstruct GitHub repo, branch `1.20.1` at commit `1b1d701` (15 November 2026). I read the generated recipe JSON in `src/generated/resources/data/tconstruct/recipes/tools/modifiers/` (the folder is `recipes`, plural, on 1.20.1), the modifier definitions in `tinkering/modifiers/`, the tool definitions in `tinkering/tool_definitions/`, material stats, `en_us.json`, and the area-of-effect code in `library/tools/definition/module/aoe/`.
- The Modrinth version API.

Statements marked *(source)* were read directly from code or data files. Statements marked *(unverified)* come from one secondary source only, or the sources disagree. Opinions from Reddit, forums and add-on pages are marked *(community)*.

## Version status

- **The latest official release is 3.12.1.231 for 1.20.1 (Forge and NeoForge), published 22 September 2026.** Modrinth lists no official build for any 1.21 version. The supported versions are 1.16.5, 1.18.2, 1.19.2 and 1.20.1. *(Modrinth API)*
- The roadmap says "1.21 ports" are the next project, and that 1.18.2 and 1.19.2 are "considered stable". It also mentions "1.21.1 if not 26.1" for the planned Soul Forge. *(official roadmap)*
- **Unofficial 1.21.1 forks exist.** "Modern Foundry: Migration Edition" is a NeoForge fork, and "Forgeweave" is a new mod that reuses some TiC code and art under MIT. *(unverified: modpack index listings only)*
- **So "TiC 3 on 1.21" means community forks.** Everything below is from the official 1.20.1 data, which is the current design.

## 1. How modifiers are priced

### Recipe types

There are four recipe shapes. *(source; recipe docs)*

- **`modifier`:** a fixed set of up to 5 items in the Tinker Station (or the Tinkers' Anvil for 4 to 5 inputs) adds one level.
- **`incremental_modifier`:** "supports adding a partial level of the modifier instead of requiring a full level to be added at once." Each recipe has `needed_per_level` and `amount_per_item`. A storage block counts as 9 dust, and an overshoot is refunded through `leftover`. **A partial level gives a scaled effect,** "though note not every effect supports scaling; some may simply round down." *(recipe docs)*
- **`multilevel_*`:** different slot costs per level. For example, Feather Falling and Leaping charge slots only on some levels. *(source; per-level details not read)*
- **`swappable_modifier`:** one modifier with variants. Rebalanced chooses a slot type, and Crystalshot chooses a crystal.

**The cost per level is flat, not escalating.** `IncrementalModifierRecipe` uses the same `needed_per_level` for every level, so Haste V costs 5 × 45 = 225 redstone. *(source)* The scaling comes from the **slot**: every level of an upgrade modifier costs another upgrade slot (the `slots` field applies to each new level; it is "ignored on tools that already have the modifier" only for partial progress inside a level). *(recipe docs, inference from `slots` semantics; not tested in game)*

### Common modifiers

All values below are *(source)*: recipe JSON plus modifier definitions.

| Modifier | Slot | Cost per level | Max level | Effect |
|---|---|---|---|---|
| **Haste** | Upgrade | 45 redstone dust (incremental) | 5 | +4 mining speed per level. On armor, +10% mining speed per level |
| **Sharpness** | Upgrade | 36 quartz (incremental; a quartz block counts as 4) | 5 | +0.75 attack damage per level |
| **Swiftstrike** | Upgrade | 72 amethyst shards (block counts as 4) | 5 | +5% base attack speed per level |
| **Smite** | Upgrade | 5 glistering melon slices | 5 | Damage against undead |
| **Bane of Sssss** | Upgrade | 15 fermented spider eyes | 5 | Damage against arthropods |
| **Killager** | Upgrade | **45 lapis** (incremental) | 5 | Damage against illagers and villagers. Lapis goes here, not to Luck |
| **Luck** (Fortune / Looting) | **Ability (level 1 only)** | Level 1: 2 lapis blocks, 2 copper ingots, cornflower or blue orchid. Level 2: 2 gold ingots, golden carrot, 2 ender pearls. Level 3: 2 rose gold ingots, rabbit foot, diamond, name tag | 3 | Fortune on harvest tools, Looting on weapons, Luck of the Sea, and armor versions. **Levels 2 and 3 cost no extra slot,** only different and rarer ingredients |
| **Diamond** | Upgrade | 1 diamond | 1 | +500 durability, +0.5 damage, +2 mining speed, **sets harvest tier to Diamond** |
| **Emerald** | Upgrade | 1 emerald | 1 | +50% base durability, +25% conditional damage and speed, repair bonus, **sets tier to Iron** |
| **Netherite** | Upgrade | 1 netherite ingot + 1 netherite upgrade template | 1 | +20% durability, +20% damage, +25% mining speed, +10% velocity, **tier Netherite**, fire immunity |
| **Reinforced** | Upgrade | 4 emerald reinforcements (incremental) | 5 | Chance to ignore durability damage: 0.025 × L × (11 − L), so **25 / 45 / 60 / 70 / 75%** |
| **Overforced** | Upgrade | 4 slimesteel reinforcements | 5 | +75 overslime capacity per level |
| **Unbreakable** | Ability | 2 shulker shells, dragon's breath, 2 netherite ingots | 1 | No durability loss. **Requires Netherite and Reinforced V already on the tool** |
| **Soulbound** | Upgrade | 1 echo shard | 1 | Kept on death |
| **Silky** (Silk Touch) | Ability | 5 silky cloth | 1 | Silk Touch, and mob shearing on tools with the Silky Shears trait |
| **Autosmelt** | Ability | Raw ore, blast furnace, ingot, 2 coal blocks | 1 (shown as "+") | Smelts drops, 20% extra-drop chance |
| **Melting** | Ability | 2 blaze rods, melter or smeltery controller, 2 lava buckets | 1 | Melts drops and struck mobs into the tool's fluid tank (1,000 mB) |
| **Expanded** | **Ability, every level** | 2 pistons, amethyst bronze ingot, 2 ichor slimeballs | No recipe cap; in practice 2 (ability slots) | Larger area of effect (see section 3) |
| **Magnetic** | Upgrade | 1 compass | 5 | Pulls drops |
| **Severing** | Upgrade | 2 wither bones, lightning rod, TNT | 3 | Head-drop chance |
| **Sweeping Edge** | Upgrade | 5 chains | 3 | Sweep damage percentage |
| **Experienced** | Upgrade | 3 bottles o' enchanting | 5 | More XP from blocks and mobs |
| **Strength** | Ability | 72 ichor slime crystals (incremental) | 2 | Damage (not read in detail) |
| **Reach** | Ability | 2 pistons, queen's slime ingot, 2 ender slimeballs | 2 | Reach |
| **Wings** | **2 ability slots** | 1 elytra | 1 | Elytra flight on a chestplate |

Armor defense modifiers use **defense** slots and are incremental from reinforcement items. Protection-type modifiers need 5 of their reinforcement per level (iron for Projectile, obsidian for Blast, seared for Fire, gold for Magic, cobalt for Melee). Shulking needs 3 shulker shells, and Revitalizing 2 jeweled apples. *(source)*

### Slot-adding items

Recipe data, to correct and extend [Tool Levelling](tool-levelling.md). All are *(source)*.

- **Writable** (book and quill), **Harmonious** (any music disc) and **Recapitated** (mob head) are slotless and each give +1 upgrade slot.
- **Resurrected** gives +1 upgrade slot but **requires Forecast first**. Forecast is a diamond, emerald or cobalt **ore block**, and "reveals an upcoming upgrade slot". That answers the open question in Tool Levelling about the fourth rare item.
- **Gilded** costs 1 ability slot and gives +2 upgrade slots. **On 1.20.1 its recipe is a gilded blackstone block, not a golden apple** (Tool Levelling says golden apple; that may be an older version).
- **Draconic** gives +1 ability slot. It comes from a dragon head, or 2 dragon's breath plus 3 dragon scales.
- **Rebalanced** gives one extra slot of your choice. It costs 2 nuggets, an end crystal and 2 slime crystal blocks, and the nugget and slime type depend on the slot type chosen. "Penalizes choice of the most powerful type": choosing abilities takes away an upgrade slot.
- **Embossed** gives +1 upgrade slot from a boss trophy. *(The source has it; the "What's New" page says embossments were removed, so this is unverified: probably a newer re-addition.)*

### Pricing patterns worth noting

- **Bulk commons for stat modifiers.** Stat modifiers cost dozens of a farmable item (45 redstone, 36 quartz, 72 amethyst), paid in any chunks. **Behaviour modifiers** cost a small, specific recipe of 3 to 5 items that points at a game milestone: a blast furnace, an elytra, dragon's breath, an echo shard.
- **Levels are flat-priced but slot-capped.** The real price of Haste V is five of your ten possible upgrade slots, not 225 redstone.
- **Luck breaks the pattern on purpose.** It costs one ability slot once, and each higher level is gated by **rarer materials instead of more slots**: lapis and flowers, then pearls and golden carrots, then rose gold, a name tag and a rabbit foot.

## 2. Gating and breakpoints

### Mining tier comes from the head material, or a modifier

Each head material sets a `mining_tier`, using vanilla's tiers. *(source: material stats)*

| Material | Durability | Mining speed | Attack | Mining tier | Material tier |
|---|---|---|---|---|---|
| Wood | 60 | 2.0 | 0 | wood | 0 |
| Stone | 130 | 4.0 | 1.0 | stone | 1 |
| Flint | 85 | 3.5 | 1.25 | stone | 1 |
| Copper | 210 | 5.0 | 0.5 | iron | 1 |
| Iron | 250 | 6.0 | 2.0 | iron | 2 |
| Rose gold | 175 | 9.0 | 1.0 | gold | — |
| Slimesteel | 1,040 | 6.0 | 2.5 | diamond | — |
| Cobalt | 800 | 6.5 | 2.25 | diamond | 3 |
| Hepatizon | 975 | 8.0 | 2.5 | netherite | — |
| Manyullyn | 1,250 | 6.5 | 3.5 | netherite | 4 |
| Queen's slime | 1,650 | 6.0 | 2.0 | netherite | — |

- **Material tiers map to vanilla tiers:** Tier 1 is "comparable to wood or stone", Tier 2 to iron, Tier 3 to diamond and Tier 4 to netherite. *(What's New)*
- **The Diamond, Emerald and Netherite modifiers *set* the harvest tier** (to Diamond, Iron and Netherite). They are the TiC 3 replacement for TiC 2's mining-level boosts. "Sharpening kits no longer change mining level, use diamond or netherite instead." *(source; What's New)* So a fast rose gold head (gold tier) can be fixed with a single diamond in one upgrade slot. The material picks the stats; one cheap modifier fixes the tier.
- **The progression chain is real:** cobalt ore needs a diamond-tier tool (FAQ). Tier 3 and 4 metals need the smeltery's alloying and the Nether (manyullyn is cobalt plus ichor-era materials). Material choice is the Era gate; slots are the build budget.

### Breakpoints

- **TiC has no special instant-mining handling that I could find.** Haste is linear, +4 speed per level, so Haste V is +20. Vanilla Efficiency is level² + 1, so Efficiency V is +26. The docs only say haste is now "a bit stronger and more in line with efficiency." *(source; What's New)*
- **Instamining therefore follows vanilla's rule** (break speed of at least 30 × the block's hardness, so 45 for stone). TiC reaches it through stacking: head speed, plus Haste, plus Netherite's +25%, plus beacon Haste. *(Inference; no TiC page discusses it.)*
- The real breakpoints in TiC are **qualitative, not numeric**: Diamond or Netherite tier unlocks new ores, Unbreakable needs Netherite plus Reinforced V, Resurrected needs Forecast, and Luck II and III need the next material band.
- **Caps are explicit.** Most stat modifiers stop at 5, abilities at 1 to 3, slots at 10 upgrade and 2 ability (15 upgrade with Gilded), and defense at 80% (best-tool page). The "no best tool" goal (FAQ) is enforced by slot scarcity, not by diminishing returns.

## 3. Tool-type abilities: area mining, felling, harvesting

**Area of effect belongs to the tool type, and Expanded scales it.** Every tool definition has an AoE module. The Expanded modifier writes an integer that each AoE type reads in its own way. *(source: `BoxAOEIterator`, `VeiningAOEIterator`, `TreeAOEIterator`, `CircleAOEIterator`)*

| Tool | Area module (base) | Each level of Expanded | Stat multipliers (durability / mining speed / damage) | Slots (upgrade + ability) |
|---|---|---|---|---|
| **Pickaxe** | Box, no base area, expands along view pitch | Level 1: +2 depth (1×1×3 tunnel). Level 2: +1 height | 1 / 1 / 1 | 3 + 1 |
| **Sledge Hammer** | **Box 3×3** on the face hit | Alternates +1 width each side, then +1 height, so **5×3, then 5×5** | **×4 / ×0.4 / ×1.35**; attack speed 0.75 | 2 + 1 |
| **Vein Hammer** | **Vein, max distance 2** (connected same-block) | +1 vein distance | ×5 / **×0.3** / ×1.25 | 2 + 1 |
| **Excavator** | Box 3×3 (shovel blocks) | 5×3, then 5×5 | ×3.75 / ×0.3 / ×1.2 | 2 + 1 |
| **Broad Axe** | **Tree felling on logs**; on other blocks a 3-wide, 6-high box | Wider and deeper tree search, alternating | ×4.25 / ×0.3 / ×1.65; attack speed 0.6 | 2 + 1 |
| **Hand Axe** | Circle, diameter 1 (2D) | +1 diameter | 1 / 1 / 1 | 3 + 1 |
| **Mattock** | Vein, distance 0 | +1 vein distance | ×1.25 / ×1.1 / ×1.1; ×2 speed on non-mattock axe blocks | 3 + 1 |
| **Scythe** | **Box 3×3×3 on the face**, plus circle melee (diameter 2) | Grows the box | ×2.5 / ×0.45 | 2 + 1 |
| **Kama** | Circle, diameter 1 (3D) | +1 diameter | Damage ×0.5 | 3 + 1 |
| **Pickadze** | Box, 1×2 tall | +1 per level | ×1.3 / ×0.75; **max tier capped at gold** | 3 + 1 |

- **Drawbacks are paid in mining speed and slots, not durability.** Broad tools trade 55 to 70% of mining speed for area, gain 3.75 to 5 times the durability to pay for breaking many blocks, and start with 2 upgrade slots instead of 3. They also need 4 parts and the Tinkers' Anvil, and have more material cost (large plates, tough handles). *(source; FAQ)*
- **Area harvest and replant** comes from the **Harvest** trait on the kama and scythe: "Right Click: Harvest and replant crops". **Tilling, Stripping and Pathing** are traits on some tools and ability-slot modifiers on others, crafted from the matching tool head (for example, Stripping is a small axe head, steel and a binding). *(source)*
- **TiC 2's lumber axe and battleaxe were merged into the Broad Axe,** and the TiC 2 hammer became the Sledge Hammer. *(removed page)*
- **The Vein Hammer is TiC's vein-miner,** and it's short range: 2 blocks from the origin, plus 1 per Expanded. It is a tool type, not a modifier, so you can't vein-mine with a normal pickaxe. *(source)*

## 4. Weapons

From the 1.20.1 tool definitions *(source)*, with descriptions from `en_us.json`.

| Weapon | Distinctive mechanic | Base attack speed | Slots |
|---|---|---|---|
| **Sword** | Sweep attack (range 1), cuts cobwebs fast | 1.6 | 3 + 1 |
| **Dagger** | **Off-hand attack** trait ("quick strikes from either hand"), Padded (low knockback), damage ×0.65, attack speed 2.0 | 2.0 | 3 + 1 |
| **Cleaver** | Broad weapon, damage ×1.5, durability ×3.5, **sweep range 2**, **Severing II** (head drops) | 1.0 | 2 + 1 |
| **Swasher** ("ancient" tool) | Fluid **Spitting** and **Spilling**: shoots or splashes fluid from its tank with fluid-based effects | 1.5 | 3 + 1 |
| **War Pick** (ancient) | **War Charge**: "mining blocks while loaded increases the velocity of the next shot" (a pick and crossbow hybrid) | 1.2 | 3 + 1 |
| **Minotaur Axe** (ancient) | **Charge Attack**: "bonus damage while sprinting" | 0.9 | 3 + 1 |
| **Javelin** | **Throwing**: thrown for melee damage at range, and you're unarmed until you retrieve it | 1.1 | 2 + 1 |
| **Longbow** | Precision, long range; two limbs, grip and string | — | 2 + 1 |
| **Crossbow** | Fast-firing arrows and fireworks. Sinistral modifier fires on attack | — | 3 + 1 |
| **Throwing Axe, Shuriken, Arrows** | Ammo built from parts | — | — |
| **Staffs** (sky, earth, ichor, ender) | Slime-themed utility and fluid tools *(not read in detail)* | — | — |

- **The longsword and rapier no longer exist.** The longsword "was not distinct enough from broadswords", and the rapier was "completely OP in PvP" and replaced by the dagger. The frypan became the excavator plus the **Bonking** ability, and returned as the "melting pan" ancient tool. The cutlass and shortbow moved to the Tinkers' Things add-on. *(removed page)*
- **Weapon abilities in ability slots:** Parrying ("briefly block after attacking"), Blocking, Dual Wielding (off-hand attacks, with reduced stats), Drill Attack (hits enemies along your movement), Bonking, Flinging, Springing and Warping. *(source; lang)*

### What translates to vanilla-style enchantments (1.9+ cooldown, mace and spear era)

| Mechanic | Translates? | Why |
|---|---|---|
| Sharpness, Smite, Bane, Killager (lapis) | **Yes, trivially** | Vanilla has damage value effects. Killager is a mob-tag damage enchantment |
| Swiftstrike (+5% attack speed per level) | **Yes, as an attribute modifier** on the item. It's directly felt under the cooldown, so it is Power and should be priced like it | Data-driven `attributes` component |
| Sweep range and Sweeping Edge | **Partly.** Sweeping Edge exists. A larger sweep radius has no vanilla value effect and needs code | — |
| Severing (head drops) | **Yes**, via loot-table conditions on the killing item, or `equipment_drops` | — |
| Charge Attack (sprint bonus) | **Yes**, a damage effect with an `entity_properties` "is sprinting" condition. Sprint-hits already matter in vanilla | — |
| Off-hand attacks, Dual Wielding | **No.** Vanilla has no off-hand swing. It needs code and fights the 1.9 cooldown model | — |
| Parrying or Blocking on a sword | **Poorly.** Shields already own blocking in 1.9+. Parrying needs code | — |
| Throwing (javelin) | **Mostly redundant.** The trident and the spear already cover thrown and reach weapons *(spear added in 1.21.11, per minecraft.wiki; not re-checked)* | — |
| Drill Attack, Charge-through | **Overlaps the spear's charge and Lunge** and the mace's Wind Burst. A new enchantment would duplicate them | — |
| War Charge (mine to load velocity) | **Fun, but needs code.** It's a cross-tool loop with no vanilla hook | — |
| Fluid Spitting and Spilling | **No.** It depends on TiC's tank and fluid system | — |
| Bonking, Flinging (knockback and launch) | **Yes.** `apply_impulse` (1.21.11) and knockback value effects | — |

## 5. Armor

- **Three sets.** *(What's New; FAQ; tool definitions)*
  - **Plate** is the defense set. The chestplate has 3 defense + 2 upgrade slots and **no ability slot**, and two material parts (plating, maille). It caps at **16 armor points; diamond is needed for the full 20** (FAQ). An older developer post says 4 defense + 1 upgrade + 1 ability. *(unverified: probably the 1.16.5 layout)*
  - **Travelers** is the hybrid set. The chestplate has 2 defense + 2 upgrade + 1 ability slot, is built from rose gold and leather, and is repaired with a part. A 2025 "travelers update" post reworked it *(not read in detail)*.
  - **Slime suit** is the utility set. The chestplate has 3 upgrade + **2 ability** slots, no defense slots, a built-in Reach trait, Overslime (a second durability bar refilled with slime) and strong traits. Its ribcage material is any material, with blood as the default slime.
- **Shields:** the Travelers shield (low defense, little movement penalty) and the Plate shield ("significantly hinders movement speed"). *(What's New)*
- **Armor uses the same modifier system:** defense modifiers in defense slots, Haste on the chestplate, Luck on armor (Fortune and Looting while worn), Wings (elytra, 2 ability slots), and Soulbound. Total protection is capped at 80%. *(source; best-tool page)*
- **Common criticism** *(community: add-on authors, not Reddit)*:
  - Plate is "severely underpowered".
  - Its "two-component design also significantly limits customizability", which is the reason people pick Tinkers in the first place.
  - The slime-suit modifiers feel "half-hearted".
  - Add-ons such as "tconstruct armor extension" and Tinkers' Things' laminar set exist mainly to fix this.

## 6. Complexity criticism *(community; mostly older threads)*

I didn't find good Reddit threads on TiC 3 specifically. The sources below are FTB forum threads, mostly about TiC 1 and 2, plus add-on pages. Treat them as weak evidence.

- **Smeltery onboarding.** "Requires a smeltery to be able to make an Iron Pick" was a common early complaint. Setting up the smeltery takes "significant amount of shovel and axe work" (grout, gravel, clay), and some players keep vanilla tools until later. Others reply "the smeltery is easy to build." *(FTB forum)*
- **TiC 3 adds steps:** Melter, then Smeltery, then Foundry and Alloyer in the Nether. Broad tools need a Tinkers' Anvil (3 metal blocks and 4 seared blocks). There are four slime types and a fluid tank on some tools. The FAQ **assumes JEI** ("Tinkers only supports JEI"), so the knowledge lives in a recipe viewer, not the game. *(official FAQ)*
- **Tedium.** "Adding modifiers 1 at a time rather than doing the whole stack in one click" was named as tedium rather than difficulty. *(FTB forum)* Incremental modifiers make this better (you can dump a stack in), but 225 redstone across 5 levels is still several trips.
- **Optimisation trap.** The official "Making the Best Tool" page exists because players ask for the best tool. Its answer is "pretty hard to make a bad tool unintentionally", so set a goal and build a good tool. That points to choice paralysis among new players. *(official; interpretation is mine)*
- **What TiC 3 simplified:** the part builder and stencil table were merged, levelling was removed, and broken tools are repairable rather than destroyed. *(What's New)*

## Takeaways for an enchanting redesign

Constraints: no XP, no durability, Era-gated materials, per-item choices.

1. **Two currencies: commons for magnitude, milestones for behaviour.** Stat enchantments (speed, damage, protection) cost bulk farmable items paid in any chunks, like TiC's 45 redstone per level. Behaviour enchantments (silk, autosmelt, area, flight) cost one small recipe that names a game milestone (a blast furnace, an echo shard, dragon's breath). This replaces XP cleanly and makes farms useful without making them the gate.
2. **The budget is the real price.** TiC's per-level material cost is flat, and slots do the balancing. In an enchanting table, a fixed per-item capacity (by material or Era) does the same job as TiC's slots, and it stays legible.
3. **Gate higher levels with Era materials, not more budget.** Luck I to III uses one ability slot, and each next level needs the next material band. That suits "Fortune II needs an Era 2 material" well, and it stops yield compounding through spending alone.
4. **Prerequisites as breakpoints.** Unbreakable needs Netherite plus Reinforced V, and Resurrected needs Forecast. Small chains like these make a capstone feel earned. With no durability in our game, use the pattern for other capstones (for example, an area enchantment that needs the tier material first).
5. **Area by tool type, scaled by one enchantment.** TiC puts the shape (3×3 box, vein, tree, circle) on the tool and lets one modifier grow it, paid with mining speed (×0.3 to ×0.45). Without durability, **the speed penalty is the only real drawback**, and it's a good one: area mining is Convenience, not Power. One "Expanded"-style enchantment that reads differently per tool keeps the list short.
6. **Two kinds of slot.** Upgrade versus ability is a simple way to stop players stacking Silk, Autosmelt, area and Luck on one pick. Gilded (trade 1 ability for 2 upgrades) is an elegant per-item choice to copy.
7. **Removable and refundable.** TiC's worktable removes modifiers and returns the slot. With per-item choices, full refunds keep experimenting cheap.
8. **Skip the parts.** The part-building, smeltery and JEI-only knowledge is where casual players bounce. Keep TiC's *outputs* (material identity, typed budget, milestone recipes) and drop its *inputs* (part crafting, fluids, alloying).
9. **Weapon mechanics that fit vanilla combat:** mob-type damage, attack-speed attributes (price as Power), sprint-bonus damage, head drops, knockback and launch. **Skip** off-hand attacks, parry-on-sword and thrown melee: they need code and overlap the shield, trident, spear and mace.
10. **Armor lesson:** TiC's plate set shows a defense-only set with few choices feels worse than vanilla. Armor needs the same interesting per-item choices as tools, not only protection numbers.

## Sources

- Tinkers' Construct 3 FAQ (official): https://slimeknights.github.io/faq/tinkers-construct-3
- What's New in TiC 3 (official): https://slimeknights.github.io/docs/whats-new
- Removed features (official): https://slimeknights.github.io/docs/removed
- Roadmap (official): https://slimeknights.github.io/docs/roadmap
- Making the Best Tool (official): https://slimeknights.github.io/faq/best-tool
- Modifier recipe JSON docs (official): https://slimeknights.github.io/docs/json/recipes/modifiers
- Travelers update post (official): https://slimeknights.github.io/posts/2025/07/17/travelers-update
- TinkersConstruct repo, branch 1.20.1: https://github.com/SlimeKnights/TinkersConstruct/tree/1.20.1
  - Modifier recipes: https://github.com/SlimeKnights/TinkersConstruct/tree/1.20.1/src/generated/resources/data/tconstruct/recipes/tools/modifiers
  - Modifier definitions: https://github.com/SlimeKnights/TinkersConstruct/tree/1.20.1/src/generated/resources/data/tconstruct/tinkering/modifiers
  - Tool definitions: https://github.com/SlimeKnights/TinkersConstruct/tree/1.20.1/src/generated/resources/data/tconstruct/tinkering/tool_definitions
  - Material stats: https://github.com/SlimeKnights/TinkersConstruct/tree/1.20.1/src/generated/resources/data/tconstruct/tinkering/materials/stats
  - AoE code: https://github.com/SlimeKnights/TinkersConstruct/tree/1.20.1/src/main/java/slimeknights/tconstruct/library/tools/definition/module/aoe
  - `IncrementalModifierRecipe.java`: https://github.com/SlimeKnights/TinkersConstruct/blob/1.20.1/src/main/java/slimeknights/tconstruct/library/recipe/modifiers/adding/IncrementalModifierRecipe.java
  - English lang file: https://github.com/SlimeKnights/TinkersConstruct/blob/1.20.1/src/main/resources/assets/tconstruct/lang/en_us.json
- Modrinth versions API: https://api.modrinth.com/v2/project/tinkers-construct/version
- Modern Foundry (unofficial 1.21.1 fork): https://www.modpackindex.com/mod/210442/modern-foundry-migration-edition-unofficial
- Forgeweave: https://www.modpackindex.com/mod/175111/forgeweave
- Armor criticism (add-on pages): https://modrinth.com/mod/tconstruct-armor-extension, https://github.com/SlimeKnights/TinkersThings/releases
- 1.16.5 armor post: https://paypur.github.io/posts/2021/12/06/armor-update
- FTB forum, getting started and smeltery work: https://forum.feed-the-beast.com/threads/tinkers-construct-how-to-start-out.31427/
- FTB forum, "Balance or Tedium": https://forum.feed-the-beast.com/goto/post?id=1157235
- FTB forum, smeltery needed for an iron pick: https://forum.feed-the-beast.com/goto/post?id=443252
