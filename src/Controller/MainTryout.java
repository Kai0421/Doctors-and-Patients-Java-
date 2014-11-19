package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import View.*;
import Model.*;

public class MainTryout {
	private static Surgery s = Surgery.getInstance();
	private static SqlDatabaseDoctor sqlD = SqlDatabaseDoctor.getInstance();
	private static SqlDatabasePatient sqlP = SqlDatabasePatient.getInstance();
	private static SqlDatabasePatientHistory sqlPH = SqlDatabasePatientHistory.getInstance();

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		//FILE INPUT STREAM READ FROM FILE
		/*try {
			FileInputStream in = new FileInputStream("Clinic Data.ser");
			ObjectInputStream oIn = new ObjectInputStream(in);
			s = (Surgery) oIn.readObject();
			oIn.close();
			
		}catch(FileNotFoundException e){
			System.out.println("File Not Found");
		}catch(Exception e){
			System.out.println("System File Not Found!!");
		}*/
		
		for (int i = 0; i < s.getDoctors().size(); i++)
		{
			for(int a = 0; a < s.getDoctors().get(i).getPatient().size(); a++)
			{
				s.getAllPatient().add(s.getDoctors().get(i).getPatient().get(a));
			}
		}
		
		for (int i = 0; i < s.getAllPatient().size(); i++)
		{
			Collections.sort(s.getAllPatient());
		}
		
		LoginGUI lGUI = new LoginGUI(s);
	}
}
