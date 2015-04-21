package main;

public class Main {
	static boolean connect = false;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test
		//Ted's test
		//System.out.println("change");
		//System.out.println("unchange");

//		Player p2 = new Player("player 2");
//		GameRoomGUI grg1 = new GameRoomGUI(p1, true, "localhost", 6789, "Test Game Room");
//		GameRoomGUI grg2 = new GameRoomGUI(p2, false, grg1.getIPAddress(), grg1.getPort(), grg1.getTitle());
//		grg2.connectToRoom(p2);
//		
		Board b = new Board();
		
//		Player p1 = new Player("host", b.getSpace(0, 0));
//		b = new Board();
//		b.setPlayer(p1);
//		new GameScreenGUI(b, p1, true);
		b = new Board();
		Player p2 = new Player("player 1", b.getSpace(7, 7));
		b.setPlayer(p2);
		new GameScreenGUI(b, p2, false);
		
	}
}



