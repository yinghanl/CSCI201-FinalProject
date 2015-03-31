package main;

public class Player extends Moveable{
	//color
	private String name;
	
	public Player(String name){
		super(new Coordinate(0, 0));
		this.name = name;
	}//end of constructor
	
	public Player(){
		super(new Coordinate(0, 0));
		name = "Player";
	}//end of default constructor
	
	public String getName(){
		return name;
	}//end of getting the name of player
}//end of class