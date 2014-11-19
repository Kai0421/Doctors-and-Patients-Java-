package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Model.*;
import View.*;

public class AddDoctorController {
	//CLASSES
	private Doctor d;
	private Surgery s = Surgery.getInstance();
	private AddDoctorGUI aDGUI;
	private SqlDatabaseDoctor sqlD = SqlDatabaseDoctor.getInstance();
	
	public AddDoctorController(AddDoctorGUI aDGUI){
		this.aDGUI = aDGUI;
	}
	
	public void addDoctorButton(){
		
		 aDGUI.getAddDoctorButton().addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				String getName = aDGUI.getTextField().getText();
				String getPass = aDGUI.getPass().getText();
				
				if ((aDGUI.getTextField().getText() == null) || (aDGUI.getPass().getText() == null))
				{
					int warning = JOptionPane.showConfirmDialog(null,"Please enter all the info.?","", JOptionPane.OK_OPTION);
				}
				else 
				{
					Doctor newDoc = new Doctor(getName, getPass, s.getDoctors().size());
					s.getDoctors().add(newDoc);
					aDGUI.getdTModel().add(newDoc);
					
					//Add to the database
					sqlD.addDoctor(newDoc.getDrId(), newDoc.getDrName(), newDoc.getDocPasswd());
					
					//Clear Text Fields
					aDGUI.getTextField().setText(null);
					aDGUI.getPass().setText(null);
				}
			}
		});
		
	}
	
}
