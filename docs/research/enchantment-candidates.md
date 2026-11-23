# Research: New Enchantment Candidates for Season 1

A long list of possible new enchantments, drawn from Tinkers' Construct, Apothic Enchanting, Ensorcellation, Enchancement and vanilla. Each candidate is sorted into the brief's slot model (upgrade slots for stat levels, ability slots for behaviour) and the Convenience / Yield / Power groups. It ends with a shortlist. See also [Tinkers' Construct 3 Modifiers](tinkers-construct.md), [Enchanting and Gear Overhaul Mods](enchanting-mods.md), [Tool Levelling](tool-levelling.md) and [Game Design 101](game-design-101.md).

Gathered November 2026 from:

- minecraft.wiki's Enchantment, Lunge, Spear and Mace pages (the current Java list of 43 enchantments).
- The Apothic Enchanting `1.21` branch lang file and Modrinth page.
- The Ensorcellation lang file (branch `1.20.x`) and CurseForge page.
- The Enchancement README and Modrinth page.
- The Tinkers' Construct `1.20.1` lang file, plus the data already summarised in [tinkers-construct.md](tinkers-construct.md).

Statements marked *(source)* were read directly from a lang file, data file or wiki table. Statements marked *(unverified)* come from a summary or a single secondary source, or I couldn't read the page. Implementation notes about vanilla's enchantment JSON are based on [enchanting-mods.md](enchanting-mods.md#data-driven-enchantments-121) and the minecraft.wiki Enchantment definition page. **Attribute names (`block_interaction_range`, `step_height` and so on) are from memory of 1.20.5+ and were not re-checked for 26.3** *(unverified)*.

It feeds the [Season 1 enchanting](../season-1/enchanting/README.md) slot design (upgrade and ability slots, durability that stops at 0, new enchantments allowed).

## What vanilla already has (don't duplicate)

From the minecraft.wiki Enchantment table *(source)*. There are 43 enchantments in release Java, including:

- **Mace:** Density (V, smash damage per block fallen), Breach (IV, lowers the target's armor effectiveness), Wind Burst (III, treasure, launches you up after a smash). Density, Breach, Sharpness, Smite and Bane are all mutually exclusive.
- **Spear (1.21.11):** Sharpness, Smite, Bane, Fire Aspect, Knockback, Looting, and **Lunge** (III; a jab launches you forward at 0.458 × level blocks per tick, costs 4 × level exhaustion, doesn't work while riding, gliding or in water; added in 25w41a). *(source: Lunge page)* Lunge's minimum hunger level is stated inconsistently on the wiki (6 or 7) *(unverified)*.
- **Movement:** Swift Sneak (III, leggings, treasure), Soul Speed (III, treasure), Frost Walker (II, treasure), Depth Strider, Feather Falling.
- **Not in release:** Cleaving (axe, Combat Tests only). It's free to reuse as an idea.
- **Vanilla's exclusivity groups:** the damage group (Sharpness, Smite, Bane, plus Breach and Density on the mace), Fortune and Silk Touch, the four Protections, Depth Strider and Frost Walker, Infinity and Mending, Multishot and Piercing, and Riptide with Loyalty or Channeling.

**Already planned in our design:** Excavating (3×3 on picks and shovels) and Felling (whole trees on axes). Ensorcellation also has an "Excavating", and Enchancement has "Burrowing" and "Lumberjack", which are the same ideas. *(source)* So the Excavating name has prior art but nothing conflicts.

## How to read the tables

- **Slot:** **U** is an upgrade slot (levelled; one level uses one slot). **A** is an ability slot (one effect, usually level 1).
- **Group:** **C** = Convenience, **Y** = Yield (more drops), **P** = Power (combat strength).
- **Cost theme:** a hint at the recipe's flavour. **OW** = Overworld, **N** = Nether, **Sky** = the new sky dimension (its materials aren't designed, so they're placeholders), **E** = End. Stat levels follow the brief: bulk commons plus an Era material for high levels.
- **Sandbox:** whether it works with the 1.9+ attack cooldown, the spear and mace, and current mob AI.
- **Impl.:** **Data** = vanilla enchantment JSON is enough (value effects, `post_attack`, `tick`, `attributes`). **Data+** = JSON plus a small Fabric hook (a loot table modifier through Fabric API's `LootTableEvents`, or a tag). **Code** = mixins or event handlers.
- **Flags:** **[OP]** is a likely overpowered or degenerate pick, **[Overlap]** overlaps vanilla, and **[Builder]** serves builders.

## 1. Tools (picks, shovels, axes, hoes, shears, fishing rods)

| # | Name (source) | What it does | Items | Slot | Group | Cost theme | Exclusive with | Sandbox | Impl. | Flags |
|---|---|---|---|---|---|---|---|---|---|---|
| T1 | **Autosmelt** (TiC Autosmelt; Ensorcellation Smelting) | Mined blocks drop their smelted result (ore to ingot, sand to glass, cobble to stone). TiC adds a 20% extra-drop chance; **we should leave that out** | Pick, shovel, axe | A | C (Y if it multiplies) | Blast furnace, raw ore, coal block (OW) | Silk Touch | Fine | Data+: a global loot modifier applies `furnace_smelt` when `match_tool` sees the enchantment | [Builder] (glass, stone, charcoal). Fortune then smelt is just Fortune, so it's fine if it doesn't add drops |
| T2 | **Magnetic** (TiC Magnetic) | Pulls drops within a few blocks to you, from block breaks (and from kills on weapons). Levels add range | Any tool, weapon | U (1–3) or A | C | Iron, compass, redstone (OW) | None | Fine. Watch Fabric item merging and hopper contraptions: pull only drops from your own breaks, not every item nearby, or it breaks technical parity | Code (tag the drops, pull on a tick) | [Builder]. Very high value with Excavating and Felling |
| T3 | **Reach** (TiC Reach; Ensorcellation Reach) | +1 block interaction range per level | Any tool (Ensorcellation: chestplate) | U (1–2) | C | Amethyst, ender pearl at II (OW → E) | None | Fine for blocks. **Don't add entity reach**: that's Power and breaks the spear's niche | Data: `attributes` with `block_interaction_range` *(unverified name)* | [Builder]. The cleanest builder enchantment on the list |
| T4 | **Air Affinity** (Ensorcellation; Apothic Stable Footing) | Removes the ×5 mining penalty when not on the ground (flying, on scaffolding, on a ladder) | Helmet (Ensorcellation) or any tool (Apothic) | A, or U level 1 | C | Feather, phantom membrane (OW), Sky material later | None | Fine | Code: I found no vanilla attribute for the airborne penalty *(check the 26.3 attribute list)* | [Builder]. Builders on scaffolds feel this constantly |
| T5 | **Exchanging** (TiC Exchanging) | Breaking a block replaces it with the block in your off-hand, without moving anything | Pick, shovel, axe | A | C | Piston, sticky piston, lapis (OW) | Excavating, Felling (keeps it to single blocks and the code simple) | Fine. Must respect block entities, claims and protection | Code | [Builder]. A cheap version of a Builder's Wand "replace" mode |
| T6 | **Glowing** (TiC Glowing) | Use to place a temporary or permanent light (a glow block, or a torch from your inventory) | Pick, shovel | A | C | Glow ink sac, glowstone (OW/N) | None | Fine. Lights affect mob spawning, which is intended | Code (use action) | [Builder] for caving. Placing a real torch from inventory keeps it vanilla-legible |
| T7 | **Momentum** (TiC Momentum) | Each block mined in a row grants stacking mining speed for a few seconds, reset by pausing | Pick, shovel | U (1–3) | C | Redstone, sugar (OW) | Efficiency? (or let them stack as a speed source) | Fine | Code (per-player counter) | A softer Efficiency that rewards long digs. TiC's text gives no numbers *(source: lang; numbers unverified)* |
| T8 | **Steady Hand** (Apothic Miner's Fervor, reworked) | Mining speed rises a lot but **never instamines**, so a slip doesn't delete a wall | Pick, shovel, axe | U | C | Amethyst (OW) | Efficiency | Fine | Code (clamp break speed below instamine) | [Builder]. An answer to the playtest's instamine Efficiency V pick. Better as a toggle on Efficiency than a separate enchantment? |
| T9 | **Prospector's Boon** (Apothic Boon of the Earth) | Mining stone sometimes drops a random ore | Pick | U | Y | Raw ores (OW), quartz (N) | Silk Touch | Fine | Data+ (loot modifier with `random_chance_with_enchanted_bonus`) | **[OP]** with Excavating: 9 stone per swing makes strip-mining the best ore farm, which is the playtest's single-activity collapse again |
| T10 | **Vein Mining** (TiC Vein Hammer; Enchancement Extracting) | Breaking one ore breaks the connected vein (capped, for example 8/16/32) | Pick | A | C | Iron, redstone, quartz at higher levels | Excavating (both are area abilities; pick one) | Fine | Code (BFS like Felling, which you already need) | Reuses Felling's code. Enchancement's Extracting pulls only the ore and leaves the stone *(source)*, a nice variant |
| T11 | **Tilling / Harvest** (TiC Tilling, Harvest; Ensorcellation Tilling, Furrowing; Apothic Nature's Blessing) | Hoe tills 3×3; right-click a mature crop to harvest and replant it; optionally bonemeal with a hoe | Hoe | A (one "Farmer's" ability with the three bundled, or split) | C | Bone block, wheat or carrots, composter (OW) | None | Fine. Bonemeal-from-a-hoe is Apothic Nature's Blessing *(source)* and needs a cost (bone meal from inventory) | Code | Farmers and builders who garden. Gives hoes their first real enchantment |
| T12 | **Hunter's Bounty / Angler's Bounty** (Ensorcellation) | Extra catches when fishing | Fishing rod | U | Y | Kelp, prismarine (OW) | None | Fine. **AFK fish farms**: design-map already caps random-source Power and Yield; this makes them stronger | Data+ (loot modifier on the fishing table) | **[OP]** for AFK farms. Probably skip |
| T13 | **Grapple** (Enchancement Grapple) | Bobber latches to blocks and pulls you to them | Fishing rod | A | C (mobility) | String, slime block, tripwire hook (OW) | Lure, Luck of the Sea? | Fine. Watch movement anti-cheat on servers | Code (needs the client mod) | Early mobility, between walking and the glider. Fun and readable |
| T14 | **Shearing** (Apothic Growth Serum / Worker Exploitation / Chromatic Aberration) | Wool regrows, doubles, or comes out a random colour | Shears | A | Y / C | Wool, dye | Each other | Fine | Data+ / Code | Low priority. Chromatic is a fun builder toy *(source: lang)* |

## 2. Melee weapons (sword, axe, spear, mace)

Under the 1.9+ cooldown every damage bonus is felt per full swing, so anything that adds raw damage is Power and belongs at the top of the price ladder. Effects that trigger "on hit" fire on weak spam hits too unless they're conditioned on cooldown or critical hits (TiC's Necrotic triggers only on crits *(source: lang)*).

| # | Name (source) | What it does | Items | Slot | Group | Cost theme | Exclusive with | Sandbox | Impl. | Flags |
|---|---|---|---|---|---|---|---|---|---|---|
| W1 | **Severing / Beheading** (TiC Severing; Enchancement Beheading; Ensorcellation Vorpal) | Chance for a mob or player kill to drop its head (zombie, skeleton, creeper, piglin; wither skeleton skull rate up) | Sword, axe | U (1–3) | Y (heads are mostly cosmetic) | TNT, bone block, lightning rod (OW); wither skeleton skull rate in N | None | Fine | Data+: loot modifier with a `random_chance_with_enchanted_bonus` condition | [Builder]: heads are decoration. **Wither skull rate is Power** (the Wither); cap that bonus or exclude it |
| W2 | **Rending** (armor piercing; TiC Armor Pierce / Spiking; Spartan warhammer) | Lowers the target's armor effectiveness, like Breach but for swords, axes and spears | Sword, axe, spear | U (1–4) | P | Flint, quartz (N), netherite scrap at IV | **Sharpness, Smite, Bane** (and it's Breach on non-mace items) | Fine; mobs with natural armor (zombies, Breeze? *check*) feel it | Data: `armor_effectiveness` value effect, as Breach uses | **[Overlap]** with Breach. Only worth it as the "anti-armor" branch of the damage group: strong vs armored targets, worse vs naked ones. TiC's exact rule (trade damage or not) wasn't confirmed *(unverified)* |
| W3 | **Lifesteal** (TiC Necrotic; Ensorcellation Leech; Enchancement Leech) | Heals you for a share of damage dealt on **critical** hits, or a flat amount on kill (Ensorcellation) | Sword, axe, spear | U (1–3) | P | Ghast tear, glistering melon (N) | Other on-hit heals | Fine if crit- or kill-gated; spam hits on full cooldown are already weak | Kill-heal: Data (`post_attack` + `apply_mob_effect` instant health on the attacker; crude). Proportional: Code | **[OP]** in Era combat if uncapped; trivialises Heart upgrades. Keep small and crit-only |
| W4 | **Frost Aspect / Freezing** (Ensorcellation Frost Aspect; TiC Freezing; Enchancement Frostbite) | Slows and weakens the target, and builds powder-snow freeze ticks | Sword, axe, spear, mace | U (1–2) | P (control) | Powder snow, blue ice, Sky material | Fire Aspect | Fine. Strong against fast mobs (spiders, Breeze) | Data: `post_attack` + `apply_mob_effect` (Slowness); freeze ticks need code | A horizontal sibling to Fire Aspect. **Not vs Strays** (they already slow) |
| W5 | **Momentum Strike** (TiC Charge Attack / Minotaur axe) | Bonus damage while sprinting | Sword, axe, spear | U (1–3) | P | Sugar, rabbit foot (OW) | Lunge? (both reward moving) | Fits: sprint-hits already matter in vanilla | Data: damage effect with an `entity_properties` "is sprinting" condition *(source: tinkers-construct.md)* | Rewards aggressive play rather than stat stacking. Close to the spear's charge attack, so keep it off the spear |
| W6 | **Insatiable** (TiC) | Each hit on the same target in a short window adds stacking damage, capped | Sword, axe | U (1–3) | P | Rotten flesh, spider eye (OW) | Sharpness | Under the cooldown a long fight builds stacks, which is good against bosses and pointless vs trash | Code | Possible **[OP]** on bosses; cap the stacks. TiC's numbers unread *(unverified)* |
| W7 | **Opening Blow** (Ensorcellation Instigating) | Extra damage vs targets at full health (Ensorcellation: 2×) | Sword, axe, spear | U | P | Gunpowder, tripwire hook (OW) | Insatiable | **Alpha-strike**: with the cooldown and the mace, one big opener kills most mobs. Bad for PvP | Code (no health check in entity predicates, as far as I know) | **[OP]** at 2×. Fine at +25–50%, and only on charged hits |
| W8 | **Cleaving** (vanilla Combat Tests) | Axe deals extra damage and disables shields for longer | Axe | U (1–3) | P | Iron, obsidian (OW) | Sharpness, Smite, Bane | Fine; Mojang designed it for this sandbox | Data for damage; shield disable time needs code | Gives the axe an identity vs the sword. Mojang's design, never shipped *(source: wiki lists it as Combat Tests only)* |
| W9 | **Sweeping Reach** (TiC Cleaver sweep range) | Larger sweep radius | Sword | U (1–2) | P | Chain, iron (OW) | None | Fine | Code (sweep box is hard-coded) | **[Overlap]** Sweeping Edge. Low priority |
| W10 | **Padded / Pinning** (TiC Padded) | Less knockback, so targets stay in reach and you can combo | Sword, axe, spear | U level 1 | P (combo) | Wool, slime ball (OW) | Knockback | Fine; sensible against skeletons and Breeze kiting | Data: negative `knockback` value effect | Small, readable trade-off. It's the "anti-Knockback" choice |
| W11 | **Bane of Illagers / Killager** (Apothic; TiC Killager; Ensorcellation Vigilante) | Bonus damage vs illagers (raids, pillagers, vindicators, evokers) | Sword, axe, spear, mace | U (1–5) | P | Emerald, lapis, banner (OW) | Sharpness, Smite, Bane | Fine; uses the `illager` entity tag | Data (one JSON file, copy Smite) | **Very cheap to add.** Pairs with Settlements and Raids later. Ensorcellation's Outlaw (vs villagers) — skip |
| W12 | **Ender Bane** (Ensorcellation Ender Disruption) | Extra damage vs Endermen, endermites, shulkers, the Dragon; stops their teleport for a moment | Sword, axe, spear, mace | U (1–5) | P | Ender pearl, chorus (E) | Damage group | Fine | Data for damage; teleport block needs code | An End-Era Power choice. Damage vs the Dragon could be too strong; check |
| W13 | **Mounted Strike / Cavalier** (Apothic Mounted Strike; Ensorcellation Cavalier) | Extra damage while riding | Sword, axe | U | P | Saddle, leather, hay (OW) | None | Fine, but **keep it off the spear**, whose charge attack already owns mounted combat | Data: damage with an `entity_properties` vehicle condition | **[Overlap]** with the spear |
| W14 | **Berserk** (Enchancement Berserk; Apothic Berserker's Fury) | More damage as your health drops | Sword, axe | U (1–3) | P | Blaze powder, nether wart (N) | Lifesteal | Fine | Code | A risk-reward identity choice for confident fighters. A low-health damage bonus can make hard content easy for skilled players |
| W15 | **Mace: Shockwave / Thunderstruck** (Enchancement Thunderstruck, Meteor) | A smash hit chains to nearby mobs, or ignites and pulls them | Mace | A | P | Lightning rod, copper, breeze rod | Wind Burst? | Fine, but the mace already one-shots a lot | Code | **[OP]** risk: the mace is already the strongest burst weapon. Low priority |

## 3. Ranged weapons (bow, crossbow, trident)

| # | Name (source) | What it does | Items | Slot | Group | Cost theme | Exclusive with | Sandbox | Impl. | Flags |
|---|---|---|---|---|---|---|---|---|---|---|
| R1 | **Quick Draw** (Ensorcellation; TiC Lightweight / Featherweight draw speed) | The bow reaches full draw faster | Bow | U (1–3) | P | String, feathers, Sky material | None | Fine. The crossbow already has Quick Charge | Code (bow draw time isn't a value effect) | **[Overlap]** in spirit with Quick Charge, which is good: it gives the bow parity |
| R2 | **Volley** (Ensorcellation) | Fires 3 arrows in an arc and uses one | Bow | A | P | Arrows, dispenser (OW) | Infinity? Power? | Fine | Data: `projectile_count` and `projectile_spread` value effects (Multishot uses them) | **[Overlap]**: Multishot for bows. Low priority |
| R3 | **Trueshot** (Ensorcellation) | Arrows are more accurate and faster | Bow, crossbow | U (1–3) | P | Feathers, flint, spyglass | Volley, Multishot | Fine | Data for spread (negative `projectile_spread`); velocity needs code | A skill-respecting upgrade: helps aim, not raw damage |
| R4 | **Hunter's Bounty** (Ensorcellation; TiC Luck on bows) | Looting for bows and crossbows | Bow, crossbow | U (1–3) | Y | Like Looting | None | Fine | Data: `equipment_drops` value effect (Looting's effect) | Lets ranged players farm without switching weapons. Price as Looting |
| R5 | **Endless Quiver** (Apothic) | All arrows are free, including tipped and spectral | Bow, crossbow | A | C | Like Infinity plus an End material | Infinity, Mending | Fine | Data: `ammo_use` | **[Overlap]** Infinity, and **[OP]** with tipped arrows. Skip; extend Infinity to crossbows instead |
| R6 | **Crescendo** (Apothic Crescendo of Bolts) | A crossbow fires extra times per reload | Crossbow | U (1–3) | P | Iron, tripwire hook, redstone | Multishot | Fine | Code | A crossbow burst identity. Interacts badly with fireworks (huge damage) — **[OP]** check |
| R7 | **Phasing** (Enchancement) | Arrows ignore gravity and pass through blocks and shields | Bow | A | P | — | — | Breaks cover, shields and mob AI | Code | **[OP]**. Skip |
| R8 | **Warp** (Enchancement) | Teleports you to a trident that missed | Trident | A | C (mobility) | Ender pearl, prismarine | Riptide, Loyalty | Fine | Code | Fun, but stacks with ender pearls. Late-Era mobility |
| R9 | **Spearfishing** (Apothic) | Kills with a thrown trident may catch a fish | Trident | A | Y | — | — | Fine | Data+ | Flavour only. Skip |
| R10 | **Grounding Shot** | Hitting a flying mob (phantom, Breeze, ghast, blaze) briefly stops its flight | Bow, crossbow | U level 1 | P | Phantom membrane, anvil (OW), Sky material | None | Useful against the 1.21 Breeze and the sky dimension's mobs | Code (or Data: `apply_mob_effect` Slow Falling on hit is a weaker version) | Original idea for the sky Era. Not from any mod |

## 4. Armour and shields

| # | Name (source) | What it does | Items | Slot | Group | Cost theme | Exclusive with | Sandbox | Impl. | Flags |
|---|---|---|---|---|---|---|---|---|---|---|
| A1 | **Stride / Step Assist** (common in QoL mods) | You step up full blocks without jumping | Boots | A, or U level 1 | C | Rabbit hide, slime (OW) | None | Fine; vanilla's horse already does it | Data: `attributes` with `step_height` *(unverified name)* | [Builder]. Everyone wants it and it does nothing for combat |
| A2 | **Night Vision / Perception** (Enchancement Perception) | Night vision while worn (Enchancement also shows mobs through walls) | Helmet | A | C | Golden carrot, glow ink sac (OW) | Respiration? | Fine. **Drop the see-through-walls part** (Power and an X-ray) | Data: `tick` effect with `apply_mob_effect` | [Builder]. **[Overlap]** with night vision potions; that's the point (no brewing for builders) |
| A3 | **Rebounding** (Apothic Rebounding; TiC Springy) | Melee attackers are knocked far away | Chestplate, leggings | U (1–3) | P (control) | Slime block, piston (OW) | Thorns | Fine with mob AI (melee mobs walk back). **Annoying for players who want the kill** | Data: `post_attack` with `apply_impulse` on the attacker (1.21.11) | Good defensive control, easy to build in data |
| A4 | **Icy Thorns / Chilling Rebuke** (Apothic; Ensorcellation) | Slows attackers | Chestplate | U (1–3) | P | Powder snow, blue ice | Thorns, Fire Rebuke | Fine | Data: `post_attack` + `apply_mob_effect` on the attacker | A cleaner Thorns. Pair with W4 as a frost theme |
| A5 | **Magic Protection** (Ensorcellation; Apothic Occult Aversion) | Reduces magic damage (evokers, witches, guardian beams, Wither effects, potions) | Any armour | U (1–4) | P | Glowstone, potion, lapis | The four Protections | Fine; uses the damage-type tags | Data: `damage_protection` with a damage-type tag condition | Fills a real hole in vanilla's Protection set. **Note:** Protection already covers magic in vanilla, so this is a specialist choice *(check)* |
| A6 | **Vitality** (Ensorcellation; TiC Revitalizing) | +2 max health per level | Chestplate | U (1–3) | P | Golden apple, glistering melon | None | Fine | Data: `attributes` with `max_health` | **[OP]** and it **overlaps Heart upgrades** in vision.md. Skip unless Hearts move here |
| A7 | **Leaping** (TiC Leaping) | Higher jumps | Leggings, boots | U (1–2) | C | Rabbit foot, slime (OW) | Feather Falling? | Fine. Bypasses one-block walls and fences (farms) | Data: `attributes` with `jump_strength` *(unverified name)* | [Builder]. A cheap early mobility step. A half-block of extra jump lets players leap fences, so test it |
| A8 | **Double Jump / Gale** (TiC Double Jump; Enchancement Gale) | Extra jumps in mid-air | Leggings, boots | A | C (mobility) | Feather, phantom membrane, Sky material | Leaping | Fine, but it competes with the glider and the Elytra | Code + client mod; movement checks on the server | A sky-Era signature? Possibly **[OP]** in combat (dodging) |
| A9 | **Gourmand / Assimilation** (Ensorcellation; Enchancement) | Food fills more, or auto-eats when hungry out of combat | Helmet | A | C | Golden carrot, cake (OW) | — | Fine | Code | Removes a drain (hunger). Lunge costs hunger, so this indirectly boosts the spear |
| A10 | **Soulbound** (TiC; Ensorcellation) | The item stays with you on death | Any | A | C | Echo shard (OW, Ancient City) | Curse of Vanishing | Fine | Code (copy items back on respawn) | Changes death stakes server-wide. A design decision, not just an enchantment. TiC uses an echo shard *(source)* |
| A11 | **Bulwark** (Ensorcellation) | No knockback while blocking; chance to disable the attacker's weapon | Shield | U (1–2) | P | Iron block, obsidian | None | Fine | Data for knockback resistance (`attributes` while held); weapon disable is code | Shields have **no enchantments** besides Unbreaking and Mending; even one gives them a choice |
| A12 | **Phalanx** (Ensorcellation) | Move faster while blocking | Shield | U (1–2) | P | Leather, iron | None | Fine | Code (use-item slowdown) | A second shield choice. Pairs with the spear for a "hoplite" build |
| A13 | **Reflective Defenses** (Apothic) | Blocked attacks may harm the attacker | Shield | U (1–3) | P | Iron, prismarine | Bulwark? | Fine | Code (no shield-block hook in enchantment JSON *(check)*) | A shield version of Thorns |

## 5. Rejected or deferred

| Candidate | Source | Why |
|---|---|---|
| Fiery, Knockback, Looting, Fortune, Silk Touch, Sweeping, Smite, Bane | TiC | Vanilla already has them |
| Experienced, Knowledge of the Ages, Insight | TiC, Apothic, Ensorcellation | We have no XP |
| Scavenger (loot tables roll twice) | Apothic | **[OP]**: doubles every drop. It's a second Looting with no cap |
| Unbreakable, Reinforced | TiC | Durability already stops at 0; Unbreakable was the playtest's top "convenience" and pulled builders into the grind |
| Life-Mending | Apothic | Repair is with the item's own material by design |
| Brimstone, Torch, Scatter, Chaos, Delay | Enchancement | Need large custom projectile code; Chaos is random |
| Strafe, Dash, Slide, E-Speed, Bouncy, Sticky | Enchancement | Movement-tech that needs the client and fights the glider and Elytra |
| Veil (lower mob detection), Wardenspine, Adrenaline | Enchancement | Stealth and low-health tanking are PvP and AI-breaking Power |
| Curse of Mercy, Curse of Foolishness | Ensorcellation | Curses with no XP or PvP context |
| Tempting, Pilfering, Disarm | Apothic, Ensorcellation, Enchancement | Niche. Pilfering (steal armour with a rod) is PvP griefing |
| Off-hand attack, Parrying, fluid tools | TiC | See [tinkers-construct.md](tinkers-construct.md#what-translates-to-vanilla-style-enchantments-19-cooldown-mace-and-spear-era): they fight the cooldown and the shield |

## Design lens notes

Checked against [Game Design 101](game-design-101.md) and the pillars:

- **Convenience is cheap.** The builder set (Reach, Air Affinity, Step Assist, Night Vision, Exchanging, Glowing, Magnetic, Autosmelt, Tilling/Harvest) needs nothing from combat and nothing past Overworld materials for its first level. Higher levels of Reach can take a Nether or End material, as "Convenience gets bigger, never stronger".
- **One ability slot is the main tension.** On a pick, Excavating, Vein Mining, Autosmelt, Exchanging and Magnetic all compete for one slot. That's a real choice. Keep it by **not** stacking two area abilities, and price a second ability slot as rare.
- **Yield compounds** (positive loop, [section 5](game-design-101.md#5-feedback-loops-video-9)). Any Yield enchantment multiplied by Excavating is a farm. Prospector's Boon, Scavenger and Angler's Bounty are the dangerous ones; Autosmelt is safe only if it adds no extra drops.
- **Damage group as a horizontal choice.** Keep Sharpness, Smite, Bane, Killager, Ender Bane and Rending in one exclusive set, as vanilla does for the mace. Each Era then has its own "best" specialist (Illagers in the Overworld, Endermen in the End) without stacking.
- **Alpha-strike and burst** (Opening Blow, mace add-ons, Crescendo with fireworks) are where degenerate strategies hide under the 1.9 cooldown. Avoid them or keep them small.
- **Overlap with planned systems:** Vitality vs Heart upgrades, Double Jump vs the glider, Soulbound vs death stakes. These are decisions for vision.md, not enchantments.
- **Implementation cost.** About a third of the list is pure data (Killager, Rending, Frost Aspect slowness, Padded, Momentum Strike, Rebounding, Icy Thorns, Magic Protection, Reach, Step Assist, Night Vision, Hunter's Bounty, Volley). Several more need only a loot modifier (Autosmelt, Severing). The behaviour enchantments that make tools feel new (Magnetic, Vein Mining, Exchanging, Tilling/Harvest, Air Affinity) are all code, but Vein Mining shares Felling's search.

## Shortlist

1. **Reach** (T3): the purest builder Convenience, one attribute in JSON.
2. **Magnetic** (T2): makes Excavating and Felling pleasant; Convenience with no combat effect.
3. **Autosmelt** (T1): a classic ability-slot rival to Excavating and Silk Touch; a loot modifier, and it must not add drops.
4. **Air Affinity** (T4): fixes the most common building annoyance (mining on scaffolds).
5. **Step Assist** (A1): cheap, loved, data-only, zero combat value.
6. **Tilling / Harvest** (T11): gives hoes and farming builders a real enchantment.
7. **Severing** (W1): heads for decoration, a Yield choice for weapons, data plus a loot modifier.
8. **Killager** (W11): one JSON file; an Overworld damage specialist that sets up Raids.
9. **Frost Aspect** (W4) with **Icy Thorns** (A4): a frost theme mirroring fire, data-only, good vs fast mobs.
10. **Rebounding** (A3): defensive armour choice beyond Protection numbers, data via `apply_impulse`.
11. **Bulwark** (A11): shields get their first real choice.

Honourable mentions: **Exchanging** (great for builders, needs code), **Vein Mining** (reuses Felling, but rivals Excavating), **Quick Draw** (bow parity with Quick Charge).

## Outcome

Taken into [Season 1 enchanting](../season-1/enchanting/README.md) in November 2026 with two rules: a new enchantment must do something no vanilla one does, and one that does the same thing as a vanilla enchantment uses the vanilla name. So Rending became **Breach** on more items, Killager became **Bane of Illagers**, Severing became **Beheading**, Autosmelt became **Smelting**, Magnetic became **Magnetism**, and Tilling/Harvest became **Harvesting**. Icy Thorns (too close to Thorns) and Air Affinity (no useful way to fly and mine in a vanilla client) were dropped.

## Sources

- minecraft.wiki, Enchantment (redirect from Enchanting): https://minecraft.wiki/w/Enchantment
- minecraft.wiki, Lunge: https://minecraft.wiki/w/Lunge
- minecraft.wiki, Spear: https://minecraft.wiki/w/Spear
- minecraft.wiki, Mace: https://minecraft.wiki/w/Mace
- minecraft.wiki, Enchantment definition: https://minecraft.wiki/w/Enchantment_definition
- Apothic Enchanting lang file, branch `1.21`: https://github.com/Shadows-of-Fire/Apothic-Enchanting/blob/1.21/src/main/resources/assets/apothic_enchanting/lang/en_us.json
- Apothic Enchanting on Modrinth: https://modrinth.com/mod/apothic-enchanting (the per-enchantment data folder returned 404, so max levels and items are unverified)
- Ensorcellation lang file (served as branch `1.20.x`): https://github.com/CoFH/Ensorcellation/blob/master/src/main/resources/assets/ensorcellation/lang/en_us.json
- Ensorcellation on CurseForge: https://www.curseforge.com/minecraft/mc-mods/ensorcellation (levels not listed; unverified)
- Enchancement on Modrinth: https://modrinth.com/mod/enchancement
- Enchancement README: https://github.com/MoriyaShiine/enchancement (lang file not read)
- Tinkers' Construct lang file, branch `1.20.1`: https://raw.githubusercontent.com/SlimeKnights/TinkersConstruct/1.20.1/src/main/resources/assets/tconstruct/lang/en_us.json (part of the file read only in summary; the 1.12 lang file for Beheading returned 404)
- Tinkers' Construct recipe and modifier data: see [tinkers-construct.md](tinkers-construct.md#sources)
