# Season 1 playtest wishlist

**Status: Idea.** A jot-down of systems to have in before the Season 1 playtest. Nothing here is designed yet. Each entry links to where the idea already appears in the docs, so the pieces aren't scattered. Promote an entry to Leaning or Decided (and into [vision.md](../vision.md)) only once it's designed.

**Scope question:** [vision.md](../vision.md#seasons) says Season 1 is Gear and everything else stays vanilla, with rails and Settlements in later Seasons. Bringing these into the Season 1 playtest widens it. Decide whether they ship in Season 1, or whether the playtest just gets an early slice of each.

## Want for the playtest

### Beacon rework

- Range depends on the blocks the pyramid is built from, not only its size.
- Some way to raise the level of the effects.
- Maybe more effects.
- **Watch:** the instant-mining breakpoint. Efficiency V on a diamond pick plus Haste II just instant-mines stone, and deepslate never instant-mines ([enchanting](enchanting/README.md), "Instant-mining stone"). Haste III or higher moves that line. Deepslate would need Haste VIII or more with Efficiency V, so a small bump is safe for deepslate but makes stone easier to reach.
- Already in the docs: the beacon as a shared build ([vision.md](../vision.md#vision-statement), [enchanting](enchanting/README.md) "Being near the group").

### Villager trading rework

- Similar in spirit to the Super Awesome Villagers datapack.
- Already in the docs:
  - [Design map](../design-map.md#gear), Ideas: the trade rework itself, librarians selling new-system books, and book prices that keep Efficiency V from being a shortcut.
  - [Enchanting](enchanting/README.md): what Villagers may never sell (Blaze Rods, Wither Skeleton Skulls, Shulker Shells, combat or loot books, enchanted gear).
  - [vision.md](../vision.md): the September 2026 playtest, where selling everything to Villagers for Emeralds made every route end at the same activity. Open question there: which parts of Super Awesome Villagers carry over.
  - [vision.md](../vision.md#settlements): Settlements, Prosperity and no single currency (later Season).

### Getting around

- Minecarts, horse changes, maybe a grapple hook and a glider.
- Already in the docs:
  - [vision.md](../vision.md#adventuring) and [design map](../design-map.md#getting-around): better mounts (faster breeding, higher baseline, horses carry chests, donkeys wear armor, leaves don't block them), a glider-like step between horse and Elytra, copper powered rails, gold rails and the Travel cart.
  - [Minecarts and rails research](../research/minecarts-and-rails.md).
  - Grapple on fishing rods as a possible enchantment ([design map](../design-map.md#gear), [enchantment candidates](../research/enchantment-candidates.md)).
- Open: what the glider is and how you get it.

### Death rework

- Graves or corpses, maybe like the [Simple Corpse](https://modrinth.com/mod/simple-corpse/versions) mod.
- Ways to teleport back to your grave, or bring your grave to you.
- Already in the docs:
  - [vision.md](../vision.md#adventuring) and [GLOSSARY](../../GLOSSARY.md): graves never despawn; mid-game tools locate, reclaim or return you to them.
  - [Design map](../design-map.md#progression), "Arena in the destination": paid remote recovery as the alternative to a corpse run, so it must arrive before the first arena.
- **Baseline:** the Vanilla Tweaks Graves datapack is what the group normally plays with, so start from how it feels: right-click puts every item back in its old slot, only the owner can open it, it never despawns, it never breaks blocks, a void death leaves it on a slab at the bottom of the world, and your coordinates go in chat. Our locate, remote reclaim and teleport-back tools would be new on top of that. Its grave keeps the XP vanilla would drop; ours holds none, since XP is gone, and that needs explaining in game.
- Research: [graves and corpses](../research/graves-and-corpses.md). Simple Corpse has no Fabric build and is All Rights Reserved, so it's a look-and-feel reference only; we'd build our own. Universal Graves (Fabric, 26.3, LGPL) is the stopgap or code to study.
- Points from the research to settle when this is designed:
  - "Bring the grave to you" (like You're in Grave Danger's Death Scroll) suits the arena idea. "Teleport to the grave" would skip an arena's one-way entry unless it's blocked there.
  - Costs in items, not levels, since there's no XP.
  - Who can open your grave: owner only, or owner-only for a while.
  - Where an End void death leaves its grave.

### /home, /spawn and /tp without commands

- Survival-friendly, vanilla-like ways to get home, get to spawn and reach other players.
- New; not in the docs yet. Nearest: "Travel tools that keep distant homes connected to the group" ([vision.md](../vision.md)) and the open "Recall or teleport items?" ([design map](../design-map.md#getting-around)).
- Ties into grave teleporting above.

## Maybe

### Potions and brewing

- New; not in the docs yet. Brewing went unused in the September 2026 playtest ([vision.md](../vision.md)).
- Overlaps with the beacon rework if beacons get more effects.

### Earlier Shulker boxes, or something like them

- Around the time players reach the Nether, or soon after.
- Already decided in principle: "Backpacks and earlier Shulkers" ([design map](../design-map.md#getting-around), [vision.md](../vision.md#adventuring)), listed as Convenience.
- **Watch:** the End slot upgrade costs 4 Shulker Shells ([enchanting](enchanting/README.md)). If earlier Shulkers come from a different recipe, keep Shells End-only so maxed gear still needs the End.
