package Graphic;

import Logic.BozorgExceptionBase;
import Logic.GameObjectID;
import Logic.Judge;
import Logic.JudgeAbstract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel {
	private Judge judge;
	private boolean enable;
	public GameObjectID curPlayer;
	public GameObjectID lookingPlayer;
	public ArrayList<GameObjectID> allPlayers = new ArrayList<GameObjectID>();
	public static boolean isNetwork;
	public Cell[][] cells;
	private JComboBox<String> lookingPlayerCombo = new JComboBox<String>();
	private JButton attackBtn[] = new JButton[4];
	private JButton throwFanBtn;
	private JButton getGiftBtn;
	private String[] playersName = { "Saman", "Jafar", "Reza", "Hasin" };

	public GamePanel(GameObjectID curPlayer, Judge judge, boolean isNetwork) {
		this.curPlayer = curPlayer;
		this.judge = judge;
		this.isNetwork = isNetwork;
		enable = true;
		setLayout(null);
		setPreferredSize(new Dimension(MyFrame.WIDTH, MyFrame.HEIGHT));
		int row = this.judge.getMapHeight();
		int col = this.judge.getMapWidth();
		cells = new Cell[row][col];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				cells[i][j] = new Cell(j * Cell.WIDTH, i * Cell.HEIGHT,
						this.judge.getMapCellType(i, j),
						this.judge.getMapWallType(i, j));

			}
		lookingPlayerCombo.addItem("All");
		updateCells(true);
		lookingPlayerCombo.setBounds(MyFrame.WIDTH - 120, 20, 100, 30);
		add(lookingPlayerCombo);

		addThrowFanBtn();
		addAttackBtn();
		addKeyListener(new GameKeyListener(curPlayer, allPlayers, judge));
		setFocusable(true);

		Thread updateThread = new Thread() {
			@Override
			public void run() {
				while (enable) {
					update();
					repaint();

					try {
						Thread.sleep(MyFrame.UPDATE_INTERVAL);
					} catch (InterruptedException ignore) {
					}

				}

			}
		};
		updateThread.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		for (int i = 0; i < MyFrame.ROW; i++)
			for (int j = 0; j < MyFrame.COLUMN; j++)
				cells[i][j].paint(g);
	}

	public void update() {
		for (GameObjectID go : allPlayers) {
			try {
				if (playersName[judge.getInfo(go).get(JudgeAbstract.NAME)]
						.equals(lookingPlayerCombo.getSelectedItem())) {
					lookingPlayer = go;
				}
			} catch (BozorgExceptionBase e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (lookingPlayerCombo.getSelectedItem().equals("All"))
			lookingPlayer = null;
		int row = this.judge.getMapHeight();
		int col = this.judge.getMapWidth();
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				if (lookingPlayer != null) {
					try {
						cells[i][j].type = this.judge.getMapCellType(j, i,
								lookingPlayer);
						cells[i][j].wallType = this.judge.getMapWallType(j, i,
								lookingPlayer);
					} catch (BozorgExceptionBase bozorgExceptionBase) {
						bozorgExceptionBase.printStackTrace();
					}
				} else {
					cells[i][j].type = this.judge.getMapCellType(i, j);
					cells[i][j].wallType = this.judge.getMapWallType(i, j);
				}
			}
		updateCells(false);
		
	}

	private void addThrowFanBtn() {
		int dirs[] = { JudgeAbstract.UP, JudgeAbstract.DOWN,
				JudgeAbstract.RIGHT, JudgeAbstract.LEFT };
		String strs[] = { "Up", "Down", "Right", "Left" };
		for (int i = 0; i < 4; i++) {
			attackBtn[i] = new JButton(strs[i]);
			attackBtn[i].addActionListener(new Graphic.dirActionListener(
					dirs[i], this.judge, curPlayer));
			attackBtn[i].setBounds(MyFrame.WIDTH - 120, 70 + 50 * i, 100, 30);
			add(attackBtn[i]);
		}
		throwFanBtn = new JButton("Trow Fan");
		throwFanBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					judge.throwFan(curPlayer);
				} catch (BozorgExceptionBase e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		throwFanBtn.setBounds(MyFrame.WIDTH - 120, 270, 100, 30);
		add(throwFanBtn);
	}

	private void addAttackBtn() {
		getGiftBtn = new JButton("Get Gift");
		getGiftBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					judge.getGift(curPlayer);
				} catch (BozorgExceptionBase e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		getGiftBtn.setBounds(MyFrame.WIDTH - 120, 320, 100, 30);
		add(getGiftBtn);
	}

	private void updateCells(boolean withCombo) {
		for (GameObjectID gameObject : this.judge.getEveryThing()) {
			try {
				if (this.judge.getInfo(gameObject).get(JudgeAbstract.IS_ALIVE)
						.equals(JudgeAbstract.ALIVE)) {
					int gbrow = this.judge.getInfo(gameObject).get(
							JudgeAbstract.ROW);
					int gbcol = this.judge.getInfo(gameObject).get(
							JudgeAbstract.COL);
					int[] existPlayers = { JudgeAbstract.SAMAN,
							JudgeAbstract.JAFAR, JudgeAbstract.REZA,
							JudgeAbstract.HASIN };
					for (int i = 0; i < 4; i++) {
						if (this.judge.getInfo(gameObject).get(
								JudgeAbstract.NAME) != null)
							if (this.judge.getInfo(gameObject)
									.get(JudgeAbstract.NAME)
									.equals(existPlayers[i])) {
								if (withCombo) {
									lookingPlayerCombo
											.addItem(playersName[existPlayers[i]]);
									allPlayers.add(gameObject);
								}
								cells[gbrow][gbcol].players.clear();
								cells[gbrow][gbcol].players
										.add(existPlayers[i]);
							} else if (this.judge.getInfo(gameObject).get(
									JudgeAbstract.OWNER) != null) {
								if (this.judge.getInfo(gameObject)
										.get(JudgeAbstract.OWNER)
										.equals(existPlayers[i])) {
									cells[gbrow][gbcol].fans.clear();
									cells[gbrow][gbcol].fans
											.add(existPlayers[i]);
								}
							}
					}
				}
			} catch (BozorgExceptionBase e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class dirActionListener implements ActionListener {
	private int dir;
	private Judge judge;
	private GameObjectID curPlayer;

	public dirActionListener(int dir, Judge judge, GameObjectID curPlayer) {
		this.dir = dir;
		this.judge = judge;
		this.curPlayer = curPlayer;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			judge.attack(curPlayer, dir);
		} catch (BozorgExceptionBase e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
