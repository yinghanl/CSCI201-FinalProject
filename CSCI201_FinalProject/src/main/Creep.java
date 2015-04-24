package main;

public class Creep extends Moveable{
	
	private int health;
	private PathSpace currentPathLocation;
	
	public Creep(PathSpace p){
		super(p); //may cause error
		currentPathLocation = p;
		currentPathLocation.setCreep(this);
		//currentPathLocation.setOccupant(this);
		health = 10;
		
	}
	
	public boolean isDead(){
		return (health == 0);
	}
	
	public void hit(int amt){
		health-=amt;
//		if(isDead()){
//			currentPathLocation.removeCreep();
//		}
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
					currentPathLocation.removeCreep();
					currentPathLocation = currentPathLocation.getNext();
					currentPathLocation.setCreep(this);
				}
				
			} catch (InterruptedException ie) {
				System.out.println("Interrupted Exception in creep.run() " + ie.getMessage());
			}
		}
		previousLocation.removeOccupant();
		currentPathLocation.removeCreep();
		
	}
	
	public Space getPathLocation(){
		return currentPathLocation;
	}
	
	public void hit(Creep c){
		
	}

}
