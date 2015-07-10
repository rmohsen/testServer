package bozorg.graphic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import bozorg.common.GameObjectID;
import bozorg.common.exceptions.BozorgExceptionBase;
import bozorg.judge.Judge;
import bozorg.judge.JudgeAbstract;

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

	public GamePanel(GameObjectID curPlayer, Judge judge,boolean isNetwork) {
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
				cells[i][j].type = this.judge.getMapCellType(i, j);
				cells[i][j].wallType = this.judge.getMapWallType(i, j);
			}
		lookingPlayerCombo.addItem("all");
		for (GameObjectID gameObject : this.judge.getEveryThing()) {
			try {
				if (this.judge.getInfo(gameObject).get("IS_ALIVE")
						.equals("ALIVE")) {
					int gbrow = this.judge.getInfo(gameObject).get("ROW");
					int gbcol = this.judge.getInfo(gameObject).get("COL");
					if (this.judge.getInfo(gameObject).get("NAME")
							.equals("SAMAN")) {
						lookingPlayerCombo.addItem("SAMAN");
						allPlayers.add(gameObject);
						cells[gbrow][gbcol].players.add(JudgeAbstract.SAMAN);
					} else if (this.judge.getInfo(gameObject).get("NAME")
							.equals("JAFAR")) {
						lookingPlayerCombo.addItem("JAFAR");
						allPlayers.add(gameObject);
						cells[gbrow][gbcol].players.add(JudgeAbstract.JAFAR);
					} else if (this.judge.getInfo(gameObject).get("NAME")
							.equals("REZA")) {
						lookingPlayerCombo.addItem("REZA");
						allPlayers.add(gameObject);
						cells[gbrow][gbcol].players.add(JudgeAbstract.REZA);
					} else if (this.judge.getInfo(gameObject).get("NAME")
							.equals("HASIN")) {
						lookingPlayerCombo.addItem("HASIN");
						allPlayers.add(gameObject);
						cells[gbrow][gbcol].players.add(JudgeAbstract.HASIN);
					}
					if (this.judge.getInfo(gameObject).get("OWNER")
							.equals("SAMAN")) {
						cells[gbrow][gbcol].fans.add(JudgeAbstract.SAMAN);
					} else if (this.judge.getInfo(gameObject).get("OWNER")
							.equals("JAFAR")) {
						cells[gbrow][gbcol].fans.add(JudgeAbstract.JAFAR);
					} else if (this.judge.getInfo(gameObject).get("OWNER")
							.equals("REZA")) {
						cells[gbrow][gbcol].fans.add(JudgeAbstract.REZA);
					} else if (this.judge.getInfo(gameObject).get("OWNER")
							.equals("HASIN")) {
						cells[gbrow][gbcol].fans.add(JudgeAbstract.HASIN);
					}
				}
			} catch (BozorgExceptionBase e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lookingPlayerCombo.setBounds(MyFrame.WIDTH - 120, 20, 100, 30);
		add(lookingPlayerCombo);
		int dirs[] = { JudgeAbstract.UP, JudgeAbstract.DOWN,
				JudgeAbstract.RIGHT, JudgeAbstract.LEFT };
		String strs[] = { "Up", "Down", "Right", "Left" };
		for (int i = 0; i < 4; i++) {
			attackBtn[i] = new JButton(strs[i]);
			attackBtn[i].addActionListener(new bozorg.graphic.dirActionListener(dirs[i],
					this.judge, curPlayer));
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
		for(GameObjectID go : allPlayers){
			try {
				if(judge.getInfo(go).get("NAME").equals(lookingPlayerCombo.getSelectedItem())){
					lookingPlayer = go;
				}
			} catch (BozorgExceptionBase e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int row = this.judge.getMapHeight();
		int col = this.judge.getMapWidth();
		cells = new Cell[row][col];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				if (lookingPlayer != null) {
					cells[i][j].type = this.judge.getMapCellType(i, j,
							lookingPlayer);
					cells[i][j].wallType = this.judge.getMapWallType(i, j,
							lookingPlayer);
				} else {
					cells[i][j].type = this.judge.getMapCellType(i, j);
					cells[i][j].wallType = this.judge.getMapWallType(i, j);
				}
			}
		for (GameObjectID gameObject : this.judge.getEveryThing()) {
			try {
				if (this.judge.getInfo(gameObject).get("IS_ALIVE")
						.equals("ALIVE")) {
					int gbrow = this.judge.getInfo(gameObject).get("ROW");
					int gbcol = this.judge.getInfo(gameObject).get("COL");
					if (this.judge.getInfo(gameObject).get("NAME")
							.equals("SAMAN")) {
						cells[gbrow][gbcol].players.clear();
						cells[gbrow][gbcol].players.add(JudgeAbstract.SAMAN);
					} else if (this.judge.getInfo(gameObject).get("NAME")
							.equals("JAFAR")) {
						cells[gbrow][gbcol].players.clear();
						cells[gbrow][gbcol].players.add(JudgeAbstract.JAFAR);
					} else if (this.judge.getInfo(gameObject).get("NAME")
							.equals("REZA")) {
						cells[gbrow][gbcol].players.clear();
						cells[gbrow][gbcol].players.add(JudgeAbstract.REZA);
					} else if (this.judge.getInfo(gameObject).get("NAME")
							.equals("HASIN")) {
						cells[gbrow][gbcol].players.clear();
						cells[gbrow][gbcol].players.add(JudgeAbstract.HASIN);
					}
					if (this.judge.getInfo(gameObject).get("OWNER")
							.equals("SAMAN")) {
						cells[gbrow][gbcol].fans.clear();
						cells[gbrow][gbcol].fans.add(JudgeAbstract.SAMAN);
					} else if (this.judge.getInfo(gameObject).get("OWNER")
							.equals("JAFAR")) {
						cells[gbrow][gbcol].fans.clear();
						cells[gbrow][gbcol].fans.add(JudgeAbstract.JAFAR);
					} else if (this.judge.getInfo(gameObject).get("OWNER")
							.equals("REZA")) {
						cells[gbrow][gbcol].fans.clear();
						cells[gbrow][gbcol].fans.add(JudgeAbstract.REZA);
					} else if (this.judge.getInfo(gameObject).get("OWNER")
							.equals("HASIN")) {
						cells[gbrow][gbcol].fans.clear();
						cells[gbrow][gbcol].fans.add(JudgeAbstract.HASIN);
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
