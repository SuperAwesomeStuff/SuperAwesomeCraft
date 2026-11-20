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
│   ├── No XP, no durability
│   ├── Enchanting (Catalysts, Capacity, library)
│   ├── Raw stats (Augments? enchantments? tool tiers?)
│   ├── Tool levelling (Familiarity, Techniques, Codex, Mastery)
│   └── Heart upgrades
├── World ──────────── readable generation, per-player loot, Era-gated blocks
├── Getting around ─── mounts, glider, rails and Travel cart, graves
├── Settlements ────── Villagers, Prosperity, Raids, economy
└── Social ─────────── Collection Log, PvP and minigames
```

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
  - Catch-up easing: Villager trades for an Era's materials appear once the world unlocks it.
- **Ideas:**
  - Each Stronghold sits in the dimension before the one it leads to (Nether Stronghold in the Overworld, and so on).
  - The vanilla Overworld Stronghold becomes the Nether Stronghold. Its portal room becomes the climax arena, and something replaces Eyes of Ender for finding it.
  - Built portals need a frame material from their destination, so obsidian frames don't open the Nether. Vanilla Ruined Portals become decoration or hints. Portal linking stays vanilla.
  - Each player's first clear of a kind of Stronghold gives a guaranteed unique reward; re-fights give per-player loot.
  - The End is rebuilt as a real Stronghold-style finale rather than just the Dragon.
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
  - No durability. Mending and Unbreaking are gone.
  - Tools level instead of a separate skills system: Familiarity (cap 5) unlocks Technique slots; Codex and Mastery are per player. Weapons and armor don't level.
  - Enchanting after Ascendant Arcana: fixed Catalyst recipes per level, a Capacity budget per item, and a library of Chiseled Bookshelves powering the table.
- **Ideas:**
  - Every system can be learned in-game without a wiki or recipe viewer (recipe book, tooltips). Ascendant Arcana's Relics fail this.
  - Stat enchantments (Efficiency, Sharpness, Protection, Power) become Augments. Behavior enchantments (Silk Touch, Fortune, Looting, Feather Falling and so on) stay and use Catalysts.
  - Library power unlocks rarer enchantments; treasure enchantments need their book stored in the library. Books come from loot, librarians and fishing.
  - In Season 1, librarians sell Catalysts and new-system books; other trades stay vanilla.
  - Familiarity comes from firsts (each new block type, biome or dimension), a daily allowance of normal use, and milestones. Mining player-placed blocks gives nothing.
  - Reaching a Technique's level records it in your Codex; any tool of that type with a free slot can then use it.
  - Builder's wand and explorer tools as levelled tools of their own.
  - Heart upgrades found while exploring each dimension, like Terraria's Life Crystals. Healing scales with max health.
- **Open:**
  - Where do raw stats come from? Augments felt like a redundant second system in an Ascendant Arcana playtest. Alternatives: stat enchantments under Capacity, or stats from each Era's tool tier. See [vision.md](vision.md#gear).
  - What replaces durability and XP as sinks for ore and time?
  - What are the Techniques per tool type?
  - What are the Capacity numbers per material?
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
