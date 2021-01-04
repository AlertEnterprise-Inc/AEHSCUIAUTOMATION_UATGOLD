package webDriverTestCases;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.BrowserSelection;
import CommonFunctions.FB_Automation_CommonMethods;
import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.ApiMethods;
import CommonFunctions.LoginPage;
import io.restassured.RestAssured;
import CommonClassReusables.Utility;


public class FB_Automation extends BrowserSelection {

	
	String testName;
	
	
	@BeforeMethod
	public void commonPage(java.lang.reflect.Method method) throws Throwable
	{
		unhandledException = false;	
		testName = method.getName();
		AGlobalComponents.takeScreenshotIfPass = false;
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
		
		logger =report.startTest("FB_Automation_TC001","Recon Job Execution ,Add Record , Delete Record , check filter functionality , search functionality , settings icon functionality");
		System.out.println("[INFO]--> FB_Automation_TC001 - TestCase Execution Begins");
		
		/* Login as AS User */
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert1234");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/* Create Recon Job */
			logger.log(LogStatus.INFO ,  "Recon Job Execution");
			FB_Automation_CommonMethods.setUpReconJob();
			
			logger.log(LogStatus.INFO ,  "Rerun the created recon record");
			FB_Automation_CommonMethods.rerunReconRecord();
			
			logger.log(LogStatus.INFO ,  "Deleting the created recon record");
			FB_Automation_CommonMethods.deleteReconRecord();
			
				
			/* Logout from Application */
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
	
		/* Login as AS User */
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert1234");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/* Mandatory fields check while Create Identity */
			logger.log(LogStatus.INFO ,  "Mandatory field check on create identity");
			FB_Automation_CommonMethods.mandatoryFieldsCheck();
			
				
			/* Logout from Application */
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
	
		/* Login as AS User */
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert1234");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			/* Identity Management */
			
			logger.log(LogStatus.INFO, "Create new Identity");
			FB_Automation_CommonMethods.createIdentity();
			
			logger.log(LogStatus.INFO, "Search the above created Identity");
			FB_Automation_CommonMethods.searchIdentity();
			
			logger.log(LogStatus.INFO, "edit the job title of created identity");
			FB_Automation_CommonMethods.editIdentity();
			
			logger.log(LogStatus.INFO, "Create duplicate Identity");
			FB_Automation_CommonMethods.createDuplicateIdentity();
			
			logger.log(LogStatus.INFO, "Again searching the identity");
			FB_Automation_CommonMethods.searchIdentity();
			
			logger.log(LogStatus.INFO, "deleting the mutiple identities");
			FB_Automation_CommonMethods.deleteMultipleIdentities();
				
			/* Logout from Application */
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
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert1234");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			logger.log(LogStatus.INFO, "test showHideFilterWidge functionality on create identity screen");
			FB_Automation_CommonMethods.cancelCreateIdentity();
			
			logger.log(LogStatus.INFO, "test cancel button functionality on create identity screen");
			FB_Automation_CommonMethods.showHideFilterWidget();
			
				
			/* Logout from Application */
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
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert1234");

		if(loginStatus){
			logger.log(LogStatus.PASS, "Login Successful");
			
			logger.log(LogStatus.INFO, "Create  identity and then delete the identity");
			FB_Automation_CommonMethods.deleteIdentity();
			
			logger.log(LogStatus.INFO, "check the deleted identity in deleted items");
			FB_Automation_CommonMethods.verifyCancelAndCloseButtonInDeletedItems();
			
			logger.log(LogStatus.INFO, "recover the above deleted identity");
			FB_Automation_CommonMethods.recoverDeletedItems();
			
				
			/* Logout from Application */
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
		boolean login =LoginPage.loginAEHSC("admin", "Alert1234");

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
		boolean login =LoginPage.loginAEHSC("admin", "Alert1234");

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
		boolean loginStatus = LoginPage.loginAEHSC("admin", "Alert1234");

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
		
	 	if(ApiMethods.generateAccessToken())
	 	{
	 		ApiMethods.createIdentityThroughAPI();
	 		FB_Automation_CommonMethods.validateIdentityData();
	 	}
	}
}
