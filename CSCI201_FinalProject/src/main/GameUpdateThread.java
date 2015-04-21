package main;

public class GameUpdateThread extends Thread{

	GameLobbyGUI glu;
	
	public GameUpdateThread(GameLobbyGUI glu)
	{
		this.glu = glu;
	}
	
	public void run() //run method should update the table of games in each user's GameLobbyGUI by constantly pulling info from database
	{
		
	}
}
