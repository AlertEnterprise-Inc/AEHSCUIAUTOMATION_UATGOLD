package webDriverTestCases;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.Utility;
import CommonFunctions.FB_Automation_CommonMethods;
import CommonFunctions.LoginPage;


public class FB_Automation extends BrowserSelection {

	
	String testName;
	
	
	@BeforeMethod
	public void commonPage(java.lang.reflect.Method method) throws Throwable
	{
		unhandledException = false;	
		testName = method.getName();
		driver.navigate().refresh();
	}
	
	public static void isConditionX(boolean condition) {
		if(condition==false){
			throw new SkipException("skipp");
		}
	}
			
	/*
	 * TC001 : Recon Job Execution.Add Record , Delete Record , check filter functionality , search functionality , settings icon functionality
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
		}	
			
		/* Create Recon Job */
		FB_Automation_CommonMethods.setUpReconJob();
		
		FB_Automation_CommonMethods.deleteReconRecord();
		
			
		/* Logout from Application */
		LoginPage.logout();
		
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
		LoginPage.loginAEHSC("admin", "Alert1234");

		logger.log(LogStatus.PASS, "Login Successful");
			
		/* Mandatory fields check while Create Identity */
				
		FB_Automation_CommonMethods.mandatoryFieldsCheck();
		
			
		/* Logout from Application */
		LoginPage.logout();
		
	}
	
	/*
	 * TC003 : Create Identity , Search Identity , Edit Identity , Duplicate Check , deleting multiple identities
	 */
	
	@Test(priority=3)
	public void FB_Automation_TC003() throws Throwable 
	{
		
		logger =report.startTest("FB_Automation_TC003","Create Identity,search Identity ,edit Identity ,Duplicate check identity");
		System.out.println("[INFO]--> FB_Automation_TC002 - TestCase Execution Begins");
	
		/* Login as AS User */
		LoginPage.loginAEHSC("admin", "Alert1234");

		logger.log(LogStatus.PASS, "Login Successful");
			
		/* Identity Management */
				
		FB_Automation_CommonMethods.createIdentity();
		
		FB_Automation_CommonMethods.searchIdentity();
		
		FB_Automation_CommonMethods.editIdentity();
		
		FB_Automation_CommonMethods.createDuplicateIdentity();
		
		FB_Automation_CommonMethods.searchIdentity();
		
		FB_Automation_CommonMethods.deleteMultipleIdentities();
			
		/* Logout from Application */
		LoginPage.logout();
		
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
		LoginPage.loginAEHSC("admin", "Alert1234");

		logger.log(LogStatus.PASS, "Login Successful");
			
		FB_Automation_CommonMethods.cancelCreateIdentity();
		
		FB_Automation_CommonMethods.showHideFilterWidget();
		
			
		/* Logout from Application */
		LoginPage.logout();
		
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
		LoginPage.loginAEHSC("admin", "Alert1234");

		logger.log(LogStatus.PASS, "Login Successful");
			
		FB_Automation_CommonMethods.deleteIdentity();
		
		FB_Automation_CommonMethods.verifyCancelAndCloseButtonInDeletedItems();
		
		FB_Automation_CommonMethods.recoverDeletedItems();
		
			
		/* Logout from Application */
		LoginPage.logout();
		
	}
	
		
}
