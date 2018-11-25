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
				
				if(checkTouch(x,y, clicked) != 0)
					touch -= checkTouch(x,y, clicked);
			}
			else {
				((Cell) e.getSource()).setBackground(Color.BLACK);
				((Cell) e.getSource()).setForeground(Color.WHITE);
				((Cell) e.getSource()).setPressed(true);
				howManyBlack++;
				clicked[x][y] = true;
				if(checkTouch(x,y, clicked) != 0)
					touch += checkTouch(x,y, clicked);
				
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
			
			if(checkCut(clicked, howManyBlack) == true)
				cut = true;
			else
				cut = false;
			
			if(check(clicked) && touch == 0 && cut == false)
				System.out.println("GRATULUJE! WYGRALES");
		}
	}
	
	public GridPane(int gridSize) {
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
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}
			
		aStar();
	}
/*--------------------------------------      A*      -------------------------------------------------------*/	
	private void aStar() {
		
		ArrayList<State> states = new ArrayList<State>();
		
		// expanding zero state
		ArrayList<State> tmp = expand(new State(-1, -1, gridSize, clicked, howManyBlack, 0, 0, 0, 0));
		for(State i : tmp) {
			states.add(i);
			//System.out.println("x: " + i.x+ " y: "+ i.y);
			
			/*for (int k = 0; k < gridSize; k++) {
				System.out.println("");
				for (int j = 0; j < gridSize; j++) {
					if(i.getBlack(k,j) == true)
						System.out.print("1 ");
					else 
						System.out.print("0 ");
				}
			}*/
		}
		
		while(true) {
			// finding state with the lowest heuristic and cost
			State lowestHC = states.get(0);
			int lowestIndex = 0;
			System.out.println("HC:" + lowestHC.getHC());
			for (int i = 0; i < states.size(); i++) {
				//System.out.println("HC:" + states.get(i).getHC());
				if(states.get(i).getHC() < lowestHC.getHC()) {
					lowestHC = states.get(i);
					lowestIndex = i;
				}
				/*if(states.get(i).getHC()>lowestHC.getHC()) {
					states.remove(i);
				}*/
			}
			
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
			for(State i : tmp) {     			// adding every son of lowestHC
				states.add(i);
			}
			
			updateMap(lowestHC.getMapBlack());
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
	
				if(e.getBlack(i,j) == false && (checkTouch(i,j,e.getMapBlack())<1)) { // this tile is white
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
							mapState[vect.get(m).x][vect.get(m).y] = 1; // setting him black
							
							System.out.println("\nPunkt: x: " + vect.get(m).x + " y: " + vect.get(m).y);
							
							setGreens(mapState);
							int isTerminal = 0;
							
							if(checkAll(vect.get(m).x, vect.get(m).y, e.getBlackCount(), newMapBlack, mapState))
							{
								
								int new_blacks = eliminateOther(mapState, newMapBlack, e.getBlackCount());
								if(new_blacks != -1)
								{
								
									System.out.println("\nnewblacks: "+new_blacks);
									
									for (int k = 0; k < gridSize; k++) {
										System.out.println("");
										for (int x = 0; x < gridSize; x++) {
											if(newMapBlack[k][x] == true)
												System.out.print("1 ");
											else 
												System.out.print("0 ");
										}
									}
									new_blacks++;
								
								
									if(check(newMapBlack)) 
										isTerminal = 1;
		
									
									int weight = 0;// = checkNeighbours(vect.get(m).x, vect.get(m).y);
								
									//System.out.println("\n\nNew state: x:" + vect.get(m).x+ " y:"+ vect.get(m).y + " terminal:" + isTerminal+" weight: "+weight);
									
									int sidesCollision = checkSidesCollision(newMapBlack, vect.get(m).x, vect.get(m).y);
									
									states.add(new State(vect.get(m).x, vect.get(m).y, gridSize, newMapBlack, 
										e.getBlackCount()+new_blacks, vect.size(), isTerminal, sidesCollision, weight));
								}
							}
						}		
					}
					System.out.println(" ");
					
					vect.clear(); // every value like first was found, so prepare for another loop
					
				} // if
			} // j for
		} // i for
		
		return states;
	}
	
	/* cover neighbors of black as greens */
	
	private void setGreens(int [][] mapState) {
		for(int i=0; i<gridSize; ++i) {
			for(int j=0; j<gridSize; ++j) {
				
				if(mapState[i][j] == 1) {
					
					if(i!=0) {
						mapState[i-1][j] = 2;
					}
					if(i!=gridSize-1) {
						mapState[i+1][j] = 2;
					}
					if(j!=0) {
						mapState[i][j-1] = 2;
					}
					if(j!=gridSize-1) {
						mapState[i][j+1] = 2;
					}
				}
			}
		}
	}	
	
	/* every black cell has 4 adjacent neighbors which are set green and can't be put black anymore
	   so there can't be two equal numbers in one row or column adjacent to black cells*/
	
	private boolean checkGreens(int[][] mapState) {
	
		for(int i=0; i<gridSize; ++i) {
			for(int j=0; j<gridSize; ++j) {
				
				if(mapState[i][j]==2) {
					
					if(i!=gridSize - 1) {
						for(int x=i+1; x < gridSize; ++x) {
							
							if(mapState[x][j] == 2) {
								if(map[x][j] == map[i][j])
									return false;
							}
						}
					}
					
					if(i!=0) {
						for(int x=i-1; x >= 0; --x) {
							
							if(mapState[x][j] == 2) {
								if(map[x][j] == map[i][j])
									return false;
							}
						}
					}
					
					if(j!=gridSize - 1) {
						for(int x=j+1; x < gridSize; ++x) {
							if(mapState[i][x] == 2) {
								if(map[i][x] == map[i][j])
									return false;
							}
						}
					}	
					
					if(j!=0) {
						for(int x=j-1; x >= 0; --x) {
							if(mapState[i][x] == 2) {
								if(map[i][x] == map[i][j])
									return false;
							}
						}
					}	
				}
			}
		}
		return true;
	}
		
	
	/* eliminate all possible cells for this state */
	
	private int eliminateOther(int [][] mapState, boolean[][] mapBlack, int actual_black) {		
		
		int new_blacks = 0;
		for(int twice = 0; twice < 2; ++twice) {
			for(int i=0; i<gridSize; ++i) {
				for(int j=0; j<gridSize; ++j) {
					
					if(mapState[i][j]==2) {
						
						if(i!=gridSize - 1) {
							for(int x=i+1; x < gridSize; ++x) {
								
								if(map[x][j] == map[i][j]) {
									
									if(mapState[x][j] == 0){
										
										mapBlack[x][j] = true;
										new_blacks++;
										mapState[x][j] = 1;
										if(x!=0) {
											mapState[x-1][j] = 2;
										}
										if(x!=gridSize-1) {
											mapState[x+1][j] = 2;
										}
										if(j!=0) {
											mapState[x][j-1] = 2;
										}
										if(j!=gridSize-1) {
											mapState[x][j+1] = 2;
										}
									
										if(!checkAll(x, j, actual_black+new_blacks, mapBlack, mapState)){	
											
											mapBlack[x][j] = false;
											new_blacks--;
											return -1;
										}
										//System.out.println("\nW dol: "+x+" "+j);
									} // if mapState
								} // if map
							} // for
						} // if i
						
						if(i!=0) {
							for(int x=i-1; x >= 0; --x) {
	
								if(map[x][j] == map[i][j]) {
									 
									if(mapState[x][j] == 0) {
										
										mapBlack[x][j] = true;
										new_blacks++;
										mapState[x][j] = 1;
										if(x!=0) {
											mapState[x-1][j] = 2;
										}
										if(x!=gridSize-1) {
											mapState[x+1][j] = 2;
										}
										if(j!=0) {
											mapState[x][j-1] = 2;
										}
										if(j!=gridSize-1) {
											mapState[x][j+1] = 2;
										}
										
										if(!checkAll(x, j, actual_black+new_blacks, mapBlack, mapState))
										{	mapBlack[x][j] = false;
											new_blacks--;
											return -1;
										}
										//System.out.println("W gore: "+x+" "+j);
									} // if mapState
								} // if map
							} // for
						} // if i
						
						if(j!=gridSize - 1) {
							for(int x=j+1; x < gridSize; ++x) {
								
								if(map[i][x] == map[i][j]) {
									
									if(mapState[i][x] == 0) {
										
										mapBlack[i][x] = true;
										new_blacks++;
										mapState[i][x] = 1;
										if(i!=0) {
											mapState[i-1][x] = 2;
										}
										if(i!=gridSize-1) {
											mapState[i+1][x] = 2;
										}
										if(x!=0) {
											mapState[i][x-1] = 2;
										}
										if(x!=gridSize-1) {
											mapState[i][x+1] = 2;
										}
										
										if(!checkAll(i, x, actual_black+new_blacks, mapBlack, mapState)) {
											
											mapBlack[i][x] = false;
											new_blacks--;
											return -1;
										}	
										//System.out.println("W prawo "+i+" "+x);
									} // if mapState
								} // if map
							} // for
						} // if j	
						
	
						if(j!=0) {
							for(int x=j-1; x >= 0; --x) {
	
								if(map[i][x] == map[i][j]) {
									
									if(mapState[i][x] == 0) {
										
										mapBlack[i][x] = true;
										new_blacks++;
										mapState[i][x] = 1;
										if(i!=0) {
											mapState[i-1][x] = 2;
										}
										if(i!=gridSize-1) {
											mapState[i+1][x] = 2;
										}
										if(x!=0) {
											mapState[i][x-1] = 2;
										}
										if(x!=gridSize-1) {
											mapState[i][x+1] = 2;
										}
										
										if(!checkAll(i, x, actual_black+new_blacks, mapBlack, mapState)) {
											
											mapBlack[i][x] = false;
											new_blacks--;
											return -1;
										}
										//System.out.println("W lewo: "+i+" "+x);
									} // if mapState
									
								} // if map
							} // for
						} // if j	
					} // if state==2
				} //for j
			} // for i
		} // for twice
		return new_blacks;
	}
	
	/* method to check all cases if cell can be covered black */
	
	private boolean checkAll(int x, int y, int nrBlack, boolean[][] mapBlack, int[][] mapState) {
		
		nrBlack++;
		if(checkTouch(x, y, mapBlack)!=0) {
			//System.out.println("checkTouch "+x+" "+y);
			return false;
		}
		if(checkCut(mapBlack, nrBlack)) {
			//System.out.println("checkCut "+x+" "+y+" nrblack: "+nrBlack);
			return false;
		}
		if(!checkGreens(mapState)) {
			//System.out.println("checkGreens "+x+" "+y+" nrblack: "+nrBlack);
			return false;
		}
		
		return true;
	}
	
	/*
	private int checkNeighbours(int i, int j) {
		int k = 1;
		
		if(i != 0) {		
			if( map[i][j] == map[i-1][j]) {
				k++;
				
				if(j!=0 && map[i][j] == map[i-1][j-1] ) {
					k++;
				}

				if(j!=gridSize-1 && map[i][j] == map[i-1][j+1] ) {
					k++;
				}
			}
		}
		
		if(i != gridSize-1) {
			if( map[i][j] == map[i+1][j]) {
				k++;
				
				if(j!=0 && map[i][j] == map[i+1][j-1] ) {
					k++;
				}

				if(j!=gridSize-1 && map[i][j] == map[i+1][j+1] ) {
					k++;
				}
			}
		}
		
		if(j != 0) {
			if( map[i][j] == map[i][j-1]) {
				k++;
				
				if(i!=0 && map[i][j] == map[i-1][j-1] ) {
					k++;
				}

				if(i!=gridSize-1 && map[i][j] == map[i+1][j-1] ) {
					k++;
				}
			}
		}
		
		if(j != gridSize-1) {
			if( map[i][j] == map[i][j+1]) {
				k++;
				
				if(i!=0 && map[i][j] == map[i-1][j+1] ) {
					k++;
				}

				if(i!=gridSize-1 && map[i][j] == map[i+1][j+1] ) {
					k++;
				}

			}

		}
		return k;
	}
*/
	private int checkSidesCollision(boolean[][] newMapBlack, int x, int y) {
		int collisions = 0;
		
		int i = x + 1;
		while(i < gridSize) {
			if(newMapBlack[i][y] == false && map[i][y] == map[x][y]) {
				/*if(i==x+1)
					collisions+=2;
				else
					collisions++;
				*/
					collisions++;
				
				break;
			}
			i++;
		}
		i = x - 1;
		while(i >= 0) {
			if(newMapBlack[i][y] == false && map[i][y] == map[x][y]) {
				/*if(i==x-1)
					collisions+=2;
				else*/
					collisions++;
				break;
			}
			i--;
		}
		i = y + 1;
		while(i < gridSize) {
			if(newMapBlack[x][i] == false && map[x][i] == map[x][y]) {
				/*if(i==y+1)
					collisions+=2;
				else*/
					collisions++;
				break;
			}
			i++;
		}
		i = y - 1; //by� x zamiast y
		while(i >= 0) {
			if(newMapBlack[x][i] == false && map[x][i] == map[x][y]) {
			/*	if(i==y-1)
					collisions+=2;
				else*/
					collisions++;
				break;
			}
			i--;
		}
		return collisions;
	}
	
/*-----------------------------------------------------------------------------------------------------------*/	
	
	// return true if hitori is solved, no collisions at all
	private boolean check(boolean[][] clicked) {
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
	
	//return true if there is cut of white tiles
	
	private boolean checkCut(boolean[][] clicked, int howManyBlack) {
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

		if(points.size() == ((gridSize*gridSize) - howManyBlack))
			return false;
		else 
			return true;
	}
	
	
	// return >0 if there is a touch
	
	private int checkTouch(int x, int y, boolean[][] clicked) {
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
					
			if(checkCut(clicked, howManyBlack) || checkTouch(first, second, clicked) != 0){	// check if it's possible to cover it
				
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
		
		for(int i=0; i<gridSize-2; ++i){
			for(int j=0; j<gridSize-2; ++j) {
				
				if(map[i][j]==map[i][j+1]) {
					if(map[i][j+1]==map[i][j+2]) {
						System.out.println("trojka poziom: "+i+", "+j);
					}
				}
				
				if(map[i][j]==map[i+1][j]) {
					if(map[i+1][j]==map[i+2][j]) {
						System.out.println("trojka pion: "+i+", "+j);
					}
				}
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
