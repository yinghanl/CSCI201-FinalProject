package main;

public class GameStats {

	private Player p;
	private int goldEarned;
	private int creepsKilled;
	private boolean gameResult;
	
	public GameStats(Player p)
	{
		this.p = p;
		goldEarned = 0;
		creepsKilled = 0;
		gameResult = false;
	}
	
	public Player getPlayer()
	{
		return p;
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
