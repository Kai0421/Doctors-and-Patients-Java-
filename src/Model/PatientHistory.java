package Model;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kai
 * @Student_Number : R00095982
 * **/

public class PatientHistory implements Serializable{
	private static int id = 0;
	private int historyId, patientId, doctorId;
	private String description, medicine, procedure;
	private Date visitDate;
	
	public PatientHistory(){}
	
	public PatientHistory(int pId, int dId, String meds, String descrpt, String procdre, int id, Date visitDate){
		setPatientId(pId);
		setDoctorId(dId);
		setMedicine(meds);
		setDescription(descrpt);
		setProcedure(procdre);
		setHistoryId(id);
		setVisitDate(visitDate);
	}
	
	//GETTERS & SETTERS
	public void setDoctorId(int dId) {
		doctorId = dId;
	}
	public int getDoctorId(){
		return doctorId;
	}

	public void setHistoryId(int hId){
		historyId = hId;
	}
	public int getHistoryId(){
		return historyId;
	}
	
	public void setPatientId(int pId){
		patientId = pId;
	}
	public int getPatientId(){
		return patientId;
	}
	
	public void setVisitDate(Date visitDate){
		this.visitDate = visitDate;
	}
	
	//BEHAVIOURS / OPERATIONS
	public String getVisitDate(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(visitDate);
		return date;
	}
	
	public void setDescription(String descrp){
		description = descrp;
	}
	public String getDescription(){
		return description;
	}
	
	public void setMedicine(String meds){
		medicine = meds;
	}
	public String getMedicine(){
		return medicine;
	}
	
	public void setProcedure(String procdre){
		procedure = procdre;
	}
	public String getProcedure(){
		return procedure;
	}
	
	//STRING AND PRINTOUT
	public String toString(){
		return "" + getVisitDate() + "\n" + "Doctor ID: " + getDoctorId() + "\tPatient ID: " + getPatientId() + "\nMeds: " + getMedicine() + "\nDescription: " + getDescription() + "\nProcedure: " + getProcedure() + "\n";
	}
	public void print(){
		System.out.println(toString());
	}
}
