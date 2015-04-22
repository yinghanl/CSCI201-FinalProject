package main;

public class Tower {
	private Space location;
	protected int cost, salvage, damage, direction;
	
	public void shoot(){
		new Bullet(location, direction, damage).start();
	}
	public void rotate()
	{
		if(direction == 0)
		{
			direction = 4;
		}
		else if(direction == 1)
		{
			direction = 7;
		}
		else if(direction == 2)
		{
			direction = 6;
		}
		else if(direction == 3)
		{
			direction = 5;
		}
		else if(direction == 4)
		{
			direction = 2;
		}
		else if(direction == 5)
		{
			direction = 0;
		}
		else if(direction == 6)
		{
			direction = 1;
		}
		else if(direction == 7)
		{
			direction = 3;
		}
	}
	
	public Space getLocation(){
		return location;
	}
	
}
