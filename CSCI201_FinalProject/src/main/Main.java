package main;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test
		//Ted's test
		//System.out.println("change");
		//System.out.println("unchange");
		
		
		Player p1 = new Player("host");
//		Player p2 = new Player("player 2");
//		Player p3 = new Player("player 3");
//		Player p4 = new Player("player 4");
//		GameRoomGUI grg1 = new GameRoomGUI(p1, true, "localhost", 6789, "Test Game Room");
//		GameRoomGUI grg2 = new GameRoomGUI(p2, false, grg1.getIPAddress(), grg1.getPort(), grg1.getTitle());
//		GameRoomGUI grg3 = new GameRoomGUI(p3, false, grg1.getIPAddress(), grg1.getPort(), grg1.getTitle());
//		GameRoomGUI grg4 = new GameRoomGUI(p4, false, grg1.getIPAddress(), grg1.getPort(), grg1.getTitle());
//		grg2.connectToRoom(p2);
//		grg3.connectToRoom(p3);
//		grg4.connectToRoom(p4);
		
		
		Board b = new Board();
		b.setPlayer(0, 0, "host");
		new GameScreenGUI(b, p1, true);
		
	}
	

}
