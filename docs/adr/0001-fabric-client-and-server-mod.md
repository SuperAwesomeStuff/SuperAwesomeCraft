# Build as a Fabric mod that runs on both client and server

**Status:** Accepted. Supersedes the earlier proposal for a server-only Polymer mod.

Super Awesome Datapacks were pure datapacks. That kept installation easy, but custom interfaces (the FancyUI workaround), faked blocks and entities, and real encounter AI were fragile or out of reach. This project is now a total conversion that uses Minecraft as an engine. It needs Era Gates with designed encounters, new dimensions, Settlements with Villager behavior, custom blocks, items, interfaces, and gear systems (enchanting capacity, tool levels).

We will build a **Fabric mod installed on both the client and the server**. Data-driven content (recipes, loot tables, world generation, trades, tags, advancements) stays as datapack JSON shipped inside the mod. Java code is for behavior that data can't express, and client code is for anything that needs real rendering or screens.

## Considered options

- **Datapack + resource pack:** no client install, but encounter AI, interfaces and new blocks remain hacks. Rejected for the core, though still used for data.
- **Server-only Fabric mod with Polymer:** players join with an unmodified client. Rejected because every rail, cart, screen, dimension sky and tooltip would have to be faked from vanilla assets (for example, the gold rail would need a display-entity overlay, and the travel cart couldn't use client-side path smoothing). Supporting unmodified clients isn't worth that cost for a small group of friends.
- **Client modpack (chosen):** unlimited capability. Players need a launcher profile.

## Consequences

- Development is in Java with Gradle, with separate client and common source sets. Each new Minecraft version needs porting.
- Players install a modpack. Ship it as a Modrinth pack (or similar) so a launcher like Prism or the Modrinth App can install and update it in one click.
- Custom screens, renderers, models, particles and dimension effects are in scope.
- Existing Super Awesome Datapacks content can be ported as data where it still fits the design.
