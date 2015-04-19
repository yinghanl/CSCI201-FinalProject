package main;

import java.io.Serializable;

public class Player extends Moveable implements Serializable{
	
	private static final long serialVersionUID = 1;
	//color
	private String name;
	private String playerDirection;
	boolean readyStatus;
	
	public Player(String name, int x, int y){
		super(new BlankSpace(x, y));
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
	
}//end of class
