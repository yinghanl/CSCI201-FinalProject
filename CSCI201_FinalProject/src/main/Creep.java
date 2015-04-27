package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Creep extends Moveable{
	
	private int health, speed;
	private PathSpace currentPathLocation;
	private boolean offGrid;
	
	public Creep(PathSpace p, int h, int s){
		super(p); //may cause error
		currentPathLocation = p;
		currentPathLocation.setCreep(this);
		//currentPathLocation.setOccupant(this);
		health = h;
		offGrid = false;
		speed = s;
		
	}
	
	public boolean isDead(){
		return (health == 0);
	}
	
	public boolean isOffGrid(){
		return offGrid;
	}
	
	public void hit(int amt){
		health-=amt;
	}
	
	public void run(){
		while(health>0){
			
			try {
				sleep(speed);
				previousLocation = currentPathLocation;
				if(currentPathLocation.getNext() ==  null){
					//decrease the team's health, have reached the end
					offGrid = true;
					break;
				}
				else{
					currentPathLocation.removeCreep();
					currentPathLocation = currentPathLocation.getNext();
					currentPathLocation.setCreep(this);
				}
				
			} catch (InterruptedException ie) {
				System.out.println("Interrupted Exception in creep.run() " + ie.getMessage());
			}
		}
		previousLocation.removeOccupant();
		currentPathLocation.removeCreep();
		
	}
	
	public Space getPathLocation(){
		return currentPathLocation;
	}
	
	public void hit(Creep c){	
	}
	
	public int getHealth(){
		return health;
	}

}
