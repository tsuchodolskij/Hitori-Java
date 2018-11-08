package hitori.mainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

class GridPane extends JPanel {

	private static final long serialVersionUID = 5376889337682664210L;
	
	private int gridSize;
	private int howManyBlack;
	private Integer[][] map;
	private boolean[][] clicked;
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int x = ((Cell) e.getSource()).getxCoordinate();
			int y = ((Cell) e.getSource()).getyCoordinate();
			
			if (((Cell) e.getSource()).isPressed()) {
				((Cell) e.getSource()).setBackground(Color.WHITE);
				((Cell) e.getSource()).setForeground(Color.BLACK);
				((Cell) e.getSource()).setPressed(false);
				howManyBlack--;
				clicked[x][y] = false;
			}
			else {
				((Cell) e.getSource()).setBackground(Color.BLACK);
				((Cell) e.getSource()).setForeground(Color.WHITE);
				((Cell) e.getSource()).setPressed(true);
				howManyBlack++;
				clicked[x][y] = true;
				if(checkTouch(x,y))
					System.out.println("TILES ARE TOUCHING EACH OTHER");	
				if(checkCut())
					System.out.println("WHITE TILES ARE NOT IN ONE PIECE");
			}
			//System.out.println("Button value: " + ((Cell) e.getSource()).getValue());
			//System.out.println("Button x coordinate: " + x);
			//System.out.println("Button y coordinate: " + y);	
			//System.out.println("Black tiles: " + howManyBlack);
		}
	}
	
	public GridPane(int gridS) {
		gridSize = gridS;
		howManyBlack = 0;
		Cell[][] cellGrid = new Cell[gridSize][gridSize];
		setLayout(new GridLayout(gridSize, gridSize));
		map = new Integer[gridSize][gridSize];
		clicked = new boolean[gridSize][gridSize];
	
		prepareMap(gridSize);
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				cellGrid[i][j] = new Cell(map[i][j], i, j);
				if (gridSize <= 10)
					cellGrid[i][j].setFont(new Font("Dialog", Font.BOLD, 24));
				else if (gridSize <= 20)
					cellGrid[i][j].setFont(new Font("Dialog", Font.BOLD, 18));
				cellGrid[i][j].setBackground(Color.WHITE);
				cellGrid[i][j].setBorder(new LineBorder(Color.BLACK, 1));
				cellGrid[i][j].addActionListener(new ButtonListener());
				add(cellGrid[i][j]);
				
				clicked[i][j] = false;
			}
		}
	}
	
	private boolean checkCut() {
		ArrayList<Point> points = new ArrayList<Point>();
		boolean[][] visited = new boolean[gridSize][gridSize];
		
		a: for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if(clicked[i][j] == false) {
					points.add(new Point(i,j));
					visited[i][j] = true;
					System.out.println("Poczatkowy pkt: " + i + " "+ j);
					break a;
				}
			}
		}
		
		for(int i = 0; i < points.size(); i++) {
			int x = points.get(i).x;
			int y = points.get(i).y;
			
			//System.out.println("Jestem na: " + x + " "+ y);
			
			if(x != 0 && visited[x-1][y] == false && clicked[x-1][y] == false) {
				points.add(new Point(x-1,y));
				visited[x-1][y] = true;
				//System.out.println("Dodaje: " + (x-1) + " "+ y);
			}
			if(x != gridSize-1 && visited[x+1][y] == false && clicked[x+1][y] == false) {
				points.add(new Point(x+1,y));
				visited[x+1][y] = true;
				//System.out.println("Dodaje: " + (x+1) + " "+ y);
			}
			if(y != 0 && visited[x][y-1] == false && clicked[x][y-1] == false) {
				points.add(new Point(x,y-1));
				visited[x][y-1] = true;
				//System.out.println("Dodaje: " + x + " "+ (y-1));
			}
			if(y != gridSize-1 && visited[x][y+1] == false && clicked[x][y+1] == false) {
				points.add(new Point(x,y+1));
				visited[x][y+1] = true;
				//System.out.println("Dodaje: " + x + " "+ (y+1));
			}
		}
		
		/*for(int i = 0; i < points.size(); i++) {
			System.out.println(points.get(i).x + " " + points.get(i).y);
		}*/

		//System.out.println("points.size(): " + points.size());
		//System.out.println("(gridSize*gridSize) - howManyBlack: " + ((gridSize*gridSize) - howManyBlack));

		if(points.size() == ((gridSize*gridSize) - howManyBlack))
			return false;
		else return true;
	}
	
	private boolean checkTouch(int x, int y) {
		if(x != 0) {
			if(clicked[x-1][y] == true)
				return true;
		}
		if(x != gridSize-1) {
			if(clicked[x+1][y] == true)
				return true;
		}
		if(y != 0) {
			if(clicked[x][y-1] == true)
				return true;
		}
		if(y != gridSize-1) {
			if(clicked[x][y+1] == true)
				return true;
		}
		
		return false;
	}
	
	private void prepareMap(int gridSize) {
		int k;
		for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j+1;
				k = (k+1)%gridSize;
			}
		}
		
		Random r = new Random();
		int first, second, tmp;
		for (int i = 0; i < gridSize/2; i++) {
			do {
				first = r.nextInt(gridSize);
				second = r.nextInt(gridSize);
			} while(first == second);
			
			for (int j = 0; j < gridSize; j++) {
				tmp = map[j][first];
				map[j][first] = map[j][second];
				map[j][second] = tmp;
			}
			
			do {
				first = r.nextInt(gridSize);
				second = r.nextInt(gridSize);
			} while(first == second);
			
			for (int j = 0; j < gridSize; j++) {
				tmp = map[first][j];
				map[first][j] = map[second][j];
				map[second][j] = tmp;
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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

class Point {
	public int x, y;
	
	Point(int a, int b){
		x = a;
		y = b;
	}
};
