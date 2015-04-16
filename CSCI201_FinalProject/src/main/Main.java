package main;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test
		//Ted's test
		//System.out.println("change");
		//System.out.println("unchange");
		Player p1 = new Player("host");
//		GameLobbyGUI glg = new GameLobbyGUI(p1);
//		glg.show(true);
		Player p2 = new Player("player");
		GameRoomGUI grg1 = new GameRoomGUI(p1, true, "localhost", 6789);
		GameRoomGUI grg2 = new GameRoomGUI(p2, false, grg1.getIPAddress(), grg1.getPort());
		grg2.connectToRoom(p2);
		//new PostGameGUI();
		//new GameScreenGUI();
		
	}

}
