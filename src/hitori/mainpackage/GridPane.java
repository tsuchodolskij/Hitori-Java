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
	private boolean[][] clicked; // true == black
	private Cell[][] cellGrid;
	private int touch;
	private boolean cut;
	
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
				
				if(checkTouch(x,y) != 0)
					touch -= checkTouch(x,y);
			}
			else {
				((Cell) e.getSource()).setBackground(Color.BLACK);
				((Cell) e.getSource()).setForeground(Color.WHITE);
				((Cell) e.getSource()).setPressed(true);
				howManyBlack++;
				clicked[x][y] = true;
				if(checkTouch(x,y) != 0)
					touch += checkTouch(x,y);
				
				/*if(checkTouch(x,y) != 0)
					System.out.println("TILES ARE TOUCHING EACH OTHER");	
				if(checkCut())
					System.out.println("WHITE TILES ARE NOT IN ONE PIECE");*/
			}
			//System.out.println("Button value: " + ((Cell) e.getSource()).getValue());
			//System.out.println("Button x coordinate: " + x);
			//System.out.println("Button y coordinate: " + y);	
			//System.out.println("Black tiles: " + howManyBlack);
			
			//System.out.println("TOUCH: " + touch);
			
			if(checkCut() == true)
				cut = true;
			else
				cut = false;
			
			if(check() && touch == 0 && cut == false)
				System.out.println("GRATULUJE! WYGRALES");
		}
	}
	
	public GridPane(int gridS) {
		touch = 0;
		cut = false;
		gridSize = gridS;
		howManyBlack = 0;
		cellGrid = new Cell[gridSize][gridSize];
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
	
	// return true if hitori is solved, no collisions at all
	private boolean check() {
		Integer[][] checked = new Integer[gridSize][gridSize];  // initialized with 0, 0-unvisited, 1-with collision, 2-without collision
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				checked[i][j] = 0;
			}
		}
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				//System.out.println("Current tile: " + i + " " + j +" checked value:"+checked[i][j]);
				if(clicked[i][j] == true) {
					checked[i][j] = 2;
					continue;
				}
				int k = j+1;
				int collisions = 0;
				while(k < gridSize) {
					if(clicked[i][k] == false && map[i][k] == map[i][j]) {
						checked[i][k] = 1; 
						cellGrid[i][j].setForeground(Color.RED);
						collisions++;
					}
					k++;
				}
				int l = i+1;
				while(l < gridSize) {
					if(clicked[l][j] == false && map[l][j] == map[i][j]) {
						checked[l][j] = 1; 
						cellGrid[i][j].setForeground(Color.RED);
						collisions++;
					}
					l++;
				}
				
				if(collisions == 0 && checked[i][j] == 0) {
					checked[i][j] = 2;
					cellGrid[i][j].setForeground(Color.BLACK);
				}
				else {
					cellGrid[i][j].setForeground(Color.RED);
					checked[i][j] = 1;
				}
			}
		}
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if(checked[i][j] == 1) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private boolean checkCut() {
		ArrayList<Point> points = new ArrayList<Point>();
		boolean[][] visited = new boolean[gridSize][gridSize];
		
		a: for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if(clicked[i][j] == false) {
					points.add(new Point(i,j));
					visited[i][j] = true;
					//System.out.println("Poczatkowy pkt: " + i + " "+ j);
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
	
	private int checkTouch(int x, int y) {
		int withHowMany = 0;
		
		if(x != 0) {
			if(clicked[x-1][y] == true)
				withHowMany++;
		}
		if(x != gridSize-1) {
			if(clicked[x+1][y] == true)
				withHowMany++;
		}
		if(y != 0) {
			if(clicked[x][y-1] == true)
				withHowMany++;
		}
		if(y != gridSize-1) {
			if(clicked[x][y+1] == true)
				withHowMany++;
		}
		
		return withHowMany;
	}
	
	private void prepareMap(int gridSize) {
		Random r = new Random();
		
		int k;
		for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j+1;
				k = (k+1)%gridSize;
			}
		}
		
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


		//generator

		ArrayList<Point> points = new ArrayList<Point>();		// hold the black fields
		boolean[][] checked = new boolean[gridSize][gridSize]; 
		int hit = 0;											
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				checked[i][j] = clicked[i][j] = false; }}

		while(hit != gridSize*gridSize) {						// hit every position on board
			
			first = r.nextInt(gridSize);
			second = r.nextInt(gridSize);						// get random field

			if(!clicked[first][second]) {						// cover it
				clicked[first][second] = true;	
				howManyBlack++;
			}
					
			if(checkCut() || checkTouch(first,second) != 0){	// check if it's possible to cover it
				
				clicked[first][second] = false;					// if not it's a normal field again
				howManyBlack--;
				
				if(!checked[first][second]) {					// hit the field for the 1st time
					hit++;
					checked[first][second] = true;
				}
			}
			else if(!checked[first][second]){					// add new black field to array
				//System.out.println("covered: "+first+", "+second);
				points.add(new Point(first, second));
				checked[first][second] = true;
				hit++;
			}
		}

		for(int i=0; i<points.size(); ++i){
			do {
				tmp = r.nextInt(gridSize)+1;
			} while(tmp == map[points.get(i).x][points.get(i).y]);

			map[points.get(i).x][points.get(i).y] = tmp;		// mix numbers
			clicked[points.get(i).x][points.get(i).y] = false;
			howManyBlack--;										// clear the board
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
