package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import View.*;
import Model.*;
public class DoctorController {
	//CLASSES
	private Doctor d;
	private DoctorGUI dGUI;
	private Surgery s;
	private SqlDatabasePatient sqlP = SqlDatabasePatient.getInstance();
	private SqlDatabasePatientHistory sqlPH = SqlDatabasePatientHistory.getInstance();
	private SqlDatabaseDoctor sqlD = SqlDatabaseDoctor.getInstance();
	
	//GUI COMPONENTS
	private JTextField userInput[] = new JTextField[3];
	private JTextArea userInputArea[] = new JTextArea[3];
	
	//PRIVATE VARIABLE
	private Date visitDate, dob;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public DoctorController(Doctor d, Surgery s, DoctorGUI dGUI){
		this.d = d;
		this.dGUI = dGUI;
		this.s = s;
	}
	
	public void submitPatientButton(){
		//ADD PATIENT FUNCTION
		dGUI.getSubmitP().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Date
				//GET TEXT FROM USER
				String fName = dGUI.getpFName().getText();
				String lName = dGUI.getpLName().getText();
				String phone = dGUI.getpPhone().getText();
				String address = dGUI.getpAddress().getText();

				try{
					dob = df.parse(dGUI.getpDOB().getText());
				}catch(ParseException a){
					System.out.println("Invalid");
				}

				//CREATE PATIENT & ADD PATIENT INTO ARRAYLIST
				if (!((fName.equals(null) && (lName.equals(null)) && (phone.equals(null)) && (address.equals(null)) && (dob.equals(null)))))
				{
					//Adding patient to the Arraylist 
					Patient newPatient = new Patient(fName, lName, address, phone, dob, d.getPatient().size() + 1);
					d.getPatient().add(newPatient);
					s.getAllPatient().add(newPatient);
					JOptionPane.showMessageDialog(null, "New Patient is added to the list");
					System.out.println( d.getPatient().size() + 1);
					
					//Add Patient into database
					sqlP.addPatient(newPatient.getPatientFName(), newPatient.getPatientLName(), newPatient.getPatientAddrs(), newPatient.getPatientPhone(), dob, d.getDrId(), newPatient.getPatientId());
					
					//Dynamically Added each row for new patient to the rows in the table
					dGUI.getpTModel().add(newPatient);
					dGUI.getaPTModel().add(newPatient);
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Please enter all the data..");
				}
				
				//RESET THE VALUE IN THE JTEXTFIELD AND JTEXTAREA
				dGUI.getpFName().setText(null);
				dGUI.getpLName().setText(null);
				dGUI.getpPhone().setText(null);
				dGUI.getpAddress().setText(null);
				dGUI.getpDOB().setText(null);
			}
		});
	}
	
	public void submitButton(){
		//ADD PATIENT HISTORY FUNCTION
		userInput = dGUI.getUserInput();
		userInputArea = dGUI.getUserInputArea();
		
		dGUI.getSubmit().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int pId = Integer.parseInt(userInput[2].getText());
				String medicine = userInputArea[0].getText();
				String description = userInputArea[1].getText();
				String procedure = userInputArea[2].getText();
				
				//PARSE STRING TO DATE
				try{
					visitDate = df.parse(userInput[0].getText());
					
				}catch(ParseException a){
					System.out.println("Invalid");
				}
				
				//RESET THE VALUES IN JTEXTAREA AND JTEXTFIELD
				for(int i = 0; i < userInputArea.length; i++)
					userInputArea[i].setText(null);
			
				userInput[2].setText(null);
				
				//ADD PATIENT'S HISTORY IN PATIENT CLASS
			
				int index = d.findPatient(pId);
				PatientHistory ph = new PatientHistory(pId, d.getDrId(), medicine, description, procedure, d.getPatient().get(index).getDoctorVisit().size() + 1, visitDate);
				d.getPatient().get(index).doctorVisit(ph);
				
				//INSERT NEW PATIENT HISTORY INTO DATABASE
				sqlPH.addPatientHistory(ph.getPatientId(), ph.getDoctorId(), ph.getMedicine(), ph.getDescription(), ph.getProcedure(), ph.getHistoryId(), visitDate);
				
			}	
		});
	}
	
	public void searchButton(){
		//SEARCH BUTTON FUNCTION
		dGUI.getSearch().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String getName = dGUI.getSearchPatient().getText();
				int index = findAndComparePatient(getName);
				
				if(index == -1)
					JOptionPane.showMessageDialog(null, "No Such Person");
				else
				{
					PatientGUI pGUI = new PatientGUI(d.getPatient().get(index), d);
				
				}
				dGUI.getSearchPatient().setText(null);
			}
		});
	}
	
	public void selectRowAllPatient(){
		//SINGLE SELECTION ALL PATIENT TABLE
		dGUI.getSelectRow().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				
				//ONLY DEALT WITH THE ACTION IN THE FINISH POINT
				if (e.getValueIsAdjusting() == false)
				{
					PatientGUI a = new PatientGUI(s.getAllPatient().get(dGUI.getAllPatient().getSelectedRow()), d);
				}
			}
		});
	}
	
	public void rowSelectionPatient(){
		//SINGLE SELECTION PATIENT TABLES 
		dGUI.getRowSelect().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				//ONLY DEALT WITH THE ACTION IN THE FINISH POINT
				if (e.getValueIsAdjusting() == false)
				{
					PatientGUI a = new PatientGUI(d.getPatient().get(dGUI.getPatientTables().getSelectedRow()), d);
				}
			}
		});
	}
	
	public void logoutButton(){
		//LOGOUT BUTTON FUNCTION
		dGUI.getLogOut().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int warning = JOptionPane.showConfirmDialog(null,"Do you want to Logout?","Logout?", JOptionPane.YES_NO_OPTION);
				if(warning == JOptionPane.YES_OPTION)
				{
					LoginGUI lGUI = new LoginGUI(s);
					dGUI.dispose();
				}
			}
		});	
	}
	
	public void saveToFileButton(){
		//SAVE TO FILE BUTTON FUNCTION
		dGUI.getSaveToFile().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				saveButtonFunciton("Clinic Data.ser");

				int option = JOptionPane.showConfirmDialog(null, "Data has been Saved...\nDo You want copy of backup", "BackUp?", JOptionPane.YES_NO_OPTION);

				if(option == JOptionPane.YES_OPTION)
				{
					saveButtonFunciton("Clinic Data BackUp.ser");
				}
			}
		});
	}
	
	public void backUpAndRestoreButton(){
		//BACKUP AND RESTORE BUTTON FUNCTION
		dGUI.getBackUpAndRestore().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				int warning = JOptionPane.showConfirmDialog(null,"Are you sure You want to load PREVIOUS file without saving??", " ", JOptionPane.YES_NO_OPTION);
				Doctor doc = null;

				if(warning == JOptionPane.YES_OPTION)
				{
					//FILE INPUT STREAM
					try {
						FileInputStream in = new FileInputStream("Clinic Data BackUp.ser");
						ObjectInputStream oIn = new ObjectInputStream(in);
						s = (Surgery) oIn.readObject();
						oIn.close();

					}catch(FileNotFoundException a){
						System.out.println("Backup File Not Found");
					}catch(Exception b){
						System.out.println("Backup System File Not Found!!");
					}

					for (int i = 0; i < s.getDoctors().size(); i++)
						if (s.getDoctors().get(i).getDrId() == d.getDrId())
						{
							doc = s.getDoctors().get(i);
							break;
						}

					new DoctorGUI(doc);
					dGUI.dispose();
				}	
			}
		});
	}
	
	public void addDoctorButton(){
		//ADD DOCTOR BUTTON FUNCTION
		dGUI.getAddDoctor().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				AddDoctorGUI aDGUI = new AddDoctorGUI(d);
			}
		});
	}
	
	public void editButton(){
		//EDIT BUTTON FUNCTION
		dGUI.getEditInfo().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(dGUI.getDrName().isEditable() == false)
				{
					dGUI.getEditInfo().setText("Done");
					editInfoButtonFunction();
				}
				else if(dGUI.getDrName().isEditable() == true)
				{
					dGUI.getEditInfo().setText("Edit Function");
					doneButtonFunction();
				}
			}
		});
	}
	
	//FIND AND COMPARE PATIENT
		private int findAndComparePatient(String name){
			int index = -1;	
			
			for (int i = 0; i < d.getPatient().size(); i++)
			{
				if (name.equalsIgnoreCase(d.getPatient().get(i).getPatientFName() + " "+ d.getPatient().get(i).getPatientLName()))
				{
					index = i;
					break;
				}	
			}
			return index;
		}
	
	//SAVE TO FILE BUTTON FUNCTION
	private void saveButtonFunciton(String fileName){
		try
		{
			FileOutputStream out = new FileOutputStream(fileName);
			ObjectOutputStream o = new ObjectOutputStream(out);
			o.writeObject(s);
			o.close();
			out.close();

		}catch(IOException i){
			i.printStackTrace();
		}
	}

	//EDIT INFO BUTTON FUNCTION
	private void editInfoButtonFunction(){
		dGUI.getDrName().setEditable(true);
		dGUI.getNewPass().setEditable(true);
	}

	//DONE BUTTON FUNCITON
	private void doneButtonFunction(){
		d.setDrName(dGUI.getDrName().getText());
		d.setDocPasswd(dGUI.getNewPass().getText());

		//UPDATE DOCTOR INFO IN DATABASE
		sqlD.updateDoctor(d.getDrName(), d.getDocPasswd(), d.getDrId());
		
		dGUI.getDrName().setEditable(false);	
		dGUI.getNewPass().setEditable(false);
		dGUI.getNewPass().setText(null);
	}
}
