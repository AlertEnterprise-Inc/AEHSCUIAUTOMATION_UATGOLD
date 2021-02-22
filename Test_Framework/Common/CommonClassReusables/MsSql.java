package CommonClassReusables;

import java.io.IOException;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;

public class MsSql extends BrowserSelection {

	static Connection conn = null;
	static ResultSet _rs = null;
	static Statement s = null;
	static PreparedStatement _smt = null;
	static ResultSetMetaData _rsmd = null;
	static ArrayList<Object> _resultRecord = null;
	static ArrayList<ArrayList<Object>> _resultList = null;
	
	
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

		conn = DriverManager.getConnection(AGlobalComponents.dbConString);
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

		conn = DriverManager.getConnection(AGlobalComponents.dbConString);
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
			_rs = s.executeQuery(spOrQueryName);
			closeCon();

		} catch (Exception e) {
//			e.printStackTrace();
		}
		return _rs;
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
	
	/* Function to check if UserId Exists in Master User Table */
	
	 public static boolean doesUserIdExist(String userId) {
		String query = "select userid_active from afbcusrmap_master where master_user='"
				+ userId + "'";
		boolean doesUserExist = false;
		try {
			
	//		 ArrayList<ArrayList<Object>> statusData = Database
	//				.getResultsFromDatabase(query);
			 
			 ArrayList<ArrayList<Object>> statusData = (ArrayList<ArrayList<Object>>) readTableWithReturnValue(query);
				
			
			if(statusData.isEmpty())
			{
				doesUserExist=false;
				System.out.println("User ID Does not Exist ..");
			}
			else
			{
				doesUserExist=true;
				System.out.println("Generated UserID already assigned");
			}
			
			} catch (Exception e) {
			System.out.println(e.getMessage());
		
		 }
		
		return doesUserExist;
	 }


	 public static ArrayList<ArrayList<Object>> getResultsFromDatabase(
				String Query) throws ClassNotFoundException, SQLException{
			logger.log(LogStatus.INFO, "Querry in getResultsFromDatabase function :  " + Query);
			Connection _conn = null;
			try {
				String columnType = null;
				String className = null;
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				System.out.println("dbConString :" + AGlobalComponents.dbConString);
				_conn = DriverManager.getConnection(AGlobalComponents.dbConString);
	//			_conn= DriverManager.getConnection("jdbc:sqlserver://192.168.192.163", "AEQA14470", "Alert@1234");
				if (_conn != null) {
					_resultList = new ArrayList<ArrayList<Object>>();
					_smt = _conn.prepareStatement(Query);
					_rs = _smt.executeQuery();
					_rsmd = _rs.getMetaData();
					while (_rs.next()) {
						_resultRecord = new ArrayList<Object>();
						for (int i = 1; i <= _rsmd.getColumnCount(); i++) {
							columnType = _rsmd.getColumnClassName(i);
							className = _rsmd.getColumnTypeName(i);
							if (columnType.toLowerCase().indexOf("string") >= 0) {
								_resultRecord.add((Object) _rs.getString(i));
							} else if (columnType.toLowerCase().indexOf("integer") >= 0) {
								_resultRecord.add((Object) _rs.getInt(i));
							} else if (columnType.toLowerCase().indexOf("date") >= 0) {
								_resultRecord.add((Object) _rs.getDate(i));
							} else if (columnType.toLowerCase().indexOf("bigdecimal") >= 0) {
								_resultRecord.add((Object) _rs.getBigDecimal(i));
							} else if (columnType.toLowerCase().indexOf("timestamp") >= 0) {
								_resultRecord.add((Object) _rs.getTimestamp(i));
							} else if (columnType.toLowerCase().indexOf("boolean") >= 0) {
								_resultRecord.add((Object) _rs.getBoolean(i));
							} else if (columnType.toLowerCase().indexOf("byte") >= 0) {
								_resultRecord.add((Object) _rs.getByte(i));
							} else if (columnType.toLowerCase().indexOf("double") >= 0) {
								_resultRecord.add((Object) _rs.getDouble(i));
							} else if (columnType.toLowerCase().indexOf("float") >= 0) {
								_resultRecord.add((Object) _rs.getFloat(i));
							} else if (columnType.toLowerCase().indexOf("long") >= 0) {
								_resultRecord.add((Object) _rs.getLong(i));
							} else if (columnType.toLowerCase().indexOf("short") >= 0) {
								_resultRecord.add((Object) _rs.getShort(i));
							} else if (className.toLowerCase().indexOf("image") >= 0) {
								_resultRecord.add((Object) _rs.getString(i));
							} else if (columnType.toLowerCase().indexOf("clob") >= 0) {
								Clob clob = _rs.getClob(i);
								Reader r = clob.getCharacterStream();
						        StringBuffer buffer = new StringBuffer();
						        int ch;
						         try {
									while ((ch = r.read())!=-1) {
									    buffer.append(""+(char)ch);
									 }
								_resultRecord.add((Object) buffer.toString());	
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							
						}
						_resultList.add(_resultRecord);
					}
					_smt.close();
					_rs.close();
				} else {
					System.err.println("Database connection not set");
				}
			} catch (SQLException se) {
				logger.log(LogStatus.ERROR, se);
			} catch (NullPointerException ne) {
				logger.log(LogStatus.ERROR, ne);
			}
			logger.log(LogStatus.INFO, "Result from getResultsFromDatabase function :  " + _resultList);
			return _resultList;
		}


	public static int setResultsToDatabase(String query) throws ClassNotFoundException {
		
		logger.log(LogStatus.INFO, "Querry in getResultsFromDatabase function :  " + query);
	
		Connection _conn = null;
		int queryStatus = -1;
		try {
		
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("dbConString :" + AGlobalComponents.dbConString);
			_conn = DriverManager.getConnection(AGlobalComponents.dbConString);
			if (_conn != null) {
				_smt = _conn.prepareStatement(query);
				try{
					queryStatus  = _smt.executeUpdate();
				}
				finally{
					try{
						_smt.close();
					}
					catch(Exception ignore){}
				}
			}
		} catch (SQLException se) {
			logger.log(LogStatus.ERROR, se);
		} catch (NullPointerException ne) {
			logger.log(LogStatus.ERROR, ne);	
		}
		logger.log(LogStatus.INFO, "Result from getResultsFromDatabase function :  " + _resultList);
		return queryStatus;
	}


	public static int setResultsToCCUREDatabase(String query) throws ClassNotFoundException {

		logger.log(LogStatus.INFO, "Querry in getResultsFromDatabase function :  " + query);
	
		Connection _conn = null;
		int queryStatus = -1;
		try {
		
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("dbConString :" + AGlobalComponents.ccureDbConString);
			_conn = DriverManager.getConnection(AGlobalComponents.ccureDbConString);
			if (_conn != null) {
				_smt = _conn.prepareStatement(query);
				try{
					queryStatus  = _smt.executeUpdate();
				}
				finally{
					try{
						_smt.close();
					}
					catch(Exception ignore){}
				}
			}
		} catch (SQLException se) {
			logger.log(LogStatus.ERROR, se);
		} catch (NullPointerException ne) {
			logger.log(LogStatus.ERROR, ne);	
		}
		logger.log(LogStatus.INFO, "Result from getResultsFromDatabase function :  " + _resultList);
		return queryStatus;
	}


	public static ArrayList<ArrayList<Object>> getResultsFromCCUREDatabase(String Query) throws ClassNotFoundException {

		logger.log(LogStatus.INFO, "Querry in getResultsFromDatabase function :  " + Query);
		Connection _conn = null;
		try {
			String columnType = null;
			String className = null;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("dbConString :" + AGlobalComponents.ccureDbConString);
			_conn = DriverManager.getConnection(AGlobalComponents.ccureDbConString);
//			_conn= DriverManager.getConnection("jdbc:sqlserver://192.168.192.163", "AEQA14470", "Alert@1234");
			if (_conn != null) {
				_resultList = new ArrayList<ArrayList<Object>>();
				_smt = _conn.prepareStatement(Query);
				_rs = _smt.executeQuery();
				_rsmd = _rs.getMetaData();
				while (_rs.next()) {
					_resultRecord = new ArrayList<Object>();
					for (int i = 1; i <= _rsmd.getColumnCount(); i++) {
						columnType = _rsmd.getColumnClassName(i);
						className = _rsmd.getColumnTypeName(i);
						if (columnType.toLowerCase().indexOf("string") >= 0) {
							_resultRecord.add((Object) _rs.getString(i));
						} else if (columnType.toLowerCase().indexOf("integer") >= 0) {
							_resultRecord.add((Object) _rs.getInt(i));
						} else if (columnType.toLowerCase().indexOf("date") >= 0) {
							_resultRecord.add((Object) _rs.getDate(i));
						} else if (columnType.toLowerCase().indexOf("bigdecimal") >= 0) {
							_resultRecord.add((Object) _rs.getBigDecimal(i));
						} else if (columnType.toLowerCase().indexOf("timestamp") >= 0) {
							_resultRecord.add((Object) _rs.getTimestamp(i));
						} else if (columnType.toLowerCase().indexOf("boolean") >= 0) {
							_resultRecord.add((Object) _rs.getBoolean(i));
						} else if (columnType.toLowerCase().indexOf("byte") >= 0) {
							_resultRecord.add((Object) _rs.getByte(i));
						} else if (columnType.toLowerCase().indexOf("double") >= 0) {
							_resultRecord.add((Object) _rs.getDouble(i));
						} else if (columnType.toLowerCase().indexOf("float") >= 0) {
							_resultRecord.add((Object) _rs.getFloat(i));
						} else if (columnType.toLowerCase().indexOf("long") >= 0) {
							_resultRecord.add((Object) _rs.getLong(i));
						} else if (columnType.toLowerCase().indexOf("short") >= 0) {
							_resultRecord.add((Object) _rs.getShort(i));
						} else if (className.toLowerCase().indexOf("image") >= 0) {
							_resultRecord.add((Object) _rs.getString(i));
						} else if (columnType.toLowerCase().indexOf("clob") >= 0) {
							Clob clob = _rs.getClob(i);
							Reader r = clob.getCharacterStream();
					        StringBuffer buffer = new StringBuffer();
					        int ch;
					         try {
								while ((ch = r.read())!=-1) {
								    buffer.append(""+(char)ch);
								 }
							_resultRecord.add((Object) buffer.toString());	
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
					}
					_resultList.add(_resultRecord);
				}
				_smt.close();
				_rs.close();
			} else {
				System.err.println("Database connection not set");
			}
		} catch (SQLException se) {
			logger.log(LogStatus.ERROR, se);
		} catch (NullPointerException ne) {
			logger.log(LogStatus.ERROR, ne);
		}
		logger.log(LogStatus.INFO, "Result from getResultsFromDatabase function :  " + _resultList);
		return _resultList;
	}


	public static int setResultsToPostgreSQLDatabase(String query) throws ClassNotFoundException {


		logger.log(LogStatus.INFO, "Querry in getResultsFromDatabase function :  " + query);
	
		Connection _conn = null;
		int queryStatus = -1;
		try {
		
			Class.forName("org.postgresql.Driver");

			 _conn = DriverManager.getConnection(AGlobalComponents.postgresqlDbUrl, AGlobalComponents.postgresqlDbUserName, AGlobalComponents.postgresqlDbPassword);
		
			if (_conn != null) {
				_smt = _conn.prepareStatement(query);
				try{
					queryStatus  = _smt.executeUpdate();
				}
				finally{
					try{
						_smt.close();
					}
					catch(Exception ignore){}
				}
			}
		} catch (SQLException se) {
			logger.log(LogStatus.ERROR, se);
		} catch (NullPointerException ne) {
			logger.log(LogStatus.ERROR, ne);	
		}
		logger.log(LogStatus.INFO, "Result from getResultsFromDatabase function :  " + _resultList);
		return queryStatus;
	
	}
}
