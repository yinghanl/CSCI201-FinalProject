package main;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GameRoom extends JFrame{
	private JPanel windowPanel;
	private JPanel picture_of_mapPanel;
	private JPanel chatboxPanel;
	private JPanel listof_playersPanel;
	private JTextArea chatbox;
	
	public GameRoom(){
		super("Game Room");
		setSize(700, 500);
		setLocation(300, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		setVisible(true);
	}//end of constructor
	
	public void createPicturePanel(){
		JLabel jl = new JLabel("");
		jl.setIcon(new ImageIcon("map.jpg"));
		picture_of_mapPanel.setLayout(new GridLayout(1,1));
		picture_of_mapPanel.add(jl);	
		add(jl);
	}//end of createpicturepanel
	
	public void createChatbox(){
		chatboxPanel = new JPanel();
		chatboxPanel.setLayout(new BoxLayout(chatboxPanel, BoxLayout.X_AXIS));

		chatbox = new JTextArea(3,50);
		JScrollPane scroll = new JScrollPane(chatbox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		chatbox.setLineWrap(true);
		chatbox.setWrapStyleWord(true);
		chatboxPanel.add(scroll);
		add(chatboxPanel);
	}//end of creating the chatbox
	
//	public void setVisible(boolean b){
//		setVisible(b);
//	}//end of setVisible
	
}//end of class
