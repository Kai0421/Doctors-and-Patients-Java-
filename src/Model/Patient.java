package Model;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kai
 * @Student_Number : R00095982
 * **/

public class Patient implements Comparable<Patient>, Serializable{
	private int patientId;
	//private static int id = 0;
	private String patientFName, patientLName, patientAddress, patientPhone;
	private Date patientDOB;
	private ArrayList<PatientHistory> myHistory = new ArrayList<>();
	
	public Patient(){}
	
	public Patient (String fName, String lName, String address, String phone, Date dob, int id){
		setPatientFName(fName);
		setPatientLName(lName);
		setPatientAddrs(address);
		setPatientPhone(phone);
		setPatientDOB(dob);
		setPatientId(id);
	}
	
	//GETTERS & SETTERS
	public void setPatientFName(String fName){
		patientFName = fName;
	}
	public String getPatientFName(){
		return patientFName;
	}
	
	public void setPatientLName(String lName){
		patientLName = lName;
	}
	public String getPatientLName(){
		return patientLName;
	}
	
	public void setPatientAddrs(String addrs){
		patientAddress = addrs;
	}
	public String getPatientAddrs(){
		return patientAddress;
	}
	
	public void setPatientPhone(String fone){
		patientPhone = fone;
	}
	public String getPatientPhone(){
		return patientPhone;
	}
	
	public void setPatientDOB(Date dob){
		patientDOB = dob;
	}
	public String getPatientDOB(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String dob = df.format(patientDOB);
		return dob;
	}
	
	/**Static generated ID **/ 
	public void setPatientId(int pId){
		patientId = pId;
	}
	public int getPatientId(){
		return patientId;
	}
	
	//BEHAVIORS / OPERATIONS
	public void doctorVisit(PatientHistory pH){
		myHistory.add(pH);
	}
	public ArrayList<PatientHistory> getDoctorVisit(){
		return myHistory;
	}
	
	//STRING AND PRINTOUT 
	public String toString(){
		return "Patient First Name : " + getPatientFName() + "\tPatient Last Name : " + getPatientLName()+ "\nPatient DOB :" + getPatientDOB() + "\nContact Number : " + getPatientPhone() + "\nPatient's Address : " + getPatientAddrs();
	}
	
	public void print(){
		System.out.println(toString());
		for(int i = 0; i < myHistory.size(); i++)
		{
			myHistory.get(i).print();
		}	
	}

	@Override
	public int compareTo(Patient p) {
		int compare;
		
		if (getPatientLName().equals(p.getPatientLName()))
			compare = getPatientFName().compareTo(p.getPatientFName());
		else 
			compare = getPatientLName().compareTo(p.getPatientLName());

		return compare;
	}
}