# Research: Vanilla Tweaks "More Mob Heads"

This note covers the More Mob Heads data pack from Vanilla Tweaks (vanillatweaks.net, run by Xisumavoid's team). It was gathered in November 2026 by downloading the pack for Minecraft 26.3 through the site's own picker and reading every file in it. Statements marked *(source)* were read directly from the pack's files. Statements marked *(unverified)* are inferences or come from one secondary source only.

- Picker: https://vanillatweaks.net/picker/datapacks/ (version 26.3, category "Decorative/Cosmetic")
- Pack metadata: https://vanillatweaks.net/assets/resources/json/26.3/dpcategories.json. This gives `"name": "more mob heads"`, `"version": "2.20.1"` and the description "Adds a chance to receive a mob's head upon killing it."
- Download: the picker POSTs `packs={"decorative-cosmetic":["more mob heads"]}&version=26.3` to `https://vanillatweaks.net/assets/server/zipdatapacks.php` and gets back a one-off link under `/download/`. The zip holds `more mob heads v2.20.1 (MC 26.3).zip`.
- There's no official GitHub repo. We used the site's own zip rather than mirrors.

## Pack layout

- `pack.mcmeta` sets `min_format` and `max_format` to `[121, 0]`, so the pack only loads on exactly the 26.3 data format. It has one overlay, `overlay_107_1`. *(source)*
- The pack uses **four namespaces** *(source)*:

| Path | Files | What it is |
|---|---|---|
| `data/minecraft/loot_table/entities/<mob>.json` | 83, plus 16 under `sheep/` | **Overrides** of the vanilla entity tables. Each is now a stub. |
| `data/vanilla/loot_table/entities/<mob>.json` | 92, plus 16 under `sheep/` | Copies of the vanilla drop tables, moved to a `vanilla:` namespace that all Vanilla Tweaks packs share. |
| `data/more_mob_heads/loot_table/entities/<mob>.json` | 83, plus 18 under `sheep/` | **One head-only loot table per mob.** |
| `data/vanillatweaks/tags/loot_table/entities/<mob>.json` and `data/more_mob_heads/tags/loot_table/entities/<mob>.json` | 83 each | Loot table tags that put the two together. |

- Other files:
  - `data/minecraft/loot_table/blocks/player_head.json`: when a placed head is broken, it copies all block-entity components back onto the item, so the name and note-block sound survive placing and breaking. *(source)* We haven't checked whether vanilla 26.3 already does this. *(unverified)*
  - `data/vanillatweaks/advancement/more_mob_heads.json`: a hidden advancement that only puts the pack in the shared Vanilla Tweaks advancement tab. *(source)*
  - An empty `double_shulker_shells:shulker_shell` item tag, used to coordinate with another Vanilla Tweaks pack. *(source)*
  - The `overlay_107_1` overlay adds a `sulfur_cube` head and alternate `vanilla:` tables. *(source)* Its format range is also 121, so it seems to apply always on 26.3. *(unverified why it's a separate overlay)*

## 1. How it adds heads

### The override chain

Every overridden vanilla table is replaced with a single-entry stub that points at a **loot table tag** *(source: `data/minecraft/loot_table/entities/cow.json`)*:

```json
{ "type": "minecraft:entity",
  "pools": [{ "rolls": 1,
    "entries": [{ "type": "minecraft:loot_table", "value": "#vanillatweaks:entities/cow" }] }] }
```

The tag lists the vanilla drops and then a second tag *(source)*:

```json
// vanillatweaks:tags/loot_table/entities/cow.json
{ "values": [ { "id": "vanilla:entities/cow", "required": false },
              { "id": "#more_mob_heads:entities/cow", "required": false } ] }
// more_mob_heads:tags/loot_table/entities/cow.json
{ "values": [ { "id": "more_mob_heads:entities/cow", "required": false } ] }
```

- So a cow kill rolls `minecraft:entities/cow`, which goes through the tag to `vanilla:entities/cow` (the normal drops) and then to `more_mob_heads:entities/cow` (the head).
- The two-level tag lets other Vanilla Tweaks packs add their own entries to the same mob without overwriting each other.
- Loot table tags, and a `loot_table` entry that takes a `#tag`, appear to be a 26.x data pack feature. The pack depends on it, but we didn't find it in the changelogs we searched. *(unverified)*
- **Not overridden:** zombie, skeleton, wither skeleton, piglin, ender dragon, giant, player, armor stand and mannequin. These are the mobs that already have a vanilla head, or that have no head. Creeper *is* overridden, but only to add a **Charged Creeper Head** when the creeper itself is powered (`entity_properties` nbt `{powered:1b}`). *(source)*

### The head entry

A typical head table looks like this *(source: `more_mob_heads:entities/cow`)*:

```json
{ "type": "minecraft:entity",
  "pools": [{ "rolls": 1,
    "entries": [{ "type": "item", "name": "minecraft:player_head",
      "modifier": [
        { "type": "set_components", "components": {
            "minecraft:note_block_sound": "minecraft:entity.cow.ambient",
            "minecraft:profile": { "properties": [{ "name": "textures", "value": "eyJ0ZXh0dXJlcyI6..." }] },
            "minecraft:item_name": "Temperate Cow Head" } },
        { "type": "set_components", "components": { ..., "minecraft:item_name": "Cold Cow Head" },
          "condition": { "type": "minecraft:all_of", "terms": [{ "type": "minecraft:entity_properties", "entity": "this",
            "predicate": { "components": { "minecraft:cow/variant": "minecraft:cold" } } }] } },
        { ... "Warm Cow Head" ... }
      ] }],
    "condition": { "type": "minecraft:all_of", "terms": [
      { "type": "killed_by_player" },
      { "type": "random_chance_with_enchanted_bonus", "enchantment": "minecraft:looting",
        "unenchanted_chance": 0.01,
        "enchanted_chance": { "type": "minecraft:linear", "base": 0.011, "per_level_above_first": 0.001 } } ] } }] }
```

- **Item.** Always `minecraft:player_head`, never a vanilla skull. *(source)*
- **Components.** Each head gets three components and nothing else *(source: 212 each across the namespace)*:
  - `minecraft:profile` with only a `textures` property. The value is a base64 JSON that points to a `textures.minecraft.net` skin. There's no name or UUID.
  - `minecraft:item_name`, a plain string such as "Temperate Cow Head" or "Nautilus Shell". It is *not* `custom_name`, so the head isn't italic and can still be renamed.
  - `minecraft:note_block_sound`, the mob's ambient sound, so the head plays it on a note block.
- **Conditions.** The pack (or a single entry) is gated by `killed_by_player` plus `random_chance_with_enchanted_bonus` on `minecraft:looting`. The gate usually sits on the pool, and on the entry or an `alternatives` parent when variants have different chances. *(source)*
- **Format.** The 26.3 format uses the singular `"condition"` and `"modifier"` keys, and so do the `vanilla:` copies. *(source)*

### Drop chances (base, then with Looting)

The chances come from `unenchanted_chance` and the `linear` enchanted chance *(source, all 83 tables)*:

| Group | Base chance | Example mobs |
|---|---|---|
| Always on a player kill | 100% | Elder Guardian, Warden, Wither, Sniffer, Camel, Camel Husk, Wandering Trader, Zombie Horse, Charged Creeper (when powered) |
| High | 20–50% | Dolphin 33%, Cat 33%, Evoker, Illusioner and Ravager 25%, Parrot 25%, Horse and Panda 27%, Llama 24%, Donkey, Mule, Polar Bear, Zoglin, Skeleton Horse and Allay 20%, Zombie Villager 50%, Snowy Wolf 50%, other wolves 20% |
| Medium | 5–15% | Pufferfish 15%, Strider and jeb_ sheep 10%, most fish, Phantom, Vex, Turtle, Breeze, Creaking, Armadillo and Endermite 10%, Ghast 6.25%, Stray, Bogged, Husk and Parched 6%, Drowned, Squid, Glow Squid, Shulker, Iron Golem, Vindicator, Copper Golem and Silverfish 5% |
| Low | 0.5–3% | Hoglin 3%, Pillager 2.5%, coloured sheep 1.75%, Cow, Pig and Chicken 1%, Blaze, Enderman, Spider, Cave Spider, Slime, Magma Cube, Guardian, Witch and Zombified Piglin 0.5% |

Looting adds very little for most mobs. Cow, for example, goes from 1% to 1.1% at Looting I and 1.3% at Looting III.

### Variants

Variants are chosen in one of three ways *(source)*:

- **Conditional `set_components`** on a single entry, where the last matching modifier wins. Used for cow, pig and chicken (temperate, cold, warm), copper golem (4 oxidation stages) and zombie nautilus.
- **An `alternatives` entry** with one child per variant, each gated by `entity_properties`. Children check components such as `minecraft:cat/variant`, `minecraft:fox/variant`, `minecraft:rabbit/variant` and `minecraft:wolf/variant`, or nbt such as `{VillagerData:{profession:"minecraft:librarian"}}`. Used for cat (11 types), fox, frog, goat, mooshroom, panda, parrot, horse, llama, trader llama, axolotl, bee, villager (15 professions), zombie villager and rabbit. Wolf has a normal and an "Angry" version of each of its 9 variants, using `{anger_end_time:-1L}`.
- **Nested loot tables.** `more_mob_heads:entities/sheep` holds an `alternatives` of `minecraft:loot_table` entries, one per `minecraft:sheep/color`, each pointing at `more_mob_heads:entities/sheep/<colour>`. A separate pool rolls `sheep/jeb_` (10%). *(source)*

Named easter eggs *(source)*:

- **jeb_ sheep** use an `entity_properties` nbt `CustomName` match.
- **Toast rabbit** uses a trick. A pool-level `set_name` with `"name": {"selector": "@s[name=Toast]"}` resolves to an empty name unless the rabbit is called Toast. A `filtered` function then sets the count to 0 when `custom_name` is empty, and the name is stripped afterwards.
- **"The Killer Bunny"** rabbit variant `evil`.
- **Wither** also rolls a 50% pool for a "Wither Projectile Head" and a blue version of it.

## 2. Can a mod find "the head this mob drops"?

Yes, and easily:

- **By path.** The head table is always `more_mob_heads:entities/<entity path>`, the same path as the mob's vanilla loot table: `entities/cow`, `entities/sheep`, `entities/wolf`. *(source)* A Fabric mod can look it up with `server.reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath("more_mob_heads", "entities/" + type.path)))` and check that the result isn't `LootTable.EMPTY`. *(unverified exact 26.3 method names)*
- **It's its own table.** It isn't one pool inside the vanilla table, so it can be rolled on its own with the kill's `LootContext`. Variant selection works automatically because the conditions read `this`.
- **By tag.** `#more_mob_heads:entities/<mob>` also exists if a mod wants to go through the pack's own aggregation point. *(source)*
- **Dropped heads.** A dropped pack head is a `player_head` that has `item_name` and a texture-only `profile`. There's no custom tag or marker component, so "is this a More Mob Heads head" can only be guessed: a player head with a textures property and no profile name. *(source)*

## 3. Is it reusable?

- **Partly.** It is one loot table per mob in its own namespace, so it can be called from anywhere. It doesn't ship an item modifier or a function library.
- **The chance is built in.** Each table rolls its own chance. Calling the table once gives the pack's normal 0.5–33% chance, not "the head". To force a head you have to bypass the `random_chance_with_enchanted_bonus` condition (see below).
- **Player kills only.** Every table also needs `killed_by_player`, so kills by tamed wolves, by Thorns-only damage with no player attribution, or by other mobs never produce a head.
- **Licensing.** Vanilla Tweaks' terms on redistributing or copying pack contents (the texture choices and tables) weren't checked. Don't copy the base64 texture values into our mod without permission. *(unverified)*

## Recommendation for our Beheading enchantment

Beheading is currently implemented in `mod/.../enchanting/ToolAbilities.java` (`behead`). It uses `LootTableEvents.MODIFY_DROPS` with a 10% chance per level, and its map only covers zombie, skeleton, creeper and piglin. Wither skeleton and ender dragon are missing even though they have vanilla heads.

### (a) Without the pack

1. **Vanilla heads first.** Map zombie → Zombie Head, skeleton → Skeleton Skull, **wither skeleton → Wither Skeleton Skull**, creeper → Creeper Head, piglin → Piglin Head, **ender dragon → Dragon Head**, player → that player's head (a `player_head` whose `profile` is the victim's game profile).
2. **Everything else: skip.** Recommended for Season 1. It's honest, needs no art, and the enchantment tooltip can say "mobs with a head".
3. **Optional later: our own tables.** We could ship `superawesomecraft:heads/<mob>` loot tables that use exactly the pack's shape (`player_head` plus `profile` textures, `item_name` and `note_block_sound`), filled in mob by mob with textures we choose. Using the same shape keeps (b) simple. Don't copy the pack's texture values (see Licensing).

### (b) With the pack installed

1. **Detect it per mob.** On a Beheading proc, look up `more_mob_heads:entities/<path>`. If it's present and non-empty, use it. Otherwise use (a).
2. **Force the roll, don't just re-roll.**
   - A plain re-roll only gives the pack's own chance again. For a 0.5% Enderman that makes Beheading near-useless, and the strength of the enchantment would vary 60× between mobs.
   - Instead, roll the head table with a thread-local "force" flag set. A small mixin makes `LootItemRandomChanceWithEnchantedBonusCondition` (and `LootItemRandomChanceCondition`) return true while the flag is set.
   - Name and variant conditions still apply, so you get the right cat, sheep colour or wolf, jeb_ only when the sheep is named jeb_, and Toast only for Toast. Wither would give both of its pools, which is acceptable.
   - Keep `killed_by_player` as is. Beheading is a player tool anyway.
3. **No double heads.** If the normal drops already contain a `player_head` or vanilla head from the pack's own roll, skip the Beheading bonus.
4. **Fallback: bounded re-rolls.** If we don't want a mixin, re-roll the table up to N times (for example 300) until it yields an item. It's simple, but low-chance mobs may still miss, so treat it as a fallback only.
5. **Avoid triple-firing.** With the pack, one kill runs `minecraft:entities/cow` → `vanilla:entities/cow` → `more_mob_heads:entities/cow`. Check whether Fabric's `MODIFY_DROPS` fires for nested tables too. *(unverified)* If it does, guard `behead` so it only runs when the table being rolled is the victim's own `getDefaultLootTable()`, or Beheading could roll several times per kill.
6. **Raising the pack's chance instead** (for example, adding Beheading as a second enchantment on its conditions through `LootTableEvents.MODIFY`) would mean rewriting conditions in 83 nested tables. That's fragile and gives uneven results, so it isn't recommended.

### Design notes

- With the pack, plain kills already drop heads at 0.5–100%. Beheading should be a flat, predictable chance per level on top of that, which (b)2 gives. Bosses (Wither, Warden, Elder Guardian) already drop 100% with the pack, so Beheading adds nothing there.
- The pack only loads on data format 121 (26.3). Each Minecraft update needs a new download, so our mod must never *require* it.
