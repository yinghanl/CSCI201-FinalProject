package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;

import main.GameRoomGUI.ChatThread;
import main.GameRoomGUI.ReadObject;

public class GameScreenGUI extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	private ImagePanel board;
	private JPanel chatBox;
	private JPanel progressPanel;
	private JTextArea chat;
	private JTextField chatEdit;
	private JLabel[][] spaces;
	private Timer progressTimer;
	private JProgressBar progressBar;
	private JLabel task;	
	
	private JLabel levelTimer;
	private JLabel teamGold;
	private JLabel lives;
	
	private int timerInt = 60;
	private int goldEarned = 0;
	private String message;
	private int livesInt;
	
	private Timer lvlTimer;
	
	private Board backendBoard;
	
	private int nextIndex = 0;
	private int previousIndex = 0;
	
	private Player currentPlayer;
	private boolean isHost;
	
	private Vector<ChatThread> ctVector = new Vector<ChatThread>();
	private ChatThread ct;
	private ServerSocket ss;
	private Socket s;
	private BufferedReader br;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Object obj;
	
	private boolean msgSent = false;

	private Tower currentTower;
		
	private Vector<Player> players; 
	private HashMap<Integer, Creep> creeps;
	
	private int MAX_CREEPS = 10;
	
	public GameScreenGUI(Board b, Player p, boolean isHost)
	{
		players = new Vector<Player>();
		creeps = new HashMap<Integer, Creep>();
		
		this.setSize(825,510);
		this.setLocation(0,0);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		
		message = "";

		this.backendBoard = b;
		
		this.currentPlayer = p;
		
		players.add(currentPlayer);
				
		this.isHost = isHost;
		
		board = this.createBoard();
		
		this.add(board, BorderLayout.CENTER);
		
		chatBox = this.getChatBox();
		
		this.add(chatBox, BorderLayout.EAST);
				
		progressPanel = this.getProgressPanel();
				
		this.add(progressPanel, BorderLayout.SOUTH);
		
		this.add(getTopPanel(), BorderLayout.NORTH);
				
		this.setVisible(true);
			
		Timer time = new Timer(10, new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				updateBoard();
			}
		});
		time.start();
		
		board.addMouseListener(new MouseAdapter()
		{
            public void mouseClicked(MouseEvent e) {
                board.requestFocusInWindow();
            }
		});
		
		new CreateConnections().start();
		
		

		
		lvlTimer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				
				timerInt--;
				if(timerInt<0){
					restartLevelTimer();
				}
				
				try{
					Command c = new Command(currentPlayer, "Timer", timerInt, 0);
					if(!isHost){
						oos.writeObject(c);
						oos.flush();	
					}
					
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
				
				levelTimer.setText("" + timerInt);
			}
			
			
		});
		
		if(isHost == true)
		{
			lvlTimer.start();
		}
		
		
	}//end of constructor
	
	private JPanel getTopPanel()
	{
		JPanel toReturn = new JPanel();
		
		toReturn.setLayout(new BoxLayout(toReturn, BoxLayout.X_AXIS));
		
		levelTimer = new JLabel("" + timerInt);
		
		teamGold = new JLabel("Gold: " + goldEarned);
		
		lives = new JLabel("Lives: " + livesInt);
		
		toReturn.add(lives);
		toReturn.add(Box.createGlue());
		toReturn.add(Box.createGlue());

		
		toReturn.add(levelTimer);
		
		toReturn.add(Box.createGlue());
		
		toReturn.add(Box.createGlue());
		
		toReturn.add(teamGold);
		
		toReturn.add(Box.createGlue());
		
		return toReturn;
	}
	
	private ImagePanel createBoard()
	{		
		ImagePanel toReturn = new ImagePanel(new ImageIcon("TowerDefense.png").getImage());
		
		toReturn.setSize(600,700);
		toReturn.setPreferredSize(toReturn.getSize());
		
		toReturn.setLayout(new GridLayout(20,32));
		spaces = new JLabel[20][32];
		
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 32; j++)
			{
				spaces[i][j] = new JLabel("");
				spaces[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				spaces[i][j].setOpaque(false);
				toReturn.add(spaces[i][j]);
			}
		}
		
		toReturn.setFocusable(true);
		toReturn.requestFocusInWindow();
		
		return toReturn;
	}
	
	private JPanel getChatBox()
	{
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new BorderLayout());
		
		toReturn.setSize(200,500);
		
		toReturn.setPreferredSize(toReturn.getSize());
		
		
		chat = new JTextArea();
		chat.setLineWrap(true);
		chat.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret)chat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane sp = new JScrollPane(chat);
		chatEdit = new JTextField();
		
		chat.setEditable(false);
		
		toReturn.add(sp, BorderLayout.CENTER);
		toReturn.add(chatEdit, BorderLayout.SOUTH);
				
		return toReturn;
	}
	
	
	private JPanel getProgressPanel()
	{
		JPanel toReturn = new JPanel();
		toReturn.setSize(100,50);
		toReturn.setPreferredSize(toReturn.getSize());
		toReturn.setLayout(new BoxLayout(toReturn, BoxLayout.Y_AXIS));
		
		JPanel topPanel = new JPanel();
		task = new JLabel("Task in Progress");
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(Box.createGlue());
		topPanel.add(task);
		topPanel.add(Box.createGlue());
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		progressBar = new JProgressBar(0, 100);
		progressBar.setString("No Task");
		progressBar.setStringPainted(true);
		progressBar.setBackground(new Color(139,69,19));
		progressBar.setForeground(new Color(0, 100, 0));
		bottomPanel.add(Box.createGlue());
		bottomPanel.add(progressBar);
		bottomPanel.add(Box.createGlue());
		
		toReturn.add(topPanel);
		toReturn.add(bottomPanel);

		return toReturn;
	}
	
	
	private void createActions()
	{
	
		board.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke) {

				int key = ke.getKeyCode();
				//System.out.println(key);
				int playerx = currentPlayer.getLocation().getX();
				int playery = currentPlayer.getLocation().getY();
//				
//				System.out.println("Before " + playerx + " " + playery);
				
				if(key == ke.VK_UP)
				{
					try {
						currentPlayer.move(0);
						currentPlayer.setPlayerDirection("NORTH");
						if(currentPlayer.moveableCouldMove())
						{
							oos.writeObject(new Command(currentPlayer, "Move(0)"));
							oos.flush();
						}
					} catch (BoundaryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(key == ke.VK_DOWN)
				{
					try {
						currentPlayer.move(1);
						currentPlayer.setPlayerDirection("SOUTH");
						if(currentPlayer.moveableCouldMove())
						{
							oos.writeObject(new Command(currentPlayer, "Move(1)"));
							oos.flush();
						}

					}catch (BoundaryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(key == ke.VK_RIGHT)
				{
					try {
						currentPlayer.move(2);
						currentPlayer.setPlayerDirection("EAST");
						if(currentPlayer.moveableCouldMove())
						{
							oos.writeObject(new Command(currentPlayer, "Move(2)"));
							oos.flush();
						}
					} 
					catch (BoundaryException e) {
						e.printStackTrace();
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(key == ke.VK_LEFT)
				{
					try {
						currentPlayer.move(3);
						currentPlayer.setPlayerDirection("WEST");
						if(currentPlayer.moveableCouldMove())
						{
							oos.writeObject(new Command(currentPlayer, "Move(3)"));
							oos.flush();
						}
					} catch (BoundaryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(key == ke.VK_SPACE)
				{
					if(currentPlayer.playerOperatingTower() != null)
					{
						Tower t = currentPlayer.playerOperatingTower();
						t.shoot();
						
						Command c = new Command(currentPlayer, "Shoot", t.getX(), t.getY());
						try
						{
							oos.writeObject(c);
							oos.flush();
						}
						catch(IOException ioe)
						{
							ioe.printStackTrace();
						}
					}
				}
				else if(key == ke.VK_SHIFT)
				{
					if(currentPlayer.playerOperatingTower() != null)
					{
						Tower t = currentPlayer.playerOperatingTower();
						t.rotate();
						
						if(t instanceof BasicTower)
						{
							int x = ((BasicTower) t).getX();
							int y = ((BasicTower) t).getY();
							BufferedImage image = ((BasicTower) t).getTowerImages();
							Image icon = image.getScaledInstance(spaces[x][y].getWidth(), spaces[x][y].getHeight(), Image.SCALE_SMOOTH);
							spaces[x][y].setIcon(new ImageIcon(icon));
							
							Command c = new Command(currentPlayer, "RotateTower", x, y);
							try
							{
								oos.writeObject(c);
								oos.flush();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
							
						}
					}
				}
				else if(key == ke.VK_1)
				{					
					if(currentPlayer.getPlayerDirection() == "SOUTH")
					{
						if(playerx+1 < 20)
						{
							placeTower(playerx+1, playery, true);
							Command c = new Command(currentPlayer, "PlaceTower", playerx+1, playery);
							try {
								oos.writeObject(c);
								oos.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					else if(currentPlayer.getPlayerDirection() == "NORTH")
					{
						if(playerx-1 > 0)
						{
							placeTower(playerx-1, playery, true);
							Command c = new Command(currentPlayer, "PlaceTower", playerx-1, playery);
							try {
								oos.writeObject(c);
								oos.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						}
					}
					
					else if(currentPlayer.getPlayerDirection() == "WEST")
					{
						if(playery-1 > 0)
						{
							placeTower(playerx, playery-1, true);
							Command c = new Command(currentPlayer, "PlaceTower", playerx, playery-1);
							try {
								oos.writeObject(c);
								oos.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					else if(currentPlayer.getPlayerDirection() == "EAST")
					{
						if(playery+1 < 32)
						{
							placeTower(playerx, playery+1, true);
							Command c = new Command(currentPlayer, "PlaceTower", playerx, playery+1);
							try {
								oos.writeObject(c);
								oos.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			
		});
		
		chatEdit.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke) {
			
				int key = ke.getKeyCode();
				
				if(key == ke.VK_ENTER && chatEdit.getText() != null){
					//System.out.println("setting messageSent to true");					 
					String toAppend = "\n"+currentPlayer.getPlayerName() + ": " + chatEdit.getText() + "\n";
					message = toAppend;
					chatEdit.setText("");
					System.out.println("enter was hit, sending message: " + message);
					if(isHost){
						chat.append(message);
						sendMessageToClients(message);
					}	
					else{
						try {
							oos.writeObject(message);
							oos.flush();
						} catch (IOException e) {
							System.out.println("IOE in GameRoom.Chatclient.run() in while loop writing string object");
						}//end of try-catch
					}	
					
				}
				
			}

		});
	}
	
	public void run(){
		int numCreeps = 10;
		while(numCreeps>0){ //there are remaining creeps
			try {
				Thread.sleep(2000);
				Creep c = new Creep(backendBoard.getPathSpace(0));
				creeps.put(numCreeps, c);
				c.start();
				//new Creep(backendBoard.getPathSpace(0)).start();
				numCreeps--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	/*
	public void updateBoard()
	{
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 32; j++)
			{
				if(backendBoard.getSpace(i, j).isOccupied())
				{
					if(backendBoard.getSpace(i, j).getMoveable() instanceof Player)
					{	
						//System.out.println(p.getPlayerName() + "(" + p.getLocation().getX() + "," + p.getLocation().getY() + ")");
						
						
						spaces[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW));
						
						if(backendBoard.getSpace(i, j).getMoveable().getPrevious() != null){
							int x = backendBoard.getSpace(i, j).getMoveable().getPrevious().getX();
							int y = backendBoard.getSpace(i, j).getMoveable().getPrevious().getY();
							//check to see if currentlocation = previous location, if the player was unable to move
							if(backendBoard.getSpace(i, j).getMoveable().moveableCouldMove()){
								spaces[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
							}
						}
						
						spaces[i][j].validate();
						spaces[i][j].repaint();
					}
					else
					{
						ImageIcon icon = new ImageIcon(backendBoard.getSpace(i,j).getMoveable().getMoveableImage());
					
						spaces[i][j].setIcon(icon);
					}

					
				}
			}
		}
	}
	*/
	
	
	
	public void updateBoard()
	{/*
		//update creeps
		for(int i = 0; i<backendBoard.getCreepPathSize(); i++){
			if(backendBoard.getPathSpace(i).getCreep()!=null){
				int p = backendBoard.getPathSpace(i).getX();
				int q = backendBoard.getPathSpace(i).getY();
//				if(backendBoard.getPathSpace(i).getCreep().isDead()){
//					spaces[p][q].setBorder(BorderFactory.createLineBorder(Color.red));
//					backendBoard.getPathSpace(i).removeCreep();
//					continue;
//				}
//				if {
					spaces[p][q].setBorder(BorderFactory.createLineBorder(Color.red));
//				}
				
				//null pointer exception?
				if(backendBoard.getPathSpace(i).getMoveable().getPrevious() != null && !backendBoard.getPathSpace(i).getMoveable().getPrevious().isOccupied()){
					int x = backendBoard.getPathSpace(i).getMoveable().getPrevious().getX();
					int y = backendBoard.getPathSpace(i).getMoveable().getPrevious().getY();
					spaces[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}

				
//				if(i>0 && !backendBoard.getPathSpace(i-1).isOccupied()){
//					int x = backendBoard.getPathSpace(i-1).getX();
//					int y = backendBoard.getPathSpace(i-1).getY();
//					spaces[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
//				}
			}
		}*/
		
		for(int i = 0; i<MAX_CREEPS; i++){
			if(creeps.containsKey(i)){
				Creep c = creeps.get(i);
				int x = c.getPathLocation().getX();
				int y = c.getPathLocation().getY();
				if(c.isDead()){
					spaces[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					creeps.remove(i);
				}
				else{
					spaces[x][y].setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				
				if(c.getPrevious() !=null){
					int p = c.getPrevious().getX();
					int q = c.getPrevious().getY();
					spaces[p][q].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}
			
		}
	
		for(int i=0; i < players.size(); i++)
		{
			Player p = players.get(i);
			int playerx = p.getLocation().getX();
			int playery = p.getLocation().getY();
			
			//spaces[playerx][playery].setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			
			Image image = p.getIcon().getScaledInstance(spaces[playerx][playery].getWidth(), spaces[playerx][playery].getHeight(), Image.SCALE_SMOOTH);
			
			spaces[playerx][playery].setIcon(new ImageIcon(image));
			
			if(p.getPrevious() != null && p.moveableCouldMove())
			{
				int x = p.getPrevious().getX();
				int y = p.getPrevious().getY();
				//spaces[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				spaces[x][y].setIcon(null);
			}
		}
		
		//bullets
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 32; j++)
			{
				if(backendBoard.getSpace(i, j).isOccupied())
				{
					if(backendBoard.getSpace(i, j).getMoveable() instanceof Bullet){
						spaces[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN));
						//normal movement
						if(backendBoard.getSpace(i, j).getMoveable().getPrevious() != null ){//&& !backendBoard.getSpace(i,j).getMoveable().getPrevious().isOccupied()){
							int x = backendBoard.getSpace(i, j).getMoveable().getPrevious().getX();
							int y = backendBoard.getSpace(i, j).getMoveable().getPrevious().getY();
							spaces[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
							
						}
						//bullet reaching end of map
						if(!backendBoard.getSpace(i, j).getMoveable().moveableCouldMove()){	
							int x = backendBoard.getSpace(i, j).getMoveable().getLocation().getX();
							int y = backendBoard.getSpace(i, j).getMoveable().getLocation().getY();
							backendBoard.getSpace(i, j).removeOccupant();
							spaces[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
						}
						
					}
//					if(backendBoard.getSpace(i,j).getMoveable().getMoveableImage() != null)
//					{
//						ImageIcon icon = new ImageIcon(backendBoard.getSpace(i,j).getMoveable().getMoveableImage());
//					
//						spaces[i][j].setIcon(icon);
//					
//					}
				}
			}
		}
	}
	
	public void placeTower(int x, int y, boolean maker)
	{	
		
		BasicTower b = new BasicTower(backendBoard.getSpace(x, y));
		
		BufferedImage img = b.getTowerImages();
		
		Image resizedImage = img.getScaledInstance(spaces[x][y].getWidth(), spaces[x][y].getHeight(), Image.SCALE_SMOOTH);

		
		if(x < 0 || y < 0 || x > 19 || y > 30)
		{
			return;
		}
			
		progressTimer = new Timer(1000, new ActionListener()
		{
			int timer = 10;
			public void actionPerformed(ActionEvent e) {
				
				if(timer > 0)
				{
					if(maker == true)
					{
						progressBar.setString("Building Tower (" + timer + "s)");
						progressBar.setStringPainted(true);
						progressBar.setValue(progressBar.getValue() + 10);
					}
					timer--;
				}
				else
				{
					if(maker == true)
					{
						progressBar.setString("No Task");
						progressBar.setValue(0);
					}
					progressTimer.stop();
					spaces[x][y].setIcon(new ImageIcon(resizedImage));
					backendBoard.placeTower(backendBoard.getSpace(x,y));
				}
				
			}
		});
		progressTimer.start();


		
		//int count = 0;
		
		/*
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 2; j++)
			{
				if(x+i < 0 || y+j < 0 || x+i > 30 || y+j > 30)
				{
					System.out.println("Attempted to place tower outside of boundaries");
					return;
				}
			}
		}
		
		
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 2; j++)
			{				
				Image resizedImage = img[count].getScaledInstance(spaces[i][j].getWidth(), spaces[i][j].getHeight(), Image.SCALE_SMOOTH);
				
				spaces[x+i][y+j].setIcon(new ImageIcon(resizedImage));
				
				backendBoard.placeTower(x+i, y+j);
				
				count++;
			}
		}
		*/
	}
	
	public void restartLevelTimer()
	{
		timerInt = 60;
	}
	
	public void removeChatThread(ChatThread ct) {
		ctVector.remove(ct);
	}
	public void sendMessageToClients(Object obj) {
		if(isHost){
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
				System.out.println("IOE from ChatThread.sendMessage(): "+e.getMessage());
			}
		}//end of send message

		public synchronized void run(){
			try {
				obj = ois.readObject();
				while(obj != null){
					if(obj instanceof String){				
						chat.append(((String)obj));
						System.out.println("sending chat message to other clients: "+(String)obj);
						sendMessageToClients(obj);
					}//end of if ob is String
					else if(obj instanceof Player)
					{						
						backendBoard.setPlayer((Player)obj);
						players.add((Player)obj);
						sendMessageToClients(obj);
						
					}
					else if(obj instanceof Command)
					{
						Player player = ((Command)obj).getPlayer();
						
						String command = ((Command)obj).getCommand();
						
						for(Player p : players)
						{
							if(player.getPlayerName() == p.getPlayerName())
							{
								if(command.equals("Move(0)"))
								{
									try
									{
										p.setPlayerDirection("NORTH");
										p.move(0);

									}
									catch (BoundaryException e) {
										e.printStackTrace();
									}
								}
								else if(command.equals("Move(1)"))
								{
									try
									{
										p.setPlayerDirection("SOUTH");
										p.move(1);
									}
									catch (BoundaryException e) {
										e.printStackTrace();
									}
								}
								else if(command.equals("Move(2)"))
								{
									try
									{
										p.setPlayerDirection("EAST");
										p.move(2);
									}
									catch (BoundaryException e) {
										e.printStackTrace();
									}
								}
								else if(command.equals("Move(3)"))
								{
									try
									{
										p.setPlayerDirection("WEST");
										p.move(3);
									}
									catch (BoundaryException e) {
										e.printStackTrace();
									}
								}
								else if(command.equals("PlaceTower"))
								{
									Command c = (Command)obj;
									int x = c.getX();
									int y = c.getY();
									placeTower(x, y, false);
								}
								else if(command.equals("RotateTower"))
								{
									Command c = (Command)obj;
									int x = c.getX();
									int y = c.getY();
									
									//Tower t = currentPlayer.playerOperatingTower();
									if(backendBoard.getSpace(x, y) instanceof TowerSpace)
									{
										TowerSpace ts = (TowerSpace) backendBoard.getSpace(x, y);
										Tower t = ts.getTower();
										t.rotate();
										
										if(t instanceof BasicTower)
										{
											BufferedImage image = ((BasicTower) t).getTowerImages();
											Image icon = image.getScaledInstance(spaces[x][y].getWidth(), spaces[x][y].getHeight(), Image.SCALE_SMOOTH);
											spaces[x][y].setIcon(new ImageIcon(icon));
										}
									}								

								}
								else if(command.equals("Shoot"))
								{
									Command c = (Command)obj;
									int x = c.getX();
									int y = c.getY();
									
									if(backendBoard.getSpace(x, y) instanceof TowerSpace)
									{
										TowerSpace ts = (TowerSpace) backendBoard.getSpace(x, y);
										Tower t = ts.getTower();
										
										if(t instanceof BasicTower)
										{
											t.shoot();
										}
									}
								}
								else if(command.equals("Timer"))
								{
									Command c = (Command)obj;
									int timer = c.getX();
									
									timerInt = timer;
									levelTimer.setText("" + timerInt);
									
								}
							}
						}
						sendMessageToClients(obj);
					}//end of else command object
					obj = ois.readObject();
				}//end of while	
			}catch(IOException ioe){
				System.out.println("IOE in chatthread.run: " + ioe.getMessage());
			} catch(ClassNotFoundException cnfe){
				System.out.println("CNFE in chatthread.run: " + cnfe.getMessage());	
			}//end of finally block
		}//end of run
	}//end of chathread
	
	public class ReadObject extends Thread{
		ReadObject(){
		}
		
		public synchronized void run(){
			try {
				obj = ois.readObject();
				while(obj != null){
					System.out.println("ob not null in client: "+obj.getClass());
					if(obj instanceof String){
						chat.append(((String)obj));
					}//end of if ob is String
					else if(obj instanceof Player)
					{						
						backendBoard.setPlayer((Player)obj);
						players.add((Player)obj);
						
					}
					else if(obj instanceof Command)
					{
						Player player = ((Command)obj).getPlayer();
						
						String command = ((Command)obj).getCommand();
						
						for(Player p : players)
						{
							if(player.getPlayerName() == p.getPlayerName())
							{
								if(command.equals("Move(0)"))
								{
									try
									{
										p.setPlayerDirection("NORTH");
										p.move(0);

									}
									catch (BoundaryException e) {
										e.printStackTrace();
									}
								}
								else if(command.equals("Move(1)"))
								{
									try
									{
										p.setPlayerDirection("SOUTH");
										p.move(1);
									}
									catch (BoundaryException e) {
										e.printStackTrace();
									}
								}
								else if(command.equals("Move(2)"))
								{
									try
									{
										p.setPlayerDirection("EAST");
										p.move(2);
									}
									catch (BoundaryException e) {
										e.printStackTrace();
									}
								}
								else if(command.equals("Move(3)"))
								{
									try
									{
										p.setPlayerDirection("WEST");
										p.move(3);
									}
									catch (BoundaryException e) {
										e.printStackTrace();
									}
								}
								else if(command.equals("PlaceTower"))
								{
									Command c = (Command)obj;
									int x = c.getX();
									int y = c.getY();
									placeTower(x, y, false);
								}
								else if(command.equals("RotateTower"))
								{
									Command c = (Command)obj;
									int x = c.getX();
									int y = c.getY();
									
									//Tower t = currentPlayer.playerOperatingTower();
									if(backendBoard.getSpace(x, y) instanceof TowerSpace)
									{
										TowerSpace ts = (TowerSpace) backendBoard.getSpace(x, y);
										Tower t = ts.getTower();
										t.rotate();
										
										if(t instanceof BasicTower)
										{
											BufferedImage image = ((BasicTower) t).getTowerImages();
											Image icon = image.getScaledInstance(spaces[x][y].getWidth(), spaces[x][y].getHeight(), Image.SCALE_SMOOTH);
											spaces[x][y].setIcon(new ImageIcon(icon));
										}
									}								

								}
								else if(command.equals("Shoot"))
								{
									Command c = (Command)obj;
									int x = c.getX();
									int y = c.getY();
									
									if(backendBoard.getSpace(x, y) instanceof TowerSpace)
									{
										TowerSpace ts = (TowerSpace) backendBoard.getSpace(x, y);
										Tower t = ts.getTower();
										
										if(t instanceof BasicTower)
										{
											t.shoot();
										}
									}
								}
								else if(command.equals("Timer"))
								{
									Command c = (Command)obj;
									int timer = c.getX();
									
									timerInt = timer;
									levelTimer.setText("" + timerInt);
									
								}
							}
						}
						
					}
					obj = ois.readObject();
				}//end of while	
			}catch(IOException ioe){
				System.out.println("IOE in readobject.run: " + ioe.getMessage());
			} catch(ClassNotFoundException cnfe){
				System.out.println("CNFE in IOE in  readobject.run: " + cnfe.getMessage());
			}
		}//end of run
	}//end of inner class read object
	class CreateConnections extends Thread{
		public CreateConnections(){
			createActions();
			if(!isHost){
				try {
					s = new Socket("localhost", 8970);
					oos = new ObjectOutputStream(s.getOutputStream());
					ois = new ObjectInputStream(s.getInputStream());
					new ReadObject().start();
				} catch (IOException ioe) {
					System.out.println("IOE client: " + ioe.getMessage());
				}
			}//end of if not host
			try
			{
				if(oos != null)
				{
					oos.writeObject(currentPlayer);
					oos.flush();
				}
			} catch (IOException e1) {
				System.out.println("Exception sending player to server");
			}
		}//end of constructor
		public void run(){
			if(isHost){
				try {
					System.out.println("Starting Chat Server");
					ss = new ServerSocket(8970);
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
		}
	}
}//end of class
