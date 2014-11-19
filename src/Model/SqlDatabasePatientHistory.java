package Model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.Connection;

public class SqlDatabasePatientHistory {
	private Connection con = null;
	private static SqlDatabasePatientHistory sql = null;
	private Surgery s = Surgery.getInstance();
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	private SqlDatabasePatientHistory(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		try { 
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + "Programming_sem2_Surgery", "root",
					null);
			Statement stm = con.createStatement();
			
			//QUESRIES FOR ALL TABLES
			ResultSet result= stm.executeQuery("SELECT * FROM PatientHistory");
		
			//GET DOCTORS IN DATABASE
			while(result.next())
			{
				for (int i = 0; i < s.getDoctors().size(); i++)
				{
					if(new Integer(result.getInt(7)) == s.getDoctors().get(i).getDrId())
					{
						for(int a = 0; a < s.getDoctors().get(i).getPatient().size(); a++)
						{
							if(new Integer(result.getInt(6)) == s.getDoctors().get(i).getPatient().get(a).getPatientId())
							{
								s.getDoctors().get(i).getPatient().get(a).doctorVisit(new PatientHistory(new Integer(result.getInt(6)), new Integer(result.getInt(7)), result.getString(2), result.getString(3), result.getString(4), new Integer(result.getInt(1)),result.getDate(5)));
							}
						}
					}
				}
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}	
	}

	// ADD PATIENTHISTORY TO THE DATABASE
		public void addPatientHistory(int pId, int dId, String meds, String descrpt, String procdre, int id, Date visitDate){
			try {
				con = (Connection) DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/" + "Programming_sem2_Surgery", "root",
						null);
				Statement add = con.createStatement();
				String sql = "Insert INTO PatientHistory values(" + id + ", '" + meds + "', '" + descrpt + "', '" + procdre + "', '" + df.format(visitDate) + "', " + pId +", " + dId + ")";

				add.executeUpdate(sql);

			} catch (SQLException e) {
				e.printStackTrace();			
			} finally{
				close();
			}
		}
		
	//CLOSE THE CONNECTION
	public void close(){
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//SINGLETONS PUBLIC METHOD TO ACCESS THE INSTANCE OF THE DATABASE PATIENT
	public static SqlDatabasePatientHistory getInstance(){
		if(sql == null)
			sql = new SqlDatabasePatientHistory();
		
		return sql;
	}
}
