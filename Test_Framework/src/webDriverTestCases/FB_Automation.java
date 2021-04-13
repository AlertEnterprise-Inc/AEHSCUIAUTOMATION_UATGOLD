package webDriverTestCases;

import java.util.ArrayList;
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


public class FB_Automation extends BrowserSelection {

	
	String testName;
	
	
	@BeforeMethod
	public void commonPage(java.lang.reflect.Method method) throws Throwable
	{
		unhandledException = false;	
		testName = method.getName();
		AGlobalComponents.applicationURL = "http://autodevhsc.alertenterprise.com/";
		AGlobalComponents.takeScreenshotIfPass = true;
		driver.navigate().refresh();
	}
	
	public static void isConditionX(boolean condition) {
		if(condition==false){
			throw new SkipException("skipp");
		}
	}
			
	/*
	 * TC001 : Role Recon Job Execution.Add Record , Delete Record , check filter functionality , search functionality , settings icon functionality
	 */
	
	@Test(priority=1)
	public void FB_Automation_TC001() throws Throwable 
	{
		logger =report.startTest("FB_Automation_TC001","Role Recon Job Execution ,Add Record , Delete Record , check filter functionality , search functionality , settings icon functionality, incremental recon");
		System.out.println("[INFO]--> FB_Automation_TC001 - TestCase Execution Begins");
		AGlobalComponents.CCURERoleRecon=true;
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC001");
	
		/** Login as AS User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/** Create Recon Job **/
			FB_Automation_CommonMethods.setUpReconJob((String) testData.get("recon_entity"),(String) testData.get("recon_connector"),(String) testData.get("recon_prefeedrule"),(String) testData.get("recon_scheduletype"),(boolean) testData.get("recon_createrequest"),(boolean) testData.get("recon_fetchentity"));
				
			/** Rerun the role recon job **/
//			FB_Automation_CommonMethods.rerunReconRecord((String) testData.get("recon_entity"));
			
			/** Deleting the Recon Record **/
//			FB_Automation_CommonMethods.deleteReconRecord((String) testData.get("recon_entity"));
			
				
			/** Logout from Application **/
			LoginPage.logout();
		}	
		else
			logger.log(LogStatus.FAIL, "Login failed");
	}
	
	
	/*
	 * TC002 : Mandatory fields check when creating new identity
	 */

	@Test(priority=2)
	public void FB_Automation_TC002() throws Throwable 
	{
		
		logger =report.startTest("FB_Automation_TC002","Mandatory Fields check in  Create Identity");
		System.out.println("[INFO]--> FB_Automation_TC005 - TestCase Execution Begins");
	
		/** Login as AS User **/
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/** Mandatory fields check while Create Identity **/
			FB_Automation_CommonMethods.mandatoryFieldsCheck();
			
			/** Logout from Application **/
			LoginPage.logout();
		}	
		else
			logger.log(LogStatus.FAIL, "Login Failed");
					
	
	}
	
	/*
	 * TC003 : Create Identity , Search Identity , Edit Identity , Duplicate Check , deleting multiple identities
	 */
	
	@Test(priority=3)
	public void FB_Automation_TC003() throws Throwable 
	{
		
		logger =report.startTest("FB_Automation_TC003","Create Identity,search Identity ,edit Identity ,Duplicate check identity");
		System.out.println("[INFO]--> FB_Automation_TC003 - TestCase Execution Begins");
		
		
	
		/** Login as AS User **/
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/** Create Identity **/
			FB_Automation_CommonMethods.createIdentity();
			
			/** Search Identity **/
			FB_Automation_CommonMethods.searchIdentity();
			
			/** update photo of User through IDM screen**/
			FB_Automation_CommonMethods.updatePhoto();
			
			/** Modify Identity **/
			FB_Automation_CommonMethods.modifyIdentityIDM("","");
			
			/** Create Duplicate Identity **/
			FB_Automation_CommonMethods.createDuplicateIdentity();
			
			/** Search the duplicate identity **/
			FB_Automation_CommonMethods.searchIdentity();
			
			/** Delete multiple identities **/
			FB_Automation_CommonMethods.deleteMultipleIdentities();
				
			/** Logout from Application **/
			LoginPage.logout();
		}	
		else			
			logger.log(LogStatus.FAIL, "Login Failed");
		
	}
	
	
	/*
	 * TC004 : Validating Show /Hide Filter Widget and Cancel button on Create Identity
	 */
	
	@Test(priority=4)
	public void FB_Automation_TC004() throws Throwable 
	{
		
		logger =report.startTest("FB_Automation_TC004","Show /Hide Filter Widget ,Cancel button on Create Identity");
		System.out.println("[INFO]--> FB_Automation_TC004 - TestCase Execution Begins");
	
		/* Login as AS User */
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/** test cancel button functionality **/
			FB_Automation_CommonMethods.cancelCreateIdentity();
			
			/** test show/HideFilterWidget functionality **/
			FB_Automation_CommonMethods.showHideFilterWidget();
			
				
			/** Logout from Application **/
			LoginPage.logout();
		}	
		else
			logger.log(LogStatus.FAIL, "Login Failed");
		
		
	}
	
	/*
	 * TC005 : Delete single identity, Verify Cancel, close & Recover buttons in deleted identity Documents
	 */
	
	@Test(priority=5)
	public void FB_Automation_TC005() throws Throwable 
	{
		
		logger =report.startTest("FB_Automation_TC005","Delete single identity, Verify Cancel, close & Recover buttons in deleted identity Documents");
		System.out.println("[INFO]--> FB_Automation_TC005 - TestCase Execution Begins");
	
		/* Login as AS User */
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/** create identity and then delete single identity **/
			FB_Automation_CommonMethods.deleteIdentity();
			
			/** check deleted items list **/
			FB_Automation_CommonMethods.verifyCancelAndCloseButtonInDeletedItems();
			
			/** recover deleted identity **/
			FB_Automation_CommonMethods.recoverDeletedItems();
			
				
			/** Logout from Application **/
			LoginPage.logout();
		}	
		else
			logger.log(LogStatus.FAIL, "Login failed");
		
		
	}
	
	
	/*
	 * TC006 : Executing Trial recon job
	 */
	
	@Test(priority=6)
	public void FB_Automation_TC006() throws Throwable 
	{
		logger =report.startTest("FB_Automation_TC006","Executing Trial recon job");
		System.out.println("[INFO]--> FB_Automation_TC006 - TestCase Execution Begins");
	
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC006");
		
		/** Login as AS User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			FB_Automation_CommonMethods.executeTrialReconjob((String) testData.get("recon_entity"),(String) testData.get("recon_connector"),(String) testData.get("recon_scheduletype"),(String) testData.get("recon_prefeedrule"),(boolean) testData.get("recon_createrequest"),(boolean) testData.get("recon_fetchentity"));
		
			/* Logout from Application */
			LoginPage.logout();
		}
		else
			logger.log(LogStatus.FAIL, "Login Failed");
	}
	
	/*
	 * TC007 : Deleting multiple recon records
	 */
	
	@Test(priority=7)
	public void FB_Automation_TC007() throws Throwable 
	{
		logger =report.startTest("FB_Automation_TC007","deleting multiple recon records");
		System.out.println("[INFO]--> FB_Automation_TC007 - TestCase Execution Begins");
	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC007");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");

			boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));

			if(loginStatus){
				logger.log(LogStatus.PASS, "Login Successful");
			
			FB_Automation_CommonMethods.deleteMultipleReconRecords((String) testData.get("recon_entity"),(String) testData.get("recon_connector"),(String) testData.get("recon_prefeedrule"),(String) testData.get("recon_scheduletype"),(boolean) testData.get("recon_createrequest"),(boolean) testData.get("recon_fetchentity"));
		
			/* Logout from Application */
			LoginPage.logout();
		}
		else
			logger.log(LogStatus.FAIL, "Login Failed");
	}
	
	/*
	 * TC008 : Search Invalid term, validate download icon, download icon functionality, Settings icon functionality	 
	 */
	@Test(priority=8)
	public void FB_Automation_TC008() throws Throwable 
	{

		logger =report.startTest("FB_Automation_TC008","Search Invalid term, valdate download icon, download icon functionality, Settings functionality");
		System.out.println("[INFO]--> FB_Automation_TC008 - TestCase Execution Begins");

		/* Login as AS User */	
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
		
			/* Search Invalid term on recon setup */
			FB_Automation_CommonMethods.searchInvalidTermOnReconSetup("Invalid");
			Utility.pause(1);
				
			/* Search Invalid term on recon monitor */
			FB_Automation_CommonMethods.searchInvalidTermOnReconMonitor("Invalid");
			Utility.pause(1);
		
			/*Validate download icon and its functionality */
			FB_Automation_CommonMethods.validateDownloadFunctionality();
		
			/* Search Invalid term on recon Remediation */
			FB_Automation_CommonMethods.searchInvalidTermOnReconRemediation("Invalid");
			Utility.pause(1);
			
			/*Validate download icon and its functionality in Recon Remediation */
			FB_Automation_CommonMethods.validateDownloadFunctionalityInReconRemediation();
		 
		 
			/* Logout from Application */
			LoginPage.logout();
		}	
		else
			logger.log(LogStatus.FAIL, "login failed");
	
	}
	
	
	/*
	 * TC009 : Create identity through API. 
	 * Rerun job
	 */
	
	@Test(priority=9)
	public void FB_Automation_TC009() throws Throwable 
	{
		logger =report.startTest("FB_Automation_TC009","Create identity through API"); 
	 	System.out.println("[INFO]--> FB_Automation_TC009 - TestCase Execution Begins");
	 	HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC009");
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
	 	
	 	if(ApiMethods.generateAccessToken((String) testData.get("admin_username"),(String) testData.get("admin_password")))
	 	{
	 		if(ApiMethods.createIdentityThroughAPI()) {
	 			FB_Automation_CommonMethods.assignBadgeToUserInCCURE();
	 			
	 			/** Login as AS User **/
	 			boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("recon_entity"), (String) testData.get("recon_entity"));

	 			if(loginStatus){
	 				logger.log(LogStatus.PASS, "Login Successful");
	 				
	 				/** Create Recon Job **/
	 				FB_Automation_CommonMethods.setUpReconJob((String) testData.get("recon_entity"),(String) testData.get("recon_connector"),(String) testData.get("recon_prefeedrule"),(String) testData.get("recon_scheduletype"),(boolean) testData.get("recon_createrequest"),(boolean) testData.get("recon_fetchentity"));
	 				FB_Automation_CommonMethods.validateIdentityData();	 	
	 				FB_Automation_CommonMethods.validateAssetsData();
	 				FB_Automation_CommonMethods.validateAccessesData();
	 				LoginPage.logout();
			}	
			else
				logger.log(LogStatus.FAIL, "login failed");
	 		}
	 		else {
	 			logger.log(LogStatus.FAIL, "failed to create identity in backend");
	 		}
	 	}
	}
	/*
	 * TC010 : User Recon from DB .Add Record , Delete Record , check filter functionality , search functionality , settings icon functionality
	 */
	
	@Test(priority=10)
	public void FB_Automation_TC010() throws Throwable 
	{
		logger =report.startTest("FB_Automation_TC010","Role Recon Job Execution ,Add Record , Delete Record , check filter functionality , search functionality , settings icon functionality, incremental recon");
		System.out.println("[INFO]--> FB_Automation_TC010 - TestCase Execution Begins");
		AGlobalComponents.DBUserRecon=true;
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC010");
		
		/** Login as AS User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("recon_entity"), (String) testData.get("recon_entity"));

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/** Create Recon Job **/
			FB_Automation_CommonMethods.setUpReconJob((String) testData.get("recon_entity"),(String) testData.get("recon_connector"),(String) testData.get("recon_prefeedrule"),(String) testData.get("recon_scheduletype"),(boolean) testData.get("recon_createrequest"),(boolean) testData.get("recon_fetchentity"));
				
			/** delete the created recon record **/
			FB_Automation_CommonMethods.deleteReconRecord((String) testData.get("recon_entity"));
				
			/** Logout from Application **/
			LoginPage.logout();
		}	
		else
			logger.log(LogStatus.FAIL, "Login failed");	
	}	
	
	@Test(priority=11)
	public void FB_Automation_TC011() throws Throwable 
	{
		logger =report.startTest("FB_Automation_TC011","Employee Onboarding from HR DB Connector");
		System.out.println("[INFO]--> FB_Automation_TC011 - TestCase Execution Begins");

		String firstName=Utility.getRandomString(5);
		String lastName="Onboard";
		String userId=Utility.UniqueNumber(6);
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC011");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");

		if(FB_Automation_CommonMethods.createUserInHRDb(firstName,lastName,userId)) {
			boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));

			if(loginStatus){
				logger.log(LogStatus.PASS, "Login Successful");
			
				/* Create Recon Job */
				FB_Automation_CommonMethods.setUpReconJob((String) testData.get("recon_entity"),(String) testData.get("recon_connector"),(String) testData.get("recon_prefeedrule"),(String) testData.get("recon_scheduletype"),(boolean) testData.get("recon_createrequest"),(boolean) testData.get("recon_fetchentity"));
						
				/**creating asset for the user**/
			 	AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				Utility.pause(10);	
				/** Launch New Private Browser **/
				Utility.switchToNewBrowserDriver();
				
				/* Login as Manager */
				loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

				if(loginStatus){
					logger.log(LogStatus.PASS, "Login Successful");
					
					/** checkStatusInMyRequestInbox**/
					Self_Service_CommonMethods.checkRequestInManagerInbox((String) testData.get("request_type"),firstName,lastName);
			 		
					/** approve request  by manager**/
					Self_Service_CommonMethods.approveRequestInInbox((String) testData.get("workflow_stage"));
					
					/* Logout from application */
					LoginPage.logout();
			 		
					/* Login as Badge Admin */
					loginStatus = LoginPage.loginAEHSC((String) testData.get("badge_admin_username"), (String) testData.get("badge_admin_password"));

					if(loginStatus){
						logger.log(LogStatus.PASS, "Login Successful");
			 	 			
						/** approve request By badge admin **/
						Self_Service_CommonMethods.approveRequestInInbox((String) testData.get("workflow_stage2"));
						
						Self_Service_CommonMethods.checkRequestInCompletedInbox((String) testData.get("request_type"),firstName,lastName,"");	
					}
				}
				
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
			 		
				/** Validate  created User in IDM after  request approved**/
				Self_Service_CommonMethods.checkStatusAfterRequestApproval("","","",(String) testData.get("script_name"));
			 	
				Utility.updateDataInDatasource("FB_Automation_TC011", "first_name", firstName);
				Utility.updateDataInDatasource("FB_Automation_TC011", "last_name", lastName);
				Utility.updateDataInDatasource("FB_Automation_TC011", "user_id", userId);
				Utility.updateDataInDatasource("FB_Automation_TC011", "full_name", firstName+" "+lastName);
				Utility.updateDataInDatasource("FB_Automation_TC011", "badge_name", AGlobalComponents.assetName);
				
				/** Logout from Application **/
				LoginPage.logout();		
			}
			else {
				logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
			}
		} 
	}
	
	@Test(priority=12)
	public void FB_Automation_TC012() throws Throwable 
	{

		logger =report.startTest("FB_Automation_TC012","Change Job Title from HR DB Connector");
		System.out.println("[INFO]--> FB_Automation_TC012 - TestCase Execution Begins");
	
		HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource("FB_Automation_TC011");
		String userId = (String) testData1.get("user_id");
		String firstName=(String) testData1.get("first_name");
		String lastName=(String) testData1.get("last_name");
		
		if(userId==null||userId.equals(""))
		{
			logger.log(LogStatus.INFO, "UserId doesn't exists in Db,Executing EmployeeOnboarding");
			FB_Automation_TC011();
		}
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC012");
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String jobTitle = (String) testData.get("job_title");
		
		if(FB_Automation_CommonMethods.changeEmpJobTitleThroughHRDB(userId,jobTitle)) {
			boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));

			if(loginStatus){
				logger.log(LogStatus.PASS, "Login Successful");
				
				/* Create Recon Job */
				FB_Automation_CommonMethods.setUpReconJob((String) testData.get("recon_entity"),(String) testData.get("recon_connector"),(String) testData.get("recon_prefeedrule"),(String) testData.get("recon_scheduletype"),(boolean) testData.get("recon_createrequest"),(boolean) testData.get("recon_fetchentity"));
							
				/** Launch New Private Browser **/
				Utility.switchToNewBrowserDriver();
				
				/* Login as Manager */
				loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

				if(loginStatus){
					logger.log(LogStatus.PASS, "Login Successful");
					
					/** checkStatusInMyRequestInbox**/
					Self_Service_CommonMethods.checkRequestInManagerInbox((String) testData.get("request_type"),firstName,lastName);
			 		
					/** approve request  by manager**/
					Self_Service_CommonMethods.approveRequestInInbox((String) testData.get("workflow_stage"));
					
					Self_Service_CommonMethods.checkRequestInCompletedInbox((String) testData.get("request_type"),"","","");
			 			
				}
				
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
			 		
				/** Validate  Changed Job Title in IDM after  request approved**/
		 		Self_Service_CommonMethods.checkStatusAfterRequestApproval("","",jobTitle,(String) testData.get("script_name"));
	
				/** Logout from Application **/
				LoginPage.logout();		
			}
			else {
				logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
			}	
		}
	}
	
	@Test(priority=13)
	public void FB_Automation_TC013() throws Throwable 
	{

		logger =report.startTest("FB_Automation_TC013","Employee Type Conversion From Permanent To Temporary from HR DB Connector");
		System.out.println("[INFO]--> FB_Automation_TC013 - TestCase Execution Begins");

		HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource("FB_Automation_TC011");
		String userId = (String) testData1.get("user_id");
		
		if(userId==null||userId.equals(""))
		{
			logger.log(LogStatus.INFO, "UserId doesn't exists in Db,Executing EmpOnboarding");
			FB_Automation_TC011();
		}
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC013");
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String employeeType =  (String) testData.get("employee_type");
		String accessName =  (String) testData.get("access_name_1");
		
		if(FB_Automation_CommonMethods.empTypeConversionThroughHRDB(userId,employeeType)) {
			boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));

			if(loginStatus){
				logger.log(LogStatus.PASS, "Login Successful");
				
				/* Create Recon Job */
				FB_Automation_CommonMethods.setUpReconJob((String) testData.get("recon_entity"),(String) testData.get("recon_connector"),(String) testData.get("recon_prefeedrule"),(String) testData.get("recon_scheduletype"),(boolean) testData.get("recon_createrequest"),(boolean) testData.get("recon_fetchentity"));
							
				/* check request in admin */
				Self_Service_CommonMethods.checkRequestInCompletedInbox((String) testData.get("request_type"),"","",accessName);
			 			
				/** Validate  Changed Job Title in IDM after  request approved**/
		 		Self_Service_CommonMethods.checkStatusAfterRequestApproval("","","",(String) testData.get("script_name"));
		 		
		 		/** Logout from Application **/
				LoginPage.logout();	
			}
			else {
				logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
			}	
		}
	}
	
	@Test(priority=14)
	public void FB_Automation_TC014() throws Throwable 
	{

		logger =report.startTest("FB_Automation_TC014","Employee Type Conversion From Temporary To Permanent from HR DB Connector");
		System.out.println("[INFO]--> FB_Automation_TC014 - TestCase Execution Begins");

		HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource("FB_Automation_TC011");
		String userId = (String) testData1.get("user_id");
		
		if(userId==null||userId.equals(""))
		{
			logger.log(LogStatus.INFO, "UserId doesn't exists in Db,Executing EmpOnboarding");
			FB_Automation_TC011();
		}
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC014");
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String employeeType =  (String) testData.get("employee_type");
		String accessName =  (String) testData.get("access_name_1");
		
		if(FB_Automation_CommonMethods.empTypeConversionThroughHRDB(userId,employeeType)) {
			boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));

			if(loginStatus){
				logger.log(LogStatus.PASS, "Login Successful");
				
				/* Create Recon Job */
				FB_Automation_CommonMethods.setUpReconJob((String) testData.get("recon_entity"),(String) testData.get("recon_connector"),(String) testData.get("recon_prefeedrule"),(String) testData.get("recon_scheduletype"),(boolean) testData.get("recon_createrequest"),(boolean) testData.get("recon_fetchentity"));
							
				/* check request in admin */
				Self_Service_CommonMethods.checkRequestInCompletedInbox((String) testData.get("request_type"),"","",accessName);
			 			
				/** Validate  Changed Job Title in IDM after  request approved**/
		 		Self_Service_CommonMethods.checkStatusAfterRequestApproval("","","",(String) testData.get("script_name"));
		 		
		 		/** Logout from Application **/
				LoginPage.logout();	
			}
			else {
				logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
			}	
		}
	}
	
	@Test(priority=15)
	public void FB_Automation_TC015() throws Throwable 
	{

		logger =report.startTest("FB_Automation_TC015","Employee Offboarding from HR DB Connector");
		System.out.println("[INFO]--> FB_Automation_TC015 - TestCase Execution Begins");

		HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource("FB_Automation_TC011");
		String userId = (String) testData1.get("user_id");
		String firstName=(String) testData1.get("first_name");
		String lastName=(String) testData1.get("last_name");
		AGlobalComponents.assetName=(String) testData1.get("badge_name");
		
		if(userId==null||userId.equals(""))
		{
			logger.log(LogStatus.INFO, "UserId doesn't exists in Db,Executing Change Of JobTitle");
			FB_Automation_TC012();
		}
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC015");
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		
		if(FB_Automation_CommonMethods.empTerminateThroughHRDB(userId)) {
			boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));

			if(loginStatus){
				logger.log(LogStatus.PASS, "Login Successful");
				
				/* Create Recon Job */
				FB_Automation_CommonMethods.setUpReconJob((String) testData.get("recon_entity"),(String) testData.get("recon_connector"),(String) testData.get("recon_prefeedrule"),(String) testData.get("recon_scheduletype"),(boolean) testData.get("recon_createrequest"),(boolean) testData.get("recon_fetchentity"));
							
				/** Launch New Private Browser **/
				Utility.switchToNewBrowserDriver();
				
				/* Login as Manager */
				loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

				if(loginStatus){
					logger.log(LogStatus.PASS, "Login Successful");
					
					/** checkStatusInMyRequestInbox**/
					Self_Service_CommonMethods.checkRequestInManagerInbox((String) testData.get("request_type"),firstName,lastName);
			 		
					/** approve request  by manager**/
					Self_Service_CommonMethods.approveRequestInInbox((String) testData.get("workflow_stage"));
					
					Self_Service_CommonMethods.checkRequestInCompletedInbox((String) testData.get("request_type"),"","","");
			 		
				}
				
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
			 		
				/** Validate  Changed Job Title in IDM after  request approved**/
		 		Self_Service_CommonMethods.checkStatusAfterRequestApproval("","","",(String) testData.get("script_name"));
		 		
		 		/** Logout from Application **/
				LoginPage.logout();	
			}
			else {
				logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
			}	
		}
	}
	
	@Test(priority=16)
	public void FB_Automation_TC016() throws Throwable 	{
		logger =report.startTest("FB_Automation_TC016","Area Admin Cases(Add Identities,Remove Identities)");
		System.out.println("[INFO]--> Area Admin Cases - TestCase Execution Begins");
		
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC016");
		AGlobalComponents.applicationURL = (String) testData.get("application_url"); 
		String accessName = (String) testData.get("access_name_1");
		
		ArrayList<String> firstNames=new ArrayList<String>();
		ArrayList<String> lastNames= new ArrayList<String>();
		
		/* Login as Manager */
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));
		
		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			for(int i=0;i<1;i++) {
				firstNames.add("Test"+Utility.getRandomString(4));
				lastNames.add("AreaAdmin");
				
				/**creating asset for the user**/
				AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
				
				/** Create Identities **/
				FB_Automation_CommonMethods.createIdentity(firstNames.get(i), lastNames.get(i),"FB_Automation_TC016");
			}
			
			/** Launch New Private Browser **/
			Utility.switchToNewBrowserDriver();
			
			/* Login as Area Admin */
			loginStatus = LoginPage.loginAEHSC((String) testData.get("area_admin_username"), (String) testData.get("area_admin_password"));	
			
			if(loginStatus){
				logger.log(LogStatus.PASS, "Login Successful");
		
				if (FB_Automation_CommonMethods.addIdentities(firstNames, lastNames, accessName)) 
				{
					/** Launch New Private Browser **/
					Utility.switchToNewBrowserDriver();
		
					/* Login as Manager */
					loginStatus = LoginPage.loginAEHSC((String) testData.get("area_admin_username"), (String) testData.get("area_admin_password"));	

					if(loginStatus){
						logger.log(LogStatus.PASS, "Login Successful");
			
						FB_Automation_CommonMethods.removeIdentities(firstNames, lastNames, accessName);
					}
				}
				else
					logger.log(LogStatus.FAIL, "Unable to add Identity");
			}	
			LoginPage.logout();		
		}
		else {
			logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
		}			
	}
	
	/*
	 * TC017 : Modify Identity through IDM
	 */
	
	@Test(priority=17)
	public void FB_Automation_TC017() throws Throwable 
	{
		
		logger =report.startTest("FB_Automation_TC017","***************Modify Identity**************");
		System.out.println("[INFO]--> FB_Automation_TC017 - TestCase Execution Begins");
		HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC017");
		
		AGlobalComponents.applicationURL = (String) testData.get("application_url");
		String firstName ="Test"+Utility.getRandomString(4);
		String lastName =Utility.getRandomString(4);
		String scriptName = (String) testData.get("script_name");
		AGlobalComponents.userId=firstName+"."+lastName;
		String parameterToBeModified=(String) testData.get("parameter_tobemodified");
	
		/** Login as admin User **/
		boolean loginStatus = LoginPage.loginAEHSC((String) testData.get("admin_username"), (String) testData.get("admin_password"));	
		if(loginStatus){
			
			/**create new asset **/
			AGlobalComponents.assetName = Self_Service_CommonMethods.createNewAsset((String) testData.get("badge_type"), (String) testData.get("badge_subtype"), (String) testData.get("badge_system"));
			
			/**create identity **/
			FB_Automation_CommonMethods.createIdentity(firstName,lastName,scriptName);
			
			/** check details of user before modifying identity **/
	 		Self_Service_CommonMethods.checkStatusBeforeRequestSubmission(AGlobalComponents.userId,parameterToBeModified,"",scriptName);
		
	 		/** Launch New Private Browser **/
	 		Utility.switchToNewBrowserDriver();
			
	 		/* Login as Manager */
	 		loginStatus = LoginPage.loginAEHSC((String) testData.get("manager_username"), (String) testData.get("manager_password"));

	 		if(loginStatus){
	 			logger.log(LogStatus.PASS, "Login Successful");
					
	 			/** Modify Identity **/
	 			FB_Automation_CommonMethods.modifyIdentityIDM(parameterToBeModified,scriptName);
	 				 		
	 				
	 			/** Switch to Default Browser **/
	 			Utility.switchToDefaultBrowserDriver();
	 		}
	 		else{
	 			logger.log(LogStatus.FAIL, "Unable to Login as end user. Plz Check Application");
	 		}

	 		/** Validate  status in IDM after  request approved**/
	 		Self_Service_CommonMethods.checkStatusAfterRequestApproval(firstName,parameterToBeModified,"",scriptName);
	 		
	 		/** Logout from Application **/
	 		LoginPage.logout();
		
	 	}
	 	else {
	 		logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
	 	}	
		
	}
	
}
