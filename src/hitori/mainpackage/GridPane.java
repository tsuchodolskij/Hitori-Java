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
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

class GridPane extends JPanel {

	private static final long serialVersionUID = 5376889337682664210L;
	
	private Algorithm alg;
	private int gridSize;
	private int howManyBlack;
	private Integer[][] map;
	private boolean[][] clicked; // true == black
	private Cell[][] cellGrid;
	private int touch;
	private boolean cut;
	
	private boolean[][]notColiding; // cells with values that don't collide with anything
	
	private boolean [][]startingMap; 
	
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
			
			if(collision == false && touch == 0 && cut == false)
				System.out.println("GRATULUJE! WYGRALES");
		}
	}
	
	public GridPane(int gridSize) {
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
				notColiding[i][j] = false;
			}
		}
		
		for(int i=0; i<gridSize; ++i) {
			for(int j=0; j<gridSize; ++j) {
				if(map[i][j]>9)
					System.out.print(map[i][j]+" ");
				else
					System.out.print(map[i][j]+"  ");
			}
			System.out.println();
		}
	
		startingMap = checkNeighbors();
		updateMap(startingMap);
		Thread thread = new Thread() {
	        @Override
	        public void run() {
	            try {
	                Thread.sleep(1000);
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
		// TODO Auto-generated method stub
		//aStar();
	}
	
/*--------------------------------------      A*      -------------------------------------------------------*/	
	private void aStar() {
		
		ArrayList<State> states = new ArrayList<State>();
		
		// expanding zero state
		boolean [][] newMapBlack = startingMap;
		states.add(new State(0, 0, gridSize, newMapBlack, howManyBlack, 0, 0, 0, 0));
		ArrayList<State> tmp = expand(states.get(0));
		for(State i : tmp) {
			states.add(i);
		}
		
		while(true) {
			// finding state with the lowest heuristic and cost
			State lowestHC = null;
			if(states.get(0) != null)
				lowestHC = states.get(0);
			
			int lowestIndex = 0;
			System.out.println("HC:" + lowestHC.getHC());
			for (int i = 0; i < states.size(); i++) {
				//System.out.println("HC:" + states.get(i).getHC());
				if(states.size() > 0 && states.get(i).getHC() < lowestHC.getHC()) {
					lowestHC = states.get(i);
					lowestIndex = i;
				}
			}
			/*try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			System.out.println("LOWEST HC:" + lowestHC.getHC());
			for (int k = 0; k < gridSize; k++) {
				System.out.println("");
				for (int x = 0; x < gridSize; x++) {
					if(lowestHC.getBlack(k, x) == true)
						System.out.print("1 ");
					else 
						System.out.print("0 ");
				}
			}
			System.out.println("");
			
			if(lowestHC.getIsTerminal() == 1) { // we have the solution
				System.out.println("Znaleziono rozwiazanie: x:" + lowestHC.x+ " y: " + lowestHC.y);
				updateMap(lowestHC.getMapBlack());
				break;
			}
			
			states.remove(lowestIndex);			// deleting lowestHC from states list, because i expand him

			tmp = expand(lowestHC);
			if(tmp != null)
				for(State i : tmp) {     			// adding every son of lowestHC
					states.add(i);
				}
			
			updateMap(lowestHC.getMapBlack());
			/*try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
		System.out.println("ALGORYTM A* ZAKONCZYL DZIALANIE");
	}
	
	private void updateMap(boolean[][] newMap){
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if(newMap[i][j] == true) {
					cellGrid[i][j].setForeground(Color.WHITE);
					cellGrid[i][j].setBackground(Color.BLACK);
				}
				else {
					cellGrid[i][j].setForeground(Color.BLACK);
					cellGrid[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}
	
	private ArrayList<State> expand(State e) {
	
		boolean[][] checked = new boolean[gridSize][gridSize]; // 0 means unchecked
		
		ArrayList<Point> vect = new ArrayList<Point>();
		ArrayList<State> states = new ArrayList<State>();
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
	
				if(e.getBlack(i,j) == false && (alg.checkTouch(i,j,e.getMapBlack())<1)) { // this tile is white
					int value = map[i][j];
					vect.add(new Point(i,j));
					
					for(int z = 0; z < vect.size(); z++){ // finding every tile, which has the same value as i,j (if it not exists, loop will go 1 time)
						checked[vect.get(z).x][vect.get(z).y] = true; // every point is setting him visited
						
						int k = vect.get(z).x;
						for(int m = 0; m < gridSize; m++) { // scaning x coord, looking for the same value
							if(checked[k][m] == false && map[k][m] == value && e.getBlack(k,m) == false) {
								vect.add(new Point(k,m));
								break; // to find only first in row/column (because of repetitions)
							}
						}
						k = vect.get(z).y;
						for(int m = 0; m < gridSize; m++) { // scaning y coord, looking for the same value
							if(checked[m][k] == false && map[m][k] == value && e.getBlack(m,k) == false) {
								vect.add(new Point(m,k));
								break;
							}
						}
					} // z for
					
					
					if(vect.size() > 1) { // if there is collision, make other states
						int terminalStates = 0;
						for(int m = 0; m < vect.size(); m++) {
							
							int[][] mapState = new int[gridSize][gridSize]; // 0 - white, 1 - black, 2-green
							for(int z1=0; z1<gridSize; ++z1) {
								for(int z2=0; z2<gridSize; ++z2) {
									mapState[z1][z2] = 0; }}
							
							boolean[][] newMapBlack = new boolean[gridSize][gridSize]; // getting map for child
							for (int k = 0; k < gridSize; k++) {
								for (int x = 0; x < gridSize; x++) {
									if(e.getBlack(k,x) == true) {
										newMapBlack[k][x] = true;
										mapState[k][x] = 1;
									}
									else {
										newMapBlack[k][x] = false;
									}
								}
							}
							newMapBlack[vect.get(m).x][vect.get(m).y] = true; // setting him black
							mapState[vect.get(m).x][vect.get(m).y] = 1;
							
							System.out.println("\nPunkt: x: " + vect.get(m).x + " y: " + vect.get(m).y);
							
							alg.setGreens(mapState);
							int isTerminal = 1, weight = 0;
							
							if(alg.checkAll(vect.get(m).x, vect.get(m).y, e.getBlackCount(), newMapBlack, mapState, map))
							{
								
								int new_blacks = alg.eliminateOther(mapState, newMapBlack, e.getBlackCount(), map);
								if(new_blacks != -1)
								{
								
									System.out.println("Newblacks: "+new_blacks);
									
									for (int k = 0; k < gridSize; k++) {
										System.out.println("");
										for (int x = 0; x < gridSize; x++) {
											if(newMapBlack[k][x] == true) {
												if(map[k][x]>9)
													System.out.print("("+map[k][x]+") ");
												else
													System.out.print(" ("+map[k][x]+") ");
											}
											else { 
												if(map[k][x]>9)
													System.out.print(" "+map[k][x]+"  ");
												else
													System.out.print("  "+map[k][x]+"  ");
											}
										}
									}
									new_blacks++;
								
									Integer[][] red = alg.check(newMapBlack, map);
									a : for (int i1 = 0; i1 < gridSize; i1++) {
										for (int j1 = 0; j1 < gridSize; j1++) {
											if(red[i1][j1] == 1) {
												isTerminal = 0; // if at least one tile is red, it is no terminal
												break a;
											}
										}
									}
									
									int sidesCollision = alg.checkSidesCollision(newMapBlack, vect.get(m).x, vect.get(m).y, map);
									
									terminalStates++;
									states.add(new State(vect.get(m).x, vect.get(m).y, gridSize, newMapBlack, 
										e.getBlackCount()+new_blacks, vect.size(), isTerminal, sidesCollision, weight));
								}
							}
							
						}	
						if(terminalStates == 0) {
							e.setCost(1000);
							return null;
						}
					}
					//System.out.println(" ");
				
					vect.clear(); // every value like first was found, so prepare for another loop
					
				} // if
			} // j for
		} // i for
		
		return states;
	}	
	
	// PRZYDALO BY SIE DODAC ARGUMENTY DO FUNKCJI, ZEBY NP NIE KORZYSTALO Z HOWMANYBLACK GLOBALNYCH TYLKO MIALO SWOJE LOKALNE O ILE MOZLIWE
	private boolean[][] checkNeighbors() {
		int[][] mapState = new int[gridSize][gridSize];
		boolean[][] newMapBlack = new boolean[gridSize][gridSize];
		boolean[][] checked = new boolean[gridSize][gridSize];
		
		for(int i=0; i<gridSize; ++i) {
			for(int j=0; j<gridSize; ++j) {
				newMapBlack[i][j] = checked[i][j] = false;
				mapState[i][j] = 0;
			}}
				
		System.out.println("Troj: "+howManyBlack);
		for(int i=0; i<gridSize - 1; ++i) {
			for(int j=0; j<gridSize - 1; ++j) {
				
				if(map[i][j] == map[i][j+1]) {
					
					if(j<gridSize-2 && map[i][j] == map[i][j+2]) { // triplet
						
						newMapBlack[i][j] = newMapBlack[i][j+2] = true;
						mapState[i][j] = mapState[i][j+2] = 1;
						if(!checked[i][j])
							howManyBlack++;
						if(!checked[i][j+2])
							howManyBlack++;
						checked[i][j] = checked[i][j+2] = true;
						
						j+=2;
						//System.out.println("POZ-Triplet: "+i+" "+j+" black: "+howManyBlack);
					}
					
					for(int k = j+2; k < gridSize; ++k) {
						if(!checked[i][k] && map[i][k] == map[i][j]) { // para/triplet + 
							newMapBlack[i][k] = true;
							mapState[i][k] = 1;
							if(!checked[i][k])
								howManyBlack++;
							
							checked[i][k] = true;
							//System.out.println("POZ-przod-do pary: "+i+" "+j+" black: "+howManyBlack);
						}
					}
					
					for(int k = j-2; k >= 0; --k) {
						if(!checked[i][k] && map[i][k] == map[i][j]) { // para/triplet + 
							newMapBlack[i][k] = true;
							mapState[i][k] = 1;
							if(!checked[i][k])
								howManyBlack++;
							
							checked[i][k] = true;
							//System.out.println("POZ-tyl-do pary: "+i+" "+j+" black: "+howManyBlack);
						}
					}
				}
				
				if(map[i][j] == map[i+1][j]) {
					
					int tmp = i;
					if(i<gridSize-2 && map[i][j] == map[i+2][j]) {
						
						
						newMapBlack[i][j] = newMapBlack[i+2][j] = true;
						mapState[i][j] = mapState[i+2][j] = 1;
						if(!checked[i][j])
							howManyBlack++;
						if(!checked[i+2][j])
							howManyBlack++;
						checked[i][j] = checked[i+2][j] = true;
						tmp++;
						//System.out.println("POZ-Triplet: "+i+" "+j+" black: "+howManyBlack);
					}
					
					for(int k = tmp+3; k < gridSize; ++k) {
						if(!checked[k][j] && map[k][j] == map[i][j]) { // para/triplet + 
							newMapBlack[k][j] = true;
							mapState[k][j] = 1;
							if(!checked[k][j])
								howManyBlack++;
							
							checked[i][k] = true;
							//System.out.println("POZ-gora-do pary: "+i+" "+j+" black: "+howManyBlack);

						}
					}
					
					for(int k = i-2; k >= 0; --k) {
						if(!checked[k][j] && map[k][j] == map[i][j]) { // para/triplet + 
							newMapBlack[k][j] = true;
							mapState[k][j] = 1;
							if(!checked[k][j])
								howManyBlack++;
							
							checked[i][k] = true;
							//System.out.println("POZ-dol-do pary: "+i+" "+j+" black: "+howManyBlack);

						}
					}
				}
			}
		}
		System.out.println("Trojki: "+howManyBlack);
		for(int i=0; i<gridSize; ++i) {
			for(int j=0; j<gridSize; ++j) {
				if(newMapBlack[i][j]) 
					System.out.print("1 ");
				else
					System.out.print("0 ");
			}
			System.out.println();
		}
		
		alg.setGreens(mapState);
		howManyBlack+=alg.eliminateOther(mapState, newMapBlack, howManyBlack-1, map);
		
		System.out.println("Mapa: "+howManyBlack);
		for(int i=0; i<gridSize; ++i) {
			for(int j=0; j<gridSize; ++j) {
				if(newMapBlack[i][j]) 
					System.out.print("1 ");
				else
					System.out.print("0 ");
			}
			System.out.println();
		}
		
		return newMapBlack;
	}
	
/*-----------------------------------------------------------------------------------------------------------*/	
	
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
					
			if(alg.checkCut(clicked, howManyBlack) || alg.checkTouch(first, second, clicked) != 0){	// check if it's possible to cover it
				
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
