# Research: Removing Item Durability

What durability does in vanilla Minecraft, how other games pace progression without it (or with gentler versions), and what removing it would mean for Super Awesome Craft. Gathered November 2026 from the Minecraft Wiki, game wikis, mod listings on Modrinth and CurseForge, press coverage and player forums. Statements marked *(unverified)* come from one secondary source only, or the sources disagree.

## What durability does in vanilla

### The numbers

- **Tools by tier:** Gold 32, Wood 59, Stone 131, Copper 191, Iron 250, Diamond 1561, Netherite 2031 uses.
- **Armour** (helmet / chest / legs / boots): Iron 165/240/225/195, Diamond 363/528/495/429, Netherite 407/592/555/481.
- **Other items:** Flint and Steel 64, Fishing Rod 64, Brush 64, Shears 238, Trident 250, Shield 336, Bow 384, Elytra 432, Crossbow 465, Mace 500.
- **What wears items:**
  - Tools: 1 per block broken (instant-break blocks are free), 2 per mob hit.
  - Swords: the reverse, 1 per hit and 2 per block.
  - Armour: each worn piece loses 1 point per 4 HP of reducible damage, at least 1. Fall, fire-over-time, drowning and magic don't wear it.
  - Shields lose the blocked damage + 1.
  - Elytra lose 1 per second of flight.
  - Fishing rods lose 1 per reel-in, more for hooking mobs.
- **At zero:** the item is destroyed. The Elytra is the exception: it stops at 1 and can't be used until repaired.

### Repair and the enchantment economy

- **Crafting grid and grindstone:** combining two damaged items adds their remaining durability plus 5%. The crafting grid also strips enchantments.
- **Anvil:**
  - Each unit of material restores 25% for 1 level. Combining two items adds 12%.
  - Every anvil use roughly doubles a hidden *prior work penalty* (0, 1, 3, 7, 15, 31...).
  - Any operation costing 40 or more levels is refused with "Too Expensive!".
  - Repeatedly repairing an enchanted item eventually locks it out of anvil repair.
- **Unbreaking:** gives tools a 1/(level+1) chance to take wear, so Unbreaking III lasts about 4× as long. Armour gets much less: Unbreaking III armour lasts about 1.43× as long.
- **Mending:** collected XP repairs equipped damaged items at 2 durability per XP point.
  - It is a treasure enchantment: the table can't roll it, but any librarian can sell it.
  - Mojang's experimental Villager Trade Rebalance moves it to master-level swamp librarians, calling librarian trades "too overpowered".
- **The result.** The prior-work penalty pushes repair out of the anvil and into Mending. Mending turns durability into an XP tax, and villager trading turns Mending into a near-mandatory early purchase. The previous playtest showed exactly this: everyone rushed Mending and Unbreaking, XP became the bottleneck, and an "unbreakable" Netherite pick was a headline goal (see [vision](../vision.md)).

### What durability achieves as a system

| Role | How it works in vanilla | How much it matters |
|---|---|---|
| **Resource sink** | Replacing tools and armour drives demand for iron, diamonds and leather. Anvil repair eats ingots | High early, then almost zero once Mending is on everything |
| **Pacing and early friction** | Wood and stone tools break fast. An iron pick lasts 250 blocks, so the first hours keep sending players back to craft | Real but short. Much of it is tedium rather than decisions |
| **XP sink** | Anvil repair, and Mending as a durability-for-XP exchange | Central. This is what makes XP farms "required" |
| **Villager demand** | Librarian books (Mending, Unbreaking) and toolsmith/armorer gear | High: Mending is the main reason to build a trading hall |
| **Armour as a damage sponge** | Armour wears in long fights and explosions, which in theory makes heavy combat cost something | Weak. Mending cancels it, and armour breaking mid-fight is rare |
| **Mobility limit** | Elytra flight time (about 7 minutes) and Phantom Membrane repair | Mostly cancelled by Mending. Rockets are the real limit on flight |
| **Utility item limits** | Flint and Steel (64), Fishing Rod (64), Shears (238), Brush (64) | Pure friction. Nobody plans around these |
| **Redstone** | Dispensers using Shears or Flint and Steel wear them | Small. Contraptions have to be refilled, but none rely on items breaking |

## Precedents

### Terraria: no durability

- Tools, weapons and armour never break. Progression is paced in other ways:
  - **Pickaxe power gates ore tiers.** Each tier's pickaxe mines the next ore.
  - **Bosses unlock new ores and areas:** the Wall of Flesh starts Hardmode, and the three mechanical bosses unlock Chlorophyte.
  - **Accessories carry a lot of the power curve,** from Hermes Boots through Wings to the Ankh Shield.
- **The money sink is reforging.** The Goblin Tinkerer rerolls an item's modifier for a third of its value. The wiki says chasing a good modifier usually loses money on average.
- Players point out that the surplus ore Terraria doesn't spend on repair goes into building materials (bricks, walls, furniture). The game has no maintenance sink; materials sink into building and new tiers. *(unverified as a stated design goal: no developer statement found)*
- A tModLoader mod adds durability back. It is niche, which suggests few players miss it.

### Starbound: a permanent main tool

- The Matter Manipulator is a never-breaking, upgradeable mining tool that gains mining power, area and liquid collection over the game.
- Pickaxes and drills were made stronger but non-repairable and low-durability. They became rare drops: a consumable alternative to the Manipulator. Mods that give them more durability exist.
- Lesson: one permanent tool that **levels up** replaces the "replace your pick" loop, and it sits close to our levelling-gear idea.

### Valheim: durability with free repair

- Gear wears out, but repair at the crafting station that made it (Workbench or Forge, at the right level, under a roof) is **free**.
- At zero, gear stops working but is **kept**, so you can carry a spare and fix the broken one later.
- Durability here is a **trip length**, not a resource sink: it sets how long you can stay out before going home. The cost is a walk back, not materials.

### Breath of the Wild and Tears of the Kingdom: breakable weapons

- In a 2017 Nintendo Power Podcast interview, the director said breakable weapons make players "more inclined to go defeat that enemy", to get its weapon. The producer said they make "the simple action of swinging a sword in this game a bit more strategic."
- **The debate never settled.**
  - Critics: "as soon as the weapon screen appears, the game moves from action to mundane resource management" (Game Informer). Players also hoard good weapons and never use them.
  - Defenders: breakage forces experimentation, and unbreakable weapons would "stifle that creative drive".
- **Tears of the Kingdom** kept breakage but added Fuse, which lets you stick materials onto weapons. Reviews called it "much better and still annoying". Weapons still can't be repaired.
- Lesson: breakage works when weapons are **plentiful, disposable loot**. That is the opposite of gear you level up and get attached to.

### Core Keeper: broken but kept

- Gear has durability, but at zero it becomes unusable rather than destroyed. It is repaired at the Salvage and Repair Station, which also offers *reinforcement* (double durability, +15% effectiveness).
- The repair cost has moved between scrap parts (from salvaged gear) and the item's own crafting materials over patches. *(unverified which applies in 1.0)*
- On Steam forums, players hold Terraria up as proof that durability isn't needed.

### Don't Starve: durability as survival pressure

- Tools, weapons and armour are consumed and destroyed. Clothing (Sewing Kit) is the only normal repair.
- Armour works as a second health bar that is used up.
- This fits a game about scarcity and attrition. Super Awesome Craft is not that game.

### Minecraft mods and data packs

| Mod | What it does | Notes |
|---|---|---|
| **What Durability?** (~283K downloads) | Removes durability from everything, with an allow-list (e.g. keep it on Elytra or armour) | Supports 26.3. The author says it suits objective-driven RPG packs more than general survival |
| **No Durability** (~37K) | Removes durability completely | Fabric, MIT licence, targets 26.1 |
| **NoDura**, **No Tool Durability**, **notollbreak** | Toggle tool or armour wear per player, by command or config | Separate tool and armour switches are common |
| **No Breaking Items** | Items stop at 0 and lock until repaired | The "broken but kept" middle ground |
| **Unbreakable (Durability Rework)** | Items "shatter" at negative durability and weaken progressively, but stay repairable. Durability is halved, which works out to about triple effective life. No anvil cost cap | A softer failure state |
| **Tinkers' Construct** | Tools go **Broken** at 0 and stop working, but never vanish. Repair uses the head material or Repair Kits with no XP cost. Auto-Repair (Mending Moss) slowly fixes them | The model most modded players know. Tools also level up and gain modifier slots |

- Modrinth's durability search is dominated by **warning** mods: Durability Notifier (~114K), Durability Guard (~67K), Low Durability Switcher (~49K), plus Durability Tooltip at 15M+. Players spend a lot of effort just not losing a Mending pick. That is a strong sign the vanilla failure state (destruction) is the problem more than wear itself.
- **Community reaction in vanilla discussions** (Mojang feedback site and Reddit):
  - One camp wants Mending out of villager trades, or durability raised a lot so that Mending matters less.
  - The other camp says Mending is the only thing that makes Netherite's low durability bearable.
  - A recurring compromise is to lower or remove the XP cost of anvil repair.
  - Both camps agree the current loop (trading hall, Mending, XP farm) is a chore you have to do, not a choice.
- **General design writing** treats durability as a currency or resource sink, and agrees on its failure modes: costs that become trivial late (the Diablo 4 example), and maintenance that feels like punishment. One MMO piece notes "a big difference between maintenance and destruction."

## Consequences of removing durability

### What becomes pointless

- **Unbreaking and Mending.** They must be removed, not just ignored, or they sit in loot and trade tables as dead rolls. Their capacity cost is freed for behaviour enchantments. Ascendant Arcana already disables both.
- **Repair:**
  - Anvil material repair, item combining and crafting-grid repair all go. The grindstone keeps only disenchanting.
  - The anvil's prior-work penalty and the "Too Expensive!" cap lose most of their reason to exist. They were largely there to stop endless repair and stacking.
- **The `repairable` repair-material lists, Phantom Membrane's main use** (Slow Falling potions remain), and **the Ascendant Arcana Durability relic.** Restorine-style repair materials are not needed.
- **Warning and protection mods:** the whole "don't lose the god pick" anxiety disappears.

### Resource sinks to replace

- **Ore.** Vanilla early-game iron demand comes from replacement tools and armour. Without it, one iron pick lasts until the diamond pick, and diamond demand drops to one set per player.
- **XP.** Mending and anvil repair are the two biggest XP sinks. Removing them is good: it **directly removes the playtest's XP bottleneck**. But XP then needs a purpose, or should be retired as Ascendant Arcana does.
- **Villager demand.** Librarians lose their main reason to exist (Mending) and toolsmiths their repeat buyers. This fits the vision's move away from Emerald trading halls.

### Effect on economy and progression speed

- **The early game speeds up a little.** Wood and stone tools stop being a grind, and the first iron pick is the last iron pick. Ore *tiers* (pickaxe tier gates ore) still pace progression, as in Terraria.
- **The "full Netherite and leave" problem gets worse unless something replaces the goal.** In the playtest, maintaining gear (Mending, XP) was part of the ladder. Removing it shortens the ladder unless levelling, slots and horizontal choices fill the gap.
- **Catch-up gets better.** Slower players never fall behind on upkeep, and nothing a casual player owns decays while they are away.

### Armour balance

- Armour becomes permanent damage reduction. Vanilla armour almost never breaks in practice under Mending, so the real balance change is small.
- What goes away is the theory that long fights cost armour. Tune Era Gate encounters with damage, mechanics and consumable pressure instead.
- Shields still have their real limits (axes disable them, block timing). Removing shield wear changes little.
- Explosion-heavy fights (Creepers, Withers, End Crystals) no longer tax armour.

### Technical and redstone items

- **Dispensers with Shears or Flint and Steel** would never run out, which is a quality-of-life gain for farms and TNT machines.
- This technically changes a vanilla mechanic that contraptions use. No common design relies on an item breaking, but it should be listed under **Technical parity** as a deliberate exception.
- **AFK fishing** becomes truly infinite. Mending already made it so, so nothing changes in practice.

### What players gain

- **Less tedium:** no repair trips, no warning mods, no trading-hall-for-Mending.
- **Attachment to gear.** A pick that never breaks can carry levels, slots, a name and a history. Tinkers-style levelling *needs* this: levelling gear that can be destroyed punishes investment. The never-despawning Grave already protects gear on death.
- **Builders get Convenience for free,** which is the vision's fourth root cause ("Convenience was tied to power"). Durability is already listed as Convenience in [CONTEXT.md](../../CONTEXT.md).
- **Multiple specialised tools become sensible.** With Ascendant Arcana capacity budgets, a Fortune pick, a Silk Touch pick and a Smelting pick are permanent and coexist. Making *several* copies replaces *replacing* one.

## Middle grounds

| Option | Example | Pros | Cons for us |
|---|---|---|---|
| **Broken, never destroyed** | Tinkers, Valheim, Core Keeper, Elytra, No Breaking Items | Keeps a trip-length limit and repair sink, and no loss of investment | Still a maintenance chore. Still tempts players toward Mending-like fixes |
| **Durability only on some items** | What Durability? allow-list | Choose exactly where friction lives | Inconsistent rules have to be learned |
| **Cheap or free repair** | Valheim (free at station), Ascendant Arcana Restorine | Wear becomes trip length, not a resource sink | A free chore is still a chore. Little gameplay value left |
| **Per-trip resource / charges** | Energy or charges that refill at home or a station | Gives dungeons attrition without loss | Needs UI, and is only worth it on *active abilities* |
| **Durability only on Power gear** | Weapons and armour wear, tools don't | Matches the Convenience/Power split | Power gear is exactly what players level and get attached to. Breaking it hits the investment hardest |
| **Progressive weakening** | Unbreakable (Durability Rework) | A soft failure state | Complicated, and still upkeep |

## Recommendation for Super Awesome Craft

**Remove durability completely as a loss mechanic. No item ever breaks, wears or needs repair.** Our pillars point one way:

- Durability is already defined as Convenience, and Convenience is cheap.
- Levelling, slotted gear needs permanence.
- Catch-up easing dislikes upkeep.
- The playtest's worst loop (Mending, then XP, then an unbreakable pick) is a durability loop.

The middle grounds all keep a chore to solve a problem we don't have. Players who want attrition get it from consumables in Era Gates.

### What to remove

- **Durability components:** remove `max_damage`/`damage` from every vanilla item, which hides the bar and stops wear. This includes Elytra, Shields, Fishing Rods, Shears, Flint and Steel, Brushes and Carrot/Warped Fungus on a Stick. On new items, never add `max_damage`.
  - Prefer removing the components over setting `unbreakable`. `unbreakable` adds a tooltip line, and that leaves "Unbreakable" in players' heads as an aspiration. *(Check any code paths that test `isDamageable`: enchantment applicability, the anvil, `repairable`.)*
- **Mending and Unbreaking:** delete them outright. Take them out of tables, trades, loot, fishing and raid drops (via the enchantment tags and loot tables; confirm the exact 26.3 tag list). Don't repurpose the names: a "new Mending" that runs on XP brings the XP rush back.
- **Repair:**
  - Drop anvil material repair, item combining and crafting-grid repair.
  - Remove the prior-work penalty and "Too Expensive!"; capacity budgets already limit stacking.
  - The anvil keeps renaming and book application. The grindstone keeps disenchanting, ideally refunding a book or partial materials rather than destroying the item.
- **The Ascendant Arcana Durability relic.** Leave that slot to speed, capacity or behaviour.
- **List under Technical parity:** dispensed Shears and Flint and Steel never wear out. This is a deliberate exception, not a separate variant, because no contraption depends on wear.

### Replacement sinks

1. **Gear growth.** Tinkers-style levels from use, and **slot unlocks or upgrades that cost Era materials.** This is the new long-term iron, diamond and Era-material sink. It sits on Power gear for Power slots, and on tools only for Convenience perks.
2. **Enchanting recipes.** Ascendant Arcana-style material costs for each level, with ingredients from each Era.
3. **More tools, not replacement tools.** Capacity budgets make specialised tools worth owning side by side. Each one costs a full set of materials once.
4. **Consumables as the attrition layer:**
   - Rockets, arrows, potions, food, totems, and new Era-specific consumables (for example dungeon-only buffs or utility charges).
   - These give Era Gates their "how long can we stay in" pressure, which durability never gave reliably.
5. **Settlements and building.** Prosperity buildings, rails and machinery soak up bulk iron and copper, which is the sink that suits the huge-projects motivation.
6. **Charges, but only for active abilities.** If levelled gear gains active abilities (area dig, dash, a burst attack), give *those* a charge or cooldown. Charges refill over time or at a Settlement, so attrition only exists where a decision exists.

### Replacing the "unbreakable pick" goal

- That goal was really "my tool is done and good". Replace it with goals that don't end:
  - tool levels and named milestones,
  - horizontal Convenience perks (vein mining, auto-smelt, magnet, reach) bought with each Era's materials,
  - a Collection Log for gear.
- Make sure the jump from diamond to the next Era's tool still feels big through speed, perks and capacity. Durability numbers should never be the selling point.

### Risks to watch

- **Iron and diamond demand collapse.** Check in playtest that slot and upgrade costs plus Settlements absorb ore. If early iron goes unused, add iron to more Convenience recipes (backpacks, rails, early Shulkers) rather than bringing wear back.
- **Era Gates feel free to brute-force.** Tune encounters against permanent armour, and lean on consumables and mechanics.
- **XP without a job.** Decide whether XP becomes one cost among several (anvil labour, enchant power) or is retired, as the Ascendant Arcana research suggests.
- **ADR conflict.** [ADR-0001](../adr/0001-server-side-fabric-with-polymer.md) assumes an unmodified client. Removing durability components works server-side either way, because clients see the item data the server sends.

## Sources

- Minecraft Wiki, Durability: https://minecraft.wiki/w/Durability
- Minecraft Wiki, Anvil mechanics: https://minecraft.wiki/w/Anvil_mechanics
- Minecraft Wiki, Mending: https://minecraft.wiki/w/Mending
- Minecraft Wiki, Unbreaking: https://minecraft.wiki/w/Unbreaking
- Minecraft Wiki, Villager Trade Rebalance: https://minecraft.wiki/w/Villager_Trade_Rebalance
- Minecraft Wiki, Data component format: https://minecraft.wiki/w/Data_component_format
- Minecraft Wiki, Dispenser: https://minecraft.wiki/w/Dispenser
- Mojang feedback, Mending and trading threads:
  - https://feedback.minecraft.net/hc/en-us/community/posts/38541014453261-Remove-mending-COMPLETELY-bare-with-me
  - https://feedback.minecraft.net/hc/en-us/community/posts/6689178477581-A-problem-with-Mending
  - https://feedback.minecraft.net/hc/en-us/community/posts/6338591738893-Tune-villager-trading
- Reddit mirror, "Anvils should be buffed": https://lr.eu.psf.lt/r/Minecraft/comments/1e6z3ep/anvils_should_be_buffed
- Terraria Wiki, Reforge: https://terraria.wiki.gg/wiki/Reforge
- Terraria Wiki, Game progression guide: https://terraria.wiki.gg/wiki/Guide:Game_progression
- Terraria Wiki, Tools: https://terraria.wiki.gg/wiki/Tools
- tModLoader durability mod: https://www.github.com/hamstar0/tml-durability-mod
- Core Keeper Steam discussion (Terraria comparison): https://steamcommunity.com/app/1621690/discussions/0/3271311587250525456
- Starbounder wiki, Tool: https://www.starbounder.org/Tool
- Steam discussion, Starbound pickaxes: https://steamcommunity.com/app/211820/discussions/0/355043117508498000
- Valheim repair guides:
  - https://allthings.how/valheim-how-to-repair-items-at-the-workbench/
  - https://www.gamewatcher.com/valheim-how-to-repair-weapons-tools-and-armor
- Inverse, developers on BotW durability: https://www.inverse.com/gaming/breath-of-the-wild-2-weapon-durability
- Game Informer, "We deserve better from games with breakable weapons": https://www.gameinformer.com/opinion/2019/08/01/we-deserve-better-from-games-with-breakable-weapons
- Den of Geek, TotK durability: https://www.denofgeek.com/games/tears-of-the-kingdoms-weapon-durability-explained/
- Zelda Dungeon, durability debate: https://www.zeldadungeon.net/?p=211561
- Core Keeper repair:
  - https://corekeeper.atma.gg/en/Salvage_and_Repair_Station (blocked when fetched; content via search summary)
  - https://player.one/core-keeper-patch-033-makes-key-changes-repairing-and-salvaging-items-146593
  - https://techraptor.net/gaming/news/core-keeper-update-adds-balancing-and-qof-improvements
- Don't Starve Wiki, Durability: https://dontstarve.wiki.gg/wiki/Durability
- Modrinth durability search: https://modrinth.com/mods?q=durability
- What Durability?: https://modrinth.com/mod/what-durability
- No Durability: https://modrinth.com/mod/nodurability
- NoDura: https://modrinth.com/mod/nodura
- notollbreak: https://modrinth.com/mod/notollbreak
- No Breaking Items: https://minecraftbible.com/mods/project/no-breaking-items (listing only; page not fetched)
- Unbreakable (Durability Rework): https://www.curseforge.com/minecraft/mc-mods/durability-rework
- Tinkers' Construct modifiers (FTB Wiki): https://ftbwiki.org/Modifiers_(Tinker%27s_Construct)
- Tinkers' Construct tools (Attack of the B-Team wiki): https://attackofthebteamwiki.com/wiki/Tools_(Tinkers%27_Construct)
- Ten Ton Hammer, MMO gear durability: https://www.tentonhammer.com/articles/why-mmo-gear-durability-still-matters-a-lot-today
- Times of Games, Diablo 4 durability: https://www.timesofgames.com/?p=13345
