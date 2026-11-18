## Agent skills

### Issue tracker

Issues and specs are tracked in GitHub Issues using the `gh` CLI. See `docs/agents/issue-tracker.md`.

### Triage labels

Triage uses the five default canonical labels. See `docs/agents/triage-labels.md`.

### Domain docs

Domain documentation uses the single-context layout. See `docs/agents/domain.md`.

## Private notes

`docs/private/` holds notes about real players (profiles and playtest data). It is git-ignored. Read it for design context, but never commit it, quote player names in commits, issues or PRs, or move its contents into tracked files.

## Wiki

The GitHub wiki (`SuperAwesomeCraft.wiki` repo, cloned beside this one) is the player-facing version of the design for friends who won't read the docs. Write it in plain language with no jargon, and label each page **Decided**, **Leaning** or **Idea**. When a decision in `docs/` changes, update the matching wiki page. The wiki is public: never include player names or anything from `docs/private/`.
