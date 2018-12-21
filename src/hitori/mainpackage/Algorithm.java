package hitori.mainpackage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Algorithm {
	private int gridSize;
	private int howManyBlack;
	private Integer[][] map;
	private GridPane grid;
	private int stateNr = 0;
	
	public Algorithm(int gridSize) {
		this.gridSize = gridSize;
	}
	
	public void setMap(Integer[][] map) {
		this.map = map;
	}
	public void setGrid(GridPane grid) {
		this.grid = grid;
	}

	public void aStar() {
		
		ArrayList<State> states = new ArrayList<State>();
		
		// expanding zero state
		boolean [][] newMapBlack = checkNeighbors();
		grid.updateMap(newMapBlack);
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
			for (int i = 0; i < states.size(); i++) {
				//System.out.println("HC:" + states.get(i).getHC());
				if(states.size() > 0 && states.get(i).getHC() < lowestHC.getHC()) {
					lowestHC = states.get(i);
					lowestIndex = i;
				}
			}
			try {
				TimeUnit.SECONDS.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("LOWEST HC:" + lowestHC.getHC());
			System.out.println("LOWEST NR:" + lowestHC.getNr());

			/*for (int k = 0; k < gridSize; k++) {
				System.out.println("");
				for (int x = 0; x < gridSize; x++) {
					if(lowestHC.getBlack(k, x) == true)
						System.out.print("1 ");
					else 
						System.out.print("0 ");
				}
			}
			System.out.println("");*/
			
			if(lowestHC.getIsTerminal() == 1) { // we have the solution
				System.out.println("Znaleziono rozwiazanie: x:" + lowestHC.x+ " y: " + lowestHC.y);
				grid.updateMap(lowestHC.getMapBlack());
				break;
			}
			
			states.remove(lowestIndex);			// deleting lowestHC from states list, because i expand him

			tmp = expand(lowestHC);
			if(tmp != null)
				for(State i : tmp) {     			// adding every son of lowestHC
					states.add(i);
				}
			
			grid.updateMap(lowestHC.getMapBlack());
			/*try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
		System.out.println("ALGORYTM A* ZAKONCZYL DZIALANIE");
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
					for(int m = 0; m < gridSize; m++) { // scanning x looking for the same value
						if(checked[k][m] == false && map[k][m] == value && e.getBlack(k,m) == false) {
							vect.add(new Point(k,m));
							break; // to find only first in row/column (because of repetitions)
						}
					}
					k = vect.get(z).y;
					for(int m = 0; m < gridSize; m++) { // scanning y looking for the same value
						if(checked[m][k] == false && map[m][k] == value && e.getBlack(m,k) == false) {
							vect.add(new Point(m,k));
							break;
						}
					}
				} // z for
				
				if(vect.size() > 1) { // if there is collision, make other states
					int terminalStates = 0;
					boolean wrong[] = new boolean[vect.size()];
					for(int ww = 0; ww < vect.size(); ++ww)
						wrong[ww] = false;
					
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
						
						setGreens(mapState);
						int isTerminal = 1, weight = 0;
						
						if(checkAll(vect.get(m).x, vect.get(m).y, e.getBlackCount(), newMapBlack, mapState, map))
						{
							
							int new_blacks = eliminateOther(mapState, newMapBlack, e.getBlackCount(), map);
							if(new_blacks != -1)
							{
							
								/*System.out.println("Newblacks: "+new_blacks);
								
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
								}*/
								new_blacks++;
							
								Integer[][] red = check(newMapBlack, map);
								a : for (int i1 = 0; i1 < gridSize; i1++) {
									for (int j1 = 0; j1 < gridSize; j1++) {
										if(red[i1][j1] == 1) {
											isTerminal = 0; // if at least one tile is red, it is no terminal
											break a;
										}
									}
								}
								
								int sidesCollision = checkSidesCollision(newMapBlack, vect.get(m).x, vect.get(m).y, map);
								
								terminalStates++;
								states.add(new State(vect.get(m).x, vect.get(m).y, gridSize, newMapBlack, 
									e.getBlackCount()+new_blacks, vect.size(), isTerminal, sidesCollision, stateNr));
								stateNr++;
							}
						}
						else {
							wrong[m] = true; 
						}
					}	
					for(int w1=0; w1<vect.size()-1; ++w1) {
						if(wrong[w1]) {
							for(int w2=w1+1; w2<vect.size(); ++w2) {
								if(wrong[w2]) {
									if(vect.get(w1).x == vect.get(w2).x ||
											vect.get(w1).y == vect.get(w2).y) {
										System.out.println("wyjebalo: x1:"+vect.get(w1).x+" y1:"+vect.get(w1).y
												+" x2:"+vect.get(w2).x+" y2:"+vect.get(w2).y);
										return null;
									}
								}
							}
						}
					}
					
					
				}// vect.size()
				//System.out.println(" ");
			
				vect.clear(); // every value like first was found, so prepare for another loop
				
			} // if
		} // j for
	} // i for
	
	return states;
}	

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
	
	setGreens(mapState);
	howManyBlack+=eliminateOther(mapState, newMapBlack, howManyBlack-1, map);
	
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
	
	/* eliminate all possible cells for this state */
	public int eliminateOther(int [][] mapState, boolean[][] mapBlack, int actual_black, Integer[][] map) {		
		
		int new_blacks = 0;
		for(int twice = 0; twice < 2; ++twice) {
			for(int i=0; i<gridSize; ++i) {
				for(int j=0; j<gridSize; ++j) {
					
					int go_up = 0;
					if(mapState[i][j]==2) {
						
						if(i!=gridSize - 1) {
							for(int x=i+1; x < gridSize; ++x) {
								
								if(map[x][j] == map[i][j]) {
									
									if(mapState[x][j] == 0){
										
										
										mapBlack[x][j] = true;
										new_blacks++;
										mapState[x][j] = 1;
										if(x!=0) {
											go_up = 1;
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
									
										if(!checkAll(x, j, actual_black+new_blacks, mapBlack, mapState, map)){	
											
											mapBlack[x][j] = false;
											new_blacks--;
											return -1;
										}
										else {
											if(go_up == 1)
												twice = 0;
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
											go_up = 1;
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
										
										if(!checkAll(x, j, actual_black+new_blacks, mapBlack, mapState, map))
										{	mapBlack[x][j] = false;
											new_blacks--;
											return -1;
										}
										else {
											if(go_up == 1)
												twice = 0;
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
											go_up = 1;
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
										
										if(!checkAll(i, x, actual_black+new_blacks, mapBlack, mapState, map)) {
											
											mapBlack[i][x] = false;
											new_blacks--;
											return -1;
										}	
										else {
											if(go_up == 1)
												twice = 0;
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
											go_up = 1;
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
										
										if(!checkAll(i, x, actual_black+new_blacks, mapBlack, mapState, map)) {
											
											mapBlack[i][x] = false;
											new_blacks--;
											return -1;
										}
										else {
											if(go_up == 1)
												twice = 0;
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
	
	
	/* cover neighbors of black as greens */
	public void setGreens(int [][] mapState) {
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
	
	/* method to check all cases if cell can be covered black */
	public boolean checkAll(int x, int y, int nrBlack, boolean[][] mapBlack, int[][] mapState, Integer[][] map) {
		nrBlack++;
		if(checkTouch(x, y, mapBlack)!=0) {
			//System.out.println("checkTouch "+x+" "+y);
			return false;
		}
		if(checkCut(mapBlack, nrBlack)) {
			//System.out.println("checkCut "+x+" "+y+" nrblack: "+nrBlack);
			return false;
		}
		if(!checkGreens(mapState, map)) {
			//System.out.println("checkGreens "+x+" "+y+" nrblack: "+nrBlack);
			return false;
		}
		
		return true;
	}
	
	/* every black cell has 4 adjacent neighbors which are set green and can't be put black anymore
	   so there can't be two equal numbers in one row or column adjacent to black cells*/
	public boolean checkGreens(int[][] mapState, Integer[][] map) {
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

	// return true if there is no collisions at all
	public Integer[][] check(boolean[][] clicked, Integer[][] map) {
		Integer[][] checked = new Integer[gridSize][gridSize];  // initialized with 0, 0-unvisited, 1-with collision, 2-without collision and black, 3 - black 
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				checked[i][j] = 0;
			}
		}
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if(clicked[i][j] == true) {
					checked[i][j] = 3;
					continue;
				}
				int k = j+1;
				int collisions = 0;
				while(k < gridSize) {
					if(clicked[i][k] == false && map[i][k] == map[i][j]) {
						checked[i][k] = 1; 
						collisions++;
					}
					k++;
				}
				int l = i+1;
				while(l < gridSize) {
					if(clicked[l][j] == false && map[l][j] == map[i][j]) {
						checked[l][j] = 1; 
						collisions++;
					}
					l++;
				}
				
				// there was no collision and it is no black tile
				if(collisions == 0 && checked[i][j] == 0)
					checked[i][j] = 2;
				else
					checked[i][j] = 1;
			}
		}
		
		return checked;
	}	
	
	// return >0 if there is a touch
	public int checkTouch(int x, int y, boolean[][] clicked) {
		int withHowMany = 0;

		if (x != 0) {
			if (clicked[x - 1][y] == true)
				withHowMany++;
		}
		if (x != gridSize - 1) {
			if (clicked[x + 1][y] == true)
				withHowMany++;
		}
		if (y != 0) {
			if (clicked[x][y - 1] == true)
				withHowMany++;
		}
		if (y != gridSize - 1) {
			if (clicked[x][y + 1] == true)
				withHowMany++;
		}
		return withHowMany;
	}
	
	// return true if there is cut of white tiles
	public boolean checkCut(boolean[][] clicked, int howManyBlack) {
		ArrayList<Point> points = new ArrayList<Point>();
		boolean[][] visited = new boolean[gridSize][gridSize];

		a: for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (clicked[i][j] == false) {
					points.add(new Point(i, j));
					visited[i][j] = true;
					// System.out.println("Poczatkowy pkt: " + i + " "+ j);
					break a;
				}
			}
		}

		for (int i = 0; i < points.size(); i++) {
			int x = points.get(i).x;
			int y = points.get(i).y;

			// System.out.println("Jestem na: " + x + " "+ y);

			if (x != 0 && visited[x - 1][y] == false && clicked[x - 1][y] == false) {
				points.add(new Point(x - 1, y));
				visited[x - 1][y] = true;
				// System.out.println("Dodaje: " + (x-1) + " "+ y);
			}
			if (x != gridSize - 1 && visited[x + 1][y] == false && clicked[x + 1][y] == false) {
				points.add(new Point(x + 1, y));
				visited[x + 1][y] = true;
				// System.out.println("Dodaje: " + (x+1) + " "+ y);
			}
			if (y != 0 && visited[x][y - 1] == false && clicked[x][y - 1] == false) {
				points.add(new Point(x, y - 1));
				visited[x][y - 1] = true;
				// System.out.println("Dodaje: " + x + " "+ (y-1));
			}
			if (y != gridSize - 1 && visited[x][y + 1] == false && clicked[x][y + 1] == false) {
				points.add(new Point(x, y + 1));
				visited[x][y + 1] = true;
				// System.out.println("Dodaje: " + x + " "+ (y+1));
			}
		}

		if (points.size() == ((gridSize * gridSize) - howManyBlack))
			return false;
		else
			return true;
	}
	
	// show with how many sides is collision for this tile
	public int checkSidesCollision(boolean[][] newMapBlack, int x, int y, Integer[][] map) {
		int collisions = 0;
		
		int i = x + 1;
		while(i < gridSize) {
			if(newMapBlack[i][y] == false && map[i][y] == map[x][y]) {
				collisions++;
				break;
			}
			i++;
		}
		i = x - 1;
		while(i >= 0) {
			if(newMapBlack[i][y] == false && map[i][y] == map[x][y]) {
				collisions++;
				break;
			}
			i--;
		}
		i = y + 1;
		while(i < gridSize) {
			if(newMapBlack[x][i] == false && map[x][i] == map[x][y]) {
				collisions++;
				break;
			}
			i++;
		}
		i = y - 1;
		while(i >= 0) {
			if(newMapBlack[x][i] == false && map[x][i] == map[x][y]) {
				collisions++;
				break;
			}
			i--;
		}
		return collisions;
	}	
}
