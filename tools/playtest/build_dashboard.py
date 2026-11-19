import json, sys, os, glob, re
from datetime import datetime
sys.path.insert(0, os.path.dirname(__file__))
from nbt import load  # noqa
root, out = sys.argv[1], sys.argv[2]
names = {u['uuid']: u['name'] for u in json.load(open(os.path.join(root,'usercache.json')))}
F = '%Y-%m-%d %H:%M:%S'
# sessions
sessions, open_ = [], {}
for f in sorted(x for x in glob.glob(os.path.join(root,'logs/*.log')) if not x.endswith('latest.log')):
    d = os.path.basename(f)[:10]
    for line in open(f, encoding='utf8', errors='replace'):
        m = re.match(r'\[(\d\d:\d\d:\d\d)\].*?: (\w+) (joined|left) the game', line)
        if not m: continue
        t = d+' '+m.group(1); n = m.group(2)
        if m.group(3)=='joined': open_[n]=t
        elif n in open_: sessions.append({'p':n,'s':open_.pop(n),'e':t})
def played_at(p, ts):
    t = datetime.strptime(ts, F); h = 0
    for s in sessions:
        if s['p']!=p: continue
        a, b = datetime.strptime(s['s'],F), datetime.strptime(s['e'],F)
        if t >= b: h += (b-a).total_seconds()
        elif t > a: h += (t-a).total_seconds()
    return round(h/3600, 2)
LABELS = {'story/mine_stone':'Stone tools','story/smelt_iron':'Smelt iron','story/iron_tools':'Iron pickaxe','adventure/trade':'First trade','story/mine_diamond':'Diamonds','story/shiny_gear':'Diamond armor','story/enter_the_nether':'Enter Nether','nether/find_fortress':'Fortress','nether/obtain_blaze_rod':'Blaze rod','nether/obtain_ancient_debris':'Ancient Debris','nether/netherite_armor':'Netherite armor','husbandry/obtain_netherite_hoe':'Netherite hoe','nether/find_bastion':'Bastion','adventure/minecraft_trials_edition':'Trial Chamber','story/follow_ender_eye':'Stronghold','story/enter_the_end':'Enter End','end/kill_dragon':'Dragon killed','end/elytra':'Elytra','husbandry/breed_an_animal':'Breed animals','adventure/voluntary_exile':'Raid outpost'}
TIER = {'story/mine_stone':0,'story/smelt_iron':1,'story/iron_tools':1,'adventure/trade':1,'story/mine_diamond':2,'story/shiny_gear':2,'story/enter_the_nether':3,'nether/obtain_ancient_debris':3,'nether/netherite_armor':4,'end/kill_dragon':5}
players = []
for f in sorted(glob.glob(os.path.join(root,'world/players/stats/*.json'))):
    u = os.path.basename(f)[:-5]; n = names.get(u,u)
    s = json.load(open(f))['stats']; c = s.get('minecraft:custom',{})
    if c.get('minecraft:play_time',0) < 72000: continue
    g = lambda cat,k: s.get('minecraft:'+cat,{}).get('minecraft:'+k,0)
    cc = lambda k: c.get('minecraft:'+k,0)
    adv = json.load(open(os.path.join(root,'world/players/advancements',u+'.json')))
    ms = []
    for k,lab in LABELS.items():
        v = adv.get('minecraft:'+k)
        if v and v.get('done'):
            ts = max(v['criteria'].values())[:19]
            ms.append({'k':k,'label':lab,'ts':ts,'h':played_at(n,ts),'tier':TIER.get(k)})
    ms.sort(key=lambda x:x['ts'])
    d = load(os.path.join(root,'world/players/data',u+'.dat'))
    def fmt(it):
        cm = it.get('components',{}); e = cm.get('minecraft:enchantments') or cm.get('minecraft:stored_enchantments') or {}
        e = e.get('levels', e) if isinstance(e,dict) else {}
        return {'id':it['id'].replace('minecraft:',''),'ench':{k.replace('minecraft:',''):v for k,v in e.items()}}
    gear = [dict(fmt(v), slot=k) for k,v in d.get('equipment',{}).items() if k in ('head','chest','legs','feet','offhand')]
    gear += [dict(fmt(it), slot='inv') for it in d.get('Inventory',[]) if any(x in it['id'] for x in ('netherite_','diamond_','elytra','bow','shield','_pickaxe','_sword')) and 'ingot' not in it['id']]
    mined = s.get('minecraft:mined',{})
    ores = {k:sum(mined.get('minecraft:'+o,0) for o in v) for k,v in {'Copper':['copper_ore','deepslate_copper_ore'],'Iron':['iron_ore','deepslate_iron_ore'],'Gold':['gold_ore','deepslate_gold_ore','nether_gold_ore'],'Diamond':['diamond_ore','deepslate_diamond_ore'],'Lapis':['lapis_ore','deepslate_lapis_ore'],'Redstone':['redstone_ore','deepslate_redstone_ore'],'Quartz':['nether_quartz_ore'],'Ancient Debris':['ancient_debris']}.items()}
    dist = {k:round(sum(v for kk,v in c.items() if kk.startswith('minecraft:'+k))/100000,1) for k in ('walk','sprint','fly','boat','swim','horse')}
    players.append({'name':n,'hours':round(cc('play_time')/72000,1),'deaths':cc('deaths'),'mobKills':cc('mob_kills'),'trades':cc('traded_with_villager'),
      'emeralds':g('crafted','emerald'),'bred':cc('animals_bred'),'cowsKilled':g('killed','cow'),'fish':cc('fish_caught'),'sleep':cc('sleep_in_bed'),
      'netherrack':g('mined','netherrack'),'tntUsed':g('used','tnt'),'potatoes':g('picked_up','potato'),'steakPicked':g('picked_up','cooked_beef'),
      'smelted':{'Copper':g('crafted','copper_ingot'),'Iron':g('crafted','iron_ingot'),'Gold':g('crafted','gold_ingot')},
      'ores':ores,'dist':dist,'xpLevel':d.get('XpLevel'),'milestones':ms,'gear':gear,
      'builds':{k:g('crafted',k) for k in ('coarse_dirt','cobblestone_wall','glass','light_blue_stained_glass','snow_block','torch')},
      'brewing':cc('interact_with_brewingstand'),'smithing':cc('interact_with_smithing_table'),'anvil':cc('interact_with_anvil')})
data = {'generated':datetime.now().strftime('%Y-%m-%d %H:%M'),'sessions':sessions,'players':players}
open(os.path.join(out,'data.js'),'w',encoding='utf8').write('window.PLAYTEST = '+json.dumps(data, indent=1)+';\n')
print(len(players),'players', len(sessions),'sessions')
for p in players: print(p['name'], [(m['label'],m['h']) for m in p['milestones'] if m['tier'] is not None])
# also emit a single self-contained file
tpl = open(os.path.join(out,'index.html'),encoding='utf8').read()
js = open(os.path.join(out,'data.js'),encoding='utf8').read()
open(os.path.join(out,'dashboard.html'),'w',encoding='utf8').write(tpl.replace('<script src="data.js"></script>','<script>'+js+'</script>'))
print('wrote dashboard.html')
