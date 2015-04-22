package main;

public class Guest extends AbstractUser
{

	private static final long serialVersionUID = 1L;

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
