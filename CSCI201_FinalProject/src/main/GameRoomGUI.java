package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private Player host;
	
	public GameRoomGUI(Player host){
		super("Game Room");
		setSize(700, 500);
		setLocation(300, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.host = host;
		players_array = new Player[4];
		players_array[0] = host;
		/*
		 * User who creates the room will have the ready button disabled because they have the start button
		 * Assumes the host is always ready so boolea[0] is set at true
		 * Gameroom will have at least one player (host who created the room)
		 */
		
		//test players
		for(int i=1; i<4; i++){
			players_array[i] = new Player("p"+i);
			System.out.println(players_array[i].getName());	
		}
		
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
		setVisible(true);
	}//end of constructor
	
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
				chatbox.append(temp+"\n");
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
		ImageIcon io = new ImageIcon("TowerDefense.png");
		picture_of_mapPanel.add(new JPanel(){public void paintComponent(Graphics g) {
			super.paintComponent(g); //call super! super is super important
			g.drawImage(io.getImage(), 0, 0, this.getSize().width, this.getSize().height,null);
			//0,0 means draw at the top left corner
			//getSize().width getSize().height means to make the image that size of (this), in which (this) is
			//the panel that contains the image. (Effectively making the image the size of the panel)
			//If we resize the window, the panel will resize, and the image will rescale as well
			}
		}, BorderLayout.CENTER);
		centerTopPanel.add(picture_of_mapPanel);
	}//end of createpicturepanel
	
	public void createChatbox(){
		chatboxPanel = new JPanel();
		chatboxPanel.setLayout(new BoxLayout(chatboxPanel, BoxLayout.X_AXIS));

		chatbox = new JTextArea(3,50);
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
		jl = new JLabel("   "+host.getName());
		jl.setBorder(border);
		jp.add(jl);
		jl = new JLabel("   Ready");
		jl.setBorder(border);
		jp.add(jl);
		for(int i=1; i<4; i++){
			jl = new JLabel("   "+players_array[i].getName());
			jl.setBorder(border);
			jp.add(jl);
			jl = new JLabel("   Not Ready");
			jl.setBorder(border);
			jp.add(jl);
		}//end of for
		centerTopPanel.add(jp);
	}//end of creating the panel that holds a table of players
	
//	public void setVisible(boolean b){
//		setVisible(b);
//	}//end of setVisible
	
}//end of class
