package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ProfileScreen extends JFrame{
	private JButton change_pass;
	private JPanel user_stats;
	
	public ProfileScreen()
	{
		super ("Profile");
		setSize(200,200);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		change_pass = new JButton("Change Password");
		change_pass.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
			//allows user to change their password
			
				changePasswordWindow cpw = new changePasswordWindow();
				
			}
		});
		
		//display user's history of achievements
		user_stats = new JPanel();
		add(change_pass, BorderLayout.NORTH);
		add(user_stats, BorderLayout.CENTER);
		
		
		setVisible(true);
	}
	
}

