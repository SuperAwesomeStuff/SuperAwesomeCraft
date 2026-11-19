# Research: Ascendant Arcana

Summary of Ascendant Arcana, an enchanting overhaul mod, gathered November 2026 from the minecraft-guides.com page, the Modrinth and CurseForge listings, and the mod's GitHub source (README, server config, recipe data and Java code at the 0.13.6 commit of 16 November 2026). Statements marked *(source)* were read directly from the code or data files. Statements marked *(unverified)* come from one secondary source only, or the sources disagree.

## Core idea

The README describes it as "making enchantments unique modifiers to your tools, rather than glorified stat bonuses that made up most of your vanilla power."

- **Stat enchantments are removed.** By default it disables Protection (all four kinds), Sharpness, Smite, Bane of Arthropods, Impaling, Power, Efficiency, Quick Charge, Unbreaking and Mending. The list is configurable. *(source: `ServerConfig.disabled_enchantments`)*
- **Stats come from Relics** instead, and enchantments are left for behaviour: new effects, projectiles, utility.
- **XP is removed by default.** Enchanting and anvils cost materials, not levels, and the XP bar is hidden. Turning XP back on is supported. *(source: `disable_xp = true`; the anvil mixin blanks the level cost)*

## Enchanting

- **Deterministic recipes.** Each enchantment level has its own fixed recipe: a primary ingredient, an optional secondary ingredient and a "scrap" stack (Lapis for early levels, Enchanted Scrap later). You pick the enchantment you want from a list; nothing is random. *(source: `recipes/enchantments/*.json`)*
- **Recipes climb through the eras.** Fortune I costs 3 Copper, 2 Iron and 3 Lapis. Fortune II costs 4 Quartz, 2 Gold and 3 Scrap. Fortune III costs 3 Diamonds, 1 Echo Shard and 3 Scrap, so it waits on an Ancient City. Silk Touch is String, Honey and Lapis. Smelting is Blaze Powder. Salvo uses Pitcher Plants. *(source)*
- **Two tables.**
  - The **Copper Enchanting Table** (Book, 2 Restorine, 4 Cut Copper) is an early option. Its power is capped at the Uncommon threshold, so it can only do Common and Uncommon enchantments. *(source)*
  - The vanilla Enchanting Table uses the new recipe screen and has no cap.
- **Power gates rarity.** The thresholds are Uncommon 22, Rare 55 and Very Rare 100. A plain Bookshelf gives 1 power. A Chiseled Bookshelf gives the sum of level × rarity multiplier (1/2/3/5) for the enchanted books stored in it. Detection range is 5 blocks horizontally and 2 vertically, and other blocks between the table and the shelves do not block it. *(source)* The README says Rare and Very Rare *require* Chiseled Bookshelves.
- **The library is the progression.**
  - Treasure enchantments cannot be made at the table until a copy sits in a nearby Chiseled Bookshelf.
  - By default, a stored book also lets you enchant it one rarity tier lower than normal (`books_tier_bypass = 1`).
  - A config option lets stored books waive the scrap cost; it is off by default. *(source)*
- **Enchanted Scrap** comes from two places. *(source)*
  - Crafting: 2 Lapis, 5 Gold Nuggets and 2 Amethyst Shards *(per guide; the recipe file was not read)*.
  - Grinding: putting an enchanted item in a grindstone **destroys the item** and returns scrap. Each level gives 1/2/3/4 scrap by rarity, +1 for treasure enchantments.
- **Enchanted books hold a single enchantment by default.** *(source)*

## Capacity (the cap and trade-off)

- Every item has an enchantment capacity budget. *(source)*
  - The default is based on the material's enchantability: Diamond 10, Netherite 15.
  - Overrides: Bow and Crossbow 20, Shield and Trident 15, Enchanted Book 12.
- Each enchantment level costs capacity by rarity: Common 1, Uncommon 2, Rare 3, Very Rare 5. Curses cost a flat 1. The anvil refuses any combination that goes over capacity. *(source)*
- Several vanilla enchantments get new rarities. Fortune and Looting become **Very Rare**, Infinity, Swift Sneak and Soul Speed become Rare, and Feather Falling, Knockback and others become Common. *(source)*
- **Worked example.** Fortune III costs 15 capacity, which is more than a diamond pickaxe holds. It needs Netherite, or a capacity Relic, and then fills the whole item. Silk Touch (Very Rare in 1.20.1) costs 5. Players have to choose a build rather than stack everything.

## Relics (stat bonuses)

- Relics are data-driven stat items, applied with an Infusion Smithing Template (Calcite and Restorine) at a Smithing Table. Each item holds **2 relics** by default; Crossbows hold 1. *(source)*
- Five tiers: Dormant, Stirring, Waking, Awakened, Ascendant *(names per guide)*. Strengths by tier *(source: `relics/*.json`)*:

| Relic | T1 | T2 | T3 | T4 | T5 |
|---|---|---|---|---|---|
| Damage (tools) | +10% | +16% | +22% | +26% | +30% |
| Haste (attack/mining speed) | +10% | +20% | +30% | +40% | +50% |
| Durability | +600 | +1200 | +1800 | +2400 | +3000 |
| Protection (armor) | +3% | +6% | +9% | +12% | +15% |
| Enchantment capacity | +5 | +10 | +15 | +20 | +25 |

- The guide lists the capacity relic as +10/+20/+30. The data files say +5 to +25, and we use those. *(unverified which one is current in-game)*
- **Sources.**
  - Witches, Wither Skeletons and bosses drop them, archaeology finds them, and they are added to any chest loot table that already contains an enchanted book. *(source config; archaeology per README)*
  - Crafting: there are shapeless recipes for every tier. Damage T1 uses Smooth Sandstone and Leather, T2 Stone and Leather, T5 End Stone, Blaze Powder and Leather. Protection T5 uses Gold and Netherite Scrap. The matching code also accepts a relic of the tier below. The README says later relics "must be found or obtained by slaying bosses." *(unverified how crafting and finding interact in play)*
- The config sets the base mining speed of all tools to **2.75×** so that a T5 Haste relic is "slightly better than Efficiency 5 on a Netherite Pickaxe." *(source)*

## Repair

- Mending is removed.
- **Restorine** is a purple crystal that grows in caves (Overworld and Nether per the guide) and regrows from Budding Restorine, like Amethyst. On an anvil it is a universal repair material worth 12.5% durability. Vanilla repair materials now repair 50% instead of 25%. Restorine also shows up in chests that contain diamonds or amethyst. *(source)*
- The 0.13.6 update "Massively increased spawn rate of Restorine Growths." *(source: commit log)*

## New enchantments

There are about 50 recipes, roughly 35 to 40 of them for new enchantments. *(source: recipe and class list)*

- **Melee:** Allegro, Debilitating Chain, Jolting (chain lightning), Meganeura, Pincushion, Snowball, Soul Burst, Stopping Power, Slaying Tempo.
- **Bows and crossbows:** Archer's Gambit, Blazebolt, Evoker's Wrath, Guiding, Hobbling Shot, Miasma, Prepared Shot, Rejuvenating Shot, Ricochet, Shattershot, Repeating, Salvo, Rocketry. Crossbows cannot load rockets without Rocketry.
- **Shield:** Bashing, Cleanse, Cross Counter, Deflect, Sonic Blast.
- **Trident:** Ambush, Lifetide, Singularity, Storm Anchor, Sundering.
- **Tools:** Smelting, Excavating.
- **Armor:**
  - Heart enchantments for the chestplate, one at a time: Bladeheart, Heart of the Nether, Heart of the Storm, Coldheart, Witch's Heart, Alchemist's Heart, Heart of the Turtle. Bladeheart costs a boss "heart item" (the Warden Heart), 12 Scrap and 8 Diamonds.
  - Glacioclasm, Launching, Protective Echo, Strafe, Surefoot, Surrounded, Hellwalker.
- **Elytra:** Cushioning, Whirlwind, Rocket Riding. Rockets do not boost Elytra without Rocket Riding.
- **Curses:** Depths, Enfeeblement, Inaccuracy.

## Other vanilla changes

Infinity no longer needs an arrow. Piercing works on bows only. Arrows from the same shooter ignore i-frames. The shield's 5-tick raise delay is removed. The enchanting table's range grows and shelves can no longer be obstructed. *(README)*

## UI

- A custom enchanting screen shows a recipe list, enchanting power and slots for the item, scrap, primary and secondary ingredients. *(source: `AArcanaEnchantingScreen`, `AArcanaEnchantingMenu`)*
- The XP bar is hidden.
- EMI integration shows the recipes.
- The mod needs a client install. It ships client mixins, custom entity models and renderers, particles and its own screen.

## Versions, loaders, licence

- **Minecraft 1.20.1 only**, on Forge and Fabric (Architectury). The latest release is 0.13.6, from 16 November 2026. The guide's "1.20+" is looser than the actual files.
- The mod is actively developed. Planned: a potion overhaul, spellbooks and more enchantments.
- **Licence, conflicting labels:**
  - The repository `LICENSE.txt` is **LGPL-3.0-or-later**.
  - Modrinth says GPL-3.0-or-later.
  - CurseForge says GPLv3.
- Either way it is copyleft, so any copied code must stay under a compatible licence. Mechanics and ideas are free to borrow. We should not copy art: textures are by a credited artist.
- A code port is impractical anyway. The mod is built on the 1.20.1 `Enchantment` class, NBT and Forge-era APIs. Since 1.21, enchantments are data-driven components, so we would rewrite rather than port.
- **Lookalikes to avoid:** "Ascendant Enchanting" (a NeoForge port of Apothic Enchanting) and "Arcana Ascendente" (enchantments beyond max level) are different mods.
- **Wiki and videos:**
  - No wiki exists; the GitHub wiki redirects to the repository.
  - One showcase video was found, "Check out this UNDERRATED Enchanting Overhaul Mod!". It was not reviewed for this summary.

## Fit with SuperAwesomeCraft

**Fits the pillars:**

- **Splitting stats from behaviour is our split between convenience and power.** Removing Efficiency, Unbreaking, Mending and Protection takes the "everyone rushes the same four enchantments" endpoint out of the table entirely. Behaviour enchantments are sidegrades, not a ladder.
- **Capacity budgets get rid of the single "max gear" endpoint.** When Fortune III fills a whole pickaxe, a Fortune pick, a Silk Touch pick and a Smelting pick become different tools rather than one god tool. The two relic slots repeat the trade-off for stats: haste or durability or capacity.
- **Per-level recipes gate by era naturally.** Fortune I is Copper and Iron, II is Nether Quartz, III is an Echo Shard from an Ancient City. That is the right level of "familiar action, new structure": a table that tells you exactly what you need, without random rolls.
- **It removes XP as the bottleneck.** Material costs spread the demand across mining, farming, the Nether and exploration, which is what our playtest lacked.
- **The library gives a Collector or Librarian a project that matters.** Chiseled Bookshelves full of books *are* the table's power, so a shared library could be a group project. Personal unlocks would still need a per-player rule.
- **Restorine is cheap convenience.** Renewable universal repair replaces Mending without being power. Doubling the value of vanilla repair materials is cheap convenience too.
- **Early access.** The Copper table puts useful but limited enchanting early, so falling-behind players and new joiners can catch up on Common and Uncommon enchantments cheaply.

**Does not fit, or needs changes:**

- **It breaks technical parity.** Disabling vanilla enchantments, a global 2.75× mining speed, arrow i-frame bypass, shield delay removal, Piercing limited to bows, and rockets locked behind enchantments all change vanilla mechanics in place. For us these must be **variants**: a new table block, new relic items, new enchantments. Vanilla items keep exact behaviour.
  - Open question: does the vanilla Enchanting Table stay available unchanged? If it does, the rush to max gear stays available too.
- **Grinding destroys the item.** That is harsh as a catch-up mechanic. Prefer extracting a book, or a partial refund.
- **Random relic drops break our pillars.** Relics from witches, chests and bosses, with tier-5 parts behind bosses, are drop-luck gating (see the Bottom Dweller research) and first-come in multiplayer. Prefer crafted or progression-unlocked tiers, with drops as a shortcut.
- **Removing XP entirely may be too far.** Instead XP could become one cost among several, such as anvil labour, so it is no longer *the* bottleneck.
- **Rocketry and Rocket Riding gates** take away existing vanilla capabilities. That is against parity, and players would resent it.
- **Very Rare power (100) favours big bases.** We should check that a solo player can reach it.

**Server-side (Polymer) feasibility:**

- **Works server-side with a resource pack:**
  - Custom table and Restorine blocks and items (Polymer blocks and items with models in the pack).
  - The recipe-picker screen: an SGui chest or anvil GUI with a font-texture background stands in for the custom screen.
  - Capacity and relic info as lore lines on tooltips.
  - New enchantments defined in data since 1.21, which unmodded clients accept.
  - Relic stats as attribute modifiers.
  - Repair rules in the anvil, with XP cost hidden in the GUI.
  - Hiding the XP bar by making its HUD sprites transparent in the pack.
  - Shield block delay through the `blocks_attacks` component.
- **Needs approximation:** projectile and AOE effects such as Singularity, Glacioclasm and turrets can use display entities and vanilla particles, but not custom models with animation or custom particles. Client-predicted behaviour, such as crossbow loading and Elytra boost, may rubber-band if we deny it on the server.
- **Needs a custom client:** a true scrolling recipe list with native widgets, a power meter that renders outside a container GUI, custom particle types, and EMI or JEI-style recipe browsing. Use a server-side recipe book GUI instead.

## Recommendations

1. Use **item capacity budgets** and **material recipes for each level** as the core of a new table variant, with recipe ingredients tied to eras.
2. Move raw stats (speed, durability, protection, damage) into a **slotted, upgradable component**, limited to 2 per item and crafted through eras, so stat power is a choice, not a checklist.
3. Make repair cheap and renewable (a Restorine equivalent); keep Mending out of the new table.
4. Use the **Chiseled Bookshelf library** as a power source and unlock gate: a long-term project for collectors.
5. Keep vanilla mechanics exact. Settle how the vanilla table coexists before building.

## Sources

- https://www.minecraft-guides.com/mod/ascendant-arcana/
- https://modrinth.com/mod/ascendant-arcana
- https://www.curseforge.com/minecraft/mc-mods/ascendant-arcana
- https://github.com/anticode-403/ascendant_arcana_architectury (README, `LICENSE.txt`, `common/.../config/ServerConfig.java`, `logic/AArcanaEnchantmentHelper.java`, `screenhandler/AArcanaEnchantingMenu.java`, `mixin/AnvilMenuMixin.java`, `mixin/GrindstoneMenuMixin.java`, `data/ascendant_arcana/recipes/**`, `data/ascendant_arcana/relics/*.json`)
- YouTube showcase, "Check out this UNDERRATED Enchanting Overhaul Mod!" (not reviewed): https://www.youtube.com/watch?v=YQqhqYCjYYg
