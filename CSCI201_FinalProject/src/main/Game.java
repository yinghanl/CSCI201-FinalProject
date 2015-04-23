package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Game implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private int gameLobbyID;
	private AbstractUser host;
	private int playersJoined = 1;
	private Lock userLock = new ReentrantLock();
	private Condition userCondition = userLock.newCondition();
	
	private ArrayList<AbstractUser> usersPlaying = new ArrayList<AbstractUser>();
	
	public Game(AbstractUser u)
	{
		this.host = u;
		gameLobbyID = -1;
	}
	
	public AbstractUser getGameHost()
	{
		return host;
	}
	
	public int getNumJoined()
	{
		return this.playersJoined;
	}
	
	public void joinGame(AbstractUser au)
	{
		
		userLock.lock();
		try
		{
			usersPlaying.add(au);
			playersJoined++;
		}
		finally
		{
			userLock.unlock();
		}
		
	}
	
	public void leaveGame(AbstractUser au)
	{
		userLock.lock();
		try
		{
			usersPlaying.remove(au);
			playersJoined--;
		}
		finally
		{
			userLock.unlock();
		}
	}
	
	public ArrayList<AbstractUser> getUsersPlaying(){
		return usersPlaying;
	}
	
	public void setID(int id)
	{
		gameLobbyID = id;
	}
	
	public int getID()
	{
		return gameLobbyID;
	}
}
