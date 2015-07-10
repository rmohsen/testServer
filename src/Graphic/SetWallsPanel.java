package Graphic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SetWallsPanel extends JPanel {
	private boolean enable;
	private JButton upWallBtn;
	private JButton downWallBtn;
	private JButton rightWallBtn;
	private JButton leftWallBtn;
	private JButton next;

	public SetWallsPanel() {
		enable = true;
		setLayout(null);
		setPreferredSize(new Dimension(MyFrame.WIDTH, MyFrame.HEIGHT));
		upWallBtn = new JButton("Up Wall");
		upWallBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameMode.gamemode = GameMode.SET_UP_WALL_MODE;
				GameMode.nextgamemode = GameMode.SET_DOWN_WALL_MODE;

			}
		});
		upWallBtn.setBounds(MyFrame.WIDTH - 120, 20, 100, 30);
		add(upWallBtn);
		downWallBtn = new JButton("Down Wall");
		downWallBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameMode.gamemode = GameMode.SET_DOWN_WALL_MODE;
				GameMode.nextgamemode = GameMode.SET_UP_WALL_MODE;


			}
		});
		downWallBtn.setBounds(MyFrame.WIDTH - 120, 70, 100, 30);
		add(downWallBtn);
		rightWallBtn = new JButton("Right Wall");
		rightWallBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameMode.gamemode = GameMode.SET_RIGHT_WALL_MODE;
				GameMode.nextgamemode = GameMode.SET_LEFT_WALL_MODE;


			}
		});
		rightWallBtn.setBounds(MyFrame.WIDTH - 120, 120, 100, 30);
		add(rightWallBtn);
		leftWallBtn = new JButton("Left Wall");
		leftWallBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameMode.gamemode = GameMode.SET_LEFT_WALL_MODE;
				GameMode.nextgamemode = GameMode.SET_RIGHT_WALL_MODE;


			}
		});
		leftWallBtn.setBounds(MyFrame.WIDTH - 120, 170, 100, 30);
		add(leftWallBtn);

		next = new JButton("next");
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				enable = false;
				JPanel panel = new SetCellsPanel();
				MyFrame.frame.setContentPane(panel);
				MyFrame.frame.setVisible(true);
			}
		});
		next.setBounds(MyFrame.WIDTH - 120, 220, 100, 30);
		add(next);
		
		GameMode.gamemode = GameMode.SET_UP_WALL_MODE;
		addMouseListener(new SetWallsMouseListener());

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
