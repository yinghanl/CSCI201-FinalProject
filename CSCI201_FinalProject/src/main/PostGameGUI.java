package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PostGameGUI extends JFrame{
	private JTable statsTable;
	private DefaultTableModel tableModel;
	private JLabel gameoutcomeLabel;
	private JPanel centerPanel;
	private JPanel topPanel;
	private JButton exitButton;
	
	private Vector<GameStats> gsVector;
	private GameLobbyGUI glw;
	
	public PostGameGUI(Vector<GameStats> gsVector, GameLobbyGUI glw){
		super("End of Game Statistics");
		this.glw = glw;
		this.gsVector = gsVector;
		instantiateComponents();
		createGUI();
		addActionListeners();
		
		setVisible(true);
	}//end of constructor
	
	public void addRow(){
		
	}//adding row to table
	
	public void instantiateComponents()
	{
		exitButton = new JButton("Done");
		topPanel = new JPanel();
		centerPanel = new JPanel();
		gameoutcomeLabel = new JLabel("Won!");
	}
	
	public void createGUI()
	{
		setLocation(350,200);
		setSize(400, 200);
		setLayout(new BorderLayout());
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		createTable();
		add(statsTable);
		add(exitButton, BorderLayout.SOUTH);
		gameoutcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
		topPanel.add(gameoutcomeLabel);
		add(topPanel, BorderLayout.NORTH);
	}
	
	public void addActionListeners()
	{
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				glw.setVisible(true);
				PostGameGUI.this.dispose();
			}
		});
		addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				glw.setVisible(true);
				PostGameGUI.this.dispose();
			}
		});
		
	}
	
	public void createTable(){
		Object data[][] = new Object[][]{};
		String columnNames[] = {"Player", "Gold Earned", "Creeps Killed"};
		tableModel = new DefaultTableModel(data, columnNames);
		for(int i = 0; i < gsVector.size(); i++)
		{
			GameStats gs = gsVector.get(i);
			AbstractUser tempUser = gs.getAbstractUser();
			System.out.println(tempUser.getUsername());
			System.out.println(gs.getGold());
			System.out.println(gs.getCreepsKilled());
			tableModel.addRow(new Object[]{tempUser.getUsername(), gs.getGold(), gs.getCreepsKilled()});
		}
		statsTable = new JTable(tableModel);
		statsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		statsTable.setGridColor(Color.BLUE);
		statsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		statsTable.setSelectionBackground(Color.GRAY);
		JScrollPane jsp = new JScrollPane(statsTable);
		centerPanel.add(jsp);
	}//end of creating table
	
}//end of class
