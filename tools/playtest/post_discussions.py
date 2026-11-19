import json, subprocess

REPO_ID = "R_kgDOVyfVpw"
CAT = {"Announcements": "DIC_kwDOVyfVp84DLYta", "General": "DIC_kwDOVyfVp84DLYtb", "Ideas": "DIC_kwDOVyfVp84DLYtd"}
WIKI = "https://github.com/SuperAwesomeStuff/SuperAwesomeCraft/wiki"

THREADS = [
    ("Announcements", "👋 Welcome! What this is and how to chip in", f"""We're building **Super Awesome Craft**, a big rework of Minecraft for our server. It still plays like Minecraft, but with real long-term goals: **Eras** with dungeons to clear, **towns that villagers move into**, **raids**, comfort gear that's cheap so builders aren't stuck grinding, and a lot more.

**Nothing is built yet.** We're still deciding what it should be, and that's where you come in.

- 📖 The easy-to-read version lives in the [wiki]({WIKI}). Start with [The Big Idea]({WIKI}/The-Big-Idea).
- 💬 Reply to any of the threads here, start your own in **Ideas**, and 👍 the stuff you like.
- 🤐 Not into forums? Just say it on Discord or in voice and it'll get written up.

Short replies are totally fine. "I hated X last server" is useful."""),

    ("Ideas", "What should the Eras be about?", f"""The game will be split into a few **Eras**. Each one ends with a dungeon you clear to unlock the next area, materials and gear. ([How Eras work]({WIKI}/Eras))

The big missing piece is the **theme**: *why* do the Eras exist in the world?

Some directions to get things going (or ignore them and pitch your own):
- 🏚️ **Lost civilizations:** each Era digs into an older, stranger ruined culture.
- 🌍 **Climbing up and down:** surface → deep underground → sky → somewhere weirder.
- 🧭 **Expeditions:** each Era is a new frontier biome or realm you push into.
- 👹 **Something waking up:** a threat that grows as the world progresses.

What would make you *want* to push to the next Era?"""),

    ("Ideas", "What makes a dungeon or fight fun? What makes one annoying?", f"""Big challenges will be **dungeons**: groups of enemies, traps, and a final fight in a room built for it, rather than one-off arena bosses. ([Eras]({WIKI}/Eras))

Tell us about fights you've loved or hated, in Minecraft, mods (Twilight Forest, Aether, whatever) or other games:
- Which fight sticks out as actually fun? Why?
- What makes you groan? (Wither spam? Bullet-sponge bosses? Getting one-shot?)
- Solo or group: what kind of challenge do you want to do together?"""),

    ("General", "Why did you stop playing on past servers?", f"""Honest answers only, no wrong ones. 🙂

Last server lasted about 10 days ([what the data showed]({WIKI}/What-Went-Wrong-Last-Time)). Most of us drifted off either once our gear was maxed out or once we fell behind the group.

- What usually makes *you* stop logging in on a server?
- What's kept you hooked the longest on any server, modded or vanilla?
- What would make you want to come back in week 3?"""),

    ("Ideas", "Long-term projects: what would make them feel worth it?", f"""A lot of us start huge projects: massive builds, tunnels, collections of one of everything, giant storage systems, enormous farms. We want the game to **back those up** instead of them being just for fun on the side.

- What's a project you've started (or always wanted to) on a server?
- What would make it feel *worth it*? Tools to make it faster? It doing something in the world? Others being able to see or use it?
- Would towns that grow as you build them ([Settlements and Raids]({WIKI}/Settlements-and-Raids)) get you building more?"""),
]

MUT = "mutation($r:ID!,$c:ID!,$t:String!,$b:String!){createDiscussion(input:{repositoryId:$r,categoryId:$c,title:$t,body:$b}){discussion{url}}}"
for cat, title, body in THREADS:
    out = subprocess.run(["gh", "api", "graphql", "-f", f"query={MUT}", "-f", f"r={REPO_ID}", "-f", f"c={CAT[cat]}", "-f", f"t={title}", "-f", f"b={body}"],
                         capture_output=True, text=True, encoding="utf8")
    if out.returncode:
        print("FAILED", title, out.stderr); continue
    print(json.loads(out.stdout)["data"]["createDiscussion"]["discussion"]["url"], "|", title)
