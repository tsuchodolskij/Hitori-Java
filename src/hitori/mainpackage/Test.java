package hitori.mainpackage;

import static org.junit.jupiter.api.Assertions.*;

class Test {

	int gridSize = 5; // should be > 2
	
	@org.junit.jupiter.api.Test
    public void touchFunctionWithAllBlackTilesShouldReturnPositive() {
        Algorithm tester = new Algorithm(gridSize); // argument = gridsize
        boolean[][] clicked = new boolean[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				clicked[i][j] = true;
			}
		}
        
        assertEquals(2, tester.checkTouch(0, 0, clicked), "In left top corner should touch 2 black tiles");
        assertEquals(2, tester.checkTouch(0, gridSize-1, clicked), "In right top corner should touch 2 black tiles");
        assertEquals(2, tester.checkTouch(gridSize-1, 0, clicked), "In left bottom corner should touch 2 black tiles");
        assertEquals(2, tester.checkTouch(gridSize-1, gridSize-1, clicked), "In right bottom corner should touch 2 black tiles");
        assertEquals(4, tester.checkTouch(1, 1, clicked), "In center should touch 4 black tiles");
    }
	
	@org.junit.jupiter.api.Test
    public void touchFunctionWithAllWhiteTilesShouldReturnZero() {
        Algorithm tester = new Algorithm(gridSize);
        boolean[][] clicked = new boolean[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				clicked[i][j] = false;
			}
		}
        
        assertEquals(0, tester.checkTouch(0, 0, clicked), "In left top corner should touch 0 tiles");
        assertEquals(0, tester.checkTouch(0, gridSize-1, clicked), "In right top corner should touch 0 tiles");
        assertEquals(0, tester.checkTouch(gridSize-1, 0, clicked), "In left bottom corner should touch 0 tiles");
        assertEquals(0, tester.checkTouch(gridSize-1, gridSize-1, clicked), "In right bottom corner should touch 0 tiles");
        assertEquals(0, tester.checkTouch(1, 1, clicked), "In center should touch 0 tiles");
    }
	
	@org.junit.jupiter.api.Test
    public void cutFunctionWithBlackTilesInLineShouldReturnTrue() {
        Algorithm tester = new Algorithm(gridSize);
        
        int howManyBlack = 0;
        boolean[][] clicked = new boolean[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if(i == 1) {
					clicked[i][j] = true;
					howManyBlack ++;
				}
				else
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
				if(i == j) {
					clicked[i][j] = true;
					howManyBlack ++;
				}
				else
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
				if((i == 0 && j == 1) || (i == 1 && j == 0)) {
					clicked[i][j] = true;
					howManyBlack ++;
				}
				else
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
				if(i == 1 && j != gridSize-1){
					clicked[i][j] = true;
					howManyBlack ++;
				}
				else
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
				if(i == 0){
					clicked[i][j] = true;
					howManyBlack ++;
				}
				else
					clicked[i][j] = false;
			}
		}
        
        assertEquals(false, tester.checkCut(clicked, howManyBlack));
    }
	
	@org.junit.jupiter.api.Test
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
				if(ret[i][j] == 1)
					collision = true;
			}
		}
        
        assertEquals(true, collision);
    }
	
	@org.junit.jupiter.api.Test
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
				if(ret[i][j] == 1)
					collision = true;
			}
		}
        
        assertEquals(false, collision);
    }
	
	@org.junit.jupiter.api.Test
    public void checkFunctionWithNumbersDiagonallyNoBlackShouldReturnFalse() {
        Algorithm tester = new Algorithm(gridSize);
        
        Integer[][] map = new Integer[gridSize][gridSize];
        boolean[][] clicked = new boolean[gridSize][gridSize];
        int k;
        for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j+1;
				k = (k+1)%gridSize;
				clicked[i][j] = false;
			}
		}
        
        boolean collision = false;
        Integer[][] ret = tester.check(clicked, map);
        for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if(ret[i][j] == 1)
					collision = true;
			}
		}
        
        assertEquals(false, collision);
    }
	
	// in right top corner duplicate number of [0][0]
	@org.junit.jupiter.api.Test
    public void checkFunctionWithOneRepetitionInCornersNoBlackShouldReturnTrue() {
        Algorithm tester = new Algorithm(gridSize);
        
        Integer[][] map = new Integer[gridSize][gridSize];
        boolean[][] clicked = new boolean[gridSize][gridSize];
        int k;
        for (int i = 0; i < gridSize; i++) {
			k = i;
			for (int j = 0; j < gridSize; j++) {
				map[i][k] = j+1;
				k = (k+1)%gridSize;
				clicked[i][j] = false;
			}
		}
        map[0][gridSize-1] = map[0][0];
        
        boolean collision = false;
        Integer[][] ret = tester.check(clicked, map);
        for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if(ret[i][j] == 1)
					collision = true;
			}
		}
        
        assertEquals(true, collision);
    }
	
	// in right top corner duplicate number of [0][0], but its black so no collision
	@org.junit.jupiter.api.Test
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
