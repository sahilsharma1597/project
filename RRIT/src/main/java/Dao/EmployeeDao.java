package Dao;

import java.sql.*;
import Bean.Employee;

public class EmployeeDao {

	public static Connection setConnection()
	{
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");		 
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useSSL=false","root","root");  
			//System.out.print("ban gya");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.print("Nhi :"+e);
		} 
		return con;
	}
	
	public static int checkExisting(int id)
	{
		try {
			Connection con=setConnection();
			PreparedStatement prepared = con.prepareStatement("select emp_name from Employee_Details where emp_id = ?");
			prepared.setInt(1,id);
			ResultSet rows = prepared.executeQuery();

			//System.out.println(rows.getRow());
			if(rows.next())
			{
				return  1;
			}
			else
			{
				return 0;
			}
		}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return 0;
	}
	public static String loginUser(Employee emp)
	{
		String message="";
		try {
			Connection con=setConnection();
			int check = checkExisting(emp.getId());
			if(check==1)
			{
				PreparedStatement prepared = con.prepareStatement("select emp_name,emp_pass from Employee_Details where binary emp_id = ?  and binary emp_pass = ? ");
				prepared.setInt(1, emp.getId());
				prepared.setString(2,emp.getPassword());
				ResultSet rows = prepared.executeQuery();
				if(rows.next())
				{
					message= rows.getString("emp_name");
				}
				else
				{
					message = "Invalid Password";
				}
				
			}
			else
			{
				message="No User associated with this ID.";
			}
			con.close();
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return message;
		}
	
	
	public static String save(Employee emp) 
	{
		String message="";
		try {
			Connection con=setConnection();
			int check = checkExisting(emp.getId());
			
			if(check==1)
			{
				message="User Exists";
				return message;
			}
			PreparedStatement prepared = con.prepareStatement("insert into Employee_Details (emp_id,emp_name,emp_pass) values(?,?,?);");
			prepared.setInt(1,emp.getId());
			prepared.setString(2, emp.getName());
			prepared.setString(3, emp.getPassword());
			
			if(!emp.getPassword().equals(emp.getCpassword()))
			{
				message="Password doesn't match";
			}
			else
			{
				prepared.executeUpdate();
				message = "success";
			}
			con.close();  
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return message;
		}

}
