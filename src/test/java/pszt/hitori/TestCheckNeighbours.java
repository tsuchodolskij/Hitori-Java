package pszt.hitori;

import static org.junit.jupiter.api.Assertions.assertEquals;

import pszt.algorithm.Algorithm;


public class TestCheckNeighbours {

	int gridSize = 5;
	
	@org.junit.jupiter.api.Test
	public void checkFunctionNeighboursForTripleShouldCoverTwoReturnTrue() {
		Algorithm tester = new Algorithm(gridSize);
		
		Integer[][] map = new Integer[gridSize][gridSize];
		int k;
		for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j + 1;
				k = (k + 1) % gridSize;
			}
		}
		map[0][1] = map[0][2] = map[0][3] = 10;
		tester.setMap(map);
		boolean[][] mapBlack = tester.checkNeighbors();
		
		assertEquals(true, mapBlack[0][1]);
		assertEquals(true, mapBlack[0][3]);
	}
	
	@org.junit.jupiter.api.Test
	public void checkFunctionNeighboursForPairPlusOneInRowShouldCoverOneReturnPositive() {
		Algorithm tester = new Algorithm(gridSize);
		
		Integer[][] map = new Integer[gridSize][gridSize];
		int k;
		for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j + 1;
				k = (k + 1) % gridSize;
			}
		}
		map[0][1] = map[0][2] = map[0][4] = 10;
		tester.setMap(map);
		boolean[][] mapBlack = tester.checkNeighbors();
		
		assertEquals(true, mapBlack[0][4]);
		assertEquals(false, mapBlack[0][1]);
		assertEquals(false, mapBlack[0][2]);
	}
	
	@org.junit.jupiter.api.Test
	public void checkFunctionNeighboursForPairPlusOneInColumnShouldCoverOneReturnPositive() {
		Algorithm tester = new Algorithm(gridSize);
		
		Integer[][] map = new Integer[gridSize][gridSize];
		int k;
		for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j + 1;
				k = (k + 1) % gridSize;
			}
		}
		map[3][1] = map[4][1] = map[1][1] = 10;
		tester.setMap(map);
		boolean[][] mapBlack = tester.checkNeighbors();
		
		assertEquals(true, mapBlack[1][1]);
		assertEquals(false, mapBlack[3][1]);
		assertEquals(false, mapBlack[4][1]);
	}
	
	@org.junit.jupiter.api.Test
	public void checkFunctionNeighboursForNoTripleShouldReturnFalse() {
		Algorithm tester = new Algorithm(gridSize);
		
		Integer[][] map = new Integer[gridSize][gridSize];
		int k;
		for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j + 1;
				k = (k + 1) % gridSize;
			}
		}
		map[0][0] = map[0][2] = map[0][4] = 10;
		tester.setMap(map);
		boolean[][] mapBlack = tester.checkNeighbors();
		
		assertEquals(false, mapBlack[0][0]);
		assertEquals(false, mapBlack[0][2]);
		assertEquals(false, mapBlack[0][4]);
	}
}
