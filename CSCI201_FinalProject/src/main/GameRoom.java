package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class GameRoom extends JFrame{
	private JPanel windowPanel;
	private JPanel picture_of_mapPanel;
	private JPanel chatboxPanel;
	private JPanel listof_playersPanel;
	private JPanel centerPanel;
	private JPanel centerTopPanel;
	private JTextArea chatbox;
	private JButton startGameButton;
	private JButton readyButton;
	private boolean players_ready_array[];
	private int players_in_room;
	private Player players_array[];
	private JTable playersJT;
	private Object data[][];
	private Player host;
	
	public GameRoom(Player host){
		super("Game Room");
		setSize(700, 500);
		setLocation(300, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.host = host;
		players_array = new Player[]{host, new Player("p2"), new Player("p3"), new Player("p4")};
		/*
		 * User who creates the room will have the ready button disabled because they have the start button
		 * Assumes the host is always ready so boolea[0] is set at true
		 * Gameroom will have at least one player (host who created the room)
		 */
		players_ready_array = new boolean[]{true, false, false, false};
		players_in_room = 1;
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2,1));
		centerTopPanel = new JPanel();
		centerTopPanel.setLayout(new GridLayout(1,2));
		centerPanel.add(centerTopPanel);
		
		
		createPicturePanel();
		createJTablePanel();
		createChatbox();
		createButton();	
		
		add(centerPanel, BorderLayout.CENTER);
		setVisible(true);
	}//end of constructor
	
	public void createButton(){
		JPanel buttonPanel = new JPanel();
		startGameButton = new JButton("Start Game");
		readyButton = new JButton("Ready Up");
		buttonPanel.add(startGameButton);
		buttonPanel.add(readyButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}//end of ceating the button
	
	public void createPicturePanel(){
		picture_of_mapPanel = new JPanel();
		picture_of_mapPanel.setLayout(new BorderLayout());
		//============= swap out WR.jpg with map.jpg
		JLabel jl = new JLabel(new ImageIcon("WR.jpg"));
		picture_of_mapPanel.add(jl, BorderLayout.CENTER);
		centerTopPanel.add(picture_of_mapPanel);
	}//end of createpicturepanel
	
	public void createChatbox(){
		chatboxPanel = new JPanel();
		chatboxPanel.setLayout(new BoxLayout(chatboxPanel, BoxLayout.X_AXIS));

		chatbox = new JTextArea(3,50);
		JScrollPane scroll = new JScrollPane(chatbox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		chatbox.setLineWrap(true);
		chatbox.setWrapStyleWord(true);
		chatboxPanel.add(scroll);
		centerPanel.add(chatboxPanel);
	}//end of creating the chatbox
	
	public void createJTablePanel(){
		JPanel jp = new JPanel();
		String columnNames[] = {"Players", "Status"};
		data = new Object[][]{
				{host.getName(), "Ready"}, 
				{players_array[1].getName(), "Not Ready"}, 
				{players_array[2].getName(), "Not Ready"},
				{players_array[3].getName(), "Not Ready"}
			};
		playersJT = new JTable(data, columnNames);
		JScrollPane jsp = new JScrollPane(playersJT);
		jp.add(jsp);
		centerTopPanel.add(jp);
	}//end of creating the panel that holds a table of players
	
//	public void setVisible(boolean b){
//		setVisible(b);
//	}//end of setVisible
	
}//end of class
