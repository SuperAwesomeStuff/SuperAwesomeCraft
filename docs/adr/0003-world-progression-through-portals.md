# World progression through portals

**Status:** Accepted. Supersedes [ADR-0002](0002-personal-progression-with-catch-up-easing.md).

ADR-0002 made progression personal: clearing an Era Gate unlocked the next Era only for the player who cleared it. In practice, that means a player whose friends say "come with us" gets stopped by a condition they haven't met, and has to go and earn something that's right in front of them. Per-player gate checks also have to be built and maintained on every piece of gated content.

Eras are now dimensions, which gives a simpler gate. **Clearing an Era Gate gives the means to open a portal to the next dimension. Once a portal exists in the world, anyone can use it and do anything on the other side.** Nothing checks whether a player personally cleared a Gate. Progression belongs to the world, like Terraria's.

## Considered options

- **Personal unlocks with catch-up easing (ADR-0002):** respects each player's pace, but blocks players from joining friends and needs per-player checks everywhere.
- **Personal unlocks, but others can enter as guests:** still has the "you can't do that yet" friction inside the dimension.

## Consequences

- No per-player unlock state. The only gate is whether a portal can be built.
- Late joiners and slower players arrive in a world where later dimensions are already open. Gear and Heart upgrades, not access, are what they lack.
- Era-gated blocks are gated by tools anyone can craft once the dimension is open, not by player conditions.
- Fast players can open dimensions before others are ready. Accepted: being able to play together matters more.
