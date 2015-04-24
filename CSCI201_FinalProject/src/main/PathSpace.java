package main;

public class PathSpace extends Space{
	
	private Creep creep;
	private PathSpace nextPathSpace;
	
	public PathSpace(int x, int y){
		super(x, y);
		creep = null;
	}
	
	public Creep getCreep(){
		return creep;
	}
	
	public void setNext(PathSpace p){
		nextPathSpace = p;
	}
	
	public PathSpace getNext(){
		return nextPathSpace;
	}
	
	public void setCreep(Creep c){
		creep = c;
		setOccupant(c);
	}
	
	public void removeCreep(){
		removeOccupant();
		creep = null;
		
	}

}
