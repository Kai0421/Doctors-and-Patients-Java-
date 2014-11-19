package View;

import Model.*;
import Controller.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.html.HTMLDocument.Iterator;

import Model.*;

public class DoctorGUI extends JFrame implements Serializable{
	// Class
	private Doctor d = null;
	private Surgery s = Surgery.getInstance();
	private PatientTableModel pTModel, aPTModel;
	private DoctorController dC;
	private ListSelectionModel selectRow;
	private ListSelectionModel rowSelect; 
	
	//Private Variables 
	private final String[] title = {"Patient No.", "First Name", "Last Name"};
	private Date visitDate, dob;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	//Private GUI Component
	//Label And TextArea
	private JLabel drIdLabel= new JLabel("Doctor ID No. : "), drNameLabel  = new JLabel("Dr. Name : "), password = new JLabel("Password :"), newPassword = new JLabel("New Password :");
	private JTextField drId = new JTextField(), drName = new JTextField();
	private JPasswordField currentPass = new JPasswordField(), newPass = new JPasswordField();
	private JTextField pFName = new JTextField(10), pLName = new JTextField(10), pDOB = new JTextField(10), pPhone = new JTextField(10),searchPatient = new JTextField(10);
	private JTextArea pAddress = new JTextArea(5, 20); 
	private JTable patientTables, allPatient;
	private JScrollPane scroller, allPatientScoller;
	private GridBagConstraints gbc;
	private JLabel visitDateLabel = new JLabel("Visit Date : "), medsLabel = new JLabel("Medicine : "), pIdLabel = new JLabel("Patient's ID: "), descriptionLabel = new JLabel("Description : "), procedureLabel = new JLabel("Procedure :");
	private JTextField userInput[] = new JTextField[3];
	private JTextArea userInputArea[] = new JTextArea[3];

	//Button
	private JButton submit = new JButton("Submit"), logOut = new JButton("LogOut"), search = new JButton("Search"), backUpAndRestore = new JButton("Restore Backup"), saveToFile = new JButton("Save to File");
	private JButton editInfo = new JButton("Edit Info"), addDoctor = new JButton("Add Doctor"), submitP = new JButton("Submit");
	
	//Containter
	private JTabbedPane tabs= new JTabbedPane();
	private JPanel panelInTab[] = new JPanel[5];
	private Container c = new JPanel(new GridLayout(0, 1));
	
	public DoctorGUI(Doctor doctor1){
		super("Doctor");
		d = doctor1;
	
		dC = new DoctorController(d, s, this);
		
		//SORT ALL PATIENT LIST
		for (int i = 0; i < s.getDoctors().size(); i++)
		{
			Collections.sort(s.getDoctors().get(i).getPatient());
		}
		
		//JTable Run method 
		populateAllPatientTable(s.getAllPatient());
		populatePatientTable(d.getPatient());
		
		//GUI Components & Create Layout
		drId.setText("DR00" + doctor1.getDrId());// make it return of the doctor index and get id
		drName.setText(doctor1.getDrName());
		currentPass.setText(doctor1.getDocPasswd());
		
		drId.setEditable(false);
		drName.setEditable(false);
		currentPass.setEditable(false);
		newPass.setEditable(false);
		
		//CREATE LAYOUT AND ADD CONTAINER INTO JFRAME
		createDoctorLayout();
		c.add(tabs);
		
		//JFRAME 
		add(c);
		setSize(600, 350);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//CREATE LAYOUT MATHOD
	private void createDoctorLayout(){
		//Method create layout of the Doctor
		makePanel();
		makeTabbedPane();
		makeLabelPanel();
		addPatientTab();
		findPatientTab();
		getPReportTab();
	}
	
	//MAKE PANEL METHOD
	private void makePanel(){
		panelInTab[0] = new JPanel(new BorderLayout());
		panelInTab[1] = new JPanel(new GridBagLayout());
		panelInTab[2] = new JPanel(new BorderLayout());
		panelInTab[3] = new JPanel(new BorderLayout());
		panelInTab[4] = new JPanel(new BorderLayout());
		
		//GET PATIENT ARRAYLIST ADDED TO PANELINTAB[4]
		panelInTab[4].add(allPatientScoller);
	}
	
	//TABBED PANE METHOD
	private void makeTabbedPane(){
		
		//LOOP THROUGH EACH TABS AND SET BORDERFACTOR TO EACH TABS
		for(int i = 0; i < panelInTab.length; i++)
		{
			String tabName[] = {"Doctor Info", "Add Patient", "Find Patient", "Patient Report","All Patient"};
			String tabNameMod[] = new String[5];
			tabNameMod[i] ="<HTML><U>" + tabName[i] + "<U><HTML>";
			tabs.add(tabName[i], panelInTab[i]);
			panelInTab[i].setBorder(BorderFactory.createTitledBorder(null, tabNameMod[i], TitledBorder.LEFT, TitledBorder.TOP, new Font("Comic Sans MS", Font.ITALIC, 25)));
		}
	}
	
	//DR INFO LABEL PANEL METHOD
	private void makeLabelPanel(){
		JPanel topPanel = new JPanel(new GridLayout(8,2)), buttonPanel = new JPanel(new GridLayout(1,1));
		
		//TOP PANEL ADDING COMPONENTS
		topPanel.add(drIdLabel);
		topPanel.add(drId);
		topPanel.add(drNameLabel);
		topPanel.add(drName);
		topPanel.add(password);
		topPanel.add(currentPass);
		topPanel.add(newPassword);
		topPanel.add(newPass);
		topPanel.add(editInfo);
		topPanel.add(addDoctor);
		
		//BUTTON PANEL ADD BUTTONS & SET SIZE
		buttonPanel.add(saveToFile);
		buttonPanel.add(backUpAndRestore);
		buttonPanel.add(logOut);
		
		buttonPanel.setSize(600, 70);
		
		//PANEL IN TAB [0] ADD BOTH TOP AND BUTTON PANEL INTO IT
		panelInTab[0].add(topPanel, BorderLayout.NORTH);
		panelInTab[0].add(buttonPanel, BorderLayout.CENTER);
		
		//ACTION LISTENER IN CONTROLLER CLASS(DoctorController.java)
		dC.editButton();
		dC.addDoctorButton();
		dC.backUpAndRestoreButton();
		dC.saveToFileButton();
		dC.logoutButton();
	}
	
	//ADD PATIENT TABS(panel)
	private void addPatientTab(){
		gbc = new GridBagConstraints();
		JLabel pFNameLabel = new JLabel("First Name: "), pLNameLabel = new JLabel("Last Name: "), pDOBLabel = new JLabel("D.O.B: "), pPhoneLabel = new JLabel("Phone No.:"), pAddressLabel = new JLabel("Address:");
		gbc.insets = new Insets(10, 0, 1, 1);
		
		//Position the GUI Component
		panelInTab[1].add(pFNameLabel);
			gbc.gridx = 0;
			gbc.gridy = 0;
			panelInTab[1].add(pFNameLabel, gbc);
		
		panelInTab[1].add(pFName);
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelInTab[1].add(pFName, gbc);
			
		panelInTab[1].add(pLNameLabel);
			gbc.gridx = 2;
			gbc.gridy = 0;
			panelInTab[1].add(pLNameLabel, gbc);
			
		panelInTab[1].add(pLName);
			gbc.gridx = 3;
			gbc.gridy = 0;
			panelInTab[1].add(pLName, gbc);
			
		panelInTab[1].add(pDOBLabel);
			gbc.gridx = 0;
			gbc.gridy = 1;
			panelInTab[1].add(pDOBLabel, gbc);
			
		panelInTab[1].add(pDOB);
			gbc.gridx = 1;
			gbc.gridy = 1;
			panelInTab[1].add(pDOB, gbc);
		
		panelInTab[1].add(pPhoneLabel);
			gbc.gridx = 0;
			gbc.gridy = 2;
			panelInTab[1].add(pPhoneLabel, gbc);
			
		panelInTab[1].add(pPhone);
			gbc.gridx = 1;
			gbc.gridy = 2;
			panelInTab[1].add(pPhone, gbc);
			
		panelInTab[1].add(pAddressLabel);
			gbc.gridx = 0;
			gbc.gridy = 3;
			panelInTab[1].add(pAddressLabel, gbc);
			
		panelInTab[1].add(pAddress);
			gbc.gridx = 1;
			gbc.gridy = 3;
			panelInTab[1].add(pAddress, gbc);
		
		panelInTab[1].add(submitP);
			gbc.gridx = 3;
			gbc.gridy = 4;
			panelInTab[1].add(submitP, gbc);
			
			pAddress.setWrapStyleWord(true);
			
			//ACTION LISTENER IN CONTROLLER CLASS(DoctorController.java)
			dC.submitPatientButton();
	}
	
	//FILL All PATIENT TABLE IN FIND PATIENT TAB
	private void populateAllPatientTable(ArrayList<Patient> p){
		aPTModel = new PatientTableModel(p);
		allPatient = new JTable(aPTModel);
		allPatient.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allPatientScoller = new JScrollPane(allPatient);
		selectRow = allPatient.getSelectionModel();
		
		//ACTION LISTENER IN CONTROLLER CLASS(DoctorController.java)
		dC.selectRowAllPatient();
	}
	
	//FILL PATIENT TABLE IN FIND PATIENT TAB
	private void populatePatientTable(ArrayList<Patient> p){
		pTModel = new PatientTableModel(p);
		patientTables =  new JTable(pTModel);
		patientTables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroller = new JScrollPane(patientTables);
		rowSelect = patientTables.getSelectionModel();
		
		//ACTION LISTENER IN CONTROLLER CLASS(DoctorController.java)
		dC.rowSelectionPatient();
	}

	//FIND PATIENT TABS(panel)
	private void findPatientTab(){
		JLabel searchPatientLabel = new JLabel("Search Patient: ");
		
		JPanel topPanel = new JPanel(new GridLayout(1, 1)), bottomPanel = new JPanel(new BorderLayout());
		search.setSize(40, 30);
		
		//TOP PANEL
		topPanel.add(searchPatientLabel);
		topPanel.add(searchPatient);
		topPanel.add(search);
		
		//ACTION LISTENER IN CONTROLLER CLASS(DoctorController.java)
		dC.searchButton();
	
		//BOTTOM PANEL
		bottomPanel.add(scroller, BorderLayout.CENTER);
		
		//PanelInTabs[2] Adding the top and bottom panel 
		panelInTab[2].add(topPanel, BorderLayout.NORTH);
		panelInTab[2].add(bottomPanel, BorderLayout.CENTER);
	}
	
	//Get Todays Date
	public String getTodaysDate(){
		visitDate = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(visitDate);
		return date;
	}
	
	//GET PATIENT REPORT TABS(panel)
	private void getPReportTab(){
		JPanel topPanel = new JPanel(new GridLayout(6,2)), bottomPanel = new JPanel(new BorderLayout());
		drIdLabel = new JLabel("Doctor ID : ");
		//Creating JTextField & JTextArea
		userInput[0] = new JTextField();
		userInput[0].setText(getTodaysDate());
		userInput[0].setEditable(false);
		
		userInput[1] = new JTextField();
		userInput[1].setText(d.getDrId() + "");
		userInput[1].setEditable(false);
		
		userInput[2] = new JTextField();
		
		for(int i = 0; i < userInputArea.length; i++)
		{
			userInputArea[i] = new JTextArea(2, 10);
			userInputArea[i].setWrapStyleWord(true);
		}
		
		//Add Label Into mainPanel
		topPanel.add(visitDateLabel);
		topPanel.add(userInput[0]);
		topPanel.add(drIdLabel);
		topPanel.add(userInput[1]);
		topPanel.add(pIdLabel);
		topPanel.add(userInput[2]);
		
		topPanel.add(medsLabel);
		topPanel.add(userInputArea[0]);
		topPanel.add(descriptionLabel);
		topPanel.add(userInputArea[1]);
		topPanel.add(procedureLabel);
		topPanel.add(userInputArea[2]);
		topPanel.setSize(600, 320);
		
		// add button
		bottomPanel.setSize(600, 30);
		bottomPanel.add(submit, BorderLayout.EAST);
		
		//ADD To Main Panel In The Tabs
		panelInTab[3].add(topPanel, BorderLayout.NORTH);
		panelInTab[3].add(bottomPanel, BorderLayout.CENTER);
		
		//ACTION LISTENER IN CONTROLLER CLASS(DoctorController.java)
		dC.submitButton();
	}
	
	/******************************************************* Getter & Setter *************************************************************************/
	public JPasswordField getNewPass() {
		return newPass;
	}
	
	public JButton getSaveToFile() {
		return saveToFile;
	}
	
	public JButton getSubmit() {
		return submit;
	}
	
	public JButton getSubmitP(){
		return submitP;
	}

	public JButton getLogOut() {
		return logOut;
	}

	public JButton getSearch() {
		return search;
	}

	public JButton getBackUpAndRestore() {
		return backUpAndRestore;
	}

	public JButton getEditInfo() {
		return editInfo;
	}

	public JButton getAddDoctor() {
		return addDoctor;
	}

	public JTextField getDrName() {
		return drName;
	}
	
	public ListSelectionModel getSelectRow() {
		return selectRow;
	}
	
	public ListSelectionModel getRowSelect() {
		return rowSelect;
	}
	
	public JTable getPatientTables() {
		return patientTables;
	}
	
	public JTable getAllPatient() {
		return allPatient;
	}
	
	public JTextField getSearchPatient() {
		return searchPatient;
	}

	public JTextField[] getUserInput() {
		return userInput;
	}

	public JTextArea[] getUserInputArea() {
		return userInputArea;
	}

	public JTextField getpFName() {
		return pFName;
	}

	public JTextField getpLName() {
		return pLName;
	}

	public JTextField getpDOB() {
		return pDOB;
	}

	public JTextField getpPhone() {
		return pPhone;
	}

	public JTextArea getpAddress() {
		return pAddress;
	}
	
	public PatientTableModel getpTModel() {
		return pTModel;
	}
	
	public PatientTableModel getaPTModel() {
		return aPTModel;
	}
	
	/******************************************************** Inner Class ****************************************************************************/
	public class PatientTableModel extends AbstractTableModel{

		private ArrayList<Patient> patientList;
		
		public PatientTableModel(ArrayList<Patient> patientList){
			this.patientList = new ArrayList<>(patientList);
			Collections.sort(this.patientList);
		}
		
		//SET THE NUMBER OF COLUMN
		@Override
		public int getColumnCount() {
			return 3;
		}

		//GETTING THE ROW COUNT IN THE ARRAYLIST
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return patientList.size();
		}
		
		//Dynamically Add patient into each row
		public void add(Patient p){
			int size = getRowCount();
			patientList.add(p);
			this.fireTableRowsInserted(size, size);
		}
		
		//SET EACH COLUMN NAME 
		public String getColumnName(int column){
			String name = "";
			switch (column){
				case 0:
					name = title[0];
					break;
					
				case 1:
					name = title[1];
					break;
					
				case 2:
					name = title[2];
					break;
				}
			return name;
		}
		
		//GETTING THE VALUE FROM EACH PATIENT AND PUT IT INTO EACH CELL IN THE ROW
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Patient p = patientList.get(rowIndex);
			Object value = null;
			
			for (int i = 0; i < patientList.size(); i++)
			switch(columnIndex){
				
				case 0:
					value = "P00" + p.getPatientId();
					break;
				
				case 1:
					value = p.getPatientFName();
					break;
				
				case 2:
					value = p.getPatientLName();
					break;
			}
			return value;
		}
	}
}