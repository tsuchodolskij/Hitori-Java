package hitori.mainpackage;

import java.io.File;
import java.util.Scanner;

public class MapLoader {
	
	private static final long serialVersionUID = -4426099419122563616L;
	
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
		/*
		System.out.println(gridSize);
		for(int i=0; i<gridSize; ++i) {
			for(int j=0; j<gridSize; ++j) {
				if(map[i][j]>9)
					System.out.print(map[i][j]+" ");
				else
					System.out.print(map[i][j]+"  ");
			}
			System.out.println();
		}
		*/
	}
	
	public void loadMap() {
		openFile();
		readFile();
		Game.buildGrid(gridSize, this);
	}
}
