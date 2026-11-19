# Research: Skill and Levelling Systems

Summary of how skill systems work in Minecraft plugins and mods and in the games they borrow from, their known problems, and what that means for Super Awesome Craft. Gathered November 2026 from plugin wikis, default config files in the plugins' repositories, game wikis and server forums.

## AuraSkills (formerly Aurelium Skills)

A Spigot/Paper plugin that adds RPG skills, stats, abilities and mana. Everything is configurable.

- **Skills:** 11 by default: Farming, Foraging, Mining, Fishing, Excavation, Archery, Defense, Fighting, Agility, Enchanting and Alchemy. Older versions had 15 (Endurance, Healing, Forging and Sorcery were merged or removed).
- **XP sources:** ordinary vanilla actions: breaking blocks, killing mobs, catching fish, brewing, enchanting. Blocks placed by players give no XP by default (`check_block_replace`), and spawner kills can be scaled down.
- **Caps and curve:** each skill caps at level 100 by default. The cost of each level is quadratic (`100 * (level - 2)^2 + 100`), so the last levels cost far more than the first.
- **Stats:** nine stats: Strength, Health, Regeneration, Luck, Wisdom, Toughness, Crit Chance, Crit Damage and Speed. Each skill raises a primary stat every level and a secondary stat every two levels. Mining, for example, gives Toughness (damage reduction) every level and Luck every two levels, so mining makes you harder to kill.
- **Abilities:** five passives per skill, unlocked at levels 1 to 5 and improved every 5 levels, with no separate cap. Most are percentage chances. Lucky Miner (double ore drops) starts at 5% and gains 5% per ability level, which reaches about 100% near level 100.
- **Mana abilities:** one active ability per skill, unlocked at level 6. It costs mana and has a cooldown. Most are Convenience in all but name: Treecapitator (fell whole trees), Speed Mine (Haste X), Terraform (dig a 4-block radius at once), Replenish (auto-replant crops). The combat ones are Charged Shot, Lightning Blade, Absorption and Sharp Hook.
- **Anti-abuse:** an anti-AFK module that watches for repeated blocks, damage and fishing from one spot. It ships disabled.

## mcMMO

The original Minecraft skills plugin, still maintained. It has 14 skills, including Repair and Salvage alongside the gathering and combat skills.

- **Scale:** "Retro mode" (levels 1 to 1000) is the default. Standard mode (1 to 100) takes the same time at a 1:10 ratio. Skill and power-level caps are off by default (`Level_Cap: 0`, `Power_Level_Cap: 0`).
- **Super abilities:** activated abilities such as Super Breaker, Tree Feller and Giga Drill Breaker. Their duration grows with level.
- **Anti-exploit:** a long `ExploitFix` list: over-fishing detection, reduced XP from cobble/stone generators and bone-mealed plants, piston tricks, Enderman farms, and a damage ceiling on combat XP. Diminished returns (an XP limit per time window) exist but ship disabled. An `EarlyGameBoost` speeds up the first levels.
- **History:** most of these fixes exist because players found the exploits first. Fishing detection went through several rounds of tightening and loosening, and a market for third-party anti-AFK-fishing and anti-mcMMO-farm plugins exists.

## Pufferfish's Skills

A data-driven Fabric/Forge framework rather than a fixed skill set. Skill trees ship separately (a default tree pack exists).

- **Structure:** categories (for example combat or mining) each have levels, and levels give points to spend in a skill tree with connections between nodes. Admins design the trees in a web editor.
- **XP sources:** configurable: mine block, break block, kill entity, craft, smelt, fish, enchant, eat, heal, take or deal damage, and advancement criteria. The criterion source means XP can come from milestones instead of repetition.
- **Rewards:** attributes, commands, scoreboard values, tags, and more points. An Origins add-on can grant Origins powers.
- **Anti-farming:** built into each source: a limit per chunk or per entity with a sliding reset window (for example, 15 kills per chunk per 300 seconds).
- **Caps:** `level_limit` is set per category.
- **Fit for us:** its design is the closest to what we would want, but Modrinth lists it as client and server, and its skills screen is client UI. We would build our own on Polymer (see [ADR-0001](../adr/0001-server-side-fabric-with-polymer.md)) and borrow the ideas.

## Other references

- **RuneScape.** The reference design. Old School RuneScape and RuneScape 3 have 23 to 29 skills capped at 99 (some at 110 or 120), with XP continuing to 200 million. The curve is exponential: level 92 is almost exactly half of level 99's XP. What makes it work is that **levels unlock access, not just numbers**: new ores, recipes, spells, Agility shortcuts and quests. A level-99 skillcape is a status and cosmetic reward. The **Collection Log** (1,925 slots of unique drops, with rank staves at thresholds) became one of the game's main goal systems, entirely through collecting rather than stats.
- **Stardew Valley.** Five skills capped at **level 10**. Most levels unlock crafting recipes (Keg, Seed Maker, Iridium Sprinkler); levels 5 and 10 offer a profession choice, which can be swapped for gold. After all five skills reach 10, Mastery unlocks one strong perk per skill. A short, legible ladder whose every step is a new thing to build.
- **Valheim.** Skills rise by doing the action, cap at 100, and scale modest effects (stamina cost, damage, speed). Death drains 5% of each skill, with a 10-minute grace period. The drain is widely disliked and commonly turned down by servers.
- **Origins.** You pick an origin at the start, with powers and drawbacks. A horizontal identity system with no grind at all, but locked choices with real drawbacks can feel bad on a small server.
- **Guild Wars 2 Masteries.** Account-wide horizontal progression after the level cap: gliding, mounts, and the ability to read a language or use updrafts. No stat increases. Criticized when story content requires Mastery levels.
- **World of Warcraft rested XP.** Offline time builds a pool that doubles kill XP until it is used up. Blizzard first tested this as a penalty for long sessions and players hated it; flipping it into a bonus made the same numbers feel generous.

## Known problems and criticism

- **Rich get richer.** Uncapped percentage bonuses compound. Double drops make the next gear tier cheaper, which makes the next level faster. On MassiveCraft players argued new players could not join a fight without "1k axes, 1k acro and 600 unarmed" and proposed boosting new players instead of nerfing veterans. Other servers report high-level combat skills killing players in diamond armor in a couple of hits.
- **Non-combat skills leak into combat.** AuraSkills's Mining grants Toughness; mcMMO's Acrobatics reduces damage. A pure builder or miner gains Power, and a fighter is pushed to grind gathering skills for stats.
- **PvP balance.** Level differences outweigh player skill. Servers respond with level-based protection or damage scaling, which then gets abused (level-0 players in max gear).
- **AFK and macro grinding.** Any XP from repeated actions turns into a farm: AFK fishing, cobble generators, mob grinders, bone-mealed plants, autoclickers. Each fix (placed-block tracking, over-fishing detection, per-chunk limits) is a patch on a design that rewards repetition. Both major plugins ship their strongest anti-AFK tools disabled because they cause false positives.
- **Time sink, not skill check.** Quadratic or exponential curves with 100 to 1000 levels mean most of the time is spent in the long tail. This is exactly the "slower is not harder" problem from [The Bottom Dweller research](bottom-dweller-and-matcha-flavoured.md).
- **Numeric bonuses are boring.** "+0.4 damage" or "+5% double drop chance" is invisible in play. The parts players remember are the actives that change how you play (Tree Feller, Treecapitator, Replenish), and those are Convenience.
- **Endings.** When a skill caps, its progress line stops mattering. Hypixel SkyBlock players describe items and lines that "completely lose a purpose" at the end; uncapped systems avoid this only by becoming endless grinds.

## Patterns that avoid these problems

- **Short ladders with hard caps.** Ten ranks like Stardew, not 100 or 1000 levels. A cap bounds the gap between the leader and a newcomer.
- **Horizontal unlocks, not vertical stats.** Each rank grants a capability: a recipe, a tool mode, a structure type, a service. You either have it or you don't, and having it doesn't make anyone else weaker.
- **Convenience, not Power.** Skills make work faster and comfier; they never add damage, protection or health.
- **XP from novelty and projects.** Pay most XP for firsts (first time mining an ore, finding a biome, completing a build or Settlement building), advancement-style milestones and project completion. Repetition pays little and decays quickly. This also makes AFK farming pointless without needing detection.
- **Diminishing returns on repetition.** A daily allowance at full rate, then a steep drop to a small floor (mcMMO's design keeps a 5% minimum so it never feels like a wall).
- **Rested XP, framed as a bonus.** Time offline builds a pool that doubles XP. Casual players get a real boost; people who play all day lose nothing.
- **Server-wide catch-up.** Once anyone reaches a rank, others below it level faster toward it, the same logic as Catch-up easing for Eras.
- **Milestone perks.** Few, memorable unlocks at known ranks instead of a smooth stream of tiny bonuses.
- **Mastery and cosmetics past the cap.** Titles, trophies, capes, block palettes and Collection Log entries. Status without power.
- **Collection-log goals.** Breadth over depth: rewards for doing many different things once rather than one thing a thousand times.
- **Cheap respec or no exclusivity.** If choices exist, make them swappable (Stardew's statue), or let everyone eventually have every skill.

## Recommendation for Super Awesome Craft

**Yes, have skills, but not a skills plugin.** Eras are the Power ladder and they are built around combat encounters. Without a second ladder, builders, miners, farmers and explorers have nothing to climb except the combat ladder, which is exactly how the Super Awesome Datapacks playtest failed. The skill system should be that second ladder: a Convenience ladder, one track per Motivation, short and capped, with rewards that change how you play. Everything that made the designer wary of skills plugins (compounding stats, uncapped levels, XP for repetition) is cut.

Working name: **Proficiency** (to be settled in [CONTEXT.md](../../CONTEXT.md)). "Skill" invites mcMMO expectations.

### Shape

- **Five or six Proficiencies**, one per non-combat Motivation: Mining, Building, Farming (crops and animals), Wayfinding (exploration and travel), Settling (Settlement and Villagers), and possibly Delving (dungeon logistics, not dungeon fighting). **No combat Proficiency.** Combat growth is the Eras' job.
- **Ten ranks each, hard cap.** Ranks 1 to 3 take an evening; rank 10 takes weeks of normal play, not grinding.
- **Everyone can rank everything.** No class choice, no exclusivity. Specialization happens because people play what they enjoy.

### What ranks give

- **New capabilities and modes.** Vein-mining and tree-felling (as tool modes, opt-in so contraption-adjacent play is unaffected), a builder's wand, area replant, bulk crafting, a glider upgrade, grave-locating tools.
- **Recipes and blueprints.** Specialized blocks, decorative palettes, Settlement building types, travel infrastructure such as locomotion track. Outputs are ordinary items that can be given or traded, so a high-rank builder or miner becomes useful to everyone without anyone being locked out.
- **Services.** Higher-rank Settling unlocks Villager services in your Settlement; higher-rank Wayfinding reveals structure hints by the learnable biome rules.
- **Comfort, never yield.** Speed and reach are fine. Multiplied drops are not: double-ore chances become a currency printer and are exactly the "rich get richer" loop.

### What ranks never give

- Damage, protection, health, regeneration, crit, attack speed, or anything else that counts as Power.
- Any requirement to enter or clear an Era Gate, and any Era Gate requirement to rank up. The two ladders stay independent.
- The only route to basic Convenience. Early Shulkers, durability and the glider stay available without ranks; ranks give the better, more specialized versions.
- Yield multipliers or anything that makes PvP depend on hours played.

### XP

- **Most XP from firsts and projects:** first time mining each ore or reaching a layer, discovering a biome or structure, completing a Settlement building, finishing a Collection Log page, building something large (measured by blocks placed in a claimed project area, not by placing and breaking).
- **Some XP from ordinary actions, with a daily allowance.** Full rate for roughly an hour of an activity per day, then a drop to a 5% floor. No placed-block XP. No XP from automated farms or mob grinders. Farms stay exactly vanilla (Technical parity) and are still welcome for materials; they just don't level you.
- **No death penalty** on Proficiencies. Death is already handled by Graves.

### Catch-up

- **Rested bonus:** offline time builds a pool that doubles Proficiency XP, capped at about one rank's worth. A player who logs in twice a week keeps pace with daily players on rank, if not on materials.
- **Pathfinder bonus:** once anyone on the server reaches rank N in a Proficiency, everyone below N earns double XP in that Proficiency up to N. This is Catch-up easing applied to Proficiencies, and like Era easing it never forces anything.
- **The cap is the real catch-up.** With ten ranks and no stats, a newcomer reaches parity in weeks. The leader's advantage is a head start, not a permanent multiplier.

### Interaction with Eras

- Eras add **new things to discover** (ores, biomes, materials) and so new first-time XP, but never raise the rank cap.
- Some high-rank recipes use Era materials. Those materials must also be obtainable by trade or from Settlements once anyone has cleared the Era, so a builder can reach rank 10 without fighting a climax.
- Era easing and the pathfinder bonus share one idea: whoever goes first opens the way.

### Past the cap

"Growth with no cap, as long as it's useful" is served by turning post-cap XP into **Settlement contribution** (it raises the Prosperity of the Settlement you belong to) and **mastery cosmetics** (titles, a Proficiency cape or trim, a trophy block for your base). Rank 10 in every Proficiency is a Collection Log milestone. None of this adds personal Power.

### Risks to watch

- **Duplication.** Proficiencies, the Collection Log and Convenience unlocks can overlap. Proficiency rewards should be the Convenience unlocks for that playstyle, and Collection Log pages should feed Proficiency XP, so there is one system with several entry points rather than three.
- **Convenience becoming expensive.** If too much basic comfort sits behind ranks, Proficiencies become the new grind. Keep ranks 1 to 3 trivially fast and keep baseline Convenience outside the system.
- **Measuring projects.** "Built something large" is hard to score without dictating how people build; this shares the open question of how Settlement Prosperity is measured.
- **Unmodified client.** All UI must be chest menus, boss bars, action bars, advancements and resource-pack textures (see ADR-0001). An advancement-style tree per Proficiency is a natural fit.

## Sources

- AuraSkills wiki: https://wiki.aurelium.dev/auraskills
- AuraSkills stats: https://wiki.aurelium.dev/auraskills/stats
- AuraSkills mana abilities: https://wiki.aurelium.dev/auraskills/mana-abilities
- AuraSkills abilities: https://wiki.aurelium.dev/auraskills/abilities
- AuraSkills main config: https://wiki.aurelium.dev/auraskills/main-config
- AuraSkills source and default configs (`skills.yml`, `stats.yml`, `abilities.yml`, `xp_requirements.yml`, `rewards/mining.yml`, `config.yml`): https://github.com/Archy-X/AuraSkills/tree/master/common/src/main/resources
- mcMMO repository and default configs (`config.yml`, `experience.yml`): https://github.com/mcMMO-Dev/mcMMO
- mcMMO update notes on Retro mode and fishing exploit detection (SpigotMC mirror): https://speedgot.ajg0702.us/resources/64348/updates
- Third-party anti-mcMMO-farm plugin: https://builtbybit.com/resources/asyantimcmmofarms.92914/
- Pufferfish's Skills: https://modrinth.com/mod/skills
- Pufferfish's Skills creators manual: https://puffish.net/skillsmod/docs/creators/
- Pufferfish's Skills experience config: https://puffish.net/skillsmod/docs/creators/configuration/files/experience
- Pufferfish's Skills per-chunk anti-farming: https://puffish.net/skillsmod/docs/creators/configuration/experience-sources/anti-farming/per-chunk
- RuneScape skills: https://runescape.wiki/w/Skills
- Old School RuneScape experience: https://oldschool.runescape.wiki/w/Experience
- Old School RuneScape Collection Log: https://oldschool.runescape.wiki/w/Collection_log
- Stardew Valley skills: https://stardewvalleywiki.com/Skills
- Stardew Valley professions: https://stardewvalleywiki.com/Professions
- Valheim death penalty: https://www.gosunoob.com/valheim/death-penalty/
- Origins: https://origins.readthedocs.io/en/latest/
- Guild Wars 2 Mastery: https://wiki.guildwars2.com/wiki/Mastery
- Guild Wars 2, "Reimagining Progression: The Mastery System": https://www.guildwars2.com/en-gb/news/reimagining-progression-the-mastery-system/
- World of Warcraft rested bonus: https://warcraft.wiki.gg/wiki/Rested
- MassiveCraft forum, "Removing Mcmmo": https://forums.massivecraft.com/threads/removing-mcmmo.40319
- Pika Network forum, level-based PvP protection: https://pika-network.net/threads/level-based-pvp-protection-system.579795
- Hypixel forum, "My issue with skyblocks progression lines": https://hypixel.net/threads/my-issue-with-skyblocks-progression-lines.5887815/latest
