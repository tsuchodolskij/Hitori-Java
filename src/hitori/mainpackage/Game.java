package hitori.mainpackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame {

	private static final long serialVersionUID = -6428442114002141800L;

	public Game() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setLayout(new BorderLayout());
		setTitle("Hitori");
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void buildGrid(int gridSize) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel gamePane = new JPanel(new GridBagLayout());
		gamePane.setSize(screenSize.width, screenSize.height - 48);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
        gbc.gridy = 0;
        gamePane.add(new GridPane(gridSize), gbc);
        gbc.gridx++;
        gamePane.add(new SettingsPane(gridSize), gbc);
        add(gamePane);
	}
}
