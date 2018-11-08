package hitori.mainpackage;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Hitori extends JFrame {
	
	private static final long serialVersionUID = -1663267871917663300L;

	private String errorMsg;
	private String[] gridSizeList = {"", "4x4", "5x5", "6x6", "7x7", "8x8", "9x9", "10x10",
			"11x11", "12x12", "13x13", "14x14", "15x15", "16x16", "17x17", "18x18", "19x19", "20x20",
			"21x21", "22x22", "23x23", "24x24", "25x25", "26x26", "27x27", "28x28", "29x29", "30x30",
			"31x31", "32x32", "33x33", "34x34", "35x35", "36x36", "37x37", "38x38", "39x39", "40x40"};
	
	
	JLabel gridSizeLabel = new JLabel("Choose the grid size:");
	JLabel errorLabel = new JLabel(errorMsg);
	JButton buildGridButton = new JButton("Build Grid");
	JButton quitButton = new JButton("Quit");
	JComboBox<String> gridSizeBox;
	
	class buildGridListener implements ActionListener {
		 @Override
		public void actionPerformed(ActionEvent e) {
			 if ((String) gridSizeBox.getSelectedItem() != "") {
				 dispose();
				 Game getStarted = new Game();
				 getStarted.buildGrid(gridSizeBox.getSelectedIndex() + 3);
			 }
			 errorLabel.setText("The grid size has not been chosen!");
		}
	 }
	 
	 class quitListener implements ActionListener {
		 @Override
		 public void actionPerformed(ActionEvent e) {
			 dispose();
		}
	 }
	 
	public void launch() {
		JPanel gamePane = new JPanel();
		add(gamePane);
		gamePane.setLayout(null);
		
		gridSizeLabel.setBounds(25, 25, 350, 25);
		gridSizeLabel.setHorizontalAlignment(JLabel.CENTER);
		gamePane.add(gridSizeLabel);
		
		gridSizeBox = new JComboBox<String>(gridSizeList);
		gridSizeBox.setBounds(160, 55, 80, 25);
		((JLabel)gridSizeBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		gamePane.add(gridSizeBox);
		
		buildGridButton.setBounds(80, 100, 110, 25);
		gamePane.add(buildGridButton);
		buildGridButton.addActionListener(new buildGridListener());
		
		quitButton.setBounds(210, 100, 110, 25);
		gamePane.add(quitButton);
		quitButton.addActionListener(new quitListener());
		
		errorLabel.setBounds(25, 130, 350, 25);
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(JLabel.CENTER);
		gamePane.add(errorLabel);
		
		setTitle("Hitori");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public static void main(String[] args) {
		Hitori getStarted = new Hitori();
		getStarted.launch();
	}

}