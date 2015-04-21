package main;

import java.awt.BorderLayout;
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

	private JButton profileButton;
	public static ArrayList<Game> gamesOpen;
	
	public GameLobbyGUI(AbstractUser u){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setSize(600, 600);
		setLocation (0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.user = u;
		gamesOpen = new ArrayList<Game>();
		
		initializeComponents();
		createGUI();
		setVisible(true);
	}
	
	private void initializeComponents(){
		lobby = new TabPanel(user, this);
	}
	
	private void createGUI(){
		setLayout(new BorderLayout());
		
		this.profileButton = new JButton("Profile");
		if(!user.isUser())
		{
			profileButton.setVisible(false);
		}
		profileButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				ProfileScreen ps = new ProfileScreen((User)user);
			}
			
		});
		
		getContentPane().add(lobby);
		
		add(profileButton, BorderLayout.SOUTH);
		
	}
	
	public static void main(String [] args){
		//Player p = new Player("awesome");
		//new GameLobbyGUI(user);
	}
	
	
}
