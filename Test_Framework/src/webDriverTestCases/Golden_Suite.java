package webDriverTestCases;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.Utility;
import CommonFunctions.ApiMethods;
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

@Test(priority=17,enabled=true)
public void Self_Service_Automation_TC017() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC017","Manager Login Scenarios : Employment Type Conversion ");
	System.out.println("[INFO]--> Self_Service_Automation_TC017 - TestCase Execution Begins");
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC017");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String firstName ="Auto"+Utility.getRandomString(4);
	String lastName ="Test"+Utility.getRandomString(4);
	String requestNumber = "";
	String scriptName = (String) testData.get("script_name");
	AGlobalComponents.userId=firstName+"."+lastName;
	AGlobalComponents.contractorToPermanentEmployeeConversion=true;
	String accessToBeAdded = (String) testData.get("access_name_1");
		
	/** Login as admin User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
		
		/**create new asset **/
	//	AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
		
		/**create identity **/
		FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "first_name", firstName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "last_name", lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "full_name", firstName+" "+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "user_id", firstName+"."+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "asset_code", AGlobalComponents.assetCode);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "first_name", firstName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "last_name", lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "full_name", firstName+" "+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "user_id", firstName+"."+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "asset_code", AGlobalComponents.assetCode);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "first_name", firstName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "last_name", lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "full_name", firstName+" "+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "user_id", firstName+"."+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "asset_code", AGlobalComponents.assetCode);
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
 		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "badge_name", AGlobalComponents.assetName);
 		
				
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
 * TC018 : 5.0 Use cases . Manager Login Scenarios : Modify Identity : photo
 */

@Test(priority=18,enabled=true)
public void Self_Service_Automation_TC018_1() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC018_1","Manager Login Scenarios  : Modify Identity");
	System.out.println("[INFO]--> Self_Service_Automation_TC018_1 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC018_1");
	
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
			firstName ="Auto"+Utility.getRandomString(4);
			lastName ="Test"+Utility.getRandomString(4);
			AGlobalComponents.userId=firstName+"."+lastName;
			
			/**create new asset **/
	//		AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
			/**create identity **/
		
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			
			Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "first_name", firstName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "last_name", lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "full_name", firstName+" "+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "user_id", firstName+"."+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "asset_code", AGlobalComponents.assetCode);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "first_name", firstName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "last_name", lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "full_name", firstName+" "+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "user_id", firstName+"."+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "asset_code", AGlobalComponents.assetCode);
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
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "badge_name", AGlobalComponents.assetName);
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
 			Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,parameterToBeModified,"",scriptName);
 			Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "first_name", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "last_name", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "full_name", "");
			Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "user_id", "");
 		}
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	
 	}
 	else {
 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
 	}	
}

/*
 * TC018 : 5.0 Use cases . Manager Login Scenarios : Modify Identity :lastName
 */

@Test(priority=18,enabled=true)
public void Self_Service_Automation_TC018_2() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC018_2","Manager Login Scenarios  : Modify Identity");
	System.out.println("[INFO]--> Self_Service_Automation_TC018_2 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC018_2");
	
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
			firstName ="Auto"+Utility.getRandomString(4);
			lastName ="Test"+Utility.getRandomString(4);
			AGlobalComponents.userId=firstName+"."+lastName;
			
			/**create new asset **/
	//		AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
			/**create identity **/
		
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "first_name", firstName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "last_name", lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "full_name", firstName+" "+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "user_id", firstName+"."+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "asset_code", AGlobalComponents.assetCode);
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
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "badge_name", AGlobalComponents.assetName);
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
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "first_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "last_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "full_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "user_id", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_2", "asset_code", "");
 		
 		
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	
 	}
 	else {
 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
 	}	
}

/*
 * TC018 : 5.0 Use cases . Manager Login Scenarios : Modify Identity  : phoneNo
 */

@Test(priority=18,enabled=true)
public void Self_Service_Automation_TC018_3() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC018_3","Manager Login Scenarios  : Modify Identity , modifying phone number");
	System.out.println("[INFO]--> Self_Service_Automation_TC018_3 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC018_3");
	
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
			firstName ="Auto"+Utility.getRandomString(4);
			lastName ="Test"+Utility.getRandomString(4);
			AGlobalComponents.userId=firstName+"."+lastName;
			
			/**create new asset **/
	//		AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
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
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "badge_name", AGlobalComponents.assetName);
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
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "first_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "last_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "full_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "user_id", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_3", "asset_code", "");
 		
 		
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	
 	}
 	else {
 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
 	}	
}


/*
 * TC018 : 5.0 Use cases . Manager Login Scenarios : Modify Identity  : department
 */

@Test(priority=18,enabled=true)
public void Self_Service_Automation_TC018_4() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC018_4","Manager Login Scenarios  : Modify Identity , modifying department");
	System.out.println("[INFO]--> Self_Service_Automation_TC018_4 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC018_4");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	
	String requestNumber = (String) testData.get("request_number");
		
	String firstName =(String) testData.get("first_name");
	String lastName =(String) testData.get("last_name");
	String scriptName =(String) testData.get("script_name");
	String accessName =(String) testData.get("access_name_1");
	AGlobalComponents.userId = (String) testData.get("user_id");
	String parameterToBeModified=(String) testData.get("parameter_tobemodified");
	
			
	/** Login as admin User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
		
		if((firstName==null||firstName.equals(""))&&(lastName==null||lastName.equals(""))){
			firstName ="Auto"+Utility.getRandomString(4);
			lastName ="Test"+Utility.getRandomString(4);
			AGlobalComponents.userId=firstName+"."+lastName;
			
			/**create new asset **/
	//		AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
			/**create identity **/
		
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			}
		
		/** checking the user details in IDM  before modification**/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,parameterToBeModified,accessName,scriptName);
	
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
		
 		/* Login as Manager */
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
				
 			/** Modify Identity **/
 			requestNumber =Self_Service_CommonMethods.modifyIdentity(firstName,parameterToBeModified,(String) testData.get("request_type"));
 				 			
 			/** checkStatusInMyRequestInbox**/
 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,parameterToBeModified,accessName,requestNumber,scriptName);
	
 			/** Switch to Default Browser **/
 			Utility.switchToDefaultBrowserDriver();
 		}
 		else{
 			logger.log(LogStatus.FAIL, "Unable to Login as end user. Plz Check Application");
 		}

 		/** Validate  status in IDM after  request approved**/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,parameterToBeModified,accessName,scriptName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_4", "first_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_4", "last_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_4", "full_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_4", "user_id", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_4", "asset_code", "");
 		
 		
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	
 	}
 	else {
 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
 	}	
}

/*
 * TC019 : 5.0 Use cases . Manager Login Scenarios :Temp Worker Onboarding
 */

@Test(priority=19,enabled=true)
public void Self_Service_Automation_TC019() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC019","Manager Login Scenarios :Temp Worker Onboarding ");
	System.out.println("[INFO]--> Self_Service_Automation_TC019 - TestCase Execution Begins");
	AGlobalComponents.tempWorkerOnboarding = true;
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC019");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String scriptName = (String) testData.get("script_name");
	String firstName ="Temp" + Utility.getRandomString(4),lastName ="Onboard";
	
	String requestNumber = "";
	
	/** Login as admin **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
		
		/**creating asset for the user**/
		AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
		
			
		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
		
 		/* Login as Manager */
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
		
			/** temp worker onboarding**/
 				requestNumber=Self_Service_CommonMethods.temporaryWorkerOnboarding(firstName,lastName);
 						
 			/** Switch to Default Browser **/
 	 		Utility.switchToDefaultBrowserDriver();
 		}
 		
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
 		
 		/* Login as Manager to approve the request */
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
 		
 			/** approve request  by manager**/
 			Self_Service_CommonMethods.approveRequest("manager",requestNumber,"");
 			
 			/* Logout from application */
 	 		LoginPage.logout();
 		
 			/* Login as Badge Admin */
 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("badge_admin_username"), (String) testData.get("badge_admin_password"));

 	 		if(loginStatus){
 	 			logger.log(LogStatus.PASS, "Login Successful");
 	 			
 	 			/** approve request By badge admin **/
 	 			Self_Service_CommonMethods.approveRequest("badgeAdmin",requestNumber,"");
 	 			
 	 			/** checkStatusInMyRequestInbox**/
 	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,scriptName);
 	 			
 	 		}
 		}
	
 		/** Switch to Default Browser **/
 		Utility.switchToDefaultBrowserDriver();
 		
 		/** Validate  created temp worker in IDM after  request approved**/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"","",scriptName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "first_name", firstName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "last_name", lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "full_name", firstName+" "+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "user_id", AGlobalComponents.userId);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "badge_name",AGlobalComponents.assetName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "asset_code", AGlobalComponents.assetCode);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "first_name", firstName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "last_name", lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "full_name", firstName+" "+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "user_id", AGlobalComponents.userId);
	 	Utility.updateDataInDatasource("Self_Service_Automation_TC021", "badge_name",AGlobalComponents.assetName);
	 	Utility.updateDataInDatasource("Self_Service_Automation_TC021", "asset_code", AGlobalComponents.assetCode);
 		
 		
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	
 	}

 	else {
 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
 	}	
}


/*
 * TC020 : 5.0 Use cases . Manager Login Scenarios :Temp Worker Modification . 
 */

@Test(priority=20,enabled=true)
public void Self_Service_Automation_TC020() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC020","Manager Login Scenarios:Temp Worker Modification . Modifying  department of the temp worker . ");
	System.out.println("[INFO]--> Self_Service_Automation_TC020 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC020");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String firstName =(String) testData.get("first_name"),lastName =(String) testData.get("last_name"); 
	String requestNumber = (String) testData.get("request_number");
	String accessName = (String) testData.get("access_name_1") ;
	String scriptName = (String) testData.get("script_name") ;
	AGlobalComponents.userId = (String) testData.get("user_id") ;
	String parameterToBeModified=(String) testData.get("parameter_tobemodified");
				
	/** Login as admin **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
		
		if((firstName==null||firstName.equals(""))&&(lastName==null||lastName.equals(""))){
			firstName="Temp"+Utility.getRandomString(4);lastName="WorkerModification";
			
			/**creating asset for the user**/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
	
			/** Launch New Private Browser **/
			Utility.switchToNewBrowserDriver();
		
			/* Login as Manager */
			loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

			if(loginStatus){
				logger.log(LogStatus.PASS, "Login Successful");
		
				/** temp worker onboarding**/
				requestNumber=Self_Service_CommonMethods.temporaryWorkerOnboarding(firstName,lastName);
 			
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
			}
 		
 			/** Launch New Private Browser **/
 			Utility.switchToNewBrowserDriver();
 		
 			/* Login as Manager to approve the request */
 			loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 			if(loginStatus){
 				logger.log(LogStatus.PASS, "Login Successful");
		
 				/** approve request  by manager**/
 				Self_Service_CommonMethods.approveRequest("manager",requestNumber,"");
 			
 				/* Logout from application */
 	 			LoginPage.logout();
 		
 				/* Login as Badge Admin */
 	 			loginStatus = LoginPage.loginAEHSC((String) testData.get("badge_admin_username"), (String) testData.get("badge_admin_password"));

 	 			if(loginStatus){
 	 				logger.log(LogStatus.PASS, "Login Successful");
 	 			
 	 				/** approve request By badge admin **/
 	 				Self_Service_CommonMethods.approveRequest("badgeAdmin",requestNumber,"");
 	 			
 	 			}
 	 			Utility.updateDataInDatasource("Self_Service_Automation_TC020", "first_name", firstName);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "last_name", lastName);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "full_name", firstName+" "+lastName);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "user_id", AGlobalComponents.userId);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "badge_name",AGlobalComponents.assetName);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC020", "asset_code", AGlobalComponents.assetCode);
 	 			Utility.updateDataInDatasource("Self_Service_Automation_TC021", "first_name", firstName);
 	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "last_name", lastName);
 	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "full_name", firstName+" "+lastName);
 	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "user_id", AGlobalComponents.userId);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "badge_name",AGlobalComponents.assetName);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "asset_code", AGlobalComponents.assetCode);
 	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "first_name", firstName);
 	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "last_name", lastName);
 	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "full_name", firstName+" "+lastName);
 	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "user_id", AGlobalComponents.userId);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "badge_name",AGlobalComponents.assetName);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "asset_code", AGlobalComponents.assetCode);
 	 	 		
 			}
	
 			/** Switch to Default Browser **/
 			Utility.switchToDefaultBrowserDriver();
		}
 
 		/** checking details of temp worker in IDM before request submission**/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,parameterToBeModified,accessName,scriptName);
 		
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
 		
 		/* Login as Manager */
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
 		
 			/** Modifying the details of temp worker**/
 				requestNumber=Self_Service_CommonMethods.tempWorkerModification(firstName,lastName,parameterToBeModified);
 				
 			
 			
 			/** checkStatusInMyRequestInbox**/
 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,parameterToBeModified,accessName,requestNumber,scriptName);
 			
 		}
 		/** Switch to Default Browser **/
 		Utility.switchToDefaultBrowserDriver();
 		

 		/** checking modified last name of temp worker in IDM **/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,parameterToBeModified,accessName,scriptName);
 		
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	
 	}

 	else {
 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
 	}	
}

/*
 * TC021 : 5.0 Use cases . Manager Login Scenarios :Temp Worker offboarding  
 */

@Test(priority=21,enabled=true)
public void Self_Service_Automation_TC021() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC021","Manager Login Scenarios:Temp Worker Offboarding , Rehire and Termination");
	System.out.println("[INFO]--> Self_Service_Automation_TC021 - TestCase Execution Begins");
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC021");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String firstName =(String) testData.get("first_name"),lastName =(String) testData.get("last_name"),userId=(String) testData.get("user_id") ;
	String requestNumber = "";
	AGlobalComponents.userId=userId;	
	AGlobalComponents.assetCode = (String) testData.get("asset_code");
	AGlobalComponents.assetName = (String) testData.get("badge_name");
			
	/** Login as admin **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
		
		if((firstName==null||firstName.equals(""))&&(lastName==null||lastName.equals(""))){
			firstName="Temp"+Utility.getRandomString(4);lastName="Workeroffboarding";
			
			/**creating asset for the user**/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
			
			/** Launch New Private Browser **/
	 		Utility.switchToNewBrowserDriver();
			
	 		/* Login as Manager to raise the request for temp worker onboarding*/
	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

	 		if(loginStatus){
	 			logger.log(LogStatus.PASS, "Login Successful");
			
				/** temp worker onboarding**/
	 			requestNumber = Self_Service_CommonMethods.temporaryWorkerOnboarding(firstName,lastName);
	 			
	 			/** Switch to Default Browser **/
	 	 		Utility.switchToDefaultBrowserDriver();
	 		}
	 		
	 		/** Launch New Private Browser **/
	 		Utility.switchToNewBrowserDriver();
	 		
	 		/* Login as Manager to approve the request */
	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

	 		if(loginStatus){
	 			logger.log(LogStatus.PASS, "Login Successful");
			
	 			/** approve request  by manager**/
	 			Self_Service_CommonMethods.approveRequest("manager",requestNumber,"");
	 			
	 			/* Logout from application */
	 	 		LoginPage.logout();
	 		
	 			/* Login as badge admin */
	 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("badge_admin_username"), (String) testData.get("badge_admin_password"));

	 	 		if(loginStatus){
	 	 			logger.log(LogStatus.PASS, "Login Successful");
	 	 			
	 	 			/** approve request By badge admin **/
	 	 			Self_Service_CommonMethods.approveRequest("badgeAdmin",requestNumber,"");
	 	 			
	 	 		}
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "first_name", firstName);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "last_name", lastName);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "full_name", firstName+" "+lastName);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "user_id", AGlobalComponents.userId);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "badge_name",AGlobalComponents.assetName);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "asset_code", AGlobalComponents.assetCode);
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "first_name", firstName);
	 	 		
	 	 		
	 		}
		
	 		/** Switch to Default Browser **/
	 		Utility.switchToDefaultBrowserDriver();
		}
		
 		

 		/** checking the access , system and assets assigned to the user in IDM 
 		 *  Status of user and assets , access and systems assigned to him will be active at this time**/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"","",(String) testData.get("script_name"));
 		
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
 		
 		/* Login as Manager to raise request for offboarding*/
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
 		
 			/** temp worker offboarding**/
 			requestNumber=Self_Service_CommonMethods.tempWorkerOffboarding(firstName,lastName);
 			
 			/** Switch to Default Browser **/
 	 		Utility.switchToDefaultBrowserDriver();
 		}
 		
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
 		
 		/* Login as Manager to approve the request */
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
		
 			/** approve request  by manager**/
 			Self_Service_CommonMethods.approveRequest("manager",requestNumber,"");
 			
 			/** checkStatusInMyRequestInbox**/
 	 		Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,(String) testData.get("script_name"));
 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "first_name", "");
	 	 	Utility.updateDataInDatasource("Self_Service_Automation_TC021", "last_name", "");
	 	 	Utility.updateDataInDatasource("Self_Service_Automation_TC021", "full_name", "");
	 	 	Utility.updateDataInDatasource("Self_Service_Automation_TC021", "user_id", "");
	 	 	Utility.updateDataInDatasource("Self_Service_Automation_TC022", "first_name", firstName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "last_name", lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "full_name", firstName+" "+lastName);
	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "user_id", AGlobalComponents.userId);
		 	Utility.updateDataInDatasource("Self_Service_Automation_TC022", "badge_name",AGlobalComponents.assetName);
		 	Utility.updateDataInDatasource("Self_Service_Automation_TC022", "asset_code", AGlobalComponents.assetCode);
 	 		
 			
 			
 		}
 		/** Switch to Default Browser **/
 		Utility.switchToDefaultBrowserDriver();
 		

 		/** checking status of access , assets , system in IDM **/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"","",(String) testData.get("script_name"));
 		
 		 		
 		/** Logout from Application **/
		LoginPage.logout();
				
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}

/*
 * TC022 : 5.0 Use cases . Manager Login Scenarios :Temp Worker Rehire  
 */

@Test(priority=22,enabled=true)
public void Self_Service_Automation_TC022() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC022","Manager Login Scenarios:Temp WorkerRehire ");
	System.out.println("[INFO]--> Self_Service_Automation_TC022 - TestCase Execution Begins");
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC022");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String firstName =(String) testData.get("first_name"),lastName =(String) testData.get("last_name") ;
	String requestNumber = "";
	AGlobalComponents.userId=(String) testData.get("user_id");	
	
			
	/** Login as admin **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
		
		/**creating asset for the user**/
		AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
		/** checking the access , system and assets assigned to the user in IDM 
 		 *  Status of user and assets , access and systems assigned to him will be inactive at this time**/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"","",(String) testData.get("script_name"));
 		
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
 		
 		/** now rehiring the the user offboared above 
 		 **/
 		
 		/* Login as Manager to raise request for rehiring*/
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
 		
 			/** temp worker Rehiring**/
 			requestNumber=Self_Service_CommonMethods.tempWorkerRehiring(firstName,lastName);
 			
 			/** Switch to Default Browser **/
 	 		Utility.switchToDefaultBrowserDriver();
 		}
 		
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
 		
 		/* Login as Manager to approve the request */
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
		
 			/** approve request  by manager**/
 			Self_Service_CommonMethods.approveRequest("manager",requestNumber,"");
 			
 			LoginPage.logout();
 			
 			/* Login as badge admin  to approve the request */
 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("badge_admin_username"), (String) testData.get("badge_admin_password"));

 	 		if(loginStatus){
 	 			/** approve request  by manager**/
 	 			Self_Service_CommonMethods.approveRequest("badgeAdmin",requestNumber,"");
 	 			
 	 			/** checkStatusInMyRequestInbox**/
 	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,(String) testData.get("script_name"));
 	 			Utility.updateDataInDatasource("Self_Service_Automation_TC022", "first_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "last_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "full_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "user_id", "");
 	 		}
		}
 		/** Switch to Default Browser **/
 		Utility.switchToDefaultBrowserDriver();
 		
 		/** checking status of access , assets , system in IDM **/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"","",(String) testData.get("script_name"));
 		
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
	
@Test(priority=23,enabled=true)
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
			firstName ="AutoTest"+Utility.getRandomString(4);
			lastName ="Reqlocation";
			
			/**create new asset **/
		//	AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
			
			/**create identity **/
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "first_name", firstName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "last_name", lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "full_name", firstName+" "+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "user_id", firstName+"."+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "asset_code", AGlobalComponents.assetCode);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "badge_name", AGlobalComponents.assetName);
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
 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("access_owner_username"), (String) testData.get("access_owner_password"));
		
 	 		if(loginStatus){

 	 			/** Approve Access Request by access owner**/
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
	
@Test(priority=24,enabled=true)
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
	AGlobalComponents.assetName=(String) testData.get("badge_name");
	String requestNumber="";
	
	/** Login as admin User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
	
		/**create identity **/
		if((firstName==null||firstName.equals(""))&&(lastName==null||lastName.equals(""))){
			firstName ="AutoTest"+Utility.getRandomString(4); lastName ="EmergencyTermination";
			AGlobalComponents.userId=firstName+"."+lastName;
			/**create new asset **/
	//		AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
		
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

@Test(priority=25,enabled=true)
public void Self_Service_Automation_TC025() throws Throwable 
{

	logger =report.startTest("Self_Service_Automation_TC025"," Position Access for Others");
	System.out.println("[INFO]--> Self_Service_Automation_TC025 - TestCase Execution Begins");

	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC025");

	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String position = (String) testData.get("position");
	String accessName = (String) testData.get("access_name_1");
	String scriptName = (String) testData.get("script_name");

	String firstName ="Auto"+Utility.getRandomString(4);
	String lastName ="Test"+Utility.getRandomString(4);

	AGlobalComponents.userId = firstName+"."+lastName;
	String requestNumber="";
	
	if(ApiMethods.createIdentityThroughAPI("Self_Service_EndUser_TC025", firstName, lastName, "alertenterprise.automation@gmail.com", "Alaska", "employee", "SYS-000002", position, (String) testData.get("manager_username")))
	{
		Utility.updateDataInDatasource("Self_Service_Automation_TC025", "first_name", firstName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC025", "last_name", lastName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC025", "full_name", firstName+" "+lastName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC025", "user_id", firstName+"."+lastName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC026", "first_name", firstName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC026", "last_name", lastName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC026", "full_name", firstName+" "+lastName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC026", "user_id", AGlobalComponents.userId);
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "first_name", firstName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "last_name", lastName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "full_name", firstName+" "+lastName);
		Utility.updateDataInDatasource("Self_Service_Automation_TC027", "user_id", AGlobalComponents.userId);
		testData = Utility.getDataFromDatasource("Self_Service_Automation_TC025");
		
		/* Login as Admin User */
		boolean loginStatus=LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
		
		if(loginStatus){
		
			/**create new asset **/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
		
			/* Add Asset to Newly Created User */
			Self_Service_CommonMethods.idmAddLocationAccessAsset((String)testData.get("user_id"), "Assets", AGlobalComponents.assetName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "asset_code", AGlobalComponents.assetCode);
			Utility.updateDataInDatasource("Self_Service_Automation_TC026", "badge_name", AGlobalComponents.assetName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "asset_code", AGlobalComponents.assetCode);
			Utility.updateDataInDatasource("Self_Service_Automation_TC027", "badge_name", AGlobalComponents.assetName);
		
			/** check accesses assigned to the user in IDM **/
			Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"",accessName,scriptName);
		
			/** Launch New Private Browser **/
			Utility.switchToNewBrowserDriver();
		}
		else
			logger.log(LogStatus.FAIL, "Unable to Login into the application");
		
	}
	
	/** Login as manager **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

	if(loginStatus){
	
		/** Request new position and add access Request **/
		requestNumber=Self_Service_CommonMethods.positionAccess(position,accessName,firstName,lastName);
							
		/** logout from the application **/
 		LoginPage.logout();
			
		/** Login as approver **/
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("access_owner_username"), (String) testData.get("access_owner_password"));
	
 		if(loginStatus){

 			/** Approve Access Request by access owner**/
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
	
}


/*
* TC026 :  Application  Access - Others
*/


@Test(priority=26,enabled=true)
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
		
			firstName ="Auto"+Utility.getRandomString(4);
			lastName ="Test"+Utility.getRandomString(4);
			AGlobalComponents.userId=firstName +"."+lastName;
			if(ApiMethods.createIdentityThroughAPI("Self_Service_EndUser_TC026", firstName, lastName, "alertenterprise.automation@gmail.com", "Alaska", "employee", "SYS-000002", "IT Director", (String) testData.get("manager_username")))
			{
				Utility.updateDataInDatasource("Self_Service_Automation_TC026", "first_name", firstName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC026", "last_name", lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC026", "full_name", firstName+" "+lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC026", "user_id", firstName+"."+lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC027", "first_name", firstName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC027", "last_name", lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC027", "full_name", firstName+" "+lastName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC027", "user_id", firstName+"."+lastName);
				testData = Utility.getDataFromDatasource("Self_Service_Automation_TC026");
				
				/**create new asset **/
				AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
				/* Add Asset to Newly Created User */
				Self_Service_CommonMethods.idmAddLocationAccessAsset((String)testData.get("user_id"), "Assets", AGlobalComponents.assetName);
				Utility.updateDataInDatasource("Self_Service_Automation_TC027", "asset_code", AGlobalComponents.assetCode);
				Utility.updateDataInDatasource("Self_Service_Automation_TC027", "badge_name", AGlobalComponents.assetName);
			
			}
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
		Utility.updateDataInDatasource("Self_Service_Automation_TC026", "first_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC026", "last_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC026", "full_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC026", "user_id", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC026", "asset_code", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC026", "badge_name", "");
	
		/** Logout from Application **/
		LoginPage.logout();
	
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}

}


/*
 * TC027 :  IT  Access - Others
*/
	

@Test(priority=27,enabled=true)
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
			
			firstName ="Auto"+Utility.getRandomString(4);
			lastName ="Test"+Utility.getRandomString(4);
			/**create new asset **/
	//		AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
						
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




	




