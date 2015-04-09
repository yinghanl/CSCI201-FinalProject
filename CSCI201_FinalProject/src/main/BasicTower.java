package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class BasicTower extends Tower{

	BufferedImage sprite;
	
	public BasicTower()
	{
		try
		{
			sprite = ImageIO.read(new File("basicTower.png"));
		}
		catch(IOException ioe)
		{
			System.out.println("Basic Tower Exception: " + ioe.getMessage());
		}
	}
	public JLabel getTowerLabel()
	{
		JLabel toReturn = new JLabel();
		
		
		
		return toReturn;
	}
}
