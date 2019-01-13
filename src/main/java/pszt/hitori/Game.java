package pszt.hitori;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public final class Game {

	private static JFrame gameFrame;
	private static SettingsPane s;
	
	private Game() {
		
	}

	public static JFrame getGameFrame() {
		return gameFrame;
	}
	
	public static void buildGrid(int gridSize, MapLoader loader) {
		s = new SettingsPane(gridSize);
		
		gameFrame = new JFrame();
		gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameFrame.setVisible(true);
		gameFrame.setLayout(new BorderLayout());
		gameFrame.setTitle("Hitori");
		gameFrame.setResizable(true);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel gamePane = new JPanel(new GridBagLayout());
		JPanel gridPane;
		if (loader != null) gridPane = new GridPane(gridSize, s, loader);
		else gridPane = new GridPane(gridSize, s, null);
		JPanel settingsPane = s;
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
