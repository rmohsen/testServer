package Graphic;

import Logic.Judge;
import Logic.JudgeAbstract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SetPlayersPanel extends JPanel {
	private boolean enable;
	private JButton[] btns = new JButton[4];
	private JButton removeBtn;
	private JButton next;
	private int[] players = { JudgeAbstract.SAMAN, JudgeAbstract.JAFAR,
			JudgeAbstract.REZA, JudgeAbstract.HASIN };
	private String[] btnsString = { "add Saman", "add Jafar", "add Reza",
			"add Hasin" };

	public SetPlayersPanel() {
		enable = true;
		setLayout(null);
		setPreferredSize(new Dimension(MyFrame.WIDTH, MyFrame.HEIGHT));
		for (int i = 0; i < 4; i++) {
			btns[i] = new JButton(btnsString[i]);
			btns[i].addActionListener(new Graphic.setPlayersAddActionListener(
					players[i]));
			btns[i].setBounds(MyFrame.WIDTH - 120, 20 + 50 * i, 100, 30);
			add(btns[i]);
		}
		removeBtn = new JButton("remove");
		removeBtn.addActionListener(new Graphic.setPlayersRemoveActionListener());
		removeBtn.setBounds(MyFrame.WIDTH - 120, 220, 100, 30);
		add(removeBtn);
		next = new JButton("next");
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[][] cellsType = new int[MyFrame.ROW][MyFrame.COLUMN];
				int[][] wallsType = new int[MyFrame.ROW][MyFrame.COLUMN];
				Vector<Integer> tmp = new Vector<Integer>();
				for (int i = 0; i < MyFrame.ROW; i++)
					for (int j = 0; j < MyFrame.COLUMN; j++)
						if (MyFrame.cells[i][j].type == JudgeAbstract.START_CELL
								&& MyFrame.cells[i][j].players.size() > 0)
							tmp.add(MyFrame.cells[i][j].players.get(0));
				int[] players = new int[tmp.size()];
				for (int i = 0; i < tmp.size(); i++)
					players[i] = tmp.get(i);
				Judge judge = new Judge();
				judge.loadMap(cellsType, wallsType, players);
				enable = false;
				MyFrame.frame.setContentPane(new GamePanel(judge.getEveryThing().get(0), judge, false));
				MyFrame.frame.setVisible(true);
//				MyFrame.frame.setContentPane(new GamePanel(GameObjectID
//						.create(Player.class), new Judge(), false));
//				MyFrame.frame.setVisible(true);
			}
		});
		next.setBounds(MyFrame.WIDTH - 120, 270, 100, 30);
		add(next);

		Thread updateThread = new Thread() {
			@Override
			public void run() {
				while (enable) {
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
				MyFrame.cells[i][j].paint(g);
	}
}

class setPlayersAddActionListener implements ActionListener {
	private int player;

	public setPlayersAddActionListener(int player) {
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		outer: for (int i = 0; i < MyFrame.ROW; i++)
			for (int j = 0; j < MyFrame.COLUMN; j++)
				if (MyFrame.cells[i][j].type == JudgeAbstract.START_CELL
						&& MyFrame.cells[i][j].players.isEmpty()) {
					MyFrame.cells[i][j].players.add(player);
					break outer;
				}

	}

}

class setPlayersRemoveActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		outer: for (int i = MyFrame.ROW - 1; i >= 0; i--)
			for (int j = MyFrame.COLUMN - 1; j >= 0; j--)
				if (MyFrame.cells[i][j].type == JudgeAbstract.START_CELL
						&& !MyFrame.cells[i][j].players.isEmpty()) {
					MyFrame.cells[i][j].players.remove(0);
					break outer;
				}
	}

}
