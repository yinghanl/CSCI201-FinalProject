package main;

public class Creep extends Moveable{
	
	private int health;
	private PathSpace currentPathLocation;
	
	public Creep(PathSpace p){
		super(p); //may cause error
		currentPathLocation = p;
		currentPathLocation.setOccupant(this);
		health = 10;
		
	}
	
	public boolean isDead(){
		return (health == 0);
	}
	
	public void hit(int amt){
		health-=amt;
	}
	
	public void run(){
		while(health>0){
			
			try {
				sleep(1000);
				previousLocation = currentPathLocation;
				if(currentPathLocation.getNext() ==  null){
					//decrease the team's health, have reached the end
				}
				else{
					//previousLocation = currentPathLocation;
					currentPathLocation.removeOccupant();
					currentPathLocation = currentPathLocation.getNext();
					currentPathLocation.setOccupant(this);
					//System.out.println(currentPathLocation.hashCode());
				}
				
			} catch (InterruptedException ie) {
				System.out.println("Interrupted Exception in creep.run() " + ie.getMessage());
			}
		}
		currentPathLocation.removeOccupant();
		
	}

}
