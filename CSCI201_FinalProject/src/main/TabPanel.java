package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

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
public class TabPanel extends JPanel implements Runnable{
	JTable gameListTable;
	DefaultTableModel gameListModel;
	Object tableData[][];
	GameLobbyGUI gameLobbyWindow;
	
	JPanel buttonPanel;
	JButton createButton;
	JButton joinButton;
	JButton returnButton;
	AbstractUser u;
	Vector<Game> games;
	private Vector<Game> gamesOpen;
	
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public TabPanel(AbstractUser user, GameLobbyGUI gameLobbyWindow){
		gamesOpen = new Vector<Game>();
		this.u = user;
		this.gameLobbyWindow = gameLobbyWindow;
		
		try 
		{
			s = new Socket("192.168.1.143", 6789); //should take in IP address of host
			ois = new ObjectInputStream( ( s.getInputStream() )  );
			oos = new ObjectOutputStream(s.getOutputStream());
			
			try {
				while (ois.readObject() != null)
				{
					try {
						games = (Vector<Game>) ois.readObject();
						//update all the games of the user's gameLobby table
						updateGames(games);
					}
					catch (ClassCastException cce) {cce.printStackTrace();} 
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally {
			try{
				oos.close();
				ois.close();
				s.close();
			} catch (IOException ioe) { ioe.printStackTrace(); }
		}
	
		
		initializeComponents();
		createGUI();
		addActionListeners();

	}
	
	public void initializeComponents(){
		buttonPanel = new JPanel();
		createButton = new JButton("Create Game");
		joinButton = new JButton("Join Game");
		returnButton = new JButton("Return Button");
		
		
		String [] columnNames = {"Host Name", "Players in Room"};
		
		tableData = new Object[0][0]; //NOTE: May need to change so not hardcoded
		gameListModel = new DefaultTableModel(tableData, columnNames);
		gameListTable = new JTable(gameListModel);
		System.out.println(gamesOpen.size());
		
		//populate with games already open
		for (int i = 0; i < gamesOpen.size(); i++){
					
					String host = gamesOpen.get(i).getGameHost().getUsername();
					int playersJoined = gamesOpen.get(i).getNumJoined();
					
					
					gameListModel.addRow(new Object[] { host, playersJoined });
					
					//recreate table to update
					gameListModel.fireTableDataChanged();
					
		}
		
		
		gameListTable.setSelectionForeground(Color.WHITE);
		gameListTable.setSelectionBackground(Color.RED);
		gameListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameListTable.setGridColor(Color.BLUE);
		
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

	}
	
	public void addActionListeners()
	{
		
		joinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				gameLobbyWindow.setVisible(false);
		
			}
		});
		
		
		createButton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e) {
						
				//create new game and add it to the table's model
				Game newgame = new Game(u);
				gamesOpen.add(newgame);
				System.out.println(gamesOpen.size());
				
				
				gameListModel.addRow(new Object[] { u.getUsername(), newgame.getNumJoined() });
				
				//recreate table to update
				gameListModel.fireTableDataChanged();
				
				new GameRoomGUI(u, true, "localhost", 8000, u.getUsername() + "'s Room");
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

	}
	
	public void updateGames(Vector<Game> games){
		
		int numGames = games.size();
		tableData = new Object[0][0];
		
		String [] columnNames = {"Host Name", "Players in Room"};
		tableData = new Object[0][0]; //NOTE: May need to change so not hardcoded
		gameListTable = new JTable(new DefaultTableModel(tableData, columnNames));
		
		DefaultTableModel tableModel = (DefaultTableModel) gameListTable.getModel();
		
		for (int i = 0; i < numGames; i++){
			Game g = games.elementAt(i);
			tableModel.addRow(new Object[] { g.getGameHost().getUsername(), g.getNumJoined() });
		}
		
		gameListTable = new JTable(tableModel);
		
	}

	
	public void run() {

	}
	
	
}