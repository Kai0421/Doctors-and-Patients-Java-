package Model;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kai
 * @Student_Number : R00095982
 * **/

public class Doctor implements Comparable<Doctor>, Serializable{
	
	//Private Attributes
	private int  doctorId, surgeryId;
	private String docName, docPasswd;
	private ArrayList<Patient> pList = new ArrayList<>();
	
	public Doctor(){}
	
	public Doctor(String drName, String drPasswd, int id){
		setDrName(drName);
		setDocPasswd(drPasswd);
		setDrId(id);
	}
	
	//GETTERS AND SETTERS
	public void setDrName(String name){
		docName = name;
	}
	public String getDrName(){
		return docName;
	}
	
	/**Static generated ID***/
	public void setDrId(int drId){
		doctorId = drId;
	}
	
	public int getDrId(){
		return doctorId;
	}
	
	public void setSurgeryId(int surId){
		surgeryId = surId;
	}
	public int getSurgeryId(){
		return surgeryId;
	}
	
	public void setDocPasswd(String docPass){
		docPasswd = docPass;
	}
	public String getDocPasswd(){
		return docPasswd;
	}

	//BEHAVIOUR / OPERATIONS
	public void addPatient(Patient newPatient){
		pList.add(newPatient);
	}
	public ArrayList<Patient> getPatient(){
		return pList;
	}
	
	public int findPatient(int pId){
		int index= 0;
		for (int i = 0; i < pList.size(); i++)
		{
			if(pId == pList.get(i).getPatientId())
			{
				index = i;
				break;
			}
		}
		return index;
	}
	
	//Change!!!
	public void updatePatient(PatientHistory pH){
		int index = findPatient(pH.getPatientId());		
		pList.get(index).doctorVisit(pH);
	}
	
	public void getPatientReport(int pId){
		int index = findPatient(pId);
		pList.get(index).print();
	}
	
	//STRING AND PRINTOUT
	public String toString()
	{
		return "Doctor ID: " + getDrId() + "\tDoctorName: " + getDrName() +  "\tPassword: " + getDocPasswd() + "\tSurgery ID: " + getSurgeryId();
	}
	
	public void print(){
		System.out.println(toString());
	}

	@Override
	public int compareTo(Doctor d) {
		int compare = getDrName().compareTo(d.getDrName());
		
		return compare;
	}
}
