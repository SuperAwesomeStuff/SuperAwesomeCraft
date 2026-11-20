## Agent skills

### Issue tracker

Issues and specs are tracked in GitHub Issues using the `gh` CLI. See `docs/agents/issue-tracker.md`.

### Triage labels

Triage uses the five default canonical labels. See `docs/agents/triage-labels.md`.

### Domain docs

Domain documentation uses the single-context layout. See `docs/agents/domain.md`.

## Private notes

`docs/private/` holds notes about real players: profiles, and playtest server data and dashboards under `docs/private/playtest-*/`. It is git-ignored. Read it for design context, but never commit it, quote player names in commits, issues or PRs, or move its contents into tracked files.

## Wiki

The GitHub wiki (the separate `SuperAwesomeCraft.wiki` repo, cloned into the git-ignored `wiki/` folder) is the player-facing version of the design for friends who won't read the docs. Write it in plain language with no jargon, and label each page **Decided**, **Leaning** or **Idea**. When a decision in `docs/` changes, update the matching wiki page. The wiki is public: never include player names or anything from `docs/private/`.

## Research

When the user mentions a mod, game, plugin or design concept you don't know the specifics of, spawn a research subagent instead of guessing. It should check primary sources (mod pages, source code, official wikis), write its findings to `docs/research/<topic>.md` in the style of the existing files there, and cite URLs. Mark anything it couldn't verify. Read the existing research notes before starting design work on a related topic.

## Design principles

When designing or critiquing a mechanic, system or Season, fixing a design problem, or reading playtest results, run it through the lenses in the `design-lens` skill (`.claude/skills/design-lens/SKILL.md`). The principles behind them are in `docs/research/game-design-101.md`.

## Design map

`docs/design-map.md` is the rough map of the whole design. New ideas go there as **Ideas** first. Don't treat Ideas as decisions or build on them; promote one to Leaning or Decided (and into `docs/vision.md`) only when the user confirms it, usually when the Season that needs it is being designed.
