# Research: Minecarts and Rails

Background for the proposal to switch the powered rail recipe to copper, add a stronger gold rail, and keep the vanilla minecart next to a faster travel cart. Gathered November 2026 from minecraft.wiki, Mojang's changelogs, the Mojira bug tracker, the Polymer docs and the Express Carts source. The target is Java 26.3 ("Wilderness Bound", released September 15, 2026).

## The Minecart Improvements experiment

Added in 24w33a (1.21.2) as the `minecraft:minecart_improvements` experimental data pack. It is off by default.

- **Speed game rule.** Added as `minecartMaxSpeed` and renamed `max_minecart_speed` in 25w44a (1.21.11). It defaults to 8, accepts 1 to 1000 blocks per second, and exists only in worlds with the experiment turned on. Reaching the cap isn't guaranteed because drag works against slopes and boosts.
- **Track following.** Carts stay on the rail at high speed and take corners smoothly, so they rarely stall or derail. Movement is split into sub-steps along the track (`NewMinecartBehavior.stepAlongTrack`), so the server does more work per tick as speed rises.
- **Slopes and momentum.** Carts keep vertical momentum where a track ends in a slope and tilt while flying through the air. They come to rest more easily between opposing slopes and no longer snap to the block centre when landing (24w34a). On slopes they collide with blocks instead of passing through them.
- **Placement and pickup.** A player can't place a cart inside another cart. Dispensers can still stack carts; 24w34a reverted that restriction for them. Carts pick up mobs more reliably and take the correct rotation as soon as they are placed.
- **Riders.** A new "Rotate with Minecarts" accessibility option is off by default and only works in worlds with the experiment. Dismounting places the player in front of or on top of the cart, no longer to its right. Activator rails eject players at a different block than vanilla does (Express Carts issue #11).
- **Interpolation.** The server sends a dedicated `ClientboundMoveMinecartPacket` ("Move Minecart Along Track"). It carries a list of lerp steps (position, movement, yaw, pitch, weight), and the client replays them through `NewMinecartBehavior`'s `lerpSteps` and `getCartLerpPosition`. Ordinary entities only get periodic position updates.
- **How the behaviour is chosen.** `AbstractMinecart.useExperimentalMovement(Level)` checks the level's enabled feature flags, and the cart's `MinecartBehavior` (old or new) is picked from that result. This happens on the server and on the client independently.
- **Status (late 2026).** Still experimental. It is listed as a current Java experiment, and the 26.1, 26.2 and 26.3 changelogs mention no change to it. A developer quoted on the wiki said Mojang wants "a good time and a good approach" because they "don't want to break the things players have already made".
- **Known issues.** MC-275756: rotation stutters when a cart lands on diagonal rails. It is experiment-only and still open at Low priority. MC-275628: carts derail on downward rails above a certain speed. MC-276602: fast carts fail to re-rail. MC-275819: carts lose momentum when derailing downhill. MC-275883, a client freeze from riding a cart cluster into a wall, was fixed in 24w37a. The wiki warns that contraptions relying on rail snapping can break.

## Vanilla powered rails

- **Recipe.** 6 gold ingots, 1 stick and 1 redstone dust make 6 powered rails, so each rail costs one gold ingot. Mineshaft minecart chests can contain 1–4 powered rails (27.1% chance, about 23% in sulfur caves). No villager trades are listed.
- **Power.** A powered rail passes power along up to 8 connected powered rails, so one source lights 17 rails. The chaining check only matches the same block type.
- **Boost.** An active rail adds about 0.06 blocks/tick per tick along the direction of travel. It pushes a stopped cart away from the solid block the cart faces. An inactive powered rail brakes a cart, usually within one block.
- **The 8 m/s cap.** Old-physics carts are capped at exactly 8 blocks/s per axis (0.4 blocks/tick), about 11.3 blocks/s on diagonals.
- **Spacing.** On flat track an occupied cart needs one powered rail every 34 blocks to hold 8 m/s; the wiki recommends one every 38 (7.97 m/s). Empty carts need about 1 in 8, full chest carts 1 in 6. Uphill, occupied carts need 1 in 3, and empty or full chest carts need every rail powered.
- **Gold demand.** A flat passenger line needs roughly 27 gold per kilometre, a modest cost. Item lines, uphill track and station boosters are what get expensive.
- **Copper vs gold supply.**
  - Copper ore: 16 attempts per chunk in blobs of up to 16 (up to 52 in dripstone caves), drops 2–5 raw copper (about 7.7 with Fortune III), plus large ore veins.
  - Gold ore: 4 attempts per chunk in blobs of up to 13, half of which are skipped when exposed to air, and drops 1 raw gold (up to 4 with Fortune). Badlands are the exception.
  - Copper is roughly an order of magnitude easier to get. Technical players mostly get gold from piglin farms anyway.
- **Copper in recent versions.**
  - The Copper Age (1.21.9, September 30, 2025) added the copper golem, copper chests, copper tools and armour, copper nuggets, torches, bars, chains, lanterns, oxidizing lightning rods and copper horse armour.
  - 26.3 added hidden oxidized copper chests in abandoned camps and a `copper_chest` block-transformer type.
  - **Mojang has not added a copper rail** in any version through 26.3. Copper rails exist only in mods such as Minecarts Overhaul and The Copper Rail.

## Technical-parity risks

- **What depends on vanilla carts and rails.**
  - Hopper and chest minecart item transport and unloaders.
  - Cartstone (minecart logic) and timing-based builds that assume exactly 0.4 blocks/tick and old snapping or bouncing.
  - Minecart stacking in one block, used for entity cramming and mob and villager transport.
  - TNT minecart cannons and bombs.
  - Builds relying on carts passing through blocks on slopes and corners. The experiment removes that.
  - Powered rail lines used as fast power and update chains.
  - Rail dupers: pistons and slime can duplicate any rail. Fabric fixes such as RailsDupeFix exist up to 26.1.2.
- **Recipe-only change.** Swapping gold for copper in the recipe leaves the block, its states and its behaviour unchanged, so nothing mechanical breaks. Things to update:
  - The recipe-unlock advancement (`recipe/transportation/powered_rail`), which currently unlocks on a gold ingot.
  - The recipe book entry.
  - Any datapack or mod recipes that use powered rails. These are unaffected.
- **Side effects of the recipe swap.**
  - Mineshaft loot still gives powered rails.
  - Rail dupers stay as cheap as before.
  - Gold farms lose one of their main sinks, but the new gold rail takes that role back.
- **Enabling the experiment world-wide would break parity.** Every vanilla cart would switch physics. The travel cart has to get the new behaviour on its own instead.

## Feasibility as a server-side Fabric mod with Polymer

**Verdict: feasible.** The travel cart is already proven by Express Carts. The gold rail is feasible, with one visual compromise.

- **Precedent: Express Carts** (MIT, Fabric, server-side, bundles Polymer). It supports 26.1–26.3 and adds an Express Minecart: 16 m/s by default, with experimental physics and vanilla carts left alone. How it works:
  - A mixin on `AbstractMinecart` ORs `useExperimentalMovement(level)` with "is express cart", so that one entity is built with `NewMinecartBehavior`. A matching mixin on `CollisionContext` does the same check.
  - A mixin on `NewMinecartBehavior.getMaxSpeed` returns a configured speed per entity. The speed can be scaled by water and by the block under the rail. The game rule isn't used, and doesn't exist without the experiment anyway.
  - A mixin on `ServerEntity.sendChanges` skips the special minecart packet for these carts. Vanilla clients without the feature flag build their copy with `OldMinecartBehavior` and "don't know what to do with" the lerp-step packets. They get normal entity movement packets instead.
  - `PolymerEntity.getPolymerEntityType` returns vanilla `MINECART`. The cart is told apart by a display block (red carpet). The item shows as a minecart with an enchantment glint, or a custom texture with the Polymer resource pack.
  - Braking (holding back) works by reading `ServerPlayer.getLastClientInput()`.
- **Per-entity physics works, client interpolation doesn't.**
  - The behaviour can be chosen per entity on the server.
  - The client's interpolation (`NewMinecartBehavior` lerp steps and Rotate with Minecarts) can't be reused. It depends on the client world's feature flags, which are global and sent at login.
  - Enabling the flag for clients alone would desync every vanilla cart.
  - The travel cart therefore renders with ordinary entity interpolation.
- **Jitter at high speed.**
  - Minecarts are server-authoritative, even when ridden, so there is no rubber-banding against client input. Smoothness depends on how often position updates arrive.
  - The custom `EntityType` exists only on the server, so its tracking update interval can be lowered to every tick without client changes. This is an inference to verify in testing.
  - Corners will look slightly cut at 24+ m/s. MC-275756-style rotation stutter is possible.
  - Above roughly 20–30 m/s, chunk sending becomes the limit; elytra flight runs around 30 m/s. Express Carts offers ender-pearl-style chunk loading as an option.
- **The gold rail block.**
  - Make it a subclass of `PoweredRailBlock`/`BaseRailBlock` so all carts treat it as a rail.
  - Both physics paths check `Blocks.POWERED_RAIL` for boosting, so mixins are needed either way.
  - `findPoweredRailSignal` only chains through the same block, so gold and copper rail segments won't pass power to each other. Document that, or extend the check.
  - On clients it shows as `powered_rail` with matching shape and power.
  - Rails have no spare client states: `waterlogged` renders water, and every shape and power combination is already used. Polymer's `BlockModelType` list has no rail type.
  - To tell it apart, either retexture vanilla `powered_rail` as copper in the resource pack (fitting, since it is now copper) and add a block-attached item-display overlay (polymer-virtual-entity) only on gold rails, or skip the new block and use Express-style "rail on block X" multipliers.
  - Display entities cost the client far more than blocks. That is acceptable for sparse booster rails but not for whole lines.

## Concrete proposal

| Cart \ rail | Plain rail | Powered rail (copper) | Gold rail |
|---|---|---|---|
| Vanilla minecart (old physics) | vanilla | vanilla, 8 m/s cap | acts exactly like a powered rail, 8 m/s cap |
| Travel cart (experimental physics) | coasts | cap 16 m/s | cap 24 m/s at launch, playtest toward 32 |

- **Powered rail.** 6 copper ingots, 1 stick and 1 redstone dust make 6. The block is unchanged.
- **Gold rail.** For example, 4 powered rails, 4 gold ingots and 1 redstone block make 4. Use a different pattern from the old gold recipe so technical players crafting from habit don't get a block that won't chain power with their powered rails.
- **Travel cart.** Minecart plus one ingredient, following Express Carts' minecart plus redstone torch. Show it as a minecart with a display block, and add a chest variant later.
- **Max speed per rail.** The travel cart's max speed comes from the rail under it, like Express Carts' block multipliers. Check how `NewMinecartBehavior` clamps speed above the cap; a sudden drop when leaving a gold section may need a gradual decay instead.
- **Vanilla carts stay exact.** The gold rail must not raise their cap or change their boost, so cartstone and item lines behave as they do in vanilla.
- **Things to watch.**
  - Activator-rail ejection and dismount positions differ from vanilla.
  - Derailing on downhill track at speed (MC-275628).
  - Server cost of sub-stepping at high speed.
  - Chunk loading while riding.
  - Rail dupers: a gold rail that can be pushed by pistons can be duplicated. Consider `PushReaction.BLOCK` for it.
  - If Mojang ships the experiment, revisit the design, since vanilla carts would change underneath technical builds.

## Relevance

- The design keeps technical parity: the recipe swap only touches data, and new physics is limited to a separate entity.
- Copper powered rails make passenger lines cheap early on. Gold moves to an optional high-speed tier, which fits "make the optimal route less required".
- A travel cart running at 16–32 m/s fills the travel gap between horse and Elytra noted in the Bottom Dweller research.

## Sources

- https://minecraft.wiki/w/Minecart_Improvements
- https://minecraft.wiki/w/Experiments
- https://minecraft.wiki/w/Minecart
- https://minecraft.wiki/w/Powered_Rail
- https://minecraft.wiki/w/Tutorial:Minecarts
- https://minecraft.wiki/w/Copper_Ore
- https://minecraft.wiki/w/Gold_Ore
- https://minecraft.wiki/w/Third_Drop_2025 (The Copper Age)
- https://minecraft.wiki/w/Java_Edition_26.1
- https://minecraft.wiki/w/Java_Edition_26.2
- https://minecraft.wiki/w/Java_Edition_26.3
- https://www.minecraft.net/en-us/article/minecraft-snapshot-24w33a
- https://mojira.dev/MC-275756
- https://mojira.dev/MC-275883
- https://bugs.mojang.com/browse/MC-275628
- https://bugs.mojang.com/browse/MC-276602
- https://bugs.mojang.com/browse/MC-275819
- https://mappings.dev/1.21.4/net/minecraft/world/entity/vehicle/NewMinecartBehavior.html
- https://mappings.dev/1.21.4/net/minecraft/world/entity/vehicle/AbstractMinecart.html
- https://maven.fabricmc.net/docs/yarn-1.21.11+build.4/net/minecraft/network/packet/s2c/play/MoveMinecartAlongTrackS2CPacket.html
- https://polymer.pb4.eu/latest/polymer-blocks/basics/
- https://polymer.pb4.eu/latest/polymer-core/entities/
- https://modrinth.com/mod/express-carts
- https://github.com/Seercat3160/express-carts (source, issues #11, #20, #23)
- https://modrinth.com/mod/minecarts-overhaul
