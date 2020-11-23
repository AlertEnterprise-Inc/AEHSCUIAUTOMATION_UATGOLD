package CommonFunctions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.mongodb.diagnostics.logging.Logger;
import com.relevantcodes.extentreports.LogStatus;
import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.ByAttribute;
import CommonClassReusables.ByAttributeAngular;
import CommonClassReusables.MsSql;
import CommonClassReusables.ReadDataFromPropertiesFile;
import CommonClassReusables.Utility;
import CommonClassReusables.dynamicwait;
import ObjectRepository.HomeObjects;
import ObjectRepository.LoginObjects;
import ObjectRepository.SelfServiceObjects;
import ObjectRepository.SetupObjects;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ObjectRepository.BadgingObjects;
import okhttp3.internal.http2.Settings;

public class Airport_Badging_CommonMethods extends BrowserSelection 
{
	
	
	/**
	* <h1>createAppointmentType</h1>
	* This is Method to Create an Appointment
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-06-2020
	* @param   	String appointmentType
	* @return  	none
	**/

	public static void createAppointmentType(String appointmentType) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** createAppointmentType *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(10);
				driver.navigate().refresh();
				Utility.pause(20);
				ByAttribute.clickOnPod("Appointment Scheduler");
				Thread.sleep(2000);
				ByAttribute.click("xpath", BadgingObjects.badgingCalendarConfigurationsLnk, "Click on Calendar Configurations Link");
				Thread.sleep(2000);
				ByAttribute.click("xpath", BadgingObjects.badgingAppointmentTypeTabLnk, "Click on Appointment Type Tab Link");
				Thread.sleep(2000);
				ByAttribute.click("xpath", BadgingObjects.badgingAppointmentTypeCreateBtn, "Click on Create Button");
				
				if(driver.findElements(By.xpath(BadgingObjects.badgingAppointmentTypeDetailsLbl)).size()>0)
				{				
					System.out.println("Appointment Type Details Form is available");
					logger.log(LogStatus.PASS, "Appointment Type Details Form is available");	
					
					ByAttribute.clearSetText("xpath", BadgingObjects.badgingAppointmentTypeTxt, appointmentType, "Enter Appointment Type");
					ByAttribute.clearSetText("xpath", BadgingObjects.badgingAppointmentSlotTxt, "30", "Enter Appointment Slot Minutes");
					ByAttribute.clearSetText("xpath", BadgingObjects.badgingAppointmentTypeDescriptionTxt, "This is a Test Slot", "Enter Appointment Type Description");
					ByAttribute.clearSetText("xpath", BadgingObjects.badgingAppointmentTypeLabelTxt, "QA Automation", "Enter Appointment Type Label");
					ByAttribute.clearSetText("xpath", BadgingObjects.badgingAppointmentTypeAdvDaysTxt, "1", "Enter Appointment Type Advance Days");

					ByAttribute.click("xpath", BadgingObjects.badgingAppointmentTypeSaveBtn, "Click on Save Button");
					Thread.sleep(3000);
					Utility.verifyElementPresent(BadgingObjects.badgingAppointmentTypeAddedSuccessfullyLbl, "Appointment Type Added Successfully Label", false, false);
					ByAttribute.click("xpath", BadgingObjects.badgingAppointmentTypeOKBtn, "Click on OK Button");
					
					Thread.sleep(5000);
					try{
						driver.findElement(By.xpath(".//*[@id='comp_15_headers']//tr[@data-role='filterrow']/td[1]/span/input")).click();
						driver.findElement(By.xpath(BadgingObjects.badgingAppointmentNameFilterTxt)).sendKeys(appointmentType);
						Utility.verifyElementPresent(".//tbody/tr/td[text()='"+appointmentType+"']", "Created Appointment Entry", false, true);
					}catch(Exception e){
						System.out.println("Delay in Appearance of Appointment Type Table");
						logger.log(LogStatus.WARNING, "Delay in Appearance of Appointment Type Table");	
					}
					
					
				}
				else
				{
					System.out.println("Navigation to 'Appointment Type Details' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Appointment Type Details' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}		
	}
	
	
	/**
	* <h1>createAplicantInvitation</h1>
	* This is Method to Create an Applicant Invite
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-20-2020
	* @param   	String actor
	* @return  	none
	**/

	public static String createAplicantInvitation(String actor) throws Throwable
	{

		String reqNum = "";
		if(unhandledException==false)
		{
			System.out.println("***************************** createAplicantInvitation *********************************");
			logger.log(LogStatus.INFO, "***************************** createAplicantInvitation *********************************");
			try
			{
				Utility.pause(10);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(5);
				driver.navigate().refresh();
				Utility.pause(10);
				ByAttribute.clickOnPod("Applicant Invitation");
				Thread.sleep(2000);
				if(actor.equalsIgnoreCase("ECMU"))
				{
					ByAttribute.click("xpath", BadgingObjects.badgingCreateApplicantInvitationByECMULnk, "Click on Applicant Invitation Link");
				}else{
					ByAttribute.click("xpath", BadgingObjects.badgingApplicantInvitationByASBtn, "Click on Applicant Invitation By AS Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", BadgingObjects.badgingInitiateNewBadgeApplicationLnk, "Click on Applicant Invitation Link");
				}
				
				Thread.sleep(5000);
				
				if(driver.findElements(By.xpath(BadgingObjects.badgingDuplicateCheckLbl)).size()>0)
				{				
					System.out.println("CreateApplicantInvitation - Duplicate Check Form is available");
					logger.log(LogStatus.PASS, "CreateApplicantInvitation - Duplicate Check Form is available");	
					
					String lastName = "Test";
					String dob = "03/26/1989";
					String email = "automation.alertuser04@gmail.com";
					String jobRole = "A P MECHANIC";
					String organization = "BRITISH AIRWAYS-LEO";
					String badgeIcons = "ATC";
					
//					ByAttribute.clearSetText("xpath", BadgingObjects.badgingDupFirstNameTxt, AGlobalComponents.applicantFirstName, "Enter First Name: "+AGlobalComponents.applicantFirstName);
//					System.out.println(AGlobalComponents.applicantFirstName);
//					ByAttribute.clearSetText("xpath", BadgingObjects.badgingDupLastNameTxt, lastName, "Enter Last Name");
					ByAttribute.clearSetText("xpath", BadgingObjects.badgingDupDOBTxt, dob, "Enter Date of Birth");
					ByAttribute.clearSetText("xpath", BadgingObjects.badgingDupSSNTxt, AGlobalComponents.applicantSSN, "Enter SSN");
					ByAttribute.click("xpath", BadgingObjects.badgingDupContinueBtn, "Click on Continue Button");
					Thread.sleep(3000);
					
					if(driver.findElement(By.xpath(BadgingObjects.badgingCreateApplicantInvitationEmailTxt)).isEnabled())
					{
						System.out.println("No Duplicate Entry Found");
						logger.log(LogStatus.PASS, "No Duplicate Entry Found");
						
						ByAttribute.selectText("xpath", BadgingObjects.badgingCreateApplicantInvitationCitizenshipTypeDdn, "US Citizen", "Select Citizenship");
						ByAttribute.clearSetText("xpath", BadgingObjects.badgingCreateApplicantInvitationFirstNameTxt, AGlobalComponents.applicantFirstName, "Enter First Name: "+AGlobalComponents.applicantFirstName);
						System.out.println(AGlobalComponents.applicantFirstName);
						ByAttribute.clearSetText("xpath", BadgingObjects.badgingCreateApplicantInvitationLastNameTxt, lastName, "Enter Last Name");
						ByAttribute.clearSetText("xpath", BadgingObjects.badgingCreateApplicantInvitationContactPhoneTxt, Utility.UniqueNumber(10), "Enter Contact Number");
						ByAttribute.clearSetText("xpath", BadgingObjects.badgingCreateApplicantInvitationEmailTxt, email, "Enter Email ID");
						if(actor.equalsIgnoreCase("AS"))
						{
							ByAttribute.clearSetText("xpath", BadgingObjects.badgingCreateApplicantInvitationSSNTxt, AGlobalComponents.applicantSSN, "Enter SSN");
							ByAttribute.clearSetText("xpath", BadgingObjects.badgingCreateApplicantInvitationSSNConfirmationTxt, AGlobalComponents.applicantSSN, "Confirm SSN");
						}
						ByAttribute.selectText("xpath", BadgingObjects.badgingCreateApplicantInvitationAirportDdn, "LAX", "Select Airport from Dropdown");
						ByAttribute.clearSetText("xpath", BadgingObjects.badgingCreateApplicantInvitationOrganizationTxt, organization, "Enter Organization");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//*[@class='ui-menu-item-wrapper' and contains(text(),'"+organization+"')]", "Select Organization : "+organization);
						Thread.sleep(1000);
						if(actor.equalsIgnoreCase("AS"))
						{
							ByAttribute.clearSetText("xpath", BadgingObjects.badgingCreateApplicantInvitationBadgeValidToTxt, "11/11/2030", "Enter Badge Valid To Date");
							Thread.sleep(1000);
							ByAttribute.selectText("xpath", BadgingObjects.badgingCreateApplicantInvitationIsAuthSignatoryDdn, "No", "Select Requires Authorised Signatory as No");
							Thread.sleep(1000);
						}
						
						ByAttribute.clearSetText("xpath", BadgingObjects.badgingCreateApplicantInvitationJobRoleTxt, jobRole, "Enter Job Role");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//*[@class='ui-menu-item-wrapper' and text()='"+jobRole+"']", "Select Job Role : "+jobRole);
						Thread.sleep(1000);
						ByAttribute.selectText("xpath", BadgingObjects.badgingCreateApplicantInvitationBadgeTypeDdn, "Blue (SIDA AOA)", "Select Badge Type from Dropdown");
//						ByAttribute.clearSetText("xpath", BadgingObjects.badgingCreateApplicantInvitationBadgeIconsTxt, badgeIcons, "Enter Badge Icon");
//						Thread.sleep(500);
//						ByAttribute.click("xpath", ".//input[@class='checkbox-multiselect' and @data-label='"+badgeIcons+"']", "Select Badge Icon");
						if(actor.equalsIgnoreCase("ECMU"))
						{
							ByAttribute.selectText("xpath", BadgingObjects.badgingCreateApplicantInvitationIsAuthSignatoryDdn, "Yes", "Requires Authorized Signatory Role Yes");
//							ByAttribute.click("xpath", BadgingObjects.badgingCreateApplicantInvitationReqAuthSignatoryChk, "Requires Authorized Signatory Role Checkbox");
						}
						ByAttribute.click("xpath", BadgingObjects.badgingCreateApplicantInvitationSubmitBtn, "Click Submit Button");
						Thread.sleep(15000);
						Utility.verifyElementPresent(BadgingObjects.badgingCreateApplicantInvitationConfirmationMessageLbl, "Confirmation Pop-Up", false, false);
						ByAttribute.click("xpath", BadgingObjects.badgingCreateApplicantInvitationOKBtn, "Click OK button");
						Utility.pause(5);
						driver.navigate().refresh();
					}else{
						
					}
				}
				else
				{
					System.out.println("Navigation to 'Appointment Type Details' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Appointment Type Details' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
		return reqNum;		
	}
	
	
	/**
	* <h1>createAppointmentTypeAPI</h1>
	* This is Method to Create an Appointment through API
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-06-2020
	* @param   	String cookie, String csrfToken
	* @return  	none
	**/
	
	public static void createAppointmentTypeAPI(String cookie, String csrfToken) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** createAppointmentTypeAPI *********************************");
			logger.log(LogStatus.INFO, "***************************** createAppointmentTypeAPI *********************************");
			try
			{
				RestAssured.baseURI = "http://192.168.194.148:8080/AlertEnterprise/rest/appointment/type";
				
				String rName = "Auto"+Utility.UniqueString(4);
			    String requestBody = "{\"name\":\""+rName+"\",\"description\":\"It is an API Test\",\"advDays\":\"5\",\"label\":\"Creating Through API Test\",\"appointmentTypeIdentityAction\":[{\"identityActionName\":\"Activate AS local user\",\"identityActionId\":66}],\"slotMinutes\":\"20\"}";
			    HashMap<String, String> jsessionIdAndCSRFToken=new HashMap<String, String>();
			              jsessionIdAndCSRFToken.put("Cookie", "JSESSIONID="+cookie);
			              jsessionIdAndCSRFToken.put("X-CSRF-TOKEN", csrfToken);

			    Response response = null;

			    try {
			        response = RestAssured.given().headers(jsessionIdAndCSRFToken).contentType("application/json").body(requestBody).post("http://192.168.194.148:8080/AlertEnterprise/rest/appointment/type");
			    } catch (Exception e) {
			        e.printStackTrace();
			    }

			    System.out.println("For Name : "+rName+", Response :" + response.asString());
			    logger.log(LogStatus.INFO, "For Name : "+rName+", Response :" + response.asString());
			    System.out.println("Status Code :" + response.getStatusCode());
			    logger.log(LogStatus.INFO, "Status Code :" + response.getStatusCode());
			    Assert.assertEquals(response.getStatusCode(),200);
			    logger.log(LogStatus.PASS, "API PASSED SUCCESSFULLY");
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}		
	}
	

	/**
	* <h1>selfServiceMyApplications</h1>
	* This is Method to Complete the Application for through Self Service Portal
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-24-2020
	* @param   	String userID, String password
	* @return  	none
	**/
	
	public static void selfServiceMyApplications(String username, String password) throws Throwable
	{
		
		if(unhandledException==false)
		{
			System.out.println("******************************** selfServiceMyApplications  *****************************************");
			logger.log(LogStatus.INFO, "***************************** selfServiceMyApplications *********************************");
			WebDriver driver2=Utility.openPrivateBrowser();
			default_driver=driver;
			driver=driver2;
			try
			{
				driver2.get(AGlobalComponents.selfServicePortalURL);
				Utility.pause(2);
				driver2.findElement(By.xpath(LoginObjects.selfServiceSigninWithOKTABtn)).click();
				Utility.pause(3);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTAUsernameTxt)).sendKeys(username);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTAPasswordTxt)).sendKeys(password);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTASigninBtn)).click();
				Utility.pause(15);
				logger.log(LogStatus.PASS, "Logged In to Self Service Portal");
				ByAttribute.clickOnSelfServicePod("My Applications");
				Utility.pause(2);
				Utility.verifyElementPresent(".//div/p[text()='Draft']", "Application Status - Draft", false, true);
				ByAttribute.click("xpath", ".//mat-icon[@class='mat-icon notranslate mat-icon-no-color' and @ng-reflect-svg-icon='edit']/ancestor::button", "Cick on Edit Icon");
				Utility.pause(4);
				
				try{
					ByAttribute.click("xpath", ".//*[@id='Citizenship_Type']", "Click Citizenship Type");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'US Citizen')]", "Select Citizenship Type");
					Thread.sleep(1000);
				}catch (Exception e){
					System.out.println("Citizenship Dropdown is Disabled/Unavailable");
					logger.log(LogStatus.INFO, "Citizenship Dropdown is Disabled/Unavailable");
				}
				
				ByAttribute.click("xpath", ".//mat-icon[@class='mat-icon notranslate mat-icon-no-color' and @svgicon='history']/ancestor::button", "Click Application History Button");
				
				String returnStatus="No";
				if(driver.findElements(By.xpath(".//div[contains(text(),'Application has been Returned by LAKE000000623. Returning Reason:')]")).size()>0)
				{
					returnStatus="Yes";
				}
				
				ByAttribute.click("xpath", ".//div[@class='tpm-sidenav-title' and text()='Application History']/following-sibling::button[@class='mat-icon-button mat-button-base']", "Click Close Appication History Button");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[@id='Country_of_birth_telos']", "Click Country of Birth");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' UNITED STATES ']", "Select Country of Birth");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//mat-select[@id='pobState']", "Click State of Birth");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Arizona')]", "Select State of Birth");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[@id='Citizenship']//div[@class='mat-select-value']", "Click Nationality");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' UNITED STATES ']", "Select Nationality");
				Thread.sleep(1000);
//				ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationBadgeValidToTxt, "Enter Badge Valid To Date");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//div[@class='mat-calendar-body-cell-content mat-calendar-body-today']", "Select today");
				Thread.sleep(1000);
				ByAttribute.clearSetText("xpath", ".//input[@id='Height']", "510", "Enter Height");
				ByAttribute.clearSetText("xpath", ".//input[@id='Weight']", "150", "Enter Weight");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//mat-select[@id='HairColor']", "Click Hair Color");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Black')]", "Select Hair Color");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//mat-select[@id='EyeColor']", "Click Eye Color");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Brown')]", "Select Eye Color");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//mat-select[@id='Sex']", "Click Gender");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Male')]", "Select Gender");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//mat-select[@id='Race']", "Click Race");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Asian')]", "Select Race");
				Thread.sleep(1000);
				ByAttribute.clearSetText("xpath", ".//input[@id='Home Address Line 1']", "13-B Locker Street", "Enter Address");
				ByAttribute.clearSetText("xpath", ".//input[@id='City']", "Arizona", "Enter City");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[@id='Country_telos']", "Click Country");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' UNITED STATES ']", "Select Country");
				Thread.sleep(3000);
				ByAttribute.click("xpath", ".//*[@id='Address_State_telos']", "Click State");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Arizona')]", "Select State");
				Thread.sleep(1000);
				ByAttribute.clearSetText("xpath", ".//*[@id='Postal Code']", "102987", "Enter Postal Code");
				ByAttribute.clearSetText("xpath", ".//*[@id='Contact Phone']", "6754327809", "Enter Contact Number");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[@id='ISAuthorizedSigner']", "Click IS Authorized Signer");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Yes')]", "Select Yes");
				
				if(returnStatus.equalsIgnoreCase("No"))
				{
					for(int i=2594;i<=2699;i=i+3)
					{
						ByAttribute.click("xpath", ".//*[@id='mat-radio-"+i+"']//div[@class='mat-radio-container']", "Select Criminal Record as NO");
						Thread.sleep(500);
	
					}
	
					ByAttribute.click("xpath", ".//*[@id='AuthorizeSSN']", "Authorize SSN Check-Box");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//*[@id='VerifySSN']", "Validate SSN Check-Box");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//*[@id='BadgeHolder_certify']", "Badge Holder Certify Check-Box");
					Thread.sleep(2000);
				}
				
				ByAttribute.click("xpath", ".//button/span[contains(text(),'Submit')]", "Click on Submit Button");
				Utility.pause(2);
				ByAttribute.click("xpath", ".//button/span[text()='Ok']", "Click on Ok Button");
				
				logger.log(LogStatus.PASS, "Successfully: Applicant Self Service Badge Request Submit");
				System.out.println("Successfully: Applicant Self Service Badge Request Submit");
			}
			catch (Exception e)
			{ 	
				Utility.takeScreenshot("screenshot");
				logger.log(LogStatus.ERROR, "Screenshort of BUGG :" + imgeHtmlPath);	
				throw new java.lang.RuntimeException("--------------->"+ e);	    		
			}
			finally
			{
				driver2.quit();
				driver=default_driver;
				logger.log(LogStatus.PASS, "Switched Back to Guardian Portal");
			}	
		}
	}

	
	/**
	* <h1>modifyAplicantInvitation</h1>
	* This is Method to Modify an Applicant Invite
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-27-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void modifyAplicantInvitation(String firstName, String actor) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** modifyAplicantInvitation *********************************");
			logger.log(LogStatus.INFO, "***************************** modifyAplicantInvitation *********************************");
			try
			{
				Thread.sleep(5000);
				ByAttribute.clickHomeTab();
				Thread.sleep(2000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Applicant Invitation");
				Thread.sleep(2000);
				ByAttribute.click("xpath", BadgingObjects.badgingModifyApplicantInviteLnk, "Click on Modify Applicant Invite Link");
				Thread.sleep(10000);

				ByAttributeAngular.click("xpath", ".//p[@class='custom-grid-display-content' and text()='"+firstName.toUpperCase()+"']//parent::div//parent::div//parent::mat-cell//parent::mat-row/mat-cell[@class='mat-cell cdk-column-applicationId mat-column-applicationId ng-star-inserted']", "Click Element");
				System.out.println("Invited Applicants Table is available");
				logger.log(LogStatus.PASS, "Invited Applicants Table is available");
				Thread.sleep(1000);
				ByAttributeAngular.click("xpath", ".//span[@class='mat-button-wrapper' and contains(text(),'ontinue')]", "Click Continue Button");
				Thread.sleep(3000);
				Utility.connectToLatestFrame(driver);
				
				ByAttribute.click("xpath", BadgingObjects.badgingModifyApplicantPreEnrolmentContinueBtn, "Click Continue Button");
				Thread.sleep(2000);
				Utility.connectToLatestFrame(driver);
				if(actor.equalsIgnoreCase("AS"))
				{	
					ByAttribute.click("xpath", ".//span[@class='fab-sp16-Accordion-Expand2-16']", "Click Expand Button");
					Thread.sleep(500);
					ByAttribute.click("xpath", ".//span[@class='fab-sp16-Accordion-Expand2-16']", "Click Expand Button");
					Thread.sleep(500);
					ByAttribute.click("xpath", ".//span[text()='All Terminals']", "Select All Terminals");
					Thread.sleep(500);
					ByAttribute.click("xpath", ".//span[text()='Terminal 1']", "Select Terminal 1");
					Thread.sleep(500);
					ByAttribute.click("xpath", ".//span[text()='Default Access']", "Select Default Access");
					Thread.sleep(550);
					ByAttribute.click("xpath", ".//span[text()='General']", "Select General");
					Thread.sleep(500);
				}
				ByAttribute.click("xpath", BadgingObjects.badgingModifyApplicantPreEnrolmentContinueBtn, "Click Continue Button");
				Thread.sleep(5000);
				Utility.connectToLatestFrame(driver);
				ByAttribute.click("xpath", BadgingObjects.badgingModifyApplicantContinueSubmitBtn, "Click Continue Submit Button");
				Thread.sleep(40000);
				ByAttribute.click("xpath", BadgingObjects.badgingCreateApplicantInvitationOKBtn, "Click on OK button");
					
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
		
	/**
	* <h1>modifyAplicantInvitationASPortal</h1>
	* This is Method to Modify an Applicant Invite via AS Portal
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-15-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void modifyAplicantInvitationASPortal(String firstName, String actor) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** modifyAplicantInvitationASPortal *********************************");
			logger.log(LogStatus.INFO, "***************************** modifyAplicantInvitationASPortal *********************************");
			try
			{
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnSelfServicePod("Applicant Invitation");
				Thread.sleep(2000);
				List<WebElement> modifyApplicantInviteButtonElements = driver.findElements(By.xpath(".//p[text()='For AS to modify applicant form if needed']/parent::a[text()='Modify Applicant Invite ']"));
				WebElement modifyApplicantInviteButton = modifyApplicantInviteButtonElements.get(0);
				
				modifyApplicantInviteButton.click();
				logger.log(LogStatus.INFO, "Click Modify Applicant Invite Button");
				Thread.sleep(3000);

				ByAttribute.click("xpath", ".//p[@class='custom-grid-display-content' and text()='"+firstName.toUpperCase()+"']//parent::div//parent::div//parent::mat-cell//parent::mat-row/mat-cell[@class='mat-cell cdk-column-applicationId mat-column-applicationId ng-star-inserted']", "Click Element");
				System.out.println("Invited Applicants Table is available");
				logger.log(LogStatus.PASS, "Invited Applicants Table is available");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-button-wrapper' and contains(text(),'ontinue')]", "Click Continue Button");
				Thread.sleep(3000);

				if(actor.equalsIgnoreCase("AS"))
				{
					ByAttribute.click("xpath", ".//pre[@class='ae-parent-heading' and text()='Airports']/ancestor::mat-tree-node//button[@class='mat-icon-button mat-button-base']", "Click Expand Airports Button");
					Thread.sleep(500);
					ByAttribute.click("xpath", ".//pre[@class='ae-parent-heading' and text()='LAX']/ancestor::mat-tree-node//button[@class='mat-icon-button mat-button-base']", "Click Expand LAX Button");
					Thread.sleep(500);
					if(driver.findElements(By.xpath(".//pre[@class='ae-parent-heading' and text()='All Terminals']/ancestor::mat-tree-node//button[@class='mat-icon-button mat-button-base']")).size()>0)
					{
						ByAttribute.click("xpath", ".//pre[@class='ae-parent-heading' and text()='All Terminals']/ancestor::mat-tree-node//button[@class='mat-icon-button mat-button-base']", "Expand All Terminals");
						Thread.sleep(500);
						try
						{
							ByAttribute.click("xpath", ".//pre[@class='ae-parent-heading' and text()='Terminal 1']/ancestor::mat-tree-node//button[@class='mat-icon-button mat-button-base']", "Expand Terminal 1");
						}catch(Exception e){
							ByAttribute.click("xpath", ".//pre[@class='ae-parent-heading' and text()='Terminal 2']/ancestor::mat-tree-node//button[@class='mat-icon-button mat-button-base']", "Expand Terminal 2");
						}
						Thread.sleep(500);
						ByAttribute.click("xpath", ".//pre[@class='ae-parent-heading' and text()='Default Access' or text()='DEFAULT ACCESS']/ancestor::mat-tree-node//button[@class='mat-icon-button mat-button-base']", "Expand Default Access");
						Thread.sleep(550);
						ByAttribute.click("xpath", ".//pre[@class='ae-child-heading' and text()='General' or text()='GENERAL']/ancestor::mat-tree-node//mat-checkbox[@class='mat-checkbox mat-accent']", "Select General");
						Thread.sleep(500);
					}else if(driver.findElements(By.xpath(".//pre[@class='ae-parent-heading' and text()='Posts, Gates']/ancestor::mat-tree-node//button[@class='mat-icon-button mat-button-base']")).size()>0)
					{
						ByAttribute.click("xpath", ".//pre[@class='ae-parent-heading' and text()='Posts, Gates']/ancestor::mat-tree-node//button[@class='mat-icon-button mat-button-base']", "Expand Posts, Gates");
						Thread.sleep(550);
						ByAttribute.click("xpath", ".//pre[@class='ae-child-heading' and text()='American Airlines']/ancestor::mat-tree-node//mat-checkbox[@class='mat-checkbox mat-accent']", "Select American Airlies");
						Thread.sleep(500);
					}
				}
			
				ByAttribute.click("xpath", SelfServiceObjects.selfServiceModifyApplicantSubmitBtn, "Click Submit Button");
				Thread.sleep(2000);
				ByAttribute.click("xpath", SelfServiceObjects.selfServiceModifyApplicantSubmitOKBtn, "Click on Submit OK button");
				Thread.sleep(20000);
				ByAttribute.click("xpath", ".//span[@class='mat-button-wrapper' and text()='Ok']/parent::button", "Click Ok Button");
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	
/**
* <h1>cancelAplicantInvitationASPortal</h1>
* This is Method to Cancel an Applicant Invite via AS Portal
* @author  	Jiten Khanna
* @modified 
* @version 	1.0
* @since   	08-11-2020
* @param   	String firstName
* @return  	none
**/

public static void cancelAplicantInvitationASPortal(String firstName, String actor, String operation) throws Throwable
{

	if(unhandledException==false)
	{
		System.out.println("***************************** cancelAplicantInvitationASPortal *********************************");
		logger.log(LogStatus.INFO, "***************************** cancelAplicantInvitationASPortal *********************************");
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,700)", "");
			ByAttribute.clickOnSelfServicePod("Applicant Invitation");
			Thread.sleep(2000);
			List<WebElement> modifyApplicantInviteButtonElements = driver.findElements(By.xpath(".//p[text()='For AS to modify applicant form if needed']/parent::a[text()='Modify Applicant Invite ']"));
			WebElement modifyApplicantInviteButton = modifyApplicantInviteButtonElements.get(0);
			
			modifyApplicantInviteButton.click();
			logger.log(LogStatus.INFO, "Click Modify Applicant Invite Button");
			Thread.sleep(3000);

			ByAttribute.click("xpath", ".//p[@class='custom-grid-display-content' and text()='"+firstName.toUpperCase()+"']//parent::div//parent::div//parent::mat-cell//parent::mat-row/mat-cell[@class='mat-cell cdk-column-applicationId mat-column-applicationId ng-star-inserted']", "Click Element");
			System.out.println("Invited Applicants Table is available");
			logger.log(LogStatus.PASS, "Invited Applicants Table is available");
			Thread.sleep(1000);
			if(operation.equalsIgnoreCase("Cancel"))
			{
				ByAttribute.click("xpath", ".//span[text()=' Cancel Application ']/parent::button", "Click Cancel Button");
			}else if(operation.equalsIgnoreCase("Return")){
				ByAttribute.click("xpath", ".//span[text()=' Return ']/parent::button", "Click Return Button");
			}
			Thread.sleep(3000);

			if(actor.equalsIgnoreCase("AS"))
			{
				ByAttribute.click("xpath", ".//mat-select[@id='ReasonCode']", "Select Cancel Reason Code");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' Others ']", "Select Reason Code as Others");
				Thread.sleep(500);
				ByAttribute.clearSetText("xpath", ".//*[@id='Comments']", "Incorrect Information", "Enter Comments");
				
			}
		
			ByAttribute.click("xpath", SelfServiceObjects.selfServiceModifyApplicantSubmitBtn, "Click Submit Button");
			Thread.sleep(5000);
			ByAttribute.click("xpath", ".//span[@class='mat-button-wrapper' and text()='Ok']/parent::button", "Click Ok Button");
			
			driver.navigate().refresh();
			Thread.sleep(2000);
            js.executeScript("window.scrollBy(0,700)", "");
			ByAttribute.clickOnSelfServicePod("Applicant Invitation");
			Thread.sleep(2000);
			modifyApplicantInviteButtonElements = driver.findElements(By.xpath(".//p[text()='For AS to modify applicant form if needed']/parent::a[text()='Modify Applicant Invite ']"));
			modifyApplicantInviteButton = modifyApplicantInviteButtonElements.get(0);
			
			modifyApplicantInviteButton.click();
			logger.log(LogStatus.INFO, "Click Modify Applicant Invite Button");
			Thread.sleep(3000);
			if(operation.equalsIgnoreCase("Cancel"))
			{
				Utility.verifyElementPresent(".//p[@class='custom-grid-display-content' and text()='"+firstName+"']//parent::div//parent::div//parent::mat-cell//parent::mat-row//p[text()='Cancelled']", "Application CANCELLED Entry in Table", false, false);
			}else if(operation.equalsIgnoreCase("Return")){
				Utility.verifyElementPresent(".//p[@class='custom-grid-display-content' and text()='"+firstName+"']//parent::div//parent::div//parent::mat-cell//parent::mat-row//p[text()='Draft']", "Application Returned and Draft Entry Created in Table", false, false);
			}
			
		}
		catch(Exception e)
		{		
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
			Utility.recoveryScenario(nameofCurrMethod, e);
		}	
	}
}

	
	/**
	* <h1>approveAplicantInvitation</h1>
	* This is Method to Approve an Applicant Invite
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-27-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void approveAplicantInvitation(String firstName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** approveAplicantInvitation *********************************");
			logger.log(LogStatus.INFO, "***************************** approveAplicantInvitation *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				
				for(int i=0;i<5;i++){
					if(driver.findElements(By.xpath(HomeObjects.homeWorkItemsForApprovalTbl)).size()>0)
					{	
						break;
					}else{
						driver.navigate().refresh();
						Thread.sleep(10000);
					}
				}
					
				if(driver.findElements(By.xpath(HomeObjects.homeWorkItemsForApprovalTbl)).size()>0)
				{				
					System.out.println("Work Items Table is available");
					logger.log(LogStatus.PASS, "Work Items Table is available");	
					
					Thread.sleep(2000);
					ByAttribute.click("xpath", ".//td[contains(text(),'"+firstName+"')]", "Click on Request");
					Utility.pause(10);
					Utility.connectToLatestFrame(driver);
//					ByAttribute.click("xpath", ".//div[@class='ng-binding' and text()='Details']", "Click on Details");
//					Thread.sleep(5000);
//					ByAttribute.clearSetText("xpath", ".//input[@id='Contact_phone_no']", Utility.UniqueNumber(9), "Enter Phone Number");
//					Utility.pause(3);
					ByAttribute.click("xpath", HomeObjects.homeApplicantPreEnrollmentApproveBtn, "Click Approve Button");
					Utility.pause(5);
					ByAttribute.click("xpath", ".//*[@id='comp_10']//button[text()='Continue']", "Click Continue Button");
					Utility.pause(5);
					ByAttribute.click("xpath", HomeObjects.homeGoBackToInboxBtn, "Click Go Back to Inbox Button");
					
				}
				else
				{
					System.out.println("Navigation to 'Invited Applicants Table' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Invited Applicants Table' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	

	/**
	* <h1>validateAplicantApproved</h1>
	* This is Method to Validate the Approval of an Applicant Invite
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-08-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void validateAplicantApproved(String firstName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** validateAplicantApproved *********************************");
			logger.log(LogStatus.INFO, "***************************** validateAplicantApproved *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
					
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(10000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);

			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	
	/**
	* <h1>createNewCompany</h1>
	* This is Method to Create a New Company
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-16-2020
	* @param   	String companyName
	**/

	public static void createNewCompany(String companyName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** createNewCompany *********************************");
			logger.log(LogStatus.INFO, "***************************** createNewCompany *********************************");
			try
			{
				Utility.pause(2);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				ByAttribute.clickOnPod("Manage Company and Divisions");
				
				Thread.sleep(5000);
				
				if(driver.findElements(By.xpath(BadgingObjects.companyManagementHeaderLbl)).size()>0)
				{				
					System.out.println("Company Management Page is available");
					logger.log(LogStatus.PASS, "Company Management Page is available");	
					
					String orgName = companyName+" PVT LTD";
					String badgeIcon = "ATC";
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
					LocalDateTime now = LocalDateTime.now();
					String startDate = dtf.format(now);
					String pocEmailID = "auto"+Utility.UniqueNumber(10)+"mate.alertuser04@gmail.com";
					
					List<WebElement> createButtonElements = driver.findElements(By.xpath(".//button[text()='Create']"));
					WebElement createButton = createButtonElements.get(0);
					
					createButton.click();
					logger.log(LogStatus.INFO, "Click Create Button");
					
					if(driver.findElements(By.xpath(BadgingObjects.companyProfileHeaderLbl)).size()>0)
					{				
						System.out.println("Company Profile Page is available");
						logger.log(LogStatus.PASS, "Company Profile Page is available");	
						
//						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileBadgeIconsTxt, badgeIcon, "Enter Badge Icon");
//						Thread.sleep(1000);
//						ByAttribute.click("xpath", ".//*[@class='template-checkbox ui-menu-item-wrapper' and text()='"+badgeIcon+"']", "Select Badge Icon");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileNameTxt, orgName, "Enter Company Name : "+orgName);
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyDBATxt, companyName, "Enter Company DBA");
						ByAttribute.selectText("xpath", BadgingObjects.companyProfileCompanyTypeDdn, "AVIATION", "Select Company Type");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileOrgShortDescriptionTxt, orgName, "Enter Org Short Description");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyWebsiteTxt, "www."+companyName.toLowerCase()+".com", "Enter Company Website");
//						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileStartDateTxt, startDate, "Enter Company Start Date");
//						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileEndDateTxt, "08/31/2099", "Enter Company End Date");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileAddressLine1Txt, "26 Lockdown Street", "Enter Address");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyCityTxt, "New York", "Enter Company City");
						ByAttribute.selectText("xpath", BadgingObjects.companyProfileCompanyCountryDdn, "UNITED STATES", "Select Company Country");
						ByAttribute.selectText("xpath", BadgingObjects.companyProfileCompanyStateDdn, "New York", "Select Company State");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyPostalCodeTxt, Utility.UniqueNumber(5), "Enter Company Postal Code");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyPhoneTxt, Utility.UniqueNumber(10), "Enter Company Phone number");
						ByAttribute.click("xpath", BadgingObjects.companyProfileSaveBtn, "Click Save Button");
						Thread.sleep(40000);
						Utility.verifyElementPresent(".//div[@class='fab-message-info' and contains(text(),'"+companyName+"')]", "Company Saved Successfully message", false, true);
						Utility.pause(2);
						ByAttribute.click("xpath", BadgingObjects.companyProfileNextBtn, "Click Next Button");
						Utility.pause(5);
						Utility.connectToLatestFrame(driver);
						ByAttribute.click("xpath", BadgingObjects.companyProfileNewContactBtn, "Click Add New Contact Button");
						ByAttribute.click("xpath", BadgingObjects.companyProfileRoleDdn, "Select Role Dropdown");
						Thread.sleep(1000);
						ByAttribute.click("xpath", BadgingObjects.companyProfilePrimaryPOCLbl, "Click Primary Point of Contact");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfilePOCFirstNameTxt, "Auto"+Utility.UniqueString(4), "Enter First Name");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfilePOCLastNameTxt, "Test", "Enter Last Name");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfilePOCEmailTxt, pocEmailID, "Enter POC Email ID");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfilePOCTitleTxt, "MR", "Enter Title");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfilePOCWorkPhoneTxt, Utility.UniqueNumber(10), "Enter Work phone Number");
						ByAttribute.clearSetText("xpath", BadgingObjects.companyProfilePOCMobilePhoneTxt, Utility.UniqueNumber(10), "Enter Mobile Phone Number");
						ByAttribute.click("xpath", BadgingObjects.companyProfilePOCPrimaryContactChk, "Check Primary POC Checkbox");
						ByAttribute.click("xpath", BadgingObjects.companyProfilePOCSaveBtn, "Click on POC Details Save Button");
						Thread.sleep(3000);
						driver.switchTo().defaultContent();
					}else{
						System.out.println("Company Profile Page Not Found");
						logger.log(LogStatus.FAIL, "Company Profile Page Not Found");
					}
				}
				else
				{
					System.out.println("Navigation to 'Appointment Type Details' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Appointment Type Details' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}
		

	/**
	* <h1>companyExists</h1>
	* This is Method to Validate A Company Exists of Not
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-18-2020
	* @param   	String companyName
	* @return   Boolean
	**/

	public static boolean validateCompanyExists(String companyName) throws Throwable
	{

		System.out.println("***************************** validateCompanyExists *********************************");
		logger.log(LogStatus.INFO, "***************************** validateCompanyExists *********************************");
		Utility.pause(5);
		ByAttribute.clickHomeTab();
		Thread.sleep(1000);
		ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
		Utility.pause(5);
		driver.navigate().refresh();
		Utility.pause(10);
		ByAttribute.clickOnPod("Manage Company and Divisions");
		
		Thread.sleep(15000);
			
		ByAttribute.clearSetText("xpath", BadgingObjects.companyCompanyNameOrCompanyCodeTxt, companyName, "Enter Company Name");
		Thread.sleep(1000);
		if(driver.findElements(By.xpath(".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']")).size()>0)
		{
			System.out.println("Company Exists");
			logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Exists");
			
			ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']", "Select Company");
			ByAttribute.click("xpath", BadgingObjects.companySearchBtn, "Click Search Button");
			
			Utility.verifyElementPresent(".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Company Entry in Table", false, false);
			
			return true;
		}
		else{
			return false;
		}
	
	}

	
	/**
	* <h1>modifyCompanyDetails</h1>
	* This is Method to Modify an Existing Company Details
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-16-2020
	* @param   	String companyName
	**/

	public static void modifyCompanyDetails(String companyName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** modifyCompanyDetails *********************************");
			logger.log(LogStatus.INFO, "***************************** modifyCompanyDetails *********************************");
			try
			{
				Utility.pause(2);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				ByAttribute.clickOnPod("Manage Company and Divisions");
				
				Thread.sleep(4000);
				
				if(driver.findElements(By.xpath(BadgingObjects.companyManagementHeaderLbl)).size()>0)
				{				
					System.out.println("Company Management Page is available");
					logger.log(LogStatus.PASS, "Company Management Page is available");	
					
					ByAttribute.clearSetText("xpath", BadgingObjects.companyCompanyNameOrCompanyCodeTxt, companyName, "Enter Company Name");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']")).size()>0)
					{
						System.out.println("Company Exists");
						logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Exists");
						
						ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']", "Select Company");
						ByAttribute.click("xpath", BadgingObjects.companySearchBtn, "Click Search Button");
						
						Utility.verifyElementPresent(".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Company Entry in Table", false, false);
						
						ByAttribute.click("xpath", ".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Select Company");
						List<WebElement> modifyButtonElements = driver.findElements(By.xpath(".//button[text()='Modify']"));
						WebElement modifyButton = modifyButtonElements.get(0);
						
						modifyButton.click();
						logger.log(LogStatus.INFO, "Click Modify Button");
						
						String badgeIcon = "ASM";
						
						if(driver.findElements(By.xpath(".//h3[contains(text(),'Company Profile - "+companyName+"')]")).size()>0)
						{				
							System.out.println("Company Profile Page is available");
							logger.log(LogStatus.PASS, "Company Profile Page is available");	
							
//							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileBadgeIconsTxt, badgeIcon, "Edit Badge Icon");
//							Thread.sleep(1000);
//							ByAttribute.click("xpath", ".//*[@class='template-checkbox ui-menu-item-wrapper' and text()='"+badgeIcon+"']", "Select Badge Icon");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyDBATxt, companyName+" Edited", "Edit Company DBA");
							ByAttribute.selectText("xpath", BadgingObjects.companyProfileCompanyTypeDdn, "CARGO SERVICE", "Edit Company Type");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileOrgShortDescriptionTxt, "This Company Details are Modified", "Edit Org Short Description");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyWebsiteTxt, "www."+companyName.toLowerCase()+"isedited.com", "Edit Company Website");
//							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileEndDateTxt, "08/31/2089", "Edit Company End Date");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileAddressLine1Txt, "26 Quarantine Street", "Edit Address");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyCityTxt, "Washington DC", "Edit Company City");
							ByAttribute.selectText("xpath", BadgingObjects.companyProfileCompanyCountryDdn, "UNITED STATES", "Select Company Country");
							ByAttribute.selectText("xpath", BadgingObjects.companyProfileCompanyStateDdn, "Washington", "Edit Company State");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyPostalCodeTxt, Utility.UniqueNumber(5), "Enter Company Postal Code");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyPhoneTxt, Utility.UniqueNumber(10), "Enter Company Phone number");
							ByAttribute.click("xpath", BadgingObjects.companyProfileSaveBtn, "Click Save Button");
							Thread.sleep(40000);
							Utility.verifyElementPresent(".//div[@class='fab-message-info' and contains(text(),'Modified Successfully')]", "Company Modified Successfully message", false, true);
							Utility.pause(2);
							ByAttribute.click("xpath", BadgingObjects.companyProfileNextBtn, "Click Next Button");
							Utility.pause(5);
							Utility.connectToLatestFrame(driver);
							ByAttribute.click("xpath", BadgingObjects.companyProfileEditContactBtn, "Click Edit Contact Button");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfilePOCFirstNameTxt, "Auto"+Utility.UniqueString(4), "Edit First Name");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfilePOCLastNameTxt, "Edit", "Edit Last Name");
							ByAttribute.click("xpath", BadgingObjects.companyProfilePOCSaveBtn, "Click on POC Details Save Button");
							Thread.sleep(3000);
							driver.switchTo().defaultContent();
							ByAttribute.clickHomeTab();
						}else{
							System.out.println("Company Profile Page Not Found");
							logger.log(LogStatus.FAIL, "Company Profile Page Not Found");
						}
						
					}
					else{
						System.out.println("Company Doesn't Exist");
						logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Doesn't Exist");
					}
					
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}
	
		
	/**
	* <h1>modifyCompanyDetailsECMU</h1>
	* This is Method to Modify an Existing Company Details by ECMU
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-16-2020
	* @param   	String companyName, String divisionName
	**/
	
	public static void modifyCompanyDetailsECMU(String companyName, String userECMU) throws Throwable
	{
	
		if(unhandledException==false)
		{
			System.out.println("***************************** modifyCompanyDetailsECMU *********************************");
			logger.log(LogStatus.INFO, "***************************** modifyCompanyDetailsECMU *********************************");
			try
			{
				Utility.pause(2);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnPod("Manage Company and Divisions");
				
				Thread.sleep(5000);
				
				if(driver.findElements(By.xpath(BadgingObjects.companyManagementHeaderLbl)).size()>0)
				{				
					System.out.println("Company Management Page is available");
					logger.log(LogStatus.PASS, "Company Management Page is available");	
					
					ByAttribute.clearSetText("xpath", BadgingObjects.companyCompanyNameOrCompanyCodeTxt, companyName, "Enter Company Name");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']")).size()>0)
					{
						System.out.println("Company Exists");
						logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Exists");
						
						ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']", "Select Company");
						ByAttribute.click("xpath", BadgingObjects.companySearchBtn, "Click Search Button");
						
						Utility.verifyElementPresent(".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Company Entry in Table", false, false);
						
						ByAttribute.click("xpath", ".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Select Company");
						List<WebElement> modifyButtonElements = driver.findElements(By.xpath(".//button[text()='Modify']"));
						WebElement modifyButton = modifyButtonElements.get(0);
						
						modifyButton.click();
						logger.log(LogStatus.INFO, "Click Modify Button");
						
						String badgeIcon = "Aircraft_fueling";
						
						if(driver.findElements(By.xpath(".//h3[contains(text(),'Company Profile - "+companyName+"')]")).size()>0)
						{				
							System.out.println("Company Profile Page is available");
							logger.log(LogStatus.PASS, "Company Profile Page is available");	
							
//							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileBadgeIconsTxt, badgeIcon, "Edit Badge Icon");
//							Thread.sleep(1000);
//							ByAttribute.click("xpath", ".//*[@class='template-checkbox ui-menu-item-wrapper' and text()='"+badgeIcon+"']", "Select Badge Icon");
							
//							ByAttribute.selectTextContain("xpath", BadgingObjects.companyProfileECMUCoordinatorDdn, userECMU, "Select ECMU Coordinator");
							ByAttribute.selectIndex("xpath", BadgingObjects.companyProfileECMUCoordinatorDdn, 1, "Select ECMU Coordinator");
							
							ByAttribute.click("xpath", BadgingObjects.companyProfileSaveBtn, "Click Save Button");
							Thread.sleep(20000);
							Utility.verifyElementPresent(".//div[@class='fab-message-info' and contains(text(),'Modified Successfully')]", "Company Modified Successfully message", false, true);
							Utility.pause(2);
							ByAttribute.click("xpath", BadgingObjects.companyProfileNextBtn, "Click Next Button");
							Utility.pause(2);
							Utility.connectToLatestFrame(driver);
							try{
								ByAttribute.click("xpath", BadgingObjects.companyProfileEditContactBtn, "Click Edit Contact Button");
							}catch(Exception e){
								System.out.println("ECMU Cannot Edit Contact");
								logger.log(LogStatus.PASS, "Verified - ECMU Cannot Edit Contact");
							}
							
							driver.switchTo().defaultContent();
							ByAttribute.clickHomeTab();
						}else{
							System.out.println("Company Profile Page Not Found");
							logger.log(LogStatus.FAIL, "Company Profile Page Not Found");
						}
						
					}
					else{
						System.out.println("Company Doesn't Exist");
						logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Doesn't Exist");
					}
					
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}
		

	/**
	* <h1>createNewDivision</h1>
	* This is Method to Create a New Division within an Existing Company Company
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-16-2020
	* @param   	String companyName
	**/

	public static void createNewDivision(String companyName, String divisionName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** createNewDivision *********************************");
			logger.log(LogStatus.INFO, "***************************** createNewDivision *********************************");
			try
			{
				Utility.pause(1);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnPod("Manage Company and Divisions");
				
				Thread.sleep(5000);
				
				if(driver.findElements(By.xpath(BadgingObjects.companyManagementHeaderLbl)).size()>0)
				{				
					System.out.println("Company Management Page is available");
					logger.log(LogStatus.PASS, "Company Management Page is available");	
					
					String orgName = companyName+" PVT LTD";
					String badgeIcon = "ATC";
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
					LocalDateTime now = LocalDateTime.now();
					String startDate = dtf.format(now);
					String jobRole = "A P MECHANIC";
					
					ByAttribute.clearSetText("xpath", BadgingObjects.companyCompanyNameOrCompanyCodeTxt, companyName, "Enter Company Name");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']")).size()>0)
					{
						System.out.println("Company Exists");
						logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Exists");
						
						ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']", "Select Company");
						ByAttribute.click("xpath", BadgingObjects.companySearchBtn, "Click Search Button");
						
						Utility.verifyElementPresent(".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Company Entry in Table", false, false);
						
						ByAttribute.click("xpath", ".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Select Company");
						Thread.sleep(2000);
						List<WebElement> manageDivisionsButtonElements = driver.findElements(By.xpath(".//button[text()='Manage Divisions']"));
						WebElement manageDivisionsButton = manageDivisionsButtonElements.get(0);
						
						manageDivisionsButton.click();
						logger.log(LogStatus.INFO, "Click Manage Divisions Button");
						Thread.sleep(4000);
						List<WebElement> createButtonElements = driver.findElements(By.xpath(".//button[text()='Create']"));
						WebElement createButton = createButtonElements.get(0);
						
						createButton.click();
						logger.log(LogStatus.INFO, "Click Create Button");
						
						if(driver.findElements(By.xpath(BadgingObjects.divisionManageDivisionHeaderLbl)).size()>0)
						{				
							System.out.println("Manage Division Page is available");
							logger.log(LogStatus.PASS, "Manage Division Page is available");	
							
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileCompanyDBATxt, "AUTODBA"+Utility.UniqueNumber(3), "Enter Division DBA");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileNameTxt, divisionName, "Enter Division Name : "+divisionName);
							ByAttribute.selectText("xpath", BadgingObjects.divisionDivisionTypeDdn, "SIDA", "Select Division Type");
//							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileStartDateTxt, startDate, "Enter Company Start Date");
//							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileEndDateTxt, "08/31/2099", "Enter Company End Date");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileOrgShortDescriptionTxt, orgName, "Enter Org Short Description");
							ByAttribute.selectText("xpath", BadgingObjects.divisionExemptSecurityCheckDdn, "Yes", "Select Exemption to Security Check");
							ByAttribute.selectText("xpath", BadgingObjects.divisionBadgeTypeDdn, "Other", "Select Badge Type");
							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileBadgeIconsTxt, badgeIcon, "Enter Badge Icon");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//*[@class='template-checkbox ui-menu-item-wrapper' and text()='"+badgeIcon+"']", "Select Badge Icon");
							Thread.sleep(1000);
							ByAttribute.click("xpath", BadgingObjects.companyProfileSaveBtn, "Click Save Button");
							Thread.sleep(10000);
							Utility.verifyElementPresent(".//div[@class='fab-message-info' and contains(text(),'"+divisionName+"')]", "Division Saved Successfully message", false, true);
							Utility.pause(2);
							ByAttribute.click("xpath", BadgingObjects.companyProfileNextBtn, "Click Next Button");
							Utility.pause(2);
							ByAttribute.click("xpath", BadgingObjects.divisionContactPodLnk, "Click Contact Pod");
							Thread.sleep(1000);
							Utility.connectToLatestFrame(driver);
							Utility.verifyElementPresent(BadgingObjects.companyProfileNewContactBtn, "Add New Contact Button", false, false);
							driver.switchTo().defaultContent();
							ByAttribute.click("xpath", BadgingObjects.divisionContractPodLnk, "Click Contract Pod");
							Thread.sleep(2000);
							Utility.connectToLatestFrame(driver);
							Utility.verifyElementPresent(BadgingObjects.companyProfileNewContractBtn, "Add New Contract Button", false, false);
							ByAttribute.click("xpath", BadgingObjects.companyProfileNewContractBtn, "Click Add New Contract Button");
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//*[@id='tenantContractControllerId']//div[contains(text(),'Select & Add Contracts')]", "Select & Add Contracts Card", false, false);
							if(driver.findElements(By.xpath(BadgingObjects.divisionContractNoRecordsLbl)).size()>0)
							{
								System.out.println("No Contracts Assigned to the Company");
								logger.log(LogStatus.INFO, "No Contracts Assigned to the Company");
								ByAttribute.click("xpath", BadgingObjects.divisionContractCloseCardBtn, "Click Close Contract Card Button");
							}else{
								
							}
							driver.switchTo().defaultContent();
							ByAttribute.click("xpath", BadgingObjects.divisionJobRolesPodLnk, "Click Job Roles and Location Pod");
							Thread.sleep(2000);
							Utility.connectToLatestFrame(driver);
							Utility.verifyElementPresent(BadgingObjects.divisionNewJobRoleBtn, "Add New Job Role Button", false, false);
							ByAttribute.click("xpath", BadgingObjects.divisionNewJobRoleBtn, "Click Add New Job Role Button");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//*[@id='mdl-main']//span[contains(text(),'"+jobRole+"')]//parent::td//parent::tr//md-checkbox/div[@md-ink-ripple-checkbox]", "Select Job Role as : "+jobRole);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleSaveBtn, "Click Save Card Button");
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//td[1]/span[contains(text(),'"+jobRole+"')]", "Job Role Added", false, false);
							driver.switchTo().defaultContent();
						}else{
							System.out.println("Company Profile Page Not Found");
							logger.log(LogStatus.FAIL, "Company Profile Page Not Found");
						}
					}
				}
				else
				{
					System.out.println("Navigation to 'Appointment Type Details' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Appointment Type Details' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}
	
	
	/** <h1>modifyDivision</h1>
	* This is Method to Modify a Division within an Existing Company Company
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-20-2020
	* @param   	String companyName, String divisionName
	**/

	public static void modifyDivision(String companyName, String divisionName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** modifyDivision *********************************");
			logger.log(LogStatus.INFO, "***************************** modifyDivision *********************************");
			try
			{
				Utility.pause(1);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnPod("Manage Company and Divisions");
				
				Thread.sleep(5000);
				
				if(driver.findElements(By.xpath(BadgingObjects.companyManagementHeaderLbl)).size()>0)
				{				
					System.out.println("Company Management Page is available");
					logger.log(LogStatus.PASS, "Company Management Page is available");	
					
					String badgeType = "Blue";
					
					ByAttribute.clearSetText("xpath", BadgingObjects.companyCompanyNameOrCompanyCodeTxt, companyName, "Enter Company Name");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']")).size()>0)
					{
						System.out.println("Company Exists");
						logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Exists");
						
						ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']", "Select Company");
						ByAttribute.click("xpath", BadgingObjects.companySearchBtn, "Click Search Button");
						
						Utility.verifyElementPresent(".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Company Entry in Table", false, false);
						
						ByAttribute.click("xpath", ".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Select Company");
						Thread.sleep(2000);
						List<WebElement> manageDivisionsButtonElements = driver.findElements(By.xpath(".//button[text()='Manage Divisions']"));
						WebElement manageDivisionsButton = manageDivisionsButtonElements.get(0);
						
						manageDivisionsButton.click();
						logger.log(LogStatus.INFO, "Click Manage Divisions Button");
						Thread.sleep(5000);
						
						ByAttribute.click("xpath", ".//*[contains(@id,'comp')]/tbody/tr[1]/td[1]", "Click on Existing Division");
						
						List<WebElement> modifyButtonElements = driver.findElements(By.xpath(".//button[text()='Modify']"));
						WebElement modifyButton = modifyButtonElements.get(0);
						
						modifyButton.click();
						logger.log(LogStatus.INFO, "Click Modify Button");
						
						if(driver.findElements(By.xpath(BadgingObjects.divisionManageDivisionHeaderLbl)).size()>0)
						{				
							System.out.println("Manage Division Page is available");
							logger.log(LogStatus.PASS, "Manage Division Page is available");	
							
//							Thread.sleep(5000);
//							ByAttribute.clearSetText("xpath", BadgingObjects.companyProfileBadgeIconsTxt, badgeType, "Enter Badge Icon");
//							Thread.sleep(1000);
//							ByAttribute.click("xpath", ".//*[@class='template-checkbox ui-menu-item-wrapper' and text()='"+badgeType+"']", "Select Badge Icon");
							Thread.sleep(1000);
							ByAttribute.click("xpath", BadgingObjects.companyProfileSaveBtn, "Click Save Button");
							Thread.sleep(60000);
							Utility.verifyElementPresent(".//div[@class='fab-message-info' and contains(text(),'Modified Successfully')]", "Division Modified Successfully message", false, true);
						
						}else{
							System.out.println("Company Profile Page Not Found");
							logger.log(LogStatus.FAIL, "Company Profile Page Not Found");
						}
					}
				}
				else
				{
					System.out.println("Navigation to 'Appointment Type Details' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Appointment Type Details' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}
	
	
	/** <h1>addLocationToDivision</h1>
	* This is Method to Add Location to a Division within an Existing Company Company
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-20-2020
	* @param   	String companyName, String divisionName
	**/

	public static void addLocationToDivision(String companyName, String divisionName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** addLocationToDivision *********************************");
			logger.log(LogStatus.INFO, "***************************** addLocationToDivision *********************************");
			try
			{
				Utility.pause(1);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnPod("Manage Company and Divisions");
				
				Thread.sleep(5000);
				
				if(driver.findElements(By.xpath(BadgingObjects.companyManagementHeaderLbl)).size()>0)
				{				
					System.out.println("Company Management Page is available");
					logger.log(LogStatus.PASS, "Company Management Page is available");	
					
					ByAttribute.clearSetText("xpath", BadgingObjects.companyCompanyNameOrCompanyCodeTxt, companyName, "Enter Company Name");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']")).size()>0)
					{
						System.out.println("Company Exists");
						logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Exists");
						
						ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']", "Select Company");
						ByAttribute.click("xpath", BadgingObjects.companySearchBtn, "Click Search Button");
						
						Utility.verifyElementPresent(".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Company Entry in Table", false, false);
						
						ByAttribute.click("xpath", ".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Select Company");
						Thread.sleep(2000);
						List<WebElement> manageDivisionsButtonElements = driver.findElements(By.xpath(".//button[text()='Manage Divisions']"));
						WebElement manageDivisionsButton = manageDivisionsButtonElements.get(0);
						
						manageDivisionsButton.click();
						logger.log(LogStatus.INFO, "Click Manage Divisions Button");
						Thread.sleep(5000);
						
						ByAttribute.click("xpath", ".//*[contains(@id,'comp')]/tbody/tr[1]/td[1]", "Click on Existing Division");
						
						List<WebElement> modifyButtonElements = driver.findElements(By.xpath(".//button[text()='Modify']"));
						WebElement modifyButton = modifyButtonElements.get(0);
						
						modifyButton.click();
						logger.log(LogStatus.INFO, "Click Modify Button");
						
						if(driver.findElements(By.xpath(BadgingObjects.divisionManageDivisionHeaderLbl)).size()>0)
						{				
							System.out.println("Manage Division Page is available");
							logger.log(LogStatus.PASS, "Manage Division Page is available");	
							Thread.sleep(2000);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRolesPodLnk, "Click Job Roles and Location Pod");
							Thread.sleep(2000);
							Utility.connectToLatestFrame(driver);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleMappedLocationLnk, "Click Mapped Location Link");
							Thread.sleep(1000);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleLocationLevelsChk, "Check Location Levels");
							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleSaveBtn, "Click Save Card Button");
							Thread.sleep(1000);
							Utility.verifyElementPresent(BadgingObjects.divisionJobRoleLocationsUpdatedSuccessfullyLbl, "Locations Updated Successfully", false, false);
							driver.switchTo().defaultContent();
						}else{
							System.out.println("Company Profile Page Not Found");
							logger.log(LogStatus.FAIL, "Company Profile Page Not Found");
						}
					}
				}
				else
				{
					System.out.println("Navigation to 'Appointment Type Details' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Appointment Type Details' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}
	
	
	/** <h1>removeLocationFromDivision</h1>
	* This is Method to Remove Location from a Division within an Existing Company Company
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-20-2020
	* @param   	String companyName, String divisionName
	**/

	public static void removeLocationFromDivision(String companyName, String divisionName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** removeLocationFromDivision *********************************");
			logger.log(LogStatus.INFO, "***************************** removeLocationFromDivision *********************************");
			try
			{
				Utility.pause(1);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				ByAttribute.clickOnPod("Manage Company and Divisions");
				
				Thread.sleep(5000);
				
				if(driver.findElements(By.xpath(BadgingObjects.companyManagementHeaderLbl)).size()>0)
				{				
					System.out.println("Company Management Page is available");
					logger.log(LogStatus.PASS, "Company Management Page is available");	
					
					ByAttribute.clearSetText("xpath", BadgingObjects.companyCompanyNameOrCompanyCodeTxt, companyName, "Enter Company Name");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']")).size()>0)
					{
						System.out.println("Company Exists");
						logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Exists");
						
						ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']", "Select Company");
						ByAttribute.click("xpath", BadgingObjects.companySearchBtn, "Click Search Button");
						
						Utility.verifyElementPresent(".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Company Entry in Table", false, false);
						
						ByAttribute.click("xpath", ".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Select Company");
						Thread.sleep(2000);
						List<WebElement> manageDivisionsButtonElements = driver.findElements(By.xpath(".//button[text()='Manage Divisions']"));
						WebElement manageDivisionsButton = manageDivisionsButtonElements.get(0);
						
						manageDivisionsButton.click();
						logger.log(LogStatus.INFO, "Click Manage Divisions Button");
						Thread.sleep(5000);
						
						ByAttribute.click("xpath", ".//*[contains(@id,'comp')]/tbody/tr[1]/td[1]", "Click on Existing Division");
						
						List<WebElement> modifyButtonElements = driver.findElements(By.xpath(".//button[text()='Modify']"));
						WebElement modifyButton = modifyButtonElements.get(0);
						
						modifyButton.click();
						logger.log(LogStatus.INFO, "Click Modify Button");
						
						if(driver.findElements(By.xpath(BadgingObjects.divisionManageDivisionHeaderLbl)).size()>0)
						{				
							System.out.println("Manage Division Page is available");
							logger.log(LogStatus.PASS, "Manage Division Page is available");	
							Thread.sleep(2000);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRolesPodLnk, "Click Job Roles and Location Pod");
							Thread.sleep(2000);
							Utility.connectToLatestFrame(driver);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleMappedLocationLnk, "Click Mapped Location Link");
							Thread.sleep(2000);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleLocationLevelsChk, "Un-Check Location Levels");
							Thread.sleep(1000);
							driver.findElement(By.xpath(BadgingObjects.divisionJobRoleLocationLevelsChk)).click();
//							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleLocationLevelsChk, "Un-Check Location Levels");
							Thread.sleep(1000);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleSaveBtn, "Click Save Card Button");
							Thread.sleep(1000);
							Utility.verifyElementPresent(BadgingObjects.divisionJobRoleLocationsUpdatedSuccessfullyLbl, "Locations Updated Successfully", false, false);
							driver.switchTo().defaultContent();
						}else{
							System.out.println("Company Profile Page Not Found");
							logger.log(LogStatus.FAIL, "Company Profile Page Not Found");
						}
					}
				}
				else
				{
					System.out.println("Navigation to 'Appointment Type Details' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Appointment Type Details' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}
	
	
	/**
	* <h1>selfServiceScheduleAppointment</h1>
	* This is Method to Schedule Appointment through Self Service Portal
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-26-2020
	* @param   	String userID, String password
	* @return  	none
	**/
	
	public static void selfServiceScheduleAppointment(String username, String password, String appointmentType, boolean duplicateAppointmentCheck) throws Throwable
	{
		
		if(unhandledException==false)
		{
			System.out.println("******************************** selfServiceScheduleAppointment  *****************************************");
			logger.log(LogStatus.INFO, "***************************** selfServiceScheduleAppointment *********************************");
			WebDriver driver2=Utility.openPrivateBrowser();
			default_driver=driver;
			driver=driver2;
			try
			{
				driver2.get(AGlobalComponents.selfServicePortalURL);
				Utility.pause(2);
				driver2.findElement(By.xpath(LoginObjects.selfServiceSigninWithOKTABtn)).click();
				Utility.pause(3);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTAUsernameTxt)).sendKeys(username);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTAPasswordTxt)).sendKeys(password);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTASigninBtn)).click();
				Utility.pause(15);
				logger.log(LogStatus.PASS, "Logged In to Self Service Portal");
				ByAttribute.clickOnSelfServicePod("Schedule Appointment");
				Utility.pause(2);
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
				DateTimeFormatter dtfPanel = DateTimeFormatter.ofPattern("MMM d, yyyy");  
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime next2Days = now;
				if(appointmentType.equals("Badge"))
				{
					next2Days = now.plus(1, ChronoUnit.DAYS);
				}else{
					next2Days = now.plus(2, ChronoUnit.DAYS);
				}
				String appointmentDate = dtf.format(next2Days);
				String appointmentDateOnPanel = dtfPanel.format(next2Days);
				
				ByAttribute.clearSetText("xpath", ".//*[contains(@id,'mat-input') and @placeholder='Search by Date']", appointmentDate, "Enter Appointment Date");
				driver.findElement(By.xpath(".//*[contains(@id,'mat-input') and @placeholder='Search by Date']")).sendKeys(Keys.TAB);
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[@aria-label='Appointment Type']", "Click Appointment Type");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'"+appointmentType+"')]", "Select Appointment Type as : "+appointmentType);
				Thread.sleep(2000);
				
				List<WebElement> expansionPanels = driver.findElements(By.xpath(".//mat-expansion-panel"));
				if(expansionPanels.size()>0)
				{
					for(WebElement panel:expansionPanels)
					{
						panel.click();
						Thread.sleep(1000);
					}
				}
				
				List<WebElement> availableSlots = driver.findElements(By.xpath(".//mat-panel-title/b[text()='Available Timings on "+appointmentDateOnPanel+"']/ancestor::div[@class='margin-bottom ng-star-inserted']//*[contains(@id,'cdk-accordion-child')]//mat-chip[@style='background-color: rgb(255, 255, 255); border: 1px dashed;']"));
				
				if(availableSlots.size()>0)
				{
					System.out.println("Appointment Slots Available. Appointment Can be Scheduled");
					logger.log(LogStatus.PASS, "Appointment Slots Available. Appointment Can be Scheduled");
					
					JavascriptExecutor js = (JavascriptExecutor) driver;
		            js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
					
					WebElement firstAvailableSlot = availableSlots.get(0);
		            js.executeScript("window.scrollBy(0,700)", "");
//					firstAvailableSlot.click();
					
					logger.log(LogStatus.INFO, "Select first Available Slot for Appointment");
					
					ByAttribute.click("xpath", ".//span[contains(text(),'Schedule')]//parent::button", "Cick on Schedule Button");
					
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//mat-dialog-content[contains(text(),'Your appointment for \"Badge Application\" has been confirmed for')]", "Appointment Confirmed Message", false, true);
					ByAttribute.click("xpath", ".//mat-dialog-actions/button/span[text()='OK']", "Cick on OK Button");
					Utility.pause(3);
					Utility.verifyElementPresent(".//p[text()='"+appointmentType+"']", appointmentType+" Type Entry in Table", false, false);
					
					logger.log(LogStatus.PASS, "Successfully: Applicant Self Service Request Appointment Created");
					System.out.println("Successfully: Applicant Self Service Request Appointment Created");
					
					if(duplicateAppointmentCheck)
					{
						ByAttribute.click("xpath", ".//span[@class='mat-button-wrapper' and contains(text(),'Back')]/parent::button", "Click Back Button");
						Thread.sleep(2000);
						
						ByAttribute.clickOnSelfServicePod("Schedule Appointment");
						Utility.pause(2);
						
						ByAttribute.clearSetText("xpath", ".//*[contains(@id,'mat-input') and @placeholder='Search by Date']", appointmentDate, "Enter Appointment Date");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//*[@aria-label='Appointment Type']", "Click Appointment Type");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'"+appointmentType+"')]", "Select Appointment Type as : "+appointmentType);
						
						expansionPanels = driver.findElements(By.xpath(".//mat-expansion-panel"));
						if(expansionPanels.size()>0)
						{
							for(WebElement panel:expansionPanels)
							{
								panel.click();
								Thread.sleep(1000);
							}
						}
						
						availableSlots = driver.findElements(By.xpath(".//*[contains(@id,'cdk-accordion-child')]//mat-chip[@style='background-color: rgb(255, 255, 255); border: 1px dashed;']"));
						
						if(availableSlots.size()>0)
						{
							System.out.println("Appointment Slots Available. Appointment Can be Scheduled");
							logger.log(LogStatus.PASS, "Appointment Slots Available. Appointment Can be Scheduled");
							
				            js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
							
							firstAvailableSlot = availableSlots.get(0);
							
//							firstAvailableSlot.click();
							
							logger.log(LogStatus.INFO, "Select first Available Slot for Appointment");
							
							ByAttribute.click("xpath", ".//span[contains(text(),'Schedule')]//parent::button", "Cick on Schedule Button");
							
							Thread.sleep(2000);
							Utility.verifyElementPresent(".//h3[text()='Error ']", "Error Pop-Up", false, false);
							Utility.verifyElementPresent(".//mat-dialog-content[contains(text(),' There is already an appointment scheduled for "+appointmentType+" under Badge Application. Do you want to reschedule it?')]", "Duplicate Appointment Message", false, false);
							Utility.verifyElementPresent(".//span[@class='mat-button-wrapper' and text()='Cancel']/parent::button", "Cancel Button", false, false);
							Utility.verifyElementPresent(".//span[@class='mat-button-wrapper' and contains(text(),'Reschedule Appointment')]/parent::button", "Reschedule Button", false, false);
							ByAttribute.click("xpath", ".//span[@class='mat-button-wrapper' and text()='Cancel']/parent::button", "Cick on Cancel Button");
							Utility.pause(3);
							
						}else{
							System.out.println("No Appointment Slots Available to Validate Duplicate Appointment. Duplicate Appointment Cannot be Scheduled");
							logger.log(LogStatus.FAIL, "No Appointment Slots Available to Validate Duplicate Appointment. Duplicate Appointment Cannot be Scheduled");
						}
					}
					
				}else{
					System.out.println("No Appointment Slots Available. Appointment Cannot be Scheduled");
					logger.log(LogStatus.FAIL, "No Appointment Slots Available. Appointment Cannot be Scheduled");
				}
			}
			catch (Exception e)
			{ 	
				Utility.takeScreenshot("screenshot");
				logger.log(LogStatus.ERROR, "Screenshort of BUGG :" + imgeHtmlPath);	
				throw new java.lang.RuntimeException("--------------->"+ e);	    		
			}
			finally
			{
				driver2.quit();
				driver=default_driver;
				logger.log(LogStatus.PASS, "Switched Back to Guardian Portal");
			}	
		}
	}


	/**
	* <h1>applicationCompletion</h1>
	* This is Method to Issue New Badge to an Applicant
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-27-2020
	* @param   	String firstName,String badgeType,String badgeValidations
	* @return  	none
	**/

	public static void applicationCompletion(String firstName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** applicationCompletion *********************************");
			logger.log(LogStatus.INFO, "***************************** applicationCompletion *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
					
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(10000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
				
				ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
				ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
				Thread.sleep(10000);
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardAuthorizationToWorkPodLnk, "Click Authorization to Work Pod");
				Thread.sleep(2000);
				ByAttribute.selectText("xpath", BadgingObjects.applicationWizardCitizenshipStatusDdn, "A citizen of the United States", "Select Citizenship of Applicant");
				ByAttribute.selectValue("xpath", BadgingObjects.applicationWizardListADdn, "550504", "Select List A Document");
				ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardDocumentNumberATxt, Utility.UniqueNumber(8), "Enter Document Number");
				ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardIssuedByATxt, "Issuing Authority", "Enter Issuing Authority");
				ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardDateIssuedATxt, "01/01/2020", "Enter Date Issued");
				ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardExpiryDateATxt, "01/01/2030", "Enter Expiry Date");
				ByAttribute.click("xpath", BadgingObjects.applicationWizardSaveBtn, "Click on Save Button");
				Thread.sleep(2000);
				Utility.verifyElementPresent(BadgingObjects.applicationWizardSavedSuccessfullyMessageLbl, "Saved Successfully. Please click Next to continue", false, false);
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardNextBtn, "Click on Next Button");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//input[@name='Interference_with_air_navigation' and @id='No' and @checked='checked']", "Interference_with_air_navigation NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Improper_transportation' and @id='No' and @checked='checked']", "Improper_transportation NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Aircraft_piracy' and @id='No' and @checked='checked']", "Aircraft_piracy NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Commission_of_certain_crimes' and @id='No' and @checked='checked']", "Commission_of_certain_crimes NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Destruction_of_an_aircraft' and @id='No' and @checked='checked']", "Destruction_of_an_aircraft NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Destruction_of_property' and @id='No' and @checked='checked']", "Destruction_of_property NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Possession_or_distribution_of_stolen_property' and @id='No' and @checked='checked']", "Possession_or_distribution_of_stolen_property NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Carrying_a_weapon_or_explosive' and @id='No' and @checked='checked']", "Carrying_a_weapon_or_explosive NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Assault_with_intent_to_murder' and @id='No' and @checked='checked']", "Assault_with_intent_to_murder NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Murder' and @id='No' and @checked='checked']", "Murder NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Kidnapping_or_hostage_taking' and @id='No' and @checked='checked']", "Kidnapping_or_hostage_taking NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Arson' and @id='No' and @checked='checked']", "Arson NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Lighting_violations' and @id='No' and @checked='checked']", "Lighting_violations NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Unlawful_entry_into_an_aircraft' and @id='No' and @checked='checked']", "Unlawful_entry_into_an_aircraft NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Unlawfula_possession' and @id='No' and @checked='checked']", "Unlawfula_possession NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Violence' and @id='No' and @checked='checked']", "Violence NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Rape_or_sexual_abuse' and @id='No' and @checked='checked']", "Rape_or_sexual_abuse NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Armed_or_unarmed_robbery' and @id='No' and @checked='checked']", "Armed_or_unarmed_robbery NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Felony_involving_threat' and @id='No' and @checked='checked']", "Felony_involving_threat NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Felony_involving_theft' and @id='No' and @checked='checked']", "Felony_involving_theft NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Bribery' and @id='No' and @checked='checked']", "Bribery NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Espionage' and @id='No' and @checked='checked']", "Espionage NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Sedition' and @id='No' and @checked='checked']", "Sedition NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Burglary' and @id='No' and @checked='checked']", "Burglary NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Felony_involving_aggravated_assault' and @id='No' and @checked='checked']", "Felony_involving_aggravated_assault NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Illegal_possession_controlledsubstance_punishable' and @id='No' and @checked='checked']", "Illegal_possession_controlledsubstance_punishable NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Conspiracy_or_attempt_to_commit_criminal_acts' and @id='No' and @checked='checked']", "Conspiracy_or_attempt_to_commit_criminal_acts NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Importation_or_manufacture_of_a_controlled_substance' and @id='No' and @checked='checked']", "Importation_or_manufacture_of_a_controlled_substance NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Treason' and @id='No' and @checked='checked']", "Treason NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Dishonesty_fraud_or_misrepresentation' and @id='No' and @checked='checked']", "Dishonesty_fraud_or_misrepresentation NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Conveying_false_information' and @id='No' and @checked='checked']", "Conveying_false_information NO Radio Button", false, true);
				Utility.verifyElementPresent(".//input[@name='Forgery' and @id='No' and @checked='checked']", "Forgery NO Radio Button", false, true);
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardInterferenceWithFlightCrewMembersNORdb, "Select No Radio Button - Interference with flight crew members or flight attendants");
				ByAttribute.selectText("xpath", BadgingObjects.applicationWizardIsNDAAttachedDdn, "No", "Select Is NDA Attached as No");
				ByAttribute.click("xpath", BadgingObjects.applicationWizardCertifyCriminalHistoryChk, "Check Certify Criminal History Check-Box");
				ByAttribute.click("xpath", BadgingObjects.applicationWizardVerifySSNChk, "Check Verify SSN Check-Box");
				
				String signaturePic = System.getProperty("user.dir") + "\\Browser_Files\\Signature.png";
				Thread.sleep(1000);
				driver.findElement(By.xpath(".//input[@class='signatureImgUploadInput']")).sendKeys(signaturePic);
				Thread.sleep(10000);
				logger.log(LogStatus.INFO,"Import Signature");
				System.out.println("Import Signature");
				Utility.verifyElementPresent(BadgingObjects.applicationWizardSavedSuccessfullyMessageLbl, "Signature uploaded successfully", false, false);
//				ByAttribute.click("xpath", BadgingObjects.applicationSaveBtn, "Click on Save Button");
//				Thread.sleep(2000);
				ByAttribute.click("xpath", BadgingObjects.applicationWizardNextBtn, "Click on Next Button");
				Thread.sleep(1000);
				Utility.connectToLatestFrame(driver);
				
				String profilePic = System.getProperty("user.dir") + "\\Browser_Files\\Applicant_Photo.jpg";
				Thread.sleep(1000);
				driver.findElement(By.xpath(".//label[@for='profileImgUploadInput']")).click();
				Thread.sleep(1500);
				
				StringSelection ss = new StringSelection(profilePic);
	            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

	            //native key strokes for CTRL, V and ENTER keys
	            Robot robot = new Robot();

	            robot.keyPress(KeyEvent.VK_CONTROL);
	            robot.keyPress(KeyEvent.VK_V);
	            robot.keyRelease(KeyEvent.VK_V);
	            robot.keyRelease(KeyEvent.VK_CONTROL);
	            robot.keyPress(KeyEvent.VK_ENTER);
	            robot.keyRelease(KeyEvent.VK_ENTER);
				
				
				
				logger.log(LogStatus.INFO,"Upload Profile Picture");
				System.out.println("Upload Profile Picture");
				Utility.verifyElementPresent(BadgingObjects.applicationWizardSavedSuccessfullyMessageLbl, "Profile Picture uploaded successfully", false, false);
				String fingerPrints = System.getProperty("user.dir") + "\\Browser_Files\\fingerprint.eft";
				Thread.sleep(1000);
				driver.findElement(By.xpath(".//input[@type='file' and @accept='.eft']")).sendKeys(fingerPrints);
				Thread.sleep(20000);
				logger.log(LogStatus.INFO,"Upload Finger Prints File");
				System.out.println("Upload Finger Prints File");
				Utility.verifyElementPresent(BadgingObjects.applicationWizardSavedSuccessfullyMessageLbl, "Fingerprint uploaded successfully", false, false);
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.applicationWizardNextBtn, "Click on Next Button");
				Thread.sleep(2000);
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardApplicantProfilePodLnk, "Click on Applicant Profile Pod");
				Thread.sleep(2000);
				Utility.verifyElementPresent(".//div[@class='fab-pod-title' and text()='Modify User Details']", "Modify User Details Form", false, false);
				ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardPINTxt, "123456", "Enter PIN");
				ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardConfirmPINTxt, "123456", "Confirm PIN");
				ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardStreetAddressTxt, "Lockdown Street", "Enter Street Name");
//				ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardZipCodeTxt, Utility.UniqueNumber(5), "Enter Zip Code");
				
				ByAttribute.selectText("xpath", ".//*[@id='pobCountry']", "UNITED STATES", "Select Country of Birth");
				Thread.sleep(1000);
				ByAttribute.selectText("xpath", ".//*[@id='pobState']", "Alaska", "Select State of Birth");
				Thread.sleep(1000);
//				ByAttribute.selectText("xpath", BadgingObjects.applicationWizardApplicantBadgeTypeDdn, "Blue (SIDA AOA)", "Select Applicant Badge Type");
				ByAttribute.clearSetText("xpath", ".//input[@id='jobRole']", "A P MECHANIC", "Enter Job Role");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[@class='ui-menu-item-wrapper' and text()='A P MECHANIC']", "Select A P MECHANIC Job Role");
				ByAttribute.click("xpath", BadgingObjects.applicationWizardSubmitBtn, "Click on Submit Button");
				Thread.sleep(15000);
				Utility.verifyElementPresent(".//div[@class='fab-message-info' and contains(text(),'Request Auto-Provisioned Initiated. Request number is')]", "Request Auto-Provisioned Initiated Message", false, false);
				ByAttribute.click("xpath", BadgingObjects.applicationWizardNextBtn, "Click on Next Button");
				Thread.sleep(2000);

			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	

	/**
	* <h1>applicantBackgroundCheckCompletion</h1>
	* This is Method to Issue New Badge to an Applicant
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	06-11-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void applicantBackgroundCheckCompletion(String firstName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** applicantBackgroundCheckCompletion *********************************");
			logger.log(LogStatus.INFO, "***************************** applicantBackgroundCheckCompletion *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
					
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(10000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
				
				ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
				ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
				Thread.sleep(2000);
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardBackgroundCheckPodLnk, "Click on Background Check Pod");
				Thread.sleep(2000);
				Utility.connectToLatestFrame(driver);
				Utility.verifyElementPresent(".//h2[@class='ng-binding' and text()='Background Check']", "Background Check Header", false, false);
				Utility.verifyElementPresent(".//span[contains(text(),'STA CHECK')]/ancestor::tr//span[contains(text(),'NEW')]", "STA CHECK added with Status as NEW", false, false);
				Utility.verifyElementPresent(".//span[contains(text(),'CHRC CHECK')]/ancestor::tr//span[contains(text(),'NEW')]", "CHRC CHECK added with Status as NEW", false, false);
				
				/** UPDATE STA BACKGROUND CHECK STATUS **/
				ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBackgroundCheckSTABtn, "Click on Update Background Check for STA");
				Thread.sleep(2000);
				ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBGConfirmAndSubmitBtn, "Click on Confirm and Submit Button");
				Thread.sleep(2000);
				Utility.verifyElementPresent(".//span[contains(text(),'STA CHECK')]/ancestor::tr/td[4]//span[contains(text(),'QUEUED')]", "STA CHECK Status Changed to QUEUED", false, false);
				
				/** UPDATE CHRC BACKGROUND CHECK STATUS **/
				ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBackgroundCheckCHRCBtn, "Click on Update Background Check for CHRC");
				Thread.sleep(2000);
				ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBGConfirmAndSubmitBtn, "Click on Confirm and Submit Button");
				Thread.sleep(4000);
				Utility.verifyElementPresent(".//span[contains(text(),'CHRC CHECK')]/ancestor::tr/td[4]//span[contains(text(),'QUEUED')]", "CHRC CHECK Status Changed to QUEUED", false, false);
				driver.switchTo().defaultContent();
				
				/** RUNNING DACS JOB TO UPDATE THE BACKGROUND CHECK **/
				int bgStatus = 0;
				int rbStatus = 0;
				for(int i=0;i<10;i++)
				{
					ByAttribute.click("xpath", SetupObjects.setupTabLnk, "Click on Setup tab");
					Utility.pause(2);
					driver.navigate().refresh();
					Utility.pause(3);
					ByAttribute.click("xpath", SetupObjects.setupLeftMenuManualConfigurationLnk, "Click on Manual Configuration");
					Thread.sleep(1000);
					ByAttribute.click("xpath", SetupObjects.setupLeftMenuJobSchedulerLnk, "Click on Job Scheduler");
					Thread.sleep(1000);
					ByAttribute.click("xpath", SetupObjects.setupLeftMenuSchedulerLnk, "Click on Scheduler");
					Thread.sleep(3000);
					Utility.connectToLatestFrame(driver);
					
					for(int k=0;k<10;k++)
					{
						if(driver.findElements(By.xpath(SetupObjects.setupSchedulerCreateNewJobBtn)).size()>0)
						{
							System.out.println("Scheduler Page Loaded Successfully");
							logger.log(LogStatus.PASS, "Scheduler Page Loaded Successfully");
							break;
						}else{
							driver.switchTo().defaultContent();
							ByAttribute.click("xpath", SetupObjects.setupTabLnk, "Click on Setup tab");
							Utility.pause(2);
							ByAttribute.click("xpath", SetupObjects.setupLeftMenuManualConfigurationLnk, "Click on Manual Configuration");
							Thread.sleep(1000);
							ByAttribute.click("xpath", SetupObjects.setupLeftMenuJobSchedulerLnk, "Click on Job Scheduler");
							Thread.sleep(1000);
							ByAttribute.click("xpath", SetupObjects.setupLeftMenuSchedulerLnk, "Click on Scheduler");
							Thread.sleep(3000);
							Utility.connectToLatestFrame(driver);
						}
					}
					ByAttribute.click("xpath", SetupObjects.setupSchedulerCreateNewJobBtn, "Click on Create New Job Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", SetupObjects.setupSchedulerJobTypeDdn, "Click on Create New Job Type Dropdown");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//div[text()='DACS Sync with Telos ID']", "Select Job Type as DACS Sync with Telos ID");
					Thread.sleep(2000);
					ByAttribute.clearSetText("xpath", SetupObjects.setupSchedulerJobNameTxt, "AUTO"+Utility.UniqueNumber(4), "Enter Job Name");
					ByAttribute.click("xpath", SetupObjects.setupSchedulerSaveJobBtn, "Click Save Job Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(SetupObjects.setupSchedulerJobScheduledLbl, "Job Sceduled Success Message", false, false);
					driver.switchTo().defaultContent();
					
					ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
					Utility.pause(1);
					driver.navigate().refresh();
					Utility.pause(2);
					ByAttribute.clickOnPod("Manage Applications");
					Thread.sleep(3000);
					ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
					Thread.sleep(5000);
					ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
					
					ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
					ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
					Thread.sleep(2000);
	
					ByAttribute.click("xpath", BadgingObjects.applicationWizardBackgroundCheckPodLnk, "Click on Background Check Pod");
					Thread.sleep(2000);
					Utility.connectToLatestFrame(driver);
					Utility.verifyElementPresent(".//h2[@class='ng-binding' and text()='Background Check']", "Background Check Header", false, false);
					if(driver.findElements(By.xpath(".//span[contains(text(),'STA CHECK')]/ancestor::tr//span[contains(text(),'SUCCESS')]")).size()>0 && driver.findElements(By.xpath(".//span[contains(text(),'CHRC CHECK')]/ancestor::tr//span[contains(text(),'ACCEPTED')]")).size()>0)
					{
						Utility.verifyElementPresent(".//span[contains(text(),'STA CHECK')]/ancestor::tr//span[contains(text(),'SUCCESS')]", "STA CHECK added with Status as SUCCESS", false, false);
						Utility.verifyElementPresent(".//span[contains(text(),'CHRC CHECK')]/ancestor::tr//span[contains(text(),'ACCEPTED')]", "CHRC CHECK added with Status as ACCEPTED", false, false);
						bgStatus=1;
						ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBackgroundCheckRapBackInformationTab, "Click on RAPBACK INFORMATION tab");
						Thread.sleep(2000);
						if(driver.findElements(By.xpath(".//md-table-container//td[2]/span[contains(text(),'Active')]")).size()>0)
						{
							Utility.verifyElementPresent(".//md-table-container//td[2]/span[contains(text(),'Active')]", "Rapback Status Active", false, false);
							rbStatus=1;
							break;
						}else{
							driver.switchTo().defaultContent();
						}
					}else{
						driver.switchTo().defaultContent();
					}
				}
				
				if(bgStatus==1)
				{
					System.out.println("Background Status Updated Successfully");
					logger.log(LogStatus.PASS, "Background Status Updated Successfully");
					if(rbStatus==1)
					{
						System.out.println("Rapback Status Updated Successfully");
						logger.log(LogStatus.PASS, "Rapback Status Updated Successfully");
					}else{
						System.out.println("Rapback Status NOT Updated");
						logger.log(LogStatus.FAIL, "Rapback Status NOT Updated");
						unhandledException=true;
					}
				}else{
					System.out.println("Background Status NOT Updated");
					logger.log(LogStatus.FAIL, "Background Status NOT Updated");
					unhandledException=true;
				}
				driver.switchTo().defaultContent();	
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}

	
	/**
	* <h1>validateBackgroundCheckExemption</h1>
	* This is Method to Issue New Badge to an Applicant
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	08-14-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void validateBackgroundCheckExemption(String firstName, String exemptBG) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** validateBackgroundCheckExemption *********************************");
			logger.log(LogStatus.INFO, "***************************** validateBackgroundCheckExemption *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
					
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(10000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
				
				ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
				ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
				Thread.sleep(2000);
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardBackgroundCheckPodLnk, "Click on Background Check Pod");
				Thread.sleep(2000);
				Utility.connectToLatestFrame(driver);
				Utility.verifyElementPresent(".//h2[@class='ng-binding' and text()='Background Check']", "Background Check Header", false, false);
				Utility.verifyElementNotPresent(".//span[contains(text(),'"+exemptBG+" CHECK')]/ancestor::tr//span[contains(text(),'NEW')]", exemptBG+" CHECK is Not Added", false);
				driver.switchTo().defaultContent();
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}


	/**
	* <h1>applicantTrainingCompletion</h1>
	* This is Method to Issue New Badge to an Applicant
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-27-2020
	* @param   	String firstName,String badgeType,String badgeValidations
	* @return  	none
	**/

	public static void applicantTrainingCompletion(String firstName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** applicantTrainingCompletion *********************************");
			logger.log(LogStatus.INFO, "***************************** applicantTrainingCompletion *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(4);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(5000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
				
				ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
				ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
				Thread.sleep(2000);
				
//				if(driver.findElements(By.xpath(".//md-dialog/md-dialog-actions/button[@class='md-primary md-confirm-button md-button md-default-theme md-ink-ripple md-autofocus']/span[text()='OK']")).size()>0)
//				{
//					ByAttribute.click("xpath", ".//md-dialog/md-dialog-actions/button[@class='md-primary md-confirm-button md-button md-default-theme md-ink-ripple md-autofocus']/span[text()='OK']", "Click on Hot Notes OK Button");
//				}
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardTrainingScreeningPodLnk, "Click on Training & Screening Pod");
				Thread.sleep(2000);
				Utility.verifyElementPresent(".//span[text()='Name']", "{ Name } Column in Training Details Table", false, false);
				Utility.verifyElementPresent(".//span[text()='Status']", "{ Status } Column in Training Details Table", false, false);
				Utility.verifyElementPresent(".//span[text()='Completion Date']", "{ Completion Date } Column in Training Details Table", false, false);
				Utility.verifyElementPresent(".//span[text()=' Training Expiry Date']", "{ Training Expiry Date } Column in Training Details Table", false, false);
				Utility.verifyElementPresent(".//span[text()='Employer']", "{ Employer } Column in Training Details Table", false, false);
				Utility.verifyElementPresent(".//span[text()='Action']", "{ Action } Column in Training Details Table", false, false);
				Utility.verifyElementPresent(".//span[text()='History']", "{ History } Column in Training Details Table", false, false);
				Utility.verifyElementPresent(".//span[text()='Operation']", "{ Operation } Column in Training Details Table", false, false);
				Utility.verifyElementPresent(".//*[contains(@id,'comp')]/tbody/tr[1]//span[text()='View']", "{ View } Hyperlink under History tab in Training Details Table", false, false);
				
				Airport_Badging_CommonMethods.trainingCompletion(firstName,"TEST");
				driver.navigate().refresh();

				Thread.sleep(2000);
				
				/** RUNNING AAAE JOB TO UPDATE THE TRAINING STATUS **/
				int trStatus = 0;
				for(int i=0;i<7;i++)
				{
					ByAttribute.click("xpath", SetupObjects.setupTabLnk, "Click on Setup tab");
					Utility.pause(2);
					driver.navigate().refresh();
					Utility.pause(3);
					ByAttribute.click("xpath", SetupObjects.setupLeftMenuManualConfigurationLnk, "Click on Manual Configuration");
					Thread.sleep(1000);
					ByAttribute.click("xpath", SetupObjects.setupLeftMenuJobSchedulerLnk, "Click on Job Scheduler");
					Thread.sleep(1000);
					ByAttribute.click("xpath", SetupObjects.setupLeftMenuSchedulerLnk, "Click on Scheduler");
					Thread.sleep(3000);
					Utility.connectToLatestFrame(driver);
					
					for(int k=0;k<5;k++)
					{
						if(driver.findElements(By.xpath(SetupObjects.setupSchedulerCreateNewJobBtn)).size()>0)
						{
							System.out.println("Scheduler Page Loaded Successfully");
							logger.log(LogStatus.PASS, "Scheduler Page Loaded Successfully");
							break;
						}else{
							driver.switchTo().defaultContent();
							ByAttribute.click("xpath", SetupObjects.setupTabLnk, "Click on Setup tab");
							Utility.pause(2);
							ByAttribute.click("xpath", SetupObjects.setupLeftMenuManualConfigurationLnk, "Click on Manual Configuration");
							Thread.sleep(1000);
							ByAttribute.click("xpath", SetupObjects.setupLeftMenuJobSchedulerLnk, "Click on Job Scheduler");
							Thread.sleep(1000);
							ByAttribute.click("xpath", SetupObjects.setupLeftMenuSchedulerLnk, "Click on Scheduler");
							Thread.sleep(3000);
							Utility.connectToLatestFrame(driver);
						}
					}
					ByAttribute.click("xpath", SetupObjects.setupSchedulerCreateNewJobBtn, "Click on Create New Job Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", SetupObjects.setupSchedulerJobTypeDdn, "Click on Create New Job Type Dropdown");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//div[text()='AAAE IET LS Sync Job']", "Select Job Type as AAAE IET LS Sync Job");
					Thread.sleep(2000);
					ByAttribute.clearSetText("xpath", SetupObjects.setupSchedulerJobNameTxt, "AUTO"+Utility.UniqueNumber(4), "Enter Job Name");
					ByAttribute.click("xpath", SetupObjects.setupSchedulerSaveJobBtn, "Click Save Job Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(SetupObjects.setupSchedulerJobScheduledLbl, "Job Sceduled Success Message", false, false);
					driver.switchTo().defaultContent();
					
					ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
					Utility.pause(1);
					driver.navigate().refresh();
					Utility.pause(2);
					ByAttribute.clickOnPod("Manage Applications");
					Thread.sleep(3000);
					ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
					Thread.sleep(5000);
					ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
					
					ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
					ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
					Thread.sleep(2000);
					
					ByAttribute.click("xpath", BadgingObjects.applicationWizardTrainingScreeningPodLnk, "Click on Training & Screening Pod");
					Thread.sleep(2000);
//					Utility.connectToLatestFrame(driver);
					Utility.verifyElementPresent(".//div[@class='fab-pod-title' and text()='Training & Screening']", "Training & Screening", false, false);
					if(driver.findElements(By.xpath(".//tbody/tr[1]/td[2]//span[@class='statspan' and text()='PASS']")).size()>0)
					{
						Utility.verifyElementPresent(".//tbody/tr[1]/td[2]//span[@class='statspan' and text()='PASS']", "Training 1 Pass", false, true);
//						Utility.verifyElementPresent(".//tbody/tr[2]/td[2]//span[@class='statspan' and text()='PASS']", "Training 2 Pass", false, true);
						trStatus=1;
						break;
					}else{
						driver.switchTo().defaultContent();
					}
				}
				
				if(trStatus==1)
				{
					System.out.println("Training Status Updated Successfully");
					logger.log(LogStatus.PASS, "Training Status Updated Successfully");
				}else{
					System.out.println("Training Status NOT Updated");
					logger.log(LogStatus.PASS, "Training Status NOT Updated");
					unhandledException=true;
				}
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	
	/**
	* <h1>issueNewBadge</h1>
	* This is Method to Issue New Badge to an Applicant
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-27-2020
	* @param   	String firstName,String badgeType,String badgeValidations
	* @return  	none
	**/

	public static void issueNewBadge(String firstName,String badgeType,String badgeValidations) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** issueNewBadge *********************************");
			logger.log(LogStatus.INFO, "***************************** issueNewBadge *********************************");
			try
			{
				driver.navigate().refresh();
				Thread.sleep(2000);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
					
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(10000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
				
				ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
				ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
				Thread.sleep(2000);
								
				ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeInfoPodLnk, "Click on Badge Info Pod");
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.applicationWizardNewBadgeBtn, "Click on New Badge Button");
				Thread.sleep(1000);
				Utility.verifyElementPresent(BadgingObjects.applicationWizardBadgeTypeLbl, "Badge Type", false, false);
				Utility.verifyElementPresent(BadgingObjects.applicationWizardBadgeValidFromLbl, "Badge Valid From", false, false);
				Utility.verifyElementPresent(BadgingObjects.applicationWizardBadgeValidToLbl, "Badge Valid To", false, false);
				Utility.verifyElementPresent(BadgingObjects.applicationWizardBadgeStatusLbl, "Badge Status", false, false);
				try{
					ByAttribute.selectText("xpath", BadgingObjects.applicationWizardBadgeTypeDdn, badgeType, "Select Badge Type : "+badgeType);
				}catch(Exception e){
					logger.log(LogStatus.INFO, "Badge Type Selection is Disabled");
				}
				ByAttribute.click("xpath", BadgingObjects.applicationWizardSaveBtn, "Click on Save Button");
				Thread.sleep(20000);
				Utility.verifyElementPresent(".//div[contains(text(),'Request Auto-Provisioned Initiated.')]", "Request Auto-Provisioned Initiated Pop-Up", false, true);
				ByAttribute.click("xpath", BadgingObjects.badgingCreateApplicantInvitationOKBtn, "Click on OK Button");
				Thread.sleep(2000);
				ByAttribute.click("xpath", ".//span[@name='chk' and @class='ui-state-default ui-corner-all ui-igcheckbox-normal']", "Click Badge ID Checkbox");
				ByAttribute.click("xpath", BadgingObjects.applicationWizardPrintBadgeBtn, "Click on Print Badge Button");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//div[@class='templateImages']", "Badge Print Window", true, false);
				Utility.verifyElementPresent(".//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange' and text()='Print']", "Print Button", false, false);
				ByAttribute.click("xpath", ".//div[@class='ui-igdialog ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable']//a[@role='button' and @title='Close']", "Click Close Button on Print Window");
				Thread.sleep(1000);
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeInfoPodLnk, "Click on Badge Info Pod");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@name='chk' and @class='ui-state-default ui-corner-all ui-igcheckbox-normal']", "Click Badge ID Checkbox");
				ByAttribute.click("xpath", BadgingObjects.applicationWizardModifyExistingMediaBtn, "Click on Modify Existing Badge Button");
				Thread.sleep(1000);
				
				Utility.verifyElementPresent(".//div[text()='Badge Id']", "Badge Id Field", false, false);
				Utility.verifyElementPresent(".//div[text()='Badge Number']", "Badge Number Field", false, false);
				Utility.verifyElementPresent(".//div[text()='Badge Valid From']", "Badge Valid From Field", false, false);
				Utility.verifyElementPresent(".//div[text()='Badge Valid To']", "Badge Valid To Field", false, false);
				Utility.verifyElementPresent(".//div[text()='Badge Status']", "Badge Status Field", false, false);
				Utility.verifyElementPresent(".//div[text()='Badge Type']", "Badge Type Field", false, false);
//				Utility.verifyElementPresent(".//div[text()='Badge Reason Code']", "BadgeReasonCode Field", false, false);
				
				String badgeID = "AUTO"+Utility.UniqueNumber(5);
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime next2Week = now.plus(2, ChronoUnit.WEEKS);
				String endDate = dtf.format(next2Week);
				
				ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardBadgeIdTxt, badgeID, "Assign Badge ID");
				ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardBadgeValidToTxt, endDate, "Assign Badge Validity");
				ByAttribute.selectText("xpath", BadgingObjects.applicationWizardBadgeStatusDdn, "Active", "Select Badge Status as Active");
				ByAttribute.selectText("xpath", BadgingObjects.applicationWizardBadgeTypeDdn, badgeType, "Select Badge Type : "+badgeType);
				ByAttribute.selectText("xpath", BadgingObjects.applicationWizardBadgeReasonCodeDdn, "Activated", "Select Badge Reason Code as Activated");
									
				ByAttribute.click("xpath", BadgingObjects.applicationWizardSaveBtn, "Click on Save Button");
				Thread.sleep(30000);
//					Utility.verifyElementPresent(".//div[contains(text(),'Request Auto-Provisioned Initiated.')]", "Request Auto-Provisioned Initiated Pop-Up", false, true);
				
				List<WebElement> okButtons = driver.findElements(By.xpath(".//button[text()='OK']"));
				WebElement currentOKBtn = okButtons.get(1);
				currentOKBtn.click();
				logger.log(LogStatus.INFO, "Click OK Button");
				Thread.sleep(2000);
					
				if(badgeValidations.equalsIgnoreCase("YES"))
				{
					ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeInfoPodLnk, "Click on Badge Info Pod");
					Thread.sleep(5000);
					ByAttribute.click("xpath", ".//span[@name='chk' and @class='ui-state-default ui-corner-all ui-igcheckbox-normal']", "Click Badge ID Checkbox");
					
					/** Capture the Signature **/
					// Store the current window handle
					String winHandleBefore = driver.getWindowHandle();

					// Click on the course to open the new window
					ByAttribute.click("xpath", BadgingObjects.applicationWizardCaptureSignatureBtn, "Click on Capture Signature Button");
					Thread.sleep(5000);
					
					// Switch to new window opened
					for(String winHandle : driver.getWindowHandles()){
					    driver.switchTo().window(winHandle);
					}

					// Perform the actions on new window
					WebElement element = driver.findElement(By.xpath(".//canvas[@id='signCanvas']"));

				    Actions builder = new Actions(driver);
				    Action drawSignature = builder.moveToElement(element,135,15).click().moveByOffset(200, 60).click().moveByOffset(100, 70).doubleClick().build();
				    drawSignature.perform();
				    System.out.println("Perform Signature");
				    logger.log(LogStatus.PASS, "Perform Signature");
				    Thread.sleep(1000);
					ByAttribute.click("xpath", ".//button[@id='done']", "Click on Signature Done Button");

					// Switch back to original browser (first window)
					driver.switchTo().window(winHandleBefore);
					
					Utility.verifyElementPresent(".//div[@class='fab-message-info' and text()='Signature uploaded successfully']", "Signature Captured Successfully Message", false, false);

				}
					
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	
	/**
	* <h1>badgeStatusHistory</h1>
	* This is Method to view Badge Status History of an Applicant
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	06-03-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void badgeStatusHistory(String firstName, String badgeHistoryPermission) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** badgeStatusHistory *********************************");
			logger.log(LogStatus.INFO, "***************************** badgeStatusHistory *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
					
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(5000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
				
				ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
				ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
				Thread.sleep(2000);
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeInfoPodLnk, "Click on Badge Info Pod");
				Thread.sleep(1000);
				
				ByAttribute.click("xpath", ".//span[@name='chk' and @class='ui-state-default ui-corner-all ui-igcheckbox-normal']", "Click Badge ID Checkbox");
				
				if(badgeHistoryPermission.equalsIgnoreCase("YES"))
				{
					Utility.verifyElementPresent(BadgingObjects.applicationWizardBadgeHistoryLnk, "Badge History Link", false, true);
					ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeHistoryLnk, "Click on Badge History Link");
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//span[@class='ui-igdialog-headertext ui-dialog-title' and text()='Badge Information']", "Badge Information Title", false, false);
					Utility.verifyElementPresent(".//div[@class='fabric-form-label fab-form-td label-badgeIdValue BadgetydfsfugID:' and text()='Badge ID:']", "Badge ID Field", false, false);
					Utility.verifyElementPresent(".//div[@class='fabric-form-label fab-form-td label-statusValue Status:' and text()='Status:']", "Status Field", false, false);
					Utility.verifyElementPresent(".//div[@class='fabric-form-label fab-form-td label-validFromValue ValidtydfsfugFrom:' and text()='Valid From:']", "Valid From Field", false, false);
					Utility.verifyElementPresent(".//div[@class='fabric-form-label fab-form-td label-validToValue ValidtydfsfugTo:' and text()='Valid To:']", "Valid To Field", false, false);
					Utility.verifyElementPresent(".//div[@class='fab-pod-title' and text()='History']", "History Table", false, false);
					Utility.verifyElementPresent(".//span[@class='ui-iggrid-headertext' and text()='Request Number']", "Request Number Column Header", false, false);
					Utility.verifyElementPresent(".//span[@class='ui-iggrid-headertext' and text()='Identity Action']", "Identity Action Column Header", false, false);
					Utility.verifyElementPresent(".//span[@class='ui-iggrid-headertext' and text()='Badge Parameter']", "Badge Parameter Column Header", false, false);
					Utility.verifyElementPresent(".//span[@class='ui-iggrid-headertext' and text()='Old Value']", "Old Value Column Header", false, false);
					Utility.verifyElementPresent(".//span[@class='ui-iggrid-headertext' and text()='New Value']", "New Value Column Header", false, false);
					Utility.verifyElementPresent(".//span[@class='ui-iggrid-headertext' and text()='Modified By']", "Modified By Column Header", false, false);
					Utility.verifyElementPresent(".//span[@class='ui-iggrid-headertext' and text()='Modified Date & Time']", "Modified Date & Time Column Header", false, false);
					Utility.verifyElementPresent(".//span[@class='fab-pod-title' and text()='Search']", "Search Option", false, false);
					Utility.verifyElementPresent(".//a[text()='Advanced Search']", "Advanced Search Option", false, false);
					
					String badgeID = ByAttribute.getText("xpath", ".//span[@id='badgeIdValue']", "Get Value of Badge ID").trim();
					String recordPanel = ByAttribute.getText("xpath", ".//div[@class='badgeHistoryDataGrid fabric_comp_grid']//span[@class='ui-iggrid-pagerrecordslabel ui-iggrid-results' and @title='Current records range']", "Get Value of Record Panel");
					String[] rec01 = recordPanel.split("of");
					String[] rec02 = rec01[1].split("records");
					String recNum = rec02[0].trim();
					
					String dbRecords = " "+Airport_Badging_CommonMethods.validatePrintedVersionCheckbox(badgeID, "34.93.172.239","sqlserver","Alert1234","gdn_db_ps","SQLSERVER");
					
					if(recNum.equalsIgnoreCase(dbRecords.trim()))
					{
						System.out.println("All the Badge Parameter attributes which got modified are reflecting In \"AAXT_USER_BDG_HISTORY\" table ");
						logger.log(LogStatus.PASS, "All the Badge Parameter attributes which got modified are reflecting In \"AAXT_USER_BDG_HISTORY\" table ");
					}else{
						System.out.println("All the Badge Parameter attributes which got modified are NOT reflecting In \"AAXT_USER_BDG_HISTORY\" table ");
						logger.log(LogStatus.FAIL, "All the Badge Parameter attributes which got modified are NOT reflecting In \"AAXT_USER_BDG_HISTORY\" table ");
					}
					
					ByAttribute.click("xpath", ".//div[@class='ui-igdialog ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable']//a[@class='ui-igdialog-headerbutton ui-corner-all ui-state-default ui-igdialog-buttonclose' and @title='Close']", "Click Close Button");
				}else{
					Utility.verifyElementNotPresent(BadgingObjects.applicationWizardBadgeHistoryLnk, "Badge History Link", false);
				}
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	
	/**
	* <h1>trainingCompletion</h1>
	* This is Method to Schedule Appointment through Self Service Portal
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	06-08-2020
	* @param   	String firstName, String lastName, String userID
	* @return  	none
	**/
	
	public static void trainingCompletion(String firstName, String lastName) throws Throwable
	{
		
		if(unhandledException==false)
		{
			System.out.println("******************************** trainingCompletion  *****************************************");
			logger.log(LogStatus.INFO, "***************************** trainingCompletion *********************************");
			
			String uniqueID = Utility.validateApplicantCreatedDB(firstName,"34.93.172.239","sqlserver","Alert1234","gdn_db_ps","SQLSERVER");
			
			WebDriver driver2=Utility.openPrivateBrowser();
			default_driver=driver;
			driver=driver2;
			try
			{
				driver.get("https://devlax.iet-ls.com/");
				Utility.pause(3);
				driver.findElement(By.xpath(".//*[@id='FirstName']")).sendKeys(firstName);
				driver.findElement(By.xpath(".//*[@id='LastName']")).sendKeys(lastName);
				driver.findElement(By.xpath(".//*[@id='PersonUniqueId']")).sendKeys(uniqueID);
				driver.findElement(By.xpath(".//button[@class='btn-main' and @type='submit']")).click();
				Utility.pause(10);
				
				if(driver.findElements(By.xpath(".//h3/i[@class='icon-user']")).size()>0)
				{
					logger.log(LogStatus.PASS, "Logged In to Learning Portal");
					
					// Store the current window handle
					String winHandleBefore = driver.getWindowHandle();
					
					if(driver.findElements(By.xpath(".//*[@id='StudentCourseList']/ul//a[@class='noRetakeCourse' and text()='Authorized Signatory Training']")).size()>0)
					{
						/** Training 1 Starts **/
	
						// Click on the course to open the new window
						ByAttribute.click("xpath", ".//*[@id='StudentCourseList']/ul//a[@class='noRetakeCourse' and text()='Authorized Signatory Training']", "Select Authorized Signatory Training");
						Thread.sleep(5000);
						
						// Switch to new window opened
						for(String winHandle : driver.getWindowHandles()){
						    driver.switchTo().window(winHandle);
						}
	
						// Perform the actions on new window
						ByAttribute.click("xpath", ".//label/span[contains(text(),'A.')]", "Click on Pass Option");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//*[@id='btnQuestionSubmit']", "Click on Submit Button");
						Thread.sleep(2000);
						ByAttribute.click("xpath", ".//button[@class='swal2-confirm swal2-styled' and text()='Continue']", "Click Continue Button");
						Thread.sleep(3000);
						ByAttribute.click("xpath", ".//*[@id='btnExitCourse']", "Click Exit Course Button");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//button[@class='swal2-confirm swal2-styled' and text()='Yes']", "Click Yes Button");
	
						// Switch back to original browser (first window)
						driver.switchTo().window(winHandleBefore);
						
						Thread.sleep(2000);
						Utility.verifyElementPresent(".//*[@id='StudentCourseList']/ul//a[@class='noRetakeCourse' and text()='Authorized Signatory Training']/parent::li/i[@class='icon-ok-sign courseCompleted']", "Authorized Signatory Training Course Completed", false, false);
						/** Training 1 Ends **/
					}
					/********************************************************************************************************/
					
					if(driver.findElements(By.xpath(".//*[@id='StudentCourseList']/ul//a[@class='noRetakeCourse' and text()='Security (SIDA) Training']")).size()>0)
					{
						/** Training 2 Starts **/
	
						// Click on the course to open the new window
						ByAttribute.click("xpath", ".//*[@id='StudentCourseList']/ul//a[@class='noRetakeCourse' and text()='Security (SIDA) Training']", "Select Security (SIDA) Training");
						Thread.sleep(5000);
						
						// Switch to new window opened
						for(String winHandle : driver.getWindowHandles()){
						    driver.switchTo().window(winHandle);
						}
	
						// Perform the actions on new window
						ByAttribute.click("xpath", ".//label/span[contains(text(),'A.')]", "Click on Pass Option");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//*[@id='btnQuestionSubmit']", "Click on Submit Button");
						Thread.sleep(2000);
						ByAttribute.click("xpath", ".//button[@class='swal2-confirm swal2-styled' and text()='Continue']", "Click Continue Button");
						Thread.sleep(3000);
						ByAttribute.click("xpath", ".//*[@id='btnExitCourse']", "Click Exit Course Button");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//button[@class='swal2-confirm swal2-styled' and text()='Yes']", "Click Yes Button");
	
						// Switch back to original browser (first window)
						driver.switchTo().window(winHandleBefore);
						
						Thread.sleep(2000);
						Utility.verifyElementPresent(".//*[@id='StudentCourseList']/ul//a[@class='noRetakeCourse' and text()='Security (SIDA) Training']/parent::li/i[@class='icon-ok-sign courseCompleted']", "Security (SIDA) Training Course Completed", false, false);
						/** Training 2 Ends **/
					}
				}
				
			}
			catch (Exception e)
			{ 	
				Utility.takeScreenshot("screenshot");
				logger.log(LogStatus.ERROR, "Screenshort of BUGG :" + imgeHtmlPath);	
				throw new java.lang.RuntimeException("--------------->"+ e);	    		
			}
			finally
			{
				driver2.quit();
				driver=default_driver;
				logger.log(LogStatus.PASS, "Switched Back to Guardian Portal");
			}	
		}
	}

	
	/** <h1>existingLocationOfDivision</h1>
	* This is Method to Validate Existing Location of a Division within an Existing Company Company
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-20-2020
	* @param   	String companyName, String divisionName
	**/

	public static void existingLocationOfDivision(String companyName, String divisionName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** existingLocationOfDivision *********************************");
			logger.log(LogStatus.INFO, "***************************** existingLocationOfDivision *********************************");
			try
			{
				Utility.pause(1);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnPod("Manage Company and Divisions");
				
				Thread.sleep(3000);
				
				if(driver.findElements(By.xpath(BadgingObjects.companyManagementHeaderLbl)).size()>0)
				{				
					System.out.println("Company Management Page is available");
					logger.log(LogStatus.PASS, "Company Management Page is available");	
					
					ByAttribute.clearSetText("xpath", BadgingObjects.companyCompanyNameOrCompanyCodeTxt, companyName, "Enter Company Name");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']")).size()>0)
					{
						System.out.println("Company Exists");
						logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Exists");
						
						ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']", "Select Company");
						ByAttribute.click("xpath", BadgingObjects.companySearchBtn, "Click Search Button");
						
						Utility.verifyElementPresent(".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Company Entry in Table", false, false);
						
						ByAttribute.click("xpath", ".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Select Company");
						Thread.sleep(2000);
						List<WebElement> manageDivisionsButtonElements = driver.findElements(By.xpath(".//button[text()='Manage Divisions']"));
						WebElement manageDivisionsButton = manageDivisionsButtonElements.get(0);
						
						manageDivisionsButton.click();
						logger.log(LogStatus.INFO, "Click Manage Divisions Button");
						Thread.sleep(5000);
						
						ByAttribute.click("xpath", ".//*[contains(@id,'comp')]/tbody/tr[1]/td[1]", "Click on Existing Division");
						
						List<WebElement> modifyButtonElements = driver.findElements(By.xpath(".//button[text()='Modify']"));
						WebElement modifyButton = modifyButtonElements.get(0);
						
						modifyButton.click();
						logger.log(LogStatus.INFO, "Click Modify Button");
						
						if(driver.findElements(By.xpath(BadgingObjects.divisionManageDivisionHeaderLbl)).size()>0)
						{				
							System.out.println("Manage Division Page is available");
							logger.log(LogStatus.PASS, "Manage Division Page is available");	
							Thread.sleep(2000);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRolesPodLnk, "Click Job Roles and Location Pod");
							Thread.sleep(2000);
							Utility.connectToLatestFrame(driver);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleMappedLocationLnk, "Click Mapped Location Link");
							Thread.sleep(2000);
							Utility.verifyElementPresent(BadgingObjects.divisionMappedLocationsNewBtn, "Locations - New Button", false, false);
							Utility.verifyElementPresent(BadgingObjects.divisionMappedLocationsExistingBtn, "Locations - Existing Button", false, false);
							ByAttribute.click("xpath", BadgingObjects.divisionMappedLocationsExistingBtn, "Click Existing Button");
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//div[@class='ng-binding' and text()='Existing Selected Locations']", "Existing Selected Locations Label", false, false);
							Utility.verifyElementPresent(".//table/tbody/tr[1]//span[contains(text(),'AIRPORTS -> LAX -> ALL TERMINALS ->')]", "The locations mapped to the job position are displayed in a structure mentioned : AIRPORTS -> All Terminals > Terminal 1.", false, false);
							Utility.verifyElementPresent(".//table/tbody/tr[1]//md-checkbox[@aria-label='Select Row']", "Check box for each mapped location", false, false);
							Utility.verifyElementPresent(".//md-toolbar//button[@aria-label='Delete']", "Delete icon", false, false);
							Utility.verifyElementPresent(".//md-toolbar//button[@aria-label='Clear All']", "Clear All icon", false, false);
							Utility.verifyElementPresent(".//md-toolbar//button[@ng-if='$ctrl.rowSelection && $ctrl.showSelectAllBtn']", "Selected Rows icon", false, false);
							Utility.verifyElementPresent(".//md-sidenav[2]//alnt-custom-grid-comp//md-table-pagination[@class='ae-st-grid-comp-pagination md-table-pagination ng-isolate-scope']", "Pagination", false, false);
							
							String firstLocation = ByAttribute.getText("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[1]/td[2]/span", "Get Value of first Location").trim();
							
							ByAttribute.click("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[1]/td[1]/md-checkbox", "Select first Location in List");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//md-toolbar//button[@aria-label='Delete']", "Click Delete Icon");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//md-dialog/md-dialog-actions/button[@ng-click='dialog.abort()']", "Click No Button");
							if(driver.findElements(By.xpath(".//table/tbody/tr[1]//span[contains(text(),'"+firstLocation+"')]")).size()>0)
							{
								System.out.println("Pop-up Closed and Location NOT Deleted");
								logger.log(LogStatus.PASS, "Pop-up Closed and Location NOT Deleted");
							}
							
							ByAttribute.click("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[1]/td[1]/md-checkbox", "Select first Location in List");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//md-toolbar//button[@aria-label='Delete']", "Click Delete Icon");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//md-dialog/md-dialog-actions/button[@ng-click='dialog.hide()']", "Click Yes Button");
							Thread.sleep(1000);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleMappedLocationLnk, "Click Mapped Location Link");
							Thread.sleep(2000);
							Utility.verifyElementPresent(BadgingObjects.divisionMappedLocationsNewBtn, "Locations - New Button", false, false);
							Utility.verifyElementPresent(BadgingObjects.divisionMappedLocationsExistingBtn, "Locations - Existing Button", false, false);
							ByAttribute.click("xpath", BadgingObjects.divisionMappedLocationsExistingBtn, "Click Existing Button");
							if(driver.findElements(By.xpath(".//table/tbody/tr[1]//span[contains(text(),'"+firstLocation+"')]")).size()>0)
							{
								System.out.println("Location NOT Deleted");
								logger.log(LogStatus.FAIL, "Location NOT Deleted");
							}else{
								System.out.println("Location IS Deleted");
								logger.log(LogStatus.PASS, "Location IS Deleted");
							}
							
							/****  LAWA-T835 (1.0)  ****/
							ByAttribute.click("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[1]/td[1]/md-checkbox", "Select first Location in List");
							if(driver.findElements(By.xpath(".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[1]/td[1]/md-checkbox[@aria-checked='true']")).size()>0)
							{
								System.out.println("Location IS SELECTED");
								logger.log(LogStatus.PASS, "Location IS SELECTED");
							}else{
								System.out.println("Location IS NOT SELECTED");
								logger.log(LogStatus.FAIL, "Location IS NOT SELECTED");
							}
							ByAttribute.click("xpath", ".//md-toolbar//button[@aria-label='Clear All']", "Click Clear All Icon");
							Thread.sleep(1000);
							if(driver.findElements(By.xpath(".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[1]/td[1]/md-checkbox[@aria-checked='false']")).size()>0)
							{
								System.out.println("Location IS NOT SELECTED");
								logger.log(LogStatus.PASS, "Location IS NOT SELECTED");
							}else{
								System.out.println("Location IS SELECTED");
								logger.log(LogStatus.FAIL, "Location IS SELECTED");
							}
							
							
							/****  LAWA-T836 (1.0)  ****/
							ByAttribute.click("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[1]/td[1]/md-checkbox", "Select first Location in List");
							if(driver.findElements(By.xpath(".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[2]/td[1]/md-checkbox")).size()>0)
							{
								System.out.println("Other Locations also Displayed");
								logger.log(LogStatus.PASS, "Other Locations also Displayed");
							}else{
								System.out.println("Other Locations NOT Displayed");
								logger.log(LogStatus.FAIL, "Other Locations NOT Displayed");
							}
							ByAttribute.click("xpath", ".//md-toolbar//button[@ng-if='$ctrl.rowSelection && $ctrl.showSelectAllBtn']", "Click Selected Rows Icon");
							Thread.sleep(1000);
							if(driver.findElements(By.xpath(".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[2]/td[1]/md-checkbox")).size()>0)
							{
								System.out.println("Other Locations also Displayed");
								logger.log(LogStatus.FAIL, "Other Locations also Displayed");
							}else{
								System.out.println("Only Selected Locations Displayed");
								logger.log(LogStatus.PASS, "Only Selected Locations Displayed");
							}
							
							ByAttribute.click("xpath", ".//md-toolbar//button[@aria-label='Clear All']", "Click Clear All Icon");
							
							/****  LAWA-T837 (1.0)  ****/
							ByAttribute.click("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//md-icon[@md-svg-icon='vendor/angular/angularTable/arrow-up.svg']", "Click on Sorting Arrow");
							String initialFirstLocationTxt = ByAttribute.getText("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[1]/td[2]/span", "Get Value of first Location").trim();
							ByAttribute.click("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//md-icon[@md-svg-icon='vendor/angular/angularTable/arrow-up.svg']", "Click on Sorting Arrow");
							String changedFirstLocationTxt = ByAttribute.getText("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[1]/td[2]/span", "Get Value of first Location").trim();
							ByAttribute.click("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//md-icon[@md-svg-icon='vendor/angular/angularTable/arrow-up.svg']", "Click on Sorting Arrow");
							String finalFirstLocationTxt = ByAttribute.getText("xpath", ".//div[@class='job-position-location-tree-comp ng-scope']//table/tbody/tr[1]/td[2]/span", "Get Value of first Location").trim();
							
							if(initialFirstLocationTxt.trim().equals(changedFirstLocationTxt.trim()))
							{
								System.out.println("Descending is NOT WORKING for Location Sorting");
								logger.log(LogStatus.FAIL, "Descending is NOT WORKING for Sorting");
							}else{
								System.out.println("Descending is WORKING for Location Sorting");
								logger.log(LogStatus.PASS, "Descending is WORKING for Location Sorting");
							}
							
							if(changedFirstLocationTxt.trim().equals(finalFirstLocationTxt.trim()))
							{
								System.out.println("Ascending is NOT WORKING for Location Sorting");
								logger.log(LogStatus.FAIL, "Ascending is NOT WORKING for Sorting");
							}else{
								System.out.println("Ascending is WORKING for Location Sorting");
								logger.log(LogStatus.PASS, "Ascending is WORKING for Location Sorting");
							}
							
							
							/****  LAWA-T838 (1.0)  ****/
							Utility.verifyElementPresent(".//div[@class='job-position-existing-grid']//md-select-value[@class='md-select-value']//div[text()='1']", "Page 1 of Added Locations List", false, false);
							ByAttribute.click("xpath", ".//div[@class='job-position-existing-grid']//button[@aria-label='Next']", "Click Next Button in Pagination");
							Thread.sleep(2000);
							Utility.verifyElementPresent(".//div[@class='job-position-existing-grid']//md-select-value[@class='md-select-value']//div[text()='2']", "Page 2 of Added Locations List - Next Button is Working Fine", false, false);
							ByAttribute.click("xpath", ".//div[@class='job-position-existing-grid']//button[@aria-label='Previous']", "Click Previous Button in Pagination");
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//div[@class='job-position-existing-grid']//md-select-value[@class='md-select-value']//div[text()='1']", "Page 1 of Added Locations List - Previous Button is Working Fine", false, false);
							
							ByAttribute.click("xpath", ".//*[@id='select_value_label_49']", "Select Page from Page Dropdown List");
							Thread.sleep(500);
							ByAttribute.click("xpath", ".//md-option[@role='option' and @value='4']", "Select Page 4");
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//div[@class='job-position-existing-grid']//md-select-value[@class='md-select-value']//div[text()='4']", "Page 4 of Added Locations List - Page Selection Dropdown is Working Fine", false, false);
							
							ByAttribute.click("xpath", ".//*[@id='select_value_label_49']", "Select Page from Page Dropdown List");
							Thread.sleep(500);
							List<WebElement> pageNumElements = driver.findElements(By.xpath(".//md-option[@role='option' and @value='1']"));
							WebElement selectPage1 = pageNumElements.get(1);
							selectPage1.click();
							logger.log(LogStatus.INFO, "Select Page 1");
							
							
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//div[@class='job-position-existing-grid']//md-select-value[@class='md-select-value']//div[text()='1']", "Page 1 of Added Locations List - Navigation Back to Page 1 Successful", false, false);
							
							ByAttribute.click("xpath", ".//*[@id='select_value_label_52']", "Select Number of Rows from Rows Per Page Dropdown List");
							Thread.sleep(500);
							List<WebElement> rowsPerPageElements = driver.findElements(By.xpath(".//md-option[@role='option' and @value='20']"));
							WebElement select20Rows = rowsPerPageElements.get(1);
							select20Rows.click();
							logger.log(LogStatus.INFO, "Select Rows 20");
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//div[@class='job-position-existing-grid']//div[@class='label ng-binding' and contains(text(),'1 - 20 of')]", "Number of Rows Changed to 20 Per Page", false, false);
							
							ByAttribute.click("xpath", ".//md-sidenav[2]//div[@class='ae-st-icon ae-st-close job-close-icon ']", "Close the Side nav");
							driver.switchTo().defaultContent();
						}else{
							System.out.println("Company Profile Page Not Found");
							logger.log(LogStatus.FAIL, "Company Profile Page Not Found");
						}
					}
				}
				else
				{
					System.out.println("Navigation to 'Appointment Type Details' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Appointment Type Details' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}

	
	/** <h1>validateLocationPresenceForDivision</h1>
	* This is Method to Validate Location Presence
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	06-17-2020
	* @param   	String companyName, String divisionName, String locationName
	**/

	public static boolean validateLocationPresenceForDivision(String companyName, String divisionName, String locationName) throws Throwable
	{
		String locationExist = "Yes";
		if(unhandledException==false)
		{
			System.out.println("***************************** validateLocationPresenceForDivision *********************************");
			logger.log(LogStatus.INFO, "***************************** validateLocationPresenceForDivision *********************************");
			
			try
			{
				Utility.pause(1);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnPod("Manage Company and Divisions");
				
				Thread.sleep(5000);
				
				if(driver.findElements(By.xpath(BadgingObjects.companyManagementHeaderLbl)).size()>0)
				{				
					System.out.println("Company Management Page is available");
					logger.log(LogStatus.PASS, "Company Management Page is available");	
					
					ByAttribute.clearSetText("xpath", BadgingObjects.companyCompanyNameOrCompanyCodeTxt, companyName, "Enter Company Name");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']")).size()>0)
					{
						System.out.println("Company Exists");
						logger.log(LogStatus.INFO, "Company with Name {"+companyName+"} Exists");
						
						ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[text()='"+companyName+"']", "Select Company");
						ByAttribute.click("xpath", BadgingObjects.companySearchBtn, "Click Search Button");
						
						Utility.verifyElementPresent(".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Company Entry in Table", false, false);
						
						ByAttribute.click("xpath", ".//tbody/tr/td[contains(text(),'"+companyName+"')]", "Select Company");
						Thread.sleep(2000);
						List<WebElement> manageDivisionsButtonElements = driver.findElements(By.xpath(".//button[text()='Manage Divisions']"));
						WebElement manageDivisionsButton = manageDivisionsButtonElements.get(0);
						
						manageDivisionsButton.click();
						logger.log(LogStatus.INFO, "Click Manage Divisions Button");
						Thread.sleep(5000);
						
						ByAttribute.click("xpath", ".//*[contains(@id,'comp')]/tbody/tr[1]/td[1]", "Click on Existing Division");
						
						List<WebElement> modifyButtonElements = driver.findElements(By.xpath(".//button[text()='Modify']"));
						WebElement modifyButton = modifyButtonElements.get(0);
						
						modifyButton.click();
						logger.log(LogStatus.INFO, "Click Modify Button");
						
						if(driver.findElements(By.xpath(BadgingObjects.divisionManageDivisionHeaderLbl)).size()>0)
						{				
							System.out.println("Manage Division Page is available");
							logger.log(LogStatus.PASS, "Manage Division Page is available");	
							Thread.sleep(2000);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRolesPodLnk, "Click Job Roles and Location Pod");
							Thread.sleep(2000);
							Utility.connectToLatestFrame(driver);
							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleMappedLocationLnk, "Click Mapped Location Link");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//div[@class='job-position-location-tree-node-label ng-binding' and text()='AIRPORTS']/ancestor::div[@class='layout-row flex']/div[@class='ae-tree-view-navigate-button']", "Click Expand Airport locations Button");
							Thread.sleep(1000);
							if(driver.findElements(By.xpath(".//div[@class='job-position-location-tree-node-label ng-binding' and text()='"+locationName+"']")).size()>0)
							{
								locationExist = "Yes";
							}else{
								locationExist = "No";
							}

							ByAttribute.click("xpath", BadgingObjects.divisionJobRoleSaveBtn, "Click Save Card Button");
							Thread.sleep(1000);
							Utility.verifyElementPresent(BadgingObjects.divisionJobRoleLocationsUpdatedSuccessfullyLbl, "Locations Updated Successfully", false, false);
							driver.switchTo().defaultContent();
						}else{
							System.out.println("Company Profile Page Not Found");
							logger.log(LogStatus.FAIL, "Company Profile Page Not Found");
						}
					}
				}
				else
				{
					System.out.println("Navigation to 'Company Management' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Company Management' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
		
		if(locationExist.equalsIgnoreCase("Yes"))
		{
			return true;
		}else{
			return false;
		}
	
	}
	
	
	/** <h1>restrictLocationMapping</h1>
	* This is Method to Restrict Location Mapping
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	06-17-2020
	* @param   	String locationName
	**/

	public static void restrictLocationMapping(String locationName, String restrictLocation) throws Throwable
	{
		if(unhandledException==false)
		{
			System.out.println("***************************** restrictLocationMapping *********************************");
			logger.log(LogStatus.INFO, "***************************** restrictLocationMapping *********************************");
			
			try
			{
				Utility.pause(1);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupTabLnk, "Click on Setup tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				ByAttribute.click("xpath", SetupObjects.setupLeftMenuManualConfigurationLnk, "Click on Manual Configuration");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupLeftMenuOrganizationDataLnk, "Click on Organization Data");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupLeftMenuLocationLnk, "Click on Location");
				Thread.sleep(1000);
				
				Utility.connectToLatestFrame(driver);
				
				ByAttribute.click("xpath", SetupObjects.setupMiddleMenuAirportsExpanderBtn, "Click on Airports Expander Button");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupMiddleMenuLAXLbl, "Click on LAX");
				Thread.sleep(1000);
				
				if(driver.findElements(By.xpath(SetupObjects.setupLocationNameLAXLbl)).size()>0)
				{				
					System.out.println("LAX Location Page is available");
					logger.log(LogStatus.PASS, "LAX Location Page is available");	
					
					ByAttribute.click("xpath", SetupObjects.setupLocationModifyBtn, "Click on Modify Button");
					
					if(restrictLocation.equalsIgnoreCase("YES"))
					{
						ByAttribute.click("xpath", SetupObjects.setupDefaultFromParentChk, "Uncheck Default from Parent Checkbox");
						Thread.sleep(1000);
						ByAttribute.click("xpath", SetupObjects.setupNoMapWithJobRoleChk, "Check No Map With Job Role Checkbox");
						Thread.sleep(1000);
						ByAttribute.click("xpath", SetupObjects.setupModifyLocationSaveBtn, "Click Save Button");
						Utility.verifyElementPresent(SetupObjects.setupSuccessfullyUpdatedLocationLbl, "Successfully updated the location Message", false, true);
					}else{
						ByAttribute.click("xpath", SetupObjects.setupDefaultFromParentChk, "Uncheck Default from Parent Checkbox");
						Thread.sleep(1000);
						ByAttribute.click("xpath", SetupObjects.setupConfirmPopupOKBtn, "Click OK Button");
						Thread.sleep(1000);
						ByAttribute.click("xpath", SetupObjects.setupModifyLocationSaveBtn, "Click Save Button");
						Utility.verifyElementPresent(SetupObjects.setupSuccessfullyUpdatedLocationLbl, "Successfully updated the location Message", false, true);
					}
				}
				else
				{
					System.out.println("Navigation to 'Locations' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Locations' Page Failed");
				}
				
				driver.switchTo().defaultContent();
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	
	/**
	* <h1>validateAppointmentScheduled</h1>
	* This is Method to Validate the if Appointment is Scheduled
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	06-26-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void validateAppointmentScheduled(String firstName, String appointmentType) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** validateAppointmentScheduled *********************************");
			logger.log(LogStatus.INFO, "***************************** validateAppointmentScheduled *********************************");
			try
			{
				Thread.sleep(1000);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnPod("Appointment Scheduler");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingAppointmentsLnk, "Click on Appointments Link");
				Thread.sleep(5000);
				
				for(int i=0;i<7;i++)
				{
					if(driver.findElements(By.xpath(".//*[@id='appointment-calendar']//div[contains(text(),'"+firstName+"')]")).size()>0)
					{
						System.out.println("Appointment Successfully Created for - "+firstName);
						logger.log(LogStatus.PASS, "Appointment Successfully Created for - "+firstName);
						
						Utility.verifyElementPresent(".//*[@id='appointment-calendar']//div[contains(text(),'"+firstName+"')]", "Scheduled Appointment for Applicant - "+firstName, false, false);
						
						ByAttribute.click("xpath", ".//*[@id='appointment-calendar']//div[contains(text(),'"+firstName+"')]", "Click on Appointment");
						Thread.sleep(2000);
						Utility.verifyElementPresent(".//*[@id='appointmentDialog']//div[contains(text(),'"+firstName+"')]", "Appointment Dialog for Correct Applicant", false, false);
						Utility.verifyElementPresent(".//*[@id='select_value_label_0']//div[text()='"+appointmentType+"']", "Correct Appointment Type", false, false);
						Utility.verifyElementPresent(".//md-radio-button[@class='md-primary status-radio ng-scope md-checked' and @aria-label='New']", "Status-New Selected", false, false);
						ByAttribute.click("xpath", ".//*[@id='appointmentDialog']//button[@ng-click='statusId == aptCancelledStatusLong ? openAppointmentCancelliationPopup() : submitEvent()']", "Appointment Save Button");
						Thread.sleep(3000);
						Utility.verifyElementPresent(".//div[@class='ng-binding ae-message-success-text' and text()='Appointment Modified Successfully']", "Appointment Modified Successfully Message", false, false);
						break;
					}else{
						ByAttribute.click("xpath", ".//*[@id='appointment-calendar']//span[@class='fc-icon fc-icon-right-single-arrow']/parent::button", "Click on Next Day Button");
						Thread.sleep(2000);
					}
				}
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	
	/**
	* <h1>selfServiceScheduleAppointmentValidations</h1>
	* This is Method to Schedule Appointment through Self Service Portal
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-26-2020
	* @param   	String userID, String password
	* @return  	none
	**/
	
	public static void selfServiceScheduleAppointmentValidations(String username, String password, String appointmentType) throws Throwable
	{
		
		if(unhandledException==false)
		{
			System.out.println("******************************** selfServiceScheduleAppointmentValidations  *****************************************");
			logger.log(LogStatus.INFO, "***************************** selfServiceScheduleAppointmentValidations *********************************");
			WebDriver driver2=Utility.openPrivateBrowser();
			default_driver=driver;
			driver=driver2;
			try
			{
				driver2.get(AGlobalComponents.selfServicePortalURL);
				Utility.pause(2);
				driver2.findElement(By.xpath(LoginObjects.selfServiceSigninWithOKTABtn)).click();
				Utility.pause(3);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTAUsernameTxt)).sendKeys(username);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTAPasswordTxt)).sendKeys(password);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTASigninBtn)).click();
				Utility.pause(15);
				logger.log(LogStatus.PASS, "Logged In to Self Service Portal");
				ByAttribute.clickOnSelfServicePod("My Appointments");
				Utility.pause(2);
				
//				if(driver.findElements(By.xpath(".//p[text()='"+appointmentType+"']")).size()>0)
//				{
//					Utility.verifyElementPresent(".//p[text()='"+appointmentType+"']", appointmentType+" Type Entry in Table", false, false);
//					ByAttribute.click("xpath", ".//mat-icon[@svgicon='reschedule']", "Click Reschedule Button");
//				
//					List<WebElement> expansionPanels = driver.findElements(By.xpath(".//mat-expansion-panel"));
//					if(expansionPanels.size()>0)
//					{
//						for(WebElement panel:expansionPanels)
//						{
//							panel.click();
//							Thread.sleep(1000);
//						}
//					}
//					
//					List<WebElement> availableSlots = driver.findElements(By.xpath(".//*[contains(@id,'cdk-accordion-child')]//mat-chip[@style='background-color: rgb(255, 255, 255); border: 1px dashed;']"));
//					
//					if(availableSlots.size()>0)
//					{
//						System.out.println("Appointment Slots Available. Appointment Can be Scheduled");
//						logger.log(LogStatus.PASS, "Appointment Slots Available. Appointment Can be Scheduled");
//						
//						WebElement firstAvailableSlot = availableSlots.get(2);
//						
//						firstAvailableSlot.click();
//						logger.log(LogStatus.INFO, "Select some next Available Slot for Appointment");
//						
//						ByAttribute.click("xpath", ".//span[contains(text(),'Reschedule')]//parent::button", "Cick on Reschedule Button");
//						
//						Thread.sleep(2000);
//						Utility.verifyElementPresent(".//mat-dialog-content[contains(text(),'Your appointment for \"Badge Application\" has been confirmed for')]", "Appointment Confirmed Message", false, true);
//						ByAttribute.click("xpath", ".//mat-dialog-actions/button/span[text()='OK']", "Cick on OK Button");
//						Utility.pause(3);
//						Utility.verifyElementPresent(".//p[text()='"+appointmentType+"']", appointmentType+" Type Entry in Table", false, false);
//						
//						logger.log(LogStatus.PASS, "Successfully: Applicant Self Service Request Appointment Created");
//						System.out.println("Successfully: Applicant Self Service Request Appointment Created");
//					}
//
//				}
				
				if(driver.findElements(By.xpath(".//p[text()='"+appointmentType+"']")).size()>0)
				{
					Utility.verifyElementPresent(".//p[text()='"+appointmentType+"']", appointmentType+" Type Entry in Table", false, false);
					ByAttribute.click("xpath", ".//mat-icon[@svgicon='cancelCalendarActive']", "Click Cancel Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//h3[text()='Confirmation ']", "Cancel Confirmation Popup", false, false);
//					Utility.verifyElementPresent(".//mat-dialog-container/app-fabric-dialog//p/text()", "Are you sure you want to Cancel the Appointment Message", false, false);
					Utility.verifyElementPresent(".//span[@class='mat-button-wrapper' and text()='Yes']/parent::button", "Yes Button", false, false);
					Utility.verifyElementPresent(".//span[@class='mat-button-wrapper' and text()='No']/parent::button", "No Button", false, false);
				
					ByAttribute.click("xpath", ".//span[@class='mat-button-wrapper' and text()='Yes']/parent::button", "Click Yes Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//app-appointment-status//p[@class='ng-star-inserted' and text()='Cancelled']", "Appointment Status Changed to Cancel", false, false);
				}else{
					System.out.println("Appointment Entry Not Found in Table");
					logger.log(LogStatus.FAIL, "Appointment Entry Not Found in Table");
				}
				
			}
			catch (Exception e)
			{ 	
				Utility.takeScreenshot("screenshot");
				logger.log(LogStatus.ERROR, "Screenshort of BUGG :" + imgeHtmlPath);	
				throw new java.lang.RuntimeException("--------------->"+ e);	    		
			}
			finally
			{
				driver2.quit();
				driver=default_driver;
				logger.log(LogStatus.PASS, "Switched Back to Guardian Portal");
			}	
		}
	}

	
	/**
	* <h1>createAplicantInvitationASPortal</h1>
	* This is Method to Create an Applicant Through Self Service Portal
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-09-2020
	* @param   	String actor
	* @return  	none
	**/

	public static String createAplicantInvitationASPortal(String actor) throws Throwable
	{

		String reqNum = "";
		if(unhandledException==false)
		{
			System.out.println("***************************** createAplicantInvitationASPortal *********************************");
			logger.log(LogStatus.INFO, "***************************** createAplicantInvitationASPortal *********************************");
			try
			{
				Utility.pause(5);
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnSelfServicePod("Applicant Invitation");
				Thread.sleep(3000);
				if(actor.equalsIgnoreCase("ECMU"))
				{
					ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInviteByECMULnk, "Click on Applicant Invitation By ECMU Link");
				}else{
					ByAttribute.click("xpath", SelfServiceObjects.selfServiceApplicantInvitationByASPanelLnk, "Click on Applicant Invitation By AS Panel");
					Thread.sleep(2000);
					ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInviteByASLnk, "Click on Applicant Invitation Link");
				}
				
				Thread.sleep(5000);
				
				if(driver.findElements(By.xpath(SelfServiceObjects.selfServiceDuplicateCheckLbl)).size()>0)
				{				
					System.out.println("CreateApplicantInvitation - Duplicate Check Form is available");
					logger.log(LogStatus.PASS, "CreateApplicantInvitation - Duplicate Check Form is available");	
					
					String lastName = "Test";
					String dob = "03/26/1989";
					String email = "automation.alertuser04@gmail.com";
					String jobRole = "A P MECHANIC";
					
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
					LocalDateTime now = LocalDateTime.now();
					LocalDateTime next4Week = now.plus(4, ChronoUnit.WEEKS);
					String badgeValidity = dtf.format(next4Week);
					
					ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceDupDOBTxt, dob, "Enter Date of Birth");
					ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceDupSSNTxt, AGlobalComponents.applicantSSN, "Enter SSN");
					ByAttribute.click("xpath", SelfServiceObjects.selfServiceDupContinueBtn, "Click on Continue Button");
					Thread.sleep(3000);
					
					if(driver.findElement(By.xpath(SelfServiceObjects.selfServiceCreateApplicantInvitationEmailTxt)).isEnabled())
					{
						System.out.println("No Duplicate Entry Found");
						logger.log(LogStatus.PASS, "No Duplicate Entry Found");
						
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationCitizenshipTypeDdn, "Select Citizenship");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' US Citizen ']", "Select US Citizen");
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationFirstNameTxt, AGlobalComponents.applicantFirstName, "Enter First Name: "+AGlobalComponents.applicantFirstName);
						System.out.println(AGlobalComponents.applicantFirstName);
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationLastNameTxt, lastName, "Enter Last Name");
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationContactPhoneTxt, Utility.UniqueNumber(10), "Enter Contact Number");
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationEmailTxt, email, "Enter Email ID");
						if(actor.equalsIgnoreCase("AS"))
						{
							ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationSSNTxt, AGlobalComponents.applicantSSN, "Enter SSN");
							ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationSSNConfirmationTxt, AGlobalComponents.applicantSSN, "Confirm SSN");
						}
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationAirportDdn, "Select Airport from Dropdown");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' LAX ']", "Select Airport as LAX");
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationOrganizationTxt, AGlobalComponents.applicantOrganization, "Enter Organization");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//span[@class='mat-option-text']/div[contains(text(),'"+AGlobalComponents.applicantOrganization+"')]", "Select Organization : "+AGlobalComponents.applicantOrganization);
						Thread.sleep(2000);
						if(actor.equalsIgnoreCase("AS"))
						{
							ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationBadgeValidToTxt, "Enter Badge Valid To Date");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//div[@class='mat-calendar-body-cell-content mat-calendar-body-today']", "Select today");
							Thread.sleep(1000);
							ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationIsAuthSignatoryDdn, "Select Requires Authorised Signatory");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' No ']", "Select No");
						}
						
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationJobRoleTxt, "*", "Enter Job Role");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//span[@class='mat-option-text']/div[text()='"+jobRole+"']", "Select Job Role : "+jobRole);
						Thread.sleep(1000);
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationBadgeTypeDdn, "Select Badge Type from Dropdown");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' Blue (SIDA AOA) ']", "Select Badge Type as Blue (SIDA AOA)");
						Thread.sleep(500);
						
						if(actor.equalsIgnoreCase("ECMU"))
						{
							ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationIsAuthSignatoryDdn, "Select Requires Authorised Signatory");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' Yes ']", "Select Yes");
						}
						
						if(AGlobalComponents.airportPriviledge!="")
						{
							ByAttribute.click("xpath", ".//mat-select[@id='"+AGlobalComponents.airportPriviledge+"']", "Click "+AGlobalComponents.airportPriviledge);
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' Yes ']", "Select Yes");
							
							if(AGlobalComponents.airportPriviledge.equalsIgnoreCase("RAD_APD"))
							{
								ByAttribute.click("xpath", ".//mat-select[@id='Aircraft_fueling']", "Click Aircraft_fueling");
								Thread.sleep(1000);
								ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' No ']", "Select No");
							}
						}
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationSubmitBtn, "Click Submit Button");
						Thread.sleep(10000);
						Utility.verifyElementPresent(SelfServiceObjects.selfServiceCreateApplicantInvitationConfirmationMessageLbl, "Confirmation Pop-Up", false, false);
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationOKBtn, "Click OK button");
						Utility.pause(5);
						driver.navigate().refresh();
					}else{
						
					}
				}
				else
				{
					System.out.println("Navigation to 'Appointment Type Details' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Appointment Type Details' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
		return reqNum;		
	}
	
	
	/**
	* <h1>createNewIncident</h1>
	* This is Method to Create a New Incident
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-14-2020
	* @param   	String incidentName
	**/

	public static void createNewIncident(String incidentName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** createNewIncident *********************************");
			logger.log(LogStatus.INFO, "***************************** createNewIncident *********************************");
			try
			{
				Utility.pause(2);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnPod("Incident Management");
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.incidentManagementIncidentNameLnk, "Click on Incident Name Link");
				Thread.sleep(3000);
				
				if(driver.findElements(By.xpath(BadgingObjects.incidentManagementViolationNamesHeaderLbl)).size()>0)
				{				
					System.out.println("Incident Management Page is available");
					logger.log(LogStatus.PASS, "Incident Management Page is available");	
					
					ByAttribute.click("xpath", BadgingObjects.incidentManagementNewBtn, "Click New Button");
					Thread.sleep(1000);
					
					if(driver.findElements(By.xpath(BadgingObjects.incidentManagementCreateViolationHeaderLbl)).size()>0)
					{				
						System.out.println("Create Incident Page is available");
						logger.log(LogStatus.PASS, "Create Incident Page is available");	
						
						ByAttribute.clearSetText("xpath", BadgingObjects.incidentManagementViolationNameTxt, incidentName, "Enter Incident Name : "+incidentName);
						ByAttribute.clearSetText("xpath", BadgingObjects.incidentManagementViolationTitleTxt, "Automation Testing", "Enter Incident Title");
						ByAttribute.selectText("xpath", BadgingObjects.incidentManagementViolationIncidentTypeDdn, "CITATION", "Select Incident Type");
						ByAttribute.clearSetText("xpath", BadgingObjects.incidentManagementViolationDemeritPointsTxt, "3", "Enter Demerit Points");
						ByAttribute.selectText("xpath", BadgingObjects.incidentManagementViolationTypeDdn, "Safety and Driving", "Select Violation Type");
						ByAttribute.click("xpath", BadgingObjects.incidentManagementViolationActiveChk, "Check Active Checkbox");
						ByAttribute.click("xpath", BadgingObjects.incidentManagementViolationSaveBtn, "Click Save Button");
						Thread.sleep(5000);
						Utility.verifyElementPresent(".//tbody/tr/td[text()='"+incidentName+"']", "Incident Saved Successfully", false, true);
					}else{
						System.out.println("Create Incident Page Not Found");
						logger.log(LogStatus.FAIL, "Create Incident Page Not Found");
					}
				}
				else
				{
					System.out.println("Navigation to 'Incident Management' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Incident Management' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}
	
	
	/**
	* <h1>modifyIncident</h1>
	* This is Method to Modify an Existing Incident
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-14-2020
	* @param   	String incidentName
	**/

	public static void modifyIncident(String incidentName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** modifyIncident *********************************");
			logger.log(LogStatus.INFO, "***************************** modifyIncident *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnPod("Incident Management");
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.incidentManagementIncidentNameLnk, "Click on Incident Name Link");
				Thread.sleep(3000);
				
				if(driver.findElements(By.xpath(BadgingObjects.incidentManagementViolationNamesHeaderLbl)).size()>0)
				{				
					System.out.println("Incident Management Page is available");
					logger.log(LogStatus.PASS, "Incident Management Page is available");	
					
					ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+incidentName+"']", "Click on Incident Name");
					Thread.sleep(1000);
					ByAttribute.click("xpath", BadgingObjects.incidentManagementModifyBtn, "Click Modify Button");
					Thread.sleep(1000);
					
					if(driver.findElements(By.xpath(BadgingObjects.incidentManagementModifyViolationHeaderLbl)).size()>0)
					{				
						System.out.println("Modify Incident Page is available");
						logger.log(LogStatus.PASS, "Modify Incident Page is available");	
						
						ByAttribute.clearSetText("xpath", BadgingObjects.incidentManagementViolationTitleTxt, "Incident Modified", "Enter Incident Title");
						ByAttribute.click("xpath", BadgingObjects.incidentManagementViolationActiveChk, "Uncheck Active Checkbox");
						Utility.verifyElementPresent(".//div[@class='confirm-message-text fabric-confirmpopup-message-error' and text()='Are you sure you want to deactivate this violation name?']", "Deactivate Incident Confirmation Popup", false, false);
						ByAttribute.click("xpath", BadgingObjects.incidentManagementDeleteIncidentOkBtn, "Click OK Button");
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//div[@class='fab-message-info' and text()='Violation name deactivated successfully']", "Violation Deactivated Successfully Message", false, false);
						ByAttribute.click("xpath", BadgingObjects.incidentManagementViolationSaveBtn, "Click Save Button");
						Thread.sleep(5000);
						Utility.verifyElementPresent(".//tbody/tr/td[text()='Incident Modified']", "Incident Modified Successfully", false, true);
					}else{
						System.out.println("Modify Incident Page Not Found");
						logger.log(LogStatus.FAIL, "Modify Incident Page Not Found");
					}
				}
				else
				{
					System.out.println("Navigation to 'Incident Management' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Incident Management' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}

	
	/**
	* <h1>deleteIncident</h1>
	* This is Method to Delete an Existing Incident
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-14-2020
	* @param   	String incidentName
	**/

	public static void deleteIncident(String incidentName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** deleteIncident *********************************");
			logger.log(LogStatus.INFO, "***************************** deleteIncident *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnPod("Incident Management");
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.incidentManagementIncidentNameLnk, "Click on Incident Name Link");
				Thread.sleep(3000);
				
				if(driver.findElements(By.xpath(BadgingObjects.incidentManagementViolationNamesHeaderLbl)).size()>0)
				{				
					System.out.println("Incident Management Page is available");
					logger.log(LogStatus.PASS, "Incident Management Page is available");	
					
					ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+incidentName+"']", "Click on Incident Name");
					Thread.sleep(1000);
					ByAttribute.click("xpath", BadgingObjects.incidentManagementDeleteBtn, "Click Delete Button");
					Thread.sleep(1000);
					
					Utility.verifyElementPresent(".//div[@class='confirm-message-text fabric-confirmpopup-message-error' and text()='Delete Incident Name: "+incidentName+"']", "Delete Incident Pop-up", false, true);
					ByAttribute.click("xpath", BadgingObjects.incidentManagementDeleteIncidentOkBtn, "Click OK Button");
					Thread.sleep(1000);
					Utility.verifyElementNotPresent(".//tbody/tr/td[text()='"+incidentName+"']", "Incident Entry in Table", false);
				}
				else
				{
					System.out.println("Navigation to 'Incident Management' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Incident Management' Page Failed");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	
	}

	
	/**
	* <h1>adminSetRolesAndPermissions</h1>
	* This is Method to Set Roles and Permissions Via Admin
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-17-2020
	* @param   	String roleName, String permissionName, String reqPermissionState
	* @return  	none
	**/
	
	public static void adminSetRolesAndPermissions(String roleName, String permissionName, String reqPermissionState) throws Throwable
	{
		
		if(unhandledException==false)
		{
			System.out.println("******************************** adminSetRolesAndPermissions  *****************************************");
			logger.log(LogStatus.INFO, "***************************** adminSetRolesAndPermissions *********************************");
			
			WebDriver driver2=Utility.openPrivateBrowser();
			default_driver=driver;
			driver=driver2;
			try
			{
				LoginPage.login("Admin", "System@123");
				logger.log(LogStatus.PASS, "Logged-In with Admin Role");
				
				Thread.sleep(2000);
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				driver.navigate().refresh();
				Utility.pause(2);
				ByAttribute.clickMoreMenuTab("Setup");
				driver.navigate().refresh();
				Utility.pause(2);
				ByAttribute.click("xpath", SetupObjects.setupLeftMenuManualConfigurationLnk, "Click on Manual Configuration");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupLeftMenuUserManagementLnk, "Click on User Management");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupLeftMenuRolesLnk, "Click on Roles");
				Thread.sleep(3000);
				Utility.connectToLatestFrame(driver);
				ByAttribute.clearSetText("xpath", SetupObjects.setupRolesPermissionsRoleNameTxt, roleName, "Enter Role Name : "+roleName);
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupRolesPermissionsSearchBtn, "Click Search Button");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupRolesPermissionsRoleChk, "Check the selected Role");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupRolesPermissionsModifyBtn, "Click Modify Button");
				Thread.sleep(20000);
				
				String currentPermissionState="";
				Utility.connectToLatestFrame(driver);
				if(driver.findElements(By.xpath(".//span[text()='"+permissionName+"']/parent::td/input[@type='checkbox']")).size()>0)
				{
					System.out.println("Permission Exists");
					logger.log(LogStatus.PASS, "Permission Exists");
					if(driver.findElements(By.xpath(".//span[text()='"+permissionName+"']/parent::td/input[@type='checkbox' and @checked='checked']")).size()>0)
					{
						currentPermissionState="YES";
					}else{
						currentPermissionState="NO";
					}
				}else{
					System.out.println("Permission Doesn't Exist");
					logger.log(LogStatus.FAIL, "Permission Doesn't Exist");
				}
				
				if(currentPermissionState.equalsIgnoreCase(reqPermissionState))
				{
					System.out.println("$$$$$$$$$ Permission for {"+permissionName+"} is Already in Required State. Changes NOT Required $$$$$$$$$");
					logger.log(LogStatus.INFO, "$$$$$$$$$ Permission for {"+permissionName+"} is Already in Required State. Changes NOT Required $$$$$$$$$");
				}else{
					System.out.println("$$$$$$$$$ Permission for {"+permissionName+"} is NOT in Required State. Changes Are Required $$$$$$$$$");
					logger.log(LogStatus.INFO, "$$$$$$$$$ Permission for {"+permissionName+"} is NOT in Required State. Changes Are Required $$$$$$$$$");
					Thread.sleep(1000);
					JavascriptExecutor js = (JavascriptExecutor) driver;
		            js.executeScript("window.scrollBy(0,700)", "");
					ByAttribute.click("xpath", ".//span[text()='"+permissionName+"']/parent::td/input[@type='checkbox']", "Change the State of "+permissionName+" to - "+reqPermissionState);
					System.out.println("$$$$$$$$$ Permission State for {"+permissionName+"} Changed to {"+reqPermissionState+"} $$$$$$$$$");
					logger.log(LogStatus.INFO, "$$$$$$$$$ Permission State for {"+permissionName+"} Changed to {"+reqPermissionState+"} $$$$$$$$$");
					Thread.sleep(1000);
					ByAttribute.click("xpath", SetupObjects.setupRolesPermissionsSaveBtn, "Click Save Button");
					Thread.sleep(5000);
					Utility.verifyElementPresent(SetupObjects.setupRolesPermissionsRoleModifiedSuccessfullyMsgLbl, "Role Modified Successfully Message", false, false);
				}
				
				driver.switchTo().defaultContent();
				LoginPage.logout();
			}
			catch (Exception e)
			{ 	
				Utility.takeScreenshot("screenshot");
				logger.log(LogStatus.ERROR, "Screenshort of BUGG :" + imgeHtmlPath);	
				throw new java.lang.RuntimeException("--------------->"+ e);	    		
			}
			finally
			{
				driver2.quit();
				driver=default_driver;
				logger.log(LogStatus.PASS, "Switched Back to Guardian Portal");
			}	
		}
	}
	
	
	/**
	* <h1>changeBadgeStatus</h1>
	* This is Method to Change and Validate Badge Status
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-20-2020
	* @param   	String firstName, String changeToBadgeStatus
	* @return  	none
	**/

	public static void changeBadgeStatus(String firstName, String changeToBadgeStatus) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** changeBadgeStatus *********************************");
			logger.log(LogStatus.INFO, "***************************** changeBadgeStatus *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
					
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(10000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
				
				ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
				ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
				Thread.sleep(2000);
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeInfoPodLnk, "Click on Badge Info Pod");
				Thread.sleep(1000);
				
				ByAttribute.click("xpath", ".//span[@name='chk' and @class='ui-state-default ui-corner-all ui-igcheckbox-normal']", "Click Badge ID Checkbox");
				
				switch(changeToBadgeStatus){
				
				case "Confiscated" :
					ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeConfiscateBtn, "Click on Confiscate a Badge Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(BadgingObjects.applicationWizardBadgeConfiscateBadgeLbl, "Confiscate Badge Page Header", false, false);
					ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardBadgeCommentsTxt, "Automation Confiscate", "Enter Comments");
					ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeChangeStatusSaveBtn, "Click Save Button");
					Thread.sleep(30000);
					ByAttribute.click("xpath", BadgingObjects.badgingCreateApplicantInvitationOKBtn, "Click OK Button");
					Thread.sleep(5000);
					Utility.verifyElementPresent(".//td[text()='Confiscated']", "Badge Confiscated Entry in Table", false, false);
					break;
					
				case "Suspended" :
					ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeSuspendBtn, "Click on Suspend a Badge Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(BadgingObjects.applicationWizardBadgeSuspendBadgeLbl, "Suspend Badge Page Header", false, false);
					ByAttribute.clearSetText("xpath", BadgingObjects.applicationWizardBadgeCommentsTxt, "Automation Confiscate", "Enter Comments");
					ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeChangeStatusSaveBtn, "Click Save Button");
					Thread.sleep(30000);
					ByAttribute.click("xpath", BadgingObjects.badgingCreateApplicantInvitationOKBtn, "Click OK Button");
					Thread.sleep(5000);
					Utility.verifyElementPresent(".//td[text()='Suspended']", "Badge Suspended Entry in Table", false, false);
					break;
					
				case "Bulk Recall" :
					break;
					
				default :
					System.out.println("Badge Status Not Identified");
					logger.log(LogStatus.WARNING, "Change To Badge Status Not Identified");
				
				}
				
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	

	/**
	* <h1>runReconciliationJob</h1>
	* This is Method to Run the Reconciliation Job
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-24-2020
	* @param   	String firstName,String badgeType,String badgeValidations
	* @return  	none
	**/

	public static void runReconciliationJob() throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** runReconciliationJob *********************************");
			logger.log(LogStatus.INFO, "***************************** runReconciliationJob *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				
				ByAttribute.click("xpath", SetupObjects.setupTabLnk, "Click on Setup tab");
				Utility.pause(2);
				driver.navigate().refresh();
				Utility.pause(3);
				ByAttribute.click("xpath", SetupObjects.setupLeftMenuManualConfigurationLnk, "Click on Manual Configuration");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupLeftMenuJobSchedulerLnk, "Click on Job Scheduler");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupLeftMenuSchedulerLnk, "Click on Scheduler");
				Thread.sleep(3000);
				Utility.connectToLatestFrame(driver);
				
				for(int k=0;k<5;k++)
				{
					if(driver.findElements(By.xpath(SetupObjects.setupSchedulerCreateNewJobBtn)).size()>0)
					{
						System.out.println("Scheduler Page Loaded Successfully");
						logger.log(LogStatus.PASS, "Scheduler Page Loaded Successfully");
						break;
					}else{
						driver.switchTo().defaultContent();
						ByAttribute.click("xpath", SetupObjects.setupTabLnk, "Click on Setup tab");
						Utility.pause(2);
						ByAttribute.click("xpath", SetupObjects.setupLeftMenuManualConfigurationLnk, "Click on Manual Configuration");
						Thread.sleep(1000);
						ByAttribute.click("xpath", SetupObjects.setupLeftMenuJobSchedulerLnk, "Click on Job Scheduler");
						Thread.sleep(1000);
						ByAttribute.click("xpath", SetupObjects.setupLeftMenuSchedulerLnk, "Click on Scheduler");
						Thread.sleep(3000);
						Utility.connectToLatestFrame(driver);
					}
				}
				ByAttribute.click("xpath", SetupObjects.setupSchedulerCreateNewJobBtn, "Click on Create New Job Button");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupSchedulerJobTypeDdn, "Click on Create New Job Type Dropdown");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//md-option[@ng-value='{\"jobName\":\"Reconciliation Job\",\"jobNameLabel\":\"Reconciliation Job\",\"id\":4,\"jobClass\":\"com.alnt.recon.jobs.ReconciliationJob\"}']", "Select Job Type as AAAE IET LS Sync Job");
				Thread.sleep(2000);
				ByAttribute.clearSetText("xpath", SetupObjects.setupSchedulerJobNameTxt, "AUTO"+Utility.UniqueNumber(4), "Enter Job Name");
				Thread.sleep(500);
				ByAttribute.click("xpath", SetupObjects.setupSchedulerJobParametersMenuBtn, "Click Job Parameters Menu Button");
				Thread.sleep(1000);
				
				ByAttribute.click("xpath", SetupObjects.setupSchedulerReconciliationForDdn, "Click on Reconciliation For Dropdown");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//div[@class='md-text ng-binding' and text()='Users']/parent::md-option", "Select Reconciliation for as Users");
				Thread.sleep(1000);
				ByAttribute.click("xpath", SetupObjects.setupSchedulerReconciliationTriggersDdn, "Click on Reconciliation Triggers Dropdown");
				Thread.sleep(1000);
				
				ByAttribute.click("xpath", SetupObjects.setupSchedulerReconciliationTypeDdn, "Click on Reconciliation Type Dropdown");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//div[@class='md-text ng-binding' and text()='Full Reconciliation']/parent::md-option", "Select Reconciliation Type as Full Reconciliation");
				Thread.sleep(1000);
				
				ByAttribute.click("xpath", SetupObjects.setupSchedulerReconciliationTriggersDdn, "Click on Reconciliation Triggers Dropdown");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//div[@class='md-text ng-binding' and text()='Icon Request Trigger']/parent::md-option", "Select Reconciliation Triggers as Icon Request Trigger");
				Thread.sleep(1000);
				
				ByAttribute.click("xpath", SetupObjects.setupSchedulerSaveJobBtn, "Click Save Job Button");
				Thread.sleep(1000);
				Utility.verifyElementPresent(SetupObjects.setupSchedulerJobScheduledLbl, "Job Sceduled Success Message", false, false);
				driver.switchTo().defaultContent();
					
//				ByAttribute.clickHomeTab();
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}

	
	/**
	* <h1>approveRequestAdmin</h1>
	* This is Method to Approve Request Via Admin Portal
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	06-08-2020
	* @param   	String firstName
	* @return  	none
	**/
	
	public static void approveRequestAdmin(String firstName, String requestType) throws Throwable
	{
		
		if(unhandledException==false)
		{
			System.out.println("******************************** approveRequestAdmin  *****************************************");
			logger.log(LogStatus.INFO, "***************************** approveRequestAdmin *********************************");
			
			WebDriver driver2=Utility.openPrivateBrowser();
			default_driver=driver;
			driver=driver2;
			try
			{
				LoginPage.login("Admin", "System@123");
				
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
				
				for(int i=0;i<5;i++){
					if(driver.findElements(By.xpath(HomeObjects.homeWorkItemsForApprovalTbl)).size()>0)
					{	
						break;
					}else{
						driver.navigate().refresh();
						Thread.sleep(6000);
					}
				}
					
				if(driver.findElements(By.xpath(HomeObjects.homeWorkItemsForApprovalTbl)).size()>0)
				{				
					System.out.println("Work Items Table is available");
					logger.log(LogStatus.PASS, "Work Items Table is available");	
					
					Thread.sleep(2000);
					ByAttribute.click("xpath", ".//td[contains(text(),'"+firstName+"')]/preceding-sibling::td[text()='"+requestType+"']", "Click on Request");
					Utility.pause(10);
					Utility.connectToLatestFrame(driver);
					ByAttribute.click("xpath", HomeObjects.homeApplicantPreEnrollmentApproveBtn, "Click Approve Button");
					Utility.pause(10);
					Utility.verifyElementPresent(".//div[@class='confirm-message-text fabric-confirmpopup-message-success' and contains(text(),'The user has been successfully provisioned.')]", "Request Approved Confirmation Message", false, false);
					ByAttribute.click("xpath", HomeObjects.homeGoBackToInboxBtn, "Click Go Back to Inbox Button");
					
				}
				else
				{
					System.out.println("Navigation to 'Work Items Table' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Work Items Table' Page Failed");
				}
				
			}
			catch (Exception e)
			{ 	
				Utility.takeScreenshot("screenshot");
				logger.log(LogStatus.ERROR, "Screenshort of BUGG :" + imgeHtmlPath);	
				throw new java.lang.RuntimeException("--------------->"+ e);	    		
			}
			finally
			{
				driver2.quit();
				driver=default_driver;
				logger.log(LogStatus.PASS, "Switched Back to Portal");
				driver.navigate().refresh();
			}	
		}
	}


	/**
	* <h1>validateBadgeIcon</h1>
	* This is Method to Validate Badge Icon
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-24-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void validateBadgeIcon(String firstName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** validateBadgeIcon *********************************");
			logger.log(LogStatus.INFO, "***************************** validateBadgeIcon *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
					
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(5000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
				
				ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
				ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
				Thread.sleep(2000);
								
				ByAttribute.click("xpath", BadgingObjects.applicationWizardBadgeInfoPodLnk, "Click on Badge Info Pod");
				Thread.sleep(5000);
				ByAttribute.click("xpath", ".//span[@name='chk' and @class='ui-state-default ui-corner-all ui-igcheckbox-normal']", "Click Badge ID Checkbox");
				ByAttribute.click("xpath", BadgingObjects.applicationWizardPrintBadgeBtn, "Click on Print Badge Button");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//div[@class='templateImages']", "Badge Print Window", true, false);
				Utility.verifyElementPresent(".//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange' and text()='Print']", "Print Button", false, false);
				String iconImage = "";
				switch(AGlobalComponents.airportPriviledge){
				
				case "ASM":
					iconImage = "";
					break;
					
				case "ATC":
					iconImage = "";
					break;
					
				case "AOA_Familiarization":
					iconImage = "svg_10";
					break;
					
				case "RAD_APD":
					iconImage = "";
					break;
					
				case "Aircraft_fueling":
					iconImage = "svg_25";
					break;
					
				case "RAD":
					iconImage = "svg_23";
					break;
					
				case "US_Customs_Seal_Zone2":
					iconImage = "svg_19";
					break;
					
				case "US_Customs_Seal_Zone1":
					iconImage = "svg_22";
					break;
					
				default:
					iconImage = "";
				}
				
				if(driver.findElements(By.xpath(".//*[@id='"+iconImage+"' and not(@display)]")).size()>0)
				{
					Utility.verifyElementPresent(".//*[@id='"+iconImage+"' and not(@display)]", AGlobalComponents.airportPriviledge+" Badge Icon", true, false);
				}else{
					System.out.println("Badge Icons not displayed on Badge for Icon Type : {"+AGlobalComponents.airportPriviledge+"}");
					logger.log(LogStatus.FAIL, "Badge Icons not displayed on Badge for Icon Type : {"+AGlobalComponents.airportPriviledge+"}");
				}
				ByAttribute.click("xpath", ".//div[@class='ui-igdialog ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable']//a[@role='button' and @title='Close']", "Click Close Button on Print Window");
				Thread.sleep(1000);
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	

	/**
	* <h1>changeBackgroundCheckStatus</h1>
	* This is Method to Change Status of Background Check
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	08-04-2020
	* @param   	String firstName, String backgroundCheck, String changeToStatus
	* @return  	none
	**/

	public static void changeBackgroundCheckStatus(String firstName, String backgroundCheck, String changeToStatus) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** changeBackgroundCheckStatus *********************************");
			logger.log(LogStatus.INFO, "***************************** changeBackgroundCheckStatus *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
					
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(10000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
				
				ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
				ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
				Thread.sleep(2000);
			
				ByAttribute.click("xpath", BadgingObjects.applicationWizardBackgroundCheckPodLnk, "Click on Background Check Pod");
				Thread.sleep(2000);
				Utility.connectToLatestFrame(driver);
				Utility.verifyElementPresent(".//h2[@class='ng-binding' and text()='Background Check']", "Background Check Header", false, false);
				Utility.verifyElementPresent(".//span[contains(text(),'STA CHECK')]", "STA CHECK Available", false, false);
				Utility.verifyElementPresent(".//span[contains(text(),'CHRC CHECK')]", "CHRC CHECK Available", false, false);
				
				/** UPDATE STA BACKGROUND CHECK STATUS **/
				ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBackgroundCheckSTABtn, "Click on Update Background Check for STA");
				Thread.sleep(2000);
				ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBGConfirmAndSubmitBtn, "Click on Confirm and Submit Button");
				Thread.sleep(2000);
				Utility.verifyElementPresent(".//span[contains(text(),'STA CHECK')]/ancestor::tr/td[4]//span[contains(text(),'QUEUED')]", "STA CHECK Status Changed to QUEUED", false, false);
				
				/** UPDATE CHRC BACKGROUND CHECK STATUS **/
				ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBackgroundCheckCHRCBtn, "Click on Update Background Check for CHRC");
				Thread.sleep(2000);
				ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBGConfirmAndSubmitBtn, "Click on Confirm and Submit Button");
				Thread.sleep(4000);
				Utility.verifyElementPresent(".//span[contains(text(),'CHRC CHECK')]/ancestor::tr/td[4]//span[contains(text(),'QUEUED')]", "CHRC CHECK Status Changed to QUEUED", false, false);
				
				if(backgroundCheck.equalsIgnoreCase("STA"))
				{
					ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBackgroundCheckSTABtn, "Click on Update Background Check for STA");
					Thread.sleep(1000);
				}else if(backgroundCheck.equalsIgnoreCase("CHRC")){
					ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBackgroundCheckCHRCBtn, "Click on Update Background Check for CHRC");
					Thread.sleep(1000);
					ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateManuallyBtn, "Click on Update Manually");
					Thread.sleep(2000);
					ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBackgroundCheckStatusDdn, "Select value from Update Background Check Status");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//div[@class='md-text ng-binding' and text()='"+changeToStatus+"']", "Select Status as : "+changeToStatus);
					Thread.sleep(1000);
					ByAttribute.click("xpath", BadgingObjects.applicationWizardUpdateBackgroundCheckSaveBtn, "Click Save Button");
					Thread.sleep(3000);
					if(changeToStatus.equalsIgnoreCase("SUBMIT ERROR"))
					{
						Utility.verifyElementNotPresent(".//span[@class='ng-binding ng-scope' and contains(text(),'CHRC CHECK')]", "CHRC Entry in Table", false);
					}else{
						Utility.verifyElementPresent(".//span[contains(text(),'CHRC CHECK')]/ancestor::tr/td[4]//span[contains(text(),'REPRINT UNCLASSIFIABLE')]", "CHRC CHECK Status Changed to REPRINT UNCLASSIFIABLE", false, false);
					}
				}
				
				driver.switchTo().defaultContent();
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}

	
	/**
	* <h1>reDOFingerprints</h1>
	* This is Method to Update Fingerprints
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	08-04-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void reDOFingerprints(String firstName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** reDOFingerprints *********************************");
			logger.log(LogStatus.INFO, "***************************** reDOFingerprints *********************************");
			try
			{
				ByAttribute.clickHomeTab();
				Thread.sleep(1000);
					
				ByAttribute.click("xpath", BadgingObjects.badgingTabLnk, "Click on Badging tab");
				Utility.pause(3);
				driver.navigate().refresh();
				Utility.pause(5);
				ByAttribute.clickOnPod("Manage Applications");
				Thread.sleep(3000);
				ByAttribute.click("xpath", BadgingObjects.badgingApplicationSearchLnk, "Click on Application Search Link");
				Thread.sleep(10000);
				ByAttribute.clearSetText("xpath", ".//*[@title='UserId or employer or first name or last name']", firstName, "Enter First Name");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[contains(@id,'ui-id')]/div[@class='description']/span[text()='"+firstName+"']", "Click User");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Active Application Status", false, true);
				
				ByAttribute.click("xpath", ".//tbody/tr/td[text()='"+firstName+"']//parent::tr/td[text()='Active']", "Click on the Application");
				ByAttribute.click("xpath", BadgingObjects.searchApplicationsContinueBtn, "Click on Continue Button");
				Thread.sleep(10000);
				
				ByAttribute.click("xpath", BadgingObjects.applicationWizardBioMetricsPodLnk, "Click on Bio Metrics Pod");
				String fingerPrints = System.getProperty("user.dir") + "\\Browser_Files\\fingerprint.eft";
				Thread.sleep(2000);
				driver.findElement(By.xpath(".//input[@type='file' and @accept='.eft']")).sendKeys(fingerPrints);
				Thread.sleep(30000);
				logger.log(LogStatus.INFO,"Upload Finger Prints File");
				System.out.println("Upload Finger Prints File");
				Utility.verifyElementPresent(BadgingObjects.applicationWizardSavedSuccessfullyMessageLbl, "Fingerprint uploaded successfully", false, false);
				Thread.sleep(1000);
				ByAttribute.click("xpath", BadgingObjects.applicationWizardNextBtn, "Click on Next Button");
				Thread.sleep(2000);
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}

	
	/**
	* <h1>validatePrintedVersionCheckbox</h1>
	* This is Method to Validate Printed Version Checkbox Functionality
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	08-06-2020
	* @param   	String firstName
	 * @return 
	* @return  	none
	**/

	public static int validatePrintedVersionCheckbox(String badgeID,String dbIP,String username,String password,String dbName,String typeRDBMS) throws Exception{
		
		int recordCount = 0;
		
		if(unhandledException==false)
		{
			System.out.println("******************************** validatePrintedVersionCheckbox  *****************************************");
			logger.log(LogStatus.INFO, "***************************** validatePrintedVersionCheckbox *********************************");
	
			try
			{
		    	//SQL Select Query to Validate Record Created.
		    	String query =  "select COUNT(*) from AAXT_USER_BDG_HISTORY where BADGE_ID ='"+badgeID+"'";
		    	Connection con = null;
		    	if(typeRDBMS.equalsIgnoreCase("SQLSERVER"))
		    	{
		    		//Connection URL Syntax: "jdbc:sqlserver://ipaddress"		
		    	    String dbUrl = "jdbc:sqlserver://"+dbIP;
		    	    
		    	    //Load sqlserver jdbc driver
			    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			    	
			    	//Create Connection to DB
			    	con = DriverManager.getConnection(dbUrl+";user="+username+";password="+password+";database="+dbName);
		    	}
		    	else if(typeRDBMS.equalsIgnoreCase("MYSQL")){
		    		//Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"		
		    	    String dbUrl = "jdbc:mysql://"+dbIP+":3306/"+dbName;		
		    	    
		    	    //Load mysql jdbc driver	
		    	    Class.forName("com.mysql.jdbc.Driver");			
		
		    		//Create Connection to DB		
		    		con = DriverManager.getConnection(dbUrl,username,password);
		    	}
		    	
		    	Statement stmt = con.createStatement();
		
		    	//Executing SQL SELECT query using executeQuery()  method of Statement object.
		    	ResultSet rs = stmt.executeQuery(query);
		    	rs.next();
		    	//Get total number of Records from the Table
		    	recordCount = rs.getInt(1);
		    	
		    	//Close the connection
		    	con.close();
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
			
		}
		
		return recordCount;
		
	}
    
	
	/**
	* <h1>submitBadgeRenewal</h1>
	* This is Method to Modify an Applicant Invite via AS Portal
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	08-12-2020
	* @param   	String firstName
	* @return  	none
	**/

	public static void submitBadgeRenewal(String firstName) throws Throwable
	{

		if(unhandledException==false)
		{
			System.out.println("***************************** submitBadgeRenewal *********************************");
			logger.log(LogStatus.INFO, "***************************** submitBadgeRenewal *********************************");
			try
			{
				JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,700)", "");
				ByAttribute.clickOnSelfServicePod("Applicant Invitation");
				Thread.sleep(2000);
				List<WebElement> initiateBadgeRenewalButtonElements = driver.findElements(By.xpath(".//a[text()='Initiate Badge Renewal Application ']"));
				WebElement initiateBadgeRenewalButton = initiateBadgeRenewalButtonElements.get(0);
				
				initiateBadgeRenewalButton.click();
				logger.log(LogStatus.INFO, "Click Initiate Badge Renewal Button");
				Thread.sleep(3000);

				ByAttribute.clearSetText("xpath", ".//input[@placeholder='Search By User Id or First Name or Last Name']", firstName, "Enter First Name in Search Field");
				Thread.sleep(500);
				ByAttribute.click("xpath", ".//button[@class='custom-searchIcon mat-icon-button mat-button-base']", "Click Search Button");
				Thread.sleep(1000);
				
				ByAttribute.click("xpath", ".//p[@class='custom-grid-display-content' and text()='"+firstName+"']/ancestor::mat-row//input[@type='checkbox']", "Check Applicant Checkbox");
				System.out.println("Selected Applicant for Badge Renewal");
				logger.log(LogStatus.PASS, "Selected Applicant for Badge Renewal");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-button-wrapper' and contains(text(),'ontinue')]", "Click Continue Button");
				Thread.sleep(3000);
				ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationSSNConfirmationTxt, AGlobalComponents.applicantSSN, "Enter SSN for Confirmation");
				Thread.sleep(500);
				ByAttribute.click("xpath", SelfServiceObjects.selfServiceModifyApplicantSubmitBtn, "Click Submit Button");
				Thread.sleep(10000);
				ByAttribute.click("xpath", ".//span[@class='mat-button-wrapper' and text()='Ok']/parent::button", "Click Ok Button");
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	
	/**
	* <h1>selfServiceMyApplicationsBadgeRenewal</h1>
	* This is Method to Complete the Application for through Self Service Portal
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	08-12-2020
	* @param   	String userID, String password
	* @return  	none
	**/
	
	public static void selfServiceMyApplicationsBadgeRenewal(String username, String password) throws Throwable
	{
		
		if(unhandledException==false)
		{
			System.out.println("******************************** selfServiceMyApplicationsBadgeRenewal  *****************************************");
			logger.log(LogStatus.INFO, "***************************** selfServiceMyApplicationsBadgeRenewal *********************************");
			WebDriver driver2=Utility.openPrivateBrowser();
			default_driver=driver;
			driver=driver2;
			try
			{
				driver2.get(AGlobalComponents.selfServicePortalURL);
				Utility.pause(2);
				driver2.findElement(By.xpath(LoginObjects.selfServiceSigninWithOKTABtn)).click();
				Utility.pause(3);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTAUsernameTxt)).sendKeys(username);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTAPasswordTxt)).sendKeys(password);
				driver2.findElement(By.xpath(LoginObjects.selfServiceOKTASigninBtn)).click();
				Utility.pause(15);
				logger.log(LogStatus.PASS, "Logged In to Self Service Portal");
				ByAttribute.clickOnSelfServicePod("My Applications");
				Utility.pause(2);
				Utility.verifyElementPresent(".//div/p[text()='Draft']", "Application Status - Draft", false, true);
				ByAttribute.click("xpath", ".//app-application-grid-actions/button[@class='mat-icon-button mat-button-base']", "Cick on Edit Icon");
				Utility.pause(4);
				try{
					ByAttribute.click("xpath", ".//*[@id='Citizenship_Type']", "Click Citizenship Type");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'US Citizen')]", "Select Citizenship Type");
					Thread.sleep(1000);
				}catch (Exception e){
					System.out.println("Citizenship Dropdown is Disabled/Unavailable");
					logger.log(LogStatus.INFO, "Citizenship Dropdown is Disabled/Unavailable");
				}
				ByAttribute.click("xpath", ".//*[@id='Country_of_birth_telos']", "Click Country of Birth");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' UNITED STATES ']", "Select Country of Birth");
				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//mat-select[@id='pobState']", "Click State of Birth");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Arizona')]", "Select State of Birth");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//*[@id='Citizenship']//div[@class='mat-select-value']", "Click Nationality");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' UNITED STATES ']", "Select Nationality");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", SelfServiceObjects.selfServiceCreateApplicantInvitationBadgeValidToTxt, "Enter Badge Valid To Date");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//div[@class='mat-calendar-body-cell-content mat-calendar-body-today']", "Select today");
//				Thread.sleep(1000);
//				ByAttribute.clearSetText("xpath", ".//input[@id='Height']", "510", "Enter Height");
//				ByAttribute.clearSetText("xpath", ".//input[@id='Weight']", "150", "Enter Weight");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//mat-select[@id='HairColor']", "Click Hair Color");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Black')]", "Select Hair Color");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//mat-select[@id='EyeColor']", "Click Eye Color");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Brown')]", "Select Eye Color");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//mat-select[@id='Sex']", "Click Gender");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Male')]", "Select Gender");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//mat-select[@id='Race']", "Click Race");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Asian')]", "Select Race");
//				Thread.sleep(1000);
//				ByAttribute.clearSetText("xpath", ".//input[@id='Home Address Line 1']", "13-B Locker Street", "Enter Address");
//				ByAttribute.clearSetText("xpath", ".//input[@id='City']", "Arizona", "Enter City");
//				ByAttribute.click("xpath", ".//*[@id='Address_State_telos']", "Click State");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Arizona')]", "Select State");
//				Thread.sleep(2000);
//				ByAttribute.click("xpath", ".//*[@id='Country_telos']", "Click Country");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and text()=' UNITED STATES ']", "Select Country");
//				ByAttribute.clearSetText("xpath", ".//*[@id='Postal Code']", "102987", "Enter Postal Code");
//				ByAttribute.clearSetText("xpath", ".//*[@id='Contact Phone']", "6754327809", "Enter Contact Number");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//*[@id='ISAuthorizedSigner']", "Click IS Authorized Signer");
//				Thread.sleep(1000);
//				ByAttribute.click("xpath", ".//span[@class='mat-option-text' and contains(text(),'Yes')]", "Select Yes");
				
				for(int i=2162;i<=2267;i=i+3)
				{
					ByAttribute.click("xpath", ".//*[@id='mat-radio-"+i+"']//div[@class='mat-radio-container']", "Select Criminal Record as NO");
					Thread.sleep(500);

				}

				ByAttribute.click("xpath", ".//*[@id='AuthorizeSSN']", "Authorize SSN Check-Box");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[@id='VerifySSN']", "Validate SSN Check-Box");
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//*[@id='BadgeHolder_certify']", "Badge Holder Certify Check-Box");
				
				ByAttribute.click("xpath", ".//button/span[contains(text(),'Submit')]", "Click on Submit Button");
				Utility.pause(2);
				ByAttribute.click("xpath", ".//button/span[text()='Ok']", "Click on Ok Button");
				
				logger.log(LogStatus.PASS, "Successfully: Applicant Self Service Request Update");
				System.out.println("Successfully: Applicant Self Service Request Update");
			}
			catch (Exception e)
			{ 	
				Utility.takeScreenshot("screenshot");
				logger.log(LogStatus.ERROR, "Screenshort of BUGG :" + imgeHtmlPath);	
				throw new java.lang.RuntimeException("--------------->"+ e);	    		
			}
			finally
			{
				driver2.quit();
				driver=default_driver;
				logger.log(LogStatus.PASS, "Switched Back to Guardian Portal");
			}	
		}
	}


	
	
	public static void contractAttachmentUpload(String file)throws Throwable
	{
		
		if(unhandledException==false)
		{
			System.out.println("***************************** contractAttachmentUpload *********************************");
			logger.log(LogStatus.INFO, "***************************** contractAttachmentUpload *********************************");
			try {
				
				ByAttribute.click("xpath", ".//*[@id='"+file+"']//parent::div//a[@class='ae-note-attachment-link']", "Click attachment link for"+file);
				Thread.sleep(1000);
				int n;
				
				switch (file){
					case "LOI":
						n=1;
						break;
					case "LOV":
						n=2;
						break;
					case "BRTC":
						n=3;
						break;
					default:
						n=4;
						break;
				}
				
				
				List <WebElement> attachmentElements=driver.findElements(By.xpath(".//*[@id='attachGrid']/mat-toolbar//button[1]"));
				
				WebElement attachment=attachmentElements.get(n);
				attachment.click();
				
				/*
				
				YOUR CODE HERE.............
				
				*/
		
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	

}