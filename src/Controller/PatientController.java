package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import View.*;
import Model.*;

public class PatientController {
	//CLASSES
	private Patient p;
	private Doctor d;
	private PatientGUI pGUI;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private SqlDatabasePatient sqlP = SqlDatabasePatient.getInstance();
	
	//GUI COMPONENTS
	private JTextField userInput[] = new JTextField[6];
	
	public PatientController(Patient p, PatientGUI pGUI, Doctor d){
		this.p = p;
		this.pGUI = pGUI;
		this.d = d;
	
		//Create JTextField
		for (JTextField e: userInput)
		{
			e = new JTextField();
			e.setEditable(false);
		}
		userInput = pGUI.getUserInput();
	}
	
	public void rowSelection(){
		
		pGUI.getRowSelect().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				//ONLY DEALT WITH THE ACTION IN THE FINISH POINT
				if (e.getValueIsAdjusting() == false)
				{
					PatientHistoryGUI pHGUI = new PatientHistoryGUI(p.getDoctorVisit().get(pGUI.getTable().getSelectedRow()));
				}
			}
		});
	}
	
	public void reportButton(){
		//REPORT BUTTON FUNCTION
		pGUI.getReport().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame reportFrame = new JFrame("Report - " + p.getPatientFName() + " " + p.getPatientLName());
				JTextArea reportArea = new JTextArea();
				JScrollPane reportScroll = new JScrollPane(reportArea);
				reportArea.setEditable(false);
				String reportInString = "";
				for(int i = 0; i < p.getDoctorVisit().size(); i++)
				{
					reportInString += p.getDoctorVisit().get(i).toString() + "\n";
				}

				reportArea.setText(reportInString);
				reportFrame.add(reportScroll);
				reportFrame.setVisible(true);
				reportFrame.setLocationRelativeTo(null);
				reportFrame.setResizable(true);
				reportFrame.setSize(500, 500);
			}
		});	
	}
	
	public void editButton(){
		//EDIT BUTTON FUNCITON
		pGUI.getEdit().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(userInput[1].isEditable() == false)
				{
					editButtonFunction();
					pGUI.getEdit().setText("Done");
				}
				else if(userInput[1].isEditable() == true)
				{
					try {
						doneButtonFunction();
					} catch (ParseException pE) {
						// TODO Auto-generated catch block
						pE.printStackTrace();
					}
					pGUI.getEdit().setText("Edit Patient Info");
				}
			}
		});
	}
	
	//EDIT BUTTON FUNCTION
	private void editButtonFunction(){
		for(int i = 1; i < userInput.length; i++)
		{
			userInput[i].setEditable(true); 
		}
	}
	
	//DONE BUTTON FUNCTION
	private void doneButtonFunction() throws ParseException{
		
		p.setPatientFName(userInput[1].getText());
		p.setPatientLName(userInput[2].getText());
		p.setPatientPhone(userInput[3].getText());
		p.setPatientDOB(df.parse(userInput[4].getText()));
		p.setPatientAddrs(userInput[5].getText());
	
		p.print();
		d.print();
		//System.out.println(p.getPatientFName() + p.getPatientLName() + p.getPatientAddrs() + p.getPatientPhone() + df.parse(userInput[4].getText()) + d.getDrId() + p.getPatientId());
		
		//UPDATE PATIENT INFO IN DATABASE
		sqlP.updatePatient(p.getPatientId(), p.getPatientFName(), p.getPatientLName(), p.getPatientAddrs(), df.parse(p.getPatientDOB()), d.getDrId(), p.getPatientPhone());
		
		for(int i = 0; i < userInput.length; i++)
		{
			userInput[i].setEditable(false);
		}
		
	}
}
