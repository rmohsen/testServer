package bozorg.judge;

import java.util.ArrayList;
import java.util.HashMap;

import bozorg.common.GameObjectID;
import bozorg.common.exceptions.BozorgExceptionBase;

public class Judge extends JudgeAbstract{

	@Override
	public ArrayList<GameObjectID> loadMap(int[][] cellsType,
			int[][] wallsType, int[] players) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMapWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMapHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMapCellType(int col, int row) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMapCellType(int col, int row, GameObjectID player) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMapWallType(int col, int row) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMapWallType(int col, int row, GameObjectID player) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movePlayer(GameObjectID player, int direction)
			throws BozorgExceptionBase {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack(GameObjectID attacker, int direction)
			throws BozorgExceptionBase {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameObjectID throwFan(GameObjectID player)
			throws BozorgExceptionBase {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getGift(GameObjectID player) throws BozorgExceptionBase {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AIByStudents(GameObjectID player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<GameObjectID> getEveryThing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getVision(GameObjectID player)
			throws BozorgExceptionBase {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<GameObjectID> getPlayersInVision(GameObjectID player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<GameObjectID> getFans(GameObjectID player)
			throws BozorgExceptionBase {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Integer> getInfo(GameObjectID id)
			throws BozorgExceptionBase {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void next50milis() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateInfo(GameObjectID id, String infoKey, Integer infoValue)
			throws BozorgExceptionBase {
		// TODO Auto-generated method stub
		
	}

}
