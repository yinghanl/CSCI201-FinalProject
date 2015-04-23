package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class GameRoomClient extends Thread
{

	private TabPanel tp;
	private AbstractUser au;
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public GameRoomClient(TabPanel tp, AbstractUser au)
	{
		this.tp = tp;
		this.au = au;
		try 
		{
			s = new Socket("localhost", 8000); //should take in IP address of host
			//System.out.println("Client Connecting");
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			//System.out.println("opened streams");
		}
		
		catch (IOException ioe)
		{
			System.out.println("IOE in GameRoomClient: " + ioe.getMessage());
			ioe.printStackTrace();
		}
	
	}
	
	public void newGame(Game g)
	{
		try
		{
			oos.writeObject(g);
			oos.flush();
		}
		catch(IOException ioe)
		{
			System.out.println("IOE in GameRoomClient NewGame: " + ioe.getMessage());
			ioe.getStackTrace();
		}
		
	}
	
	public void deleteGame(Game g)
	{
		
	}
	
	public void joinHostGame(String username)
	{
		try
		{
			oos.writeObject(username);
			oos.flush();
			
		}
		catch(IOException ioe)
		{
			System.out.println("IOE in GameRoomClient: " + ioe.getMessage());
		}
		
	}
	
	public void run()
	{
		try
		{
			//System.out.println("Thread started");
			while(true)
			{
				Object readObj = ois.readObject();
				if(readObj instanceof Vector<?>)
				{
					Vector<Game> gamesOpen = (Vector<Game>)readObj;
					//System.out.println("gamesOpen.size = " + gamesOpen.size());
					tp.updateGames(gamesOpen);
				}
				else if(readObj instanceof Game)
				{
					Game hostGame = (Game)readObj;
					hostGame.joinGame(au);
					newGame(hostGame);
					
				}
				
			}
		}
		catch(ClassNotFoundException cnfe)
		{
			System.out.println("CNFE in GRC run: " + cnfe.getMessage());
		}
		catch(IOException ioe)
		{
			System.out.println("IOE in GRC run: " + ioe.getMessage());
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
}
