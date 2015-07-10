package bozorg.graphic;

import java.awt.Color;
import java.awt.Graphics;

public class Player implements Painable{
	protected int X;
	protected int Y;
	protected int width;
	protected int height;
	private int type;
	private Color[] colors = {Color.YELLOW,Color.WHITE,Color.RED,Color.GREEN};
	public Player(int X,int Y,int width,int height,int type) {
		this.X = X;
		this.Y = Y;
		this.width = width;
		this.height = height;
		this.type = type;
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(colors[type]);
		g.fillOval(X, Y, width, height);
		g.setColor(colors[3-type]);
		g.drawOval(X, Y, width, height);
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
