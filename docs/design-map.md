# Design map

A rough map of the whole design, so ideas have somewhere to live before they're decided. Each area lists what's **Decided**, what we're **Leaning** toward, loose **Ideas**, and **Open** questions. Ideas here aren't commitments; promote them when the Season that needs them gets designed.

Details for decided items live in [vision.md](vision.md), terms in [GLOSSARY.md](../GLOSSARY.md), and big decisions in [adr/](adr/).

```
SuperAwesomeCraft
├── How we build it ── Seasons, Fabric client+server mod, modpack
├── Progression ────── Eras = dimensions, Strongholds, portals, world unlock
│   ├── Overworld → Nether → Sky dimension → End
│   ├── Strongholds (dungeons, climax fights, rituals)
│   └── Catch-up easing (trades, carrying stuff back)
├── Gear ───────────── Convenience vs Power
│   ├── No XP, durability that never breaks gear
│   ├── Enchanting (slots, starts early)
│   ├── Raw stats (Augments? enchantments? tool tiers?)
│   └── Heart upgrades
├── World ──────────── readable generation, per-player loot, Era-gated blocks
├── Getting around ─── mounts, glider, rails and Travel cart, graves
├── Settlements ────── Villagers, Prosperity, Raids, economy
└── Social ─────────── Collection Log, PvP and minigames
```

## Vision statement

- **Decided:** "Every playstyle pushes the world forward." Pillars: progress is shared by building and giving (no hidden server-wide unlocks); always a next horizon; Convenience is cheap, Power is earned. See [vision.md](vision.md#vision-statement).

## How we build it

- **Decided:** build in Seasons with vanilla as the placeholder for anything not built yet. Fabric mod on client and server ([ADR-0001](adr/0001-fabric-client-and-server-mod.md)), shipped as a one-click modpack.
- **Leaning:** Season 0 is setup (mod skeleton, modpack, dev server, playtest scripts). Season 1 is Gear.
- **Open:** order of the Seasons after Gear. Target hours of play per Era.

## Progression

- **Decided:**
  - One Era per dimension: Overworld → Nether → a new sky dimension → End.
  - Each Era ends at a Stronghold whose climax fight opens the first portal to the next dimension. Later portals are built from that dimension's materials.
  - Progression belongs to the world: once a portal exists, anyone can use it ([ADR-0003](adr/0003-world-progression-through-portals.md)).
  - Several Strongholds of each kind per world; re-fight by finding another or by a ritual.
  - Catch-up easing comes from players carrying things back, not automatic server-wide unlocks.
- **Ideas:**
  - Each Stronghold sits in the dimension before the one it leads to (Nether Stronghold in the Overworld, and so on).
  - The vanilla Overworld Stronghold becomes the Nether Stronghold. Its portal room becomes the climax arena, and something replaces Eyes of Ender for finding it.
  - Built portals need a frame material from their destination, so obsidian frames don't open the Nether. Vanilla Ruined Portals become decoration or hints. Portal linking stays vanilla.
  - Each player's first clear of a kind of Stronghold gives a guaranteed unique reward; re-fights give per-player loot.
  - The End is rebuilt as a real Stronghold-style finale rather than just the Dragon.
  - **Power dip and regrowth** (after Terraria's Hardmode). Each new dimension starts much harder than the last, so top gear from the previous Era feels weak again. Players regain strength through Heart upgrades found by exploring and the new Era's gear, rather than only by rushing the next tier. Unlike Hardmode, already-cleared dimensions don't get harder, so builders' homes stay safe.
  - **Coming back stronger.** Older dimensions stay as dangerous as they were, so going back shows how far you've grown. Revisits are worth making: some resources in older dimensions can only be mined or used with a later Era's tools, like Terraria's Chlorophyte.
  - **The dip comes from new mobs, not buffed vanilla ones.** Making the Nether harder with new mobs or variants, or with a player-side effect that fades as you adapt (for example, through that dimension's Heart upgrades), keeps vanilla mob stats and the farms that depend on them intact.
  - **Arena in the destination.** A Stronghold's portal works before the climax fight, one-way into a fixed arena in the next dimension. It's hard to leave without winning, like the End's platform. The win opens the arena into the dimension. Remote grave recovery (see Getting around) gives players a paid alternative to a corpse run into the arena, so it needs to arrive before the first arena does. This would answer how the arena is protected from cheese. It conflicts with the current plan of an arena built into the Stronghold.
  - **Harder summoned versions** of climax fights for skilled groups, added in a later Season. Rewards are challenge, trophies and cosmetics rather than Power, so they don't widen the gap between players.
- **Open:**
  - What's the theme? Why do the Eras exist?
  - What is the sky dimension like?
  - What does the re-fight ritual cost?
  - What locates a Stronghold?
  - How is a climax arena protected from cheese?

## Gear

Everything in Gear except no XP is preliminary until Season 1 is designed.

- **Decided:**
  - Convenience is cheap and early; Power is earned through Eras.
  - No XP. Mobs keep their hidden XP value for Sculk Catalysts. The anvil only renames, for free.
- **Leaning:**
  - Durability stays but gear never breaks; repair in the crafting grid with its own material, 50% per item. Unbreaking stays, Mending is gone.
  - Enchanting with slots, starting early (Table: Book, 2 Lapis, 4 Amethyst Blocks). Slot count from material, one slot per enchantment at any level, three one-off slot upgrades. Each enchantment has one material that costs more each level (16, then 16 more per level); levels IV and V also need Blaze Rods, except Efficiency. Damage enchantments mix on one weapon and Protections on one armour piece, each in its own slot. About a dozen new enchantments (Excavating, Felling, Reach, Magnetism, Smelting, Harvesting, Step Assist, Beheading, Bane of Illagers, Frost Aspect, Rebounding, Bulwark), each doing something no vanilla one does; Breach also goes on swords, axes and spears. See [Season 1 enchanting](season-1/enchanting/README.md).
- **Ideas:**
  - Every system can be learned in-game without a wiki or recipe viewer (recipe book, tooltips). Ascendant Arcana's Relics fail this.
  - Stat enchantments (Efficiency, Sharpness, Protection, Power) become Augments. Behavior enchantments (Silk Touch, Fortune, Looting, Feather Falling and so on) stay and use Catalysts.
  - A Villager starts trading an Era's materials once a player gives it some.
  - In Season 1, librarians sell Catalysts and new-system books; other trades stay vanilla.
  - From the retired [library design](season-1/enchanting/retired/library.md), in case shared progress needs a boost later:
    - **Library tab:** a chest-like tab in the table showing every Chiseled Bookshelf nearby.
    - **Chipping in:** a table holds a half-paid recipe that anyone can add materials to.
    - **Spares:** duplicate books make something cheaper.
    - **Biome recipes:** some enchantments need a material from one biome (Packed Ice, Prismarine, Soul Soil), so recipes send people exploring.
  - **Endless Convenience scaling:** Convenience enchantments (Efficiency VI and up) keep going with steeply rising costs and shrinking gains, for players who love growth with no cap. Power stays capped.
  - More enchantments from the [candidate list](research/enchantment-candidates.md) once the playtest shows what gets used: Exchanging (swap blocks in place), Vein Mining (reuses Felling), Night Vision on helmets, Grapple on fishing rods, Cleaving on axes, Bane of Enders for the End Era, Grounding Shot for the sky Era.
  - Vanilla enchantments on more items, under their own names: Looting and Quick Charge on bows, Multishot on bows, Infinity on crossbows, Thorns on shields.
  - Parked: tool levelling (Familiarity, Techniques, Codex, Mastery). Background: [tool levelling](research/tool-levelling.md).
  - Builder's wand and explorer tools.
  - Heart upgrades found while exploring each dimension, like Terraria's Life Crystals. Healing scales with max health.
  - **Parallel materials:** each Era has two or three gear materials, each with its own armor, tools or weapons and a distinct situational bonus rather than bigger numbers. No set is locked behind an activity a player might dislike, and sets aren't built around a playstyle. Instead they give variety within one: a fighter might swap between sets for different fights, a miner for different jobs. Since gear never breaks, owning several sets is itself a goal. This could also answer the raw-stats question: stats come from the material.
- **Open:**
  - Where do raw stats come from? Augments felt like a redundant second system in an Ascendant Arcana playtest. Alternatives: stat enchantments, or stats from each Era's tool tier. See [vision.md](vision.md#gear).
  - How much health does each Heart upgrade give?

## World

- **Decided:**
  - The world stays infinite and random, with no fixed start region.
  - Structures are placed by learnable biome rules and carry environmental storytelling.
  - Generated loot is per player.
  - Each Era's new ores, dungeon walls and structure blocks need that Era's tools. Vanilla building blocks and anything players place never get harder.
- **Open:** which vanilla structures stay, change or go.

## Getting around

- **Decided:**
  - Player movement stays vanilla.
  - Mounts are better: faster breeding with a higher baseline, horses carry chests, donkeys wear armor, and leaves don't block them.
  - A glider-like step between horse and Elytra.
  - Graves never despawn, with remote recovery later.
  - Backpacks and earlier Shulkers.
  - Nether highways stay.
- **Leaning:**
  - Powered rails are crafted from copper, and a stronger gold rail is added.
  - A Travel cart beside the unchanged vanilla minecart. Roughly 16 m/s on powered rails and 24+ on gold rails.
- **Open:**
  - What is the glider, and how do you get it?
  - Recall or teleport items?

## Settlements

- **Leaning:**
  - Villagers move into player-built Settlements.
  - Prosperity unlocks trades and services.
  - Raids scale with Prosperity and never break blocks.
  - No single universal currency.
- **Open:**
  - How is Prosperity measured without dictating how people build?
  - Are Raids opt-in?
  - What replaces Emeralds?
  - What are the limits on buyer income?

## Social

- **Ideas:**
  - A Collection Log with rewards for breadth.
  - Opt-in PvP arenas and minigame spaces.
- **Open:** how do PvP spaces work without bothering people who don't want them?
