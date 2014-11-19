package Model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.Connection;

public class SqlDatabasePatient {
	private Connection con = null;
	private static SqlDatabasePatient sql = null;
	private Surgery s = Surgery.getInstance();
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	private SqlDatabasePatient(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			// error out
		}

		try {
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + "Programming_sem2_Surgery", "root",
					null);
			Statement stm = con.createStatement();
			
			//QUESRIES FOR ALL TABLES
			ResultSet result= stm.executeQuery("SELECT * FROM Patient");
			
			//GET PATIENTS IN DATABASE
			while(result.next())
			{

				for(int i = 0; i < s.getDoctors().size(); i++)
				{
					if (new Integer(result.getInt(6)) ==  s.getDoctors().get(i).getDrId())
					{
						s.getDoctors().get(i).getPatient().add(new Patient(result.getString(2), result.getString(3), result.getString(4), result.getString(7), result.getDate(5), new Integer(result.getInt(1))));
						break;
					}
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
			close();
		}		
	}
	
	// ADD PATIENTS TO THE DATABASE
	public void addPatient(String fName, String lName, String address, String phone, Date dob, int dId, int id){
		try {
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + "Programming_sem2_Surgery", "root",
					null);
			Statement add = con.createStatement();
			String sql = "Insert INTO Patient values(" + id + ", '" + fName + "', '" + lName + "', '" + address + "', '" + df.format(dob) + "', " + dId +", " + phone + ")";

			add.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();			
		} finally{
			close();
		}
	}
	
	//UPDATE CURRENT Patient's DATA IN THE DATABASE
		public void updatePatient(int pno, String fName, String lName, String address, Date dob, int dId, String phone){
			try{
				con = (Connection) DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/" + "Programming_sem2_Surgery", "root",
						null);
				
				Statement add = con.createStatement();
				String sqlUpdateP = "Update Patient SET pFName = '" + fName + "', pLName = '" + lName + "', address = '" + address +"', dob = '" + df.format(dob) +"' Where pno = " + pno + " and dno = " + dId + ";";
				 
				add.executeUpdate(sqlUpdateP);
				
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
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
	public static SqlDatabasePatient getInstance(){
		if(sql == null)
			sql = new SqlDatabasePatient();
		
		return sql;
	}
}
