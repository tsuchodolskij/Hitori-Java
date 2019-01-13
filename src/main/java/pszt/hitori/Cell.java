package pszt.hitori;

import javax.swing.JButton;

public class Cell extends JButton {

	private static final long serialVersionUID = -1650306087968649758L;
	
	private final int value;
	private final int xCoordinate;
	private final int yCoordinate;
	private boolean pressed;

	public int getValue() {
		return value;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}
	
	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public Cell(int value, int xCoordinate, int yCoordinate) {
		super(Integer.toString(value), null);
		this.value = value;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	
}
