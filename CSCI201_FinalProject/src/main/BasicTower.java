package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	public BufferedImage[] getTowerImages()
	{
		BufferedImage[] toReturn = new BufferedImage[4];
		
		int rows = 2;
		int cols = 2;
		int chunks = rows*cols;
		
		int chunkWidth = sprite.getWidth() / cols;
	    int chunkHeight = sprite.getHeight() / rows;  
		
	    int count = 0;
	    
	    for (int x = 0; x < rows; x++) {  
            for (int y = 0; y < cols; y++) {  
                //Initialize the image array with image chunks  
                toReturn[count] = new BufferedImage(chunkWidth, chunkHeight, sprite.getType());  
  
                // draws the image chunk  
                Graphics2D gr = toReturn[count++].createGraphics();  
                gr.drawImage(sprite, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
                gr.dispose();  
            }  
        }  
	    
		return toReturn;
	}
}
