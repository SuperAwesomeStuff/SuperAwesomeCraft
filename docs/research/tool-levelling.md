# Research: Tool Levelling

Should player progression live on tools instead of in a separate skills system? This page covers "gear that levels up with use" systems and how they could fold into the planned Ascendant Arcana-style enchanting (see [Ascendant Arcana](ascendant-arcana.md)) instead of the separate Proficiencies track proposed in [Skill and Levelling Systems](skills-systems.md). Gathered November 2026 from mod pages, the official Tinkers' Construct FAQ, mod source code on GitHub, and game wikis. Statements marked *(source)* were read directly from code. Statements marked *(unverified)* come from one secondary source only, or the sources disagree.

This page assumes the brief's premise that a **client mod is allowed**. [ADR-0001](../adr/0001-server-side-fabric-with-polymer.md) still says unmodified client, and needs revisiting if that has changed.

## Tinkers' Construct across versions

### TiC 1.x (1.7.10): fixed modifier budget, levelling via Iguana Tweaks

- **Base mod.** Tools are assembled from parts, and each tool starts with a small number of free modifier slots (commonly 3; *(unverified)*). Each modifier you apply (Redstone for speed, Lapis for Luck, Quartz for damage and so on) uses one. Extra slots come from one-off upgrades: **Diamond + Gold Block gives +1 slot, single use**, and **a Nether Star gives +1 slot, single use**. Some materials add slots through traits *(unverified)*.
- **Base TiC 1.x never had tool levelling.** The official FAQ says: "Tool leveling was added by an addon, it was never part of the base mod."
- **Iguana Tinker Tweaks** (1.7.10) is the version most people remember, and it shipped in popular packs. *(source: `IguanaConfig.java`, `IguanaLevelingLogic.java`)*
  - XP comes from using the tool for its job. Separate scaling applies to tools (`xpRequiredToolsPercentage`) and weapons (`xpRequiredWeaponsPercentage`), and each level costs **1.35×** the previous one (`xpPerLevelMultiplier`).
  - **Six levels, hard cap**, named as a skill: Clumsy, Comfortable, Accustomed, Adept, Expert, Master. The tooltip literally says "Skill Level". The config comment reads "Can your skill with tools 'level up' as you use them?"
  - `toolLevelingExtraModifiers` (on by default) **removes the free modifiers from newly built tools and gives them back through levelling.** With random bonuses off, slots arrive at levels 2 and 4. With random bonuses on (the default), every level also rolls a random bonus.
  - **Pick boosting.** By default a new pickaxe mines only up to its head material's level and has to be "boosted" to reach the next tier. Boosting uses a separate head XP pool, or a mob head as a shortcut. This made a tool's mining tier something you *earned with that tool*.
  - Takeaway: the remembered "level your tool to gain slots" was a short, capped, six-step ladder that **redistributed** slots the tool would otherwise have had. It did not stack extra slots on top.

### Tinkers' Tool Leveling (1.10 to 1.12.2)

- A separate addon for TiC 2. Tools gain XP by doing their job. Swords earn XP from damage dealt, "only if the mob is killed". **Each level gives 1 free modifier, each level costs twice the XP of the last, and there is "currently no upper limit."** A newly built tool's base modifier count is configurable (default 3).
- The author's own warning: it removes any resemblance to balance, because TiC is built on a limited number of modifiers. It was never updated past 1.12.2.
- A random-modifier variant instead grants a random modifier on level-up, with XP doubling per level by default.

### Tinkers' Construct 3 (1.16+): typed slots from materials and milestones

- **Slot types** (official "What's New"):
  - **Upgrades** are the common slot, roughly one level of a cheap enchantment.
  - **Abilities** are stronger, more unique modifiers. Silk Touch and autosmelt are the examples given, and Unbreakable now needs one.
  - **Defense** slots are for armor and shields.
  - **Slotless** modifiers and **traits** come from materials.
- **Starting slots** (official FAQ). Small tools start with 3 upgrade slots and large tools with 2. Most tools have 1 ability slot. Armor sets start with different mixes: Plate has strong defense slots, Slime has many upgrade and ability slots.
- **How slots are gained. There is no XP; it is all materials and one-off milestone modifiers** (official FAQ):
  - A Book and Quill (Writable), a music disc (Harmonious), a mob head (Recapitated) or a fourth rare item (Resurrected) each gives +1 upgrade slot, up to +4. *(unverified: which item maps to Resurrected)*
  - Rose gold gives +1 per rose gold part.
  - **Gilded** trades 1 ability slot for 2 upgrade slots (golden apple), and can be repeated.
  - **Draconic** and **Rebalanced** are the only ways to add ability slots. Rebalanced grants one extra slot of a type you choose.
  - Defense slots come from Balanced or an armor trim.
  - The caps are explicit: "most melee, harvest and ranged tools have a maximum of 10 upgrade slots, 2 ability slots, and rebalanced's bonus," or 15 upgrade slots with Gilded.
- **Modifiers are refundable.** The modifier worktable removes modifiers and restores their slots.
- **Broken tools are not destroyed.** At zero durability a TiC tool stops working but keeps its modifiers, and repair brings it back. This is a long-standing Tinkers behaviour; it was not re-checked in the 3.x docs for this page.
- **Official stance on levelling.** No plans to add it; "since 1.16, leveling isn't needed for extra slots." The design goal: "Tinkers' Construct by design does not have best tools."
- **Tinkers' Levelling Addon** (unofficial, 1.16.5 to 1.20.1):
  - Levelling is opt-in per item: you apply the **Improvable** modifier (default 1 Nether Star and 4 Bottles o' Enchanting, costing 1 ability slot).
  - Tools earn XP from mining, harvesting, shearing and attacking; armor from taking damage.
  - Each level gives 1 slot and/or a stat, in a fixed configurable order that the tooltip previews, or at random. The cap is configurable and can be unlimited.
  - Its page says it "completely disrupts" TiC's balance.

### Community opinion

- Levelling is remembered fondly as attachment ("my pick"), but it is consistently named as the thing that made tools overpowered once it stacked on top of a slot-budget design. One r/feedthebeast commenter: "The most OP TiC got for me was in a 1.12 pack ... Tool leveling did, additional materials did."
- Every addon author warns it breaks balance. The common thread: **uncapped levelling bolted onto a fixed budget**. Iguana's capped, redistributive version drew much less of that criticism *(unverified, based on the absence of complaints rather than a source)*.
- TiC 3's milestone slots (a mob head, a music disc, a book) are a quiet success. They are one-off collection goals that read as achievements, not grind.

## Other references

| Reference | How it works | Gets right | Gets wrong |
|---|---|---|---|
| **Tetra** | Modular items have an **Integrity** budget: handles and bindings give integrity, strong heads and enhancements cost it. **Honing:** an item counts down `honeBase (450) + 200 × integrity cost` uses, then becomes "honeable", and the player **chooses** an improvement at the workbench. **Settling:** modules with extreme integrity "settle in" with use and gain a Settled improvement. Settle progress is cleared when a module is removed. *(source: `IModularItem`, `ModularItem`, `ItemModuleMajor`)* | The upgrade is a **choice at a workbench**, not an automatic random roll. Time to level scales with how ambitious the build is. The budget is one visible number. | Progress is tied to modules, so swapping a part costs you its progression. The system is complex and hard to read. High tiers sit behind finding ruins (drop and worldgen luck). |
| **Silent Gear** | Parts made from materials. Material **traits** give identity. Upgrade parts (tips, coatings, minor upgrades) are limited to **one of each type**. No use-levelling. | Material identity; "one upgrade per type" is a clean cap. | Nothing grows with use, so there is no attachment. Stats pile up with material tier. |
| **Apotheosis** | Affix items roll a random rarity and affixes. Gems go in sockets, which are added with a **Sigil of Socketing**. A **Sigil of Withdrawal** returns the item and every gem. Salvaging returns materials. | Sockets are non-destructive: contents come back. | Random rarity and affixes are drop luck and push players to reroll. That is the "don't gate behind rare drops" problem. |
| **Path of Exile** | Items provide sockets and links. **Skill gems gain XP while socketed** (10% of character XP), keep it when moved or traded, and stop at their level cap. | **Progression lives on the movable piece.** Replacing your armour never loses your gem levels. | Socket and link colour RNG on the item itself is a notorious currency sink. |
| **Diablo II** | Sockets limited by base type and item level. Larzuk adds sockets **once per difficulty**. Runewords are permanent. | Scarce socket-adding works as a milestone reward. | Permanent socketing means mistakes cost the item. |
| **Final Fantasy IX** | Equipment grants abilities while worn and earns AP. At full AP the character **learns the ability permanently** and can drop the item. | **The best answer to "losing the tool":** use the item, keep the knowledge. It drives players to try new gear. | Late game, the old equipment becomes stat-stick junk. |
| **Minecraft Dungeons** | Each character level gives 1 enchantment point, spent on enchantments on individual items. **Salvaging an item refunds every point.** | Budget on the player, spending on the item, full refund. Swapping gear costs nothing. | Levels are pure XP grind; the item itself has no history. |
| **Terraria** | Reforging at the Goblin Tinkerer re-rolls a modifier for a third of the item's buy value. It has no memory and every eligible modifier is equally likely. Accessories form a loadout. | Accessory loadouts are horizontal choices. Reforging is a steady money sink. | Reforging is a gamble; chasing Legendary or Warding is slot-machine play. |
| **Dark Souls 3 / Elden Ring** | Fixed material ladders: Titanite Shard to +3, Large to +6, Chunk to +9, Slab to +10. Somber stones upgrade special weapons one tier at a time and are scarce. | Deterministic, legible, material-driven, like Ascendant Arcana's recipes. | Investment is non-transferable and scarce, so players commit to one weapon early and stop experimenting *(inference; no source states it directly)*. |
| **Stardew Valley** | Tools are upgraded by Clint for gold plus bars (Copper 2,000g + 5 Copper Bars up to Iridium 25,000g), cannot skip a tier, and are **gone for 2 days**. Skills are separate and player-level. | A tool tier is a clear purchased milestone. | The tool and the skill never interact. Downtime without the tool is a pure annoyance. |
| **Valheim** | Item quality is upgraded at a workbench or forge, and higher qualities need **station level** (bench upgrades). Skills level by use and drain on death. | Station level works as era gating: progress shows in your base. | The death drain on skills is disliked (see [skills research](skills-systems.md)). |
| **Core Keeper** | Skills level by use (Mining: +1 mining damage per level up to +100). Every 5 levels gives a talent point. | Talent choices are memorable. | 100 levels of +1 creep is the "numeric bonuses are boring" problem. Progress is on the player, so tools are interchangeable. |

**Pattern across all of them:** the systems people like put **unlocks and knowledge somewhere they cannot lose them** (FF9, Path of Exile gems, Minecraft Dungeons points, TiC 3 refundable modifiers). They put **slots or budget on the item**, and make **upgrading a choice, not a roll** (Tetra honing, Ascendant Arcana recipes). The systems people complain about are uncapped (TiC 2 addons), random (Apotheosis, Terraria, Path of Exile sockets) or non-transferable and scarce (Soulsborne).

## Problems with tool levelling

- **Losing the tool.**
  - *Death.* Graves already cover this ([CONTEXT.md](../../CONTEXT.md)). The leak is lava, void and despawn, so graves need to catch void and lava deaths too.
  - *Breakage.* In vanilla a tool at zero durability is deleted along with all its history. TiC's "broken, not destroyed" rule is essential if tools carry levels.
  - *Tier upgrades.* If an iron to diamond to netherite upgrade resets progress, players delay upgrading or never bond with a tool. Vanilla's netherite smithing upgrade already keeps enchantments, and the same rule should apply to levels.
  - *Wanting a second tool.* A Silk Touch pick and a Fortune pick should not mean levelling twice from zero.
- **AFK levelling.** Any XP for repetition turns into a farm: AFK fishing rods, swords at mob grinders, autoclicker strip-mining. Tool levelling is somewhat *less* exposed than player skills, because automated farms don't hold your tool. Grinders and fishing still do.
- **Power compounding.** The TiC 2 addons show it: uncapped slots on top of a slot budget give a god tool. On weapons and armour, any level-based slot is Power by definition.
- **New players behind.** A veteran's level-30 tool is a permanent lead. Levels that live on items can at least be handed down; levels that live on a player cannot.
- **Rotating tools to dodge limits.** A daily XP allowance per tool is dodged by carrying five pickaxes. Allowances have to be per player per tool type.
- **Builders and explorers don't swing tools.** Mining, forestry, farming and fighting map onto tools. Building, exploring and settling mostly don't.

## Recommendation for SuperAwesomeCraft

**Yes: fold Proficiencies into tools, but split them three ways rather than moving everything onto items.** The designer is right that a separate Proficiency XP bar per playstyle would duplicate what enchanting already does: both are "use this tool more, it gets more convenient." The fix is to put each piece of progression where it cannot be lost and cannot compound.

- **The tool holds slots and history.** It earns Technique slots by being used.
- **The player holds knowledge.** The Codex records which Techniques and recipes you have learned. Tool-type Mastery sets a floor for every future tool of that type.
- **Eras and materials hold Power.** Enchantment capacity and relic tiers stay exactly as in the Ascendant Arcana plan.

Working names below (Familiarity, Technique, Mastery, Codex) need settling in [CONTEXT.md](../../CONTEXT.md).

### 1. Familiarity: a short level track on each tool

- **Five levels, hard cap.** That is Iguana's six-step ladder, minus one. No exponential tail: levels 1 to 2 take an evening of normal use, level 5 takes about a week of real use of that tool.
- **Applies to:** pickaxe, axe, shovel, hoe or sickle, shears or brush, fishing rod, and two signature tools for playstyles that don't swing one: a **Builder's Wand** and a **Wayfinder's kit** (glider or compass). **Weapons and armour do not gain Familiarity** (see below).
- **XP, weighted toward novelty:** first time breaking each block type with this tool type, new biomes or layers, distinct crops, distinct structures reached, blocks placed inside a claimed project area (wand), new chunks entered (glider). Ordinary repetition gives full XP for about an hour a day **per player per tool type**, then a 5% floor (the [skills research](skills-systems.md) allowance, moved here).
- **No Familiarity XP from:** player-placed blocks broken again, mobs from spawners or crowding farms, or fishing while standing still for more than N minutes. The cap of 5 makes cheating mostly pointless anyway.
- **Level-ups are a choice moment, Tetra-style.** At levels 2, 4 and 5 the tool becomes "ready", and you open it at a workbench to fit the new slot. No random rolls.

### 2. Slots and what they hold

| Slot | Gained by | Holds | Convenience or Power |
|---|---|---|---|
| **Enchantment capacity** | Base material (Ascendant Arcana: Diamond 10, Netherite 15), capacity relics | Behaviour enchantments made from era-gated recipes | Tools: Convenience, except yield (Fortune, Looting), which stays capacity-expensive. Weapons and armour: Power |
| **Relic slots (2)** | Fixed per item | Stat relics, crafted by Era | Haste and Durability on tools: Convenience. Damage and Protection: Power |
| **Technique slots (0 to 3)** | **Familiarity only:** 1 at level 2, 2 at level 4, 3 at level 5 | Techniques: toggleable tool modes | **Always Convenience** |
| **Milestone slot (1, optional)** | TiC 3-style one-off: a Collection Log item tied to the tool (a mob head for the sword, a music disc for the wand) | One extra Technique | Convenience |

- **Keep the three budgets orthogonal.** Familiarity never adds enchantment capacity or relic slots. If it did, use would buy Fortune III and yield would compound, which is exactly the TiC 2 addon failure. Capacity and relics stay material and Era progression; Techniques stay use progression.
- **Techniques by tool** (the Convenience content the skills research gave to Proficiency ranks):
  - Pickaxe: vein-mine, 3×3 toggle, ore-sense pulse.
  - Axe: tree-fell, area strip.
  - Shovel: area flatten, path lines.
  - Hoe or sickle: area harvest and replant, 3×3 till.
  - Builder's Wand: line, plane and fill modes, palette randomiser, reach.
  - Wayfinder's kit: longer glides, campfire updrafts, structure-hint pings that follow the learnable placement rules.
  - All techniques are opt-in toggles, so they never interfere with Technical parity contraption work.
- **Techniques have ranks gated by Era materials** (vein-mine 8, then 16, then 32 blocks). Rank 1 must be cheap and early: Convenience is cheap. Higher ranks make convenience *bigger*, never stronger in combat.

### 3. Carry progress forward

- **Smithing tier-ups keep everything:** Familiarity, Techniques and enchantments that still fit the new capacity. That covers iron to diamond to netherite to the sky-dimension tier. Vanilla smithing already carries components onto the result; *(unverified for 26.3 custom components; check in code)*.
- **Tools break to "Broken", not to nothing.** They keep all data and are repaired with Restorine.
- **Re-hafting:** a cheap workbench recipe moves Familiarity and Techniques from an old tool onto a new tool of the same type. This lets you build a second, Silk Touch pick without starting from zero, at the cost of the old tool's Familiarity (so no duplication).
- **Techniques and enchantments come out non-destructively** (TiC worktable, Apotheosis withdrawal). This replaces Ascendant Arcana's destroy-for-scrap grindstone as the main removal path.

### 4. What stays player-level

These are the parts that should *not* live on tools, and they replace the rest of Proficiencies:

- **The Codex (knowledge).** The first time any of your tools reaches level 2, 4 or 5, you learn that tier's Techniques permanently, FF9-style. A tool can only slot Techniques you know. The Codex also holds **recipes and blueprints**: builder palettes, Settlement building types, locomotion track, technical-player recipes. These unlock from Collection Log pages and Prosperity, not from XP. Knowledge is never lost with a tool.
- **Mastery per tool type.** Taking any tool of a type to level 5 marks you a Master of that type. Every **new** tool of that type you craft starts at Familiarity 2 and levels at double speed up to 4. This is the anti-loss floor and the main catch-up mechanic.
- **Settling stays with Settlements.** Running a Settlement is not a tool; its progression is Prosperity. Remove the Settling Proficiency entirely.
- **Era unlocks stay personal** per [ADR-0002](../adr/0002-personal-progression-with-catch-up-easing.md). Power remains the Era ladder.
- **No player XP bars.** Proficiencies as a separate system go away. What survives is the Codex (a list of things you know), the Mastery flags, and the Collection Log that feeds both.

### 5. Fighters: weapons and armour do not level

Combat Power is the Era ladder's job. Weapon Familiarity would be Power from hours played, the MassiveCraft complaint. The fighter's project stays Era Gates, encounter loot and relic tiers. If weapons need attachment, give them a **cosmetic record**: kills per mob type, Gates cleared, a title in the tooltip. That is history without power.

### 6. Catch-up easing

- **The cap is the catch-up.** Five levels, and a newcomer reaches parity on a tool in about a week.
- **Mastery floor:** after your first level-5 pick, every later pick starts at 2.
- **Pathfinder bonus:** once anyone on the server has Mastery of a tool type, everyone else earns double Familiarity XP for that type up to level 4. This is Catch-up easing applied to tools.
- **Rested bonus:** offline time builds a pool of double Familiarity XP, capped at one level's worth.
- **Hand-me-downs:** a levelled tool is an item, so a veteran can give one to a new player. The receiver still needs the Codex entries to *use* its Techniques beyond what they know. Until they learn them, those slots act as tier-1 Techniques, so a gift helps without skipping the system.

### 7. AFK and abuse, summarised

The system relies on its shape rather than on detection: novelty-weighted XP, a daily allowance per player per tool type, a cap of 5, and Convenience-only rewards. Grinders and AFK fishing reach the cap a few days sooner at most and gain nothing past it. Farms stay vanilla and welcome for materials.

### Risks to watch

- **Builder and explorer fit.** The signature-tool idea (Wand, Wayfinder's kit) needs prototyping. "Blocks placed in a project area" shares the unsolved Prosperity measurement question.
- **Technical players** have no tool. Their progression is Codex recipes (from the Collection Log and Prosperity); check that this is enough of a project.
- **Too much Convenience behind Familiarity.** Keep level 2 (first Technique slot) an evening's play. Basic comfort such as early Shulkers and the glider stays outside the system entirely.
- **UI.** A client mod makes this easy: a Familiarity bar in the tooltip, a slot screen at the workbench, and a keybind or radial menu for toggling Techniques. If ADR-0001's unmodified-client rule still holds, Technique toggles become a sneak-use cycle and the slot screen a chest GUI.

## Sources

- Tinkers' Construct 3 FAQ (official): https://slimeknights.github.io/faq/tinkers-construct-3
- Tinkers' Construct, "What's New" (official): https://slimeknights.github.io/docs/whats-new
- Tinkers' Construct, "Making the Best Tool": https://slimeknights.github.io/faq/best-tool
- Tinkers' Construct modifier recipe docs: https://slimeknights.github.io/docs/json/recipes/modifiers
- TiC 1.x modifiers (Diamond + Gold Block, Nether Star): https://attackofthebteamwiki.com/wiki/Modifiers_(Tinkers%27_Construct)
- Iguana Tinker Tweaks: https://www.curseforge.com/minecraft/mc-mods/iguanas-tinker-tweaks
- Iguana Tinker Tweaks source (`IguanaConfig.java`, `IguanaLevelingLogic.java`): https://github.com/iguanaman/IguanaTweaksTConstruct
- Tinkers' Tool Leveling (1.10 to 1.12.2): https://www.curseforge.com/minecraft/mc-mods/tinkers-tool-leveling
- Tool Leveling with Random Modifiers: https://www.curseforge.com/minecraft/mc-mods/tinkers-construct-tool-leveling-with-random
- Tinkers' Levelling Addon (TiC 3): https://modrinth.com/mod/tinkers-levelling-addon
- r/feedthebeast comment on TiC levelling: https://reddit.rtrace.io/r/feedthebeast/comments/12w7cy8/modpack_cook_needs_ur_intel_what_are_ur_biggest/jhhs0pa/?context=3
- Tetra source (`IModularItem.java`, `ModularItem.java`, `ItemModuleMajor.java`): https://github.com/mickelus/tetra
- Tetra overview: https://wiki.9minecraft.net/java/mod/tetra/
- Silent Gear: https://modrinth.com/mod/silent-gear
- Apotheosis gems and sockets: https://www.minecraft-guides.com/mod/apotheosis/wiki/gems-overview/
- Apotheosis guide: https://www.minecraft-guides.com/mod/apotheosis/
- Path of Exile gems: https://www.poewiki.net/wiki/Gems
- Path of Exile item sockets: https://www.poewiki.net/wiki/Link
- Diablo II sockets: https://diablo2.diablowiki.net/Socket
- Diablo II, Larzuk socket quest: https://almarsguides.com/computer/games/diablo2/quests/permanentrewards/larzukaddsocket/
- Final Fantasy IX abilities: https://www.cavesofnarshe.com/ff9/support-abilities.php
- Final Fantasy IX, learning abilities: https://mail.jegged.com/Games/Final-Fantasy-IX/Tips-and-Tricks/Learning-New-Abilities.html
- Minecraft Dungeons level and enchantment points: https://minecraft.gamepedia.com/Minecraft_Dungeons:Level
- Minecraft Dungeons, reclaiming enchantment points: https://shacknews.com/article/118265/how-to-reclaim-enchantment-points-minecraft-dungeons
- Terraria modifiers and reforging: https://terraria.wiki.gg/wiki/Modifiers
- Dark Souls 3 upgrades: https://darksouls3.wiki.fextralife.com/Upgrades
- Elden Ring Somber Smithing Stones: https://eldenring.wiki.gg/wiki/Somberstones
- Stardew Valley tools: https://stardewvalleywiki.com/Tools
- Stardew Valley blacksmith: https://wiki.stardewvalley.net/Blacksmith
- Valheim workbench upgrades: https://www.pcgamer.com/valheim-workbench-upgrade-level
- Core Keeper skills: https://core-keeper.fandom.com/wiki/Skills
- Core Keeper mining: https://corekeeper.atma.gg/en/Mining
