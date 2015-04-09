package main;

public class User {

	private boolean guest;
	private int date;
	private int userID;
	private String username;
	private int creepsKilled;
	private int goldEarned;
	private int gamesPlayed;
	private int gamesWon;
	private int gamesLost;
	
	public User(int userID, String username, int date, int [] userData)
	{
		this.guest = false;
		this.username = username;
		this.date = date;
		this.userID = userID;
		this.creepsKilled = userData[0];
		this.goldEarned = userData[1];
		this.gamesPlayed = userData[2];
		this.gamesWon = userData[3];
		this.gamesLost = userData[4];
	}
	
	public User(int guestID)
	{
		guest = true;
		userID = guestID;
		username = "Guest-" + guestID;
		date = -1;
		creepsKilled = -1;
		goldEarned = -1;
		gamesPlayed = -1;
		gamesWon = -1;
		gamesLost = -1;
	}
	public boolean getGuest()
	{
		return guest;
	}
	public int getUserID()
	{
		return userID;
	}
	
	public int getDate()
	{
		return date;
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
