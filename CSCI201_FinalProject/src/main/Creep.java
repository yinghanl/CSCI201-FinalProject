package main;

public class Creep extends Moveable{
	
	private int health;
	
	public Creep(Coordinate loc){
		super(loc);
	}
	
	public boolean isDead(){
		return (health == 0);
	}
	
	public void hit(int amt){
		health-=amt;
	}

}
