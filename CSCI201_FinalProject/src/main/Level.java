package main;

import java.io.Serializable;

public class Level implements Serializable{
	private int creepHealth, creepSpeed, creepFrequency, numberCreeps;
	public Level(int h, int s, int f, int c){
		creepHealth = h;
		creepSpeed = s;
		creepFrequency = f;
		numberCreeps = c;
	}
	
	public int getHealth(){
		return creepHealth;
	}
	
	public int getSpeed(){
		return creepSpeed;
	}
	
	public int getFrequency(){
		return creepFrequency;
	}
	
	public int getNumber(){
		return numberCreeps;
	}
}
