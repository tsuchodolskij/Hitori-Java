package hitori.mainpackage;

import java.util.ArrayList;

public class Algorithm {
	private int gridSize;
	
	public Algorithm(int gridSize) {
		this.gridSize = gridSize;
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

	// return true if hitori is solved, no collisions at all
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
