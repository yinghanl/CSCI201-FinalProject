package main;

public class Main {
	static boolean connect = false;
	public static void main(String[] args) {
		
//		Player p2 = new Player("player 2");
//		GameRoomGUI grg1 = new GameRoomGUI(p1, true, "localhost", 6789, "Test Game Room");
//		GameRoomGUI grg2 = new GameRoomGUI(p2, false, grg1.getIPAddress(), grg1.getPort(), grg1.getTitle());
//		grg2.connectToRoom(p2);
		
		Board b = new Board();
		Player p1 = new Player("host", b.getSpace(15, 3));
		b.setPlayer(p1);
		new GameScreenGUI(b, p1, true);
		
	}
}



