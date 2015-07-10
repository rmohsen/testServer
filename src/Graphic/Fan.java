package Graphic;

import java.awt.Color;
import java.awt.Graphics;

public class Fan implements Painable {
	protected int X;
	protected int Y;
	protected int width;
	protected int height;
	private int type;
	private Color[] colors = { Color.YELLOW, Color.WHITE, Color.RED,
			Color.GREEN };

	public Fan(int X, int Y, int width, int height, int type) {
		this.X = X;
		this.Y = Y;
		this.width = width;
		this.height = height;
		this.type = type;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(colors[type]);
		g.fillOval(X + width / 4, Y + height / 4, width / 2, height / 2);
		g.setColor(colors[3 - type]);
		g.drawOval(X + width / 4, Y + height / 4, width / 2, height / 2);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
