package View;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Model.*;

public class PatientHistoryGUI extends JFrame{
	//Class
	private PatientHistory pH;
	
	//Private Variable
	private Date visitDate;
	
	//GUI Components
	private JLabel drIdLabel, visitDateLabel = new JLabel("Visit Date : "),  medsLabel = new JLabel("Medicine : "), pIdLabel = new JLabel("Patient's ID: "), descriptionLabel = new JLabel("Description : "), procedureLabel = new JLabel("Procedure :");;
	private JTextField userInput[] = new JTextField[3];
	private JTextArea userInputArea[] = new JTextArea[3];
	private JPanel mainPanel = new JPanel(new GridLayout(7,2));
	
	
	public PatientHistoryGUI(PatientHistory patientHistory){
		super("Patient History");
		pH = patientHistory;
		
		//GUI Components & Create Layout
		mainPanel.setBorder(BorderFactory.createTitledBorder(null, pH.getVisitDate(), TitledBorder.LEFT, TitledBorder.TOP, new Font("Comic Sans MS", Font.ITALIC, 25)));
		createPatientHistoryLayout();

		//JFRAME
		add(mainPanel);
		setSize(500, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	private void createPatientHistoryLayout(){
		//Initializing JLabel, JTextField and JTextArea
		drIdLabel = new JLabel("Doctor ID : ");
		//Creating JTextField & JTextArea
		userInput[0] = new JTextField();
		userInput[1] = new JTextField();
		userInput[2] = new JTextField();
		userInputArea[0] = new JTextArea();
		userInputArea[1] = new JTextArea();
		userInputArea[2] = new JTextArea();
		
		// Set Editable to false for TextArea And TextField
		for (int i = 0; i < userInput.length; i++)
		{
			userInput[i].setEditable(false);
			userInputArea[i].setEditable(false);	
		}
		
		//Set Value in Array
		userInput[0].setText(pH.getVisitDate());
		userInput[1].setText(pH.getDoctorId() + "");
		userInput[2].setText(pH.getPatientId() + "");
		userInputArea[0].setText(pH.getMedicine());
		userInputArea[1].setText(pH.getDescription());
		userInputArea[2].setText(pH.getProcedure());
		
		//Add Label Into mainPanel
		mainPanel.add(visitDateLabel);
		mainPanel.add(userInput[0]);
		mainPanel.add(drIdLabel);
		mainPanel.add(userInput[2]);
		mainPanel.add(pIdLabel);
		mainPanel.add(userInput[1]);
		
		mainPanel.add(medsLabel);
		mainPanel.add(userInputArea[0]);
		mainPanel.add(descriptionLabel);
		mainPanel.add(userInputArea[1]);
		mainPanel.add(procedureLabel);
		mainPanel.add(userInputArea[2]);	
	}

}
