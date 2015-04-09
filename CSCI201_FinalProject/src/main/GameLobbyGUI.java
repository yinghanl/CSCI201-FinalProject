package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class GameLobbyGUI extends JFrame {
	private JTabbedPane mainPane;
	private TabPanel easyPanel;
	private TabPanel mediumPanel;
	private TabPanel hardPanel;
	
	public GameLobbyGUI(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setSize(600, 600);
		setLocation (0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initializeComponents();
		createGUI();
		setVisible(true);
	}
	
	private void initializeComponents(){
		mainPane = new JTabbedPane();
		easyPanel = new TabPanel();
		mediumPanel = new TabPanel();
		hardPanel = new TabPanel();
	}
	
	private void createGUI(){
		setLayout(new BorderLayout());
		
		getContentPane().add(mainPane);
		mainPane.add("EASY",easyPanel);
		mainPane.add("MEDIUM", mediumPanel);
		mainPane.add("HARD", hardPanel);
		
	}
	
	public static void main(String [] args){
		new GameLobbyGUI();
	}
}
