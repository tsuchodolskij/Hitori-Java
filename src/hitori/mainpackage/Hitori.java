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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1663267871917663300L;

	String errorMsg;
	
	JLabel gridSizeLabel = new JLabel("Choose the grid size:");
	JLabel errorLabel = new JLabel(errorMsg);
	
	JComboBox<String> gridSizeBox;
	
	String[] gridSizeList = {"", "5x5", "6x6", "7x7", "8x8", "9x9", "10x10", "11x11", "12x12"};
	
	JButton continueButton = new JButton("Continue");
	JButton quitButton = new JButton("Quit");
	
	class continueListener implements ActionListener {
		 @Override
		public void actionPerformed(ActionEvent e) {
			 Object selectedGridSize = gridSizeBox.getSelectedItem();
			 if ((String) selectedGridSize != "") {
				 dispose();
				 Game launchGame = new Game();
				 launchGame.display();
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
		JPanel gamePanel = new JPanel();
		add(gamePanel);
		gamePanel.setLayout(null);
		
		gridSizeLabel.setBounds(25, 25, 350, 25);
		gridSizeLabel.setHorizontalAlignment(JLabel.CENTER);
		gamePanel.add(gridSizeLabel);
		
		gridSizeBox = new JComboBox<String>(gridSizeList);
		((JLabel)gridSizeBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		gridSizeBox.setBounds(160, 55, 80, 25);
		gamePanel.add(gridSizeBox);
		
		continueButton.setBounds(80, 100, 110, 25);
		gamePanel.add(continueButton);
		continueButton.addActionListener(new continueListener());
		
		quitButton.setBounds(210, 100, 110, 25);
		gamePanel.add(quitButton);
		quitButton.addActionListener(new quitListener());
		
		errorLabel.setBounds(25, 130, 350, 25);
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(JLabel.CENTER);
		gamePanel.add(errorLabel);
		
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