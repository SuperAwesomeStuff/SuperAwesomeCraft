# Research: Terraria Town NPCs and Building-Block Vendors

Background for the Season 1 Villager rework and the later Settlements design, where Villagers move into what players build and trades are a supplemental way to get resources a player would rather not gather. The designer likes how modded Terraria has NPCs selling building blocks, which cuts the grind for big builds without making anyone depend on NPCs. This note also looks for precedents for the design-map Idea "a Villager starts trading an Era's materials once a player gives it some". Gathered November 2026 from the official Terraria wiki (terraria.wiki.gg, which describes 1.4.5 and later; the Pylons page says the current version is 1.4.6.1), the Fargo's, Calamity, Thorium and Terraria Mods wikis on wiki.gg, Steam Workshop pages, and mod source on GitHub (AlchemistNPC Lite, and a public copy of Fargo's Mutant Mod). Anything marked *Unverified* couldn't be confirmed from these sources.

Currency, for reading prices: 100 Copper = 1 Silver, 100 Silver = 1 Gold, 100 Gold = 1 Platinum. A vanilla shop's buy price is normally the item's value, and selling to a vendor returns a fifth of it.

## How NPCs move in

- **Each NPC is a unique character with a move-in condition.** There are 26 town NPCs. Most conditions point at something the player has done or owns, so a new arrival works as a sign of progress. Selected conditions from the NPCs page:

| NPC | Moves in when |
| --- | --- |
| Guide | Always there in a new world |
| Merchant | All players together carry more than 50 Silver |
| Nurse | A player has over 100 max health and the Merchant is present |
| Demolitionist | A player has an explosive and the Merchant is present |
| Arms Dealer | A player has bullets or a gun that fires them |
| Dye Trader | A player has a dye, dye plant, Strange Plant or Dye Vat, and 4 other town NPCs live there |
| Dryad | Eye of Cthulhu, Eater of Worlds/Brain of Cthulhu or Skeletron defeated |
| Painter | 8 other town NPCs live there |
| Zoologist | The Bestiary is at least 10% full (55 entries) |
| Clothier | Skeletron defeated |
| Goblin Tinkerer, Mechanic, Stylist, Angler, Golfer, Wizard | Found somewhere in the world (a cavern, the Dungeon, a spider nest, the ocean) and talked to. The Goblin also needs a defeated Goblin Army |
| Steampunker, Cyborg, Pirate | A mechanical boss, Plantera, a Pirate Invasion |
| Tax Collector | Hardmode: a Tortured Soul in the Underworld is purified with Purification Powder |
| Party Girl | 1 in 40 chance once 20 other town NPCs live there (raised from 14 in 1.4.5) |
| Princess | Every other town NPC is present |

- **Three kinds of trigger.** Owning something (Merchant, Arms Dealer, Demolitionist), beating something (Dryad, Clothier, Steampunker), or finding the NPC out in the world (Goblin, Mechanic, Angler). The last kind turns exploration into a town upgrade.
- **Daytime only, one at a time.** NPCs don't arrive at night or during events. About 2 minutes pass between arrivals. Only one homeless NPC can exist at a time; it arrives by noon if there's no free house and leaves at 6 PM.
- **Death isn't permanent.** A killed NPC comes back after a delay if a free valid house exists, without meeting its condition again. Players can't normally hurt town NPCs.
- **Exceptions that need no house.** The Traveling Merchant, the Old Man and the Skeleton Merchant.

## Valid housing

From the House page.

- **Size.** At least 60 tiles including the frame, under 750 tiles, and no more than 100 wide or high. Examples of the minimum: 3 wide by 10 high inside, or 8 by 8 with walls.
- **Enclosure.** A closed frame of solid blocks, platforms, doors or trapdoors, with background walls: every tile needs a solid tile or a player-placed ("safe") wall within 2 tiles in each direction. Gaps of natural ("unsafe") wall up to 4×4 are allowed.
- **Furniture, one of each category.** A light source (torch, candle, lamp), a "table" or flat surface (table, work bench, dresser, even a bathtub), a "chair" or comfort item (chair, bed, toilet, bench, throne), and an entrance (door, platform, trapdoor). The entrance doesn't have to be usable by the NPC.
- **Biome.** A house surrounded by enough Corruption or Crimson (an "evil score" of 300 in a box about 45 tiles around the house) is invalid: "This housing is corrupted."
- **Sharing.** Any valid house holds one town NPC and one town pet. Two NPCs can't take home tiles within 3 tiles of each other.
- **The checks are functional, not aesthetic.** Each requirement is a category with many qualifying items, so any style of build passes as long as it's enclosed and has a light, a table, a chair and a door.

### The housing UI

- **A housing menu** opens from a house button in the inventory. It shows a flag for each NPC (red for NPCs, purple for town pets) that can be dragged onto a room to assign it.
- **The housing query** tool checks a room and gives a status message such as "This housing is suitable." or names the failed requirement ("This room has no space to stand in!").
- **Assigned rooms keep their tenant.** A golden flag frame means the room is reserved, so the NPC moves back into it after dying.
- **NPCs prefer roomier houses.** Houses with more wall area get NPCs faster. Sandstone Brick Walls have a 1 in 4 move-in chance; other walls 1 in 1.
- **1.4.5 added instructions.** Homeless NPCs now have a Housing dialogue option, with instructions for building a suitable house, in place of the Happiness option.
- **Census, a popular mod, fills a gap.** It adds missing NPCs to the housing panel with a tick, cross or question mark and their move-in conditions on hover, for vanilla and modded NPCs alike. Its existence suggests players struggled to learn the move-in conditions in-game.

## Happiness

Added in 1.4.0.1 (Journey's End, May 2020). The patch notes call it "an expansion on the town system that culminates with the ability to build a teleportation network to towns around the world" and add "the NPC happiness system based on where and who they live with."

- **What it does.** It only changes prices (and a few services that charge money). Happy vendors sell cheaper and buy for more. Buy prices range from 75% to 150%, sell prices from 133% down to 67%.
- **Biome.** Each NPC loves, likes, dislikes or hates some biomes: 88%, 94%, 106% or 112% on buy prices. Oddly, it's the biome the **player** is standing in when talking, not the NPC's home.
- **Neighbours.** Every loved, liked, disliked or hated NPC within 25 tiles applies the same steps. Everyone likes the Princess. Example: the Merchant likes the Forest, the Princess, the Golfer and the Nurse, dislikes the Desert and the Tax Collector, and hates the Angler.
- **Crowding.** A solitude bonus (95%) if no more than two other NPCs live within 25 tiles and no more than three within 120. Each NPC beyond three within 25 tiles adds a 105% penalty.
- **Worst case forced.** Prices go to the maximum penalty if the NPC is homeless, more than 120 tiles from home, or in the Dungeon or an evil biome.
- **Feedback.** A Happiness chat option says what is pleasing or bothering the NPC, but not what would fix it. Since 1.4.5, shops show an icon with the exact price modifier.
- **Pylons were the real reward, then were decoupled.** Pylons (fast travel between towns, 10 Gold each, one per biome per world) are sold by vendors and need two housed NPCs near each pylon to work. Selling them first needed happy vendors. 1.4.3.3 required "a minimum of two NPCs under all conditions" and loosened the happiness threshold from 85% to 90%. 1.4.5.0 removed it: "The minimum required happiness for NPCs to sell pylons has been removed entirely." *Unverified:* the 1.4.0 threshold, and any developer statement on why it was dropped.
- **Remix seed** turns happiness off entirely, prices stay at 100%.

## Shops and progression

- **Shops grow in steps.** Items are added for Hardmode, specific boss kills, the moon phase, Blood Moons, holidays, the biome the player is in, a Graveyard, or holding a related item. Examples from the Merchant: Healing Potions and a Sharpening Station in Hardmode, a Safe after Skeletron, Flares only if a player holds a Flare Gun, Marshmallows in snow, Throwing Knives during a Blood Moon.
- **Conditional stock teaches the world.** Biome-only items send players to biomes, and "player holds X" items (Flares, Nails) appear exactly when they're useful.
- **What vanilla sells for building is mostly finishing, not bulk.**
  - **Painter:** 16 paints at 25 Copper, Shadow and Negative paint in Hardmode, coatings in a Graveyard, and wallpaper walls at 75 Copper each (Christmas sets in season).
  - **Dryad:** Grass Seeds (20 Copper), biome-spreading seeds (Hallowed in Hardmode, Corrupt or Crimson during a Blood Moon), Grass, Flower and Jungle Walls (10 Copper), Planter Boxes (1 Silver; since 1.4.5 all sold with no boss requirement), potted trees (3 Gold), Purification Powder, and a Dirt Rod (5 Gold).
  - **Steampunker:** the Clentaminator (2 Platinum), a biome-conversion tool, and its solutions at 15 Silver (Green outside special biomes, Blue in the Hallow, colour solutions that turn blocks into sand, snow or dirt after the Moon Lord). Also crafting stations at 10 Gold, some only in a matching biome (Ice Machine in snow, Sky Mill in space), plus logic gates and conveyors.
  - **Merchant:** torches, rope, and the basic Iron Anvil and Furnace (the Furnace only in the Jungle).
- **What vanilla doesn't sell.** None of the shop pages read for this note list stone, dirt, sand, wood, bricks, ores or bars. Raw blocks are left to gathering; vendors sell paint, walls, seeds and tools that change blocks. *Unverified:* a full check of every vanilla shop for ores, bars and raw blocks wasn't done. The Traveling Merchant has a random stock (see below) not checked for blocks.
- **Traveling Merchant.** About a 22% chance per day once 2 other NPCs live there. He stays until 6 PM, needs no house, and sells about 4 to 10 random items (more in Hardmode), always including one painting, with rare exclusives (a Diamond Ring at 2 Platinum). Scarcity and "come see what he has today" are the point.

### The Bestiary link (Zoologist)

- **Stock unlocks by collection breadth.** The Zoologist moves in at 10% Bestiary completion, then her shop expands at thresholds: 3% (Leather Whip), 25% (Dog License, Digging Molecart), 30% (saplings, saddles), 50% (globes that change the world's look), 70% (Ball O' Fuse Wire), and at 100% the **Universal Pylon**, which works anywhere without NPCs. Each Bestiary entry is filled by meeting or killing a creature, so the shop rewards breadth of exploration, not grinding one thing.

### Journey Mode research

- **"Collect enough and it becomes infinite."** Not an NPC, but the closest vanilla precedent for unlock-by-handing-over. In Journey Mode, sacrificing a set number of an item unlocks infinite duplication of it: **100 for blocks** (also ores, torches, rope), 25 for bars, herbs, seeds and most materials, 20 for potions, 15 for gems, 1 for gear and furniture. Research is per character, but teammates online in the same world share progress (not retroactively).

## Services, not items

- **Nurse.** Heals for 1 Copper per HP plus 1 Silver per debuff, multiplied by progress (×3 after the Eye of Cthulhu, up to ×200 after Golem) and by happiness. Costs track progress so healing stays a real sink.
- **Goblin Tinkerer: reforging.** Rerolls an item's random modifier for a third of its value (adjusted by happiness). Every eligible modifier has equal chance. A repeatable money sink.
- **Tax Collector: passive income.** 50 Copper per real minute for each housed NPC, capped at 25 Gold stored, collected by talking to him. Happiness scales both the rate (33 to 66 Copper) and the cap. With 20 NPCs: 10 Silver a minute. In multiplayer each player has separate funds. The cap is what stops it being an AFK faucet.
- **Angler: daily quests.** One fish quest per in-game day. Progress belongs to the character, not the world, so every player in multiplayer has their own. Rewards scale with quests completed, with fixed milestones (Angler armour at 10/15/20, Golden Fishing Rod at 30).
- **Dye Trader: barter.** In Hardmode, trades one Strange Plant (found in the world) for six vials of an animated dye that can't be crafted or bought.
- **Guide.** Shows recipes for an item you give him and gives housing hints.
- **Dryad.** Reports how much of the world is Corruption, Crimson or Hallow.

## Mods: building-block and "already-found" vendors

### AlchemistNPC Lite: the Architect

By Gregg, b2mauler and Rockman Zeta. Source on GitHub (`VVV101/AlchemistNPCLite`, last pushed July 2026). The Steam page says it was last updated December 2025 but now shows a "removed from the community" notice.

- **Moves in** once 3 town NPCs live there.
- **Ten shop tabs:** Filler (raw blocks and wood), Building (bricks and crafted blocks), basic and advanced furniture and crafting stations, and five light-source tabs (torches, candles, lamps, lanterns, chandeliers, candelabras).
- **Prices are near zero for bulk.** Gating mirrors where the block is found: Dungeon bricks after Skeletron (who guards the Dungeon), Hive and Honey after Queen Bee, Lihzahrd Brick after Golem (the Temple boss).

| Item | Price | Condition |
| --- | --- | --- |
| Dirt, Mud, Ash, Snow, Ice | 1 Copper | Always |
| Stone, Ebonstone, Crimstone, Sand types, Sandstone | 5 Copper | Always |
| Wood, Boreal, Ebonwood, Shadewood | 10 Copper | Always |
| Gray Brick, Glass | 10 Copper | Always |
| Marble, Granite | 50 Copper | Always |
| Hive | 10 Copper | Queen Bee defeated |
| Obsidian | 25 Silver | Eater of Worlds or Brain of Cthulhu defeated |
| Pink, Green, Blue (Dungeon) Brick | 50 Copper | Skeletron defeated |
| Pearlstone, Pearlsand | 25 Copper | Hardmode |
| Lihzahrd Brick | 1 Silver | Golem defeated |
| Spooky Wood | 1 Gold | Pumpking defeated |
| Builder accessories (Brick Layer, Extendo Grip, Ancient Chisel and others) | 15 Gold each | Hardmode |

- **Scale.** A full 999 stack of Stone costs about 50 Silver, the amount that makes the Merchant move in. Bulk blocks are effectively free once the Architect arrives.

### Fargo's Mutant Mod

One of the most widely used tModLoader mods. Read from the Fargo's wiki and the source in a public GitHub repository (`Midnight295/Fargowiltas`, latest commit "Version 3.3.6", March 2025). *Unverified:* whether that repository matches the current release.

- **LumberJack: wood, unlocked by chopping.** The wiki says he moves in once "all players combined have gathered 500 wood from chopping trees". The source differs: after the world's chopped-wood counter passes 400, shaking a tree has a 1 in 10 chance to drop him out of it, which unlocks him for good. Shop: Wood and Boreal Wood at 10 Copper, Rich Mahogany, Palm, Ebonwood and Shadewood at 15, Ash Wood at 20, Pearlwood at 20 in Hardmode, Spooky Wood at 50 after Pumpking, Cactus and Bamboo at 10, a Living Wood Wand at 1 Gold. A second button, **Tree Treasures**, gives a free biome-based gift once per day-and-night cycle (100 Cactus in the desert, 50 Rich Mahogany plus fruit and critters in the jungle, 50 Boreal Wood in snow, gems or a mouse underground). *Unverified:* exactly what the counter counts.
- **Squirrel: sells what you've already owned.** Moves in after King Slime (or a Top Hat Squirrel with the Souls add-on). His shop is built from items players have held: any item that has been in a player's inventory, piggy bank or armour slots is flagged as "owned" and saved with the character. Critters, town-NPC items and (with Souls) Enchantments and Souls appear once owned. **Potions appear only once a player has owned a stack of 30.** Price is twice the item's value (critters 10 Gold, golden critters 50). Two quirks from the source:
  - Ownership is only recorded when the shop list is built, which happens when someone talks to the Squirrel while carrying the item.
  - The shop is the union of what **currently online** players have owned, so its contents change with who's logged in.
- **Mutant: boss summons after the first kill.** Moves in after any boss. Sells each boss's summon item once that boss has been defeated in the world (`DownedTheBoss` condition), in three tabs (pre-Hardmode, Hardmode, post-Moon Lord).
- **Deviantt: rare-enemy summons after the first kill.** Moves in after any rare enemy is killed. Sells a summon for each rare enemy or miniboss once it has been killed, for example Attractive Ore (Undead Miner, 3 Gold), Holy Grail (Tim, 5 Gold), Pink Slime Crown (Pinky, 5 Gold).
- **Abominationn: event summons** after the Goblin Army.
- **The common rule.** Fargo's NPCs never sell something you haven't reached. They make repeating it cheap: re-fighting a boss, refilling a potion, rebuying a critter. The mod's pitch is removing farming, not skipping progress.

### Bosses As NPCs (Rijam)

- Each vanilla boss becomes a town NPC after it's killed and sells its own drops. Price rule: sell value × 5 for guaranteed drops, otherwise sell value ÷ drop chance, so rare drops cost more. Listed for tModLoader 2026.07. No building blocks are mentioned.

### Others checked

- **Helpful NPCs (Caseratis):** its Miner sells bars, gems and "natural blocks" (v2.2.0, August 2023, "Reworked Miner's shop a little by adding natural blocks"). The Steam page now shows a removal notice. *Unverified:* the stock and conditions.
- **Architect NPC (Solitaire), Useful NPCs, standalone Lumberjack NPC:** mod listings describe an Architect selling wood, stone and dirt after the Eye of Cthulhu (tModLoader 0.11.4, so old), and a Useful NPCs Dealer selling "common blocks, rare blocks", furniture and Traveling Merchant paintings. The mirror and catalogue pages couldn't be fetched. *Unverified.*
- **Thorium:** no block vendor. Its Blacksmith sells most vanilla pre-Hardmode bars after the Eye of Cthulhu, and the Druid sells all seeds. Thorium leans on services: the Cobbler's free Shined Shoes buff, the Desert Acolyte starting a Sandstorm for 1 Gold, the Tracker's contracts to kill rare enemies, which unlock shop items, and the Weapon Master as a hireable fighter.
- **Calamity:** only the Sea King's page was checked. He sells ocean weapons and bait, and no blocks. *Unverified:* whether any Calamity NPC sells blocks.

## Design discussion

- **No developer rationale was found** for gating or limiting shops, or for the happiness design, beyond the 1.4.0.1 patch-note line linking towns, happiness and a teleport network. The changes themselves say something: Re-Logic loosened the pylon happiness requirement in 1.4.3.3 and removed it in 1.4.5.0, added price icons and housing instructions, and dropped the boss requirements on Planter Boxes. Each change makes the system less fiddly and more readable. *Unverified:* the reasons.
- **Happiness criticism.** A long Steam thread ("I don't like the 1.4 NPC happiness mechanics (rant)", April 2021, 80 replies) complains of tedium ("the tedium involved in setting everything up"), of having to build "a load of 3 house outposts in arbitrary biomes", and that old creative builds would now be penalised for crowding or the wrong biome. One reply says rarely anyone liked it. Others defend it as zero effort or say biome-themed villages look good. Player opinion, not a survey.
- **Opacity of move-in conditions.** Census exists to show them, and 1.4.5 added in-game housing help. Players needing a mod or a wiki to know why an NPC hasn't arrived is a recurring problem.
- **Shops trivialising gathering.** *Unverified:* no substantial primary discussion was found of the Architect or the LumberJack trivialising exploration. The mods' own gating (blocks only after the area's boss, summons only after the first kill) suggests the authors avoided letting shops skip content.

## Relevance

What transfers to a shared 5 to 10 player Minecraft server, and what doesn't. Terraria worlds are usually solo or small co-op, owned by one player, with one of each NPC.

- **Bulk blocks are the easy win.** Vanilla Terraria barely sells raw blocks. The mods that fill the gap price them near zero and nobody's progress suffers, because raw building blocks carry no progression value. Vanilla Minecraft building blocks are the same, and our vision already says they never get harder. A Mason-style vendor for vanilla blocks fits "supplemental, never required" with little risk. The open question is price: near-free (the Architect) helps builders most; Super Awesome Villagers' 16 per Emerald keeps some cost.
- **Gate a block where it's found, not by an arbitrary boss.** AlchemistNPC sells Dungeon bricks after the Dungeon's boss and Lihzahrd Brick after the Temple's boss. Our equivalent: an Era's blocks become buyable only after someone has been there. Note the tension with the Decided rule that Era-gated blocks need that Era's tools to mine. Selling only *decorative* Era blocks after the fact is safe; selling ores or materials would bypass the tool gate. The Super Awesome Villagers notes record vision.md's rule that Villagers never sell Era materials, so the Idea needs to say which kind it means.
- **"Sells it once you've given it some" has three Terraria models.**
  - **World flag (AlchemistNPC, Mutant):** unlock once the boss or area is done, for everyone. On a shared server that's "one player's progress unlocks it for the whole server", which vision.md's catch-up easing rejects.
  - **Per-player ownership (Fargo's Squirrel):** unlock what you've owned. Its shop changes with who's online, which would confuse a shared Settlement.
  - **Hand over a quantity (Journey research):** give 100 blocks once, unlock forever. This is the closest to our Idea, and the thresholds (100 for blocks, 25 for materials) are a ready starting point.
  - **Recommendation:** tie the unlock to a specific Villager or Settlement that was physically given the items. That keeps it visible in the world and local, credits the player who did it, and still lets others benefit. Mostly the giver's friends, which matches the catch-up goal.
- **Fargo's principle fits our philosophy.** Its NPCs only ever make *repeating* reached content cheaper (re-summoning, refilling), never skip it. That's the same line as "a supplemental way to get what you'd rather not gather". The Squirrel's "owned 30 at once" rule is a neat filter: it proves the player could gather it before the shortcut opens.
- **Move-in conditions as signposts.** Owning, beating or finding something brings a new NPC. That's a cheap way to make a Settlement grow with progress, and to show it. "Found in the world and talked to" (Goblin, Mechanic, Angler) suits exploration: a rescued Villager in a structure could become a Settlement specialist. Learn from Census and 1.4.5: show what's missing and why, in-game (this matches the "learnable without a wiki" Idea).
- **Housing checks answer part of "measure Prosperity without dictating builds".** Terraria checks categories (enclosed, light, a surface, a seat, a door), each satisfied by many items, plus a query tool that names what's missing. A Minecraft version could check a room for a bed, a light and a workstation without caring about style. The query tool's direct feedback is worth copying.
- **Avoid happiness-style placement puzzles.** Biome and neighbour preferences with price swings of up to 50% were widely seen as tedious, and Re-Logic has been unwinding their link to pylons. Crowding penalties actively fight our "central Settlement worth living near" goal. If Villager preferences exist, keep them flavour or mild and never gate a core feature behind them. Terraria's quirk of reading the *player's* biome wouldn't make sense in Minecraft anyway.
- **One-of-each NPCs don't map to breedable Villagers.** Terraria's systems assume each NPC is unique (one Nurse, one Tax Collector). Per-NPC benefits would be farmed with Minecraft's breeding: the Tax Collector's "income per housed NPC" becomes a breeder exploit. Services and unlocks should scale with the Settlement (Prosperity) rather than headcount, or count distinct professions.
- **Services fit "Prosperity unlocks trades and services".**
  - **Progress-scaled cost (Nurse):** a service whose price follows progress stays meaningful.
  - **Repeatable sink (Goblin reforge):** rerolling suits anything random. Note that random rerolls clash with our enchanting design's fixed recipes.
  - **Capped passive income (Tax Collector):** collect-in-person with a low cap is the model if any passive income exists. The cap is what keeps it within the "no AFK income" rule.
  - **Per-player daily quests (Angler):** progress tracked per character works naturally on a shared server.
  - **Barter (Dye Trader):** a rare found item traded for something unbuyable. A good non-Emerald trade shape for "no single universal currency".
- **Collection-driven shops (Zoologist) link to the Collection Log Idea.** Stock unlocking by breadth (percentage of creatures met), topped by a Convenience capstone (the Universal Pylon), rewards exploring widely rather than grinding.
- **Traveling Merchant scarcity** is the Wandering Trader's role already. Its "always one painting" rule (one guaranteed decorative item) is a cheap way to make visits worth checking.
- **Currency doesn't transfer.** Terraria's coins are universal and plentiful by midgame, so cheap shops cost nothing. Our Settlements are Leaning towards no single currency, so Terraria's prices only show *relative* scale: bulk blocks around 1/100th of a basic tool, and the Universal Pylon priced as a capstone.

## Sources

- https://terraria.wiki.gg/wiki/NPCs (move-in conditions, spawn timing, respawning)
- https://terraria.wiki.gg/wiki/House (housing rules, housing menu and query)
- https://terraria.wiki.gg/wiki/NPC_happiness (modifiers, crowding, Happiness option)
- https://terraria.wiki.gg/wiki/Pylons (sale and use rules, version history)
- https://terraria.wiki.gg/wiki/Merchant, https://terraria.wiki.gg/wiki/Painter, https://terraria.wiki.gg/wiki/Dryad, https://terraria.wiki.gg/wiki/Steampunker (shops)
- https://terraria.wiki.gg/wiki/Zoologist (Bestiary thresholds)
- https://terraria.wiki.gg/wiki/Traveling_Merchant
- https://terraria.wiki.gg/wiki/Nurse, https://terraria.wiki.gg/wiki/Reforge, https://terraria.wiki.gg/wiki/Tax_Collector, https://terraria.wiki.gg/wiki/Angler, https://terraria.wiki.gg/wiki/Dye_Trader
- https://terraria.wiki.gg/wiki/Journey_Mode (research counts)
- https://terraria.wiki.gg/1.4.0.1 and https://terraria.wiki.gg/wiki/1.4.5 (patch notes)
- https://github.com/VVV101/AlchemistNPCLite (`NPCs/Architect.cs`) and https://steamcommunity.com/sharedfiles/filedetails/?id=2599842771
- https://fargosmods.wiki.gg/wiki/NPCs and https://github.com/Midnight295/Fargowiltas (`NPCs/LumberJack.cs`, `NPCs/Squirrel.cs`, `NPCs/Mutant.cs`, `NPCs/Deviantt.cs`, `FargoPlayer.cs`, `FargoWorld.cs`)
- https://terrariamods.wiki.gg/wiki/Bosses_As_NPCs
- https://steamcommunity.com/sharedfiles/filedetails/?id=2687866031 (Census)
- https://steamcommunity.com/sharedfiles/filedetails/changelog/2817545194 (Helpful NPCs)
- https://thoriummod.wiki.gg/wiki/NPCs
- https://calamitymod.wiki.gg/wiki/Sea_King
- https://steamcommunity.com/app/105600/discussions/0/3168820451531560091 (happiness criticism thread)
