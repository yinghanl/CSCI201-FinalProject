package main;

public class Creep extends Moveable{
	
	private int health;
	
	public Creep(Space loc){
		super(loc);
	}
	
	public boolean isDead(){
		return (health == 0);
	}
	
	public void hit(int amt){
		health-=amt;
	}

}
