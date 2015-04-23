package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class GameRoomServer {

	private Vector<Game> gamesOpen;
	private ServerSocket ss;
	private Vector<GameRoomThread> gmtVector;
	private int gameIndex;
	
	public GameRoomServer()
	{
		gamesOpen = new Vector<Game>();
		gmtVector = new Vector<GameRoomThread>();
		UpdateGameRoomThread ugrt = new UpdateGameRoomThread(this);
		ugrt.start();
		try
		{
			ss = new ServerSocket(8000);
			System.out.println("Waiting for connection");
			while(true)
			{
				Socket s = ss.accept();
				//ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				//ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				System.out.println("Accepted user");
				GameRoomThread gmt = new GameRoomThread(this, s);
				System.out.println("Started gmt1");
				gmtVector.add(gmt);
				gmt.start();
				System.out.println("Started gmt2");
			}
		}
		catch(IOException ioe)
		{
			System.out.println("IOE in chatserver constructor: " + ioe.getMessage());
		} 
		finally
		{
			try 
			{
				if(ss != null)
				{
					ss.close();
				}
				
				
			} 
			catch (IOException e) 
			{
				System.out.println("IOE in server.run() in finally block: "+e.getMessage());
			}
		}
	}
	
	public void deleteGame(int gameID)
	{
		for (Game g : gamesOpen) {
			if(g.getID() == gameID)
			{
				gamesOpen.remove(g);
			}
		}
	}
	
	public int getNumGames()
	{
		return gamesOpen.size();
	}
	
	public Vector<Game> getGameVector()
	{
		return gamesOpen;
	}
	
	public void updateUsers()
	{
		for (GameRoomThread gmt : gmtVector) {
			gmt.updateClient();
		}
	}
	
	public void gameUpdate(Game g)
	{
		if(g.getID() == -1)
		{
			g.setID(gameIndex);
			gamesOpen.add(g);
			synchronized(this)
			{
				gameIndex++;
			}
			return;
		}
		for (Game oldG : gamesOpen) {
			if(g.getID() == oldG.getID())
			{
				oldG = g;
			}
		}
	}
	public void removeGameRoomThread(GameRoomThread gmt)
	{
		gmtVector.remove(gmt);
	}
	
	public class UpdateGameRoomThread extends Thread
	{
		private GameRoomServer grs;
		public UpdateGameRoomThread(GameRoomServer grs)
		{
			this.grs = grs;
		}
		public void run()
		{
			System.out.println("UpdateGameRoomThread started");
			try
			{
				while(true)
				{
					Thread.sleep(10000);
					grs.updateUsers();
				}
			}
			catch(InterruptedException ie)
			{
				System.out.println("IE: " + ie.getMessage());
			}
			
		}
	}
	
	public class GameRoomThread extends Thread
	{
		private GameRoomServer grs;
		private Socket s;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		public GameRoomThread(GameRoomServer grs, Socket s)
		{
			System.out.println(s.getPort());
			this.grs = grs;
			try
			{
				this.s = s;	
				oos = new ObjectOutputStream(s.getOutputStream());
				ois = new ObjectInputStream(s.getInputStream());
			

			}
			catch(IOException ioe)
			{
				System.out.println("IOE in GameRoomThread constructor: ");
				ioe.printStackTrace();
			}
			
		}
		
		public void updateClient()
		{
			try
			{
				oos.writeObject(grs.getGameVector());
				oos.flush();
				
			}
			catch(IOException ioe)
			{
				System.out.println("IOE: " + ioe.getMessage());
			}
			
			
		}
		
		public void run() {
			try 
			{
				System.out.println("Started gameroomserver thread");
				Object newObj;
				while (true) 
				{
					if(ois != null)
					{
						newObj = ois.readObject();
						if(newObj instanceof Game)
						{
							Game newGame = (Game)newObj;
							grs.gameUpdate(newGame);
						}
						else
						{
							Integer gameID = (Integer)newObj;
							grs.deleteGame(gameID);
						}
						
					}
					
					
				}
			} 
			catch(ClassNotFoundException cnfe)
			{
				System.out.println("CNFE: " + cnfe.getMessage());
			}
			catch (IOException ioe) 
			{
				grs.removeGameRoomThread(this);
				System.out.println(s.getInetAddress() + ":" + s.getPort() + " disconnected.");
			}
		}
	}
}
