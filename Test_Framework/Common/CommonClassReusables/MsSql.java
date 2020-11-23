package CommonClassReusables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import CommonClassReusables.AGlobalComponents;

public class MsSql extends AGlobalComponents {

	static Connection conn = null;
	static ResultSet rs = null;
	static Statement s = null;

	
	/**
	* <h1>openCon</h1>
	* This is a Method to Open DB Connection
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	none
	* @return  	none
	*/
	
	public static void openCon() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		conn = DriverManager.getConnection(dbConString);
		if (conn != null) {
			System.out.println("Successfully: Connected to Database");
		} else
			System.out.println("Connection returned null!");
	}

	
	/**
	* <h1>closeCon</h1>
	* This is a Method to Close DB Connection
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	none
	* @return  	none
	*/
	
	public static void closeCon() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		conn = DriverManager.getConnection(dbConString);
		if (conn != null) {
			conn.close();
			System.out.println("Successfully: Disconnected to Database");
		} else
			System.out.println("Connection returned null!");
	}

	
	/**
	* <h1>readTableWithReturnValue</h1>
	* This is a Method to get Result Set for DB Operations
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	String spOrQueryName
	* @return  	ResultSet
	*/
	
	public static ResultSet readTableWithReturnValue(String spOrQueryName) {
		try {
			openCon();
			s = conn.createStatement();
			rs = s.executeQuery(spOrQueryName);
			closeCon();

		} catch (Exception e) {
//			e.printStackTrace();
		}
		return rs;
	}


	/**
	* <h1>executeSqlQuery</h1>
	* This is a Method to Execute Query for DB Operations
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	String spOrQueryName
	* @return  	none
	*/
	
	public static void executeSqlQuery(String spOrQueryName) {
		try {
			openCon();
			s = conn.createStatement();
			s.executeQuery(spOrQueryName);
			closeCon();
	
		} catch (Exception e) {
	//		e.printStackTrace();
		}
		
	}

}
