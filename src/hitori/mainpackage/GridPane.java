package hitori.mainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

class GridPane extends JPanel {

	private static final long serialVersionUID = 5376889337682664210L;

	public GridPane(int gridSize) {
		int squaredSize = gridSize * gridSize;
		Cell cellGrid[] = new Cell[squaredSize];
		setLayout(new GridLayout(gridSize, gridSize));
	
		for (int i = 0; i < squaredSize; i++) {
			cellGrid[i] = new Cell(i+1);
			cellGrid[i].setBackground(Color.WHITE);
			cellGrid[i].setBorder(new LineBorder(Color.BLACK, 1));
			add(cellGrid[i]);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int paneWidth = (int) screenSize.width * 3 / 4;
		int paneHeight = screenSize.height - 48;
		return new Dimension(paneWidth, paneHeight);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
}