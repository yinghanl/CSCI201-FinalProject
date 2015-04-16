package main;

import java.io.Serializable;

public class Player extends Moveable implements Serializable{
	
	private static final long serialVersionUID = 1;
	//color
	private String name;
	boolean readyStatus;
	
	public Player(String name){
		super(new BlankSpace(0, 0));
		this.name = name;
		readyStatus = false;
	}//end of constructor
	
	public String getPlayerName(){
		return name;
	}//end of getting the name of player
	
	public boolean getReadyStatus(){
		return readyStatus;
	}//end of returning status
}//end of class
