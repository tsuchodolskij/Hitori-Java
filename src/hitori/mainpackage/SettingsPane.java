package hitori.mainpackage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

class SettingsPane extends JPanel {

	private static final long serialVersionUID = 1316360567254798368L;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//private String errorMsg;
	private String[] gridSizeList = {"", "4x4", "5x5", "6x6", "7x7", "8x8", "9x9", "10x10",
			"11x11", "12x12", "13x13", "14x14", "15x15", "16x16", "17x17", "18x18", "19x19", "20x20",
			"21x21", "22x22", "23x23", "24x24", "25x25", "26x26", "27x27", "28x28", "29x29", "30x30",
			"31x31", "32x32", "33x33", "34x34", "35x35", "36x36", "37x37", "38x38", "39x39", "40x40"};
	
	JLabel status = new JLabel("Game Status");
	JLabel gridSizeLabel = new JLabel("Choose the grid size:");
	//JLabel errorLabel = new JLabel(errorMsg);
	JButton buildGridButton = new JButton("Build Grid");
	JButton loadGridButton = new JButton("Load Grid");
	JButton checkButton = new JButton("Check");
	JButton solveButton = new JButton("Solve");
	JButton exitButton = new JButton("Exit");
	JComboBox<String> gridSizeBox;
	
	class buildGridListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ((String) gridSizeBox.getSelectedItem() != "") {
				Game.getGameFrame().dispose();
				Game.buildGrid(gridSizeBox.getSelectedIndex() + 3);
			 }
		}
	}
	
	class loadGridListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			 
		}
	}
	 
	 class checkListener implements ActionListener {
		 @Override
		 public void actionPerformed(ActionEvent e) {
			 
		}
	 }
	 
	 class solveListener implements ActionListener {
		 @Override
		 public void actionPerformed(ActionEvent e) {
			 
		}
	 }
	 
	 class exitListener implements ActionListener {
		 @Override
		 public void actionPerformed(ActionEvent e) {
			 Game.getGameFrame().dispose();
			 System.exit(0);
		}
	 }
	
	public SettingsPane(int gridSize) {
		setLayout(null);
		
		status.setBounds(0, ((screenSize.height - 48) / 4) - 100, (int) screenSize.width / 4, 25);
		status.setHorizontalAlignment(JLabel.CENTER);
		status.setFont(new Font("Status", Font.BOLD, 24));
		add(status);
		
	    gridSizeLabel.setBounds(0, (screenSize.height - 48) / 4, (int) screenSize.width / 4, 25);
	    gridSizeLabel.setHorizontalAlignment(JLabel.CENTER);
	    add(gridSizeLabel);
	    
	    gridSizeBox = new JComboBox<String>(gridSizeList);
		gridSizeBox.setBounds(screenSize.width / 8 - 40, (screenSize.height - 48) / 4 + 30, 80, 25);
		((JLabel)gridSizeBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		gridSizeBox.setSelectedIndex(gridSize - 3);
		add(gridSizeBox);
		
		buildGridButton.setBounds(screenSize.width / 8 - 55, (screenSize.height - 48) / 4 + 75, 110, 25);
		add(buildGridButton);
		buildGridButton.addActionListener(new buildGridListener());
		
		loadGridButton.setBounds(screenSize.width / 8 - 55, (screenSize.height - 48) / 4 + 105, 110, 25);
		add(loadGridButton);
		loadGridButton.addActionListener(new loadGridListener());
		
		checkButton.setBounds(screenSize.width / 8 - 55, (screenSize.height - 48) / 4 + 165, 110, 25);
		add(checkButton);
		checkButton.addActionListener(new checkListener());
		
		solveButton.setBounds(screenSize.width / 8 - 55, (screenSize.height - 48) / 4 + 220, 110, 25);
		add(solveButton);
		solveButton.addActionListener(new solveListener());
		
		exitButton.setBounds(screenSize.width / 8 - 55, (screenSize.height - 48) / 4 + 250, 110, 25);
		add(exitButton);
		exitButton.addActionListener(new exitListener());
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