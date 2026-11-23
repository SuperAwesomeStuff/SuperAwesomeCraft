# Research: Gear and enchanting on big servers and RPG plugins

How large Minecraft servers and server plugins handle enchanting and gear upgrades, and what their players complain about. Gathered November 2026 from the community Hypixel SkyBlock Wiki (hypixelskyblock.minecraft.wiki, which moved off Fandom in April 2026; Hypixel closed its own official wiki in July 2026), Hypixel forum threads, the Wynncraft wiki (wynncraft.wiki.gg) and forums, the EcoEnchants source and config files on GitHub, the AdvancedEnchantments docs and store page, the MCC Island wiki, the Minecraft Wiki and Mojang's feedback site, and mod pages for Apothic Enchanting and Create: Enchantment Industry. Statements marked *(source)* were read directly from a wiki page, config file or official doc. Statements marked *(unverified)* come from one forum post or secondary source only, or the sources disagree. Wiki pages were read through a summarising fetch, so exact numbers should be checked before we rely on them.

## Hypixel SkyBlock

SkyBlock is the most-played RPG mode in Minecraft, and the clearest example of what happens when upgrade layers keep piling onto the same item.

### Enchanting table and anvil

- **The table goes up to level V.** Most enchantments cost about 30 to 50 XP levels at their top table level; a few, such as First Strike IV, cost up to 75. *(source)*
- Enchantments can be swapped or removed at the table for the same XP cost. Since July 2026 every tier can be removed at the table or The Hex. *(source)*
- **An Enchanting skill gates which enchantments you can use.** Since update 0.10 (December 2020) most enchantments need an Enchanting skill level to apply *and to use*: Scavenger 1, Looting 3, Drain 15, Overload 33. Existing gear got a two-week grace period. *(source)*
- Bookshelf power used to gate enchantments too; it was dropped in July 2026. *(source)*
- **Conflicts** make some choices: Drain, Life Steal and Mana Steal exclude each other, as do Giant Killer and Titan Killer, the four Protections, and the four armor "Vitality" enchantments. Silk Touch conflicts with Smelting Touch and Fortune. Applying a conflicting enchantment replaces the old one. *(source)*

### Higher tiers

- **Level VI and above can't be made at the table or by combining two level V books.** They come from the Experimentation Table, Dungeon chests, the Dark Auction, the Bazaar, bosses and events. **Endcap upgrades** raise an enchantment one more tier at an anvil, and need the previous tier already applied. *(source)*
- **Stacking enchantments level up through use**, each tied to one activity: Champion (combat XP, 3m XP for level X), Compact (1m blocks mined for X), Cultivating (100m crops for X), Expertise (15k sea creatures), Hecatomb (100 S-rank dungeon runs). *(source)* These are pure grind gates with very long tails.

### Experimentation Table

- A piece of furniture unlocked at Enchanting 10, one per island. *(source)*
- Daily memory minigames (Chronomatron, Ultrasequencer, Superpairs) reward Enchanting XP, high-level books, Endcap enchantments and XP bottles. Charges reset 24 hours after the last one is used, and extra charges can be bought with Bits. *(source)* The number of free charges per day is not on the wiki. *(unverified)*
- This is a **daily login gate** on top-tier enchantments.

### Ultimate enchantments

- **One per item.** "An item can only have 1 Ultimate Enchantment at a time." Applying another overwrites the first. *(source)*
- **Armor exception:** each armor piece holds its own, so a set can carry four, and some (Bank) stack across pieces. *(source)*
- Applied at an anvil for 50/100/150/200/250 XP levels for I to V, and most need an Enchanting skill level. Sources are dungeon chests (Soul Eater from Floor VII, Legion from Floor V), rare drops (Chimera from the Minos Inquisitor) and the Bazaar. *(source)*
- The examples are build-defining rather than plain stats: Legion scales with nearby players (up to 20 within 30 blocks), Ultimate Wise cuts ability mana cost, Chimera copies pet stats. *(source)* This is the system that creates the most real choice in SkyBlock gear.

### Reforges

- **One reforge per item**; a new one replaces the old. *(source)*
- **Basic reforging** at the Blacksmith is a paid random roll; every legal reforge has the same chance and you see the result only after paying. *(source)*
- **Reforge stones** pick a specific reforge, applied at an anvil or The Hex for coins set by the stone's rarity (5,000 for Common up to 2,000,000 for Divine). Some need a Mining level. *(source)*
- **Reforge stats scale with item rarity**, which is why the Recombobulator matters (below). *(source)*

### Flat stat stacks

- **Hot Potato Books:** up to 10 per sword or armor piece, +2 Strength and +2 Damage each (armor gets Defense and Health instead). Crafted from an Enchanted Baked Potato and paper, or found in dungeon chests. *(source)*
- **Fuming Potato Books:** 5 more, only after the 10 Hot Potato Books, from dungeon chests only. *(source)*
- **Recombobulator 3000:** raises an item's rarity by one tier, once per item, which also boosts its reforge. From dungeon chests (chests cost about 5 to 6 million coins to open) and boss collections. *(source)*
- **Essence stars:** each star gives +2% stats, or +10% inside the Catacombs for dungeon items. Dungeon items take 5 stars, others up to 15, plus Master Stars beyond those. Essence comes in 12 types, each tied to an area and sold at that area's NPC. *(source)*

### Gemstone slots

- Some items have sockets that hold one gemstone each. Gemstones go from Rough to Perfect, and their value scales with gem quality *and* item rarity: a Rough Ruby gives +1 Health on a Common item, a Perfect Ruby +30 on a Divine one. *(source)*
- Slots are typed: twelve single-gem types, plus Combat, Defensive, Mining and Universal slots that accept several. Some slots cost something to unlock first. Removing a gem costs 1 to 500,000 coins by quality. *(source)*
- Slot counts are fixed per item (2 on the Talisman of Power, 5 on Divan's Armor). *(source)*

### How it limits and gates

- **Hard caps per layer:** one ultimate, one reforge, one recombobulation, 10+5 potato books, a fixed number of stars and gem slots.
- **But no budget across layers.** Every layer can be maxed at once, so a "finished" item is one with every layer maxed. The caps limit each checklist line, not the checklist.
- **Gates** are skill levels (Enchanting, Mining, Catacombs), area-specific currencies (essences), daily charges, and coins.

### What the community criticises

- **Upgrade layers become mandatory checklists.** A 2023 forum thread on "feature creep" lists potato books, recombs, reforges, gemstones, enchants and the then-new attributes, and says "the time / coin investment it takes to max out a single item is becoming insane." Its author: "we're just getting more and more mandatory upgrades instead of customization." *(source: forum, April 2023)* Others pushed back that "mandatory" is overstated, since endgame runs work without everything maxed.
- **Unlearnable without outside help.** A reply in the same thread: the game is "basically impossible to get into without a wiki or some sort of guide," and the problem is a lack of intuitiveness, not difficulty. Suggestions were to have The Hex show what upgrades an item can take, and to consolidate enchantments. *(source: forum)*
- **Enchantment clutter.** Players suggested merging half the enchantments into general damage, and one poster claimed admins planned to cap enchantments per item. *(unverified: single forum claim; no cap appears on the wiki)*
- **A ranked upgrade order.** A popular 2021 "effort post" ("Stop. Recombobulating. Your. Items.") ranks upgrades by value per coin and tells new players to skip recombs. *(source: forum, May 2021)* Gear has become an efficiency spreadsheet.
- **Obsolete items.** A 2025 thread complains that once you can use the next weapon, the previous one is pointless. Replies split between wanting linear upgrade lines and defending variety: "direct upgrades just make everything boring and decreases variety." *(source: forum, April 2025)*
- Hypixel itself has moved toward *simpler* rules: Telekinesis was replaced with Auto-pickup in 2022, and bookshelf power was dropped in 2026. *(source: wiki changelog)* What the Auto-pickup change looks like in-game was not checked. *(unverified)*

## Wynncraft

Wynncraft is a full MMORPG on a Minecraft server, with classes, levels and loot.

### Item identifications

- Dropped gear rolls random bonuses called **identifications (IDs)**: skill points, damage, health, mana, elemental defence, movement, spell cost, XP and gathering. Any ID can roll positive or negative. *(source)*
- Items drop **unidentified** and are identified at an NPC. Positive IDs roll 30% to 130% of a base value. Re-identifying costs more each time (×5 per reroll). *(unverified: older forum guides)*
- **Major IDs** are fixed, not rolled, and appear only on specific items. They are build-defining effects, like SkyBlock's ultimates. *(source)*
- Mythic items drop at about 1 in 142,857 from mobs and chests, with better odds (and pity) from lootruns and raids. *(source)*

### Powders

- Five elements (Earth, Thunder, Water, Fire, Air), seven tiers. Mobs and chests drop up to tier 4. Tiers 5 to 7 come only from combining four lower powders at an NPC, or from raids and trading. *(source)*
- **Powder slots** are fixed per item, shown on the tooltip. Most items have 0 to 5. *(unverified: 2021 guide)* Each tier needs a minimum item level (tier I at 1, tier VII at 70). *(source)*
- On weapons, powders convert neutral damage to elemental, and **application order matters**. On armor, each powder adds one element's defence and lowers another's (Earth up, Air down). *(source)*
- **Powder specials:** two tier-4+ powders of the same element unlock one special per item, set by the first powder applied: active abilities on weapons (Quake, Chain Lightning, Curse), passive buffs on armor. *(source)*
- Powders can be removed at an NPC for a partial refund. *(source)*
- The design point: **a small number of slots, plus elements that trade off against each other.** You can't have everything, and the trade-off is visible on the item.

### Crafting professions

- Four gathering professions (Mining, Woodcutting, Fishing, Farming) feed eight crafting ones. *(source)*
- A recipe takes up to six **ingredients**. Ingredients add IDs, usually at a cost in durability, and some have **positional effects**: Bob's Tear boosts the ingredient below it by 25%, Dominant Force boosts touching ingredients by 120% but cuts those left and right by 75%. *(source)*
- Crafted gear has durability and weakens as it wears (down to 10% of its IDs at zero); dropped gear has no durability. Results are random within a range. *(source)*
- Some IDs (loot quality, gathering speed) come **only** from crafting ingredients. *(source)*

### What the community criticises

- **Crafting feels irrelevant until late game.** A 2020 forum poll found 74% saying professions don't matter until then. *(unverified: secondary summary of the poll)*
- Players dislike the **intermediate-item grind** (crafting things you'll never use to level up) and the many **useless ingredients**. *(source: forum)*
- **Durability on crafted gear only** is the most common complaint: dropped gear has none, so crafted gear starts behind. Defenders say durability is what balances crafted against dropped. *(source: forum)*
- Players asked for crafting to have "less RNG than regular items" and give the player more control. *(source: forum)*
- Heavy reliance on outside build calculators and item search is the norm. *(unverified: general community knowledge; not checked against a primary source here)*

## Enchantment plugins

### EcoEnchants (Auxilor)

A premium Spigot/Paper plugin, source on GitHub under GPL-3.0. *(source)*

- Custom enchantments are YAML files with `rarity`, `type`, `targets`, `conflicts`, `required` (prerequisite enchantments, optionally consumed) and `max-level`, plus flags for whether they come from the table (`enchantable`), villagers (`tradeable`) or loot (`discoverable`). *(source)*
- **Rarity sets the odds and the minimum table level.** Defaults *(source: `rarity.yml`)*:

| Rarity | Table chance | Min table level | Villager | Loot |
|---|---|---|---|---|
| Common | 30% | 1 | 10.5% | 12% |
| Uncommon | 20% | 5 | 9% | 16% |
| Rare | 20% | 15 | 7.5% | 18% |
| Epic | 10% | 16 | 6% | 20% |
| Legendary | 8% | 20 | 4.5% | 15% |
| Special | 2% | 30 | 3% | 5% |
| Very Special | 1% | 30 | 1.5% | 2% |

- **Types carry the one-per-item limit.** In `types.yml`, `normal` and `curse` have no limit; `spell` and `special` have `limit: 1`, so an item holds at most one of each. This is SkyBlock's ultimate rule in config form. *(source)*
- **The total enchantment cap is off by default** (`anvil.enchant-limit: -1`). *(source)* Out of the box, the only stacking limit is the one-special, one-spell rule.
- It hooks into the **vanilla** table, villagers and loot, so everything is random rolls. *(source)*

### AdvancedEnchantments

A paid plugin (€29.99 at the time of reading) advertising "450+ Custom Enchants." *(source: store page)*

- Enchantments belong to **groups** (Simple, Unique, Elite and so on). Players buy a random book of a group from the **Enchanter**, priced in XP, money, souls or items. *(source)*
- **Books have success and destroy rates.** A failed application can destroy the item; admins can turn breaking off. *(source)* Protection scrolls ("white scrolls") and extracting scrolls ("black scrolls") come from the wider custom-enchant server tradition. *(unverified for this plugin; documented on servers using similar systems)*
- **Magic dust** raises a book's success rate for one rarity. *(source)*
- **Slots:** admins can limit enchantments per item, and **Slot Increasers** (grouped, such as SIMPLE or ELITE) add slots up to a config cap. *(source)*
- **Souls** gathered from PvP kills pay for some enchantments. *(source)*
- This is the factions/prison design lineage: gambling, item loss and paid power. It suits servers that sell upgrades, not a friend group.

### Origin Realms and MCC Island

- **Origin Realms:** no primary source on its gear or enchanting systems was found. *(unverified; skipped)*
- **MCC Island** (Noxcrew) keeps combat in its minigames **equal**: progression is cosmetic or tied to its fishing side game. *(unverified that no power is sold; the wiki does not say)* Fishing **augments** are consumables: up to 5 of each kind equipped at once, each adding a small perk boost (+3 or +4) and **wearing out** after a set number of qualifying catches (100 fish for Strong Hook, 5 treasures for Greedy Hook). *(source)* This is a way to have upgrades and a material sink without a permanent power ladder.

## Vanilla and vanilla+ design

- **Mojang's Villager Trade Rebalance** (experimental) is the clearest multiplayer-fairness change to vanilla enchanting. Librarians sell different books by biome, and each biome has one "special" book sold only by Master librarians: Mending in swamps, Silk Touch in snowy villages, Efficiency III in deserts. The stated reason is that librarians were "too overpowered compared to using an enchanting table" and that players should "explore villages in different biomes... instead of relying on random chances." *(source: Minecraft Wiki)* The cartographer and wandering-trader parts shipped in 1.21.5; the librarian part is still experimental as far as the wiki says. *(source)* Feedback-site comments were mixed: players accepted that librarians were overpowered, but called the biome lock grindy and asked for anvil cost fixes instead. *(source: feedback.minecraft.net comments)*
- **Hermitcraft-style servers** play pure vanilla, so enchanting fairness there comes from social norms and trading halls, not rules. *(unverified; no primary source checked)*
- **Vanilla Tweaks** datapacks mostly add convenience (XP Management bottles XP at a table, Multiplayer Sleep, Unlock All Recipes). None that change enchanting balance were found. *(unverified: the pack picker didn't load; secondary lists only)*
- **Apothic Enchanting** (the Apotheosis module) goes the opposite way from us: shelves give Eterna (level), Quanta (randomness) and Arcana (more and rarer enchantments), the table goes to level 100, and most enchantment caps rise. It adds **Clues** (see more of what a slot will give) and **Filters** (blacklist enchantments) to tame randomness, and a **Library** that stores books as points. It has no cap on enchantments per item. *(source: Modrinth page)*
- **Create: Enchantment Industry** automates vanilla enchanting with liquid XP: the Blaze Enchanter, plus a Printer that copies enchanted books. *(source)* Once copying is possible, a book only has to be found once. That's good for sharing on a server, but it ends any scarcity.

## Patterns across systems

- **Limits that work:** one-per-item slots for build-defining effects (SkyBlock ultimates, EcoEnchants `special`, Wynncraft powder specials and Major IDs). Fixed slot counts with trade-offs visible on the item (Wynncraft powders, where an element's gain costs another's defence).
- **Limits that don't:** separate caps on many independent layers (SkyBlock). Each layer is capped, but every layer can be maxed together, so a finished item is just a longer checklist.
- **Random rolls** (identifications, Blacksmith reforges, table rolls, success/destroy rates) turn upgrading into rerolling and gambling, and reward whoever grinds or pays most.
- **Grind gates** (stacking enchantments, skill levels, daily charges) last a long time but feel like chores, and on a small server they let the most active player outpace everyone.
- **Plain stat stacks** (potato books, stars) add no choices at all; they only add steps.

## Fit with SuperAwesomeCraft

**Fits the pillars:**

- **One build-defining slot per item.** SkyBlock's ultimates and Wynncraft's Major IDs and powder specials are where those games' real choices live. A single "signature" slot per item, filled with a behaviour rather than a stat, gives a Fortune pick and a vein-mining pick their own identities. It sits naturally inside the Capacity budget we're already leaning toward.
- **One budget, not many caps.** SkyBlock shows what goes wrong when each layer has its own cap: every layer gets maxed. Our Capacity budget is the opposite, a single pool that every Power choice draws on. Keep it that way: any new upgrade (stats, Augments) should spend the same Capacity, not add a new layer with its own limit. This supports option 1 of the raw-stats question in [vision.md](../vision.md#gear).
- **Trade-offs shown on the item.** Wynncraft's powders say on the tooltip what you gain and what you give up. Capacity should do the same: "7/10 Capacity used" on the tooltip, and each enchantment's cost shown before it's applied.
- **Consumables as the sink.** MCC Island's augments wear out after a number of uses, which gives a material sink and a short-term boost without a permanent ladder. That fits our no-durability plan: Stronghold consumables can wear out while gear does not.
- **Fixed recipes over rolls.** Apothic Enchanting needed Clues and Filters to tame its randomness, and AdvancedEnchantments needs dust and scrolls. Our fixed recipes avoid all of that. A copy machine like Create: Enchantment Industry's Printer fits "progress is shared" (one player's book becomes everyone's) if copying costs materials.
- **Biome-specific sources** (the Villager Trade Rebalance) match "structures found by learnable rules": the swamp is where Mending comes from, and anyone can learn that by playing.

**Pitfalls to avoid:**

- **Stacking layers.** The biggest SkyBlock lesson. Enchantments, reforges, potato books, stars, gems, recombobulators and attributes each look reasonable alone; together they're a wiki-only checklist. Every new gear system should replace or spend from an existing one, not sit on top.
- **Flat stat stacks.** Potato books and stars add steps with no choice. If raw stats exist, they should cost Capacity that could have gone to a behaviour.
- **Grind-to-level enchantments** (Compact, Cultivating at 100m crops). They contradict "grinding can't buy power" and let the most active player run away from the group. Familiarity's hard cap of 5 already avoids this; keep Power out of use-based levelling.
- **Daily charges.** The Experimentation Table is a login gate. On a 5 to 10 friend server it punishes people who play twice a week.
- **Random rolls and item loss.** Identifications, Blacksmith reforges and success/destroy rates mean whoever rerolls most wins. Destroying items on failure is especially bad for a group of friends.
- **Skill gates on using gear.** SkyBlock requires an Enchanting level to *use* an enchantment. That would stop players handing gear to each other, which breaks our catch-up plan (levelled tools can be given to new players).
- **Needing a wiki.** Every system above relies on outside wikis or calculators, and Hypixel had to close its own wiki for lack of staff. Everything we add must be learnable in-game: recipes in the recipe book or the table screen, costs on tooltips, conflicts shown before applying.
- **Obsolete-by-tier gear.** SkyBlock players complain that the next weapon makes the last one pointless. Horizontal choices per Era (a different behaviour, not just bigger numbers) keep older gear useful for its niche.
- **Changing vanilla in place.** EcoEnchants and AdvancedEnchantments change the vanilla table, villagers and loot directly. For technical parity our enchanting stays a separate table and items; vanilla mechanics are unchanged.
- **Paid or PvP-sourced power.** AdvancedEnchantments' souls and Slot Increasers come from a server-shop culture. Nothing like that belongs here.

## Sources

- Hypixel SkyBlock Wiki (community, Weird Gloop): https://hypixelskyblock.minecraft.wiki/w/Enchantments, https://hypixelskyblock.minecraft.wiki/w/Ultimate_Enchantment, https://hypixelskyblock.minecraft.wiki/w/Reforging, https://hypixelskyblock.minecraft.wiki/w/Experimentation_Table, https://hypixelskyblock.minecraft.wiki/w/Hot_Potato_Book, https://hypixelskyblock.minecraft.wiki/w/Fuming_Potato_Book, https://hypixelskyblock.minecraft.wiki/w/Gemstone_Slot, https://hypixelskyblock.minecraft.wiki/w/Recombobulator_3000, https://hypixelskyblock.minecraft.wiki/w/Essence
- End of the official Hypixel Wiki (July 2026): https://hypixel.net/threads/end-of-the-official-hypixel-wiki-july-2026.6112020/
- Hypixel forum threads: https://hypixel.net/threads/skyblock-and-feature-creep.5332602/, https://hypixel.net/threads/stop-recombobulating-your-items-effort-post.4188662/, https://hypixel.net/threads/my-issue-with-skyblocks-progression-lines.5887815/
- Wynncraft wiki: https://wynncraft.wiki.gg/wiki/Powders, https://wynncraft.wiki.gg/wiki/Identifications, https://wynncraft.wiki.gg/wiki/Identifying, https://wynncraft.wiki.gg/wiki/Crafting, https://wynncraft.wiki.gg/wiki/Ingredients, https://wynncraft.wiki.gg/wiki/Mythic_Items
- Wynncraft forums (identification and profession discussions, found via search; individual posts not all reviewed): https://forums.wynncraft.com/
- EcoEnchants: https://github.com/Auxilor/EcoEnchants (`eco-core/core-plugin/src/main/resources/config.yml`, `rarity.yml`, `types.yml`), https://hub.auxilor.io/wiki/ecoenchants/how-to-make-a-custom-enchant
- AdvancedEnchantments: https://advancedplugins.net/item/1, https://ae.advancedplugins.net/general-information/giving-plugin-items/slot-increasers, https://ae.advancedplugins.net/configuration/unsuccessful-enchants.md, https://ae.advancedplugins.net/configuration/enchanter.md, https://ae.advancedplugins.net/configuration/groups.md
- Custom-enchant server wikis (scroll mechanics, secondary): https://wiki.torrentsmp.com/lifesteal/enchanting, https://wiki.cosmicpe.me/wiki/Enchanting
- MCC Island wiki: https://mccisland.wiki/Augments
- Villager Trade Rebalance: https://minecraft.wiki/w/Villager_Trade_Rebalance, https://feedback.minecraft.net/hc/en-us/community/posts/17813182847245
- Apothic Enchanting: https://modrinth.com/mod/apothic-enchanting, https://github.com/Shadows-of-Fire/Apothic-Enchanting
- Create: Enchantment Industry: https://modrinth.com/mod/create-enchantment-industry
