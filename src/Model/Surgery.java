package Model;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kai
 * @Student_Number : R00095982
 * **/

public class Surgery implements Serializable {

	private ArrayList<Doctor> doctorArrayList = new ArrayList<>();
	private static Surgery s = null;
	private ArrayList<Patient> p = new ArrayList<>();
	
	private Surgery (){}
	
	public static Surgery getInstance(){
		if (s == null)
			s = new Surgery();
		return s;
	}
	
	public void listPatient(){
		for(int i = 0; i < doctorArrayList.size(); i++)
		{
			System.out.println(doctorArrayList.size());
			doctorArrayList.get(i).print();
		}
	}
	
	public ArrayList<Doctor> getDoctors(){
		return doctorArrayList;
	}
	
	public ArrayList<Patient> getAllPatient(){
		return p;
	}
	
}
