package main;

import java.util.Vector;

public class TestPostGameGUI {

	public static void main(String [] args)
	{
		Vector<GameStats> gsVector = new Vector<GameStats>();
		int [] userData = {0, 0, 0, 0, 0};
		User u1 = new User(1, "u1", userData);
		User u2 = new User(2, "u2", userData);
		GameStats g1 = new GameStats(u1);
		GameStats g2 = new GameStats(u2);
		g1.updateCreepsKilled(1);
		g2.updateGold(20);
		gsVector.add(g1);
		gsVector.add(g2);
		new PostGameGUI(gsVector);
	}
}
