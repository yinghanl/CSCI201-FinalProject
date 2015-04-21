package main;

import java.io.Serializable;

abstract class Space implements Serializable{
	private static final long serialVersionUID = 1;
	private Moveable occupant;
	private Space north, south, east, west, northWest, northEast, southWest, southEast;
	private int x, y;
	
	public Space(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Moveable getMoveable(){
		return occupant;
	}
	
	public void setOccupant(Moveable moveable){
		occupant = moveable;
	}
	
	public void removeOccupant(){
		occupant = null;
	}
	
	public boolean isOccupied(){
		return (occupant != null);
	}
	
	public void setNorth(Space s){
		north = s;
	}
	
	public void setSouth(Space s){
		south = s;
	}
	
	public void setEast(Space s){
		east = s;
	}
	
	public void setWest(Space s){
		west = s;
	}
	
	public void setNorthWest(Space s){
		northWest = s;
	}
	
	public void setNorthEast(Space s){
		northEast = s;
	}
	
	public void setSouthWest(Space s){
		southWest = s;
	}
	
	public void setSouthEast(Space s){
		southEast = s;
	}
	
	public Space getNorth() throws BoundaryException{
		if(north == null)
			throw new BoundaryException();
		else
			return north;
	}
	
	public Space getSouth() throws BoundaryException{
		if(south == null)
			throw new BoundaryException();
		else
			return south;
	}
	
	public Space getWest() throws BoundaryException{
		if(west == null)
			throw new BoundaryException();
		else
			return west;	
	}
	
	public Space getEast() throws BoundaryException{
		if(east == null)
			throw new BoundaryException();
		else
			return east;	
	}
	
	public Space getNorthEast() throws BoundaryException{
		if(northEast == null)
			throw new BoundaryException();
		else
			return northEast;
	}
	
	public Space getSouthEast() throws BoundaryException{
		if(southEast == null)
			throw new BoundaryException();
		else
			return southEast;
	}
	
	public Space getNorthWest() throws BoundaryException{
		if(northWest == null)
			throw new BoundaryException();
		else
			return northWest;
	}
	
	public Space getSouthWest() throws BoundaryException{
		if(southWest == null)
			throw new BoundaryException();
		else
			return southWest;
	}
	
	public String toString(){
		return x + " " + y;
	}
	
	
}
