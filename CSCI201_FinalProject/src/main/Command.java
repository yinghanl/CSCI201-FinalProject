package main;

import java.io.Serializable;

public class Command implements Serializable {

	private static final long serialVersionUID = 1L;
	Player player;
	String command;
	int x;
	int y;
	
	public Command(Player p, String command)
	{
		player = p;
		this.command = command;
		x = 0;
		y = 0;
	}
	public Command(Player p, String command, int x, int y)
	{
		this.player = p;
		this.command = command;
		this.x = x;
		this.y = y;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	public String getCommand()
	{
		return command;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
}
