package CommonFunctions;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.ByAttribute;
import CommonClassReusables.MsSql;
import CommonClassReusables.DBValidations;
import CommonClassReusables.Utility;
import ObjectRepository.AdminObjects;
import ObjectRepository.SelfServiceObjects;
import ObjectRepository.AssetObjects;
import ObjectRepository.HomeObjects;
import ObjectRepository.IdentityObjects;

public class Self_Service_CommonMethods extends BrowserSelection{
	
	private static int assignmentStatusIndex,accessIndex,actionIndex,assetCodeIndex = 0,systemIndex;
	private static String oldAssetStatus = null,newAssetStatus=null,badgeStatusInRequest=null,reqNum="",requestorName="",assetStatusBeforeOffboarding="";
	private static ArrayList<String> contractorAccessNames = new ArrayList<String>();
	private static ArrayList<String> permanentAccessNames = new ArrayList<String>();
	private static String access1ForTempOnboarding = "SC NWPTIE NONR GATE ACCESS" , access2ForTempOnboarding = "End User";
	private static String access1ForPermanentEmp = "SC CHRWLS NONR CONST GATE" , access2ForPermanentEmp = "SC LEEXSS NONR GENERAL ACCESS";
	private static String system1ForTempOnboarding = "AMAG",system2ForTempOnboarding = "HR System",departmentName="Finance";
	private static String access1ForEmpOnboarding = "CCURE_NEW_TEST18" , access2ForEmpOnboarding = "New Admin Role";
	private static ArrayList<String> tempWorkerAccessNames = new ArrayList<String>();
	private static ArrayList<String> tempWorkerSystemNames = new ArrayList<String>();

	
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

				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) {
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//label[text()='"+requestType+"']//parent::div//input[@type='radio']", "Click "+requestType+" Radio Button");
					Thread.sleep(2000);
					
//					To be Added When Implemented
//					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					
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
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
						Thread.sleep(1000);
						if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestSearchAccessTxt)).size() > 0)
						{
							System.out.println("Navigation to Access Page Successful");
							logger.log(LogStatus.INFO, "Navigation to Access Page Successful");
							
		//					ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchAccessTxt, access, "Search Access in Location");
		//					Utility.verifyElementPresent(".//h4[@data-qtip='"+access+"']", "Searched Access", false);
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchAccessTxt, "", "Clear Search Filter");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']//ancestor::div[@class='innerWidget']", "Add Location");
							
							List<WebElement> locationAddButtonElements = driver.findElements(By.xpath(".//div[@class='innerWidget' and contains(@data-boundview,'baseAccessLocationBox')]"));
							WebElement locationAddButton = locationAddButtonElements.get(0);

							locationAddButton.click();
							logger.log(LogStatus.INFO, "Add 2nd Location");
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreatePreviousBtn, "Click Previous Button");
							Thread.sleep(1000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
							Thread.sleep(2000);
//							Utility.verifyElementPresent(".//h4[@data-qtip='"+access+"']//ancestor::div[@class='innerWidget x-item-select-active x-item-selected']", "Access Selected", false);
							
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
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
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
							String editedValidToDate = new SimpleDateFormat("MMM d").format(currentDatePlusOne);
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
							
							ByAttribute.click("xpath", ".//tbody//div[@role='button' and @data-qtip='Attachment']", "Click Access Level Attachment Button");
							Thread.sleep(2000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestUploadAttachmentBtn, "Cick Upload Attachment Button");
							Thread.sleep(3000);
							
							uploadFile = System.getProperty("user.dir") + "\\Browser_Files\\Signature.png";
							ss = new StringSelection(uploadFile);
				            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

				            robot.keyPress(KeyEvent.VK_CONTROL);
				            robot.keyPress(KeyEvent.VK_V);
				            robot.keyRelease(KeyEvent.VK_V);
				            robot.keyRelease(KeyEvent.VK_CONTROL);
				            robot.keyPress(KeyEvent.VK_ENTER);
				            robot.keyRelease(KeyEvent.VK_ENTER);
							
				            Thread.sleep(2000);
//				            Utility.verifyElementPresent(".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Attachments']", "Attachment", false);
				            ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog button");
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
							}else{
								ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationBusinessJustificationTxt, "Automation Test", "Enter Business Justification");
							}
							Thread.sleep(1000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationConfirmBtn, "Click Confirm Button");
							Thread.sleep(40000);
							
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
			System.out.println("***************************** createAccessRequestOthers *********************************");
			logger.log(LogStatus.INFO,"***************************** createAccessRequestOthers *********************************");
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
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
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
//							ByAttribute.click("xpath", "(.//div[@data-qtip='Close Dialog']/div)[2]", "Click Close Dialog button");
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
//							ByAttribute.click("xpath", "(.//div[@data-qtip='Close Dialog']/div)[2]", "Click Close Dialog button");
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
						String editedValidToDate = new SimpleDateFormat("MMM d").format(currentDatePlusOne);
						
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
							Thread.sleep(20000);
						}else if(requestType.equals("Application Access")){
							Thread.sleep(18000);
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

	public static void approveAccessRequest(String locationName, String reqNum, String userId) throws Throwable {

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
				
				if (driver.findElements(By.xpath(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']")).size() > 0) {
					System.out.println("Access Request is Found in Inbox");
					logger.log(LogStatus.PASS, "Access Request is Found in Inbox");
					
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='tagorange' and text()='OPEN']", "Request Number: "+reqNum+" Current Status OPEN", false);
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and contains(text(),'Access Owner')]", "Request Number: "+reqNum+" Workflow Stage Access Owner", false);
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+locationName+"']", "Access Name Matching with Location Selected via Self Service", false);
					Thread.sleep(500);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestApproveBtn, "Click Approve Button");
					Thread.sleep(50000);
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click Expand Inbox Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='Access Owner']", "Access Owner Stage Inbox", false);
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
					
					Thread.sleep(1000);
					ByAttribute.click("xpath", "(.//tbody//div[@data-qtip='Comments'])[1]", "Click Access Level Comments Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//*[@data-ref='btnInnerEl' and text()='Add Comment']", "Access Level Comment Added via Self Service", false);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCloseDialogBtn, "Click Close Dialog Button");
					
					String validToDate = new SimpleDateFormat("MMM d").format(AGlobalComponents.validToDate);
					Utility.verifyElementPresent(".//tbody//div[contains(text(),'"+validToDate+"')]", "Valid To Date Modified from Self Service to: "+validToDate, false);
					Thread.sleep(1000);
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusBtn, "Click View Status Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and text()='Workflow Status : "+reqNum+"']", "Workflow Status for Request : "+reqNum, false);
					Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'Approved')]", "Access Request Approved", false);
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");
					Thread.sleep(1000);
					
					//Validate Attachment Yet to be Verified
					
					
////					Provisioning Monitor Validation
//					ByAttribute.mouseHover("xpath", AdminObjects.adminTabBtn, "Mouse Hover on Admin Tab Link");
//					Thread.sleep(1000);
//					ByAttribute.click("xpath", AdminObjects.adminProvMonitorLnk, "Click on Provisioning Monitor Link");
//					Utility.pause(20);
//					
//					for(int i=0;i<20;i++)
//					{
//						if(driver.findElements(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']//ancestor::tr//div[@class='x-grid-cell-inner ' and text()='ASSIGN_ROLES_SUCCESS']")).size()>0)
//						{
//							Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']", "Request Provisioned with Request Number: "+reqNum, false);
//							Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+reqNum+"']//ancestor::tr//div[@class='x-grid-cell-inner ' and text()='ASSIGN_ROLES_SUCCESS']", "Request Provisioned with Provisioning Code: ASSIGN_ROLES_SUCCESS", false);
//							break;
//						}else{
//							ByAttribute.click("xpath", AdminObjects.adminProvMonitorReloadBtn, "Click Provisioning Monitor Refresh button");
//							Thread.sleep(3000);
//						}
//					}
//					
////					IDM Validation
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
//					Utility.verifyElementPresent(".//tbody/tr/td[2]/div[@class='x-grid-cell-inner ' and text()='"+locationName+"']", "Access: "+locationName+" is Added with Provided Dates", false);
								
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
					Utility.verifyElementNotPresent(".//tbody/tr/td[2]/div[@class='x-grid-cell-inner ' and text()='"+locationName+"']", "Access: "+locationName+" with Provided Dates", true);
				
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
					"******************************** idmUserValidation  *****************************************");
			logger.log(LogStatus.INFO,
					"***************************** idmUserValidation *********************************");

			try {
				
				
	//				IDM Validation
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
					
					ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='"+validationTab+"']", "Click "+validationTab+" Tab");
					Thread.sleep(1000);
					
					if(validationKey.equals(""))
					{
						Utility.verifyElementPresent("(.//tbody//label[text()='"+validationStatus+"'])[1]", validationTab+" Tab has Value with Status: "+validationStatus, false);
					}else{
						Utility.verifyElementPresent(".//tbody//div[text()='"+validationKey+"']//ancestor::tr//label[text()='"+validationStatus+"']", validationTab+" Tab has "+validationKey+" with Status: "+validationStatus, false);
					}
					Thread.sleep(3000);
//					Utility.verifyMail("alerthsc@alertenterprise.com", "passw0rd@123", "Approval required","Please log into portal to review and approve this request.");
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

	public static void validateAccessRequestStatus(String requestNumber, String requestStatus,String userFullName) throws Throwable {

		if (unhandledException == false) {
			System.out.println("***************************** validateAccessRequestStatus *********************************");
			logger.log(LogStatus.INFO,"***************************** validateAccessRequestStatus *********************************");
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

		String reqNum="";
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
	 * <h1>approveBadgeRequest</h1> 
	 * This is Method to Approve an Access Request
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 12-16-2020
	 * @param String requestType
	 * @return none
	 **/

	public static void approveBadgeRequest(int stageNum, String reqNum, String cardStatus, String assetName, String userId) throws Throwable {

		if (unhandledException == false) {
			System.out.println(
					"******************************** approveBadgeRequest  *****************************************");
			logger.log(LogStatus.INFO,
					"***************************** approveBadgeRequest *********************************");

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
						Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and contains(text(),'Manager')]", "Request Number: "+reqNum+" Workflow Stage Access Owner", false);
					}else{
						Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and contains(text(),'Access Owner')]", "Request Number: "+reqNum+" Workflow Stage Access Owner", false);
					}
					Thread.sleep(1000);
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					Utility.verifyElementPresent(".//tbody//label[text()='"+cardStatus+"']", "Card Staus: "+cardStatus+" as set in Request", false);
					Thread.sleep(500);
					
					if(stageNum==1)
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
						Thread.sleep(10000);
					}else{
						Thread.sleep(30000);
					}
					
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click Expand Inbox Button");
					Thread.sleep(1000);
					if(stageNum==1)
					{
						Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='Manager']", "Manager Stage Inbox", false);
					}else{
						Utility.verifyElementPresent(".//span[contains(@class,'x-tree-node-text') and text()='Access Owner']", "Access Owner Stage Inbox", false);
					}
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCompletedBtn, "Click Completed Inbox Button");
					Thread.sleep(2000);
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggreen' and text()='COMPLETED']", "Request Number: "+reqNum+" Changed to Completed", false);
					
					ByAttribute.click("xpath", ".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']", "Click on Request from Inbox");
					Thread.sleep(3000);
					
					if(stageNum==2)
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
						
						//PRINT BADGE
						ByAttribute.click("xpath", SelfServiceObjects.selfServicePrintBtn, "Click Print Button");
						Thread.sleep(2000);
						Utility.verifyElementPresent(".//*[contains(@id,'header-title-textEl') and text()='Badge Print']", "Badge Print Dialog Box", false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceBadgeCancelBtn, "Click Cancel Button");
					}
					
					ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusBtn, "Click View Status Button");
					Thread.sleep(1000);
					Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and contains(text(),'"+reqNum+"')]", "Workflow Status for Request : "+reqNum, false);
					
					if(stageNum==1)
					{
						Utility.verifyElementPresent(".//*[@id='containerBox']//td[contains(text(),'Pending action by')]", "Action Pending at Access Owner Stage", false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", "(.//div[contains(@class,'window')]//span[@data-ref='btnInnerEl' and text()='Cancel'])[2]", "Click Cancel Button");
					}else{
						Utility.verifyElementPresent(".//*[@id='containerBoxHistory']//td[contains(text(),'Asset: "+assetName+" Approve for')]", "Asset: "+assetName+" Assigned Successfully", false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", HomeObjects.homeInboxRequestWorkflowViewStatusCancelBtn, "Click Cancel Button");
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

	public static void approveReplaceBadgeRequest(int stageNum, String reqNum, String cardStatus, String assetName, String userId) throws Throwable {

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
				
				if (driver.findElements(By.xpath(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']")).size() > 0) {
					System.out.println("Access Request is Found in Inbox");
					logger.log(LogStatus.PASS, "Access Request is Found in Inbox");
					
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='tagorange' and text()='OPEN']", "Request Number: "+reqNum+" Current Status OPEN", false);
					if(stageNum==1)
					{
						Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and contains(text(),'Manager')]", "Request Number: "+reqNum+" Workflow Stage Manager", false);
					}else{
						Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and contains(text(),'Badge Admin')]", "Request Number: "+reqNum+" Workflow Stage Badge Admin", false);
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
						ByAttribute.click("xpath", SelfServiceObjects.selfServiceBadgeCancelBtn, "Click Cancel Button");

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
						Utility.verifyElementPresent(".//*[@id='containerBoxHistory']//td[contains(text(),'Asset: "+assetName+" Approve for')]", "Asset: "+assetName+" Assigned Successfully", false);
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
	 * <h1>checkStatus</h1> 
	 * This is Method to check sset status before and after request approval
	 * @author Monika Mehta
	 * @modified
	 * @version 1.0
	 * @since 01-15-2021
	 * @param String firstname,lastname
	 * @return none
	 **/

		public static void checkStatusAfterRequestApproval(String firstName, String lastName,String newAttribute) throws Throwable {
	
		if (unhandledException == false) {
			System.out.println("********* Check status in IDM After request approval*******************");
			logger.log(LogStatus.INFO,"******Check status in IDM After request Approval************");
			try {
				WebElement image=null ;
				String oldPhotoSrc=null;
										
				if(AGlobalComponents.RequestSubmit){
					logger.log(LogStatus.INFO, "Checking status of the user after request submission");
									
					//Photo validation
					if(AGlobalComponents.updatePhoto){
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
						Utility.pause(5);
						logger.log(LogStatus.INFO, "Capturing updated photo");
						String newPhotoSrc = image.getAttribute("src");
						if(Utility.compareStringValues(oldPhotoSrc,newPhotoSrc ))
							logger.log(LogStatus.FAIL, "image is not modified");
						else{
							logger.log(LogStatus.PASS, "image is updated");
							Utility.verifyElementPresent(IdentityObjects.imageLnk, "new image", false);
						}
					}	
					//Asset Status Validation 
					if(AGlobalComponents.wellnessCheckCase){	
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
						Utility.pause(5);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
						WebElement assetStatus = driver.findElement(By.xpath("//tr//td["+assignmentStatusIndex+"]//div[@class='x-grid-cell-inner ']//label"));
						newAssetStatus=assetStatus.getText();
						Utility.verifyElementPresent("//tr//td["+assignmentStatusIndex+"]//div[@class='x-grid-cell-inner ']//label", "Asset Assignemnt status", false);
					
						logger.log(LogStatus.INFO, "Current asset Assignment status is : " +newAssetStatus);
						//if condition lagani hai ki IDM me bhi same status hai jo request open karne pe tha and before status se opposite hai hence pass
						logger.log(LogStatus.PASS, "Current asset Assignment status in IDM is  : " +newAssetStatus);
					}
					
					
					if(AGlobalComponents.requestLocationAccessOthers){
						String accessAdded=newAttribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
						Utility.pause(5);
						ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Access Tab ");
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
					
					//Access Validation 
					if((AGlobalComponents.contractorToPermanentEmployeeConversion)){
						String accessAdded=newAttribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
						Utility.pause(5);
						ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Access Tab ");
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						for (int i=1;i<=size;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							permanentAccessNames.add(i-1, accessAssigned);
							if(Utility.compareStringValues(accessAssigned, accessAdded))
								logger.log(LogStatus.PASS, "Access is successfully assigned to the user");
							else
								logger.log(LogStatus.FAIL, "access assignment failed");
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
								logger.log(LogStatus.INFO,"i+1."+ permanentAccessNames.get(i));
							}
						}
						else{
							logger.log(LogStatus.FAIL, "There is no changes in the accesses assigned to the user ");
						}
					}
					
					if(AGlobalComponents.tempWorkerOnboarding){
						FB_Automation_CommonMethods.searchIdentity(firstName,lastName);
						logger.log(LogStatus.PASS, "Temporary Onboardidng done is successful , user is present in IDM");
						
						/*Validating access assigned to the user*/
						ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Access Tab ");
						Utility.verifyElementPresent("//*[text()='"+access1ForTempOnboarding+"']", access1ForTempOnboarding, false);
						Utility.verifyElementPresent("//*[text()='"+access2ForTempOnboarding+"']", access2ForTempOnboarding, false);
						logger.log(LogStatus.PASS, "Accesses are successfully assigned to the user");
						
						 /* checking for asset added by badge admin at time of approval
						 */
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
						Utility.verifyElementPresent("//*[text()='"+AGlobalComponents.badgeId+"']", "badgeId", false);
						logger.log(LogStatus.PASS, "Asset is assigned to the user with asset Id : "+AGlobalComponents.badgeId);
						
						
						/*
						 * checking for Systems AMAG and HR added on the basis of Accesses
						 */
						ByAttribute.click("xpath", IdentityObjects.systemsTabLnk, "Click on Systems Tab ");
						Utility.verifyElementPresent(IdentityObjects.systemsTabLnk, "AMAG and HR System", false);
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						if(size==2)
							logger.log(LogStatus.PASS, "2 systems are successfully assigned to the user");
						else{
							logger.log(LogStatus.FAIL, "systems are not assigned to the user correctly");
							
						}
						
									
						
					}
					
					if(AGlobalComponents.tempWorkerModificationLastName){
						String modifiedLastName=newAttribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
						Utility.pause(5);
						
					//	String newLastName = ByAttribute.getText("xpath", HomeObjects.homeAccessRequestLastNameTxt, "Fetching Last name value");
						String newLastName = driver.findElement(By.xpath(HomeObjects.homeAccessRequestLastNameTxt)).getAttribute("value");
						boolean flag=true;
						for(int i=0;i<5 && flag;i++){
							if(Utility.compareStringValues(newLastName, modifiedLastName)){
								logger.log(LogStatus.PASS, "Last name modified successfully ");
								Utility.verifyElementPresent(HomeObjects.homeAccessRequestLastNameTxt, "Modified last name", false);
								flag=false;
							}
							else{
								ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
								Utility.pause(1);
								ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
								Utility.pause(5);
								newLastName = driver.findElement(By.xpath("HomeObjects.homeAccessRequestLastNameTxt")).getAttribute("value");
							}
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Last name modification failed ");
						
					}
					
					if(AGlobalComponents.tempWorkerModificationDepartment){
						String accessAdded=newAttribute;
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
						Utility.pause(5);
						
						String deptName = driver.findElement(By.xpath(".//input[contains(@id,'baseComboBoxRemote') and @placeholder='Select Department Name']")).getAttribute("value");
						
						boolean flag=true;
						for (int i=0;i<5 && flag;i++){
							deptName = driver.findElement(By.xpath(".//input[contains(@id,'baseComboBoxRemote') and @placeholder='Select Department Name']")).getAttribute("value");
							
							if(Utility.compareStringValues(deptName, departmentName)){
								logger.log(LogStatus.PASS, "Department of temp worker is modified to : "+deptName);
								Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestDepartmentDdn, "Modified department",true, false);
								flag=false;
							}
							else{
								ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
								Utility.pause(1);
								ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
								Utility.pause(5);
							}
						}
						if(flag)
							logger.log(LogStatus.FAIL, "Deaprtment name not modified");
						/*Validating access assigned to the user*/
						ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Access Tab ");
						if(Utility.verifyElementPresentReturn("//*[text()='"+accessAdded+"']", accessAdded,true, false))
								logger.log(LogStatus.PASS, "Accesses are successfully assigned to the user");
						else
							logger.log(LogStatus.FAIL, "Required access not assigned to the temp worker");
					}
					
					/* Validating for Temp worker offboarding case */
					
					if(AGlobalComponents.tempWorkerOffboardingRehireTermination){
						ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
						Utility.pause(1);
						ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
						Utility.pause(5);
						
						/*system tab*/
						ByAttribute.click("xpath", IdentityObjects.systemsTabLnk, "Navigate to systems tab");
						gettingIndexOfIDMSystemsTab();
						 for (int i=1;i<=tempWorkerSystemNames.size();i++){
								WebElement systemName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div"));
								String systemAssigned = systemName.getText();
								if(driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]/td["+systemIndex+"]/div")).size()>0){
									Utility.verifyElementPresent("(.//img[contains(@src,'redcircle')])["+i+"]", "System Status InActive", false);
									logger.log(LogStatus.PASS,"status of the system assigned to the temporary worker : "+systemAssigned+ " is inactive");
								}
								else
									logger.log(LogStatus.FAIL,"status of the system assigned to the temporary worker : "+systemAssigned+ " is not Inactive");
							}
						
						 /*access tab*/
							ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Navigate to access tab");
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
							if(driver.findElements(By.xpath("//div[text()='AST-002229']")).size()>0){
				//			if(driver.findElements(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']")).size()>0){
					//			WebElement assetStatus = driver.findElement(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div"));
								WebElement assetStatus = driver.findElement(By.xpath("//div[text()='AST-002229']//ancestor::tr//td["+assignmentStatusIndex+"]/div"));
								String assetAssignmentStatus = assetStatus.getText();
								logger.log(LogStatus.INFO,"current assignment status of asset is : "+assetAssignmentStatus);
					//			Utility.verifyElementPresent("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::tr//td["+assignmentStatusIndex+"]/div", "asset status", false);
								Utility.verifyElementPresent("//div[text()='AST-002229']//ancestor::tr//td["+assignmentStatusIndex+"]/div", "asset status", false);
								if(Utility.compareStringValues(assetAssignmentStatus, "INACTIVE"))
									logger.log(LogStatus.PASS, "asset status is inactive after offboarding");
								else
									logger.log(LogStatus.FAIL, "asset status is not deactivated");
								}
							}
							else
								logger.log(LogStatus.INFO, "asset not assigned to the user");
					
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
				
	public static void checkStatusBeforeRequestSubmission(String firstName , String lastName , String newAttribute) throws Throwable{
		
		if (unhandledException == false) {
			System.out.println("********* Check status in IDM before request submission*******************");
			logger.log(LogStatus.INFO,"******Check status in IDM before request submission************");
			try {
				
				WebElement image=null ;
				String oldPhotoSrc=null;
				
				FB_Automation_CommonMethods.searchIdentity(firstName,lastName);
		
				//existing photo capture
				if(AGlobalComponents.updatePhoto){
					image = driver.findElement(By.xpath(IdentityObjects.imageLnk));
					oldPhotoSrc = image.getAttribute("src");
					logger.log(LogStatus.INFO, "Capturing the existing photo");
					Utility.verifyElementPresent(IdentityObjects.imageLnk, "existing image", false);
					Utility.pause(5);
				}
	
				//Checking the  asset status in IDM
				if (AGlobalComponents.wellnessCheckCase){
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
				if (AGlobalComponents.requestLocationAccessOthers){
					String accessAdded=newAttribute;
					ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Access Tab ");
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
				
				
		
				//Checking the  assigned access to the user  in IDM
				if ((AGlobalComponents.contractorToPermanentEmployeeConversion)||(AGlobalComponents.requestLocationAccessOthers)){
					String accessAdded=newAttribute;
					ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Access Tab ");
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
							if(!(Utility.compareStringValues(accessAssigned, accessAdded))){
								logger.log(LogStatus.INFO, "Access to be added is not assigned to the user before");
							}
						}
					}
				}
		
		
		
				//checking the last name of user before modification
				if (AGlobalComponents.tempWorkerModificationLastName){
					Utility.verifyElementPresent(HomeObjects.homeAccessRequestLastNameTxt, "last name", false);
				//	String currentLastName = ByAttribute.getText("xpath", HomeObjects.homeAccessRequestLastNameTxt, "Fetching Last name value");
					String currentLastName = driver.findElement(By.xpath(HomeObjects.homeAccessRequestLastNameTxt)).getAttribute("value");
					logger.log(LogStatus.INFO, "Current last name of temp worker is : "+currentLastName);
				}
		
		
				/*checking the department and access assigned to the user before modification*/
				if (AGlobalComponents.tempWorkerModificationDepartment){
					String accessAdded=newAttribute;
//					WebElement departmentName = driver.findElement(By.xpath(HomeObjects.homeAccessRequestDepartmentDdn));
//					String deptName = departmentName.getText();
			
					String deptName = driver.findElement(By.xpath(".//input[contains(@id,'baseComboBoxRemote') and @placeholder='Select Department Name']")).getAttribute("value");			
			
					logger.log(LogStatus.INFO, "Current department of temp worker is : "+deptName);
			
					ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Access Tab ");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
					else{
						List<WebElement> noOfAccessRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr"));
						int size = noOfAccessRows.size();
						for (int i=1;i<=size;i++){
							WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container' ]//table["+i+"]//tr[1]//td["+accessIndex+"]"));
							String accessAssigned = accessName.getText();
							Utility.verifyElementPresent("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//table["+i+"]//tr[1]//td["+accessIndex+"]", accessAssigned, false);
							if(!(Utility.compareStringValues(accessAssigned, accessAdded))){
								logger.log(LogStatus.INFO, "Access to be added :"+accessAdded+" is not assigned to the user before");
							}
						}
					}
				}
		
				/* checking the asset , access and system status before offboarding */
				if (AGlobalComponents.tempWorkerOffboardingRehireTermination){
			
					int noOfAccess=0 , noOfSystem=0,noOfAsset=0,size=0;
					/*access tab*/
					ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Navigate to access tab");
					getIndexOfAccessHeaders();
					if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
						logger.log(LogStatus.INFO, "No Access is assigned to the user ");
					else{
						List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
						size=noOfRows.size();
						noOfAccess = size;
						if(noOfAccess>0){
							logger.log(LogStatus.INFO, "Getting the list of accesses assigned to the temporary worker");
							for (int i=1;i<=noOfAccess;i++){
								WebElement accessName = driver.findElement(By.xpath("//div[@class='x-grid-item-container']//table["+i+"]//tr[1]//td["+accessIndex+"]"));
								String accessAssigned = accessName.getText();
								tempWorkerAccessNames.add(i-1, accessAssigned);
								logger.log(LogStatus.INFO,"access assigned to the temporary worker : "+accessAssigned);
								Utility.verifyElementPresent("//*[text()='"+accessAssigned+"']", accessAssigned, false);
							}
						}
					}
			
					/*system tab*/
					ByAttribute.click("xpath", IdentityObjects.systemsTabLnk, "Navigate to systems tab");
					List<WebElement> noOfRows = driver.findElements(By.xpath("//div[@class='x-grid-item-container']//tr"));
					size = noOfRows.size();
					noOfSystem=size-noOfAccess;
					int index=noOfAccess+1;
					if(noOfSystem>0){
						logger.log(LogStatus.INFO, "Getting the list of systems assigned to the temporary worker");
						gettingIndexOfIDMSystemsTab();
						for (int i=1;i<=noOfSystem;i++){
							WebElement systemName = driver.findElement(By.xpath("(//div[@class='x-grid-item-container' ]//tr[1]/td["+systemIndex+"]/div)["+index+"]"));
							String systemAssigned = systemName.getText();
							tempWorkerSystemNames.add(i-1,systemAssigned);
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
		//					WebElement assetStatus = driver.findElement(By.xpath("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div"));
							WebElement assetStatus = driver.findElement(By.xpath("//div[text()='AST-002229']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div"));
							assetStatusBeforeOffboarding = assetStatus.getText();
							if(Utility.compareStringValues(assetStatusBeforeOffboarding, "INACTIVE"))
								logger.log(LogStatus.FAIL, "Status is already Inactive");
							else{
								logger.log(LogStatus.INFO,"current assignment status of asset is : "+assetStatusBeforeOffboarding);
					//			Utility.verifyElementPresent("//div[text()='"+AGlobalComponents.assetCode+"']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div", "asset status", false);
								Utility.verifyElementPresent("//div[text()='AST-002229']//ancestor::table["+i+"]//tr[1]//td["+assignmentStatusIndex+"]/div", "asset status", false);
								index++;
							}
						}
					}
					else
						logger.log(LogStatus.INFO, "No assets are assigned to the temporary worker");
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
	
	public static void getIndexOfAccessHeaders() throws Throwable {
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



	public static void checkRequestInMyRequestInbox(String firstName, String lastName ,String modifiedAttribute,String requestNumber) throws Throwable {
		if (unhandledException == false) {
			System.out.println("***************************** Checking the request in completed inbox *********************************");
			logger.log(LogStatus.INFO,"*************** Checking the request in completed inbox *********************************");
			try{
				String identityName = firstName +" "+ lastName;
				
				ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxExpandBtn, "Click to expand the request menu");
				Utility.pause(2);
				ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCompletedBtn, "Click on completed button");
				Utility.pause(2);
				ByAttribute.click("xpath", HomeObjects.homeInboxRequestInboxCollapseBtn, "Click to collapse the request menu");
				
				List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
				WebElement requestNo=null;
				int index=0;
				boolean reqFlag=true;
				for (int i=0;i<requestNumberElements.size() && reqFlag;i++){
					requestNo = requestNumberElements.get(i);
					reqNum = requestNo.getText();
					if(reqNum.contains(requestNumber)){
						index=i+1;
						reqFlag=false;
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
				if(Utility.compareStringValues(requestedBy, requestorName)){
					Utility.verifyElementPresent("(.//tr[1]/td[2]/div//*[text()='COMPLETED'])["+index+"]", "Completed Request status", false);
					logger.log(LogStatus.INFO ,"Request "+requestNumber+" opened successfully in  inbox");
				}
				else if(Utility.compareStringValues(requestedBy, identityName.toUpperCase())){
					logger.log(LogStatus.INFO ,"Request opened successfully in my request inbox");
				}else
					logger.log(LogStatus.FAIL ,"Incorrect request  is expanded");
	
				if(AGlobalComponents.wellnessCheckCase){
					//validate badge/asset status in my request inbox
					//write the code to check the badge status and store it in badgeStatusInRequest
					//compare badge status is different from before status hence pass
				}
				
				if(AGlobalComponents.requestLocationAccessOthers){
				
				  if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestHistoryBtn)).size()>0)
					  ByAttribute.click("xpath", HomeObjects.homeAccessRequestHistoryBtn, "Click on History button");
				  else{
					  ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
					  Utility.pause(1);
					  ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
				  }
					boolean flag=true;
					for(int i=0;i<10 && flag;i++){
						if(driver.findElements(By.xpath("//div[contains(text(),' assignment successful for user ')]")).size()>0){
							logger.log(LogStatus.PASS, "Provisioning successful");
							flag=false;
							Utility.verifyElementPresent("//div[text()='Provisioning Done for :']", "Provisioning message", false);
							Utility.pause(2);
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
				
				if(AGlobalComponents.contractorToPermanentEmployeeConversion){
				
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					if((driver.findElements(By.xpath("//*[text()='"+access1ForPermanentEmp+"']")).size()>0) && (driver.findElements(By.xpath("//*[text()='"+access2ForPermanentEmp+"']")).size()>0)){
						logger.log(LogStatus.INFO, "2 accesses :" +access1ForPermanentEmp +","+access2ForPermanentEmp+ " are assigned to the user");
					}
					else
						logger.log(LogStatus.FAIL, " As per the rule , new accesses are not added");
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestWorkFlowBtn, "CLick on workflow button");
					Utility.pause(2);
					Utility.verifyElementPresent(HomeObjects.workflowStatusHeaderInWFPopupWindow, "workflow status window", false);
					ByAttribute.click("xpath", HomeObjects.closeIconInWFStatusWindow, "close the workflow status popup window");
				}
				
				if(AGlobalComponents.tempWorkerOnboarding){
					/*
					 * checking for accesses added through pre feed rule while onboarding
					 */
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestAccessListGrid)).size()>0){
						Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
						if((driver.findElements(By.xpath("//*[text()='"+access1ForTempOnboarding+"']")).size()>0) && (driver.findElements(By.xpath("//*[text()='"+access2ForTempOnboarding+"']")).size()>0)){
							logger.log(LogStatus.PASS, "2 accesses :" +access1ForTempOnboarding +","+access2ForTempOnboarding+ " are assigned to the user");
						}
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
						if(driver.findElements(By.xpath("//div[contains(text(),' assignment successful for user ')]")).size()>0){
							logger.log(LogStatus.PASS, "Provisioning successful");
							flag=false;
							Utility.verifyElementPresent("//div[contains(text(),' assignment successful for user ')]", "Provisioning message", false);
						}else if(driver.findElements(By.xpath("//div[contains(text(),' assignment failed for user ')]")).size()>0){
								Utility.verifyElementPresent("//div[contains(text(),' assignment failed for user ')]", "Provisioning failed message", false);
								break;
						}
						else{
							ByAttribute.click("xpath", "//div[@class='x-tool-tool-el x-tool-img x-tool-close ']", "close history window pop up ");
							Utility.pause(20);
							ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
							Utility.pause(1);
							ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
						}
							
					}	
					if(flag)
						logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
					
				}
				
				if(AGlobalComponents.tempWorkerModificationDepartment){
					String accessName = modifiedAttribute;
					ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Click on comparison button to verify the modifications");
					Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='"+departmentName+"']", "modifieddepartmentName", false);
					logger.log(LogStatus.PASS,"new department name is present in comparison tab for temp worker :"+departmentName);
					
					ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
					Utility.pause(1);
					ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
					boolean flag=true;
					for(int i=0;i<10 && flag;i++){
						if(driver.findElements(By.xpath("//div[text()='Provisioning Done for Identity :']")).size()>0){
							logger.log(LogStatus.PASS, "Provisioning successful");
							flag=false;
							Utility.verifyElementPresent("//div[text()='Provisioning Done for Identity :']", "Provisioning message", false);
						}
						else{
							ByAttribute.click("xpath", "//div[@class='x-tool-tool-el x-tool-img x-tool-close ']", "close history window pop up ");
							Utility.pause(20);
							ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
							Utility.pause(1);
							ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
						}
							
					}	
					if(flag)
						logger.log(LogStatus.FAIL, "Provisioning Unsuccessful");
					ByAttribute.click("xpath", "//div[@class='x-tool-tool-el x-tool-img x-tool-close ']", "close history window pop up ");
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					if((driver.findElements(By.xpath("//*[text()='"+accessName+"']")).size()>0)){
						WebElement accessStatus = driver.findElement(By.xpath("//div[text()='"+accessName+"']//ancestor::td//following-sibling::td//label"));
						String status = accessStatus.getText();
						
						if((Utility.compareStringValues(status, "ADDED")))
							logger.log(LogStatus.INFO, "New access is added when department is modified");
						else
							logger.log(LogStatus.FAIL, "Access is not added on department change");
						
					}
				}
				
				if(AGlobalComponents.tempWorkerModificationLastName){
					ByAttribute.click("xpath", HomeObjects.ComparisonButton, "Clickon comparison button to verify the modifications");
					Utility.verifyElementPresent("//*[@class='x-grid-cell-inner ' and text()='"+modifiedAttribute+"']", "modifiedLastName", false);
					logger.log(LogStatus.PASS,"Last name is modified for temp worker");
					
					ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
					Utility.pause(1);
					ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
					boolean flag=true;
					for(int i=0;i<10 && flag;i++){
						if(driver.findElements(By.xpath("//div[text()='Provisioning Done for Identity :']")).size()>0){
							logger.log(LogStatus.PASS, "Provisioning successful");
							flag=false;
							Utility.verifyElementPresent("//div[text()='Provisioning Done for Identity :']", "Provisioning message", false);
						}
						else{
							ByAttribute.click("xpath", "//div[@class='x-tool-tool-el x-tool-img x-tool-close ']", "close history window pop up ");
							Utility.pause(20);
							ByAttribute.click("xpath", "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]", "Clickon menu");
							Utility.pause(1);
							ByAttribute.click("xpath", "(//*[text()='History'])[2]", "Clickon History");
						}
							
					}	
					
				}
				
				if(AGlobalComponents.tempWorkerOffboardingRehireTermination){
					/*
					 * checking for accesses status after offboarding rquest
					 */
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					for (int i=0;i<tempWorkerAccessNames.size();i++){
						String accessName=tempWorkerAccessNames.get(i);
						WebElement accessStatus = driver.findElement(By.xpath("//label[text()='Access List']//parent::div//parent::div//parent::div//following-sibling::div[contains(@id,'baseGrid')]//*[text()='"+accessName+"']//ancestor::td//following-sibling::td//label"));
						String status = accessStatus.getText();
						if(Utility.compareStringValues(status, "REMOVED")) 
							logger.log(LogStatus.INFO, "status of  access :" +accessName+ " is removed ");
						else
							logger.log(LogStatus.FAIL, "Access status is not removed");
						
					}
					
					/*
					 * checking for systems list after offboarding request
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestSystemListGrid, "SystemList Grid", true,false);
					for (int i=0;i<tempWorkerSystemNames.size();i++){
						String systemName=tempWorkerSystemNames.get(i);
						WebElement systemStatus = driver.findElement(By.xpath("//label[text()='System List']//parent::div//parent::div//parent::div//following-sibling::div[contains(@id,'baseGrid')]//*[text()='"+systemName+"']//ancestor::td//following-sibling::td//label"));
						String status = systemStatus.getText();
						if(Utility.compareStringValues(status, "LOCK"))
							logger.log(LogStatus.INFO, "status of  system :" +systemName +" is LOCKED ");
						else
							logger.log(LogStatus.FAIL, "Systems status is not LOCKED");
						
					}
					
					/*
					 * checking for Asset status after offboarding request
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestBadgeListGrid, "Badge List Grid", true,false);
					int statusIndex=getBadgeListGridHeadersInRequest();
					WebElement badgeStatus = driver.findElement(By.xpath("//label[text()='Badge List']//parent::div//parent::div//parent::div//following-sibling::div[contains(@id,'baseGrid')]//td["+statusIndex+"]//label"));
					String status = badgeStatus.getText();
					if(Utility.compareStringValues(status, "DEACTIVATE"))
						logger.log(LogStatus.INFO, "status of  asset is DEACTIVATE ");
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
							if(driver.findElements(By.xpath("//div[contains(text(),' deactivated successfully ')]")).size()>0){
								logger.log(LogStatus.PASS, "Provisioning successful");
								flag=false;
								Utility.verifyElementPresent("//div[contains(text(),' deactivated successfully ')]", "Provisioning message", false);
								Utility.pause(2);
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
					ByAttribute.click("xpath","//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'United States of America')]" , "Select the country");
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
					Utility.pause(60);
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



	public static void tempWorkerModification(String firstName,String lastName) throws Throwable {
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
					
					logger.log(LogStatus.INFO, "Search the user to be modified");
					WebElement identity = driver.findElement(By.xpath(".//*[contains(@class,'x-placeholder-label') and text()='Search Identity or User']"));
					Actions action = new Actions(driver);
					action.sendKeys(identity, firstName);
					action.build().perform();
					Utility.pause(2);
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					if(AGlobalComponents.tempWorkerModificationLastName){
						ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestLastNameTxt, lastName, "Enter the value of modified last name ");
						Utility.pause(2);
						
					}
					if(AGlobalComponents.tempWorkerModificationDepartment){
						ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestDepartmentDdn, departmentName, "Enter the value of new department ");
						Utility.pause(2);
						ByAttribute.click("xpath","//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'"+departmentName+"')]" , "Select the department");
						Utility.pause(2);
					}
					
					ByAttribute.click("xpath",HomeObjects.submitBtn, "Click on submit button");
					Utility.pause(5);
					while((driver.findElements(By.xpath(HomeObjects.submitBtn))).size()>0){
					     Utility.pause(5);
					}
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
				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) 
				{
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					ByAttribute.click("xpath", HomeObjects.otherRequestsLnk, "Click on Other Requests Pod");
					Utility.pause(2);
					ByAttribute.click("xpath", HomeObjects.selectRequestType,"Enter the request type");
					ByAttribute.setText("xpath", HomeObjects.selectRequestType,"Employment Type Conversion", "Enter the request type");
					Utility.pause(2);
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
		


	public static void modifyIdentity(String firstName) throws Throwable {
		
		
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
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
					Utility.pause(5);
				}
				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) 
				{
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					ByAttribute.click("xpath", HomeObjects.otherRequestsLnk, "Click on Other Requests Pod");
					Utility.pause(2);
					ByAttribute.click("xpath", HomeObjects.selectRequestType,"Enter the request type");
					ByAttribute.setText("xpath", HomeObjects.selectRequestType,"Modify Identity", "Enter the request type");
					Utility.pause(2);
					Actions action = new Actions(driver);
					action.sendKeys(Keys.TAB);
					action.sendKeys(firstName);
					action.build().perform();
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					String photoFilePath=System.getProperty("user.dir") + "\\Browser_Files\\Applicant_Photo.jpg";
					ByAttribute.click("xpath",HomeObjects.uploadImgBtn, "Click on upload button to update the photo");
					Thread.sleep(1000);
									
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
		            ByAttribute.click("xpath",HomeObjects.cropAndSaveBtn, "Click on Crop and Save button");
		            Utility.pause(4);
		            if(driver.findElements(By.xpath("//*[@class='x-img x-box-item x-img-default']")).size()>0){
		            	logger.log(LogStatus.PASS, "Image uploaded successfully");
		            	Utility.verifyElementPresent(HomeObjects.deleteAttachmentBtnLnk, "Uploaded Image", false);
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
	
	}



	public static void revertIdentityChanges(String firstName, String lastName) throws Throwable {
	if (unhandledException == false) {
		System.out.println("**************");
		logger.log(LogStatus.INFO,"******Reverting back the changes done ******");
		try {	
			if(AGlobalComponents.contractorToPermanentEmployeeConversion){
				ByAttribute.click("xpath", IdentityObjects.profileTabLnk, "Navigate to profile tab" );
				WebElement empType = driver.findElement(By.xpath(IdentityObjects.employeeTypeLnk));
				empType.clear();
				ByAttribute.setText("xpath", IdentityObjects.employeeTypeLnk, "Contractor", "Setting emp type of identity as contractor");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.SaveBtn, "Save the changes done to Identity");
				Utility.pause(5);
			}
			if(AGlobalComponents.updatePhoto){
				ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse Hover on Home Tab Link");
				Thread.sleep(1000);
				ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
				Utility.pause(5);
				if (driver.findElements(By.xpath(HomeObjects.homeAccessRequestCreateBtn)).size() > 0) 
				{
					System.out.println("Access Request Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Access Request Page Loaded Successfully");
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestOthersRdb, "Click on Others Radio Button");
					ByAttribute.click("xpath", HomeObjects.otherRequestsLnk, "Click on Other Requests Pod");
					Utility.pause(2);
					ByAttribute.click("xpath", HomeObjects.selectRequestType,"Enter the request type");
					ByAttribute.setText("xpath", HomeObjects.selectRequestType,"Modify Identity", "Enter the request type");
					Utility.pause(2);
					Actions action = new Actions(driver);
					action.sendKeys(Keys.TAB);
					action.sendKeys(firstName);
					action.build().perform();
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					ByAttribute.click("xpath",HomeObjects.deleteAttachmentBtnLnk , "Click on delete button to remove the photo");
					Utility.pause(5);
				
					ByAttribute.click("xpath",HomeObjects.submitBtn, "Click on submit button");
		            Utility.pause(5);
				}
				
			}
		} catch (Exception e) {
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
			Utility.recoveryScenario(nameofCurrMethod, e);
		}

	}
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
						
				if(driver.findElements(By.xpath(HomeObjects.homeOpenRequestsLnk)).size()>0){
					ByAttribute.click("xpath", HomeObjects.homeOpenRequestsLnk, "Click on Open Requests link");
					Utility.pause(5);
					if(driver.findElements(By.xpath("//*[@class='x-component x-component-activityLabeltext' and text()='"+requestNumber+"']")).size()>0){
						logger.log(LogStatus.INFO, "Request  is  present in approvers inbox");
						requestPresent=true;
					}
					else{
						ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "mouse hover on Home tab");
						ByAttribute.click("xpath", HomeObjects.homeMyRequestsLnk, "Click on My Requests Link to open the request");
						Utility.pause(5);
						WebElement reqNo= driver.findElement(By.xpath(".//*[contains(@class,'x-grid-cell-inner') and contains( text(),'"+requestNumber+"')]"));
						if(Utility.verifyElementPresentReturn(".//*[contains(@class,'x-grid-cell-inner') and contains( text(),'"+requestNumber+"')]", "Request",true, false)){
							requestPresent=true;
							Actions action = new Actions(driver);
							action.doubleClick(reqNo).build().perform();
							Utility.pause(5);
						}
						else
							logger.log(LogStatus.FAIL, "request number not present in approvers inbox");
					}
				}
				if(Utility.compareStringValues("manager", WfActor)){
					System.out.println("Approving the temp onboarding request by : "+WfActor);
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
					}else{
						System.out.println("Approve button not visible");
						logger.log(LogStatus.FAIL, "Approve button not visible");
					}
					
						
				}
				if(Utility.compareStringValues("areaAdmin", WfActor)){
					System.out.println("Approving the location access request by : "+WfActor);
					logger.log(LogStatus.INFO,"******Approving the location access request by ******: "+WfActor);
					Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+accessName+"']", "Access Name Matching with Location Selected by Manager", false);
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
					}else{
						System.out.println("Approve button not visible");
						logger.log(LogStatus.FAIL, "Approve button not visible");
					}
					
						
				}
				if(Utility.compareStringValues("badgeAdmin", WfActor)){
					System.out.println("Approving the  request by : "+WfActor);
					logger.log(LogStatus.INFO,"******Approving the  request by  ******: "+WfActor);
										
					/*Giving badge to the user */
					
					ByAttribute.click("xpath",HomeObjects.homeAccessRequestSelectBadgeDDn,"Enter the  asset created to the user");
					Utility.pause(2);

					Actions action = new Actions(driver);
					action.sendKeys(AGlobalComponents.assetName).build().perform();
					Utility.pause(2);
					ByAttribute.click("xpath","//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'"+AGlobalComponents.assetName+"')]" , "Select the asset name");
					Utility.pause(2);
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
					}else{
						System.out.println("Approve button not visible");
						logger.log(LogStatus.FAIL, "Approve button not visible");
					}
					ByAttribute.click("xpath",HomeObjects.homeAccessRequestYesButtonOnPopUpWindow, "Click yes button to save the changes done in badge");
					Utility.pause(15);
					logger.log(LogStatus.PASS,"Request successfully approved by badge admin");
						
				}
					
				
			} catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}

		}
		
	}
	
	
	
	public static void checkRequestInManagerInbox() throws Throwable {
		if (unhandledException == false) {
			System.out.println("***************************** Checking the status in the Request *********************************");
			logger.log(LogStatus.INFO,"*************** Checking the status in the Request *********************************");
			try{
				if(AGlobalComponents.EmpOnboardingthroughHRDb){
					if(driver.findElements(By.xpath(HomeObjects.homeOpenRequestsLnk)).size()>0){
						ByAttribute.mouseHover("xpath",HomeObjects.homeTabBtn,"Mouse hover on Home tab to open My Requests");
						ByAttribute.click("xpath",HomeObjects.homeInboxLnk,"click on Inbox");
						Utility.pause(5);
					}
					List<WebElement> requestNumberElements = driver.findElements(By.xpath("//div[@class='x-grid-cell-inner ']/descendant::div"));
					WebElement requestNo=requestNumberElements.get(0);
					Actions action = new Actions(driver);
					action.doubleClick(requestNo);
					action.build().perform();
					Utility.pause(5);
					reqNum=requestNo.getText();
					
					WebElement requestBy = driver.findElement(By.xpath("//div[text()='Request By']//parent::div//label"));
					String requesterName= requestBy.getText();
					if(Utility.compareStringValues(requesterName, "SYSTEM")&& AGlobalComponents.EmpOnboardingthroughHRDb)
						logger.log(LogStatus.INFO ,"Request opened successfully in my request inbox");
					else
						logger.log(LogStatus.FAIL ,"Incorrect request  is expanded");
	
					/*
					 * checking for accesses added through pre feed rule while onboarding
					 */
					
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestAccessListGrid, "AccessList Grid",true, false);
					if((driver.findElements(By.xpath("//*[text()='"+access1ForEmpOnboarding+"']")).size()>0) && (driver.findElements(By.xpath("//*[text()='"+access2ForEmpOnboarding+"']")).size()>0)){
						logger.log(LogStatus.INFO, "2 accesses :" +access1ForEmpOnboarding +","+access2ForEmpOnboarding+ " are assigned to the user");
					}
					
					/*
					 * checking for badge added through pre feed rule while onboarding
					 */
					Utility.verifyElementPresentByScrollView(HomeObjects.homeAccessRequestBadgeListGrid, "BadgeList Grid", true,false);
					WebElement activationDate = driver.findElement(By.xpath("//span[normalize-space(text())='Activation Date']//ancestor::div[contains(@id,'headercontainer')]//following-sibling::div//td[1]"));
					String activationDateValue = activationDate.getText();
					if(Utility.checkIfStringIsNotNull(activationDateValue)){
						logger.log(LogStatus.INFO, "Badge is assigned to the user with activation date as: "+ activationDateValue);
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
			
					FB_Automation_CommonMethods.searchIdentity(firstName,lastName);
					logger.log(LogStatus.PASS, "Employee Onboardidng done is successful , user is present in IDM");
			
					/*Validating access assigned to the user*/
					ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Access Tab ");
					Utility.verifyElementPresent("//*[text()='"+access1ForEmpOnboarding+"']", access1ForEmpOnboarding, false);
					Utility.verifyElementPresent("//*[text()='"+access2ForEmpOnboarding+"']", access2ForEmpOnboarding, false);
					logger.log(LogStatus.PASS, "Accesses are successfully assigned to the user");
			
					/* checking for asset added by badge admin at time of approval
					 */
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
					Utility.verifyElementPresent("//*[text()='"+AGlobalComponents.badgeId+"']", "badgeId", false);
					logger.log(LogStatus.PASS, "Asset is assigned to the user with asset Id : "+AGlobalComponents.badgeId);
					
					/*
					 * checking for Systems AMAG and HR added on the basis of Accesses
					 */
					ByAttribute.click("xpath", IdentityObjects.systemsTabLnk, "Click on Systems Tab ");
					Utility.verifyElementPresent(IdentityObjects.systemsTabLnk, "AMAG and HR System", false);
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
				ByAttribute.mouseHover("xpath", HomeObjects.homeTabBtn, "Mouse hover on Home Tab");
				ByAttribute.click("xpath",HomeObjects.homeDashboardLnk, "Click on Dashboard");
				Utility.pause(4);
				
				if(driver.findElements(By.xpath(HomeObjects.homeOpenRequestsLnk)).size()>0){
					ByAttribute.click("xpath", HomeObjects.homeOpenRequestsLnk, "Click on Open Requests Link to open the request");
					Utility.pause(5);
					Utility.verifyElementPresent("//div[contains(@class,'x-component') and text()='"+reqNum+"']", "Request", false);
					
					if(Utility.compareStringValues("manager", WfActor)){
						WfActor="Manager";
						System.out.println("Approving the Employee onboarding request by : "+WfActor);
						logger.log(LogStatus.INFO,"******Approving the Employee onboarding request by manager ******: "+WfActor);
						Utility.verifyElementPresent("//div[@class='x-box-target']//*[text()='Stage: "+WfActor+"']", WfActor+" Stage", false);
						ByAttribute.click("xpath",HomeObjects.homeAccessRequestApproveButton, "Click approve button ");
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
						
						ByAttribute.click("xpath",HomeObjects.homeAccessRequestSelectBadgeDDn,"Enter the  asset created to the user");
						Actions action = new Actions(driver);
						action.sendKeys(AGlobalComponents.badgeName).build().perform();
						Utility.pause(2);
						ByAttribute.click("xpath","//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'"+AGlobalComponents.badgeName+"')]" , "Select the asset name");
						Utility.pause(2);
						ByAttribute.click("xpath",HomeObjects.homeAccessRequestBadgeListGrid,"Asset assigned");
						Utility.verifyElementPresent("//*[text()='"+AGlobalComponents.badgeId+"']", AGlobalComponents.badgeId, false);
						ByAttribute.click("xpath",HomeObjects.homeAccessRequestApproveButton, "Click approve button By badge admin");
						Utility.pause(2);
						ByAttribute.click("xpath",HomeObjects.homeAccessRequestYesButtonOnPopUpWindow, "Click yes button to save the changes done in badge");
						Utility.pause(5);
						logger.log(LogStatus.PASS,"Request successfully approved by badge admin");		
					}	
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
		
		String badgeId;
		String badgeName="";
		if (unhandledException == false) {
			System.out.println("**************************** createNewAsset ********************************");
			logger.log(LogStatus.INFO,"**************************** createNewAsset ********************************");
			try {
				
				if(driver.findElements(By.xpath(AssetObjects.cardHoldersAndAssetsTabBtn)).size()>0){
					ByAttribute.mouseHover("xpath", AssetObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Asset Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", AssetObjects.manageAssetsLnk, "Click on Manage Asset Link");
					Utility.pause(5);
				}
				else{
					ByAttribute.mouseHover("xpath", AssetObjects.assetTabBtn, "Mouse Hover on Asset Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", AssetObjects.assetManageAssetLnk, "Click on Manage Asset Link");
					Utility.pause(5);
				}
				if ((driver.findElements(By.xpath(".//label[text()='Manage Asset']")).size() > 0)||(driver.findElements(By.xpath(".//label[text()='Manage Assets']")).size() > 0)) 
				{
					System.out.println("Manage Asset Page Loaded Successfully");
					logger.log(LogStatus.PASS, "Manage Asset Page Loaded Successfully");
					
					ByAttribute.click("xpath", AssetObjects.assetCreateAssetBtn, "Click Create Button");
					Thread.sleep(1000);
					ByAttribute.clearSetText("xpath", AssetObjects.assetTypeTxt, badgeType, "Enter Badge Type: "+badgeType);
					Thread.sleep(500);
					ByAttribute.click("xpath", ".//li[@role='option' and text()='"+badgeType+"']", "Select "+badgeType+" Option from List");
					
					ByAttribute.clearSetText("xpath", AssetObjects.assetSubTypeTxt, badgeSubType, "Enter Bdge Sub Type: "+badgeSubType);
					Thread.sleep(500);
					ByAttribute.click("xpath", ".//li[@role='option' and text()='"+badgeSubType+"']", "Select "+badgeSubType+" Option from List");
					
					badgeName="AUTO-"+Utility.getRandomNumber(5);
					ByAttribute.clearSetText("xpath", AssetObjects.assetSerialNumberTxt, badgeName, "Enter Serial Number");
					Thread.sleep(1000);
					
					switch(system){
					
					case "AMAG":
						badgeId = "8"+Utility.getRandomNumber(11);
						break;
						
					case "AMAG1":
						badgeId = "8"+Utility.getRandomNumber(11);
						break;
						
					case "CCURE 9000":
						badgeId = "9"+Utility.getRandomNumber(4);
						break;
						
					case "Lenel":
						badgeId = "1"+Utility.getRandomNumber(11);
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
					ByAttribute.click("xpath", ".//li[@role='option' and text()='"+system+"']", "Select "+system+" Option from List");
					Thread.sleep(1000);
										
					ByAttribute.clearSetText("xpath", AssetObjects.assetSourceIdTxt, badgeId, "Enter Source Id");
					Thread.sleep(1000);
					
					ByAttribute.click("xpath", AssetObjects.assetSaveBtn, "Click Save Button");
					Thread.sleep(3000);

					if(driver.findElements(By.xpath(AssetObjects.cardHoldersAndAssetsTabBtn)).size()>0){
						ByAttribute.mouseHover("xpath", AssetObjects.cardHoldersAndAssetsTabBtn, "Mouse Hover on Asset Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", AssetObjects.manageAssetsLnk, "Click on Manage Asset Link");
						Utility.pause(5);
					}
					else{
						ByAttribute.mouseHover("xpath", AssetObjects.assetTabBtn, "Mouse Hover on Asset Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", AssetObjects.assetManageAssetLnk, "Click on Manage Asset Link");
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
					
					logger.log(LogStatus.INFO, "Search the user to be modified");
					WebElement identity = driver.findElement(By.xpath(".//*[contains(@class,'x-placeholder-label') and text()='Search Identity or User']"));
					Actions action = new Actions(driver);
					action.sendKeys(identity, firstName);
					action.build().perform();
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					ByAttribute.setText("xpath", HomeObjects.homeAccessRequestSelectTerminationReasonTxt, terminationReason, "Enter the termination Reason ");
					Utility.pause(2);
					ByAttribute.click("xpath","//div[@class='x-boundlist-list-ct x-unselectable x-scroller']//li[contains(text(),'Misconduct')]" , "Select termination reason from dropdown ");
					ByAttribute.setText("xpath", HomeObjects.homeAccessRequestValidToDate, currentDate, "Enter the date on which offboarding will be done ");
					Utility.pause(2);
					
					
					ByAttribute.click("xpath",HomeObjects.submitBtn, "Click on submit button");
		            Utility.pause(20);
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

	public static String requestNewLocation(String requestType, String location, String access, String locCity, String locBuilding,String firstName,String lastName) throws Throwable {

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
					Thread.sleep(1000);
					
					logger.log(LogStatus.INFO, "Search the user to be modified");
					WebElement identity = driver.findElement(By.xpath(".//*[contains(@class,'x-placeholder-label') and text()='Search Identity or User']"));
					Actions action = new Actions(driver);
					action.sendKeys(identity, firstName);
					action.build().perform();
					Utility.pause(2);;
					ByAttribute.click("xpath","//div[@class='idmlistitem']//span[contains(text(),'"+firstName+"')]" , "Select the user name ");
					Utility.pause(5);
					
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestCreateNextBtn, "Click Next Button");
					Thread.sleep(1000);
					if(driver.findElements(By.xpath(HomeObjects.homeAccessRequestSearchAccessTxt)).size() > 0)
						{
							System.out.println("Navigation to Access Page Successful");
							logger.log(LogStatus.INFO, "Navigation to Access Page Successful");
							
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchAccessTxt, access, "Search Access in Location");
							
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchAccessTxt, "", "Clear Search Filter");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//h4[@data-qtip='"+access+"']//ancestor::div[@class='innerWidget']", "Add Location");
							Utility.verifyElementPresent(".//h4[@data-qtip='"+access+"']", "Searched Access", false);
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestReviewTab, "Click on Review Tab");
							Utility.verifyElementPresent(".//tbody//td[2]/div[text()='"+access+"']", "Added Access: "+access, false);
							
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationSubmitBtn, "Click Submit Button");
							Thread.sleep(2000);
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationReasonTxt, "General Access Required", "Enter Reason Code");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//li[text()='General Access Required']", "Select Reason Option");
							Thread.sleep(1000);
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationGroupNameTxt, "mca-crar-group1", "Enter Group Name");
							Thread.sleep(1000);
							ByAttribute.click("xpath", ".//li[text()='mca-crar-group1']", "Select Group Name");
							Thread.sleep(1000);
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestAddLocationJustificationTxt, "Automation Test", "Enter Business Justification");
							Thread.sleep(1000);
							ByAttribute.click("xpath", HomeObjects.homeAccessRequestAddLocationConfirmBtn, "Click Confirm Button");
							Thread.sleep(10000);
							
							ByAttribute.click("xpath", HomeObjects.homeMyDashboardBtn, "Click on My Dashboard");
							Thread.sleep(2000);
							ByAttribute.click("xpath", HomeObjects.homeOpenRequestsLnk, "Click on Open Requests");
							Thread.sleep(5000);
							
							String identityName = firstName +" "+ lastName;
							WebElement requestBy = driver.findElement(By.xpath("//div[text()='Request By']//parent::div//label"));
							WebElement requestFor = driver.findElement(By.xpath("//div[normalize-space(text())='Request For']//parent::div//span[@class='tagorange identityBox']"));
							String requestedFor = requestFor.getText();
							String requestedBy= requestBy.getText();
							if((Utility.compareStringValues(requestedBy, requestorName))  && (Utility.compareStringValues(requestedFor, identityName.toUpperCase()))){
								logger.log(LogStatus.INFO ,"Request opened successfully ");
							}else
								logger.log(LogStatus.FAIL ,"Incorrect request  is expanded");
							
							WebElement requestNumber = driver.findElement(By.xpath("//div[@class='x-component x-component-activitySubtext' and text()='Request Number']//following-sibling::div"));
							reqNum=requestNumber.getText();
							
							AGlobalComponents.RequestSubmit=true;
//							List<WebElement> requestNumberElements = driver.findElements(By.xpath(".//tr[1]/td[2]/div"));
//							WebElement latestRequestNumber = requestNumberElements.get(0);
//							
//							reqNum = latestRequestNumber.getText();
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
					ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
					Utility.pause(5);
				}

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
						Utility.verifyElementPresent("(.//img[contains(@src,'redcircle')])[1]", "Card Status InActive", false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", "(.//img[contains(@src,'redcircle')])[1]//ancestor::td[@role='gridcell']//following-sibling::td//div[@role='button']", "Click Activate Button");
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//img[contains(@src,'greencircle')]", "Card Status Changed to Active", false);
					}else{
						Utility.verifyElementPresent("(.//img[contains(@src,'greencircle')])[1]", "Card Status Active", false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", "(.//img[contains(@src,'greencircle')])[1]//ancestor::td[@role='gridcell']//following-sibling::td//div[@role='button']", "Click Deactivate Button");
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//img[contains(@src,'redcircle')]", "Card Status Changed to InActive", false);
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
					Thread.sleep(20000);
					
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
					Utility.pause(20);
					
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
	* <h1>activatedeactivateBadge</h1>
	* This is Method to activate badge by badge admin
	* @author Vishal Gupta
	* @modified
	* @version 1.0
	* @since 2-15-2021
	* @param String requestFor
	* @return String
	**/

	public static void activatedeactivateBadge(String firstName, String lastName) throws Throwable {

	if (unhandledException == false) {
	System.out.println("*************************** activate badge *******************************");
	logger.log(LogStatus.INFO,"*************************** activate badge *******************************");
	try {
	FB_Automation_CommonMethods.searchIdentity(firstName,lastName);

	getIndexOfIDMAssetsHeaders();
	ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
	if(driver.findElements(By.xpath(IdentityObjects.emptyGrid)).size()>0)
	logger.log(LogStatus.INFO, "No Badge is assigned to the user ");
	else{
	WebElement assetStatus = driver.findElement(By.xpath("//tr//td["+assignmentStatusIndex+"]//div[@class='x-grid-cell-inner ']//label"));
	oldAssetStatus=assetStatus.getText();
	Utility.verifyElementPresent("//tr//td["+assignmentStatusIndex+"]//div[@class='x-grid-cell-inner ']//label", "Asset Assignemnt status", false);

	logger.log(LogStatus.INFO, "Asset Assignment status before is : " +oldAssetStatus);
	Utility.pause(3);
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
	ByAttribute.click("xpath",IdentityObjects.actionBtn, "clicking on activate button");
	ByAttribute.click("xpath", IdentityObjects.SaveBtn, "clicking on save button");
	Utility.pause(60);
	ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
	Utility.pause(1);
	ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
	Utility.pause(10);
	ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
	ByAttribute.click("xpath", IdentityObjects.reloadOptionMenu, "Click on menu to reload");
	Utility.pause(1);
	ByAttribute.click("xpath", IdentityObjects.reloadOption, "Click on reload ");
	Utility.pause(10);
	ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");

	WebElement assetStatusNew = driver.findElement(By.xpath("(//div[@class='x-grid-cell-inner ']//label)[2]"));
	newAssetStatus=assetStatusNew.getText();
	Utility.verifyElementPresent("(//div[@class='x-grid-cell-inner ']//label)[2]", "Asset Assignemnt status", false);
	logger.log(LogStatus.INFO, "Current asset Assignment status is : " +newAssetStatus);
	logger.log(LogStatus.PASS, "Current asset Assignment status in IDM is : " +newAssetStatus);
	WebElement card = driver.findElement(By.xpath("//tbody/tr/td[7]/div"));
	String cardNumber = card.getText();
	String status = DBValidations.checkAssetStatusInAMAG(cardNumber);
	logger.log(LogStatus.PASS, "Inactive status in AMAG is : " +status);
	}

	}
	catch (Exception e) {
	String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
	Utility.recoveryScenario(nameofCurrMethod, e);
	}

	}

	}
	
	/**
	* <h1>check Provisioning logs</h1>
	* This is Method to check provisioning logs
	* @author Vishal Gupta
	* @modified
	* @version 1.0
	* @since 2-15-2021
	**/

	public static void checkProvisioningLogs(String msg) throws Throwable {

	if (unhandledException == false) {
	System.out.println("*************************** checking Provisioning logs *******************************");
	logger.log(LogStatus.INFO,"*************************** checking Provisioning Logs *******************************");
	try {
	ByAttribute.mouseHover("xpath", AdminObjects.adminTabBtn, "Mouse Hover on Admin Tab Link");
	Thread.sleep(1000);
	ByAttribute.click("xpath", AdminObjects.adminProvMonitorLnk, "Click on Provisioning Monitor Link");
	Utility.pause(10);

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
	 * <h1>idmUserValidation</h1> 
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
					
					ByAttribute.click("xpath", ".//*[@data-ref='btnInnerEl' and text()='Assets']", "Click Assets Tab");
					Thread.sleep(1000);
					
					if(driver.findElements(By.xpath(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']")).size()>0)
					{
						Utility.verifyElementPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']",  "Temporary Badge is Available with User: "+userId, false);
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//tbody//div[@role='button' and @data-qtip='Return Asset']", "Click Return Asset Button");
						Thread.sleep(1000);
						Utility.verifyElementPresent(".//div[contains(@id,'header-title-textEl') and text()='Do you want to return badge?']", "Do you want to return badge? Dialog box", false);
						ByAttribute.click("xpath", ".//*[contains(@id,'baseCheckBox') and text()='Is unlock Permanent badge:']", "Check Is unlock Permanent badge Checkbox");
						Thread.sleep(1000);
						ByAttribute.click("xpath", ".//div[contains(@id,'header-title-textEl') and text()='Do you want to return badge?']//ancestor::div[@class='x-window x-layer x-window-default x-closable x-window-closable x-window-default-closable x-border-box']//span[contains(@id,'btnInnerEl') and text()='Confirm']", "Click Confirm Button");
						Thread.sleep(10000);
//						Utility.verifyElementNotPresent(".//tbody//div[contains(@class,'x-grid-cell-inner') and text()='Temporary Badge']", "Assigned Temporary Badge", true);
						ByAttribute.click("xpath", ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Save']", "Click Save Button");
						Thread.sleep(10000);
						
						//IDM VALIDATION
						ByAttribute.mouseHover("xpath", IdentityObjects.idmTabBtn, "Mouse Hover on IDM Tab Link");
						Thread.sleep(1000);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityLnk, "Click on Manage Identity Link");
						Utility.pause(5);
						
						ByAttribute.clearSetText("xpath", IdentityObjects.idmManageIdentitySearchFieldTxt, userId, "Enter User ID in Search field");
						Thread.sleep(3000);
						Utility.verifyElementPresent(".//div[@class='x-grid-cell-inner ' and text()='"+userId+"']", "User Available in IDM", false);
						
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
	* <h1>createReplaceBadgeRequest</h1>
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
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
						Utility.pause(5);
					}
				
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
						ByAttribute.click("xpath", HomeObjects.homeAccessRequestLnk, "Click on Access Request Link");
						Utility.pause(5);
					}
				
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
	
	
	
}

