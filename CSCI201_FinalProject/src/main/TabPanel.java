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
	private JTable gameListTable;
	private DefaultTableModel gameListModel;
	private Object tableData[][];
	private GameLobbyGUI gameLobbyWindow;
	
	private JPanel buttonPanel;
	private JButton createButton;
	private JButton joinButton;
	private JButton returnButton;
	private AbstractUser u;
	
	private LogInGUI loginWindow;
	private int chatPort;
	
	
	private GameRoomClient grc;

	public TabPanel(AbstractUser user, GameLobbyGUI gameLobbyWindow, LogInGUI loginWindow){
		this.u = user;
		this.gameLobbyWindow = gameLobbyWindow;
		this.loginWindow = loginWindow;
	
		initializeComponents();
		createGUI();
		addActionListeners();
		grc = new GameRoomClient(this, u);
		grc.start();
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
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException ie)
				{
					System.out.println("IE:" + ie.getMessage());
				}
				//System.out.println("chatPort = " + chatPort);
				new GameRoomGUI(u, false, "localhost", chatPort, host + "'s Room", gameLobbyWindow, grc);
				gameLobbyWindow.setVisible(false);
			}
		});
		
		
		createButton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e) {
				Game newGame = new Game(u);
				newGame.setChatPort(grc.getChatPort());
				grc.newGame(newGame);
				new GameRoomGUI(u, true, "localhost", grc.getChatPort(), u.getUsername() + "'s Room", gameLobbyWindow, grc);
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
				else if(((String)gameListTable.getValueAt(gameListTable.getSelectedRow(), 1)).equals("2"))
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
	
	public void updateGames(String [] gameString){
		//System.out.println("Trying to update game");
		int numGames = gameString.length;
		int selectedRow = -1;
		//System.out.println("number of games: " + numGames);
		if(gameListTable != null)
		{
			selectedRow = gameListTable.getSelectedRow();
		}
		gameListModel.setRowCount(0);
		if(numGames != 1 || !gameString[0].equals(""))
		{
			for (int i = 0; i < numGames; i++)
			{
			//System.out.println("gameString[i] = " + gameString[i]);
			
			String [] gameInfo = gameString[i].split(":::");
			gameListModel.addRow(new Object[] { gameInfo[0], gameInfo[1] });
			}
		}
		
		gameListModel.fireTableDataChanged();
		if(selectedRow != -1 && selectedRow < gameListModel.getRowCount())
		{
			gameListTable.setRowSelectionInterval(selectedRow, selectedRow);
		}
		
		
		
	}
	
	public void setChatPort(int nextPort)
	{
		chatPort = nextPort;
	}
	
}