package main;

abstract class Space {
	private Coordinate coord;
	
	public Space(int x, int y){
		coord = new Coordinate(x, y);
	}
	
	public Coordinate getCoord(){
		return coord;
	}
	
	
}
