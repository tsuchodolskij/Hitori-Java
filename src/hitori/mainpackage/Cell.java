package hitori.mainpackage;

import javax.swing.JButton;

public class Cell extends JButton {

	private static final long serialVersionUID = -1650306087968649758L;
	
	int value;
	
	public Cell(int value) {
		super(Integer.toString(value), null);
		this.value = value;
	}
	
}
