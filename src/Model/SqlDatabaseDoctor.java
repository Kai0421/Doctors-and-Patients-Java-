package Model;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;


public class SqlDatabaseDoctor {
	private Connection con = null;
	private static SqlDatabaseDoctor sql = null;
	private Surgery s = Surgery.getInstance();
	private ResultSet result;
	
	private SqlDatabaseDoctor(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		try { // jdbc:mysql://localhost:3306/emp
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + "Programming_sem2_Surgery", "root",
					null);
			Statement stm = con.createStatement();
			
			//QUESRIES FOR ALL TABLES
			this.result= stm.executeQuery("SELECT * FROM Doctor");
		
			//GET DOCTORS IN DATABASE
			while(result.next())
			{
				s.getDoctors().add(new Doctor(result.getString(2), result.getString(3), new Integer(result.getInt(1))));
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			try{
				this.result.close();
			} catch(SQLException sql){
				sql.printStackTrace();
				close();
			}
		}
	}
	
	// ADD DOCTORS TO THE DATABASE
	public void addDoctor(int id, String name, String password){
	
		try {
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + "Programming_sem2_Surgery", "root",
					null);
			Statement add = con.createStatement();
			String sql = "Insert INTO Doctor values(" + id + ", '" + name + "', '" + password + "')";
			
			add.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();			
		} finally{
			close();
		}
	}
	
	//UPDATE CURRENT DOCTOR'S DATA IN THE DATABASE
	public void updateDoctor(String name, String pass, int dno){
		try{
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + "Programming_sem2_Surgery", "root",
					null);
			Statement add = con.createStatement();
			String sqlUpdate = "Update Doctor SET drName = '" + name + "', password = '" + pass + "' Where dno = " + dno + ";";
			
			add.executeUpdate(sqlUpdate);
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	//CLOSE THE CONNECTION
	private void close(){
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//SINGLETONS PUBLIC METHOD TO ACCESS THE INSTANCE OF THE DATABASE DOCTOR
	public static SqlDatabaseDoctor getInstance(){
		if(sql == null)
			sql = new SqlDatabaseDoctor();
		
		return sql;
	}
}
