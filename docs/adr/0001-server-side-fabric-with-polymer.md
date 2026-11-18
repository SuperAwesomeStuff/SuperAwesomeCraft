# Build as a server-side Fabric mod with Polymer

**Status:** Proposed. Confirm that Fabric and Polymer support the target Minecraft version before accepting.

Super Awesome Datapacks were pure datapacks. That kept installation easy, but custom interfaces (the FancyUI workaround), faked blocks and entities, and real encounter AI were fragile or out of reach. This project needs Era Gates with designed encounters, Settlements with Villager behavior, and custom blocks, items and interfaces.

We will build a **server-only Fabric mod using Polymer**, so players join with an unmodified client and receive the resource pack from the server automatically. Data-driven content (recipes, loot tables, world generation, trades, tags, advancements) stays as datapack JSON shipped inside the mod. Java code is only for behavior that data can't express.

## Considered options

- **Datapack + resource pack:** no client install, but encounter AI, interfaces and new blocks remain hacks. Rejected for the core, though still used for data.
- **Client modpack:** unlimited capability, but every player has to install and update a mod launcher, and porting costs more. Rejected unless a feature truly needs client code.

## Consequences

- Development is in Java with Gradle; each new Minecraft version needs porting.
- Visuals depend on Polymer's resource-pack mapping, so client-only features such as custom rendering and shaders are out of scope.
- Existing Super Awesome Datapacks content can be ported as data where it still fits the design.
