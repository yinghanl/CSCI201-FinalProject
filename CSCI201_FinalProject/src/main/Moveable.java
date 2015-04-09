package main;

abstract class Moveable extends Thread{
	protected Space currentLocation;
	
	public Moveable(Space loc){
		currentLocation = loc;
	}
	
	//max x = 20
	//max y = 32
	public void move(int direction) throws BoundaryException{
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
		
		
	}
	
	public Space getLocation(){
		return currentLocation;
	}
	
}
