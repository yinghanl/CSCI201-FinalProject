package main;

/*
 * need to find out how to send objects over server
 * chat doesnt work with server
 * need to try to make a new thread for the server 
*/
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.sun.prism.paint.Color;

public class GameRoomGUI extends JFrame{
	private JPanel windowPanel;
	private JPanel picture_of_mapPanel;
	private JPanel chatboxPanel;
	private JPanel listof_playersPanel;
	private JPanel centerPanel;
	private JPanel centerTopPanel;
	private JTextArea chatbox;
	private JTextField typefield;
	private JButton sendButton;
	private JButton startGameButton;
	private JButton readyButton;
	private boolean players_ready_array[];
	private int players_in_room;
	private JTable playersJT;
	private JLabel playerLabels[][];
	private Player players_array[];
	private Player player;
	private boolean isHost;
	private String IPAddress;
	private Chatserver chatserver;
	private Chatclient chatclient;
	
	public GameRoomGUI(Player player, boolean isHost, String IPAddress){
		super("Game Room");
		setSize(700, 500);
		setLocation(300, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.player = player;
		this.isHost = isHost;
		/*
		 * User who creates the room will have the ready button disabled because they have the start button
		 * Assumes the host is always ready so boolea[0] is set at true
		 * Gameroom will have at least one player (host who created the room)
		 */
		
		//test players
//		for(int i=1; i<4; i++){
//			players_array[i] = new Player("p"+i);
//		}
//		
		players_ready_array = new boolean[]{true, false, false, false};
		players_in_room = 1;
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2,1));
		centerTopPanel = new JPanel();
		centerTopPanel.setLayout(new GridLayout(1,2));
		
		createPicturePanel();
		createStatusPanel();
		centerPanel.add(centerTopPanel);
		createChatbox();
		createJTextField();
		createButton();	
		
		add(centerPanel, BorderLayout.CENTER);
		getRootPane().setDefaultButton(sendButton);
		
		setVisible(true);
		if(isHost){
			SetupHost();
			SetupChatClient();
		}
		SetupChatClient();
		
	}//end of constructor
	
	
	//setup chat server here
	public String GetIPAddress(){
		return IPAddress;
	}
	
	public void PlayerConnected(){
		
		
	}//end of player connected to host game room
	
	public void SetupHost(){
		players_array = new Player[4];
		players_array[0] = player;
		IPAddress = "localhost";
		chatserver = new Chatserver();
	}//end of setuphost
	
	public void SetupChatClient(){
		chatclient = new Chatclient(IPAddress);
	}//end of setting up the chat client
	
	public void createJTextField(){
		typefield = new JTextField("Press Enter to Send", 30);
	}//end of creating the textfield
	
	public void createButton(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,2));
		startGameButton = new JButton("Start Game");
		readyButton = new JButton("Ready Up");
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String temp = typefield.getText();
				typefield.setText("");
				chatbox.append("\n"+player.getName()+": "+temp);
			}
		});
		buttonPanel.add(typefield);
		buttonPanel.add(sendButton);
		buttonPanel.add(startGameButton);
		buttonPanel.add(readyButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}//end of ceating the button
	
	public void createPicturePanel(){
		picture_of_mapPanel = new JPanel();
		picture_of_mapPanel.setLayout(new BorderLayout());
		//====================DISPLAYING MAP PREVIEW
		ImageIcon io = new ImageIcon("TowerDefense.png");
		picture_of_mapPanel.add(new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g); //call super! super is super important
				g.drawImage(io.getImage(), 0, 0, this.getSize().width, this.getSize().height,null);
				}
		}, BorderLayout.CENTER);
		//0,0 means draw at the top left corner
		//getSize().width getSize().height means to make the image that size of (this), in which (this) is
		//the panel that contains the image. (Effectively making the image the size of the panel)
		//If we resize the window, the panel will resize, and the image will rescale as well
		
		centerTopPanel.add(picture_of_mapPanel);
	}//end of createpicturepanel
	
	public void createChatbox(){
		chatboxPanel = new JPanel();
		chatboxPanel.setLayout(new BoxLayout(chatboxPanel, BoxLayout.X_AXIS));
		chatbox = new JTextArea(3,50);
		chatbox.setEditable(false);
		JScrollPane scroll = new JScrollPane(chatbox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		chatbox.setLineWrap(true);
		chatbox.setWrapStyleWord(true);
		chatbox.append("Chat Screen\n");
		chatboxPanel.add(scroll);
		centerPanel.add(chatboxPanel);
	}//end of creating the chatbox
	
	public void createStatusPanel(){
		JPanel jp = new JPanel();
		playerLabels = new JLabel[4][4];
		jp.setLayout(new GridLayout(5,5));
		JLabel jl;
		Border border = LineBorder.createBlackLineBorder();
		jl = new JLabel("  Player name");
		jl.setBorder(border);
		jp.add(jl);
		jl = new JLabel("  Status");
		jl.setBorder(border);
		jp.add(jl);
		jl = new JLabel("   "+player.getName());
		jl.setBorder(border);
		jp.add(jl);
		jl = new JLabel("   Ready");
		jl.setBorder(border);
		jp.add(jl);
		for(int i=1; i<4; i++){
			//jl = new JLabel("   "+players_array[i].getName());
			jl = new JLabel("   player"+i);
			jl.setBorder(border);
			jp.add(jl);
			jl = new JLabel("   Not Ready");
			jl.setBorder(border);
			jp.add(jl);
		}//end of for
		centerTopPanel.add(jp);
	}//end of creating the panel that holds a table of player
	
	
	public class Chatserver {
		private ServerSocket ss;
		private Socket s;
		private BufferedReader br;
		private PrintWriter pw;
		public Chatserver(){
			try{
				ss = new ServerSocket(6789);
				chatbox.append("\nWaiting for connection...");
				s = ss.accept();   //blocking line 
				players_in_room++;
				chatbox.append("\nConnection established!");
				br = new BufferedReader( new InputStreamReader(s.getInputStream()));
				pw = new PrintWriter(s.getOutputStream());
				
				br.readLine();
				
				pw.println("thanks for connecting");
				pw.flush();
				String line = br.readLine();
				while(line != null){
					chatbox.append("\nclient: " + line);
					br.readLine();
				}//end of while still taking lines from client	
				pw.close();
				br.close();
				s.close();
				ss.close();
			}catch(IOException ioe){
				System.out.println("IOE in chatserver constructor: " + ioe.getMessage());
			}
			//end of try
		}//end of constructor
		
	}//end of chat server class
	
	public class Chatclient extends Thread{
		private PrintWriter pw;
		private Socket s;
		private BufferedReader br;
		private String IPAddress;
		
		public Chatclient(String IPAddress){
			this.IPAddress = IPAddress;
			System.out.println("inside chatclient constructor");
			try{
				s = new Socket("localhost", 6789);
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				pw = new PrintWriter(s.getOutputStream());
				this.start();
				//Scanner keyboard = new Scanner(System.in);
				
				String  line = br.readLine();
				while(line != null){
					chatbox.append("From host: " + line);
					line = br.readLine();
				}//end of while	
				String str = typefield.getText();
				str = player.getName() + ": " + str;
				pw.println(str);
				pw.flush();
				
			}catch(IOException ioe){
				System.out.println("IOE in chatclient constructor: " + ioe.getMessage());
			} finally{
				try{
					pw.close();
					br.close();
					s.close();
				}catch(IOException ioe){
					System.out.println("IOE in chatclient finally block: " + ioe.getMessage());
				}
			}//end of finally block
		
		}//end of constructor
		
		public void run(){
			Scanner keyboard = new Scanner(System.in);
			String line = typefield.getText();
			while(line != null){
				//line = keyboard.nextLine();
				line = player.getName() + ": " + line;
				pw.println(line);
				pw.flush();
				line = typefield.getText();
			}//end of line
		}//end of run()
	}//end of class
}//end of class

