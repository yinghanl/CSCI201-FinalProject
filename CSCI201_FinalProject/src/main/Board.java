package main;

public class Board {
	private Space [][] map;
	private PathSpace [] creepPath;
	
	public Space getSpace(int x, int y){
		return map[x][y];
	}
}
