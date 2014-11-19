package Model;

/**
 * @author Kai
 * @Student_Number : R00095982
 * **/

public class Login {
	private Surgery s;
	private Doctor d;
	private int index;
	
	public Login(int id, String pass, Surgery s){
		
		this.s = s;
		d = verifyID(id, pass);
	}
	
	private Doctor verifyID(int i, String pass){
		
		for (int a = 0; a < s.getDoctors().size(); a++)
		{
			if (i == s.getDoctors().get(a).getDrId())
			{		
				if (pass.equals(s.getDoctors().get(a).getDocPasswd()))
				{
					d = s.getDoctors().get(a);	
					break;
				}
				else
				{
					index = -1;
				}
				break;
			}
			else 
			{
				index = -1;
			}
		}
		
		return d;	
	}
	
	public Doctor returnDoctor(){
		return d;
	}
	
	public int getIndex(){
		return index;
	}
	
	public Surgery getSurgery(){
		return s;
	}
}