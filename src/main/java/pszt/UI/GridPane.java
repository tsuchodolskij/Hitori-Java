package pszt.UI;

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

import pszt.algorithm.Algorithm;
import pszt.algorithm.Point;
import pszt.hitori.Cell;
import pszt.map.MapLoader;

public class GridPane extends JPanel {

	private static final long serialVersionUID = 5376889337682664210L;
	
	private SettingsPane sp;
	private Algorithm alg;
	private int gridSize;
	private int howManyBlack;
	private Integer[][] map;
	private boolean[][] clicked; // true == black
	private Cell[][] cellGrid;
	private int touch;
	private boolean cut;
	
	private boolean[][]notColiding; // cells with values that don't collide with anything
	
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
				
				if(alg.checkTouch(x,y, clicked) != 0)
					touch -= alg.checkTouch(x,y, clicked);
			}
			else {
				((Cell) e.getSource()).setBackground(Color.BLACK);
				((Cell) e.getSource()).setForeground(Color.WHITE);
				((Cell) e.getSource()).setPressed(true);
				howManyBlack++;
				clicked[x][y] = true;
				if(alg.checkTouch(x,y, clicked) != 0)
					touch += alg.checkTouch(x,y, clicked);
			}
			
			if(alg.checkCut(clicked, howManyBlack) == true)
				cut = true;
			else
				cut = false;
			
			boolean collision = false;
			Integer[][] red = alg.check(clicked, map);
			for (int i1 = 0; i1 < gridSize; i1++) {
				for (int j1 = 0; j1 < gridSize; j1++) {
					if(red[i1][j1] == 1) {
						collision = true; // if at least one tile is red, it is collision
						cellGrid[i1][j1].setForeground(Color.RED);
					}
					else if(red[i1][j1] == 2) // if tile was white and without collision
						cellGrid[i1][j1].setForeground(Color.BLACK);
				}
			}
			
			if(touch == 0 && cut == false)
				sp.statusSetText("");
			
			if(collision == false && touch == 0 && cut == false)
				sp.statusSetText("<html><div style='text-align: center;'>"+ "YOU WON!" +"</div></html>");
			else if(touch != 0)
				sp.statusSetText("<html><div style='text-align: center;'>"+ "BLACK TILES CANNOT TOUCH EACH OTHER" +"</div></html>");
			else if(cut == true)
				sp.statusSetText("<html><div style='text-align: center;'>"+ "WHITE FIELD CANNOT BE CUT" +"</div></html>");
		}
	}
	
	public GridPane(int gridSize, SettingsPane sp, MapLoader loader) {
		this.sp = sp;
		sp.setGridPane(this);
		alg = new Algorithm(gridSize);
		touch = 0;
		cut = false;
		this.gridSize = gridSize;
		howManyBlack = 0;
		cellGrid = new Cell[gridSize][gridSize];
		setLayout(new GridLayout(gridSize, gridSize));
		map = new Integer[gridSize][gridSize];
		clicked = new boolean[gridSize][gridSize];
		notColiding = new boolean[gridSize][gridSize];
		
		if (loader == null) prepareMap(gridSize);
		else readMapFromLoader(loader);
		
		alg.setMap(map);
		alg.setGrid(this);

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
				notColiding[i][j] = false;
			}
		}
	}

	private void readMapFromLoader(MapLoader loader) {
		for(int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				this.map[j][i] = loader.getCell(j, i);
			}
		}
	}

	public void solve() {
		Thread thread = new Thread() {
	        @Override
	        public void run() {
	            try {
	                Thread.sleep(0);
	            } catch (InterruptedException e) {
	            }
	
	            runOnUiThread(new Runnable() {
	                @Override
	                public void run() {
	                    // Do some stuff
	                }
	            });
	        }
	    };
	    thread.start(); //start the thread
	}
	
	protected void runOnUiThread(Runnable runnable) {
			sp.statusSetText("");
			alg = new Algorithm(gridSize);
			alg.setMap(map);
			alg.setGrid(this);
			
			alg.aStar();
			
			sp.statusSetText("<html><div style='text-align: center;'>"+ "A* SOLVED HITORI" +"</div></html>");
	}
	
	public void updateMap(boolean[][] newMap){
		int blackTiles = 0;
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				clicked[i][j] = newMap[i][j];
				if(clicked[i][j])
					cellGrid[i][j].setPressed(true);
				else 
					cellGrid[i][j].setPressed(false);
				
				if(newMap[i][j] == true) {
					blackTiles++;
					cellGrid[i][j].setForeground(Color.WHITE);
					cellGrid[i][j].setBackground(Color.BLACK);
				}
				else {
					cellGrid[i][j].setForeground(Color.BLACK);
					cellGrid[i][j].setBackground(Color.WHITE);
				}
			}
		}
		howManyBlack = blackTiles;
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
		Generator();
	}
	
	private void Generator() {
		int first, second, tmp;
		Random r = new Random();
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
			if(alg.checkCut(clicked, howManyBlack) || alg.checkTouch(first, second, clicked) != 0){	// check if it's possible to cover it
				
				clicked[first][second] = false;					// if not it's a normal field again
				howManyBlack--;
				
				if(!checked[first][second]) {					// hit the field for the 1st time
					hit++;
					checked[first][second] = true;
				}
			}
			else if(!checked[first][second]){					// add new black field to array
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
