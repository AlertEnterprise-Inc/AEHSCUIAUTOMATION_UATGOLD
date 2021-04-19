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


public class Self_Service extends BrowserSelection {

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
	 * TC001 : AEAP-9 : Physical Access - Case1(Submission flow and validations)
	*/
		
	@Test(priority=1)
	public void Self_Service_Automation_TC001() throws Throwable 
	{
		
		logger =report.startTest("Self_Service_Automation_TC001","AEAP-9 : Physical Access - Case1(Submission flow and validations)");
		System.out.println("[INFO]--> Self_Service_Automation_TC001 - TestCase Execution Begins");
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC001");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String requestNumber = (String) testData.get("request_number");
		
		/** Login as Requester User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));
	
		if(loginStatus){
			
			/** Create Access Request **/
			if(requestNumber==null||requestNumber.equals(""))
			{
				requestNumber = Self_Service_CommonMethods.createAccessRequest((String) testData.get("request_type"),(String) testData.get("location_name_1"),(String) testData.get("access_name_1"),"Las Vegas","300 S 4th St");
				Utility.updateDataInDatasource("Self_Service_Automation_TC001", "request_number", requestNumber);
			}
			
			/** Launch New Private Browser **/
			Utility.switchToNewBrowserDriver();
			
			/** Login as Approver User **/
			LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
			
			if(loginStatus){
	
				/** Approve Access Request **/
				Self_Service_CommonMethods.approveAccessRequest((String) testData.get("access_name_1"),requestNumber,(String) testData.get("workflow_stage"));
				
				/** Logout from Application **/
				LoginPage.logout();
				
				/** Login as Admin User **/
				LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
				
				/** Validate Provisioning Monitor Status **/
				Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,(String) testData.get("provisioning_code"),(String) testData.get("user_id"));
				
				/** Validate IDM User Status **/
				Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),(String) testData.get("idm_validation_status"));
				
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
				
				/** Validate Access Request Status **/
				Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"));
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
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC002");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String requestNumber = (String) testData.get("request_number");
		
		/** Login as Requester User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));
	
		if(loginStatus){
			
			/** Create Access Request **/
			if(requestNumber==null||requestNumber.equals(""))
			{
				requestNumber = Self_Service_CommonMethods.createAccessRequest((String) testData.get("request_type"),(String) testData.get("location_name_1"),(String) testData.get("access_name_1"),"Las Vegas","300 S 4th St");
				Utility.updateDataInDatasource("Self_Service_Automation_TC002", "request_number", requestNumber);
			}
			
			/** Launch New Private Browser **/
			Utility.switchToNewBrowserDriver();
			
			/** Login as Approver User **/
			LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
			
			if(loginStatus){
				
				/** Reject Access Request **/
				Self_Service_CommonMethods.rejectAccessRequest((String) testData.get("access_name_1"),requestNumber,"");
				
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
				
				/** Validate Access Request Status **/
				Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"));
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
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC003");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String requestNumber = (String) testData.get("request_number");
		
		/** Login as Requester User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));
		
		if(loginStatus){
			
			/** Create Multiple Access Request **/
			if(requestNumber==null||requestNumber.equals(""))
			{
				requestNumber = Self_Service_CommonMethods.createAccessRequestMultiple((String) testData.get("request_type"),(String) testData.get("location_name_1"),(String) testData.get("access_name_1"),(String) testData.get("location_name_2"),(String) testData.get("access_name_2"));
				Utility.updateDataInDatasource("Self_Service_Automation_TC003", "request_number", requestNumber);
			}
			
			/** Launch New Private Browser **/
			Utility.switchToNewBrowserDriver();
			
			/** Login as Approver User **/
			LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
			
			if(loginStatus){
				
				/** Approve Access Request **/
				Self_Service_CommonMethods.approveAccessRequestMultpile((String) testData.get("access_name_1"),(String) testData.get("access_name_2"),requestNumber,(String) testData.get("user_id"));
				
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
				
				/** Validate Access Request Status **/
				Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"));
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
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC004");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String requestNumber = (String) testData.get("request_number");
		
		/** Login as Requester User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));
		
		if(loginStatus){
			
			/** Create Multiple Access Request **/
			if(requestNumber==null||requestNumber.equals(""))
			{
				requestNumber = Self_Service_CommonMethods.createAccessRequestMultiple((String) testData.get("request_type"),(String) testData.get("location_name_1"),(String) testData.get("access_name_1"),(String) testData.get("location_name_2"),(String) testData.get("access_name_2"));
				Utility.updateDataInDatasource("Self_Service_Automation_TC004", "request_number", requestNumber);
			}
			
			/** Launch New Private Browser **/
			Utility.switchToNewBrowserDriver();
			
			/** Login as Approver User **/
			LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
			
			if(loginStatus){
				
				/** Approve Access Request **/
				Self_Service_CommonMethods.approveAndRejectAccessRequestMultpile((String) testData.get("access_name_1"),(String) testData.get("access_name_2"),requestNumber,(String) testData.get("user_id"));
				
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
				
				/** Validate Access Request Status **/
				Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"));
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
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC005");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String requestNumber = (String) testData.get("request_number");
		
		String locationNames = (String) testData.get("location_name_1")+";"+(String) testData.get("location_name_2");
		String accessNames = (String) testData.get("access_name_1")+";"+(String) testData.get("access_name_2");
		
		/** Login as Requester User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));
		
		if(loginStatus){
			
			/** Create Multiple Access Request **/
			if(requestNumber==null||requestNumber.equals(""))
			{
				requestNumber = Self_Service_CommonMethods.createLocationAccessRequestMultiple((String) testData.get("request_type"),locationNames,accessNames);
				Utility.updateDataInDatasource("Self_Service_Automation_TC005", "request_number", requestNumber);
			}
			
			/** Launch New Private Browser **/
			Utility.switchToNewBrowserDriver();
			
			/** Login as Approver User **/
			LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
			
			if(loginStatus){
				
				/** Approve Access Request **/
				String[] accesses = accessNames.split(";");
				String accessName1 = accesses[0];
				String accessName2 = accesses[1];
				Self_Service_CommonMethods.approveAccessRequestMultpile(accessName1,accessName2,requestNumber,(String) testData.get("user_id"));
				
				/** Logout from Application **/
				LoginPage.logout();
				
				/** Login as Approver User **/
				LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));
				
				/** Approve 1 and Reject 1 Access Request **/
				String accessName3 = accesses[2];
				String accessName4 = accesses[3];
				Self_Service_CommonMethods.approveAndRejectAccessRequestMultpile(accessName3,accessName4,requestNumber,(String) testData.get("user_id"));
				
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
				
				/** Validate Access Request Status **/
				Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"));
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
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
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
 * TC007 : AEAP-17 : Self Service - New Badge
 */

@Test(priority=7)
public void Self_Service_Automation_TC007() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC007","AEAP-17 : Self Service - New Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC007 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC007");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String requestNumber = (String) testData.get("request_number");
	String badgeName = (String) testData.get("badge_name");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));

	if(loginStatus){
		
		/** Create Access Request **/
		if(requestNumber==null||requestNumber.equals(""))
		{
			requestNumber = Self_Service_CommonMethods.createNewBadgeRequest((String) testData.get("request_type"),(String) testData.get("request_for"),(String) testData.get("first_name"),(String) testData.get("card_status"));
			Utility.updateDataInDatasource("Self_Service_Automation_TC007", "request_number", requestNumber);
		}
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Admin User **/
		LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
		
		/** Create New Badge **/
		if(badgeName==null||badgeName.equals(""))
		{
			badgeName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system")); //CCURE 9000,AMAG,etc.
			Utility.updateDataInDatasource("Self_Service_Automation_TC007", "badge_name", badgeName);
		}
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Stage 1 Approver User **/
		LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
		
		if(loginStatus){
			
			/** Approve Badge Request Stage 1 **/
			Self_Service_CommonMethods.approveBadgeRequest(1,requestNumber,(String) testData.get("card_status"),badgeName,(String) testData.get("full_name"));
			
			/** Logout from Application **/
			LoginPage.logout();
			
			/** Login as Stage 2 Approver User **/
			LoginPage.loginAEHSC((String) testData.get("badge_admin_username"), (String) testData.get("badge_admin_password"));
			
			/** Approve Badge Request Stage 2 **/
			Self_Service_CommonMethods.approveBadgeRequest(2,requestNumber,(String) testData.get("card_status"),badgeName,(String) testData.get("full_name"));
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Self Approval Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("card_status"), (String) testData.get("full_name"));
			
			/** Validate Provisioning Monitor Status **/
			AGlobalComponents.systemName=(String) testData.get("system_name");
			Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,(String) testData.get("provisioning_code"),(String) testData.get("user_id"));
			AGlobalComponents.systemName="";
			
			/** Validate IDM User Status **/
			Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),(String) testData.get("card_status"));
			
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
 * TC008 : AEAP-18 : Self Service - ACTIVATE Badge
 */

@Test(priority=8)
public void Self_Service_Automation_TC008() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC008","AEAP-18 : Self Service - ACTIVATE Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC008 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC008");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String requestNumber = (String) testData.get("request_number");
	
	/* Login as Requester User */
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));

	if(loginStatus){
		
		/* Create Activate Badge Request */
		if(requestNumber==null||requestNumber.equals(""))
		{
			requestNumber = Self_Service_CommonMethods.createActivateDeactivateBadgeRequest((String) testData.get("request_type"),(String) testData.get("request_for"),(String) testData.get("first_name"));
			Utility.updateDataInDatasource("Self_Service_Automation_TC008", "request_number", requestNumber);
		}
		
		/* Validate Self Approval Request Status */
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"), (String) testData.get("full_name"));
		
		/* Logout from Application */
		LoginPage.logout();
		
		/* Login as Admin User */
		LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
		
		/* Validate Provisioning Monitor Status */
		AGlobalComponents.systemName=(String) testData.get("system_name");
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,(String) testData.get("provisioning_code"),(String) testData.get("user_id"));
		AGlobalComponents.systemName="";
		
		/* Validate IDM User Status */
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),(String) testData.get("idm_validation_status"));		
		
		/* Logout from Application */
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}


/*
 * TC009 : AEAP-19 : Self Service - DEACTIVATE Badge
*/

@Test(priority=9)
public void Self_Service_Automation_TC009() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC009","AEAP-19 : Self Service - DEACTIVATE Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC009 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC009");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String requestNumber = (String) testData.get("request_number");
	
	/* Login as Requester User */
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));

	if(loginStatus){
		
		/* Create Deactivate Badge Request */
		if(requestNumber==null||requestNumber.equals(""))
		{
			requestNumber = Self_Service_CommonMethods.createActivateDeactivateBadgeRequest((String) testData.get("request_type"),(String) testData.get("request_for"),(String) testData.get("first_name"));
			Utility.updateDataInDatasource("Self_Service_Automation_TC009", "request_number", requestNumber);
		}
		
		/* Validate Self Approval Request Status */
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"), (String) testData.get("full_name"));
		
		/* Logout from Application */
		LoginPage.logout();
		
		/* Login as Admin User */
		LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
		
		/* Validate Provisioning Monitor Status */
		AGlobalComponents.systemName=(String) testData.get("system_name");
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,(String) testData.get("provisioning_code"),(String) testData.get("user_id"));
		AGlobalComponents.systemName="";
		
		/* Validate IDM User Status */
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),(String) testData.get("idm_validation_status"));
		
		/* Logout from Application */
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}
/*
 * TC010 : Self Service - IT Access
*/
	
@Test(priority=10)
public void Self_Service_Automation_TC010() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC010","Self Service - IT Access");
	System.out.println("[INFO]--> Self_Service_Automation_TC010 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC010");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String requestNumber = (String) testData.get("request_number");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));

	if(loginStatus){
		
		/** Create Access Request **/
		if(requestNumber==null||requestNumber.equals(""))
		{
			requestNumber = Self_Service_CommonMethods.createAccessRequestOthers((String) testData.get("request_type"),(String) testData.get("system_name"),(String) testData.get("access_name_1"),(String) testData.get("first_name"));
			Utility.updateDataInDatasource("Self_Service_Automation_TC010", "request_number", requestNumber);
		}		
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Approver User **/
		LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
		
		if(loginStatus){

			/** Approve Access Request **/
			Self_Service_CommonMethods.approveAccessRequest((String) testData.get("access_name_1"),requestNumber,(String) testData.get("workflow_stage"));
			
			/** Logout from Application **/
			LoginPage.logout();
			
			/** Login as Approver User **/
			LoginPage.loginAEHSC((String) testData.get("area_admin_username"), (String) testData.get("area_admin_password"));
			
			/** Approve Access Request **/
			Self_Service_CommonMethods.approveAccessRequest((String) testData.get("access_name_1"),requestNumber,(String) testData.get("request_status"));
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Access Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"));
			
			/** Logout from Application **/
			LoginPage.logout();
			
			/** Login as Admin User **/
			LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
			
			/** Validate IDM User Status **/
			Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("access_name_1"),(String) testData.get("idm_validation_status"));
			
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
 * TC011 : Self Service - Application Access
*/
	
@Test(priority=11)
public void Self_Service_Automation_TC011() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC011","Self Service - Application Access");
	System.out.println("[INFO]--> Self_Service_Automation_TC011 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC011");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String requestNumber = (String) testData.get("request_number");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));

	if(loginStatus){
		
		/** Create Access Request **/
		if(requestNumber==null||requestNumber.equals(""))
		{
			requestNumber = Self_Service_CommonMethods.createAccessRequestOthers((String) testData.get("request_type"),(String) testData.get("system_name"),(String) testData.get("access_name_1"),(String) testData.get("first_name"));
			Utility.updateDataInDatasource("Self_Service_Automation_TC011", "request_number", requestNumber);
		}	
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Approver User **/
		LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
		
		if(loginStatus){

			/** Approve Access Request **/
			Self_Service_CommonMethods.approveAccessRequest((String) testData.get("access_name_1"),requestNumber,(String) testData.get("workflow_stage"));
			
			/** Logout from Application **/
			LoginPage.logout();
			
			/** Login as Approver User **/
			LoginPage.loginAEHSC((String) testData.get("area_admin_username"), (String) testData.get("area_admin_password"));
			
			/** Approve Access Request **/
			Self_Service_CommonMethods.approveAccessRequest((String) testData.get("access_name_1"),requestNumber,(String) testData.get("request_status"));
			
			/** Switch to Default Browser **/
			Utility.switchToDefaultBrowserDriver();
			
			/** Validate Access Request Status **/
			Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"));
			
			/** Logout from Application **/
			LoginPage.logout();
			
			/** Login as Admin User **/
			LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
			
			/** Validate IDM User Status **/
			Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("access_name_1"),(String) testData.get("idm_validation_status"));
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
 * TC012 : Self Service - Report Lost or Stolen Badge
*/

@Test(priority=12)
public void Self_Service_Automation_TC012() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC012","Self Service - Report Lost or Stolen Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC012 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC012");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String requestNumber = (String) testData.get("request_number");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));

	if(loginStatus){
		
		/** Create Deactivate Badge Request **/
		if(requestNumber==null||requestNumber.equals(""))
		{
			requestNumber = Self_Service_CommonMethods.createLostOrStolenBadgeRequest((String) testData.get("request_type"),(String) testData.get("request_for"),(String) testData.get("first_name"));
			Utility.updateDataInDatasource("Self_Service_Automation_TC012", "request_number", requestNumber);
		}	
		
		/** Validate Self Approval Request Status **/
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"), (String) testData.get("full_name"));
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,(String) testData.get("provisioning_code"),(String) testData.get("user_id"));
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),(String) testData.get("idm_validation_status"));	
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}


/*
 * TC013 : Self Service - Request Replacement Badge DAMAGED
*/

@Test(priority=13)
public void Self_Service_Automation_TC013() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC013","Self Service - Request Replacement Badge DAMAGED");
	System.out.println("[INFO]--> Self_Service_Automation_TC013 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC013");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String requestNumber = (String) testData.get("request_number");
	String badgeName = (String) testData.get("badge_name");
	
	/* Login as Requester User */
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));

	if(loginStatus){
		
		/* Create Replace Badge Request */
		if(requestNumber==null||requestNumber.equals(""))
		{
			requestNumber = Self_Service_CommonMethods.createReplaceBadgeRequest((String) testData.get("request_type"),(String) testData.get("request_for"),(String) testData.get("first_name"),(String) testData.get("idm_validation_status"));
			Utility.updateDataInDatasource("Self_Service_Automation_TC013", "request_number", requestNumber);
		}	
		
		/* Logout from Application */
		LoginPage.logout();
		
		/* Login as Admin User */
		LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
		
		/* Create New Badge */
		if(badgeName==null||badgeName.equals(""))
		{
			badgeName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system")); //CCURE 9000,AMAG,etc.
			Utility.updateDataInDatasource("Self_Service_Automation_TC013", "badge_name", badgeName);
		}
		
		/* Launch New Private Browser */
		Utility.switchToNewBrowserDriver();
		
		/* Login as Manager User */
		LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
		
		/* Approve Replace Badge Request Stage 1 */
		Self_Service_CommonMethods.approveReplaceBadgeRequest(1,requestNumber,(String) testData.get("idm_validation_status"),badgeName,(String) testData.get("full_name"));
		
		/* Logout from Application */
		LoginPage.logout();
		
		/* Login as Stage 2 Approver User */
		LoginPage.loginAEHSC((String) testData.get("badge_admin_username"), (String) testData.get("badge_admin_password"));
		
		/* Approve Replace Badge Request Stage 2 */
		Self_Service_CommonMethods.approveReplaceBadgeRequest(2,requestNumber,(String) testData.get("idm_validation_status"),badgeName,(String) testData.get("full_name"));
		
		/* Switch to Default Browser */
		Utility.switchToDefaultBrowserDriver();
		
		/* Validate Self Approval Request Status */
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"), (String) testData.get("full_name"));
		
		/* Validate Provisioning Monitor Status */
		AGlobalComponents.systemName=(String) testData.get("system_name");
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,(String) testData.get("provisioning_code"),(String) testData.get("user_id"));
		AGlobalComponents.systemName="";
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CREATE_USER_SUCCESS",(String) testData.get("user_id"));
		
		/* Validate IDM User Status */
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),(String) testData.get("idm_validation_status"));
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),"ASSIGNED");
		
		/* Logout from Application */
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}


/*
 * TC014 : Self Service - Return Temporary Badge
*/

@Test(priority=14)
public void Self_Service_Automation_TC014() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC014","Self Service - Return Temporary Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC014 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC014");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String badgeName = (String) testData.get("badge_name");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));

	if(loginStatus){
		
		/** Create New Badge **/
		if(badgeName==null||badgeName.equals(""))
		{
			badgeName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system")); //CCURE 9000,AMAG,etc.
			Utility.updateDataInDatasource("Self_Service_Automation_TC014", "badge_name", badgeName);
		}	
		
		/** Assign New Badge **/
		Self_Service_CommonMethods.assignBadgeIDM((String) testData.get("user_id"), badgeName, (String) testData.get("badge_type"));
		
		/** Create Return Badge Request **/
		Self_Service_CommonMethods.returnTempBadge((String) testData.get("user_id"));
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}


/*
 * TC015 : Self Service - Request Replacement Badge LOST
*/

@Test(priority=15)
public void Self_Service_Automation_TC015() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC015","Self Service - Request Replacement Badge LOST");
	System.out.println("[INFO]--> Self_Service_Automation_TC015 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC015");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String requestNumber = (String) testData.get("request_number");
	String badgeName = (String) testData.get("badge_name");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));

	if(loginStatus){
		
		/** Create Replace Badge Request **/
		if(requestNumber==null||requestNumber.equals(""))
		{
			requestNumber = Self_Service_CommonMethods.createReplaceBadgeRequest((String) testData.get("request_type"),(String) testData.get("request_for"),(String) testData.get("first_name"),(String) testData.get("idm_validation_status"));
			Utility.updateDataInDatasource("Self_Service_Automation_TC015", "request_number", requestNumber);
		}	
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Admin User **/
		LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),(String) testData.get("idm_validation_status"));
		
		/** Validate Provisioning Monitor Status **/
		AGlobalComponents.systemName=(String) testData.get("system_name");
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,(String) testData.get("provisioning_code"),(String) testData.get("user_id"));
		AGlobalComponents.systemName="";
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CHANGE_USER_SUCCESS",(String) testData.get("user_id"));
		
		/** Create New Badge **/
		if(badgeName==null||badgeName.equals(""))
		{
			badgeName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system")); //CCURE 9000,AMAG,etc.
			Utility.updateDataInDatasource("Self_Service_Automation_TC015", "badge_name", badgeName);
		}
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Manager User **/
		LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
		
		/** Approve Replace Badge Request Stage 1 **/
		Self_Service_CommonMethods.approveReplaceBadgeRequest(1,requestNumber,(String) testData.get("idm_validation_status"),badgeName,(String) testData.get("full_name"));
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Stage 2 Approver User **/
		LoginPage.loginAEHSC((String) testData.get("badge_admin_username"), (String) testData.get("badge_admin_password"));
		
		/** Approve Replace Badge Request Stage 2 **/
		Self_Service_CommonMethods.approveReplaceBadgeRequest(2,requestNumber,(String) testData.get("idm_validation_status"),badgeName,(String) testData.get("full_name"));
		
		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
		
		/** Validate Self Approval Request Status **/
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"), (String) testData.get("full_name"));
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,(String) testData.get("provisioning_code"),(String) testData.get("user_id"));
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CREATE_USER_SUCCESS",(String) testData.get("user_id"));
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),(String) testData.get("idm_validation_status"));
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),"ASSIGNED");
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
	
}
		

/*
 * TC016 : Self Service - Request Replacement Badge STOLEN
*/

@Test(priority=16)
public void Self_Service_Automation_TC016() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC016","Self Service - Request Replacement Badge STOLEN");
	System.out.println("[INFO]--> Self_Service_Automation_TC016 - TestCase Execution Begins");
	
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC016");
	
	AGlobalComponents.applicationURL = (String) testData.get("application_url");
	String requestNumber = (String) testData.get("request_number");
	String badgeName = (String) testData.get("badge_name");
	
	/** Login as Requester User **/
	boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("end_user_username"), (String) testData.get("end_user_password"));
	
	if(loginStatus){
		
		/** Create Replace Badge Request **/
		if(requestNumber==null||requestNumber.equals(""))
		{
			requestNumber = Self_Service_CommonMethods.createReplaceBadgeRequest((String) testData.get("request_type"),(String) testData.get("request_for"),(String) testData.get("first_name"),(String) testData.get("idm_validation_status"));
			Utility.updateDataInDatasource("Self_Service_Automation_TC016", "request_number", requestNumber);
		}	
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Admin User **/
		LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),(String) testData.get("idm_validation_status"));
		
		/** Validate Provisioning Monitor Status **/
		AGlobalComponents.systemName=(String) testData.get("system_name");
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,(String) testData.get("provisioning_code"),(String) testData.get("user_id"));
		AGlobalComponents.systemName="";
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CHANGE_USER_SUCCESS",(String) testData.get("user_id"));
		
		/** Create New Badge **/
		if(badgeName==null||badgeName.equals(""))
		{
			badgeName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system")); //CCURE 9000,AMAG,etc.
			Utility.updateDataInDatasource("Self_Service_Automation_TC016", "badge_name", badgeName);
		}
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Manager User **/
		LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));
		
		/** Approve Replace Badge Request Stage 1 **/
		Self_Service_CommonMethods.approveReplaceBadgeRequest(1,requestNumber,(String) testData.get("idm_validation_status"),badgeName,(String) testData.get("full_name"));
		
		/** Logout from Application **/
		LoginPage.logout();
		
		/** Login as Stage 2 Approver User **/
		LoginPage.loginAEHSC((String) testData.get("badge_admin_username"), (String) testData.get("badge_admin_password"));
		
		/** Approve Replace Badge Request Stage 2 **/
		Self_Service_CommonMethods.approveReplaceBadgeRequest(2,requestNumber,(String) testData.get("idm_validation_status"),badgeName,(String) testData.get("full_name"));
		
		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
		
		/** Validate Self Approval Request Status **/
		Self_Service_CommonMethods.validateAccessRequestStatus(requestNumber, (String) testData.get("request_status"), (String) testData.get("full_name"));
		
		/** Validate Provisioning Monitor Status **/
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,(String) testData.get("provisioning_code"),(String) testData.get("user_id"));
		Self_Service_CommonMethods.provisioningMonitorValidation(requestNumber,"CREATE_USER_SUCCESS",(String) testData.get("user_id"));
		
		/** Validate IDM User Status **/
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),(String) testData.get("idm_validation_status"));
		Self_Service_CommonMethods.idmUserValidation((String) testData.get("user_id"),(String) testData.get("idm_validation_tab"),(String) testData.get("idm_validation_key"),"ASSIGNED");
		
		/** Logout from Application **/
		LoginPage.logout();
		
	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
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

@Test(priority=18)
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
			firstName ="Test"+Utility.getRandomString(4);
			lastName =Utility.getRandomString(4);
			AGlobalComponents.userId=firstName+"."+lastName;
			
			/**create new asset **/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
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
 		Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "first_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "last_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "full_name", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "user_id", "");
		Utility.updateDataInDatasource("Self_Service_Automation_TC018_1", "asset_code", "");
 		
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

@Test(priority=18)
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
			firstName ="Test"+Utility.getRandomString(4);
			lastName =Utility.getRandomString(4);
			AGlobalComponents.userId=firstName+"."+lastName;
			
			/**create new asset **/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
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

@Test(priority=18)
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
 * TC019 : 5.0 Use cases . Manager Login Scenarios :Temp Worker Onboarding
 */

@Test(priority=19)
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
 * TC020 : 5.0 Use cases . Manager Login Scenarios :Temp Worker Modification . 
 */

@Test(priority=20)
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

@Test(priority=21)
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
 			
 			LoginPage.logout();
 			
 			/* Login as admin user to approve the request */
 	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_user_username"), (String) testData.get("admin_user_password"));

 	 		if(loginStatus){
 	 			/** approve request  by admin user**/
 	 			Self_Service_CommonMethods.approveRequest("admin_user",requestNumber,"");
 	 			
 	 			/** checkStatusInMyRequestInbox**/
 	 			Self_Service_CommonMethods.checkRequestInMyRequestInbox(firstName,lastName,"","",requestNumber,(String) testData.get("script_name"));
 	 			Utility.updateDataInDatasource("Self_Service_Automation_TC021", "first_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "last_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "full_name", "");
	 	 		Utility.updateDataInDatasource("Self_Service_Automation_TC021", "user_id", "");
 	 		}
 			
 			
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

@Test(priority=22)
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
	String requestNumber="";
	
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

/*
 * TC028 : 5.0 Use cases . Badge Admin Login Scenarios : Activate Badge
 */

@Test(priority=28)
public void Self_Service_Automation_TC028() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC0028","Badge Admin login scenarios - Activate Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC028 - TestCase Execution Begins");
	String firstName ="Amberd";
	String lastName ="Root";
		
	/** Login as badge admin User **/

 	logger.log(LogStatus.PASS, "Login Successful");
 	boolean loginStatus = LoginPage.loginAEHSC("badge.admin", "Alert1234");
 	if (loginStatus) {
 		Self_Service_CommonMethods.activatedeactivateBadge(firstName,lastName,"activate");
 	 }
 		
 	Utility.switchToNewBrowserDriver();
 	/** Login as admin User **/
 		loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");	
 		if(loginStatus){
 			Self_Service_CommonMethods.checkProvisioningLogs("UNLOCK_USER_SUCCESS","CCURE9000");
 			
 		}
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	}
	

/*
 * TC029 : 5.0 Use cases . Badge Admin Login Scenarios : Deactivate Badge
 */

@Test(priority=29)
public void Self_Service_Automation_TC029() throws Throwable 
{
	
	logger =report.startTest("Self_Service_Automation_TC029","Badge Admin login scenarios - Deactivate Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC029 - TestCase Execution Begins");
	String firstName ="Amberd";
	String lastName ="Root";
		
	/** Login as badge admin User **/

 	logger.log(LogStatus.PASS, "Login Successful");
 	boolean loginStatus = LoginPage.loginAEHSC("badge.admin", "Alert1234");
 	if (loginStatus) {
 		Self_Service_CommonMethods.activatedeactivateBadge(firstName,lastName,"deactivate");
 	 }
 		
 	Utility.switchToNewBrowserDriver();
 	/** Login as admin User **/
 		loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");	
 		if(loginStatus){
 			Self_Service_CommonMethods.checkProvisioningLogs("LOCK_USER_SUCCESS","CCURE9000");
 			
 		}
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	}
	

/*
 * TC030 : 5.0 Use cases . Badge Admin Login Scenarios : Request Replacement Badge
 */

@Test(priority=30)
public void Self_Service_Automation_TC030() throws Throwable 
	{
		String firstName ="Mike";
		String lastName ="Rodi";
		logger =report.startTest("Self_Service_Automation_TC030","Badge Admin login scenarios - Request Replacement badge");
		System.out.println("[INFO]--> Self_Service_Automation_TC030 - TestCase Execution Begins");
		boolean loginStatus = LoginPage.loginAEHSC("badge.admin", "Alert1234");
		if(loginStatus){
 			logger.log(LogStatus.PASS, "Login Successful");
 			Self_Service_CommonMethods.requestReplacementBadge(firstName, lastName);
 			}
		
		Utility.switchToNewBrowserDriver();
	 	/** Login as admin User **/
	 		loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");	
	 		if(loginStatus){
	 			Self_Service_CommonMethods.checkProvisioningLogs("LOCK_USER_SUCCESS","CCURE9000");
	 			Self_Service_CommonMethods.checkProvisioningLogs("CREATE_USER_SUCCESS","AMAG");
	 			
	 		}
	 		
	 		/** Logout from Application **/
	 		LoginPage.logout();
	}

/*
 * TC031 : 5.0 Use cases . Badge Admin Login Scenarios : Request new badge
 */

@Test(priority=31)
public void Self_Service_Automation_TC031() throws Throwable 
	{
		
	logger =report.startTest("Self_Service_Automation_TC031","Badge Admin login scenarios - Request New Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC031- TestCase Execution Begins");
	String firstName ="Mike";
	String lastName ="Rodi";
		
	/** Login as badge admin User **/

 	logger.log(LogStatus.PASS, "Login Successful");
 	boolean loginStatus = LoginPage.loginAEHSC("badge.admin", "Alert1234");
 	if (loginStatus) {
 		Self_Service_CommonMethods.requestNewBadge(firstName,lastName);
 	 	}
 		
 	Utility.switchToNewBrowserDriver();
 	/** Login as admin User **/
 		loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");	
 		if(loginStatus){
 			Self_Service_CommonMethods.checkProvisioningLogs("CREATE_USER_SUCCESS","CCURE9000");
 			
 		}
 		
 		/** Logout from Application **/
 		LoginPage.logout();
	}

/*
 * TC032 : 5.0 Use cases . Badge Admin Login Scenarios : Reset Pin
 */

@Test(priority=32)
public void Self_Service_Automation_TC032() throws Throwable 
	{
		
	logger =report.startTest("Self_Service_Automation_TC032","Badge Admin login scenarios - Reset Pin");
	System.out.println("[INFO]--> Self_Service_Automation_TC032 - TestCase Execution Begins");
	String firstName ="Scott";
	String lastName ="Carter";
		
	/** Login as badge admin User **/

 	logger.log(LogStatus.PASS, "Login Successful");
 	boolean loginStatus = LoginPage.loginAEHSC("badge.admin", "Alert1234");
 	if (loginStatus) {
 		Self_Service_CommonMethods.resetBadgePin(firstName, lastName);
 		
 		/** Logout from Application **/
 		LoginPage.logout();
 	 	}
	}

/*
 * TC033 : Self Service - Return Temporary Badge
*/

@Test(priority=33)
public void Self_Service_Automation_TC033() throws Throwable 
{

	logger =report.startTest("Self_Service_Automation_TC033","Self Service - Return Temporary Badge");
	System.out.println("[INFO]--> Self_Service_Automation_TC033 - TestCase Execution Begins");
	
	String firstName ="Ujjwal";
	String lastName ="Mishra";

	/** Login as badge admin **/
	boolean loginStatus = LoginPage.loginAEHSC("badge.admin", "Alert1234");

	if(loginStatus){

		/** Create Return Badge Request **/
		Self_Service_CommonMethods.returnTemporaryBadge(firstName, lastName);

		Utility.switchToNewBrowserDriver();
	 	/** Login as admin User **/
	 		loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");	
	 		if(loginStatus){
	 			Self_Service_CommonMethods.checkProvisioningLogs("DELETE_USER_SUCCESS","LENEL");
	 			
	 		}
	 		/** Logout from Application **/
	 		LoginPage.logout();

	}else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}

}

/*
 * TC034 : Self Service - Access Review Identity Expiring in X days
*/

@Test(priority=34)
public void Self_Service_Automation_TC034() throws Throwable 
{
	logger =report.startTest("Self_Service_Automation_TC034","Self Service - Access Review Identity Expiring in X days");
	System.out.println("[INFO]--> Self_Service_Automation_TC034 - TestCase Execution Begins");
	
	/** Login as  admin **/
	boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");
	
	if(loginStatus){
		ArrayList<String> arr = Self_Service_CommonMethods.checkIdentityExpiring("CCTV_ROOM", "abc@gmail.com", "New York", "employee", "SYS-000002","QA Manager");
		String reqNo =  arr.remove(arr.size() - 1);
		System.out.println("the array is "+arr);
		System.out.println("the req no is "+reqNo);
		
		/** Launch New Private Browser **/
		Utility.switchToNewBrowserDriver();
		
		/** Login as Manager User **/
		LoginPage.loginAEHSC("anna.mordeno", "Alert1234");
		HashMap<String, String> map = Self_Service_CommonMethods.checkIdentityExpiringRequestInManagerInbox(arr, reqNo,"ADMIN USER","CCTV_ROOM");
		
		/** Switch to Default Browser **/
		Utility.switchToDefaultBrowserDriver();
		
		for(int i=0;i<arr.size();i++)
		{	
		Self_Service_CommonMethods.checkUserStatusInIDM(arr.get(i),map,"CCTV_ROOM");
		}
		Self_Service_CommonMethods.checkRequestStatusInMyRequest(reqNo);
		/** Logout from Application **/
 		LoginPage.logout();
	}
	else{
		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	}
}



}