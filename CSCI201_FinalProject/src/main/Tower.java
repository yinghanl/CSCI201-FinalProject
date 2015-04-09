package main;

public class Tower {
	private Space location;
	private int cost, salvage, damage, direction;
	
	public void shoot(){
		Bullet b = new Bullet(location, direction, damage);
	}
}
