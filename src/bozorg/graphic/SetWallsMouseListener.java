package bozorg.graphic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SetWallsMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		for (int i = 0; i < MyFrame.ROW; i++)
			for (int j = 0; j < MyFrame.COLUMN; j++) {

				if ((MyFrame.cells[i][j].X < arg0.getX())
						&& (arg0.getX() < MyFrame.cells[i][j].X + Cell.WIDTH)
						&& (MyFrame.cells[i][j].Y < arg0.getY())
						&& (arg0.getY() < MyFrame.cells[i][j].Y + Cell.HEIGHT)) {
					MyFrame.cells[i][j].wallType = MyFrame.change(
							MyFrame.cells[i][j].wallType, GameMode.gamemode);
					if (GameMode.gamemode == GameMode.SET_UP_WALL_MODE && i > 0)
						MyFrame.cells[i - 1][j].wallType = MyFrame.change(
								MyFrame.cells[i - 1][j].wallType,
								GameMode.nextgamemode);
					else if (GameMode.gamemode == GameMode.SET_DOWN_WALL_MODE
							&& i < MyFrame.ROW - 1)
						MyFrame.cells[i + 1][j].wallType = MyFrame.change(
								MyFrame.cells[i + 1][j].wallType,
								GameMode.nextgamemode);
					else if (GameMode.gamemode == GameMode.SET_RIGHT_WALL_MODE
							&& j < MyFrame.COLUMN - 1)
						MyFrame.cells[i][j + 1].wallType = MyFrame.change(
								MyFrame.cells[i][j + 1].wallType,
								GameMode.nextgamemode);
					else if (GameMode.gamemode == GameMode.SET_LEFT_WALL_MODE
							&& j > 0)
						MyFrame.cells[i][j - 1].wallType = MyFrame.change(
								MyFrame.cells[i][j - 1].wallType,
								GameMode.nextgamemode);
				}
			}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
