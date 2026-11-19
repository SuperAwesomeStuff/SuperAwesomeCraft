import json, sys, os, glob, re
from datetime import datetime, timedelta
root = sys.argv[1]
names = {u['uuid']: u['name'] for u in json.load(open(os.path.join(root,'usercache.json')))}
keys = ['story/mine_stone','story/smelt_iron','story/iron_tools','adventure/trade','story/mine_diamond','story/enter_the_nether','story/enchant_item','nether/obtain_ancient_debris','nether/netherite_armor','story/shiny_gear','nether/find_fortress','nether/find_bastion','story/follow_ender_eye','story/enter_the_end','end/kill_dragon','end/elytra','husbandry/breed_an_animal','husbandry/plant_seed','adventure/sleep_in_bed','nether/obtain_blaze_rod','story/lava_bucket','adventure/kill_a_mob','husbandry/fishy_business','adventure/hero_of_the_village','adventure/voluntary_exile','end/find_end_city','nether/explore_nether','adventure/adventuring_time','nether/get_wither_skull','nether/summon_wither','adventure/trade_at_world_height','husbandry/use_lead','husbandry/tactical_fishing']
# session from logs
sess = {}
for f in sorted(glob.glob(os.path.join(root,'logs/*.log'))):
    b = os.path.basename(f)
    if b=='latest.log': continue
    d = b[:10]
    for line in open(f, encoding='utf8', errors='replace'):
        m = re.match(r'\[(\d\d:\d\d:\d\d)\].*?: (\w+) (joined|left) the game', line)
        if m:
            sess.setdefault(m.group(2), []).append((d+' '+m.group(1), m.group(3)))
for f in sorted(glob.glob(os.path.join(root,'world/players/advancements/*.json'))):
    u = os.path.basename(f)[:-5]; n = names.get(u,u)
    a = json.load(open(f))
    done = {}
    for k,v in a.items():
        if not isinstance(v, dict) or not v.get('done'): continue
        ts = max(v['criteria'].values())
        done[k.replace('minecraft:','')] = ts[:16]
    print('='*10, n, f'({len(done)} advancements done)')
    for k,ts in sorted(done.items(), key=lambda x:x[1]):
        if 'recipes/' in k: continue
        print('  ', ts, k)
    s = sess.get(n, [])
    if s:
        days = sorted({t[:10] for t,_ in s})
        print('  sessions days:', ', '.join(days))
