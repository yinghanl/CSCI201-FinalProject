package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameScreenGUI extends JFrame{

	ImagePanel board;
	JPanel chatBox;
	JPanel options;
	JTextArea chat;
	JTextField chatEdit;
	JLabel[][] spaces;
	
	public GameScreenGUI()
	{
		this.setSize(800,400);
		this.setLocation(0,0);
		
		this.setLayout(new BorderLayout());
		
		board = this.createBoard();
		
		this.add(board, BorderLayout.CENTER);
		
		chatBox = this.getChatBox();
		
		this.add(chatBox, BorderLayout.EAST);
		
		this.setVisible(true);
		
		
	}
	
	private ImagePanel createBoard()
	{		
		ImagePanel toReturn = new ImagePanel(new ImageIcon("TowerDefense.png").getImage());
		
		toReturn.setSize(600,400);
		toReturn.setPreferredSize(toReturn.getSize());
		
		toReturn.setLayout(new GridLayout(10,16));
		spaces = new JLabel[12][16];
		
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				spaces[i][j] = new JLabel("");
				spaces[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				spaces[i][j].setOpaque(false);
				toReturn.add(spaces[i][j]);
			}
		}
		
		
		return toReturn;
	}
	
	private JPanel getChatBox()
	{
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new BorderLayout());
		
		toReturn.setSize(200,500);
		
		toReturn.setPreferredSize(toReturn.getSize());
		
		
		chat = new JTextArea();
		JScrollPane sp = new JScrollPane(chat);

		chatEdit = new JTextField();
		
		chat.setEditable(false);
		
		toReturn.add(sp, BorderLayout.CENTER);
		toReturn.add(chatEdit, BorderLayout.SOUTH);
				
		return toReturn;
	}
	
	private JPanel getOptions()
	{
		JPanel toReturn = new JPanel();
		toReturn.setSize(200,500);
		
		toReturn.setPreferredSize(toReturn.getSize());
		
		return toReturn;
	}
	
}
