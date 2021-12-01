import java.io.*;
import java.sql.*;
import java.util.Scanner;

class hw4 {
	public static void main (String[] args) throws SQLException, IOException, java.lang.ClassNotFoundException {
    	
		String userID;
		String password;

		Scanner myScanner = new Scanner(System.in);
	
		System.out.print("enter Oracle user id: ");
		userID = myScanner.next();
		System.out.print("enter Oracle password for " + userID + ": ");
		password = myScanner.next();
		logOn(userID, password);
	
	}
	
	public static void logOn(String userID, String password){
		String subString;
		String deptInstructor;
		ResultSet result;
		String q;
		String p;
		ResultSet answer;
		
		try (
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", 
			userID, password);
			Statement s=con.createStatement();
		) {
			Scanner myScanner = new Scanner(System.in);
			System.out.print("Enter the course ID for the course for which you seek enrollment information: ");
			subString = myScanner.next();
			q = "select to_char (student.id, '09999') as studentID, student.name as studentName, section.year as year, section.semester as semester, to_char (section.sec_id, '09') as section from student, section where course_id = " + subString; 
			result = s.executeQuery(q);
			
			if (!result.next()) System.out.println ("Empty result.");
			else {
				do {
					System.out.println (result.getString("studentID") + " " 
					+ result.getString("studentName") + " " +  result.getString("year") 
					+ " " + result.getString("semester") + " " + result.getString("section"));
				} while (result.next());
			}
			
			s.close();
			con.close();
		}
		catch (Exception sqle){
			System.out.println(sqle.toString());
			Scanner myScanner = new Scanner(System.in);
			String id;
			String pass;
			System.out.print("connect error. Re-enter login data:");
			System.out.print("enter Oracle user id: ");
			id = myScanner.next();
			if (id.equals("")){
				System.exit(0);
			}
			System.out.print("enter Oracle password for " + id + ": ");
			pass = myScanner.next();
			logOn(id, pass);
		}
	}
}

