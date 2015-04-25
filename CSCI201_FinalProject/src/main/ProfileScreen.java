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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class ProfileScreen extends JFrame{
	private JButton change_pass;
	private JLabel PlayerLabel;
	private JPanel user_stats_panel;
	private User u;
	private JTable userStats;
	
	public ProfileScreen(User u)
	{
		super (u.getUsername() + "'s Profile");
		setSize(200,150);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.u = u;
		
		change_pass = new JButton("Change Password");
		change_pass.addActionListener(new OpenPasswordWindow(this));
		
		PlayerLabel = new JLabel(u.getUsername() + "'s statistics", SwingConstants.CENTER);
		
		//display user's history of achievements
		user_stats_panel = new JPanel();
		String[] columnNames = {"Achievements", "Score"};
		int[] userData = u.getUserData();
		Object[][] tableData= { 
				{"Creeps Killed", userData[0] },
				{"Gold Earned", userData[1] },
				{"Games Played", userData[2] },
				{"Games Won", userData[3] },
				{"Games Lost", userData[4] },
		};
		DefaultTableModel userStatsModel = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		
		userStats = new JTable(userStatsModel);
		user_stats_panel.add(userStats);
		
		add(change_pass, BorderLayout.SOUTH);
		add(userStats, BorderLayout.CENTER);
		add(PlayerLabel,BorderLayout.NORTH);
		
		
		setVisible(true);
	}
	
	class OpenPasswordWindow implements ActionListener{
		ProfileScreen parentReference;
		OpenPasswordWindow(ProfileScreen p){
			parentReference = p;
		}
		public void actionPerformed(ActionEvent e) {
		//allows user to change their password
			changePasswordWindow cpw = new changePasswordWindow(u,parentReference);
			
		}

	}
	
}

