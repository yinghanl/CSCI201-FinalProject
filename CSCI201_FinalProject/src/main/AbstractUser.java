package main;

public abstract class AbstractUser {

	protected int userID;
	protected String username;
	
	
	public AbstractUser(int userID)
	{
		this.userID = userID;
	}
	
	public int getUserID()
	{
		return userID;
	}
	
	abstract public String getUsername();
}
