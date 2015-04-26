package main;

import java.io.Serializable;

public class GameStats implements Serializable
{

	private static final long serialVersionUID = 1L;
	private AbstractUser au;
	private int goldEarned;
	private int creepsKilled;
	private boolean gameResult;
	
	public GameStats(AbstractUser au)
	{
		this.au = au;
		goldEarned = 0;
		creepsKilled = 0;
		gameResult = false;
	}
	
	public AbstractUser getAbstractUser()
	{
		return au;
	}
	
	public int getCreepsKilled()
	{
		return creepsKilled;
	}
	
	public int getGold()
	{
		return goldEarned;
	}
	
	public boolean getGameResult()
	{
		return gameResult;
	}
	
	public void updateGold(int gold)
	{
		goldEarned += gold;
	}
	
	public void updateCreepsKilled(int creeps)
	{
		creepsKilled += creeps;
	}
	
	public void updateGameResult(boolean wonGame)
	{
		gameResult = wonGame;
	}
	
}
