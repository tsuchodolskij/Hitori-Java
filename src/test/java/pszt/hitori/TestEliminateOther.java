package pszt.hitori;

import static org.junit.jupiter.api.Assertions.*;


public class TestEliminateOther {

	int gridSize = 5;
	/*map:			    mapState:
	1  1  1  1  1		0 0 0 0 0
	1  1  1  1  10      0 0 0 0 0
	14 1  2  11 2       0 0 2 0 0
	3  3 (6) 4  12      0 2 1 2 0
	15 1  4  4  13      0 0 2 0 0
	*/
	
	@org.junit.jupiter.api.Test
	public void eliminateFunctionShouldReturnTrue() {
		
		Algorithm tester = new Algorithm(gridSize);
		
		Integer[][] map = new Integer[gridSize][gridSize];
		for(int i=0; i<gridSize; ++i) {
			for(int j=0; j<gridSize; ++j) {
				map[i][j] = 1;}}
		map[1][4]=10; map[2][0]=14; map[2][2]=2; map[2][3]=11; map[2][4]=2; map[3][0]=3; map[3][1]=3; 
		map[3][2]=6; map[3][3]=4; map[3][4]=12; map[4][0]=15; map[4][2]=map[4][3]=4; map[4][4]=13;
		
		boolean[][] mapBlack = new boolean[gridSize][gridSize];
		for(int i=0; i<gridSize; ++i) {
			for(int j=0; j<gridSize; ++j) {
				mapBlack[i][j] = false;}}
		mapBlack[3][2]=true;
		
		int[][] mapState = new int[gridSize][gridSize];
		for(int i=0; i<gridSize; ++i) {
			for(int j=0; j<gridSize; ++j) {
				mapState[i][j] = 0;}}
		mapState[3][2] = 1;
		tester.setGreens(mapState);
				
		int actualBlack=0, blackCount;		
		blackCount = tester.eliminateOther(mapState, mapBlack, actualBlack, map);

		assertEquals(3, blackCount, "There should be 3 eliminated tiles");
		assertEquals(true, mapBlack[2][4], "Tile should be covered");
		assertEquals(true, mapBlack[3][0], "Tile should be covered");
		assertEquals(true, mapBlack[4][3], "Tile should be covered");
		assertEquals(false, mapBlack[4][2], "Tile should not be covered");
		assertEquals(false, mapBlack[3][3], "Tile should not be covered");
		assertEquals(1, mapState[2][4], "State should be 1");
		assertEquals(2, mapState[2][3], "State should be 2");
		assertEquals(2, mapState[1][4], "State should be 2");
		assertEquals(2, mapState[3][4], "State should be 2");
		assertEquals(1, mapState[3][0], "State should be 1");
		assertEquals(2, mapState[3][1], "State should be 2");
		assertEquals(2, mapState[2][0], "State should be 2");
		assertEquals(2, mapState[4][0], "State should be 2");
		assertEquals(1, mapState[4][3], "State should be 1");
		assertEquals(2, mapState[4][2], "State should be 2");
		assertEquals(2, mapState[4][4], "State should be 2");
		assertEquals(2, mapState[3][3], "State should be 2");
	}
}
