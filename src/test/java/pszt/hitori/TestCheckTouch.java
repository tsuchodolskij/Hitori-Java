package pszt.hitori;

import static org.junit.jupiter.api.Assertions.*;

import pszt.algorithm.Algorithm;


public class TestCheckTouch {

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
}
