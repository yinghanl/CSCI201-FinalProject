package main;

public class Creep extends Moveable{
	
	private int health;
	private PathSpace currentPathLocation;
	
	public Creep(Space loc){
		super(loc);
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
				sleep(500);
				if(currentPathLocation.getNext() ==  null){
					//decrease the team's health, have reached the end
				}
				else{
					currentPathLocation = currentPathLocation.getNext();
				}
				
			} catch (InterruptedException ie) {
				System.out.println("Interrupted Exception in creep.run() " + ie.getMessage());
			}
		}
	}

}
