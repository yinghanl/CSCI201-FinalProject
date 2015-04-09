package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HomeScreen extends JFrame {
	private JButton play;
	private JButton profile;
	private JTextArea credits;
	private JPanel buttonPanel;
	
	public HomeScreen()
	{
		super ("Home");
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		this.play = new JButton("Play now");
		play.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//open to game lobby page
				setVisible(false);
				//lobbyWindow lw = new lobbyWindow();
			}
			
		});
		this.profile = new JButton("Profile");
		profile.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				ProfileScreen ps = new ProfileScreen();
			}
			
		});
		
		this.credits = new JTextArea();
		JScrollPane creditPane = new JScrollPane(credits);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(play);
		buttonPanel.add(profile);
		
		add(creditPane, BorderLayout.WEST);
		add(buttonPanel, BorderLayout.EAST);
		
		setVisible(true);
	}
	
	public static void main (String[] args)
	{
		HomeScreen hs = new HomeScreen();
	}
	
	

}
