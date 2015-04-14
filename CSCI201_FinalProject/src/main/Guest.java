package main;

public class Guest extends AbstractUser
{

	public Guest(int guestID)
	{
		super(guestID);
		username = "Guest-" + guestID;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public boolean isUser()
	{
		return false;
	}
	
}
