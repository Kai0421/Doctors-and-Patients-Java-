package View;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import Controller.*;
import Model.*;
import View.DoctorGUI.PatientTableModel;

public class AddDoctorGUI extends JFrame{
	//CLASSES
	private Doctor d = null;
	private Surgery s = Surgery.getInstance();
	private DoctorTableModel dTModel;
	private AddDoctorController aDC;
	
	//PRIVATE VARIABLE
	private int numberOfTabs = 2;
	
	//GUI COMPONENTS
	private Container mainPanel = new JPanel(new GridLayout(0,1));
	private JPanel tabsPanel[] = new JPanel[numberOfTabs];
	private JTabbedPane tabs = new JTabbedPane();
	private JLabel labelArray[] = new JLabel[2];
	private JTextField textField = new JTextField(10);
	private JPasswordField pass = new JPasswordField(10);
	private JButton addDoctorButton = new JButton("Add Doctor");
	private JScrollPane scroller;
	private JTable doctorTables;
	
	public AddDoctorGUI(Doctor doc){
		super("Doctor's Option");
		
		//CLASSES
		d = doc;
		aDC = new AddDoctorController(this);
		
		//CREATING LAYOUT
		mainPanel.add(tabs);
		createLayout();
		
		//CREATE DOCTOR TABLE
		dTModel = new DoctorTableModel(s.getDoctors());
		doctorTables = new JTable(dTModel);
		scroller = new JScrollPane(doctorTables);
		tabsPanel[1].add(scroller);
		
		add(mainPanel);
		setLocationRelativeTo(null);
		setSize(500, 600);
		setResizable(false);
		setVisible(true);
	}
	
	//CREATE LAYOUT METHOD
	private void createLayout(){
		makeTabs();
		addDocTab();
	}
	
	//MAKE TABS METHOD
	private void makeTabs(){
		tabsPanel[0] = new JPanel(new BorderLayout());
		tabsPanel[1] = new JPanel();
		
		for(int i = 0; i < numberOfTabs; i++)
		{
			String tabsName[] = {"Add Doctor", "All Doctor"};
			String tabsNameMod[] = new String[3];
			
			tabsNameMod[i] = "<HTML><U>" + tabsName[i] + "<U><HTML>";
			tabs.add(tabsName[i], tabsPanel[i]);
			
			tabsPanel[i].setBorder(BorderFactory.createTitledBorder(null, tabsNameMod[i], TitledBorder.LEFT, TitledBorder.TOP, new Font("Comic Sans MS", Font.ITALIC, 25)));	
		}
	}
	
	//CREATE DOCTOR TAB
	private void addDocTab(){
		JPanel topPanel = new JPanel(new GridLayout(3,2)), buttonPanel = new JPanel(new GridLayout(1,1));
		
		labelArray[0] = new JLabel("Doctor Name : ");
		labelArray[1] = new JLabel("Doctor Password : ");
		
		topPanel.add(labelArray[0]);
		topPanel.add(textField);
		topPanel.add(labelArray[1]);
		topPanel.add(pass);
		
		buttonPanel.add(addDoctorButton);
		buttonPanel.setPreferredSize(new Dimension(500, 70));
		
		tabsPanel[0].add(topPanel, BorderLayout.CENTER);
		tabsPanel[0].add(buttonPanel, BorderLayout.SOUTH);
		
		//BUTTON FUNCTIONS IN CONTROLLER CLASS(AddDoctorController.java)
		aDC.addDoctorButton();
	}
	
	/********************************************************** Getters & Setters ********************************************************************/
	public JTextField getTextField() {
		return textField;
	}

	public JPasswordField getPass() {
		return pass;
	}

	public JButton getAddDoctorButton() {
		return addDoctorButton;
	}
	
	public DoctorTableModel getdTModel() {
		return dTModel;
	}
	
	/******************************************************** Inner Class ****************************************************************************/
	public class DoctorTableModel extends AbstractTableModel{
		private String title[] = {"Doctor No.", "Doctor Name"};
		private ArrayList<Doctor> doctorList;
		
		public DoctorTableModel(ArrayList<Doctor> dcArrayList){
			this.doctorList = new ArrayList<>(dcArrayList);
		}
		
		//SET THE NUMBER OF COLUMN
		@Override
		public int getColumnCount() {
			return 2;
		}

		//GETTING THE ROW COUNT IN THE ARRAYLIST
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return doctorList.size();
		}
		
		//Dynamically Add DOCTOR into each row
		public void add(Doctor d){
			int size = getRowCount();
			doctorList.add(d);
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
				}
			return name;
		}
		
		//GETTING THE VALUE FROM EACH DOCTOR AND PUT IT INTO EACH CELL IN THE ROW
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Doctor d = doctorList.get(rowIndex);
			Object value = null;
			
			for (int i = 0; i < doctorList.size(); i++)
			switch(columnIndex){
				
				case 0:
					value = "DR00" + d.getDrId();
					break;
				
				case 1:
					value = d.getDrName();
					break;
			}
			return value;
		}
	}
}
