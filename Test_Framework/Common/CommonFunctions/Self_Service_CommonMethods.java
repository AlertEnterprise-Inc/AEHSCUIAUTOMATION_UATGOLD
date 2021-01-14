package CommonFunctions;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.ByAttribute;
import CommonClassReusables.Utility;
import ObjectRepository.AdminObjects;
import ObjectRepository.HomeObjects;
import ObjectRepository.IdentityObjects;


public class Self_Service_CommonMethods extends BrowserSelection{
	
	
	
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
							
							ByAttribute.clearSetText("xpath", HomeObjects.homeAccessRequestSearchAccessTxt, access, "Search Access in Location");
							Utility.verifyElementPresent(".//h4[@data-qtip='"+access+"']", "Searched Access", false);
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
					Utility.verifyElementPresent(".//td[@style='display:flex;justify-content:space-between;']/div[text()='"+reqNum+"']//following-sibling::div/div[@class='taggrey' and text()='Access Owner']", "Request Number: "+reqNum+" Workflow Stage Access Owner", false);
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
					
					
//					Provisioning Monitor Validation
					ByAttribute.mouseHover("xpath", AdminObjects.adminTabBtn, "Mouse Hover on Admin Tab Link");
					Thread.sleep(1000);
					ByAttribute.click("xpath", AdminObjects.adminProvMonitorLnk, "Click on Provisioning Monitor Link");
					Utility.pause(10);
					
					for(int i=0;i<20;i++)
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
					Utility.verifyElementPresent(".//tbody/tr/td[2]/div[@class='x-grid-cell-inner ' and text()='"+locationName+"']", "Access: "+locationName+" is Added with Provided Dates", false);
								
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
						
						Utility.verifyElementPresent(".//label[text()='My Requests']//following-sibling::label[text()='"+requestNumber+"']", "Request Page for Request Number: "+requestNumber, false);
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

	

}
