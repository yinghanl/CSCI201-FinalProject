package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;

public class GameLobbyGUI extends JFrame{
	private Player player;
	private JLabel playerLabel;
	
	public GameLobbyGUI(Player player){
		super("Game Lobby");
		setLayout(new BorderLayout());		
		setSize(900,600);
		setLocation(100, 50);
		
		this.player = player;
		playerLabel = new JLabel(player.getName());
		playerLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(playerLabel, BorderLayout.NORTH);
		
		setVisible(true);
	}//end of constructor

}//end of gamelobby gui class
