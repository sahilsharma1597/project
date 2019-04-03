package Dao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*; 
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/*import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/
import Bean.Candidate;

public class CandidateDao {

	public static Connection setConnection()
	{
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");		 
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useSSL=false","root","root");  
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return con;
	}

	public static String createCandidate(Candidate candidate, File file) throws IOException
	{
		
		
		
		//String root="C:\\Users\\765371\\Desktop\\";
		String message="";
		try {
			Connection con=setConnection();
			int check = checkExisting(candidate.getCand_id());
			if(check==1)
			{
				message="Details already present for "+candidate.getCand_name();
				return message;
			}
			PreparedStatement prepared = con.prepareStatement("insert into Candidate_Details (cand_id, cand_name, domain, experience,cv) values(?,?,?,?,?);");
			prepared.setInt(1,candidate.getCand_id());
			prepared.setString(2, candidate.getCand_name());
			prepared.setString(3, candidate.getDomain());
			prepared.setInt(4, candidate.getExperience());
			//String filename=root+candidate.getCv();
			FileInputStream fis = new FileInputStream(file);
			//System.out.println("asdasdasdas"+fis);
			prepared.setBlob(5, fis);
			prepared.executeUpdate();
			message = "success";
			con.close();  
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return message;
	}
	
	
	public static String updateCandidate(Candidate candidate, String name) throws IOException
	{
		String message="successfully saved";
		try {
			Connection con=setConnection();
			PreparedStatement prepared = con.prepareStatement("update candidate_details set l1=?,l2=?,hr=? where cand_id=?");
			String l1="",l2="",hr="";
			if(candidate.getMyCheckBox1()!=null)
			{
				 l1="Cleared";
			}
			else
			{
				l1="Not Cleared";			
			}
			if(candidate.getMyCheckBox2()!=null)
			{
				if(l1.equals("Cleared"))
					
						l2="Cleared";
				else
				{	message="Candidate with ID :"+name+" first need to clear their L1 assessments";
					l2="Not Cleared";
				}
				
			}
			else
			{
				l2="Not Cleared";				
				
			}
			if(candidate.getMyCheckBox3()!=null)
			{
				if(l1.equals("Cleared") && l2.equals("Cleared"))
					hr="Cleared";
				else
				{
					hr="Not Cleared";
					message="Candidate with ID :"+name+" first need to clear their L1 and L2 assessments";
				}
			}
			
			else
			{
					hr="Not Cleared";
			}
			if(message.equals("successfully saved"))
			{	
				prepared.setString(1,l1);
				prepared.setString(2, l2);
				prepared.setString(3, hr);
				prepared.setInt(4, Integer.valueOf(name));
				prepared.executeUpdate();
			}
			con.close();
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return message;
	}
	
	
	public static int checkExisting(int id)
	{
		try {
			Connection con=setConnection();
			PreparedStatement prepared = con.prepareStatement("select cand_name from Candidate_Details where cand_id = ?");
			prepared.setInt(1,id);
			ResultSet rows = prepared.executeQuery();
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
	
	@SuppressWarnings("resource")
	public static void cvDownload(int id)
	{
	        try {
	        	Connection con=setConnection();
				PreparedStatement prepared = con.prepareStatement("select cv from Candidate_Details where cand_id = ?");
				prepared.setInt(1,id);
	            ResultSet rs = prepared.executeQuery();
	            if (rs.next()) {
	            	String username = System.getProperty("user.name");
	            	Blob blob = rs.getBlob("cv");
	                InputStream inputStream = blob.getBinaryStream();
	                byte[] buffer = new byte[inputStream.available()];
	                inputStream.read(buffer);
	                File targetFile = new File("C:/Users/"+username+"/Downloads/"+id+"_cv.pdf");
	                OutputStream outStream = new FileOutputStream(targetFile);
	                outStream.write(buffer);
	            }
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	@SuppressWarnings("resource")
	public static void download()
	{
		 try {
	        	Connection con=setConnection();
	        	 Statement statement = con.createStatement();
	             ResultSet resultSet = statement.executeQuery("select cand_id, cand_name, domain, experience, l1, l2, hr from Candidate_Details");
				XSSFWorkbook workbook = new XSSFWorkbook(); 
			      XSSFSheet spreadsheet = workbook.createSheet("employe db");
			      
			      XSSFRow row = spreadsheet.createRow(1);
			      XSSFCell cell;
			      cell = row.createCell(1);
			      cell.setCellValue("cand_id");
			      cell = row.createCell(2);
			      cell.setCellValue("cand_name");
			      cell = row.createCell(3);
			      cell.setCellValue("domain");
			      cell = row.createCell(4);
			      cell.setCellValue("experience");
			      cell = row.createCell(5);
			      cell.setCellValue("l1");
			      cell = row.createCell(6);
			      cell.setCellValue("l2");
			      cell = row.createCell(7);
			      cell.setCellValue("hr");
			      int i = 2;

			      while(resultSet.next()) {
			         row = spreadsheet.createRow(i);
			         cell = row.createCell(1);
			         cell.setCellValue(resultSet.getInt("cand_id"));
			         cell = row.createCell(2);
			         cell.setCellValue(resultSet.getString("cand_name"));
			         cell = row.createCell(3);
			         cell.setCellValue(resultSet.getString("domain"));
			         cell = row.createCell(4);
			         cell.setCellValue(resultSet.getString("experience"));
			         cell = row.createCell(5);
			         cell.setCellValue(resultSet.getString("l1"));
			         cell = row.createCell(6);
			         cell.setCellValue(resultSet.getString("l2"));
			         cell = row.createCell(7);
			         cell.setCellValue(resultSet.getString("hr"));
			         i++;
			      }
			      String username = System.getProperty("user.name");
			      FileOutputStream out = new FileOutputStream(new File("C:/Users/"+username+"/Downloads/CandidatesData.xlsx"));
			      workbook.write(out);
			      out.close();
		 }
		 catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}