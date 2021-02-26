package CommonClassReusables;

import java.sql.SQLException;

import java.util.ArrayList;
import com.relevantcodes.extentreports.LogStatus;



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

		String query = "select format(valid_from,'MM-dd-yyyy') from identity_user where master_identity_id='"+userId+"'";
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

		String query = "select format(valid_to,'MM-dd-yyyy') from identity_user where master_identity_id='"+userId+"'";
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

	public static void deleteUserInMasterTable(String userId) throws ClassNotFoundException {

		int noOfRecordsUpdated=-1;
		String query = "delete from identity_user where master_identity_id='"+userId+"'";	
		noOfRecordsUpdated = MsSql.setResultsToDatabase(query);
		if(noOfRecordsUpdated>0) {		
			System.out.println(noOfRecordsUpdated+" rows affected");
		}
		else {
			System.out.println("failed to delete user in identity system");
		}
	
	}

	public static String getIdentityIdOfUser(String userId) throws ClassNotFoundException, SQLException {

		String query = "select id from identity_user where master_identity_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String identityId =rs.get(0).get(0);	
		if(identityId!=null) {
			System.out.println("IdentityId: "+identityId);
		}
		else {
			System.out.println("Unable to fetch IdentityId");
		}
		return identityId;
	
	}

	public static void deleteUserInSystemTable(String userId) throws ClassNotFoundException, SQLException {

		int noOfRecordsUpdated=-1;
		String query = "delete from identity_system where identity_id=(select id from identity_user where master_identity_id='"+userId+"')";	
		noOfRecordsUpdated = MsSql.setResultsToDatabase(query);
		if(noOfRecordsUpdated>0) {		
			System.out.println(noOfRecordsUpdated+" rows affected");
		}
		else {
			System.out.println("failed to delete user in identity system");
		}
	}

	public static String getEndDate(String reconConfigId,String connectorName) throws ClassNotFoundException, SQLException {

		String query = "select top 1 changed_on from recon_config where id="+reconConfigId+" and system_id=(select id from system where text='"+connectorName+"') and entity_id='42' order by changed_on desc";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String endDate =rs.get(0).get(0);	
		if(endDate!=null) {
			System.out.println("EndDate: "+endDate);
		}
		else {
			System.out.println("Unable to fetch ManagerId");
		}
		return endDate;
	}

	public static ArrayList<String> getReconConfigIdList() throws ClassNotFoundException, SQLException {

		ArrayList<String> reconConfigList = new ArrayList<String>();
		String query = "select recon_config_id from recon_monitor order by 1 desc";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		if(!rs.isEmpty()) {
			for(ArrayList<String> eachResult: rs) {
				reconConfigList.add(eachResult.get(0));
			}
		}
		else {
			System.out.println("Unable to get recon Config List");
		}
		return reconConfigList;
	}

	public static String getEntityType(String reconConfigId) throws ClassNotFoundException, SQLException {

		String query = "select type from job where config_id="+reconConfigId+" order by changed_on desc";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String endDate =rs.get(0).get(0);	
		if(endDate!=null) {
			System.out.println("entityType: "+endDate);
		}
		else {
			System.out.println("Unable to fetch entityType");
		}
		return endDate;
	}

	public static boolean assignBadgeToUserInCCURE(int objectId,String name,String badgeId,String activationDate,String expirationDate,int credentialObjectId) throws ClassNotFoundException {
		int noOfRecordsUpdated=-1;
		String query="insert into [ACVSCore].[Access].[Credential](ObjectID,Name,AccessType,ClassType,ActivationDateTime,ExpirationDateTime,CardNumber,PersonnelId) "
				+ "values("+credentialObjectId+",'"+name+"',0,'SoftwareHouse.NextGen.Common.SecurityObjects.Credential','"+activationDate+"','"+expirationDate+"','"+badgeId+"','"+objectId+"')";
		noOfRecordsUpdated = MsSql.setResultsToCCUREDatabase(query);
		if(noOfRecordsUpdated>0) {		
			System.out.println(noOfRecordsUpdated+" rows affected");
			return true;
		}
		else {
			System.out.println("failed to delete user in identity system");
			return false;
		}
	}

	public static int getMaxCredentialObjectId() throws ClassNotFoundException {

		String query = "Select max(ObjectID) from [ACVSCore].[Access].[Credential]";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromCCUREDatabase(query));
		int objectId =Integer.parseInt(rs.get(0).get(0));	
		if(objectId!=0) {
			System.out.println("MaxObjectId: "+objectId);
		}
		else {
			System.out.println("Unable to fetch maxObjectId");
		}
		return objectId;
	}

	public static int getObjectIdOfTheUser(String name) throws ClassNotFoundException {

		String query = "Select ObjectID from [ACVSCore].[Access].[Personnel] where Name='"+name+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromCCUREDatabase(query));
		int objectId =Integer.parseInt(rs.get(0).get(0));	
		if(objectId!=0) {
			System.out.println("ObjectId of the user: "+objectId);
		}
		else {
			System.out.println("Unable to get ObjectId of the user");
		}
		return objectId;
	}

	public static String getUserdataExtId(String userId) throws ClassNotFoundException, SQLException {

		String query = "select ext_id from stg_user_data where user_id='"+userId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String identityId =rs.get(0).get(0);	
		if(identityId!=null) {
			System.out.println("UserDataExtId: "+identityId);
		}
		else {
			System.out.println("Unable to fetch UserDataExtId");
		}
		return identityId;
	}

	public static String getBadgeValidToFromStagingTable(String userDatExtId) throws ClassNotFoundException, SQLException {

		String query = "select valid_to from stg_user_badge_data where user_data_ext_id='"+userDatExtId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String badgeValidTo =rs.get(0).get(0);	
		if(badgeValidTo!=null) {
			System.out.println("BadgeValidTo: "+badgeValidTo);
		}
		else {
			System.out.println("Unable to fetch BadgeValidTo");
		}
		return badgeValidTo;
	}

	public static String getBadgeValidFromFromStagingTable(String userDatExtId) throws ClassNotFoundException, SQLException {

		String query = "select valid_from from stg_user_badge_data where user_data_ext_id='"+userDatExtId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String badgeId =rs.get(0).get(0);	
		if(badgeId!=null) {
			System.out.println("BadgeValidFrom: "+badgeId);
		}
		else {
			System.out.println("Unable to fetch BadgeValidFrom");
		}
		return badgeId;
	}

	public static String getBadgeIdFromStagingTable(String userDatExtId) throws ClassNotFoundException, SQLException {


		String query = "select source_id from stg_user_badge_data where user_data_ext_id='"+userDatExtId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String badgeId =rs.get(0).get(0);	
		if(badgeId!=null) {
			System.out.println("BadgeIs: "+badgeId);
		}
		else {
			System.out.println("Unable to fetch BadgeId");
		}
		return badgeId;
	
		
	}

	public static String getBadgeTypeFromStagingTable(String userDataExtId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getAssetId(String identityId) throws ClassNotFoundException, SQLException {

		String query = "select asset_id from identity_asset where identity_id='"+identityId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String assetId =rs.get(0).get(0);	
		if(assetId!=null) {
			System.out.println("UserDataExtId: "+assetId);
		}
		else {
			System.out.println("Unable to fetch UserDataExtId");
		}
		return assetId;	
	}

	public static String getBadgeValidFromFromMasterTable(String identityId) throws ClassNotFoundException, SQLException {
		
		String query = "select format(valid_from,'MM-dd-yyyy HH:mm:ss') from identity_asset where identity_id='"+identityId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String badgeId =rs.get(0).get(0);	
		if(badgeId!=null) {
			System.out.println("UserDataExtId: "+badgeId);
		}
		else {
			System.out.println("Unable to fetch UserDataExtId");
		}
		return badgeId;				
	}

	public static String getBadgeIdFromMasterTable(String assetId) throws ClassNotFoundException, SQLException {
	
		String query = "select source_id from asset where id='"+assetId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String badgeId =rs.get(0).get(0);	
		if(badgeId!=null) {
			System.out.println("BadgeId: "+badgeId);
		}
		else {
			System.out.println("Unable to fetch BadgeId");
		}
		return badgeId;				
	}

	public static String getBadgeValidToFromMasterTable(String identityId) throws ClassNotFoundException, SQLException {
	
		String query = "select format(valid_to,'MM-dd-yyyy HH:mm:ss') from identity_asset where identity_id='"+identityId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String validTo =rs.get(0).get(0);	
		if(validTo!=null) {
			System.out.println("BadgeValidTo: "+validTo);
		}
		else {
			System.out.println("Unable to fetch BadgeValidTo");
		}
		return validTo;				
	}

	public static String getBadgeTypeFromMasterTable(String assetId) throws ClassNotFoundException, SQLException {
	
		String query = "select type from asset where id='"+assetId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String type =rs.get(0).get(0);	
		if(type!=null) {
			System.out.println("BadgeType: "+type);
		}
		else {
			System.out.println("Unable to fetch BadgeType");
		}
		return type;				
	}

	public static String getAssetCodeFromMasterTable(String assetId) throws ClassNotFoundException, SQLException {
	
		String query = "select ext_id from asset where id='"+assetId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String type =rs.get(0).get(0);	
		if(type!=null) {
			System.out.println("assetCode: "+type);
		}
		else {
			System.out.println("Unable to fetch assetCode");
		}
		return type;				
	}

	public static int getAccessId(String name) throws ClassNotFoundException {

		String query = "Select ObjectID from [ACVSCore].[Access].[Clearance] where Name='"+name+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromCCUREDatabase(query));
		int objectId =Integer.parseInt(rs.get(0).get(0));	
		if(objectId!=0) {
			System.out.println("ObjectId of the user: "+objectId);
		}
		else {
			System.out.println("Unable to get ObjectId of the user");
		}
		return objectId;
	}

	public static boolean assignAccessToUserInCCURE(int userId,int accessId, String activationDate, String expirationDate) throws ClassNotFoundException {

		int noOfRecordsUpdated=-1;
		String query="insert into [ACVSCore].[Access].[PersonnelClearancePair](ClassType,PersonnelID,ClearanceID,StartDateTime,EndDateTime)"
				+ "values('SoftwareHouse.NextGen.Common.SecurityObjects.Credential',"+userId+",'"+accessId+"','"+activationDate+"','"+expirationDate+"')";
		noOfRecordsUpdated = MsSql.setResultsToCCUREDatabase(query);
		if(noOfRecordsUpdated>0) {		
			System.out.println(noOfRecordsUpdated+" rows affected");
			return true;
		}
		else {
			System.out.println("failed to delete user in identity system");
			return false;
		}
	
	}

	public static String getAccessNameFromStagingTable(String userDataExtId) throws ClassNotFoundException, SQLException {

		String query = "select description from stg_user_role_data where user_data_ext_id='"+userDataExtId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String accessName =rs.get(0).get(0);	
		if(accessName!=null) {
			System.out.println("AccessName: "+accessName);
		}
		else {
			System.out.println("Unable to fetch AccessName");
		}
		return accessName;
	}

	public static String getAccessValidFromFromStagingTable(String userDataExtId) throws ClassNotFoundException, SQLException {

		String query = "select description from stg_user_role_data where user_data_ext_id='"+userDataExtId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String accessName =rs.get(0).get(0);	
		if(accessName!=null) {
			System.out.println("AccessValidFrom: "+accessName);
		}
		else {
			System.out.println("Unable to fetch AccessValidFrom");
		}
		return accessName;
	}

	public static String getAccessIdFromMasterTable(String identityId) throws ClassNotFoundException, SQLException {

		String query = "select access_id from identity_access where identity_id='"+identityId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String assetId =rs.get(0).get(0);	
		if(assetId!=null) {
			System.out.println("AccessId: "+assetId);
		}
		else {
			System.out.println("Unable to fetch AccessId");
		}
		return assetId;	
	}

	public static String getAccessValidFromFromMasterTable(String identityId) throws ClassNotFoundException, SQLException {

		String query = "select valid_from from identity_access where identity_id='"+identityId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String assetId =rs.get(0).get(0);	
		if(assetId!=null) {
			System.out.println("AccessValidFrom: "+assetId);
		}
		else {
			System.out.println("Unable to fetch AccessValidFrom");
		}
		return assetId;	
	}

	public static String getAccessValidToFromMasterTable(String identityId) throws ClassNotFoundException, SQLException {

		String query = "select valid_to from identity_access where identity_id='"+identityId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String assetId =rs.get(0).get(0);	
		if(assetId!=null) {
			System.out.println("AccessValidTo: "+assetId);
		}
		else {
			System.out.println("Unable to fetch AccessValidTo");
		}
		return assetId;	
	}

	public static String geAccessNameFromMasterTable(String accessId) throws ClassNotFoundException, SQLException {

		String query = "select text from access_role where id='"+accessId+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String assetId =rs.get(0).get(0);	
		if(assetId!=null) {
			System.out.println("AccessName: "+assetId);
		}
		else {
			System.out.println("Unable to fetch AccessName");
		}
		return assetId;	
	}

	public static boolean createUserInHRDb(String userId, String firstName, String lastName, String name,
			String validFrom, String validTo, String jobTitle, String identityType,String payStatus, String managerId) throws ClassNotFoundException {

		int noOfRecordsUpdated=-1;
		String query="insert into aepdemo.hr_data(user_id,fullname,firstname,lastname,identitytype,managerid,jobtitle,paystatus,validfrom,validto)" + 
				"values('"+userId+"','"+name+"','"+firstName+"','"+lastName+"','"+identityType+"','"+managerId+"','"+jobTitle+"','Hire','"+validFrom+"','"+validTo+"');";
				noOfRecordsUpdated = MsSql.setResultsToPostgreSQLDatabase(query);
		if(noOfRecordsUpdated>0) {		
			System.out.println(noOfRecordsUpdated+" rows affected");
			return true;
		}
		else {
			System.out.println("failed to create user in HR DB");
			return false;
		}	
	}

	public static boolean updateUserInHrdb(String userId, String jobTitle) throws ClassNotFoundException {
		int noOfRecordsUpdated=-1;
		String query="update aepdemo.hr_data set jobtitle ='"+jobTitle+"' where user_id='"+userId+"'";
				noOfRecordsUpdated = MsSql.setResultsToPostgreSQLDatabase(query);
		if(noOfRecordsUpdated>0) {		
			System.out.println(noOfRecordsUpdated+" rows affected");
			return true;
		}
		else {
			System.out.println("failed to update user in HR DB");
			return false;
		}
	}
	
	public static String checkAssetStatusInAMAG(String cardNumber) throws ClassNotFoundException {
		String query = "Select Inactive from [multiMAX].[dbo].[CardInfoTable] where CardNumberDisplay='"+cardNumber+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromAMAGDatabase(query));
		//System.out.println("result set of query is: "+rs);
		//System.out.println("result set of query 1 is: "+rs.get(0));
		System.out.println("status is: "+rs.get(0).get(0));
		String status =rs.get(0).get(0);	
		if(status!=null) {
			System.out.println("status of asset is: "+status);
		}
		else {
			System.out.println("Unable to fetch status");
		}
		return status;
	}
}
