//package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


// TabPanel is used to store the information for each difficulty
//The table and buttons for the gamelobby are stored here
public class TabPanel extends JPanel {
	JTable gameListTable;
	Object tableData[][];
	
	JPanel buttonPanel;
	JButton createButton;
	JButton joinButton;
	JButton returnButton;
	AbstractUser u;
	ArrayList<Game> gamesOpen;
	
	public TabPanel(AbstractUser user){
		initializeComponents();
		createGUI();
		this.u = user;
		gamesOpen = new ArrayList<Game>();
	}
	
	public void initializeComponents(){
		buttonPanel = new JPanel();
		createButton = new JButton("Create Game");
		joinButton = new JButton("Join Game");
		returnButton = new JButton("Return Button");
		
		
		String [] columnNames = {"Host Name", "Players in Room"};
		tableData = new Object[20][20];
		gameListTable = new JTable(new DefaultTableModel(tableData, columnNames));
		
		
		gameListTable.setSelectionForeground(Color.WHITE);
		gameListTable.setSelectionBackground(Color.RED);
		gameListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameListTable.setGridColor(Color.BLUE);
		
	}
	
	public void createGUI(){
		//setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		//setLayout(new GridBagLayout());
		
		joinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				int row = gameListTable.getSelectedRow();
				
				//check that there are less than 4 players 
				if ( gameListTable.getValueAt(row,1).equals(4) ) { return; } 
				
				//if there are less than 4 players, join the game and update the table
				else {
					String gameToJoincreator = (String) gameListTable.getValueAt(row, 0);
					//find the game in the vector of games to join the right one
					Game toJoin = null;
					for (int i = 0; i < gamesOpen.size(); i++)
					{
						if (gamesOpen.get(i).getGameCreator().equals(gameToJoincreator))
						{
							toJoin = gamesOpen.get(i);
							
							//join game
							toJoin.joinGame(u.getUsername());
							
							//update table (1) is the index of  column NumJoined
							gameListTable.setValueAt(toJoin.getNumJoined(), row, 1);
							DefaultTableModel tableModel = (DefaultTableModel) gameListTable.getModel();
							gameListTable = new JTable(tableModel);
							
							break;
						}
					}
					
				}
				
			}
		});
		
		createButton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e) {
						
				//create new game and add it to the table's model
				Game newgame = new Game(u);
				gamesOpen.add(newgame);
				
				
				DefaultTableModel tableModel = (DefaultTableModel) gameListTable.getModel();
				tableModel.addRow(new Object[] { newgame.getGameCreator(), newgame.getNumJoined() });
				//recreate table to update
				gameListTable = new JTable(tableModel);
				
				System.out.println("HERE!!");
				
			}
			
		});
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
