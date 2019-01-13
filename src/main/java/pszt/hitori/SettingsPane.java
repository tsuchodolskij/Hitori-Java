package pszt.hitori;

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
	private GridPane gp;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//private String errorMsg;
	private String[] gridSizeList = {"", "4x4", "5x5", "6x6", "7x7", "8x8", "9x9", "10x10",
			"11x11", "12x12", "13x13", "14x14", "15x15", "16x16", "17x17", "18x18", "19x19", "20x20",
			"21x21", "22x22", "23x23", "24x24", "25x25", "26x26", "27x27", "28x28", "29x29", "30x30",
			"31x31", "32x32", "33x33", "34x34", "35x35", "36x36", "37x37", "38x38", "39x39", "40x40"};
	
	JLabel status = new JLabel("");
	JLabel gridSizeLabel = new JLabel("Choose the grid size:");
	JButton buildGridButton = new JButton("Build Grid");
	JButton loadGridButton = new JButton("Load Grid");
	JButton solveButton = new JButton("Solve");
	JButton exitButton = new JButton("Exit");
	JComboBox<String> gridSizeBox;
	
	class buildGridListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ((String) gridSizeBox.getSelectedItem() != "") {
				Game.getGameFrame().dispose();
				Game.buildGrid(gridSizeBox.getSelectedIndex() + 3, null);
			 }
		}
	}
	
	class loadGridListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			 Game.getGameFrame().dispose();
			 MapLoader mapLoader = new MapLoader();
			 mapLoader.loadMap();
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
			 gp.solve();
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
		
		status.setBounds(20, ((screenSize.height - 48) / 4) - 150, ((int) screenSize.width / 4)-40, 100);
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
		
		solveButton.setBounds(screenSize.width / 8 - 55, (screenSize.height - 48) / 4 + 170, 110, 25);
		add(solveButton);
		solveButton.addActionListener(new solveListener());
		
		exitButton.setBounds(screenSize.width / 8 - 55, (screenSize.height - 48) / 4 + 250, 110, 25);
		add(exitButton);
		exitButton.addActionListener(new exitListener());
	}
	
	public void statusSetText(String text) {
		status.setText(text);
	}
	
	public void setGridPane(GridPane gp) {
		this.gp = gp;
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