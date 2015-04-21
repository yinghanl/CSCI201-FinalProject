package main;

public class Creep extends Moveable{
	
	private int health;
	private PathSpace currentPathLocation;
	
	public Creep(PathSpace p){
		super(p); //may cause error
		currentPathLocation = p;
	}
	
	public boolean isDead(){
		return (health == 0);
	}
	
	public void hit(int amt){
		health-=amt;
	}
	
	public void run(){
		while(health>0){
			previousLocation = currentPathLocation;
			currentPathLocation.removeOccupant();
			try {
				sleep(1000);
				if(currentPathLocation.getNext() ==  null){
					//decrease the team's health, have reached the end
				}
				else{
					currentPathLocation = currentPathLocation.getNext();
					currentPathLocation.setOccupant(this);
				}
				
			} catch (InterruptedException ie) {
				System.out.println("Interrupted Exception in creep.run() " + ie.getMessage());
			}
		}
		currentPathLocation.removeOccupant();
		
	}

}
