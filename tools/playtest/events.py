"""Summarise the mod's playtest log into the Season 1 playtest measures.

The mod writes one JSON line per event to <world>/superawesomecraft/playtest/<date>.jsonl (see PlaytestLog.java).
`tools/devserver.sh pull <date>` copies them to serverdata/world/superawesomecraft/playtest/.

    python -I tools/playtest/events.py docs/private/playtest-<date>/serverdata          # print the measures
    python -I tools/playtest/events.py docs/private/playtest-<date>/serverdata --json   # the same as JSON

The measures are the six in docs/season-1/enchanting/README.md#playtest-measures.
"""
import glob
import json
import os
import sys
from collections import Counter, defaultdict
from datetime import datetime

WEEK_MS = 7 * 24 * 3600 * 1000


def load(root):
    events = []
    for f in sorted(glob.glob(os.path.join(root, 'world', 'superawesomecraft', 'playtest', '*.jsonl'))):
        with open(f, encoding='utf8') as fh:
            for line in fh:
                line = line.strip()
                if line:
                    events.append(json.loads(line))
    events.sort(key=lambda e: e['t'])
    return events


def played_hours(events):
    """Hours each player had played at each event, from join and leave events."""
    joined, total, at = {}, defaultdict(float), []
    for e in events:
        p = e['player']
        if e['type'] == 'join':
            joined[p] = e['t']
        elif e['type'] == 'leave' and p in joined:
            total[p] += (e['t'] - joined.pop(p)) / 3_600_000
        now = total[p] + ((e['t'] - joined[p]) / 3_600_000 if p in joined else 0)
        at.append(round(now, 2))
    return at, total


def short(i):
    return i.replace('minecraft:', '').replace('superawesomecraft:', '')


def summarise(events):
    if not events:
        return {}
    names = {e['player']: e['name'] for e in events}
    hours_at, hours = played_hours(events)
    start = events[0]['t']
    week = lambda t: (t - start) // WEEK_MS + 1

    # 1. Pace: hours played when each player first carried a maxed item, and the most maxed items they carried at once.
    first_maxed, most_maxed = {}, Counter()
    # 3. Variety: each player's latest enchantments on each kind of item.
    latest = defaultdict(dict)
    # 2. Spending, 4. Usage, 5. Repairs, 6. Retention
    spent = defaultdict(lambda: defaultdict(Counter))  # player -> week -> material -> count
    repair_materials = defaultdict(lambda: defaultdict(int))
    picked, removed, upgrades, broken, repairs = Counter(), Counter(), Counter(), Counter(), Counter()
    transfers = Counter()  # from item -> to item
    ability = Counter()
    enchant_weeks = defaultdict(set)
    deaths = defaultdict(list)

    for e, h in zip(events, hours_at):
        p, kind = e['player'], e['type']
        if kind == 'snapshot':
            maxed = [g for g in e.get('gear', []) if g.get('maxed')]
            if maxed and p not in first_maxed:
                first_maxed[p] = {'hours': h, 'item': short(maxed[0]['id'])}
            most_maxed[p] = max(most_maxed[p], len(maxed))
            for g in e.get('gear', []):
                if g.get('ench'):
                    latest[short(g['id'])][p] = tuple(sorted(short(k) for k in g['ench']))
        if kind in ('snapshot', 'counts'):
            ability.update(e.get('counts', {}))
        if kind in ('enchant', 'slot_upgrade') and not e.get('creative'):
            for c in e.get('cost', []):
                spent[p][week(e['t'])][short(c['item'])] += c['count']
        if kind == 'enchant':
            picked[short(e['enchantment'])] += 1
            enchant_weeks[p].add(week(e['t']))
        elif kind == 'unenchant':
            removed[short(e['enchantment'])] += 1
        elif kind == 'slot_upgrade':
            upgrades[e['upgrade']] += 1
        elif kind == 'transfer':
            transfers[f"{short(e['from']['id'])} -> {short(e['item']['id'])}"] += 1
        elif kind == 'gear_broken':
            broken[p] += 1
        elif kind == 'repair':
            repairs[p] += 1
            repair_materials[p][week(e['t'])] += e['materials']
        elif kind == 'death':
            deaths[p].append({'cause': short(e.get('cause', '?')), 'dim': short(e['dim'])})

    variety = {}
    for item, by_player in sorted(latest.items()):
        if len(by_player) >= 2:
            variety[item] = {'players': len(by_player), 'distinct_builds': len(set(by_player.values())),
                             'builds': Counter(', '.join(b) for b in by_player.values()).most_common()}

    players = sorted(names, key=lambda p: -hours[p])
    return {
        'generated': datetime.now().strftime('%Y-%m-%d %H:%M'),
        'players': [{
            'name': names[p],
            'hours': round(hours[p], 1),
            'first_maxed': first_maxed.get(p),
            'most_maxed_items': most_maxed[p],
            'spent_by_week': {w: dict(c) for w, c in sorted(spent[p].items())},
            'repair_materials_by_week': dict(sorted(repair_materials[p].items())),
            'gear_hit_zero': broken[p],
            'repairs': repairs[p],
            'enchanting_weeks': sorted(enchant_weeks[p]),
            'deaths': deaths[p],
        } for p in players],
        'enchantments_picked': picked.most_common(),
        'enchantments_removed': removed.most_common(),
        'slot_upgrades': dict(upgrades),
        'transfers': transfers.most_common(),
        'ability_use': dict(ability.most_common()),
        'variety': variety,
        'retention': {
            'players': len(names),
            'enchanting_in_week_2': sum(1 for p in names if 2 in enchant_weeks[p]),
        },
    }


def show(s):
    if not s:
        print('No playtest log events found.')
        return
    print('# Pace (hours played)')
    for p in s['players']:
        fm = p['first_maxed']
        print(f"  {p['name']:<16} {p['hours']:>6}h played  first maxed item: "
              + (f"{fm['item']} at {fm['hours']}h" if fm else 'none') + f"  most maxed at once: {p['most_maxed_items']}")
    print('\n# Spending (materials per week, enchanting and slot upgrades)')
    for p in s['players']:
        for w, c in p['spent_by_week'].items():
            print(f"  {p['name']:<16} week {w}: " + ', '.join(f"{n} {m}" for m, n in sorted(c.items(), key=lambda x: -x[1])))
    print('\n# Variety (items two or more players enchanted)')
    for item, v in s['variety'].items():
        print(f"  {item}: {v['distinct_builds']} builds across {v['players']} players")
        for build, n in v['builds']:
            print(f"      {n}x {build}")
    print('\n# Usage')
    print('  picked:  ' + ', '.join(f"{e} {n}" for e, n in s['enchantments_picked']))
    print('  removed: ' + ', '.join(f"{e} {n}" for e, n in s['enchantments_removed']))
    print('  slot upgrades: ' + ', '.join(f"{u} {n}" for u, n in s['slot_upgrades'].items()))
    print('  moved: ' + ', '.join(f"{t} {n}" for t, n in s['transfers']))
    print('  abilities: ' + ', '.join(f"{a} {n}" for a, n in s['ability_use'].items()))
    print('\n# Repairs')
    for p in s['players']:
        print(f"  {p['name']:<16} gear hit 0: {p['gear_hit_zero']}  repairs: {p['repairs']}  "
              f"materials by week: {p['repair_materials_by_week']}")
    r = s['retention']
    print(f"\n# Retention\n  {r['enchanting_in_week_2']} of {r['players']} players enchanted in week 2")


if __name__ == '__main__':
    summary = summarise(load(sys.argv[1]))
    if '--json' in sys.argv:
        print(json.dumps(summary, indent=1))
    else:
        show(summary)
