import gzip, struct, sys, os, json, glob
def rd(b, i, t):
    if t==1: return struct.unpack_from('>b',b,i)[0], i+1
    if t==2: return struct.unpack_from('>h',b,i)[0], i+2
    if t==3: return struct.unpack_from('>i',b,i)[0], i+4
    if t==4: return struct.unpack_from('>q',b,i)[0], i+8
    if t==5: return struct.unpack_from('>f',b,i)[0], i+4
    if t==6: return struct.unpack_from('>d',b,i)[0], i+8
    if t==7:
        n=struct.unpack_from('>i',b,i)[0]; return list(b[i+4:i+4+n]), i+4+n
    if t==8:
        n=struct.unpack_from('>H',b,i)[0]; return b[i+2:i+2+n].decode('utf8','replace'), i+2+n
    if t==9:
        et=b[i]; n=struct.unpack_from('>i',b,i+1)[0]; i+=5; out=[]
        for _ in range(n):
            v,i=rd(b,i,et); out.append(v)
        return out,i
    if t==10:
        d={}
        while True:
            tt=b[i]; i+=1
            if tt==0: return d,i
            n=struct.unpack_from('>H',b,i)[0]; k=b[i+2:i+2+n].decode('utf8','replace'); i+=2+n
            d[k],i=rd(b,i,tt)
    if t==11:
        n=struct.unpack_from('>i',b,i)[0]; return list(struct.unpack_from(f'>{n}i',b,i+4)), i+4+4*n
    if t==12:
        n=struct.unpack_from('>i',b,i)[0]; return list(struct.unpack_from(f'>{n}q',b,i+4)), i+4+8*n
def load(p):
    b=gzip.open(p).read(); tt=b[0]; n=struct.unpack_from('>H',b,1)[0]
    return rd(b,3+n,tt)[0]
def main():
    root=sys.argv[1]
    names = {u['uuid']: u['name'] for u in json.load(open(os.path.join(root,'usercache.json')))}
    def fmt(it):
        s=f"{it.get('count',1)}x {it['id'].replace('minecraft:','')}"
        c=it.get('components',{})
        e=c.get('minecraft:enchantments') or c.get('minecraft:stored_enchantments')
        if e:
            e = e.get('levels', e) if isinstance(e,dict) else e
            s+=' ['+', '.join(f"{k.replace('minecraft:','')} {v}" for k,v in e.items())+']'
        if 'minecraft:unbreakable' in c: s+=' {unbreakable}'
        return s
    for f in sorted(glob.glob(os.path.join(root,'world/players/data/*.dat'))):
        u=os.path.basename(f)[:-4]; d=load(f)
        print('='*10, names.get(u,u), 'XpLevel', d.get('XpLevel'), 'XpTotal', d.get('XpTotal'), 'pos', [round(x) for x in d.get('Pos',[])], d.get('Dimension'))
        inv = d.get('Inventory',[])
        eq = d.get('equipment',{})
        for k,v in eq.items(): print('  EQ', k, fmt(v))
        for it in inv:
            c=it.get('components',{})
            if any(x in c for x in ('minecraft:enchantments','minecraft:stored_enchantments')) or 'netherite' in it['id'] or 'diamond' in it['id'] or it['id'].endswith('emerald') or it['id'].endswith('emerald_block'):
                print('  INV', fmt(it))
        ec = d.get('EnderItems',[])
        if ec: print('  ENDER', '; '.join(fmt(i) for i in ec))

if __name__=='__main__':
    main()
