package main;

public class GameStats {

	private Player p;
	private int goldEarned;
	private int creepsKilled;
	
	public GameStats(Player p)
	{
		this.p = p;
		goldEarned = 0;
		creepsKilled = 0;
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
	
	public void updateGold(int gold)
	{
		goldEarned += gold;
	}
	
	public void updateCreepsKilled(int creeps)
	{
		creepsKilled += creeps;
	}
	
	
}
