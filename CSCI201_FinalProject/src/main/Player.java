package main;

public class Player extends Moveable{
	//color
	String name;
	
	public Player(String name){
		super(new Coordinate(0, 0));
		this.name = name;
	}
}
