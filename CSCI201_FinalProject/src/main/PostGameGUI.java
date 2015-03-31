package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JLabel timeLabel;
	private JLabel gameoutcomeLabel;
	private JPanel centerPanel;
	private JPanel topPanel;
	private JButton exitButton;
	public PostGameGUI(){
		super("End of Game Statistics");
		setLocation(350,200);
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		centerPanel = new JPanel();
		
		createTable();
		add(statsTable);
		createButtons();
		add(exitButton, BorderLayout.SOUTH);
		createLabels();
		timeLabel.setAlignmentX(CENTER_ALIGNMENT);
		gameoutcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
		topPanel.add(timeLabel);
		topPanel.add(gameoutcomeLabel);
		add(topPanel, BorderLayout.NORTH);
		setVisible(true);
	}//end of constructor
	
	public void createButtons(){
		exitButton = new JButton("Done");
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				dispose();
			}
		});
	}//end of creating buttons
	
	public void createLabels(){
		timeLabel = new JLabel("14:41");
		gameoutcomeLabel = new JLabel("Won!");
	}//end of creating labels
	
	public void createTable(){
		Object data[][] = new Object[][]{};
		String columnNames[] = {"Player", "Gold Earned", "Creeps Killed", "Towers Bought"};
		tableModel = new DefaultTableModel(data,columnNames);
		//tableModel.addRow(new Object[]{"Player", "Gold Earned", "Creeps Killed", "Towers Bought"});
		tableModel.addRow(new Object[]{"host", 1500, 231, 25});
		tableModel.addRow(new Object[]{"Player 1", 1100, 132, 14});
		statsTable = new JTable(tableModel);
		statsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		statsTable.setGridColor(Color.BLUE);
		statsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		statsTable.setSelectionBackground(Color.GRAY);
		JScrollPane jsp = new JScrollPane(statsTable);
		centerPanel.add(jsp);
	}//end of creating table
	
	public void addRow(){
		
	}//adding row to table
}//end of class
