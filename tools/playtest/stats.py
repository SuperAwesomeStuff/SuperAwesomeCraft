import json, sys, os, glob
root = sys.argv[1]
names = {u['uuid']: u['name'] for u in json.load(open(os.path.join(root,'usercache.json')))}
def top(d, n=12):
    return ', '.join(f"{k.replace('minecraft:','')}={v}" for k,v in sorted(d.items(), key=lambda x:-x[1])[:n] if v)
for f in sorted(glob.glob(os.path.join(root,'world/players/stats/*.json'))):
    u = os.path.basename(f)[:-5]
    s = json.load(open(f))['stats']
    c = s.get('minecraft:custom', {})
    g = lambda k: c.get('minecraft:'+k, 0)
    print('='*20, names.get(u,u))
    print(f"play_h={g('play_time')/72000:.1f} deaths={g('deaths')} mob_kills={g('mob_kills')} trades={g('traded_with_villager')} talked={g('talked_to_villager')} enchant_item={g('enchant_item')} anvil={g('interact_with_anvil')} smithing={g('interact_with_smithing_table')} furnace={g('interact_with_furnace')} blastf={g('interact_with_blast_furnace')} fish={g('fish_caught')} animals_bred={g('animals_bred')} sleep={g('sleep_in_bed')} jumps={g('jump')}")
    dist = {k.replace('minecraft:',''):round(v/100000,1) for k,v in c.items() if k.endswith('_one_cm')}
    print('dist_km:', top(dist, 12))
    print('custom:', top({k:v for k,v in c.items() if not k.endswith('_one_cm') and 'time' not in k}, 40))
    for cat in ['mined','crafted','used','killed','picked_up','dropped','killed_by','broken']:
        print(f"{cat}:", top(s.get('minecraft:'+cat,{}), 25))
