package main;

public class Main {
	static boolean connect = false;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test
		//Ted's test
		//System.out.println("change");
		//System.out.println("unchange");
		Player p1 = new Player("host", 7, 2);
		Player p2 = new Player("player 2", 0, 0);

//		Player p2 = new Player("player 2");
//		GameRoomGUI grg1 = new GameRoomGUI(p1, true, "localhost", 6789, "Test Game Room");
//		GameRoomGUI grg2 = new GameRoomGUI(p2, false, grg1.getIPAddress(), grg1.getPort(), grg1.getTitle());
//		grg2.connectToRoom(p2);
		
		Board b = new Board();
//		b.setPlayer(p1);
//		new GameScreenGUI(b, p1, true);
		b = new Board();
		b.setPlayer(p2);
		new GameScreenGUI(b, p2, false);
		Player p1 = new Player("host", b.getSpace(15, 3));
		b.setPlayer(p1);
		new GameScreenGUI(b, p1, true);
		
>>>>>>> df2140963c72491d6b9b63fc8db8f7c37eaa8b15
	}
}



