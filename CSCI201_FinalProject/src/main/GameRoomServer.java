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
		//System.out.println("Updating users");
		for (GameRoomThread gmt : gmtVector) {
			gmt.updateClient();
		}
	}
	
	
	public void gameUpdate(Game g)
	{
		System.out.println("Checking if new game");
		if(g.getID() == -1)
		{
			System.out.println("new game");
			g.setID(gameIndex);
			gamesOpen.add(g);
			synchronized(this)
			{
				gameIndex++;
			}
			System.out.println("gamesOpen.size()" + gamesOpen.size());
			return;
		}
		for (Game oldG : gamesOpen) {
			if(g.getID() == oldG.getID())
			{
				oldG = g;
			}
		}
		System.out.println("gamesOpen.size()" + gamesOpen.size());
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
				updateClient();
				
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
				//System.out.println("Started gameroomserver thread");
				Object newObj;
				while (true) 
				{
					newObj = ois.readObject();
					//System.out.println(newObj.toString());
					if(newObj instanceof Game)
					{
						//System.out.println("Read in a game");
						Game newGame = (Game)newObj;
						grs.gameUpdate(newGame);
					}
					else if(newObj instanceof String)
					{
						boolean gameFound = false;
						for (Game g : grs.getGameVector()) {
							if(g.getGameHost().getUsername().equals(newObj))
							{
								oos.writeObject(g);
								oos.flush();
								gameFound = true;
							}
						}
						if(gameFound == false)
						{
							oos.writeObject(null);
							oos.flush();
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
