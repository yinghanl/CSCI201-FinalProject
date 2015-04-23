package main;

/*
 * need to find out how to send objects over server
 * chat doesnt work with server
 * need to try to make a new thread for the server 
*/


//import ChatServer;

//import ChatThread;

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
import java.net.UnknownHostException;
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
import javax.swing.text.DefaultCaret;


import com.sun.prism.paint.Color;

public class GameRoomGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	//private JPanel windowPanel;
	private JPanel picture_of_mapPanel;
	private JPanel chatboxPanel;
	//private JPanel listof_usersPanel;
	private JPanel centerPanel;
	private JPanel centerTopPanel;
	private JTextArea chatbox;
	private JTextField typefield;
	private JButton sendButton;
	private JButton startGameButton;
	private JButton readyButton;
	private int users_in_room;
	private JLabel userLabels[][];
	private AbstractUser usersConnected[];
	private AbstractUser user;
	private boolean isHost;
	private boolean msgSent;
	private boolean updated;
	private boolean labelChange;
	private boolean resetLabels;
	private String message;
	private String IPAddress;
	private String roomTitle;
	private boolean loadGameScreen;
	private int port;
	private int userLabelIndex;
	private int usersReady;
	
	private Vector<ChatThread> ctVector = new Vector<ChatThread>();
	private ChatThread ct;
	private ServerSocket ss;
	private Socket s;
	private BufferedReader br;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Object obj;
	
	public GameRoomGUI(AbstractUser u, boolean isHost, String IPAddress, int port, String title){
		this.user = u;
		this.isHost = isHost;
		this.port = port;
		this.roomTitle = title;
		setTitle(title);
		setSize(700, 500);
		setLocation(300, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		message = null;
		msgSent = false;
		updated = false;
		loadGameScreen = false;
		resetLabels = false;
		usersReady = 1;
		users_in_room = 0;
		usersConnected = new AbstractUser[4];
		
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
		new CreateConnections().start();
//		if(isHost){
//			userLabelIndex = 0;
//			setupHost();
//			try {
//				System.out.println("Starting Chat Server");
//				ss = new ServerSocket(6789);
//				while (true) {
//					System.out.println("Waiting for client to connect...");
//					Socket s = ss.accept();
//					System.out.println("Client " + s.getInetAddress() + ":" + s.getPort() + " connected");
//					ChatThread ct = new ChatThread(s, this);
//					ctVector.add(ct);
//					ct.start();
//				}
//			} catch (IOException ioe) {
//				System.out.println("IOE: " + ioe.getMessage());
//			} finally {
//				if (ss != null) {
//					try {
//						ss.close();
//					} catch (IOException ioe) {
//						System.out.println("IOE closing ServerSocket: " + ioe.getMessage());
//					}
//				}
//			}//end of finally
//
//		}//end of if host
//		else{
//			try {
//				s = new Socket("localhost", 6789);
//				oos = new ObjectOutputStream(s.getOutputStream());
//				ois = new ObjectInputStream(s.getInputStream());
//				new ReadObject().start();
//				} catch (IOException ioe) {
//					System.out.println("IOE client: " + ioe.getMessage());
//				}
//			}
	}//end of constructor
	
	
	public int getPort(){
		return port;
	}
	public String getIPAddress(){
		return IPAddress;
	}
	
	public void usersConnected(AbstractUser u){
		usersConnected[users_in_room] = u;
		users_in_room++;
		chatbox.append("\n" + u.getUsername()+" connected!");
	}//end of user connected to host game room
	
	public void setupHost(){
		users_in_room++;
		usersConnected[0] = user;
		IPAddress = "localhost";
	}//end of setuphost
	
	public void connectToRoom(AbstractUser user){
	}//end of setting up the chat client
	
	public String getTitle(){
		return roomTitle;
	}//end of returning title of the room
	
	public int getNumOfusers(){
		return users_in_room;
	}//end of returning amount of users in the room
	
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
				message = "\n"+user.getUsername()+": "+temp+"\n";
				typefield.setText("");
				if(isHost){
					chatbox.append(message);
					sendMessageToClients(message);
				}	
				else{
					try {
						oos.writeObject(message);
						sendMessageToClients(message);
						oos.flush();
					} catch (IOException e) {
						System.out.println("IOE in GameRoom.Chatclient.run() in while loop writing string object");
					}//end of try-catch
				}	
				msgSent = true;
			}
		});
		
		readyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(readyButton.getText() == "Ready Up"){
					//readyButton.setText("Unready");
					userLabels[userLabelIndex][1].setText("   Ready");
					user.setReadyStatus(true);
					labelChange = true;
					try {
						oos.writeObject(userLabels);
						oos.flush();
						oos.writeObject(new Integer(-2));
						oos.flush();
					} catch (IOException e) {
						System.out.println("IOE in GameRoom.Chatclient.run() in while loop writing string object");
					}//end of try-catch

					readyButton.setEnabled(false);
				}
				else{
					readyButton.setText("Ready Up");
					userLabels[userLabelIndex][1].setText("   Not Ready");
					user.setReadyStatus(false);
					try {
						oos.writeObject(userLabels);
						oos.flush();
						oos.writeObject(new Integer(-2));
						oos.flush();
					} catch (IOException e) {
						System.out.println("IOE in GameRoom.Chatclient.run() in while loop writing string object");
					}//end of try-catch

					labelChange = true;
				}
			}
		});
		
		startGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				loadGameScreen = true;
				try {
					oos.writeObject(new Integer(-1));
					oos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Board b = new Board();
				new GameScreenGUI(b, user.toPlayer(), true);
				setVisible(false);
				loadGameScreen = false;
			}
		});
		buttonPanel.add(typefield);
		buttonPanel.add(sendButton);
		buttonPanel.add(startGameButton);
		buttonPanel.add(readyButton);
		add(buttonPanel, BorderLayout.SOUTH);
		startGameButton.setEnabled(false);
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
		DefaultCaret caret = (DefaultCaret)chatbox.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scroll = new JScrollPane(chatbox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		chatbox.setLineWrap(true);
		chatbox.setWrapStyleWord(true);
		chatbox.append("Chat Screen\n");
		chatboxPanel.add(scroll);
		centerPanel.add(chatboxPanel);
	}//end of creating the chatbox
	
	public void createStatusPanel(){
		JPanel jp = new JPanel();
		userLabels = new JLabel[4][4];
		jp.setLayout(new GridLayout(5,5));
		JLabel jl;
		Border border = LineBorder.createBlackLineBorder();
		jl = new JLabel("  user name");
		jl.setBorder(border);
		jp.add(jl);
		jl = new JLabel("  Status");
		jl.setBorder(border);
		jp.add(jl);
		jl = new JLabel("   "+user.getUsername());
		jl.setBorder(border);
		jp.add(jl);
		userLabels[0][0] = jl;
		if(isHost){
			jl = new JLabel("   Ready");
		}
		else{
			jl = new JLabel("   Not Ready");
		}
		jl.setBorder(border);
		jp.add(jl);
		userLabels[0][1] = jl;
		
		for(int i=1; i<4; i++){
			if(i <= users_in_room-1){
				jl = new JLabel("   "+usersConnected[i-1].getUsername());
				jl.setBorder(border);
				jp.add(jl);
				userLabels[i][0] = jl;
				if(usersConnected[i-1].getReadyStatus()){
					jl = new JLabel("   Ready");
				}
				else{
					jl = new JLabel("   Not Ready");
				}
				jl.setBorder(border);
				jp.add(jl);
				userLabels[i][1] = jl;
			}
			else{
				jl = new JLabel("   -------");
				jl.setBorder(border);
				jp.add(jl);
				userLabels[i][0] = jl;
				jl = new JLabel("   ---------");
				jl.setBorder(border);
				jp.add(jl);
				userLabels[i][1] = jl;
			}//end of else	
		}//end of for
		centerTopPanel.add(jp);
	}//end of creating the panel that holds a table of user
	
	public void updateuserLabels(){
		for(int i=1; i<4; i++){
			if(i < users_in_room){
				userLabels[i][0].setText("   "+usersConnected[i].getUsername());
				if(usersConnected[i].getReadyStatus()){
					userLabels[i][1].setText("   Ready");
					usersReady++;
				}//end of if ready
				else{
					userLabels[i][1].setText("   Not Ready");
				}//end of else not ready
			}//end of if
		}//end of for
		updated = true;
	}//end of updating the labels
	
	public void reopenGameRoom(){
		setVisible(true);
		/*
		 * reset jlabels
		*/
	}//end of reopening the gameroom
	public void reset(){
		for(int i=1; i < 4; i++){
			userLabels[i][1].setText("   Not Ready");
		}//end of for
	}
	
	public void removeChatThread(ChatThread ct) {
		ctVector.remove(ct);
	}
	public void sendMessageToClients(Object obj) {
		if(isHost){
			System.out.println("ctvector: "+ctVector.size());
			for (ChatThread ct1 : ctVector) {
				//if (!ct.equals(ct1)) {
					ct1.sendMessage(obj);
				//}
			}
		}	
	}
	
	class ChatThread extends Thread {
		//private BufferedReader br;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		//private GameRoomGUI grg;
		private Socket s;
		public ChatThread(Socket s) {
			//this.grg = grg;
			this.s = s;
			try {
				ois = new ObjectInputStream(s.getInputStream());
				oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject("Connected to server!\n");
				oos.flush();
			} catch (IOException ioe) {
				System.out.println("IOE in ChatThread constructor: " + ioe.getMessage());
			}
		}//end of chat thread

		public void sendMessage(Object obj) {
			try {
				oos.writeObject(obj);
				oos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end of send message

		public synchronized void run(){
			try {
				obj = ois.readObject();
				while(obj != null){
					System.out.println("in while: "+obj.getClass());
					if(obj instanceof User){
						System.out.println("instance of user: "+obj.getClass());
						usersConnected((User)obj);
						updateuserLabels();
						while(!updated){}
						oos.writeObject(userLabels);
						oos.flush();
						oos.writeObject(new Integer(users_in_room-1));
						oos.flush();
					}//end of if user object
					else if(obj instanceof String){
						System.out.println("instance of string");
						sendMessageToClients(obj);
						System.out.println("msgs sent out");
						chatbox.append(((String)obj));
					}//end of else if obj is a string
					else if(obj instanceof JLabel[][]){
						for(int i=1; i < 4; i++){
							userLabels[i][0].setText(((JLabel[][])obj)[i][0].getText());
							userLabels[i][1].setText(((JLabel[][])obj)[i][1].getText());
						}//end of for
					}//end of updating labels
					else if(obj instanceof Integer){
						if((Integer)obj == -2){
							usersReady++;
						}//end of if  == 2
						else if((Integer)obj == -1){
							Board b = new Board();
							new GameScreenGUI(b, user.toPlayer(), false);
							setVisible(false);
							loadGameScreen = false;
						}
						else{
							userLabelIndex = (Integer)obj;
						}
					}//end of else integer
					obj = ois.readObject();
				}//end of while	
			}catch(IOException ioe){
				System.out.println("IOE in chatserver constructor: " + ioe.getMessage());
			} catch(ClassNotFoundException cnfe){
				System.out.println("CNFE in readobject.run: " + cnfe.getMessage());	
			} finally{
				try {
					if(br != null)
					{
						br.close();
					}
					if(s != null)
					{
						s.close();
					}
					if(ss != null)
					{
						ss.close();
					}
					
				} catch (IOException e) {
					System.out.println("IOE in server.run() in finally block: "+e.getMessage());
				}
			}//end of finally block
		}//end of run
	}//end of chathread
	
		class ReadObject extends Thread{
			ReadObject(){
			}
			
			public synchronized void run(){
				try {
					System.out.println("obj = ois.readObject()");
					obj = ois.readObject();
					System.out.println(obj.getClass());
					System.out.println((String)obj);
					oos.writeObject(user);
					oos.flush();
					while(obj != null){
						if(obj instanceof AbstractUser){
							usersConnected((AbstractUser)obj);
							updateuserLabels();
							while(!updated){}
							oos.writeObject(userLabels);
							oos.flush();
							oos.writeObject(new Integer(users_in_room-1));
							oos.flush();
						}//end of if user object
						else if(obj instanceof String){
							chatbox.append(((String)obj));
						}//end of else if obj is a string
						else if(obj instanceof JLabel[][]){
							for(int i=1; i < 4; i++){
								//userLabels = (JLabel[][])obj;
								userLabels[i][0].setText(((JLabel[][])obj)[i][0].getText());
								userLabels[i][1].setText(((JLabel[][])obj)[i][1].getText());
							}//end of for
						}//end of updating labels
						else if(obj instanceof Integer){
							if((Integer)obj == -2){
								usersReady++;
							}//end of if  == 2
							else if((Integer)obj == -1){
								Board b = new Board();
								new GameScreenGUI(b, user.toPlayer(), false);
								setVisible(false);
								loadGameScreen = false;
							}
							else{
								userLabelIndex = (Integer)obj;
							}
						}//end of else integer
						System.out.println("before");
						obj = ois.readObject();
						System.out.println("after");
					}//end of while	
				}catch(IOException ioe){
					System.out.println("IOE in readobject.run: " + ioe.getMessage());
				} catch(ClassNotFoundException cnfe){
					System.out.println("CNFE in readobject.run: " + cnfe.getMessage());
				}
			}//end of run
		}//end of inner class read object
		
		
	class CreateConnections extends Thread{
		public CreateConnections(){
			
		}
		public void run(){
			if(isHost){
				userLabelIndex = 0;
				setupHost();
				try {
					System.out.println("Starting Chat Server");
					ss = new ServerSocket(6789);
					while (true) {
						System.out.println("Waiting for client to connect...");
						Socket s = ss.accept();
						System.out.println("Client " + s.getInetAddress() + ":" + s.getPort() + " connected");
						ChatThread ct = new ChatThread(s);
						ctVector.add(ct);
						ct.start();
					}
				} catch (IOException ioe) {
					System.out.println("IOE: " + ioe.getMessage());
				} finally {
					if (ss != null) {
						try {
							ss.close();
						} catch (IOException ioe) {
							System.out.println("IOE closing ServerSocket: " + ioe.getMessage());
						}
					}
				}//end of finally

			}//end of if host
			else{
				try {
					s = new Socket("localhost", 6789);
					oos = new ObjectOutputStream(s.getOutputStream());
					ois = new ObjectInputStream(s.getInputStream());
					new ReadObject().start();
					} catch (IOException ioe) {
						System.out.println("IOE client: " + ioe.getMessage());
					}
				}
		}
	}
	
}//end of class
