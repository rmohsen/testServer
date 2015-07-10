package bozorg.graphic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import bozorg.judge.JudgeAbstract;

public class SetCellsMouseListener implements MouseListener{
	public static int CellType = JudgeAbstract.NONE_CELL;
	@Override
	public void mouseClicked(MouseEvent arg0) {
		for (int i = 0; i < MyFrame.ROW; i++)
			for (int j = 0; j < MyFrame.COLUMN; j++) {

				if ((MyFrame.cells[i][j].X < arg0.getX())
						&& (arg0.getX() < MyFrame.cells[i][j].X + Cell.WIDTH)
						&& (MyFrame.cells[i][j].Y < arg0.getY())
						&& (arg0.getY() < MyFrame.cells[i][j].Y + Cell.HEIGHT)) {
					MyFrame.cells[i][j].type = CellType;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
