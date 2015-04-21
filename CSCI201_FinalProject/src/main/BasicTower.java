package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BasicTower extends Tower{

	BufferedImage sprite;
	int x;
	int y;
	
	public BasicTower(int x, int y)
	{
		this.x = x;
		this.y = y;
		try
		{
			sprite = ImageIO.read(new File("basicTower.png"));
		}
		catch(IOException ioe)
		{
			System.out.println("Basic Tower Exception: " + ioe.getMessage());
		}
	}
	public BufferedImage getTowerImages()
	{
		return sprite;
	}
}
