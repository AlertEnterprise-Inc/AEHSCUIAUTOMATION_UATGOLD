package webDriverTestCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.Utility;
import CommonFunctions.Airport_Badging_CommonMethods;
import CommonFunctions.LoginPage;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;


public class UI_Automation extends BrowserSelection {

	
	String testName;
	
	@BeforeClass
	public void Login() throws Exception {
//		LoginPage.login(AGlobalComponents.username,AGlobalComponents.password);
	}
	
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
			

@Test(priority=1)
	public void Airport_Badging_TEST001212() throws Throwable 
	{
	
		logger =report.startTest("Airport_Badging_TC001: LAWA-T266, LAWA-T287 & LAWA-T288", "Create Aplicant Invitation - AS Enrolling Applicant and Submit Badge Renewal");
		System.out.println("[INFO]--> Airport_Badging_TC001 - TestCase Execution Begins");
		
		/* Login as AS User */
		LoginPage.loginSelfService("DEV0000002190", "Alert1234");

		logger.log(LogStatus.PASS, "Login Successful");
		AGlobalComponents.applicantFirstName = ("Auto"+Utility.UniqueString(4)).toUpperCase();
		AGlobalComponents.applicantSSN = Utility.UniqueNumber(9);
		String submitBadgeRenewal = "NO";
		
		/* Create Applicant Invitation */
		Airport_Badging_CommonMethods.createAplicantInvitationASPortal("AS");
		
		/* Self Service Application Completion */
		Airport_Badging_CommonMethods.selfServiceMyApplications("LAKE000000805","Alert1234");
		driver.navigate().refresh();
		
		/* Modify Applicant Invitation */
		Airport_Badging_CommonMethods.modifyAplicantInvitationASPortal(AGlobalComponents.applicantFirstName,"AS");
		
		if(submitBadgeRenewal.equalsIgnoreCase("YES"))
		{
			/* Submit Badge Renewal Application */
			Airport_Badging_CommonMethods.submitBadgeRenewal(AGlobalComponents.applicantFirstName);
			
			/* Self Service Application Completion */
			Airport_Badging_CommonMethods.selfServiceMyApplicationsBadgeRenewal("LAKE000000805","Alert1234");
			driver.navigate().refresh();
		}
		
		/* Logout from AS Portal */
		LoginPage.logoutSelfService();
		
		/* Login to Guardian as AS User */
		LoginPage.login("DEV0000002190","Alert1234");
		
		/* Validate Applicant Invitation Active Status */
		Airport_Badging_CommonMethods.validateAplicantApproved(AGlobalComponents.applicantFirstName);
		
		/* Validate Applicant Status in DB */
		Utility.validateApplicantCreatedDB(AGlobalComponents.applicantFirstName,"34.93.172.239","sqlserver","Alert1234","gdn_db_ps","SQLSERVER");
		
		/* Update Self Service Email ID */
		Utility.updateSelfServiceEmail(AGlobalComponents.applicantFirstName,"34.93.172.239","sqlserver","Alert1234","self_service_db_ps");
		
		/* Update Guardian Email ID */
		Utility.updateGuardianUserEmail("34.93.172.239","sqlserver","Alert1234","gdn_db_ps");
		
		/* Logout from Application */
		LoginPage.logout();
		
	}

	
}