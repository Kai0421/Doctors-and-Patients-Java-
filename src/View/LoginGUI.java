package View;
import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Model.*;
import Controller.*;

public class LoginGUI extends JFrame{
	
	//Classes
	private Surgery s;
	private LoginController lC;
	
	//GUI Components
	private JPanel panel = new JPanel(new GridBagLayout());
	private JTextField username = new JTextField(10);
	private JPasswordField password = new JPasswordField(10);
	private GridBagConstraints gbc;
	
	//Button
	private JButton login = new JButton("Login"), cancel = new JButton("Cancel");
	
	public  LoginGUI(Surgery s){
	super("Login");
		this.s = s;
		lC = new LoginController(s, this);
		
		//GUI Components & Create Layout
		add(panel);
		createLoginLayout();
		
		//JFRAME
		setSize(300, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void createLoginLayout(){
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 10, 10, 10);
		JLabel usernameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		
		//Position the GUI Component
		panel.add(usernameLabel);
			gbc.gridx = 0;
			gbc.gridy = 0;
			panel.add(usernameLabel, gbc);
			
		panel.add(username);
			gbc.gridx = 1;
			gbc.gridy = 0;
			panel.add(username, gbc);	
			
		panel.add(passwordLabel);
			gbc.gridx = 0;
			gbc.gridy = 1;
			panel.add(passwordLabel, gbc);
			
		panel.add(password);
			gbc.gridx = 1;
			gbc.gridy = 1;
			panel.add(password, gbc);
		
		panel.add(login);
			gbc.gridx = 0;
			gbc.gridy = 2;
			panel.add(login, gbc);
			
		panel.add(cancel);
			gbc.gridx = 1;
			gbc.gridy = 2;
			panel.add(cancel, gbc);
			
		//BUTTON FUNCTIONS IN CONTROLLER CLASS(LoginController.java)
		lC.loginButton();
		lC.cancelButton();
	}
	
	public final void SYSTEM_EXIT(){
		System.exit(EXIT_ON_CLOSE);
	}
	
	/*************************************************************** Getter & setter ****************************************************/
	public JTextField getUsername() {
		return username;
	}
	
	public JPasswordField getPassword() {
		return password;
	}
	
	public JButton getLogin() {
		return login;
	}
	
	public JButton getCancel() {
		return cancel;
	}
}
