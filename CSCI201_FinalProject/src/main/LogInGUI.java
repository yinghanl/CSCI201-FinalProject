package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import main.DataBaseUtils;
import main.GameLobbyGUI;
import main.User;
import main.LogInGUI.UserLoginActionListener;

public class LogInGUI extends JFrame
{
	JTextField usernameJTF;
	JPasswordField passwordJPF;
	
	JButton userLogInButton;
	JButton guestLogInButton;
	JButton createUserButton;
	
	JLabel warningLabel;
	
	JPanel backgroundPanel;
	
	LogInGUI self;
	
	public LogInGUI()
	{
		
		UIManager.put("nimbusBase", new Color(25,25,112));
		UIManager.put("nimbusBlueGrey", new Color(46,139,87));
		//UIManager.put("nimbusBlueGrey", new Color(204,240,248));
		UIManager.put("control",new Color(204,240,248));
		//UIManager.put("control", new Color(240,255,240));

		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		instantiateComponents();
		createGUI();
		addActionListeners();
		
		setVisible(true);
	}
	
	private void instantiateComponents()
	{
		self = this;
		
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
		
		backgroundPanel = null;
		
		
	}
	
	private void createGUI()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		setSize((int)(width * 0.8), (int)(height * 0.8));
		setLocation(100, 100);
		
		backgroundPanel = new BGPanel();
		backgroundPanel.setLayout(new BorderLayout());

		JPanel contentPanel = new LoginPanel();
		FlowLayout bgPanelLayout = new FlowLayout(FlowLayout.CENTER);
		bgPanelLayout.setVgap((int)(height *.6));
		backgroundPanel.setLayout(bgPanelLayout);
		backgroundPanel.add(contentPanel);
		setContentPane(backgroundPanel);
	}
	
	private void addActionListeners()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		userLogInButton.addActionListener(new UserLoginActionListener());
		passwordJPF.addActionListener(new UserLoginActionListener());
		
		guestLogInButton.addActionListener(new ActionListener()
		{
			//creates guest user and logs in
			public void actionPerformed(ActionEvent ae)
			{
				Guest newGuest = DataBaseUtils.createGuest();
				passwordJPF.setText("");
				usernameJTF.setText("");
				new GameLobbyGUI(newGuest,self);
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
					passwordJPF.setText("");
					usernameJTF.setText("");
					new GameLobbyGUI(newUser,self);
					setVisible(false);
				}
				else{
					JOptionPane.showMessageDialog(null, "Username already exists, try again!");
				}
				//calls DatabaseUtils.createNewUser
				//if true, creates new user and logs in
				//else makes warning label appear
			}
		});
		
		
	}
	
	class BGPanel extends JPanel{
		Image bg = new ImageIcon("images/loginImage.jpg").getImage();
		
		public void paintComponent(Graphics g){
			g.drawImage(bg,0,0,getWidth(),getHeight(),this);
		}
	}
	
	class LoginPanel extends JPanel{
		LoginPanel(){
			setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			
			JPanel usernameLine = new JPanel();
			JPanel passwordLine = new JPanel();
			JPanel buttonLine = new JPanel();
			
			usernameLine.setLayout(new BoxLayout(usernameLine,BoxLayout.X_AXIS));
			passwordLine.setLayout(new BoxLayout(passwordLine,BoxLayout.X_AXIS));
			buttonLine.setLayout(new BoxLayout(buttonLine,BoxLayout.X_AXIS));
			
			JLabel usernameLabel = new JLabel("USERNAME: ");
			usernameLabel.setForeground(Color.WHITE);
			usernameLabel.setPreferredSize(new Dimension(85,25));
			JLabel passwordLabel = new JLabel("PASSWORD: ");
			passwordLabel.setForeground(Color.WHITE);
			passwordLabel.setPreferredSize(new Dimension(85,25));
			
			usernameLine.add(usernameLabel);
			usernameLine.add(usernameJTF);
			passwordLine.add(passwordLabel);
			passwordLine.add(passwordJPF);
			
			buttonLine.add(createUserButton);
			buttonLine.add(guestLogInButton);
			buttonLine.add(userLogInButton);
			
			add(usernameLine);
			add(passwordLine);
			add(Box.createGlue());
			add(buttonLine);
			
			setOpaque(false);
			usernameLine.setOpaque(false);
			passwordLine.setOpaque(false);
			buttonLine.setOpaque(false);
			
		}
	}
	
	class UserLoginActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae)
		{
			String username = usernameJTF.getText();
			char [] password = passwordJPF.getPassword();
			User newUser;
			if(DataBaseUtils.verifyUser(username, password))
			{
				int userID = DataBaseUtils.getUserID(username);
				newUser = DataBaseUtils.createUser(userID);
				
				new GameLobbyGUI(newUser,self);
				setVisible(false);
				usernameJTF.setText("");
				passwordJPF.setText("");
			}
			else{
				JOptionPane.showMessageDialog(null, "Username password combination does not exist");
			}
		}
	}
	
}
