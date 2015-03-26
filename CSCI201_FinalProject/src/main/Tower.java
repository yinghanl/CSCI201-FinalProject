package main;

public class Tower {
	private Coordinate location;
	private int cost, salvage, damage, direction;
	
	public void shoot(){
		Bullet b = new Bullet(location, direction);
	}
}
