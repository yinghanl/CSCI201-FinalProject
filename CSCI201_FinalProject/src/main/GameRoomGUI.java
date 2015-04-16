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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private Player playersConnected[];
	private Player player;
	private boolean isHost;
	private boolean msgSent;
	private String IPAddress;
	private String playerIPAddresses[];
	private int port;
	private Chatserver chatserver;
	private Chatclient chatclient;
	
	public GameRoomGUI(Player player, boolean isHost, String IPAddress, int port){
		super("Game Room");
		setSize(700, 500);
		setLocation(300, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.player = player;
		this.isHost = isHost;
		this.port = port;
	
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
		msgSent = false;
		players_ready_array = new boolean[]{true, false, false, false};
		players_in_room = 1;
		playerIPAddresses = new String[4];
		
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
			setupHost();
			//connectToRoom(player);
		}
		else{
			connectToRoom(player);
		}
		
	}//end of constructor
	
	//setup chat server here
	
	public int getPort(){
		System.out.println("returning port: "+port);
		return port;
	}
	public String getIPAddress(){
		System.out.println("Returning ip address: "+IPAddress);
		return IPAddress;
	}
	
	public void playerConnected(Player p){
		players_in_room++;
		playersConnected[players_in_room] = p;
		chatbox.append("\n" + p.getPlayerName()+" connected!");
	}//end of player connected to host game room
	
	public void setupHost(){
		playersConnected = new Player[4];
		playersConnected[0] = player;
		IPAddress = "localhost";
		System.out.println("chatserver before init");				
		//chatserver = new Chatserver(port);
		new Chatserver(port).start();
		System.out.println("chatserver after init");
	}//end of setuphost
	
	public void connectToRoom(Player player){
		System.out.println("chatclient before init");		
		chatclient = new Chatclient(IPAddress, player, port);
		System.out.println("chatclient made");
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
				chatbox.append("\n"+player.getPlayerName()+": "+temp);
				msgSent = true;
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
		jl = new JLabel("   "+player.getPlayerName());
		jl.setBorder(border);
		jp.add(jl);
		jl = new JLabel("   Ready");
		jl.setBorder(border);
		jp.add(jl);
		for(int i=1; i<4; i++){
			//jl = new JLabel("   "+players_array[i].getPlayerName());
			jl = new JLabel("   player"+i);
			jl.setBorder(border);
			jp.add(jl);
			jl = new JLabel("   Not Ready");
			jl.setBorder(border);
			jp.add(jl);
		}//end of for
		centerTopPanel.add(jp);
	}//end of creating the panel that holds a table of player
	
	
	public class Chatserver extends Thread{
		private ServerSocket ss;
		private Socket s;
		private BufferedReader br;
		private ObjectInputStream ois;
		private int port;
		private Player player;
		public Chatserver(int port){
			this.port = port;
			
			//end of try
		}//end of constructor
		public void run(){

			try{
				ss = new ServerSocket(port);
				chatbox.append("\nWaiting for players to connect...");
				s = ss.accept();   //blocking line waits till accepted to proceed to next lines of code
				players_in_room++;
				chatbox.append("\nConnection established!");
				br = new BufferedReader( new InputStreamReader(s.getInputStream()));
				//pw = new PrintWriter(s.getOutputStream());
				ois = new ObjectInputStream(s.getInputStream());
				
				Object obj = ois.readObject();
				while(player != null){
					if(obj instanceof Player){
						playerConnected((Player)obj);
						player = (Player)obj;
						chatbox.append("\n"+player.getPlayerName() + " connected!");
					}//end of if player object
					else if(obj instanceof String){
						chatbox.append("\n" + ((String)obj));
					}//end of else if obj is a string
					obj = ois.readObject();
				}//end of while
				
				br.close();
				s.close();
				ss.close();
			}catch(IOException ioe){
				System.out.println("IOE in chatserver constructor: " + ioe.getMessage());
			} catch(ClassNotFoundException cnfe){
				System.out.println("CNFE in chatserver constructor: " + cnfe.getMessage());
			}
			
		}
	}//end of chat server class
	
	public class Chatclient extends Thread{
//		private PrintWriter pw;
		private Socket s;
		private BufferedReader br;
		private ObjectOutputStream oos;
		private String IPAddress;
		private int port;
		private Player player;
		
		public Chatclient(String IPAddress, Player p, int port){
			this.IPAddress = IPAddress;
			this.player = p;
			this.port = port;
			try{
				s = new Socket(IPAddress, port);
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				oos = new ObjectOutputStream(s.getOutputStream());
				this.start();
				
				oos.writeObject(player);
				oos.flush();
				
				String  line = br.readLine();
				while(line != null){
					chatbox.append("From host: " + line);
					line = br.readLine();
				}//end of while	
				
			}catch(IOException ioe){
				System.out.println("IOE in chatclient constructor: " + ioe.getMessage());
			} finally{
//				try{
//					br.close();
//					s.close();
//				}catch(IOException ioe){
//					System.out.println("IOE in chatclient finally block: " + ioe.getMessage());
//				}
			}//end of finally block
		
		}//end of constructor
		
		public void run(){
			String line = typefield.getText();
			while(line != null){
				if(msgSent){
					line = player.getPlayerName() + ": " + line;
					try {
						oos.writeObject(line);
					} catch (IOException e) {
						System.out.println("IOE in GameRoom.Chatclient.run() in while loop writing string object");
						System.exit(0);
					}//end of try-catch
					msgSent = false;
				}//end of if acceptable text	
				line = typefield.getText();
			}//end of while loop
		}//end of run()
	}//end of class
}//end of class

