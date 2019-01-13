package pszt.map;

import java.io.File;
import java.util.Scanner;

import pszt.hitori.Game;

public class MapLoader {
	
	private Scanner loader;
	private int gridSize;
	private Integer[][] map;
	
	public Integer getCell (int i, int j) {
		return map[i][j];
	}
	
	public void openFile() {
		try {
			loader = new Scanner (new File ("map.txt"));
		}
		catch (Exception e) {
			System.out.println("File can not be opened");
		}
	}
	
	public void readFile() {
		try {
			gridSize = Integer.parseInt(loader.next());
			map = new Integer[gridSize][gridSize];
			int i = 0;
			int j = 0;
			while (loader.hasNext()) {
				if (j == gridSize) {
					j = 0;
					i++;
				}
				map[i][j] = Integer.parseInt(loader.next());
				j++;
			}
			loader.close();
		}
		catch (Exception e) {
			e.toString();
			e.printStackTrace();
		}
	}
	
	public void loadMap() {
		openFile();
		readFile();
		Game.buildGrid(gridSize, this);
	}
}
