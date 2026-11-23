# Candidate C: Runes

> **Retired.** Superseded by [the library design](library.md), which was itself replaced by [the slot design](../README.md). Kept for reference.

**Status: Idea.** One of three Season 1 candidates; see the [comparison](README.md). The [shared ground rules](README.md#shared-ground-rules) apply.

**In two sentences:** every Power and Yield enchantment is a **rune**, an item you carve, keep until you rank it up, and slot into your gear's **sockets** (one per item wearing it at a time); runes come out intact whenever you like, so your collection is your progress. The top rank of each rune is **Grand** and needs a socket **Gilded** with a far-off material, while Convenience enchantments use no socket and cost almost nothing.

This candidate borrows Monster Hunter's decorations (crafted jewels in limited slots), Hollow Knight's charms (a visible collection and a small budget) and MCC Island's physical augments.

**Four new words:** *rune*, *socket*, *Gilded*, and *Grand* for a rune's top rank, which needs a Gilded socket. Everything else is a vanilla word.

## How a player experiences it

1. **First evening.** The Enchanting Table's screen lists everything that fits the item you're holding. Put in your pick, click Efficiency II and Silk Touch, pay a few Redstone and Amethyst Shards: Convenience goes straight on and uses no socket. Power enchantments are runes, each with its recipe and a one-line effect. You carve a Looting I rune from Bones, Gold, Lapis and an Amethyst Shard.
2. **First week.** Your diamond sword has **3 sockets**. You slot Sharpness IV, Looting II and Knockback I. For the Wither you swap Sharpness IV for Smite IV from your Rune Pouch. Nothing is lost; the Sharpness rune goes back in the pouch. You hand a spare Looting II to a friend's farm sword; it carries your name.
3. **After full Netherite.** Sharpness V is a **Grand** rune, and its recipe line says *"Grand: needs a Gilded socket"* before you carve it. You Gild a socket with a Heart of the Sea, which also lets you swim faster while holding the sword. Then you carve the Grand rune. You'd carved a second Sharpness IV for your axe; now that the axe has a better build, you give that spare to a friend who just reached diamond.

## Rules

### Runes

- **A rune is one enchantment at one rank** (*Looting Rune II*). Its tooltip says what it does, what it fits on, and who carved it; a Grand rune's tooltip also says *"needs a Gilded socket"*. Under the hood, runes are a single item whose enchantment and rank are data components, with a tinted texture per family and a gold frame for Grand runes. Runes don't stack.
- **Ranks follow vanilla's maximum levels** (Sharpness V, Protection IV, Looting III, Quick Charge III, Lunge III, and so on).
- **Grand runes** are the top rank of **every enchantment with three or more levels** (Sharpness V, Protection IV, Thorns III, Looting III, Fortune III, Sweeping Edge III, Warding IV, Steadfast III, and so on). They fit only in a **Gilded socket**. Everything else (Protection III, Sharpness IV, Fire Aspect II, Flame, Infinity) fits any socket.
- **Carving** happens on the table's Runes tab, which lists every rune for the item you've put in (or all of them, with no item) and its full recipe. No recipe book or viewer is needed.
  - **Rank I:** the enchantment's catalyst plus 2 Lapis and 1 Amethyst Shard.
  - **Rank up:** a rune of the rank below plus the next catalyst (if it's socketed, the table offers to pull it out first). The lower rune is used up (the recipe line says so, so Sharpness IV on two items means carving it twice); the new rune names whoever carved it. Carving rank III when you have nothing costs ranks I, II and III together.
- Catalysts climb the [source ladder](README.md#the-source-ladder): rank I from tier 1, middle ranks tier 2 or 3, **every Grand rune needs a tier 3 or 4 material** (tier 4 for Yield). Heavy Cores are never used in carving. Examples:

| Rune | Recipe |
|---|---|
| Looting I | 4 Bones, 2 Gold, 2 Lapis, 1 Amethyst Shard |
| Looting II | Looting I, 1 Gold Block, 2 Ghast Tears |
| Looting III (Grand) | Looting II, 4 Echo Shards |
| Fortune III (Grand) | Fortune II, 4 Echo Shards or 1 Heart of the Sea (a pickaxe's single socket counts as Gilded, so a Fortune III pick costs one far-off item, as in A and B) |
| Sharpness I | 5 Amethyst Shards, 2 Lapis |
| Sharpness V (Grand) | Sharpness IV, 1 Wither Skeleton Skull, 4 Quartz |
| Protection IV (Grand) | Protection III, 1 Netherite Scrap, 2 Diamonds |
| Warding I | 2 Glass Bottles, 2 Redstone, 2 Lapis, 1 Amethyst Shard |

- **Wind Burst** (treasure) can't be carved from nothing: the first Wind Burst rune comes from breaking down an ominous vault book (level I; each ominous vault rewards each player once, so anyone can get one, though nobody is guaranteed one). Higher ranks are carved from it as usual.
- The full recipe table follows these rules and is written once this candidate is chosen.

### Sockets

- **Every weapon, armour piece, ranged weapon and tool has sockets**, shown in the tooltip as `[Sharpness IV] [Looting II] [ ]`, with Gilded sockets in gold. Every Grand rune's recipe line says *"Grand: needs a Gilded socket"* before you carve it, and shows whether the item in hand has one, so nobody spends a far-off material and then discovers a second requirement.

| Item | Wood, Stone, Leather, Chainmail, Copper | Iron, Gold, Turtle Shell | Diamond, Netherite |
|---|---|---|---|
| Swords, spears | 1 | 2 | 3 |
| Axes | 1 | 1 | 2 |
| Armour | 1 | 2 | 2 |
| Pickaxe, shovel, hoe | 1 | 1 | 1 |

| Item | Sockets |
|---|---|
| Bow | 3 |
| Mace | 2 |
| Crossbow, Trident, Fishing rod | 1 |
| Shield, Elytra | none (no Power or Yield enchantments in vanilla) |

- **Every weapon and armour piece has fewer sockets than useful runes** (tools have one socket for their one Yield rune), so every one is a choice even fully Gilded: a sword has six (Sharpness or Smite or Bane, Looting, Fire Aspect, Knockback, Sweeping Edge, Venom) for 3 sockets; an axe three (a damage rune, Fortune, Venom) for 2; armour has three (a Protection, Thorns, Steadfast) for 2; a bow has four for 3; a mace three for 2; a crossbow two (Multishot or Piercing, Quick Charge) and a trident three (Impaling, Channeling, Frostbite) for 1. Frostbite also fits swords, but it excludes Fire Aspect, so the sword's count is unchanged. Netherite's advantage over diamond is its base stats, not sockets.
- **One rune per enchantment per item.** Vanilla's incompatibilities are checked across runes and Convenience enchantments, in both directions, and shown on screen: Silk Touch blocks a Fortune rune, a socketed Channeling blocks Riptide.
- **Gilding (the long horizon):** at the table, Gild one socket with one of [the shared five far-off materials](README.md#tier-4-fair-access-and-shared-upgrade-materials). **Each material can Gild only one socket per item**, and each gives the item its small Convenience effect (silent to Sculk, faster swimming, a drop that can't be destroyed, free Ender Pearls, higher Wind Charge jumps). No item has more than three sockets, so the rare Heavy Core is never required. Smithing to netherite keeps Gilding (smithing copies item components).
- **No one has to Gild.** Protection III, Sharpness IV, Looting II and every one- or two-level enchantment fit ordinary sockets, so a full set of solid gear never needs a far-off trip. Grand runes are the top step, not the floor.
- **Tools** (pickaxes, shovels, hoes and fishing rods; axes are weapons) have one socket because Fortune (or Luck of the Sea) is their only rune; **a tool's single socket counts as Gilded**, so Fortune III or Luck of the Sea III needs only its own recipe and builders never pay twice (it can still be Gilded for an effect). Tool choices come from Techniques; the tooltip shows `Familiarity 3 · Techniques 1/2` on its own line.

### Convenience is cheap

- **Convenience enchantments use no socket**: they cost a few tier 1 or 2 materials per level (Efficiency I is 2 Redstone; Efficiency V is 4 Redstone Blocks) and go straight onto the item, on the same screen as runes. The screen says why in one line: *"Comfort uses no socket; power is a rune."*
- They stay on the item. Each has a remove button; removing refunds nothing, because they're cheap.

### First carvings: the shared layer

- **The first time anyone carves a middle rank at the table (rank II up to the one below Grand), that rank's catalysts are halved for everyone, at every table** (rounding up, minimum 1; never the lower rune a rank-up uses up). That reaches the ranks that slow a newer player (Sharpness IV, Protection III). Grand runes and Gilding stay full price, so the far-off sink stays measurable; the world's first carving of each Grand rune is announced in chat as credit, so the shared layer keeps speaking after the milestone. It's announced in chat with the carver's name (*"<you> carved the world's first Looting II"*), and the table shows **First carvings 12/46** (every rank from II up of the 31 runes, less the 22 Grand ranks; rank I costs too little to matter). Runes from breaking down books or found gear never count as a first carving, so a fish farm can't claim the credit. So every carver, including the slowest, makes the world's runes cheaper, and the count is a collection the whole server fills together.

### Gifting: runes are shareable, Gilding is yours

- **Every rune can be given away, Grand ones included.** The personal far-off sink lives in Gilding: every item that holds a Grand rune needs its own Gilded socket, one far-off material per socket, so a gifted Grand rune still costs its receiver a trip.
- **That's the person-to-person half of sharing:** a carver's spare runes equip a friend straight away, with the carver's name on them, and passing runes around through a shared chest is accepted catch-up among friends. The operation log records every rune that ends up with someone other than its carver; if one rune starts working as a price for everything, or a communal chest of Grand runes replaces carving, the prepared lever is that Grand runes bind to whoever carved them.

### Books and found gear

- **Putting an enchanted book in the table breaks it down** (a book with only Power and Yield enchantments comes back as a plain Book): its Power and Yield enchantments become runes, and the book comes back holding only its Convenience enchantments, which can be applied from it for free (using the book up) or kept. Random books are at most level II ([shared rule](README.md#random-sources-stay-below-the-ladder)), so a fish farm hands out rank I and II runes, never Grand ones. That skips the tier 1 and 2 carving sink at those ranks, and so do mob-farm gear and smiths' equipment trades (below); the sink floor tests it, and if it fails, the prepared lever is that fished books, mob gear and traded gear break down into Convenience only.
- **A rune pulled from found gear** is clamped to vanilla's maximum level, and to rank II unless the mod's own carving made it (the clamp is logged), so commands, other mods or a missed random source can't mint over-max or Grand runes.
- **Enchanted loot gear** keeps working as found; its Power and Yield enchantments count as runes in its sockets and can be pulled out at the table like any others. If it has more than its socket count, you break it down before adding anything; the item comes back clean, so nothing is duplicated.

### Socketing, swapping and the Rune Pouch

- **Put an item in the table, or select a worn piece (so Curse of Binding armour isn't stuck; the server re-checks the worn slot on every action), and its sockets appear as slots.** Drag runes in from your inventory; drag them out to get them back intact. Free, instant, unlimited.
- **Rune Pouch:** a bundle that holds only runes (Leather and String), up to 64 (each rune weighs 1/64 in the pouch instead of a non-stackable item's full bundle weight). It shows how many Grand runes you own and how many of the 31 Power and Yield enchantments you have a rune for. **A dropped pouch can't burn or be blown up** (it's its own item with its own contents component, so vanilla bundles are unchanged, and a `damage_resistant` component pointing at a mod tag that includes `#is_fire` and `#is_explosion`; the runes-only filter hooks both ways it takes items in), so a collection isn't lost to one lava pool (cactus or the void still destroy it, spilling its runes like a vanilla bundle), and it never despawns (the same small hook as Voidtouched; deliberate, since the pouch holds only runes and Voidtouched still matters for gear). Its tooltip notes that socketed runes drop with their gear. Otherwise death is vanilla, as in A and B (a socketed Grand rune lost in lava takes its Gilding with it, and the socket screen says so); Curse of Vanishing on found gear takes its runes with it, and the socket screen warns before you socket into a cursed item.
- **Concurrency:** the table holds nothing between uses; each player works in their own copy of the screen.
- **Under the hood:** socketed runes are written into the item's ordinary `enchantments` component, so every vanilla path (Fortune's drops, glint, predicates, tooltips) works with no extra hooks. A small `sockets` component records the metadata : which sockets are Gilded, which enchantments are runes, and who carved them (the socket count comes from the item type). Convenience enchantments are ordinary enchantments not listed as runes. If commands or other mods change the enchantments without updating `sockets`, the table reconciles them when it reads the item: a Convenience enchantment with no entry is just Convenience, and a Power or Yield one with no entry counts as a found-gear rune. The vanilla grindstone refuses items with any runes, found gear included (breaking it down at the table is the way to strip it), so a misclick can't destroy them.

### Villagers

- Librarians sell **Convenience books** (applied from the book for free) plus Bookshelves, and **no** Power or Yield books ([shared rule](README.md#convenience-costs-no-budget)). Smiths' equipment trades roll at most level II.

## Design lenses

- **MDA.** Mechanics: carved runes, a socket count, Gilded sockets, free swapping, free-standing Convenience, giftable runes. Dynamics: players build a rune collection, plan socket layouts, swap loadouts for fights, and hand down spare runes. Aesthetics: **collection** (a pouch of runes is visible progress) and **tinkering** (slotting pieces in and out).
- **Lodestar.** Every first carving of a middle rank halves its catalysts for the whole world, with the carver's name in chat; a carver's spare runes equip a friend directly; Gilding sends players to the places last season skipped, each with its own effect. Pillars: always a next horizon (the collection grows sideways, Gilding grows per item); Convenience is cheap (no socket); Progress is shared (first carvings, and gifting).
- **Borrowed.** Monster Hunter decorations work because the slot limit is the build and choosing jewels is the fun; that holds here. World's randomly dropped jewels were hated and Rise moved them to crafting; ours are carved. Hollow Knight charms work because the collection is a visible goal; the pouch counter is that goal.
- **Economy.** Taps: mining, mobs, structures, loot and fishing books (rank I and II runes). Converter: carving and ranking. Inventory: the rune collection and sockets. Drain: catalysts consumed once per rune (one per item that uses it) and Gilding. Gilding keeps the top of the sink per item, whoever carved the rune. Exploit checks: random books cap at rank II; breaking down returns items clean; the grindstone refuses socketed items.
- **Snowball.** Looting and Fortune compound yield; their Grand ranks need Echo Shards and a Gilded socket, which slows the loop. Players who fall behind catch up through gifted runes. Getting ahead is never worse.
- **Optimise the fun out.** Most efficient boring play: carve one set of Grand runes and stop. Every item has fewer sockets than useful runes, Grand runes need Gilded sockets, and each socket on an item needs a different far-off material.
- **Presence.** Runes are the Season's main item family and get a strong visual identity. Convenience is a line on the same screen, matching its small importance.
- **Skill floor and ceiling.** Floor: the Runes tab lists every recipe for the item you're holding; sockets show what fits; solid gear never needs Gilding; swapping is free, so trying things is safe. Ceiling: socket layouts for specific fights, and the trips for Gilding.

## Playtest prediction

Written before the playtest. The measures are the [shared ones](README.md#measuring-the-playtest).

- **Reuse** ([measure 4](README.md#measuring-the-playtest)): at least **40%** of Grand runes are, at some point, socketed into a second item or given to another player; that's C's identity measured directly. Secondary reading: tier 3 and 4 Grand-rune spend on later items, at undiscounted prices, is at most 60% of the first item's.
- **Primary:** players who reach their milestone average **3 or more Gildings** each, and at least **30%** of their sessions after the milestone include a paid enchanting operation (carving or Gilding; swaps reported separately).
- **Collection:** each regular player carves at least **4 new runes** (any rank) in days 7 to 14, and median carving spend in week 2, valued at undiscounted prices, is at least **half** of week 1; if not, the collection bet has plateaued.
- **Horizon estimate:** for the fastest player, about **25 hours of play after full Netherite** to carve and Gild for a sword, a bow and a full armour set (around twelve Gildings: three on the sword for Grand Sharpness, Looting and Sweeping Edge, one on the bow, two per armour piece from Echo Shards, Hearts of the Sea, Shulker Shells and Dragon's Breath, with no Heavy Cores, plus the Grand runes' tier 3 and 4 catalysts), plus carving for second loadouts.
- **Sink floor:** at least **1 stack of tier 1 and 2 materials per player per week** on carving in weeks 1 and 2, with a week-3 reading so a late collapse is visible. Spend is expected to fall once collections exist; Gilding and Grand runes then carry the tier 3 and 4 spend.
- At least **4 of 6** players swap runes between items or loadouts at least once a week.
- Carved runes end up with at least **4 different players** other than their carvers, given by at least **3 different carvers**, and the world's first carvings come from at least **4 different players**. Gifting and first carvings (above) are measured separately.

## Risks

- **The collection plateaus.** Runes are reused, so once a player has one of each useful rune per item they wear, carving slows. Gilding and gifting are the long tail; the sink floor above tests whether it's enough. **If it fails, the first lever keeps C's identity: Grand catalysts cost more.** Only if that isn't enough, **the last-resort lever is that a Grand rune binds to the first item it's socketed in**, so each new item needs its own Grand runes. Pulling it makes C's top end consumed like A's and gives up C's identity, so pull it only after the week-2 collection reading.
- **First carvings may mostly go to the fastest player.** The discount still reaches everyone, so it's catch-up either way; only the chat credit is a race. The measure (first carvings from at least 4 players) shows how widely it spreads; if it fails, the prepared lever is that the second and third different carvers of a rank each lower its cost a further step (to a floor of a quarter), so late and slower players still move the world forward.
- **Runes as currency.** Giftable runes could become money (the Path of Exile orb problem in [gear systems](../../../research/gear-systems-in-games.md)). The log and the binding lever are the safeguard.
- **Protection IV may become every armour piece's default Grand rune.** The log watches it; if almost every piece carries it, Protection IV's catalyst cost is the lever, as in A.

## Build size

Large, between A and B (most client work). The rune item with components and tinted models; the sockets component and tooltips; the table screen (Convenience, runes, socketing, Gilding, breaking down); the Rune Pouch (it can't reuse the vanilla bundle item, so it needs its own contents component, tooltip and selection UI, both stack-on interactions, the runes-only filter, 64 rune capacity, `damage_resistant` and the no-despawn hook); first carvings in world saved data with chat announcements; the rune model's per-family tint via an item-model `select` on the rune component; the five shared upgrade effects; level clamp for random sources; Mending and Unbreaking removal; four new enchantments as JSON; librarian and cartographer trade data; operation and rune-transfer log; game tests.
