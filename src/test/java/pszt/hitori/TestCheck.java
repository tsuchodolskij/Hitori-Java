package pszt.hitori;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pszt.algorithm.Algorithm;


public class TestCheck {

	int gridSize = 5; // should be > 2

	@Test
	public void checkFunctionWithAllNumbersSameNoBlackShouldReturnTrue() {
		Algorithm tester = new Algorithm(gridSize);

		Integer[][] map = new Integer[gridSize][gridSize];
		boolean[][] clicked = new boolean[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				clicked[i][j] = false;
				map[i][j] = 1;
			}
		}

		boolean collision = false;
		Integer[][] ret = tester.check(clicked, map);
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (ret[i][j] == 1)
					collision = true;
			}
		}

		assertEquals(true, collision);
	}

	@Test
	public void checkFunctionWithAllNumbersSameAllBlackShouldReturnFalse() {
		Algorithm tester = new Algorithm(gridSize);

		Integer[][] map = new Integer[gridSize][gridSize];
		boolean[][] clicked = new boolean[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				clicked[i][j] = true;
				map[i][j] = 1;
			}
		}

		boolean collision = false;
		Integer[][] ret = tester.check(clicked, map);
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (ret[i][j] == 1)
					collision = true;
			}
		}

		assertEquals(false, collision);
	}

	@Test
	public void checkFunctionWithNumbersDiagonallyNoBlackShouldReturnFalse() {
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

		boolean collision = false;
		Integer[][] ret = tester.check(clicked, map);
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (ret[i][j] == 1)
					collision = true;
			}
		}

		assertEquals(false, collision);
	}

	// in right top corner duplicate number of [0][0]
	@Test
	public void checkFunctionWithOneRepetitionInCornersNoBlackShouldReturnTrue() {
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

		boolean collision = false;
		Integer[][] ret = tester.check(clicked, map);
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (ret[i][j] == 1)
					collision = true;
			}
		}

		assertEquals(true, collision);
	}

	// in right top corner duplicate number of [0][0], but its black so no collision
	@Test
	public void checkFunctionWithOneRepetitionInCornersOneCornerBlackShouldReturnFalse() {
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

		boolean collision = false;
		Integer[][] ret = tester.check(clicked, map);
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (ret[i][j] == 1)
					collision = true;
			}
		}

		assertEquals(false, collision);
	}
}
