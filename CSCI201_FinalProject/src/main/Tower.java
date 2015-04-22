package main;

public class Tower {
	private Space location;
	protected int cost, salvage, damage, direction;
	
	public void shoot(){
		Bullet b = new Bullet(location, direction, damage);
	}
	
}
