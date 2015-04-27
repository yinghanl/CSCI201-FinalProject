package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class changePasswordWindow extends JFrame
{	
	private JLabel oldPass = new JLabel("Enter current password");
	private JLabel newPass = new JLabel("Enter a new password");
	private JLabel confirm = new JLabel("Confirm new password");
	private JPasswordField oldPassField = new JPasswordField(20);
	private JPasswordField newPassField = new JPasswordField(20);
	private JPasswordField confirmField = new JPasswordField(20);
	private JButton submit = new JButton ("Submit");
	private User u;
	private JFrame parentFrame;
	
	public changePasswordWindow(User u,ProfileScreen parentFrame)
	{
		super ("Change your password");
		setSize(450,200);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.u = u;
		this.parentFrame = parentFrame;
		
		JPanel oldPanel = new JPanel();
		oldPanel.add(oldPass); oldPanel.add(oldPassField);
			
		JPanel newPanel = new JPanel();
		newPanel.add(newPass); newPanel.add(newPassField);
		
		JPanel confirmPanel = new JPanel();
		confirmPanel.add(confirm); confirmPanel.add(confirmField);
		
		confirmField.addActionListener(new ChangePasswordListener());
		submit.addActionListener(new ChangePasswordListener());
		
		add(oldPanel);
		add(Box.createGlue());
		add(newPanel);
		add(Box.createGlue());
		add(confirmPanel);
		add(Box.createGlue());
		add(submit);
		
		
		
		setVisible(true);
	}
class ChangePasswordListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//verify the user entered the correct current password
			if (!DataBaseUtils.verifyUser(u.getUsername(), oldPassField.getPassword() ))
			{
				//if the name/password can't be verified
				//System.out.println("Username / Password can't be verified");
				JOptionPane.showMessageDialog(null, "Incorrect original password");
			}
			
			//verify that the two new passwords are the same
			else if (! Arrays.equals(newPassField.getPassword(), confirmField.getPassword()))
			{
				//if the two passwords don't match
				//System.out.println("Passwords don't match");
				JOptionPane.showMessageDialog(null, "New passwords do not match");
				
			}
			else
			{
				//if the user is verified and the passwords match, set the new password
				int userID = DataBaseUtils.getUserID(u.getUsername());
				DataBaseUtils.changePassword(userID, newPassField.getPassword());
				JOptionPane.showMessageDialog(null, "Password sucessfully changed!");
				//System.out.println("Password reset");
				dispose();
				
			}
			
		}
	}
	
}