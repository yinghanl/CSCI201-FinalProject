package main;

import java.io.Serializable;

public class Player extends Moveable implements Serializable{
	
	private static final long serialVersionUID = 1;
	//color
	private String name;
	private String playerDirection;
	boolean readyStatus;
	
	public Player(String name, Space s){
		super(s);
		this.name = name;
		readyStatus = false;
		playerDirection = "SOUTH";
	}//end of constructor
	
	public String getPlayerName(){
		return name;
	}//end of getting the name of player
	
	public boolean getReadyStatus(){
		return readyStatus;
	}//end of returning status
	
	public void setReadyStatus(boolean b){
		readyStatus = b;
	}
	public String getPlayerDirection()
	{
		return playerDirection;
	}
	public void setPlayerDirection(String s)
	{
		playerDirection = s;
	}
	public Tower playerOperatingTower()
	{
		System.out.println(this.getLocation().getX() + " " + this.getLocation().getY());
		
		
		if(playerDirection.equals("NORTH"))
		{
			try
			{
				if(this.getLocation().getNorth() instanceof TowerSpace)
				{
					return ((TowerSpace) (this.getLocation().getNorth())).getTower();
				}
			}
			catch (BoundaryException e)
			{
				e.printStackTrace();
			}
		}
		else if(playerDirection.equals("SOUTH"))
		{
			try
			{
				if(this.getLocation().getSouth() instanceof TowerSpace)
				{
					return ((TowerSpace) (this.getLocation().getSouth())).getTower();
				}
			}
			catch (BoundaryException e)
			{
				e.printStackTrace();
			}
		}
		else if(playerDirection.equals("WEST"))
		{
			try
			{
				if(this.getLocation().getWest() instanceof TowerSpace)
				{
					return ((TowerSpace) (this.getLocation().getWest())).getTower();
				}
			}
			catch (BoundaryException e)
			{
				e.printStackTrace();
			}
		}
		else if(playerDirection.equals("EAST"))
		{
			try
			{
				if(this.getLocation().getEast() instanceof TowerSpace)
				{
					return ((TowerSpace) (this.getLocation().getEast())).getTower();
				}
			}
			catch (BoundaryException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
}//end of class
