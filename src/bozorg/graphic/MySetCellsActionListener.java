package bozorg.graphic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MySetCellsActionListener implements ActionListener {
	private int index;

	public MySetCellsActionListener(int index) {
		this.index = index;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		SetCellsMouseListener.CellType = index;
	}

}
