package webDriverTestCases;

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
	
		/** Login as AS User **/
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/** Create Recon Job **/
			FB_Automation_CommonMethods.setUpReconJob();
			
			/** Rerun the role recon job **/
			FB_Automation_CommonMethods.rerunReconRecord();
			
			/** Deleting the Recon Record **/
			FB_Automation_CommonMethods.deleteReconRecord();
			
				
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
			FB_Automation_CommonMethods.modifyIdentity();
			
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
	
		/* Login as AS User */
		boolean login =LoginPage.loginAEHSC("admin", "Alert@783");

		if(login)
		{
			logger.log(LogStatus.PASS, "Login Successful");
		
			FB_Automation_CommonMethods.executeTrialReconjob();
		
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
	
		/* Login as AS User */
		boolean login =LoginPage.loginAEHSC("admin", "Alert@783");

		if(login)
		{
			logger.log(LogStatus.PASS, "Login Successful");
					
			FB_Automation_CommonMethods.deleteMultipleReconRecords();
		
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
	 	AGlobalComponents.CCUREUserRecon=true;
	 	AGlobalComponents.rerunReconJob= true;
	 	
	 	if(ApiMethods.generateAccessToken())
	 	{
	 		if(ApiMethods.createIdentityThroughAPI()) {
	 			FB_Automation_CommonMethods.assignBadgeToUserInCCURE();
	 			boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");

	 			if(loginStatus){
	 				logger.log(LogStatus.PASS, "Login Successful");
			
	 				FB_Automation_CommonMethods.setUpReconJob();
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
		
		logger =report.startTest("FB_Automation_TC001","Role Recon Job Execution ,Add Record , Delete Record , check filter functionality , search functionality , settings icon functionality, incremental recon");
		System.out.println("[INFO]--> FB_Automation_TC001 - TestCase Execution Begins");
		AGlobalComponents.DBUserRecon=true;
		
		/* Login as AS User */
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/* Create Recon Job */
			FB_Automation_CommonMethods.setUpReconJob();
			
			/** delete the created recon record **/
			FB_Automation_CommonMethods.deleteReconRecord();
				
			/** Logout from Application **/
			LoginPage.logout();
		}	
		else
			logger.log(LogStatus.FAIL, "Login failed");	
	}	
	
	@Test(priority=11)
	public void FB_Automation_TC011() throws Throwable 
	{
		logger =report.startTest("SuccessFactors HR System Usecases E01","Employee Onboarding from HR DB Connector");
		System.out.println("[INFO]--> SuccessFactors HR System Usecases E01 - TestCase Execution Begins");
		AGlobalComponents.EmpOnboardingthroughHRDb=true;
		AGlobalComponents.DBUserRecon=true;
		AGlobalComponents.applicationURL="http://aepdemo.alertenterprise.com/";
		if(FB_Automation_CommonMethods.createUserInHRDb()) {
			boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert@783");

			if(loginStatus){
				logger.log(LogStatus.PASS, "Login Successful");
			
				/* Create Recon Job */
				FB_Automation_CommonMethods.setUpReconJob();
						
				/**creating asset for the user**/
				AGlobalComponents.badgeName = Self_Service_CommonMethods.createNewAsset("Permanent Badge", "SRSeries_10And12Digit", "CCURE 9000");
					
				/** Launch New Private Browser **/
				Utility.switchToNewBrowserDriver();
				
				/* Login as Manager */
				loginStatus = LoginPage.loginAEHSC("anna.mordeno", "Alert1234");

				if(loginStatus){
					logger.log(LogStatus.PASS, "Login Successful");
					
					/** checkStatusInMyRequestInbox**/
					Self_Service_CommonMethods.checkRequestInManagerInbox();
			 		
					/** approve request  by manager**/
					Self_Service_CommonMethods.approveRequestInInbox("Manager");
			 			
					/* Logout from application */
					LoginPage.logout();
			 		
					/* Login as Badge Admin */
					loginStatus = LoginPage.loginAEHSC("badge.admin", "Alert1234");

					if(loginStatus){
						logger.log(LogStatus.PASS, "Login Successful");
			 	 			
						/** approve request By badge admin **/
						Self_Service_CommonMethods.approveRequestInInbox("Badge Admin");
					}
				}
				
				/** Switch to Default Browser **/
				Utility.switchToDefaultBrowserDriver();
			 		
				/** Validate  created User in IDM after  request approved**/
				Self_Service_CommonMethods.checkUserStatus();
			 		
				/** Validate  created employee in database**/
		//		Self_Service_CommonMethods.checkStatusInDB(firstName,lastName);
			 		
				/** Logout from Application **/
				LoginPage.logout();		
			}
			else {
				logger.log(LogStatus.FAIL, "Unable to Login----> Plz Check Application");
			}	
		}
	}
}
