# Research: Graves and Corpses

Background for the grave in the Adventuring design (items go into a grave that never despawns, and mid-game Convenience tools locate it, reclaim it remotely or teleport you back to it), and for the idea of something like Simple Corpse with ways to teleport to your grave or bring your grave to you. Gathered November 2026 from the Vanilla Tweaks site and its Graves data pack 4.1.2 for 26.3 (downloaded from the picker and read file by file), the Modrinth and CurseForge pages, the Modrinth API (versions and changelogs), the Simple Corpse 1.8.0 jar (no source is published, so its classes were inspected with `javap`), and the source repositories of Universal Graves, You're in Grave Danger, Corpse and Gravestones. The target is Java 26.3. Anything marked *Unverified* couldn't be confirmed from a primary source.

## Vanilla Tweaks Graves

The data pack the group already plays with on almost every server, so it's the baseline for what a grave "should" do. From the 4.1.2 files (`data/graves/...`) unless stated otherwise.

- **What it is.** A data pack from vanillatweaks.net, in the Gameplay Changes category of the data pack picker. The picker blurb: "When you die, it places a grave to save all your drops. Right-click or left-click a grave to retrieve your items." No resource pack or mod is needed, so it runs on a vanilla server with vanilla clients.
- **Versions.** The picker offers **26.3** (Graves 4.1.2). Older picks: 26.2 (4.1.1), 26.1 (4.1.0), 1.21 (4.0.6), 1.20 (3.0.5), and further back. The 26.3 zip is named `graves v4.1.2 (MC 26.3).zip`, and its `pack.mcmeta` sets both minimum and maximum pack format to 121.0, so each download targets one game version.
- **Licence and terms.** No open-source licence. The site's Terms let anyone "use, modify and share our packs" in their own projects, but not redistribute a pack as is without "proper modification and/or additions", not paywall it, and only with credit (a `credits.txt` naming Vanilla Tweaks and linking the site). So we could study it or build on it, with credit, as long as the result is free.

### How a grave is made

- **Detecting the death.** The pack overrides the player loot table (`minecraft:loot_table/entities/player`) so that a death drops a hidden "Grave" marker item carrying a copy of your inventory and equipment, your UUID and your name. A death-count scoreboard plus an advancement then runs the creation function, which finds that marker item and every item entity that dropped at your death spot in the same tick, matches them against your pre-death inventory, and removes them from the ground. If anything couldn't be matched, the owner gets a chat warning saying how many slots may have been missed. A spectator death makes no grave.
- **What it stores.** Every item you dropped: main inventory, hotbar, armour and offhand, each with the slot it came from. Items with Curse of Vanishing are already gone in vanilla before anything drops. *Unverified:* whether modded slots are caught. A **Compatibility Mode** option (off by default) grabs every item dropped at the same tick and spot, which fixes modded slots but lets one grave take everything if two players die in the same spot at the same moment.
- **XP.** With **XP Collection** on (the default), the grave absorbs the XP orbs that dropped at the death spot. So it stores what vanilla drops, which is not all your XP. The config screen says so: "players do not drop all their XP on death." (Vanilla drops 7 points per level, capped at 100.)
- **Empty deaths.** If there are no items and no XP to store (for example with `keepInventory` on), no grave is made.
- **What it looks like.** Not a block. A grave is an `item_display` entity showing a scaled-down **stone brick wall** (a small stone post, turned to face a cardinal direction) with an invisible 0.75-block `interaction` entity riding it as the hitbox. The items live inside the display entity itself (in the item's `bundle_contents` component), along with the XP total and the owner's UUID. The owner's name goes on the hitbox as its custom name. There's no player head, body, glow, particles or beacon. *Unverified:* how far away it renders, and whether the name ever shows in-game.

### Placement

- **Starting point.** The grave starts at the death position. If that spot is inside a solid block, or overlaps another grave, a shulker or an end crystal, it climbs upward (also checking the four sides) until it finds room.
- **Falling and floating.** It then falls through "attracting" blocks (air, plants, torches, rails, redstone dust, doors, signs and the like) until something stops it. It floats up through still liquid sources until it reaches the surface. That covers lava as well as water, so a grave from a lava lake ends up on top of it. Carpets, candles, lily pads, trapdoors, heads and pots stop it on their top or bottom.
- **What it never touches.** It never breaks or replaces a block, apart from the void platform below. It won't pass through bedrock, barriers, command blocks, portals or end gateways.
- **Void deaths.** A grave that falls out of the world (or a death below it) goes to the lowest block of the world in that column. If that space is empty, the pack places a **cobblestone slab** under it. In the End that means a grave floating at the bottom of the void.
- **Unloaded chunks.** If the death spot isn't loaded, the pack forceloads it while it finishes, then removes the forceload.

### Opening it

- **Right-click (use)** loads the grave straight into your inventory. Each item goes back to its old slot, armour and offhand included. Anything already in that slot, and anything whose slot is unknown, drops at your feet with no pickup delay, and only you can pick it up. Armour with Curse of Binding isn't swapped out. Stored XP is added to you directly. An actionbar tip points players to this.
- **Left-click (attack)** shakes the grave. Hitting it again while it shakes breaks it open and **drops** everything, items and XP orbs, on the ground.
- **Either way the grave is used up.** It plays a stone break sound and particles, and the grave and its list entry are deleted. There's no partial take and no GUI, and since it isn't a container, hoppers can't touch it.
- **Explosions, fire and lava.** Nothing in the pack handles them. Display and interaction entities aren't damaged by explosions, fire or lava in vanilla, so the grave should survive them. *Unverified:* not tested in-game.

### Who can open it

- **Grave Robbing** is **off by default**. Only the owner can open their grave, and anyone else gets "Grave robbing is disabled." With it on, anyone can open any grave. There's no timed protection and no rule for killers.
- **Grave Key** (admin tool). A glinting tripwire hook, "Use this to forcibly open anyone's grave," held in either hand. One is used up per grave outside creative. It only comes from the config screen. There's no recipe.

### Despawning

- **Never, by default.** `$despawn_seconds` defaults to **0**, which means graves never despawn. If set, a grave and its contents are **deleted** (not dropped) after that many seconds, and the timer only runs while the grave is loaded.

### Finding it

- **Death message.** With locating on, the owner gets a chat line on death: "Your last grave is at (x, y, z) in the Overworld. Enter /trigger graves to list all your graves." Clicking the coordinates puts an `/execute in <dimension> run tp @s x y z` command in the chat box, which only works for players allowed to use `/tp`.
- **`/trigger graves`** lists your graves, 5 per page: coordinates, dimension, how long ago (counted only while the world is running), and a hover with the item slot count and XP points. The list is kept in data storage (`graves:main`) separately from the graves themselves, so it works from anywhere, but it's only a list. Nothing can be done to a grave from it.
- **Grave Locating** is on by default, unless the `reduced_debug_info` game rule is on, which turns it off. When off, no coordinates are shown and `/trigger graves` is disabled.
- **No compass, waypoint, teleport or remote recovery.** Coordinates in chat are the only locating feature.

### Config and admin

- **`/function graves:config`** opens a clickable chat menu with toggles for Grave Robbing (off), XP Collection (on), Grave Locating (opposite of `reduced_debug_info`) and Compatibility Mode (off), the Grave Despawn Time (0), **List All Graves** (`/function graves:list`, every grave in the world with its owner) and **Receive Grave Key**. Settings live in `storage graves:config` and the `graves.config` scoreboard.
- **Uninstalling.** `/function graves:uninstall` refuses while a death is still being processed. `/function graves:force_uninstall` skips that check.

### Known issues and limits

- **No public issue tracker.** Support is through the Vanilla Tweaks Discord, which the pack's own error messages link to. *Unverified:* what's outstanding.
- **Failure modes the pack warns about itself:**
  - "Your loot table data wasn't found." This happens when another pack or mod overrides the player loot table, which this pack needs.
  - The grave's hitbox losing its display entity.
  - "A rare Minecraft quirk" where some items aren't picked up.
  - In each case it tells you where your items dropped.
- **An entity in the world.** The grave only exists in its chunk, so it can only be opened in person, and anything that removes entities (`/kill @e`, an entity-clearing plugin) would delete the items.

## Simple Corpse: what it is

- **Author and pitch.** By Bocha (Bocha9031). The page calls it a "basic gravestone mod in the style of a corpse with no UI or keybinds". You die, a corpse that looks like your body appears with all your items, and you have to find your way back and mine it. It deliberately has no waypoints, guides or GUI.
- **Loaders and versions.** Forge for 1.20.1 (latest 1.5.0) and NeoForge for 1.21.1 (latest 1.8.0, November 22, 2026, needs NeoForge 21.1.232+). Neither Modrinth nor CurseForge lists a Fabric build, and there's nothing for 26.x. Needed on both client and server.
- **Licence.** All Rights Reserved, on both sites and in the jar. There is no public source repository or issue tracker, only a Discord. We can't port, fork or bundle it.
- **Size.** About 3,500 Modrinth downloads (first Modrinth upload June 2026; the CurseForge history goes back to September 2024).
- **No teleporting or remote recovery.** It has neither. The closest thing is an optional chat message with your corpse's coordinates.

## Simple Corpse: how the corpse works

From the 1.8.0 jar and changelog.

- **A block, not an entity.** The corpse is a block (`CorpseBlock`) with a block entity that stores the items as NBT (`StoredInventory`), plus XP, owner UUID, death cause and pose data used for rendering. Because it's a block, it saves with its chunk and doesn't need the chunk loaded to survive.
- **Never despawns.** The code has no timer that removes a corpse. Its only scheduled tick is a one-off position fix after explosion deaths. A corpse stays until someone breaks it.
- **Hard to destroy by accident.** The base block is unbreakable with blast resistance 18,000,000, so explosions can't break it. Mining is special-cased: a player allowed to loot it mines it like a block of hardness 0.75. Since 1.8.0 the Wither can't break it either.
- **Getting items back.** Mining the corpse drops every stored item and the XP as orbs on the ground at the corpse. With the optional Shift Click to Collect, sneak-right-clicking puts items straight into your inventory, optionally back in their original slots (armour, offhand, Curios and Cosmetic Armor Reworked included). It isn't a container, so there's no GUI and hoppers can't pull from it. 1.8.0 fixed hoppers being able to *insert* items, which were then lost.
- **What goes in.** Everything in the player's death drops (it hooks NeoForge's `LivingDropsEvent`), including armour, offhand and Curios slots, with each item's original slot recorded. If `keepInventory` is on, no corpse is made.
- **XP.** By default it stores 7 XP per level, uncapped. Turned off, it caps at vanilla's 100.
- **Placement.** The corpse goes at the death position. If that spot is taken it moves up until it finds room. Corpses in water are waterlogged, and corpses in snow are flagged for rendering. If a corpse can't be placed at all, the items and XP drop on the ground instead (1.8.0).
- **Void deaths.** With Protect Void Deaths on (the default), the corpse is placed at the bottom of the world with a cobblestone block put under it.
- **Lava deaths.** With Protect Lava Deaths on (the default), dying in lava still leaves a corpse. *Unverified:* exactly where it ends up in a lava lake. The code has a lava check with a range of 10 blocks, but the bytecode alone doesn't settle the placement rule.
- **Ownership and PvP.** By default anyone can mine anyone's corpse, so PvP kills and looting work as they do in vanilla. With Owner Only Looting on, only the owner can mine or collect it (creative players still can, so admins can clear corpses). There's no timed protection and no special rule for killers. Since 1.8.0 corpses don't send their inventory to nearby clients, so other players can't read what's inside.
- **Finding it.** Optionally the owner gets a chat message with the corpse's coordinates and dimension. `/simplecorpse lastinventory` lists the items from your last death and, if that message is on, where the corpse is. Ops can view any online player's last death and use `/simplecorpse restore <player>` to drop those items at the player's feet. The server keeps a copy of the last death's inventory in the player's persistent data for this.
- **Looks.** The body uses the player's skin, lies in a pose based on how they died (arrows stuck in it, frozen, withered with bones showing, slimed, and so on), droops over ledges, and renders from up to 128 blocks away. It respects Pehkui player scale and works on Create Aeronautics ships.

## Simple Corpse: config

A NeoForge server config (`simplecorpse-server.toml`), also editable in-game from the Mods menu. All options were added in 1.8.0.

- **Owner Only Looting** (off): only the owner can mine or collect the corpse.
- **Corpse Location In Chat** (off): on death, tells only you where your corpse is and in which dimension.
- **Shift Click to Collect** (off): sneak-right-click to take the items into your inventory. Anything that doesn't fit drops at your feet.
- **Restore Original Slots** (off): with Shift Click to Collect, puts each item back in its old slot if that slot is empty.
- **Uncapped XP Drops** (on): stores 7 XP per level with no cap.
- **Protect Lava Deaths** (on) and **Protect Void Deaths** (on): leave a corpse for these deaths instead of losing everything as vanilla does.

## Simple Corpse: known issues

- **No public tracker.** Issues are handled on Discord, so open bugs can't be checked. *Unverified:* whether any are outstanding.
- **Fixed bugs worth knowing about**, from the 1.7.2 and 1.8.0 changelogs:
  - Packet overload with very large inventories.
  - Hoppers putting items into corpses, where they were lost.
  - Two players dying in the same spot at the same moment, with one corpse overwriting the other.
  - Items lost when a corpse couldn't be placed.
  - Repeated skin lookups causing lag.
- **Upgrade warning.** The 1.8.0 changelog tells players to retrieve any corpses holding items they care about *before* updating. That suggests the stored format changed (the jar has a `StoredInventoryFixer` class).

## Other grave and corpse mods

### Universal Graves (Patbox)

- **Status.** Fabric and Quilt. Version 3.13.0+26.3 has supported **Java 26.3** since September 10, 2026, and there are builds for every 26.x version. Licence LGPL-3.0. Over 5 million downloads. It runs purely server-side through Polymer, so vanilla clients see a skull or a model.
- **Storage.** Grave contents live in world save data (a `SavedData` called `graves`), not just in the block. The block is the visible marker. Because of this, graves can be listed, opened and acted on from anywhere.
- **Despawning.** In the current source, `self_destruction_time` defaults to -1, so graves never self-destruct. The README's example config still shows 1800 seconds, so the README is out of date. By default timers only run while the server is running (`use_real_time` is off). Game rules `universal_graves:protection_time` and `universal_graves:breaking_time` can override these per world.
- **Ownership and PvP.** Other players are locked out for `non_owner_protection_time` (900 seconds by default), and after that anyone can loot. `attackers_bypass_protection` lets killers loot straight away on PvP servers. Players can whitelist others, and a grave limit per player can be set.
- **Locating.** A **death compass** is given on respawn (`give_death_compass`, on by default). It points to your latest grave, opens the grave's GUI when used, and disappears (or turns back into a vanilla compass) once the grave is gone. `/graves` lists all your graves with their coordinates.
- **Remote actions** from the `/graves` GUI:
  - Remove protection remotely (on by default).
  - Break the grave remotely (on by default). This **drops the items at the grave's location**. It doesn't send them to you.
  - Pay the unlock cost remotely (off by default).
  - None of these brings the items to you. Quick pickup (shift-use, or breaking it yourself) only works at the grave.
- **Teleporting to the grave.** The grave GUI has a teleport button. Its cost defaults to type `creative`, which means only creative players get the button. Servers can set it to `free`, an `item` cost (any item stack and count) or a `level` (XP) cost. Teleporting takes a 5-second warm-up that is cancelled if you move, puts you 1 block above the grave, and gives 2 seconds of invincibility. A `universal_graves.teleport` permission lets staff teleport to any grave.
- **Costs elsewhere.** `unlocking_cost` (free by default) can make opening your own grave cost items or levels.
- **Placement.** It searches up to 8 blocks for a free spot, then tries shifting up to 40 blocks, up to 5 times. It doesn't replace solid blocks unless configured to, can restore the block it replaced, keeps graves inside the world border, and can be blocked by damage type, attacker type, world, area or land claim. It also adds a craftable decorative gravestone (stone bricks and a sign).

### You're in Grave Danger (B1n-ry)

- **Status.** Fabric and NeoForge. The latest Fabric build is 2.4.18 for **1.21–1.21.1** (June 22, 2025), with nothing for 26.x. Licence MIT. Needs Cloth Config on Fabric. Around 4.8 million downloads.
- **Despawning.** Grave timeout is off by default, so graves never despawn. When it's on, the default is 5 hours, and contents drop on timeout. It keeps up to 100 backups per player for admin restores.
- **Ownership and PvP.** Grave robbing is on by default: other players can loot after 1 hour, with an option to let killers skip the wait. The owner is told when, and by whom, their grave was robbed.
- **Locating.**
  - A **grave compass**, optionally given on respawn and consumed on use, can be set to point to your closest grave.
  - A **Death Sight** enchantment (off by default) shows graves within 64 blocks.
  - An option (off by default) announces where a player's graves are when they log off.
- **Remote retrieval and teleporting: the Death Scroll** (off by default, optionally given on respawn). On use it does one of three things, set in the config:
  - View the grave's contents.
  - **Restore the contents straight into your inventory from anywhere.** The scroll claims the grave remotely.
  - **Teleport you to the grave.**
  - Use time, cooldown and whether it's consumed are configurable.
- **Other items and rules.**
  - **Grave keys** (off by default) can be required to open a grave and can be bound to a player or to a specific grave.
  - Graves can be unlocked for others from the GUI.
  - Soulbound is supported.
  - The void and minimum grave height are configurable per dimension: −60 in the Overworld, 3 in the Nether and the End.
- **Getting the items.** No recipe for the scroll, key or compass ships in the mod. They come from config options such as receive-on-respawn or the GUI. Only the grave block has a recipe.

### Corpse (henkelmax)

- **Status.** NeoForge (and Forge for older versions) only. It has a **26.3** build (1.1.22+26.3, updated October 2026) but no Fabric build. The source is on GitHub, but Modrinth lists the licence as All Rights Reserved and the repo has no licence file. About 4.8 million downloads.
- **An entity, not a block.** The corpse is an entity with your skin and equipment. It can't fall into the void (it's held at the bottom of the world), doesn't burn in lava, and floats in liquids. After an hour it turns into a skeleton, which is purely cosmetic.
- **Despawning.** An emptied corpse despawns after 30 seconds. A full corpse never despawns by default (`force_time` −1).
- **Ownership.** Anyone can open it by default. An owner-only option exists, optionally opening to everyone at the skeleton stage.
- **Locating and teleporting.** No compass. A death history screen (the U key) lists past deaths with their coordinates. Clicking the location puts a teleport command in chat, which only works for players allowed to run `/tp`. Items can only be pulled from the history in creative mode. There's no player-facing remote retrieval or paid teleport.

### Gravestones (Geometrically)

- **Status.** Fabric only, last updated for **1.20.1** (June 2023). It looks abandoned for our purposes. Licence MIT.
- **Features.** A deliberately simple, "elegant" design:
  - Retrieval on break, use or step.
  - Items go back into your inventory.
  - 75% of XP is kept.
  - Optional chat coordinates.
  - Looting by others is off by default, except for ops at level 4.
- **What it lacks.** No compass, remote retrieval or teleport.

## At a glance

| Mod | Fabric 26.3? | Form | Never despawns by default? | Locate | Remote reclaim | Teleport to grave |
|---|---|---|---|---|---|---|
| Vanilla Tweaks Graves | Data pack, **26.3** | Entity (item display + interaction) | Yes | Chat coordinates, `/trigger graves` list | No | Op-only `/tp` suggestion |
| Simple Corpse | No (Forge/NeoForge 1.20.1/1.21.1) | Block | Yes | Optional chat coordinates | No | No |
| Universal Graves | **Yes** | Block + world save data | Yes (in source) | Death compass, `/graves` list | Remote break drops items *at* the grave | Yes, cost configurable (creative-only by default) |
| You're in Grave Danger | No (1.21.1 latest) | Block | Yes | Compass, Death Sight enchant | Death Scroll restores to your inventory | Death Scroll |
| Corpse | No (NeoForge 26.3) | Entity | Yes (when not empty) | Death history coordinates | Creative only | `/tp` command only |
| Gravestones | No (1.20.1) | Block | Yes | Optional chat coordinates | No | No |

## Takeaways for SuperAwesomeCraft

- **Vanilla Tweaks Graves is what players expect.** Players coming from it will expect these, so keep them unless there's a reason not to:
  - Right-click to put everything back in its old slots, armour and offhand included, with leftovers dropped at your feet for you alone.
  - Hit it to spill everything on the ground instead.
  - Only the owner can open it.
  - It never despawns.
  - It never breaks blocks, floats out of lava and water, and lands on a slab at the bottom of the void.
  - A chat line with your grave's coordinates on death, and a command that lists your graves.
  - It isn't a container, so hoppers can't touch it.
- **Where our design differs from that baseline:**
  - **Never despawns.** Same as their default, so nothing changes for players.
  - **Locate, reclaim remotely, teleport back.** Vanilla Tweaks has none of these. Its only way to teleport is an op `/tp` suggestion, and its list is information only. These will be new to players, and they're the Convenience upgrades.
  - **No XP.** Vanilla Tweaks stores vanilla's capped XP drop by default. Season 1 removes XP, so our grave holds items only. Players used to getting their XP back may need to be told why.
  - **Looks.** A small stone post is easy to miss. The body that looks like you (from the Ideas) would stand out more.
  - **Storage.** Vanilla Tweaks keeps the items inside the grave entity, so it can only be opened in person, and a `/kill @e` would delete them. Remote reclaim needs the contents in world save data, the way Universal Graves does it, and that also makes the grave safer.
- **We'd have to build our own.** Simple Corpse is All Rights Reserved and has no Fabric build, so it's a reference for look and feel only. Universal Graves is the only Fabric 26.3 option. It's LGPL-3.0, server-side and very configurable, so it could serve as a stopgap dependency or as code to study. Its locate, teleport and remote-break features are close to our list.
- **Never despawns is easy and standard.** Every mod here can do it, and most do by default. A block-based grave (Simple Corpse, Universal Graves) survives unloaded chunks with no extra work. Keeping the contents in world save data, the way Universal Graves does, is what makes "locate from far away" and "reclaim remotely" cheap. A remote action then doesn't need to find a block entity in an unloaded chunk.
- **Locate, reclaim remotely, teleport back map onto existing patterns:**
  - **Locate:** a death compass (Universal Graves, You're in Grave Danger). A good fit for an early, cheap Convenience tool.
  - **Reclaim remotely:** You're in Grave Danger's Death Scroll in restore mode is the only one that brings the items *to you*. Universal Graves' remote break only drops them at the grave. For the "Arena in the destination" idea, which needs a paid alternative to a corpse run, it has to be the "to you" version.
  - **Teleport back:** Universal Graves' item- or level-cost teleport with a warm-up that movement cancels is a ready model. Level costs don't fit us because Season 1 removes XP, but an item cost does.
- **Teleporting into an arena is a hole.** A teleport-to-grave tool would let players skip the arena's one-way entry. If remote recovery exists to replace corpse runs into arenas, the "grave comes to you" direction is safer than "you go to the grave" there. Otherwise teleporting would need a dimension or structure blacklist (Universal Graves and You're in Grave Danger both support blocking by world or area).
- **Convenience vs Power.** Every tool here only saves time and trips: finding, fetching and travelling. None adds combat strength, so they sit cleanly on the Convenience side. A cost (items, a cooldown or a consumable like the Death Scroll) keeps "die, respawn, reclaim instantly" from erasing death's sting. The cost can drop as players progress.
- **Technical parity.** A grave is a new block or entity and doesn't change any vanilla mechanic, so it fits the parity rule. Details worth copying:
  - **Hoppers:** don't make the grave a vanilla container. Simple Corpse had a bug where hoppers inserted items into corpses and lost them. Corpses that no hopper can touch are safer for item sorters and for players.
  - **Placement:** don't replace solid blocks or redstone. Move to a free spot the way Simple Corpse and Universal Graves do.
  - **Explosions and the Wither:** make the grave immune to both.
- **Void and lava deaths.** All four mods, and Vanilla Tweaks, keep these deaths from wiping your items. Simple Corpse places the corpse at the bottom of the world on a cobblestone block, Vanilla Tweaks does the same on a cobblestone slab, and You're in Grave Danger uses a minimum height per dimension. For the End's void this needs a deliberate rule, because a grave at the bottom of the End can't be reached on foot. That makes a strong case for a remote reclaim tool there.
- **PvP.** The mods split between open looting (Simple Corpse's default, Corpse) and timed owner protection (Universal Graves 15 minutes, You're in Grave Danger 1 hour). With 5 to 10 friends on one world, owner-only or a long protection time fits the "no first-come grabs" spirit. If we want PvP looting, Universal Graves' killer-may-loot option is the model.
- **Simple Corpse's flavour is worth stealing.** A body in your skin, posed by how you died and visible from 128 blocks away, is a readable in-world landmark that needs no UI. It suits the early game, before any locate tool exists.

## Sources

- https://vanillatweaks.net/picker/datapacks/ and the picker's data (`/assets/resources/json/<version>/dpcategories.json` for 26.3, 26.2, 26.1, 1.21 and 1.20) for the description and pack versions
- Graves 4.1.2 for MC 26.3, generated through the picker (`/assets/server/zipdatapacks.php`): `pack.mcmeta`, `data/minecraft/loot_table/entities/player.json`, `data/graves/loot_table/entities/player.json`, `data/graves/advancement/*.json`, and the `graves` functions `load`, `config`, `creation/*`, `placement/*`, `opening/*`, `destruction/*`, `location/*`, `give_grave_key`, `uninstall`, plus the block and entity tags in `data/graves/tags`
- https://vanillatweaks.net/terms (use and redistribution terms) and https://vanillatweaks.net/about
- https://modrinth.com/mod/simple-corpse/versions and the Modrinth API (`api.modrinth.com/v2/project/simple-corpse`, `/version`) for the page text, licence, loaders and changelogs 1.5.0–1.8.0
- https://www.curseforge.com/minecraft/mc-mods/simple-corpse/files (file history back to 1.0.0, September 2024, Forge and NeoForge only)
- Simple Corpse 1.8.0 jar: https://cdn.modrinth.com/data/l2IanFwi/versions/XDLbbcbt/simplecorpse-1.21.1-1.8.0.jar (`neoforge.mods.toml`; `CorpseBlock`, `CorpseBlockEntity`, `CorpseDeathEvents`, `CorpseLooting`, `CorpsePlacement` and `SimpleCorpseConfig` inspected with `javap`; `en_us.json`)
- https://modrinth.com/mod/universal-graves and https://github.com/Patbox/UniversalGraves (commit 7f7508e, 3.13.0+26.3: `config/Config.java`, `grave/Grave.java`, `grave/GraveManager.java`, `ui/GraveGui.java`, `other/GraveUtils.java`, `registry/GraveCompassItem.java`, `registry/GraveGameRules.java`)
- https://modrinth.com/mod/yigd and https://github.com/B1n-ry/Youre-in-grave-danger (branches `1.21-fabric` and `1.21-neoforge`: `YigdConfig`, `GraveConfig`, `ExtraFeaturesConfig`, `CommandConfig`, `item/DeathScrollItem.java`), with the wiki at https://github.com/B1n-ry/Youre-in-grave-danger/wiki
- https://modrinth.com/mod/corpse and https://github.com/henkelmax/corpse (master, 1.1.22+26.3: `ServerConfig.java`, `entities/CorpseEntity.java`, `gui/DeathHistoryScreen.java`)
- https://modrinth.com/mod/gravestones and https://github.com/Geometrically/Gravestones (master: `config/GravestonesConfig.java`, `GravestoneRetrievalType.java`, `block/GravestoneBlock.java`)
