package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;


public class ProfileScreen extends JFrame{
	private JButton change_pass;
	private JPanel user_stats_panel;
	private User u;
	private JTable userStats;
	
	public ProfileScreen(User u)
	{
		super ("Profile");
		setSize(200,200);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.u = u;
		
		change_pass = new JButton("Change Password");
		change_pass.addActionListener(new OpenPasswordWindow(this));
		
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
		userStats = new JTable(tableData, columnNames);
		user_stats_panel.add(userStats);
		
		add(change_pass, BorderLayout.NORTH);
		add(user_stats_panel, BorderLayout.CENTER);
		
		
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

