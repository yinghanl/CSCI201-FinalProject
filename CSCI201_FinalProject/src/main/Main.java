package main;

public class Main {
	static boolean connect = false;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test
		//Ted's test
		//System.out.println("change");
		//System.out.println("unchange");

		Board b = new Board();
		User u1 = new User(1);
		User u2 = new User(2);
		User u3 = new User(3);
//		GameRoomGUI grg1 = new GameRoomGUI(u1, true, "localhost", 6789, "Test Game Room");
//		GameRoomGUI grg2 = new GameRoomGUI(u2, false, "localhost", 6789, "Test Game Room");
		GameRoomGUI grg3 = new GameRoomGUI(u3, false, "localhost", 6789, "Test Game Room");
//		grg2.connectToRoom(u2);
//		
		
		
//		Player p1 = new Player("host", b.getSpace(0, 0));
//		b = new Board();
//		b.setPlayer(p1);
//		new GameScreenGUI(b, p1, true);
<<<<<<< HEAD
//		Player p1 = new Player("player 1", b.getSpace(10, 10));
		//Player p1 = new Player("Player 2", b.getSpace(5, 5));
//		b.setPlayer(p1);
=======
		//Player p1 = new Player("player 1", b.getSpace(10, 10));
		Player p1 = new Player("Player 2", b.getSpace(3, 3));
		b.setPlayer(p1);
>>>>>>> 3c8677545cb391b509049a74f6bb9fd0bdfe6638
		//new GameScreenGUI(b, p1, true).run();
//		new GameScreenGUI(b, p1, false).run();
		
		
	}
}



