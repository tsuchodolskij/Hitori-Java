package hitori.mainpackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class Game {

	private static JFrame gameFrame;
	
	private Game() {
		
	}

	public static JFrame getGameFrame() {
		return gameFrame;
	}
	
	public static void buildGrid(int gridSize) {
		gameFrame = new JFrame();
		gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameFrame.setVisible(true);
		gameFrame.setLayout(new BorderLayout());
		gameFrame.setTitle("Hitori");
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel gamePane = new JPanel(new GridBagLayout());
		JPanel gridPane = new GridPane(gridSize);
		JPanel settingsPane = new SettingsPane(gridSize);
		GridBagConstraints gbc = new GridBagConstraints();
		gamePane.setSize(screenSize.width, screenSize.height - 48);
		gbc.gridx = 0;
        gbc.gridy = 0;
        gamePane.add(gridPane, gbc);
        gbc.gridx++;
        gamePane.add(settingsPane, gbc);
        gameFrame.add(gamePane);
	}
}
