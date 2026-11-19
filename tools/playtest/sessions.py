import sys, os, glob, re
from datetime import datetime
root = sys.argv[1]
open_ = {}; tot = {}
files = sorted(f for f in glob.glob(os.path.join(root,'logs/*.log')) if not f.endswith('latest.log'))
for f in files:
    d = os.path.basename(f)[:10]
    for line in open(f, encoding='utf8', errors='replace'):
        m = re.match(r'\[(\d\d:\d\d:\d\d)\].*?: (\w+) (joined|left) the game', line)
        if not m: continue
        t = datetime.strptime(d+' '+m.group(1), '%Y-%m-%d %H:%M:%S'); n = m.group(2)
        if m.group(3)=='joined': open_[n]=t
        elif n in open_:
            s = open_.pop(n); day = s.strftime('%m-%d')
            tot.setdefault(n,{}); tot[n][day] = tot[n].get(day,0)+(t-s).total_seconds()/3600
for n,d in tot.items():
    print(f"{n:14s} total={sum(d.values()):5.1f}h  " + ' '.join(f"{k}:{v:.1f}" for k,v in sorted(d.items())))
print('unclosed', open_)
