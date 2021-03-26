package webDriverTestCases;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;

import CommonClassReusables.MsSql;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.Utility;
import CommonFunctions.FB_Automation_CommonMethods;
import CommonFunctions.LoginPage;
import CommonFunctions.Self_Service_CommonMethods;


public class Self_Service extends BrowserSelection {

	String testName;
	
	@BeforeMethod
	public void commonPage(java.lang.reflect.Method method) throws Throwable
	{
		unhandledException = false;	
		testName = method.getName();
	//	AGlobalComponents.applicationURL = "http://autodevhsc.alertenterprise.com/";
		AGlobalComponents.applicationURL = "http://aepdemo.alertenterprise.com/";
		AGlobalComponents.takeScreenshotIfPass = true;
		driver.navigate().refresh();
	}
	
	public static void isConditionX(boolean condition) {
		if(condition==false){
			throw new SkipException("skipp");
		}
	}
			
/*
 * TC001 : AEAP-9 : Physical Access - Case1(Submission flow and validations)
*/
	
@Test(priority=1)
public void Self_Service_Automation_TC001() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC001","AEAP-9 : Physical Access - Case1(Submission flow and validations)");
	System.out.println("[INFO]--> Self_Service_Automation_TC001 - TestCase Execution Begins");
	
	String locationName = "Plaza, Financial Center";
	String accessName = "SC RIPPSU RLY GENERAL ACCESS SECURE";
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("john.payne", "Alert1234");

	if(loginStatus){
		
		/** Create Access Request **/
		String requestNumber = Self_Service_CommonMethods.createAccessRequest("Physical Access",locationName,accessName,"Las Vegas","300 S 4th St");
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Approver User **/
		LoginPage.loginAEHSC("carol.payne", "Alert1234");
		
		if(loginStatus){

			/** Approve Access Request **/
			Self_Service_CommonMethods.approveAccessRequest(accessName,requestNumber,"john.payne");
			
			/** Logout from Application **/
			LoginPage.logout();
			
			/** Login as Admin User **/
			LoginPage.loginAEHSC("admin", "Alert@783");
			
			/** Validate Provisioning Monitor Status **/
			Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"ASSIGN_ROLES_SUCCESS","john.payne");
			
			/** Validate IDM User Status **/
			Self_Service_CommonMethods.idmUserValidation("john.payne","Accesses",accessName,"Active");
			
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Access Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Approved","John Payne");
		}else{
			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
		}
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}
	
	
/*
 * TC002 : AEAP-10 : Physical Access - Case2(Approval Flow - Role Rejection)
 */

@Test(priority=2)
public void Self_Service_Automation_TC002() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC002","AEAP-10 : Physical Access - Case2(Approval Flow - Role Rejection)");
	System.out.println("[INFO]--> Self_Service_Automation_TC001 - TestCase Execution Begins");
	
	String locationName = "Plaza, Financial Center";
	String accessName = "SC LEEXSS NONR GENERAL ACCESS";
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("john.payne", "Alert1234");

	if(loginStatus){
		
		/** Create Access Request **/
		String requestNumber = Self_Service_CommonMethods.createAccessRequest("Physical Access",locationName,accessName,"Las Vegas","300 S 4th St");
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Approver User **/
		LoginPage.loginAEHSC("carol.payne", "Alert1234");
		
		if(loginStatus){
			
			/** Reject Access Request **/
			Self_Service_CommonMethods.rejectAccessRequest(accessName,requestNumber,"john.payne");
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Access Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Rejected","John Payne");
		}else{
			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
		}
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}



/*
* TC003 : AEAP-11 : Physical Access - Case2(Approval Flow - Multiple Role approver with same owner)
*/

@Test(priority=3)
public void Self_Service_Automation_TC003() throws Throwable 
{

	logger =report.startTest("Self_Service_Automation_TC003","AEAP-11 : Physical Access - Case2(Approval Flow - Multiple Role approver with same owner)");
	System.out.println("[INFO]--> Self_Service_Automation_TC003 - TestCase Execution Begins");
	
	String locationName1 = "Plaza, Financial Center";
	String accessName1 = "SC LEEXSS NONR GENERAL ACCESS";
	
	String locationName2 = "Plaza, Financial Center";
	String accessName2 = "SC RIPPSU RLY GENERAL ACCESS SECURE";
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("john.payne", "Alert1234");
	
	if(loginStatus){
		
		/** Create Multiple Access Request **/
		String requestNumber = Self_Service_CommonMethods.createAccessRequestMultiple("Physical Access",locationName1,accessName1,locationName2,accessName2);
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Approver User **/
		LoginPage.loginAEHSC("carol.payne", "Alert1234");
		
		if(loginStatus){
			
			/** Approve Access Request **/
			Self_Service_CommonMethods.approveAccessRequestMultpile(accessName1,accessName2,requestNumber,"john.payne");
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Access Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Approved","John Payne");
		}else{
			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
		}
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}

}



/*
* TC004 : AEAP-12 : Physical Access - Case4(Approval Flow - Approve 1 Role and Reject 1 Role with same owner)
*/

@Test(priority=4)
public void Self_Service_Automation_TC004() throws Throwable 
{

	logger =report.startTest("Self_Service_Automation_TC004","AEAP-12 : Physical Access - Case4(Approval Flow - Approve 1 Role and Reject 1 Role with same owner)");
	System.out.println("[INFO]--> Self_Service_Automation_TC004 - TestCase Execution Begins");
	
	String locationName1 = "Plaza, Financial Center";
	String accessName1 = "SC LEEXSS NONR GENERAL ACCESS";
	
	String locationName2 = "Plaza, Financial Center";
	String accessName2 = "SC RIPPSU RLY GENERAL ACCESS SECURE";
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("john.payne", "Alert1234");
	
	if(loginStatus){
		
		/** Create Multiple Access Request **/
		String requestNumber = Self_Service_CommonMethods.createAccessRequestMultiple("Physical Access",locationName1,accessName1,locationName2,accessName2);
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Approver User **/
		LoginPage.loginAEHSC("carol.payne", "Alert1234");
		
		if(loginStatus){
			
			/** Approve Access Request **/
			Self_Service_CommonMethods.approveAndRejectAccessRequestMultpile(accessName1,accessName2,requestNumber,"john.payne");
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Access Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Approved","John Payne");
		}else{
			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
		}
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}

}



/*
* TC005 : AEAP-13 : Physical Access - Case5(Approval Flow - Approve/Reject Role with different owner)
*/

@Test(priority=5)
public void Self_Service_Automation_TC005() throws Throwable 
{

	logger =report.startTest("Self_Service_Automation_TC005","AEAP-13 : Physical Access - Case5(Approval Flow - Approve/Reject Role with different owner)");
	System.out.println("[INFO]--> Self_Service_Automation_TC005 - TestCase Execution Begins");
	
	String locationNames = "Plaza, Financial Center;Albany, Financial Center";
	String accessNames = "SC LEEXSS NONR GENERAL ACCESS;SC RIPPSU RLY GENERAL ACCESS SECURE;FL NRTHPN NONR CONF RM 4C6;NC CABRGR NONR LEGAL CONF RM ACCESS";
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("john.payne", "Alert1234");
	
	if(loginStatus){
		
		/** Create Multiple Access Request **/
		String requestNumber = Self_Service_CommonMethods.createLocationAccessRequestMultiple("Physical Access",locationNames,accessNames);
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Approver User **/
		LoginPage.loginAEHSC("carol.payne", "Alert1234");
		
		if(loginStatus){
			
			/** Approve Access Request **/
			Self_Service_CommonMethods.approveAccessRequestMultpile("SC LEEXSS NONR GENERAL ACCESS","SC RIPPSU RLY GENERAL ACCESS SECURE",requestNumber,"john.payne");
			
			/** Logout from Application **/
			LoginPage.logout();
			
			/** Login as Approver User **/
			LoginPage.loginAEHSC("john.payne", "Alert1234");
			
			/** Approve 1 and Reject 1 Access Request **/
			Self_Service_CommonMethods.approveAndRejectAccessRequestMultpile("FL NRTHPN NONR CONF RM 4C6","NC CABRGR NONR LEGAL CONF RM ACCESS",requestNumber,"john.payne");
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Access Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Approved","John Payne");
		}else{
			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
		}
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}

}

/*
* TC006 : AEAP-14 : Wellness check : Auto approval  )
*/

@Test(priority=6)
public void Self_Service_Automation_TC006() throws Throwable 
{

	logger =report.startTest("Self_Service_Automation_TC006","AEAP-14 : Wellness Check - Auto approval)");
	System.out.println("[INFO]--> Self_Service_Automation_TC006 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC006");
	
	String firstName = "Wellcheck";
	String lastName = "Bailey";
	AGlobalComponents.userId=firstName+"."+lastName;
	
	/** Login as admin User **/
	boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert1234");
	
	if(loginStatus){
		
		for(int i=0;i<2;i++){
		
			/** check asset status in IDM before wellness check **/
			Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"","",(String) testData.get("request_type"));
	
			/** Launch New Private Browser **/
			Utility.switchToNewBrowserDriver();
		
			/** Login as requester **/
			LoginPage.loginAEHSC("wellcheck.bailey", "Alert1234");
			
			if(loginStatus){
			
				/** submit wellness check request to activate the user**/
				Self_Service_CommonMethods.createWellnessCheckRequest();
			
				/** checkAssetStatusInMyRequestInbox**/
				Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","","",(String) testData.get("request_type"));
			
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
			}
			else{
				logger.log(LogStatus.FAIL, "Unable to Login as end user. Plz Check Application");
			}
		
			/** Validate asset status in IDM after wellness check request approved**/
			Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName, "", "",(String) testData.get("request_type"));
			
		}
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
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
	String requestNumber = (String) testData.get("request_number");
		
	String firstName =(String) testData.get("first_name");
	String lastName =(String) testData.get("last_name");
	String scriptName =(String) testData.get("script_name");
	AGlobalComponents.userId = firstName+"."+lastName;
	String parameterToBeModified=(String) testData.get("parameterToBeModified");
	
			
	/** Login as admin User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
		
		if((firstName==null||firstName.equals(""))&&(lastName==null||lastName.equals(""))){
			firstName ="Test"+Utility.getRandomString(4);
			lastName =Utility.getRandomString(4);
			
			/**create new asset **/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
			/**create identity **/
		
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "first_name", firstName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "last_name", lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "full_name", firstName+" "+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "user_id", firstName+"."+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "first_name", firstName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "last_name", lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "full_name", firstName+" "+lastName);
			Utility.updateDataInDatasource("Self_Service_Automation_TC024", "user_id", firstName+"."+lastName);
		}
		
		/** checking the user details in IDM  before modification**/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,parameterToBeModified,"",(String) testData.get("request_type"));
	
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
		
 		/* Login as Manager */
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
				
 			/** Modify Identity **/
 			if(requestNumber==null||requestNumber.equals("")){
 				requestNumber =Self_Service_CommonMethods.modifyIdentity(firstName,parameterToBeModified,(String) testData.get("request_type"));
 				Utility.updateDataInDatasource("Self_Service_Automation_TC018", "request_number", requestNumber);
 			}
		
 			/** checkStatusInMyRequestInbox**/
 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,parameterToBeModified,"",requestNumber,(String) testData.get("request_type"));
	
 			/** Switch to Default Browser **/
 			Utility.switchToDefaultBrowserDriver();
 		}
 		else{
 			logger.log(LogStatus.FAIL, "Unable to Login as end user. Plz Check Application");
 		}

 		/** Validate  status in IDM after  request approved**/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",parameterToBeModified,(String) testData.get("request_type"));
 		
 		/** checking the user details in IDM  before modification**/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,parameterToBeModified,"",(String) testData.get("request_type"));
 		
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
 		
 		/* Login as Manager */
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
				
 			/** Modify Identity **/
 	     	requestNumber =Self_Service_CommonMethods.modifyIdentity(firstName,parameterToBeModified,(String) testData.get("request_type"));
		
 			/** checkStatusInMyRequestInbox**/
 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,parameterToBeModified,"",requestNumber,(String) testData.get("request_type"));
	
 			/** Switch to Default Browser **/
 			Utility.switchToDefaultBrowserDriver();
 		}
 		else{
 			logger.log(LogStatus.FAIL, "Unable to Login as end user. Plz Check Application");
 		}

 		/** Validate  status in IDM after  request approved**/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",parameterToBeModified,(String) testData.get("request_type"));
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	
 	}
 	else {
 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
 	}	
}

/*
 * TC018 : 5.0 Use cases . Manager Login Scenarios :Employment Type Conversion
 */

@Test(priority=17)
public void Self_Service_Automation_TC017() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC017","Manager Login Scenarios : Employment Type Conversion ");
	System.out.println("[INFO]--> Self_Service_Automation_TC017 - TestCase Execution Begins");
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC017");
	
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
 		Utility.updateDataInDatasource("Self_Service_Automation_TC023", "first_name", firstName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC023", "last_name", lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC023", "full_name", firstName+" "+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC023", "user_id", firstName+"."+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "first_name", firstName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "last_name", lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "full_name", firstName+" "+lastName);
 		Utility.updateDataInDatasource("Self_Service_Automation_TC024", "user_id", firstName+"."+lastName);
				
		/** check accesses assigned to Contractor in IDM **/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"",accessToBeAdded,(String) testData.get("request_type"));
	
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
		
 		/* Login as Manager */
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
				
 			/** Employee Type Conversion**/
 			requestNumber = Self_Service_CommonMethods.employeeConversion(firstName);
 				
 			/** checkStatusInMyRequestInbox**/
 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"",accessToBeAdded,requestNumber,(String) testData.get("request_type"));
 			
 			/** Switch to Default Browser **/
 			Utility.switchToDefaultBrowserDriver();
 		}
 		else{
 			logger.log(LogStatus.FAIL, "Unable to Login as end user. Plz Check Application");
 		}

 		/** Validate  status in IDM after  request approved**/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",accessToBeAdded,(String) testData.get("request_type"));
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	
 	}
 	else {
 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
 	}	
}




/*
 * TC009 : 5.0 Use cases . Manager Login Scenarios :Temp Worker Onboarding
 */

@Test(priority=19)
public void Self_Service_Automation_TC019() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC019","Manager Login Scenarios :Temp Worker Onboarding ");
	System.out.println("[INFO]--> Self_Service_Automation_TC019 - TestCase Execution Begins");
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC019");
	
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
 			if(requestNumber==null||requestNumber.equals("")){
 				requestNumber=Self_Service_CommonMethods.temporaryWorkerOnboarding(firstName,lastName);
 				Utility.updateDataInDatasource("Self_Service_Automation_TC019", "request_number", requestNumber);
 			}
		
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
 	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,(String) testData.get("request_type"));
 	 			
 	 		}
 		}
	
 		/** Switch to Default Browser **/
 		Utility.switchToDefaultBrowserDriver();
 		
 		/** Validate  created temp worker in IDM after  request approved**/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"","",(String) testData.get("request_type"));
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
 		
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	
 	}

 	else {
 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
 	}	
}


/*
 * TC010 : 5.0 Use cases . Manager Login Scenarios :Temp Worker Modification . 
 */

@Test(priority=20)
public void Self_Service_Automation_TC020() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC020","Manager Login Scenarios:Temp Worker Modification . Modifying  department of the temp worker . ");
	System.out.println("[INFO]--> Self_Service_Automation_TC020 - TestCase Execution Begins");
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC020");
	
	String firstName =(String) testData.get("first_name"),lastName =(String) testData.get("last_name"); 
	String requestNumber = (String) testData.get("request_number");
	String accessName = (String) testData.get("access_name_1") ;
	String parameterToBeModified=(String) testData.get("parameterToBeModified");
				
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
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,parameterToBeModified,accessName,(String) testData.get("request_type"));
 		
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
 		
 		/* Login as Manager */
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
 		
 			/** Modifying the details of temp worker**/
 			if(requestNumber==null||requestNumber.equals("")){
 				requestNumber=Self_Service_CommonMethods.tempWorkerModification(firstName,lastName,parameterToBeModified);
 				Utility.updateDataInDatasource("Self_Service_Automation_TC020", "request_number", requestNumber);
 			}
 			
 			/** checkStatusInMyRequestInbox**/
 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,parameterToBeModified,accessName,requestNumber,(String) testData.get("request_type"));
 			
 		}
 		/** Switch to Default Browser **/
 		Utility.switchToDefaultBrowserDriver();
 		

 		/** checking modified last name of temp worker in IDM **/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,parameterToBeModified,accessName,(String) testData.get("request_type"));
 		
 		
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
	
	String locationName = (String) testData.get("location_name_1");
	String accessName = "access_name_1";
		
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
		}
			
		/** check accesses assigned to the user in IDM **/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"","",(String) testData.get("request_type"));
	
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
	
 		/** Login as manager **/
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
		
 			/** Request new location and add access Request **/
 			requestNumber=Self_Service_CommonMethods.requestNewLocation("Physical Access",locationName,accessName,"Pittsburgh","430 Market St",firstName,lastName);
 			Utility.updateDataInDatasource("Self_Service_Automation_TC023", "request_number", requestNumber);
 				
 			/** logout from the application **/
 	 		LoginPage.logout();
 			
 			/** Login as approver **/
 	 		loginStatus = LoginPage.loginAEHSC("anna.mordeno", "Alert1234");
		
 	 		if(loginStatus){

 	 			/** Approve Access Request by area admin**/
 	 			Self_Service_CommonMethods.approveRequest("areaAdmin",requestNumber,accessName);
 	 			
 		 	 	/** Validate Access Request Status **/
 	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,(String) testData.get("request_type"));
 	 		}else{
 	 			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
 	 	
 	 		}
 	 	}else{
 			logger.log(LogStatus.FAIL, "Unable to Login as Manager. Plz Check Application");
 	 	}
 		
 		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
			
		/** checking status of access assigned in IDM **/
		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"",accessName,(String) testData.get("request_type"));	
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}




/*
 * TC021 : 5.0 Use cases . Manager Login Scenarios :Temp Worker offboarding  
 */

@Test(priority=21)
public void Self_Service_Automation_TC021() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC021","Manager Login Scenarios:Temp Worker Offboarding , Rehire and Termination");
	System.out.println("[INFO]--> Self_Service_Automation_TC021 - TestCase Execution Begins");
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC021");
	
	
	String firstName =(String) testData.get("first_name"),lastName =(String) testData.get("last_name"),userId=(String) testData.get("user_id") ;
	String requestNumber = "";
	AGlobalComponents.userId=userId;	
	AGlobalComponents.assetCode = "AST-002409";
			
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
	 		
	 			/* Login as admin user */
	 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_user_username"), (String) testData.get("admin_user_password"));

	 	 		if(loginStatus){
	 	 			logger.log(LogStatus.PASS, "Login Successful");
	 	 			
	 	 			/** approve request By badge admin **/
	 	 			Self_Service_CommonMethods.approveRequest("badgeAdmin",requestNumber,"");
	 	 			
	 	 		}
	 	 		
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
		
 		

 		/** checking the access , system and assets assigned to the user in IDM 
 		 *  Status of user and assets , access and systems assigned to him will be active at this time**/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"","",(String) testData.get("request_type"));
 		
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
 			
 			LoginPage.logout();
 			
 			/* Login as admin user to approve the request */
 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_user_username"), (String) testData.get("admin_user_password"));

 	 		if(loginStatus){
 	 			/** approve request  by admin user**/
 	 			Self_Service_CommonMethods.approveRequest("admin_user",requestNumber,"");
 	 			
 	 			/** checkStatusInMyRequestInbox**/
 	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,(String) testData.get("request_type"));
 	 			Utility.updateDataInDatasource("Self_Service_Automation_TC021", "first_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "last_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "full_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "user_id", "");
 	 		}
 			
 			
 		}
 		/** Switch to Default Browser **/
 		Utility.switchToDefaultBrowserDriver();
 		

 		/** checking status of access , assets , system in IDM **/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"","",(String) testData.get("request_type"));
 		
 		 		
 		/** Logout from Application **/
		LoginPage.logout();
				
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}

/*
 * TC022 : 5.0 Use cases . Manager Login Scenarios :Temp Worker Rehire  
 */

@Test(priority=22)
public void Self_Service_Automation_TC022() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC022","Manager Login Scenarios:Temp WorkerRehire ");
	System.out.println("[INFO]--> Self_Service_Automation_TC022 - TestCase Execution Begins");
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC022");
	
	
	String firstName =(String) testData.get("first_name"),lastName =(String) testData.get("last_name") ;
	String requestNumber = "";
	AGlobalComponents.userId=(String) testData.get("user_id");	
	AGlobalComponents.assetCode = (String) testData.get("asset_code");
	AGlobalComponents.assetName = (String) testData.get("badge_name");
			
	/** Login as admin **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
		
		/** checking the access , system and assets assigned to the user in IDM 
 		 *  Status of user and assets , access and systems assigned to him will be inactive at this time**/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"","",(String) testData.get("request_type"));
 		
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
 	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,(String) testData.get("request_type"));
 	 			Utility.updateDataInDatasource("Self_Service_Automation_TC022", "first_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "last_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "full_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC022", "user_id", "");
 	 		}
		}
 		/** Switch to Default Browser **/
 		Utility.switchToDefaultBrowserDriver();
 		
 		/** checking status of access , assets , system in IDM **/
 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"","",(String) testData.get("request_type"));
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	
 	}

 	else {
 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
 	}	
}

/*
 * TC014 : AEAP-17 : Self Service - New Badge
 */

@Test(priority=14)
public void Self_Service_Automation_TC014() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC014","AEAP-17 : Self Service - New Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC014 - TestCase Execution Begins");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("ruth.parson", "Alert1234");

	if(loginStatus){
		
		/** Create Access Request **/
		String requestNumber = Self_Service_CommonMethods.createNewBadgeRequest("New Badge","Others","Keith","InActive");
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Admin User **/
		LoginPage.loginAEHSC("admin", "Alert@783");
		
		/** Create New Badge **/
		String badgeName = Self_Service_CommonMethods.createNewAsset("Permanent Badge", "SRSeries_10And12Digit", "AMAG"); //CCURE 9000,AMAG,etc.
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Stage 1 Approver User **/
		LoginPage.loginAEHSC("badge.admin", "Alert1234");
		
		if(loginStatus){
			
			/** Approve Badge Request Stage 1 **/
			Self_Service_CommonMethods.approveBadgeRequest(1,requestNumber,"InActive",badgeName,"keith.ledger");
			
			/** Logout from Application **/
			LoginPage.logout();
			
			/** Login as Stage 2 Approver User **/
			LoginPage.loginAEHSC("rebecca.bell", "Alert1234");
			
			/** Approve Badge Request Stage 2 **/
			Self_Service_CommonMethods.approveBadgeRequest(2,requestNumber,"InActive",badgeName,"keith.ledger");
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Self Approval Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "InActive", "Keith Ledger");
			
			/** Validate Provisioning Monitor Status **/
			Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CHANGE_USER_SUCCESS","keith.ledger");
			
			/** Validate IDM User Status **/
			Self_Service_CommonMethods.idmUserValidation("keith.ledger","Assets","","InActive");
			
		}else{
			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
		}
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}


/*
 * TC015 : AEAP-18 : Self Service - ACTIVATE Badge
 */

@Test(priority=15)
public void Self_Service_Automation_TC015() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC015","AEAP-18 : Self Service - ACTIVATE Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC015 - TestCase Execution Begins");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("john.payne", "Alert1234");

	if(loginStatus){
		
		/** Create Activate Badge Request **/
		String requestNumber = Self_Service_CommonMethods.createActivateDeactivateBadgeRequest("Activate Badge","Others","Treppy");
		
		/** Validate Self Approval Request Status **/
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Activate", "Treppy Terry");
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"UNLOCK_USER_SUCCESS","Treppy.terry");
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation("Treppy.terry","Assets","","Active");		
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}


/*
 * TC016 : AEAP-19 : Self Service - DEACTIVATE Badge
*/

@Test(priority=16)
public void Self_Service_Automation_TC016() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC016","AEAP-19 : Self Service - DEACTIVATE Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC016 - TestCase Execution Begins");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("john.payne", "Alert1234");

	if(loginStatus){
		
		/** Create Deactivate Badge Request **/
		String requestNumber = Self_Service_CommonMethods.createActivateDeactivateBadgeRequest("Deactivate Badge","Others","Treppy");
		
		/** Validate Self Approval Request Status **/
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Deactivate", "Treppy Terry");
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"LOCK_USER_SUCCESS","Treppy.terry");
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation("Treppy.terry","Assets","","InActive");	
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}


/*
 * TC017 : Self Service - IT Access
*/
	
@Test(priority=117)
public void Self_Service_Automation_TC0117() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC0117","Self Service - IT Access");
	System.out.println("[INFO]--> Self_Service_Automation_TC0117 - TestCase Execution Begins");
	
	String systemName = "CCURE 9000";
	String accessName = "JAPAN-TOKYO-MIN251-MINATO-KU";
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("ruth.parson", "Alert1234");

	if(loginStatus){
		
		/** Create Access Request **/
		String requestNumber = Self_Service_CommonMethods.createAccessRequestOthers("IT Access",systemName,accessName,"sihana");
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Approver User **/
		LoginPage.loginAEHSC("carol.payne", "Alert1234");
		
		if(loginStatus){

			/** Approve Access Request **/
			Self_Service_CommonMethods.approveAccessRequest(accessName,requestNumber,"sihana.gomez");
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Access Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Approved","sihana.gomez");
		}else{
			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
		}
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}



/*
 * TC018 : Self Service - Application Access
*/
	
@Test(priority=118)
public void Self_Service_Automation_TC0118() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC0118","Self Service - Application Access");
	System.out.println("[INFO]--> Self_Service_Automation_TC0118 - TestCase Execution Begins");
	
	String systemName = "McAfee";
	String accessName = "SC NWPTIE NONR GATE ACCESS";
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("ruth.parson", "Alert1234");

	if(loginStatus){
		
		/** Create Access Request **/
		String requestNumber = Self_Service_CommonMethods.createAccessRequestOthers("Application Access",systemName,accessName,"Sihana");
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Approver User **/
		LoginPage.loginAEHSC("rebecca.bell", "Alert1234");
		
		if(loginStatus){

			/** Approve Access Request **/
			Self_Service_CommonMethods.approveAccessRequest(accessName,requestNumber,"sihana.gomez");
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Access Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Approved","sihana.gomez");
		}else{
			logger.log(LogStatus.FAIL, "Unable to Login as Approver. Plz Check Application");
		}
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}



/*
 * TC019 : Self Service - Report Lost or Stolen Badge
*/

@Test(priority=119)
public void Self_Service_Automation_TC0119() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC019","Self Service - Report Lost or Stolen Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC019 - TestCase Execution Begins");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("john.payne", "Alert1234");

	if(loginStatus){
		
		/** Create Deactivate Badge Request **/
		String requestNumber = Self_Service_CommonMethods.createLostOrStolenBadgeRequest("Lost or Stolen Badge","Others","Harry");
		
		/** Validate Self Approval Request Status **/
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Deactivate", "Treppy Terry");
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"LOCK_USER_SUCCESS","Treppy.terry");
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation("Treppy.terry","Assets","","InActive");	
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}



/*
 * TC020 : Self Service - Request Replacement Badge DAMAGED
*/

@Test(priority=20)
public void Self_Service_Automation_TC0200() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC020","Self Service - Request Replacement Badge DAMAGED");
	System.out.println("[INFO]--> Self_Service_Automation_TC020 - TestCase Execution Begins");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("ruth.parson", "Alert1234");

	if(loginStatus){
		
		/** Create Replace Badge Request **/
		String requestNumber = Self_Service_CommonMethods.createReplaceBadgeRequest("Replace Badge","Others","kerry","DAMAGED");
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Admin User **/
		LoginPage.loginAEHSC("admin", "Alert@783");
		
		/** Create New Badge **/
		String badgeName = Self_Service_CommonMethods.createNewAsset("Permanent Badge", "SRSeries_10And12Digit", "AMAG"); //CCURE 9000,AMAG,etc.
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Manager User **/
		LoginPage.loginAEHSC("carol.payne", "Alert1234");
		
		/** Approve Replace Badge Request Stage 1 **/
		Self_Service_CommonMethods.approveReplaceBadgeRequest(1,requestNumber,"DAMAGED",badgeName,"jackson.kerry");
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Stage 2 Approver User **/
		LoginPage.loginAEHSC("rebecca.bell", "Alert1234");
		
		/** Approve Replace Badge Request Stage 2 **/
		Self_Service_CommonMethods.approveReplaceBadgeRequest(2,requestNumber,"DAMAGED",badgeName,"jackson.kerry");
		
		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
		
		/** Validate Self Approval Request Status **/
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Active", "Jackson kerry");
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"LOCK_USER_SUCCESS","jackson.kerry");
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CREATE_USER_SUCCESS","jackson.kerry");
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation("jackson.kerry","Assets","","DAMAGED");
		Self_Service_CommonMethods.idmUserValidation("jackson.kerry","Assets","","ASSIGNED");
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}



/*
 * TC021 : Self Service - Return Temporary Badge
*/

@Test(priority=211)
public void Self_Service_Automation_TC0211() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC021","Self Service - Return Temporary Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC021 - TestCase Execution Begins");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");

	if(loginStatus){
		
		/** Create Return Badge Request **/
		Self_Service_CommonMethods.returnTempBadge("keith.ledger");
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}


/*
 * TC022 : Self Service - Request Replacement Badge LOST
*/

@Test(priority=222)
public void Self_Service_Automation_TC0222() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC022","Self Service - Request Replacement Badge LOST");
	System.out.println("[INFO]--> Self_Service_Automation_TC022 - TestCase Execution Begins");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("ruth.parson", "Alert1234");

	if(loginStatus){
		
		/** Create Replace Badge Request **/
		String requestNumber = Self_Service_CommonMethods.createReplaceBadgeRequest("Replace Badge","Others","kerry","LOST");
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Admin User **/
		LoginPage.loginAEHSC("admin", "Alert@783");
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation("jackson.kerry","Assets","","LOST");
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"LOCK_USER_SUCCESS","jackson.kerry");
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CHANGE_USER_SUCCESS","jackson.kerry");
		
		/** Create New Badge **/
		String badgeName = Self_Service_CommonMethods.createNewAsset("Permanent Badge", "SRSeries_10And12Digit", "AMAG"); //CCURE 9000,AMAG,etc.
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Manager User **/
		LoginPage.loginAEHSC("carol.payne", "Alert1234");
		
		/** Approve Replace Badge Request Stage 1 **/
		Self_Service_CommonMethods.approveReplaceBadgeRequest(1,requestNumber,"LOST",badgeName,"jackson.kerry");
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Stage 2 Approver User **/
		LoginPage.loginAEHSC("rebecca.bell", "Alert1234");
		
		/** Approve Replace Badge Request Stage 2 **/
		Self_Service_CommonMethods.approveReplaceBadgeRequest(2,requestNumber,"LOST",badgeName,"jackson.kerry");
		
		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
		
		/** Validate Self Approval Request Status **/
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Active", "Jackson kerry");
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"LOCK_USER_SUCCESS","jackson.kerry");
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CREATE_USER_SUCCESS","jackson.kerry");
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation("jackson.kerry","Assets","","LOST");
		Self_Service_CommonMethods.idmUserValidation("jackson.kerry","Assets","","ASSIGNED");
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}
		


/*
 * TC023: Self Service - Request Replacement Badge STOLEN
*/

@Test(priority=233)
public void Self_Service_Automation_TC0233() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC0233","Self Service - Request Replacement Badge STOLEN");
	System.out.println("[INFO]--> Self_Service_Automation_TC0233 - TestCase Execution Begins");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC("ruth.parson", "Alert1234");

	if(loginStatus){
		
		/** Create Replace Badge Request **/
		String requestNumber = Self_Service_CommonMethods.createReplaceBadgeRequest("Replace Badge","Others","Jonathan","STOLEN");
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Admin User **/
		LoginPage.loginAEHSC("admin", "Alert@783");
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation("jonathan.carl","Assets","","LOST");
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"LOCK_USER_SUCCESS","jonathan.carl");
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CHANGE_USER_SUCCESS","jonathan.carl");
		
		/** Create New Badge **/
		String badgeName = Self_Service_CommonMethods.createNewAsset("Permanent Badge", "SRSeries_10And12Digit", "AMAG"); //CCURE 9000,AMAG,etc.
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Manager User **/
		LoginPage.loginAEHSC("carol.payne", "Alert1234");
		
		/** Approve Replace Badge Request Stage 1 **/
		Self_Service_CommonMethods.approveReplaceBadgeRequest(1,requestNumber,"STOLEN",badgeName,"jonathan.carl");
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Stage 2 Approver User **/
		LoginPage.loginAEHSC("rebecca.bell", "Alert1234");
		
		/** Approve Replace Badge Request Stage 2 **/
		Self_Service_CommonMethods.approveReplaceBadgeRequest(2,requestNumber,"STOLEN",badgeName,"jonathan.carl");
		
		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
		
		/** Validate Self Approval Request Status **/
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, "Active", "Jonathan Carl");
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"LOCK_USER_SUCCESS","jonathan.carl");
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CREATE_USER_SUCCESS","jonathan.carl");
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation("jonathan.carl","Assets","","STOLEN");
		Self_Service_CommonMethods.idmUserValidation("jonathan.carl","Assets","","ASSIGNED");
		
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
	
	String firstName =(String) testData.get("first_name");
	String lastName =(String) testData.get("last_name");
	String scriptName =(String) testData.get("script_name");
	AGlobalComponents.userId=(String) testData.get("user_id");
	String requestNumber=(String) testData.get("request_Number");
		
	/** Login as admin User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
	if(loginStatus){
		
		/**create new asset **/
		AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
		
		/**create identity **/
		if((firstName==null||firstName.equals(""))&&(lastName==null||lastName.equals(""))){
			firstName ="Test"+Utility.getRandomString(4); lastName ="EmergencyTermination";
			AGlobalComponents.userId=firstName+"."+lastName;
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
		}
			
		
		/** check accesses assigned to the user in IDM **/
 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,"","",(String) testData.get("request_type"));
	
 		/** Launch New Private Browser **/
 		Utility.switchToNewBrowserDriver();
	
 		/** Login as manager **/
 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

 		if(loginStatus){
		
 			/** Request for emergency termination **/
 			requestNumber=Self_Service_CommonMethods.emergencyTermination(firstName,lastName);
 		
 		 	/** Validate Access Request Status **/
 	 		Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName, lastName,"","",requestNumber,(String) testData.get("request_type"));
 	 		
 	 	}else{
 			logger.log(LogStatus.FAIL, "Unable to Login as Manager. Plz Check Application");
 	 	}
 		
 		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
			
		/** checking status of access assigned in IDM **/
		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,"","",(String) testData.get("request_type"));
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}



}