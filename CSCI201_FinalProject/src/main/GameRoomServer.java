package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
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
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			ss = new ServerSocket(8000);
		
			System.out.println("Waiting for connection");
			while(true)
			{
				Socket s = ss.accept();
				
				System.out.println("Accepted user");
				GameRoomThread gmt = new GameRoomThread(this, s);
				gmtVector.add(gmt);
				gmt.start();

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
	
	public void newGame(Game g)
	{
		System.out.println("new game");
		gamesOpen.add(g);
		synchronized(this)
		{
			gameIndex++;
		}
		System.out.println("gamesOpen.size()" + gamesOpen.size());
		return;
	}
	
	public void deleteGame(Game g)
	{
		for (int i = 0; i < gamesOpen.size(); i++) {
			if(g.getID() == gamesOpen.get(i).getID())
			{
				System.out.println("updated game");
				gamesOpen.remove(i);
			}
		}
		
		System.out.println("Deleting game");
		synchronized(this)
		{
			gameIndex--;
		}
		System.out.println("gamesOpen.size()" + gamesOpen.size());
	}
	
	public void gameUpdate(Game g)
	{
		for (int i = 0; i < gamesOpen.size(); i++) {
			if(g.getID() == gamesOpen.get(i).getID())
			{
				System.out.println("updated game");
				gamesOpen.set(i, g);
			}
		}
		System.out.println("gamesOpen.size()" + gamesOpen.size());
		return;
	}
	
	public int getGameIndex()
	{
		return gameIndex;
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
					if(newObj instanceof GameRoomPacket)
					{
						System.out.println("Reading in new GameRoomPacket");
						GameRoomPacket grp = (GameRoomPacket)newObj;
						//System.out.println("Read in a game");
						Game newGame = grp.getGame();
						System.out.println("newGame.id" + newGame.getID());
						int code = grp.getCode();
						System.out.println("code = " + code);
						if(code == 1) //change existing game
						{
							newGame.setID(grs.getGameIndex());
							grs.newGame(newGame);
							oos.writeObject(new GameRoomPacket(newGame, 5));
							oos.flush();
						}
						else if(code == 2) //add new game
						{
							grs.gameUpdate(newGame);
						}
						else if(code == 3) //delete game
						{
							grs.deleteGame(newGame);
						}
						
					}
					else if(newObj instanceof String)
					{
						boolean gameFound = false;
						for (Game g : grs.getGameVector()) {
							if(g.getGameHost().getUsername().equals(newObj))
							{
								oos.writeObject(new GameRoomPacket(g, 4));
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
