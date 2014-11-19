package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import View.*;
import Model.*;

public class LoginController {
	//CLASSES
	private Surgery s;
	private LoginGUI lGUI;
	private Doctor d;
	
	public LoginController(Surgery s, LoginGUI lGUI){
		this.s = s;
		this.lGUI = lGUI;
		
	}
	
	//ACTION LIESTENER / ACTION PERFORMED
	public void loginButton(){
		//LOGIN BUTTON FUNCTION
		lGUI.getLogin().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(lGUI.getUsername().getText());
				String pass = lGUI.getPassword().getText();

				Login l = new Login(id, pass, s);
				d = l.returnDoctor();

				if (d == null)
				{
					JOptionPane.showMessageDialog(null, "Unauthorized Access!!");
				}
				else
				{
					DoctorGUI dGUI = new DoctorGUI(d);

					//Disposing the Window after the pressed
					lGUI.dispose();
				}
			}
		});
	}
	
	public void cancelButton(){
		//CANCEL BUTTON FUNCITON
		lGUI.getCancel().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				lGUI.SYSTEM_EXIT();
			}
		});
	}
}
