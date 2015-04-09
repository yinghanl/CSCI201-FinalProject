package main;

public class PathSpace extends Space{
	
	private Creep creep;
	
	public PathSpace(int x, int y){
		super(x, y);
	}
	
	public Creep getCreep(){
		return creep;
	}

}
