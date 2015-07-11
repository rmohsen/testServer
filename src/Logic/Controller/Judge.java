package Logic.Controller;

import Logic.Common.exception.BozorgExceptionBase;
import Logic.Common.GameObjectID;
import Logic.Model.Fan;
import Logic.Model.Map;
import Logic.Model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Judge extends JudgeAbstract {

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
		return map.getWidth();
	}

	@Override
	public int getMapHeight() {
		return map.getHeight();
	}

	@Override
	public int getMapCellType(int col, int row) {
		if(map.getMap()[col][row].getType()==Judge.JJ_CELL)
			if(time%5000!=0)
				return Judge.NONE_CELL;
		return map.getMap()[col][row].getType();
	}

	@Override
	public int getMapCellType(int col, int row, GameObjectID player) throws BozorgExceptionBase {
		String s=row+","+col;
		if(getVision(player).contains(s)){
			if(map.getMap()[col][row].getType()==Judge.JJ_CELL)
				if(time%5000!=0)
					return Judge.NONE_CELL;
			if(map.getMap()[row][col].getType()<4)
				return map.getMap()[row][col].getType();
			return JudgeAbstract.BONUS_CELL;
		}
		return JudgeAbstract.DARK_CELL;
	}

	@Override
	public int getMapWallType(int col, int row) {
		return map.getMap()[col][row].getWalls();
	}

	@Override
	public int getMapWallType(int col, int row, GameObjectID player) throws BozorgExceptionBase {
		String s=row+","+col;
		if(getVision(player).contains(s)){
			return map.getMap()[row][col].getWalls();
		}
		return JudgeAbstract.XXXX_WALL;
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		for(int[] arr:AIvisionType)
			for(int i:arr)
				i=Judge.DARK_CELL;
		for(int[] arr:AIvisionWalls)
			for(int i:arr)
				i=Judge.XXXX_WALL;
	}

	@Override
	public void movePlayer(GameObjectID player, int direction)
			throws BozorgExceptionBase {
		Player p=(Player) objects.get(player.getNumber());
		int w=map.getMap()[p.x][p.y].getWalls();
		if(p.getHp()==0)
			throw new BozorgExceptionBase();
		if(direction<0 || direction>3)
			throw new BozorgExceptionBase();
		if((w>>direction)%2==1)
			throw new BozorgExceptionBase();
		if(p.getMoveTime()>0)
			throw new BozorgExceptionBase();
		p.setMoveTime(p.getSpeed() + p.getMoveTime());
		p.setMoveDir(direction);
	}

	@Override
	public void attack(GameObjectID attacker, int direction)
			throws BozorgExceptionBase {
		Player p=(Player) objects.get(attacker.getNumber());
		int w=map.getMap()[p.x][p.y].getWalls();
		if(p.getHp()==0)
			throw new BozorgExceptionBase();
		if(direction<0 || direction>4)
			throw new BozorgExceptionBase();
		if((w>>direction)%2==1)
			throw new BozorgExceptionBase();
		if(p.getAttackTime()<0)
			throw new BozorgExceptionBase();
		p.setAttackTime(p.getSpeed()+p.getAttackTime());
		p.setAttackDir(direction);
	}

	@Override
	public GameObjectID throwFan(GameObjectID player)
			throws BozorgExceptionBase {
		Player p=(Player) objects.get(player.getNumber());
		if(p.getHp()==0)
			throw new BozorgExceptionBase();
		if(p.getFans()==0)
			throw new BozorgExceptionBase();
		Fan f=new Fan(p.x,p.y,(Player)objects.get(player.getNumber()));
		objects.add(f);
		GameObjectID g=GameObjectID.create(f.getClass());
		f.id=g;
		objectsId.add(g);
		p.setFans(p.getFans()-1);
		return g;
	}

	@Override
	public void getGift(GameObjectID player) throws BozorgExceptionBase {
		Player p=(Player) objects.get(player.getNumber());
		if(p.getHp()==0)
			throw new BozorgExceptionBase();
		if(getMapCellType(p.x, p.y, player)!=BONUS_CELL)
			throw new BozorgExceptionBase();
		for(Player x:Judge.players){
			if(x.equals(p))
				continue;
			if(x.x==p.x && x.y==p.y)
				throw new BozorgExceptionBase();
		}
		int t=map.getMap()[p.x][p.y].getType();
		if(t==JUMP_CELL){
			p.setJump(p.getJump()+2000);
		}
		if(t==SPEEDUP_CELL){
			p.setSpeed(p.getSpeed()+5000);
		}
		if(t==STONE_CELL){
			p.setStun(p.getStun()+3000);
		}
		if(t==FAN_CELL){
			p.setFans(p.getFans()+3);
		}
		if(t==RADAR_CELL){
			p.setVision(p.getVision()+3000);
		}
		if(t==HOSPITAL_CELL){
			p.setHp(Math.min(p.getHp() + 20, 100));
		}
		map.getMap()[p.x][p.y].setType(0);
	}


	int[][] AIvisionType=new int[map.width][map.height];
	int[][] AIvisionWalls=new int[map.width][map.height];
	
	@Override
	public void AIByStudents(GameObjectID player) {
		boolean hasDark=false;
		int firstDir=4;
		for(int i=0;i<map.width;i++){
			for(int j=0;j<map.height;j++){
				try {
					if(this.getMapCellType(i, j, player)!=Judge.DARK_CELL)
						AIvisionType[i][j]=this.getMapCellType(i, j, player);
					else if(AIvisionType[i][j]==DARK_CELL)
						hasDark=true;
				} catch (BozorgExceptionBase e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if(this.getMapWallType(i, j, player)!=Judge.XXXX_WALL)
						AIvisionWalls[i][j]=this.getMapWallType(i, j, player);
				} catch (BozorgExceptionBase e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Player p=(Player) objects.get(player.getNumber());
		if(AIvisionType[p.x][p.y]==BONUS_CELL && (hasDark || p.hp<20))
			try {
				this.getGift(player);
			} catch (BozorgExceptionBase e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		ArrayList<Pair> cells=new ArrayList<Pair>();
		cells.add(new Pair(p.x, p.y,4));
		Pair tmp;
		if(p.attackTime==0)
			try {
				this.attack(player, NONE);
			} catch (BozorgExceptionBase e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		if(p.moveTime==0){
			for(int i=0;i<cells.size();i++){
				tmp=cells.get(i);
				if(AIvisionType[tmp.first][tmp.second]==JJ_CELL){
					try {
						this.movePlayer(player, tmp.third);
					} catch (BozorgExceptionBase e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				if(AIvisionType[tmp.first][tmp.second]==DARK_CELL && firstDir==4){
					firstDir=tmp.third;
				}
				if((AIvisionWalls[tmp.first][tmp.second]/1)%2==0){
					boolean contain=false;
					for(Pair pair:cells)
						if(pair.first==tmp.first-1 && pair.second==tmp.second)
							contain=true;
					if(!contain)
						if(tmp.third==4)
							cells.add(new Pair(tmp.first-1, tmp.second, UP));
						else
							cells.add(new Pair(tmp.first-1, tmp.second, tmp.third));
				}
				if((AIvisionWalls[tmp.first][tmp.second]/2)%2==0){
					boolean contain=false;
					for(Pair pair:cells)
						if(pair.first==tmp.first && pair.second==tmp.second+1)
							contain=true;
					if(!contain)
						if(tmp.third==4)
							cells.add(new Pair(tmp.first, tmp.second+1, RIGHT));
						else
							cells.add(new Pair(tmp.first, tmp.second+1, tmp.third));
				}
				if((AIvisionWalls[tmp.first][tmp.second]/4)%2==0){
					boolean contain=false;
					for(Pair pair:cells)
						if(pair.first==tmp.first+1 && pair.second==tmp.second)
							contain=true;
					if(!contain)
						if(tmp.third==4)
							cells.add(new Pair(tmp.first+1, tmp.second, DOWN));
						else
							cells.add(new Pair(tmp.first+1, tmp.second, tmp.third));
				}
				if((AIvisionWalls[tmp.first][tmp.second]/1/8)%2==0){
					boolean contain=false;
					for(Pair pair:cells)
						if(pair.first==tmp.first && pair.second==tmp.second-1)
							contain=true;
					if(!contain)
						if(tmp.third==4)
							cells.add(new Pair(tmp.first, tmp.second-1, LEFT));
						else
							cells.add(new Pair(tmp.first, tmp.second-1, tmp.third));
				}
			}
			try {
				this.movePlayer(player, firstDir);
			} catch (BozorgExceptionBase e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public ArrayList<GameObjectID> getEveryThing() {
		return objectsId;
	}

	@Override
	public ArrayList<String> getVision(GameObjectID player)
			throws BozorgExceptionBase {
//		if(!player.getClass().equals(Player.class))
//			throw new BozorgExceptionBase();
		if(((Player)(objects.get(player.getNumber()))).getHp()==0)
			throw new BozorgExceptionBase();
		if(((Player)objects.get(player.getNumber())).getVision()>0){
			ArrayList<String>ret=new ArrayList<String>();
			for(int i=0;i<map.getWidth();i++)
				for(int j=0;j<map.getHeight();j++)
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
		for(int i=Math.max(0,p.x-p.getVision());i<Math.min(map.getWidth(),p.x+p.getVision()+1);i++)
			for(int j=Math.max(0,p.y-p.getVision());j<Math.min(map.getHeight(),p.y+p.getVision()+1);j++)
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
			ret.put(JudgeAbstract.OWNER, f.owner.getName());
			if(f.isAlive)
				ret.put(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
			else
				ret.put(JudgeAbstract.IS_ALIVE, JudgeAbstract.DEAD);
			System.out.println("*****fan******");
//			System.out.println(objects.get(id.getNumber()).getClass().equals(Fan.class));
		}

		if(objects.get(id.getNumber()).getClass().equals(Player.class)){
			System.out.println(objects.get(id.getNumber()).getClass().equals(Player.class));
			Player p=(Player)objects.get(id.getNumber());
			ret.put(JudgeAbstract.ROW, p.x);
			System.out.println("*****Player******"+p.x+"  "+p.y+"  "+p.getName());
			ret.put(JudgeAbstract.COL, p.y);
			ret.put(JudgeAbstract.SPEED, p.getSpeed());
			ret.put(JudgeAbstract.NAME, p.getName());
			if(Judge.winner==4)
				ret.put(JudgeAbstract.IS_WINNER,JudgeAbstract.NOT_FINISHED);
			else if(Judge.winner==p.getName())
				ret.put(JudgeAbstract.IS_WINNER,JudgeAbstract.WINS);
			else
				ret.put(JudgeAbstract.IS_WINNER,JudgeAbstract.LOST);
			ret.put(JudgeAbstract.POWER, p.getPower());
			ret.put(JudgeAbstract.VISION, p.getVision());
			ret.put(JudgeAbstract.FANS,p.getFans());
			if(p.getHp()>0)
				ret.put(JudgeAbstract.IS_ALIVE, JudgeAbstract.ALIVE);
			else
				ret.put(JudgeAbstract.IS_ALIVE, JudgeAbstract.DEAD);
			ret.put(JudgeAbstract.HEALTH,(int)Math.max(p.getHp(),0));
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
				if(map.getMap()[p.x][p.y].getType()==JudgeAbstract.JJ_CELL){
					tmp++;
					winner=p.getName();
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
					if(p.getName()==infoValue)
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
					if(p.getHp()==0)
						p.setHp(100);
			if(infoKey.equals(JudgeAbstract.SPEED))
				p.setSpeed(infoValue);
			if(infoKey.equals(JudgeAbstract.NAME))
				p.setName(infoValue);
			if(infoKey.equals(JudgeAbstract.POWER))
				p.setPower(infoValue);
			if(infoKey.equals(JudgeAbstract.VISION))
				p.setVision(infoValue);
			if(infoKey.equals(JudgeAbstract.FANS))
				p.setFans(infoValue);
		}
	}

}

class Pair{
	public int first=0,second=0,third=0;
	public Pair(int first,int second,int third){
		this.first=first;
		this.second=second;
		this.third=third;
	}
}
