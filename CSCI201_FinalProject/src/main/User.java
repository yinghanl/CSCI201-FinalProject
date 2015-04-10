package main;

public class User extends AbstractUser
{

	private int creepsKilled;
	private int goldEarned;
	private int gamesPlayed;
	private int gamesWon;
	private int gamesLost;
	
	public User(int userID, String username, int [] userData)
	{
		super(userID);
		this.username = username;
		this.creepsKilled = userData[0];
		this.goldEarned = userData[1];
		this.gamesPlayed = userData[2];
		this.gamesWon = userData[3];
		this.gamesLost = userData[4];
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public int [] getUserData()
	{
		int [] userData = {creepsKilled, goldEarned, gamesPlayed, gamesWon, gamesLost};
		return userData;
	}
	
	public void addGameStats(int creepsKilled, int goldEarned, boolean wonGame)
	{
		this.creepsKilled += creepsKilled;
		this.goldEarned += goldEarned;
		gamesPlayed += 1;
		if(wonGame)
			gamesWon++;
		else
			gamesLost++;
	}
}
