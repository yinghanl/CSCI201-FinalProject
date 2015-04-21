package main;


import java.io.Serializable;
import java.awt.image.BufferedImage;
abstract class Moveable extends Thread implements Serializable{
	private static final long serialVersionUID = 1;

	protected Space currentLocation, previousLocation;
	protected BufferedImage img;
	protected boolean couldMove = true;
	
	public Moveable(Space loc){
		currentLocation = loc;
		previousLocation = null;
	}
	
	//max x = 20
	//max y = 32
	public void move(int direction) throws BoundaryException{
		
		previousLocation = currentLocation;
		//currentLocation.removeOccupant();
		Space s;
		try{
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
				case 3:
					currentLocation = currentLocation.getWest();
					break;
				//4 = northeast
				case 4:
					currentLocation = currentLocation.getNorthEast();
					break;
				//5 = northwest
				case 5:
					currentLocation = currentLocation.getNorthWest();
					break;
				//6 = southeast
				case 6:
					currentLocation = currentLocation.getSouthEast();
					break;
				//7 = southwest
				case 7:
					currentLocation = currentLocation.getSouthWest();
					break;
			}
			if(currentLocation instanceof TowerSpace || currentLocation.isOccupied() || currentLocation instanceof PathSpace){
				throw new BoundaryException();
			}
			couldMove = true;
		}
		catch(BoundaryException be){
			currentLocation = previousLocation;
			couldMove = false;
			System.out.println("Boundary exception in Moveable.move()");
		}
		previousLocation.removeOccupant();
		currentLocation.setOccupant(this);
		
		
	}
	
	public Space getLocation(){
		return currentLocation;
	}
	
	public Space getPrevious(){
		return previousLocation;
	}
	
	public BufferedImage getMoveableImage(){
		return img;
	}
	
	public boolean moveableCouldMove(){
		return couldMove;
	}
	
}
