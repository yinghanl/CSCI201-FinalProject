//package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogInGUI extends JFrame
{

	JTextField usernameJTF;
	JPasswordField passwordJPF;
	
	JButton userLogInButton;
	JButton guestLogInButton;
	JButton createUserButton;
	
	JLabel warningLabel;
	
	private void instantiateComponents()
	{
		
		usernameJTF = new JTextField(20);
		passwordJPF = new JPasswordField(20);
		
		userLogInButton = new JButton("Log in as User");
		userLogInButton.setPreferredSize(new Dimension(200, 40));
		guestLogInButton = new JButton("Log in as Guest");
		guestLogInButton.setPreferredSize(new Dimension(200, 40));
		createUserButton = new JButton("Create new User");
		createUserButton.setPreferredSize(new Dimension(200, 40));
		
		warningLabel = new JLabel("The username or password is incorrect");
		warningLabel.setForeground(Color.red);
		
		
	}
	
	private void createGUI()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		setSize((int)(width * 0.8), (int)(height * 0.8));
		setLocation(100, 100);
		
		JPanel northPanel = new JPanel();
		JLabel tdLabel = new JLabel("Tower Defense");
		tdLabel.setFont(new Font("Arial", Font.BOLD, 30));
		northPanel.add(tdLabel);
		
		JPanel centerPanel = new JPanel();
		//centerPanel.add(new JLabel(new ImageIcon("usc.gif")));
		//centerPanel.add(new JLabel(new ImageIcon("usc.gif")));
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		southPanel.setPreferredSize(new Dimension((int)(width * 0.4), (int)(height * 0.3)));
		JPanel swPanel = new JPanel();
		swPanel.setLayout(new BoxLayout(swPanel, BoxLayout.Y_AXIS));
		JPanel sw1Panel = new JPanel();
		JPanel sw2Panel = new JPanel();
		sw1Panel.add(new JLabel("username:"));
		sw1Panel.add(usernameJTF);
		sw2Panel.add(new JLabel("password:"));
		sw2Panel.add(passwordJPF);
		swPanel.add(Box.createGlue());
		swPanel.add(sw1Panel);
		swPanel.add(sw2Panel);
		swPanel.add(Box.createGlue());
		southPanel.add(swPanel);
		
		JPanel sePanel = new JPanel();
		sePanel.setLayout(new BoxLayout(sePanel, BoxLayout.Y_AXIS));
		sePanel.add(Box.createGlue());
		sePanel.add(userLogInButton);
		sePanel.add(Box.createGlue());
		sePanel.add(guestLogInButton);
		sePanel.add(Box.createGlue());
		sePanel.add(createUserButton);
		sePanel.add(Box.createGlue());
		southPanel.add(sePanel);
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		
	}
	
	private void addActionListeners()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		userLogInButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String username = usernameJTF.getText();
				char [] password = passwordJPF.getPassword();
				User newUser;
				if(DataBaseUtils.verifyUser(username, password))
				{
					int userID = DataBaseUtils.getUserID(username);
					newUser = DataBaseUtils.createUser(userID);
					new GameLobbyGUI(newUser);
					setVisible(false);
				}
			}
		});
		guestLogInButton.addActionListener(new ActionListener()
		{
			//creates guest user and logs in
			public void actionPerformed(ActionEvent ae)
			{
				Guest newGuest = DataBaseUtils.createGuest();
				new GameLobbyGUI(newGuest);
				setVisible(false);
				
				//User newUser = DataBaseUtils.createGuest();
				//new Homescreen(newUser);
				
			}
		});
		createUserButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String username = usernameJTF.getText();
				char [] password = passwordJPF.getPassword();
				if(!DataBaseUtils.verifyUser(username, password))
				{
					int userID = DataBaseUtils.createNewUser(username, password);
					User newUser = DataBaseUtils.createUser(userID);
					new GameLobbyGUI(newUser);
					setVisible(false);
				}
				//calls DatabaseUtils.createNewUser
				//if true, creates new user and logs in
				//else makes warning label appear
			}
		});
		
		
	}
	
	public LogInGUI()
	{
		instantiateComponents();
		createGUI();
		addActionListeners();
		
		setVisible(true);
	}
	
	
	
}
