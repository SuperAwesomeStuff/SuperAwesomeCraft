---
name: design-lens
description: Apply game design principles (MDA, economies, feedback loops, skill floor and ceiling, steering behaviour) to SuperAwesomeCraft. Use when designing or critiquing a mechanic, system or Season, fixing a design problem, or reading playtest results.
---

# Design lens

Run a design proposal, problem or playtest result through the lenses below. The full explanation and examples behind each lens are in [`docs/research/game-design-101.md`](../../../docs/research/game-design-101.md); read the section a lens names whenever its question isn't obvious.

## Steps

1. **Load context.** Read `docs/vision.md`, `docs/design-map.md`, the relevant `docs/research/` notes and any ADR touching the area. Done when you can say which motivations from the vision's table the thing serves and which Season it belongs to.
2. **Pick the branch:**
   - **Designing**: a new or changed mechanic or system. Apply every lens.
   - **Fixing**: something reported as broken. Start with *Root cause* and *Levers*, then apply the rest to each candidate fix.
   - **Playtest reading**: results came back. Start with *Hypothesis*, then *Degenerate strategies* and *Snowball*.
3. **Apply the lenses.** Done when every lens has an answer or a one-line reason it doesn't apply.
4. **Report back** as a short list per lens, then the two or three findings that matter most. Mark anything new as an **Idea**; per `AGENTS.md`, it goes into `docs/design-map.md` only as an Idea and is promoted only when the user confirms.

## Lenses

- **MDA** *(§1)*: Mechanic → what will players *do* → what will they *feel*? Name the feeling with a strong word, never "fun". Does it serve the vision and the rest of the design, or clash with them?
- **Lodestar** *(§1)*: how does this help a playstyle push the world forward (the vision statement in `docs/vision.md`), and which pillar does it serve? If none, flag it as side content or a candidate for subtraction.
- **Borrowed** *(§2)*: if it comes from another game or mod, why does it work *there*, and does that reason hold on a shared Minecraft server for 5 to 10 players?
- **Economy** *(§4)*: map it as taps, inventories, converters, drains and traders. Which behaviour does each tap reward? Is there an exploit loop (a broken tap)? How many currencies, and does that create decisions or remove them?
- **Snowball** *(§5)*: which positive loops does it create, both for runaway winners and for players falling behind? What negative loop offsets it, and does that loop ever make getting ahead the worse choice?
- **Optimise the fun out** *(§6)*: what's the most efficient boring strategy? Does the design reward the behaviour we want, or only punish the behaviour we don't? Could a limit be flipped into a bonus?
- **Presence** *(§6)*: is the system's depth and visibility proportional to how much it matters?
- **Skill floor and ceiling** *(§3)*: can the least experienced player in a mixed group contribute and feel capable? Where is the optional depth for the strongest? Are rewards for skill given as challenge rather than power?
- **Root cause** *(§7, Fixing)*: what's the cause behind the reported symptom? Does everyone agree on which problem it is?
- **Levers** *(§7, Fixing)*: which values define the thing and are fixed (including technical parity), and which are free to change? Consider big changes (double or halve), flipping it, fixing it in another system, and a fix that solves several problems at once. What are the second-order effects?
- **Hypothesis** *(§8)*: what result would prove or disprove this in the Season's playtest? Write the prediction down before the playtest.
- **Degenerate strategies** *(§1, §8)*: what unexpected behaviour showed up, and does it fit the vision (keep it) or undermine it (fix it)?
