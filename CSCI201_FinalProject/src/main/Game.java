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
