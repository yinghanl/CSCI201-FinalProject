package main;

abstract class Space {
	private Moveable occupant;
	
	private int x, y;
	
	public Space(int x, int y){
		this.x = x;
		this.y = y;
		occupant = null;
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
	
	public Space getNorth() throws BoundaryException{
		if(x == 0)
			throw new BoundaryException();
		else
			return board.getSpace(x-1, y);
	}
	
	public Space getSouth(){
		if(x==19)
			throw new BoundaryException();
		else
			return board.getSpace(x+1, y);
		
	}
	
	public Space getWest(){
		if(y==0)
			throw new BoundaryException();
		else
			return board.getSpace(x, y-1);
		
	}
	
	public Space getEast(){
		if(y==31)
			throw new BoundaryException();
		else
			return board.getSpace(x, y+1);
		
	}
	
}
