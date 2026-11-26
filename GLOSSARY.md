# SuperAwesomeCraft

A Minecraft total conversion for a small friends server. Vanilla's moment-to-moment play stays familiar; progression, economy and shared goals are replaced.

## Design

**Season**:
One build-and-playtest cycle that replaces one part of vanilla and answers one design question. Parts not yet built stay vanilla.
_Avoid_: Update, version, phase

**Motivation**:
A reason a player plays, such as huge projects, collecting or power. Players combine several; the design targets motivations, not archetypes.
_Avoid_: Archetype, player type, class

**Convenience**:
Quality-of-life capability such as mining speed, builder tools, storage, mobility and block access. Cheap, early and available without combat.
_Avoid_: Utility gear, comfort

**Power**:
Combat capability such as damage, protection, survivability and Heart upgrades. Earned through Eras.

**Technical parity**:
The rule that vanilla mechanics behave exactly as in vanilla: physics, redstone timing, mob AI, spawning and item movement. It protects how mechanics behave, not whether a resource or system exists; changed behavior ships as a separate variant beside the vanilla one.
_Avoid_: Vanilla compatibility

## Progression

**Era**:
A stage of progression tied to one dimension, with its own materials and power tier.
_Avoid_: Tier, chapter, age

**Stronghold**:
The dungeon that ends an Era, named for the dimension its portal leads to (a Nether Stronghold holds the first portal to the Nether). A world has several of each kind, and a cleared one can be fought again.
_Avoid_: Era Gate, gate dungeon, boss arena

**Climax fight**:
The final encounter of a Stronghold, held in an arena the game designed. Winning it opens the Stronghold's portal.
_Avoid_: Boss fight, final fight

**Portal**:
The way into an Era's dimension, and the only thing that unlocks for the whole world. The first one is opened by a Climax fight; later ones are built from that dimension's materials.
_Avoid_: World unlock, gate

**Catch-up easing**:
Help for players who are behind that comes from other players carrying things back, such as materials, blocks and crafting stations from a new dimension. Never an automatic server-wide unlock.
_Avoid_: Rubber-banding

**Heart upgrade**:
A permanent increase to a player's max health, found while exploring an Era's dimension.
_Avoid_: Life crystal

## Gear

**Slot**:
Room on an item for one enchantment, at any level. How many an item has depends on its material, plus up to three Slot upgrades.
_Avoid_: Capacity, modifier slot, upgrade slot, ability slot, enchanting power

**Slot upgrade**:
A one-off upgrade that adds a Slot to an item, each paid with a material from a different Era.

**Augment**:
A proposed slotted part that adds a raw stat, such as speed, damage or protection, to an item. Raw stats may come from enchantments or tool tiers instead.
_Avoid_: Relic, gem, upgrade part

## World and getting around

**Per-player loot**:
Generated loot containers that roll separately for each player who opens them. The first player to arrive never empties them for others.
_Avoid_: Shared loot, instanced chests

**Grave**:
A container holding a player's items after death. It never despawns, and Convenience tools can locate it, reclaim it, or return the player to it.
_Avoid_: Corpse, gravestone

**Travel cart**:
The fast minecart variant built for getting around, beside the unchanged vanilla minecart.
_Avoid_: Fast minecart, locomotion cart

**Gold rail**:
A stronger powered rail that boosts only Travel carts beyond vanilla speed.

## Villages and social

**Village**:
A vanilla village, generated or founded by players bringing Villagers to a bell, extended with a Steward, Prosperity, quests and Raids. Players may run any number, alone or together.
_Avoid_: Settlement, colony, town, base

**Steward**:
The one Villager per Village whose job block is the bell. He stays near it and is the Village's interface for salary, quests, Prosperity requirements and stats.
_Avoid_: Chief, mayor, leader

**Prosperity**:
A Village's level, raised by meeting that level's requirements and surviving a Raid. It raises every Villager's level and unlocks better trades.

**Raid**:
A wave-based group encounter against a Village that scales with its Prosperity. It never destroys player blocks.

**Collection Log**:
A per-player record of distinct items, creatures and places discovered, with rewards for breadth.
