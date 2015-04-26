package main;

public class Tower {
	private Space location;
	protected int cost, salvage, damage, direction;
	protected int x;
	protected int y;
	
	public Tower(Space s, String direction){
		location = s;
		damage = 10;
		
		switch(direction){
			case "NORTH":
				this.direction = 0;
				break;
			case "SOUTH": 
				this.direction = 1;
				break;
			case "WEST":
				this.direction = 3;
				break;
			case "EAST":
				this.direction = 2;
				break;
		}
	}
	
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
	
	public int getX()
	{
		return location.getX();
	}
	public int getY()
	{
		return location.getY();
	}
	public int getDirection()
	{
		return direction;
	}
}
