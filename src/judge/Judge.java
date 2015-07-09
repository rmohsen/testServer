package judge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

class Judge extends JudgeAbstract {

	public static ArrayList<Object> objects=new ArrayList<Object>();
	public static ArrayList<GameObjectID> objectsId=new ArrayList<GameObjectID>();
	public static ArrayList<Player> players=new ArrayList<Player>();
	public static int winner=4;
	public static int time=0;
	public Map map;
	@Override
	public ArrayList<GameObjectID> loadMap(int[][] cellsType,
			int[][] wallsType, int[] players) {
		Player[] p=new Player[players.length];
		ArrayList<GameObjectID> ret=new ArrayList<GameObjectID>();
		for(int i=0;i<p.length;i++){
			p[i]=new Player(players[i]);
			ret.add(GameObjectID.create(p[i].getClass()));
			p[i].id=ret.get(i);
			objects.add(p[i]);
			objectsId.add(ret.get(i));
			this.players.add(p[i]);
		}
		map=new Map(cellsType, wallsType, p);
		return ret;
	}

	@Override
	public int getMapWidth() {
		return map.width;
	}

	@Override
	public int getMapHeight() {
		return map.height;
	}

	@Override
	public int getMapCellType(int col, int row) {
		if(map.map[col][row].type==Judge.JJ_CELL)
			if(time%5000!=0)
				return Judge.NONE_CELL;
		return map.map[col][row].type;
	}

	@Override
	public int getMapCellType(int col, int row, GameObjectID player) throws BozorgExceptionBase {
		String s=row+","+col;
		if(getVision(player).contains(s)){
			if(map.map[col][row].type==Judge.JJ_CELL)
				if(time%5000!=0)
					return Judge.NONE_CELL;
			if(map.map[row][col].type<4)
				return map.map[row][col].type;
			return JudgeAbstract.BONUS_CELL;
		}
		return JudgeAbstract.DARK_CELL;
	}

	@Override
	public int getMapWallType(int col, int row) {
		return map.map[col][row].walls;
	}

	@Override
	public int getMapWallType(int col, int row, GameObjectID player) throws BozorgExceptionBase {
		String s=row+","+col;
		if(getVision(player).contains(s)){
			return map.map[row][col].walls;
		}
		return JudgeAbstract.XXXX_WALL;
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movePlayer(GameObjectID player, int direction)
			throws BozorgExceptionBase {
		Player p=(Player) objects.get(player.getNumber());
		int w=map.map[p.x][p.y].walls;
		if(p.hp==0)
			throw new BozorgExceptionBase();
		if(direction<0 || direction>3)
			throw new BozorgExceptionBase();
		if((w>>direction)%2==1)
			throw new BozorgExceptionBase();
		if(p.moveTime>0)
			throw new BozorgExceptionBase();
		p.moveTime+=p.speed;
		p.moveDir=direction;
	}

	@Override
	public void attack(GameObjectID attacker, int direction)
			throws BozorgExceptionBase {
		Player p=(Player) objects.get(attacker.getNumber());
		int w=map.map[p.x][p.y].walls;
		if(p.hp==0)
			throw new BozorgExceptionBase();
		if(direction<0 || direction>4)
			throw new BozorgExceptionBase();
		if((w>>direction)%2==1)
			throw new BozorgExceptionBase();
		if(p.attackTime>0)
			throw new BozorgExceptionBase();
		p.attackTime+=p.speed;
		p.attackDir=direction;
	}

	@Override
	public GameObjectID throwFan(GameObjectID player)
			throws BozorgExceptionBase {
		Player p=(Player) objects.get(player.getNumber());
		if(p.hp==0)
			throw new BozorgExceptionBase();
		if(p.fans==0)
			throw new BozorgExceptionBase();
		Fan f=new Fan(p.x,p.y,(Player)objects.get(player.getNumber()));
		objects.add(f);
		GameObjectID g=GameObjectID.create(f.getClass());
		f.id=g;
		objectsId.add(g);
		p.fans--;
		return g;
	}

	@Override
	public void getGift(GameObjectID player) throws BozorgExceptionBase {
		Player p=(Player) objects.get(player.getNumber());
		if(p.hp==0)
			throw new BozorgExceptionBase();
		if(getMapCellType(p.x, p.y, player)!=BONUS_CELL)
			throw new BozorgExceptionBase();
		for(Player x:Judge.players){
			if(x.equals(p))
				continue;
			if(x.x==p.x && x.y==p.y)
				throw new BozorgExceptionBase();
		}
		int t=map.map[p.x][p.y].type;
		if(t==JUMP_CELL){
			p.jump+=2000;
		}
		if(t==SPEEDUP_CELL){
			p.speed+=5000;
		}
		if(t==STONE_CELL){
			p.stun+=3000;
		}
		if(t==FAN_CELL){
			p.fans+=3;
		}
		if(t==RADAR_CELL){
			p.vision+=3000;
		}
		if(t==HOSPITAL_CELL){
			p.hp=Math.min(p.hp+20, 100);
		}
		map.map[p.x][p.y].type=0;
	}

	@Override
	public void AIByStudents(GameObjectID player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<GameObjectID> getEveryThing() {
		return objectsId;
	}

	@Override
	public ArrayList<String> getVision(GameObjectID player)
			throws BozorgExceptionBase {
		if(!player.getClass().equals(Player.class))
			throw new BozorgExceptionBase();
		if(((Player)(objects.get(player.getNumber()))).hp==0)
			throw new BozorgExceptionBase();
		if(((Player)objects.get(player.getNumber())).vision>0){
			ArrayList<String>ret=new ArrayList<String>();
			for(int i=0;i<map.width;i++)
				for(int j=0;j<map.height;j++)
					ret.add(i+","+j);
			return ret;
		}
		TreeSet<String> set=new TreeSet<String>();
		Player p=(Player)objects.get(player.getNumber());
		for(Object o:objects){
			if(o.getClass().equals(Fan.class))
				if(((Fan)o).owner.equals((Player)objects.get(player.getNumber())) && ((Fan)o).isAlive)
					set.add(((Fan)o).x+","+((Fan)o).y);
		}
		for(int i=Math.max(0,p.x-p.vision);i<Math.min(map.width,p.x+p.vision+1);i++)
			for(int j=Math.max(0,p.y-p.vision);j<Math.min(map.height,p.y+p.vision+1);j++)
				set.add(i+","+j);
		ArrayList<String>ret=new ArrayList<String>();
		for(String s:set)
			ret.add(s);
		return ret;
	}

	@Override
	public ArrayList<GameObjectID> getPlayersInVision(GameObjectID player) throws BozorgExceptionBase {
		ArrayList<String> vis=getVision(player);
		ArrayList<GameObjectID> ret=new ArrayList<GameObjectID>();
		for(Player p:players){
			if(vis.contains(p.x+","+p.y))
				ret.add(p.id);
		}
		return ret;
	}

	@Override
	public ArrayList<GameObjectID> getFans(GameObjectID player)
			throws BozorgExceptionBase {
		ArrayList<GameObjectID> ret=new ArrayList<GameObjectID>();
		for(Object o:objects){
			if(o.getClass().equals(Fan.class))
				if(((Fan)o).owner.equals((Player)objects.get(player.getNumber())) && ((Fan)o).isAlive)
					ret.add(((Fan)o).id);
		}
		return ret;
	}

	@Override
	public HashMap<String, Integer> getInfo(GameObjectID id)
			throws BozorgExceptionBase {
		HashMap<String, Integer> ret=new HashMap<String, Integer>();
		if(objects.get(id.getNumber()).getClass().equals(Fan.class)){
			Fan f=(Fan)objects.get(id.getNumber());
			ret.put(JudgeAbstract.ROW, f.x);
			ret.put(JudgeAbstract.COL, f.y);
			ret.put(JudgeAbstract.OWNER, f.owner.name);
			if(f.isAlive)
				ret.put(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
			else
				ret.put(JudgeAbstract.IS_ALIVE, JudgeAbstract.DEAD);
		}
		if(objects.get(id.getNumber()).getClass().equals(Player.class)){
			Player p=(Player)objects.get(id.getNumber());
			ret.put(JudgeAbstract.ROW, p.x);
			ret.put(JudgeAbstract.COL, p.y);
			ret.put(JudgeAbstract.SPEED, p.speed);
			ret.put(JudgeAbstract.NAME, p.name);
			if(Judge.winner==4)
				ret.put(JudgeAbstract.IS_WINNER,JudgeAbstract.NOT_FINISHED);
			else if(Judge.winner==p.name)
				ret.put(JudgeAbstract.IS_WINNER,JudgeAbstract.WINS);
			else
				ret.put(JudgeAbstract.IS_WINNER,JudgeAbstract.LOST);
			ret.put(JudgeAbstract.POWER, p.power);
			ret.put(JudgeAbstract.VISION, p.vision);
			ret.put(JudgeAbstract.FANS,p.fans);
			if(p.hp>0)
				ret.put(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
			else
				ret.put(JudgeAbstract.IS_ALIVE, JudgeAbstract.DEAD);
			ret.put(JudgeAbstract.HEALTH,Math.max(p.hp,0));
		}
		return ret;
	}

	@Override
	public void next50milis() {
		for(Player p:Judge.players)
			p.next50milis();
		time+=50;
		if(time%5000!=0)
			return;
		if(winner==4){
			int tmp=0;
			for(Player p:Judge.players){
				if(map.map[p.x][p.y].type==JudgeAbstract.JJ_CELL){
					tmp++;
					winner=p.name;
				}
			}
			if(tmp>1)
				winner=4;
		}
	}

	@Override
	public void startTimer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseTimer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getTime() {
		return time;
	}

	@Override
	public void updateInfo(GameObjectID id, String infoKey, Integer infoValue)
			throws BozorgExceptionBase {
		if(objects.get(id.getNumber()).getClass().equals(Fan.class)){
			Fan f=(Fan)objects.get(id.getNumber());
			if(infoKey.equals(JudgeAbstract.ROW))
				f.x=infoValue;
			if(infoKey.equals(JudgeAbstract.COL))
				f.y=infoValue;
			if(infoKey.equals(JudgeAbstract.OWNER))
				for(Player p:players)
					if(p.name==infoValue)
						f.owner=p;
			if(infoKey.equals(JudgeAbstract.IS_ALIVE))
				if(infoValue==JudgeAbstract.ALIVE)
					f.isAlive=true;
				else
					f.isAlive=false;
		}
		if(objects.get(id.getNumber()).getClass().equals(Player.class)){
			Player p=(Player)objects.get(id.getNumber());
			if(infoKey.equals(JudgeAbstract.ROW))
				p.x=infoValue;
			if(infoKey.equals(JudgeAbstract.COL))
				p.y=infoValue;
			if(infoKey.equals(JudgeAbstract.IS_ALIVE))
				if(infoValue==JudgeAbstract.ALIVE)
					if(p.hp==0)
						p.hp=100;
			if(infoKey.equals(JudgeAbstract.SPEED))
				p.speed=infoValue;
			if(infoKey.equals(JudgeAbstract.NAME))
				p.name=infoValue;
			if(infoKey.equals(JudgeAbstract.POWER))
				p.power=infoValue;
			if(infoKey.equals(JudgeAbstract.VISION))
				p.vision=infoValue;
			if(infoKey.equals(JudgeAbstract.FANS))
				p.fans=infoValue;
		}
	}

}

class Player{
	
	int fans;
	int speed;
	int vision;
	int power;
	int name;
	int moveDir=0;
	int moveTime=0;
	int attackDir=0;
	int attackTime=0;
	int hp=100;
	int doubleSpeed=0;
	int globalVision=0;
	int stun=0;
	int jump=0;
	int dead=0;
	public int x;
	public int y;
	public GameObjectID id;
	
	public Player(int i) {
		this.name=i;
		if(i==Judge.HASIN){
			fans=10;
			speed=500;
			vision=3;
			power=5;
		}
		if(i==Judge.JAFAR){
			fans=5;
			speed=200;
			vision=3;
			power=1;
		}
		if(i==Judge.SAMAN){
			fans=100;
			speed=500;
			vision=3;
			power=5;
		}
		if(i==Judge.REZA){
			fans=0;
			speed=500;
			vision=6;
			power=4;
		}
	}
	
	public void next50milis(){
		if(dead>0){
			dead-=50;
			return;
		}
		int t=50;
		if(doubleSpeed>0){
			t*=2;
			doubleSpeed-=50;
		}
		if(stun>0){
			stun-=50;
			return;
		}
		if(globalVision>0)
			globalVision-=50;
		if(jump>0)
			jump-=50;
		if(attackTime>0){
			attackTime-=t;
			if(attackTime==0){
				int x = this.x,y = this.y;
				if(attackDir==JudgeAbstract.UP){
					x=this.x-1;
					y=this.y;
				}
				if(attackDir==JudgeAbstract.DOWN){
					x=this.x+1;
					y=this.y;
				}
				if(attackDir==JudgeAbstract.RIGHT){
					x=this.x;
					y=this.y+1;
				}
				if(attackDir==JudgeAbstract.LEFT){
					x=this.x;
					y=this.y-1;
				}
				for(Player p:Judge.players){
					if(this.equals(p))
						continue;
					if(p.x==x && p.y==y)
						p.hp=Math.max(p.hp-this.power,0);
					if(p.hp==0){
						p.dead=30000;
						p.attackTime=0;
						p.moveTime=0;
						p.doubleSpeed=0;
						p.globalVision=0;
						p.jump=0;
						p.stun=0;
						p.hp=100;
					}
				}
			}
		}
		if(moveTime>0){
			moveTime-=t;
			if(moveTime==0){
				if(attackDir==JudgeAbstract.UP){
					this.x--;
				}
				if(attackDir==JudgeAbstract.DOWN){
					this.x++;
				}
				if(attackDir==JudgeAbstract.RIGHT){
					this.y++;
				}
				if(attackDir==JudgeAbstract.LEFT){
					this.y--;
				}
			}
		}
	}
}

class Fan{
	public GameObjectID id;
	public int x,y;
	public Player owner;
	public boolean isAlive=true;
	public Fan(int x, int y, Player player) {
		this.x=x;
		this.y=y;
		this.owner=player;
	}
}

class Map{
	Cell[][] map;
	int width,height;
	public Map(int[][] cellsType,int[][] wallsType,Player[] players){
		this.width=cellsType.length;
		this.height=cellsType[0].length;
		map=new Cell[width][height];
		for(int i=0;i<this.width;i++)
			for(int j=0;j<this.height;j++)
				map[i][j]=new Cell(cellsType[i][j],wallsType[i][j]);
		int cnt=0;
		for(int i=0;i<this.width;i++)
			for(int j=0;j<this.height;j++)
				if(map[i][j].getType()==Judge.START_CELL){
					players[cnt].x=i;
					players[cnt].y=j;
					cnt++;
				}
	}
}

class Cell{
	int type;
	int walls;
	public Cell(int type,int walls){
		this.type=type;
		this.walls=walls;
	}
	
	public int getType(){
		return this.type;
	}
	
	public void setType(int type){
		this.type=type;
	}

	public boolean up(){
		return (this.walls%2==0);
	}
	
	public boolean right(){
		return ((this.walls/2)%2==0);
	}

	public boolean down(){
		return ((this.walls/4)%2==0);
	}

	public boolean left(){
		return ((this.walls/8)%2==0);
	}
}