package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class GameRoomClient extends Thread
{

	private TabPanel tp;
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public GameRoomClient(TabPanel tp)
	{
		this.tp = tp;
		try 
		{
			s = new Socket("localhost", 8000); //should take in IP address of host
			System.out.println("Client Connecting");
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			System.out.println("opened streams");
		}
		
		catch (IOException ioe)
		{
			System.out.println("IOE in GameRoomClient: " + ioe.getMessage());
			ioe.printStackTrace();
		}
		finally {
			try{
				System.out.println("Closing everything");
				if(oos != null)
				{
					oos.close();
				}
				if(ois != null)
				{
					ois.close();
				}
				if(s != null)
				{
					s.close();
				}
				
			} catch (IOException ioe) { ioe.printStackTrace(); }
		}
	
	}
	
	public void run()
	{
		try
		{
			System.out.println("Thread started");
			while(true)
			{
				Vector<Game> gamesOpen = (Vector<Game>)ois.readObject();
				tp.updateGames(gamesOpen);
			}
		}
		catch(ClassNotFoundException cnfe)
		{
			System.out.println("CNFE: " + cnfe.getMessage());
		}
		catch(IOException ioe)
		{
			System.out.println("IOE: " + ioe.getMessage());
		}
	}
}
