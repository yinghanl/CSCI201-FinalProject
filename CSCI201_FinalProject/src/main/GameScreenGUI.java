package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameScreenGUI extends JFrame{

	ImagePanel board;
	JPanel chatBox;
	JPanel optionsPanel;
	JTextArea chat;
	JTextField chatEdit;
	JLabel[][] spaces;
	JButton[] options;
	JButton previous = new JButton("<-");
	JButton next = new JButton("->");
	JPanel buttonsPanel;
	
	int nextIndex = 0;
	int previousIndex = 0;
	
	public GameScreenGUI()
	{
		this.setSize(800,400);
		this.setLocation(0,0);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		board = this.createBoard();
		
		this.add(board, BorderLayout.CENTER);
		
		chatBox = this.getChatBox();
		
		this.add(chatBox, BorderLayout.EAST);
		
		this.createButtons(13);
		
		optionsPanel = this.getOptions();
				
		this.add(optionsPanel, BorderLayout.SOUTH);
		
		this.createActions();
		
		this.setVisible(true);
		
		
	}
	
	private ImagePanel createBoard()
	{		
		ImagePanel toReturn = new ImagePanel(new ImageIcon("TowerDefense.png").getImage());
		
		toReturn.setSize(600,400);
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
	
	private void createButtons(int k)
	{
		options = new JButton[k];
		
		for(int i = 0; i < k; i++)
		{
			options[i] = new JButton("" + i);
			options[i].setSize(10,10);
			options[i].setPreferredSize(options[i].getPreferredSize());
		}
	}
	
	private JPanel getOptions()
	{
		JPanel toReturn = new JPanel();
		toReturn.setSize(100,50);
		toReturn.setPreferredSize(toReturn.getSize());
		toReturn.setLayout(new BorderLayout());
		
		buttonsPanel = new JPanel();
		buttonsPanel.setSize(100,50);
		buttonsPanel.setPreferredSize(buttonsPanel.getSize());
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

		
		toReturn.add(previous, BorderLayout.WEST);

		buttonsPanel.add(javax.swing.Box.createGlue());
		
		for(int i = 0; i < 5; i++)
		{
			buttonsPanel.add(options[i]);
			buttonsPanel.add(javax.swing.Box.createGlue());
		}

		toReturn.add(buttonsPanel, BorderLayout.CENTER);
		toReturn.add(next, BorderLayout.EAST);

		nextIndex = 5;
		previousIndex = 0;
		
		return toReturn;
	}
	
	private JPanel updateOptionsNext()
	{
		JPanel toReturn = new JPanel();
		
		toReturn.setSize(100,50);
		toReturn.setPreferredSize(toReturn.getSize());
		toReturn.setLayout(new BoxLayout(toReturn, BoxLayout.X_AXIS));

		toReturn.add(javax.swing.Box.createGlue());
		int counter = 0;
		
		for(int i = nextIndex; i < nextIndex + 5; i++)
		{
			if(i < options.length)
			{
				toReturn.add(options[i]);
				counter++;
			}
			toReturn.add(javax.swing.Box.createGlue());
		}
		
		nextIndex = nextIndex + counter;
		previousIndex = previousIndex + 5;
				
		return toReturn;
	}
	
	private JPanel updateOptionsPrevious()
	{
		JPanel toReturn = new JPanel();
		
		toReturn.setSize(100,50);
		toReturn.setPreferredSize(toReturn.getSize());
		toReturn.setLayout(new BoxLayout(toReturn, BoxLayout.X_AXIS));

		toReturn.add(javax.swing.Box.createGlue());
		int counter = 0;

		for(int i = previousIndex-5; i < previousIndex; i++)
		{
			if(i < options.length)
			{
				toReturn.add(options[i]);
				counter++;
			}
			toReturn.add(javax.swing.Box.createGlue());
		}
		
		previousIndex = previousIndex - 5;
		nextIndex = previousIndex + 5;
		
		return toReturn;
	}
	
	
	private void createActions()
	{
		next.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				
				if(nextIndex != options.length)
				{
					JPanel newOptionsPanel = updateOptionsNext();
					buttonsPanel.setVisible(false);
					optionsPanel.remove(buttonsPanel);	
					optionsPanel.add(newOptionsPanel, BorderLayout.CENTER);
					buttonsPanel = newOptionsPanel;
				}
			}
			
		});
		
		previous.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(previousIndex != 0)
				{
					JPanel newOptionsPanel = updateOptionsPrevious();
					buttonsPanel.setVisible(false);
					optionsPanel.remove(buttonsPanel);	
					optionsPanel.add(newOptionsPanel, BorderLayout.CENTER);
					buttonsPanel = newOptionsPanel;
				}
			}
		});
		
	}
	
}
