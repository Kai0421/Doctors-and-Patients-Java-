package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import Controller.*;
import Model.*;

public class PatientGUI extends JFrame{
	//Class
	private Patient p;
	private Doctor d;
	private PatientHistoryTableModel pHTModel;
	private PatientController pC;
	
	//Private Variables
	private String[] title = {"No.", "Visit Date"};
	
	//Private GUI Component
	private JPanel outerPanel = new JPanel(new BorderLayout());
	private JButton edit = new JButton("Edit Patient Info"), report = new JButton("View Report");
	private JTextField[] userInput = new JTextField[6];
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private JScrollPane sp;
	private JTable table;
	private ListSelectionModel rowSelect;
	
	public PatientGUI(Patient patient, Doctor d){
		super("Patient");
		this.p = patient;
		this.d = d;
		pC = new PatientController(p, this, this.d);
		
		outerPanel.setBorder(BorderFactory.createTitledBorder(null, p.getPatientFName() + " " + p.getPatientLName(), TitledBorder.LEFT, TitledBorder.TOP, new Font("Comic Sans MS", Font.ITALIC, 25)));
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				 try {
		             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		         } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
		             ex.printStackTrace();
		         }
				 
				//CREATE LAYOUT METHOD
				createPatientLayout();
				pHTModel = new PatientHistoryTableModel(p.getDoctorVisit());
				table =  new JTable(pHTModel);
				sp = new JScrollPane(table);
				rowSelect = table.getSelectionModel();
				
				//BUTTON FUNCTIONS IN CONTROLLER CLASS(PatientController.java)
				pC.rowSelection();
				
			outerPanel.add(sp, BorderLayout.CENTER);
			add(outerPanel);
		
			//JFRAME
			setSize(500, 600);
			setResizable(false);
			setVisible(true);
			setLocationRelativeTo(null);	
			}
		});
	}	
	
	private void createPatientLayout(){
		//CREATING PANELS & GRIDBAG CONSTRAINTS
		JPanel mainPanel = new JPanel(new GridLayout(6,2)), listPanel = new JPanel(new BorderLayout()), buttonPanel = new JPanel(new GridLayout(1,2));
		mainPanel.setSize(250,300);
		listPanel.setSize(250, 300);

		//JLABEL & JLIST
		JLabel pFNameLabel = new JLabel("First Name: "), pLNameLabel = new JLabel("Last Name: "), pIdLabel = new JLabel("Patient's ID: "), pDOBLabel = new JLabel("D.O.B: "), pPhoneLabel = new JLabel("Phone No.:"), pAddressLabel = new JLabel("Address:");
		
		for (int i = 0; i < userInput.length; i++)
		{
			userInput[i] = new JTextField();
			userInput[i].setEditable(false);
		}
		userInput[0].setText(p.getPatientId() + "");
		userInput[1].setText(p.getPatientFName());
		userInput[2].setText(p.getPatientLName());
		userInput[3].setText(p.getPatientPhone());
		userInput[4].setText(p.getPatientDOB());
		userInput[5].setText(p.getPatientAddrs());
		
		//LAYOUT CONSTRUCT HERE (mainPanel)
		mainPanel.add(pIdLabel);
		mainPanel.add(userInput[0]);
		mainPanel.add(pFNameLabel);
		mainPanel.add(userInput[1]);
		mainPanel.add(pLNameLabel);
		mainPanel.add(userInput[2]);
		
		mainPanel.add(pPhoneLabel);
		mainPanel.add(userInput[3]);
		mainPanel.add(pDOBLabel);
		mainPanel.add(userInput[4]);
		mainPanel.add(pAddressLabel);
		mainPanel.add(userInput[5]);
		
		//BUTTON PANEL ADD BUTTON EDIT
		buttonPanel.add(edit);
		buttonPanel.add(report);
		
		//BUTTON FUNCTIONS IN CONTROLLER CLASS(PatientController.java)
		pC.editButton();
		pC.reportButton();
		
		//OUTER PANEL ADD THE SUB-PANEL(mainPanel & listPanel & buttonPanel)
		outerPanel.add(mainPanel, BorderLayout.NORTH);
		outerPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	 /**************************************************************** Getters Setters  ************************************************************************************/
	public JButton getEdit() {
		return edit;
	}

	public JButton getReport() {
		return report;
	}

	public JTextField[] getUserInput() {
		return userInput;
	}
	
	public ListSelectionModel getRowSelect() {
		return rowSelect;
	}
	
	public JTable getTable() {
		return table;
	}
	
	/************************************************************ INNER CLASS ********************************************************************************************/
	
	public class PatientHistoryTableModel extends AbstractTableModel{

		private ArrayList<PatientHistory> patientHistoryList;
		
		public PatientHistoryTableModel(ArrayList<PatientHistory> pHList){
			this.patientHistoryList = new ArrayList<>(pHList);
		}
		
		//SET NUMBER OF COLUMN
		@Override
		public int getColumnCount() {
			return 2;
		}

		//GET NUMBER OF ROWS FROM PATIENTHISTORY ARRAYLIST FOR TABLE ROWS
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return patientHistoryList.size();
		}
		
		//Dynamically Add patient into each row
		public void add(PatientHistory pH){
			int size = getRowCount();
			patientHistoryList.add(pH);
			this.fireTableRowsInserted(size, size);
		}
		//SETTING COLUMN NAME
		public String getColumnName(int column){
			String name = "";
			switch (column){
				case 0:
					name = title[0];
					break;
					
				case 1:
					name = title[1];
					break;
					
					default:
						break;
				}
			return name;
		}
		
		//SET EACH VALUE IN PATIENTHISTORY TO EACH CELL IN THE ROW
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			PatientHistory pH = patientHistoryList.get(rowIndex);
			Object value = null;
			
			for (int i = 0; i < patientHistoryList.size(); i++)
			switch(columnIndex){
				
				case 0:
					value = "P00" + pH.getHistoryId();
					break;
				
				case 1:
					value = pH.getVisitDate();
					break;
					
					default:
						break;
			}
			return value;
		}
	}
}