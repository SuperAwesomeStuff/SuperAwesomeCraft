# Vision

## The problem

Super Awesome Datapacks reworked Enchanting and Villager trading. They were played on a 26.3 server with six regular players in September 2026. The reworks worked as designed, but the server lasted about ten days and roughly 54 player-hours.

- **Every route ended at the same activity.** Players sold raw ore and food to Villagers for Emeralds, bought Diamond gear, enchanted it (Fortune first), then strip-mined Netherrack with an unbreakable Efficiency V pick until they had full Netherite.
- **Players left when their goals ran out.** The fastest player reached full Netherite in about 6 hours of play and left soon after. Another left the day after killing the Dragon.
- **Slower players fell behind and drifted away.** The two players who barely traded never reached the Nether.
- **Builders were pulled into the grind.** The best convenience (Unbreakable, instant mining) sat at the top of the combat ladder, so building comfortably meant climbing it.
- **Large parts of the game went unused:** fishing, brewing, Trial Chambers and Bastions.

Root causes:

1. One universal currency turned playstyles into exchange rates.
2. Each player climbed a private ladder with a fixed end.
3. Nothing gave the server shared goals.
4. Convenience was tied to power.

## Who we are designing for

The design targets **motivations**, not fixed archetypes, because every real player combines several. The motivations seen in the group:

| Motivation | What serves it |
| --- | --- |
| Being near the group | A central Settlement worth living in or near; shared events |
| Having space of their own | Travel tools that keep distant homes connected to the group |
| Huge long-term projects | Bulk materials and builder tools that make large projects possible to finish, and systems that give those projects a function |
| Growth with no cap, as long as it's useful | Settlement expansion, deep resource layers, large-scale scaling systems |
| Gear and power | Era Gates, encounter loot, horizontal gear choices |
| Collecting | A Collection Log with rewards for breadth |
| Exploration and challenge | Dungeons and areas that unlock in sequence; structures found by learnable rules, not luck |
| Running a settlement | Villagers who live in, use and grow with what players build |
| Mischief and PvP | Opt-in arenas and minigame spaces |
| Pure building | Cheap convenience and plentiful blocks; building never requires combat |

## Structure

### Eras

The game advances through a small number of **Eras**. Each Era has an **Era Gate**: a dungeon chain with group encounters, ending in a climax fight in an arena built into the dungeon. Clearing it gives the player:

- access to the next Era's area or dimension (a personal unlock, as with Twilight Forest's progression barriers),
- new materials, Catalysts and trades,
- the next tier of power.

Once any player has cleared a Gate, the world eases that Era for everyone else: trades get cheaper, structures easier to find, Catalysts cheaper. That's catch-up help, not a requirement.

### Convenience and Power

| Convenience (cheap, early, no combat needed) | Power (earned through Eras) |
| --- | --- |
| Durability, mining speed, Silk Touch, storage (early Shulkers), mobility, reach, bulk block access | Damage, protection, survivability, encounter-specific gear |

Gear tiers should offer horizontal choices with distinct abilities, not one best set.

### Settlements and Raids

Villagers move into Settlements that players build and use their buildings. A Settlement's prosperity grows with what's been built and how it's used, which unlocks trades and services. Prosperity draws Raids: wave-based group encounters that scale with the Settlement. A Raid never destroys player blocks. Walls and layout matter, fighters have a reason to defend, and everyone has a reason to show up.

### Economy

There is no single currency everything converts into. Each playstyle earns rewards that are useful in their own right instead of an exchange rate that can be optimized. Buyer-side income has limits. Farms are welcome but never required.

## Constraints

- Players connect with an **unmodified client**. The server can send a resource pack automatically. See [ADR-0001](adr/0001-server-side-fabric-with-polymer.md).
- Balance for 5 to 10 players of varying skill on one shared world.
- Avoid first-come finite resources that the first player can claim.

## Open questions

- What is the setting or theme that gives Eras a reason to exist?
- How many Eras, and what does each one unlock?
- What does each Era Gate look like as a dungeon, and how is the climax arena protected?
- How is Settlement prosperity measured without dictating how people build?
- What replaces the Emerald economy, and what are the limits on buyer income?
- What are the target hours played for each Era?
- Which of Super Awesome Enchanting and Super Awesome Villagers carry over, and how do they change?
