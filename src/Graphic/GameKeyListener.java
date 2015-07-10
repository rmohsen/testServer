package Graphic;

import Logic.BozorgExceptionBase;
import Logic.GameObjectID;
import Logic.Judge;
import Logic.JudgeAbstract;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameKeyListener implements KeyListener {
	public GameObjectID curPlayer;
	private Judge judge;
	ArrayList<GameObjectID> players;

	public GameKeyListener(GameObjectID curPlayer,
			ArrayList<GameObjectID> players, Judge judge) {
		this.curPlayer = curPlayer;
		this.judge = judge;
		this.players = players;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		for (GameObjectID go : players)
			if (go == curPlayer)
				players.remove(go);
		try {
			if (arg0.getKeyChar() == 'a')
				judge.movePlayer(curPlayer, JudgeAbstract.LEFT);
			else if (arg0.getKeyChar() == 'w')
				judge.movePlayer(curPlayer, JudgeAbstract.UP);

			else if (arg0.getKeyChar() == 'd')
				judge.movePlayer(curPlayer, JudgeAbstract.RIGHT);

			else if (arg0.getKeyChar() == 's')
				judge.movePlayer(curPlayer, JudgeAbstract.DOWN);
			//
			if (GamePanel.isNetwork) {
				if (players.size() > 0) {
					if (arg0.getKeyChar() == 'j')
						judge.movePlayer(players.get(0), JudgeAbstract.LEFT);
					else if (arg0.getKeyChar() == 'i')
						judge.movePlayer(players.get(0), JudgeAbstract.UP);

					else if (arg0.getKeyChar() == 'l')
						judge.movePlayer(players.get(0), JudgeAbstract.RIGHT);

					else if (arg0.getKeyChar() == 'k')
						judge.movePlayer(players.get(0), JudgeAbstract.DOWN);
				}

				//
				if (players.size() > 1) {
					if (arg0.getKeyChar() == '4')
						judge.movePlayer(players.get(1), JudgeAbstract.LEFT);
					else if (arg0.getKeyChar() == '8')
						judge.movePlayer(players.get(1), JudgeAbstract.UP);

					else if (arg0.getKeyChar() == '6')
						judge.movePlayer(players.get(1), JudgeAbstract.RIGHT);

					else if (arg0.getKeyChar() == '2')
						judge.movePlayer(players.get(1), JudgeAbstract.DOWN);
				}

				//
				if (players.size() > 2) {
					if (arg0.getKeyCode() == KeyEvent.VK_LEFT)
						judge.movePlayer(players.get(0), JudgeAbstract.LEFT);
					else if (arg0.getKeyCode() == KeyEvent.VK_UP)
						judge.movePlayer(players.get(2), JudgeAbstract.UP);

					else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT)
						judge.movePlayer(players.get(2), JudgeAbstract.RIGHT);

					else if (arg0.getKeyCode() == KeyEvent.VK_DOWN)
						judge.movePlayer(players.get(2), JudgeAbstract.DOWN);
				}

			}
		} catch (BozorgExceptionBase e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
