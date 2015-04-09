package main;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


// TabPanel is used to store the information for each difficulty
//The table and buttons for the gamelobby are stored here
public class TabPanel extends JPanel {
	JTable gameListTable;
	Object tableData[][][];
	
	JPanel buttonPanel;
	JButton createButton;
	JButton joinButton;
	JButton returnButton;
	
	
	public TabPanel(){
		initializeComponents();
		createGUI();
	}
	
	public void initializeComponents(){
		buttonPanel = new JPanel();
		createButton = new JButton("Create Game");
		joinButton = new JButton("Join Game");
		returnButton = new JButton("Return Button");
		
		
		String [] columnNames = {"Host Name, Difficulty, Player in Room"};
		tableData = new Object[100][100][100];
		gameListTable = new JTable(30,3);
	}
	
	public void createGUI(){
		//setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		//setLayout(new GridBagLayout());
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		buttonPanel.add(joinButton);
		buttonPanel.add(Box.createGlue());
		buttonPanel.add(createButton);
		buttonPanel.add(Box.createGlue());
		buttonPanel.add(returnButton);
		
		JScrollPane jsp = new JScrollPane(gameListTable);
		gameListTable.setFillsViewportHeight(true);
		
		add(jsp,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
		/*
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 10;
		gbc.gridwidth = 3;
		add(jsp,gbc);
		
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 11;
		
		
		add(buttonPanel,gbc);
		*/
		//add(jsp);
		//add(buttonPanel);
	}
}
