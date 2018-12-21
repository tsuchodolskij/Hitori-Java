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
	private int Nr;
	
	public State(int x, int y, int gridSize, boolean[][] black, int blackCount, int chainLength, int isTerminal, int sidesCollisions, int nr){
		this.x = x;
		this.y = y;
		this.gridSize = gridSize;
		this.black = black;
		this.blackCount = blackCount;
		this.chainLength = chainLength;
		this.isTerminal = isTerminal;
		this.sidesCollisions = sidesCollisions;
		this.Nr = nr;		
		
		cost = gridSize*gridSize; //+ chainLength;
		if(isTerminal == 2)
			cost *= gridSize*gridSize;
		
		heuristic = (gridSize*gridSize - 2*blackCount);
		if(isTerminal == 1)
			heuristic = 0;
		hc = heuristic + cost;
		
		System.out.println("\nNew state hc: "+hc);
		System.out.println("New state Nr: "+Nr);
	}
	
	public double getHC() {
		return hc;
	}
	public int getNr() {
		return Nr;
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
	public void setCost(int cost) {
		this.cost = cost;
	}
}
