package main;

import java.io.Serializable;

public class GameRoomPacket implements Serializable
{

	private static final long serialVersionUID = 1L;
	private Game g;
	private int code;
	
	public GameRoomPacket(Game g, int code)
	{
		this.g = g;
		this.code = code;
	}
	
	public Game getGame()
	{
		return g;
	}
	
	public int getCode()
	{
		return code;
	}
}
