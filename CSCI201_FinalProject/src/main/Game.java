package main;
<<<<<<< HEAD

=======
>>>>>>> 25d59111f3ce38795913a8f4681c978bd3827a5f
import java.util.ArrayList;


public class Game {

	AbstractUser creator;
	int playersJoined = 1;
	ArrayList<String> usersPlaying = new ArrayList<String>();
	
	public Game(AbstractUser u)
	{
		this.creator = u;
	}
	
	public String getGameCreator()
	{
		return creator.getUsername();
	}
	
	public int getNumJoined()
	{
		return this.playersJoined;
	}
	
	public void joinGame(String userName)
	{
		usersPlaying.add(userName);
		playersJoined++;
	}
	
	public ArrayList<String> getUsersPlaying(){
		return usersPlaying;
	}
	
}
