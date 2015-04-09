import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class changePasswordWindow extends JFrame
{	
	private JLabel oldPass = new JLabel("Enter current password");
	private JLabel newPass = new JLabel("Enter a new password");
	private JLabel confirm = new JLabel("Confirm new password");
	private JTextField oldPassField = new JTextField(20);
	private JTextField newPassField = new JTextField(20);
	private JTextField confirmField = new JTextField(20);
	private JButton submit = new JButton ("Submit");
	
	
	
	public changePasswordWindow()
	{
		super ("Change your password");
		setSize(450,200);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel oldPanel = new JPanel();
		oldPanel.add(oldPass); oldPanel.add(oldPassField);
			
		JPanel newPanel = new JPanel();
		newPanel.add(newPass); newPanel.add(newPassField);
		
		JPanel confirmPanel = new JPanel();
		confirmPanel.add(confirm); confirmPanel.add(confirmField);
		
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//u.changePassword();
			}
			
		});
		
		add(oldPanel);
		add(Box.createGlue());
		add(newPanel);
		add(Box.createGlue());
		add(confirmPanel);
		add(Box.createGlue());
		add(submit);
		
		
		
		setVisible(true);
	}
}
