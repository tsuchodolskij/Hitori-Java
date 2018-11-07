package hitori.mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Game extends JFrame {

	private static final long serialVersionUID = -6428442114002141800L;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	class gridPane extends JPanel {

		private static final long serialVersionUID = 5376889337682664210L;

			public gridPane(int gridSize) {
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
		
	class settingsPane extends JPanel {

		private static final long serialVersionUID = 1316360567254798368L;

			public settingsPane() {
				
	        }

	        @Override
	        public Dimension getPreferredSize() {
	        	int paneWidth = (int) screenSize.width / 4;
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
		JPanel gamePane = new JPanel(new GridBagLayout());
		gamePane.setSize(screenSize.width, screenSize.height - 48);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
        gbc.gridy = 0;
        gamePane.add(new gridPane(gridSize), gbc);
        gbc.gridx++;
        gamePane.add(new settingsPane(), gbc);
        add(gamePane);
	}
}
