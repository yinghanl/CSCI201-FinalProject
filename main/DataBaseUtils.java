package main;
//To make this work on your machine:
//Run the CreateDatabaseAndTable script on your machine
//replace the string on line 19 with the string you should connect to
//It should start with "jdbc:mysql://localhost/towerdefense?user=YourUsername"  (most likely root)
//If you have a password, append "&password=YourPassword" to the end of the string
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataBaseUtils {

	private static String getConnectionString()
	{
		return("jdbc:mysql://localhost/towerdefense?user=root&password=Hyvesolutions2014");
	}
	
	public static boolean verifyUser(String username, char [] password)
	//Checks if the username and password is in the database
	//returns true is user exists
	//returns false if not
	{
		String pass = cArrayToString(password);
		boolean userExists = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(getConnectionString());
			Statement st = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM User WHERE username=? AND password=?");
			ps.setString(1, username); // set first variable in prepared statement
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				userExists = true;
			}
			else
			{
				userExists = false;
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		}
		return userExists;
	}
	
	public static int getUserID(String username)
	//returns the userID associated with a username
	//returns -1 if the username is not in the database
	{
		int userID = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(getConnectionString());
			Statement st = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("SELECT userID FROM User WHERE username=?");
			ps.setString(1, username); // set first variable in prepared statement
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				userID = rs.getInt("userID");
			}
			else
			{
				userID = -1;
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		}
		return userID;
	}
	
	public static User createUser(int userID)
	//Creates user from userID
	//returns null if userID is not in the database
	{
		User newUser = null;
		int date;
		String username;
		int creepsKilled;
		int gamesPlayed;
		int gamesWon;
		int gamesLost;
		int [] userData = new int[4];
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(getConnectionString());
			Statement st = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM User WHERE userID=?");
			ps.setInt(1, userID); // set first variable in prepared statement
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				username = rs.getString("username");
				date = rs.getInt("date_created");
				creepsKilled = rs.getInt("creeps_killed");
				gamesPlayed = rs.getInt("games_played");
				gamesWon = rs.getInt("games_won");
				gamesLost = rs.getInt("games_lost");
				
			}
			else
			{
				userID = -1;
				return null;
			}
			userData[0] = creepsKilled;
			userData[1] = gamesPlayed;
			userData[2] = gamesWon;
			userData[3] = gamesLost;
			newUser = new User(userID, username, date, userData);
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		}
		
		return newUser;
	}
	
	
	private static String cArrayToString(char [] cArray)
	{
		String returnValue = new String(cArray);
		return returnValue;
	}
	
}
