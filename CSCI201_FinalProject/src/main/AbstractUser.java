package main;

import java.io.Serializable;

public abstract class AbstractUser implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int userID;
	protected String username;
	protected boolean readyStatus;
	
	public AbstractUser(int userID)
	{
		this.userID = userID;
		readyStatus = false;
	}
	
	public int getUserID()
	{
		return userID;
	}
	
	public void setReadyStatus(boolean ready)
	{
		readyStatus = ready;
	}
	
	public boolean getReadyStatus()
	{
		return readyStatus;
	}
	
	public Player toPlayer()
	{
		Board tempBoard = new Board();
		Player newPlayer = new Player(username, tempBoard.getSpace(0,  0));
		return newPlayer;
	}
	
	abstract public boolean isUser();
	
	abstract public String getUsername();
}
