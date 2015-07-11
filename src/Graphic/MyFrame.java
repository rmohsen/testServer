package Graphic;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Logic.Controller.JudgeAbstract;

public class MyFrame extends JFrame {
	public static Graphic.MyFrame frame;
	public static int WIDTH = 800;
	public static int HEIGHT = 800;
	public static int ROW;
	public static int COLUMN;
	public static Cell[][] cells;
	public static int BORDER = 20;
	public static final int UPDATE_INTERVAL = 100;
	
	public MyFrame() {
		frame = this;
		ROW = Integer.valueOf(JOptionPane.showInputDialog("Enter Row: "));
		COLUMN = Integer.valueOf(JOptionPane.showInputDialog("Enter Column: "));
		Cell.WIDTH = (WIDTH - 120 - 2 * BORDER) / COLUMN;
		Cell.HEIGHT = (HEIGHT - 120 - 2 * BORDER) / ROW;
		cells = new Cell[ROW][COLUMN];
		for (int i = 0; i < ROW; i++)
			for (int j = 0; j < COLUMN; j++)
				cells[i][j] = new Cell(BORDER + j * Cell.WIDTH, BORDER + i
						* Cell.HEIGHT, JudgeAbstract.NONE_CELL,
						JudgeAbstract.FFFF_WALL);
		cells[0][0].wallType = JudgeAbstract.TFFT_WALL;
		cells[0][COLUMN - 1].wallType = JudgeAbstract.FFTT_WALL;
		cells[ROW - 1][0].wallType = JudgeAbstract.TTFF_WALL;
		cells[ROW - 1][COLUMN - 1].wallType = JudgeAbstract.FTTF_WALL;
		for (int i = 1; i < COLUMN - 1; i++)
			cells[0][i].wallType = JudgeAbstract.FFFT_WALL;
		for (int i = 1; i < COLUMN - 1; i++)
			cells[ROW - 1][i].wallType = JudgeAbstract.FTFF_WALL;
		for (int i = 1; i < ROW - 1; i++)
			cells[i][0].wallType = JudgeAbstract.TFFF_WALL;
		for (int i = 1; i < ROW - 1; i++)
			cells[i][COLUMN - 1].wallType = JudgeAbstract.FFTF_WALL;
		setContentPane(new SetWallsPanel());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("bozorg");
		setVisible(true);

		// be centered!
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x2 = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y2 = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x2, y2);
	}

	public static int calc(int x, int n) {
		int p = (int) Math.pow(2, n - 1);
		x /= p;
		return x % 2;
	}

	public static int change(int x, int n) {
		int p = (int) Math.pow(2, n - 1);
		int i = calc(x, n);
		if (i == 0)
			return x + p;
		else
			return x - p;

	}
}
