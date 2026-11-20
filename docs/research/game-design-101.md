# Research: Game Design 101 (Game Maker's Toolkit)

The main lessons from Game Maker's Toolkit's 11-video **Game Design 101** playlist by Mark Brown, sorted by theme, followed by what they mean for Super Awesome Craft. Gathered November 2026 from the videos' English captions. Quotes come from those captions. The developer anecdotes are as GMTK tells them; they weren't checked against the original interviews or GDC talks.

Playlist: <https://www.youtube.com/playlist?list=PLc38fcMFcV_vToz9Nvc_YQTNH8hkIQ2uC>

| # | Video | Main idea |
|---|---|---|
| 1 | [10 Game Design Lessons from 10 Years of GMTK](https://www.youtube.com/watch?v=Cm2_drGLGbc) | Ten lessons; an overview of the whole series |
| 2 | [Valve's "Secret Weapon"](https://www.youtube.com/watch?v=9Yomqk0C6kE) | Playtesting as an experiment |
| 3 | [How To Think Like A Game Designer](https://www.youtube.com/watch?v=iIOIT3dCy5w) | MDA and the vision statement |
| 4 | [How Game Designers Solved These 11 Problems](https://www.youtube.com/watch?v=rJZyPdYIbZI) | Ways to solve design problems |
| 5 | [How Video Game Economies are Designed](https://www.youtube.com/watch?v=Zrf1cou_yVo) | Taps, inventories, converters, drains, traders |
| 6 | [How to Get a Job as a Game Designer](https://www.youtube.com/watch?v=PMXf0e8n2Oc) | Careers; mostly not relevant here |
| 7 | [Who Gets to be Awesome in Games?](https://www.youtube.com/watch?v=XSvclSkmdyY) | Skill floor and ceiling; serving players of every skill level |
| 8 | [The Games That Designed Themselves](https://www.youtube.com/watch?v=kMDe7_YwVKI) | Follow the fun, fail fast |
| 9 | [How Games Use Feedback Loops](https://www.youtube.com/watch?v=H4kbJObhcHw) | Positive and negative loops; snowballing |
| 10 | [How Game Designers Protect Players From Themselves](https://www.youtube.com/watch?v=7L8vAGGitr8) | Encourage what you want instead of punishing what you don't |
| 11 | [The Last Guardian and the Language of Games](https://www.youtube.com/watch?v=Qot5_rMB8Jc) | Mechanics as the way a game says things |

## 1. Mechanics create the experience

**The experience comes from the mechanics,** not the plot, graphics or setting. Far Cry 2 and Far Cry 4 have nearly the same premise, but Far Cry 4 is a power fantasy (lots of ammo, enemy tracking, camps that stay cleared) and Far Cry 2 is harrowing (guns jam, cars break down, malaria, long walks back from death). To understand why a game feels a certain way, ask how its mechanics produce that feeling. *(video 1)*

### MDA: Mechanics → Dynamics → Aesthetics

The MDA framework (Hunicke, LeBlanc and Zubek, 2004) splits a game into three layers that cause each other in order: *(video 3)*

- **Mechanics** happen in the code: rules, systems, stats, what the buttons do.
- **Dynamics** happen in the player's actions: how players behave in response to the mechanics. The term also covers consequences that grow out of the systems.
- **Aesthetics** happen in the player's feelings, not the art style. Name them with strong words like *powerful, tense, crafty, curious, cooperative, flustered*. "Fun" isn't specific enough.

Designers can only change mechanics directly; dynamics and aesthetics follow from them. Example: raise max ammo and players shoot wildly and feel *reckless and unstoppable*. Cut it and players play carefully, hunt for ammo and feel *cautious and afraid*. Breath of the Wild's breakable swords lead to sneaking, ambushes and constantly hunting for weapons, which feels *crafty, underpowered, like an explorer in a decaying world*.

Notes on using MDA:

- **Mechanics interact and can undercut each other.** The Callisto Protocol limits ammo so you feel underpowered, then gives you a one-button stealth kill that makes you feel unstoppable.
- **The feelings you want can change over the course of a game.** A story about growing from weak to strong should be reflected in the mechanics.
- **Aesthetics are subjective.** A score makes one player feel competitive and another feel judged. Time pressure excites some people and stresses others. How a player feels often depends on their skill.
- **Your predictions need playtesting.** Players do unexpected things. Some of those things fit the vision and can stay (flying cars in Rocket League). Others are **degenerate strategies** that work against it: in Alien: Isolation, players dashed to the next checkpoint and died on purpose to escape the alien.

### The vision statement

**A vision is the one feeling or experience the whole game serves.** It's a lodestar for judging every mechanic: does this create the feelings we want, or clash with them? *(video 3)*

- Subnautica: "thrill of the unknown". Resident Evil Village: "struggle to survive", which guided a rebalance when playtesters hated the combat. DOOM (2016): "push forward combat". FTL: "recreate the atmosphere of commanding a starship".
- A vision can be a fantasy (be Batman), a feeling (Flower: relaxation) or an experience (FTL).
- **Habits from other games pull against the vision.** Flower once had levelling, spells, resource management and time limits, and cut all of them because they worked against calm. Jenova Chen: "fun doesn't always help the emotion you want to deliver."
- **Everything should point the same way,** including music, art and animation. Dead Space's first score made players feel heroic instead of scared and had to be redone. Chen: "all the elements have to sing the same notes."

### Mechanics are the language of games

Each medium has its own language. A game's unique language is interaction: rules, mechanics and systems. The Last Guardian shows its central relationship through mechanics. The boy can't fight; Trico can't pass stained-glass eyes until the boy breaks them. That makes them depend on each other. When Trico finally breaks its own established rule to save the boy, the moment hits harder than a cutscene could, because the player understood the rule. *(video 11)*

- **What each character can and can't do in play is the story.** BioShock Infinite says Elizabeth needs protecting while the combat shows her invincible and saving you; the story and the mechanics contradict each other.
- **Meaning sometimes costs fun.** Trico ignoring commands sells it as a living animal, and it frustrates many players. It's a deliberate trade, not a free win.
- **Design by subtraction** (Fumito Ueda): remove everything that doesn't serve the core idea. *(mentioned in video 11)*

## 2. There are no right or wrong mechanics

- **Judge a mechanic only by whether it serves the experience,** not by whether it's trendy or old-fashioned. Lives, leaderboards, fixed cameras, permanent progress in roguelikes: the answer is always "it depends". *(video 1)*
- **Borrow mechanics only after understanding why they work in the original game.** Alien: Isolation started with automatic checkpoints because every game had them, and they made players careless in a horror game. Manual save stations that take a few seconds to activate made "saving become tense". *(video 3)*
- **Treat genres loosely.** Seeing a genre as a checklist of mechanics from one game produces clones (the wave of Soulslikes with renamed bonfires and souls). Mix and drop features freely. *(video 1)*
- **Complex-seeming systems are usually simple underneath.** Good AI exists to create interesting play, not to pass a Turing test. The best answer to a complex problem is whatever gives the player the most interesting experience. *(video 1)*

## 3. Know your audience

- **Every game has a target audience;** tune the mechanics for them. Spider-Man's easy swinging disappointed a hardcore player, but it gave the game's intended casual audience a great power fantasy. *(video 1)*
- **Optional content serves players more hardcore than the target audience.** Mario's tough bonus coins, levels and post-game world; Portal's advanced chambers. *(videos 1, 2)*
- **Assist options can coexist with the intended experience** if you explain how the game is meant to be played before offering them, as Celeste's Assist Mode does. *(video 1)*

### Who gets to be awesome *(video 7)*

There are two ways to deliver a fantasy: **give** it to the player (Arkham, Spider-Man: simple inputs, flashy results, forgiving systems) or make them **earn** it through mastery (DOOM Eternal). An earned fantasy is more satisfying, but it shuts out players who won't or can't reach that skill level. Scott Rigby's "control mastery" is the price of admission before any fun starts.

**Skill floor** is the baseline skill needed to do okay. **Skill ceiling** is the most skill a player can show. Spider-Man: low floor, low ceiling. DOOM Eternal: high floor, high ceiling. **The target is a low floor and a high ceiling** (Bayonetta).

Ways to make players at every skill level feel awesome:

1. **Gameplay and accessibility options, not just difficulty.** Forza's assists reduce how hard driving is without making rivals worse. Difficulty settings rarely change how complex a game is.
2. **Reward mastery without requiring it.** Mashing in Bayonetta looks great; deep combo play is there for those who want it.
3. **Add complexity over time.** Ori starts with jumping and builds up a full moveset across the whole game.
4. **Prepare players for failure through story and framing.** Skate casts you as an amateur, so its learning curve matches the story. DOOM Eternal casts you as an unstoppable god and then has you die to basic demons, which feels wrong.
5. **Multiple ways to win.** Hades' weapons vary in difficulty, and slow permanent upgrades let players win through time instead of skill.

These solutions also **pull players toward growth**: Forza gives less XP with assists on, Hades rewards weapons you haven't used lately. The power fantasy should grow as the player does, with room to improve at every level.

## 4. Economies *(video 5)*

An economy is how resources flow through a system. Anything collectable counts: coins, materials, XP, ammo, health. There are five entities:

| Entity | What it does | Design levers |
|---|---|---|
| **Tap** | Creates resources (mob drops, ore, timers, regenerating health) | **Taps steer behaviour:** put rewards on what you want players doing (unique materials in every biome encourage exploration; DOOM's glory kills drop health). Flow rate sets scarcity. A broken tap is an exploit (The Witcher 3's respawning cows) |
| **Inventory** | Stores resources | A cap forces decisions about what matters right now, and stops hoarding "just in case" |
| **Converter** | Exchanges one resource for another (shops, crafting, levelling up) | Conversion cost × tap output = pacing. **Fewer currencies spent on many things create hard decisions** (Metro Exodus' 2 materials); **many currencies each tied to one use remove them** (Ghost of Tsushima's 13). Many currencies suit exploration or making some conversions rare |
| **Drain** | Permanently removes resources (durability, ammo, hunger, taxes, losing things on death) | Slows power growth (you spend time replacing losses), pushes players to act (hunger), pushes them to vary strategy (breakable weapons), adds risk (Elden Ring's dropped runes, Death Stranding's cargo) |
| **Trader** | An agent with its own stock and prices (Civilization's city-states, The Witcher 3's merchants) | Allows trade routes and arbitrage. Needs a check on runaway profit: supply and demand (Moonlighter's prices drop when you flood the market) or risk (Animal Crossing's turnips) |

**Grinding is a positive loop:** spend output to get stronger at producing output. It can be the fun itself (idle games, roguelites), but when you don't want it there are three fixes:

- **Turn it into a puzzle:** Factorio, Satisfactory and Stardew make efficiency the challenge (layout, bottlenecks, routing) instead of simply piling up.
- **A negative loop:** rising level costs (Elden Ring: about 10 guards for level 10, hundreds for level 50) make farming easy enemies pointless and push players toward bigger risks.
- **A drain:** losses send players back to the tap without making them stronger.

Fixes have side effects. The Witcher 3 stopped cow farming by spawning a level-27 monster, then found high-level players farming that monster's head, and had to patch it again so the monster only spawns once.

## 5. Feedback loops *(video 9)*

- **Positive loops** compound: success leads to more success (Call of Duty killstreaks) and loss leads to more loss (chess pieces, Monopoly). They push a game to a decisive end and prevent stalemates (TF2's shorter respawn for teams holding points), but they create **runaway winners and death spirals**.
- **Negative loops** pull back toward balance: Mario Kart gives the best items to the players at the back. They create comebacks and drama, but can feel unfair to whoever is winning.
- **Persistent games magnify loops.** In XCOM, veterans grow stronger and survive while rookies die and get replaced by more rookies, and there's no easy area to retreat to. Players end up either steamrolling or spiralling.
- **Fix: pair a positive loop with a negative one.** Pyre gives XP even for losing, has no permadeath, and periodically *liberates* your strongest characters for good, which is the goal of the game and tied to the story. The final battles are fought with your weaker units and are the tensest in the game. Civilization offsets snowballing conquest with unhappiness and the cost of a larger empire, and other nations gang up on the leader.
- **A negative loop must never make success the worse choice.** Telling players the game gets harder when they do well teaches good players to make mistakes (which is why Resident Evil 4 never revealed its dynamic difficulty). In Pyre, winning is always better.
- **Other fixes:** **dampen** the loop (Black Ops: killstreak rewards no longer count toward further killstreaks), add a **counter** (Mario Kart 8's horn against the blue shell), or **separate rewards for skill from progression**: Donkey Kong's KONG letters unlock harder bonus levels instead of lives, so skilled players get challenge rather than power they don't need.

## 6. Steering player behaviour *(video 10)*

Sid Meier: designers must "protect the player from themselves". Soren Johnson: "Given the opportunity, players will optimise the fun out of a game." Players take the most reliable path to success even when it's boring: grinding, repeating one tactic, playing slow and safe (XCOM's overwatch creep).

- **Encourage what you want instead of punishing what you don't.** XCOM 2's turn timers worked but were hated, modded out and scaled back. World of Warcraft's beta reduced XP for long sessions; players hated it until it was **flipped** into a rest bonus for time away, with the same numbers. Reward a fast finish instead of punishing a slow one.
- **Encourage through the basic moment-to-moment mechanics.** DOOM's glory kills (no ammo, free health) pull you toward demons. Bloodborne's rally gets health back if you hit back quickly. Burnout fills your boost for driving dangerously. Hyper Light Drifter recharges your gun when you use your sword.
- **Encourage through scores, grades, XP and achievements:** Devil May Cry's style ranks, Tony Hawk's diminishing points for repeated tricks, Hitman's Silent Assassin. You choose exactly which actions earn rewards.
- **Discourage without forbidding.** The goal is to keep a safe tactic useful in the right situations without letting players depend on it. Grenades or breakable cover rather than removing cover; making Batman weak against guns rather than failing you the moment you're spotted. Instant failure throws away exciting moments like being spotted and getting away.
- **Give each system a presence proportional to how much it matters.** Mark of the Ninja's deep combat system told players fighting was important, so they abandoned stealth. Simplifying combat fixed that.

## 7. Solving design problems *(video 4)*

**First find the real problem.**

- **Look for the root cause behind the reported symptom.** Dying Light's director asked for more weapon durability. The real problem was that players killed only a few zombies per weapon, so the designer lowered zombie health instead, which avoided disturbing the economy.
- **Make sure everyone agrees on what the problem is.** Astroneer's designers argued over whether crafting was "too simple" or "too complex". It was both: the overall loop was shallow and the individual machines were fiddly. Seeing that let them fix both together.

**Then try these approaches:**

1. **Iterate quickly.** Diablo 3 tried weakening potions, then healing while unseen, then healing after 3 seconds without damage (which made players run away). Each failure taught them something, and they arrived at health orbs dropped by enemies. Build ideas you don't expect to ship, because they teach you about the problem.
2. **Identify the levers.** Halo 3's overpowered sniper: range, damage, accuracy and headshots *define* a sniper rifle, so those levers were off the table. Time between shots went from 0.5s to 0.7s. Sometimes the lever you've ruled out really is the answer.
3. **Make big changes.** Sid Meier halved Civilization's map a month before launch: "double it, or cut it in half." Tiny 5% tweaks may never reach the answer before time runs out.
4. **Flip it on its head.** Shovel Knight's pay-to-save checkpoints punished beginners. Flipped, saving is free and automatic, and experts can break checkpoints for money.
5. **Fix it somewhere else.** The Last of Us moved upgrades out of the menu into upgrade benches in the world, so players saved up, considered options and upgraded the weapons they actually liked.
6. **Solve several problems at once.** Miyamoto: "A good idea is something that does not solve just one single problem, but rather can solve multiple problems at once." New Super Mario Bros. Wii's bubble fixed waiting after death *and* let weaker players hide in a bubble through hard sections.
7. **Study how different players behave.** Gears of War: experts land perfect reloads; novices empty the whole clip. Secretly stronger last bullets ("magic bullets") gave novices a matching boost.

**Then check the solution:**

- **Second-order effects:** nerfing Rainbow Six Siege's shotgun hurt defenders, so rounds were shortened to compensate.
- **Work within your constraints:** Prey needed a new enemy late with no budget, and the invisible poltergeist that throws objects became a favourite. "Good designers understand how to solve problems within the constraints that they have."
- **Retest without telling testers what changed,** so they aren't biased.

"Very few ideas survive first contact with players." Good designers are the ones who solve problems, not just the ones with ideas.

## 8. Build, test and iterate

### Ideas are worth nothing until built *(videos 1, 8)*

- "Our brains are truly bad video game simulators." Everything seems fun in your head; the flaws only show once it's real.
- **Follow the fun:** build something, then let the game show you where it wants to go. Ape Out began as a stealth game until throwing guards was the best part. Into the Breach was built around its one enemy that telegraphed attacks. Rocket League's aerials started as a side effect of the boost. Devil May Cry's juggling came from an Onimusha bug. Gunpoint's rewiring minigame became the whole game.
- **The full phrase is "fail fast, follow the fun"** (Marc LeBlanc). Without fast cycles it drags on: Heat Signature took years to find its core.
- **Speed up the cycle:** game jams, prototyping tools, paper or LEGO prototypes, placeholder art, in-house level tools so everyone can try ideas.
- **One unchangeable anchor focuses exploration.** Journey changed constantly, but it was always about love.
- **Following the fun works best early,** while the game is still "a white hot ball of malleable magma", but it also helps later with content (Braid's puzzles came from its engine) and with exploits worth keeping (SpyParty).

### Playtesting is an experiment *(video 2)*

Playtesting means watching people play to drive design changes. It's different from QA (bugs) and focus testing (marketing). Valve: "we see our game designs as hypotheses and our playtests as experiments to validate these hypotheses." The cycle: **goal → design → playtest → evaluate → repeat** until it's "no longer excruciatingly painful to watch the playtests."

Playtesting changed nearly everything in Portal: GLaDOS was added because testers thought the game was a tutorial; the clean white walls came after a tester spent 30 minutes on a shelf and ignored a box; the gravity gun arrived earlier in Half-Life 2 because players loved it.

Valve's tips:

1. **Test early,** with ugly placeholder art. Problems found late only get band-aid fixes.
2. **Test often,** weekly. Lots of tests show trends instead of outliers, and you build intuition ("players don't learn when stressed", "players don't look up").
3. **Stay quiet and watch.** No hints. Body language tells you more than interviews. Ignore testers' suggested fixes; they don't know your vision or constraints.
4. **The designer runs the test.** Watching motivates fixes and inspires new ideas (covering your real mouth in Alyx became a mechanic).
5. **Test with the right people,** with a target audience in mind. Hardcore FPS players wanted a harder GLaDOS fight; most Portal players found that frustrating. Hardcore players got optional challenge maps instead.
6. **Challenge your assumptions.** The easiest puzzle in Portal (the fire-pit escape) felt the most climactic because of time pressure and drama.
7. **Feedback is data, and you interpret it.** Testers loved Half-Life 2's quick start, and Valve still delayed combat on purpose for a better emotional payoff. Bend to every tester and you get "design-by-committee sludge". Go in with a clear goal and use testing to check whether you're hitting it.

Caveat: Valve's approach suits linear, hand-crafted games. Data like heatmaps may suit other kinds of game better.

## 9. Keep re-checking the lessons *(video 1)*

Things change, and the advice can be wrong. "Never take some game design lesson as gospel. Especially if it's coming from a YouTuber." Check whether each lesson holds for this particular game.

Video 6 (careers) is mostly not relevant here. Its useful design points: designers turn ideas into docs and tunable values for others to build, the work is collaborative and iterative, and the interview test "what happens if you remove one option from rock-paper-scissors?" is a quick way to practise thinking about balance.

## What this means for Super Awesome Craft

These are questions and connections for future design sessions, not decisions. Anything here that becomes a design proposal goes into [design-map.md](../design-map.md) as an **Idea** first.

### Where current docs already match these lessons

- **Seasons are Valve's loop at server scale.** Each Season states one hypothesis ("without a single max-gear endpoint, do people keep playing?") and its playtest is the experiment. Vanilla as the placeholder for anything unbuilt plays the role of grey-box and placeholder art.
- **The vision's root-cause section is the Dying Light lesson.** It goes past the symptom ("players left") to causes (one currency, private ladders, no shared goals, convenience tied to power).
- **Catch-up easing, Mastery and world progression are negative loops** offsetting each player's positive loop. Pyre's rule applies to them: they must never make getting ahead the worse choice.
- **Technical parity is a fixed lever list,** like Halo's sniper: some levers are off the table, which focuses the search on the others.
- **The Augments problem was design by subtraction:** a system doing the same job as enchanting with different materials.

### Questions these lessons raise

- **Vision:** answered November 2026: "Every playstyle pushes the world forward" (see [vision.md](../vision.md#vision-statement)). The motivations table says *who* we serve; the statement says what the whole game should feel like and gives every mechanic one test.
- **MDA per feature:** for each Season 1 mechanic (no XP, no durability, Capacity, Familiarity), what behaviour do we predict, and what feeling? Write the predictions down before the playtest so it can confirm or refute them.
- **Economy map:** draw Season 1 as taps, inventories, converters, drains and traders. Removing durability removes a major drain, so "what replaces sinks" is really "which drain or converter takes its place?" Villagers are traders: Moonlighter-style supply and demand is one possible check on buyer income.
- **Number of currencies:** GMTK says fewer currencies create harder decisions, yet the vision blames one universal currency. These don't conflict: in a multiplayer world with trading, a single currency becomes an exchange rate that can be optimised. It's worth stating that distinction explicitly when the economy gets designed.
- **Encourage instead of punish:** check each limit (Familiarity's daily allowance, nothing from mining player-placed blocks, buyer-income caps) for how it's *framed*. A daily allowance shown as a bonus pool reads like WoW's rest bonus; the same numbers shown as diminishing returns feel like a penalty.
- **Skill floor and ceiling for a mixed group:** Strongholds are group encounters for 5 to 10 players of mixed skill. What lets weaker players contribute (multiple ways to win, roles, NSMB-style bubbles), and where is the optional ceiling for strong players?
- **Separate skill rewards from progression:** where strong players need a reward, consider giving more challenge (Donkey Kong's bonus levels) instead of more power that widens the gap.
- **Presence proportional to importance:** is each system's depth in the UI, recipes and tooltips proportional to how much it matters? The idea of making everything learnable in-game is related.
- **Borrowed mechanics:** for each one taken from Terraria, Ascendant Arcana, the Aether and others, the existing research notes describe *what* it is; also record *why it works there* and whether that reason holds on a shared Minecraft server.
- **Tuning:** when numbers like travel cart speeds and Capacity costs come up for playtesting, change them by doubling or halving rather than small percentages.
- **Playtest practice:** watch more and ask less, look for trends across players rather than single complaints, ignore testers' suggested fixes but take their problems seriously, and retest fixes without saying what changed.
