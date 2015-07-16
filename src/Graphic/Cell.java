package Graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Cell implements Painable {
	public int X;
	public int Y;
	public int type;
	public int wallType;
	public ArrayList<Integer> players = new ArrayList<Integer>();
	public ArrayList<Player> PlayersArray = new ArrayList<Player>();
	public ArrayList<Integer> fans = new ArrayList<Integer>();
	public ArrayList<Fan> fansArray = new ArrayList<Fan>();
	public static int WIDTH;
	public static int HEIGHT;
	private static int BORDER = 1;
	private Color[] colors = { Color.BLUE, Color.CYAN, Color.DARK_GRAY,
			Color.gray, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
			Color.ORANGE, Color.PINK, Color.RED, Color.WHITE };

	public Cell(int X, int Y, int type, int wallType) {
		this.X = X;
		this.Y = Y;
		this.type = type;
		this.wallType = wallType;
	}

	@Override
	public void paint(Graphics g) {
		PlayersArray.clear();
		fansArray.clear();
		g.setColor(colors[type]);
		g.fillRect(X + BORDER, Y + BORDER, WIDTH - 2 * BORDER, HEIGHT - 2
				* BORDER);
		paintUp(g, MyFrame.calc(wallType, 1));
		paintRight(g, MyFrame.calc(wallType, 2));
		paintDown(g, MyFrame.calc(wallType, 3));
		paintLeft(g, MyFrame.calc(wallType, 4));
		if (players.size() + fans.size() > 0) {
			int num = (int) Math.ceil(Math.sqrt(players.size() + fans.size()));
			int width = WIDTH / num;
			int height = HEIGHT / num;
			int x = 0;
			int y = 0;
			for (int player : players) {
				PlayersArray
						.add(new Player(X + x, Y + y, width, height, player));
				x += width;
				y += (x / WIDTH);
				x %= WIDTH;
			}
			for (int fan : fans) {
				fansArray.add(new Fan(X + x, Y + y, width, height, fan));
				x += width;
				y += (x / WIDTH);
				x %= WIDTH;
			}
			for (Player player : PlayersArray)
				player.paint(g);
			for (Fan fan : fansArray)
				fan.paint(g);
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	private void paintUp(Graphics g, int type) {
		if (type == 1)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.WHITE);
		g.fillRect(X, Y, WIDTH, BORDER);
		;
	}

	private void paintRight(Graphics g, int type) {
		if (type == 1)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.WHITE);
		g.fillRect(X + WIDTH - BORDER, Y, BORDER, HEIGHT);
	}

	private void paintDown(Graphics g, int type) {
		if (type == 1)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.WHITE);
		g.fillRect(X, Y + HEIGHT - BORDER, WIDTH, BORDER);
	}

	private void paintLeft(Graphics g, int type) {
		if (type == 1)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.WHITE);
		g.fillRect(X, Y, BORDER, HEIGHT);
	}
}
