package webDriverTestCases;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import CommonClassReusables.MsSql;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.Utility;
import CommonFunctions.FB_Automation_CommonMethods;
import CommonFunctions.LoginPage;
import CommonFunctions.Self_Service_CommonMethods;


public class Golden_Suite  extends BrowserSelection {

	String testName;
	
	@BeforeMethod
	public void commonPage(java.lang.reflect.Method method) throws Throwable
	{
		unhandledException = false;	
		testName = method.getName();
		AGlobalComponents.takeScreenshotIfPass = true;
		driver.navigate().refresh();
	}
	
	public static void isConditionX(boolean condition) {
		if(condition==false){
			throw new SkipException("skipp");
		}
	}


	/*
	 * TC017 : 5.0 Use cases . Manager Login Scenarios :Employment Type Conversion
	 */

	@Test(priority=17)
	public void Self_Service_Automation_TC017() throws Throwable 
	{
		
		logger =report.startTest("Self_Service_Automation_TC017","Manager Login Scenarios : Employment Type Conversion ");
		System.out.println("[INFO]--> Self_Service_Automation_TC017 - TestCase Execution Begins");
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC017");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String firstName ="Test"+Utility.getRandomString(4);
		String lastName =Utility.getRandomString(4);
		String requestNumber = "";
		String scriptName = (String) testData.get("script_name");
		AGlobalComponents.userId=firstName+"."+lastName;
		AGlobalComponents.contractorToPermanentEmployeeConversion=true;
		String accessToBeAdded = (String) testData.get("access_name_1");
			
		/** Login as admin User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
		if(loginStatus){
			
			/**create new asset **/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
			
			/**create identity **/
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC018", "first_name", firstName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018", "last_name", lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018", "full_name", firstName+" "+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018", "user_id", firstName+"."+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018", "asset_code", AGlobalComponents.assetCode);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC023", "first_name", firstName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC023", "last_name", lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC023", "full_name", firstName+" "+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC023", "user_id", firstName+"."+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC023", "asset_code", AGlobalComponents.assetCode);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "first_name", firstName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "last_name", lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "full_name", firstName+" "+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "user_id", firstName+"."+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "asset_code", AGlobalComponents.assetCode);
					
			/** check accesses assigned to Contractor in IDM **/
	 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"",accessToBeAdded,scriptName);
		
	 		/** Launch New Private Browser **/
	 		Utility.switchToNewBrowserDriver();
			
	 		/* Login as Manager */
	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

	 		if(loginStatus){
	 			logger.log(LogStatus.PASS, "Login Successful");
					
	 			/** Employee Type Conversion**/
	 			requestNumber = Self_Service_CommonMethods.employeeConversion(firstName);
	 				
	 			/** checkStatusInMyRequestInbox**/
	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"",accessToBeAdded,requestNumber,scriptName);
	 			
	 			/** Switch to Default Browser **/
	 			Utility.switchToDefaultBrowserDriver();
	 		}
	 		else{
	 			logger.log(LogStatus.FAIL, "Unable to Login as end user. Plz Check Application");
	 		}

	 		/** Validate  status in IDM after  request approved**/
	 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",accessToBeAdded,scriptName);
	 		
	 		/** Logout from Application **/
	 		LoginPage.logout();
		
	 	}
	 	else {
	 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	 	}	
	}




	/*
	 * TC018 : 5.0 Use cases . Manager Login Scenarios : Modify Identity 
	 */

	@Test(priority=18)
	public void Self_Service_Automation_TC018() throws Throwable 
	{
		
		logger =report.startTest("Self_Service_Automation_TC018","Manager Login Scenarios  : Modify Identity");
		System.out.println("[INFO]--> Self_Service_Automation_TC018 - TestCase Execution Begins");
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC018");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String requestNumber = (String) testData.get("request_number");
			
		String firstName =(String) testData.get("first_name");
		String lastName =(String) testData.get("last_name");
		String scriptName =(String) testData.get("script_name");
		AGlobalComponents.userId = (String) testData.get("user_id");
		String parameterToBeModified=(String) testData.get("parameter_tobemodified");
		
				
		/** Login as admin User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
		if(loginStatus){
			
			if((firstName==null||firstName.equals(""))&&(lastName==null||lastName.equals(""))){
				firstName ="Test"+Utility.getRandomString(4);
				lastName =Utility.getRandomString(4);
				AGlobalComponents.userId=firstName+"."+lastName;
				
				/**create new asset **/
				AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
					
				/**create identity **/
			
				FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC023", "first_name", firstName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC023", "last_name", lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC023", "full_name", firstName+" "+lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC023", "user_id", firstName+"."+lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC023", "asset_code", AGlobalComponents.assetCode);
				Utility.updateDataInDatasource("Self_Service_Automation_TC024", "first_name", firstName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC024", "last_name", lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC024", "full_name", firstName+" "+lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC024", "user_id", firstName+"."+lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC024", "asset_code", AGlobalComponents.assetCode);
			}
			
			/** checking the user details in IDM  before modification**/
	 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,parameterToBeModified,"",scriptName);
		
	 		/** Launch New Private Browser **/
	 		Utility.switchToNewBrowserDriver();
			
	 		/* Login as Manager */
	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

	 		if(loginStatus){
	 			logger.log(LogStatus.PASS, "Login Successful");
					
	 			/** Modify Identity **/
	 			requestNumber =Self_Service_CommonMethods.modifyIdentity(firstName,parameterToBeModified,(String) testData.get("request_type"));
	 				 			
	 			/** checkStatusInMyRequestInbox**/
	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,parameterToBeModified,"",requestNumber,scriptName);
		
	 			/** Switch to Default Browser **/
	 			Utility.switchToDefaultBrowserDriver();
	 		}
	 		else{
	 			logger.log(LogStatus.FAIL, "Unable to Login as end user. Plz Check Application");
	 		}

	 		/** Validate  status in IDM after  request approved**/
	 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",parameterToBeModified,scriptName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018", "first_name", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC018", "last_name", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC018", "full_name", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC018", "user_id", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC018", "asset_code", "");
	 		
	 		if(Utility.compareStringValues(parameterToBeModified, "photo")){
	 		
	 			/** checking the user details in IDM  before modification**/
	 			Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,parameterToBeModified,"",scriptName);
	 		
	 			/** Launch New Private Browser **/
	 			Utility.switchToNewBrowserDriver();
	 		
	 			/* Login as Manager */
	 			loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

	 			if(loginStatus){
	 				logger.log(LogStatus.PASS, "Login Successful");
					
	 				/** Modify Identity **/
	 				requestNumber =Self_Service_CommonMethods.modifyIdentity(firstName,parameterToBeModified,(String) testData.get("request_type"));
			
	 				/** checkStatusInMyRequestInbox**/
	 				Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,parameterToBeModified,"",requestNumber,scriptName);
		
	 				/** Switch to Default Browser **/
	 				Utility.switchToDefaultBrowserDriver();
	 			}
	 			else{
	 				logger.log(LogStatus.FAIL, "Unable to Login as end user. Plz Check Application");
	 			}

	 			/** Validate  status in IDM after  request approved**/
	 			Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",parameterToBeModified,scriptName);
	 			Utility.updateDataInDatasource("Self_Service_Automation_TC018", "first_name", "");
				Utility.updateDataInDatasource("Self_Service_Automation_TC018", "last_name", "");
				Utility.updateDataInDatasource("Self_Service_Automation_TC018", "full_name", "");
				Utility.updateDataInDatasource("Self_Service_Automation_TC018", "user_id", "");
	 		}
	 		
	 		/** Logout from Application **/
	 		LoginPage.logout();
		
	 	}
	 	else {
	 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	 	}	
	}

	/*
	 * TC023 :  Physical Access - Request Location access for Others
	*/
		
	@Test(priority=23)
	public void Self_Service_Automation_TC023() throws Throwable 
	{
		
		logger =report.startTest("Self_Service_Automation_TC023"," Physical Access - Request Location access for Others");
		System.out.println("[INFO]--> Self_Service_Automation_TC023 - TestCase Execution Begins");
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC023");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String locationName = (String) testData.get("location_name_1");
		String accessName = (String) testData.get("access_name_1");
			
		String firstName =(String) testData.get("first_name");
		String lastName =(String) testData.get("last_name");
		String scriptName =(String) testData.get("script_name");
		AGlobalComponents.userId = firstName+"."+lastName;
		String requestNumber="";
			
		/** Login as admin User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
		if(loginStatus){
			
			if((firstName==null||firstName.equals(""))&&(lastName==null||lastName.equals(""))){
				firstName ="Test"+Utility.getRandomString(4);
				lastName ="Reqlocation";
				
				/**create new asset **/
				AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
				/**create identity **/
				FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC024", "first_name", firstName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC024", "last_name", lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC024", "full_name", firstName+" "+lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC024", "user_id", firstName+"."+lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC024", "asset_code", AGlobalComponents.assetCode);
			}
				
			/** check accesses assigned to the user in IDM **/
	 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"","",scriptName);
		
	 		/** Launch New Private Browser **/
	 		Utility.switchToNewBrowserDriver();
		
	 		/** Login as manager **/
	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

	 		if(loginStatus){
			
	 			/** Request new location and add access Request **/
	 			requestNumber=Self_Service_CommonMethods.requestNewLocation(locationName,accessName,"Pittsburgh","430 Market St",firstName,lastName);
	 			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "request_number", requestNumber);
	 				
	 			/** logout from the application **/
	 	 		LoginPage.logout();
	 			
	 			/** Login as approver **/
	 	 		loginStatus = LoginPage.loginAEHSC("anna.mordeno", "Alert1234");
			
	 	 		if(loginStatus){

	 	 			/** Approve Access Request by area admin**/
	 	 			Self_Service_CommonMethods.approveRequest("areaAdmin",requestNumber,accessName);
	 	 			
	 		 	 	/** Validate Access Request Status **/
	 	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,scriptName);
	 	 		}else{
	 	 			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
	 	 	
	 	 		}
	 	 	}else{
	 			logger.log(LogStatus.FAIL, "Unable to Login as Manager. Plz Check Application");
	 	 	}
	 		
	 		/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
				
			/** checking status of access assigned in IDM **/
			Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",accessName,scriptName);	
			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "first_name", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "last_name", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "full_name", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "user_id", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "asset_code", "");
			
			/** Logout from Application **/
			LoginPage.logout();
			
		}else{
			logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
		}
		
	}


	/*
	 * TC024 :  emergency Termination
	*/
		
	@Test(priority=24)
	public void Self_Service_Automation_TC024() throws Throwable 
	{

	
	logger =report.startTest("Self_Service_Automation_TC024"," Emergency Termination");
	System.out.println("[INFO]--> Self_Service_Automation_TC024 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC024");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String firstName =(String) testData.get("first_name");
	String lastName =(String) testData.get("last_name");
	String scriptName =(String) testData.get("script_name");
	AGlobalComponents.userId=(String) testData.get("user_id");
	AGlobalComponents.assetCode=(String) testData.get("asset_code");
	String requestNumber=(String) testData.get("request_Number");
		
	/** Login as admin User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
		
		/**create identity **/
		if((firstName==null||firstName.equals(""))&&(lastName==null||lastName.equals(""))){
			firstName ="Test"+Utility.getRandomString(4); lastName ="EmergencyTermination";
			AGlobalComponents.userId=firstName+"."+lastName;
			/**create new asset **/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
			
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "first_name", firstName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "last_name", lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "full_name", firstName+" "+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "user_id", firstName+"."+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "asset_code", AGlobalComponents.assetCode);
			
		}
			
		
		/** check accesses assigned to the user in IDM **/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"","",scriptName);
	
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
	
 		/** Login as manager **/
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
		
 			/** Request for emergency termination **/
 			requestNumber=Self_Service_CommonMethods.emergencyTermination(firstName,lastName);
 		
 		 	/** Validate Access Request Status **/
 	 		Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName, lastName,"","",requestNumber,scriptName);
 	 		
 	 	}else{
 			logger.log(LogStatus.FAIL, "Unable to Login as Manager. Plz Check Application");
 	 	}
 		
 		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
			
		/** checking status of access assigned in IDM **/
		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"","",scriptName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "first_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "last_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "full_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "user_id", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "asset_code", "");
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}
/*
 * TC025 :  Position Access
*/
	
@Test(priority=25)
public void Self_Service_Automation_TC025() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC025"," Position Access for Others");
	System.out.println("[INFO]--> Self_Service_Automation_TC025 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC025");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String position = (String) testData.get("position");
	String accessName = (String) testData.get("access_name_1");
	String scriptName = (String) testData.get("script_name");
	
	String firstName =Utility.getRandomString(6);
	String lastName = Utility.getRandomString(4);
	
	AGlobalComponents.userId = firstName+"."+lastName;
	String requestNumber="";
		
	/** Login as admin User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
	
	if(loginStatus){
		
			/**create new asset **/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
			/**create identity **/
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "first_name", firstName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "last_name", lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "full_name", firstName+" "+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "user_id", AGlobalComponents.userId);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "asset_code", AGlobalComponents.assetCode);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "badge_name", AGlobalComponents.assetName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "first_name", firstName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "last_name", lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "full_name", firstName+" "+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "user_id", AGlobalComponents.userId);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "asset_code", AGlobalComponents.assetCode);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "badge_name", AGlobalComponents.assetName);
			
		
			
		/** check accesses assigned to the user in IDM **/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"",accessName,scriptName);
	
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
	
 		/** Login as manager **/
		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
		
 			/** Request new position and add access Request **/
 			requestNumber=Self_Service_CommonMethods.positionAccess(position,accessName,firstName,lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC025", "request_number", requestNumber);
 				
 			/** logout from the application **/
 	 		LoginPage.logout();
 			
 			/** Login as approver **/
 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("access_owner_username"), (String) testData.get("access_owner_password"));
		
 	 		if(loginStatus){

 	 			/** Approve Access Request by area admin**/
 	 			Self_Service_CommonMethods.approveRequest("access_owner",requestNumber,accessName);
 	 			
 		 	 	/** Validate Access Request Status **/
 	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,scriptName);
  	 			
 	 		}else{
 	 			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
 	 	
 	 		}
 	 	}else{
 			logger.log(LogStatus.FAIL, "Unable to Login as Manager. Plz Check Application");
 	 	}
 		
 		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
			
		/** checking status of access assigned in IDM **/
		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",accessName,scriptName);	
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}

/*
 * TC026 :  Application  Access - Others
*/
	

@Test(priority=26)
public void Self_Service_Automation_TC026() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC026"," Application Access for Others");
	System.out.println("[INFO]--> Self_Service_Automation_TC026 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC026");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String application_name = (String) testData.get("application_name");
	String accessName = (String) testData.get("access_name_1");
	String scriptName = (String) testData.get("script_name");
	String firstName=(String) testData.get("first_name");
	String lastName=(String) testData.get("last_name");
	
	AGlobalComponents.userId = (String) testData.get("user_id");
	AGlobalComponents.assetCode = (String) testData.get("asset_code");
	AGlobalComponents.assetName = (String) testData.get("badge_name");
	String requestNumber="";
		
	/** Login as admin User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
	if(loginStatus){
		
		if(firstName==null||firstName.equals("")){
			
			firstName =Utility.getRandomString(6);
			lastName =Utility.getRandomString(4);
			/**create new asset **/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
						
			/**create identity **/
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "first_name", firstName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "last_name", lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "full_name", firstName+" "+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "user_id", AGlobalComponents.userId);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "asset_code", AGlobalComponents.assetCode);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "badge_name", AGlobalComponents.assetName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "first_name", firstName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "last_name", lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "full_name", firstName+" "+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "user_id", AGlobalComponents.userId);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "asset_code", AGlobalComponents.assetCode);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "badge_name", AGlobalComponents.assetName);
		}
			
		/** check accesses assigned to the user in IDM **/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"",accessName,scriptName);

 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
	
 		/** Login as manager **/
		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
		
 			/** Request new location and add access Request **/
 			requestNumber=Self_Service_CommonMethods.applicationAccess(application_name,accessName,firstName,lastName);
 			Utility.updateDataInDatasource(scriptName, "request_number", requestNumber);
 				
 			/** logout from the application **/
 	 		LoginPage.logout();
 			
 			/** Login as approver **/
 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
		
 	 		if(loginStatus){

 	 			/** Approve Access Request by manager**/
 	 			Self_Service_CommonMethods.approveRequest("manager",requestNumber,accessName);
 	 			
 	 			/** logout from the application **/
 	 			LoginPage.logout();
 	 			
 	 			/** Login as approver **/
 	 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("area_admin_username"), (String) testData.get("area_admin_password"));
 			
 	 	 		if(loginStatus){

 	 	 			/** Approve Access Request by areaAdmin**/
 	 	 			Self_Service_CommonMethods.approveRequest("areaAdmin",requestNumber,accessName);
 	 			
 	 	 			/** Validate Access Request Status **/
 	 				Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,scriptName);
 	 	 		}else{
 	 	 			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
 	 	 		}
 	 	 		
 	 			
 	 		}else{
 	 			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
 	 	
 	 		}
 	 	}else{
 			logger.log(LogStatus.FAIL, "Unable to Login as Manager. Plz Check Application");
 	 	}
 		
 		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
			
		/** checking status of access assigned in IDM **/
		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",accessName,scriptName);	
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "first_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "last_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "full_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "user_id", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "asset_code", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "badge_name", "");
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}

/*
 * TC027 :  IT  Access - Others
*/
	

@Test(priority=27)
public void Self_Service_Automation_TC027() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC027"," IT Access for Others");
	System.out.println("[INFO]--> Self_Service_Automation_TC027 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC027");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String IT_system = (String) testData.get("it_system");
	String accessName = (String) testData.get("access_name_1");
	String scriptName = (String) testData.get("script_name");
	String firstName=(String) testData.get("first_name");
	String lastName=(String) testData.get("last_name");
	
	AGlobalComponents.userId = (String) testData.get("user_id");
	AGlobalComponents.assetCode = (String) testData.get("asset_code");
	AGlobalComponents.assetName = (String) testData.get("badge_name");
	String requestNumber="";
		
	/** Login as admin User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
	if(loginStatus){
		
		if(firstName==null||firstName.equals("")){
			
			firstName =Utility.getRandomString(6);
			lastName =Utility.getRandomString(4);
			/**create new asset **/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
						
			/**create identity **/
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			
		}
			
		/** check accesses assigned to the user in IDM **/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"",accessName,scriptName);

 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
	
 		/** Login as manager **/
		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
		
 			/** Request new IT Access **/
 			requestNumber=Self_Service_CommonMethods.itAccess(IT_system,accessName,firstName,lastName);
 			Utility.updateDataInDatasource(scriptName, "request_number", requestNumber);
 				
 			/** logout from the application **/
 	 		LoginPage.logout();
 			
 			/** Login as approver **/
 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
		
 	 		if(loginStatus){

 	 			/** Approve Access Request by manager**/
 	 			Self_Service_CommonMethods.approveRequest("manager",requestNumber,accessName);
 	 			
 	 			/** logout from the application **/
 	 			LoginPage.logout();
 	 			
 	 			/** Login as approver **/
 	 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("area_admin_username"), (String) testData.get("area_admin_password"));
 			
 	 	 		if(loginStatus){

 	 	 			/** Approve Access Request by areaAdmin**/
 	 	 			Self_Service_CommonMethods.approveRequest("areaAdmin",requestNumber,accessName);
 	 			
 	 	 			/** Validate Access Request Status **/
 	 				Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,scriptName);
 	 	 		}else{
 	 	 			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
 	 	 		}
 	 	 		
 	 			
 	 		}else{
 	 			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
 	 	
 	 		}
 	 	}else{
 			logger.log(LogStatus.FAIL, "Unable to Login as Manager. Plz Check Application");
 	 	}
 		
 		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
			
		/** checking status of access assigned in IDM **/
		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",accessName,scriptName);	
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "first_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "last_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "full_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "user_id", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "asset_code", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "badge_name", "");
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}


}



