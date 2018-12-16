package tests;

import static org.junit.jupiter.api.Assertions.*;

import hitori.mainpackage.Algorithm;

public class TestCheckCut {

	int gridSize = 5; // should be > 2

	@org.junit.jupiter.api.Test
	public void cutFunctionWithBlackTilesInLineShouldReturnTrue() {
		Algorithm tester = new Algorithm(gridSize);

		int howManyBlack = 0;
		boolean[][] clicked = new boolean[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (i == 1) {
					clicked[i][j] = true;
					howManyBlack++;
				} else
					clicked[i][j] = false;
			}
		}

		assertEquals(true, tester.checkCut(clicked, howManyBlack));
	}

	@org.junit.jupiter.api.Test
	public void cutFunctionWithBlackTilesDiagonallyShouldReturnTrue() {
		Algorithm tester = new Algorithm(gridSize);

		int howManyBlack = 0;
		boolean[][] clicked = new boolean[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (i == j) {
					clicked[i][j] = true;
					howManyBlack++;
				} else
					clicked[i][j] = false;
			}
		}

		assertEquals(true, tester.checkCut(clicked, howManyBlack));
	}

	@org.junit.jupiter.api.Test
	public void cutFunctionWithBlackTilesCutCornerShouldReturnTrue() {
		Algorithm tester = new Algorithm(gridSize);

		int howManyBlack = 0;
		boolean[][] clicked = new boolean[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if ((i == 0 && j == 1) || (i == 1 && j == 0)) {
					clicked[i][j] = true;
					howManyBlack++;
				} else
					clicked[i][j] = false;
			}
		}

		assertEquals(true, tester.checkCut(clicked, howManyBlack));
	}

	@org.junit.jupiter.api.Test
	public void cutFunctionWithBlackTilesNotCompleteLineShouldReturnFalse() {
		Algorithm tester = new Algorithm(gridSize);

		int howManyBlack = 0;
		boolean[][] clicked = new boolean[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (i == 1 && j != gridSize - 1) {
					clicked[i][j] = true;
					howManyBlack++;
				} else
					clicked[i][j] = false;
			}
		}

		assertEquals(false, tester.checkCut(clicked, howManyBlack));
	}

	@org.junit.jupiter.api.Test
	public void cutFunctionWithBlackTilesInLineTopShouldReturnFalse() {
		Algorithm tester = new Algorithm(gridSize);

		int howManyBlack = 0;
		boolean[][] clicked = new boolean[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (i == 0) {
					clicked[i][j] = true;
					howManyBlack++;
				} else
					clicked[i][j] = false;
			}
		}

		assertEquals(false, tester.checkCut(clicked, howManyBlack));
	}
}
