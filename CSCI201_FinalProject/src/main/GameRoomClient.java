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
	private Game g;
	private int nextPort;
	
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
			oos.writeObject(new Integer(-1)); // get portnumber
			oos.flush();
			//System.out.println("opened streams");
		}
		catch (IOException ioe)
		{
			System.out.println("IOE in GameRoomClient: " + ioe.getMessage());
		}
	
	}
	
	public void newGame(Game g)
	{
		setGame(g);
		sendGamePacket(new GameRoomPacket(g, 1));
		
	}
	
	public void updateGame(Game g)
	{
		setGame(g);
		sendGamePacket(new GameRoomPacket(g, 2));
	}
	
	public void leaveGame()
	{
		g.leaveGame(au);
		sendGamePacket(new GameRoomPacket(g, 2));
		g = null;
	}
	
	public void deleteGame()
	{
		
		sendGamePacket(new GameRoomPacket(g, 3));
	}
	
	public void setGame(Game newG)
	{
		g = newG;
	}
	
	public void sendGamePacket(GameRoomPacket grp)
	{
		try
		{
			oos.writeObject(grp);
			oos.flush();
		}
		catch(IOException ioe)
		{
			System.out.println("IOE in GameRoomClient NewGame: " + ioe.getMessage());
		}
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
	
	public int getChatPort()
	{
		return nextPort;
	}
	
	public void run()
	{
		try
		{
			//System.out.println("Thread started");
			while(true)
			{
				Object readObj = ois.readObject();
				if(readObj instanceof String)
				{
					String vectorString = (String)readObj;
					String [] gameString = vectorString.split("\n");
					System.out.println("gameString.size = " + gameString.length);
					tp.updateGames(gameString);
				}
				else if(readObj instanceof GameRoomPacket)
				{
					GameRoomPacket grp = (GameRoomPacket)readObj;
					Game hostGame = grp.getGame();
					int code = grp.getCode();
					if(code == 4)
					{
						hostGame.joinGame(au);
						tp.setChatPort(hostGame.getChatPort());
						updateGame(hostGame);
					}
					else if(code == 5)
					{
						setGame(hostGame);
					}
				}
				else if(readObj instanceof Integer)
				{
					nextPort = (Integer)readObj;
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
