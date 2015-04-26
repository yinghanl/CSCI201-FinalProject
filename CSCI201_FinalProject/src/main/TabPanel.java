package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import main.User;


// TabPanel is used to store the information for each difficulty
//The table and buttons for the gamelobby are stored here
public class TabPanel extends JPanel {
	JTable gameListTable;
	DefaultTableModel gameListModel;
	Object tableData[][];
	GameLobbyGUI gameLobbyWindow;
	
	JPanel buttonPanel;
	JButton createButton;
	JButton joinButton;
	JButton returnButton;
	AbstractUser u;
	
	private LogInGUI loginWindow;
	
	
	
	private GameRoomClient grc;

	public TabPanel(AbstractUser user, GameLobbyGUI gameLobbyWindow, LogInGUI loginWindow){
		this.u = user;
		this.gameLobbyWindow = gameLobbyWindow;
		this.loginWindow = loginWindow;
		
		grc = new GameRoomClient(this, u);
		System.out.println("created gameroomClient");
		grc.start();
		System.out.println("started gameroomclientthread");
		initializeComponents();
		System.out.println("initialized components");
		createGUI();
		System.out.println("createdGUI");
		addActionListeners();
		System.out.println("added Actionlisteners");

	}
	
	public void initializeComponents(){
		buttonPanel = new JPanel();
		createButton = new JButton("Create Game");
		joinButton = new JButton("Join Game");
		joinButton.setEnabled(false);
		if(u instanceof User) returnButton = new JButton("Logout");
		else returnButton = new JButton("Return to Login Window");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int buttonWidth = (int)(.33*screenSize.getWidth());
		createButton.setPreferredSize(new Dimension(buttonWidth,25));
		joinButton.setPreferredSize(new Dimension(buttonWidth,25));
		returnButton.setPreferredSize(new Dimension(buttonWidth,25));
		
		
		String [] columnNames = {"Host Name", "Players in Room"};
		
		tableData = new Object[0][0]; //NOTE: May need to change so not hardcoded
		gameListModel = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		gameListTable = new JTable(gameListModel);
		gameListTable.setOpaque(false);	
		gameListTable.setSelectionForeground(Color.WHITE);
		gameListTable.setSelectionBackground(Color.RED);
		gameListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameListTable.setGridColor(Color.BLUE);
		gameListTable.setPreferredScrollableViewportSize(gameListTable.getPreferredSize());
		
	}
	
	public void createGUI(){
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		//setLayout(new GridBagLayout());
		
		

		//buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(joinButton);
		buttonPanel.add(createButton);
		buttonPanel.add(returnButton);
		
		((DefaultTableCellRenderer)gameListTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		Border loweredBevel = BorderFactory.createRaisedBevelBorder();
		JScrollPane jsp = new JScrollPane(gameListTable);
		jsp.setBorder(loweredBevel);
		gameListTable.setFillsViewportHeight(true);
		jsp.getViewport().setBackground(new Color(204,240,248));
		
		//add(jsp,BorderLayout.CENTER);
		//add(buttonPanel,BorderLayout.SOUTH);
		add(jsp);
		add(buttonPanel);
	}
	
	public void addActionListeners()
	{
		
		joinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			
				String host = (String)gameListModel.getValueAt(gameListTable.getSelectedRow(), 0);
				grc.joinHostGame(host);
				
				new GameRoomGUI(u, false, "localhost", 8002, host + "'s Room", gameLobbyWindow, grc);
				gameLobbyWindow.setVisible(false);
			}
		});
		
		
		createButton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e) {
				Game newGame = new Game(u);
				grc.newGame(newGame);
				
				new GameRoomGUI(u, true, "localhost", 8002, u.getUsername() + "'s Room", gameLobbyWindow, grc);
				gameLobbyWindow.setVisible(false);
			}	
		});
		
		returnButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				loginWindow.setVisible(true);
				gameLobbyWindow.dispose();
			}
		});

		
		gameListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e) 
			{
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				if(lsm.isSelectionEmpty())
				{
					joinButton.setEnabled(false);
				}
				else if((int)gameListTable.getValueAt(gameListTable.getSelectedRow(), 1) == 2)
				{
					joinButton.setEnabled(false);
				}
				else
				{
					joinButton.setEnabled(true);
				}
			}
		});
	}
	
	public void updateGames(Vector<Game> games){
		//System.out.println("Trying to update game");
		int numGames = games.size();
		int selectedRow = -1;
		System.out.println("number of games: " + numGames);
		if(gameListTable != null)
		{
			selectedRow = gameListTable.getSelectedRow();
		}
		
		gameListModel.setRowCount(0);
		for (int i = 0; i < numGames; i++){
			Game g = games.elementAt(i);
			gameListModel.addRow(new Object[] { g.getGameHost().getUsername(), g.getNumJoined() });
		}
		gameListModel.fireTableDataChanged();
		if(selectedRow != -1)
		{
			gameListTable.setRowSelectionInterval(selectedRow, selectedRow);
		}
		
		
		
	}
	
}