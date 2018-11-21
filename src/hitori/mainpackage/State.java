package hitori.mainpackage;

import java.awt.Color;

public class State {
	private int gridSize;
	private boolean[][] black;
	public int x, y;
	private int blackCount;
	private int sidesCollisions;
	private double chainLength;
	private double heuristic;
	private double cost;
	private double hc;
	private int isTerminal;  // 0- not terminal, 1 - terminal, 2 - collision (high cost function, not go in this state)
	private int weight, prevWeight;
	
	public State(int x, int y, int gridSize, boolean[][] black, int blackCount, int chainLength, int isTerminal, int sidesCollisions, int weight){
		this.x = x;
		this.y = y;
		this.gridSize = gridSize;
		this.black = black;
		this.blackCount = blackCount;
		this.chainLength = chainLength;
		this.isTerminal = isTerminal;
		this.sidesCollisions = sidesCollisions;
		this.weight = weight;
		
		
		
		cost = gridSize*gridSize + 0.5*chainLength;
		if(isTerminal == 2)
			cost *= gridSize*gridSize;
		
		heuristic = (gridSize*gridSize - 5*blackCount) - sidesCollisions;
		if(isTerminal == 1)
			heuristic = 0;
		hc = heuristic + cost - 2*weight;
		
		System.out.println("new state hc: "+hc);
	}
	
	public double getHC() {
		return hc;
	}
	
	public boolean getBlack(int x, int y) {
		return black[x][y];
	}
	
	public boolean[][] getMapBlack() {
		return black;
	}
	
	public int getBlackCount() {
		return blackCount;
	}
	
	public int getIsTerminal() {
		return isTerminal;
	}
}
