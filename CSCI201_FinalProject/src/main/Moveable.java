package main;

import java.io.Serializable;

abstract class Moveable extends Thread implements Serializable{
	private static final long serialVersionUID = 1;
	protected Space currentLocation, previousLocation;
	
	public Moveable(Space loc){
		currentLocation = loc;
		previousLocation = null;
	}
	
	//max x = 20
	//max y = 32
	public void move(int direction) throws BoundaryException{
		previousLocation = currentLocation;
		currentLocation.removeOccupant();
		switch(direction){
			//0 = up
			case 0:
				currentLocation = currentLocation.getNorth();
				break;
			//1 = down
			case 1:
				currentLocation = currentLocation.getSouth();
				break;
			//2 = right
			case 2:
				currentLocation = currentLocation.getEast();
				break;
			//3 = left
			default:
				currentLocation = currentLocation.getWest();
				break;
		}
		currentLocation.setOccupant(this);
		
		
	}
	
	public Space getLocation(){
		return currentLocation;
	}
	
	public Space getPrevious(){
		return previousLocation;
	}
	
}
