package CommonFunctions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.ByAttribute;
import CommonClassReusables.ByAttributeAngular;
import CommonClassReusables.DBValidations;
import CommonClassReusables.MsSql;
import CommonClassReusables.Utility;
import ObjectRepository.AccessObjects;
import ObjectRepository.AdminObjects;
import ObjectRepository.AssetObjects;
import ObjectRepository.HomeObjects;
import ObjectRepository.IdentityObjects;
import ObjectRepository.SelfServiceObjects;


public class Self_Service_CommonMethods extends BrowserSelection{
	
	private static int assignmentStatusIndex,accessIndex,actionIndex,assetCodeIndex = 0,badgeaccessIndex=0,systemIndex,modifiedPhoneNumber=0;
	private static String oldAssetStatus = null,employeeType="",newAssetStatus=null,badgeStatusInRequest=null,reqNum="",requestorName="",assetStatusBeforeOffboarding="",oldPhotoSrc=null,modifiedLastName="",locationName="";
	private static ArrayList<String> contractorAccessNames = new ArrayList<String>();
	private static ArrayList<String> permanentAccessNames = new ArrayList<String>();
	private static String access1ForTempOnboarding = "Server Room" , access2ForTempOnboarding = "End User Role",access3ForTempOnboarding="Common_Area_Access";
	private static String access1ForPermanentEmp = "SC CHRWLS NONR CONST GATE" , access2ForPermanentEmp = "SC LEEXSS NONR GENERAL ACCESS";
	private static String system2ForTempOnboarding = "CCURE 9000",system1ForTempOnboarding = "Database Connector",departmentName="Finance";
	private static String access1ForEmpOnboarding,access2ForEmpOnboarding,accessNewForChangeJobTitle,jobTitle,system1ForEmpOnboarding,system2ForEmpOnboarding;
	private static ArrayList<String> accessesAssignedToUser = new ArrayList<String>();
	private static ArrayList<String> systemsAssignedToUser = new ArrayList<String>();
	private static File fileInput ;
	private static File fileOutPut ;

	
	/**
	 * <h1>createAccessRequest</h1> 
	 * This is Method to Create an Access Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 12-16-2020
	 * @param String requestType
	 * @return String
	 **/

	public static String createAccessRequest(String requestType, String location, String access, String locCity, String locBuilding) throws Throwable {

		String reqNum="";
		if (unhandledException == false) {
			System.out.println("***************************** createAccessRequest *********************************");
			logger.log(LogStatus.INFO,"***************************** createAccessRequest *********************************");
			try {

				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
					Utility.pause(5);
				}

				Utility.handleAnnouncementPopup();
				
				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) {
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//label[text()='"+requestType+"']//parent::div//input[@type='radio']", "Click "+requestType+" Radio Button");
					Thread.sleep(2000);
					
					if(requestType.equals("Physical Access") || requestType.equals("Request Location Access"))
					{
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestSelfRdb, "Click on Self Radio Button");
						try{
							ByAttribute.click("xpath", "//*[@data-ref='btnInnerEl' and text()='Yes']", "Click Yes Warning Button");
						}catch(Exception e){
							System.out.println("Warning Popup Not Found");
						}
						Thread.sleep(1000);
						ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchLocationTxt, locCity, "Search Location by City");
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+location+"') and contains(text(),'"+location+"')]", "Searched Location by City", false);
						
						Thread.sleep(1000);
						ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchLocationTxt, locBuilding, "Search Location by Building");
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+location+"') and contains(text(),'"+location+"')]", "Searched Location by Building", false);
						
						Thread.sleep(1000);
						ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchLocationTxt, location, "Search Location by Location Name");
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+location+"') and contains(text(),'"+location+"')]", "Searched Location by Location Name", false);
						
						ByAttribute.click("xpath", ".//*[contains(@data-qtip,'"+location+"') and contains(text(),'"+location+"')]", "Click Location: "+location);
						Thread.sleep(1000);
						try{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
						}catch(Exception e){
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateAddBtn, "Click Add Button");
						}
						Thread.sleep(1000);
						if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestSearchAccessTxt)).size() > 0)
						{
							System.out.println("Navigation to Access Page Successful");
							logger.log(LogStatus.INFO, "Navigation to Access Page Successful");

							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchAccessTxt, "", "Clear Search Filter");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']//ancestor::div[@class='innerWidget']", "Add Location");
							
							List<WebElement> locationAddButtonElements = driver.findElements(By.xpath(".//div[@class='innerWidget' and contains(@data-boundview,'baseAccessLocationBox')]"));
							WebElement locationAddButton = locationAddButtonElements.get(0);

							locationAddButton.click();
							logger.log(LogStatus.INFO, "Add 2nd Location");
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreatePreviousBtn, "Click Previous Button");
							Thread.sleep(1000);
							try{
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
							}catch(Exception e){
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateAddBtn, "Click Add Button");
							}
							Thread.sleep(2000);

							if(driver.findElements(By.xpath(".//div[@class='aegrid-active2']")).size()>0)
							{
								System.out.println("Selected Access is still Added");
								logger.log(LogStatus.PASS, "Selected Access is still Added");
							}else{
								System.out.println("Selected Access is Removed");
								logger.log(LogStatus.FAIL, "Selected Access is Removed");
							}
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationCancelBtn, "Click Cancel Button");
							Utility.verifyElementPresent(HomeObjects.homeAccessRequestAddLocationChangesWillBeLostMsgLbl, "You request changes will lost, do you want to proceed? Warning Message", false);
							ByAttribute.click("xpath", "//*[@data-ref='btnInnerEl' and text()='No']", "Click No Warning Button");
							Thread.sleep(2000);
							
							if(driver.findElements(By.xpath(".//div[@class='aegrid-active2']")).size()>0)
							{
								System.out.println("Selected Access is still Added");
								logger.log(LogStatus.PASS, "Selected Access is still Added");
							}else{
								System.out.println("Selected Access is Removed");
								logger.log(LogStatus.FAIL, "Selected Access is Removed");
							}
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationCancelBtn, "Click Cancel Button");
							Utility.verifyElementPresent(HomeObjects.homeAccessRequestAddLocationChangesWillBeLostMsgLbl, "You request changes will lost, do you want to proceed? Warning Message", false);
							ByAttribute.click("xpath", "//*[@data-ref='btnInnerEl' and text()='Yes']", "Click Yes Warning Button");
							Thread.sleep(1000);
							
							Utility.verifyElementPresent(".//label[text()='Select Request Type']", "Select Request Type Page", false);
							Thread.sleep(1000);
							
//							Add Request Access Again
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestSelfRdb, "Click on Self Radio Button");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//label[text()='"+requestType+"']//parent::div//input[@type='radio']", "Click "+requestType+" Radio Button");
							Thread.sleep(2000);
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchLocationTxt, location, "Search Location");
							Thread.sleep(500);
							ByAttribute.click("xpath", ".//*[contains(@data-qtip,'"+location+"') and contains(text(),'"+location+"')]", "Click Location: "+location);
							Thread.sleep(1000);
							try{
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
							}catch(Exception e){
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateAddBtn, "Click Add Button");
							}
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']//ancestor::div[@class='innerWidget']", "Add Location");
							
							locationAddButtonElements = driver.findElements(By.xpath(".//div[@class='innerWidget' and contains(@data-boundview,'baseAccessLocationBox')]"));
							locationAddButton = locationAddButtonElements.get(0);

							locationAddButton.click();
							logger.log(LogStatus.INFO, "Add 2nd Location");
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestReviewTab, "Click on Review Tab");
							Utility.verifyElementPresent(".//tbody//td[2]/div[text()='"+access+"']", "Added Access: "+access, false);
							
							ByAttribute.click("xpath", ".//tbody//td[2]/div[text()='"+access+"']//ancestor::table//following-sibling::table//div[@data-qtip='Delete Row']", "Click Delete Row for 2nd Access");
							Thread.sleep(501);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "Click Comments Button");
							Thread.sleep(1000);
							
							Robot robot = new Robot();
							
							robot.keyPress(KeyEvent.VK_TAB);
							robot.keyRelease(KeyEvent.VK_TAB);
							Thread.sleep(500);
							robot.keyPress(KeyEvent.VK_TAB);
							robot.keyRelease(KeyEvent.VK_TAB);
							Thread.sleep(500);
							robot.keyPress(KeyEvent.VK_A);
							robot.keyPress(KeyEvent.VK_U);
							robot.keyPress(KeyEvent.VK_T);
							robot.keyPress(KeyEvent.VK_O);
							
							robot.keyRelease(KeyEvent.VK_A);
							robot.keyRelease(KeyEvent.VK_U);
							robot.keyRelease(KeyEvent.VK_T);
							robot.keyRelease(KeyEvent.VK_O);

							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
							Thread.sleep(2000);
							Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Add Comment']", "Add Comment", false);
							ByAttribute.click("xpath", "(.//div[@data-qtip='Close Dialog']/div)[2]", "Click Close Dialog button");
							Thread.sleep(1000);														
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAttachmentsBtn, "Click Attachments Button");
							Thread.sleep(2000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestUploadAttachmentBtn, "Cick Upload Attachment Button");
							Thread.sleep(3000);
							
							String uploadFile = System.getProperty("user.dir") + "\\Browser_Files\\Applicant_Photo.jpg";
							StringSelection ss = new StringSelection(uploadFile);
				            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

				            robot.keyPress(KeyEvent.VK_CONTROL);
				            robot.keyPress(KeyEvent.VK_V);
				            robot.keyRelease(KeyEvent.VK_V);
				            robot.keyRelease(KeyEvent.VK_CONTROL);
				            robot.keyPress(KeyEvent.VK_ENTER);
				            robot.keyRelease(KeyEvent.VK_ENTER);
							
				            Thread.sleep(2000);
				            Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Attachments']", "Attachment", false);
				            ByAttribute.click("xpath", "(.//div[@data-qtip='Close Dialog']/div)[2]", "Click Close Dialog button");
				            Thread.sleep(1000);
							
							DateFormat dateFormat = new SimpleDateFormat("M/d/yy hh:mm a");
							Date currentDate = new Date();
					        System.out.println(dateFormat.format(currentDate));

					        // convert date to calendar
					        Calendar c = Calendar.getInstance();
					        c.setTime(currentDate);

					        // manipulate date
					        c.add(Calendar.MONTH, 1);
					        
					        // convert calendar to date
					        Date currentDatePlusOne = c.getTime();
					        AGlobalComponents.validToDate = currentDatePlusOne;
					        String validTo = new SimpleDateFormat("M/d/yy h:mm a").format(currentDatePlusOne);
							
					        Actions action = new Actions(driver);
					        WebElement validToDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[2]"));
							action.moveToElement(validToDate).click();
							action.build().perform();
							Thread.sleep(1000);
							
							StringSelection toDate = new StringSelection(validTo);
				            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(toDate, null);

				            robot.keyPress(KeyEvent.VK_CONTROL);
				            robot.keyPress(KeyEvent.VK_V);
				            robot.keyRelease(KeyEvent.VK_V);
				            robot.keyRelease(KeyEvent.VK_CONTROL);
				            robot.keyPress(KeyEvent.VK_TAB);
				            robot.keyRelease(KeyEvent.VK_TAB);
							Utility.pause(2);
							logger.log(LogStatus.INFO, "Entered valid to Date as: {"+validTo+"}");
							Thread.sleep(1000);
							String editedValidToDate = new SimpleDateFormat("M/d/YY").format(currentDatePlusOne);
							Utility.verifyElementPresent(".//tbody//div[contains(text(),'"+editedValidToDate+"')]", "Valid To Date Edited", false);
							
							ByAttribute.click("xpath", ".//tbody//div[@role='button' and @data-qtip='Comments']", "Click Access Level Comments Button");
							Thread.sleep(1000);
							
							robot.keyPress(KeyEvent.VK_TAB);
							robot.keyRelease(KeyEvent.VK_TAB);
							Thread.sleep(500);
							robot.keyPress(KeyEvent.VK_TAB);
							robot.keyRelease(KeyEvent.VK_TAB);
							Thread.sleep(500);
							robot.keyPress(KeyEvent.VK_A);
							robot.keyPress(KeyEvent.VK_C);
							robot.keyPress(KeyEvent.VK_E);
							robot.keyPress(KeyEvent.VK_S);
							
							robot.keyRelease(KeyEvent.VK_A);
							robot.keyRelease(KeyEvent.VK_C);
							robot.keyRelease(KeyEvent.VK_E);
							robot.keyRelease(KeyEvent.VK_S);

							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
							Thread.sleep(2000);
							Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Add Comment']", "Add Comment", false);
							ByAttribute.click("xpath", "(.//div[@data-qtip='Close Dialog']/div)[2]", "Click Close Dialog button");
							Thread.sleep(1000);
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationSubmitBtn, "Click Submit Button");
							Thread.sleep(2000);
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationReasonTxt, "General Access Required", "Enter Reason Code");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//li[text()='General Access Required']", "Select Reason Option");
							Thread.sleep(1000);
							if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestAddLocationGroupNameTxt)).size()>0)
							{
								ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationGroupNameTxt, "mca-crar-group1", "Enter Group Name");
								Thread.sleep(500);
								ByAttribute.click("xpath", ".//li[@role='option' and text()='mca-crar-group1']", "Select Option");
							}else if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestAddLocationBusinessJustificationTxt)).size()>0){
								ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationBusinessJustificationTxt, "Automation Test", "Enter Business Justification");
							}
							Thread.sleep(1000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationConfirmBtn, "Click Confirm Button");
							Thread.sleep(10000);
							
							List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
							WebElement latestRequestNumber = requestNumberElements.get(0);
							
							reqNum = latestRequestNumber.getText();
						}else{
							System.out.println("Navigation to Access Page NOT Successful");
							logger.log(LogStatus.FAIL, "Navigation to Access Page NOT Successful");
						}
					}
					

				} else {
					System.out.println("Navigation to 'Access Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Access Request' Page Failed");
				}

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}

		return reqNum;
	}

	
	/**
	* <h1>createAccessRequestOthers</h1>
	* This is Method to Create an Access Request
	* @author Jiten Khanna
	* @modified
	* @version 1.0
	* @since 2-11-2021
	* @param String requestType
	* @return String
	**/

	public static String createAccessRequestOthers(String requestType, String system, String access, String otherUser) throws Throwable {

	String reqNum="";
	if (unhandledException == false) {
	System.out.println("**************************** createAccessRequestOthers ********************************");
	logger.log(LogStatus.INFO,"**************************** createAccessRequestOthers ********************************");
	try {

	if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
	{
	ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
	Utility.pause(5);
	}else{
	ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
	Thread.sleep(1000);
	ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
	Utility.pause(5);
	}

	Utility.handleAnnouncementPopup();

	if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) {
	System.out.println("Access Request Page Loaded Successfully");
	logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");

	ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
	Thread.sleep(1000);

	ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
	Thread.sleep(1000);

	if(driver.findElements(By.xpath(".//label[text()='"+requestType+"']//parent::div//input[@type='radio']")).size()>0)
	{
	ByAttribute.click("xpath", ".//label[text()='"+requestType+"']//parent::div//input[@type='radio']", "Click "+requestType+" Radio Button");
	Thread.sleep(2000);
	}else{
	ByAttribute.click("xpath", ".//label[text()='Request Type']//following-sibling::div//label[text()='Others']//parent::div//input[@type='radio']", "Click Others Radio Button");
	Thread.sleep(2000);
	ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSelectRequestTypeTxt, requestType, "Enter Request Type");
	Thread.sleep(1000);
	ByAttribute.click("xpath", ".//li[@role='option' and text()='"+requestType+"']", "Select Request Type as: "+requestType);
	Thread.sleep(2000);
	}

	ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSearchUserTxt, otherUser, "Enter Other User");
	Thread.sleep(1000);
	ByAttribute.click("xpath", ".//span[contains(text(),'"+otherUser+"')]", "Select User "+otherUser+" from List");
	Thread.sleep(2000);

	Utility.verifyElementPresent("(.//*[contains(@data-qtip,'"+system+"') and contains(text(),'"+system+"')])[1]", "Searched IT System Name: "+system, false);

	ByAttribute.click("xpath", "(.//*[contains(@data-qtip,'"+system+"') and contains(text(),'"+system+"')])[1]", "Click System: "+system);
	Thread.sleep(1000);
	try{
	ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
	}catch(Exception e){
	ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateAddBtn, "Click Add Button");
	}
	Thread.sleep(1000);
	if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestAddLocationSubmitBtn)).size() > 0)
	{
	System.out.println("Navigation to Access Page Successful");
	logger.log(LogStatus.INFO, "Navigation to Access Page Successful");

	if(requestType.equals("IT Access"))
	{
	ByAttribute.click("xpath", HomeObjects.homeAccessRequestSearchITAccessLbl, "Search Access in List");
	Thread.sleep(2000);
	ByAttribute.click("xpath", ".//span[text()='"+access+"']", "Select Access: "+access+" from List");
	Thread.sleep(1000);
	}else if(requestType.equals("Application Access")){
	ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']//ancestor::div[@class='innerWidget']", "Add Location");
	Thread.sleep(1000);
	}

	ByAttribute.click("xpath", HomeObjects.homeAccessRequestReviewTab, "Click on Review Tab");
	if(requestType.equals("IT Access"))
	{
	Utility.verifyElementPresent("(.//tbody//td[2]/div[text()='"+access+"'])[2]", "Added Access: "+access, false);
	}else if(requestType.equals("Application Access")){
	Utility.verifyElementPresent(".//tbody//td[2]/div[text()='"+access+"']", "Added Access: "+access, false);
	}

	ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "Click Comments Button");
	Thread.sleep(1000);

	Robot robot = new Robot();

	robot.keyPress(KeyEvent.VK_TAB);
	robot.keyRelease(KeyEvent.VK_TAB);
	Thread.sleep(500);
	robot.keyPress(KeyEvent.VK_TAB);
	robot.keyRelease(KeyEvent.VK_TAB);
	Thread.sleep(500);
	robot.keyPress(KeyEvent.VK_A);
	robot.keyPress(KeyEvent.VK_U);
	robot.keyPress(KeyEvent.VK_T);
	robot.keyPress(KeyEvent.VK_O);

	robot.keyRelease(KeyEvent.VK_A);
	robot.keyRelease(KeyEvent.VK_U);
	robot.keyRelease(KeyEvent.VK_T);
	robot.keyRelease(KeyEvent.VK_O);

	ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
	Thread.sleep(2000);
	Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Add Comment']", "Add Comment", false);
	if(requestType.equals("IT Access"))
	{
	ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
	}else if(requestType.equals("Application Access")){
	// ByAttribute.click("xpath", "(.//div[@data-qtip='Close Dialog']/div)[2]", "Click Close Dialog button");
	ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
	}
	Thread.sleep(1000);

	ByAttribute.click("xpath", HomeObjects.homeAccessRequestAttachmentsBtn, "Click Attachments Button");
	Thread.sleep(2000);
	ByAttribute.click("xpath", HomeObjects.homeAccessRequestUploadAttachmentBtn, "Cick Upload Attachment Button");
	Thread.sleep(3000);

	String uploadFile = System.getProperty("user.dir") + "\\Browser_Files\\Applicant_Photo.jpg";
	StringSelection ss = new StringSelection(uploadFile);
	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

	robot.keyPress(KeyEvent.VK_CONTROL);
	robot.keyPress(KeyEvent.VK_V);
	robot.keyRelease(KeyEvent.VK_V);
	robot.keyRelease(KeyEvent.VK_CONTROL);
	robot.keyPress(KeyEvent.VK_ENTER);
	robot.keyRelease(KeyEvent.VK_ENTER);

	Thread.sleep(5000);
	Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Attachments']", "Attachment", false);
	if(requestType.equals("IT Access"))
	{
	ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
	}else if(requestType.equals("Application Access")){
	// ByAttribute.click("xpath", "(.//div[@data-qtip='Close Dialog']/div)[2]", "Click Close Dialog button");
	ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
	}
	Thread.sleep(1000);

	DateFormat dateFormat = new SimpleDateFormat("M/d/yy hh:mm a");
	Date currentDate = new Date();
	System.out.println(dateFormat.format(currentDate));

	// convert date to calendar
	Calendar c = Calendar.getInstance();
	c.setTime(currentDate);

	// manipulate date
	c.add(Calendar.MONTH, 1);

	// convert calendar to date
	Date currentDatePlusOne = c.getTime();
	AGlobalComponents.validToDate = currentDatePlusOne;
	String validTo = new SimpleDateFormat("M/d/yy h:mm a").format(currentDatePlusOne);

	Actions action = new Actions(driver);
	if(requestType.equals("IT Access"))
	{
	WebElement validToDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[4]"));
	action.moveToElement(validToDate).click();
	action.build().perform();
	}else if(requestType.equals("Application Access")){
	WebElement validToDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[2]"));
	action.moveToElement(validToDate).click();
	action.build().perform();
	}

	Thread.sleep(1000);

	StringSelection toDate = new StringSelection(validTo);
	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(toDate, null);

	robot.keyPress(KeyEvent.VK_CONTROL);
	robot.keyPress(KeyEvent.VK_V);
	robot.keyRelease(KeyEvent.VK_V);
	robot.keyRelease(KeyEvent.VK_CONTROL);
	robot.keyPress(KeyEvent.VK_TAB);
	robot.keyRelease(KeyEvent.VK_TAB);
	Utility.pause(2);
	logger.log(LogStatus.INFO, "Entered valid to Date as: {"+validTo+"}");
	Thread.sleep(1000);
	String editedValidToDate = new SimpleDateFormat("M/d/YY").format(currentDatePlusOne);

	if(requestType.equals("IT Access"))
	{
	Utility.verifyElementPresent("(.//tbody//div[contains(text(),'"+editedValidToDate+"')])[2]", "Valid To Date Edited", false);
	}else if(requestType.equals("Application Access"))
	{
	Utility.verifyElementPresent(".//tbody//div[contains(text(),'"+editedValidToDate+"')]", "Valid To Date Edited", false);
	}
	Thread.sleep(1000);

	ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationSubmitBtn, "Click Submit Button");
	Thread.sleep(2000);
	if(requestType.equals("IT Access"))
	{
	ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationReasonTxt, "Need Temporary Access for System", "Enter Reason Code");
	Thread.sleep(1000);
	ByAttribute.click("xpath", ".//li[text()='Need Temporary Access for System']", "Select Reason Option");
	Thread.sleep(1000);
	ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationConfirmBtn, "Click Confirm Button");
	Thread.sleep(10000);
	}else if(requestType.equals("Application Access")){
	Thread.sleep(8000);
	}

	List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
	WebElement latestRequestNumber = requestNumberElements.get(0);

	reqNum = latestRequestNumber.getText();
	}else{
	System.out.println("Navigation to Access Page NOT Successful");
	logger.log(LogStatus.FAIL, "Navigation to Access Page NOT Successful");
	}

	} else {
	System.out.println("Navigation to 'Access Request' Page Failed");
	logger.log(LogStatus.FAIL, "Navigation to 'Access Request' Page Failed");
	}

	} catch (Exception e) {
	String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
	Utility.recoveryScenario(nameofCurrMethod, e);
	}
	}

	return reqNum;
	}
	
	

	
	/**
	 * <h1>createAccessRequestMultiple</h1> 
	 * This is Method to Create an Multiple Access Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 1-8-2021
	 * @param String requestType
	 * @return String
	 **/

	public static String createAccessRequestMultiple(String requestType, String location1, String access1, String location2, String access2) throws Throwable {

		String reqNum="";
		if (unhandledException == false) {
			System.out.println("***************************** createAccessRequestMultiple *********************************");
			logger.log(LogStatus.INFO,"***************************** createAccessRequestMultiple *********************************");
			try {
				
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
					Utility.pause(5);
				}

				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) {
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//label[text()='"+requestType+"']//parent::div//input[@type='radio']", "Click "+requestType+" Radio Button");
					Thread.sleep(2000);
					
					if(requestType.equals("Physical Access") || requestType.equals("Request Location Access"))
					{
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestSelfRdb, "Click on Self Radio Button");
						try{
							ByAttribute.click("xpath", "//*[@data-ref='btnInnerEl' and text()='Yes']", "Click Yes Warning Button");
						}catch(Exception e){
							System.out.println("Warning Popup Not Found");
						}
						
						ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchLocationTxt, location1, "Search Location by Location Name");
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+location1+"') and contains(text(),'"+location1+"')]", "Searched Location by Location Name", false);
						
						ByAttribute.click("xpath", ".//*[contains(@data-qtip,'"+location1+"') and contains(text(),'"+location1+"')]", "Click Location: "+location1);
						Thread.sleep(1000);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
						Thread.sleep(1000);
						if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestSearchAccessTxt)).size() > 0)
						{
							System.out.println("Navigation to Access Page Successful");
							logger.log(LogStatus.INFO, "Navigation to Access Page Successful");
							
							Utility.verifyElementPresent(".//h4[@data-qtip='"+access1+"']", "Searched Access", false);
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//h4[@data-qtip='"+access1+"']//ancestor::div[@class='innerWidget']", "Add Physical Access 1: "+access1);
							Thread.sleep(1000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreatePreviousBtn, "Click Previous Button");
							Thread.sleep(1000);
							
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchLocationTxt, location2, "Search Location by Location Name");
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+location2+"') and contains(text(),'"+location2+"')]", "Searched Location by Location Name", false);
							
							if(location2.equals(location1))
							{
								System.out.println("Second Location is the same as First Location");
							}else{
								ByAttribute.click("xpath", ".//*[contains(@data-qtip,'"+location2+"') and contains(text(),'"+location2+"')]", "Click Location: "+location1);
							}
							Thread.sleep(1000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
							Thread.sleep(2000);
							
							ByAttribute.click("xpath", ".//*[@class='x-tab-inner x-tab-inner-default' and contains(text(),'"+location2+"')]", "Click Location ("+location2+") Tab");
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//h4[@data-qtip='"+access2+"']", "Searched Access", false);
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//h4[@data-qtip='"+access2+"']//ancestor::div[@class='innerWidget']", "Add Physical Access 2: "+access2);
							Thread.sleep(1000);
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestReviewTab, "Click on Review Tab");
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//tbody//td[2]/div[text()='"+access1+"']", "Added Access: "+access1, false);
							Utility.verifyElementPresent(".//tbody//td[2]/div[text()='"+access2+"']", "Added Access: "+access2, false);
				            Thread.sleep(1000);
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationSubmitBtn, "Click Submit Button");
							Thread.sleep(2000);
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationReasonTxt, "General Access Required", "Enter Reason Code");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//li[text()='General Access Required']", "Select Reason Option");
							Thread.sleep(1000);
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationBusinessJustificationTxt, "Automation Test", "Enter Business Justification");
							Thread.sleep(1000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationConfirmBtn, "Click Confirm Button");
							Thread.sleep(10000);
							
							List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
							WebElement latestRequestNumber = requestNumberElements.get(0);
							
							reqNum = latestRequestNumber.getText();
						}else{
							System.out.println("Navigation to Access Page NOT Successful");
							logger.log(LogStatus.FAIL, "Navigation to Access Page NOT Successful");
						}
					}
					

				} else {
					System.out.println("Navigation to 'Access Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Access Request' Page Failed");
				}

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}

		return reqNum;
	}


	/**
	 * <h1>createAccessRequestMultiple</h1> 
	 * This is Method to Create an Multiple Access Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 1-8-2021
	 * @param String requestType
	 * @return String
	 **/

	public static String createLocationAccessRequestMultiple(String requestType, String locations, String accesses) throws Throwable {

		String reqNum="";
		if (unhandledException == false) {
			System.out.println("***************************** createLocationAccessRequestMultiple *********************************");
			logger.log(LogStatus.INFO,"***************************** createLocationAccessRequestMultiple *********************************");
			try {
				
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
					Utility.pause(5);
				}

				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) 
				{
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//label[text()='"+requestType+"']//parent::div//input[@type='radio']", "Click "+requestType+" Radio Button");
					Thread.sleep(2000);
					
					if(requestType.equals("Physical Access") || requestType.equals("Request Location Access"))
					{
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestSelfRdb, "Click on Self Radio Button");
						try{
							ByAttribute.click("xpath", "//*[@data-ref='btnInnerEl' and text()='Yes']", "Click Yes Warning Button");
						}catch(Exception e){
							System.out.println("Warning Popup Not Found");
						}
						
						String[] locationElements = locations.split(";");
						String[] accessElements = accesses.split(";");
						
						for(int i=0;i<locationElements.length;i++)
						{
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchLocationTxt, locationElements[i], "Search Location by Location Name");
							Thread.sleep(1000);
							Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+locationElements[i]+"') and contains(text(),'"+locationElements[i]+"')]", "Searched Location by Location Name", false);
							
							ByAttribute.click("xpath", ".//*[contains(@data-qtip,'"+locationElements[i]+"') and contains(text(),'"+locationElements[i]+"')]", "Click Location: "+locationElements[i]);
							Thread.sleep(1000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//*[@class='x-tab-inner x-tab-inner-default' and contains(text(),'"+locationElements[i]+"')]", "Click Location ("+locationElements[i]+") Tab");
							
							for(int j=0;j<accessElements.length;j++)
							{
								if(driver.findElements(By.xpath(".//h4[@data-qtip='"+accessElements[j]+"']")).size()>0)
								{
									if(driver.findElements(By.xpath(".//h4[@data-qtip='"+accessElements[j]+"']//ancestor::div[contains(@class,'innerWidget x-item-select-active')]")).size()>0)
									{
										System.out.println("Access already Selected");
										logger.log(LogStatus.INFO, "Access: "+accessElements[j]+" already Selected");
									}else{
										Utility.verifyElementPresent(".//h4[@data-qtip='"+accessElements[j]+"']", "Searched Access: "+accessElements[j], false);
										Thread.sleep(1000);
										ByAttribute.click("xpath", ".//h4[@data-qtip='"+accessElements[j]+"']//ancestor::div[@class='innerWidget']", "Add Physical Access 1: "+accessElements[j]);
										Thread.sleep(1000);
									}
								}
							}
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreatePreviousBtn, "Click Previous Button");
							Thread.sleep(1000);
						}
						
						if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateNextBtn)).size()>0)
						{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
							Thread.sleep(2000);
						}
						
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestReviewTab, "Click on Review Tab");
						Thread.sleep(1000);
						
						for(int j=0;j<accessElements.length;j++)
						{
							Utility.verifyElementPresent(".//tbody//td[2]/div[text()='"+accessElements[j]+"']", "Added Access: "+accessElements[j], false);
							Thread.sleep(500);
						}
					
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationSubmitBtn, "Click Submit Button");
						Thread.sleep(2000);
						ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationReasonTxt, "General Access Required", "Enter Reason Code");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//li[text()='General Access Required']", "Select Reason Option");
						Thread.sleep(1000);
						ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationBusinessJustificationTxt, "Automation Test", "Enter Business Justification");
						Thread.sleep(1000);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationConfirmBtn, "Click Confirm Button");
						Thread.sleep(10000);
						
						List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
						WebElement latestRequestNumber = requestNumberElements.get(0);
						
						reqNum = latestRequestNumber.getText();
					}
				}else {
					System.out.println("Navigation to 'Access Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Access Request' Page Failed");
				}

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}

		return reqNum;
	}


	/**
	 * <h1>approveAccessRequest</h1> 
	 * This is Method to Approve an Access Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 12-16-2020
	 * @param String requestType
	 * @return none
	 **/

	public static void approveAccessRequest(String locationName, String reqNum, String workflowStage) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"******************************** approveAccessRequest  *****************************************");
			logger.log(LogStatus.INFO,
					"***************************** approveAccessRequest *********************************");

			try {
				
				if(driver.findElements(By.xpath(HomeObjects.inboxTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.inboxTabBtn, "Click on Inbox Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeInboxLnk, "Click on Inbox Link");
					Utility.pause(5);
				}
				
				Utility.handleAnnouncementPopup();
				
				if (driver.findElements(By.xpath(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']")).size() > 0) {
					System.out.println("Access Request is Found in Inbox");
					logger.log(LogStatus.PASS, "Access Request is Found in Inbox");
					
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='tagorange' and text()='OPEN']", "Request Number: "+reqNum+" Current Status OPEN", false);
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and contains(text(),'"+workflowStage+"')]", "Request Number: "+reqNum+" Workflow Stage : "+workflowStage, false);
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					if(workflowStage.equals("Manager"))
					{
						Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+locationName+"']", "Access Name Matching with Location Selected via Self Service", false);
						Thread.sleep(500);
					}
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestApproveBtn, "Click Approve Button");
					Thread.sleep(5000);
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click Expand Inbox Button");
					Thread.sleep(1000);
					if(workflowStage.equals("Approved"))
					{
						Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='Area Admin']", "Area Admin Stage Inbox", false);
					}else{
						Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='"+workflowStage+"']", workflowStage+" Stage Inbox", false);
					}
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCompletedBtn, "Click Completed Inbox Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggreen' and text()='COMPLETED']", "Request Number: "+reqNum+" Changed to Completed", false);
					
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "Click Request Level Comments Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[@data-ref='btnInnerEl' and text()='Add Comment']", "Request Level Comment Added via Self Service", false);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog Button");
					
					ByAttribute.click("xpath", ".//a[@role='button' and @data-qtip='Attachments']", "Click Attachments Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[contains(@id,'header-title-textEl') and text()='Attachments']", "Attachment Added via Self Service", false);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog Button");
					Thread.sleep(1000);
					
					String validToDate = new SimpleDateFormat("M/d/YY").format(AGlobalComponents.validToDate);
					Utility.verifyElementPresent(".//tbody//div[contains(text(),'"+validToDate+"')]", "Valid To Date Modified from Self Service to: "+validToDate, false);
					Thread.sleep(1000);
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusBtn, "Click View Status Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and text()='Request Number : "+reqNum+"']", "Workflow Status for Request : "+reqNum, false);
					Utility.verifyElementPresent(".//*[@id='containerBoxHistory']//div[contains(text(),'Approved')]", "Access Request Approved", false);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");
					Thread.sleep(1000);	
					
				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	

	/**
	 * <h1>approveAccessRequestMultpile</h1> 
	 * This is Method to Approve Multiple Access In Single Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 1-8-2020
	 * @param String locationName1, String locationName2, String reqNum, String userId
	 * @return none
	 **/

	public static void approveAccessRequestMultpile(String locationName1, String locationName2, String reqNum, String userId) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"******************************** approveAccessRequestMultpile  *****************************************");
			logger.log(LogStatus.INFO,
					"***************************** approveAccessRequestMultpile *********************************");
			
			try {
				
				if(driver.findElements(By.xpath(HomeObjects.inboxTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.inboxTabBtn, "Click on Inbox Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeInboxLnk, "Click on Inbox Link");
					Utility.pause(5);
				}
				
				if (driver.findElements(By.xpath(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']")).size() > 0) {
					System.out.println("Access Request is Found in Inbox");
					logger.log(LogStatus.PASS, "Access Request is Found in Inbox");
					
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='tagorange' and text()='OPEN']", "Request Number: "+reqNum+" Current Status OPEN", false);
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and text()='Access Owner']", "Request Number: "+reqNum+" Workflow Stage Access Owner", false);
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+locationName1+"']", "Access Name Matching with Location Access 1 Selected via Self Service", false);
					Thread.sleep(500);
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+locationName2+"']", "Access Name Matching with Location Access 2 Selected via Self Service", false);
					Thread.sleep(500);
					
					ByAttribute.click("xpath", ".//div[@class='x-grid-cell-inner ' and text()='"+locationName1+"']//parent::td//following-sibling::td//button[contains(@data-qtip,'Approve')]", "Click Approve Button for Access: "+locationName1);
					Thread.sleep(50000);
					ByAttribute.click("xpath", ".//div[@class='x-grid-cell-inner ' and text()='"+locationName2+"']//parent::td//following-sibling::td//button[contains(@data-qtip,'Approve')]", "Click Approve Button for Access: "+locationName2);
					Thread.sleep(50000);
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click Expand Inbox Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='Access Owner']", "Access Owner Stage Inbox", false);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCompletedBtn, "Click Completed Inbox Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggreen' and text()='COMPLETED']", "Request Number: "+reqNum+" Changed to Completed", false);
					
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					ByAttribute.click("xpath", ".//div[@class='x-grid-cell-inner ' and text()='"+locationName1+"']//parent::td//following-sibling::td//button[@data-qtip='View Status']", "Click View Status Button for Access: "+locationName1);
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and text()='Workflow Status : "+reqNum+"']", "Workflow Status for Request : "+reqNum, false);
					Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'Approved')]", "Access Request Approved", false);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");
					Thread.sleep(1000);
					
					ByAttribute.click("xpath", ".//div[@class='x-grid-cell-inner ' and text()='"+locationName2+"']//parent::td//following-sibling::td//button[@data-qtip='View Status']", "Click View Status Button for Access: "+locationName2);
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and text()='Workflow Status : "+reqNum+"']", "Workflow Status for Request : "+reqNum, false);
					Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'Approved')]", "Access Request Approved", false);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");
					Thread.sleep(1000);
					
//					Provisioning Monitor Validation
					ByAttribute.mouseHover("xpath", AdminObjects.adminTabBtn, "Mouse Hover on Admin Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", AdminObjects.adminProvMonitorLnk, "Click on Provisioning Monitor Link");
					Utility.pause(10);
					
					for(int i=0;i<10;i++)
					{
						if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']//ancestor::tr//div[@class='x-grid-cell-inner ' and text()='ASSIGN_ROLES_SUCCESS']")).size()>0)
						{
							Utility.verifyElementPresent("(.//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"'])[1]", "1st Role Provisioned with Request Number: "+reqNum, false);
							Utility.verifyElementPresent("(.//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']//ancestor::tr//div[@class='x-grid-cell-inner ' and text()='ASSIGN_ROLES_SUCCESS'])[1]", "1st Role Provisioned with Provisioning Code: ASSIGN_ROLES_SUCCESS", false);
							Thread.sleep(1000);
							Utility.verifyElementPresent("(.//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"'])[2]", "2nd Role Provisioned with Request Number: "+reqNum, false);
							Utility.verifyElementPresent("(.//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']//ancestor::tr//div[@class='x-grid-cell-inner ' and text()='ASSIGN_ROLES_SUCCESS'])[2]", "2nd Role Provisioned with Provisioning Code: ASSIGN_ROLES_SUCCESS", false);
							break;
						}else{
							ByAttribute.click("xpath", AdminObjects.adminProvMonitorReloadBtn, "Click Provisioning Monitor Refresh button");
							Thread.sleep(3000);
						}
					}
					
//					IDM Validation
					ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on IDM Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity Link");
					Utility.pause(5);
					
					ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, userId, "Enter User ID in Search field");
					Thread.sleep(3000);
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']", "User Available in IDM", false);
					
					Actions actions = new Actions(driver);
					WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']"));
					actions.doubleClick(elementLocator).perform();
					Thread.sleep(3000);
					
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessesTabBtn, "Click Access Tab");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//tbody/tr/td[2]/div[@class='x-grid-cell-inner ' and text()='"+locationName1+"']", "Access: "+locationName1+" is Added with Provided Dates", false);
					Utility.verifyElementPresent(".//tbody/tr/td[2]/div[@class='x-grid-cell-inner ' and text()='"+locationName2+"']", "Access: "+locationName2+" is Added with Provided Dates", false);
								
				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			} 
		}
	}

	

	/**
	 * <h1>approveAndRejectAccessRequestMultpile</h1> 
	 * This is Method to Approve 1 and Reject other Access for Multiple Access In Single Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 1-8-2020
	 * @param String locationName1, String locationName2, String reqNum, String userId
	 * @return none
	 **/

	public static void approveAndRejectAccessRequestMultpile(String locationName1, String locationName2, String reqNum, String userId) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"******************************** approveAndRejectAccessRequestMultpile  *****************************************");
			logger.log(LogStatus.INFO,
					"***************************** approveAndRejectAccessRequestMultpile *********************************");
			try {

				if(driver.findElements(By.xpath(HomeObjects.inboxTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.inboxTabBtn, "Click on Inbox Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeInboxLnk, "Click on Inbox Link");
					Utility.pause(5);
				}
				
				if (driver.findElements(By.xpath(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']")).size() > 0) {
					System.out.println("Access Request is Found in Inbox");
					logger.log(LogStatus.PASS, "Access Request is Found in Inbox");
					
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='tagorange' and text()='OPEN']", "Request Number: "+reqNum+" Current Status OPEN", false);
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and text()='Access Owner']", "Request Number: "+reqNum+" Workflow Stage Access Owner", false);
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+locationName1+"']", "Access Name Matching with Location Access 1 Selected via Self Service", false);
					Thread.sleep(500);
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+locationName2+"']", "Access Name Matching with Location Access 2 Selected via Self Service", false);
					Thread.sleep(500);
					
					ByAttribute.click("xpath", ".//div[@class='x-grid-cell-inner ' and text()='"+locationName1+"']//parent::td//following-sibling::td//button[contains(@data-qtip,'Approve')]", "Click Approve Button for Access: "+locationName1);
					Thread.sleep(50000);
					ByAttribute.click("xpath", ".//div[@class='x-grid-cell-inner ' and text()='"+locationName2+"']//parent::td//following-sibling::td//button[contains(@data-qtip,'Reject')]", "Click Reject Button for Access: "+locationName2);
					Thread.sleep(3000);
					
					if(driver.findElements(By.xpath(HomeObjects.homeInboxCommentRequiredLbl)).size()>0)
					{
						Robot robot = new Robot();
						
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						Thread.sleep(500);
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						Thread.sleep(500);
						robot.keyPress(KeyEvent.VK_A);
						robot.keyPress(KeyEvent.VK_U);
						robot.keyPress(KeyEvent.VK_T);
						robot.keyPress(KeyEvent.VK_O);
						
						robot.keyRelease(KeyEvent.VK_A);
						robot.keyRelease(KeyEvent.VK_U);
						robot.keyRelease(KeyEvent.VK_T);
						robot.keyRelease(KeyEvent.VK_O);
						
						Utility.verifyElementPresent(HomeObjects.homeInboxCommentRequiredConfirmBtn, "Rejection Comment Added", false);
						ByAttribute.click("xpath", HomeObjects.homeInboxCommentRequiredConfirmBtn, "Click Confirm Button");
					}
					
					Thread.sleep(7000);

					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click Expand Inbox Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='Access Owner']", "Access Owner Stage Inbox", false);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCompletedBtn, "Click Completed Inbox Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggreen' and text()='COMPLETED']", "Request Number: "+reqNum+" Changed to Completed", false);
					
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					ByAttribute.click("xpath", ".//div[@class='x-grid-cell-inner ' and text()='"+locationName1+"']//parent::td//following-sibling::td//button[@data-qtip='View Status']", "Click View Status Button for Access: "+locationName1);
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and text()='Workflow Status : "+reqNum+"']", "Workflow Status for Request : "+reqNum, false);
					Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'Approved')]", "Access Request Approved", false);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");
					Thread.sleep(1000);
					
					ByAttribute.click("xpath", ".//div[@class='x-grid-cell-inner ' and text()='"+locationName2+"']//parent::td//following-sibling::td//button[@data-qtip='View Status']", "Click View Status Button for Access: "+locationName2);
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and text()='Workflow Status : "+reqNum+"']", "Workflow Status for Request : "+reqNum, false);
					Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'Rejected')]", "Access Request Rejected", false);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");
					Thread.sleep(1000);
					
//					Provisioning Monitor Validation
					ByAttribute.mouseHover("xpath", AdminObjects.adminTabBtn, "Mouse Hover on Admin Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", AdminObjects.adminProvMonitorLnk, "Click on Provisioning Monitor Link");
					Utility.pause(10);
					
					for(int i=0;i<10;i++)
					{
						if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']//ancestor::tr//div[@class='x-grid-cell-inner ' and text()='ASSIGN_ROLES_SUCCESS']")).size()>0)
						{
							Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']", "Request Provisioned with Request Number: "+reqNum, false);
							Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']//ancestor::tr//div[@class='x-grid-cell-inner ' and text()='ASSIGN_ROLES_SUCCESS']", "Request Provisioned with Provisioning Code: ASSIGN_ROLES_SUCCESS", false);
							break;
						}else{
							ByAttribute.click("xpath", AdminObjects.adminProvMonitorReloadBtn, "Click Provisioning Monitor Refresh button");
							Thread.sleep(3000);
						}
					}
					
//					IDM Validation
					ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on IDM Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity Link");
					Utility.pause(5);
					
					ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, userId, "Enter User ID in Search field");
					Thread.sleep(3000);
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']", "User Available in IDM", false);
					
					Actions actions = new Actions(driver);
					WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']"));
					actions.doubleClick(elementLocator).perform();
					Thread.sleep(3000);
					
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessesTabBtn, "Click Access Tab");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//tbody/tr/td[2]/div[@class='x-grid-cell-inner ' and text()='"+locationName1+"']", "Access: "+locationName1+" is Added with Provided Dates", false);
					Utility.verifyElementNotPresent(".//tbody/tr/td[2]/div[@class='x-grid-cell-inner ' and text()='"+locationName2+"']", "Access: "+locationName2, true);
								
				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	

	/**
	 * <h1>rejectAccessRequest</h1> 
	 * This is Method to Approve an Access Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 12-16-2020
	 * @param String requestType
	 * @return none
	 **/

	public static void rejectAccessRequest(String locationName, String reqNum, String userId) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"******************************** rejectAccessRequest  *****************************************");
			logger.log(LogStatus.INFO,
					"***************************** rejectAccessRequest *********************************");
			try {
				
				if(driver.findElements(By.xpath(HomeObjects.inboxTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.inboxTabBtn, "Click on Inbox Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeInboxLnk, "Click on Inbox Link");
					Utility.pause(5);
				}
				
				if (driver.findElements(By.xpath(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']")).size() > 0) {
					System.out.println("Access Request is Found in Inbox");
					logger.log(LogStatus.PASS, "Access Request is Found in Inbox");
					
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+locationName+"']", "Access Name Matching with Location Selected via Self Service", false);
					Thread.sleep(500);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestRejectBtn, "Click Reject Button");
					Thread.sleep(3000);
					
					if(driver.findElements(By.xpath(HomeObjects.homeInboxCommentRequiredLbl)).size()>0)
					{
						Robot robot = new Robot();
						
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						Thread.sleep(500);
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						Thread.sleep(500);
						robot.keyPress(KeyEvent.VK_A);
						robot.keyPress(KeyEvent.VK_U);
						robot.keyPress(KeyEvent.VK_T);
						robot.keyPress(KeyEvent.VK_O);
						
						robot.keyRelease(KeyEvent.VK_A);
						robot.keyRelease(KeyEvent.VK_U);
						robot.keyRelease(KeyEvent.VK_T);
						robot.keyRelease(KeyEvent.VK_O);
						
						Utility.verifyElementPresent(HomeObjects.homeInboxCommentRequiredConfirmBtn, "Rejection Comment Added", false);
						ByAttribute.click("xpath", HomeObjects.homeInboxCommentRequiredConfirmBtn, "Click Confirm Button");
					}
					
					Thread.sleep(7000);
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click Expand Inbox Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCompletedBtn, "Click Completed Inbox Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggreen' and text()='COMPLETED']", "Request Number: "+reqNum+" Changed to Completed", false);
					
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "Click Comments Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[@data-ref='btnInnerEl' and text()='Add Comment']", "Comment Added via Self Service", false);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog Button");
					
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusBtn, "Click View Status Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and text()='Workflow Status : "+reqNum+"']", "Workflow Status for Request : "+reqNum, false);
					Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'Rejected')]", "Access Request Approved", false);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");
					Thread.sleep(1000);
					
//					Validate Attachment Yet to be Verified
					
	
					
//					IDM Validation
//					ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on IDM Tab Link");
//					Thread.sleep(1000);
//					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity Link");
//					Utility.pause(5);
//					
//					ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, userId, "Enter User ID in Search field");
//					Thread.sleep(3000);
//					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']", "User Available in IDM", false);
//					
//					Actions actions = new Actions(driver);
//					WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']"));
//					actions.doubleClick(elementLocator).perform();
//					Thread.sleep(3000);
//					
//					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessesTabBtn, "Click Access Tab");
//					Thread.sleep(1000);
//					Utility.verifyElementNotPresent(".//tbody/tr/td[2]/div[@class='x-grid-cell-inner ' and text()='"+locationName+"']", "Access: "+locationName+" with Provided Dates", true);
				
				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}


	
	



	/**
	 * <h1>createNewBadgeRequest</h1> 
	 * This is Method to Create a New Badge Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 1-18-2021
	 * @param String requestType
	 * @return String
	 **/

	public static String createNewBadgeRequest(String requestType, String requestFor, String otherUser, String cardStatus) throws Throwable {

			reqNum="";
		if (unhandledException == false) {
			System.out.println("***************************** createNewBadgeRequest *********************************");
			logger.log(LogStatus.INFO,"***************************** createNewBadgeRequest *********************************");
			try {
				
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
					Utility.pause(5);
				}
				
				Utility.handleAnnouncementPopup();

				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) 
				{
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//label[text()='"+requestType+"']//parent::div//input[@type='radio']", "Click "+requestType+" Radio Button");
					Thread.sleep(2000);
					
					if(requestFor.equalsIgnoreCase("Others"))
					{
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
						Thread.sleep(2000);
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSearchUserTxt, otherUser, "Enter Other User");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//span[contains(text(),'"+otherUser+"')]", "Select User "+otherUser+" from List");
						Thread.sleep(1000);
					}else{
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestSelfRdb, "Click on Self Radio Button");
					}
					
					if(driver.findElements(By.xpath(SelfServiceObjects.selfServiceCardActiveDateTxt)).size()>0)
					{
						DateFormat dateFormat = new SimpleDateFormat("M/d/yy hh:mm a");
						Date currentDate = new Date();
				        System.out.println(dateFormat.format(currentDate));
	
				        // convert date to calendar
				        Calendar c = Calendar.getInstance();
				        c.setTime(currentDate);
	
				        // convert calendar to date
				        Date activeDate = c.getTime();
				        String validFrom = new SimpleDateFormat("M/d/yy h:mm a").format(activeDate);
				        
				        ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCardActiveDateTxt, validFrom, "Enter Badge Valid From Date");
				        Thread.sleep(1000);
					}
					
					if(driver.findElements(By.xpath(SelfServiceObjects.selfServiceCardInactiveDateTxt)).size()>0)
					{
						DateFormat dateFormat = new SimpleDateFormat("M/d/yy hh:mm a");
						Date currentDate = new Date();
				        System.out.println(dateFormat.format(currentDate));
	
				        // convert date to calendar
				        Calendar c = Calendar.getInstance();
				        c.setTime(currentDate);
	
				        // manipulate date
				        c.add(Calendar.MONTH, 1);
				        
				        // convert calendar to date
				        Date currentDatePlusOne = c.getTime();
				        AGlobalComponents.validToDate = currentDatePlusOne;
				        String validTo = new SimpleDateFormat("M/d/yy h:mm a").format(currentDatePlusOne);
				        
				        ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCardInactiveDateTxt, validTo, "Enter Badge Valid To Date");
				        Thread.sleep(1000);
					}
					
					ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCardStatusTxt, cardStatus, "Enter Card Status");
					Thread.sleep(500);
					ByAttribute.click("xpath", ".//li[@role='option' and text()='"+cardStatus+"']", "Select Card Status from List");
					
					ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceCardRequestReasonTxt, "Automation Testing", "Enter Card Request Reason");
					Thread.sleep(1000);
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "Click Comments Button");
					Thread.sleep(1000);
					
						Robot robot = new Robot();
					
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyPress(KeyEvent.VK_U);
					robot.keyPress(KeyEvent.VK_T);
					robot.keyPress(KeyEvent.VK_O);
					
					robot.keyRelease(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_U);
					robot.keyRelease(KeyEvent.VK_T);
					robot.keyRelease(KeyEvent.VK_O);

					ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Add Comment']", "Add Comment", false);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
					Thread.sleep(1000);														
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestAttachmentsBtn, "Click Attachments Button");
					Thread.sleep(2000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestUploadAttachmentBtn, "Cick Upload Attachment Button");
					Thread.sleep(3000);
					
					String uploadFile = System.getProperty("user.dir") + "\\Browser_Files\\Applicant_Photo.jpg";
					StringSelection ss = new StringSelection(uploadFile);
		            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		            robot.keyPress(KeyEvent.VK_CONTROL);
		            robot.keyPress(KeyEvent.VK_V);
		            robot.keyRelease(KeyEvent.VK_V);
		            robot.keyRelease(KeyEvent.VK_CONTROL);
		            robot.keyPress(KeyEvent.VK_ENTER);
		            robot.keyRelease(KeyEvent.VK_ENTER);
					
		            Thread.sleep(4000);
		            Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Attachments']", "Attachment", false);
		            Thread.sleep(1000);
		            ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
		            Thread.sleep(2000);

		            ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationSubmitBtn, "Click Submit Button");
					Thread.sleep(10000);
					
					List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					WebElement latestRequestNumber = requestNumberElements.get(0);
					
					reqNum = latestRequestNumber.getText();

				}else{
					System.out.println("Unable to Load Access Request Page");
					logger.log(LogStatus.FAIL, "Unable to Load Access Request Page");
				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}

		return reqNum;
	}




	/**
	 * <h1>approveBadgeRequest</h1> 
	 * This is Method to Approve an Access Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 12-16-2020
	 * @param String requestType
	 * @return none
	 **/

	public static void approveBadgeRequest(int stageNum, String reqNum, String cardStatus, String assetName, String fullName) throws Throwable {

		if (unhandledException == false) {
			System.out.println(	"******************************* approveBadgeRequest ****************************************");
			logger.log(LogStatus.INFO,"**************************** approveBadgeRequest ********************************");

		try {

			if(driver.findElements(By.xpath(HomeObjects.inboxTabBtn)).size()>0)
			{
				ByAttribute.click("xpath", HomeObjects.inboxTabBtn, "Click on Inbox Link");
				Utility.pause(5);
			}else if(driver.findElements(By.xpath(".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='My Inbox']")).size()>0){
				ByAttribute.click("xpath", ".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='My Inbox']", "Click on Inbox Link");
				Utility.pause(5);
			}else{
				ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
				Thread.sleep(1000);
				ByAttribute.click("xpath", HomeObjects.homeInboxLnk, "Click on Inbox Link");
				Utility.pause(5);
			}

			if (driver.findElements(By.xpath(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']")).size() > 0) {
				System.out.println("Access Request is Found in Inbox");
				logger.log(LogStatus.PASS, "Access Request is Found in Inbox");

				Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='tagorange' and text()='OPEN']", "Request Number: "+reqNum+" Current Status OPEN", false);
				if(stageNum==1)
				{
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and contains(text(),'Manager')]", "Request Number: "+reqNum+" Workflow Stage Manager", false);
				}else{
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and contains(text(),'Approved')]", "Request Number: "+reqNum+" Workflow Stage Approved", false);
				}
				Thread.sleep(1000);
				ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
				Thread.sleep(3000);

				if(stageNum==2)
				{
					ByAttribute.click("xpath", SelfServiceObjects.selfServiceSelectBadgeDdn, "Select Asset from list");
					Thread.sleep(2000);

					Robot robot = new Robot();

					StringSelection ss = new StringSelection(assetName);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
					
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					Thread.sleep(4000);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);

					Thread.sleep(2000);
				}

				Thread.sleep(5000);
				ByAttribute.click("xpath", HomeObjects.homeInboxRequestApproveBtn, "Click Approve Button");
				Thread.sleep(1000);
				if(driver.findElements(By.xpath(".//*[contains(@id,'btnInnerEl') and text()='Yes']")).size()>0)
				{
					ByAttribute.click("xpath", ".//*[contains(@id,'btnInnerEl') and text()='Yes']", "Click Save Yes Button");
					Thread.sleep(5000);
				}else{
					Thread.sleep(10000);
				}


				ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click Expand Inbox Button");
				Thread.sleep(1000);
				if(stageNum==1)
				{
					Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='Manager']", "Manager Stage Inbox", false);
				}else{
					Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='Approved']", "Approved Stage Inbox", false);
				}
				ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCompletedBtn, "Click Completed Inbox Button");
				Thread.sleep(2000);
				Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggreen' and text()='COMPLETED']", "Request Number: "+reqNum+" Changed to Completed", false);

				ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
				Thread.sleep(3000);

				Utility.verifyElementPresent(".//tbody//label[text()='"+cardStatus+"']", "Card Staus: "+cardStatus+" as set in Request", false);
				Thread.sleep(500);

				if(stageNum==1)
				{
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "Click Comments Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[@data-ref='btnInnerEl' and text()='Add Comment']", "Comment Added via Self Service", false);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog Button");

					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestAttachmentsBtn, "Click Attachments Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Attachments']", "Attachment Added via Self Service", false);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog Button");
					Thread.sleep(2000);
				}

				if(stageNum==2)
				{
					//	PRINT BADGE
					ByAttribute.click("xpath", SelfServiceObjects.selfServicePrintBtn, "Click Print Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//*[contains(@id,'header-title-textEl') and text()='Badge Print']", "Badge Print Dialog Box", false);
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[contains(@id,'fullName') and contains(text(),'"+fullName+"')]", "Badge With Employee Name Printed as: "+fullName, false);
					Thread.sleep(1000);
					ByAttribute.click("xpath", SelfServiceObjects.selfServiceBadgeCancelBtn, "Click Cancel Button");
				}

				ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusBtn, "Click View Status Button");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and contains(text(),'"+reqNum+"')]", "Workflow Status for Request : "+reqNum, false);

				if(stageNum==1)
				{
					Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'Pending action by')]", "Action Pending at Badge Admin Stage", false);
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");
				}else{
					Utility.verifyElementPresent(".//*[@id='containerBoxHistory']/table//div[contains(text(),'"+assetName+" Completed for')]", "Completed by Badge Admin", false);
					Thread.sleep(1000);
					ByAttribute.click("xpath", "(.//div[contains(@class,'window')]//span[@data-ref='btnInnerEl' and text()='Cancel'])[2]", "Click Cancel Button");
				}
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
			Utility.recoveryScenario(nameofCurrMethod, e);
		}
	}
}

	

	/**
	 * <h1>approveBadgeRequest</h1> 
	 * This is Method to Approve a Replace Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 2-18-2021
	 * @param String requestType
	 * @return none
	 **/

	public static void approveReplaceBadgeRequest(int stageNum, String reqNum, String cardStatus, String assetName, String fullName) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"******************************** approveReplaceBadgeRequest  *****************************************");
			logger.log(LogStatus.INFO,
					"***************************** approveReplaceBadgeRequest *********************************");

			try {
				
				if(driver.findElements(By.xpath(HomeObjects.inboxTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.inboxTabBtn, "Click on Inbox Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeInboxLnk, "Click on Inbox Link");
					Utility.pause(5);
				}
				
				Utility.handleAnnouncementPopup();
				
				if (driver.findElements(By.xpath(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']")).size() > 0) {
					System.out.println("Access Request is Found in Inbox");
					logger.log(LogStatus.PASS, "Access Request is Found in Inbox");
					
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='tagorange' and text()='OPEN']", "Request Number: "+reqNum+" Current Status OPEN", false);
					if(stageNum==1)
					{
						Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and contains(text(),'Manager')]", "Request Number: "+reqNum+" Workflow Stage Manager", false);
					}else{
						Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and contains(text(),'Owner')]", "Request Number: "+reqNum+" Workflow Stage Badge Admin", false);
					}
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					if(stageNum==1)
					{
						Utility.verifyElementPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='"+cardStatus+"']", "Asset Status: "+cardStatus, false);
					}
					Utility.verifyElementPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Automation Testing']", "Replacement Reason: Automation Testing", false);

					if(stageNum==2)
					{
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceSelectBadgeDdn, "Select Asset from list");
						Thread.sleep(2000);
						
						Robot robot = new Robot();
						
						StringSelection ss = new StringSelection(assetName);
			            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	
			            robot.keyPress(KeyEvent.VK_CONTROL);
			            robot.keyPress(KeyEvent.VK_V);
			            robot.keyRelease(KeyEvent.VK_V);
			            robot.keyRelease(KeyEvent.VK_CONTROL);
			            Thread.sleep(4000);
			            robot.keyPress(KeyEvent.VK_ENTER);
			            robot.keyRelease(KeyEvent.VK_ENTER);
			            Thread.sleep(500);
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						
						Thread.sleep(2000);
						
						//PRINT BADGE
						ByAttribute.click("xpath", SelfServiceObjects.selfServicePrintBtn, "Click Print Button");
						Thread.sleep(2000);
						Utility.verifyElementPresent(".//*[contains(@id,'header-title-textEl') and text()='Badge Print']", "Badge Print Dialog Box", false);
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//*[contains(@id,'fullName') and text()='"+fullName+"']", "Badge With Employee Name Printed as: "+fullName, false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", "(//*[contains(@id,'btnInnerEl') and text()='Cancel'])[2]", "Click Cancel Button");

					}
					
					Thread.sleep(3000);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestApproveBtn, "Click Approve Button");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(".//*[contains(@id,'btnInnerEl') and text()='Yes']")).size()>0)
					{
						ByAttribute.click("xpath", ".//*[contains(@id,'btnInnerEl') and text()='Yes']", "Click Save Yes Button");
					}
					Thread.sleep(10000);
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click Expand Inbox Button");
					Thread.sleep(1000);
					if(stageNum==1)
					{
						Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='Manager']", "Manager Stage Inbox", false);
					}else{
						Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='Badge Admin']", "Badge Admin Stage Inbox", false);
					}
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCompletedBtn, "Click Completed Inbox Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggreen' and text()='COMPLETED']", "Request Number: "+reqNum+" Changed to Completed", false);
					
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					if(stageNum==1)
					{
						Thread.sleep(1000);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "Click Comments Button");
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//*[@data-ref='btnInnerEl' and text()='Add Comment']", "Comment Added via Self Service", false);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog Button");
						
						Thread.sleep(1000);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestAttachmentsBtn, "Click Attachments Button");
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Attachments']", "Attachment Added via Self Service", false);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog Button");	
						Thread.sleep(2000);
					}
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusBtn, "Click View Status Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and contains(text(),'"+reqNum+"')]", "Workflow Status for Request : "+reqNum, false);
					
					if(stageNum==1)
					{
						Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'Pending action by')]//span[contains(text(),'Badge Admin')]", "Action Pending at Badge Admin Stage", false);
						ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");
					}else{
						Utility.verifyElementPresent(".//*[@id='containerBoxHistory']//td//div[contains(text(),'Asset: "+assetName+" Provisioning Success')]", "Asset: "+assetName+" Assigned Successfully", false);
						ByAttribute.click("xpath", "(.//div[contains(@class,'window')]//span[@data-ref='btnInnerEl' and text()='Cancel'])[2]", "Click Cancel Button");
					}
					Thread.sleep(1000);

				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	
	/**
	 * <h1>createNewAsset</h1> 
	 * This is Method to Create a New Asset
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 02-09-2021
	 * @param String requestStatus
	 * @return none
	 **/

	public static String createNewAsset(String badgeType, String badgeSubType, String system) throws Throwable {

		String badgeName="";
		String badgeId="";
		if (unhandledException == false) {
			System.out.println("***************************** createNewAsset *********************************");
			logger.log(LogStatus.INFO,"***************************** createNewAsset *********************************");
			try {
				
				if(driver.findElements(By.xpath(AssetObjects.assetTabBtn)).size()>0)
				{
					ByAttribute.mouseHover("xpath", AssetObjects.assetTabBtn, "Mouse Hover on Asset Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", AssetObjects.assetManageAssetLnk, "Click on Manage Asset Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Card Holders & Assets Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", AssetObjects.manageAssetsLnk, "Click on Manage Asset Link");
					Utility.pause(5);
				}
				
								
				if (driver.findElements(By.xpath(".//label[contains(text(),'Manage Asset')]")).size() > 0) 
				{
					System.out.println("Manage Assets Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Manage Assets Page Loaded Successfully");
					
					ByAttribute.click("xpath", AssetObjects.assetCreateAssetBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.clearSetText("xpath", AssetObjects.assetTypeTxt, badgeType, "Enter Badge Type: "+badgeType);
					Thread.sleep(500);
					ByAttribute.click("xpath", ".//li[@role='option' and text()='"+badgeType+"']", "Select "+badgeType+" Option from List");
					
					if(badgeSubType.equals(""))
					{
						
					}else{
						ByAttribute.clearSetText("xpath", AssetObjects.assetSubTypeTxt, badgeSubType, "Enter Bdge Sub Type: "+badgeSubType);
						Thread.sleep(500);
						ByAttribute.click("xpath", ".//li[@role='option' and text()='"+badgeSubType+"']", "Select "+badgeSubType+" Option from List");
					}
					
					badgeName="AUTO-"+Utility.getRandomNumber(5);
					ByAttribute.clearSetText("xpath", AssetObjects.assetSerialNumberTxt, badgeName, "Enter Serial Number");
					Thread.sleep(1000);
					
					switch(system){
					
					case "AMAG":
						badgeId = "8"+Utility.getRandomNumber(11);
						break;
						
					case "CCURE 9000":
						badgeId = "9"+Utility.getRandomNumber(4);
						break;
						
					case "Lenel":
						badgeId = "6"+Utility.getRandomNumber(5);
						break;
						
					default:
						badgeId = "";
						
					}
					
					AGlobalComponents.badgeId = badgeId;
					ByAttribute.clearSetText("xpath", AssetObjects.assetBadgeIdTxt, badgeId, "Enter Badge ID: "+badgeId);
					Thread.sleep(1000);
					
					ByAttribute.clearSetText("xpath", AssetObjects.assetBadgeNameTxt, badgeName, "Enter Badge Name: "+badgeName);
					Thread.sleep(1000);
					
					ByAttribute.clearSetText("xpath", AssetObjects.assetSystemTxt, system, "Enter System");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//li[@role='option' and contains(text(),'"+system+"')]", "Select "+system+" Option from List");
					Thread.sleep(1000);
					
					ByAttribute.clearSetText("xpath", AssetObjects.assetSourceIdTxt, badgeId, "Enter Source Id");
					Thread.sleep(1000);
					
					ByAttribute.click("xpath", AssetObjects.assetSaveBtn, "Click Save Button");
					Thread.sleep(3000);

					if(driver.findElements(By.xpath(AssetObjects.assetTabBtn)).size()>0)
					{
						ByAttribute.mouseHover("xpath", AssetObjects.assetTabBtn, "Mouse Hover on Asset Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", AssetObjects.assetManageAssetLnk, "Click on Manage Asset Link");
						Utility.pause(5);
					}else{
						ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Card Holders & Assets Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", AssetObjects.manageAssetsLnk, "Click on Manage Asset Link");
						Utility.pause(5);
					}
					
					Utility.verifyElementPresent(".//tbody//div[text()='"+badgeName+"']", "Newly Create Badge", false);
					getIndexOfManageAssetsHeaders();
					WebElement asstCode= driver.findElement(By.xpath("(//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr[1]/td["+assetCodeIndex+"]/div)[1]"));
					AGlobalComponents.assetCode = asstCode.getText();
				} else {
					System.out.println("Navigation to 'Manage Asset' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Manage Asset' Page Failed");
				}

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
		return badgeName;
		
	}


	
	
	
	
	
	/**
	 * <h1>createActivateDeactivateBadgeRequest</h1> 
	 * This is Method to Activate or Deactivate an Existing Badge
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 2-10-2021
	 * @param String requestType
	 * @return String
	 **/

	public static String createActivateDeactivateBadgeRequest(String requestType, String requestFor, String otherUser) throws Throwable {

		String reqNum="";
		if (unhandledException == false) {
			System.out.println("***************************** createActivateDeactivateBadgeRequest *********************************");
			logger.log(LogStatus.INFO,"***************************** createActivateDeactivateBadgeRequest *********************************");
			try {
				
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestLnk)).size()>0)
					{
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
					}else{
						ByAttribute.click("xpath", ".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='My Requests']", "Click on Access Request Link");
					}
					Utility.pause(10);
				}
				
				Utility.handleAnnouncementPopup();

				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) 
				{
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					
					if(requestFor.equalsIgnoreCase("Others"))
					{
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
						Thread.sleep(1000);
					}else{
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestSelfRdb, "Click on Self Radio Button");
						Thread.sleep(1000);
					}
					
					if(driver.findElements(By.xpath(".//label[text()='"+requestType+"']//parent::div//input[@type='radio']")).size()>0)
					{
						ByAttribute.click("xpath", ".//label[text()='"+requestType+"']//parent::div//input[@type='radio']", "Click "+requestType+" Radio Button");
						Thread.sleep(2000);
					}else{
						ByAttribute.click("xpath", ".//label[text()='Request Type']//following-sibling::div//label[text()='Others']//parent::div//input[@type='radio']", "Click Others Radio Button");
						Thread.sleep(2000);
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSelectRequestTypeTxt, requestType, "Enter Request Type");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//li[@role='option' and text()='"+requestType+"']", "Select Request Type as: "+requestType);
						Thread.sleep(2000);
					}
					
					ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSearchUserTxt, otherUser, "Enter Other User");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//span[contains(text(),'"+otherUser+"')]", "Select User "+otherUser+" from List");
					Thread.sleep(2000);
					
					if(requestType.equalsIgnoreCase("Activate Badge"))
					{
						Utility.verifyElementPresent("(.//div[@role='button' and @class='x-action-col-icon x-action-col-0  aegrid-active3'])[1]", "Card Status InActive", false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", "(.//div[@role='button' and @class='x-action-col-icon x-action-col-0  aegrid-active3'])[1]", "Click Activate Button");
						Thread.sleep(1000);
						Utility.verifyElementPresent("(.//div[@role='button' and @class='x-action-col-icon x-action-col-0  aegrid-inactive2'])[1]", "Card Status Changed to Active", false);
					}else{
						Utility.verifyElementPresent("(.//div[@role='button' and @class='x-action-col-icon x-action-col-0  aegrid-inactive2'])[1]", "Card Status Active", false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", "(.//div[@role='button' and @class='x-action-col-icon x-action-col-0  aegrid-inactive2'])[1]", "Click Deactivate Button");
						Thread.sleep(1000);
						Utility.verifyElementPresent("(.//div[@role='button' and @class='x-action-col-icon x-action-col-0  aegrid-active3'])[1]", "Card Status Changed to InActive", false);
					}
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "Click Comments Button");
					Thread.sleep(1000);
					
					Robot robot = new Robot();
					
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyPress(KeyEvent.VK_U);
					robot.keyPress(KeyEvent.VK_T);
					robot.keyPress(KeyEvent.VK_O);
					
					robot.keyRelease(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_U);
					robot.keyRelease(KeyEvent.VK_T);
					robot.keyRelease(KeyEvent.VK_O);

					ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Add Comment']", "Add Comment", false);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
					Thread.sleep(1000);														
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestAttachmentsBtn, "Click Attachments Button");
					Thread.sleep(2000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestUploadAttachmentBtn, "Cick Upload Attachment Button");
					Thread.sleep(3000);
					
					String uploadFile = System.getProperty("user.dir") + "\\Browser_Files\\Applicant_Photo.jpg";
					StringSelection ss = new StringSelection(uploadFile);
		            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		            robot.keyPress(KeyEvent.VK_CONTROL);
		            robot.keyPress(KeyEvent.VK_V);
		            robot.keyRelease(KeyEvent.VK_V);
		            robot.keyRelease(KeyEvent.VK_CONTROL);
		            robot.keyPress(KeyEvent.VK_ENTER);
		            robot.keyRelease(KeyEvent.VK_ENTER);
					
		            Thread.sleep(5000);
		            Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Attachments']", "Attachment", false);
		            ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
		            Thread.sleep(1000);

		            ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationSubmitBtn, "Click Submit Button");
					Thread.sleep(10000);
					
					List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					WebElement latestRequestNumber = requestNumberElements.get(0);
					
					reqNum = latestRequestNumber.getText();

				}else{
					System.out.println("Unable to Load Access Request Page");
					logger.log(LogStatus.FAIL, "Unable to Load Access Request Page");
				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}

		return reqNum;
	}
	
	
	
	
	
	
	
	
	/**
	 * <h1>provisioningMonitorValidation</h1> 
	 * This is Method to Validate Request Status in Provisioning Monitor
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 2-10-2021
	 * @param String requestType
	 * @return none
	 **/

	public static void provisioningMonitorValidation(String reqNum, String provCode, String userId) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"******************************** provisioningMonitorValidation  *****************************************");
			logger.log(LogStatus.INFO,
					"***************************** provisioningMonitorValidation *********************************");

			try {
						
//					Provisioning Monitor Validation
					ByAttribute.mouseHover("xpath", AdminObjects.adminTabBtn, "Mouse Hover on Admin Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", AdminObjects.adminProvMonitorLnk, "Click on Provisioning Monitor Link");
					Utility.pause(10);
					
					Utility.handleAnnouncementPopup();
					
					if(AGlobalComponents.systemName.equals(""))
					{
						
					}else{
						ByAttribute.setText("xpath", ".//span[contains(@id,'selectedText') and text()='Selected AMAG.']//parent::div//input[@data-ref='inputEl']", AGlobalComponents.systemName, "Enter System");
						Thread.sleep(3000);
						ByAttribute.click("xpath", ".//li[@role='option' and text()='"+AGlobalComponents.systemName+"']", "Click System Option: "+AGlobalComponents.systemName);
						Thread.sleep(10000);
					}
					
					for(int i=0;i<10;i++)
					{
						if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']")).size()>0)
						{
							Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']", "Request Provisioned with Request Number: "+reqNum, false);
							Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']//ancestor::tr//div[@class='x-grid-cell-inner ' and text()='"+provCode+"']", "Request Provisioned with Provisioning Code: "+provCode, false);
							break;
						}else if(driver.findElements(By.xpath(".//div[contains(@class,'x-grid-cell-inner') and text()='"+userId+"']")).size()>0){
							Utility.verifyElementPresent("(.//div[contains(@class,'x-grid-cell-inner') and text()='"+userId+"'])[1]//ancestor::tr//div[text()='"+provCode+"']", "Request Provisioned with Provisioning Code: "+provCode, false);
							break;
						}else{
							ByAttribute.click("xpath", AdminObjects.adminProvMonitorReloadBtn, "Click Provisioning Monitor Refresh button");
							Thread.sleep(3000);
						}
					}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}


	
	
	/**
	* <h1>idmUserValidation</h1>
	* This is Method to Validate User in IDM
	* @author Jiten Khanna
	* @modified
	* @version 1.0
	* @since 2-10-2021
	* @param String requestType
	* @return none
	**/

	public static void idmUserValidation(String userId, String validationTab, String validationKey, String validationStatus) throws Throwable {

	if (unhandledException == false) {
	System.out.println(
	"******************************* idmUserValidation ****************************************");
	logger.log(LogStatus.INFO,
	"**************************** idmUserValidation ********************************");

	try {


	// IDM Validation
	if(driver.findElements(By.xpath(IdentityObjects.idmTabBtn)).size()>0)
	{
		ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on IDM Tab Link");
		Thread.sleep(1000);
		ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity Link");
		Utility.pause(5);
	}else{
		ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Cardholders & Assets Tab Link");
		Thread.sleep(1000);
		ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identities Link");
		Utility.pause(5);
	}

	Utility.handleAnnouncementPopup();

	ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, userId, "Enter User ID in Search field");
	Thread.sleep(3000);
	Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']", "User Available in IDM", false);

	Actions actions = new Actions(driver);
	WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']"));
	actions.doubleClick(elementLocator).perform();
	Thread.sleep(3000);

	ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='"+validationTab+"']", "Click "+validationTab+" Tab");
	Thread.sleep(3000);

	if(validationKey.equals(""))
	{
		Utility.verifyElementPresent("(.//tbody//label[text()='"+validationStatus+"'])[1]", validationTab+" Tab has Value with Status: "+validationStatus, false);
	}else{
		Utility.verifyElementPresent(".//tbody//div[text()='"+validationKey+"']//ancestor::tr//label[text()='"+validationStatus+"']", validationTab+" Tab has "+validationKey+" with Status: "+validationStatus, false);
	}
	Thread.sleep(3000);
	// utility.verifymail("alerthsc@alertenterprise.com", "passw0rd@123", "Approval required","Please log into portal to review and approve this request.");
	} catch (Exception e) {
		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		Utility.recoveryScenario(nameofCurrMethod, e);
	}
	}
	}
	
	/**
	* <h1>validateAccessRequestStatus</h1>
	* This is Method to Validate Access Request Status
	* @author Jiten Khanna
	* @modified
	* @version 1.0
	* @since 12-22-2020
	* @param String requestStatus
	* @return none
	**/

	public static void validateAccessRequestStatus(String requestNumber, String requestStatus, String userFullName) throws Throwable {

	if (unhandledException == false) {
	System.out.println("**************************** validateAccessRequestStatus ********************************");
	logger.log(LogStatus.INFO,"**************************** validateAccessRequestStatus ********************************");
	try {

		if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
		{
			ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
			Utility.pause(5);
		}else{
			ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
			Thread.sleep(1000);
			if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestLnk)).size()>0)
			{
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
			}else{
				ByAttribute.click("xpath", ".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='My Requests']", "Click on Access Request Link");
			}
			Utility.pause(10);
		}

		if(driver.findElements(By.xpath(".//div[contains(@id,'header-title-textEl') and text()='Announcement']")).size()>0)
		{
			driver.findElement(By.xpath(".//div[contains(@id,'header-title-textEl') and text()='Announcement']//parent::div//following-sibling::div[contains(@id,'tool') and @data-qtip='Close Dialog']")).click();
			Thread.sleep(1000);
		}

		if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0)
		{
			System.out.println("Access Request Page Loaded Successfully");
			logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");

			if(driver.findElements(By.xpath(".//tr[1]/td[2]/div[text()='"+requestNumber+"']")).size() > 0)
			{
				System.out.println("Request Found in Table");
				logger.log(LogStatus.PASS, "Request Found in Table");

				if(driver.findElements(By.xpath(".//tr[1]/td[2]/div[text()='"+requestNumber+"']//parent::td//following-sibling::td//label[@class='taggreen' and text()='COMPLETED']")).size()>0)
				{
					Utility.verifyElementPresent(".//tr[1]/td[2]/div[text()='"+requestNumber+"']//parent::td//following-sibling::td//label[@class='taggreen' and text()='COMPLETED']", "Request Status for "+requestNumber+" Changed to COMPLETED", false);
				}

				Actions actions = new Actions(driver);
				WebElement elementLocator = driver.findElement(By.xpath(".//tr[1]/td[2]/div[text()='"+requestNumber+"']"));
				actions.doubleClick(elementLocator).perform();
				Thread.sleep(3000);

				Utility.verifyElementPresent(".//label[@class='x-component x-box-item x-component-activitytext' and text()='"+requestNumber+"']", "Request Page for Request Number: "+requestNumber+" with Status: "+requestStatus, false);

				ByAttribute.click("xpath", ".//a[@role='button' and @data-qtip='Comments']", "Click Comments Button");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//*[@data-ref='btnInnerEl' and text()='Add Comment']", "Comment Added via Self Service", false);
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog Button");
				Thread.sleep(1000);

				ByAttribute.click("xpath", ".//a[@role='button' and @data-qtip='Attachments']", "Click Attachments Button");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//*[contains(@id,'header-title-textEl') and text()='Attachments']", "Attachment Added via Self Service", false);
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog Button");
				Thread.sleep(1000);

				Utility.verifyElementPresent(".//*[@class='tagorange identityBox' and text()='"+userFullName+"']", "Request Created for: "+userFullName, false);
				if(requestStatus.equals("")){

				}else{
					Utility.verifyElementPresent(".//td//label[text()='"+requestStatus+"']", "Request Status: "+requestStatus, false);
				}

				Thread.sleep(2000);
				ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusBtn, "Click View Status Button");
				Thread.sleep(1000);
				Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and contains(text(),'"+requestNumber+"')]", "Workflow Status for Request : "+requestNumber, false);
				if(requestStatus.equalsIgnoreCase("Activate"))
				{
					Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'Approve')]", "Activate Request Approved", false);
				}else if(requestStatus.equalsIgnoreCase("Deactivate")){
					Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'by')]", "Deactivate Request Approved", false);
				}
				ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");

			}else{
				System.out.println("Request Not Found in Table");
				logger.log(LogStatus.FAIL, "Request Not Found in Table");
			}

		} else {
			System.out.println("Navigation to 'Access Request' Page Failed");
			logger.log(LogStatus.FAIL, "Navigation to 'Access Request' Page Failed");
		}

		} catch (Exception e) {
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
			Utility.recoveryScenario(nameofCurrMethod, e);
		}
	}

	}



	/**
	 * <h1>validateAccessRequestStatus</h1> 
	 * This is Method to Validate Access Request Status
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 12-22-2020
	 * @param String requestStatus
	 * @return none
	 **/
	
	public static void validateAccessRequestStatus(String requestNumber, String requestStatus) throws Throwable {

		if (unhandledException == false) {
			System.out.println("**************************** validateAccessRequestStatus ********************************");
			logger.log(LogStatus.INFO,"**************************** validateAccessRequestStatus ********************************");
			try {
				
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
					Utility.pause(5);
				}
				
				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) 
				{
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					if(driver.findElements(By.xpath(".//tr[1]/td[2]/div[text()='"+requestNumber+"']")).size() > 0)
					{
						System.out.println("Request Found in Table");
						logger.log(LogStatus.PASS, "Request Found in Table");
						
						Actions actions = new Actions(driver);
						WebElement elementLocator = driver.findElement(By.xpath(".//tr[1]/td[2]/div[text()='"+requestNumber+"']"));
						actions.doubleClick(elementLocator).perform();
						Thread.sleep(3000);
						
						Utility.verifyElementPresent(".//label[text()='My Requests']//following-sibling::label[contains(text(),'"+requestNumber+"')]", "Request Page for Request Number: "+requestNumber, false);
						Utility.verifyElementPresent(".//td//label[text()='Added']", "Request Status Added", false);
//						Utility.verifyElementPresent(".//td[@data-columnid='workflowActionColumn']//div[text()='"+requestStatus+"']", "Workflow "+requestStatus+" Label", false);
						
					}else{
						System.out.println("Request Not Found in Table");
						logger.log(LogStatus.FAIL, "Request Not Found in Table");
					}

				} else {
					System.out.println("Navigation to 'Access Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Access Request' Page Failed");
				}

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
	}
	

	
	
	/**
	* <h1>createLostorStolenRequest</h1>
	* This is Method to report lost or stolen badge
	* @author Vishal Gupta
	* @modified
	* @version 1.0
	* @since 2-11-2021
	* @param String requestFor
	* @return String
	**/

	public static String createLostOrStolenBadgeRequest(String requestType,String requestFor, String otherUser) throws Throwable {

		String reqNum="";
		if (unhandledException == false) {
			System.out.println("*************************** createLostOrStolenBadgeRequest *******************************");
			logger.log(LogStatus.INFO,"*************************** createLostOrStolenBadgeRequest *******************************");
			try {
		
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
					}else{
						ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
						Thread.sleep(1000);
						if(AGlobalComponents.badgeAdminLogin==true)
							ByAttribute.click("xpath", ".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='My Requests']", "Click on My Request Link");
						else
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
					
							Utility.pause(5);
					}
				
				Utility.handleAnnouncementPopup();
				
					if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0)
					{
						System.out.println("Access Request Page Loaded Successfully");
						logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
						Thread.sleep(1000);
						if(requestFor.equalsIgnoreCase("Others"))
						{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
							Thread.sleep(1000);
						}else{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestSelfRdb, "Click on Self Radio Button");
							Thread.sleep(1000);
						}
					
						ByAttribute.click("xpath", ".//label[text()='Request Type']//following-sibling::div//label[text()='Others']//parent::div//input[@type='radio']", "Click Others Radio Button");
						Thread.sleep(2000);
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSelectRequestTypeTxt, requestType, "Enter Request Type");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//li[@role='option' and text()='"+requestType+"']", "Select Request Type as: "+requestType);
						Thread.sleep(2000);
					
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSearchUserTxt, otherUser, "Enter Other User");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//span[contains(text(),'"+otherUser+"')]", "Select User "+otherUser+" from List");
						Thread.sleep(2000);
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceSelectCardDdn, "clicking on select card dropdown");
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceCardValueLbl, "clicking on card value");
						ByAttribute.clearSetText("xpath",SelfServiceObjects.selfServiceReasonForReplaceTxt , "Lost or Stolen", "entering reason for card replace");
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "Click Comments Button");
						Thread.sleep(1000);
					
						Robot robot = new Robot();
					
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						Thread.sleep(500);
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						Thread.sleep(500);
						robot.keyPress(KeyEvent.VK_A);
						robot.keyPress(KeyEvent.VK_U);
						robot.keyPress(KeyEvent.VK_T);
						robot.keyPress(KeyEvent.VK_O);
					
						robot.keyRelease(KeyEvent.VK_A);
						robot.keyRelease(KeyEvent.VK_U);
						robot.keyRelease(KeyEvent.VK_T);
						robot.keyRelease(KeyEvent.VK_O);
					
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
						Thread.sleep(2000);
						Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Add Comment']", "Add Comment", false);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
						Thread.sleep(1000);
					
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestAttachmentsBtn, "Click Attachments Button");
						Thread.sleep(2000);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestUploadAttachmentBtn, "Cick Upload Attachment Button");
						Thread.sleep(3000);
					
						String uploadFile = System.getProperty("user.dir") + "\\Browser_Files\\Automation.png";
						StringSelection ss = new StringSelection(uploadFile);
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
					
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
					
						Thread.sleep(2000);
						Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Attachments']", "Attachment", false);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
						Thread.sleep(1000);
					
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationSubmitBtn, "Click Submit Button");
						Thread.sleep(10000);
					
						List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
						WebElement latestRequestNumber = requestNumberElements.get(0);
					
						reqNum = latestRequestNumber.getText();
					
					}
					else{
						System.out.println("Unable to Load Access Request Page");
						logger.log(LogStatus.FAIL, "Unable to Load Access Request Page");
					}
			
				}
				catch (Exception e) {
					String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
					Utility.recoveryScenario(nameofCurrMethod, e);
			}
	
		}
		return reqNum;
	}

	
	
	/* <h1>createReplaceBadgeRequest</h1>
	* This is Method to create Replace badge request
	* @author Jiten Khanna
	* @modified
	* @version 1.0
	* @since 2-17-2021
	* @param String requestType,String requestFor, String otherUser
	* @return String
	**/

	public static String createReplaceBadgeRequest(String requestType,String requestFor, String otherUser, String status) throws Throwable {

		String reqNum="";
		if (unhandledException == false) {
			System.out.println("*************************** createReplaceBadgeRequest *******************************");
			logger.log(LogStatus.INFO,"*************************** createReplaceBadgeRequest *******************************");
			try {
		
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
					}else{
						ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
						Thread.sleep(1000);
						if(AGlobalComponents.badgeAdminLogin==true)
							ByAttribute.click("xpath", ".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='My Requests']", "Click on My Request Link");
						else
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
					
							Utility.pause(5);
					}
				
				Utility.handleAnnouncementPopup();
				
					if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0)
					{
						System.out.println("Access Request Page Loaded Successfully");
						logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
						Thread.sleep(1000);
						if(requestFor.equalsIgnoreCase("Others"))
						{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
							Thread.sleep(1000);
						}else{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestSelfRdb, "Click on Self Radio Button");
							Thread.sleep(1000);
						}
					
						ByAttribute.click("xpath", ".//label[text()='Request Type']//following-sibling::div//label[text()='Others']//parent::div//input[@type='radio']", "Click Others Radio Button");
						Thread.sleep(2000);
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSelectRequestTypeTxt, requestType, "Enter Request Type");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//li[@role='option' and text()='"+requestType+"']", "Select Request Type as: "+requestType);
						Thread.sleep(2000);
					
						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSearchUserTxt, otherUser, "Enter Other User");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//span[contains(text(),'"+otherUser+"')]", "Select User "+otherUser+" from List");
						Thread.sleep(2000);
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceSelectCardDdn, "clicking on select card dropdown");
						Thread.sleep(500);
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceCardValueLbl, "clicking on card value");
						Thread.sleep(500);
						ByAttribute.clearSetText("xpath",SelfServiceObjects.selfServiceReasonForReplaceTxt , "Automation Testing", "Enter Reason for card replace");
						Thread.sleep(500);
						ByAttribute.clearSetText("xpath",SelfServiceObjects.selfServiceSelectStatusTxt , status, "Enter Card Status");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//li[@role='option' and text()='"+status+"']", "Select Status as "+status+" from List");
						Thread.sleep(1000);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "Click Comments Button");
						Thread.sleep(1000);
						
						Robot robot = new Robot();
					
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						Thread.sleep(500);
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						Thread.sleep(500);
						robot.keyPress(KeyEvent.VK_A);
						robot.keyPress(KeyEvent.VK_U);
						robot.keyPress(KeyEvent.VK_T);
						robot.keyPress(KeyEvent.VK_O);
					
						robot.keyRelease(KeyEvent.VK_A);
						robot.keyRelease(KeyEvent.VK_U);
						robot.keyRelease(KeyEvent.VK_T);
						robot.keyRelease(KeyEvent.VK_O);
					
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
						Thread.sleep(2000);
						Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Add Comment']", "Add Comment", false);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
						Thread.sleep(1000);
					
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestAttachmentsBtn, "Click Attachments Button");
						Thread.sleep(2000);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestUploadAttachmentBtn, "Cick Upload Attachment Button");
						Thread.sleep(3000);
					
						String uploadFile = System.getProperty("user.dir") + "\\Browser_Files\\Applicant_Photo.jpg";
						StringSelection ss = new StringSelection(uploadFile);
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
					
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
					
						Thread.sleep(4000);
						Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Attachments']", "Attachment", false);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
						Thread.sleep(1000);
					
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationSubmitBtn, "Click Submit Button");
						Thread.sleep(40000);
					
						List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
						WebElement latestRequestNumber = requestNumberElements.get(0);
					
						reqNum = latestRequestNumber.getText();
					
					}
					else{
						System.out.println("Unable to Load Access Request Page");
						logger.log(LogStatus.FAIL, "Unable to Load Access Request Page");
					}
			
				}
				catch (Exception e) {
					String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
					Utility.recoveryScenario(nameofCurrMethod, e);
			}
	
		}
		return reqNum;
	}

		
	
	/**
	* <h1>check Provisioning logs</h1>
	* This is Method to check provisioning logs
	* @author Vishal Gupta
	* @modified
	* @version 1.0
	* @since 2-15-2021
	**/

	public static void checkProvisioningLogs(String msg, String system) throws Throwable {

		if (unhandledException == false) {
		System.out.println("**************************** checking Provisioning logs ********************************");
		logger.log(LogStatus.INFO,"**************************** checking Provisioning Logs ********************************");
		try {
			
			ByAttribute.mouseHover("xpath", AdminObjects.adminTabBtn, "Mouse Hover on Admin Tab Link");
			Thread.sleep(1000);
			ByAttribute.click("xpath", AdminObjects.adminProvMonitorLnk, "Click on Provisioning Monitor Link");
			Utility.pause(10);
			
			Utility.handleAnnouncementPopup();
			
			if(!system.equalsIgnoreCase("AMAG")) {
				ByAttribute.click("xpath", "(//span[@class = 'x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-rowMinus '])[1]", "clickking on remove filter");
			}
	
			for(int i=0;i<20;i++)
				{
					if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ']//ancestor::tr//div[@class='x-grid-cell-inner ' and text()='"+msg+"']")).size()>0)
					{
						Utility.verifyElementPresent("(.//div[@class='x-grid-cell-inner ']//ancestor::tr//div[@class='x-grid-cell-inner ' and text()='"+msg+"'])[1]","checking" + msg, false);
						break;
					}else{
						ByAttribute.click("xpath", AdminObjects.adminProvMonitorReloadBtn, "Click Provisioning Monitor Refresh button");
						Thread.sleep(3000);
					}
				}
			}
		
		catch (Exception e) {
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
			Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	/**
	 * <h1>returnTempBadge</h1> 
	 * This is Method to Return Temporary Badge
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 2-23-2021
	 * @param String requestType
	 * @return none
	 **/

	public static void returnTempBadge(String userId) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"******************************** returnTempBadge  *****************************************");
			logger.log(LogStatus.INFO,
					"***************************** returnTempBadge *********************************");

			try {
				
				
//					IDM Validation
					if(driver.findElements(By.xpath(IdentityObjects.idmTabBtn)).size()>0)
					{
						ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on IDM Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity Link");
						Utility.pause(5);
					}else{
						ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Cardholders & Assets Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identities Link");
						Utility.pause(5);
					}
					
					ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, userId, "Enter User ID in Search field");
					Thread.sleep(3000);
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']", "User ", false);
					
					Actions actions = new Actions(driver);
					WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']"));
					actions.doubleClick(elementLocator).perform();
					Thread.sleep(3000);
					
					ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='Assets']", "Click Assets Tab");
					Thread.sleep(1000);
					
					if(driver.findElements(By.xpath(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']")).size()>0)
					{
						Utility.verifyElementPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']",  "Temporary Badge is Available with User: "+userId, false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//tbody//div[@role='button' and @data-qtip='Return Asset']", "Click Return Asset Button");
						Thread.sleep(1000);
						
						Utility.verifyElementPresent(".//label[@class='x-component x-box-item x-component-activityLabeltext' and text()='Do you want to return badge?']", "Do you want to return badge? Dialog box", false);
						ByAttribute.click("xpath", ".//label[contains(@id,'baseCheckBox') and text()='Shall also unlock permanent badge']", "Check Is unlock Permanent badge Checkbox");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//label[@class='x-component x-box-item x-component-activityLabeltext' and text()='Do you want to return badge?']//ancestor::div[@class='x-window x-layer x-window-default x-closable x-window-closable x-window-default-closable x-border-box']//span[contains(@id,'btnInnerEl') and text()='Confirm']", "Click Confirm Button");
						Thread.sleep(10000);
						
						Utility.verifyElementNotPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']", "Assigned Temporary Badge", true);
						ByAttribute.click("xpath", ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Save']", "Click Save Button");
						Thread.sleep(10000);
						
						//IDM VALIDATION
						ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on IDM Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity Link");
						Utility.pause(5);
						
						ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, userId, "Enter User ID in Search field");
						Thread.sleep(3000);
						Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']", "User", false);
						
						WebElement elementLocator2 = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']"));
						actions.doubleClick(elementLocator2).perform();
						Thread.sleep(3000);
						
						ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='Assets']", "Click Assets Tab");
						Thread.sleep(1000);
						Utility.verifyElementNotPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']", "Returned Temporary Badge", true);
					}else{
						System.out.println("Temporary Badge not assigned to User");
						logger.log(LogStatus.FAIL, "Temporary Badge not assigned to User");
					}
					
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	
	
	
	/**
	 * <h1>assignBadgeIDM</h1> 
	 * This is Method to Return Temporary Badge
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 03-01-2021
	 * @param String requestType
	 * @return none
	 **/

	public static void assignBadgeIDM(String userId,String badgeName,String badgeType) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"******************************** assignBadgeIDM  *****************************************");
			logger.log(LogStatus.INFO,
					"***************************** assignBadgeIDM *********************************");

			try {
				
				
//					IDM Validation
					if(driver.findElements(By.xpath(IdentityObjects.idmTabBtn)).size()>0)
					{
						ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on IDM Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity Link");
						Utility.pause(5);
					}else{
						ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Cardholders & Assets Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identities Link");
						Utility.pause(5);
					}
					
					ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, userId, "Enter User ID in Search field");
					Thread.sleep(3000);
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']", "User Available in IDM", false);
					
					Actions actions = new Actions(driver);
					WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']"));
					actions.doubleClick(elementLocator).perform();
					Thread.sleep(3000);
					
					ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='Assets']", "Click Assets Tab");
					Thread.sleep(1000);
					
					if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityAssetsAddAssetBtn)).size()>0)
					{
						
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsAddAssetBtn, "Click Add Asset Button");
						Thread.sleep(1000);
						ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentityAssetsSelectAssetTxt, badgeName, "Enter Badge Name: "+badgeName);
						Thread.sleep(4000);
						ByAttribute.click("xpath", ".//span[text()='"+badgeName+"']", "Select Badge");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//input[contains(@id,'inputEl') and @placeholder='Select existing permanent badge']//parent::div//following-sibling::div[contains(@class,'arrow-trigger')]", "Click to Select Permanent Asset");
						Thread.sleep(2000);
						
						Robot robot = new Robot();
						
						robot.keyPress(KeyEvent.VK_ENTER);
			            robot.keyRelease(KeyEvent.VK_ENTER);
			            Thread.sleep(500);
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
						Thread.sleep(1000);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsAddAssetConfirmBtn, "Click Confirm Button");
						Thread.sleep(2000);
						Utility.verifyElementPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='"+badgeType+"']", "Assigned Asset Type: "+badgeType, false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Save']", "Click Save Button");
						Thread.sleep(30000);
					}else{
						System.out.println("Add Asset Button is Unavailable");
						logger.log(LogStatus.FAIL, "Add Asset Button is Unavailable");
					}
					
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}


	
	
	
	
	/**
	 * <h1>checkStatus</h1> 
	 * This is Method to check  status after request approval
	 * @author Monika Mehta
	 * @modified
	 * @version 1.0
	 * @since 01-15-2021
	 * @param String firstname,lastname
	 * @return none
	 **/

		public static void checkStatusAfterRequestApproval(String firstName, String parameterToBeModified,String attribute,String scriptName) throws Throwable {
	
		if (unhandledException == false) {
			System.out.println("********* Check status in IDM After request approval*******************");
			logger.log(LogStatus.INFO,"******Check status in IDM After request Approval************");
			
			try {
				Thread.sleep(10000);
				WebElement image=null ;	
				
				if (driver.findElements(By.xpath("//*[contains(@id,'toolEl') and @class='x-tool-tool-el x-tool-img x-tool-close ']")).size()>0)
					ByAttribute.click("xpath", "//*[contains(@id,'toolEl') and @class='x-tool-tool-el x-tool-img x-tool-close ']", "close the popup");
				
				if(AGlobalComponents.RequestSubmit){
					logger.log(LogStatus.INFO, "Checking status of the user after request submission");
									
					HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource(scriptName);
					
					String requestType = (String) testData1.get("request_type");
					
					/*
					 * Validating employee offboarding details after request approval
					 */
	
					if(requestType.equalsIgnoreCase("Employee Leave Of Absence")){
						
//						IDM Validation
						if(driver.findElements(By.xpath(IdentityObjects.cardHoldersAndAssetsTabBtn)).size()>0){
							ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identity ");
							Utility.pause(15);
						}
						else{
							ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity ");
							Utility.pause(15);
							
						}
						ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
						Thread.sleep(3000);
						Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']", "User Available in IDM", false);
						
						Actions actions = new Actions(driver);
						WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']"));
						actions.doubleClick(elementLocator).perform();
						Thread.sleep(3000);
						
						logger.log(LogStatus.PASS, "User is present in IDM");
						
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
						Utility.pause(5);
						
						/*system tab*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Navigate to systems tab");
						gettingIndexOfIDMSystemsTab();
						List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
						int size = noOfRows.size();
						for (int i=1;i<=size;i++){
							WebElement systemName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div"));
							String systemAssigned = systemName.getText();
							if(driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div")).size()>0){
								Utility.verifyElementPresent("//*[text()='"+systemAssigned+"']/parent::td/following-sibling::td/descendant::*[text()='Inactive']", "System Status InActive", false);
								logger.log(LogStatus.PASS,"status of the system assigned to the Employee : "+systemAssigned+ " is inactive");
							}
							else
								logger.log(LogStatus.FAIL,"status of the system assigned to the Employee : "+systemAssigned+ " is not Inactive");
					
						}
						
						/*Assets tab*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Navigate to Assets tab");
							
						getIndexOfIDMAssetsHeaders();
						if(driver.findElements(By.xpath("//div[text()='"+AGlobalComponents.badgeId+"']")).size()>0){
							WebElement assetStatus = driver.findElement(By.xpath("//div[text()='"+AGlobalComponents.badgeId+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div"));
							String assetAssignmentStatus = assetStatus.getText();
							logger.log(LogStatus.INFO,"current assignment status of asset is : "+assetAssignmentStatus);
							Utility.verifyElementPresent("//div[text()='"+AGlobalComponents.badgeId+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div", "asset status", false);
							if(Utility.compareStringValues(assetAssignmentStatus, "INACTIVE"))
								logger.log(LogStatus.PASS, "asset status is inactive after offboarding");
							else
								logger.log(LogStatus.FAIL, "asset status is not deactivated");
						}
						else{
							logger.log(LogStatus.INFO, "asset not assigned to the user");
						}		 
					}
					
					/*
					 * Validating employee rehire details after request approval
					 */
					if(requestType.equalsIgnoreCase("Employee Rehire")){
						
//						IDM Validation
						if(driver.findElements(By.xpath(IdentityObjects.cardHoldersAndAssetsTabBtn)).size()>0){
							ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identity ");
							Utility.pause(15);
						}
						else{
							ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity ");
							Utility.pause(15);
							
						}
						ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
						Thread.sleep(3000);
						Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']", "User Available in IDM", false);
						
						Actions actions = new Actions(driver);
						WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']"));
						actions.doubleClick(elementLocator).perform();
						Thread.sleep(3000);
						
						logger.log(LogStatus.PASS, "Employee Onboardidng done is successful , user is present in IDM");
						
						/*Validating access assigned to the user*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						Utility.verifyElementPresent("//*[text()='"+access1ForEmpOnboarding+"']", access1ForEmpOnboarding, false);
						logger.log(LogStatus.PASS, "Accesses are successfully assigned to the user");
						
						 /* checking for asset added by badge admin at time of approval
						 */
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
						Utility.verifyElementPresent("//*[text()='"+AGlobalComponents.badgeId+"']", "badgeId", false);
						logger.log(LogStatus.PASS, "Asset is assigned to the user with asset Id : "+AGlobalComponents.badgeId);
						
						
						/*
						 * checking for Systems CCURE and IDM added on the basis of Accesses
						 */
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Click on Systems Tab ");
						Utility.verifyElementPresent(IdentityObjects.idmManageIdentitySystemsTabBtn, "Systems", false);
								
					}
					if(requestType.equalsIgnoreCase("Employee Offboarding")){
						
//						IDM Validation
						if(driver.findElements(By.xpath(IdentityObjects.cardHoldersAndAssetsTabBtn)).size()>0){
							ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identity ");
							Utility.pause(15);
						}
						else{
							ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity ");
							Utility.pause(15);
							
						}
						ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
						Thread.sleep(3000);
						Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']", "User Available in IDM", false);
						
						Actions actions = new Actions(driver);
						WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']"));
						actions.doubleClick(elementLocator).perform();
						Thread.sleep(3000);
						
						logger.log(LogStatus.PASS, "User is present in IDM");
						
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
						Utility.pause(5);
						
						
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
						Utility.pause(5);
						
						/*system tab*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Navigate to systems tab");
						gettingIndexOfIDMSystemsTab();
						List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
						int size = noOfRows.size();
						for (int i=1;i<=size;i++){
							WebElement systemName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div"));
							String systemAssigned = systemName.getText();
							if(driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div")).size()>0){
								Utility.verifyElementPresent("//*[text()='"+systemAssigned+"']/parent::td/following-sibling::td/descendant::*[text()='Inactive']", "System Status InActive", false);
								logger.log(LogStatus.PASS,"status of the system assigned to the Employee : "+systemAssigned+ " is inactive");
							}
							else
								logger.log(LogStatus.FAIL,"status of the system assigned to the Employee : "+systemAssigned+ " is not Inactive");
					
						}
						
						/*access tab*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Navigate to access tab");
						if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0){
							logger.log(LogStatus.PASS, "Accesses are removed");
							Utility.verifyElementPresent(IdentityObjects.emptyGrid, "No records found", false);
							
						}
						else{
							logger.log(LogStatus.FAIL, "Accesses are still assigned to the Employee");
						}
						
						/*Assets tab*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Navigate to Assets tab");
							
						getIndexOfIDMAssetsHeaders();
						if(driver.findElements(By.xpath("//div[text()='"+AGlobalComponents.badgeId+"']")).size()>0){
							WebElement assetStatus = driver.findElement(By.xpath("//div[text()='"+AGlobalComponents.badgeId+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div"));
							String assetAssignmentStatus = assetStatus.getText();
							logger.log(LogStatus.INFO,"current assignment status of asset is : "+assetAssignmentStatus);
							Utility.verifyElementPresent("//div[text()='"+AGlobalComponents.badgeId+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div", "asset status", false);
							if(Utility.compareStringValues(assetAssignmentStatus, "INACTIVE"))
								logger.log(LogStatus.PASS, "asset status is inactive after offboarding");
							else
								logger.log(LogStatus.FAIL, "asset status is not deactivated");
						}
						else{
							logger.log(LogStatus.INFO, "asset not assigned to the user");
						}		 
					}
					
						else if(requestType.equalsIgnoreCase("Employee Modification")){
						
//						IDM Validation
						if(driver.findElements(By.xpath(IdentityObjects.cardHoldersAndAssetsTabBtn)).size()>0){
							ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identity ");
							Utility.pause(15);
						}
						else{
							ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity ");
							Utility.pause(15);
							
						}
						ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
						Thread.sleep(3000);
						Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']", "User Available in IDM", false);
						
						Actions actions = new Actions(driver);
						WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']"));
						actions.doubleClick(elementLocator).perform();
						Thread.sleep(3000);
						
						logger.log(LogStatus.PASS, "User is present in IDM");
						
						String modifiedJobTitle=attribute;
						String newJobTitle = driver.findElement(By.xpath(HomeObjects.homeAccessRequestLastNameTxt)).getAttribute("value");
						boolean flag=true;
						for(int i=0;i<5 && flag;i++){
							if(Utility.compareStringValues(newJobTitle, modifiedJobTitle)){
								logger.log(LogStatus.PASS, "LastName changed successfully ");
								Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestLastNameTxt, "Modified LastName", true, false);
								flag=false;
							}
							else{
								ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
								Utility.pause(1);
								ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
								Utility.pause(5);
								newJobTitle = driver.findElement(By.xpath("HomeObjects.homeAccessRequestLastName")).getAttribute("value");
							}
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Change LastName failed ");
					}
					
					else if(requestType.equalsIgnoreCase("Transfer")){
						
//						IDM Validation
						if(driver.findElements(By.xpath(IdentityObjects.cardHoldersAndAssetsTabBtn)).size()>0){
							ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identity ");
							Utility.pause(15);
						}
						else{
							ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity ");
							Utility.pause(15);
							
						}
						ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
						Thread.sleep(3000);
						Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']", "User Available in IDM", false);
						
						Actions actions = new Actions(driver);
						WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']"));
						actions.doubleClick(elementLocator).perform();
						Thread.sleep(3000);
						
						logger.log(LogStatus.PASS, "User is present in IDM");
						
						/*Validating location assigned to the user*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLocationTabBtn, "Click on Location Tab ");
						Utility.verifyElementPresent("//*[text()='"+locationName+"']", locationName, false);
						
						
						/*Validating access assigned to the user*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						Utility.verifyElementPresent("//*[text()='"+accessNewForChangeJobTitle+"']", accessNewForChangeJobTitle, false);
						
					}
					
					if(requestType.equalsIgnoreCase("Employee Onboarding")){
						
//						IDM Validation
						if(driver.findElements(By.xpath(IdentityObjects.cardHoldersAndAssetsTabBtn)).size()>0){
							ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identity ");
							Utility.pause(15);
						}
						else{
							ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity ");
							Utility.pause(15);
							
						}
						ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
						Thread.sleep(3000);
						Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']", "User Available in IDM", false);
						
						Actions actions = new Actions(driver);
						WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']"));
						actions.doubleClick(elementLocator).perform();
						Thread.sleep(3000);
						
						logger.log(LogStatus.PASS, "Employee Onboardidng done is successful , user is present in IDM");
						
						/*Validating access assigned to the user*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						Utility.verifyElementPresent("//*[text()='"+access1ForEmpOnboarding+"']", access1ForEmpOnboarding, false);
						Utility.verifyElementPresent("//*[text()='"+access2ForEmpOnboarding+"']", access2ForEmpOnboarding, false);
						logger.log(LogStatus.PASS, "Accesses are successfully assigned to the user");
						
						 /* checking for asset added by badge admin at time of approval
						 */
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
						Utility.verifyElementPresent("//*[text()='"+AGlobalComponents.badgeId+"']", "badgeId", false);
						logger.log(LogStatus.PASS, "Asset is assigned to the user with asset Id : "+AGlobalComponents.badgeId);
						
						
						/*
						 * checking for Systems CCURE and IDM added on the basis of Accesses
						 */
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Click on Systems Tab ");
						Utility.verifyElementPresent(IdentityObjects.idmManageIdentitySystemsTabBtn, "Systems", false);
								
					}			
					
					if(Utility.compareStringValues(requestType, "Employement Type Conversion")) {						
						
						String employeeType =  (String) testData1.get("employee_type");
						String accessName =  (String) testData1.get("access_name_1");
						
//						IDM Validation
						if(driver.findElements(By.xpath(IdentityObjects.cardHoldersAndAssetsTabBtn)).size()>0){
							ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identity ");
							Utility.pause(15);
						}
						else{
							ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on Identity tab");
							Utility.pause(5);
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity ");
							Utility.pause(15);
							
						}
						ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
						Thread.sleep(3000);
						Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']", "User Available in IDM", false);
						
						Actions actions = new Actions(driver);
						WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']"));
						actions.doubleClick(elementLocator).perform();
						Thread.sleep(3000);
						
						String newEmpType = driver.findElement(By.xpath(HomeObjects.homeAccessRequestEmpTypeTxt)).getAttribute("value");
						if(Utility.compareStringValues(newEmpType, employeeType)){
							Utility.verifyElementPresent(HomeObjects.homeAccessRequestEmpTypeTxt, "Employee Type", false);
							logger.log(LogStatus.PASS, "Employee Type Modified to "+employeeType);
						}
						else
							logger.log(LogStatus.FAIL, "Employee Type not modified to Permanent");
						
						/*Validating access assigned to the user*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						Utility.verifyElementPresent("//*[text()='"+accessName+"']", accessName, false);
						logger.log(LogStatus.PASS, "Accesses are successfully assigned to the user");
						
						 /* checking for asset added by badge admin at time of approval
						 */
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
						Utility.verifyElementPresent("//*[text()='"+AGlobalComponents.badgeId+"']", "badgeId", false);
						logger.log(LogStatus.PASS, "Asset is assigned to the user with asset Id : "+AGlobalComponents.badgeId);
						
					}
					
					//modified identity validation
					if(Utility.compareStringValues(requestType, "Update Identity")){
						String accessAdded=attribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						String parameterToBeUpdated=(String) testData1.get("parameter_tobemodified");
						switch(parameterToBeUpdated.toLowerCase()){
						case "photo":
							ByAttribute.click("xpath", IdentityObjects.idmMAnageIdentityExpandLeftViewLnk, "Expand left view");
							logger.log(LogStatus.INFO, "Capturing updated photo");
							File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					        Thread.sleep(3000);
					        String path = System.getProperty("user.dir") + "\\Browser_Files\\ModifiedPhoto.jpg";
					        fileOutPut = new File(path);
					        if(fileOutPut.exists())
								fileOutPut.delete();
					        System.out.println("File Path: "+path);
					        FileUtils.copyFile(screenshot, new File(path));  
					        fileOutPut = new File(path);
			                
				            BufferedImage bufferfileInput = ImageIO.read(fileInput);
			                DataBuffer bufferfileInput1 = bufferfileInput.getData().getDataBuffer();
			                int sizefileInput = bufferfileInput1.getSize();                     
			                BufferedImage bufferfileOutPut = ImageIO.read(fileOutPut);
			                DataBuffer datafileOutPut = bufferfileOutPut.getData().getDataBuffer();
			                int sizefileOutPut = datafileOutPut.getSize();
			                Boolean matchFlag = true;
			                if(sizefileInput == sizefileOutPut) {                         
			                   for(int i=0; i<sizefileInput; i++) {
			                         if(bufferfileInput1.getElem(i) != datafileOutPut.getElem(i)) {
			                               matchFlag = false;
			                               break;
			                         }
			                    }
			                }
			                else                            
			                   matchFlag = false;
			          
							if(matchFlag){
								if(AGlobalComponents.updatePhoto)
									logger.log(LogStatus.FAIL, "image is not modified");
								else
									logger.log(LogStatus.FAIL, "image is not uploaded");
							}
								
							else{
								if(AGlobalComponents.updatePhoto){
									logger.log(LogStatus.PASS, "image is updated successfully");
									Utility.verifyElementPresent(IdentityObjects.imageLnk, "new image", false);
								}
								else{
									logger.log(LogStatus.PASS, "image is uploaded successfully");
									Utility.verifyElementPresent(IdentityObjects.imageLnk, "uploaded image", false);
								}
							}
			                fileInput.delete();
			                fileOutPut.delete();
			            	break;
						case "lastname":
													
							String newLastName = driver.findElement(By.xpath(IdentityObjects.idmManageIdentityProfileInfoLastNameTxt)).getAttribute("value");
							
							if(Utility.compareStringValues(newLastName, modifiedLastName)){
								logger.log(LogStatus.PASS, "Last name modified successfully ");
								Utility.verifyElementPresent(IdentityObjects.idmManageIdentityProfileInfoLastNameTxt, "Modified last name", false);
							}
							else{
									logger.log(LogStatus.FAIL, "Last name modification failed ");
							}
			            	break;
						case "phoneno":
							String newPhoneNo = driver.findElement(By.xpath(IdentityObjects.idmManageIdentityProfileInfoPhoneNoTxt)).getAttribute("value");
							if(Utility.compareStringValues(newPhoneNo, String.valueOf(modifiedPhoneNumber))){
								logger.log(LogStatus.PASS, "phone number modified successfully ");
								Utility.verifyElementPresentByScrollView(IdentityObjects.idmManageIdentityProfileInfoPhoneNoTxt, "Modified phone number",true, false);
								
							}
							else{
								logger.log(LogStatus.FAIL, "Phone number modification failed ");
							}
			            	break;
			            	
						case "department":
							String deptName = driver.findElement(By.xpath(".//input[contains(@id,'baseComboBoxRemote') and @placeholder='Select Department Name']")).getAttribute("value");
							if(Utility.compareStringValues(deptName, departmentName)){
								Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestDepartmentDdn, "Modified department",true, false);
								logger.log(LogStatus.PASS, "Department of temp worker is modified to : "+deptName);
								
							}
							else
								logger.log(LogStatus.FAIL, "Deaprtment name not modified");
							/*Validating access assigned to the user*/
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
							if(Utility.verifyElementPresentReturn("//*[text()='"+accessAdded+"']", accessAdded,true, false))
									logger.log(LogStatus.PASS, "Accesses are successfully assigned to the user");
							else
								logger.log(LogStatus.FAIL, "Required access not assigned to the temp worker");
							break;
						case "addaccess":
							accessAdded=(String) testData1.get("access_name_1");
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
							List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
							int size = noOfAccessRows.size();
							boolean flag=true;
							for (int i=1;i<=size && flag;i++){
								WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
								String accessAssigned = accessName.getText();
								if(Utility.compareStringValues(accessAssigned, accessAdded)){
									logger.log(LogStatus.PASS, "Access is successfully assigned to the user");
									Utility.verifyElementPresent("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]", "Newly assigned access", false);
									flag=false;
								}
								
							}
							if(flag)
								logger.log(LogStatus.FAIL, "Access is not assigned to the user");
			            	break;
						case "removeaccess":
							String accessRemoved=(String) testData1.get("pre_assigned_access_1");
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
							noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
							size = noOfAccessRows.size();
							flag=true;
							if(size==0){
								logger.log(LogStatus.PASS, "Access assigned to the user is removed . User has no access at present. ");
								Utility.verifyElementPresent(IdentityObjects.emptyGrid, "Empty Grid", false);
							}
							for (int i=1;i<=size && flag;i++){
								WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
								String accessAssigned = accessName.getText();
								if(Utility.compareStringValues(accessAssigned, accessRemoved)){
									logger.log(LogStatus.FAIL, "Access is not removed . ");
									Utility.verifyElementPresent("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]", "Newly assigned access", false);
									flag=false;
								}
								
							}
							
			            	break;
			            	
			            default:
			            	System.out.println("No parameter updation request is received");
							break;	
						
						}
						
					}	
					//Asset Status Validation 
					if(Utility.compareStringValues(requestType,"Wellness Check")){	
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
						WebElement assetStatus = driver.findElement(By.xpath("//tr//td["+assignmentStatusIndex+"]//div[@class='x-grid-cell-inner ']//label"));
						newAssetStatus=assetStatus.getText();
						Utility.verifyElementPresent("//tr//td["+assignmentStatusIndex+"]//div[@class='x-grid-cell-inner ']//label", "Asset Assignemnt status", false);
					
						logger.log(LogStatus.INFO, "Current asset Assignment status is : " +newAssetStatus);
						//if condition lagani hai ki IDM me bhi same status hai jo request open karne pe tha and before status se opposite hai hence pass
						logger.log(LogStatus.PASS, "Current asset Assignment status in IDM is  : " +newAssetStatus);
					}
					
					
					if(Utility.compareStringValues(requestType,"Request Location Access")){
						String accessAdded=attribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						boolean flag=true;
						for (int i=1;i<=size && flag;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							if(Utility.compareStringValues(accessAssigned, accessAdded)){
								logger.log(LogStatus.PASS, "Access is successfully assigned to the user");
								Utility.verifyElementPresent("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]//div[text()='"+accessAdded+"']", "Newly assigned access", false);
								flag=false;
							}
							
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Access is not assigned to the user");
					}
					
					
					/*
					 *  Validation after Position Access
					 */
					if(Utility.compareStringValues(requestType,"Position Access")){
						String accessAdded=attribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						boolean flag=true;
						for (int i=1;i<=size && flag;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							if(Utility.compareStringValues(accessAssigned, accessAdded)){
								logger.log(LogStatus.PASS, "Access is successfully assigned to the user");
								Utility.verifyElementPresent("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]", "Newly assigned access", false);
								flag=false;
							}
							
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Access is not assigned to the user");
					}
					
					/*
					 *  Validation after Application Access
					 */
					if(Utility.compareStringValues(requestType,"Application Access")){
						String accessAdded=attribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						boolean flag=true;
						for (int i=1;i<=size && flag;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							if(Utility.compareStringValues(accessAssigned, accessAdded)){
								logger.log(LogStatus.PASS, "Access is successfully assigned to the user");
								Utility.verifyElementPresent("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]", "Newly assigned access", false);
								flag=false;
							}
							
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Access is not assigned to the user");
					}
					
					
					/*
					 *  Validation after IT Access
					 */
					if(Utility.compareStringValues(requestType,"IT Access")){
						String accessAdded=attribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						boolean flag=true;
						for (int i=1;i<=size && flag;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							if(Utility.compareStringValues(accessAssigned, accessAdded)){
								logger.log(LogStatus.PASS, "Access is successfully assigned to the user");
								Utility.verifyElementPresent("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]", "Newly assigned access", false);
								flag=false;
							}
							
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Access is not assigned to the user");
					}
					
					
					/*
					 *  Validation after employment type conversion
					 */
					if(Utility.compareStringValues(requestType, "Employment Type Conversion")){
						String accessAdded=attribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						String newEmpType = driver.findElement(By.xpath(HomeObjects.homeAccessRequestEmpTypeTxt)).getAttribute("value");
						if(Utility.compareStringValues(newEmpType, "Permanent")){
							Utility.verifyElementPresent(HomeObjects.homeAccessRequestEmpTypeTxt, "New Emp Type", false);
							logger.log(LogStatus.PASS, "Employee Type has been modified to Permanent");
						}
						else
							logger.log(LogStatus.FAIL, "Employee Type not modified to Permanent");
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						for (int i=1;i<=size;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							permanentAccessNames.add(i-1, accessAssigned);
//							if(Utility.compareStringValues(accessAssigned, accessAdded))
//								logger.log(LogStatus.PASS, "Access is successfully assigned to the user");
//							else
//								logger.log(LogStatus.FAIL, "access assignment failed");
						}
						
						/** compare contractor and permanent access **/
						int flag = 0;
						int noOfPermanentAccess = permanentAccessNames.size() , noOfContractorAccess = contractorAccessNames.size();
						for(int i=0;i<noOfPermanentAccess;i++ ){
							for(int j=0;j<noOfContractorAccess;j++){
								if(Utility.compareStringValues(contractorAccessNames.get(j), permanentAccessNames.get(i))){
									System.out.println("Previuosly existing access is there with permanent employee : "+ permanentAccessNames.get(i));
								}
								else{
									System.out.println("New access is there with permanent employee : "+ permanentAccessNames.get(i));
									flag++;
								}
							}
						}
						if (flag>0){
							logger.log(LogStatus.PASS, "new accesses are granted to the permanent employee");
							logger.log(LogStatus.INFO, "Accessess are:");
							for (int i=0;i<noOfPermanentAccess;i++){
								logger.log(LogStatus.INFO,(i+1)+"."+ permanentAccessNames.get(i));
								System.out.println((i+1)+"."+ permanentAccessNames.get(i));
							}
						}
						else{
							logger.log(LogStatus.FAIL, "There is no changes in the accesses assigned to the user ");
						}
					}
					
					/*
					 * Validating Temp worker details in IDM after onboarding 
					 * 
					 */
					
					if(Utility.compareStringValues(requestType, "Temp Worker Onboarding")){
						int noOfAccess=0 , noOfSystem=0,noOfAsset=0,size=0;
						FB_Automation_CommonMethods.searchIdentity(AGlobalComponents.userId);
						logger.log(LogStatus.PASS, "Temporary Onboardidng done is successful , user is present in IDM");
						
						/*Validating access assigned to the user*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
							logger.log(LogStatus.INFO, "No Access is assigned to the user ");
						else{
							List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
							size=noOfRows.size();
							noOfAccess = size;
							if(noOfAccess>0){
								logger.log(LogStatus.INFO, "3 accesses are assigned to the onboarded user");
								Utility.verifyElementPresent("//*[text()='"+access1ForTempOnboarding+"']", access1ForTempOnboarding, false);
								Utility.verifyElementPresent("//*[text()='"+access2ForTempOnboarding+"']", access2ForTempOnboarding, false);
								Utility.verifyElementPresent("//*[text()='"+access3ForTempOnboarding+"']", access3ForTempOnboarding, false);
								logger.log(LogStatus.PASS, "Accesses are successfully assigned to the user");
							}
							else
								logger.log(LogStatus.FAIL, "Accesses are not assigned to the onboarded user as expected");
						}
						
							
						/*
						 * checking for Systems added 
						 */
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Click on Systems Tab ");
						List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
						size = noOfRows.size();
						noOfSystem=size-noOfAccess;
						int index=noOfAccess+1;
						if(noOfSystem>0){
							logger.log(LogStatus.INFO, "systems are assigned to the onboarded user");
							if(driver.findElements(By.xpath("(//div[@class='x-action-col-icon x-action-col-0  aegrid-rowAdd'])["+index+"]//ancestor::tr//*[text()='"+system1ForTempOnboarding+"']")).size()>0)
								Utility.verifyElementPresent("(//div[@class='x-action-col-icon x-action-col-0  aegrid-rowAdd'])["+index+"]//ancestor::tr//*[text()='"+system1ForTempOnboarding+"']", system1ForTempOnboarding, false);
							if(driver.findElements(By.xpath("(//div[@class='x-action-col-icon x-action-col-0  aegrid-rowAdd'])["+index+"]//ancestor::tr//*[text()='"+system2ForTempOnboarding+"']")).size()>0)
								Utility.verifyElementPresent("(//div[@class='x-action-col-icon x-action-col-0  aegrid-rowAdd'])["+index+"]//ancestor::tr//*[text()='"+system2ForTempOnboarding+"']", system2ForTempOnboarding, false);
							index++;
							if(driver.findElements(By.xpath("(//div[@class='x-action-col-icon x-action-col-0  aegrid-rowAdd'])["+index+"]//ancestor::tr//*[text()='"+system1ForTempOnboarding+"']")).size()>0)
								Utility.verifyElementPresent("(//div[@class='x-action-col-icon x-action-col-0  aegrid-rowAdd'])["+index+"]//ancestor::tr//*[text()='"+system1ForTempOnboarding+"']", system1ForTempOnboarding, false);
							if(driver.findElements(By.xpath("(//div[@class='x-action-col-icon x-action-col-0  aegrid-rowAdd'])["+index+"]//ancestor::tr//*[text()='"+system2ForTempOnboarding+"']")).size()>0)
								Utility.verifyElementPresent("(//div[@class='x-action-col-icon x-action-col-0  aegrid-rowAdd'])["+index+"]//ancestor::tr//*[text()='"+system2ForTempOnboarding+"']", system2ForTempOnboarding, false);
								logger.log(LogStatus.PASS, "Systems are successfully assigned to the user");
						}
						else
							logger.log(LogStatus.FAIL, "Systems are not assigned to the onboarded user as expected");

						 /* checking for asset added by badge admin at time of approval
						 */
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
						noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
						size = noOfRows.size();
						noOfAsset=size-(noOfAccess+noOfSystem);
						index=noOfAccess+noOfSystem+1;
						if(noOfAsset>0){
							Utility.verifyElementPresent("//*[text()='"+AGlobalComponents.assetCode+"']", "Asset", false);
							logger.log(LogStatus.PASS, "Asset is assigned to the user with asset code : "+AGlobalComponents.assetCode);
						}
						else			
							logger.log(LogStatus.FAIL, "Asset is not assigned to the user");
					}
					
					if(Utility.compareStringValues(requestType, "Temp Worker Modification")){
						String accessAdded=attribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						
						switch(parameterToBeModified.toLowerCase()){
						case "lastname":
							String newLastName = driver.findElement(By.xpath(HomeObjects.homeAccessRequestLastNameTxt)).getAttribute("value");
							boolean flag=true;
							if(Utility.compareStringValues(newLastName, modifiedLastName)){
								logger.log(LogStatus.PASS, "Last name modified successfully ");
								Utility.verifyElementPresent(HomeObjects.homeAccessRequestLastNameTxt, "Modified last name", false);
								flag=false;
							}
							else
								logger.log(LogStatus.FAIL, "Last name modification failed ");
							break;
						case "department":
							String deptName = driver.findElement(By.xpath(".//input[contains(@id,'baseComboBoxRemote') and @placeholder='Select Department Name']")).getAttribute("value");
							if(Utility.compareStringValues(deptName, departmentName)){
								Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestDepartmentDdn, "Modified department",true, false);
								logger.log(LogStatus.PASS, "Department of temp worker is modified to : "+deptName);
								flag=false;
							}
							else
								logger.log(LogStatus.FAIL, "Deaprtment name not modified");
							/*Validating access assigned to the user*/
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
							if(Utility.verifyElementPresentReturn("//*[text()='"+accessAdded+"']", accessAdded,true, false))
									logger.log(LogStatus.PASS, "Accesses are successfully assigned to the user");
							else
								logger.log(LogStatus.FAIL, "Required access not assigned to the temp worker");
							break;
						default:
							System.out.println("No parameter updation request is received");
						}
						
						
					}
					
					/* Validating for Temp worker offboarding case */
					
					if (Utility.compareStringValues(requestType, "Temp Worker Offboarding")){
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						
						/*system tab*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Navigate to systems tab");
						gettingIndexOfIDMSystemsTab();
						 for (int i=1;i<=systemsAssignedToUser.size();i++){
								WebElement systemName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div"));
								String systemAssigned = systemName.getText();
								if(driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div")).size()>0){
									Utility.verifyElementPresent("(.//label[@class='tagred' and text()='Inactive'])["+i+"]", "System Status InActive", false);
									logger.log(LogStatus.PASS,"status of the system assigned to the temporary worker : "+systemAssigned+ " is inactive");
								}
								else
									logger.log(LogStatus.FAIL,"status of the system assigned to the temporary worker : "+systemAssigned+ " is  active");
							}
						
						 /*access tab*/
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Navigate to access tab");
							if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0){
								logger.log(LogStatus.PASS, "Accesses are removed");
								Utility.verifyElementPresent(IdentityObjects.emptyGrid, "No records found", false);
								
							}
							else{
								logger.log(LogStatus.FAIL, "Accesses are still assigned to the temporary worker");
							}
							
							/*Assets tab*/
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Navigate to Assets tab");
							
							getIndexOfIDMAssetsHeaders();
							if(driver.findElements(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']")).size()>0){
								WebElement assetStatus = driver.findElement(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div"));
								String assetAssignmentStatus = assetStatus.getText();
								logger.log(LogStatus.INFO,"current assignment status of asset is : "+assetAssignmentStatus);
								Utility.verifyElementPresent("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div", "asset status", false);
								if(Utility.compareStringValues(assetAssignmentStatus, "INACTIVE"))
									logger.log(LogStatus.PASS, "asset status is inactive after offboarding");
								else
									logger.log(LogStatus.FAIL, "asset status is not deactivated");
								}
							
							else{
								logger.log(LogStatus.INFO, "asset not assigned to the user");
							}
					}
					
					
					/* Validating for Temp worker rehiring case */
					
					if (Utility.compareStringValues(requestType, "Temp Worker Rehire")){
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						
						/*system tab*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Navigate to systems tab");
						gettingIndexOfIDMSystemsTab();
						HashMap<String, Comparable> testData = Utility.getDataFromDatasource(scriptName);
						systemsAssignedToUser.add(2,(String) testData.get("system_name"));
						for (int i=1;i<=systemsAssignedToUser.size();i++){
								WebElement systemName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div"));
								String systemAssigned = systemName.getText();
								WebElement systemStatus = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]//following-sibling::td//label"));
								String sysStatus = systemStatus.getText();
								if(Utility.compareStringValues(sysStatus.toUpperCase(),"ACTIVE")){
									Utility.verifyElementPresent("(.//label[@class='taggreen' and text()='Active'])["+i+"]", "System Status is Active", false);
									logger.log(LogStatus.PASS,"status of the system assigned to the temporary worker : "+systemAssigned+ " is active");
								}
								else
									logger.log(LogStatus.FAIL,"status of the system assigned to the temporary worker : "+systemAssigned+ " is Inactive");
						}
						
						 /*access tab*/
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Navigate to access tab");
							if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0){
								logger.log(LogStatus.FAIL, "Accesses are not assigned to the temporary worker after rehiring");
							}
							else{
								
								String access1=(String) testData.get("access_name_1");
								String access2=(String) testData.get("access_name_2");
																
								Utility.verifyElementPresent("//*[text()='"+access1+"']", access1, false);
								Utility.verifyElementPresent("//*[text()='"+access2+"']", access2, false);
																
								logger.log(LogStatus.PASS, "Accesses are assigned to the temporary worker after rehiring");
								
							}
							
							/*Assets tab*/
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Navigate to Assets tab");
							
							getIndexOfIDMAssetsHeaders();
							if(driver.findElements(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']")).size()>0){
								WebElement assetStatus = driver.findElement(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div"));
								String assetAssignmentStatus = assetStatus.getText();
								logger.log(LogStatus.INFO,"current assignment status of asset is : "+assetAssignmentStatus);
								Utility.verifyElementPresent("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div", "asset status", false);
								if(Utility.compareStringValues(assetAssignmentStatus, "INACTIVE"))
									logger.log(LogStatus.FAIL, "asset status is inactive after rehiring");
								else
									logger.log(LogStatus.PASS, "asset status of newly asigned asset is Active");
								}
							
							else{
								logger.log(LogStatus.INFO, "asset not assigned to the user");
							}
					}
					
					
					/* Validating for emergency termination case */
					
					if(Utility.compareStringValues(requestType, "Emergency Termination")){
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						if(driver.findElements(By.xpath(IdentityObjects.idmManageIdentityReloadBtn)).size()>0){
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
							Utility.pause(5);
						}
						else{
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityCancelBtn, "Click on Cancel Button ");
							ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, AGlobalComponents.userId, "Enter User ID in Search field");
							Thread.sleep(3000);
							if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.userId+"']")).size()>0){
								WebElement record=driver.findElement(By.xpath("(//div[text()='"+AGlobalComponents.userId+"']/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
								Actions action = new Actions(driver);
								action.doubleClick(record).build().perform();
								Utility.pause(10);
							}
						}
						
						/*system tab*/
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Navigate to systems tab");
						gettingIndexOfIDMSystemsTab();
						 for (int i=1;i<=systemsAssignedToUser.size();i++){
								WebElement systemName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div"));
								String systemAssigned = systemName.getText();
								if(driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div")).size()>0){
									Utility.verifyElementPresent("(.//label[@class='tagred' and text()='Inactive'])["+i+"]", "System Status InActive", false);
									logger.log(LogStatus.PASS,"status of the system assigned to the user : "+systemAssigned+ " is inactive");
								}
								else
									logger.log(LogStatus.FAIL,"status of the system assigned to the user : "+systemAssigned+ " is not active");
							}
						
						 /*access tab*/
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Navigate to access tab");
							if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0){
								logger.log(LogStatus.PASS, "Accesses are removed");
								Utility.verifyElementPresent(IdentityObjects.emptyGrid, "No records found", false);
								
							}
							else{
								logger.log(LogStatus.FAIL, "Accesses are still assigned to the user");
							}
							
							/*Assets tab*/
							ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Navigate to Assets tab");
							
							getIndexOfIDMAssetsHeaders();
							if(driver.findElements(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']")).size()>0){
								WebElement assetStatus = driver.findElement(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div"));
								String assetAssignmentStatus = assetStatus.getText();
								logger.log(LogStatus.INFO,"current assignment status of asset is : "+assetAssignmentStatus);
								Utility.verifyElementPresent("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div", "asset status", false);
								if(Utility.compareStringValues(assetAssignmentStatus, "INACTIVE"))
									logger.log(LogStatus.PASS, "asset status is inactive after termination");
								else
									logger.log(LogStatus.FAIL, "asset status is not deactivated");
								}
							else{
								logger.log(LogStatus.INFO, "asset not assigned to the user");
							}
					}
					AGlobalComponents.RequestSubmit=false;
				}
				else
					logger.log(LogStatus.FAIL, "Request is not submiited suuccessfully");
			}catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
			
	}
				
	public static void checkStatusBeforeRequestSubmission(String userId , String parameterToBeModified,String attribute,String scriptName) throws Throwable{
		
		if (unhandledException == false) {
			
			System.out.println("********* Check status in IDM before request submission*******************");
			logger.log(LogStatus.INFO,"******Check status in IDM before request submission************");
			
			try {
				HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource(scriptName);
				String requestType = (String) testData1.get("request_type");
				WebElement image=null ;
				FB_Automation_CommonMethods.searchIdentity(userId);
		
				/**
				 * Checking for update identity use case
				 */
				if(Utility.compareStringValues(requestType, "Update Identity")){
					String parameterToBeUpdated = parameterToBeModified;
					switch (parameterToBeUpdated.toLowerCase()) {
		            case "photo":
		            	ByAttribute.click("xpath", IdentityObjects.idmMAnageIdentityExpandLeftViewLnk, "Expand left view ");
		            	File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		                Thread.sleep(3000);
		                String path = System.getProperty("user.dir") + "\\Browser_Files\\CurrentPhoto.jpg";
		                fileInput = new File(path);
		                if(fileInput.exists())
							fileInput.delete();
		                System.out.println("File Path: "+path);
		                FileUtils.copyFile(screenshot, new File(path));
		                fileInput = new File(path);
		                
						image = driver.findElement(By.xpath(IdentityObjects.imageLnk));
						oldPhotoSrc = image.getAttribute("src");
						if(oldPhotoSrc.contains("defaultprofile.jpg")){
							logger.log(LogStatus.INFO, "Image will be uploaded first time");
							Utility.verifyElementPresent(IdentityObjects.imageLnk, "Default Image", false);
							
						}
						else{
							AGlobalComponents.updatePhoto=true;
							logger.log(LogStatus.INFO, "Capturing the existing photo");
							Utility.verifyElementPresent(IdentityObjects.imageLnk, "existing image", false);
						}
						Utility.pause(5);
		            	break;
		            case "lastname":
		            	Utility.verifyElementPresent(IdentityObjects.idmManageIdentityProfileInfoLastNameTxt, "last name", false);
						String currentLastName = driver.findElement(By.xpath(IdentityObjects.idmManageIdentityProfileInfoLastNameTxt)).getAttribute("value");
						logger.log(LogStatus.INFO, "Current last name of employee is : "+currentLastName);
		            	break;
		            case "phoneno":
		            	Utility.verifyElementPresentByScrollView(IdentityObjects.idmManageIdentityProfileInfoPhoneNoTxt, "Phone number",true, false);
						String currentPhoneNo = driver.findElement(By.xpath(IdentityObjects.idmManageIdentityProfileInfoPhoneNoTxt)).getAttribute("value");
						logger.log(LogStatus.INFO, "Current last name of employee is : "+currentPhoneNo);
		            	break;
		            case "department":
		            	String accessToBeAdded=attribute;
						String deptName = driver.findElement(By.xpath(".//input[contains(@id,'baseComboBoxRemote') and @placeholder='Select Department Name']")).getAttribute("value");			
				
						logger.log(LogStatus.INFO, "Current department of user is : "+deptName);
				
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						getIndexOfAccessHeaders();
						if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
							logger.log(LogStatus.INFO, "No Access is assigned to the user ");
						else{
							List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
							int size = noOfAccessRows.size();
							accessesAssignedToUser.clear();
							for (int i=1;i<=size;i++){
								WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' ]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
								String accessAssigned = accessName.getText();
								accessesAssignedToUser.add(i-1, accessAssigned);
								Utility.verifyElementPresent("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]", accessAssigned, false);
								if(!(Utility.compareStringValues(accessAssigned, accessToBeAdded))){
									logger.log(LogStatus.INFO, "Access to be added :"+accessToBeAdded+" is not assigned to the user before");
								}
							}
						}
		            	break;
		            case "addaccess":
		            	String accessAdded=(String) testData1.get("access_name_1");
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						getIndexOfAccessHeaders();
						if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
							logger.log(LogStatus.INFO, "No Access is assigned to the user ");
						else{
							List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
							int size = noOfAccessRows.size();
							for (int i=1;i<=size;i++){
								WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
								String accessAssigned = accessName.getText();
								if(!(Utility.compareStringValues(accessAssigned, accessAdded))){
									logger.log(LogStatus.INFO, "Access to be added is not assigned to the user before");
								}
							}
						}
		            	
		            	break;
		            case "removeaccess":
		            	String accessToBeRemoved=(String) testData1.get("pre_assigned_Access_1");
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
						getIndexOfAccessHeaders();
						if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
							logger.log(LogStatus.INFO, "No Access is assigned to the user , so we cant remove any access ");
						else{
							List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
							int size = noOfAccessRows.size();
							for (int i=1;i<=size;i++){
								WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
								String accessAssigned = accessName.getText();
								if(Utility.compareStringValues(accessAssigned, accessToBeRemoved)){
									logger.log(LogStatus.INFO, "Access to be removed is assigned to the user before");
									Utility.verifyElementPresent("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]", "Access to be removed", false);
								}
							}
						}
		            	break;	
		            
		            default: 
		            	System.out.println("No parameter updation request is received");
		            	
					}
					
				}
	
				//Checking the  asset status in IDM
				if (Utility.compareStringValues(requestType, "Wellness Check")){
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
					getIndexOfIDMAssetsHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Badge is assigned to the user ");
					else{
						WebElement assetStatus = driver.findElement(By.xpath("//tr//td["+assignmentStatusIndex+"]//div[@class='x-grid-cell-inner ']//label"));
						oldAssetStatus=assetStatus.getText();
						Utility.verifyElementPresent("//tr//td["+assignmentStatusIndex+"]//div[@class='x-grid-cell-inner ']//label", "Asset Assignemnt status", false);
		
						logger.log(LogStatus.INFO, "Current asset Assignment status is : " +oldAssetStatus);
						if(Utility.compareStringValues(oldAssetStatus, "ACTIVE"))
							AGlobalComponents.wellnessCheckDeActivate =true;
						else
							AGlobalComponents.wellnessCheckActivate =true;
					}
				}
		
				
				//Checking the  assigned access to the user  in IDM
				if (Utility.compareStringValues(requestType, "Request Location Access")){
					String accessAdded=attribute;
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
					else{
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						for (int i=1;i<=size;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							if(!(Utility.compareStringValues(accessAssigned, accessAdded))){
								logger.log(LogStatus.INFO, "Access to be added is not assigned to the user before");
							}
						}
					}
				}
				
				/*
				 * checking the previously assigned accesses to the user before raising request for Position access
				 * 
				 */
				
				//Checking the  assigned access to the user  in IDM
				if (Utility.compareStringValues(requestType, "Position Access")){
					String accessAdded=attribute;
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
					else{
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						for (int i=1;i<=size;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							if(!(Utility.compareStringValues(accessAssigned, accessAdded))){
								logger.log(LogStatus.INFO, "Access to be added is not assigned to the user before");
							}
						}
					}
				}
				
				/*
				 * checking the previously assigned accesses to the user before raising request for Application access
				 * 
				 */
				
				//Checking the  assigned access to the user  in IDM
				if (Utility.compareStringValues(requestType, "Application Access")){
					String accessAdded=attribute;
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
					else{
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						for (int i=1;i<=size;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							if(!(Utility.compareStringValues(accessAssigned, accessAdded))){
								logger.log(LogStatus.INFO, "Access to be added is not assigned to the user before");
							}
						}
					}
				}
				
				/*
				 * checking the previously assigned accesses to the user before raising request for IT access
				 * 
				 */
				
				//Checking the  assigned access to the user  in IDM
				if (Utility.compareStringValues(requestType, "IT Access")){
					String accessAdded=attribute;
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
					else{
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						for (int i=1;i<=size;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							if(!(Utility.compareStringValues(accessAssigned, accessAdded))){
								logger.log(LogStatus.INFO, "Access to be added :"+accessAdded +" is not assigned to the user before");
							}
						}
					}
				}
				
				
		
				//Checking the  assigned access to the user  in IDM
				if (Utility.compareStringValues(requestType, "Employment Type Conversion")){
					String accessToBeAdded=attribute;
					Utility.verifyElementPresent(HomeObjects.homeAccessRequestEmpTypeTxt, "Employee Type", false);
					String currentEmpType = driver.findElement(By.xpath(HomeObjects.homeAccessRequestEmpTypeTxt)).getAttribute("value");
					logger.log(LogStatus.INFO, "Current employee type is : "+currentEmpType);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
					else{
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						for (int i=1;i<=size;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							contractorAccessNames.add(i-1, accessAssigned);
							if(!(Utility.compareStringValues(accessAssigned, accessToBeAdded))){
								logger.log(LogStatus.INFO, "Access to be added is not assigned to the user before");
							}
						}
					}
				}
		
		
				if (Utility.compareStringValues(requestType, "Temp Worker Modification")){
				//checking the details of temp worker before modification
				switch(parameterToBeModified.toLowerCase()){
				case "lastname":
					Utility.verifyElementPresent(HomeObjects.homeAccessRequestLastNameTxt, "last name", false);
					String currentLastName = driver.findElement(By.xpath(HomeObjects.homeAccessRequestLastNameTxt)).getAttribute("value");
					logger.log(LogStatus.INFO, "Current last name of temp worker is : "+currentLastName);
						break;
				case "department":
					String accessToBeAdded=attribute;
					String deptName = driver.findElement(By.xpath(".//input[contains(@id,'baseComboBoxRemote') and @placeholder='Select Department Name']")).getAttribute("value");			
			
					logger.log(LogStatus.INFO, "Current department of temp worker is : "+deptName);
			
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
					else{
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						accessesAssignedToUser.clear();
						for (int i=1;i<=size;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' ]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							accessesAssignedToUser.add(i-1, accessAssigned);
							Utility.verifyElementPresent("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]", accessAssigned, false);
							if(!(Utility.compareStringValues(accessAssigned, accessToBeAdded))){
								logger.log(LogStatus.INFO, "Access to be added :"+accessToBeAdded+" is not assigned to the user before");
							}
						}
					}
						break;
				default: 
	            		System.out.println("No parameter updation request is received");
				}
				}
				
				
				/* checking the asset , access and system status before offboarding */
				if (Utility.compareStringValues(requestType, "Temp Worker Offboarding")){
			
					int noOfAccess=0 , noOfSystem=0,noOfAsset=0,size=0;
					/*access tab*/
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Navigate to access tab");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
					else{
						accessesAssignedToUser.clear();
						List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
						size=noOfRows.size();
						noOfAccess = size;
						if(noOfAccess>0){
							logger.log(LogStatus.INFO, "Getting the list of accesses assigned to the temporary worker");
							for (int i=1;i<=noOfAccess;i++){
								WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container']//table["+i+"]//tr[1]//td["+accessIndex+"]"));
								String accessAssigned = accessName.getText();
								
								accessesAssignedToUser.add(i-1, accessAssigned);
								String column = "pre_assigned_access_"+i;
								Utility.updateDataInDatasource("Self_Service_Automation_TC022", column, accessAssigned);
								logger.log(LogStatus.INFO,"access assigned to the temporary worker : "+accessAssigned);
								Utility.verifyElementPresent("//*[text()='"+accessAssigned+"']", accessAssigned, false);
							}
						}
					}
			
					/*system tab*/
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Navigate to systems tab");
					List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
					size = noOfRows.size();
					noOfSystem=size-noOfAccess;
					int index=noOfAccess+1;
					systemsAssignedToUser.clear();
					if(noOfSystem>0){
						logger.log(LogStatus.INFO, "Getting the list of systems assigned to the temporary worker");
						gettingIndexOfIDMSystemsTab();
						for (int i=1;i<=noOfSystem;i++){
							WebElement systemName = driver.findElement(By.xpath("(//div[@class='x-grid-item-container' ]//tr[1]/td["+systemIndex+"]/div)["+index+"]"));
							String systemAssigned = systemName.getText();
							systemsAssignedToUser.add(i-1,systemAssigned);
							logger.log(LogStatus.INFO,"system assigned to the temporary worker : "+systemAssigned+ " and status is active");
							Utility.verifyElementPresent("//div[@class='x-grid-item-container']//tr[1]/td["+systemIndex+"]/div[text()='"+systemAssigned+"']", systemAssigned, false);
							index++;
						}
					}
					else
						logger.log(LogStatus.INFO, "No systems are assigned to the temporary worker");
			
					/*Assets tab*/
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Navigate to Assets tab");
					noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
					size = noOfRows.size();
					noOfAsset=size-(noOfAccess+noOfSystem);
					index=noOfAccess+noOfSystem+1;
					if(noOfAsset>0){
						logger.log(LogStatus.INFO, "Getting the list of assets assigned to the temporary worker");
						getIndexOfIDMAssetsHeaders();
						for (int i=1;i<=noOfAsset;i++){
							WebElement assetStatus = driver.findElement(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div"));
							assetStatusBeforeOffboarding = assetStatus.getText();
							if(Utility.compareStringValues(assetStatusBeforeOffboarding, "INACTIVE"))
								logger.log(LogStatus.FAIL, "Status is already Inactive");
							else{
								logger.log(LogStatus.INFO,"current assignment status of asset is : "+assetStatusBeforeOffboarding);
								Utility.verifyElementPresent("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div", "asset status", false);
								index++;
							}
						}
					}
					else
						logger.log(LogStatus.INFO, "No assets are assigned to the temporary worker");
				}
				
				
				/*
				 *  checking the asset , access and system status before Rehiring
				 *   */
				if (Utility.compareStringValues(requestType, "Temp Worker Rehire")){
			
					int noOfAccess=0 , noOfSystem=0,noOfAsset=0,size=0;
					/*access tab*/
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Navigate to access tab");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0){
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
						
					}
					else{
						List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
						size=noOfRows.size();
						noOfAccess = size;
						accessesAssignedToUser.clear();
						if(noOfAccess>0){
							logger.log(LogStatus.INFO, "Getting the list of accesses assigned to the temporary worker");
							for (int i=1;i<=noOfAccess;i++){
								WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container']//table["+i+"]//tr[1]//td["+accessIndex+"]"));
								String accessAssigned = accessName.getText();
								accessesAssignedToUser.add(i-1, accessAssigned);
								logger.log(LogStatus.INFO,"access assigned to the temporary worker : "+accessAssigned);
								Utility.verifyElementPresent("//*[text()='"+accessAssigned+"']", accessAssigned, false);
							}
						}
					}
			
					/*system tab*/
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Navigate to systems tab");
					List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
					size = noOfRows.size();
					noOfSystem=size-noOfAccess;
					int index=noOfAccess+1;
					String systemStatus = "";
					systemsAssignedToUser.clear();
					if(noOfSystem>0){
						logger.log(LogStatus.INFO, "Getting the list of systems assigned to the temporary worker");
						gettingIndexOfIDMSystemsTab();
						for (int i=1;i<=noOfSystem;i++){
							WebElement systemName = driver.findElement(By.xpath("(//div[@class='x-grid-item-container' ]//tr[1]/td["+systemIndex+"]/div)["+index+"]"));
							String systemAssigned = systemName.getText();
							if (driver.findElements(By.xpath("//div[@class='x-grid-item-container' ]//tr[1]/td["+systemIndex+"]//following-sibling::td//img")).size()>0)
								systemStatus = driver.findElement(By.xpath("(//div[@class='x-grid-item-container' ]//tr[1]/td["+systemIndex+"]//following-sibling::td//img)["+index+"]")).getAttribute("src");
							if (driver.findElements(By.xpath("//div[@class='x-grid-item-container' ]//tr[1]/td["+systemIndex+"]//following-sibling::td//label")).size()>0)
								systemStatus = driver.findElement(By.xpath("(//div[@class='x-grid-item-container' ]//tr[1]/td["+systemIndex+"]//following-sibling::td//label)["+index+"]")).getText();
							systemsAssignedToUser.add(i-1,systemAssigned);
							if((systemStatus.contains("redcircle"))||(Utility.compareStringValues(systemStatus, "INACTIVE"))){
								logger.log(LogStatus.INFO,"system assigned to the temporary worker : "+systemAssigned+ " and status is inactive");
								Utility.verifyElementPresent("//div[@class='x-grid-item-container']//tr[1]/td["+systemIndex+"]/div[text()='"+systemAssigned+"']", systemAssigned, false);
							}
							index++;
						}
					}
					else
						logger.log(LogStatus.INFO, "No systems are assigned to the temporary worker");
			
					/*Assets tab*/
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Navigate to Assets tab");
					HashMap<String, Comparable> testData = Utility.getDataFromDatasource("Self_Service_Automation_TC022");
					String asstCode = (String) testData.get("asset_code");
					String asstName = (String) testData.get("badge_name");
					noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
					size = noOfRows.size();
					noOfAsset=size-(noOfAccess+noOfSystem);
					index=noOfAccess+noOfSystem+1;
					if(noOfAsset>0){
						logger.log(LogStatus.INFO, "Getting the list of assets assigned to the temporary worker");
						getIndexOfIDMAssetsHeaders();
						for (int i=1;i<=noOfAsset;i++){
							WebElement assetStatus = driver.findElement(By.xpath("//div[text()='"+asstCode+"']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div"));
							assetStatusBeforeOffboarding = assetStatus.getText();
							if(Utility.compareStringValues(assetStatusBeforeOffboarding, "INACTIVE")){
								logger.log(LogStatus.INFO, "Status is already Inactive");
								Utility.verifyElementPresent("//div[text()='"+asstCode+"']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div", "asset status", false);
							}
							else{
								logger.log(LogStatus.INFO,"current assignment status of asset is : "+assetStatusBeforeOffboarding);
								Utility.verifyElementPresent("//div[text()='"+asstCode+"']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div", "asset status", false);
								index++;
							}
						}
					}
					else
						logger.log(LogStatus.INFO, "No assets are assigned to the temporary worker");
				}
				
				
				/* 
				 * checking the asset , access and system status before termination
				 *  */
				if (Utility.compareStringValues(requestType, "Emergency Termination")){
			
					int noOfAccess=0 , noOfSystem=0,noOfAsset=0,size=0;
					/*access tab*/
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Navigate to access tab");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
					else{
						List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
						size=noOfRows.size();
						noOfAccess = size;
						accessesAssignedToUser.clear();
						if(noOfAccess>0){
							logger.log(LogStatus.INFO, "Getting the list of accesses assigned to the user");
							for (int i=1;i<=noOfAccess;i++){
								WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container']//table["+i+"]//tr[1]//td["+accessIndex+"]"));
								String accessAssigned = accessName.getText();
								accessesAssignedToUser.add(i-1, accessAssigned);
								logger.log(LogStatus.INFO,"access assigned to the employee : "+accessAssigned);
								Utility.verifyElementPresent("//*[text()='"+accessAssigned+"']", accessAssigned, false);
							}
						}
					}
			
					/*system tab*/
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Navigate to systems tab");
					List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
					size = noOfRows.size();
					noOfSystem=size-noOfAccess;
					int index=noOfAccess+1;
					systemsAssignedToUser.clear();
					if(noOfSystem>0){
						logger.log(LogStatus.INFO, "Getting the list of systems assigned to the user");
						gettingIndexOfIDMSystemsTab();
						for (int i=1;i<=noOfSystem;i++){
							WebElement systemName = driver.findElement(By.xpath("(//div[@class='x-grid-item-container' ]//tr[1]/td["+systemIndex+"]/div)["+index+"]"));
							String systemAssigned = systemName.getText();
							systemsAssignedToUser.add(i-1,systemAssigned);
							logger.log(LogStatus.INFO,"system assigned to the user is : "+systemAssigned+ " and status is active");
							Utility.verifyElementPresent("//div[@class='x-grid-item-container']//tr[1]/td["+systemIndex+"]/div[text()='"+systemAssigned+"']", systemAssigned, false);
							index++;
						}
					}
					else
						logger.log(LogStatus.INFO, "No systems are assigned to the user");
			
					/*Assets tab*/
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Navigate to Assets tab");
					noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
					size = noOfRows.size();
					noOfAsset=size-(noOfAccess+noOfSystem);
					index=noOfAccess+noOfSystem+1;
					if(noOfAsset>0){
						logger.log(LogStatus.INFO, "Getting the list of assets assigned to the user");
						getIndexOfIDMAssetsHeaders();
						for (int i=1;i<=noOfAsset;i++){
							WebElement assetStatus = driver.findElement(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div"));
							assetStatusBeforeOffboarding = assetStatus.getText();
							if(Utility.compareStringValues(assetStatusBeforeOffboarding, "INACTIVE"))
								logger.log(LogStatus.FAIL, "Status is already Inactive");
							else{
								logger.log(LogStatus.INFO,"current assignment status of asset is : "+assetStatusBeforeOffboarding);
								Utility.verifyElementPresent("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div", "asset status", false);
								index++;
							}
						}
					}
					else
						logger.log(LogStatus.INFO, "No assets are assigned to the user");
				}
			}
		
			catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
}
	
				
				
	
	private static void gettingIndexOfIDMSystemsTab() {
		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
			boolean flag=true;
						
			for (int i=0;i<size && flag;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println("heading "+ (i+1) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "system":
		            	systemIndex = j;
		            	j++;
		            	flag=false;
		            	break;
		            case "":
		                break;
		            default: 
		            	System.out.println("Need to skip this header : "+ heading);
		            	j++;
					}
				}
			}
			catch(Exception e){
				logger.log(LogStatus.ERROR, "Failed: Header Not Found ");
			}
		
		
	}



	public static void getIndexOfIDMAssetsHeaders() throws Throwable {
		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
			boolean flag=true;
						
			for (int i=0;i<size && flag;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println("heading "+ (i) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "assignment status":
		            	assignmentStatusIndex = j;
		            	j++;
		            	flag=false;
		            	break;
		            case "":
		                break;
		            default: 
		            	System.out.println("Need to skip this header : "+ heading);
		            	j++;
					}
				}
			}
			catch(Exception e){
				logger.log(LogStatus.ERROR, "Failed: Header Not Found ");
			}
	}
	
	
	public static void getIndexOfManageAssetsHeaders() throws Throwable {
		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
			boolean flag=true;
						
			for (int i=1;i<size && flag;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println("heading "+ (i) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "asset code":
		            	assetCodeIndex = j+1;
		            	j++;
		            	flag=false;
		            	break;
		            case "":
		                break;
		            default: 
		            	System.out.println("Need to skip this header : "+ heading);
		            	j++;
					}
				}
			}
			catch(Exception e){
				logger.log(LogStatus.ERROR, "Failed: Header Not Found ");
			}
	}
	
	public static int getIndexOfAccessHeaders() throws Throwable {
		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
									
			for (int i=1;i<size ;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println("heading "+ (i) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "access":
		            	accessIndex = j+1;
		            	j++;
		            	break;
		            case "action":
		            	actionIndex = j+1;
		            	j++;
		            	break;
		            case "":
		                break;
		            default: 
		            	System.out.println("Need to skip this header : "+ heading);
		            	j++;
					}
				}
			}
			catch(Exception e){
				logger.log(LogStatus.ERROR, "Failed: Header Not Found ");
			}
		return accessIndex;
	}


	/**
	 * <h1>Submit WellnessCheck request</h1> 
	 * This is Method to submit WllnessCheck request
	 * @author Monika Mehta
	 * @modified
	 * @version 1.0
	 * @since 01-15-2021
	 * @param none
	 * @return none
	 **/

	public static void createWellnessCheckRequest() throws Throwable {
		if (unhandledException == false) {
			System.out.println("***************************** submitRequest For WellnessCheck *********************************");
			logger.log(LogStatus.INFO,"***************************** submitRequest For WellnessCheck *********************************");
			try {
				
					WebElement requestor=driver.findElement(By.xpath(".//span[@class='x-btn-wrap x-btn-wrap-aetextlink-medium x-btn-arrow x-btn-arrow-right' and @role='presentation']"));
					requestorName = requestor.getText();
					logger.log(LogStatus.INFO, "Navigate to My Requests Tab");
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on MyRequests tab");
					Utility.pause(5);
					ByAttribute.click("xpath", HomeObjects.createBtn, "Click on create button ");
					Utility.pause(5);
					ByAttribute.click("xpath", HomeObjects.wellnessCheckLnk, "Click on WellnessCheck pod ");
					Utility.pause(5);
					
					logger.log(LogStatus.INFO, "Fill the questionnaire" );
					if(AGlobalComponents.wellnessCheckActivate)
						submitRequestToActivateTheBadge();
					else if(AGlobalComponents.wellnessCheckDeActivate)
						submitRequestToDeActivateTheBadge();
					else
						logger.log(LogStatus.FAIL, "Unable to fill the questionnaire");
					
					
					ByAttribute.click("xpath", HomeObjects.acknowledgementCheckbox, "Click acknowledgement checkbox ");
					Utility.pause(2);
					ByAttribute.click("xpath", HomeObjects.submitBtn, "Click Submit button ");
					Utility.pause(5);
					AGlobalComponents.RequestSubmit= true;
								
				}catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
	}



	private static void submitRequestToActivateTheBadge() {
		try{
			logger.log(LogStatus.INFO, "Filling the questionnaire for badge activation case");
			ByAttribute.click("xpath", HomeObjects.questionOneNo, "Select radio button for first question");
			Utility.pause(2);
			ByAttribute.click("xpath", HomeObjects.questionTwoNo, "Select radio button for second question");
			Utility.pause(2);
			ByAttribute.click("xpath", HomeObjects.questionThreeNo, "Select radio button for third question");
			Utility.verifyElementPresent(HomeObjects.userRequestHeader, "Filled User Request", false);
			
		}catch(Exception e){
			logger.log(LogStatus.FAIL, "Unable to fill the questionnaire");
		}
				
	}



	private static void submitRequestToDeActivateTheBadge() {
		try{
			
			logger.log(LogStatus.INFO, "Filling the questionnaire for badge deactivation case");
			ByAttribute.click("xpath", HomeObjects.questionOneYes, "Select radio button for first question");
			Utility.pause(2);
			ByAttribute.click("xpath", HomeObjects.questionTwoNo, "Select radio button for second question");
			Utility.pause(2);
			ByAttribute.click("xpath", HomeObjects.questionThreeNo, "Select radio button for third question");
			Utility.verifyElementPresent(HomeObjects.userRequestHeader, "Filled User Request", false);
		}catch(Exception e){
			logger.log(LogStatus.FAIL, "Unable to fill the questionnaire");
		}
		
	}



	public static void checkRequestInMyRequestInbox(String firstName, String lastName ,String parameterToBeModified,String modifiedAttribute,String requestNumber,String scriptName) throws Throwable {
		if (unhandledException == false) {
			System.out.println("***************************** Checking the request in completed inbox *********************************");
			logger.log(LogStatus.INFO,"*************** Checking the request in completed inbox *********************************");
			try{
				HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource(scriptName);
				String requestType = (String) testData1.get("request_type");
				List<WebElement> requestNumberElements = null;
				WebElement requestNo=null,latestRequestNumber=null;int index=0;
				boolean reqFlag=true;
				
				Actions action = new Actions(driver);
				action.click().build().perform();
				Thread.sleep(2000);
				
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0){
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on My Requests");
					Utility.pause(5);
					requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					latestRequestNumber = requestNumberElements.get(0);
					
					reqNum = latestRequestNumber.getText();
					if(Utility.compareStringValues(reqNum, requestNumber)){
						action.doubleClick(latestRequestNumber);
						action.build().perform();
						Utility.pause(5);
						logger.log(LogStatus.INFO ,"Request "+requestNumber+" opened successfully in completed inbox");
						reqFlag=false;
					}
				}else if (driver.findElements(By.xpath(HomeObjects.homeTabBtn)).size()>0){
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse hover on Home Tab");
					Utility.pause(3);
					ByAttribute.click("xpath", HomeObjects.homeInboxLnk, "Click on Inbox");
					Utility.pause(5);
					if(driver.findElements(By.xpath(HomeObjects.homeInboxRequestInboxExpandBtn)).size()>0){
						ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click to expand the request menu");
						Utility.pause(2);
						ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCompletedBtn, "Click on completed button");
						Utility.pause(25);
						ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCollapseBtn, "Click to collapse the request menu");
						Utility.pause(5);
						
						requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
						for (int i=0;i<requestNumberElements.size() && reqFlag;i++){
							requestNo = requestNumberElements.get(i);
							reqNum = requestNo.getText();
							if(reqNum.contains(requestNumber)){
								index=i+1;
								reqFlag=false;
								action.doubleClick(requestNo);
								action.build().perform();
								Utility.pause(5);
								WebElement reqNo = driver.findElement(By.xpath("//div[text()='Request Number']//following-sibling::div"));
								String requestNum= reqNo.getText();
								if(Utility.compareStringValues(requestNum, requestNumber))
									logger.log(LogStatus.PASS, "Request openend successfully");
								else
									logger.log(LogStatus.FAIL, "Incorrect  request opened");
							}
						}
						
					}
				}
				if(reqFlag){
					if (driver.findElements(By.xpath(HomeObjects.homeTabBtn)).size()>0){	
						ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse hover on Home Tab");
						Utility.pause(1);
						ByAttribute.click("xpath", HomeObjects.homeMyRequestsLnk, "Click on My Requests");
						Utility.pause(5);
						requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
						for (int i=0;i<requestNumberElements.size();i++){
							latestRequestNumber = requestNumberElements.get(i);
							reqNum = latestRequestNumber.getText();
							if(Utility.compareStringValues(reqNum, requestNumber)){
								action.doubleClick(latestRequestNumber);
								action.build().perform();
								Utility.pause(5);
								logger.log(LogStatus.INFO ,"Request "+requestNumber+" opened successfully in completed inbox");
								reqFlag=false;
								break;
							}
							
						}
						
					}
				}
				if(reqFlag)
					logger.log(LogStatus.FAIL, "Request not present in completed inbox");
				
						
				if(Utility.compareStringValues(requestType, "Update Identity")){
					String parameterToBeUpdated = parameterToBeModified;
					switch(parameterToBeUpdated){
					case "photo":
						break;
					case "lastName":
						
						ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Clickon comparison button to verify the modifications");
						Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='"+modifiedLastName+"']", "modifiedLastName", false);
						logger.log(LogStatus.PASS,"Last name is modified for the employee");
						break;
					case "phoneno":
						
						ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Clickon comparison button to verify the modifications");
						Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='"+modifiedPhoneNumber+"']", "modified Phone number", false);
						logger.log(LogStatus.PASS,"Phone number is modified for the employee");
						break;
					case "department":
						
						ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Click on comparison button to verify the modifications");
						Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='"+departmentName+"']", "modifieddepartmentName", false);
						logger.log(LogStatus.PASS,"new department name is present in comparison tab for temp worker :"+departmentName);
						break;
					default: 
						System.out.println("No parameter updation request is received");
						break;
						
					}
					
					
				  if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
					  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
				  else{
					  ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
					  Utility.pause(1);
					  ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
				  }
			    	boolean flag=true;
					for(int i=0;i<10 && flag;i++){
						WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]"));
						String provMessage = provisioningMessage.getText();
						if((provMessage.contains("assignment successful"))||(provMessage.contains("changed successfully"))){
							logger.log(LogStatus.PASS, "Provisioning successful");
							flag=false;
							for(int j=0;j<2;j++){
								if(driver.findElements(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]")).size()>0){
									Utility.verifyElementPresent("(//div[text()='Provisioning Done for :']//parent::div//div)[2]", "Provisioning message", false);
									Utility.pause(2);
									break;
								}
							}
						}
						else{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
							Utility.pause(20);
							if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
								  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
							 else{
								  ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
								  Utility.pause(1);
								  ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
							  }
						}
					
					}
					if(flag)
						logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
				}
				
	
				if(Utility.compareStringValues(requestType, "Wellness Check")){
					//validate badge/asset status in my request inbox
					//write the code to check the badge status and store it in badgeStatusInRequest
					//compare badge status is different from before status hence pass
				}
				
				if(Utility.compareStringValues(requestType, "Request Location Access")){
				
				  if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0){
					  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
					  Thread.sleep(2000);
				  }
				  else{
					  ByAttribute.click("xpath", HomeObjects.homeInboxRequestMenuIconLnk, "Clickon menu");
					  Utility.pause(1);
					  ByAttribute.click("xpath",HomeObjects.homeInboxRequestHistoryOptionInMenuLnk, "Clickon History");
				  }
					boolean flag=true;
					for(int i=0;i<10 && flag;i++){
						WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]"));
						String provMessage = provisioningMessage.getText();
						if(provMessage.contains("assignment successful for user")){
							logger.log(LogStatus.PASS, "Provisioning successful");
							flag=false;
							for(int j=0;j<2;j++){
								if(driver.findElements(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]")).size()>0){
									Utility.verifyElementPresent("(//div[text()='Provisioning Done for :']//parent::div//div)[2]", "Provisioning message", false);
									Utility.pause(2);
									break;
								}
							}
							
						}
						else{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
							Utility.pause(20);
							if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
								  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
							 else{
								  ByAttribute.click("xpath", HomeObjects.homeInboxRequestMenuIconLnk, "Clickon menu");
								  Utility.pause(1);
								  ByAttribute.click("xpath", HomeObjects.homeInboxRequestHistoryOptionInMenuLnk, "Clickon History");
							  }
						}
					
					}
					if(flag)
						logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
				}
				
				
				/* 
				 * validating request in completed inbox for Position Access
				 */
				if(Utility.compareStringValues(requestType, "Position Access")){
					
					  if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
						  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
					  else{
						  ByAttribute.click("xpath", HomeObjects.homeInboxRequestMenuIconLnk, "Clickon menu");
						  Utility.pause(1);
						  ByAttribute.click("xpath", HomeObjects.homeInboxRequestHistoryOptionInMenuLnk, "Click on History");
					  }
						boolean flag=true;
						for(int i=0;i<10 && flag;i++){
							WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]"));
							String provMessage = provisioningMessage.getText();
							if(provMessage.contains("assignment successful for user")){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								for(int j=0;j<2;j++){
									if(driver.findElements(By.xpath("((//div[contains(text(),' assignment successful ')])[1]//parent::div)[1]")).size()>0){
										Utility.verifyElementPresent("((//div[contains(text(),' assignment successful ')])[1]//parent::div)[1]", "Provisioning message", false);
										Utility.pause(2);
										break;
									}
								}
								
							}
							
							else{
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
								Utility.pause(20);
								if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
									  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
								 else{
									  ByAttribute.click("xpath", HomeObjects.homeInboxRequestMenuIconLnk, "Clickon menu");
									  Utility.pause(1);
									  ByAttribute.click("xpath", HomeObjects.homeInboxRequestHistoryOptionInMenuLnk, "Clickon History");
								  }
							}
						
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
				}
				
				/* 
				 * validating request in completed inbox for Application Access
				 */
				if(Utility.compareStringValues(requestType, "Application Access")){
					
					  if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
						  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
					  else{
						  ByAttribute.click("xpath", HomeObjects.homeInboxRequestMenuIconLnk, "Clickon menu");
						  Utility.pause(1);
						  ByAttribute.click("xpath", HomeObjects.homeInboxRequestHistoryOptionInMenuLnk, "Click on History");
					  }
						boolean flag=true;
						for(int i=0;i<10 && flag;i++){
							WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Skipped for :']//parent::div//div)[2]"));
							String provMessage = provisioningMessage.getText();
							if(provMessage.contains("Skip Provisioning Successful")){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								for(int j=0;j<2;j++){
									if(driver.findElements(By.xpath("(//div[text()='Provisioning Skipped for :']//parent::div//div)[2]")).size()>0){
										Utility.verifyElementPresent("(//div[text()='Provisioning Skipped for :']//parent::div//div)[2]", "Provisioning message", false);
										Utility.pause(2);
										break;
									}
								}
								
							}
							
							else{
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
								Utility.pause(20);
								if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
									  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
								 else{
									  ByAttribute.click("xpath",HomeObjects.homeInboxRequestMenuIconLnk , "Clickon menu");
									  Utility.pause(1);
									  ByAttribute.click("xpath",HomeObjects.homeInboxRequestHistoryOptionInMenuLnk, "Clickon History");
								  }
							}
						
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
				}
				
				/* 
				 * validating request in completed inbox for IT Access
				 */
				if(Utility.compareStringValues(requestType, "IT Access")){
					
					  if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
						  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
					  else{
						  ByAttribute.click("xpath", HomeObjects.homeInboxRequestMenuIconLnk, "Clickon menu");
						  Utility.pause(1);
						  ByAttribute.click("xpath", HomeObjects.homeInboxRequestHistoryOptionInMenuLnk, "Click on History");
					  }
						boolean flag=true;
						for(int i=0;i<10 && flag;i++){
							WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Skipped for :']//parent::div//div)[2]"));
							String provMessage = provisioningMessage.getText();
							if(provMessage.contains("Skip Provisioning Successful")){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								for(int j=0;j<2;j++){
									if(driver.findElements(By.xpath("(//div[text()='Provisioning Skipped for :']//parent::div//div)[2]")).size()>0){
										Utility.verifyElementPresent("(//div[text()='Provisioning Skipped for :']//parent::div//div)[2]", "Provisioning message", false);
										Utility.pause(2);
										break;
									}
								}
								
							}
								
							
							else{
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
								Utility.pause(20);
								if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
									  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
								 else{
									  ByAttribute.click("xpath",HomeObjects.homeInboxRequestMenuIconLnk , "Clickon menu");
									  Utility.pause(1);
									  ByAttribute.click("xpath",HomeObjects.homeInboxRequestHistoryOptionInMenuLnk, "Clickon History");
								  }
							}
						
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
				}
				
				/* 
				 * validating request in completed inbox for Employment Type Conversion
				 */
				
				if(Utility.compareStringValues(requestType, "Employment Type Conversion")){
					String accessName = modifiedAttribute;
					
					ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Click on comparison button to verify the modifications");
					Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='Permanent']", "modifiedEmployeeType", false);
					logger.log(LogStatus.PASS,"New and Old Employee Type can be seen in comparison");
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestMenuIconLnk, "Click on menu");
					Utility.pause(1);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestHistoryOptionInMenuLnk, "Click on History");
					boolean flag=true;
					for(int i=0;i<10 && flag;i++){
						WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]"));
						String provMessage = provisioningMessage.getText();
						if(provMessage.contains("assignment successful")){
							logger.log(LogStatus.PASS, "Provisioning successful");
							flag=false;
							Utility.verifyElementPresent("(//div[text()='Provisioning Done for :']//parent::div//div)[2]", "Provisioning message", false);
						}
						else{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
							Utility.pause(20);
							ByAttribute.click("xpath", HomeObjects.homeRequestInboxMenuIcn, "Clickon menu");
							Utility.pause(1);
							ByAttribute.click("xpath",HomeObjects.homeRequestInboxHistoryMenuLnk , "Click on History");
						}
							
					}	
					if(flag)
						logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
					ByAttribute.click("xpath", HomeObjects.homeRequestInboxCloseHistoryWindowIcn, "close history window pop up ");
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "Access List Grid",true, false);
					if((driver.findElements(By.xpath("//*[text()='"+accessName+"']")).size()>0)){
						String uiAccessStatus=DBValidations.getUIActionOfAccess(requestNumber,accessName);
						if((Utility.compareStringValues(uiAccessStatus, "ADD"))){
							Utility.verifyElementPresent("//*[text()='"+accessName+"']", accessName, false);
							logger.log(LogStatus.PASS, "New access is added when employment type is modified");
						}
						else
							logger.log(LogStatus.FAIL, "Access is not added when employment type is modified");
						
					}
					for (int i=0;i<contractorAccessNames.size();i++){
						String accessAssignedName=contractorAccessNames.get(i);
						String uiAccessStatus=DBValidations.getUIActionOfAccess(requestNumber,accessAssignedName);
						if(Utility.compareStringValues(uiAccessStatus, "REMOVE")) {
							Utility.verifyElementPresent("//*[text()='"+accessAssignedName+"']", accessAssignedName, false);
							logger.log(LogStatus.PASS, "status of existing  access :" +accessAssignedName+ " is removed when employment type is modified");
						}
						else
							logger.log(LogStatus.FAIL, "Access status is not removed");
						
					}
				}
				
				if(Utility.compareStringValues(requestType, "Temp Worker Onboarding")){
					/*
					 * checking for accesses added through pre feed rule while onboarding
					 */
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestAccessListGrid)).size()>0){
						Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
						if((driver.findElements(By.xpath("//*[text()='"+access1ForTempOnboarding+"']")).size()>0) && (driver.findElements(By.xpath("//*[text()='"+access2ForTempOnboarding+"']")).size()>0) && (driver.findElements(By.xpath("//*[text()='"+access3ForTempOnboarding+"']")).size()>0)){
							logger.log(LogStatus.PASS, "3 accesses :" +access1ForTempOnboarding +","+access2ForTempOnboarding+ ","+access3ForTempOnboarding+" are assigned to the user");
							Utility.verifyElementPresent("//*[text()='"+access1ForTempOnboarding+"']", access1ForTempOnboarding,false);
							Utility.verifyElementPresent("//*[text()='"+access2ForTempOnboarding+"']", access2ForTempOnboarding,false);
							Utility.verifyElementPresent("//*[text()='"+access3ForTempOnboarding+"']", access3ForTempOnboarding, false);
						}
						else
							logger.log(LogStatus.FAIL, "2 accesses :" +access1ForTempOnboarding +","+access2ForTempOnboarding+ " are not assigned to the user while onboarding");
					}
					else
						logger.log(LogStatus.INFO, "Access Grid is not present");
					/*
					 * checking for badge added through pre feed rule while onboarding
					 */
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestBadgeListGrid)).size()>0){
						Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestBadgeListGrid, "BadgeList Grid", true,false);
						Utility.verifyElementPresent("//div[normalize-space(text())='"+AGlobalComponents.assetCode+"']","Asset assigned", false);
						logger.log(LogStatus.PASS, "Badge is assigned to the user ");
					}
					else
						logger.log(LogStatus.INFO, "Badge Grid is not present");
					
					/*
					 * checking for prerequisites added through pre feed rule while onboarding
					 */
					String prerequisite1 = "IT Security Training 2020" , prerequisite2 = "Background Check";
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestPrerequisiteList)).size()>0){
						Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestPrerequisiteList, "PrerequisiteList Grid",true, false);
						if((driver.findElements(By.xpath("//*[text()='"+prerequisite1+"']")).size()>0) && (driver.findElements(By.xpath("//*[text()='"+prerequisite1+"']")).size()>0)){
							logger.log(LogStatus.PASS, "2 prerquisites  :" +prerequisite1 +","+prerequisite2+ " are assigned to the user");
						}
					}
					else
						logger.log(LogStatus.INFO, "Prerequisite Grid is not present");
					
					
					/*checking the history of request */
					
					ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
					Utility.pause(1);
					ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
					boolean flag=true;
					for(int i=0;i<10 && flag;i++){
						WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]"));
						String provMessage = provisioningMessage.getText();
						if(provMessage.contains("assignment successful for user")){
							logger.log(LogStatus.PASS, "Provisioning successful");
							flag=false;
							for(int j=0;j<2;j++){
								if(driver.findElements(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]")).size()>0){
									Utility.verifyElementPresent("(//div[text()='Provisioning Done for :']//parent::div//div)[2]", "Provisioning message", false);
									Utility.pause(2);
									break;
								}
							}
							
						}
															
						else if(driver.findElements(By.xpath("//div[contains(text(),' assignment failed for user ')]")).size()>0){
								Utility.verifyElementPresent("//div[contains(text(),' assignment failed for user ')]", "Provisioning failed message", false);
								break;
						}
						else{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
							Utility.pause(20);
							ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
							Utility.pause(1);
							ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
						}
						
					}	
					if(flag)
						logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
					
				}
				
				if(Utility.compareStringValues(requestType, "Temp Worker Modification")){
					String accessName = modifiedAttribute;
					switch(parameterToBeModified.toLowerCase()){
					case "lastname":
						ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Clickon comparison button to verify the modifications");
						Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='"+modifiedLastName+"']", "modifiedLastName", false);
						logger.log(LogStatus.PASS,"Last name is modified for temp worker");
						break;
					case "department":
						
						ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Click on comparison button to verify the modifications");
						Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='"+departmentName+"']", "modifieddepartmentName", false);
						logger.log(LogStatus.PASS,"new department name is present in comparison tab for temp worker :"+departmentName);
						break;
					}
					
					
					ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
					Utility.pause(1);
					ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
					boolean flag=true;
					for(int i=0;i<10 && flag;i++){
						WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]"));
						String provMessage = provisioningMessage.getText();
						if((provMessage.contains("removal successful"))||(provMessage.contains("assignment successful"))||(provMessage.contains("changed successfully"))){
							logger.log(LogStatus.PASS, "Provisioning successful");
							flag=false;
							for(int j=0;j<2;j++){
								if(driver.findElements(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]")).size()>0){
									Utility.verifyElementPresent("(//div[text()='Provisioning Done for :']//parent::div//div)[2]", "Provisioning message", false);
									Utility.pause(2);
									break;
								}
								Thread.sleep(2000);
							}
							
						}
						else{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
							Utility.pause(20);
							ByAttribute.click("xpath", HomeObjects.homeRequestInboxMenuIcn, "Clickon menu");
							Utility.pause(1);
							ByAttribute.click("xpath",HomeObjects.homeRequestInboxHistoryMenuLnk , "Clickon History");
						}
							
					}	
					if(flag)
						logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
					if(Utility.compareStringValues(parameterToBeModified, "department")){
						if((driver.findElements(By.xpath(HomeObjects.homeAccessRequestAccessListGrid)).size()>0)){
							Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "Access List Grid",true, false);
							String uiActionStatus=DBValidations.getUIActionOfAccess(requestNumber,accessName);
							if((Utility.compareStringValues(uiActionStatus, "ADD")))
								logger.log(LogStatus.PASS, "New access is added when department is modified");
							else
								logger.log(LogStatus.FAIL, "Access is not added on department change");
						
							
						}
						for (int i=0;i<accessesAssignedToUser.size();i++){
							String accessAssignedName=accessesAssignedToUser.get(i);
							String uiActionStatus=DBValidations.getUIActionOfAccess(requestNumber,accessAssignedName);
							if(Utility.compareStringValues(uiActionStatus, "REMOVE")) 
								logger.log(LogStatus.PASS, "status of existing  access :" +accessAssignedName+ " is removed when department is modified");
							else
								logger.log(LogStatus.FAIL, "Access status is not removed");
						
						}
					}
				}
				
				
				
				if(Utility.compareStringValues(requestType, "Temp Worker Offboarding")){
					/*
					 * checking for accesses status after offboarding rquest
					 */
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					for (int i=0;i<accessesAssignedToUser.size();i++){
						String accessName=accessesAssignedToUser.get(i);
						String uiActionStatus=DBValidations.getUIActionOfAccess(requestNumber,accessName);
						if(Utility.compareStringValues(uiActionStatus, "REMOVE")) 
							logger.log(LogStatus.PASS, "status of  access :" +accessName+ " is removed ");
						else
							logger.log(LogStatus.FAIL, "Access status is not removed");
						
					}
					
					/*
					 * checking for systems list after offboarding request
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestSystemListGrid, "SystemList Grid", true,false);
					for (int i=0;i<systemsAssignedToUser.size();i++){
						String systemName=systemsAssignedToUser.get(i);
						String uiSystemStatus=DBValidations.getUiActionOfSystem(requestNumber,systemName); 
						if(Utility.compareStringValues(uiSystemStatus, "LOCK"))
							logger.log(LogStatus.PASS, "status of  system :" +systemName +" is LOCKED ");
						else
							logger.log(LogStatus.FAIL, "Systems status is not LOCKED");
						
					}
					
					/*
					 * checking for Asset status after offboarding request
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestBadgeListGrid, "Badge List Grid", true,false);
					String uiBadgeStatus=DBValidations.getUiActionOfAsset(requestNumber,AGlobalComponents.assetName);
					if(Utility.compareStringValues(uiBadgeStatus, "LOCK"))
						logger.log(LogStatus.PASS, "status of  asset is LOCK ");
					else
						logger.log(LogStatus.FAIL, "status of  asset is NOT DEACTIVATE");
					
					
					/**checking the history of the request **/
					logger.log(LogStatus.INFO, "Checking the history of the request ");
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
						  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
					  else{
						  ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
						  Utility.pause(1);
						  ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
					  }
						boolean flag=true;
						for(int i=0;i<10 && flag;i++){
							WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]"));
							String provMessage = provisioningMessage.getText();
							if((provMessage.contains("removal successful"))){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								for(int j=0;j<2;j++){
									if(driver.findElements(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]")).size()>0){
										Utility.verifyElementPresent("(//div[text()='Provisioning Done for :']//parent::div//div)[2]", "Provisioning message", false);
										Utility.pause(2);
										break;
									}
									Thread.sleep(2000);
								}
								
							}
							
							else{
								ByAttribute.click("xpath", "//div[@class='x-tool-tool-el x-tool-img x-tool-close ']", "close history window pop up ");
								Utility.pause(20);
								if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
									  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
								 else{
									  ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
									  Utility.pause(1);
									  ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
								  }
							}
						
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
						
					
				}	
				
				
				if(Utility.compareStringValues(requestType, "Temp Worker Rehire")){
					/*
					 * checking for accesses status after rehiring
					 */
					HashMap<String, Comparable> testData = Utility.getDataFromDatasource(scriptName);
					accessesAssignedToUser.clear();
					accessesAssignedToUser.add(0, (String) testData.get("access_name_1"));	
					accessesAssignedToUser.add(1, (String) testData.get("access_name_2"));
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					for (int i=0;i<accessesAssignedToUser.size();i++){
						String accessName=accessesAssignedToUser.get(i);
						String uiAccessStatus=DBValidations.getUIActionOfAccess(requestNumber,accessName);
						if(Utility.compareStringValues(uiAccessStatus, "ADD")) 
							logger.log(LogStatus.PASS, "status of  access :" +accessName+ " is added ");
						else
							logger.log(LogStatus.FAIL, "Access status is not removed");
						
					}
					
					/*
					 * checking for systems list after rehiring
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestSystemListGrid, "SystemList Grid", true,false);
					for (int i=0;i<systemsAssignedToUser.size();i++){
						String systemName=systemsAssignedToUser.get(i);
						String uiSystemStatus=DBValidations.getUiActionOfSystem(requestNumber,systemName); 
						if(Utility.compareStringValues(uiSystemStatus, "LOCK"))
							logger.log(LogStatus.FAIL, "Systems status is still LOCKED");
						else
							logger.log(LogStatus.PASS, "status of  system :" +systemName +" is unlock ");
							
						
					}
					
					/*
					 * checking for Asset status after rehiring
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestBadgeListGrid, "Badge List Grid", true,false);
					String uiBadgeStatus=DBValidations.getUiActionOfAsset(requestNumber,AGlobalComponents.assetName);
					if(Utility.compareStringValues(uiBadgeStatus, "ADD"))
						logger.log(LogStatus.PASS, "status of  asset is ACTIVE");
					else
						logger.log(LogStatus.FAIL, "status of  asset is still DEACTIVATE");
					
					
					/**checking the history of the request **/
					logger.log(LogStatus.INFO, "Checking the history of the request ");
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
						  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
					  else{
						  ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
						  Utility.pause(1);
						  ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
					  }
						boolean flag=true;
						for(int i=0;i<10 && flag;i++){
							
							WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]"));
							String provMessage = provisioningMessage.getText();
							if((provMessage.contains("assignment successful"))){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								for(int j=0;j<2;j++){
									if(driver.findElements(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]")).size()>0){
										Utility.verifyElementPresent("(//div[text()='Provisioning Done for :']//parent::div//div)[2]", "Provisioning message", false);
										Utility.pause(2);
										break;
									}
									Thread.sleep(2000);
								}
								
							}
							
							else{
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
								Utility.pause(20);
								if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
									  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
								 else{
									  ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
									  Utility.pause(1);
									  ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
								  }
							}
						
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
				}	
				
				
				
				if(Utility.compareStringValues(requestType, "Emergency Termination")){
					/*
					 * checking for accesses status after termination rquest
					 */
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					for (int i=0;i<accessesAssignedToUser.size();i++){
						String accessName=accessesAssignedToUser.get(i);
						String uiAccessStatus=DBValidations.getUIActionOfAccess(requestNumber,accessName);
						if(Utility.compareStringValues(uiAccessStatus, "REMOVE")) 
							logger.log(LogStatus.PASS, "status of  access :" +accessName+ " is removed ");
						else
							logger.log(LogStatus.FAIL, "Access status is not removed");
						
					}
					
					/*
					 * checking for systems list after termination request
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestSystemListGrid, "SystemList Grid", true,false);
					for (int i=0;i<systemsAssignedToUser.size();i++){
						String systemName=systemsAssignedToUser.get(i);
						String uiSystemStatus=DBValidations.getUiActionOfSystem(requestNumber,systemName); 
						if(Utility.compareStringValues(uiSystemStatus, "LOCK"))
							logger.log(LogStatus.PASS, "status of  system :" +systemName +" is LOCKED ");
						else
							logger.log(LogStatus.FAIL, "Systems status is not LOCKED");
						
					}
					
					/*
					 * checking for Asset status after termination request
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestBadgeListGrid, "Badge List Grid", true,false);
					String uiBadgeStatus=DBValidations.getUiActionOfAsset(requestNumber,AGlobalComponents.assetName);
					if(Utility.compareStringValues(uiBadgeStatus, "LOCK"))
						logger.log(LogStatus.PASS, "status of  asset is : "+ uiBadgeStatus);
					else
						logger.log(LogStatus.FAIL, "status of  asset is locked or deactivated");
					
					
					/**checking the history of the request **/
					logger.log(LogStatus.INFO, "Checking the history of the request ");
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
						  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
					  else{
						  ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
						  Utility.pause(1);
						  ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
					  }
						boolean flag=true;
						for(int i=0;i<10 && flag;i++){
							
							WebElement provisioningMessage = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :']//parent::div//div)[2]"));
							String provMessage = provisioningMessage.getText();
							if((provMessage.contains("removal successful"))){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								for(int j=0;j<2;j++){
									if(driver.findElements(By.xpath("((//div[contains(text(),' removal successful ')])[1]//parent::div)[1]")).size()>0){
										Utility.verifyElementPresent("((//div[contains(text(),' removal successful ')])[1]//parent::div)[1]", "Provisioning message", false);
										Utility.pause(2);
										break;
									}
									Thread.sleep(2000);
								}
								
							}
							
							else{
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
								Utility.pause(20);
								if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
									  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
								 else{
									  ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
									  Utility.pause(1);
									  ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
								  }
							}
						
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
						
					
				}	
				
				
				
			}catch (Exception e) {
 				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	private static int getBadgeListGridHeadersInRequest() {
		int statusIndex=0;
		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
			boolean flag=true;
						
			for (int i=1;i<size && flag;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println("heading "+ (i) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "status":
		            	statusIndex = j;
		            	j++;
		            	flag=false;
		            	break;
		            case "":
		            	j++;
		                break;
		            default: 
		            	System.out.println("Need to skip this header : "+ heading);
		            	j++;
					}
				}
			}
			catch(Exception e){
				logger.log(LogStatus.ERROR, "Failed: Header Not Found ");
			}
		return statusIndex;
	}
	

	/**
	 * <h1>Temporary worker Onboarding</h1> 
	 * This is Method to hire temporary worker by Manager
	 * @author Monika Mehta
	 * @modified
	 * @version 1.0
	 * @since 01-21-2021
	 * @param none
	 * @return none
	 * @throws Throwable 
	 **/

	public static String temporaryWorkerOnboarding(String firstName , String lastName) throws Throwable {
		if (unhandledException == false) {
			System.out.println("******** Temporary worker onboarding By Manager*********");
			logger.log(LogStatus.INFO,"*********Temporary worker onboarding By Manager**************");
			
			try {
				
					WebElement requestor=driver.findElement(By.xpath(".//span[@class='x-btn-wrap x-btn-wrap-aetextlink-medium x-btn-arrow x-btn-arrow-right' and @role='presentation']"));
					requestorName = requestor.getText();
				
					if (driver.findElements(By.xpath(HomeObjects.TempWorkerTab)).size() > 0) 
					{
					ByAttribute.mouseHover("xpath", HomeObjects.TempWorkerTab, "Mouse hover on Temp Worker tab");
					ByAttribute.click("xpath", HomeObjects.TempWorkerOnboardingLnk, "Click on Temp worker Onboarding");
					Utility.pause(5);
					
													
					ByAttribute.click("xpath",HomeObjects.homeAccessRequestFirstNameTxt , "Click to enter first name ");
					ByAttribute.setText("xpath",HomeObjects.homeAccessRequestFirstNameTxt,firstName , "Enter First name");
					Utility.pause(2);
					
					ByAttribute.click("xpath",HomeObjects.homeAccessRequestLastNameTxt , "Click to enter last name ");
					ByAttribute.setText("xpath",HomeObjects.homeAccessRequestLastNameTxt,lastName , "Enter Last name");
					Utility.pause(2);
					
					ByAttribute.click("xpath",HomeObjects.homeAccessRequestCountryDdn , "Click to enter Country ");
					ByAttribute.setText("xpath",HomeObjects.homeAccessRequestCountryDdn,"United States Of America" , "Enter Country");
					Utility.pause(2);
					ByAttribute.click("xpath","//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'United States')]" , "Select the country");
					Utility.pause(2);
					
					ByAttribute.click("xpath",HomeObjects.homeAccessRequestDepartmentDdn , "Click to enter department name ");
					ByAttribute.setText("xpath",HomeObjects.homeAccessRequestDepartmentDdn,"Sales" , "Enter department name");
					Utility.pause(2);
					ByAttribute.click("xpath","//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'Sales')]" , "Select the department");
					Utility.pause(2);
					
					Actions action = new Actions(driver);
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestLocationDdn)).size()>0){
						ByAttribute.click("xpath",HomeObjects.homeAccessRequestLocationDdn , "Click to enter location ");
						action.sendKeys("Kansas").build().perform();
						Utility.pause(2);
						ByAttribute.click("xpath","//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'Kansas')]" , "Select the location");
						Utility.pause(2);
					}					       
		       //     ByAttribute.click("xpath",HomeObjects.submitBtn, "Click on submit button");
					WebElement submitBtn = driver.findElement(By.xpath(HomeObjects.submitBtn));
					
					action.click(submitBtn).build().perform();	
					Utility.pause(20);
		            if(AGlobalComponents.tempWorkerOnboarding)
		            	AGlobalComponents.RequestSubmit=true;
		            
		            if(driver.findElements(By.xpath(HomeObjects.homeOpenRequestsLnk)).size()>0){
		            	ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse hover on Home tab");
		            	ByAttribute.click("xpath", HomeObjects.homeMyRequestLnk, "click on My Requests");
		            	Utility.pause(10);
		            }
		            List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					WebElement latestRequestNumber = requestNumberElements.get(0);
					
					reqNum = latestRequestNumber.getText();
				}else {
					System.out.println("Navigation to 'Create Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Create Request' Page Failed");
				}

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		
		}
	
		return reqNum;
	}



	public static String tempWorkerModification(String firstName,String lastName,String parameterToBeModified) throws Throwable {
		if (unhandledException == false) {
			System.out.println("******** Modification of Temporary worker  By Manager*********");
			logger.log(LogStatus.INFO,"*********Modification of Temporary worker  By Manager**************");
			try {
				
				WebElement requestor=driver.findElement(By.xpath(".//span[@class='x-btn-wrap x-btn-wrap-aetextlink-medium x-btn-arrow x-btn-arrow-right' and @role='presentation']"));
				requestorName = requestor.getText();
				
				if (driver.findElements(By.xpath(HomeObjects.TempWorkerTab)).size() > 0) 
				{
					ByAttribute.mouseHover("xpath", HomeObjects.TempWorkerTab, "Mouse hover on Temp Worker tab");
					ByAttribute.click("xpath", HomeObjects.TempWorkerModificationLnk, "Click on Temp worker modification");
					Thread.sleep(1000);
					
					Utility.handleAnnouncementPopup();
					
					logger.log(LogStatus.INFO, "Search the user to be modified");
					WebElement identity = driver.findElement(By.xpath(".//*[contains(@class,'x-placeholder-label') and text()='Search Identity or User']"));
					Actions action = new Actions(driver);
					action.sendKeys(identity, firstName);
					action.build().perform();
					Utility.pause(2);
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					switch(parameterToBeModified.toLowerCase()){
					case "lastname":
							modifiedLastName="lastNameModified";
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestLastNameTxt, modifiedLastName, "Enter the value of modified last name ");
							Utility.pause(2);
							break;
					case "department":
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestDepartmentDdn, departmentName, "Enter the value of new department ");
							Utility.pause(2);
							ByAttribute.click("xpath","//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'"+departmentName+"')]" , "Select the department");
							Utility.pause(2);
							break;
					default: 
		            		System.out.println("No parameter updation request is received");
					}
					
					
					ByAttribute.click("xpath",HomeObjects.submitBtn, "Click on submit button");
					Utility.pause(5);
					while((driver.findElements(By.xpath(HomeObjects.submitBtn))).size()>0){
					     Utility.pause(5);
					}
					if(driver.findElements(By.xpath(HomeObjects.homeOpenRequestsLnk)).size()>0){
		            	ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse hover on Home tab");
		            	ByAttribute.click("xpath", HomeObjects.homeMyRequestLnk, "click on My Requests");
		            	Utility.pause(10);
		            }
		            List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					WebElement latestRequestNumber = requestNumberElements.get(0);
					
					reqNum = latestRequestNumber.getText();
					
		            AGlobalComponents.RequestSubmit=true;
				}else {
					System.out.println("Navigation to 'Create Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Create Request' Page Failed");
				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		
		}
		return reqNum;
	}



	public static String employeeConversion(String firstName) throws Throwable {
		String reqNum="";
		
		if (unhandledException == false) {
			System.out.println("******** Employment Type conversion from contractor to Permanent By Manager*********");
			logger.log(LogStatus.INFO,"*********Employment Type conversion from contractor to Permanent By Manager**************");
			try {
						
				WebElement requestor=driver.findElement(By.xpath(".//span[@class='x-btn-wrap x-btn-wrap-aetextlink-medium x-btn-arrow x-btn-arrow-right' and @role='presentation']"));
				requestorName = requestor.getText();
				
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeMyRequestsLnk, "Click on My Requests Link");
					Utility.pause(5);
				}
				
				Utility.handleAnnouncementPopup();
				
				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) 
				{
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					Utility.pause(2);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOtherRequestsPod, "Click on Other Requests Pod");
					Utility.pause(2);
					ByAttribute.click("xpath", HomeObjects.selectRequestType,"Enter the request type");
					ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSelectRequestTypeTxt, "Employment Type Conversion", "Enter Request Type");
					Thread.sleep(4000);
					ByAttribute.click("xpath", ".//li[@role='option' and text()='Employment Type Conversion']", "Select Request Type as: Employment Type Conversion");
					Thread.sleep(2000);
					Actions action = new Actions(driver);
					action.sendKeys(Keys.TAB);
					action.sendKeys(firstName);
					action.build().perform();
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					WebElement employeeType = driver.findElement(By.xpath(HomeObjects.employeeType));
					employeeType.clear();
					ByAttribute.setText("xpath",HomeObjects.employeeType,"Permanent", "Enter the employement type as permanent");
					action.sendKeys(Keys.TAB);
					action.sendKeys("Employment Conversion from Contractor to Permanent");
					action.build().perform();
						         	            
		            ByAttribute.click("xpath",HomeObjects.submitBtn, "Click on submit button");
		            Utility.pause(5);
		            List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					WebElement latestRequestNumber = requestNumberElements.get(0);
					reqNum = latestRequestNumber.getText();
		            AGlobalComponents.RequestSubmit=true;
		            
				}else {
					System.out.println("Navigation to 'Access Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Access Request' Page Failed");
				}

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		
		}
	
		return reqNum;
	}
		


	public static String modifyIdentity(String firstName,String parameterToBeUpdated,String request_Type) throws Throwable {
		
		
		if (unhandledException == false) {
			System.out.println("***************************** Modify Identity By Manager*********************************");
			logger.log(LogStatus.INFO,"***************************** Modify Identity By Manager*********************************");
			
			try {
				
				 WebElement requestor=driver.findElement(By.xpath(".//span[@class='x-btn-wrap x-btn-wrap-aetextlink-medium x-btn-arrow x-btn-arrow-right' and @role='presentation']"));
				requestorName = requestor.getText();
				
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeMyRequestsLnk, "Click on My Requests Link");
					Utility.pause(5);
				}
				
				
				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) 
				{
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOtherRequestsPod, "Click on Other Requests Pod");
					Utility.pause(2);
					ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceSelectRequestTypeTxt, request_Type, "Enter Request Type");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//li[@role='option' and contains(text(),'"+request_Type+"')]", "Select Request Type as: "+request_Type);
					Thread.sleep(2000);
					Actions action = new Actions(driver);
					action.sendKeys(Keys.TAB);
					action.sendKeys(firstName);
					action.build().perform();
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					switch (parameterToBeUpdated.toLowerCase()) {
		            case "photo":
		            	if(AGlobalComponents.updatePhoto)
							 logger.log(LogStatus.INFO,"***************************** Modifying existing photo*********************************");
		            	else
		            		logger.log(LogStatus.INFO,"***************************** UPloading photo first time*********************************");
					
		            	String photoFilePath="";
		            	if(AGlobalComponents.updatePhoto)
		            		photoFilePath=System.getProperty("user.dir") + "\\Browser_Files\\UpdatedUserImage.png";
		            	else
		            		photoFilePath=System.getProperty("user.dir") + "\\Browser_Files\\Applicant_Photo.jpg";
						ByAttribute.click("xpath",HomeObjects.uploadImgBtn, "Click on upload button to update the photo");
						Thread.sleep(5000);
										
						StringSelection ss = new StringSelection(photoFilePath);
			            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			            
			            Robot robot = new Robot();
			            robot.keyPress(KeyEvent.VK_CONTROL);
			            robot.keyPress(KeyEvent.VK_V);
			            robot.keyRelease(KeyEvent.VK_V);
			            robot.keyRelease(KeyEvent.VK_CONTROL);
			            robot.keyPress(KeyEvent.VK_ENTER);
			            robot.keyRelease(KeyEvent.VK_ENTER);
						Utility.pause(10);
						ByAttribute.click("xpath",HomeObjects.homeMyRequestsActualPhotoLnk, "Click on Actual Photo to crop");
						Utility.pause(10);
			            ByAttribute.click("xpath",HomeObjects.cropAndSaveBtn, "Click on Crop and Save button");
		            	
		        //    	WebElement imageElement = driver.findElement(By.xpath("//label[@class='x-component x-box-item x-component-activitySubtext' and text()='photo']//parent::div//*[@class='x-img x-box-item x-img-default']"));    
		         //   	((JavascriptExecutor)driver).executeScript("arguments[0].src='"+photoFilePath+"'", imageElement);
		            	
			            Utility.pause(4);
			            if(driver.findElements(By.xpath(HomeObjects.homeMyRequestsUploadedImageLnk)).size()>0){
			            	logger.log(LogStatus.PASS, "Image uploaded successfully");
			            	Utility.verifyElementPresent(HomeObjects.homeMyRequestsUploadedImageSectionLnk, "Uploaded Image", false);
			            }
		            	break;
		            case "lastname":
		            	modifiedLastName = "Newlastname";
		            	ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentityProfileInfoLastNameTxt, modifiedLastName, "Enter the value of modified last name ");
						Utility.pause(2);
		            	break;
		            case "phoneno":
		            	modifiedPhoneNumber = Utility.getRandomIntNumber(5);
		            	ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentityProfileInfoPhoneNoTxt, String.valueOf(modifiedPhoneNumber), "Enter the value of modified phone number ");
						Utility.pause(2);
		            	break;
		            case "department":
						ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestDepartmentDdn, departmentName, "Enter the value of new department ");
						Utility.pause(2);
						ByAttribute.click("xpath","//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'"+departmentName+"')]" , "Select the department");
						Utility.pause(2);
						break;
		            default: 
		            	System.out.println("No parameter updation request is received");
		            	
					}
					
					ByAttribute.click("xpath",HomeObjects.submitBtn, "Click on submit button");
		            Utility.pause(5);
		            List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					WebElement latestRequestNumber = requestNumberElements.get(0);
					
					reqNum = latestRequestNumber.getText();
		            AGlobalComponents.RequestSubmit=true;
				}else {
					System.out.println("Navigation to 'Access Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Access Request' Page Failed");
				}

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		
		}
		return reqNum;
	}



	
	

	/**
	 * <h1>Approve Request</h1> 
	 * This is Method to approve the request by respective workflow Actor
	 * @author Monika Mehta
	 * @modified
	 * @version 1.0
	 * @since 02-09-2021
	 * @param String WfActor
	 * @return none
	 **/


	public static void approveRequest(String WfActor, String requestNumber,String accessName) throws Throwable {
		if (unhandledException == false) {
			try {
				boolean requestPresent=false;
				logger.log(LogStatus.INFO, "Approving the request ");
						
				if(driver.findElements(By.xpath(HomeObjects.homeTabBtn)).size()>0){
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeInboxLnk, "Click on Inbox Link");
					Utility.pause(10);
				}
				else	
				{
					ByAttribute.click("xpath", HomeObjects.inboxTabBtn, "Click on Inbox Link");
					Utility.pause(15);
				}
				
				boolean inboxLoadedFlag=false;
				for (int i=0;i<5 & (!inboxLoadedFlag);i++){
					if ((driver.findElements(By.xpath("//*[text()='No message selected!']")).size()>0))
						Utility.pause(5);
					else{
						inboxLoadedFlag=true;
						System.out.println("Approvers Inbox loaded");
						logger.log(LogStatus.PASS, "Approvers Inbox loaded");
					}	
				}
				
				if(inboxLoadedFlag){
					if (driver.findElements(By.xpath(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+requestNumber+"']")).size() > 0) {
						System.out.println("Access Request is Found in Inbox");
						logger.log(LogStatus.PASS, "Access Request is Found in Inbox");
					
						Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+requestNumber+"']", "Request Number: "+reqNum+" Current Status OPEN", false);
						ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+requestNumber+"']", "Click on Request from Inbox");
						Thread.sleep(3000);
							
						if(Utility.compareStringValues("manager", WfActor)){
							System.out.println("Approving the request by : "+WfActor);
							logger.log(LogStatus.INFO,"******Approving the  request by ******: "+WfActor);
							if (driver.findElements(By.xpath(IdentityObjects.idmProfileUserIdTxt)).size()>0){
								AGlobalComponents.userId=driver.findElement(By.xpath(IdentityObjects.idmProfileUserIdTxt)).getAttribute("value");
							}
							if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestApproveButton)).size()>0){
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestApproveButton, "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}
							else if(driver.findElements(By.xpath(".//button[@class='aegrid-active2']")).size()>0)
							{
								ByAttribute.click("xpath", ".//button[@class='aegrid-active2']", "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}else if (driver.findElements(By.xpath(HomeObjects.homeMyRequestsActionMenuBtn)).size()>0){
								ByAttribute.click("xpath", HomeObjects.homeMyRequestsActionMenuBtn, "Click Actions Button");
								Utility.pause(1);
								ByAttribute.click("xpath", HomeObjects.homeMyRequestsApprovalMenuItemLnk, "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}else{
								System.out.println("Approve button not visible");
								logger.log(LogStatus.FAIL, "Approve button not visible");
							}
					
						
						}
				
						if(Utility.compareStringValues("access_owner", WfActor)){
							System.out.println("Approving the request by : "+WfActor);
							logger.log(LogStatus.INFO,"******Approving the  request by ******: "+WfActor);
							if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestApproveButton)).size()>0){
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestApproveButton, "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}
							else if(driver.findElements(By.xpath(".//button[@class='aegrid-active2']")).size()>0)
							{
								ByAttribute.click("xpath", ".//button[@class='aegrid-active2']", "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}else if (driver.findElements(By.xpath(HomeObjects.homeMyRequestsActionMenuBtn)).size()>0){
								ByAttribute.click("xpath", HomeObjects.homeMyRequestsActionMenuBtn, "Click Actions Button");
								Utility.pause(1);
								ByAttribute.click("xpath", HomeObjects.homeMyRequestsApprovalMenuItemLnk, "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}else{
								System.out.println("Approve button not visible");
								logger.log(LogStatus.FAIL, "Approve button not visible");
							}
					
						
						}
						if(Utility.compareStringValues("admin_user", WfActor)){
							System.out.println("Approving the  request by : "+WfActor);
							logger.log(LogStatus.INFO,"******Approving the request by ******: "+WfActor);
							if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestApproveButton)).size()>0){
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestApproveButton, "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}
							else if(driver.findElements(By.xpath(".//button[@class='aegrid-active2']")).size()>0)
							{
								ByAttribute.click("xpath", ".//button[@class='aegrid-active2']", "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}
							else if(driver.findElements(By.xpath(".//button[@data-qtip='Click to Approve']")).size()>0)
							{
								ByAttribute.click("xpath", ".//button[@data-qtip='Click to Approve']", "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}else if (driver.findElements(By.xpath(HomeObjects.homeMyRequestsActionMenuBtn)).size()>0){
								ByAttribute.click("xpath", HomeObjects.homeMyRequestsActionMenuBtn, "Click Actions Button");
								Utility.pause(1);
								ByAttribute.click("xpath", HomeObjects.homeMyRequestsApprovalMenuItemLnk, "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}else{
								System.out.println("Approve button not visible");
								logger.log(LogStatus.FAIL, "Approve button not visible");
							}
					
						
						}
						if(Utility.compareStringValues("areaAdmin", WfActor)){
							System.out.println("Approving the  request by : "+WfActor);
							logger.log(LogStatus.INFO,"******Approving the  request by ******: "+WfActor);
							Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+accessName+"']", "Access Name Selected by Manager", false);
							if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestApproveButton)).size()>0){
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestApproveButton, "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}
							else if(driver.findElements(By.xpath(".//button[@class='aegrid-active2']")).size()>0)
							{
								ByAttribute.click("xpath", ".//button[@class='aegrid-active2']", "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}else if (driver.findElements(By.xpath(HomeObjects.homeMyRequestsActionMenuBtn)).size()>0){
								ByAttribute.click("xpath", HomeObjects.homeMyRequestsActionMenuBtn, "Click Actions Button");
								Utility.pause(1);
								ByAttribute.click("xpath", HomeObjects.homeMyRequestsApprovalMenuItemLnk, "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}else{
								System.out.println("Approve button not visible");
								logger.log(LogStatus.FAIL, "Approve button not visible");
							}
						}
						if(Utility.compareStringValues("badgeAdmin", WfActor)){
							System.out.println("Approving the  request by : "+WfActor);
							logger.log(LogStatus.INFO,"******Approving the  request by  ******: "+WfActor);
							Thread.sleep(4000);
					
							/*Giving badge to the user */
							ByAttribute.click("xpath", SelfServiceObjects.selfServiceSelectBadgeDdn, "Select Asset from list");
							Thread.sleep(2000);
							
							ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceEnterAssetNameTxt, AGlobalComponents.assetName, "Enter Asset Name: "+AGlobalComponents.assetName);
							Thread.sleep(4000);
							ByAttribute.click("xpath", ".//span[@data-qtip='"+AGlobalComponents.assetName+"' and text()='"+AGlobalComponents.assetName+"']", "Click on Asset Option");
							Thread.sleep(1000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestSystemListGrid, "Move Control");
					
							Thread.sleep(2000);
							Utility.pause(5);
					
							ByAttribute.click("xpath",HomeObjects.homeAccessRequestBadgeListGrid,"Asset assigned");
							Utility.verifyElementPresent("//*[text()='"+AGlobalComponents.badgeId+"']", "badgeId", false);
							if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestApproveButton)).size()>0){
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestApproveButton, "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}
							else if(driver.findElements(By.xpath(".//button[@class='aegrid-active2']")).size()>0)
							{
								ByAttribute.click("xpath", ".//button[@class='aegrid-active2']", "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}else if (driver.findElements(By.xpath(HomeObjects.homeMyRequestsActionMenuBtn)).size()>0){
								ByAttribute.click("xpath", HomeObjects.homeMyRequestsActionMenuBtn, "Click Actions Button");
								Utility.pause(1);
								ByAttribute.click("xpath", HomeObjects.homeMyRequestsApprovalMenuItemLnk, "Click Approve Button");
								Utility.pause(5);
								logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
							}else{
								System.out.println("Approve button not visible");
								logger.log(LogStatus.FAIL, "Approve button not visible");
							}
							ByAttribute.click("xpath",HomeObjects.homeAccessRequestYesButtonOnPopUpWindow, "Click yes button to save the changes done in badge");
							Utility.pause(15);
							logger.log(LogStatus.PASS,"Request successfully approved by badge admin");
						
						}
					} else 
						logger.log(LogStatus.FAIL, "request number not present in approvers inbox");	
				}
				else
					logger.log(LogStatus.FAIL, "Inbox not loaded");
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
}		
	
	
	public static void checkRequestInInbox(String requestType,String firstName,String lastName) throws Throwable {
		
		if (unhandledException == false) {
			System.out.println("***************************** Checking the status in the Request *********************************");
			logger.log(LogStatus.INFO,"*************** Checking the status in the Request *********************************");
			try{		
				String requestNumber="";
				if(driver.findElements(By.xpath(HomeObjects.homeOpenRequestsLnk)).size()>0){
					ByAttribute.mouseHover("xpath",HomeObjects.homeTabBtn,"Mouse hover on Home tab to open My Requests");
					ByAttribute.click("xpath",HomeObjects.homeInboxLnk,"click on Inbox");
					
				}
				
				if(requestType.equalsIgnoreCase("Employee Offboarding")){
					requestNumber=DBValidations.getAccessRequestOfTerminateFromDB();
					HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC014");
					accessNewForChangeJobTitle = (String) testData.get("access_name_1");					
					HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource("FB_Automation_TC011");
					system1ForEmpOnboarding= (String) testData1.get("system_name");
					system2ForEmpOnboarding= (String) testData1.get("system_name2");
					
				}
				if(requestType.equalsIgnoreCase("Employee Modification")){
					requestNumber=DBValidations.getAccessRequestOfEmployeeModificationFromDB();
					HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC012");
					modifiedLastName = (String) testData.get("last_name");
					HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource("FB_Automation_TC011");
					access2ForEmpOnboarding= (String) testData1.get("access_name_2");
					
				}
				if(requestType.equalsIgnoreCase("Employee Rehire")){
					requestNumber=DBValidations.getAccessRequestOfEmployeeRehireFromDB();
					HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC011");
					system1ForEmpOnboarding= (String) testData.get("system_name");
					system2ForEmpOnboarding= (String) testData.get("system_name2");
					HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource("FB_Automation_TC016");
					access1ForEmpOnboarding= (String) testData1.get("access_name_1");
					
				}
				if(requestType.equalsIgnoreCase("Transfer")){
					requestNumber=DBValidations.getAccessRequestOfEmployeeTransferFromDB();
					HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC014");
					accessNewForChangeJobTitle = (String) testData.get("access_name_1");
					locationName=(String) testData.get("location_name_1");
					
				}
				if(requestType.equalsIgnoreCase("Employement Type Conversion")) {						
					requestNumber=DBValidations.getAccessRequestOfEmployeeTypeConversionFromDB(AGlobalComponents.userId);
					HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC011");
					access1ForEmpOnboarding= (String) testData.get("access_name_1");
					HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource("FB_Automation_TC013");
					employeeType=(String) testData1.get("identity_type");
					access2ForEmpOnboarding= (String) testData1.get("access_name_1");
				}
				if(requestType.equalsIgnoreCase("Employee Onboarding")) {						
					requestNumber=DBValidations.getAccessRequestNoFromDB(AGlobalComponents.userId);
					HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC011");
					access1ForEmpOnboarding= (String) testData.get("access_name_1");
					access2ForEmpOnboarding= (String) testData.get("access_name_2");
					system1ForEmpOnboarding= (String) testData.get("system_name");	
					system2ForEmpOnboarding= (String) testData.get("system_name2");
				}
				
				if(Utility.waitTillInboxLoad()) {
				List<WebElement> requestNumberElements = driver.findElements(By.xpath("//div[@class='x-grid-cell-inner ']/descendant::div"));
				WebElement requestNo=null;
				for (int i=0;i<requestNumberElements.size();i++){
					requestNo = requestNumberElements.get(i);
					reqNum = requestNo.getText();
					if(Utility.compareStringValues(reqNum, requestNumber))
						break;
				}
					
				Actions action = new Actions(driver);
				action.doubleClick(requestNo);
				action.build().perform();
				Utility.pause(5);
					
				Utility.verifyElementPresent("//div[contains(@class,'x-component') and text()='"+reqNum+"']", "Request", false);
				
				Utility.verifyElementPresent("//div[contains(@class,'x-component') and text()='"+requestType+"']", "Request Type:'"+requestType+"'", false);
				
				WebElement requestBy = driver.findElement(By.xpath("//div[text()='Request By']//parent::div//label"));
				String requesterName= requestBy.getText();
				if(Utility.compareStringValues(requesterName, "ADMIN USER")) {
					logger.log(LogStatus.INFO ,"Request opened successfully in my request inbox");
					Utility.verifyElementPresent("//div[text()='Request By']//parent::div//label", "Request By: '"+requesterName+"'", false);
				}
				else
					logger.log(LogStatus.FAIL ,"Incorrect request  is expanded");	
					
				if(requestType.equalsIgnoreCase("Employee Rehire")) {
					
					WebElement requestFor = driver.findElement(By.xpath("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']"));
					String requestedFor = requestFor.getText();
					if(Utility.compareStringValues(requestedFor, firstName+" "+lastName.toUpperCase())){
						Utility.verifyElementPresent("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']", "Request for: "+requestedFor , false);
						logger.log(LogStatus.INFO ,"RequestFor "+requestedFor+" is present");
					}else
						logger.log(LogStatus.FAIL ,"RequestFor "+requestedFor+" is not present");
					
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					if((driver.findElements(By.xpath("//*[text()='"+access1ForEmpOnboarding+"']")).size()>0)){
						String uiAccessStatus=DBValidations.getUIActionOfAccess(requestNumber,access1ForEmpOnboarding);
						if((Utility.compareStringValues(uiAccessStatus, "ADD")))
							logger.log(LogStatus.PASS, "Access :" +access1ForEmpOnboarding +" is added ");
						else
							logger.log(LogStatus.FAIL, "Access status is not added");			
					}
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestSystemListGrid, "SystemList Grid", true,false);
					if((driver.findElements(By.xpath("//*[text()='"+system1ForEmpOnboarding+"']")).size()>0) && (driver.findElements(By.xpath("//*[text()='"+system2ForEmpOnboarding+"']")).size()>0)){
						String uiSystemStatus1=DBValidations.getUiActionOfSystem(requestNumber,system1ForEmpOnboarding); 
						String uiSystemStatus2=DBValidations.getUiActionOfSystem(requestNumber,system2ForEmpOnboarding);
						if((Utility.compareStringValues(uiSystemStatus1, "UNLOCK")) && (Utility.compareStringValues(uiSystemStatus2, "UNLOCK")))
							logger.log(LogStatus.PASS, "status of  systems :" +system1ForEmpOnboarding +","+system2ForEmpOnboarding+ ","+system2ForEmpOnboarding+ "is LOCKED ");
						else
							logger.log(LogStatus.FAIL, "Systems status is not LOCKED");			
					} 
				}
		
				if(requestType.equalsIgnoreCase("Employee Offboarding")) {
					
					WebElement requestFor = driver.findElement(By.xpath("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']"));
					String requestedFor = requestFor.getText();
					if(Utility.compareStringValues(requestedFor, firstName+" "+lastName.toUpperCase())){
						Utility.verifyElementPresent("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']", "Request for: "+requestedFor , false);
						logger.log(LogStatus.INFO ,"RequestFor "+requestedFor+" is present");
					}else
						logger.log(LogStatus.FAIL ,"RequestFor "+requestedFor+" is not present");
					
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					if((driver.findElements(By.xpath("//*[text()='"+accessNewForChangeJobTitle+"']")).size()>0)){
						String uiAccessStatus=DBValidations.getUIActionOfAccess(requestNumber,accessNewForChangeJobTitle);
						if((Utility.compareStringValues(uiAccessStatus, "REMOVE")))
							logger.log(LogStatus.PASS, "Access :" +accessNewForChangeJobTitle +" is removed ");
						else
							logger.log(LogStatus.FAIL, "Access status is not removed");			
					}
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestSystemListGrid, "SystemList Grid", true,false);
					if((driver.findElements(By.xpath("//*[text()='"+system1ForEmpOnboarding+"']")).size()>0) && (driver.findElements(By.xpath("//*[text()='"+system2ForEmpOnboarding+"']")).size()>0)){
						String uiSystemStatus1=DBValidations.getUiActionOfSystem(requestNumber,system1ForEmpOnboarding); 
						String uiSystemStatus2=DBValidations.getUiActionOfSystem(requestNumber,system2ForEmpOnboarding);
						if((Utility.compareStringValues(uiSystemStatus1, "LOCK")) && (Utility.compareStringValues(uiSystemStatus2, "LOCK")))
							logger.log(LogStatus.PASS, "status of  systems :" +system1ForEmpOnboarding +","+system2ForEmpOnboarding+ ","+system2ForEmpOnboarding+ "is LOCKED ");
						else
							logger.log(LogStatus.FAIL, "Systems status is not LOCKED");			
					}
					
					/*
					 * checking for Asset status after offboarding request
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestBadgeListGrid, "Badge List Grid", true,false);
					String uiBadgeStatus=DBValidations.getUiActionOfAsset(requestNumber,AGlobalComponents.assetName);
					if(Utility.compareStringValues(uiBadgeStatus, "LOCK"))
						logger.log(LogStatus.PASS, "status of  asset is DEACTIVATE ");
					else
						logger.log(LogStatus.FAIL, "status of  asset is NOT DEACTIVATE"); 
					
				}
				
				else if(requestType.equalsIgnoreCase("Employement Type Conversion")) {
					
					WebElement requestFor = driver.findElement(By.xpath("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']"));
					String requestedFor = requestFor.getText();
					if(Utility.compareStringValues(requestedFor, firstName+" "+lastName.toUpperCase())){
						Utility.verifyElementPresent("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']", "Request for: "+requestedFor , false);
						logger.log(LogStatus.INFO ,"Request opened successfully ");
					}else
						logger.log(LogStatus.FAIL ,"Incorrect request  is expanded");
					

					ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Click on comparison button to verify the modifications");
					Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='"+employeeType+"']", "Employee Type", false);
					logger.log(LogStatus.PASS,"Changed Employee Type is present in comparison tab for Employee :"+employeeType);
					
					/*
					 * checking for accesses status after change of jobtitle rquest
					 */
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					if(driver.findElements(By.xpath("//*[text()='"+access2ForEmpOnboarding+"']")).size()>0){
						Utility.verifyElementPresent("//*[text()='"+access2ForEmpOnboarding+"']", access2ForEmpOnboarding, false);
						logger.log(LogStatus.INFO, "Access :" +access2ForEmpOnboarding +",  assigned to the user");	
					}
				}
				
				else if(requestType.equalsIgnoreCase("Employee Modification")) {
					
					WebElement requestFor = driver.findElement(By.xpath("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']"));
					String requestedFor = requestFor.getText();
					if(Utility.compareStringValues(requestedFor, firstName+" "+lastName.toUpperCase())){
						Utility.verifyElementPresent("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']", "Request for: "+requestedFor , false);
						logger.log(LogStatus.INFO ,"Request opened successfully ");
					}else
						logger.log(LogStatus.FAIL ,"Incorrect request  is expanded");
					

					ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Click on comparison button to verify the modifications");
					Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='"+modifiedLastName+"']", "Modified LastName", false);
					logger.log(LogStatus.PASS,"Modified Last Name is present in comparison tab for Employee :"+modifiedLastName);
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					if((driver.findElements(By.xpath("//*[text()='"+access2ForEmpOnboarding+"']")).size()>0)){
						String uiAccessStatus=DBValidations.getUIActionOfAccess(requestNumber,access2ForEmpOnboarding);
						if((Utility.compareStringValues(uiAccessStatus, "REMOVE")))
							logger.log(LogStatus.PASS, "Status of access :" +access2ForEmpOnboarding +"is removed ");
						else
							logger.log(LogStatus.FAIL, "Accesses status is not removed");			
					}	
				}
				
				else if(requestType.equalsIgnoreCase("Transfer")) {
					
					WebElement requestFor = driver.findElement(By.xpath("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']"));
					String requestedFor = requestFor.getText();
					if(Utility.compareStringValues(requestedFor, firstName+" "+lastName.toUpperCase())){
						Utility.verifyElementPresent("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']", "Request for: "+requestedFor , false);
						logger.log(LogStatus.INFO ,"Request opened successfully ");
					}else
						logger.log(LogStatus.FAIL ,"Incorrect request  is expanded");
					

					ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Click on comparison button to verify the modifications");
					Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='"+locationName+"']", "Modified Location", false);
					logger.log(LogStatus.PASS,"Modified Last Name is present in comparison tab for Employee :"+locationName);
					
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestAccessListGrid)).size()>0){
						Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
						Utility.verifyElementPresent("//*[text()='"+accessNewForChangeJobTitle+"']", accessNewForChangeJobTitle, false);
					}
					else
						logger.log(LogStatus.INFO, "Access Grid is not present");
				
				}
				
				else if(requestType.equalsIgnoreCase("Employee Onboarding")){
					
					String firstNameOnUI = driver.findElement(By.xpath(HomeObjects.homeAccessRequestFirstNameTxt)).getAttribute("value");
					if(firstNameOnUI.equalsIgnoreCase(firstName))
						Utility.verifyElementPresent(HomeObjects.homeAccessRequestFirstNameTxt, "FirstName", false);
					else
						logger.log(LogStatus.FAIL, "FirstName: "+firstName+"is not present");
					
					String lastNameOnUI = driver.findElement(By.xpath(HomeObjects.homeAccessRequestLastNameTxt)).getAttribute("value");
					if(lastNameOnUI.equalsIgnoreCase(lastName))
						Utility.verifyElementPresent(HomeObjects.homeAccessRequestLastNameTxt, "LastName", false);
					else
						logger.log(LogStatus.FAIL, "LastName: "+lastName+"is not present");
					
					/*
					 * checking for accesses added through pre feed rule while onboarding
					 */
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestAccessListGrid)).size()>0){
						Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
						Utility.verifyElementPresent("//*[text()='"+access1ForEmpOnboarding+"']", access1ForEmpOnboarding, false);
					}
					else
						logger.log(LogStatus.INFO, "Access Grid is not present");
				
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestSystemListGrid, "SystemList Grid", true,false);
					Utility.verifyElementPresent("//*[text()='"+system1ForEmpOnboarding+"']", system1ForEmpOnboarding, false);
					logger.log(LogStatus.PASS, "Systems are successfully assigned to the user");		
									
				}
				}
			}catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}

	public static void checkUserStatus() throws Throwable {

		if (unhandledException == false) {
			System.out.println("********* Check status in IDM *******************");
			logger.log(LogStatus.INFO,"******Check status in IDM ************");
			try {	
				if(AGlobalComponents.EmpOnboardingthroughHRDb){	
					String hrUserDataFile = "Test_Data/Recon/HRUserData.csv"; 
					String firstName=Utility.getCSVCellValue(hrUserDataFile, "FirstName", 1);
					String lastName=Utility.getCSVCellValue(hrUserDataFile, "LastName", 1);
			
					FB_Automation_CommonMethods.searchIdentity(firstName+"."+lastName);
					logger.log(LogStatus.PASS, "Employee Onboardidng done is successful , user is present in IDM");
			
					/*Validating access assigned to the user*/
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "Click on Access Tab ");
					Utility.verifyElementPresent("//*[text()='"+access1ForEmpOnboarding+"']", access1ForEmpOnboarding, false);
			//		Utility.verifyElementPresent("//*[text()='"+access2ForEmpOnboarding+"']", access2ForEmpOnboarding, false);
					logger.log(LogStatus.PASS, "Accesses are successfully assigned to the user");
			
					/* checking for asset added by badge admin at time of approval
					 */
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
					Utility.verifyElementPresent("//*[text()='"+AGlobalComponents.badgeId+"']", "badgeId", false);
					logger.log(LogStatus.PASS, "Asset is assigned to the user with asset Id : "+AGlobalComponents.badgeId);
					
					/*
					 * checking for Systems AMAG and HR added on the basis of Accesses
					 */
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySystemsTabBtn, "Click on Systems Tab ");
					Utility.verifyElementPresent(IdentityObjects.idmManageIdentitySystemsTabBtn, "AMAG and HR System", false);
					logger.log(LogStatus.PASS, "2 systems are successfully assigned to the user");
				}
			}catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}



		public static void approveRequestInInbox(String WfActor) throws Throwable {

		if (unhandledException == false) {
			try {
				logger.log(LogStatus.INFO, "Approving the request ");
				if(driver.findElements(By.xpath("//div[contains(@class,'x-component') and text()='"+reqNum+"']")).size()>0) {
					Utility.verifyElementPresent("//*[text()='"+reqNum+"']",reqNum , false);
						}
				else {
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse hover on Home Tab");
					ByAttribute.click("xpath",HomeObjects.homeDashboardLnk, "Click on Dashboard");
					Utility.pause(4);
					
					if(driver.findElements(By.xpath(HomeObjects.homeOpenRequestsLnk)).size()>0){
						ByAttribute.click("xpath", HomeObjects.homeOpenRequestsLnk, "Click on Open Requests Link to open the request");
						Utility.pause(5);
					}
				}
				
				Actions action = new Actions(driver);
				action.doubleClick(driver.findElement(By.xpath("//*[text()='"+reqNum+"']")));
				action.build().perform();
				Utility.pause(5);

				Utility.verifyElementPresent("//div[contains(@class,'x-component') and text()='"+reqNum+"']", "Request", false);
				
					if(Utility.compareStringValues("manager", WfActor)){
						WfActor="Manager";
						System.out.println("Approving the Employee onboarding request by : "+WfActor);
						logger.log(LogStatus.INFO,"******Approving the Employee onboarding request by manager ******: "+WfActor);
						Utility.verifyElementPresent("//div[@class='x-box-target']//*[text()='Stage: "+WfActor+"']", WfActor+" Stage", false);
						
						if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestApproveButton)).size()>0){
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestApproveButton, " Click approve button ");
							Utility.pause(5);
						}
						else if (driver.findElements(By.xpath(HomeObjects.homeMyRequestsActionMenuBtn)).size()>0){
							ByAttribute.click("xpath", HomeObjects.homeMyRequestsActionMenuBtn, "Click Actions Button");
							Utility.pause(1);
							ByAttribute.click("xpath", HomeObjects.homeMyRequestsApprovalMenuItemLnk, "Click Approve Button");
							Utility.pause(5);
							logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
						}else{
							System.out.println("Approve button not visible");
							logger.log(LogStatus.FAIL, "Approve button not visible");
						}
						if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestYesButtonOnPopUpWindow)).size()>0)
							ByAttribute.click("xpath",HomeObjects.homeAccessRequestYesButtonOnPopUpWindow, "Click yes button to save the changes done in manager stage");
						Utility.pause(5);
						logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
						
					}
					
					if(Utility.compareStringValues("Badge Admin", WfActor)){
						System.out.println("Approving the Employee onboarding request by : "+WfActor);
						logger.log(LogStatus.INFO,"******Approving the Employee onboarding request by  ******: "+WfActor);
						Utility.verifyElementPresent("//div[@class='x-box-target']//*[text()='Stage: "+WfActor+"']", WfActor+" Stage", false);
						
						/**creating asset for the user**/
//						String badgeName = Self_Service_CommonMethods.createNewAsset("Permanent Badge", "SRSeries_10And12Digit", "AMAG");
						
						/*Giving badge to the user */
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceSelectBadgeDdn, "Select Asset from list");
						Thread.sleep(2000);
						
						WebElement elementConnector=driver.findElement(By.xpath("//*[text()='Select Asset']"));
						action.moveToElement(elementConnector).click();
						action.sendKeys(AGlobalComponents.assetName);
						action.build().perform();
									
//						ByAttribute.clearSetText("xpath", SelfServiceObjects.selfServiceEnterAssetNameTxt, AGlobalComponents.assetName, "Enter Asset Name: "+AGlobalComponents.assetName);
						Thread.sleep(4000);
						ByAttribute.click("xpath", ".//span[@data-qtip='"+AGlobalComponents.assetName+"' and text()='"+AGlobalComponents.assetName+"']", "Click on Asset Option");
						Thread.sleep(1000);
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestSystemListGrid, "Move Control");
						
						Thread.sleep(2000);
						Utility.pause(5);
						
						if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestApproveButton)).size()>0){
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestApproveButton, " Click approve button ");
							Utility.pause(5);
						}
						else if (driver.findElements(By.xpath(HomeObjects.homeMyRequestsActionMenuBtn)).size()>0){
							ByAttribute.click("xpath", HomeObjects.homeMyRequestsActionMenuBtn, "Click Actions Button");
							Utility.pause(1);
							ByAttribute.click("xpath", HomeObjects.homeMyRequestsApprovalMenuItemLnk, "Click Approve Button");
							Utility.pause(5);
							logger.log(LogStatus.PASS,"Request successfully approved by : "+WfActor);
						}else{
							System.out.println("Approve button not visible");
							logger.log(LogStatus.FAIL, "Approve button not visible");
						}
						ByAttribute.click("xpath",HomeObjects.homeAccessRequestYesButtonOnPopUpWindow, "Click yes button to save the changes done in badge");
						Utility.pause(5);
						logger.log(LogStatus.PASS,"Request successfully approved by badge admin");		
					}	
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}	
	}



	


	public static void checkStatusInDB(String firstName, String lastName) throws Throwable {
		if (unhandledException == false) {
			
			try {
				logger.log(LogStatus.INFO,"Verify temp onboarded user from DB");
				String query = "select * from aepdemo.identity_user where first_name='"+firstName+"' and last_name='"+lastName+"'";
				ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
				//for each loop laga ke data print karna hai basically yahan
				
	
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}

		
	}



	public static String tempWorkerOffboarding(String firstName, String lastName) throws Throwable {
 		String reqNum="";
		if (unhandledException == false) {
			System.out.println("********Offboarding a Temporary Worker*********");
			logger.log(LogStatus.INFO,"*********Offboarding a Temporary Worker**************");
			try {
				
				WebElement requestor=driver.findElement(By.xpath(".//span[@class='x-btn-wrap x-btn-wrap-aetextlink-medium x-btn-arrow x-btn-arrow-right' and @role='presentation']"));
				requestorName = requestor.getText();
				String terminationReason = "Misconduct";
				
				Calendar c = Calendar.getInstance();
				DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
				Date date = new Date();
				String currentDate= dateFormat.format(date);
				System.out.println(currentDate);
				
				try{
					   //Setting the date to the given date
					   c.setTime(dateFormat.parse(currentDate));
					}catch(Exception e){
						e.printStackTrace();
					}
					   
					//Number of Days to add
					c.add(Calendar.DAY_OF_MONTH, 5);  
					//Date after adding the days to the given date
					String validTo = dateFormat.format(c.getTime());  
					//Displaying the new Date after addition of Days
					System.out.println("Date after Addition: "+validTo);
				
				
				
				if (driver.findElements(By.xpath(HomeObjects.TempWorkerTab)).size() > 0) 
				{
					ByAttribute.mouseHover("xpath", HomeObjects.TempWorkerTab, "Mouse hover on Temp Worker tab");
					ByAttribute.click("xpath", HomeObjects.TempWorkerOffboardingLnk, "Click on Temp worker offboarding");
					Utility.pause(5);
					
					Utility.handleAnnouncementPopup();
					
					logger.log(LogStatus.INFO, "Search the user to be offboarded");
					WebElement identity = driver.findElement(By.xpath(".//*[contains(@class,'x-placeholder-label') and text()='Search Identity or User']"));
					Actions action = new Actions(driver);
					action.sendKeys(identity, firstName);
					action.build().perform();
					Utility.pause(2);
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					ByAttribute.setText("xpath", HomeObjects.homeAccessRequestSelectTerminationReasonTxt, terminationReason, "Enter the termination Reason ");
					Utility.pause(2);
					ByAttribute.click("xpath","//div[@class='x-boundlist-list-ct x-unselectable x-scroller']//li[contains(text(),'Misconduct')]" , "Select termination reason from dropdown ");
					
					ByAttribute.setText("xpath", HomeObjects.homeAccessRequestValidToDate, currentDate, "Enter the date on which offboarding will be done ");
					Utility.pause(2);
					
					
					ByAttribute.click("xpath",HomeObjects.submitBtn, "Click on submit button");
		            Utility.pause(10);
		            action.click().build().perform();
		            if(driver.findElements(By.xpath(HomeObjects.homeOpenRequestsLnk)).size()>0){
						ByAttribute.mouseHover("xpath",HomeObjects.homeTabBtn,"Mouse hover on Home tab to open My Requests");
						Utility.pause(2);
						ByAttribute.click("xpath",HomeObjects.homeMyRequestLnk,"click on My Requests");
						Utility.pause(5);
					}
		            List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					WebElement latestRequestNumber = requestNumberElements.get(0);
					reqNum = latestRequestNumber.getText();
		            AGlobalComponents.RequestSubmit=true;
				}else {
					System.out.println("Navigation to 'Create Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Create Request' Page Failed");
				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		
		}
		
	return reqNum;	
	}
	
	/**
	 * <h1>request New location</h1> 
	 * This is Method to Create an Access Request
	 * @author Monika Mehta
	 * @modified
	 * @version 1.0
	 * @since 12-16-2020
	 * @param String requestType
	 * @return String
	 **/

	public static String requestNewLocation( String location, String access, String locCity, String locBuilding,String firstName,String lastName) throws Throwable {

		String reqNum="";
		if (unhandledException == false) {
			System.out.println("***************************** Request new location*********************************");
			logger.log(LogStatus.INFO,"***************************** Request new location *********************************");
			try {
				WebElement requestor=driver.findElement(By.xpath(".//span[@class='x-btn-wrap x-btn-wrap-aetextlink-medium x-btn-arrow x-btn-arrow-right' and @role='presentation']"));
				requestorName = requestor.getText();
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeMyRequestsLnk, "Click on My Requests Link");
					Utility.pause(5);
				}
				
				
				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) {
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					
					ByAttribute.click("xpath", HomeObjects.requestLocationAccessLnk, "Click on Request Location Access pod ");
					Utility.pause(5);
					
					ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchLocationTxt, locCity, "Search Location by City");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+location+"') and contains(text(),'"+location+"')]", "Searched Location by City", false);
		
					Thread.sleep(1000);
					ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchLocationTxt, locBuilding, "Search Location by Building");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+location+"') and contains(text(),'"+location+"')]", "Searched Location by Building", false);
					
					Thread.sleep(1000);
					ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchLocationTxt, location, "Search Location by Location Name");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+location+"') and contains(text(),'"+location+"')]", "Searched Location by Location Name", false);
						
					ByAttribute.click("xpath", ".//*[contains(@data-qtip,'"+location+"') and contains(text(),'"+location+"')]", "Click Location: "+location);
					Thread.sleep(2000);
					
					logger.log(LogStatus.INFO, "Search the user to be modified");
					WebElement identity = driver.findElement(By.xpath(".//*[contains(@class,'x-placeholder-label') and text()='Search Identity or User']"));
					Actions action = new Actions(driver);
					action.sendKeys(identity, firstName);
					action.build().perform();
					Utility.pause(2);;
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateAddBtn)).size()>0)
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateAddBtn, "Click Add Button");
					else
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
					
					Thread.sleep(6000);
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestSearchAccessTxt)).size() > 0)
						{
							System.out.println("Navigation to Access Page Successful");
							logger.log(LogStatus.INFO, "Navigation to Access Page Successful");
							
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchAccessTxt, access, "Search Access in Location");
							
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchAccessTxt, "", "Clear Search Filter");
							Thread.sleep(1000);
							
							Utility.verifyElementPresent(".//h4[@data-qtip='"+access+"']", "Searched Access", false);
							ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']", "Add Location");
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestReviewTab, "Click on Review Tab");
							Utility.verifyElementPresent(".//tbody//tr//td//*[text()='"+access+"']", "Added Access: "+access, false);
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationSubmitBtn, "Click Submit Button");
							Thread.sleep(2000);
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationReasonTxt, "General Access Required", "Enter Reason Code");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//li[text()='General Access Required']", "Select Reason Option");
							Thread.sleep(1000);
							if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestAddLocationGroupNameTxt)).size()>0){
								ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationGroupNameTxt, "mca-crar-group1", "Enter Group Name");
								Thread.sleep(1000);
								ByAttribute.click("xpath", ".//li[text()='mca-crar-group1']", "Select Group Name");
							}
							Thread.sleep(1000);
							if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestAddLocationJustificationTxt)).size()>0){
								ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationJustificationTxt, "Automation Test", "Enter Business Justification");
								Thread.sleep(1000);
							}
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationConfirmBtn, "Click Confirm Button");
							Thread.sleep(10000);
							
							List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
							WebElement latestRequestNumber = requestNumberElements.get(0);
							reqNum = latestRequestNumber.getText();
							AGlobalComponents.RequestSubmit=true;
						}else{
							System.out.println("Navigation to Access Page NOT Successful");
							logger.log(LogStatus.FAIL, "Navigation to Access Page NOT Successful");
						}
					}
					

				

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		return reqNum;
	}
	
	



	

	
	
	
	
	
	




	public static String tempWorkerRehiring(String firstName, String lastName) throws Throwable {
		String reqNum="";
		if (unhandledException == false) {
			System.out.println("********Rehiring a Temporary Worker*********");
			logger.log(LogStatus.INFO,"*********Rehiring a Temporary Worker**************");
			try {
				
				WebElement requestor=driver.findElement(By.xpath(".//span[@class='x-btn-wrap x-btn-wrap-aetextlink-medium x-btn-arrow x-btn-arrow-right' and @role='presentation']"));
				requestorName = requestor.getText();
				String reason = "Rehire";
				
				
				if (driver.findElements(By.xpath(HomeObjects.TempWorkerTab)).size() > 0) 
				{
					ByAttribute.mouseHover("xpath", HomeObjects.TempWorkerTab, "Mouse hover on Temp Worker tab");
					ByAttribute.click("xpath", HomeObjects.TempWorkerRehireLnk, "Click on Temp worker Rehire");
					Utility.pause(5);
					
					Utility.handleAnnouncementPopup();
					
					logger.log(LogStatus.INFO, "Search the user to be modified");
					WebElement identity = driver.findElement(By.xpath(".//*[contains(@class,'x-placeholder-label') and text()='Search Identity or User']"));
					Actions action = new Actions(driver);
					action.sendKeys(identity, firstName);
					action.build().perform();
					Utility.pause(5);
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestSelectRehireReasonTxt)).size()>0){
						ByAttribute.setText("xpath", HomeObjects.homeAccessRequestSelectRehireReasonTxt, reason, "Enter the Reason for rehiring");
						Utility.pause(2);
					}
										
					ByAttribute.click("xpath",HomeObjects.submitBtn, "Click on submit button");
		            Utility.pause(10);
		            action.click().build().perform();
		            if(driver.findElements(By.xpath(HomeObjects.homeOpenRequestsLnk)).size()>0){
						ByAttribute.mouseHover("xpath",HomeObjects.homeTabBtn,"Mouse hover on Home tab to open My Requests");
						Utility.pause(2);
						ByAttribute.click("xpath",HomeObjects.homeMyRequestLnk,"click on My Requests");
						Utility.pause(5);
					}
		            List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					WebElement latestRequestNumber = requestNumberElements.get(0);
					reqNum = latestRequestNumber.getText();
		            AGlobalComponents.RequestSubmit=true;
				}else {
					System.out.println("Navigation to 'Create Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Create Request' Page Failed");
				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		
		}
		
	return reqNum;	
	}




	public static String emergencyTermination(String firstName, String lastName) throws Throwable {
		String reqNum="",terminationReason="Misconduct";
		if (unhandledException == false) {
			System.out.println("***************************** Emergency Termination of Employee *********************************");
			logger.log(LogStatus.INFO,"***************************** Emergency Termination of Employee ***********************");
			try {

				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else {
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestLnk)).size()>0) {
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
						Utility.pause(5);
					}
					else{
						ByAttribute.click("xpath", HomeObjects.homeMyRequestsLnk, "Click on My Requests Link");
						Utility.pause(5);
					}
				}
				
				Utility.handleAnnouncementPopup();

				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) {
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					Thread.sleep(1000);
										
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOtherRequestsPod, "Click on Other Requests Pod");
					Thread.sleep(2000);
					
					ByAttribute.click("xpath", HomeObjects.selectRequestType,"Enter the request type");
					ByAttribute.setText("xpath", HomeObjects.selectRequestType,"Emergency Termination", "Enter the request type");
					Thread.sleep(4000);
					
					Actions action = new Actions(driver);
					action.sendKeys(Keys.TAB);
					action.sendKeys(firstName);
					action.build().perform();
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					action.sendKeys(Keys.TAB);
					action.sendKeys(terminationReason);
					action.build().perform();
					ByAttribute.click("xpath","//div[@class='x-boundlist-list-ct x-unselectable x-scroller']//li[text()='"+terminationReason+"']" , "Select termination reason ");
					Utility.pause(5);
						         	            
		            ByAttribute.click("xpath",HomeObjects.submitBtn, "Click on submit button");
		            Utility.pause(5);
		            List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					WebElement latestRequestNumber = requestNumberElements.get(0);
					reqNum = latestRequestNumber.getText();
		            AGlobalComponents.RequestSubmit=true;
		            
				}else {
					System.out.println("Navigation to 'Access Request' Page Failed");
					logger.log(LogStatus.FAIL, "Navigation to 'Access Request' Page Failed");
				}
					
					
			}
			 catch (Exception e) {
					String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
					Utility.recoveryScenario(nameofCurrMethod, e);
			}
			
		}
	return reqNum;	
}		
	
	public static void checkRequestInCompletedInbox(String requestType,String firstName, String lastName, String modifiedAttribute) throws Throwable {
		if (unhandledException == false) {
			System.out.println("***************************** Checking the request in completed inbox *********************************");
			logger.log(LogStatus.INFO,"*************** Checking the request in completed inbox *********************************");
			try{
				List<WebElement> requestNumberElements = null;
				WebElement requestNo=null,latestRequestNumber=null;int index=0;
				boolean reqFlag=true;	
				String identityName = firstName +" "+ lastName,reqNo;
				
				if(requestType.equalsIgnoreCase("Employee Leave Of Absence")) {
					
					/*checking the history of request */
					ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
					Utility.pause(1);
					ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
					boolean flag=true;
					for(int i=0;i<3 && flag;i++){
						if(driver.findElements(By.xpath("//div[contains(text(),' removal successful for user ')]")).size()>0){
							logger.log(LogStatus.PASS, "Removal successful");
							flag=false;
							Utility.verifyElementPresent("//div[contains(text(),' removal successful for user ')]", "Removal Successful Message", false);
						}else if(driver.findElements(By.xpath("//div[contains(text(),' Skip Provisioning Successful ')][1]")).size()>0){
							logger.log(LogStatus.PASS, "Provisioning successful");
							flag=false;
							Utility.verifyElementPresent("//div[contains(text(),' Skip Provisioning Successful ')][1]", "Provisioning message", false);
						}
						else if(driver.findElements(By.xpath("//div[contains(text(),' removal failed for user ')]")).size()>0){
							Utility.verifyElementPresent("//div[contains(text(),' removal failed for user ')]", "Removal failed message", false);
							break;
						}
						else{
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
							Utility.pause(20);
							ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
							Utility.pause(1);
							ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
						}							
					}	
					if(flag)
						logger.log(LogStatus.FAIL, "Removal Unsuccessful");	
					ByAttribute.click("xpath", HomeObjects.homeRequestInboxCloseHistoryWindowIcn, "close history window pop up ");
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestSystemListGrid, "SystemList Grid", true,false);
					if((driver.findElements(By.xpath("//*[text()='"+system1ForEmpOnboarding+"']")).size()>0) && (driver.findElements(By.xpath("//*[text()='"+system2ForEmpOnboarding+"']")).size()>0)){
						String uiSystemStatus1=DBValidations.getUiActionOfSystem(reqNum,system1ForEmpOnboarding); 
						String uiSystemStatus2=DBValidations.getUiActionOfSystem(reqNum,system2ForEmpOnboarding);
						if((Utility.compareStringValues(uiSystemStatus1, "LOCK")) && (Utility.compareStringValues(uiSystemStatus2, "LOCK")))
							logger.log(LogStatus.PASS, "status of  systems :" +system1ForEmpOnboarding +","+system2ForEmpOnboarding+ ","+system2ForEmpOnboarding+ "is LOCKED ");
						else
							logger.log(LogStatus.FAIL, "Systems status is not LOCKED");			
					}
					
					/*
					 * checking for Asset status after offboarding request
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestBadgeListGrid, "Badge List Grid", true,false);
					String uiBadgeStatus=DBValidations.getUiActionOfAsset(reqNum,AGlobalComponents.assetName);
					if(Utility.compareStringValues(uiBadgeStatus, "LOCK"))
						logger.log(LogStatus.PASS, "status of  asset is DEACTIVATE ");
					else
						logger.log(LogStatus.FAIL, "status of  asset is NOT DEACTIVATE"); 
				}
				
				if(requestType.equalsIgnoreCase("Employee Offboarding")) {						
					reqNum=DBValidations.getAccessRequestOfTerminateFromDB();
					HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC014");
					access1ForEmpOnboarding = (String) testData.get("access_name_1");	
					HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource("FB_Automation_TC013");
					access2ForEmpOnboarding = (String) testData1.get("access_name_1");
					HashMap<String, Comparable> testData2 = Utility.getDataFromDatasource("FB_Automation_TC011");
					system1ForEmpOnboarding= (String) testData2.get("system_name");
					system2ForEmpOnboarding= (String) testData2.get("system_name2");	
				}
				if(requestType.equalsIgnoreCase("Employee Leave Of Absence")) {						
					reqNum=DBValidations.getAccessRequestOfLeaveOfAbsenceFromDB();
					HashMap<String, Comparable> testData = Utility.getDataFromDatasource("FB_Automation_TC014");
					access1ForEmpOnboarding = (String) testData.get("access_name_1");	
					HashMap<String, Comparable> testData1 = Utility.getDataFromDatasource("FB_Automation_TC013");
					access2ForEmpOnboarding = (String) testData1.get("access_name_1");
					HashMap<String, Comparable> testData2 = Utility.getDataFromDatasource("FB_Automation_TC011");
					system1ForEmpOnboarding= (String) testData2.get("system_name");
					system2ForEmpOnboarding= (String) testData2.get("system_name2");	
				}
				
				if((driver.findElements(By.xpath(HomeObjects.homeInboxRequestInboxExpandBtn)).size()>0)){
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click to expand the request menu");
					Utility.pause(2);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCompletedBtn, "Click on completed button");
					Utility.pause(2);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCollapseBtn, "Click to collapse the request menu");
					Utility.pause(60);
					
					requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					requestNo=null;
					
					 reqFlag=true;
					for (int i=0;i<requestNumberElements.size() && reqFlag;i++){
						requestNo = requestNumberElements.get(i);
						reqNo = requestNo.getText();
						if(reqNo.contains(reqNum)){
							index=i+1;
							reqFlag=false;
						}
					}
				}
				else if (driver.findElements(By.xpath(HomeObjects.homeTabBtn)).size()>0){
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse hover on Home Tab");
					Utility.pause(3);
					ByAttribute.click("xpath", HomeObjects.homeMyRequestsLnk, "Click on My Requests");
					Utility.pause(5);
					requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
					latestRequestNumber = requestNumberElements.get(0);
					
					 reqFlag=true;
						for (int i=0;i<requestNumberElements.size() && reqFlag;i++){
							requestNo = requestNumberElements.get(i);
							reqNo = requestNo.getText();
							if(reqNo.contains(reqNum)){
								index=i+1;
								reqFlag=false;
							}
						}
				}
				
					if (reqFlag)
						logger.log(LogStatus.FAIL, "Request not present in completed inbox");
					Actions action = new Actions(driver);
					action.doubleClick(requestNo);
					action.build().perform();
					Utility.pause(5);
					
					WebElement requestBy = driver.findElement(By.xpath("//div[text()='Request By']//parent::div//label"));
					String requestedBy= requestBy.getText();
					if(Utility.compareStringValues(requestedBy, "ADMIN USER")){
						logger.log(LogStatus.INFO ,"Request "+reqNum+" opened successfully in  inbox");
					}
					else
						logger.log(LogStatus.FAIL ,"Incorrect request  is expanded");
					
				
					
					if(requestType.equalsIgnoreCase("Employee Offboarding")) {
		
						/*checking the history of request */
						ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
						Utility.pause(1);
						ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
						boolean flag=true;
						for(int i=0;i<10 && flag;i++){
							if(driver.findElements(By.xpath("//div[contains(text(),' removal successful for user ')]")).size()>0){
								logger.log(LogStatus.PASS, "Removal successful");
								flag=false;
								Utility.verifyElementPresent("//div[contains(text(),' removal successful for user ')]", "Removal Successful Message", false);
							}else if(driver.findElements(By.xpath("//div[contains(text(),' Skip Provisioning Successful ')]")).size()>0){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								Utility.verifyElementPresent("//div[contains(text(),' Skip Provisioning Successful ')]", "Provisioning message", false);
							}
							else if(driver.findElements(By.xpath("//div[contains(text(),' removal failed for user ')]")).size()>0){
								Utility.verifyElementPresent("//div[contains(text(),' removal failed for user ')]", "Removal failed message", false);
								break;
							}
							else{
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
								Utility.pause(20);
								ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
								Utility.pause(1);
								ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
							}							
						}	
						if(flag)
							logger.log(LogStatus.FAIL, "Removal Unsuccessful");	
						ByAttribute.click("xpath", HomeObjects.homeRequestInboxCloseHistoryWindowIcn, "close history window pop up ");
						Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
						if((driver.findElements(By.xpath("//*[text()='"+access1ForEmpOnboarding+"']")).size()>0)){
							String uiAccessStatus=DBValidations.getUIActionOfAccess(reqNum,access1ForEmpOnboarding);
							if((Utility.compareStringValues(uiAccessStatus, "REMOVE")))
								logger.log(LogStatus.PASS, "Access :" +access1ForEmpOnboarding +" is removed ");
							else
								logger.log(LogStatus.FAIL, "Access status is not removed");			
						}
						if((driver.findElements(By.xpath("//*[text()='"+access2ForEmpOnboarding+"']")).size()>0)){
							String uiAccessStatus=DBValidations.getUIActionOfAccess(reqNum,access2ForEmpOnboarding);
							if((Utility.compareStringValues(uiAccessStatus, "REMOVE")))
								logger.log(LogStatus.PASS, "Access :" +access2ForEmpOnboarding +" is removed ");
							else
								logger.log(LogStatus.FAIL, "Access status is not removed");			
						}
						
						Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestSystemListGrid, "SystemList Grid", true,false);
						if((driver.findElements(By.xpath("//*[text()='"+system1ForEmpOnboarding+"']")).size()>0) && (driver.findElements(By.xpath("//*[text()='"+system2ForEmpOnboarding+"']")).size()>0)){
							String uiSystemStatus1=DBValidations.getUiActionOfSystem(reqNum,system1ForEmpOnboarding); 
							String uiSystemStatus2=DBValidations.getUiActionOfSystem(reqNum,system2ForEmpOnboarding);
							if((Utility.compareStringValues(uiSystemStatus1, "LOCK")) && (Utility.compareStringValues(uiSystemStatus2, "LOCK")))
								logger.log(LogStatus.PASS, "status of  systems :" +system1ForEmpOnboarding +","+system2ForEmpOnboarding+ ","+system2ForEmpOnboarding+ "is LOCKED ");
							else
								logger.log(LogStatus.FAIL, "Systems status is not LOCKED");			
						}
						
						/*
						 * checking for Asset status after offboarding request
						 */
						Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestBadgeListGrid, "Badge List Grid", true,false);
						String uiBadgeStatus=DBValidations.getUiActionOfAsset(reqNum,AGlobalComponents.assetName);
						if(Utility.compareStringValues(uiBadgeStatus, "LOCK"))
							logger.log(LogStatus.PASS, "status of  asset is DEACTIVATE ");
						else
							logger.log(LogStatus.FAIL, "status of  asset is NOT DEACTIVATE"); 
					}
					
					if(requestType.equalsIgnoreCase("Employee Onboarding")){
					
						/*checking the history of request */
						ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
						Utility.pause(1);
						ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
						Utility.pause(5);
						boolean flag=true;
						for(int i=0;i<10 && flag;i++){
							if(driver.findElements(By.xpath("//div[contains(text(),' assignment successful for user ')]")).size()>0){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								Utility.verifyElementPresent("//div[contains(text(),' assignment successful for user ')]", "Provisioning message", false);
							}else if(driver.findElements(By.xpath("//div[contains(text(),' assignment failed for user ')]")).size()>0){
								Utility.verifyElementPresent("//div[contains(text(),' assignment failed for user ')]", "Provisioning failed message", false);
								break;
							}
							else{
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
								Utility.pause(20);
								ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
								Utility.pause(1);
								ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
							}							
						}	
						if(flag)
							logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");					
					}
					
					if(requestType.equalsIgnoreCase("Employee Modification")){
						
						/*checking the history of request */
						ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
						Utility.pause(1);
						ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
						Utility.pause(5);
						boolean flag=true;
						for(int i=0;i<10 && flag;i++){
							if(driver.findElements(By.xpath("//div[contains(text(),' Skip Provisioning Successful ')]")).size()>0){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								Utility.verifyElementPresent("//div[contains(text(),' Skip Provisioning Successful ')]", "Provisioning message", false);
							}else if(driver.findElements(By.xpath("//div[contains(text(),' assignment failed for user ')]")).size()>0){
								Utility.verifyElementPresent("//div[contains(text(),' assignment failed for user ')]", "Provisioning failed message", false);
								break;
							}
							else{
								ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryPopUpCloseIcn, "close history window pop up ");
								Utility.pause(20);
								ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
								Utility.pause(1);
								ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
							}							
						}	
						if(flag)
							logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");					
					}
					
					if(Utility.compareStringValues(requestType, "Employement Type Conversion")||Utility.compareStringValues(requestType, "Transfer")||Utility.compareStringValues(requestType, "Employee Rehire")) {

						String accessName = modifiedAttribute;
						
						ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
						Utility.pause(1);
						ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
						boolean flag=true;
						for(int i=0;i<10 && flag;i++){
							if(driver.findElements(By.xpath("//div[contains(text(),' assignment successful for user ')]")).size()>0){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								Utility.verifyElementPresent("//div[contains(text(),' assignment successful for user ')]", "Provisioning message", false);
							}else if(driver.findElements(By.xpath("//div[contains(text(),' Skip Provisioning Successful ')]")).size()>0){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								Utility.verifyElementPresent("//div[contains(text(),' Skip Provisioning Successful ')]", "Provisioning message", false);
							}
							else if(driver.findElements(By.xpath("//div[contains(text(),' assignment failed for user ')]")).size()>0){
								Utility.verifyElementPresent("//div[contains(text(),' assignment failed for user ')]", "Provisioning failed message", false);
								break;
							}
							else{
								ByAttribute.click("xpath", HomeObjects.homeRequestInboxCloseHistoryWindowIcn, "close history window pop up ");
								Utility.pause(20);
							}
								
						}	
						if(flag)
							logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
						ByAttribute.click("xpath", HomeObjects.homeRequestInboxCloseHistoryWindowIcn, "close history window pop up ");
						Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "Access List Grid",true, false);
						if((driver.findElements(By.xpath("//*[text()='"+accessName+"']")).size()>0)){
							WebElement accessStatus = driver.findElement(By.xpath("//div[text()='"+accessName+"']//ancestor::td//following-sibling::td//label"));
							String status = accessStatus.getText();
							
							if((Utility.compareStringValues(status, "ADDED"))){
								Utility.verifyElementPresent("//*[text()='"+accessName+"']", accessName, false);
								logger.log(LogStatus.INFO, "New access is added when employment type is modified");
							}
							else
								logger.log(LogStatus.FAIL, "Access is not added when employment type is modified");
							
						}			
					}				
			}catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	public static String positionAccess( String position, String access, String firstName,String lastName) throws Throwable {
		String reqNum="";
		if (unhandledException == false) {
			System.out.println("***************************** Position Access*********************************");
			logger.log(LogStatus.INFO,"***************************** Position Access *********************************");
			try {
				WebElement requestor=driver.findElement(By.xpath(".//span[@class='x-btn-wrap x-btn-wrap-aetextlink-medium x-btn-arrow x-btn-arrow-right' and @role='presentation']"));
				requestorName = requestor.getText();
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeMyRequestsLnk, "Click on My Requests Link");
					Utility.pause(5);
				}
				
				Utility.handleAnnouncementPopup();

				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) {
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					
					ByAttribute.click("xpath", HomeObjects.homeCreateRequestPositionAccessPod, "Click on Position Access pod ");
					Utility.pause(5);
					
					ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchPositionTxt, position, "Search Position");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+position+"') and contains(text(),'"+position+"')]", "Searched Position", false);
		
					
					ByAttribute.click("xpath", ".//*[contains(@data-qtip,'"+position+"') and contains(text(),'"+position+"')]", "Click Position: "+position);
					Thread.sleep(1000);
					
					logger.log(LogStatus.INFO, "Search the user to be modified");
					WebElement identity = driver.findElement(By.xpath(".//*[contains(@class,'x-placeholder-label') and text()='Search Identity or User']"));
					Actions action = new Actions(driver);
					action.sendKeys(identity, firstName);
					action.build().perform();
					Utility.pause(2);;
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateNextBtn)).size() > 0)
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
					else
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateAddBtn, "Click Add Button");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestSearchPositionAccessTxt)).size() > 0)
						{
							System.out.println("Navigation to Access Page Successful");
							logger.log(LogStatus.INFO, "Navigation to Access Page Successful");
							
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchPositionAccessTxt, access, "Search Access based on Position");
							
					//		ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']", "Add Access");
						//	Thread.sleep(1000);
					//		ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']//ancestor::div[@class='innerWidget x-item-select-active x-item-selected']", "Add Access");
							Utility.verifyElementPresent(".//h4[@data-qtip='"+access+"']", "Searched Access", false);
							ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']", "Add Access");
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestReviewTab, "Click on Review Tab");
							Utility.verifyElementPresent(".//tbody//tr//td//div[text()='"+access+"']", "Added Access: "+access, false);
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddPositionSubmitBtn, "Click Submit Button");
							Thread.sleep(5000);
														
							List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
							WebElement latestRequestNumber = requestNumberElements.get(0);
							reqNum = latestRequestNumber.getText();
							AGlobalComponents.RequestSubmit=true;
						}else{
							System.out.println("Navigation to Access Page NOT Successful");
							logger.log(LogStatus.FAIL, "Navigation to Access Page NOT Successful");
						}
					}
					

				

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		return reqNum;
	}




	public static String applicationAccess(String application_name, String access, String firstName,String lastName) throws Throwable {
		String reqNum="";
		if (unhandledException == false) {
			System.out.println("***************************** Application Access*********************************");
			logger.log(LogStatus.INFO,"***************************** Application Access *********************************");
			try {
				WebElement requestor=driver.findElement(By.xpath(HomeObjects.homeMyRequestsRequesterNameLnk));
				requestorName = requestor.getText();
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeMyRequestsLnk, "Click on My Requests Link");
					Utility.pause(5);
				}

				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) {
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					
					ByAttribute.click("xpath", HomeObjects.homeCreateRequestApplicationAccessPod, "Click on Application Access pod ");
					Utility.pause(5);
					
					ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchApplicationTxt, application_name, "Search Application");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+application_name+"') and contains(text(),'"+application_name+"')]", "Searched Application", false);
		
					
					ByAttribute.click("xpath", ".//*[contains(@data-qtip,'"+application_name+"') and contains(text(),'"+application_name+"')]", "Click Application: "+application_name);
					Thread.sleep(1000);
					
					logger.log(LogStatus.INFO, "Search the user to be modified");
					WebElement identity = driver.findElement(By.xpath(HomeObjects.homeMyRequestSearchUserTxt));
					Actions action = new Actions(driver);
					action.sendKeys(identity, firstName);
					action.build().perform();
					Utility.pause(2);;
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateNextBtn)).size() > 0)
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
					else
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateAddBtn, "Click Add Button");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestSearchApplicationAccessTxt)).size() > 0)
						{
							System.out.println("Navigation to Access Page Successful");
							logger.log(LogStatus.INFO, "Navigation to Access Page Successful");
							
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchApplicationAccessTxt, access, "Search Access based on Application");
							
							//					ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']//ancestor::div[@class='innerWidget']", "Add Access");
							Utility.verifyElementPresent(".//h4[@data-qtip='"+access+"']", "Searched Access", false);
							ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']", "Add Access");
							Thread.sleep(1000);
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestReviewTab, "Click on Review Tab");
							Utility.verifyElementPresent(".//tbody//tr//td//div[text()='"+access+"']", "Added Access: "+access, false);
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddApplicationSubmitBtn, "Click Submit Button");
							Thread.sleep(5000);
														
							List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
							WebElement latestRequestNumber = requestNumberElements.get(0);
							reqNum = latestRequestNumber.getText();
							AGlobalComponents.RequestSubmit=true;
						}else{
							System.out.println("Navigation to Access Page NOT Successful");
							logger.log(LogStatus.FAIL, "Navigation to Access Page NOT Successful");
						}
					}
					

				

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		return reqNum;
	}




	public static String itAccess(String it_system, String access, String firstName, String lastName) throws Throwable {
		String reqNum="";
		if (unhandledException == false) {
			System.out.println("***************************** IT Access*********************************");
			logger.log(LogStatus.INFO,"***************************** IT Access *********************************");
			try {
				WebElement requestor=driver.findElement(By.xpath(HomeObjects.homeMyRequestsRequesterNameLnk));
				requestorName = requestor.getText();
				if(driver.findElements(By.xpath(HomeObjects.myRequestsTabBtn)).size()>0)
				{
					ByAttribute.click("xpath", HomeObjects.myRequestsTabBtn, "Click on Access Request Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeMyRequestsLnk, "Click on My Requests Link");
					Utility.pause(5);
				}
				
				
				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) {
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					
					ByAttribute.click("xpath", HomeObjects.homeCreateRequestITAccessPod, "Click on IT Access pod ");
					Utility.pause(5);
					
					ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchITSystemTxt, it_system, "Search IT System");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[contains(@data-qtip,'"+it_system+"') and contains(text(),'"+it_system+"')]", "Searched IT System", false);
		
					
					ByAttribute.click("xpath", ".//*[contains(@data-qtip,'"+it_system+"') and contains(text(),'"+it_system+"')]", "Click IT System: "+it_system);
					Thread.sleep(1000);
					
					logger.log(LogStatus.INFO, "Search the user to be modified");
					WebElement identity = driver.findElement(By.xpath(HomeObjects.homeMyRequestSearchUserTxt));
					Actions action = new Actions(driver);
					action.sendKeys(identity, firstName);
					action.build().perform();
					Utility.pause(2);;
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateNextBtn)).size() > 0)
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
					else
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateAddBtn, "Click Add Button");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestSearchITAccessLbl)).size() > 0)
						{
							System.out.println("Navigation to Access Page Successful");
							logger.log(LogStatus.INFO, "Navigation to Access Page Successful");
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestSearchITAccessLbl, "Search Access based on IT system");
							Thread.sleep(1000);
							ByAttribute.click("xpath","//div[@class='idmlistitem']//span[text()='"+access+"']", "Select Access ");
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestReviewTab, "Click on Review Tab");
							Utility.verifyElementPresent("(.//tbody//tr//td/div[text()='"+access+"'])[2]", "Added Access: "+access, false);
							
			                ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddApplicationSubmitBtn, "Click Submit Button");
							Thread.sleep(5000);
				            
				            ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationReasonTxt, "Need Temporary Access for System", "Enter Reason Code");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//li[text()='Need Temporary Access for System']", "Select Reason Option");
							Thread.sleep(1000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationConfirmBtn, "Click Confirm Button");
							Thread.sleep(10000);
							
							
														
							List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
							WebElement latestRequestNumber = requestNumberElements.get(0);
							reqNum = latestRequestNumber.getText();
							AGlobalComponents.RequestSubmit=true;
						}else{
							System.out.println("Navigation to Access Page NOT Successful");
							logger.log(LogStatus.FAIL, "Navigation to Access Page NOT Successful");
						}
					}
					

				

			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		return reqNum;
	}
	
	/**
	* <h1>activatedeactivateBadge</h1>
	* This is Method to activate badge by badge admin
	* @author Vishal Gupta
	* @modified
	* @version 1.0
	* @since 2-15-2021
	* @param String requestFor
	* @return String
	**/

	public static void activatedeactivateBadge(String firstName, String lastName,String action) throws Throwable {

	if (unhandledException == false) {
	System.out.println("*************************** activate badge *******************************");
	logger.log(LogStatus.INFO,"*************************** activate badge *******************************");
	try {
		searchIdentity(firstName,lastName);

		getIndexOfAssetsHeaders();
		if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
			logger.log(LogStatus.INFO, "No Badge is assigned to the user ");
		else{
			WebElement assetStatus = driver.findElement(By.xpath("//tr//td["+assignmentStatusIndex+"]//div[@class='x-grid-cell-inner ']//label"));
			oldAssetStatus=assetStatus.getText();
			if(action.equalsIgnoreCase("activate"))
			{
				if(oldAssetStatus.equalsIgnoreCase("INACTIVE"))
					logger.log(LogStatus.PASS, "status before is "+oldAssetStatus);
				else
					logger.log(LogStatus.FAIL, "status before is not inactive");
			}
			else {
				if(oldAssetStatus.equalsIgnoreCase("ACTIVE"))
					logger.log(LogStatus.PASS, "status before is "+oldAssetStatus);
				else
					logger.log(LogStatus.FAIL, "status before is not active");
			}

			Utility.verifyElementPresent("//tr//td["+assignmentStatusIndex+"]//div[@class='x-grid-cell-inner ']//label", "Asset Assignemnt status", false);

			Utility.pause(3);
			if((driver.findElements(By.xpath(IdentityObjects.identityCommentsBtn)).size()>0)){
				ByAttribute.click("xpath",IdentityObjects.identityCommentsBtn, "clicking on comments button");
				Thread.sleep(1000);

				Robot robot = new Robot();

				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_A);
				robot.keyPress(KeyEvent.VK_U);
				robot.keyPress(KeyEvent.VK_T);
				robot.keyPress(KeyEvent.VK_O);

				ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
				Thread.sleep(2000);
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
				Thread.sleep(2000);
			}
			if(action.equalsIgnoreCase("activate"))
				ByAttribute.click("xpath",IdentityObjects.activateActionBtn, "clicking on activate button");
			else
				ByAttribute.click("xpath",IdentityObjects.deactivateActionBtn, "clicking on deactivate button");
			ByAttribute.click("xpath", IdentityObjects.SaveBtn, "clicking on save button");
			Utility.pause(60);
			ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
			Utility.pause(1);
			ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
			Utility.pause(10);
			ByAttribute.click("xpath", IdentityObjects.badgeTabLnk, "Click on Badges Tab ");
			ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
			Utility.pause(1);
			ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
			Utility.pause(10);
			ByAttribute.click("xpath", IdentityObjects.badgeTabLnk, "Click on Badges Tab ");

			WebElement assetStatusNew = driver.findElement(By.xpath("//div[@class='x-grid-cell-inner ']//label"));
			newAssetStatus=assetStatusNew.getText();
			if(action.equalsIgnoreCase("activate"))
			{
				if(newAssetStatus.equalsIgnoreCase("ACTIVE"))
					logger.log(LogStatus.PASS, "status after is "+newAssetStatus);
				else
					logger.log(LogStatus.FAIL, "status after is not active");
			}
			else {
				if(newAssetStatus.equalsIgnoreCase("INACTIVE"))
					logger.log(LogStatus.PASS, "status after is "+newAssetStatus);
				else
					logger.log(LogStatus.FAIL, "status after is not inactive");
			}
			Utility.verifyElementPresent("//div[@class='x-grid-cell-inner ']//label", "Asset Assignemnt status", false);
			getIndexOfAccessIdHeaders();
			WebElement card = driver.findElement(By.xpath("//tr//td["+badgeaccessIndex+"]"));
			String cardNumber = card.getText();
			String status = DBValidations.checkAssetStatusInCCURE(cardNumber);
			if(action.equalsIgnoreCase("activate"))
			{
				if(status.equals("false"))
					logger.log(LogStatus.PASS, "the badge is activated successfully in CCURE db");
				else
					logger.log(LogStatus.FAIL, "the badge is not activated successfully in CCURE db");
			}
			else {
				if(status.equals("true"))
					logger.log(LogStatus.PASS, "the badge is de-activated successfully in CCURE db");
				else
					logger.log(LogStatus.FAIL, "the badge is not de-activated successfully in CCURE db");
			}
		}

	}
	catch (Exception e) {
		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		Utility.recoveryScenario(nameofCurrMethod, e);
	}

	}

	}
	
	public static void getIndexOfAccessIdHeaders() throws Throwable {
		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
			boolean flag=true;
						
			for (int i=1;i<size && flag;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println("heading "+ (i) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "access id":
		            	badgeaccessIndex = j+1;
		            	j++;
		            	flag=false;
		            	break;
		            	
		            case "":
		                break;
		            default: 
		            	System.out.println("Need to skip this header : "+ heading);
		            	j++;
					}
				}
			}
			catch(Exception e){
				logger.log(LogStatus.ERROR, "Failed: Header Not Found ");
			}
	}
	
	public static void getIndexOfAssetsHeaders() throws Throwable {
		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
			boolean flag=true;
						
			for (int i=1;i<size && flag;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println("heading "+ (i) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "assignment status":
		            	assignmentStatusIndex = j+1;
		            	j++;
		            	flag=false;
		            	break;
		            	
		           case "":
		                break;
		            default: 
		            	System.out.println("Need to skip this header : "+ heading);
		            	j++;
					}
				}
			}
			catch(Exception e){
				logger.log(LogStatus.ERROR, "Failed: Header Not Found ");
			}
	}
	
	/**
	* <h1>requestReplacementBadge</h1>
	* This is Method to replace badge by badge admin
	* @author Vishal Gupta
	* @modified
	* @version 1.0
	* @since 2-16-2021
	* @param String requestFor
	* @return String
	**/

	public static void requestReplacementBadge(String firstName, String lastName) throws Throwable {

		if (unhandledException == false) {
		System.out.println("**************************** requestReplacementBadge ********************************");
		logger.log(LogStatus.INFO,"**************************** requestReplacementBadge ********************************");
		try {
			searchIdentity(firstName,lastName);
			getIndexOfAssetsHeaders();
			if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
				logger.log(LogStatus.INFO, "No Badge is assigned to the user ");
			else {
				if((driver.findElements(By.xpath(IdentityObjects.identityCommentsBtn)).size()>0)){
					ByAttribute.click("xpath",IdentityObjects.identityCommentsBtn, "clicking on comments button");
					Thread.sleep(1000);

					Robot robot = new Robot();

					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyPress(KeyEvent.VK_U);
					robot.keyPress(KeyEvent.VK_T);
					robot.keyPress(KeyEvent.VK_O);

				ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
				Thread.sleep(2000);
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
				Thread.sleep(2000);
				}
				WebElement replace = driver.findElement(By.xpath("(//label[text()='Active'])[1]//parent::div//parent::div//parent::td//preceding-sibling::td[2]"));
				String replaceAccessId = replace.getText();
				ByAttribute.click("xpath", IdentityObjects.identityReplaceBadgeBtn, "clicking on replace badge button");
				Utility.verifyElementPresentReturn(IdentityObjects.identityReplaceBadgeLbl, "replace badge label", true, false);
				ByAttribute.setText("xpath", IdentityObjects.identityReplacementReason, "Lost", "entering text in replacement reason");
				ByAttribute.click("xpath", IdentityObjects.identitySelectStatusDnd, "clicking on select status dropdown");
				Utility.pause(1);
				ByAttribute.click("xpath", IdentityObjects.identityDamagedStatusTxt, "selecting status value");
				ByAttribute.click("xpath",IdentityObjects.identitySelectBadgetDnd, "Click on select badge dropdown");	
				Utility.pause(10);
				ByAttribute.click("xpath", IdentityObjects.identityAdvancedSearchBtn, "clicking on Advanced Search");
				Utility.pause(5);
				if(driver.findElements(By.xpath("//input[@placeholder = 'Choose Field']")).size()<0) {
					ByAttribute.click("xpath", "(//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-filter')])[2]", "Clicking on filter icon");
				}
				ByAttribute.setText("xpath", "//input[@placeholder = 'Choose Field']", "System", "putting filter on system");
				ByAttribute.click("xpath", "//li[text()='System']", "clicking on system value");
				ByAttribute.setText("xpath", "//input[contains(@class,'x-tagfield-input-field')]", "CCURE", "entering system name");
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(5);
				ByAttribute.click("xpath", "//li[text()='CCURE 9000']", "clicking on system value");
				Utility.pause(5);
				if(driver.findElements(By.xpath("(//input[@placeholder = 'Choose Field'])[2]")).size()<0) 
					ByAttribute.click("xpath", "(//span[@class='x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-rowAdd '])[2]", "clicking on 2nd Filter");
				ByAttribute.setText("xpath", "(//input[@placeholder = 'Choose Field'])[2]", "Status", "putting filter on status");
				ByAttribute.click("xpath", "//li[text()='Status']", "clicking on status");
				ByAttribute.setText("xpath", "(//input[contains(@class,'x-tagfield-input-field')])[2]", "OPEN", "entering status value");
				ByAttribute.click("xpath", "//li[text()='OPEN']", "clicking on status value");
				Utility.pause(5);
				ByAttribute.click("xpath", "(//div[@class = 'x-grid-cell-inner x-grid-checkcolumn-cell-inner'])[1]", "selecting value of Asset");
				WebElement code=driver.findElement(By.xpath("(//div[@class = 'x-grid-cell-inner x-grid-checkcolumn-cell-inner'])[1]//parent::td//following-sibling::td[3]"));
				String identityCode=code.getText();
				WebElement accessCode=driver.findElement(By.xpath("(//div[@class = 'x-grid-cell-inner x-grid-checkcolumn-cell-inner'])[1]//parent::td//following-sibling::td[7]"));
				String accessId=accessCode.getText();
				ByAttribute.click("xpath", "//span[text()='Confirm' and @class= 'x-btn-inner x-btn-inner-aebtnPrimary-medium']", "clicking on confirm button");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yy h:mm a");  
				LocalDateTime now = LocalDateTime.now();  
				String currTime =   dtf.format(now.plusDays(14));
				ByAttribute.setText("xpath","(//input[@placeholder = 'Select Valid To'])[6]", currTime, "setting valid to");
				Utility.pause(2);
				ByAttribute.click("xpath","//body","clicking on blank page");
				ByAttribute.click("xpath",IdentityObjects.identityReplaceSaveBtn, "Click on save button");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.SaveBtn, "clicking on save");
				Utility.pause(30);
				ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
				Utility.pause(1);
				ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
				Utility.pause(10);
				ByAttribute.click("xpath", IdentityObjects.badgeTabLnk, "Click on Badges Tab ");
				ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
				Utility.pause(1);
				ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
				Utility.pause(10);
				ByAttribute.click("xpath", IdentityObjects.badgeTabLnk, "Click on Badges Tab");
				Utility.pause(3);
				WebElement inactiveStatus = driver.findElement(By.xpath("//div[text()='"+replaceAccessId+"']//parent::td//following-sibling::td[2]//child::div//label"));
				String inactiveStatusText = inactiveStatus.getText();
				if(inactiveStatusText.equals("INACTIVE")) {
					logger.log(LogStatus.PASS, "the status of the badge which is replaced is Inactive");
				}
				else {
					logger.log(LogStatus.FAIL, "the status of the badge which is replaced is not Inactive");
				}
				
				Utility.verifyElementPresentReturn("//div[text()='"+replaceAccessId+"']//parent::td//following-sibling::td[2]//child::div//label","status of replaced card", true, false);
				Utility.verifyElementPresentReturn("//div[text()='"+identityCode+"']","replace record added", true, false);
				String status = DBValidations.checkAssetStatusInCCURE(replaceAccessId);
				logger.log(LogStatus.PASS, "Inactive status of replaced card in CCURE  is  : " +status);
				
				int count = DBValidations.checkRecordInCCURE(accessId);
				if(count>=1) {
					logger.log(LogStatus.PASS, "badge is replaced in CCURE");
			}
				else {
					logger.log(LogStatus.FAIL, "badge is not replaced in CCURE");
				}
			}
		}
				
			catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
				}
		}
	}
	/**
	* <h1>requestNewBadge</h1>
	* This is Method to request new badge by badge admin
	* @author Vishal Gupta
	* @modified
	* @version 1.0
	* @since 2-15-2021
	* @param String requestFor
	* @return String
	**/

	public static void requestNewBadge(String firstName, String lastName) throws Throwable {

		if (unhandledException == false) {
		System.out.println("************************** request new badge ******************************");
		logger.log(LogStatus.INFO,"************************** request new badge ******************************");
		try {
			searchIdentity(firstName,lastName);
			getIndexOfAssetsHeaders();
			if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
				logger.log(LogStatus.INFO, "No Badge is assigned to the user ");
			else{
				if((driver.findElements(By.xpath(IdentityObjects.identityCommentsBtn)).size()>0)){
					ByAttribute.click("xpath",IdentityObjects.identityCommentsBtn, "clicking on comments button");
					Thread.sleep(1000);
	
					Robot robot = new Robot();
	
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyPress(KeyEvent.VK_U);
					robot.keyPress(KeyEvent.VK_T);
					robot.keyPress(KeyEvent.VK_O);
	
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
					Thread.sleep(2000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
					Thread.sleep(2000);
				}
				ByAttribute.click("xpath",IdentityObjects.identityRowAdderLnk , "clicking on row adder");
				Utility.pause(3);
				Utility.verifyElementPresentReturn(IdentityObjects.identityAssignAssetLbl, "Assign asset label", true, false);
				ByAttribute.click("xpath",IdentityObjects.identitySelectAssetDnd , "clicking on select Asset");
				Utility.pause(10);
				ByAttribute.click("xpath", IdentityObjects.identityAdvancedSearchBtn, "clicking on Advanced Search");
				Utility.pause(5);
				if(driver.findElements(By.xpath("//input[@placeholder = 'Choose Field']")).size()<0) {
					ByAttribute.click("xpath", "(//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-filter')])[2]", "Clicking on filter icon");
				}
				ByAttribute.setText("xpath", "//input[@placeholder = 'Choose Field']", "System", "putting filter on system");
				ByAttribute.click("xpath", "//li[text()='System']", "clicking on system value");
				ByAttribute.setText("xpath", "//input[contains(@class,'x-tagfield-input-field')]", "CCURE", "entering system name");
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(5);
				ByAttribute.click("xpath", "//li[text()='CCURE 9000']", "clicking on system value");
				Utility.pause(5);
				if(driver.findElements(By.xpath("(//input[@placeholder = 'Choose Field'])[2]")).size()<0) 
					ByAttribute.click("xpath", "(//span[@class='x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-rowAdd '])[2]", "clicking on 2nd Filter");
				ByAttribute.setText("xpath", "(//input[@placeholder = 'Choose Field'])[2]", "Status", "putting filter on status");
				ByAttribute.click("xpath", "//li[text()='Status']", "clicking on status");
				ByAttribute.setText("xpath", "(//input[contains(@class,'x-tagfield-input-field')])[2]", "OPEN", "entering status value");
				ByAttribute.click("xpath", "//li[text()='OPEN']", "clicking on status value");
				Utility.pause(5);
				ByAttribute.click("xpath", "(//div[@class = 'x-grid-cell-inner x-grid-checkcolumn-cell-inner'])[1]", "selecting value of Asset");
				WebElement code=driver.findElement(By.xpath("(//div[@class = 'x-grid-cell-inner x-grid-checkcolumn-cell-inner'])[1]//parent::td//following-sibling::td[3]"));
				String identityCode=code.getText();
				WebElement accessCode=driver.findElement(By.xpath("(//div[@class = 'x-grid-cell-inner x-grid-checkcolumn-cell-inner'])[1]//parent::td//following-sibling::td[7]"));
				String accessId=accessCode.getText();
				ByAttribute.click("xpath", "(//span[text()='Confirm' and @class= 'x-btn-inner x-btn-inner-aebtnPrimary-medium'])[2]", "clicking on confirm button");
		
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yy h:mm a");  
				LocalDateTime now = LocalDateTime.now();  
				String currTime =   dtf.format(now.plusDays(14));
				ByAttribute.setText("xpath","(//input[@placeholder = 'Select Valid To'])[5]", currTime, "setting valid to");
				Utility.pause(2);
				ByAttribute.click("xpath","//body","clicking on blank page");
				ByAttribute.click("xpath", IdentityObjects.identityConfirmAssetBtn, "clicking on confirm button");
				Utility.pause(5);
				ByAttribute.click("xpath", IdentityObjects.SaveBtn, "clicking on save button");
				Utility.pause(60);
				ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
				Utility.pause(1);
				ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
				Utility.pause(10);
				ByAttribute.click("xpath", IdentityObjects.badgeTabLnk, "Click on Badges Tab ");
				ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
				Utility.pause(1);
				ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
				Utility.pause(10);
				ByAttribute.click("xpath", IdentityObjects.badgeTabLnk, "Click on Badges Tab ");
				Utility.pause(3);
				if(Utility.verifyElementPresentReturn("//div[text()='"+identityCode+"']","New record added", true, false))
					logger.log(LogStatus.PASS, "record is added in CCURE");
				else	
					logger.log(LogStatus.FAIL,"record is not added in CCURE");
				int count = DBValidations.checkRecordInCCURE(accessId);
				if(count>=1)
					logger.log(LogStatus.PASS, "record is added in CCURE db");
				else
					logger.log(LogStatus.FAIL,"record is not added in CCURE db");
			}
			
		}
		catch (Exception e) {
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
			Utility.recoveryScenario(nameofCurrMethod, e);
			}
		
		}
		
	}
	
	/**
	* <h1>returnTemporaryBadge</h1>
	* This is Method to return temporary by badge admin
	* @author Vishal Gupta
	* @modified
	* @version 1.0
	* @since 3-01-2021
	* @param String requestFor
	* @return String
	**/

	public static void returnTemporaryBadge(String firstName, String lastName) throws Throwable {

		if (unhandledException == false) {
		System.out.println("**************************** returnTemporaryBadge ********************************");
		logger.log(LogStatus.INFO,"**************************** returnTemporaryBadge ********************************");
		try {
			searchIdentity(firstName,lastName);
			if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
				logger.log(LogStatus.INFO, "No Badge is assigned to the user ");
			else {
				if((driver.findElements(By.xpath(IdentityObjects.identityCommentsBtn)).size()>0)){
					ByAttribute.click("xpath",IdentityObjects.identityCommentsBtn, "clicking on comments button");
					Thread.sleep(1000);
	
					Robot robot = new Robot();
	
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyPress(KeyEvent.VK_U);
					robot.keyPress(KeyEvent.VK_T);
					robot.keyPress(KeyEvent.VK_O);
	
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
					Thread.sleep(2000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
					Thread.sleep(2000);
				}
				
				if(driver.findElements(By.xpath(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']")).size()>0)
				{
					Utility.verifyElementPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']",  "Temporary Badge is Available with User: ", false);
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']//parent::td//following-sibling::td[10]//div[@data-qtip='Return Asset']", "Click Return Asset Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//label[text()='Do you want to return badge?']", "Do you want to return badge? Dialog box", false);
					ByAttribute.click("xpath", "//*[contains(@id,'baseCheckBox') and contains(text(),'unlock permanent badge')]", "Check Is unlock Permanent badge Checkbox");
					Thread.sleep(1000);
					ByAttribute.click("xpath", "//span[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Confirm']", "Click Confirm Button");
					Thread.sleep(10000);
					ByAttribute.click("xpath", ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Save']", "Click Save Button");
					Thread.sleep(10000);
					//IDM VALIDATION
					
					ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
					Utility.pause(1);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click on reload ");
					Utility.pause(10);
					ByAttribute.click("xpath", IdentityObjects.badgeTabLnk, "Click on Badges Tab");
					
					Thread.sleep(3000);
					ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='Assets']", "Click Assets Tab");
					Utility.verifyElementNotPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']", "Returned Temporary Badge", true);
				}else{
					System.out.println("Temporary Badge not assigned to User");
					logger.log(LogStatus.FAIL, "Temporary Badge not assigned to User");
				}
			}
			
		}
		catch (Exception e) {
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
			Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	/**
	* <h1>resetBadgePin</h1>
	* This is Method to reset pin by badge admin
	* @author Vishal Gupta
	* @modified
	* @version 1.0
	* @since 2-16-2021
	* @param String requestFor
	* @return String
	**/

	public static void resetBadgePin(String firstName, String lastName) throws Throwable {

		if (unhandledException == false) {
		System.out.println("**************************** resetBadgePin ********************************");
		logger.log(LogStatus.INFO,"**************************** resetBadgePin ********************************");
		try {
			searchIdentity(firstName,lastName);
			getIndexOfAssetsHeaders();
			if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
				logger.log(LogStatus.INFO, "No Badge is assigned to the user ");
			else {
				WebElement code=driver.findElement(By.xpath("(//div[@data-qtip='Reset Pin' and @class = 'x-action-col-icon x-action-col-0  aegrid-passwordUpdate'])[1]//parent::div//ancestor::tr//child::td[6]")); 
				String accessId = code.getText();
				String oldPin = DBValidations.getResetPinInLenel(accessId);
				logger.log(LogStatus.INFO, "the pin before reset is "+ oldPin);
				if((driver.findElements(By.xpath(IdentityObjects.identityCommentsBtn)).size()>0)){
					ByAttribute.click("xpath",IdentityObjects.identityCommentsBtn, "clicking on comments button");
					Thread.sleep(1000);
	
					Robot robot = new Robot();
	
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyPress(KeyEvent.VK_U);
					robot.keyPress(KeyEvent.VK_T);
					robot.keyPress(KeyEvent.VK_O);
	
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
					Thread.sleep(2000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
					Thread.sleep(2000);
				}
				ByAttribute.click("xpath",IdentityObjects.identityResetPinBtn, "clicking on reset pin");
				Utility.pause(3);
				ByAttribute.click("xpath", IdentityObjects.identityResetPinYesBtn, "clickinng on yes pop up");
				WebDriverWait wait = new WebDriverWait(driver, 60, 500);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(//div[contains(text(),'Loading') and contains(@class,'x-mask-msg-text') and @role='presentation'])[2]")));
				Utility.pause(10);
				String newPin = DBValidations.getResetPinInLenel(accessId);
				logger.log(LogStatus.INFO, "the pin after reset is "+ newPin);
				if(newPin==oldPin)
				{
					logger.log(LogStatus.FAIL, "pin reset failed");
				}
				else {
					logger.log(LogStatus.PASS, "pin reset successful");
				}
				
			}
		}
				
			
			catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
				}
		}
	}
	
	public static ArrayList<String> checkIdentityExpiring( String AccessName, String email,String city,String workerType, String sysCode, String position) throws Throwable {
		String reqNo=null;
		ArrayList<String> arr = new ArrayList<String>();
		if (unhandledException == false) {
		try {
			
			if(ApiMethods.generateAccessToken())
		 	{
				for(int i=0;i<3;i++) {
					String firstName = "Test";
					String lastName = "User" + Utility.getRandomNumber(3);
					arr.add(firstName+" "+lastName);
					CommonFunctions.ApiMethods.createIdentityThroughAPI( "",firstName, lastName, email, city, workerType, sysCode,position,"");
					addAccessToUsers(firstName, lastName, AccessName,position);
					
			}
				triggerAccessJob("Identity Expiring");
				checkJobStatusinManageJobs("ACCESS_REVIEW_JOB");
				reqNo = checkRequestinMyRequest("Identity Expiring");
				arr.add(reqNo);
				}
		}
		catch(Exception e){
			logger.log(LogStatus.ERROR, "Failed to create Identity ");
		}
		}
		return arr;
	}
	
	public static String checkRequestinMyRequest(String jobType) throws Throwable {
		String reqNumber= null;
		if (unhandledException == false) {
			try {
				ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home tab");
				Utility.pause(2);
				ByAttribute.click("xpath", HomeObjects.homeMyRequestLnk, "Click on My Requests");
				Utility.pause(5);
				WebElement reqNo=driver.findElement(By.xpath("(//div[text()='"+jobType+"'])[1]//parent::td//preceding-sibling::td[1]"));
				reqNumber = reqNo.getText();
				Actions action = new Actions(driver);
				action.doubleClick(reqNo).perform();
				Utility.pause(5);
				Utility.verifyElementPresentReturn("//div[contains(text(),'"+reqNumber+"')]", "req no", true, false);
				Utility.verifyElementPresentReturn("//div[text()='"+jobType+"']", jobType, true, false);
				ByAttribute.click("xpath", "(//button[@class='aegrid-view'])[1]", "clicking on view status");
				Utility.pause(2);
				Utility.verifyElementPresentReturn("//td[@class='aegrid-clockend']", "Request status", true, false);
				WebElement status=driver.findElement(By.xpath("//td[@class='aegrid-clockend']"));
				String viewStatus = status.getText();
				logger.log(LogStatus.INFO, "the status of the request is"+viewStatus);
				ByAttribute.click("xpath", "(//span[text()='Cancel' and @class='x-btn-inner x-btn-inner-aebtnSecondary-medium' ])[2]", "clicking on cancel btn");
				
			}
			catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
		}
		}
		
		return reqNumber;
			
	}
	
	public static HashMap<String, String> checkIdentityExpiringRequestInManagerInbox(ArrayList<String> arr ,String reqNo, String reqUser,String accessName) throws Throwable {
		HashMap<String, String> map = new HashMap<>();
		if (unhandledException == false) {
			try {
				ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home tab");
				Utility.pause(2);
				ByAttribute.click("xpath", HomeObjects.homeInboxLnk, "Click on inbox");
				Utility.pause(5);
				
				Utility.handleAnnouncementPopup();
				
				WebElement req=driver.findElement(By.xpath("//div[text()='"+reqNo+"']"));
				Actions action = new Actions(driver);
				action.doubleClick(req).perform();
				Utility.pause(10);
				Utility.verifyElementPresentReturn("(//div[text()='"+reqNo+"'])[2]", "request no", true, false);
				Utility.verifyElementPresentReturn("//div[@class = 'x-component x-component-activityLabeltext' and text()='Identity Expiring']","Job type", true, false);
				WebElement reqBy=driver.findElement(By.xpath("//div[text()='Request By']//parent::div//following-sibling::div"));
				String reqByUser = reqBy.getText();
				logger.log(LogStatus.INFO, "the requested user id is " +reqByUser );
				if(reqByUser.equals(reqUser)) {
					logger.log(LogStatus.PASS, "the requested user id is admin");
				}
				else {
					logger.log(LogStatus.FAIL, "the requested user id is not admin");
				}
			/*	
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestCommentsBtn, "clicking on comments button");
				Robot robot = new Robot();

				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_A);
				robot.keyPress(KeyEvent.VK_U);
				robot.keyPress(KeyEvent.VK_T);
				robot.keyPress(KeyEvent.VK_O);

				ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddCommentBtn, "Click Add Comment Button");
				Thread.sleep(2000);
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
				Thread.sleep(2000);
				
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestAttachmentsBtn, "Click Attachments Button");
				Thread.sleep(2000);
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestUploadAttachmentBtn, "Cick Upload Attachment Button");
				Thread.sleep(3000);
				
				String uploadFile = System.getProperty("user.dir") + "\\Browser_Files\\Applicant_Photo.jpg";
				StringSelection ss = new StringSelection(uploadFile);
	            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

	            robot.keyPress(KeyEvent.VK_CONTROL);
	            robot.keyPress(KeyEvent.VK_V);
	            robot.keyRelease(KeyEvent.VK_V);
	            robot.keyRelease(KeyEvent.VK_CONTROL);
	            robot.keyPress(KeyEvent.VK_ENTER);
	            robot.keyRelease(KeyEvent.VK_ENTER);
				
	            Thread.sleep(5000);
	            Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Attachments']", "Attachment", false);
	            ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
	            Thread.sleep(1000);
	          */  

				for(int i=0;i<arr.size();i++)
				{
					Utility.verifyElementPresentReturn("//div[text()='"+arr.get(i)+"']", "identity", true, false);
					WebElement access = driver.findElement(By.xpath("//div[text()='"+arr.get(i)+"']//parent::td//following-sibling::td[2]//div"));
					String aName = access.getText();
					logger.log(LogStatus.INFO, "the access from element is "+aName);
					if(aName.equals(accessName)) {
						logger.log(LogStatus.PASS, "the access is "+accessName);
					}
					else {
						logger.log(LogStatus.FAIL, "the access is not "+accessName);
					}
					Utility.verifyElementPresentReturn("//div[text()='"+accessName+"']", "access "+accessName, true, false);
					if(i==0) {
						ByAttribute.click("xpath", "//div[text()='"+arr.get(i)+"']//parent::td//following-sibling::td[5]//button[@data-qtip='Click to Approve']", "clicking on approve for user "+arr.get(i));
						map.put(arr.get(i), "approve");
						Utility.pause(5);
					}
					else if(i==1) {
						ByAttribute.click("xpath", "//div[text()='"+arr.get(i)+"']//parent::td//following-sibling::td[5]//button[@data-qtip='Click to Reject']", "clicking on reject for user "+arr.get(i));
						map.put(arr.get(i), "reject");
						Utility.pause(5);
					}
					else if(i==2) {
						ByAttribute.click("xpath", "//div[text()='"+arr.get(i)+"']//parent::td//following-sibling::td[5]//button[@data-qtip='Click to Reject']", "clicking on reject for user "+arr.get(i));
						map.put(arr.get(i), "reject");
						Utility.pause(5);
					}
				}
				
			}
			catch (Exception e) {	
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		return map;
	}
	
	public static void checkUserStatusInIDM(String Name,HashMap<String, String> map, String accessName) throws Throwable {
		if (unhandledException == false) {
			try {
				String[] arr1 = Name.split(" ");
				String firstName = arr1[0];
				String lastName = arr1[1];
					searchUserinIDM(firstName,lastName);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "*********Click on Accesses Tab********** ");
					Utility.pause(2);
					if(map.get(Name)=="approve") {
						Utility.verifyElementPresentReturn("//div[text()='"+accessName+"']", "access Name for user "+Name, true, false);
					}
					else {
						Utility.verifyElementNotPresent("//div[text()='"+accessName+"']", "access Name for user "+Name, true);
					}
					
				}
			
			catch (Exception e) {	
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		
		}
	}
	
	public static void checkRequestStatusInMyRequest(String reqNo) throws Throwable {
		if (unhandledException == false) {
			try {
				ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home tab");
				Utility.pause(2);
				ByAttribute.click("xpath", HomeObjects.homeMyRequestLnk, "Click on My Requests");
				Utility.pause(5);
				WebElement reqStatus = driver.findElement(By.xpath("//div[text()='"+reqNo+"']//parent::td//following-sibling::td[8]//div//label"));
				String reqStatusValue = reqStatus.getText();
				if(reqStatusValue.equals("COMPLETED")) {
					logger.log(LogStatus.PASS, "the req status is completed");
				}
				else {
					logger.log(LogStatus.PASS, "the req status is not completed");
				}
				
				WebElement reqEle = driver.findElement(By.xpath("//div[text()='"+reqNo+"']"));
				Actions action = new Actions(driver);
				action.doubleClick(reqEle).perform();
				Utility.pause(5);
				Utility.verifyElementPresentReturn("//div[contains(text(),'"+reqNo+"')]", "req no", true, false);
				Utility.pause(2);
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "clicking on history icon");
				Utility.pause(2);
				WebElement res1 = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :' ]//following-sibling::div)[1]"));
				String msg1 = res1.getText();
				logger.log(LogStatus.INFO ,"Provisioning msg for user is  :" + msg1); 
				WebElement res2 = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :' ]//following-sibling::div)[2]"));
				String msg2 = res2.getText();
				logger.log(LogStatus.INFO ,"Provisioning msg for user is  :" + msg2);
				WebElement res3 = driver.findElement(By.xpath("(//div[text()='Provisioning Done for :' ]//following-sibling::div)[3]"));
				String msg3 = res3.getText();
				logger.log(LogStatus.INFO ,"Provisioning msg for user is  :" + msg3);
				Utility.pause(2);
				ByAttribute.click("xpath", ".//div[@data-qtip='Close Dialog']/div", "Click Close Dialog button"); 
				
				}
			catch (Exception e) {	
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}


	public static void searchUserinIDM(String firstName, String lastName) {
		if (unhandledException == false) {
			try {
				if((driver.findElements(By.xpath(IdentityObjects.cardHoldersAndAssetsTabBtn)).size()>0)){
					ByAttribute.mouseHover("xpath", IdentityObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Identity tab");
					Utility.pause(2);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identity ");
					Utility.pause(5);
				}
					
				else{
					ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on Identity tab");
					Utility.pause(2);
					
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity ");
					Utility.pause(8);
				}
				
				Utility.handleAnnouncementPopup();
								
				Utility.pause(10);
				if((driver.findElements(By.xpath("//input[@placeholder='Choose Field']")).size()>0)){
					ByAttribute.click("xpath", "//input[@placeholder='Choose Field']", "click to enter the value");
					Utility.pause(2);
					ByAttribute.setText("xpath", "//input[@placeholder='Enter First Name']",firstName, "Enter the first name");
					Utility.pause(2);
				}
				else {
					ByAttribute.click("xpath", IdentityObjects.filterIconLnk, "Click on Filter icon ");
					Utility.pause(3);
					ByAttribute.click("xpath", IdentityObjects.addFilterLnk, "Click on Add icon ");
					ByAttribute.click("xpath", IdentityObjects.enterFieldName1ToFilter, "click to enter field name for Filtering");
					Utility.pause(2);
					ByAttribute.setText("xpath", IdentityObjects.enterFieldName1ToFilter,"First Name", "Enter the field name for Filtering");
					Utility.pause(2);
					ByAttribute.click("xpath", IdentityObjects.clickFieldValue1, "click to enter the value");
					Utility.pause(2);
					ByAttribute.setText("xpath", IdentityObjects.enterFieldValue1,firstName, "Enter the first name");
				}
				
				Utility.pause(2);
				if((driver.findElements(By.xpath("(//input[@placeholder='Choose Field'])[2]")).size()>0)){
					ByAttribute.click("xpath", "(//input[@placeholder='Choose Field'])[2]", "click to enter the second value");
					Utility.pause(2);
					ByAttribute.setText("xpath", "//input[@placeholder='Enter Last Name']",lastName, "Enter the last name");
				}
				else {
					ByAttribute.click("xpath", IdentityObjects.addFilterLnk, "Click on Add icon to enter the filter");
					Utility.pause(2);
					ByAttribute.click("xpath", IdentityObjects.enterFieldName2ToFilter, "click to enter field name for Filtering");
					Utility.pause(2);
					ByAttribute.setText("xpath", IdentityObjects.enterFieldName2ToFilter,"Last Name", "Enter the field name for Filtering");
					Utility.pause(2);
					ByAttribute.click("xpath", IdentityObjects.clickFieldValue2, "click to enter the second value");
					Utility.pause(2);
					ByAttribute.setText("xpath", IdentityObjects.enterFieldValue2,lastName, "Enter the last name");
				}
			
				Utility.pause(5);
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(10);
				if(driver.findElements(By.xpath("((//div[text()='"+firstName+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]")).size()>0){
					WebElement record=driver.findElement(By.xpath("((//div[text()='"+firstName+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
					WebElement record12=driver.findElement(By.xpath("((//div[text()='"+firstName+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[3]"));
					String identityCode=record.getText();
					Utility.pause(2);
					for(int j=0;j<3 ;j++){
						if(driver.findElements(By.xpath("//label[contains(text(),'"+firstName+"')]")).size()>0)
						{
							logger.log(LogStatus.INFO ,"double click successful");
							break;
						}
						else {
							action.doubleClick(record12).perform();
							Utility.pause(15);
						}
					}
					String searchResult= "//label[contains(text(),'"+firstName+"')]";
					if(Utility.verifyElementPresentReturn(searchResult,firstName,true,false)){
						logger.log(LogStatus.INFO ,"Search result record appeared with identity code as : "+ identityCode);
					}
					
					
				}
			}
			catch(Exception e){
				logger.log(LogStatus.ERROR, "Failed to create Identity ");
			}
		}
	}
	
	public static void addAccessToUsers(String firstName, String lastName, String AccessName,String position) throws Throwable {
		if (unhandledException == false) {
			try {
				searchUserinIDM(firstName,lastName);
				addPosition(position);
				ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAccessTabBtn, "*********Click on Accesses Tab********** ");
				Utility.pause(2);
				
				String addRecordsIcon = "//a[normalize-space(text())='Click here to Add']";
				ByAttribute.click("xpath", addRecordsIcon, "click on add icon to insert new access");
				Utility.pause(5);
				Actions action = new Actions(driver);
				action.sendKeys(AccessName);
				action.build().perform();
				Utility.pause(5);
				WebElement accessValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'"+AccessName+"')]"));
				action.moveToElement(accessValue).click();
				action.build().perform();
				logger.log(LogStatus.INFO, "Access Value selected");
				Utility.pause(2);
		
				WebElement ele=driver.findElement(By.xpath("//span[text()='Description']"));
				ele.click();
				ByAttribute.click("xpath", IdentityObjects.SaveBtn, "Click on save Button ");
				Utility.pause(15);
	
			}
			
			 catch (Exception e) {
					String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
					Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
	}
	
	public static void addPosition(String position) throws Throwable {
		if (unhandledException == false) {
			try {
				ByAttribute.setText("xpath", "//input[@placeholder='Select Position']", position, "Adding Position");
				Utility.pause(2);
				ByAttribute.click("xpath", "//li[text()='"+position+"']", "selecting position as "+position);
			}
			 catch (Exception e) {
					String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
					Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	
	public static void triggerAccessJob(String jobType) throws Throwable {
		if (unhandledException == false) {
			try {
				ByAttribute.mouseHover("xpath", AccessObjects.accessTabLnk, "Mouse Hover on Access tab");
				Utility.pause(2);
				ByAttribute.click("xpath", AccessObjects.accessReviewLnk, "Click on Access Review");
				Utility.pause(5);
				ByAttribute.click("xpath", AccessObjects.filterIconLnk, "clicking on filter icon");
				ByAttribute.click("xpath", AccessObjects.addFilterLnk, "clicking on add filter");
				ByAttribute.setText("xpath", AccessObjects.enterFieldValue1,"Name", "putting name filter");
				ByAttribute.click("xpath", "//li[text()='Name']", "selecting name value");
				Utility.pause(2);
				ByAttribute.setText("xpath", "//input[@placeholder='Enter Name']",jobType, "searching job type");
				Utility.pause(2);
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(10);
				WebElement checkBox = driver.findElement(By.xpath("(//div[contains(text(),'"+jobType+"')])[1]//parent::td[contains(@class,'x-grid-cell x-grid-td x-grid-cell-gridcolumn')]//preceding-sibling::td"));
				action.doubleClick(checkBox).perform();
				Utility.pause(5);
				Utility.verifyElementPresentReturn("//label[contains(text(),'"+jobType+"')]", jobType+" label", true, false);
				ByAttribute.click("xpath", AccessObjects.accessSceduleDetailsLnk, "clicking on schedule details tab");
				Utility.pause(2);
//				ByAttribute.click("xpath",AccessObjects.accessReviewScheduleBtn,"clicking on schedule icon");
//				Utility.pause(5);
				ByAttribute.setText("xpath", AccessObjects.accessReviewSceduleTypeDnd, "Once", "selecting schedule type as Once");
				Utility.pause(2);
				ByAttribute.click("xpath", "//li[text()='Once']", "selecting schedule type value");
				Utility.pause(2);
				ByAttribute.setText("xpath", AccessObjects.accessReviewTimeZoneDnd, "India Standard Time (UTC +5:30)", "selecting IST time zone");
				Utility.pause(2);
				ByAttribute.click("xpath", "//li[text()='India Standard Time (UTC +5:30)']", "selecting time zone value");
				Utility.pause(2);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yy h:mm a");  
				LocalDateTime now = LocalDateTime.now();  
				String currTime =   dtf.format(now.plusMinutes(1));
				System.out.println("the current time is "+currTime);
				ByAttribute.setText("xpath", AccessObjects.accessReviewStartTimeDnd, currTime, "typing current time");
				Utility.pause(5);
//				ByAttribute.click("xpath", AccessObjects.accessReviewConfirmBtn, "clicking on confirm button");
//				Utility.pause(2);
				ByAttribute.click("xpath", AccessObjects.saveBtn, "clicking on save button");
				Utility.pause(10);
				Utility.pause(60);
				
			}
			 catch (Exception e) {
					String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
					Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
			
	
	
	public static void checkJobStatusinManageJobs(String jobType) throws Throwable {
		if (unhandledException == false) {
			try {
				ByAttribute.mouseHover("xpath", AdminObjects.adminTabBtn, "Mouse Hover on Admin tab");
				Utility.pause(2);
				ByAttribute.click("xpath", AdminObjects.adminProvManageJobsLnk, "Click on Manage Jobs");
				Utility.pause(5);
				// addig because of duplicate jobs
				Utility.pause(60);
				ByAttribute.click("xpath", "//*[@data-qtip='Reload']", "Click on Manage Jobs");
				Utility.pause(5);
				// end here
				WebElement jobStatus=driver.findElement(By.xpath("(//div[contains(text(),'"+jobType+"')])[1]//parent::td//following-sibling::td[3]"));
				String status = jobStatus.getText();
				logger.log(LogStatus.INFO, "the status of job is "+status);
				Utility.verifyElementPresentReturn("(//div[contains(text(),'"+jobType+"')])[1]//parent::td//following-sibling::td[3]", "jobstatus", true, false);
				if(status.equals("COMPLETED")) {
					logger.log(LogStatus.PASS, "the status of job is completed");
				}
				else {
					logger.log(LogStatus.FAIL, "the status of job is not completed");
				}
			}
			 catch (Exception e) {
					String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
					Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
			
		
	}
	
	/**
	* <h1>searchIdentity</h1>
	* This is Method to search and open identity
	* @author Vishal Gupta
	* @modified
	* @version 1.0
	* @since 2-15-2021
	**/

	public static void searchIdentity(String firstName, String lastName) throws Throwable {

		if (unhandledException == false) {
			try {
				ByAttribute.mouseHover("xpath", IdentityObjects.manageEntities, "Mouse Hover on Manage Entities tab");
				Utility.pause(5);
				ByAttribute.click("xpath", IdentityObjects.cardHolders, "Click on Card Holders ");
				Utility.pause(30);
				
				Utility.handleAnnouncementPopup();
				
				ByAttribute.click("xpath", IdentityObjects.filterIconLnk, "Click on Filter icon ");
				Utility.pause(3);
				ByAttribute.click("xpath", IdentityObjects.addFilterLnk, "Click on Add icon ");
				ByAttribute.click("xpath", IdentityObjects.enterFieldName1ToFilter, "click to enter field name for Filtering");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.enterFieldName1ToFilter,"First Name", "Enter the field name for Filtering");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.clickFieldValue1, "click to enter the value");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.enterFieldValue1,firstName, "Enter the first name");
				Utility.pause(2);
		
				ByAttribute.click("xpath", IdentityObjects.addFilterLnk, "Click on Add icon to enter the filter");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.enterFieldName2ToFilter, "click to enter field name for Filtering");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.enterFieldName2ToFilter,"Last Name", "Enter the field name for Filtering");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.clickFieldValue2, "click to enter the second value");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.enterFieldValue2,lastName, "Enter the last name");
		
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(20);
				if(driver.findElements(By.xpath("((//div[text()='"+firstName+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]")).size()>0){
					WebElement record=driver.findElement(By.xpath("((//div[text()='"+firstName+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
					WebElement record12=driver.findElement(By.xpath("((//div[text()='"+firstName+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[3]"));
					String identityCode=record.getText();
					Utility.pause(2);
					action.doubleClick(record12).perform();
					Utility.pause(15);
					String searchResult= "//label[contains(text(),'"+firstName+"')]";
					if(Utility.verifyElementPresentReturn(searchResult,firstName,true,false)){
						logger.log(LogStatus.INFO ,"Search result record appeared with identity code as : "+ identityCode);
					}
					logger.log(LogStatus.PASS, "Search Identity successful");
				}
				else{
					logger.log(LogStatus.FAIL ,"Failed to search the record");
				}
				ByAttribute.click("xpath", IdentityObjects.badgeTabLnk, "Click on Badges Tab ");
		}
			catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
				}
		}
	}
	
	
	/**
	 * <h1>idmRemoveAssetAccess</h1> 
	 * This is Method to Validate User in IDM
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 4-15-2021
	 * @param String requestType
	 * @return none
	 **/

	public static void idmRemoveAssetAccess(String userId, String idmTab, String assetAccessName) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"******************************* idmRemoveAssetAccess  ****************************************");
			logger.log(LogStatus.INFO,
					"**************************** idmRemoveAssetAccess ********************************");

			try {
				
					if(driver.findElements(By.xpath(IdentityObjects.idmCardholdersAssetsTabBtn)).size()>0)
					{
						ByAttribute.mouseHover("xpath", IdentityObjects.idmCardholdersAssetsTabBtn, "Mouse Hover on Cardholders & Assets Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identities Link");
						Utility.pause(5);
					}else{
						ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on IDM Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity Link");
						Utility.pause(5);
					}
					
					ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, userId, "Enter User ID in Search field");
					Thread.sleep(3000);
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']", "User Available in IDM", false);
					
					Actions actions = new Actions(driver);
					WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']"));
					actions.doubleClick(elementLocator).perform();
					Thread.sleep(3000);
					
					ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='"+idmTab+"']", "Click "+idmTab+" Tab");
					Thread.sleep(3000);
					
					ByAttribute.click("xpath", ".//tbody//div[text()='"+assetAccessName+"']//ancestor::tr//div[contains(@class,'aegrid-rowMinus') and @data-qtip='Delete Row']", "Click Delete Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySaveBtn, "Click Save Button");
					Thread.sleep(10000);
					if(idmTab.equals("Accesses"))
					{
						Utility.pause(5);
					}
					
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityMenuBtn, "Click IDM Menu button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click Reload Identity button");
					Thread.sleep(3000);
					ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='"+idmTab+"']", "Click "+idmTab+" Tab");
					Thread.sleep(1000);
					Utility.verifyElementNotPresent(".//tbody//div[text()='"+assetAccessName+"']", assetAccessName, false);
					
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}


	/**
	 * <h1>idmAddLocationAccessAsset</h1> 
	 * This is Method to Validate User in IDM
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 6-08-2021
	 * @param String requestType
	 * @return none
	 **/
	

	public static void idmAddLocationAccessAsset(String userId, String idmTab, String assetAccessName) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"****************************** idmAddLocationAccessAsset  ***************************************");
			logger.log(LogStatus.INFO,
					"*************************** idmAddLocationAccessAsset *******************************");

			try {

				String editedValidToDate = "";
				if(driver.findElements(By.xpath(IdentityObjects.idmCardholdersAssetsTabBtn)).size()>0)
				{
					ByAttribute.mouseHover("xpath", IdentityObjects.idmCardholdersAssetsTabBtn, "Mouse Hover on Cardholders & Assets Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentitiesLnk, "Click on Manage Identities Link");
					Utility.pause(5);
				}else{
					ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on IDM Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity Link");
					Utility.pause(5);
				}

				ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, userId, "Enter User ID in Search field");
				Thread.sleep(3000);
				Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']", "User Available in IDM", false);

				Actions actions = new Actions(driver);
				WebElement elementLocator = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']"));
				actions.doubleClick(elementLocator).perform();
				Thread.sleep(3000);

				ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='"+idmTab+"']", "Click "+idmTab+" Tab");
				Thread.sleep(2000);

	//			ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsAccessLocAddBtn, "Click Add Button");
				ByAttribute.click("xpath", "(//a[normalize-space(text())='Click here to Add'])", "Click Add Button");
				Thread.sleep(1000);
				
				
				switch(idmTab){
				case "Location":
					ByAttribute.clearSetText("xpath", ".//input[@placeholder='Select Location']", assetAccessName, "Enter Location");
					Thread.sleep(2000);
					ByAttribute.click("xpath", ".//li[@role='option' and contains(text(),'"+assetAccessName+"')]", "Select from List");
					Thread.sleep(2000);
					break;
				case "Accesses":
					ByAttribute.clearSetText("xpath", ".//input[@placeholder='Select Access']", assetAccessName, "Enter Access Name");
					Thread.sleep(2000);
					ByAttribute.click("xpath", ".//li[@role='option' and contains(text(),'"+assetAccessName+"')]", "Select from List");
					Thread.sleep(1500);
					ByAttribute.click("xpath", ".//input[@name='searchField']", "Click outside the table");
					Thread.sleep(1500);
					break;
				case "Assets":
					ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentityAssetsSelectAssetTxt, assetAccessName, "Enter Badge Name: "+assetAccessName);
					Thread.sleep(4000);
					ByAttribute.click("xpath", ".//span[text()='"+assetAccessName+"']", "Select Badge");
					Thread.sleep(1000);
					
					DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy hh:mm a");
					Date currentDate = new Date();
					System.out.println(dateFormat.format(currentDate));

					// convert date to calendar
					Calendar c = Calendar.getInstance();
					c.setTime(currentDate);

					// manipulate date
					c.add(Calendar.MONTH, 1);

					// convert calendar to date
					Date currentDatePlusOne = c.getTime();
					AGlobalComponents.validToDate = currentDatePlusOne;
					String validTo = new SimpleDateFormat("dd.MM.yy h:mm a").format(currentDatePlusOne);

					ByAttribute.clearSetText("xpath", IdentityObjects.identityAssignAssetValidToDate, validTo, "Enter Badge Validity");
					Thread.sleep(1000);
					ByAttribute.clearSetText("xpath", IdentityObjects.idmAddAssetStatusDdn, "Active", "Enter Badge Status as Active");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//li[@role='option' and text()='Active']", "Select from List");
					Thread.sleep(1000);
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsAddAssetConfirmBtn, "Click Confirm Button");
					Thread.sleep(2000);
					editedValidToDate = new SimpleDateFormat("dd.MM.yy").format(currentDatePlusOne);
					Utility.verifyElementPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and contains(text(),'"+editedValidToDate+"')]", "Recently Added Badge", false);
					Thread.sleep(1000);
					break;
				default:
					System.out.println("Invalid Input");
					logger.log(LogStatus.WARNING, "Invalid Input");
				
				}
				
				ByAttribute.click("xpath", IdentityObjects.idmManageIdentitySaveBtn, "Click Save Button");
				Thread.sleep(5000);
				if(idmTab.equals("Assets"))
				{
					Thread.sleep(5000);
				}
				ByAttribute.click("xpath", IdentityObjects.idmManageIdentityMenuBtn, "Click IDM Menu button");
				Thread.sleep(1000);
				ByAttribute.click("xpath", IdentityObjects.idmManageIdentityReloadBtn, "Click Reload Identity button");
				Thread.sleep(3000);
				ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='"+idmTab+"']", "Click "+idmTab+" Tab");
				Thread.sleep(1000);
				if(idmTab.equals("Assets"))
				{
					Utility.verifyElementPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and contains(text(),'"+editedValidToDate+"')]", "Recently Added Badge", false);
				}else{
					Utility.verifyElementPresent(".//tbody//div[contains(text(),'"+assetAccessName+"')]", assetAccessName, false);
				}
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	
}

