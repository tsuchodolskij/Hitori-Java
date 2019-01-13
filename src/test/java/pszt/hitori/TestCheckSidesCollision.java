package pszt.hitori;

import static org.junit.jupiter.api.Assertions.*;

import pszt.algorithm.Algorithm;


public class TestCheckSidesCollision {

	int gridSize = 5; // should be > 2

	// in right top corner duplicate number of [0][0], for 0 0 one collision
	@org.junit.jupiter.api.Test
	public void checkSidesCollisionOneRepetitionInCornerShouldReturnPositive() {
		Algorithm tester = new Algorithm(gridSize);

		Integer[][] map = new Integer[gridSize][gridSize];
		boolean[][] clicked = new boolean[gridSize][gridSize];
		int k;
		for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j + 1;
				k = (k + 1) % gridSize;
				clicked[i][j] = false;
			}
		}
		map[0][gridSize - 1] = map[0][0];

		assertEquals(1, tester.checkSidesCollision(clicked, 0, 0, map));
	}

	// in right top corner duplicate number of [0][0], but corner is black, for 0 0
	// zero collisions
	@org.junit.jupiter.api.Test
	public void checkSidesCollisionOneRepetitionInCornerButBlackShouldReturnZero() {
		Algorithm tester = new Algorithm(gridSize);

		Integer[][] map = new Integer[gridSize][gridSize];
		boolean[][] clicked = new boolean[gridSize][gridSize];
		int k;
		for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j + 1;
				k = (k + 1) % gridSize;
				clicked[i][j] = false;
			}
		}
		map[0][gridSize - 1] = map[0][0];
		clicked[0][gridSize - 1] = true;

		assertEquals(0, tester.checkSidesCollision(clicked, 0, 0, map));
	}

	// in right top corner duplicate number of [0][0], for 0 gridsize-1 two
	// collision
	@org.junit.jupiter.api.Test
	public void checkSidesCollisionTwoRepetitionInCornerShouldReturnPositive() {
		Algorithm tester = new Algorithm(gridSize);

		Integer[][] map = new Integer[gridSize][gridSize];
		boolean[][] clicked = new boolean[gridSize][gridSize];
		int k;
		for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j + 1;
				k = (k + 1) % gridSize;
				clicked[i][j] = false;
			}
		}
		map[0][gridSize - 1] = map[0][0];

		assertEquals(2, tester.checkSidesCollision(clicked, 0, gridSize - 1, map));
	}

	// all values the same, no black, four sides collisions for 1 1 tile
	@org.junit.jupiter.api.Test
	public void checkSidesCollisionAllValuesSameShouldReturnPositive() {
		Algorithm tester = new Algorithm(gridSize);

		Integer[][] map = new Integer[gridSize][gridSize];
		boolean[][] clicked = new boolean[gridSize][gridSize];

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				map[i][j] = 1;
			}
		}

		assertEquals(4, tester.checkSidesCollision(clicked, 1, 1, map));
	}
}
