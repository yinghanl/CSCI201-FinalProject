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
		direction = 0;
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
		try
		{	
			if(direction == 0)
			{
				sprite = ImageIO.read(new File("images/BasicTowerN.png"));	
			}
			else if (direction == 1)
			{
				sprite = ImageIO.read(new File("images/BasicTowerS.png"));
			}
			else if (direction == 2)
			{
				sprite = ImageIO.read(new File("images/BasicTowerE.png"));
			}
			else if (direction == 3)
			{
				sprite = ImageIO.read(new File("images/BasicTowerW.png"));
			}
			else if (direction == 4)
			{
				sprite = ImageIO.read(new File("images/BasicTowerNE.png"));
			}
			else if (direction == 5)
			{
				sprite = ImageIO.read(new File("images/BasicTowerNW.png"));
			}
			else if (direction == 6)
			{
				sprite = ImageIO.read(new File("images/BasicTowerSE.png"));
			}
			else if (direction == 7)
			{
				sprite = ImageIO.read(new File("images/BasicTowerSW.png"));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return sprite;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
}
