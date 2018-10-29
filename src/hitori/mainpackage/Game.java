package hitori.mainpackage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6428442114002141800L;

	public void display(int gridSize) {
		setSize(540,620);
		JPanel regPanel = new JPanel();
		add(regPanel);
		regPanel.setLayout(null);
		setTitle("Hitori");
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


}
