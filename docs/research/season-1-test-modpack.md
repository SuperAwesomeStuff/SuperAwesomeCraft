# Research: Season 1 Test Modpack

A small, throwaway mod list for trying out "Season 1, Gear" (see [vision](../vision.md)) in single-player or on a small test server, built around [Ascendant Arcana](ascendant-arcana.md). Gathered November 2026 from the Modrinth API (version lists, dependency lists and client/server flags), CurseForge file lists, and the GitHub source of Ascendant Arcana (0.13.6 commit of 16 November 2026), Vein Mining, FallingTree, FTB Ultimine, What Durability? and Lukas' Weapon Leveling. Statements marked *(source)* were read directly from code or mod metadata. Statements marked *(unverified)* come from one secondary source only, or were not checked in-game. **Nothing on this page has been play-tested yet.**

Season 1 is: no XP, no durability, AA-style enchanting with Capacity and Relics (standing in for Augments), and tool Familiarity (cap 5) unlocking toggleable Techniques (vein-mining, tree-felling, area and builder modes) on the pickaxe, axe and shovel. Everything else is vanilla.

## Ascendant Arcana's own requirements

- **Minecraft 1.20.1 only.** 0.13.6 has separate Fabric and Forge files on Modrinth. *(source)*
- **Fabric** (`fabric.mod.json`): Fabric Loader **>= 0.19.3**, Fabric API (any), Architectury API >= 9.2.14, Cloth Config >= 11.1.136, Java 17+. EMI is "suggested". *(source)* The Modrinth version page lists Architectury and Cloth Config as required but leaves Fabric API off, so install Fabric API anyway. *(source)*
- **Forge** (`mods.toml`): Forge 47+, Architectury >= 9.2.14, Cloth Config >= 11.1.136. EMI is optional and client-side. *(source)*
- **Both sides.** Modrinth marks it required on client and server, and it ships client mixins and its own screens. *(source)*
- **Built-in integrations** *(source)*:
  - **EMI** shows the enchanting recipes.
  - **Vein Mining** (TheIllusiveC4) is listed as an optional dependency. AA ships an enchanting-table recipe for `veinmining:vein_mining` (6 Chain, 1 Fire Charge, 3 Lapis), sets its rarity to Common (1 capacity), and makes its own Excavating enchantment incompatible with it.
  - **Majrusz's Enchantments**: AA disables several of its enchantments by default. It is not needed here.
  - **AppleSkin**: there is a Fabric entrypoint for it. It is not needed.
- **Config file:** `config/ascendant_arcana/server.json5` (Cloth AutoConfig, partitioned, Jankson). *(source: serializer; the exact filename is inferred)*
- **Its own Technique-like enchantments** *(source)*:
  - **Excavating** (Uncommon, for any digging tool). Level I also breaks the block below the target (1×2). Level II breaks a 3×3 face. **Sneaking turns it off.** The table recipe (6 Amethyst Shards, 3 Copper, 3 Scrap) has only one level, so level II presumably comes from combining books at an anvil. *(unverified)*
  - **Smelting** auto-smelts drops, and it also works on blocks broken by other mods because it hooks `Block.getDrops`.
- **Issue tracker:** two issues in total (Whirlwind crash, enchanting-power compat request), both closed. No reports of conflicts with any mod below. *(source)*

## No durability

| Option | 1.20.1 loaders | How it works | Notes |
|---|---|---|---|
| **What Durability?** (Serilum) 1.20.1-2.1 | Fabric, Forge, NeoForge, Quilt (one jar) | Mixin on `ItemStack`: `isDamaged` returns false, `getDamageValue` returns 0, and `setDamageValue` is forced to 0. *(source)* | Needs **Collective**. Config `itemsWithDurability` keeps durability on listed items or tags (e.g. Elytra). Max durability is left alone. Modrinth says "server only", but there is client code and in single-player it goes in the normal mods folder. Last updated October 2026. |
| No Durability (AdyTech99) 1.5 | Fabric | Unknown for 1.20.1. The repository only holds the 1.21 code, which zeroes the damage component. | Last 1.20.1 build is from March 2024. Not checked. *(unverified)* |
| NoDurability data pack / mod (unfear_underscore) | Data pack, plus a Fabric/Forge/Quilt wrapper | The description says only that it "removes durability entirely". *(unverified mechanism)* | Last updated December 2023. |
| Vanilla `Unbreakable:1b` NBT | Any | `/give @s diamond_pickaxe{Unbreakable:1b}` (or edit the item with `/item modify`). | Needs no mod, but you have to apply it to each item by hand and the item shows an "Unbreakable" line. |

**How What Durability? interacts with AA** *(from reading both codebases, not tested)*:

- AA's **Durability relic** raises `getMaxDamage`. With What Durability? installed, that does nothing useful, so the relic is a dead pick. Ignore it, or treat its existence as a reminder that Season 1 has to replace it.
- **Restorine and anvil repair** become pointless because nothing is ever damaged. Restorine is still needed to craft the Copper Enchanting Table and the Infusion Smithing Template.
- AA's **Damage relic** checks the item type's base max damage (`getItem().getMaxDamage() > 1`). What Durability? does not change that, so the Damage relic keeps working. *(source)* A no-durability mod that set max damage to 0 would break the Damage relic, which is one more reason to prefer What Durability?.
- The two mods don't mixin into the same `ItemStack` methods. *(source)*
- The durability-related settings in Vein Mining, Ultimine and FallingTree (prevent tool break, damage multipliers) stop mattering.

## Tool levelling

**No 1.20.1 Fabric mod matches "per-tool levels capped at 5 that unlock convenience abilities".** Candidates checked:

- **Tool Leveling+** (1.20.1-2.0.0, Fabric and Forge). It is a block that raises *enchantment* levels above vanilla caps. That is the opposite of AA's stat removal, and it would fight AA Capacity. **Do not use.** *(per Modrinth description)*
- **Lukas' Weapon Leveling** (1.20.1-3.2.0, Fabric and Forge, needs Architectury). Weapons and armour level up from kills and gain damage and protection. Season 1 says weapons and armour do not level, and the bonuses are exactly the stats AA removes. **Do not use.** *(source: config defaults list only melee, projectile and armour items)*
- **LevelsPlus** (4.0.0, Fabric and Forge): "simple weapon and armor leveling". Same problem. *(per description)*
- **Tool Arts** comes closest in spirit: per-player levels for each tool type, abilities at level 5 (Vein Breaker, Lumberjack, Earthmover), triggered by right-click, with no passive stat bonuses. Its maximum level is 100, and it is listed only for **1.21.1 and 26.x**, not 1.20.1. *(per CurseForge page)*
- **Tool Leveling Master** (Forge 1.20.1 only). Tools rise from Common to Legendary and gain attack and mining speed. That clashes with AA. *(per CurseForge page)*
- **Tool Levels** (Forge 1.20.1, pickaxe only, gives mining-speed and ore-drop bonuses) and **Tinkers' Construct with a levelling addon** (Forge, replaces vanilla tools). Neither fits.

**Approximation for the test (honour system):** track use with a vanilla scoreboard and allow yourself one Technique per "level". Example:

```
/scoreboard objectives add pick_uses minecraft.used:minecraft.iron_pickaxe "Pickaxe uses"
/scoreboard objectives setdisplay sidebar pick_uses
```

This counts **per player and per item type**, not per item. It counts every block broken, not "new things", and it has no cap. Pick thresholds (say 200 / 600 / 1500 / 3000 / 5000 uses for levels 1 to 5) and only use as many Technique modes as your level allows. This is a rough stand-in for Familiarity.

## Technique stand-ins

| Mod | Latest for 1.20.1 | Loaders | Needs | Sides | What it covers | How to toggle or gate it |
|---|---|---|---|---|---|---|
| **FTB Ultimine** | 2001.1.8 (5 April 2026) | Fabric and Forge (separate CurseForge projects) | FTB Library (Fabric 2001.2.13), Architectury | Both (keybind) | Vein-mine (Shapeless), **Small Tunnel**, **Large Tunnel 3×3**, **Small Square 3×3**, Mining Tunnel, Escape Tunnel. With the key held: right-click with an axe strips many logs, with a shovel makes many paths, with a hoe tills many blocks. Also harvests crops and amethyst. | Hold the Ultimine key, and scroll to change shape. Server config: `max_blocks` (64), `exhaustion_per_block` (20), `experience_per_block` (0), `require_tool`, `ultimine_cooldown`, each right-click mode on or off. **No enchantment gate.** *(source)* |
| **Vein Mining** (TheIllusiveC4) | 1.5.0+1.20.1 (June 2024) | Fabric, Forge, Quilt | Fabric API (SpectreLib is bundled) | Both | Vein-mining. The default list includes ores **and logs**, so it also fells trunks (not leaves). | **Enchantment-gated by default** (`maxBlocksBase = 0`, `maxBlocksPerLevel = 50`, `levels = 1`). Activation can be STANDING, CROUCHING or HOLD_KEY_DOWN. If you set `maxBlocksBase > 0` it works without the enchantment, using `activationStateWithoutEnchantment` (default HOLD_KEY_DOWN). AA has a table recipe for it. *(source)* |
| **FallingTree** | 1.20.1-4.3.6 (1 November 2026) | Fabric and Forge (one jar) | Cloth Config, Mod Menu and Fabric API are all optional | Server required, client optional | Whole-tree felling, including leaves (default `maxSize` 100 logs, INSTANTANEOUS break mode). | `sneakMode` SNEAK_DISABLE (the default), SNEAK_ENABLE or IGNORE. It has an optional "chopper" enchantment (`registerEnchant`, `requireEnchantment`, both false by default). AA has **no recipe** for that enchantment, so it would only come from `/enchant`. *(source)* |
| AA **Excavating** | (in AA) | | | | 1×2, or 3×3 at level II | An enchantment that costs capacity. Sneaking disables it. *(source)* |
| **Building Wands** (nicguzzo) | 3.0.5 beta (June 2026) | Fabric and Forge | Architectury, Cloth Config, Fabric API | Both | Builder modes (lines, planes, fills) on a separate **wand item**, not on a tool. | It's an item, so having it is the "unlock". It is a beta build. Optional. |

- **Gating Ultimine per player only works on Forge.** Ultimine has a `ftbultimine:max_blocks_modifier` attribute that adds to `max_blocks`. Only the Forge build attaches it to players. The Fabric build never registers it on the player, and the code skips attributes the player lacks, so on Fabric it does nothing. *(source)* On Forge, `max_blocks = 0` plus `/attribute @s ftbultimine:max_blocks_modifier base set 16` could stand in for "unlocked at level N". *(untested)*
- **Don't run Ultimine and Vein Mining together** unless Vein Mining is enchantment-only and you never hold the Ultimine key at the same time. Both break connected blocks. *(precaution, not a reported conflict)*

## Small QoL

- **Mod Menu** 7.2.2 (Fabric, client only): config screens for Cloth-based mods.
- **Jade** 11.13.3 (Fabric file name "1.20-Fabric", supports 1.20.1): shows the block you are looking at and its harvest tool. WTHIT 8.21.2 is the alternative, but it needs Bad Packets. Use one, not both.
- **EMI** 1.1.24+1.20.1: needed to see AA's recipes. It's the same version AA builds against. *(source: `gradle.properties`)*

## Recommendation

**Loader: Fabric.** Every mod needed has a 1.20.1 Fabric build, AA is built and tested against Fabric API 0.92.x, and the real project is Fabric. Forge has the same set (What Durability?, Vein Mining, FallingTree, FTB Ultimine, EMI, Jade and AA all ship Forge files). Its only advantage is that Ultimine can be gated per player through the attribute. That advantage is too small to switch.

### Mod list (Fabric, Minecraft 1.20.1, Java 17+)

| Mod | Version | Link | Role |
|---|---|---|---|
| Fabric Loader | 0.20.1 (anything >= 0.19.3) | https://fabricmc.net/use/installer/ | AA needs at least 0.19.3 |
| Fabric API | 0.92.12+1.20.1 | https://modrinth.com/mod/fabric-api | Base library |
| Architectury API | 9.2.14+fabric | https://modrinth.com/mod/architectury-api | AA and FTB dependency |
| Cloth Config API | 11.1.136+fabric | https://modrinth.com/mod/cloth-config | AA config dependency |
| Ascendant Arcana | 0.13.6 (Fabric) | https://modrinth.com/mod/ascendant-arcana | Enchanting, Capacity, Relics, no XP |
| EMI | 1.1.24+1.20.1+fabric | https://modrinth.com/mod/emi | AA recipe viewer |
| What Durability? | 1.20.1-2.1 | https://modrinth.com/mod/what-durability | No durability |
| Collective | 1.20.1-8.87 | https://modrinth.com/mod/collective | What Durability? dependency |
| FTB Library (Fabric) | 2001.2.13 | https://www.curseforge.com/minecraft/mc-mods/ftb-library-fabric | Ultimine dependency |
| FTB Ultimine (Fabric) | 2001.1.8 | https://www.curseforge.com/minecraft/mc-mods/ftb-ultimine-fabric | Vein-mine plus 3×3 area and tunnel modes, multi-strip and multi-path |
| FallingTree | 1.20.1-4.3.6 | https://modrinth.com/mod/fallingtree | Tree-felling |
| Mod Menu | 7.2.2 | https://modrinth.com/mod/modmenu | Config access |
| Jade | 11.13.3+fabric | https://modrinth.com/mod/jade | Block info |

**Optional swaps:**

- **Vein Mining** 1.5.0+1.20.1 (https://modrinth.com/mod/vein-mining) instead of Ultimine, if you want vein-mining to be *earned at the AA table*. The catch is that it costs 1 Capacity, while Season 1 Techniques use separate slots.
- **Building Wands** 3.0.5 beta (https://modrinth.com/mod/building-wands) for builder modes.

### Config tweaks

- **AA** (`config/ascendant_arcana/server.json5`): leave the defaults. `disable_xp = true` and `hide_xp_bar = true` already match Season 1. Optional changes:
  - `add_relics_to_entities` / `add_relics_to_chests`: keep them on, to feel the drop-luck problem the AA research flagged.
  - `mining_speed_multiplier`: 2.75 is AA's tuning. Keep it, since Relics assume it.
  - `books_remove_scrap_cost = true` if Scrap grinding gets tedious in a short test.
- **What Durability?**: leave `itemsWithDurability` empty, so every item, including Elytra, has no durability. That matches "everything stripped".
- **FTB Ultimine** (server config, SNBT; the file location was not checked *(unverified)*):
  - `max_blocks`: 32 to 64.
  - `exhaustion_per_block`: lower the default 20 to about 0 to 2, so Techniques stay convenience and don't drain hunger.
  - `experience_per_block = 0` (already the default; XP is off anyway).
  - Turn `right_click_hoe` and `right_click_harvesting` off, since hoes and farming are not Season 1 Techniques.
  - `require_tool = true`, so Ultimine needs a tool in hand.
- **FallingTree**: keep `sneakMode = SNEAK_DISABLE`, so sneaking is the off switch, just like AA Excavating. Leave the enchantment off.
- **Honour rules:** use Ultimine only on the pickaxe, shovel and axe. Turn on one shape or mode per Familiarity level (use the scoreboard above). Keep swords and armour as they are.

### Can't be approximated

- **Per-tool Familiarity** with a hard cap, a "new things" bias and a daily allowance. Vanilla stats are per player and per item type. The scoreboard is only a rough proxy.
- **Technique slots separate from Capacity.** In-mod gates (Vein Mining, Excavating) cost Capacity. Ungated mods (Ultimine, FallingTree) are always available, so they depend on self-discipline.
- **Codex and Mastery** (Techniques kept forever, new tools starting at level 2).
- **Augments as designed.** AA Relics stand in, but they are raw stats from drops and recipes. The Durability relic is meaningless with no durability.
- **Per-player Ultimine gating on Fabric** (Forge only, see above).

### Risks

- **No combined testing.** None of these mods report conflicts with AA, and AA's tracker is nearly empty. That only means nobody has tried this combination.
- **Several mods break connected blocks.** AA Excavating, FTB Ultimine and FallingTree all hook block breaking. Excavating plus Ultimine on the same pickaxe has not been tested.
- **Building Wands 3.0.5 is a beta.**
- **What Durability? and Ultimine on a dedicated server** need What Durability? on the server, plus FTB Library, Ultimine and AA on every client.

## Fit with SuperAwesomeCraft

- **Good enough to answer the Season 1 question:** "without a single max-gear endpoint, do people keep playing?" AA gives Capacity trade-offs and material-gated recipes, What Durability? removes the repair loop, and Ultimine plus FallingTree give Technique-style convenience.
- **What the test will mostly measure:** how no durability and AA Capacity feel together, and whether convenience modes feel like a reward. It **won't** test Familiarity pacing. Treat any conclusion about levelling as weak.
- **Things to watch, which feed the design:**
  - Does no-durability make the Durability relic, Restorine and Mending's absence feel like dead content? Season 1 needs replacement sinks (see [durability](durability.md)).
  - Does always-on Ultimine feel like power rather than convenience? If yes, Familiarity gating matters (see [tool levelling](tool-levelling.md)).

## Sources

- https://modrinth.com/mod/ascendant-arcana and Modrinth API `/v2/project/ascendant-arcana/version`
- https://github.com/anticode-403/ascendant_arcana_architectury (`fabric.mod.json`, `mods.toml`, `gradle.properties`, `ServerConfig.java`, `ItemStackMixin.java`, `ServerPlayerGameModeMixin.java`, `Excavating.java`, `EnchantCommandMixin.java`, `data/veinmining/recipes/enchantments/vein_mining.json`, `recipes/enchantments/excavating.json`)
- https://github.com/anticode-403/ascendant_arcana_architectury/issues
- https://modrinth.com/mod/what-durability and https://github.com/Serilum/What-Durability (branch `1.20.1`: `ItemStackMixin.java`, `Util.java`, `ConfigHandler.java`)
- https://modrinth.com/mod/collective
- https://modrinth.com/mod/nodurability and https://github.com/AdyTech99/NoDurability
- https://modrinth.com/mod/novanilladurability
- https://modrinth.com/mod/vein-mining and https://github.com/illusivesoulworks/veinmining (branch `1.20.x`: `VeinMiningConfig.java`, `fabric.mod.json`, `CHANGELOG_LATEST.md`)
- https://modrinth.com/mod/fallingtree and https://github.com/RakambdaOrg/FallingTree (branch `minecraft/1.20.1`: `wiki/Settings---*.asciidoc`, `config/real/*.java`)
- https://www.curseforge.com/minecraft/mc-mods/ftb-ultimine-fabric/files/all?version=1.20.1, https://www.curseforge.com/minecraft/mc-mods/ftb-ultimine-forge/files/all?version=1.20.1 and https://github.com/FTBTeam/FTB-Ultimine (branch `1.20.1/main`: `FTBUltimineServerConfig.java`, `ModAttributes.java`, `FTBUltimineForge.java`, `en_us.json`, `fabric.mod.json`)
- https://www.curseforge.com/minecraft/mc-mods/ftb-library-fabric/files/all?version=1.20.1
- https://modrinth.com/mod/tool-leveling
- https://modrinth.com/mod/weapon-leveling and https://github.com/GekidoLukas/weaponleveling (branch `architectury-1.20.1`: `ConfigLists.java`)
- https://modrinth.com/mod/levelsplus
- https://www.curseforge.com/minecraft/mc-mods/tool-arts
- https://www.curseforge.com/minecraft/mc-mods/tool-leveling-master
- https://modrinth.com/mod/building-wands
- https://modrinth.com/mod/emi, https://modrinth.com/mod/modmenu, https://modrinth.com/mod/jade, https://modrinth.com/mod/wthit
- https://meta.fabricmc.net/v2/versions/loader/1.20.1
