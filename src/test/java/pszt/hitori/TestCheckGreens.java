package pszt.hitori;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pszt.algorithm.Algorithm;

public class TestCheckGreens {
	int gridSize = 5;
	
	
	@Test
	public void checkFunctionGreensForOneShouldReturnTrue() {
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
		
		int[][] mapState = new int[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				mapState[i][j] = 0;}}		
		
		mapState[0][0] = 2;
		assertEquals(true, tester.checkGreens(mapState, map));
	}
	
	@org.junit.jupiter.api.Test
	public void checkFunctionGreensForMoreShouldReturnFalse() {
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
		
		map[0][1] = 10;
		map[4][1] = 10;
		
		int[][] mapState = new int[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				mapState[i][j] = 0;}}		
		
		mapState[0][1] = 2;
		mapState[4][1] = 2;
		
		assertEquals(false, tester.checkGreens(mapState, map));
	}

}
