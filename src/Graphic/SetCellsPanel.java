package Graphic;

import Logic.JudgeAbstract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetCellsPanel extends JPanel {
	private boolean enable;
	private JButton next;
	private JButton[] btns = new JButton[9];
	private int[] cellTypes = { JudgeAbstract.NONE_CELL, JudgeAbstract.JJ_CELL,
			JudgeAbstract.START_CELL, JudgeAbstract.SPEEDUP_CELL,
			JudgeAbstract.RADAR_CELL, JudgeAbstract.STONE_CELL,
			JudgeAbstract.JUMP_CELL, JudgeAbstract.FAN_CELL,
			JudgeAbstract.HOSPITAL_CELL };
	private String[] btnsStrings = { "none Cell", "jj Cell", "start Cell",
			"speedUp Cell", "radar Cell", "stone Cell", "jump Cell",
			"fan Cell", "hospital Cell" };

	public SetCellsPanel() {
		enable = true;
		setLayout(null);
		setPreferredSize(new Dimension(MyFrame.WIDTH, MyFrame.HEIGHT));
		for (int i = 0; i < 9; i++) {
			btns[i] = new JButton(btnsStrings[i]);
			btns[i].addActionListener(new MySetCellsActionListener(cellTypes[i]));
			btns[i].setBounds(MyFrame.WIDTH - 120, 20 + 50 * i, 100, 30);
			add(btns[i]);
		}
		next = new JButton("next");
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				enable = false;
				MyFrame.frame.setContentPane(new SetPlayersPanel());
				MyFrame.frame.setVisible(true);
			}
		});
		next.setBounds(MyFrame.WIDTH - 120, 470, 100, 30);
		add(next);
		addMouseListener(new SetCellsMouseListener());
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
