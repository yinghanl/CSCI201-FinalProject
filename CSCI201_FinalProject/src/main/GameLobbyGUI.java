package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class GameLobbyGUI extends JFrame {

	private static final long serialVersionUID = 1;
	private AbstractUser user;
	private TabPanel lobby;
	ProfileScreen ps;
	private JButton profileButton;
	public static ArrayList<Game> gamesOpen;
	private LogInGUI parentFrame;
	private GameLobbyGUI self;
	
	public GameLobbyGUI(AbstractUser u,LogInGUI parentFrame){
		super("Game Lobby");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setSize((int)(width*.4), (int)(height));
		setLocation (0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.user = u;
		this.parentFrame = parentFrame;
		gamesOpen = new ArrayList<Game>();
		self = this;
		
		initializeComponents();
		createGUI();
		setVisible(true);
	}
	
	private void initializeComponents(){
		lobby = new TabPanel(user, this, parentFrame);
		if(user instanceof User)
		{
			ps = new ProfileScreen((User)user);
			ps.setVisible(false);
		}
		
		this.profileButton = new JButton("View Player Statistics & Profile Options");
	}
	
	private void createGUI(){
		setLayout(new BorderLayout());
		
		if(!user.isUser())
		{
			profileButton.setVisible(false);
		}
		profileButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				ps.setVisible(true);
			}
			
		});
		
		add(lobby);
		
		add(profileButton, BorderLayout.NORTH);
		
	}
	
	
}
