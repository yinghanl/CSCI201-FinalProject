package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class PostGameGUI extends JFrame{
	private JTable statsTable;
	private DefaultTableModel tableModel;
	private JLabel gameoutcomeLabel;
	private JPanel centerPanel;
	private JPanel topPanel;
	private JButton exitButton;
	private boolean gameWon;
	
	private Vector<GameStats> gsVector;
	private GameLobbyGUI glw;
	
	public PostGameGUI(Vector<GameStats> gsVector, GameLobbyGUI glw){
		super("End of Game Statistics");
		if(gsVector.get(0).getGameResult() == true){
			gameWon = true;
		}
		else{
			gameWon = false;
		}
		this.glw = glw;
		this.gsVector = gsVector;
		instantiateComponents();
		createGUI();
		addActionListeners();
		
		setVisible(true);
	}//end of constructor
	
	public void addRow(){
		
	}//adding row to table
	
	public void instantiateComponents()
	{
		exitButton = new JButton("EXIT TO GAMELOBBY");
		topPanel = new JPanel();
		centerPanel = new JPanel();
		if(gameWon == true){
			gameoutcomeLabel = new JLabel("Congratulations!");
		}
		else{	
			gameoutcomeLabel = new JLabel("YOU LOSE!!!!!!!!");
		}
	}
	
	public void createGUI()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		setSize((int)(width * 0.8), (int)(height * 0.8));
		setLocation(100, 100);
		
		JPanel bgPanel = null;
		if(gameWon == true){
			bgPanel = new BGPanel("images/PostGameBackground.png");
		}else{
			bgPanel = new BGPanel("images/PostGameLose.jpg");
		}
		
		bgPanel.setLayout(new BorderLayout());

		gameoutcomeLabel.setHorizontalAlignment(JLabel.CENTER);
		gameoutcomeLabel.setFont(new Font("Jokerman",Font.ITALIC,64));
		if(gameWon){
			gameoutcomeLabel.setForeground(new Color(255,215,0));
		}else{ 
			gameoutcomeLabel.setForeground(Color.RED);
			gameoutcomeLabel.setFont(new Font("Forte",Font.ITALIC,64));
		}
		JPanel userPanel1 = userInfoPanel(gsVector.get(0));
		JPanel userPanel2 = userInfoPanel(gsVector.get(1));
		
		
		JPanel userPanel = new JPanel(new FlowLayout());
		userPanel.add(userPanel1);
		userPanel.add(userPanel2);
		userPanel.setOpaque(false);
		
		bgPanel.add(gameoutcomeLabel,BorderLayout.NORTH);
		bgPanel.add(userPanel,BorderLayout.CENTER);
		bgPanel.add(exitButton,BorderLayout.SOUTH);
		
		bgPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		
		setContentPane(bgPanel);
		
	}
	
	public void addActionListeners()
	{
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				glw.setVisible(true);
				PostGameGUI.this.dispose();
			}
		});
		addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				glw.setVisible(true);
				PostGameGUI.this.dispose();
			}
		});
		
	}
	
	public JPanel userInfoPanel(GameStats gs){
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new BoxLayout(toReturn,BoxLayout.Y_AXIS));
		AbstractUser tempUser = gs.getAbstractUser();
		
		JLabel nameLabel = new JLabel(tempUser.getUsername() + "'s Statistics");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Casteller", Font.BOLD,32));
		if(gameWon == true){
			nameLabel.setForeground(new Color(255,215,0));
		}else{
			nameLabel.setForeground(Color.RED);
		}
		
		nameLabel.setOpaque(false);
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("images/GoldIcon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image temp = image.getScaledInstance(25, 25,Image.SCALE_SMOOTH);
		ImageIcon goldIcon = new ImageIcon(temp);
		
		JPanel goldPanel = new JPanel();
		goldPanel.setLayout(new FlowLayout());
		goldPanel.setOpaque(false);
		goldPanel.add(new JLabel(goldIcon));
		JLabel goldPanelText = new JLabel(" Collected: " + gs.getGold());
		if (gameWon) goldPanelText.setForeground(Color.white);
		else goldPanelText.setForeground(Color.black);
		goldPanelText.setFont(new Font("Casteller", Font.ITALIC,24));
		goldPanel.add(goldPanelText);
		
		JPanel creepPanel = new JPanel();
		creepPanel.setOpaque(false);
		creepPanel.setLayout(new FlowLayout());
		creepPanel.add(new JLabel(new ImageIcon("images/Creep.png")));
		JLabel creepPanelText = new JLabel(" Killed: " + gs.getCreepsKilled());
		if(gameWon) creepPanelText.setForeground(Color.white);
		else creepPanelText.setForeground(Color.black);
		creepPanelText.setFont(new Font("Casteller", Font.ITALIC,24));
		creepPanel.add(creepPanelText);
		
		toReturn.add(nameLabel);
		toReturn.add(goldPanel);
		toReturn.add(creepPanel);
		
		toReturn.setOpaque(false);
		toReturn.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		
		return toReturn;
	}
	
}//end of class

class BGPanel extends JPanel{
	Image bg;
	
	BGPanel(String fileName){
		bg = new ImageIcon(fileName).getImage();
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(bg,0,0,getWidth(),getHeight(),this);
	}
}
