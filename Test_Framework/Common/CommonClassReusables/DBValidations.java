package CommonClassReusables;

import java.sql.SQLException;
import java.util.ArrayList;
import com.relevantcodes.extentreports.LogStatus;


;

public class DBValidations extends BrowserSelection{
	
	public static String returnApplicationDateFormat() {
		String dateFormat = "";
		try {
			ArrayList<ArrayList<Object>> _dateFormatResults = MsSql.getResultsFromDatabase("select config_param_value from AFBMAPP_CONFIG where CONFIG_PARAM_NAME = 'DATE_FORMAT_LABEL'");
			dateFormat = String.valueOf(_dateFormatResults.get(0).get(0));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.log(LogStatus.ERROR, e);
		}
		return dateFormat;

	}

	public static String returnApplicationUIDateFormat() {
		String dateFormat = "";
		try {
			ArrayList<ArrayList<Object>> _dateFormatResults = MsSql
					.getResultsFromDatabase("select CUSTOM_VALUE, RESOURCE_VALUE, CASE WHEN CUSTOM_VALUE is NULL THEN RESOURCE_VALUE ELSE CUSTOM_VALUE END AS DATEFORMAT from AFBCRES_BUNDLE where resource_key like 'DATE.FORMAT.JAVA'");
			dateFormat = String.valueOf(_dateFormatResults.get(0).get(2));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.log(LogStatus.ERROR, e);
		}
		return dateFormat;

	}


	
	public static String returnApplicationTimeFormat() {
 		String timeFormat = "";
 		try {
 			ArrayList<ArrayList<Object>> _timeFormatResults = MsSql
 					.getResultsFromDatabase("select config_param_value from AFBMAPP_CONFIG where CONFIG_PARAM_NAME = 'TIME_FORMAT_LABEL'");
 			if(_timeFormatResults != null && !_timeFormatResults.isEmpty()){
 				timeFormat = String.valueOf(_timeFormatResults.get(0).get(0));
 			}
 		} catch (Exception e) {
 			System.err.println(e.getMessage());
 			logger.log(LogStatus.ERROR, e);
 		}
 		return timeFormat;

 	}

	public static String getFirstNameOfUser(String userId) throws ClassNotFoundException, SQLException {

		String query = "select first_name from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String firstName =rs.get(0).get(0);	
		if(firstName!=null) {
			System.out.println("firstname: "+firstName);
		}
		else {
			System.out.println("Unable to firstname");
		}
		return firstName;
	}

	public static String getLastNameOfUser(String userId) throws ClassNotFoundException, SQLException {

		String query = "select last_name from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String lastName =rs.get(0).get(0);	
		if(lastName!=null) {
			System.out.println("lastname: "+lastName);
		}
		else {
			System.out.println("Unable to fetch lastname");
		}
		return lastName;
	}

	public static String getTypeofUserId(String userId) throws ClassNotFoundException, SQLException {

		String query = "select Lower(type) from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String type =rs.get(0).get(0);	
		if(type!=null) {
			System.out.println("worker type: "+type);
		}
		else {
			System.out.println("Unable to fetch worker type");
		}
		return type;
	}

	public static String getValidFromOfUser(String userId) throws ClassNotFoundException, SQLException {

		String query = "select format(valid_from,'dd-MM-yyyy') from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String validFrom =rs.get(0).get(0);	
		validFrom=validFrom.split(" ")[0];
		if(validFrom!=null) {
			System.out.println("ValidFrom: "+validFrom);
		}
		else {
			System.out.println("Unable to fetch Valid From");
		}
		return validFrom;
	}

	public static String getValidToOfUser(String userId) throws ClassNotFoundException, SQLException {

		String query = "select format(valid_to,'dd-MM-yyyy') from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String validTo =rs.get(0).get(0);
		validTo=validTo.split(" ")[0];
		if(validTo!=null) {
			System.out.println("ValidTo: "+validTo);
		}
		else {
			System.out.println("Unable to fetch ValidTo");
		}
		return validTo;
	}

	public static String getEmailIdOfUser(String userId) throws ClassNotFoundException, SQLException {

		String query = "select email from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String email =rs.get(0).get(0);	
		if(email!=null) {
			System.out.println("EmailId: "+email);
		}
		else {
			System.out.println("Unable to fetch EmailId");
		}
		return email;
	}

	public static String getPhoneNoOfUser(String userId) throws ClassNotFoundException, SQLException {

		String query = "select phone from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String phone =rs.get(0).get(0);	
		if(phone!=null) {
			System.out.println("worker type: "+phone);
		}
		else {
			System.out.println("Unable to fetch worker type");
		}
		return phone;
	}

	public static String getWorkLocationOfUser(String userId) throws ClassNotFoundException, SQLException {

		String query = "select work_location from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String workLocation =rs.get(0).get(0);	
		if(workLocation!=null) {
			System.out.println("work location: "+workLocation);
		}
		else {
			System.out.println("Unable to fetch work location");
		}
		return workLocation;
	}

	public static String getCityOfUser(String userId) throws ClassNotFoundException, SQLException {

		String query = "select city from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String city =rs.get(0).get(0);	
		if(city!=null) {
			System.out.println("city: "+city);
		}
		else {
			System.out.println("Unable to fetch worker type");
		}
		return city;
	}

	public static String getManagerIdOfUser(String userId) throws ClassNotFoundException, SQLException {

		String query = "select manager_source_id from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String manageId =rs.get(0).get(0);	
		if(manageId!=null) {
			System.out.println("ManagerId: "+manageId);
		}
		else {
			System.out.println("Unable to fetch ManagerId");
		}
		return manageId;
	}
}
