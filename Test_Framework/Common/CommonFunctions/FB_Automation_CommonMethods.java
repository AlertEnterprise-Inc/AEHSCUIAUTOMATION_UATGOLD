package CommonFunctions;

import static org.testng.Assert.assertEquals;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import CommonClassReusables.FileOperations;
import CommonClassReusables.MsSql;

import CommonClassReusables.ReadDataFromPropertiesFile;
import CommonClassReusables.TestDataEngine;
import CommonClassReusables.TestDataInterface;
import CommonClassReusables.Utility;
import ObjectRepository.AccessObjects;
import ObjectRepository.HomeObjects;
import ObjectRepository.IdentityObjects;
import ObjectRepository.ReconObjects;

public class FB_Automation_CommonMethods extends BrowserSelection{
	
	public static String createIdentityTestDataDirectory= "Test_Data/IdentityManagement";
	public static String ManagerCasesTestDataDirectory= "Test_Data/ManagerLoginScenarios";
	public static String reconTestDataDirectory= "Test_Data/Recon";
	public static String accessReviewTestDataDirectory= "Test_Data/AccessReview";
	public static String photoFilePath ;
	public static ArrayList<String> identityCodes = new ArrayList<String>();
	public static ArrayList<String> jobNames = new ArrayList<String>();
	private static boolean dupIdentity=false;
	private static String identityCode = null;
	private static String entityType= null;
	private static String activeReconRecords = null;
	private static int assetCodeIndex,badgeValidFromIndex, badgeValidToIndex,badgeTypeIndex,index=0;

	
	
	
	
	/**
	 * 
	 * Method to add Recon job in Recon Setup 
	 *  
	 * Author : Monika Mehta,
	 * 
	 * 
	 **/
	public static void setUpReconJob() throws Throwable
	{

		if(unhandledException==false)
		{
			logger.log(LogStatus.INFO ,  "Recon Job Execution");	
			System.out.println("***************************** Execute Recon Job *********************************");
			try
			{
				String reconDataFile = null;
				if(AGlobalComponents.CCUREUserRecon)
					reconDataFile = reconTestDataDirectory + "/CCUREUserRecon.csv";
				else if(AGlobalComponents.CCURERoleRecon)
					reconDataFile = reconTestDataDirectory + "/CCURERoleRecon.csv";
				else if(AGlobalComponents.DBUserRecon)
					reconDataFile = reconTestDataDirectory + "/DBUserRecon.csv";
				ArrayList<String> connectorNames=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "ConnectorName");
				ArrayList<String> entityTypes=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "EntityType");
				ArrayList<String> scheduleTypes=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "ScheduleType");
				ArrayList<String> preFeedRule=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "PreFeedRule");
				ArrayList<String> createRequest=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "CreateRequest");
				
				for(int i=0;i<connectorNames.size();i++) {	
					entityType=entityTypes.get(i);
					logger.log(LogStatus.INFO, "Creating recon job in recon set up : "+(i+1));
					initiateReconJob(connectorNames.get(i),scheduleTypes.get(i),preFeedRule.get(i),createRequest.get(i));
					if(connectorNames.get(i).equalsIgnoreCase("CCURE 9000")&&(entityType.equalsIgnoreCase("User Data")))
						checkCCUREUserDataJobInReconMonitor(connectorNames.get(i));	
						else {
							checkJobInReconMonitor();	
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

	private static void checkCCUREUserDataJobInReconMonitor(String connectorName) throws Throwable {

		if(unhandledException==false)
		{
				
			System.out.println("******************Checking Recon job in recon monitor************************");	
			try{
				
				ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
				Utility.pause(3);
				ByAttribute.click("xpath", ReconObjects.reconMonitorLnk,"click on Recon Monitor");
				Utility.pause(20);
				
				if(AGlobalComponents.trialReconJob){
					ByAttribute.click("xpath", ReconObjects.trialJobRadioButton, "click on trial job radio button");
					logger.log(LogStatus.INFO, "Navigated to Trial tab on Recon monitor screen");
					Utility.pause(5);
				}

				/*
				 * Filter the records in Recon monitor on basis of entity type
				 */
				
				Actions action = new Actions (driver);
				boolean existingFilterflg = true;
				try{
					ByAttribute.click("xpath", ReconObjects.MinusIconToRemoveExistingFilter, "Remove existing filter ");
					Utility.pause(5);
					ByAttribute.click("xpath", ReconObjects.addIconToAddFilter, "Click on Add icon to enter the filter");
					
				}
				catch(Exception e){
					logger.log(LogStatus.INFO, "there is no existing filter applied on recon monitor screen");
					existingFilterflg= false;
				}
				try{
					if(driver.findElements(By.xpath(ReconObjects.enterFieldName1ToFilter)).size()>0){
						logger.log(LogStatus.INFO, "Filter  icon is already pressed");
					}
					else{
						ByAttribute.click("xpath", ReconObjects.filterIconLnk, "Click on Filter icon ");
						Utility.pause(3);
						if(driver.findElements(By.xpath(ReconObjects.enterFieldName1ToFilter)).size()>0)
							logger.log(LogStatus.INFO, "Add icon is already pressed");
						else
							ByAttribute.click("xpath", ReconObjects.addIconToAddFilter, "Click on Add icon ");
					}
					ByAttribute.click("xpath", ReconObjects.enterFieldName1ToFilter, "click to enter field name for Filtering");
					Utility.pause(2);
					ByAttribute.setText("xpath", ReconObjects.enterFieldName1ToFilter,"Entity", "Enter the field name for Filtering");
					Utility.pause(2);
					ByAttribute.click("xpath", ReconObjects.clickFieldValue1, "click to enter the value");
					Utility.pause(2);
					WebElement filterValue = driver.findElement(By.xpath(ReconObjects.enterFieldValue1));
					action.moveToElement(filterValue).click().build().perform();
					action.sendKeys(entityType).build().perform();
					action.sendKeys(Keys.ENTER).build().perform();
					Utility.pause(15);
					
				}
				catch(Exception e){
					logger.log(LogStatus.FAIL , "Issue in filtering records on recon monitor screen");
				}
				
				if(driver.findElements(By.xpath(ReconObjects.jobNameLocator)).size()>0)
					System.out.println("Description header is present on Recon Monitor Screen");
				else{
					/*
					 * Select the view for role monitor screen
					 */
					logger.log(LogStatus.INFO,"Selecting the appropriate view on recon monitor screen");
					WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
					settingsIcon.click();
					ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for recon monitor screen");
					ByAttribute.click("xpath",ReconObjects.roleMonitorViewLnk, "click on role monitor view");
					Utility.pause(10);
				}
				getIndexOfHeaders();
							
				//checking if recon job is registered in recon monitor screen or not
			
				WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchBarInRecon));
				searchBar.click();
				Utility.pause(5);
				ByAttribute.setText("xpath", ReconObjects.searchBarInRecon, AGlobalComponents.jobName, "Searching recon job on recon monitor screen");
				Utility.pause(5);
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(5);
					
				if(Utility.verifyElementPresentReturn(ReconObjects.jobNameLocator, AGlobalComponents.jobName, true, false)){
					logger.log(LogStatus.INFO,"Recon job found");
				
				}
				else{
					try{
						boolean jobPresent = Utility.verifyElementPresentReturn(ReconObjects.jobNameLocator, AGlobalComponents.jobName, true, false);
						int c=0;
						while((!jobPresent)&& (c<10)){
							System.out.println("job not registered in recon monitor , refreshing the page");
							logger.log(LogStatus.INFO, "waiting for job to get registered in recon monitor");
							WebElement refreshIcon = driver.findElement(By.xpath(ReconObjects.refreshIconLnk));
							refreshIcon.click();
							Utility.pause(5);
							c++;
							String jobNameLocator = "//div[text()='"+AGlobalComponents.jobName+"']";
							jobPresent = Utility.verifyElementPresentReturn(jobNameLocator, AGlobalComponents.jobName, true, false);
						}
					}
					catch(Exception e){
						logger.log(LogStatus.FAIL, "Recon job not registered in Recon monitor screen");
					}
		
				}
				//job is present in Recon monitor screen , Checking the status of the job
			
				WebElement status=driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.statusIndex+"]"));
				boolean flag=false;
				if((Utility.compareStringValues(status.getText(), "STARTED"))|| (Utility.compareStringValues(status.getText(), "COMPLETED"))){
					logger.log(LogStatus.INFO, "recon job is present and status is : "+ status.getText());
					if((Utility.compareStringValues(status.getText(), "STARTED")))
							flag= true;
					
					while(!Utility.compareStringValues(status.getText(), "COMPLETED") && flag){
						int count =0;
						while((Utility.verifyElementPresentReturn(ReconObjects.jobNameLocator, AGlobalComponents.jobName, false, false)) && (Utility.compareStringValues(status.getText(), "STARTED"))){
							WebElement refreshIcon = driver.findElement(By.xpath(ReconObjects.refreshIconLnk));
							refreshIcon.click();
							Utility.pause(20);
							status=driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.statusIndex+"]"));
							logger.log(LogStatus.INFO, "recon job is present and status is : "+ status.getText());
							count++;
						}
						System.out.println("Waiting for the recon job to get completed");
						status=driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.statusIndex+"]"));
						if(Utility.compareStringValues(status.getText(), "COMPLETED")){
							flag=false;
						}
					}
					logger.log(LogStatus.INFO, "Checking Recon job on Recon remediation screen");
					checkCCUREUserDataJobInReconRemediation(connectorName);
				}
			
				else if(Utility.compareStringValues(status.getText(), "FAILED")){
					logger.log(LogStatus.INFO, "Recon Job failed");
					WebElement errorMessage = driver.findElement(By.xpath("(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.messageIndex+"]"));
					String message = errorMessage.getText();
					logger.log(LogStatus.INFO, "Recon job failed with message : " + message);
			
				}
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}

	}

	private static void checkCCUREUserDataJobInReconRemediation(String connectorName) throws Throwable {

		if(unhandledException==false)
		{
					
			System.out.println("******************Checking Recon job in recon remediation************************");	
			try{
				
				ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
				Utility.pause(3);
				ByAttribute.click("xpath", ReconObjects.reconRemediationLnk,"click on Recon Remediation");
				Utility.pause(20);
				
				logger.log(LogStatus.INFO,"Selecting the appropriate view for recon remediation screen");
				WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
				settingsIcon.click();
				ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for user recon data");
				ByAttribute.click("xpath",ReconObjects.reconRemediationViewLnk, "click on user recon view");
						Utility.pause(10);
				
				
				ByAttribute.click("xpath", ReconObjects.filterIconLnk, "Click on Filter icon ");
				Utility.pause(3);
				ByAttribute.click("xpath", ReconObjects.addIconToAddFilter, "Click on Add icon to enter the filter");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.enterFieldName1ToFilter, "click to enter field name for Filtering");
				Utility.pause(2);
				ByAttribute.setText("xpath", ReconObjects.enterFieldName1ToFilter,"Entity", "Enter the field name for Filtering");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.clickFieldValue1, "click to enter the value");
				Utility.pause(2);
				WebElement filterValue = driver.findElement(By.xpath(ReconObjects.enterFieldValue1));
				Actions action = new Actions (driver);
				action.moveToElement(filterValue).click().build().perform();;
				action.sendKeys(entityType).build().perform();
				Utility.pause(20);
									
				
				WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchBarInRecon));
				searchBar.click();
				Utility.pause(5);
					
				/* verifying search option with valid search item */
					
				ByAttribute.setText("xpath", ReconObjects.searchBarInRecon, AGlobalComponents.jobName, "Searching recon job on recon monitor screen");
				Utility.pause(5);
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(5);
				
				getIndexOfHeadersInReconRemediation();
				
				/* view a list */
				WebElement activeRecords=driver.findElement(By.xpath("(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"'])//ancestor::tr//td["+AGlobalComponents.activeIndex+"]"));
				WebElement errorRecords=driver.findElement(By.xpath("(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"'])//ancestor::tr//td["+AGlobalComponents.errorIndex+"]"));
				String totalActiveRecords =  activeRecords.getText();
				if(Utility.compareStringValues(totalActiveRecords, activeReconRecords)){
					if(AGlobalComponents.rerunReconJob)
						logger.log(LogStatus.INFO, "Active records after rerunning the job are same  , when initial job was executed : "+totalActiveRecords +" which means no new records are added ");
				}
				else{
					activeReconRecords = totalActiveRecords;
					if(activeRecords.getText()!="0" ){
						logger.log(LogStatus.INFO, "Total number of active records are : " +totalActiveRecords);
						activeRecords.click();
						Utility.pause(10);
						verifyCCUREUserReconData();
					}
					else if(errorRecords.getText()!="0"){
						logger.log(LogStatus.INFO, "There are Error records ");
					}
					else if ((driver.findElements(By.xpath("(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"'])//ancestor::tr//td["+AGlobalComponents.messageIndex+"]")).size()>0)){
						WebElement errorMessage = driver.findElement(By.xpath("(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"'])//ancestor::tr//td["+AGlobalComponents.messageIndex+"]"));
						String message = errorMessage.getText();
						logger.log(LogStatus.INFO, "Recon job failed with message : " + message);
					}
				
				}
			}
				
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	private static void getIndexOfHeadersInReconRemediation() {

		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
						
			for (int i=1;i<size;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println(i);
				System.out.println(j);
				System.out.println("heading "+ (i) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "status":
		            	AGlobalComponents.statusIndex = j+1;
		            	j++;
		            	break;
		            case "message":
		            	AGlobalComponents.messageIndex = j;
		            	j++;
		            	break;
		            case "description":
		            	AGlobalComponents.descriptionIndex = j+1;
		            	j++;
		            	break;
		            case "active":
		            	AGlobalComponents.activeIndex = j+1;
		            	j++;
		            	break;
		            case "":
		            	
		            	break;
		            case "error":
		            	AGlobalComponents.errorIndex = j+1;
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

	private static void verifyCCUREUserReconData() throws Throwable {

		if(unhandledException==false)
		{
			System.out.println("***************************** Verify User Data Fetched From Recon *********************************");
			try
			{
				logger.log(LogStatus.INFO , "Recon remediation screen loaded with user recon data");
				System.out.println("Recon remediation screen loaded with user recon data");
		
				/*
				 * Select the view for role recon data
				 */

				logger.log(LogStatus.INFO,"Selecting the appropriate view to check user recon data");
				WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
				settingsIcon.click();
				ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for user recon data");
				ByAttribute.click("xpath",ReconObjects.roleReconViewLnk, "click on user recon view");
				Utility.pause(10);

				getIndexHeadersOfActiveRecords();
				String userDataFile=reconTestDataDirectory+"/Identity.csv";
				ArrayList<String> firstNameList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "firstName");
				for(String firstName:firstNameList) {
					WebElement userId=driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+firstName+"']//ancestor::tr//td["+AGlobalComponents.userIdIndex+"]//div"));
					String usrId = userId.getText();
				TestDataEngine.updateCSVCellValue(userDataFile, "masterIdentityId", 1, usrId);
				WebElement syncId = driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+firstName+"']//ancestor::tr//td["+AGlobalComponents.syncIdIndex+"]//div"));
				String syncID = syncId.getText();
				AGlobalComponents.syncId=syncID;
				logger.log(LogStatus.INFO, "Navigating to Identity tab to verify recon data on UI");
				verifyUserReconDataFromUI(usrId);
		
				logger.log(LogStatus.INFO, "verify recon data from DB");
				String query = "select active from recon_monitor where job_instance_id='"+syncID+"'";
//				verifyReconDataFromDB(query);
				}
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}

	private static void getIndexHeadersOfActiveRecords() {

		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
						
			for (int i=1;i<size;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println(i);
				System.out.println(j);
				System.out.println("heading "+ (i) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "user id":
		            	AGlobalComponents.userIdIndex = j+1;
		            	j++;
		            	break;
		            case "sync id":
		            	AGlobalComponents.syncIdIndex = j+1;
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
	 * Validating newly created recon job in recon monitor and navigating to remediation screen to 
	 * check active/erroneous data
	 * 
	 * Author : Monika Mehta, 
	 * 
	 * 
	 **/
	public static void checkJobInReconMonitor() throws Throwable {
	if(unhandledException==false)
	{
			
		System.out.println("******************Checking Recon job in recon monitor************************");	
		try{
			
			ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
			Utility.pause(3);
			ByAttribute.click("xpath", ReconObjects.reconMonitorLnk,"click on Recon Monitor");
			Utility.pause(20);
			
			if(AGlobalComponents.trialReconJob){
				ByAttribute.click("xpath", ReconObjects.trialJobRadioButton, "click on trial job radio button");
				logger.log(LogStatus.INFO, "Navigated to Trial tab on Recon monitor screen");
				Utility.pause(5);
			}

			/*
			 * Filter the records in Recon monitor on basis of entity type
			 */
			
			Actions action = new Actions (driver);
			boolean existingFilterflg = true;
			try{
				ByAttribute.click("xpath", ReconObjects.MinusIconToRemoveExistingFilter, "Remove existing filter ");
				Utility.pause(5);
				ByAttribute.click("xpath", ReconObjects.addIconToAddFilter, "Click on Add icon to enter the filter");
				
			}
			catch(Exception e){
				logger.log(LogStatus.INFO, "there is no existing filter applied on recon monitor screen");
				existingFilterflg= false;
			}
			try{
				if(driver.findElements(By.xpath(ReconObjects.enterFieldName1ToFilter)).size()>0){
					logger.log(LogStatus.INFO, "Filter  icon is already pressed");
				}
				else{
					ByAttribute.click("xpath", ReconObjects.filterIconLnk, "Click on Filter icon ");
					Utility.pause(3);
					if(driver.findElements(By.xpath(ReconObjects.enterFieldName1ToFilter)).size()>0)
						logger.log(LogStatus.INFO, "Add icon is already pressed");
					else
						ByAttribute.click("xpath", ReconObjects.addIconToAddFilter, "Click on Add icon ");
				}
				ByAttribute.click("xpath", ReconObjects.enterFieldName1ToFilter, "click to enter field name for Filtering");
				Utility.pause(2);
				ByAttribute.setText("xpath", ReconObjects.enterFieldName1ToFilter,"Entity", "Enter the field name for Filtering");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.clickFieldValue1, "click to enter the value");
				Utility.pause(2);
				WebElement filterValue = driver.findElement(By.xpath(ReconObjects.enterFieldValue1));
				action.moveToElement(filterValue).click().build().perform();
				action.sendKeys(entityType).build().perform();
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(15);
				
			}
			catch(Exception e){
				logger.log(LogStatus.FAIL , "Issue in filtering records on recon monitor screen");
			}
				
			if(driver.findElements(By.xpath(ReconObjects.jobNameLocator)).size()>0)
				System.out.println("Description header is present on Recon Monitor Screen");
			else{
				/*
				 * Select the view for role monitor screen
				 */
				logger.log(LogStatus.INFO,"Selecting the appropriate view on recon monitor screen");
				WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
				settingsIcon.click();
				ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for recon monitor screen");
				ByAttribute.click("xpath",ReconObjects.roleMonitorViewLnk, "click on role monitor view");
				Utility.pause(10);
			}
			getIndexOfHeaders();	
							
			//checking if recon job is registered in recon monitor screen or not
		
			WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchBarInRecon));
			searchBar.click();
			Utility.pause(5);
			ByAttribute.setText("xpath", ReconObjects.searchBarInRecon, AGlobalComponents.jobName, "Searching recon job on recon monitor screen");
			Utility.pause(5);
			action.sendKeys(Keys.ENTER).build().perform();
			Utility.pause(5);
		
			if(Utility.verifyElementPresentReturn(ReconObjects.jobNameLocator, AGlobalComponents.jobName, true, false)){
				logger.log(LogStatus.INFO,"Recon job found");
			
			}
			else{
				
				try{
					boolean jobPresent = Utility.verifyElementPresentReturn(ReconObjects.jobNameLocator, AGlobalComponents.jobName, true, false);
					int c=0;
					while((!jobPresent)&& (c<10)){
						System.out.println("job not registered in recon monitor , refreshing the page");
						logger.log(LogStatus.INFO, "waiting for job to get registered in recon monitor");
						WebElement refreshIcon = driver.findElement(By.xpath(ReconObjects.refreshIconLnk));
						refreshIcon.click();
						Utility.pause(5);
						c++;
						String jobNameLocator = "//div[text()='"+AGlobalComponents.jobName+"']";

						jobPresent = Utility.verifyElementPresentReturn(jobNameLocator, AGlobalComponents.jobName, true, false);
					}
				} 
	     
				catch(Exception e){
					logger.log(LogStatus.FAIL, "Recon job not registered in Recon monitor screen");
				}
			}


			//job is present in Recon monitor screen , Checking the status of the job
		
			WebElement status=driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.statusIndex+"]"));
			boolean flag=false;
			if((Utility.compareStringValues(status.getText(), "STARTED"))|| (Utility.compareStringValues(status.getText(), "COMPLETED"))){
				logger.log(LogStatus.INFO, "recon job is present and status is : "+ status.getText());
				if((Utility.compareStringValues(status.getText(), "STARTED")))
					flag= true;
				
				while(!Utility.compareStringValues(status.getText(), "COMPLETED") && flag){
					int count =0;
					while((Utility.verifyElementPresentReturn(ReconObjects.jobNameLocator, AGlobalComponents.jobName, false, false)) && (Utility.compareStringValues(status.getText(), "STARTED"))){
						WebElement refreshIcon = driver.findElement(By.xpath(ReconObjects.refreshIconLnk));
						refreshIcon.click();
						Utility.pause(20);
						status=driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.statusIndex+"]"));
						logger.log(LogStatus.INFO, "recon job is present and status is : "+ status.getText());
						count++;
					}
					System.out.println("Waiting for the recon job to get completed");
					status=driver.findElement(By.xpath(".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.statusIndex+"]"));
					if(Utility.compareStringValues(status.getText(), "COMPLETED")){
						flag=false;
					}
				}
				
				logger.log(LogStatus.INFO, "Checking Recon job on Recon remediation screen");
				checkJobInReconRemediation();
			}
		
			else if(Utility.compareStringValues(status.getText(), "FAILED")){
				logger.log(LogStatus.INFO, "Recon Job failed");
				WebElement errorMessage = driver.findElement(By.xpath("(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.messageIndex+"]"));
				String message = errorMessage.getText();
				logger.log(LogStatus.INFO, "Recon job failed with message : " + message);
		
			}
		}
		catch(Exception e){
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
			Utility.recoveryScenario(nameofCurrMethod, e);
		}
	}
}

	public static void getIndexOfHeaders() throws Throwable {
		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
						
			for (int i=1;i<size;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println(i);
				System.out.println(j);
				System.out.println("heading "+ (i) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "status":
		            	AGlobalComponents.statusIndex = j+1;
		            	j++;
		            	break;
		            case "message":
		            	AGlobalComponents.messageIndex = j;
		            	j++;
		            	break;
		            case "description":
		            	AGlobalComponents.descriptionIndex = j+1;
		            	j++;
		            	break;
		            case "active":
		            	AGlobalComponents.activeIndex = j+1;
		            	j++;
		            	break;
		            case "":
		            	
		            	break;
		            case "error":
		            	AGlobalComponents.errorIndex = j+1;
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

	private static void checkJobInReconRemediation() throws Throwable {
		if(unhandledException==false)
		{
			
			System.out.println("******************Checking Recon job in recon remediation************************");	
			try{
			
				ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
				Utility.pause(3);
				ByAttribute.click("xpath", ReconObjects.reconRemediationLnk,"click on Recon Remediation");
				Utility.pause(20);
			
				logger.log(LogStatus.INFO,"Selecting the appropriate view for recon remediation screen");
				WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
				settingsIcon.click();
				ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for user recon data");
				ByAttribute.click("xpath",ReconObjects.reconRemediationViewLnk, "click on user recon view");
				Utility.pause(10);
			
			
				ByAttribute.click("xpath", ReconObjects.filterIconLnk, "Click on Filter icon ");
				Utility.pause(3);
				ByAttribute.click("xpath", ReconObjects.addIconToAddFilter, "Click on Add icon to enter the filter");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.enterFieldName1ToFilter, "click to enter field name for Filtering");
				Utility.pause(2);
				ByAttribute.setText("xpath", ReconObjects.enterFieldName1ToFilter,"Entity", "Enter the field name for Filtering");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.clickFieldValue1, "click to enter the value");
				Utility.pause(2);
				WebElement filterValue = driver.findElement(By.xpath(ReconObjects.enterFieldValue1));
				Actions action = new Actions (driver);
				action.moveToElement(filterValue).click().build().perform();;
				action.sendKeys(entityType).build().perform();
				Utility.pause(20);
								
			
				WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchBarInRecon));
				searchBar.click();
				Utility.pause(5);
				
				/* verifying search option with valid search item */
				
				ByAttribute.setText("xpath", ReconObjects.searchBarInRecon, AGlobalComponents.jobName, "Searching recon job on recon monitor screen");
				Utility.pause(5);
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(5);
			
				/* view a list */
				WebElement activeRecords=driver.findElement(By.xpath("//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.activeIndex+"]"));
				WebElement errorRecords=driver.findElement(By.xpath("//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.errorIndex+"]"));
				String totalActiveRecords =  activeRecords.getText();
				if(Utility.compareStringValues(totalActiveRecords, activeReconRecords)){
					if(AGlobalComponents.rerunReconJob)
						logger.log(LogStatus.INFO, "Active records after rerunning the job are same  , when initial job was executed : "+totalActiveRecords +" which means no new records are added ");
				}
				else{
					activeReconRecords = totalActiveRecords;
					if(activeRecords.getText()!="0" ){
						logger.log(LogStatus.INFO, "Total number of active records are : " +totalActiveRecords);
						activeRecords.click();
						Utility.pause(10);
						verifyReconData();
					}
					else if(errorRecords.getText()!="0"){
						logger.log(LogStatus.INFO, "There are Error records ");
					}
					else if ((driver.findElements(By.xpath("(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.messageIndex+"]")).size()>0)){
						WebElement errorMessage = driver.findElement(By.xpath("(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.messageIndex+"]"));
						String message = errorMessage.getText();
						logger.log(LogStatus.INFO, "Recon job failed with message : " + message);
					}		

				}
			}
			
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	public static void verifyReconData() throws Throwable {
		if(unhandledException==false)
		{
			
			System.out.println("******************Verifying Recon Data************************");	
			try{
				if(AGlobalComponents.trialReconJob){
					verifyTrialReconData();
				}
				else if (AGlobalComponents.userRecon){
					verifyUserReconData();
				}
				else if (AGlobalComponents.roleRecon){
					verifyRoleReconData();
				}
				
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
	}

	/**
	 * 
	 * Initiate recon job by providing description , connector value and entity
	 *  
	 * Author : Monika Mehta
	 * @param createRequest 
	 * @param preFeedRule 
	 * @param string
	 * @param string 
	 * @param string 
	 * @throws Throwable 
	 * 
	 * 
	 **/
	public static void initiateReconJob(String connectorName, String scheduleType, String preFeedRule, String createRequest) throws Throwable {
	if(unhandledException==false)
		{
		System.out.println("******************Initiate recon job********************");
		try{
			String endDate="";
			if(Utility.compareStringValues(entityType, "Role Data"))
				AGlobalComponents.roleRecon = true;
			else if(Utility.compareStringValues(entityType, "User Data")) {
				AGlobalComponents.userRecon = true;
			}
			AGlobalComponents.jobName="testRecon"+ Utility.getCurrentDateTime("dd/MM/yy hh:mm:ss");		
			ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
			Utility.pause(2);
			ByAttribute.click("xpath", ReconObjects.reconSetUpLnk, "Click on Recon Setup ");
			Utility.pause(60);
			
			if(driver.findElements(By.xpath(ReconObjects.emptyReconSetUpGrid)).size()>0)
				ByAttribute.click("xpath", ReconObjects.addRecordsLnk, "Click to add recon record in empty grid");
			ByAttribute.click("xpath",ReconObjects.addReconRowLnk,"Click on Add icon to initiate Recon");
			Utility.pause(20);
			while(!ByAttribute.verifyCheckBox("xpath",ReconObjects.checkboxLnk)){
				ByAttribute.click("xpath",ReconObjects.addReconRowLnk,"Click on Add icon to initiate Recon");
				Utility.pause(10);
			}
					
			Actions action = new Actions(driver);
			WebElement elementConnector=driver.findElement(By.xpath(ReconObjects.elementConnector));
			action.moveToElement(elementConnector).click();
			action.sendKeys(connectorName);
			action.build().perform();
			WebElement connectorValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'"+connectorName+"')]"));
			action.moveToElement(connectorValue).click();
			action.build().perform();
			logger.log(LogStatus.INFO, "Connector Value selected");
			Utility.pause(5);
		
			WebElement elementDesc=driver.findElement(By.xpath(ReconObjects.elementDesc));
			action.moveToElement(elementDesc).click();
			action.sendKeys(AGlobalComponents.jobName);
			action.build().perform();
			
	        WebElement elementDescr=driver.findElement(By.xpath(ReconObjects.elementDesc));
			action.moveToElement(elementDescr).click();
			action.sendKeys(AGlobalComponents.jobName);
			action.build().perform();
			logger.log(LogStatus.INFO, "Entered the Description Value");
        	       									
			WebElement elementEntity=driver.findElement(By.xpath(ReconObjects.elementEntity));
			action.moveToElement(elementEntity).click();
			action.sendKeys(entityType);
			action.build().perform();
//			WebElement entityValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'"+entityType+"')]"));
//			action.moveToElement(entityValue).click();
//			action.build().perform();
			logger.log(LogStatus.INFO, "Entity Type selected");
			Utility.pause(5);
		
			WebElement sequence=driver.findElement(By.xpath(ReconObjects.sequence));
			action.moveToElement(sequence).click();
			action.sendKeys("1");
			action.build().perform();
			Utility.pause(5);
		
			if(createRequest.equalsIgnoreCase("yes")) {
				WebElement createRequestLocator=driver.findElement(By.xpath(ReconObjects.createRequest));
				action.moveToElement(createRequestLocator).click();
				action.build().perform();
				logger.log(LogStatus.INFO, "CreateRequest checkbox selected");
				AGlobalComponents.createRequest=true;
				Utility.pause(5);
			}
			
			WebElement fetchEntityLocator=driver.findElement(By.xpath(ReconObjects.createRequest));
			action.moveToElement(fetchEntityLocator).click();
			action.build().perform();
			logger.log(LogStatus.INFO, "Fetch Entity checkbox selected");
			Utility.pause(5);
			
			if(preFeedRule!=null) {
				WebElement preFeedRuleLocator=driver.findElement(By.xpath(ReconObjects.preFeedRule));
				action.click(preFeedRuleLocator);
				action.build().perform();
				Utility.pause(5);
				List<WebElement> checkBoxPreFeedRuleLocator=driver.findElements(By.xpath("//div[text()='"+preFeedRule+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox')]"));
				int size=checkBoxPreFeedRuleLocator.size();
				action.moveToElement(checkBoxPreFeedRuleLocator.get(size-1)).click();
				action.build().perform();
				WebElement confirmBtn = driver.findElement(By.xpath(ReconObjects.confirmButton));
				action.moveToElement(confirmBtn).click();
				action.build().perform();
				logger.log(LogStatus.INFO, "PreFeed Rule :"+preFeedRule);
				Utility.pause(5);
			}
			
			WebElement elementScheduler=driver.findElement(By.xpath(ReconObjects.elementScheduler));
			action.moveToElement(elementScheduler).click();
			action.build().perform();
        	WebElement elementSchedulr=driver.findElement(By.xpath(ReconObjects.elementScheduler));
			action.moveToElement(elementSchedulr).click();
			action.build().perform();
			logger.log(LogStatus.INFO, "Clicked On Scheduler");
			Utility.pause(10);
      	
			ByAttribute.setText("xpath", ReconObjects.scheduleTypeLnk,scheduleType, "Select Scheduler Type");
			Utility.pause(2);
			ByAttribute.click("xpath", ReconObjects.confirmButtonLnk,"Click confirm button after scheduling recon job");
			WebElement confirmButton = driver.findElement(By.xpath(ReconObjects.confirmButton));
			action.moveToElement(confirmButton).click();
			action.build().perform();
			Utility.pause(2);
	
			if(AGlobalComponents.trialReconJob){
				logger.log(LogStatus.INFO, "Executing trial job");
				ByAttribute.click("xpath", ReconObjects.trialButtonLnk, "Click on trial Job Icon");
				Utility.pause(2);
			
				Calendar c = Calendar.getInstance();
				DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
				Date date = new Date();
				String currentDate= dateFormat.format(date);
				c.setTime(dateFormat.parse(currentDate));
				//Number of Days to minus
				c.add(Calendar.DAY_OF_MONTH, -2);  
				//Date after subtracting the days to the given date
				String temp = dateFormat.format(c.getTime());  
			
				date = new SimpleDateFormat("MM-dd-yy").parse(temp);
				endDate = new SimpleDateFormat("M/d/yy hh:mm a").format(date); 
					
				ByAttribute.click("xpath", ReconObjects.endDateForTrialJob, "Click to enter end date");
				ByAttribute.setText("xpath", ReconObjects.endDateForTrialJob, endDate, "Enter end date to run trial job");
				confirmButton = driver.findElement(By.xpath(ReconObjects.confirmButton));
				action.moveToElement(confirmButton).click();
				action.build().perform();
				Utility.pause(5);
				logger.log(LogStatus.PASS, "Trial recon job saved successfully");
			}
			else if(entityType.equalsIgnoreCase("User Data")) {
				ByAttribute.click("xpath", ReconObjects.rerunIconReconSetup, "click on rerun icon to rerun the job");
				
				Calendar c = Calendar.getInstance();
				DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy hh:mm:ss");
				
				Date date = new Date();
				String currentDate= dateFormat.format(date);
				c.setTime(dateFormat.parse(currentDate));
				//Number of minutes to minus
				c.set(Calendar.HOUR_OF_DAY, 0);
				//Date after subtracting the days to the given date
				String temp = dateFormat.format(c.getTime());  
			
				date = new SimpleDateFormat("MM-dd-yy hh:mm:ss").parse(temp);
				endDate = new SimpleDateFormat("M/d/yy hh:mm a").format(date); 
					
				ByAttribute.click("xpath", ReconObjects.endDateToRerunTheJob, "Click to enter end date");
				ByAttribute.setText("xpath", ReconObjects.endDateToRerunTheJob, endDate, "Enter end date to rerun the job");
				WebElement confirmBtn = driver.findElement(By.xpath(ReconObjects.confirmButton));
				confirmBtn = driver.findElement(By.xpath(ReconObjects.confirmButton));
				action.moveToElement(confirmBtn).click();
				action.build().perform();
				Utility.pause(5);
				logger.log(LogStatus.PASS,"reran the job successfully");
			}
			else{
				ByAttribute.click("xpath", ReconObjects.submitButtonLnk,"submit the recon request");
				Utility.pause(5);
				ByAttribute.click("xpath", ReconObjects.confirmPopUpLnk,"click confirm button on popup");
				Utility.pause(10);
				logger.log(LogStatus.PASS, "Recon Job saved successfully");
			}
			
		}
		catch( Exception e){
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
			Utility.recoveryScenario(nameofCurrMethod, e);
		}
		}		
	}
	
	private static String getEndDate(String connectorName) throws ClassNotFoundException, SQLException {

		ArrayList<String> reconConfigIdList=DBValidations.getReconConfigIdList();
		String endDate="";
		if(!reconConfigIdList.isEmpty()) {	
			for(int i=1;i<reconConfigIdList.size()-1;i++) {
				endDate=DBValidations.getEndDate(reconConfigIdList.get(i),connectorName);
				if(endDate!=null)
					break;
			}
		}
		return endDate;
	}

	/**
	 * 
	 * Method to create identity through Identity Tab
	 * Author : Monika Mehta
	 * 
	 * 
	 **/

	public static void createIdentity() throws Throwable
	{
		String firstName= null;
		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO, "Create new Identity");
			System.out.println("***************************** Create Identity *********************************");
			try
			{
				
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
				ByAttribute.click("xpath", IdentityObjects.createBtn, "click on create  icon to create new identity");
				Utility.pause(8);
				
				fillProfileInfo();
				if(!AGlobalComponents.deleteSingleIdentityFlag){
					ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Accesses Tab ");
					Utility.pause(2);
					fillAccessesInfo();
					ByAttribute.click("xpath", IdentityObjects.systemsTabLnk, "Click on Systems Tab ");
					Utility.pause(2);
					fillSystemsInfo();
					ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
					Utility.pause(2);
					fillAssetsInfo();
					ByAttribute.click("xpath", IdentityObjects.prerequisitesTabLnk, "Click on Prerequisites Tab ");
					Utility.pause(10);
					fillPrerequisitesInfo();
				}
				ByAttribute.click("xpath", IdentityObjects.SaveBtn, "Click on save Button ");
				Utility.pause(20);

				logger.log(LogStatus.PASS, "identity created");	
				

			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
				
	}
	
	
	public static void createIdentity(String firstName,String lastName) throws Throwable
	{
		
		if(unhandledException==false)
		{
			
			
			System.out.println("***************************** Create Identity *********************************");
			try
			{
				System.out.println("**************************** createNewIdentity ********************************");
				logger.log(LogStatus.INFO,"**************************** createNewIdentity ********************************");
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
					
				ByAttribute.click("xpath", IdentityObjects.createBtn, "click on create  icon to create new identity");
				Utility.pause(2);
				
				fillProfileInfo(firstName,lastName);
				
				ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "*********Click on Accesses Tab********** ");
				Utility.pause(2);
				fillAccessesInfo();
			
				ByAttribute.click("xpath", IdentityObjects.prerequisitesTabLnk, "***********Click on Prerequisites Tab************ ");
				Utility.pause(2);
				fillPrerequisitesInfo();
				
				ByAttribute.click("xpath", IdentityObjects.SaveBtn, "Click on save Button ");
				Utility.pause(20);

				logger.log(LogStatus.PASS, "identity created");	
				
				ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "********Click on Assets Tab*********** ");
				Utility.pause(2);
				fillAssetsInfo(AGlobalComponents.assetCode);
				ByAttribute.click("xpath", IdentityObjects.SaveBtn, "Click on save Button ");
				Utility.pause(20);
				logger.log(LogStatus.INFO, "Created asset assigned to the user");
				
				searchIdentity(firstName,lastName);
				
				ByAttribute.click("xpath", IdentityObjects.systemsTabLnk, "**************Click on Systems Tab ***************");
				if(driver.findElements(By.xpath("//div[@class='x-grid-item-container' and contains(@style,'transform: translate')]//tr")).size()>0)
					logger.log(LogStatus.INFO, "System is assigned to the user");
				else
					logger.log(LogStatus.FAIL, "System is not assigned to the user");
				
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
				
	}
	
	
	
	
	/**
	 * 
	 * Method to search Identity 
	 * 
	 * Author : Monika Mehta
	 * 
	 * 
	 **/
	public static String searchIdentity() throws Throwable
	{
		
		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO, "Search the above created Identity");
			System.out.println("***************************** Search Identity*********************************");
			try
			{
				String searchIdentityTemplateFile,searchIdentityDataFile;
				
				
				if(AGlobalComponents.ManagerLogin){
					searchIdentityTemplateFile = ManagerCasesTestDataDirectory + "/SearchIdentity_Template.csv";
					searchIdentityDataFile = ManagerCasesTestDataDirectory + "/SearchIdentity.csv";
				}
				else{
					searchIdentityTemplateFile = createIdentityTestDataDirectory + "/SearchIdentity_Template.csv";
					searchIdentityDataFile = createIdentityTestDataDirectory + "/SearchIdentity.csv";
				}
				TestDataInterface.compileTwoRowDataTemplate(searchIdentityTemplateFile, searchIdentityDataFile);
				
				
				ArrayList<String> headers = Utility.getCSVRow(searchIdentityDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(searchIdentityDataFile, 0);
				int len = headers.size();

				Actions action = new Actions(driver);
				
				String fieldName1= null,fieldValue1= null,fieldName2= null,fieldValue2= null,empType= null,header,idCode,idCode1,idCode2;
								
				try{		
				for (int i=0;i<len;i++){
					header= headers.get(i);
					System.out.println("heading "+ (i+1) +" "+ header);
					int index = Utility.getIndex(headers,header);
					for(ArrayList<String> userData : usersData) {
						
						switch (header.toLowerCase()) {
			            case "fieldname1":
			            	fieldName1 = Utility.getIndexValue(userData, index);
			            	break;
			            case "fieldvalue1":
			            	fieldValue1 = Utility.getIndexValue(userData, index);
			            	break;
			            case "fieldname2":
			            	fieldName2 = Utility.getIndexValue(userData, index);
			            	break;
			            case "fieldvalue2":
			            	fieldValue2 = Utility.getIndexValue(userData, index);
			            	break;
			            case "employeetype":
			            	empType = Utility.getIndexValue(userData, index);
			                break;
			            default: 
			            	logger.log(LogStatus.ERROR, "Failed: Field {" +header+"} Not Found ");
			            	throw new UnsupportedOperationException();
						}			

					}

				}
				}
				catch(Exception e){
					
				}
				
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
				

				if(!dupIdentity){
					ByAttribute.click("xpath", IdentityObjects.filterIconLnk, "Click on Filter icon ");
					Utility.pause(3);
					ByAttribute.click("xpath", IdentityObjects.addFilterLnk, "Click on Add icon ");
					
					ByAttribute.click("xpath", IdentityObjects.enterFieldName1ToFilter, "click to enter field name for Filtering");
					Utility.pause(2);
					ByAttribute.setText("xpath", IdentityObjects.enterFieldName1ToFilter,fieldName1, "Enter the field name for Filtering");
					Utility.pause(2);
					ByAttribute.click("xpath", IdentityObjects.clickFieldValue1, "click to enter the value");
					Utility.pause(2);
					ByAttribute.setText("xpath", IdentityObjects.enterFieldValue1,fieldValue1, "Enter the first field value for Filtering");
					Utility.pause(2);
				
					ByAttribute.click("xpath", IdentityObjects.addFilterLnk, "Click on Add icon to enter the filter");
					Utility.pause(2);
					ByAttribute.click("xpath", IdentityObjects.enterFieldName2ToFilter, "click to enter field name for Filtering");
					Utility.pause(2);
					ByAttribute.setText("xpath", IdentityObjects.enterFieldName2ToFilter,fieldName2, "Enter the field name for Filtering");
					Utility.pause(2);
					ByAttribute.click("xpath", IdentityObjects.clickFieldValue2, "click to enter the second value");
					Utility.pause(2);
					ByAttribute.setText("xpath", IdentityObjects.enterFieldValue2,fieldValue2, "Enter the second field value for Filtering");
				
					action.sendKeys(Keys.ENTER).build().perform();
					Utility.pause(20);
				
				
					if(driver.findElements(By.xpath("((//div[text()='"+fieldValue1+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]")).size()>0){
						WebElement record=driver.findElement(By.xpath("((//div[text()='"+fieldValue1+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
						identityCode=record.getText();	
						action.doubleClick(record).perform();
						Utility.pause(15);
						String searchResult= "//label[contains(text(),'"+fieldValue1+"')]";
						if(Utility.verifyElementPresentReturn(searchResult,fieldValue1,true,false)){
							logger.log(LogStatus.INFO ,"Search result record appeared with identity code as : "+ identityCode);
						}
						logger.log(LogStatus.PASS, "Search Identity successful");
					}
					else{
						logger.log(LogStatus.FAIL ,"Failed to search the record");
					}
				}
				else{
						int noOfRecords= driver.findElements(By.xpath("//div[text()='"+fieldValue1+"']")).size();
						for (int i=0;i<noOfRecords;i++){
							WebElement record1 = driver.findElement(By.xpath("((//div[text()='"+fieldValue1+"'])["+(i+1)+"]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
							idCode=record1.getText();
							identityCodes.add(i,idCode);
						}
						idCode1=identityCodes.get(0);
						idCode2=identityCodes.get(1);
						if(!Utility.compareStringValues(idCode1, idCode2)){
							logger.log(LogStatus.PASS ,"Duplicate identity is created with different identity code : " + idCode1+ ","+idCode2);
						}
				}	

			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
		return identityCode;		
	}
	
	
	
	public static void searchIdentity(String firstName,String lastName) throws Throwable
	{
		
		if(unhandledException==false)
		{
			logger.log(LogStatus.INFO, "********************Search the  Identity*******************************");
			System.out.println("***************************** Search Identity*********************************");
			try
			{
				
				
				Actions action = new Actions(driver);
				action.click().build().perform();
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
				if(driver.findElements(By.xpath("//*[contains(@class,'x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-rowMinus ')]")).size()>=2)
					System.out.println("Identity is already searched in IDM");
				else{
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
		
		//		Actions action = new Actions(driver);
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(5);
				}
		
			if(driver.findElements(By.xpath("((//div[text()='"+firstName+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]")).size()>0){
				WebElement record=driver.findElement(By.xpath("((//div[text()='"+firstName+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
				identityCode=record.getText();	
				action.doubleClick(record).build().perform();
				Utility.pause(10);
				String searchResult= "//*[contains(text(),'"+identityCode+"')]";
				if(driver.findElements(By.xpath(searchResult)).size()>0){
					Utility.verifyElementPresent(searchResult,"Identity",false);
					logger.log(LogStatus.INFO ,"Search result record appeared with identity code as : "+ identityCode);
				}
				logger.log(LogStatus.PASS, "Search Identity successful");
			}
			else{
				logger.log(LogStatus.FAIL ,"Failed to search the record");
			}
				
			}catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
	}
	
	public static void modifyIdentity() throws Throwable
	{

		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO, "edit the job title of created identity");
			System.out.println("***************************** Edit Identity *********************************");
			logger.log(LogStatus.INFO, "Edit Identity and add Job title");
			try
			{
				if(!(driver.findElements(By.xpath("IdentityObjects.IdentityTabLnk")).size()>0))
					Utility.pause(5);
				
				editProfileInfo();
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}		
	}
	
	
	public static void createDuplicateIdentity() throws Throwable
	{

		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO, "Create duplicate Identity");
			System.out.println("***************************** Create DuplicateIdentity *********************************");
			try
			{
				
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
				ByAttribute.click("xpath", IdentityObjects.createBtn, "click on create  icon to create new identity");
				Utility.pause(10);
				
				fillDuplicateProfileInfo();
				ByAttribute.click("xpath", IdentityObjects.SaveBtn, "Click on save Button ");
				Utility.pause(15);
				dupIdentity=true;
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}		
	}
	
	
	public static void cancelCreateIdentity() throws Throwable{
		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO, "test showHideFilterWidge functionality on create identity screen");
			System.out.println("***************************** Cancel Button in Create Identity *********************************");
			try
			{
				
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
				ByAttribute.click("xpath", IdentityObjects.createBtn, "click on create  icon to create new identity");
				Utility.pause(10);
				
				if(driver.findElements(By.xpath(IdentityObjects.createIdentityHeader)).size()>0){

                                        					Utility.verifyElementPresent(IdentityObjects.createIdentityHeader, "Create identity screen", false);

					logger.log(LogStatus.INFO, "Create Identity  screen is loaded ");
				}
				
				ByAttribute.click("xpath", IdentityObjects.cancelButtonLnk, "Click on cancel button ");
				Utility.pause(10);
				
				if(driver.findElements(By.xpath(IdentityObjects.identityManagementHeader)).size()>0){

					Utility.verifyElementPresent(IdentityObjects.identityManagementHeader, "Manage Identity screen", false);
					logger.log(LogStatus.INFO, "Manage identity screen is loaded after cancelling the create identity operation ");
				}
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}		
		
	}
	
	public static void showHideFilterWidget() throws Throwable{
		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO,"Testing Show/Hide filter widget functionality ");
			System.out.println("***************************** Show/hide filter widget *********************************");
			try
			{
				if(!(driver.findElements(By.xpath("IdentityObjects.IdentityTabLnk")).size()>0))
					Utility.pause(5);
				
				if(Utility.verifyElementPresentReturn("//label[text()='Refine Search']", "Filter Widget", true, false)){
					System.out.println("Filter widget is present on the screen");
					ByAttribute.click("xpath", IdentityObjects.settingsIconLnk, "Click on settings icon");
					Utility.pause(2);
					ByAttribute.click("xpath", IdentityObjects.widgetMenuLnk, "Click on show/hide widget menu ");
					Utility.pause(2);
				}
				if(Utility.verifyElementPresentReturn(IdentityObjects.identityManagementHeader, "Identity Management header", true, false)){

					if(driver.findElements(By.xpath("//label[text()='Refine Search']")).size()>0)
						logger.log(LogStatus.FAIL,"Show/Hide filter widget functionality is NOT working fine");
					else{
						System.out.println("Filter widget is hidden from the screen");
						logger.log(LogStatus.PASS,"Show/Hide filter widget functionality is working fine");
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
	
	public static void mandatoryFieldsCheck() throws Throwable{
		
		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO ,  "Mandatory field check on create identity");
			System.out.println("***************************** Mandatory Field check in Create Identity *********************************");
			try
			{
				
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
				ByAttribute.click("xpath", IdentityObjects.createBtn, "click on create  icon to create new identity");
				Utility.pause(10);
				
				ByAttribute.click("xpath", IdentityObjects.SaveBtn, "click on save  icon ");
				Utility.pause(10);
								

				if(driver.findElements(By.xpath(IdentityObjects.errorMessageForFirstName)).size()>0){
					logger.log(LogStatus.INFO, "Error message displayed to enter the mandatory fields");
					Utility.verifyElementPresent(IdentityObjects.errorMessageForFirstName, "ErrorMessage",false);
				}		

			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}		
		
	}
	
	
	public static void deleteIdentity() throws Throwable{
		
		String iCode;
		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO, "Create  identity and then delete the identity");
			System.out.println("***************************** delete single identity *********************************");
			try
			{
				if(!(driver.findElements(By.xpath("IdentityObjects.IdentityTabLnk")).size()>0))
					Utility.pause(5);
				
				AGlobalComponents.deleteSingleIdentityFlag=true;
				createIdentity();
				logger.log(LogStatus.INFO, "Search above created identity");
				iCode=searchIdentity();
				System.out.println("Identity Created: "+ iCode);
				logger.log(LogStatus.INFO, "Identity Created: "+ iCode);
								
				deleteSingleIdentity(iCode);
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}		
		
	}

	public static void deleteMultipleIdentities() throws Throwable  {
	
		if(unhandledException==false){
			
			logger.log(LogStatus.INFO, "deleting the mutiple identities");
			System.out.println("*******************Delete multiple identitis************************");
			try{	
		
				for (int i=0;i<identityCodes.size();i++){
					String idcode = identityCodes.get(i);
					WebElement idCheckbox = driver.findElement(By.xpath("//div[text()='"+idcode+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-not-selected')]"));
					Actions action = new Actions(driver);
					action.click(idCheckbox).build().perform();
					Utility.pause(5);
					if(driver.findElements(By.xpath("//div[text()='"+idcode+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-selected')]")).size()>0){
						System.out.println("identity " +identityCodes.get(i) +"  selected for deletion");
					}
					else{
						System.out.println("identity " +identityCodes.get(i) +"  not selected for deletion");
						logger.log(LogStatus.ERROR, "Identity checkbox not selected");
					}
				}
				ByAttribute.click("xpath", IdentityObjects.deleteIdentityIconLnk, "Delete identities selected");
				ByAttribute.click("xpath", IdentityObjects.yesButtonInDeleteIdentitiesPopUp, "Confirm deletion of identities");
				Utility.verifyElementPresent("//div[@class='emptyGridMsg']", "Empty Grid Message", false);
				logger.log(LogStatus.PASS, "deleted the selected identities");
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	
	public static void deleteSingleIdentity(String identityCode) throws Throwable  {
		if(unhandledException==false)
		{
			
			System.out.println("***************Delete single identity*********************");

			try{
		
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
				
				Actions action = new Actions(driver);
				WebElement idCheckbox = driver.findElement(By.xpath("//div[text()='"+identityCode+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-not-selected')]"));
				action.click(idCheckbox).build().perform();
				Utility.pause(5);
				
				if(driver.findElements(By.xpath("//div[text()='"+identityCode+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-selected')]")).size()>0){
					System.out.println("identity " +identityCode+"  selected for deletion");
					ByAttribute.click("xpath", IdentityObjects.deleteIdentityIconLnk, "Delete identities selected");
					ByAttribute.click("xpath", IdentityObjects.yesButtonInDeleteIdentitiesPopUp, "Confirm deletion of identities");
					Utility.verifyElementPresent("//div[@class='emptyGridMsg']", "Empty Grid Message", false);
					logger.log(LogStatus.PASS, "deleted the selected identity");
				}
				else{
					System.out.println("Checkbox not selected for deletion");
					logger.log(LogStatus.ERROR, "Checkbox not selected for deletion");
				}
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	public static void verifyCancelAndCloseButtonInDeletedItems() throws Throwable {
		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO, "check the deleted identity in deleted items");
			System.out.println("***********verify Cancel And Close Button In Deleted Items**********");

			try{
			
				System.out.println("Click on icon to display the menu");
				ByAttribute.click("xpath", IdentityObjects.menuItemsIcon, "Click on icon to display the menu");
				Utility.pause(1);
			
				System.out.println("Click on Deleted Items link from the menu");
				ByAttribute.click("xpath", IdentityObjects.deletedItemsLnk, "Open Deleted Items");
				Utility.pause(5);
			
				if(!(driver.findElements(By.xpath(IdentityObjects.deletedIdentityDocumentsHeader)).size()>0)){
					Utility.pause(2);
				}
				Utility.verifyElementPresent(IdentityObjects.deletedIdentityDocumentsHeader, "Deleted Identity Documents",false);
				logger.log(LogStatus.INFO, "Deleted identities pop up appeared");
				System.out.println("Deleted identities pop up appeared");
				ByAttribute.click("xpath", IdentityObjects.closeButtonInDeletedItemsWindow, "Clicked on close icon in the pop up box");
				Utility.pause(5);
				Utility.verifyElementPresent(IdentityObjects.identityManagementHeader, "Identity management header", false);
				logger.log(LogStatus.PASS, "Deleted identities pop up box closed after clicking close icon, we are back on manage identity screen");
				System.out.println("Deleted identities pop up box closed afte clicking close icon, we are back on manage identity screen");
			
				/*
				 * Verifying Cancel button functionality on Deleted Identity Documents pop up
				 */
				System.out.println("Click on icon to display the menu");
				ByAttribute.click("xpath", IdentityObjects.menuItemsIcon, "Click on icon to display the menu");
				Utility.pause(1);
			
				System.out.println("Click on Deleted Items link from the menu");
				ByAttribute.click("xpath", IdentityObjects.deletedItemsLnk, "Open Deleted Items");
				Utility.pause(5);
			
				if(!(driver.findElements(By.xpath(IdentityObjects.deletedIdentityDocumentsHeader)).size()>0)){
					Utility.pause(2);
				}
				Utility.verifyElementPresent(IdentityObjects.deletedIdentityDocumentsHeader, "Deleted Identity Documents", false);
				logger.log(LogStatus.INFO, "Deleted identities pop up appeared");
				System.out.println("Deleted identities pop up appeared");
				ByAttribute.click("xpath", IdentityObjects.cancelButtonInDeletedItemsWindow, "Clicked on cancel button in the pop up box");
				Utility.pause(5);
				Utility.verifyElementPresent(IdentityObjects.identityManagementHeader, "Identity Management Header",  false);
				logger.log(LogStatus.PASS, "Deleted identities pop up box closed after clicking cancel button, we are back on manage identity screen");
				System.out.println("Deleted identities pop up box closed afte clicking cancel button, we are back on manage identity screen");
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	public static void recoverDeletedItems() throws Throwable  {
		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO, "recover the above deleted identity");
			System.out.println("**************Recover Deleted Items****************");
		
			try{
			
				System.out.println("Click on icon to display the menu");
				ByAttribute.click("xpath", IdentityObjects.menuItemsIcon, "Click on icon to display the menu");
				Utility.pause(1);
			
				System.out.println("Click on Deleted Items link from the menu");
				ByAttribute.click("xpath", IdentityObjects.deletedItemsLnk, "Open Deleted Items");
				Utility.pause(5);
			
				if(!(driver.findElements(By.xpath("//div[contains(@id,'MainPanelTrashCanPopup') and text()='Deleted identity Documents']")).size()>0)){
					Utility.pause(2);
				}
			
				SimpleDateFormat f = new SimpleDateFormat("MM/dd/yy");
				String strDate = f.format(new Date());
				ByAttribute.click("xpath", IdentityObjects.deletedSinceDateValue, "click to enter the date value from when deleted records are required");
				ByAttribute.setText("xpath", IdentityObjects.deletedSinceDateValue,strDate, "Enter the date value");
				Utility.pause(5);
			
				String idCode = driver.findElement(By.xpath(("(//td[contains(@class,'x-grid-cell x-grid-td x-grid-cell-gridcolumn')])[1]//div"))).getText();
			
				if(Utility.checkIfStringIsNotNull(idCode)){
					if(Utility.compareStringValues(idCode, identityCode)){
						System.out.println("Deleted identity is present in recently deleted items");

						Utility.verifyElementPresent("xpath", idCode, false);
						logger.log(LogStatus.INFO, "Deleted identity is present in recently deleted items");
						

						Actions action = new Actions(driver);
						WebElement idCheckbox = driver.findElement(By.xpath("//div[text()='"+identityCode+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-not-selected')]"));
						action.click(idCheckbox).build().perform();
						Utility.pause(5);
						if(driver.findElements(By.xpath("//div[text()='"+identityCode+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-selected')]")).size()>0){
							System.out.println("identity " +identityCode+"  selected for restoration");
							ByAttribute.click("xpath", IdentityObjects.restoreButtonInDeletedItemsWindow, "Restore selected identity");
							Utility.pause(10);
						}
						else{
							logger.log(LogStatus.ERROR, "Checkbox of id not selected for restoration");
							System.out.println("checkbox not selected for restoration");
						}
					}
					else{
						logger.log(LogStatus.ERROR, "Identity  present does not match with the identity to be restored");
						System.out.println("Identity  present does not match with the identity to be restored");
					}
				}
				Utility.verifyElementPresent("(//td[contains(@class,'x-grid-cell x-grid-td x-grid-cell-gridcolumn')])[1]//div[text()='"+identityCode+"']", identityCode, false);
				logger.log(LogStatus.PASS,"Deleted identity has been restored");
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	public static void fillProfileInfo() throws Throwable 
	{
		
		if(unhandledException==false)
		{
			String createIdentityTemplateFile ,createIdentityDataFile;
			System.out.println("********************Fill Profile Info********************");
			try{
				if(AGlobalComponents.ManagerLogin){
					createIdentityTemplateFile = ManagerCasesTestDataDirectory + "/CreateIdentity_Template.csv";
					createIdentityDataFile=ManagerCasesTestDataDirectory+ "/CreateIdentity.csv";
				}
				else{
					createIdentityTemplateFile = createIdentityTestDataDirectory + "/CreateIdentity_Template.csv";
					createIdentityDataFile=createIdentityTestDataDirectory+ "/CreateIdentity.csv";
				}
				TestDataInterface.compileTwoRowDataTemplate(createIdentityTemplateFile, createIdentityDataFile);
		
				ArrayList<String> headers = Utility.getCSVRow(createIdentityDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(createIdentityDataFile, 0);
				int len = headers.size();
				String firstName=null,lastName= null,validFrom= null,validTo = null,emailId= null,empType= null,header,jobTitle;
				
				for (int i=0;i<len;i++){
					header= headers.get(i);
					System.out.println("heading "+ (i+1) +" "+ header);
					int index = Utility.getIndex(headers,header);
					for(ArrayList<String> userData : usersData) {
				
						switch (header.toLowerCase()) {
						case "firstname":
							firstName = Utility.getIndexValue(userData, index);
							break;
						case "lastname":
							lastName = Utility.getIndexValue(userData, index);
							break;
						case "validfrom":
							validFrom=Utility.getIndexValue(userData, index);
							break;
						case "validto":
							validTo=Utility.getIndexValue(userData, index);
							break;
						case "employeetype":
							empType = Utility.getIndexValue(userData, index);
							break;
						case "email":
							emailId = Utility.getIndexValue(userData, index);
							break;
						case "jobtitle":
							jobTitle = Utility.getIndexValue(userData, index);
							break;
						default: 
							logger.log(LogStatus.ERROR, "Failed: Field {" +header+"} Not Found ");
							throw new UnsupportedOperationException();
						}
					}
				}
				
				ByAttribute.setText("xpath", IdentityObjects.employeeTypeLnk, empType, "Enter employee Type");
				Utility.pause(5);
				ByAttribute.click("xpath", IdentityObjects.firstNameLnk, "Enter first Name");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.firstNameLnk, firstName, "Enter first Name");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.lastNameLnk, lastName, "Enter Last Name");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseBasicInfoSection, "collapse Basic Information Section");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.emailIdLnk, emailId, "Enter email Id");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseContactInfoSection, "collapse Contact Information Section");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseOrganisationInfoSection, "collapse Organisation Information Section");
				Utility.pause(2);
//				ByAttribute.setText("xpath", IdentityObjects.validFromLnk, validFrom, "Enter valid From");
//				Utility.pause(2);


//				ByAttribute.setText("xpath", IdentityObjects.validToLnk, validTo, "Enter valid To");
				
			}
			catch(Exception e)
			{
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
	}
	
	public static void fillProfileInfo(String fName,String lName) throws Throwable 
	{
		
		if(unhandledException==false)
		{
			String createIdentityTemplateFile ,createIdentityDataFile;
			System.out.println("********************Fill Profile Info********************");
			try{
				if(AGlobalComponents.ManagerLogin){
					createIdentityTemplateFile = ManagerCasesTestDataDirectory + "/CreateIdentity_Template.csv";
					createIdentityDataFile=ManagerCasesTestDataDirectory+ "/CreateIdentity.csv";
				}
				else{
					createIdentityTemplateFile = createIdentityTestDataDirectory + "/CreateIdentity_Template.csv";
					createIdentityDataFile=createIdentityTestDataDirectory+ "/CreateIdentity.csv";
				}
				TestDataInterface.compileTwoRowDataTemplate(createIdentityTemplateFile, createIdentityDataFile);
		
				ArrayList<String> headers = Utility.getCSVRow(createIdentityDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(createIdentityDataFile, 0);
				int len = headers.size();
				String firstName=null,lastName= null,validFrom= null,validTo = null,emailId= null,empType= null,header,jobTitle;
				
				for (int i=0;i<len;i++){
					header= headers.get(i);
					System.out.println("heading "+ (i+1) +" "+ header);
					int index = Utility.getIndex(headers,header);
					for(ArrayList<String> userData : usersData) {
				
						switch (header.toLowerCase()) {
						case "firstname":
							if((AGlobalComponents.contractorToPermanentEmployeeConversion)||(AGlobalComponents.requestLocationAccessOthers))
								firstName = fName;
							else
								firstName = Utility.getIndexValue(userData, index);
							break;
						case "lastname":
							if((AGlobalComponents.contractorToPermanentEmployeeConversion)||(AGlobalComponents.requestLocationAccessOthers))
								lastName = lName;
							else
								lastName = Utility.getIndexValue(userData, index);
							break;
						case "validfrom":
							validFrom=Utility.getIndexValue(userData, index);
							break;
						case "validto":
							validTo=Utility.getIndexValue(userData, index);
							break;
						case "employeetype":
							empType = Utility.getIndexValue(userData, index);
							break;
						case "email":
							emailId = Utility.getIndexValue(userData, index);
							break;
						case "jobtitle":
							jobTitle = Utility.getIndexValue(userData, index);
							break;
						default: 
							logger.log(LogStatus.ERROR, "Failed: Field {" +header+"} Not Found ");
							throw new UnsupportedOperationException();
						}
					}
				}
				
				ByAttribute.setText("xpath", IdentityObjects.employeeTypeLnk, empType, "Enter employee Type");
				Utility.pause(5);
				ByAttribute.click("xpath", IdentityObjects.firstNameLnk, "Enter first Name");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.firstNameLnk, firstName, "Enter first Name");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.lastNameLnk, lastName, "Enter Last Name");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseBasicInfoSection, "collapse Basic Information Section");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.emailIdLnk, emailId, "Enter email Id");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseContactInfoSection, "collapse Contact Information Section");
				Utility.pause(2);
				ByAttribute.clearSetText("xpath", IdentityObjects.idmProfileUserIdTxt, firstName+"."+lastName, "Enter user id");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseOrganisationInfoSection, "collapse Organisation Information Section");
				Utility.pause(2);
//				ByAttribute.setText("xpath", IdentityObjects.validFromLnk, validFrom, "Enter valid From");
//				Utility.pause(2);


//				ByAttribute.setText("xpath", IdentityObjects.validToLnk, validTo, "Enter valid To");
				
			}
			catch(Exception e)
			{
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
	}
	
	
	
	
	public static void editProfileInfo() throws Throwable 
	{
		if(unhandledException==false)
		{
			
			System.out.println("********************Edit Profile Info*******************");
			try
			{
				String EmployeeCreationTemplateFile = createIdentityTestDataDirectory + "/EditIdentity_Template.csv";
				String EmployeeCreationDataFile=createIdentityTestDataDirectory+ "/EditIdentity.csv";
				TestDataInterface.compileTwoRowDataTemplate(EmployeeCreationTemplateFile, EmployeeCreationDataFile);
		
				ArrayList<String> headers = Utility.getCSVRow(EmployeeCreationDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(EmployeeCreationDataFile, 0);
				int len = headers.size();
				String jobTitle= null,header;
	                
				for (int i=0;i<len;i++){
					header= headers.get(i);
					System.out.println("heading "+ (i+1) +" "+ header);
					int index = Utility.getIndex(headers,header);
					for(ArrayList<String> userData : usersData) {
	        		
						switch (header.toLowerCase()) {
						case "jobtitle":
							jobTitle = Utility.getIndexValue(userData, index);
							break;
						default: 
							logger.log(LogStatus.ERROR, "Failed: Field {" +header+"} Not Found ");
							throw new UnsupportedOperationException();
						}
					}
				}
			
				ByAttribute.click("xpath", IdentityObjects.collapseBasicInfoSection, "collapse Basic Information Section");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseContactInfoSection, "collapse Contact Information Section");
				Utility.pause(2);
				String existingJobtitle = ByAttribute.getText("xpath", IdentityObjects.jobTitleLnk, "Get Job Title Value");
				if((existingJobtitle==null)||(existingJobtitle=="")) {
					System.out.println("No job title is assigned to the identity");
					logger.log(LogStatus.INFO, "No job title is assigned to the identity");
				}
				ByAttribute.setText("xpath", IdentityObjects.jobTitleLnk, jobTitle, "Enter job Title");
				ByAttribute.click("xpath", IdentityObjects.SaveBtn, "Click on save Button ");
				Utility.pause(20);
		
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
				
				WebElement finalJobTitle = driver.findElement(By.xpath("(//div[@class='x-grid-cell-inner '])[7]"));
				String jbTitle = finalJobTitle.getText();
				if(Utility.compareStringValues(jbTitle, jobTitle)){
					System.out.println("job title is successfully updated");
					logger.log(LogStatus.PASS, "job title is successfully updated");
				}
			}
			catch(Exception e)
			{
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	public static void fillDuplicateProfileInfo() throws Throwable 
	{
		if(unhandledException==false)
		{
			
			System.out.println("**************Fill Duplicate profile Info*****************");
			try{
			
				String EmployeeCreationTemplateFile = createIdentityTestDataDirectory + "/CreateDuplicateIdentity_Template.csv";
				String EmployeeCreationDataFile=createIdentityTestDataDirectory+ "/CreateDuplicateIdentity.csv";
				TestDataInterface.compileTwoRowDataTemplate(EmployeeCreationTemplateFile, EmployeeCreationDataFile);
		
				ArrayList<String> headers = Utility.getCSVRow(EmployeeCreationDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(EmployeeCreationDataFile, 0);
				int len = headers.size();
				String firstName= null,lastName= null,empType= null,header;
			
				for (int i=0;i<len;i++){
					header= headers.get(i);
					System.out.println("heading "+ (i+1) +" "+ header);
					int index = Utility.getIndex(headers,header);
					for(ArrayList<String> userData : usersData) {
						
						switch (header.toLowerCase()) {
						case "firstname":
							firstName = Utility.getIndexValue(userData, index);
							break;
						case "lastname":
							lastName = Utility.getIndexValue(userData, index);
							break;
						case "employeetype":
							empType = Utility.getIndexValue(userData, index);
							break;
						default: 
							logger.log(LogStatus.ERROR, "Failed: Field {" +header+"} Not Found ");
							throw new UnsupportedOperationException();
						}
					}
				}
			
				ByAttribute.setText("xpath", IdentityObjects.employeeTypeLnk, empType, "Enter employee Type");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.firstNameLnk, firstName, "Enter first Name");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.lastNameLnk, lastName, "Enter Last Name");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseBasicInfoSection, "collapse Basic Information Section");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseContactInfoSection, "collapse Contact Information Section");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseOrganisationInfoSection, "collapse Organisation Information Section");
			}
			catch(Exception e)
			{
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	
	public static void fillAccessesInfo() throws Throwable 
	{
		if(unhandledException==false)
		{
			index++;
			System.out.println("****************Fill Access Info******************");
			try{
				String accessName= null,validFrom= null,validTo = null,header,temp,accessTemplateFile,accessDataFile;
				Date date;
				
				accessTemplateFile = createIdentityTestDataDirectory + "/Accesses_Template.csv";
				accessDataFile=createIdentityTestDataDirectory+ "/Accesses.csv";
				
				TestDataInterface.compileTwoRowDataTemplate(accessTemplateFile, accessDataFile);
		
				ArrayList<String> headers = Utility.getCSVRow(accessDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(accessDataFile, 0);
				int len = headers.size();
		
				for (int i=0;i<len;i++){
					header= headers.get(i);
					System.out.println("heading "+ (i+1) +" "+ header);
					int index = Utility.getIndex(headers,header);
					for(ArrayList<String> userData : usersData) {
				
						switch (header.toLowerCase()) {
						case "accessname":
							accessName = Utility.getIndexValue(userData, index);
							break;
						case "validfrom":
							temp=Utility.getIndexValue(userData, index);
							if(!Utility.compareStringValues(temp, header)){
								date = new SimpleDateFormat("MM-dd-yy").parse(temp);
								validFrom = new SimpleDateFormat("M/d/yy hh:mm a").format(date);
							}
							break;
						case "validto":
							temp=Utility.getIndexValue(userData, index);
							if(!Utility.compareStringValues(temp, header)){
								date = new SimpleDateFormat("MM-dd-yy").parse(temp);
								validTo = new SimpleDateFormat("M/d/yy hh:mm a").format(date);
							}
							break;
						default: 
							logger.log(LogStatus.ERROR, "Failed: Field {" +header+"} Not Found ");
							throw new UnsupportedOperationException();
						}
					}
				}
				String addRecordsIcon = "(//a[normalize-space(text())='Click here to Add'])["+index+"]";
				ByAttribute.click("xpath", addRecordsIcon, "click on add icon to insert new access");
				Utility.pause(5);
		
				Actions action = new Actions(driver);		
				action.sendKeys(accessName);
				action.build().perform();
				Utility.pause(5);
				WebElement accessValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'"+accessName+"')]"));
				action.moveToElement(accessValue).click();
				action.build().perform();
				logger.log(LogStatus.INFO, "Access Value selected");
				Utility.pause(2);
		
				WebElement ele=driver.findElement(By.xpath("//span[text()='Description']"));
				ele.click();
		
//				WebElement validFromDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[1]"));
//				action.moveToElement(validFromDate).click();
//				action.build().perform();
//				String validFromDt="(//div[contains(@id,'baseDateTime')]//input[@placeholder='Select Valid From'])[2]";
//				ByAttribute.setText("xpath", validFromDt, validFrom, "Enter Valid From");
//				Utility.pause(2);
//				logger.log(LogStatus.INFO, "Entered valid from");
		

//				WebElement validToDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[2]"));
//				action.moveToElement(validToDate).click();
//				action.build().perform();
//				String validToDt =	"(//div[contains(@id,'baseDateTime')]//input[@placeholder='Select Valid To'])[2]";
//				ByAttribute.setText("xpath", validToDt, validTo, "Enter Valid TO");
//				Utility.pause(2);
//				logger.log(LogStatus.INFO, "Entered valid to");
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	public static void fillSystemsInfo() throws Throwable 
	{
		if(unhandledException==false)
		{
			System.out.println("*************Fill systems info**********");
			try{
				index++;
				String systemName= null,srcId= null,validTo = null,validFrom = null,header,temp,systemTemplateFile,systemDataFile;
				Date date;
				
				systemTemplateFile = createIdentityTestDataDirectory + "/Systems_Template.csv";
				systemDataFile=createIdentityTestDataDirectory+ "/Systems.csv";
				
				TestDataInterface.compileTwoRowDataTemplate(systemTemplateFile, systemDataFile);
				ArrayList<String> headers = Utility.getCSVRow(systemDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(systemDataFile, 0);
				int len = headers.size();
		
				for (int i=0;i<len;i++){
					header= headers.get(i);
					System.out.println("heading "+ (i+1) +" "+ header);
					int index = Utility.getIndex(headers,header);
					for(ArrayList<String> userData : usersData) {
			
						switch (header.toLowerCase()) {
						case "systemname":
							systemName = Utility.getIndexValue(userData, index);
							break;
						case "sourceid":
							srcId = Utility.getIndexValue(userData, index);
							break;
						case "validfrom":
							temp=Utility.getIndexValue(userData, index);
							if(!Utility.compareStringValues(temp, header)){
								date = new SimpleDateFormat("MM-dd-yy").parse(temp);
								validFrom = new SimpleDateFormat("M/d/yy hh:mm a").format(date);
							}
							break;
						case "validto":
							temp=Utility.getIndexValue(userData, index);
							if(!Utility.compareStringValues(temp, header)){
								date = new SimpleDateFormat("MM-dd-yy").parse(temp);
								validTo = new SimpleDateFormat("M/d/yy hh:mm a").format(date);
							}
							break;
						default: 
							logger.log(LogStatus.ERROR, "Failed: Field {" +header+"} Not Found ");
							throw new UnsupportedOperationException();
						}
					}
				}
				String addRecordsIcon = "(//a[normalize-space(text())='Click here to Add'])["+index+"]";
				ByAttribute.click("xpath", addRecordsIcon, "click on add icon to insert new system");
				
				Utility.pause(5);
		
				Actions action = new Actions(driver);
				action.sendKeys(systemName);
				action.build().perform();
				Utility.pause(5);
				WebElement systemValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'CCURE 9000')]"));
				action.moveToElement(systemValue).click();
				action.build().perform();
				logger.log(LogStatus.INFO, "System Name selected");
				Utility.pause(5);
		
				WebElement sourceId=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-td x-grid-cell-gridcolumn')])[3]"));
				action.moveToElement(sourceId).click();
				action.sendKeys("123");
				action.build().perform();
				logger.log(LogStatus.INFO, "Entered the sourceId");
        
				WebElement sourcId=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-td x-grid-cell-gridcolumn')])[3]"));
				action.moveToElement(sourcId).click();
				action.sendKeys(srcId);
				action.build().perform();
				logger.log(LogStatus.INFO, "Entered the sourceId");
        
//				WebElement validFromDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[3]"));
//				action.moveToElement(validFromDate).click();
//				action.build().perform();
//				String validFromDt="(//input[@placeholder='Select Valid From'])[2]";
//				ByAttribute.setText("xpath", validFromDt, validFrom, "Enter Valid From");
//				Utility.pause(2);
//				logger.log(LogStatus.INFO, "Entered valid from");
		

//				WebElement validToDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[4]"));
//				action.moveToElement(validToDate).click();
//				action.build().perform();
//				String validToDt =	"(//input[@placeholder='Select Valid To'])[2]";
//				ByAttribute.setText("xpath", validToDt, validTo, "Enter Valid TO");
//				Utility.pause(2);
//				logger.log(LogStatus.INFO, "Entered valid to");
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	public static void fillAssetsInfo() throws Throwable 
	{
		if(unhandledException==false)
		{
			
			System.out.println("*****************Fill Assets Info*****************");
			try{
				String assetCode= null,validTo = null,validFrom = null,header,temp,assetTemplateFile,assetDataFile;
				Date date;
				
								
				String addRecordsIcon = "(//a[normalize-space(text())='Click here to Add'])";
				ByAttribute.click("xpath", addRecordsIcon, "click on add icon to insert new access");
				Utility.pause(5);
				/*
				 * Verify Cancel button on Assets screen
				 */
		
				logger.log(LogStatus.INFO, "By Clicking on Cancel button , we will be navigated to Existing asset screen");
				ByAttribute.click("xpath", IdentityObjects.cancelAssetButtonLnk, "click on cancel button");
				logger.log(LogStatus.INFO, "Navigated to Existing asset screen");
				Utility.verifyElementPresent("//div[contains(@class,'x-column-header-text')]//span[text()='Asset']", "Existing asset screen",  false);
		
				/*
				 * verify Reset button on New asset screen
				 */
				ByAttribute.click("xpath", addRecordsIcon, "click on add icon to insert new access");
				Utility.pause(2);
				String dropdownArrow="(//span[text()='Asset Code']//ancestor::label//following::div[contains(@class,' x-form-trigger-default x-form-arrow-trigger')])[1]";
				ByAttribute.click("xpath", dropdownArrow, "click on dropdown to select asset code");
				Utility.pause(2);
		
				for(int i=0;i<10;i++){
					if(driver.findElements(By.xpath("//*[@class='idmlistitem']//span[text()='"+AGlobalComponents.assetCode+"']")).size()>0){
						break;
					}
					else
						Utility.pause(10);
				}
				String asstCode="//div[text()='"+assetCode+"']";
				ByAttribute.click("xpath", asstCode, " select asset code");
				Utility.pause(2);
				Utility.verifyElementPresent("//input[contains(@class,'x-form-required-field x-form-text x-form-text-default ') and @placeholder='Select Asset']", "Asset Code", false);
				System.out.println("Asset Code is filled");
			
				ByAttribute.click("xpath", IdentityObjects.resetButtonAssetScreen, " Click on Reset button on add new asset screen");
				Utility.verifyElementPresent("//input[contains(@class,'x-form-required-field x-form-text x-form-text-default ') and @placeholder='Select Asset']", "Asset Code", false);
				logger.log(LogStatus.PASS , "Fields are cleared after clicking reset button");
			
				/*
				 * navigating to new asset screen to add new asset
				 */
				ByAttribute.click("xpath", dropdownArrow, "click on dropdown to select asset code");
				Utility.pause(20);
		
				for(int i=0;i<10;i++){
					if(driver.findElements(By.xpath("//div[text()='"+assetCode+"']")).size()>0){
						break;
					}
					else
						Utility.pause(10);
				}
				ByAttribute.click("xpath", asstCode, " select asset code");
				Utility.pause(2);
		
				String validFromDt="(//div[contains(@id,'baseDateTime')]//input[@placeholder='Select Valid From'])[2]";
				String validToDt =	"(//div[contains(@id,'baseDateTime')]//input[@placeholder='Select Valid To'])[2]";	
//				ByAttribute.setText("xpath", validFromDt, validFrom, "Enter Valid From");
//				Utility.pause(2);
//				ByAttribute.setText("xpath", validToDt, validTo, "Enter Valid TO");
//				Utility.pause(2);
			
				ByAttribute.click("xpath", IdentityObjects.confirmButton, " Click confirm ");
				Utility.pause(5);
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	public static void fillAssetsInfo(String assetCode) throws Throwable 
	{
		if(unhandledException==false)
		{
			
			System.out.println("*****************Fill Assets Info*****************");
			try{
				System.out.println("******************assign asset to the user**************************");
				logger.log(LogStatus.INFO,"**************assign asset to the user**************************");		
								
				String addRecordsIcon = "(//a[normalize-space(text())='Click here to Add'])";
				ByAttribute.click("xpath", addRecordsIcon, "click on add icon to insert new access");
				Utility.pause(5);
				
				ByAttribute.clearSetText("xpath", IdentityObjects.idmAddAssetSelectDdn, AGlobalComponents.assetName, "Enter the asset ");
				Thread.sleep(1000);
				ByAttribute.click("xpath", "//*[@class='idmlistitem']//span[text()='"+AGlobalComponents.assetName+"']", " select asset code");
				Utility.pause(2);
				
				Utility.verifyElementPresentByScrollView(IdentityObjects.idmAddAssetStatusDdn, "status field", false, false);
				ByAttribute.clearSetText("xpath", IdentityObjects.idmAddAssetStatusDdn, "Active", "Enter the status of the asset ");
				Thread.sleep(1000);
				ByAttribute.click("xpath", "//li[contains(@class,'x-boundlist-item') and text()='Active']", " select status");
				Utility.pause(2);
		
				ByAttribute.click("xpath", IdentityObjects.idmAddAssetSaveBtn, " Click Save to add the asset ");
				Utility.pause(5);
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	public static void fillPrerequisitesInfo() throws Throwable
	{
	if(unhandledException==false)
	{
	index++;
	System.out.println("**************Fill prerequisite info***********");
	try{
	String type= null,prerequisite=null,validFrom= null,validTo = null,header,prerequisiteTemplateFile,prerequisiteDataFile;

	prerequisiteTemplateFile = createIdentityTestDataDirectory + "/Prerequisite_Template.csv";
	prerequisiteDataFile=createIdentityTestDataDirectory+ "/Prerequisite.csv";

	TestDataInterface.compileTwoRowDataTemplate(prerequisiteTemplateFile, prerequisiteDataFile);

	ArrayList<String> prerequisiteTypeList=Utility.getCSVColumnPerHeader(prerequisiteDataFile, "Type");
	ArrayList<String> prerequisiteNameList=Utility.getCSVColumnPerHeader(prerequisiteDataFile, "Prerequisite");
	ArrayList<String> prerequisiteValidFromList=Utility.getCSVColumnPerHeader(prerequisiteDataFile, "ValidFrom");
	ArrayList<String> prerequisiteValidToList=Utility.getCSVColumnPerHeader(prerequisiteDataFile, "ValidTo");

	for(int i=0;i<prerequisiteNameList.size();i++) {
	Actions action = new Actions(driver);
	if(i==0) {
	String addRecordsIcon = "(//a[normalize-space(text())='Click here to Add'])";
	WebElement addIcon = driver.findElement(By.xpath(addRecordsIcon));
	action.moveToElement(addIcon).click();
	}
	else
	ByAttribute.click("xpath",IdentityObjects.addRowLnk,"Click on Add icon to insert new training");

	action.sendKeys(prerequisiteTypeList.get(i));
	action.build().perform();
	Utility.pause(5);
	WebElement typeValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct x-unselectable x-scroller')]//li[text()='"+prerequisiteTypeList.get(i)+"']"));
	action.moveToElement(typeValue).click();
	action.build().perform();
	logger.log(LogStatus.INFO, "prerequisite type Value selected");
	Utility.pause(4);

	action.sendKeys(Keys.TAB);
	action.sendKeys(prerequisiteNameList.get(i));
	action.build().perform();
	Utility.pause(5);
	WebElement prerequisiteValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct x-unselectable x-scroller')]//li[text()='"+prerequisiteNameList.get(i)+"']"));
	action.moveToElement(prerequisiteValue).click();
	action.build().perform();
	logger.log(LogStatus.INFO, "Entered the Prerequisite Name");

	// WebElement validFromDate=driver.findElement(By.xpath("(//div[text()='"+prerequisiteTypeList.get(prerequisiteNameList.indexOf(name))+"']/parent::td[contains(@class,'x-grid-cell-baseComboColumn')]/following-sibling::td//div[@class='x-grid-cell-inner '])[2]"));
	// action.moveToElement(validFromDate).click();
	action.sendKeys(Keys.TAB);
	action.sendKeys(prerequisiteValidFromList.get(i));
	action.build().perform();
	Utility.pause(5);
	logger.log(LogStatus.INFO, "Entered prerequiste valid from");

	// WebElement validToDate=driver.findElement(By.xpath("(//div[text()='"+prerequisiteTypeList.get(prerequisiteNameList.indexOf(name))+"']/parent::td[contains(@class,'x-grid-cell-baseComboColumn')]/following-sibling::td//div[@class='x-grid-cell-inner '])[3]"));
	// action.moveToElement(validToDate).click();
	action.sendKeys(Keys.TAB);
	action.sendKeys(prerequisiteValidToList.get(i));
	action.build().perform();
	logger.log(LogStatus.INFO, "Entered prerequiste valid to");
	}
	}
	catch(Exception e){
	String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
	Utility.recoveryScenario(nameofCurrMethod, e);
	}
	}
	}

	private static void verifyRoleReconData() throws Throwable{
		if(unhandledException==false)
		{
			System.out.println("***************************** Verify Data Fetched From Recon *********************************");
			try
			{
				logger.log(LogStatus.INFO , "Recon remediation screen loaded with role recon data");
				System.out.println("Recon remediation screen loaded with role recon data");
		
				/*
				 * Select the view for role recon data
				 */
				logger.log(LogStatus.INFO,"Selecting the appropriate view to check role recon data");
				WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
				settingsIcon.click();
				ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for role recon data");
				ByAttribute.click("xpath",ReconObjects.roleReconViewLnk, "click on role recon view");
				Utility.pause(10);
	    
				WebElement desc = driver.findElement(By.xpath("(//td[contains(@class,'x-grid-td x-grid-cell-gridcolumn')])[11]//div"));
				String description = desc.getText();
				
				WebElement syncId = driver.findElement(By.xpath("(//td[contains(@class,'x-grid-td x-grid-cell-gridcolumn')])[12]//div"));
				String syncID = syncId.getText();
			
				logger.log(LogStatus.INFO, "Navigating to Access tab to verify recon data on UI. Access name is : "+ description);
				verifyRoleReconDataFromUI(description);
		
				logger.log(LogStatus.INFO, "verify recon data from DB using sync id : " +syncID);
				String query = "select count(*) from aehscnew.stg_role_data where sync_id = '"+syncID+"'";
				verifyReconDataFromDB(query);
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	private static void verifyUserReconData() throws Throwable{
		if(unhandledException==false)
		{
			System.out.println("***************************** Verify User Data Fetched From Recon *********************************");
			try
			{
				logger.log(LogStatus.INFO , "Recon remediation screen loaded with user recon data");
				System.out.println("Recon remediation screen loaded with user recon data");
		
				/*
				 * Select the view for role recon data
				 */

				logger.log(LogStatus.INFO,"Selecting the appropriate view to check user recon data");
				WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
				settingsIcon.click();
				ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for user recon data");

				ByAttribute.click("xpath",ReconObjects.userReconViewLnk, "click on user recon view");
				Utility.pause(10);
	    

				WebElement userId = driver.findElement(By.xpath("(//td[contains(@class,'x-grid-td x-grid-cell-gridcolumn')])[10]//div"));
				String usrId = userId.getText();
				if(usrId!=null) {
					logger.log(LogStatus.PASS, "UserId" +usrId+ "successfully reconciled");
				}
				else {
					logger.log(LogStatus.FAIL, "There might exists error for the userId "+usrId);
				}
				
				WebElement syncId = driver.findElement(By.xpath("(//td[contains(@class,'x-grid-td x-grid-cell-gridcolumn')])[9]//div"));
				String syncID = syncId.getText();
			
				if(!AGlobalComponents.createRequest) {
					logger.log(LogStatus.INFO, "Navigating to Identity tab to verify recon data on UI");
					verifyUserReconDataFromUI(usrId);
					logger.log(LogStatus.INFO, "verify recon data from DB");
					String query = "select count(*) from aehscnew.stg_user_data where sync_id = '"+syncID+"' and int_status='0'";
					verifyReconDataFromDB(query);
				}
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	private static void verifyTrialReconData() throws Throwable{
		if(unhandledException==false)
		{
			System.out.println("***************************** Verify Data Fetched From Recon *********************************");
			try
			{
				logger.log(LogStatus.INFO , "Recon remediation screen loaded with role recon data");
				System.out.println("Recon remediation screen loaded with role recon data");
		
				/*
				 * Select the appropriate view for  recon data
				 * 
				 */
				logger.log(LogStatus.INFO,"Selecting the appropriate view to check role recon data");
				WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
				settingsIcon.click();
				ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for role recon data");
				if(AGlobalComponents.roleRecon)
					ByAttribute.click("xpath",ReconObjects.roleReconViewLnk, "click on role recon view");
				if(AGlobalComponents.userRecon)
					ByAttribute.click("xpath",ReconObjects.userReconViewLnk, "click on role recon view");
				Utility.pause(10);
	    
				WebElement syncId = driver.findElement(By.xpath("(//td[contains(@class,'x-grid-td x-grid-cell-gridcolumn')])[12]//div"));
				String syncID = syncId.getText();
				String query = "";
			
				logger.log(LogStatus.INFO, "verify recon data from DB");
				if(AGlobalComponents.roleRecon)
					query = "select count(*) from aehscnew.stg_role_data where int_status='1' and sync_type='TRIAL'";
				if(AGlobalComponents.userRecon)
					query = "select count(*) from aehscnew.stg_user_data where int_status='1' and sync_type='TRIAL'";
				
				verifyReconDataFromDB(query);
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}

	public static void verifyReconDataFromDB(String query) throws Throwable {
		if(unhandledException==false)
		{
			
			System.out.println("**********Verify Recon Data from DB*********");
			try{
					
					ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
					String dbRecords = rs.get(0).get(0);
					logger.log(LogStatus.INFO, "total no. of records in staging table after trial job are : " +dbRecords);
					if(Utility.compareStringValues(dbRecords, activeReconRecords)){
						logger.log(LogStatus.PASS, "total no. of active records are equivalent to data stored in staging table :" + dbRecords);
					}
					else{
						logger.log(LogStatus.FAIL, "db validation after trail job execution failed");
					}
				}
				catch(Exception e)	{
					String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
					Utility.recoveryScenario(nameofCurrMethod, e);
				}
		}
	}

	public static void verifyRoleReconDataFromUI(String description) throws Throwable {
		if(unhandledException==false)
		{
			System.out.println("************Verify Recon Data from UI**********");
			try{
					ByAttribute.mouseHover("xpath", AccessObjects.accessTabLnk, "Mouse Hover on Access tab");
					Utility.pause(5);
					ByAttribute.click("xpath", AccessObjects.manageAccessLnk, "Click on Manage Access ");
					Utility.pause(10);
		
					WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
					settingsIcon.click();
					ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for role recon data");
					ByAttribute.click("xpath",ReconObjects.roleReconViewLnk, "click on role recon view");
					Utility.pause(10);
	    
					ByAttribute.click("xpath", AccessObjects.filterIconLnk, "Click on Filter icon ");
					Utility.pause(3);
			//		ByAttribute.click("xpath", AccessObjects.addFilterLnk, "Click on Add icon to enter the filter");
			//		Utility.pause(2);
					ByAttribute.click("xpath", AccessObjects.enterFieldNameToFilter, "click to enter field name for Filtering");
					Utility.pause(2);
					ByAttribute.setText("xpath", AccessObjects.enterFieldNameToFilter,"Name", "Enter the field name for Filtering");
					Utility.pause(2);
					ByAttribute.click("xpath", AccessObjects.clickFieldValue1, "click to enter the value");
					Utility.pause(2);
					ByAttribute.setText("xpath", AccessObjects.enterFieldValue1,description, "Enter the first field value for Filtering");
					Utility.pause(2);
		
					Actions action = new Actions(driver);
					action.sendKeys(Keys.ENTER);
					action.build().perform();
					Utility.pause(5);
		
					if(driver.findElements(By.xpath("//div[text()='"+description+"']")).size()>0){
						int size = driver.findElements(By.xpath("//div[text()='"+description+"']")).size();
						boolean flag = false;
						if(size>2){
							for (int i=0;i<size/2 && (!flag) ;i++){
								WebElement sourceId = driver.findElement(By.xpath(AccessObjects.sourceIdLnk));
								String srcId = sourceId.getText();
								if(Utility.checkIfStringIsNotNull(srcId)){
									ByAttribute.click("xpath", AccessObjects.addFilterLnk, "Click on Add icon to enter the filter");
									Utility.pause(2);
									ByAttribute.click("xpath", AccessObjects.enterFieldNameToFilter, "click to enter field name for Filtering");
									Utility.pause(2);
									ByAttribute.setText("xpath", AccessObjects.enterFieldNameToFilter,"Source ID", "Enter the field name for Filtering");
									Utility.pause(2);
									ByAttribute.click("xpath", AccessObjects.enterFieldValue1, "click to enter the value");
									Utility.pause(2);
									ByAttribute.setText("xpath", AccessObjects.enterFieldValue1,srcId, "Enter the value for Filtering");
									Utility.pause(2);
								
									action = new Actions(driver);
									action.sendKeys(Keys.ENTER);
									action.build().perform();
									Utility.pause(2);
									flag = true;
								}
								
							}
						}
						logger.log(LogStatus.PASS, "Recon data is present on UI");
						Utility.verifyElementPresent("//div[text()='"+description+"']", "Role Name",  false);
						System.out.println("Recon data is present on UI");
						
					}
					else{
						logger.log(LogStatus.FAIL, "Recon data not present on UI");
					}
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	public static void verifyUserReconDataFromUI(String userId) throws Throwable {
		if(unhandledException==false)
		{
			System.out.println("************Verify User Recon Data from UI**********");
			try{
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
		
					WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
					settingsIcon.click();
					ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for role recon data");
					ByAttribute.click("xpath",ReconObjects.userReconViewLnk, "click on user recon view");
					Utility.pause(10);
	    
					ByAttribute.click("xpath", AccessObjects.filterIconLnk, "Click on Filter icon ");
					Utility.pause(3);
					ByAttribute.click("xpath", AccessObjects.addFilterLnk, "Click on Add icon to enter the filter");
					Utility.pause(2);
					ByAttribute.click("xpath", AccessObjects.enterFieldNameToFilter, "click to enter field name for Filtering");
					Utility.pause(2);
					ByAttribute.setText("xpath", AccessObjects.enterFieldNameToFilter,"User ID", "Enter the field name for Filtering");
					Utility.pause(2);
					ByAttribute.click("xpath", AccessObjects.clickFieldValue1, "click to enter the value");
					Utility.pause(2);
					ByAttribute.setText("xpath", AccessObjects.enterFieldValue1,userId, "Enter the first field value for Filtering");
					Utility.pause(2);
		
					Actions action = new Actions(driver);
					action.sendKeys(Keys.ENTER);
					action.build().perform();
					Utility.pause(5);
		
					if(driver.findElements(By.xpath("//div[text()='"+userId+"']")).size()>0){
						logger.log(LogStatus.PASS, "Recon data is present on UI");
						Utility.verifyElementPresent("//div[text()='"+userId+"']", "User ID",  false);
						System.out.println("User id is present on UI");
						
					}
					else{
						logger.log(LogStatus.FAIL, "Recon data not present on UI");
					}
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}

	
	public static void rerunReconRecord() throws Throwable {
		if(unhandledException==false)
		{
			AGlobalComponents.rerunReconJob= true;

			logger.log(LogStatus.INFO ,  "Rerun the created recon record");
			System.out.println("*******************Rerun recon record************************");
			try{
				ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.reconSetUpLnk, "Click on Recon Setup ");
				Utility.pause(40);
			
					    
				/*
				 * Filter the records in Recon set up on basis of entity type
				 */
				ByAttribute.click("xpath", ReconObjects.filterIconLnk, "Click on Filter icon ");
				Utility.pause(3);
				ByAttribute.click("xpath", ReconObjects.enterFieldName1ToFilter, "click to enter field name for Filtering");
				Utility.pause(2);
				ByAttribute.setText("xpath", ReconObjects.enterFieldName1ToFilter,"Entity", "Enter the field name for Filtering");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.clickFieldValue1, "click to enter the value");
				Utility.pause(2);
				WebElement filterValue = driver.findElement(By.xpath(ReconObjects.enterFieldValue1));
				Actions action = new Actions (driver);
				action.moveToElement(filterValue).click().build().perform();;
				action.sendKeys(entityType).build().perform();
				Utility.pause(20);
							
			//	searching for recon job which we want to rerun
			
				WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchBarInRecon));
				searchBar.click();
				Utility.pause(20);
				ByAttribute.setText("xpath", ReconObjects.searchBarInRecon, AGlobalComponents.jobName, "Searching recon job on recon set up screen to delete ");
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(4);
				String jobNameLocator = "//div[text()='"+AGlobalComponents.jobName+"']";
			
				if(Utility.verifyElementPresentReturn(jobNameLocator, AGlobalComponents.jobName, true, false) ){
					logger.log(LogStatus.INFO,"Recon job found to rerun");
					ByAttribute.click("xpath", ReconObjects.rerunIconReconSetup, "click on rerun icon to rerun the job");
					String endDate=getEndDate(AGlobalComponents.jobName);
					Calendar c = Calendar.getInstance();
					DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
					Date date = new Date();
//					String currentDate= dateFormat.format(date);
					c.setTime(dateFormat.parse(endDate));

					//Number of Days to minus
//					c.add(Calendar.DAY_OF_MONTH, -2);  
					//Date after subtracting the days to the given date
//					String temp = dateFormat.format(c.getTime());  


				
//					date = new SimpleDateFormat("MM-dd-yy").parse(temp);
					endDate = new SimpleDateFormat("M/d/yy hh:mm a").format(date); 
						
					ByAttribute.click("xpath", ReconObjects.endDateToRerunTheJob, "Click to enter end date");
					ByAttribute.setText("xpath", ReconObjects.endDateToRerunTheJob, endDate, "Enter end date to rerun the job");
					WebElement confirmButton = driver.findElement(By.xpath(ReconObjects.confirmButton));
					confirmButton = driver.findElement(By.xpath(ReconObjects.confirmButton));
					action.moveToElement(confirmButton).click();
					action.build().perform();
					Utility.pause(5);
					logger.log(LogStatus.PASS,"reran the job successfully");
				}
				else{
					logger.log(LogStatus.INFO,"Recon job not found to rerun");
				}
				checkJobInReconMonitor();
							
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}
	
	public static void deleteReconRecord() throws Throwable {
		if(unhandledException==false)
		{

			logger.log(LogStatus.INFO ,  "Deleting the created recon record");
			System.out.println("*******************Delete recon record************************");
			try{
				ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.reconSetUpLnk, "Click on Recon Setup ");
				Utility.pause(40);
			
				logger.log(LogStatus.INFO,"Selecting the appropriate view on recon setup screen");
				WebElement settingsIcon = driver.findElement(By.xpath(ReconObjects.settingsIcon));
				settingsIcon.click();
				ByAttribute.mouseHover("xpath", ReconObjects.selectViewLnk, "select the view for role recon data");
				ByAttribute.click("xpath",ReconObjects.ReconViewLnk, "click on recon view");
				Utility.pause(20);


		    
				/*
				 * Filter the records in Recon set up on basis of entity type
				 */
				ByAttribute.click("xpath", ReconObjects.filterIconLnk, "Click on Filter icon ");
				Utility.pause(3);
				ByAttribute.click("xpath", ReconObjects.enterFieldName1ToFilter, "click to enter field name for Filtering");
				Utility.pause(2);
				ByAttribute.setText("xpath", ReconObjects.enterFieldName1ToFilter,"Entity", "Enter the field name for Filtering");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.clickFieldValue1, "click to enter the value");
				Utility.pause(2);
				WebElement filterValue = driver.findElement(By.xpath(ReconObjects.enterFieldValue1));
				Actions action = new Actions (driver);
				action.moveToElement(filterValue).click().build().perform();;
				action.sendKeys(entityType).build().perform();
				Utility.pause(20);
							
			//	searching for recon job to be deleted
			
				WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchBarInRecon));
				searchBar.click();
				Utility.pause(20);
				ByAttribute.setText("xpath", ReconObjects.searchBarInRecon, AGlobalComponents.jobName, "Searching recon job on recon set up screen to delete ");
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(4);
				String jobNameLocator = "//div[text()='"+AGlobalComponents.jobName+"']";
			
				if(Utility.verifyElementPresentReturn(jobNameLocator, AGlobalComponents.jobName, true, false) ){
					logger.log(LogStatus.INFO,"Recon job found for deletion");
					ByAttribute.click("xpath", ReconObjects.deleteIconReconSetup, "click on minus icon to delete the record");
					ByAttribute.click("xpath", ReconObjects.submitButtonLnk, "Click submit after deleting the record");
					Utility.pause(5);
					ByAttribute.click("xpath", ReconObjects.confirmPopUpLnk,"click confirm button on popup");
					Utility.pause(10);
					logger.log(LogStatus.INFO,"Record deleted");
				}
				else{
					logger.log(LogStatus.INFO,"Recon job not found for deletion");
				}
				verifyDeletedReconDataFromDB(AGlobalComponents.jobName);	
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}

	public static void verifyDeletedReconDataFromDB(String jobName) throws Throwable {
		if(unhandledException==false)
		{
			
			System.out.println("**********Verify deleted Recon Data from DB*********");
			try{
					String query = "select int_status from aehscnew.recon_config where description = '"+jobName+"'";
					ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
					String status = rs.get(0).get(0);
					logger.log(LogStatus.INFO, "Int status in db for job : "+jobName+ " is : " +status);
					if(Utility.compareStringValues(status, "3")){
						logger.log(LogStatus.PASS, "int status obtained fron DB is 3 , which means job " +jobName + " is deleted.");
					}
					else{
						logger.log(LogStatus.FAIL, "unable to get the int status of job " +jobName + " from db");
					}
					
				}
				catch(Exception e)	{
					String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
					Utility.recoveryScenario(nameofCurrMethod, e);
				}
		}
	}
	public static void executeTrialReconjob() throws Throwable {
		if(unhandledException==false)
		{
			System.out.println("***************************** Set up Trial Recon Job *********************************");
			try
			{
				AGlobalComponents.trialReconJob = true;
				String reconDataFile = reconTestDataDirectory + "/TrialRecon.csv";
				ArrayList<String> connectorNames=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "ConnectorName");
				ArrayList<String> entityTypes=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "EntityType");
				ArrayList<String> scheduleTypes=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "ScheduleType");
				ArrayList<String> preFeedRule=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "PreFeedRule");
				ArrayList<String> createRequest=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "CreateRequest");
				
					
				for(int i=0;i<connectorNames.size();i++) {
					entityType=entityTypes.get(i);
					initiateReconJob(connectorNames.get(i),scheduleTypes.get(i),preFeedRule.get(i),createRequest.get(i));
					checkJobInReconMonitor();
				}
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}	
		
	}

	
	public static void searchInvalidTermOnReconMonitor(String invalidTerm) throws Throwable {
		try{
			
			ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
			Utility.pause(3);
			ByAttribute.click("xpath", ReconObjects.reconMonitorLnk,"click on Recon Monitor");
			Utility.pause(20);
			WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchInReconMonitor));
			searchBar.click();
			ByAttribute.setText("xpath", ReconObjects.searchInReconMonitor, invalidTerm, "Searching invalid term on recon monitor screen");
			Actions action = new Actions (driver);
			action.sendKeys(Keys.ENTER).build().perform();
			if(driver.findElements(By.xpath("//div[contains(@class,'x-grid-checkcolumn')]")).size()!=0) {
				if(!driver.findElement(By.xpath("//div[contains(@class,'x-grid-checkcolumn')]")).isSelected()) {
				logger.log(LogStatus.PASS, "No element is selected");
				}
			}
			
		}
		catch(Exception e)
		{		
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
			Utility.recoveryScenario(nameofCurrMethod, e);
		}
		}
	
	
	public static void deleteMultipleReconRecords() throws Throwable {
		if(unhandledException==false)
		{
			System.out.println("***************************Deleting multiple recon jobs *********************************");
			try
			{
				for (int i=0 ; i<2 ; i++){
					
					String reconDataFile = reconTestDataDirectory + "/DelMultiReconRecords.csv";
					ArrayList<String> connectorNames=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "ConnectorName");
					ArrayList<String> entityTypes=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "EntityType");
					ArrayList<String> scheduleTypes=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "ScheduleType");
					ArrayList<String> preFeedRule=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "PreFeedRule");
					ArrayList<String> createRequest=TestDataEngine.getCSVColumnPerHeader(reconDataFile, "CreateRequest");
					
					for(int j=0;j<connectorNames.size();j++) {
						entityType=entityTypes.get(j);
						initiateReconJob(connectorNames.get(j),scheduleTypes.get(j),preFeedRule.get(i),createRequest.get(i));
						jobNames.add(i, AGlobalComponents.jobName);
					}
				}
				deleteMultipleRecords(jobNames);
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}	
		
	}

	
	public static void validateDownloadFunctionality() throws Throwable {
		if(unhandledException==false) {
			try {
			if(Utility.verifyElementPresentReturn(ReconObjects.downloadLink, "Recon Monitor download Link", true, false)) {
				System.out.println("download icon is present");
				logger.log(LogStatus.INFO,"Download icon is present in Recon Monitor");
				ByAttribute.click("xpath", ReconObjects.filterExpand, "Removing applied filter");
				WebDriverWait wait = new WebDriverWait(driver, 20, 500);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@id,'loadmask') and contains(@class,'x-mask-msg-text')]")));
				ReadDataFromPropertiesFile.clearExistingDownloadFiles("Recon Monitor");
				ByAttribute.click("xpath", ReconObjects.downloadLink, "clicking on download link");
				Utility.pause(30);
				if (ReadDataFromPropertiesFile.checkWhetherFileIsDownloaded("Recon Monitor")) {
					logger.log(LogStatus.PASS, "Download functionaity verified");
				}
				else {
					logger.log(LogStatus.FAIL, "Unable to download file");
				}
				validateDownloadedData();
				}
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
			}
		}
	
	public static void validateDownloadedData() throws Throwable {
		if(unhandledException==false) {
			try {
			String elementsNumber = ByAttribute.getText("xpath", ReconObjects.reconMonitorElements, "getting Recon Monitor Grid data");
			String number=elementsNumber.split(":")[1].trim();
			System.out.println(number);
			 String folderName = System.getProperty("user.dir")+ "\\csv Download\\";
			 File[] listFiles = new File(folderName).listFiles();
			 String fileOrg = "None";
			 for (int i = 0; i < listFiles.length; i++) {
	
			     if (listFiles[i].isFile()) {
			         String fileName = listFiles[i].getName();
			         if (fileName.startsWith("Recon Monitor")
			                 && fileName.endsWith(".xlsx")) {
			             System.out.println("found file" + " " + fileName);
			             fileOrg = fileName;
			             break;
			         }
			     }
			 }
	
			int numbercsv = ReadDataFromPropertiesFile.countLine(System.getProperty("user.dir")+ "\\csv Download\\"+fileOrg);
			 String str1 = Integer.toString(numbercsv); 
			 assertEquals(str1,number);
			 logger.log(LogStatus.PASS, "Download data verified");
			
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}
	
	public static void validateSettingsFunctionality() throws Throwable {
		if(unhandledException==false) {
			try {
			
			ByAttribute.click("xpath", ReconObjects.settingsIcon, "clicking on settings icon of recon monitor");
			Utility.verifyElementPresent(ReconObjects.selectViewLnk, "Select View",  false); 
			Utility.verifyElementPresent(ReconObjects.createNewView, "Create New View",  false); 
		
			ByAttribute.click("xpath",ReconObjects.createNewView,"clicking on create new view option of Settings icon");
			Utility.verifyElementPresent(ReconObjects.saveLayout, "Save Current Layout Label ",false); 
			Utility.verifyElementPresent(ReconObjects.nameInSettingsView, "Name field Verified",false); 
			Utility.verifyElementPresent(ReconObjects.preferredLableInSettingsView, "Preferred checkbox",false); 
			Utility.verifyElementPresent(ReconObjects.sharedLabelInSettingsVew, "Shared checkbox ",false); 
			Utility.verifyElementPresent(ReconObjects.closeInSettingsView, "Close button",false); 
			Utility.verifyElementPresent(ReconObjects.cancelInSettingsView, "Cancel button",false);
			Utility.verifyElementPresent(ReconObjects.confirmButton, "Confirm button",false); 
				
			ByAttribute.click("xpath",ReconObjects.closeInSettingsView , "Clicking on close icon of settings view");
			Utility.verifyElementNotPresent(ReconObjects.saveLayout, "save layout on create new view", false);
	
			ByAttribute.click("xpath", ReconObjects.settingsIcon, "clicking on settings icon of recon monitor");
			ByAttribute.click("xpath",ReconObjects.createNewView,"clicking on create new view option of Settings icon");
			ByAttribute.click("xpath",ReconObjects.cancelInSettingsView , "Clicking on cancel button of settings view");
			}

			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
		
	}
	
	
	public static void deleteMultipleRecords(ArrayList<String> jobNames) throws Throwable {
		if(unhandledException==false){
			
			System.out.println("*******************Delete multiple recon records************************");
			try{	
		
				for (int i=0;i<jobNames.size();i++){
					String jbName = jobNames.get(i);
					WebElement minusIcon = driver.findElement(By.xpath("//div[text()='"+jbName+"']/parent::td/preceding-sibling::td//div[contains(@class,'aegrid-rowMinus')]"));
					Actions action = new Actions(driver);
					action.click(minusIcon).build().perform();
					Utility.pause(2);
					
					
				}
				ByAttribute.click("xpath", ReconObjects.submitButtonLnk,"submit the request");
				Utility.pause(5);
				ByAttribute.click("xpath", ReconObjects.confirmPopUpLnk,"click confirm button on popup");
				Utility.pause(10);
				logger.log(LogStatus.PASS, "deleted the selected records from UI");
				for (int i=0;i<jobNames.size();i++){
					String jbName = jobNames.get(i);
					verifyDeletedReconDataFromDB(jbName)	;
					
				}
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
	}
	
	public static void searchInvalidTermOnReconSetup(String invalidTerm) throws Throwable {

		try{
			
			ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
			Utility.pause(3);
			ByAttribute.click("xpath", ReconObjects.reconSetUpLnk,"click on Recon Monitor");
			Utility.pause(20);
			WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchInReconSetup));
			searchBar.click();
			ByAttribute.setText("xpath", ReconObjects.searchInReconSetup, invalidTerm, "Searching invalid term on recon monitor screen");
			Actions action = new Actions (driver);
			action.sendKeys(Keys.ENTER).build().perform();
			if(driver.findElements(By.xpath("//div[contains(@class,'x-grid-checkcolumn')]")).size()!=0) {
				if(!driver.findElement(By.xpath("//div[contains(@class,'x-grid-checkcolumn')]")).isSelected()) {
				logger.log(LogStatus.PASS, "No element is selected");
				}
			}
			
		}
		catch(Exception e)
		{		
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
			Utility.recoveryScenario(nameofCurrMethod, e);
		}
		
	}
	

	public static void searchInvalidTermOnReconRemediation(String invalidTerm) throws Throwable {


		try{
			
			ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
			Utility.pause(3);
			ByAttribute.click("xpath", ReconObjects.reconRemediationLnk,"click on Recon Remediation");
			Utility.pause(20);
			WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchInReconRemediation));
			searchBar.click();
			ByAttribute.setText("xpath", ReconObjects.searchInReconRemediation, invalidTerm, "Searching invalid term on recon monitor screen");
			Actions action = new Actions (driver);
			action.sendKeys(Keys.ENTER).build().perform();
			if(driver.findElements(By.xpath("//div[contains(@class,'x-grid-checkcolumn')]")).size()!=0) {
				if(!driver.findElement(By.xpath("//div[contains(@class,'x-grid-checkcolumn')]")).isSelected()) {
				logger.log(LogStatus.PASS, "No element is selected");
				}
			}
			
		}
		catch(Exception e)
		{		
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
			Utility.recoveryScenario(nameofCurrMethod, e);
		}
		
	
	}

	public static void validateDownloadFunctionalityInReconRemediation() throws Throwable {

		if(unhandledException==false) {
			try {
			if(Utility.verifyElementPresentReturn(ReconObjects.downloadLink, "Recon Remediation download Link", true, false)) {
				System.out.println("download icon is present");
				logger.log(LogStatus.INFO,"Download icon is present in Recon Remediation");
				WebDriverWait wait = new WebDriverWait(driver, 20, 500);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@id,'loadmask') and contains(@class,'x-mask-msg-text')]")));
				ReadDataFromPropertiesFile.clearExistingDownloadFiles("Recon Remediation");
				ByAttribute.click("xpath", ReconObjects.downloadLink, "clicking on download link");
				Utility.pause(30);
				if (ReadDataFromPropertiesFile.checkWhetherFileIsDownloaded("Recon Remediation")) {
					logger.log(LogStatus.PASS, "Download functionaity verified");
				}
				else {
					logger.log(LogStatus.FAIL, "Unable to download file");
				}
				validateDownloadedDataForReconRemediation();
				}
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
			}
		
	}

	private static void validateDownloadedDataForReconRemediation() throws Throwable {

		if(unhandledException==false) {
			try {
			String elementsNumber = ByAttribute.getText("xpath", ReconObjects.reconMonitorElements, "getting Recon Monitor Grid data");
			String number=elementsNumber.split(":")[1].trim();
			System.out.println(number);
			 String folderName = System.getProperty("user.dir")+ "\\csv Download\\";
			 File[] listFiles = new File(folderName).listFiles();
			 String fileOrg = "None";
			 for (int i = 0; i < listFiles.length; i++) {
	
			     if (listFiles[i].isFile()) {
			         String fileName = listFiles[i].getName();
			         if (fileName.startsWith("Recon Remediation")
			                 && fileName.endsWith(".xlsx")) {
			             System.out.println("found file" + " " + fileName);
			             fileOrg = fileName;
			             break;
			         }
			     }
			 }
	
			int numbercsv = ReadDataFromPropertiesFile.countLine(System.getProperty("user.dir")+ "\\csv Download\\"+fileOrg);
			 String str1 = Integer.toString(numbercsv); 
			 assertEquals(str1,number);
			 logger.log(LogStatus.PASS, "Download data verified");
			
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	public static void validateIdentityData() throws Throwable {
		String userDataFile=reconTestDataDirectory+"/Identity.csv";
		ArrayList<String> firstNameList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "firstName");
		ArrayList<String> lastNameList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "lastName");
		ArrayList<String> typeList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "workerType");
		ArrayList<String> validFromList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "validFrom");
		ArrayList<String> validToList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "validTo");
		ArrayList<String> userIdList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "masterIdentityId");
		ArrayList<String> emailList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "email");
		ArrayList<String> cityList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "city");
		
		ArrayList<String> empTypeList=getEmpTypeFromtypeList(typeList);
		validateDataInStagingTable(firstNameList,lastNameList,userIdList,empTypeList);
		validateDataInMasterTable(firstNameList,lastNameList,userIdList,typeList,validFromList,validToList,emailList,cityList);
		validateDataOnUI(firstNameList,lastNameList,userIdList,typeList,validFromList,validToList,emailList,cityList);
	}

	private static void validateDataInMasterTable(ArrayList<String> firstNameList, ArrayList<String> lastNameList,
			ArrayList<String> userIdList, ArrayList<String> typeList, ArrayList<String> validFromList,
			ArrayList<String> validToList, ArrayList<String> emailList,
			ArrayList<String> cityList) throws ClassNotFoundException, SQLException, ParseException {

		for(String userId:userIdList) {
			String firstName=DBValidations.getFirstNameOfUser(userId);
			String lastName=DBValidations.getLastNameOfUser(userId);
			String type=DBValidations.getTypeofUserId(userId);
			String validFrom=DBValidations.getValidFromOfUser(userId);
			String validTo=DBValidations.getValidToOfUser(userId);
			String email=DBValidations.getEmailIdOfUser(userId);
			String city=DBValidations.getCityOfUser(userId);
			if(firstNameList.contains(firstName)) {
				logger.log(LogStatus.PASS, "FirstName "+firstName+" exists in master table for UserID" +userId);
			}
			if(lastNameList.contains(lastName)) {
				logger.log(LogStatus.PASS, "LastName "+lastName+" exists in master table for UserID" +userId);
			}
			if(typeList.contains(type)) {
				logger.log(LogStatus.PASS, "WorkerType "+type+" exists in master table for UserID" +userId);
			}
			if(validFromList.contains(validFrom)) {
				logger.log(LogStatus.PASS, "ValidFrom "+validFrom+" exists in master table for UserID" +userId);
			}
			if(validToList.contains(validTo)) {
				logger.log(LogStatus.PASS, "ValidTo "+validTo+" exists in master table for UserID" +userId);
			}
			if(emailList.contains(email)) {
				logger.log(LogStatus.PASS, "Email "+email+" exists in master table for UserID" +userId);
			}
			if(cityList.contains(city)) {
				logger.log(LogStatus.PASS, "City "+city+" exists in master table for UserID" +userId);
			}
		}
	}

	private static ArrayList<String> getEmpTypeFromtypeList(ArrayList<String> typeList) {
		ArrayList<String> empTypeList= new ArrayList<String>();
		for(String type:typeList) {
			 if(type.trim().equals("employee")){
				empTypeList.add("2");
			}
			else if(type.trim().equals("contractor")){
				empTypeList.add("3");
			}
			else if (type.trim().equals("visitor")){
				empTypeList.add("4");
			}
		}
		return empTypeList;
	}

	private static void validateDataInStagingTable(ArrayList<String> firstNameList,ArrayList<String> lastNameList,ArrayList<String> userIdList,ArrayList<String> typeList) throws Throwable {
		
//		String job_instance_id=getJobInstanceIdFromJobName(AGlobalComponents.jobName);
		
		ArrayList<ArrayList<String>> dataList=getDataFromStagingTable(AGlobalComponents.syncId);
		
		for(int i=0;i<=firstNameList.size()-1;i++) {

			for (ArrayList<String> s : dataList) {	

				if(firstNameList.get(i).equalsIgnoreCase(s.get(0))) {
					logger.log(LogStatus.PASS,"firstName "+firstNameList.get(i)+"exists in staging table");
				}
				if(lastNameList.get(i).equalsIgnoreCase(s.get(1))) {
					logger.log(LogStatus.PASS,"lastName "+lastNameList.get(i)+ "exists in staging table");
				}
				if(userIdList.get(i).equalsIgnoreCase(s.get(2))) {
					logger.log(LogStatus.PASS,"UserId "+userIdList.get(i)+ "exists in staging table ");
				}
				if(typeList.get(i).equalsIgnoreCase(s.get(3))) {
					logger.log(LogStatus.PASS,"EmployeeType "+typeList.get(i)+" exists in staging table for userId "+userIdList.get(i));
				}
			}
		}
	}



	private static ArrayList<ArrayList<String>> getDataFromStagingTable(String job_instance_id) throws ClassNotFoundException, SQLException {
		String query = "select first_name,last_name,user_id,type from stg_user_data where sync_id='"+job_instance_id+"'";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		System.out.println("resultset"+rs);
	
		return rs;
	
	}
	private static String getJobInstanceIdFromJobName(String jobName) throws Throwable {
		
		String query = "select job_instance_id from recon_monitor where recon_config_id=(select id from recon_config where description='"+jobName+"') and int_status=0";
		ArrayList<ArrayList<String>> rs = Utility.objectToStringConversion(MsSql.getResultsFromDatabase(query));
		String Id =rs.get(0).get(0);	
		if(Id!=null) {
			System.out.println("max objectId is"+Id);
		}
		else {
			System.out.println("Unable to fetch max objectID");
		}
		return Id;
	}

	private static void validateDataOnUI(ArrayList<String> firstNameList, ArrayList<String> lastNameList, ArrayList<String> userIdList, ArrayList<String> empTypeList,
			ArrayList<String> validFromList,ArrayList<String> validToList,ArrayList<String> emailList,ArrayList<String> cityList) throws Throwable {
		
		
		for(int i=0;i<=userIdList.size()-1;i++) {
			
			Actions action = new Actions(driver);
			if(driver.findElements(By.xpath("((//div[text()='"+userIdList.get(i)+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]")).size()<0){
				AGlobalComponents.takeScreenshotIfPass=true;
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
				ByAttribute.click("xpath", IdentityObjects.filterIconLnk, "Click on Filter icon ");
				Utility.pause(3);
				ByAttribute.click("xpath", IdentityObjects.addFilterLnk, "Click on Add icon to enter the filter");
				Utility.pause(5);
				ByAttribute.click("xpath", IdentityObjects.enterFieldName1ToFilter, "click to enter field name for Filtering");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.enterFieldName1ToFilter,"User ID", "Enter the field name for Filtering");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.clickFieldValue1, "click to enter the value");
				Utility.pause(2);
				
				ByAttribute.setText("xpath", IdentityObjects.enterFieldValue1,userIdList.get(i), "Enter the first field value for Filtering");
				Utility.pause(2);
		
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(20);
			} 
			
				WebElement record=driver.findElement(By.xpath("((//div[text()='"+userIdList.get(i)+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
				identityCode=record.getText();	
				action.doubleClick(record).perform();
				Utility.pause(15);
				String searchResult= "//div[contains(text(),'"+userIdList.get(i)+"')]";
				if(Utility.verifyElementPresentReturn(searchResult,userIdList.get(i),true,false)){
					logger.log(LogStatus.INFO ,"Search result record appeared with identity code as : "+ identityCode);
					//FirstName Validation
					String firstName=driver.findElement(By.xpath(IdentityObjects.firstNameLnk)).getAttribute("value");
					if(firstName!=null) {
						if(firstNameList.get(i).equalsIgnoreCase(firstName)) {
							Utility.verifyElementPresentByScrollView(IdentityObjects.firstNameLnk, "FirstName", true, false);
						}
						else {
							logger.log(LogStatus.FAIL, "FirstName " +firstName+" on UI is not same as expected");
						}
					}
					else {
						logger.log(LogStatus.FAIL, "Not able to see firstName "+firstNameList.get(i)+"on UI ");
					}
					//LastName validation
					String lastName=driver.findElement(By.xpath(IdentityObjects.lastNameLnk)).getAttribute("value");
					if(lastName!=null) {
						if(lastNameList.get(i).equalsIgnoreCase(lastName)) {
							Utility.verifyElementPresentByScrollView(IdentityObjects.lastNameLnk, "LastName", true, false);
						}
						else {
							logger.log(LogStatus.FAIL, "LastName " +lastName+" on UI is not same as expected");
						}
					}
					else {
						logger.log(LogStatus.FAIL, "Not able to see LastName "+lastNameList.get(i)+"on UI ");
					}
					//Employee Type Validaiton
					String employeeType=driver.findElement(By.xpath(IdentityObjects.employeeTypeLnk)).getAttribute("value");
					if(employeeType!=null) {
						if(empTypeList.get(i).equalsIgnoreCase(employeeType)) {
							Utility.verifyElementPresentByScrollView(IdentityObjects.employeeTypeLnk, "EmployeeType", true, false);
							logger.log(LogStatus.PASS, "EmployeeType "+employeeType+" displaying on UI");
						}
						else {
							logger.log(LogStatus.FAIL, "EmployeeType " +employeeType+" on UI is not same as expected");
						}
					}
					else {
						logger.log(LogStatus.FAIL, "Not able to see EmployeeType "+empTypeList.get(i)+"on UI ");
					}
					//validfrom Validation
					String validFrom=driver.findElement(By.xpath(IdentityObjects.validFromLnk)).getAttribute("value");
					validFrom=TestDataEngine.convertDateFormatToGivenFormat(validFrom, "MM-dd-yyyy");
					if(validFrom!=null) {
						if(validFromList.get(i).equalsIgnoreCase(validFrom)) {
							Utility.verifyElementPresentByScrollView(IdentityObjects.validFromLnk, "ValidFrom", true, false);
						}
						else {
							logger.log(LogStatus.FAIL, "ValidFrom " +validFrom+" on UI is not same as expected" +userIdList.get(i));
						}
					}
					else {
						logger.log(LogStatus.FAIL, "Not able to see EmployeeType "+validFromList.get(i)+"on UI " +userIdList.get(i));
					}
					//ValidTo validation
					String validTo=driver.findElement(By.xpath("//input[@placeholder='Select Valid To']")).getAttribute("value");
					validTo=TestDataEngine.convertDateFormatToGivenFormat(validTo, "MM-dd-yyyy");
					if(validTo!=null) {
						if(validToList.get(i).equalsIgnoreCase(validTo)) {
							Utility.verifyElementPresentByScrollView(IdentityObjects.validToLnk, "ValidTo", true, false);							Utility.verifyElementPresent(IdentityObjects.validFromLnk, "ValidTo "+validTo+" displaying on UI "+userIdList.get(i),false);
						}
						else {
							logger.log(LogStatus.FAIL, "ValidTo " +validTo+" on UI is not same as expected" +userIdList.get(i));
						}
					}
					else {
						logger.log(LogStatus.FAIL, "Not able to see ValidTo "+validToList.get(i)+"on UI " +userIdList.get(i));
					}
					
					//EmailId Validation
					String email=driver.findElement(By.xpath(IdentityObjects.emailIdLnk)).getAttribute("value");
					if(email!=null) {
						if(emailList.get(i).equalsIgnoreCase(email)) {
							Utility.verifyElementPresentByScrollView(IdentityObjects.emailIdLnk, "EmailId", true, false);
						}
						else {
							logger.log(LogStatus.FAIL, "EmailId" +email+" on UI is not same as expected" +userIdList.get(i));
						}
					}
					else {
						logger.log(LogStatus.FAIL, "Not able to see ValidTo "+emailList.get(i)+"on UI " +userIdList.get(i));
					}
				
				//City Validation
				String city=driver.findElement(By.xpath(IdentityObjects.cityLnk)).getAttribute("value");
				if(city!=null) {
					if(cityList.get(i).equalsIgnoreCase(city)) {
						Utility.verifyElementPresentByScrollView(IdentityObjects.workLocationLnk, "City", true, false);
					}
					else {
						logger.log(LogStatus.FAIL, "City " +city+" on UI is not same as expected" +userIdList.get(i));
					}
				}	
				else {
					logger.log(LogStatus.FAIL, "Not able to see City "+cityList.get(i)+"on UI " +userIdList.get(i));
				}		
			}
		}			
	}

	public static void assignBadgeToUserInCCURE() throws Throwable {
		if(unhandledException==false) {
			try {
				String identityDataFile = reconTestDataDirectory + "/Identity.csv";
				String badgesDataFile = reconTestDataDirectory + "/BadgesData.csv"; 
				ArrayList<String> userIdList=Utility.getCSVColumnPerHeader(identityDataFile, "masterIdentityId");
				ArrayList<String> firstNameList=Utility.getCSVColumnPerHeader(identityDataFile, "firstName");
				ArrayList<String> lastNameList=Utility.getCSVColumnPerHeader(identityDataFile, "lastName");
				ArrayList<String> userIds=new ArrayList<String>();
				ArrayList<String> badgeIds=new ArrayList<String>();
				ArrayList<String> badgeValidFroms=new ArrayList<String>();
				ArrayList<String> badgeValidTos=new ArrayList<String>();
				ArrayList<String> badgeTypes=new ArrayList<String>();
				for(String userId:userIdList) {
					String name=lastNameList.get(userIdList.indexOf(userId))+", "+firstNameList.get(userIdList.indexOf(userId));
					String badgeId=Utility.UniqueNumber(6);
					String activationDate=Utility.getCurrentDateTime("MM-dd-yyyy");
					String expirationDate=Utility.getDate("MM-dd-yyyy", 4, "years");
					Calendar cal = Calendar.getInstance();
					
					Date date=new SimpleDateFormat("MM-dd-yyyy")
                            .parse(activationDate);

					cal.setTime(date);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.SECOND,(cal.get(Calendar.SECOND)+20));
					String badgeActivationDate=new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(cal.getTime());
					
					Date date1=new SimpleDateFormat("MM-dd-yyyy")
                            .parse(expirationDate);

					cal.setTime(date1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.SECOND,(cal.get(Calendar.SECOND)-20));

					String badgeExpirationDate=new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(cal.getTime());
					
					int maxObjectId=DBValidations.getMaxCredentialObjectId();
					int objectId=DBValidations.getObjectIdOfTheUser(name);
					if(DBValidations.assignBadgeToUserInCCURE(objectId,name,badgeId,activationDate,expirationDate,maxObjectId+1)) {
						
						ReadDataFromPropertiesFile.updateCSVCellValue(identityDataFile, "validTo", 1,expirationDate);
						userIds.add(Integer.toString(objectId));
						badgeIds.add(badgeId);
						badgeValidFroms.add(badgeActivationDate);
						badgeValidTos.add(badgeExpirationDate);
						badgeTypes.add("gb");
						
						ArrayList<ArrayList<String>> badgesData = new ArrayList<ArrayList<String>>();
						String detailsHeaderArray []= {"UserId","BadgeId","BadgeValidFrom","BadgeValidTo","BadgeType"};
						
						ArrayList<String> badgesDataFileHeaders = new ArrayList<String>(Arrays.asList(detailsHeaderArray));
						
						badgesData = Utility.appendColumnDataAtEnd(badgesData, userIds);
						badgesData = Utility.appendColumnDataAtEnd(badgesData, badgeIds);
						badgesData = Utility.appendColumnDataAtEnd(badgesData, badgeValidFroms);
						badgesData = Utility.appendColumnDataAtEnd(badgesData, badgeValidTos);
						badgesData = Utility.appendColumnDataAtEnd(badgesData, badgeTypes);
						if(Utility.checkIfListIsNotNullAndNotEmpty(badgesData)) {
							
								badgesData.add(0, badgesDataFileHeaders);
								if(FileOperations.createFileIfDoesNotExist(badgesDataFile)) {
									ReadDataFromPropertiesFile.writeTestDataInFile(badgesDataFile, badgesData, false);
								}
						}	
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

	public static void validateAssetsData() throws Throwable {

		String userDataFile=reconTestDataDirectory+"/BadgesData.csv";
		ArrayList<String> userIdList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "UserId");
		ArrayList<String> badgeIdList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "BadgeId");
		ArrayList<String> badgeValidFromList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "BadgeValidFrom");
		ArrayList<String> badgeValidToList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "BadgeValidTo");
		ArrayList<String> badgeTypeList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "BadgeType");
		
		validateAssetsDataInStagingTable(userIdList,badgeIdList,badgeValidFromList,badgeValidToList,badgeTypeList);
	
		validateAssetsDataInMasterTable(userIdList,badgeIdList,badgeValidFromList,badgeValidToList,badgeTypeList);
		validateAssetsDataOnUI(userIdList,badgeIdList,badgeValidFromList,badgeValidToList,badgeTypeList);
	
	}

	private static void validateAssetsDataOnUI(ArrayList<String> userIdList, ArrayList<String> badgeIdList,
			ArrayList<String> badgeValidFromList, ArrayList<String> badgeValidToList, ArrayList<String> badgeTypeList) throws Throwable {
		

		if(unhandledException==false)
		{
			System.out.println("*****************Validate Assets Info*****************");
			try{
				for(int i=0;i<=userIdList.size()-1;i++) {
					String searchResult= "//div[contains(text(),'"+userIdList.get(i)+"')]";
					if(Utility.verifyElementPresentReturn(searchResult,userIdList.get(i),true,false)){
						logger.log(LogStatus.INFO ,"Search result record appeared with identity code as : "+ identityCode);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
						Utility.pause(2);
					
						logger.log(LogStatus.INFO, "Navigated to Existing asset screen");
						Utility.verifyElementPresent("//label[contains(@class,'x-form-cb-label') and text()='AssetExisting']", "Existing asset screen",  false);
				
						getIndexOfAssetsHeaders();
						
						//AssetCode Validation
						String assetCodeElement= "//td[contains(@class,'x-grid-cell x-grid-td')]["+assetCodeIndex+"]/div";
						String assetCode=driver.findElement(By.xpath(assetCodeElement)).getText();
						
						Utility.pause(2);
		
						if(assetCode!=null) {
							if(AGlobalComponents.assetCode.equalsIgnoreCase(assetCode)) {
								Utility.verifyElementPresentByScrollView(assetCodeElement, "AssetCode", true, false);
							}
							else {
								logger.log(LogStatus.FAIL, "FirstName " +assetCode+" on UI is not same as expected");
							}
						}
						else {
							logger.log(LogStatus.FAIL, "Not able to see assetCode "+assetCode+"on UI ");
						}
						
						//BadgeValidFrom Validation
						String badgeValidFromElement= "//td[contains(@class,'x-grid-cell x-grid-td')]["+badgeValidFromIndex+"]/div";
						String badgeValidFrom=driver.findElement(By.xpath(badgeValidFromElement)).getText();
						Utility.pause(2);
						
						SimpleDateFormat month_date = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");
						SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
						Date date = month_date.parse(badgeValidFrom);
						String newDate = sdf.format(date);
						
						if(newDate!=null) {
							if(badgeValidFromList.get(i).equalsIgnoreCase(newDate)) {
								Utility.verifyElementPresentByScrollView(badgeValidFromElement, "BadgeValidFrom", true, false);
							}
							else {
								logger.log(LogStatus.FAIL, "BadgeValidFrom " +newDate+" on UI is not same as expected");
							}
						}
						else {
							logger.log(LogStatus.FAIL, "Not able to see badgeValidFrom "+newDate+"on UI ");
						}
						
						//BadgeValidTo Validation
						String badgeValidToElement= "//td[contains(@class,'x-grid-cell x-grid-td')]["+badgeValidToIndex+"]/div";
						String badgeValidTo=driver.findElement(By.xpath(badgeValidToElement)).getText();
						Utility.pause(2);
						
						SimpleDateFormat valdToMonth = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");
						SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
						Date date1 = valdToMonth.parse(badgeValidTo);
						String newValidToDate = sdf1.format(date1);
						
						if(badgeValidTo!=null) {
							if(badgeValidToList.get(i).equalsIgnoreCase(newValidToDate)) {
								Utility.verifyElementPresentByScrollView(badgeValidToElement, "BadgeValidTo", true, false);
							}
							else {
								logger.log(LogStatus.FAIL, "BadgeValidTo " +newValidToDate+" on UI is not same as expected");
							}
						}
						else {
							logger.log(LogStatus.FAIL, "Not able to see badgeValidTo "+newValidToDate+"on UI ");
						}
						
						//Badge Type Validation
						String badgeTypeElement="//td[contains(@class,'x-grid-cell x-grid-td')]["+badgeTypeIndex+"]/div";
						String badgeType=driver.findElement(By.xpath(badgeTypeElement)).getText();
						Utility.pause(2);
		
						if(badgeType!=null) {
							if(badgeType.equalsIgnoreCase("Generic Badge")) {
								badgeType="gb";
							}
								
							if(badgeTypeList.get(i).equalsIgnoreCase(badgeType)) {
								Utility.verifyElementPresentByScrollView(badgeTypeElement, "Asset Type", true, false);
							}
							else {
								logger.log(LogStatus.FAIL, "AssetType " +badgeType+" on UI is not same as expected");
							}
						}
						else {
							logger.log(LogStatus.FAIL, "Not able to see AssetType "+badgeType+"on UI ");
						}
					}
				}
			}			
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	public static void getIndexOfAssetsHeaders() {

		try{
			List<WebElement> headers = driver.findElements(By.xpath(".//div[@class='x-column-header-text']//span"));
			int size = headers.size(),j=1;
			boolean flag=true;
				
			for (int i=1;i<size;i++){
				WebElement header= headers.get(i);
				String heading = header.getText();
				System.out.println("heading "+ (i) +" "+ heading);
						
					switch (heading.toLowerCase()) {
		            case "asset":
		            	assetCodeIndex = j+1;
		            	j++;
		            	break;
		            case "valid from":
		            	badgeValidFromIndex=j+1;
		            	j++;
		            	break;
		            case "valid to":
		            	badgeValidToIndex=j+1;
		            	j++;
		            	break;
		            case "asset type":
		            	badgeTypeIndex=j+1;
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

	private static void validateAssetsDataInMasterTable(ArrayList<String> userIdList, ArrayList<String> badgeIdList,
			ArrayList<String> badgeValidFromList, ArrayList<String> badgeValidToList,ArrayList<String> badgeTypeList) throws ClassNotFoundException, SQLException 
	{
		for(String userId:userIdList) {
			String identityId=DBValidations.getIdentityIdOfUser(userId);
			String assetId=DBValidations.getAssetId(identityId);
			String badgeId=DBValidations.getBadgeIdFromMasterTable(assetId);
			String badgeValidFrom=DBValidations.getBadgeValidFromFromMasterTable(identityId);
			String badgeValidTo=DBValidations.getBadgeValidToFromMasterTable(identityId);
			String badgeType=DBValidations.getBadgeTypeFromMasterTable(assetId);
			AGlobalComponents.assetCode=DBValidations.getAssetCodeFromMasterTable(assetId);
			if(badgeIdList.contains(badgeId)) {
				logger.log(LogStatus.PASS, "BadgeId "+badgeId+" exists in master table for UserID" +userId);
			}
			if(badgeValidFromList.contains(badgeValidFrom)) {
				logger.log(LogStatus.PASS, "BadgeValidFrom "+badgeValidFrom+" exists in master table for UserID" +userId);
			}
			if(badgeValidToList.contains(badgeValidTo)) {
				logger.log(LogStatus.PASS, "BadgeValidTo "+badgeValidTo+" exists in master table for UserID" +userId);
			}
			if(badgeTypeList.contains(badgeType)) {
				logger.log(LogStatus.PASS, "BadgeType "+badgeType+" exists in master table for UserID" +userId);
			}
		}
	}

	private static void validateAssetsDataInStagingTable(ArrayList<String> userIdList, ArrayList<String> badgeIdList,
			ArrayList<String> badgeValidFromList, ArrayList<String> badgeValidToList, ArrayList<String> badgeTypeList) throws ClassNotFoundException, SQLException, ParseException {
		for(String userId:userIdList) {
			String userDataExtId=DBValidations.getUserdataExtId(userId);
			String badgeId=DBValidations.getBadgeIdFromStagingTable(userDataExtId);
			String badgeValidFrom=DBValidations.getBadgeValidFromFromStagingTable(userDataExtId);	
			badgeValidFrom=badgeValidFrom.replace("/", "-");
			Date date1=new SimpleDateFormat("MM-dd-yyyy")
                    .parse(badgeValidFrom);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.SECOND,(cal.get(Calendar.SECOND)+20));
			String badgeActivationDate=new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(cal.getTime());
			
			String badgeValidTo=DBValidations.getBadgeValidToFromStagingTable(userDataExtId);
			
			badgeValidTo=badgeValidTo.replace("/", "-");
			Date date=new SimpleDateFormat("MM-dd-yyyy")
                    .parse(badgeValidTo);
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.SECOND,(cal.get(Calendar.SECOND)-20));
			String badgeExpirationDate=new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(cal.getTime());
//			String badgeType=DBValidations.getBadgeTypeFromStagingTable("3130431672928784");
			
			if(badgeIdList.contains(badgeId)) {
				logger.log(LogStatus.PASS, "BadgeId "+badgeId+" exists in master table for UserID" +userId);
			}
			if(badgeValidFromList.contains(badgeActivationDate)) {
				logger.log(LogStatus.PASS, "BadgeValidFrom "+badgeValidFrom+" exists in master table for UserID" +userId);
			}
			if(badgeValidToList.contains(badgeExpirationDate)) {
				logger.log(LogStatus.PASS, "BadgeValidTo "+badgeValidTo+" exists in master table for UserID" +userId);
			}
			
		}
	}
	
	public static void updatePhoto() throws Throwable {
		if(unhandledException==false) {
			System.out.println("********* Update Photo Of user through IDM screen*******************");
			logger.log(LogStatus.INFO,"******Update Photo Of user through IDM screen************");
			try {
				
				ByAttribute.click("xpath",IdentityObjects.leftPaneExpansionLnk , "Expand left pane window");
				WebElement image = driver.findElement(By.xpath(IdentityObjects.imageLnk));
				String oldPhotoSrc = image.getAttribute("src");
				if(oldPhotoSrc.contains("defaultprofile")){
					photoFilePath = System.getProperty("user.dir") + "\\Browser_Files\\Applicant_Photo.jpg";
				}
				else{
					photoFilePath = System.getProperty("user.dir") + "\\Browser_Files\\UserImage.png";
				}
				Utility.verifyElementPresent(IdentityObjects.imageLnk, "existing image", false);
				Utility.pause(5);
				ByAttribute.click("xpath",IdentityObjects.addImageLnk, "Click on existing image to modify it");
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
				
				String newPhotoSrc = image.getAttribute("src");
				if(Utility.compareStringValues(oldPhotoSrc,newPhotoSrc ))
					logger.log(LogStatus.FAIL, "image is not modified");
				else{
					logger.log(LogStatus.PASS, "image is updated");
					Utility.verifyElementPresent(IdentityObjects.imageLnk, "new image", false);
				}
					
			}catch (Exception e) {
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
	}

	public static void assignAccessToUserInCCURE() throws Throwable {

		if(unhandledException==false) {
			try {
				String identityDataFile = reconTestDataDirectory + "/Identity.csv";
				String accessDataFile = reconTestDataDirectory + "/AccessData.csv"; 
				ArrayList<String> userIdList=Utility.getCSVColumnPerHeader(identityDataFile, "masterIdentityId");
				ArrayList<String> firstNameList=Utility.getCSVColumnPerHeader(identityDataFile, "firstName");
				ArrayList<String> lastNameList=Utility.getCSVColumnPerHeader(identityDataFile, "lastName");
				ArrayList<String> userIds=new ArrayList<String>();
				ArrayList<String> accessValidFroms=new ArrayList<String>();
				ArrayList<String> accessValidTos=new ArrayList<String>();
				ArrayList<String> accessNames=new ArrayList<String>();
				ArrayList<String> accessIds=new ArrayList<String>();
				for(String userId:userIdList) {
					String name=lastNameList.get(userIdList.indexOf(userId))+", "+firstNameList.get(userIdList.indexOf(userId));
					String activationDate=Utility.getCurrentDateTime("MM-dd-yyyy");
					String expirationDate=Utility.getDate("MM-dd-yyyy", 4, "years");
					int accessId=DBValidations.getAccessId("07 Contractors");
					int objectId=DBValidations.getObjectIdOfTheUser(name);
					if(DBValidations.assignAccessToUserInCCURE(objectId,accessId,activationDate,expirationDate)) {
											
						accessValidFroms.add(activationDate);
						accessValidTos.add(expirationDate);
						accessNames.add("07 Contractors");
						accessIds.add(Integer.toString(accessId));
						userIds.add(Integer.toString(objectId));
						ArrayList<ArrayList<String>> accessesData = new ArrayList<ArrayList<String>>();
						String detailsHeaderArray []= {"UserId","AccessId","AccessName","ValidFrom","ValidTo"};
						
						ArrayList<String> accessDataFileHeaders = new ArrayList<String>(Arrays.asList(detailsHeaderArray));
						
						accessesData = Utility.appendColumnDataAtEnd(accessesData, userIds);
						accessesData = Utility.appendColumnDataAtEnd(accessesData, accessIds);
						accessesData = Utility.appendColumnDataAtEnd(accessesData, accessNames );
						accessesData = Utility.appendColumnDataAtEnd(accessesData, accessValidFroms );
						accessesData = Utility.appendColumnDataAtEnd(accessesData, accessValidTos);
						if(Utility.checkIfListIsNotNullAndNotEmpty(accessesData)) {
							
							accessesData.add(0, accessDataFileHeaders);
								if(FileOperations.createFileIfDoesNotExist(accessDataFile)) {
									ReadDataFromPropertiesFile.writeTestDataInFile(accessDataFile, accessesData, false);
								}
						}	
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

	public static void validateAccessesData() throws Throwable {

		String userDataFile=reconTestDataDirectory+"/AccessData.csv";
		ArrayList<String> userIdList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "UserId");
		ArrayList<String> accessIdList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "AccessId");
		ArrayList<String> accessValidFromList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "ValidFrom");
		ArrayList<String> accessValidToList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "ValidTo");
		ArrayList<String> accessNameList=TestDataEngine.getCSVColumnPerHeader(userDataFile, "AccessName");
		
		validateAccessesDataInStagingTable(userIdList,accessIdList,accessValidFromList,accessValidToList,accessNameList);
	
		validateAccessesDataInMasterTable(userIdList,accessIdList,accessValidFromList,accessValidToList,accessNameList);
		validateAccessesDataOnUI(userIdList,accessIdList,accessValidFromList,accessValidToList,accessNameList);
	}

	private static void validateAccessesDataOnUI(ArrayList<String> userIdList, ArrayList<String> accessIdList,
			ArrayList<String> accessValidFromList, ArrayList<String> accessValidToList,
			ArrayList<String> accessNameList) throws Throwable {

		
		if(unhandledException==false)
		{
			System.out.println("*****************Validate Assets Info*****************");
			try{
				for(int i=0;i<=userIdList.size()-1;i++) {
					String searchResult= "//div[contains(text(),'"+userIdList.get(i)+"')]";
					if(Utility.verifyElementPresentReturn(searchResult,userIdList.get(i),true,false)){
						logger.log(LogStatus.INFO ,"Search result record appeared with identity code as : "+ identityCode);
						ByAttribute.click("xpath", IdentityObjects.idmManageIdentityAssetsTabBtn, "Click on Assets Tab ");
						Utility.pause(2);
					
						logger.log(LogStatus.INFO, "Navigated to Existing asset screen");
						Utility.verifyElementPresent("//label[contains(@class,'x-form-cb-label') and text()='AssetExisting']", "Existing asset screen",  false);
				
						getIndexOfAssetsHeaders();
						
						//AssetCode Validation
						String assetCodeElement= "//td[contains(@class,'x-grid-cell x-grid-td')]["+assetCodeIndex+"]/div";
						String assetCode=driver.findElement(By.xpath(assetCodeElement)).getText();
						
						Utility.pause(2);
		
						if(assetCode!=null) {
							if(AGlobalComponents.assetCode.equalsIgnoreCase(assetCode)) {
								Utility.verifyElementPresentByScrollView(assetCodeElement, "AssetCode", true, false);
							}
							else {
								logger.log(LogStatus.FAIL, "FirstName " +assetCode+" on UI is not same as expected");
							}
						}
						else {
							logger.log(LogStatus.FAIL, "Not able to see assetCode "+assetCode+"on UI ");
						}
						
						//BadgeValidFrom Validation
						String badgeValidFromElement= "//td[contains(@class,'x-grid-cell x-grid-td')]["+badgeValidFromIndex+"]/div";
						String badgeValidFrom=driver.findElement(By.xpath(badgeValidFromElement)).getText();
						Utility.pause(2);
						
						SimpleDateFormat month_date = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");
						SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
						Date date = month_date.parse(badgeValidFrom);
						String newDate = sdf.format(date);
						
						if(newDate!=null) {
							if(accessValidFromList.get(i).equalsIgnoreCase(newDate)) {
								Utility.verifyElementPresentByScrollView(badgeValidFromElement, "BadgeValidFrom", true, false);
							}
							else {
								logger.log(LogStatus.FAIL, "BadgeValidFrom " +newDate+" on UI is not same as expected");
							}
						}
						else {
							logger.log(LogStatus.FAIL, "Not able to see badgeValidFrom "+newDate+"on UI ");
						}
						
						//BadgeValidTo Validation
						String badgeValidToElement= "//td[contains(@class,'x-grid-cell x-grid-td')]["+badgeValidToIndex+"]/div";
						String badgeValidTo=driver.findElement(By.xpath(badgeValidToElement)).getText();
						Utility.pause(2);
						
						SimpleDateFormat valdToMonth = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");
						SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
						Date date1 = valdToMonth.parse(badgeValidTo);
						String newValidToDate = sdf1.format(date1);
						
						if(badgeValidTo!=null) {
							if(accessValidToList.get(i).equalsIgnoreCase(newValidToDate)) {
								Utility.verifyElementPresentByScrollView(badgeValidToElement, "BadgeValidTo", true, false);
							}
							else {
								logger.log(LogStatus.FAIL, "BadgeValidTo " +newValidToDate+" on UI is not same as expected");
							}
						}
						else {
							logger.log(LogStatus.FAIL, "Not able to see badgeValidTo "+newValidToDate+"on UI ");
						}
						
						//Badge Type Validation
						String badgeTypeElement="//td[contains(@class,'x-grid-cell x-grid-td')]["+badgeTypeIndex+"]/div";
						String badgeType=driver.findElement(By.xpath(badgeTypeElement)).getText();
						Utility.pause(2);
		
						if(badgeType!=null) {
							if(badgeType.equalsIgnoreCase("Generic Badge")) {
								badgeType="gb";
							}
								
							if(accessNameList.get(i).equalsIgnoreCase(badgeType)) {
								Utility.verifyElementPresentByScrollView(badgeTypeElement, "Asset Type", true, false);
							}
							else {
								logger.log(LogStatus.FAIL, "AssetType " +badgeType+" on UI is not same as expected");
							}
						}
						else {
							logger.log(LogStatus.FAIL, "Not able to see AssetType "+badgeType+"on UI ");
						}
					}
				}
			}			
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	private static void validateAccessesDataInMasterTable(ArrayList<String> userIdList, ArrayList<String> accessIdList,
			ArrayList<String> accessValidFromList, ArrayList<String> accessValidToList,
			ArrayList<String> accessNameList) throws ClassNotFoundException, SQLException {

		for(String userId:userIdList) {
			String identityId=DBValidations.getIdentityIdOfUser(userId);
			String accessId=DBValidations.getAccessIdFromMasterTable(identityId);
			String accessValidFrom=DBValidations.getAccessValidFromFromMasterTable(identityId);
			String accessValidTo=DBValidations.getAccessValidToFromMasterTable(identityId);
			String accessName=DBValidations.geAccessNameFromMasterTable(accessId);
			AGlobalComponents.assetCode=DBValidations.getAssetCodeFromMasterTable(accessId);
			if(accessIdList.contains(accessId)) {
				logger.log(LogStatus.PASS, "AccessId "+accessId+" exists in master table for UserID" +userId);
			}
			if(accessValidFromList.contains(accessValidFrom)) {
				logger.log(LogStatus.PASS, "AccessValidFrom "+accessValidFrom+" exists in master table for UserID" +userId);
			}
			if(accessValidToList.contains(accessValidTo)) {
				logger.log(LogStatus.PASS, "AccessValidTo "+accessValidTo+" exists in master table for UserID" +userId);
			}
			if(accessNameList.contains(accessName)) {
				logger.log(LogStatus.PASS, "AccessName "+accessName+" exists in master table for UserID" +userId);
			}
		}
	}

	private static void validateAccessesDataInStagingTable(ArrayList<String> userIdList, ArrayList<String> accessIdList,
			ArrayList<String> accessValidFromList, ArrayList<String> accessValidToList,
			ArrayList<String> accessNameList) throws ParseException, ClassNotFoundException, SQLException {

		for(String userId:userIdList) {
			String userDataExtId=DBValidations.getUserdataExtId(userId);
			String accessName=DBValidations.getAccessNameFromStagingTable(userDataExtId);
			String accessValidFrom=DBValidations.getAccessValidFromFromStagingTable(userDataExtId);	
			String accessValidTo=DBValidations.getBadgeValidToFromStagingTable(userDataExtId);
					
			if(accessIdList.contains(accessName)) {
				logger.log(LogStatus.PASS, "AccessId "+accessName+" exists in master table for UserID" +userId);
			}
			if(accessValidFromList.contains(accessValidFrom)) {
				logger.log(LogStatus.PASS, "BadgeValidFrom "+accessValidFrom+" exists in master table for UserID" +userId);
			}
			if(accessValidToList.contains(accessValidTo)) {
				logger.log(LogStatus.PASS, "BadgeValidTo "+accessValidTo+" exists in master table for UserID" +userId);
			}
		}
	}

	public static boolean createUserInHRDb() throws ClassNotFoundException {
		
		ArrayList<String> userIds=new ArrayList<String>();
		ArrayList<String> firstNameList=new ArrayList<String>();
		ArrayList<String> lastNameList=new ArrayList<String>();
		ArrayList<String> validFroms=new ArrayList<String>();
		ArrayList<String> validTos=new ArrayList<String>();
		ArrayList<String> jobTitle=new ArrayList<String>();
		ArrayList<String> identityType=new ArrayList<String>();
		ArrayList<String> managerId=new ArrayList<String>();
		ArrayList<String> payStatus= new ArrayList<String>();
		
		String hrUserDataFile = reconTestDataDirectory + "/HRDbCreateUser.csv"; 
		String validFrom=Utility.getCurrentDateTime("MM-dd-yyyy");
		String validTo=Utility.getDate("MM-dd-yyyy", 4, "years");
		String userId=Utility.UniqueNumber(6);
		String firstName=Utility.getRandomString(5);
		String lastName=Utility.getRandomString(5);
		String name=firstName+" "+lastName;
		
		if(DBValidations.createUserInHRDb(userId,firstName,lastName,name,validFrom,validTo,"Software Engineer","Permanent","Hire","anna.mordeno")) {
				
			userIds.add(userId);
			firstNameList.add(firstName);
			lastNameList.add(lastName);
			validFroms.add(validFrom);
			validTos.add(validTo);
			jobTitle.add("Software Engineer");
			identityType.add("Permanent");
			managerId.add("anna.mordeno");
			payStatus.add("Hire");
			
			ArrayList<ArrayList<String>> userData = new ArrayList<ArrayList<String>>();
			String detailsHeaderArray []= {"UserId","FirstName","LastName","ValidFrom","ValidTo","JobTitle","IdentityType","ManagerId","PayStatus"};
			
			ArrayList<String> userDataFileHeaders = new ArrayList<String>(Arrays.asList(detailsHeaderArray));
			
			userData = Utility.appendColumnDataAtEnd(userData, userIds);
			userData = Utility.appendColumnDataAtEnd(userData, firstNameList);
			userData = Utility.appendColumnDataAtEnd(userData, lastNameList );
			userData = Utility.appendColumnDataAtEnd(userData, validFroms );
			userData = Utility.appendColumnDataAtEnd(userData, validTos);
			userData = Utility.appendColumnDataAtEnd(userData, jobTitle);
			userData = Utility.appendColumnDataAtEnd(userData, identityType);
			userData = Utility.appendColumnDataAtEnd(userData, managerId);
			userData = Utility.appendColumnDataAtEnd(userData, payStatus);
			
			if(Utility.checkIfListIsNotNullAndNotEmpty(userData)) {
				
				userData.add(0, userDataFileHeaders);
				if(FileOperations.createFileIfDoesNotExist(hrUserDataFile)) {
					ReadDataFromPropertiesFile.writeTestDataInFile(hrUserDataFile, userData, false);
					return true;
				}
			}
			return true;
		}	
		else {
			logger.log(LogStatus.FAIL, "Failed to create user in HR Db");
			return false;		
		}
	}

	public static boolean changeEmpJobTitleThroughHRDB() throws ClassNotFoundException {
		String hrUserDataFile = reconTestDataDirectory + "/HRDbCreateUser.csv";
		
		String userId=Utility.getCSVCellValue(hrUserDataFile, "UserId", 1);
		
		if(DBValidations.updateUserInHrdb(userId,"Manager")) {
			ReadDataFromPropertiesFile.updateCSVCellValue(hrUserDataFile, "JobTitle", 1, "Manager");
			return true;
		}
		else {
			logger.log(LogStatus.FAIL, "Failed to change job title of user in HR Db");
			return false;		
		}
	}

	public static void addIdentitiesToAccess(ArrayList<String> firstNames, ArrayList<String> lastNames,String accessName) throws Throwable {
		if(unhandledException==false)
		{
			try
			{		
				ByAttribute.click("xpath", AccessObjects.accessTabLnk, "Click on Access tab");
				Utility.pause(20);
				
				ByAttribute.click("xpath", AccessObjects.filterIconLnk, "Click on Filter icon ");
				Utility.pause(3);
				ByAttribute.click("xpath", AccessObjects.addFilterLnk, "Click on Add icon to enter the filter");
				Utility.pause(2);
				ByAttribute.click("xpath", AccessObjects.enterFieldNameToFilter, "click to enter field name for Filtering");
				Utility.pause(2);
				ByAttribute.setText("xpath", AccessObjects.enterFieldNameToFilter,"Name", "Enter the field name for Filtering");
				Utility.pause(2);
				ByAttribute.click("xpath", AccessObjects.clickFieldValue1, "click to enter the value");
				Utility.pause(2);
				ByAttribute.setText("xpath", AccessObjects.enterFieldValue1,accessName, "Enter the first field value for Filtering");
				Utility.pause(2);
	
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ENTER);
				action.build().perform();
				Utility.pause(5);
				WebElement record=driver.findElement(By.xpath("((//div[text()='"+accessName+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
				action.doubleClick(record).perform();
				Utility.pause(5);
				
				ByAttribute.click("xpath", AccessObjects.IdentityNewRadioButton, "Selected Identity New radio button");
				
				for(String firstName:firstNames) {
					ByAttribute.click("xpath", AccessObjects.clickToEnterIdentityName,"click to enter first name for search");
					action.sendKeys(firstName);
					action.build().perform();
					Utility.pause(5);
					
					WebElement selectIdentity=driver.findElement(By.xpath("//*[@class='x-list-plain']/div[1]/div/span[2]"));
					action.click(selectIdentity);
					action.build().perform();
					Utility.pause(5);
					if(Utility.verifyElementPresentReturn("//div[@class='x-grid-cell-inner ' and contains(text(),'"+firstName+"')]",firstName,true,false)){
						logger.log(LogStatus.PASS, "Successfully added User" +firstName);
					}
					else {
						logger.log(LogStatus.FAIL, "Unable to add user "+firstName);
					}
				}
				ByAttribute.click("xpath", AccessObjects.SaveBtn, "click on save button");
				Utility.pause(2);
				
				
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	public static boolean validateAssignedAccessOnUI(String accessName) throws Throwable {
		logger.log(LogStatus.INFO, "Validate Assigned Access On UI");
		System.out.println("***************************** Validate Assigned Access On UI *********************************");
		try
		{	
			ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Assets Tab ");
			Utility.pause(2);
			
			if(Utility.verifyElementPresentReturn("//div[text()='"+accessName+"']",accessName,true,false)){
				logger.log(LogStatus.PASS,"Access Name: "+accessName+ " successfully assigned to user");
				return true;
			}
			else {
				logger.log(LogStatus.FAIL,"Access Name: "+accessName+ " not assigned to user");	
				return false;
			}
		}
		catch(Exception e){
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
			Utility.recoveryScenario(nameofCurrMethod, e);
			return false;
		}
	}

	public static void validateAssignedAccessInDB(String firstName,String lastName,String accessName) throws Throwable {
		logger.log(LogStatus.INFO, "validate Assigned Access In DB");
		System.out.println("***************************** Add Identities *********************************");
		try
		{
			String userId=firstName+"."+lastName;
			String identityId=DBValidations.getIdentityIdOfUser(userId);
			String accessId=DBValidations.getAccessIdFromMasterTable(identityId);
			String accessNameInDB=DBValidations.geAccessNameFromMasterTable(accessId);
			if(accessNameInDB.equalsIgnoreCase(accessName)) {
				logger.log(LogStatus.PASS,"Assigned access "+accessName+" is present in DB");
			}
		}
		catch(Exception e){
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
			Utility.recoveryScenario(nameofCurrMethod, e);
		}
	}

	public static boolean addIdentities(ArrayList<String> firstNames, ArrayList<String> lastNames, String accessName) throws Throwable {
	
		boolean flag=false;
		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO, "***************************** Add Identities *********************************");
			System.out.println("***************************** Add Identities *********************************");
			try
			{
				/** Launch New Private Browser **/
				Utility.switchToNewBrowserDriver();
		
				/* Login as Manager */
				boolean loginStatus = LoginPage.loginAEHSC("area.admin", "Alert1234");

				if(loginStatus){
					logger.log(LogStatus.PASS, "Login Successful");
			
					FB_Automation_CommonMethods.addIdentitiesToAccess(firstNames,lastNames,accessName);
			
					/* Logout from application */
					LoginPage.logout();	
			
					/** Switch to Default Browser **/
					Utility.switchToDefaultBrowserDriver();
			
					for(int i=0;i<1;i++) {
						FB_Automation_CommonMethods.searchIdentity(firstNames.get(i), lastNames.get(i));
						if(FB_Automation_CommonMethods.validateAssignedAccessOnUI(accessName)) 
							flag=true;
						
				        FB_Automation_CommonMethods.validateAssignedAccessInDB(firstNames.get(i), lastNames.get(i),accessName);
					}					 		
				}
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
				flag=false;
			}
		}
		return flag;
	}

	public static void removeIdentities(ArrayList<String> firstNames, ArrayList<String> lastNames, String accessName) throws Throwable {

		if(unhandledException==false)
		{
			
			logger.log(LogStatus.INFO, "***************************** Remove Identities *********************************");
			System.out.println("***************************** Remove Identities *********************************");
			try
			{
				/** Launch New Private Browser **/
				Utility.switchToNewBrowserDriver();
		
				/* Login as Manager */
				boolean loginStatus = LoginPage.loginAEHSC("area.admin", "Alert1234");

				if(loginStatus){
					logger.log(LogStatus.PASS, "Login Successful");
			
					FB_Automation_CommonMethods.removeIdentitiesFromAccess(firstNames,lastNames,accessName);
			
					/* Logout from application */
					LoginPage.logout();	
			
					/** Switch to Default Browser **/
					Utility.switchToDefaultBrowserDriver();
			
					for(int i=0;i<1;i++) {
						FB_Automation_CommonMethods.searchIdentity(firstNames.get(i), lastNames.get(i));
						FB_Automation_CommonMethods.validateRemovedAccessOnUI(accessName);
//				        FB_Automation_CommonMethods.validateAssignedAccessInDB(firstNames.get(i), lastNames.get(i),accessName);
					}					 		
				}
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	
	}

	private static void removeIdentitiesFromAccess(ArrayList<String> firstNames, ArrayList<String> lastNames,
			String accessName) throws Throwable {

		if(unhandledException==false)
		{
			try
			{		
				ByAttribute.click("xpath", AccessObjects.accessTabLnk, "Click on Access tab");
				Utility.pause(20);
				
				ByAttribute.click("xpath", AccessObjects.filterIconLnk, "Click on Filter icon ");
				Utility.pause(3);
				ByAttribute.click("xpath", AccessObjects.addFilterLnk, "Click on Add icon to enter the filter");
				Utility.pause(2);
				ByAttribute.click("xpath", AccessObjects.enterFieldNameToFilter, "click to enter field name for Filtering");
				Utility.pause(2);
				ByAttribute.setText("xpath", AccessObjects.enterFieldNameToFilter,"Name", "Enter the field name for Filtering");
				Utility.pause(2);
				ByAttribute.click("xpath", AccessObjects.clickFieldValue1, "click to enter the value");
				Utility.pause(2);
				ByAttribute.setText("xpath", AccessObjects.enterFieldValue1,accessName, "Enter the first field value for Filtering");
				Utility.pause(2);
	
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ENTER);
				action.build().perform();
				Utility.pause(5);
				WebElement record=driver.findElement(By.xpath("((//div[text()='"+accessName+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
				action.doubleClick(record).perform();
				Utility.pause(5);
				
				ByAttribute.click("xpath", AccessObjects.IdentityExistingRadioButton, "Selected Identity Existing radio button");
				
				for(String firstName:firstNames) {
					WebElement checkBox=driver.findElement(By.xpath("//div[text()='"+firstName+"']/parent::td/preceding-sibling::td[contains(@class,'x-grid-cell x-grid-td x-grid-cell-checkcolumn')]"));
					if(checkBox!=null) {
						checkBox.click();
						logger.log(LogStatus.INFO, "User "+firstName+" is selected ");
						Utility.verifyElementPresentByScrollView(AccessObjects.removeAccessLnk, "Remove Access", true, false);
						driver.findElement(By.xpath(AccessObjects.removeAccessLnk)).click();
						String checkStatusLocator="//div[text()='"+firstName+"']/parent::td/following-sibling::td[7]/div/label";
						String checkStatus=driver.findElement(By.xpath(checkStatusLocator)).getText();
						if(checkStatus.equalsIgnoreCase("REMOVED")) {
							Utility.verifyElementPresentByScrollView(checkStatusLocator, "Removed Status", true, false);
							logger.log(LogStatus.PASS,"User: "+firstName+" "+lastNames.get(firstNames.indexOf(firstName))+" successfully selected for removal");
						}
					}
					else {
						logger.log(LogStatus.FAIL,"Failed to select  "+firstName+" "+lastNames.get(firstNames.indexOf(firstName))+ " for removal");	
					}		
				}
				ByAttribute.click("xpath", AccessObjects.SaveBtn, "click on save button");
				Utility.pause(2);		
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
	}

	private static void validateRemovedAccessOnUI(String accessName) throws Throwable {

		logger.log(LogStatus.INFO, "Validate Removed Access OnUI");
		System.out.println("***************************** Validate Removed Access On UI *********************************");
		try
		{	
			ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Access Tab ");
			Utility.pause(2);
			
			WebElement accessNameLocator=driver.findElement(By.xpath("//div[text()='"+accessName+"']"));
			if(accessNameLocator!=null) {
				logger.log(LogStatus.FAIL,"Not able to remove Access Name: "+accessName);	
			}
		}
		catch(Exception e){
			logger.log(LogStatus.PASS,"Access Name: "+accessName+ " successfully removed from user");
		}
	}
}

