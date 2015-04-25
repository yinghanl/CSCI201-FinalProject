package main;

public class Main {
	static boolean connect = false;
	public static void main(String[] args) {
		
<<<<<<< HEAD
		Board b = new Board();
	//	User u1 = new User(1);
	//	User u2 = new User(2);
	//	User u3 = new User(3);
	//	User u4 = new User(4);
//		GameRoomGUI grg1 = new GameRoomGUI(u1, true, "localhost", 6789, "Test Game Room");
//		GameRoomGUI grg2 = new GameRoomGUI(u2, false, "localhost", 6789, "Test Game Room");
//		GameRoomGUI grg3 = new GameRoomGUI(u3, false, "localhost", 6789, "Test Game Room");
	//	GameRoomGUI grg4 = new GameRoomGUI(u4, false, "localhost", 6789, "Test Game Room");
		
//		Player p1 = new Player("player 1", b.getSpace(10, 10));
		Player p1 = new Player("Player 2", b.getSpace(15, 25));
		b.setPlayer(p1);

<<<<<<< HEAD
		new GameScreenGUI(b, p1, true).run();
		//new GameScreenGUI(b, p1, false).run();
=======
		//new GameScreenGUI(b, p1, true).run();
		new GameScreenGUI(b, p1, false).run();
=======
		new LogInGUI();
		
>>>>>>> jl
>>>>>>> c10e568b63cc3995fd80220ca8e058484d589390
	}
}



