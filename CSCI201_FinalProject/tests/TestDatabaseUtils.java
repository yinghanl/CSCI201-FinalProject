package main;

public class TestDatabaseUtils {

	
	public static void testVerifyUser()
	{
		String pass = "CSCI201";
		char [] password = pass.toCharArray();
		if(DataBaseUtils.verifyUser("Ted Monyak", password))
		{
			
			System.out.println("database found Ted Monyak, password = " + password);
			System.out.println("Test passed");
		}
		else
		{
			System.out.println("Test failed");
		}
		
		pass = "incorrect password";
		password = pass.toCharArray();
		if(DataBaseUtils.verifyUser("Ted Monyak", password))
		{
			
			System.out.println("database found Ted Monyak, password = " + password);
			System.out.println("Test Failed");
		}
		else
		{
			System.out.println("Test passed");
		}
		
		
	}
	
	public static void testGetUserID()
	{
		int userID = DataBaseUtils.getUserID("Ted Monyak");
		System.out.println("userID = " + userID);
		if(userID == 4)
		{
			System.out.println("Test Passed");
		}
		else
		{
			System.out.println("Test Failed");
		}
		
		userID = DataBaseUtils.getUserID("should not work");
		System.out.println("userID = " + userID);
		if(userID == -1)
		{
			System.out.println("Test Passed");
		}
		else
		{
			System.out.println("Test failed");
		}
		
	}
	
	public static void testCreateNewUser()
	{
		
	}
	
	public static void main(String [] args)
	{
		//testVerifyUser();
		testGetUserID();
	}
}
				