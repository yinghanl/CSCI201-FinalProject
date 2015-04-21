package main;

import java.io.Serializable;

public class Command implements Serializable {

	private static final long serialVersionUID = 1L;
	Player player;
	String command;
	
	public Command(Player p, String command)
	{
		player = p;
		this.command = command;
	}
	public Player getPlayer()
	{
		return player;
	}
	public String getCommand()
	{
		return command;
	}

}
