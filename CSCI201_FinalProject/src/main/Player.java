package main;

import java.io.Serializable;

public class Player extends Moveable implements Serializable{
	
	private static final long serialVersionUID = 1;
	//color
	private String name;
	
	public Player(String name){
		super(new BlankSpace(0, 0));
		System.out.println("Player name given: "+ name);
		this.name = name;
	}//end of constructor
	
	public Player(){
		super(new BlankSpace(0, 0));
		name = "DefaultPlayer";
	}//end of default constructor
	
	public String getPlayerName(){
		System.out.println("getplayername(): "+name);
		return name;
	}//end of getting the name of player
}//end of class
