package CommonFunctions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.ByAttribute;
import CommonClassReusables.MsSql;
import CommonClassReusables.TestDataInterface;
import CommonClassReusables.Utility;
import ObjectRepository.AccessObjects;
import ObjectRepository.IdentityObjects;
import ObjectRepository.ReconObjects;


public class FB_Automation_CommonMethods extends BrowserSelection{
	
	public static String testDataDirectory= "Test_Data/IdentityManagement";
	public static String reconTestDataDirectory= "Test_Data/Recon";
	public static ArrayList<String> identityCodes = new ArrayList<String>();
	public static ArrayList<String> jobNames = new ArrayList<String>();
	private static boolean dupIdentity=false;
	private static String identityCode = null;
	private static String entityType= null;
//	private static String jobName="testRecon"+ Utility.UniqueNumber(2);
	private static String activeRoleReconRecords = null;
	
	
	
	
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
			System.out.println("***************************** Execute Recon Job *********************************");
			try
			{
				ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.reconSetUpLnk, "Click on Recon Setup ");
						
				Utility.pause(60);
				
				ByAttribute.click("xpath",ReconObjects.addReconRowLnk,"Click on Add icon to initiate Recon");
				Utility.pause(20);
				while(!ByAttribute.verifyCheckBox("xpath",ReconObjects.checkboxLnk)){
					ByAttribute.click("xpath",ReconObjects.addReconRowLnk,"Click on Add icon to initiate Recon");
					Utility.pause(10);
				}
						
				if(ByAttribute.verifyCheckBox("xpath",ReconObjects.checkboxLnk))
				{
					initiateReconJob();
					
					checkJobInReconMonitor();		
				}
				else{
					logger.log(LogStatus.FAIL, "Checkbox not visible ");
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

			/*
			 * Filter the records in Recon monitor on basis of entity type
			 */
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
			action.moveToElement(filterValue).click().sendKeys(entityType).build().perform();
			action.sendKeys(Keys.ENTER).build().perform();
			Utility.pause(2);
				
			//checking if recon job is registered in recon monitor screen or not
		
			WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchBarInRecon));
			searchBar.click();
			Utility.pause(5);
			ByAttribute.setText("xpath", ReconObjects.searchBarInRecon, AGlobalComponents.jobName, "Searching recon job on recon monitor screen");
			//	ByAttribute.click("xpath", ReconObjects.ListValueAfterSearch, "clicking on search result");
			action.sendKeys(Keys.ENTER).build().perform();
			String jobNameLocator = "//div[text()='"+AGlobalComponents.jobName+"']";
		
			if(ByAttribute.verifyCheckBox("xpath",ReconObjects.checkboxLnkOfReconJob) && Utility.verifyElementPresentReturn(jobNameLocator, AGlobalComponents.jobName, true, false)){
				logger.log(LogStatus.INFO,"Recon job found");
			
			}
			else{
				boolean jobPresent = Utility.verifyElementPresentReturn(jobNameLocator, AGlobalComponents.jobName, true, false);
				while(!jobPresent){
					System.out.println("job not registered in recon monitor , refreshing the page");
					logger.log(LogStatus.INFO, "waiting for job to get registered in recon monitor");
					WebElement refreshIcon = driver.findElement(By.xpath(ReconObjects.refreshIconLnk));
					refreshIcon.click();
					Utility.pause(5);
					
					jobNameLocator = "//div[text()='"+AGlobalComponents.jobName+"']";
					jobPresent = Utility.verifyElementPresentReturn(jobNameLocator, AGlobalComponents.jobName, true, false);
				}
			}
	
	
			//job is present in Recon monitor screen , Checking the status of the job
		
			WebElement status=driver.findElement(By.xpath(ReconObjects.reconJobStatus));
			if(Utility.compareStringValues(status.getText(), "STARTED")){
				logger.log(LogStatus.INFO, "Recon Job has been started");
				boolean flag= true;
				for(int i=1;i<10 && flag;i++){
					while(!Utility.compareStringValues(status.getText(), "COMPLETED") && flag){
						int count =0;
						while((Utility.verifyElementPresentReturn(jobNameLocator, AGlobalComponents.jobName, false, false)) && (Utility.compareStringValues(status.getText(), "STARTED"))){
							WebElement refreshIcon = driver.findElement(By.xpath(ReconObjects.refreshIconLnk));
							refreshIcon.click();
							Utility.pause(10);
							status=driver.findElement(By.xpath(ReconObjects.reconJobStatus));
							logger.log(LogStatus.INFO, "recon job is present and status is : "+ status.getText());
							count++;
						}
						System.out.println("Waiting for the recon job to get completed");
						status=driver.findElement(By.xpath(ReconObjects.reconJobStatus));
						if(Utility.compareStringValues(status.getText(), "COMPLETED")){
							flag=false;
						}
					}
				}

			
				WebElement activeRecords=driver.findElement(By.xpath(ReconObjects.activeRecords));
				WebElement errorRecords=driver.findElement(By.xpath(ReconObjects.errorRecords));
				activeRoleReconRecords = activeRecords.getText();
				if(activeRecords.getText()!="0" ){
					logger.log(LogStatus.INFO, "Total number of active records are : " +activeRecords.getText());
					activeRecords.click();
					Utility.pause(10);
				
					try {
						verifyReconData();
					} catch (Throwable e) {
						logger.log(LogStatus.ERROR, e);
					}
				} 
				else if(errorRecords.getText()!="0"){
					logger.log(LogStatus.INFO, "There are Error records ");
				}
				else if ((driver.findElements(By.xpath(ReconObjects.errorMessage)).size()>0)){
					WebElement errorMessage = driver.findElement(By.xpath(ReconObjects.errorMessage));
					String message = errorMessage.getText();
					logger.log(LogStatus.INFO, "Recon job failed with message : " + message);
				}
			
			}
		
			else if(Utility.compareStringValues(status.getText(), "FAILED")){
				logger.log(LogStatus.INFO, "Recon Job failed");
			
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
	 * @throws Throwable 
	 * 
	 * 
	 **/
	public static void initiateReconJob() throws Throwable {
	if(unhandledException==false)
	{
		System.out.println("******************Initiate recon job********************");
		try{	
		
			String reconDataFile = reconTestDataDirectory + "/Recon.csv";
			AGlobalComponents.jobName="testRecon"+ Utility.getCurrentDateTime("dd/MM/yy hh:mm:ss");		
			ArrayList<String> headers = Utility.getCSVRow(reconDataFile, 1);
			ArrayList<ArrayList<String>> usersData = Utility.getCSVData(reconDataFile, 0);
			int len = headers.size();
		
			String connectorName= null,scheduleType= null,header;
			
			for (int i=0;i<len;i++){
				header= headers.get(i);
				System.out.println("heading "+ (i+1) +" "+ header);
				int index = Utility.getIndex(headers,header);
				for(ArrayList<String> userData : usersData) {
					
					switch (header.toLowerCase()) {
					case "connectorname":
						connectorName = Utility.getIndexValue(userData, index);
						break;
					case "entitytype":
						entityType = Utility.getIndexValue(userData, index);
						break;
					case "scheduletype":
						scheduleType=Utility.getIndexValue(userData, index);
						break;
					default: 
						logger.log(LogStatus.ERROR, "Failed: Field {" +header+"} Not Found ");
						throw new UnsupportedOperationException();
					}	
				}
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
			WebElement entityValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'"+entityType+"')]"));
			action.moveToElement(entityValue).click();
			action.build().perform();
			logger.log(LogStatus.INFO, "Entity Type selected");
			Utility.pause(5);
		
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
				String	endDate = new SimpleDateFormat("M/d/yy hh:mm a").format(date); 
					
				ByAttribute.click("xpath", ReconObjects.endDateForTrialJob, "Click to enter end date");
				ByAttribute.setText("xpath", ReconObjects.endDateForTrialJob, endDate, "Enter end date to run trial job");
				confirmButton = driver.findElement(By.xpath(ReconObjects.confirmButton));
				action.moveToElement(confirmButton).click();
				action.build().perform();
				Utility.pause(5);
				logger.log(LogStatus.PASS, "Trial recon job saved successfully");
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
	
	/**
	 * 
	 * Method to create identity through Identity Tab
	 * Author : Monika Mehta
	 * 
	 * 
	 **/

	public static void createIdentity() throws Throwable
	{

		if(unhandledException==false)
		{
			
			System.out.println("***************************** Create Identity *********************************");
			try
			{
				if(!(driver.findElements(By.xpath("IdentityObjects.IdentityTabLnk")).size()>0))
					Utility.pause(3);
				
				ByAttribute.mouseHover("xpath", IdentityObjects.IdentityTabLnk, "Mouse Hover on Identity tab");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.manageIdentityLnk, "Click on Manage Identity ");
				Utility.pause(8);
				ByAttribute.click("xpath", IdentityObjects.createIdentityLnk, "click on create  icon to create new identity");
				Utility.pause(8);
				
				fillProfileInfo();
				ByAttribute.click("xpath", IdentityObjects.accessTabLnk, "Click on Accesses Tab ");
				Utility.pause(2);
				fillAccessesInfo();
				ByAttribute.click("xpath", IdentityObjects.systemsTabLnk, "Click on Systems Tab ");
				Utility.pause(2);
				fillSystemsInfo();	
				ByAttribute.click("xpath", IdentityObjects.assetsTabLnk, "Click on Assets Tab ");
				Utility.pause(2);
				fillAssetsInfo();
				ByAttribute.click("xpath", IdentityObjects.prerequisitesTabLnk, "Click on Prerequisites Tab ");
				Utility.pause(10);
				fillPrerequisitesInfo();
				ByAttribute.click("xpath", IdentityObjects.saveIconLnk, "Click on save Button ");
				Utility.pause(20);
				
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
			
			System.out.println("***************************** Search Identity*********************************");
			try
			{
				if(!(driver.findElements(By.xpath("IdentityObjects.IdentityTabLnk")).size()>0))
					Utility.pause(5);
				
				String identityTemplateFile = testDataDirectory + "/SearchIdentity_Template.csv";
				String identityDataFile = testDataDirectory + "/SearchIdentity.csv";
				TestDataInterface.compileTwoRowDataTemplate(identityTemplateFile, identityDataFile);
				
				
				ArrayList<String> headers = Utility.getCSVRow(identityDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(identityDataFile, 0);
				int len = headers.size();
				
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
				
				ByAttribute.mouseHover("xpath", IdentityObjects.IdentityTabLnk, "Mouse Hover on Identity tab");
				Utility.pause(5);
				ByAttribute.click("xpath", IdentityObjects.manageIdentityLnk, "Click on Manage Identity ");
				Utility.pause(20);
				
				//narrowing down the search by selecting Employee Type 
//				if(!dupIdentity){
//					String employeeType = "//div[@class='thumbInnerHolder']//span[text()='"+ empType+"']";
//					ByAttribute.click("xpath", employeeType, "Click on employee type ");
//					Utility.pause(5);
//				}
				ByAttribute.click("xpath", IdentityObjects.filterIconLnk, "Click on Filter icon ");
				Utility.pause(3);
				ByAttribute.click("xpath", IdentityObjects.addFilterLnk, "Click on Add icon to enter the filter");
				Utility.pause(2);
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
				
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ENTER).build().perform();
				Utility.pause(20);
				
				if(!dupIdentity){
					if(driver.findElements(By.xpath("((//div[text()='"+fieldValue1+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]")).size()>0){
						WebElement record=driver.findElement(By.xpath("((//div[text()='"+fieldValue1+"'])[1]/ancestor::tr//div[contains(@class,'x-grid-cell-inner ')])[2]"));
						identityCode=record.getText();	
						action.doubleClick(record).perform();
						Utility.pause(15);
						String searchResult= "//label[contains(text(),'"+fieldValue1+"')]";
						if(Utility.verifyElementPresentReturn(searchResult,fieldValue1,true,false)){
							logger.log(LogStatus.INFO ,"Search result record appeared with identity code as : "+ identityCode);
						}
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
							logger.log(LogStatus.PASS ,"Duplicate identity is created with different identity code");
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
	
	
	public static void editIdentity() throws Throwable
	{

		if(unhandledException==false)
		{
			
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
			
			System.out.println("***************************** Create DuplicateIdentity *********************************");
			try
			{
				if(!(driver.findElements(By.xpath("IdentityObjects.IdentityTabLnk")).size()>0))
					Utility.pause(5);
				
				ByAttribute.mouseHover("xpath", IdentityObjects.IdentityTabLnk, "Mouse Hover on Identity tab");
				Utility.pause(10);
				ByAttribute.click("xpath", IdentityObjects.manageIdentityLnk, "Click on Manage Identity ");
				Utility.pause(20);
				ByAttribute.click("xpath", IdentityObjects.createIdentityLnk, "click on create  icon to create new identity");
				Utility.pause(10);
				
				fillDuplicateProfileInfo();
				ByAttribute.click("xpath", IdentityObjects.saveIconLnk, "Click on save Button ");
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
			
			System.out.println("***************************** Cancel Button in Create Identity *********************************");
			try
			{
				if(!(driver.findElements(By.xpath("IdentityObjects.IdentityTabLnk")).size()>0))
					Utility.pause(5);
				
				ByAttribute.mouseHover("xpath", IdentityObjects.IdentityTabLnk, "Mouse Hover on Identity tab");
				Utility.pause(10);
				ByAttribute.click("xpath", IdentityObjects.manageIdentityLnk, "Click on Manage Identity ");
				Utility.pause(20);
				ByAttribute.click("xpath", IdentityObjects.createIdentityLnk, "click on create  icon to create new identity");
				Utility.pause(10);
				
				if(driver.findElements(By.xpath(IdentityObjects.createIdentityHeader)).size()>0){
					logger.log(LogStatus.INFO, "Create Identity  screen is loaded ");
				}
				
				ByAttribute.click("xpath", IdentityObjects.cancelButtonLnk, "Click on cancel button ");
				Utility.pause(10);
				
				if(driver.findElements(By.xpath(IdentityObjects.identityManagementHeader)).size()>0){
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
					System.out.println("Filter widget is hidden from the screen");
					logger.log(LogStatus.PASS,"Show/Hide filter widget functionality is working fine");
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
			
			System.out.println("***************************** Mandatory Field check in Create Identity *********************************");
			try
			{
				if(!(driver.findElements(By.xpath("IdentityObjects.IdentityTabLnk")).size()>0))
					Utility.pause(5);
				
				ByAttribute.mouseHover("xpath", IdentityObjects.IdentityTabLnk, "Mouse Hover on Identity tab");
				Utility.pause(10);
				ByAttribute.click("xpath", IdentityObjects.manageIdentityLnk, "Click on Manage Identity ");
				Utility.pause(20);
				ByAttribute.click("xpath", IdentityObjects.createIdentityLnk, "click on create  icon to create new identity");
				Utility.pause(10);
				
				ByAttribute.click("xpath", IdentityObjects.saveIconLnk, "click on save  icon ");
				Utility.pause(10);
				
								
				if(driver.findElements(By.xpath(IdentityObjects.errorMessageForFirstName)).size()>0){
					logger.log(LogStatus.INFO, "Error message displayed to enter the mandatory fields");
					Utility.verifyElementPresent(IdentityObjects.errorMessageForFirstName, "ErrorMessage", true, false);
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
			
			System.out.println("***************************** delete single identity *********************************");
			try
			{
				if(!(driver.findElements(By.xpath("IdentityObjects.IdentityTabLnk")).size()>0))
					Utility.pause(5);
				
				createIdentity();
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
				ByAttribute.click("xpath", IdentityObjects.yesButtonToDeleteIdentities, "Confirm deletion of identities");
				Utility.verifyElementPresent("//div[@class='emptyGridMsg']", "Empty Grid Message", true, false);
				logger.log(LogStatus.INFO, "deleted the selected identities");
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
		
				ByAttribute.mouseHover("xpath", IdentityObjects.IdentityTabLnk, "Mouse Hover on Identity tab");
				Utility.pause(5);
				ByAttribute.click("xpath", IdentityObjects.manageIdentityLnk, "Click on Manage Identity ");
				Utility.pause(20);
				
				Actions action = new Actions(driver);
				WebElement idCheckbox = driver.findElement(By.xpath("//div[text()='"+identityCode+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-not-selected')]"));
				action.click(idCheckbox).build().perform();
				Utility.pause(5);
				
				if(driver.findElements(By.xpath("//div[text()='"+identityCode+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-selected')]")).size()>0){
					System.out.println("identity " +identityCode+"  selected for deletion");
					ByAttribute.click("xpath", IdentityObjects.deleteIdentityIconLnk, "Delete identities selected");
					ByAttribute.click("xpath", IdentityObjects.yesButtonToDeleteIdentities, "Confirm deletion of identities");
					Utility.verifyElementPresent("//div[@class='emptyGridMsg']", "Empty Grid Message", true, false);
					logger.log(LogStatus.INFO, "deleted the selected identity");
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
			
			System.out.println("***********verify Cancel And Close Button In Deleted Items**********");

			try{
			
				System.out.println("Click on icon to display the menu");
				ByAttribute.click("xpath", IdentityObjects.deletedItemsIcon, "Click on icon to display the menu");
				Utility.pause(1);
			
				System.out.println("Click on Deleted Items link from the menu");
				ByAttribute.click("xpath", IdentityObjects.deletedItemsLnk, "Open Deleted Items");
				Utility.pause(5);
			
				if(!(driver.findElements(By.xpath(IdentityObjects.deletedIdentityDocumentsHeader)).size()>0)){
					Utility.pause(2);
				}
				Utility.verifyElementPresent(IdentityObjects.deletedIdentityDocumentsHeader, "Deleted Identity Documents", true, false);
				logger.log(LogStatus.INFO, "Deleted identities pop up appeared");
				System.out.println("Deleted identities pop up appeared");
				ByAttribute.click("xpath", IdentityObjects.closeButtonInDeletedItemsLnk, "Clicked on close icon in the pop up box");
				Utility.pause(5);
				Utility.verifyElementPresent(IdentityObjects.identityManagementHeader, "Identity management header", true, false);
				logger.log(LogStatus.INFO, "Deleted identities pop up box closed , we are back on manage identity screen");
				System.out.println("Deleted identities pop up box closed afte clicking close icon, we are back on manage identity screen");
			
				/*
				 * Verifying Cancel button functionality on Deleted Identity Documents pop up
				 */
				System.out.println("Click on icon to display the menu");
				ByAttribute.click("xpath", IdentityObjects.deletedItemsIcon, "Click on icon to display the menu");
				Utility.pause(1);
			
				System.out.println("Click on Deleted Items link from the menu");
				ByAttribute.click("xpath", IdentityObjects.deletedItemsLnk, "Open Deleted Items");
				Utility.pause(5);
			
				if(!(driver.findElements(By.xpath(IdentityObjects.deletedIdentityDocumentsHeader)).size()>0)){
					Utility.pause(2);
				}
				Utility.verifyElementPresent(IdentityObjects.deletedIdentityDocumentsHeader, "Deleted Identity Documents", true, false);
				logger.log(LogStatus.INFO, "Deleted identities pop up appeared");
				System.out.println("Deleted identities pop up appeared");
				ByAttribute.click("xpath", IdentityObjects.cancelButtonInDeletedItemsLnk, "Clicked on cancel button in the pop up box");
				Utility.pause(5);
				Utility.verifyElementPresent(IdentityObjects.identityManagementHeader, "Identity Management Header", true, false);
				logger.log(LogStatus.INFO, "Deleted identities pop up box closed , we are back on manage identity screen");
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
			
			System.out.println("**************Recover Deleted Items****************");
		
			try{
			
				System.out.println("Click on icon to display the menu");
				ByAttribute.click("xpath", IdentityObjects.deletedItemsIcon, "Click on icon to display the menu");
				Utility.pause(1);
			
				System.out.println("Click on Deleted Items link from the menu");
				ByAttribute.click("xpath", IdentityObjects.deletedItemsLnk, "Open Deleted Items");
				Utility.pause(5);
			
				if(!(driver.findElements(By.xpath("//div[contains(@id,'MainPanelTrashCanPopup') and text()='Deleted identity Documents']")).size()>0)){
					Utility.pause(2);
				}
			
				SimpleDateFormat f = new SimpleDateFormat("MM/dd/yy");
				String strDate = f.format(new Date());
				ByAttribute.click("xpath", IdentityObjects.deletedSinceDateValue, "Select Deleted Since date value");
				ByAttribute.setText("xpath", IdentityObjects.deletedSinceDateValue,strDate, "Select Deleted Since date value");
				Utility.pause(5);
			
				String idCode = driver.findElement(By.xpath(("(//td[contains(@class,'x-grid-cell x-grid-td x-grid-cell-gridcolumn')])[1]//div"))).getText();
			
				if(Utility.checkIfStringIsNotNull(idCode)){
					if(Utility.compareStringValues(idCode, identityCode)){
						System.out.println("Deleted identity is present in recently deleted items");
						Actions action = new Actions(driver);
						WebElement idCheckbox = driver.findElement(By.xpath("//div[text()='"+identityCode+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-not-selected')]"));
						action.click(idCheckbox).build().perform();
						Utility.pause(5);
						if(driver.findElements(By.xpath("//div[text()='"+identityCode+"']/parent::td/preceding-sibling::td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-selected')]")).size()>0){
							System.out.println("identity " +identityCode+"  selected for restoration");
							ByAttribute.click("xpath", IdentityObjects.restoreButtonInDeletedItemsLnk, "Restore selected identity");
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
				Utility.verifyElementPresent("(//td[contains(@class,'x-grid-cell x-grid-td x-grid-cell-gridcolumn')])[1]//div[text()='"+identityCode+"']", identityCode, true, false);
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
			
			System.out.println("********************Fill Profile Info********************");
			try{
				String EmployeeCreationTemplateFile = testDataDirectory + "/CreateIdentity_Template.csv";
				String EmployeeCreationDataFile=testDataDirectory+ "/CreateIdentity.csv";
				TestDataInterface.compileTwoRowDataTemplate(EmployeeCreationTemplateFile, EmployeeCreationDataFile);
		
				ArrayList<String> headers = Utility.getCSVRow(EmployeeCreationDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(EmployeeCreationDataFile, 0);
				int len = headers.size();
				String firstName= null,lastName= null,validFrom= null,validTo = null,emailId= null,empType= null,header;
				
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
				ByAttribute.setText("xpath", IdentityObjects.emailIdLnk, emailId, "Enter email Id");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseContactInfoSection, "collapse Contact Information Section");
				Utility.pause(2);
				ByAttribute.click("xpath", IdentityObjects.collapseOrganisationInfoSection, "collapse Organisation Information Section");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.validFromLnk, validFrom, "Enter valid From");
				Utility.pause(2);
				ByAttribute.setText("xpath", IdentityObjects.validToLnk, validTo, "Enter valid To");
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
				String EmployeeCreationTemplateFile = testDataDirectory + "/EditIdentity_Template.csv";
				String EmployeeCreationDataFile=testDataDirectory+ "/EditIdentity.csv";
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
				ByAttribute.click("xpath", IdentityObjects.saveIconLnk, "Click on save Button ");
				Utility.pause(20);
		
				ByAttribute.mouseHover("xpath", IdentityObjects.IdentityTabLnk, "Mouse Hover on Identity tab");
				Utility.pause(5);
				ByAttribute.click("xpath", IdentityObjects.manageIdentityLnk, "Click on Manage Identity ");
				Utility.pause(20);
				
				WebElement finalJobTitle = driver.findElement(By.xpath("(//div[@class='x-grid-cell-inner '])[7]"));
				String jbTitle = finalJobTitle.getText();
				if(Utility.compareStringValues(jbTitle, jobTitle)){
					System.out.println("job title is successfully updated");
					logger.log(LogStatus.INFO, "job title is successfully updated");
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
			
				String EmployeeCreationTemplateFile = testDataDirectory + "/CreateDuplicateIdentity_Template.csv";
				String EmployeeCreationDataFile=testDataDirectory+ "/CreateDuplicateIdentity.csv";
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
			
			System.out.println("****************Fill Access Info******************");
			try{
				String accessName= null,validFrom= null,validTo = null,header,temp;
				Date date;
		
				String EmployeeCreationAccessTemplateFile = testDataDirectory + "/Accesses_Template.csv";
				String EmployeeCreationAccessDataFile=testDataDirectory+ "/Accesses.csv";
				TestDataInterface.compileTwoRowDataTemplate(EmployeeCreationAccessTemplateFile, EmployeeCreationAccessDataFile);
		
				ArrayList<String> headers = Utility.getCSVRow(EmployeeCreationAccessDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(EmployeeCreationAccessDataFile, 0);
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
		
				ByAttribute.click("xpath", IdentityObjects.addRecordsIconAccessTab, "click on add icon to insert new access");
				Utility.pause(5);
		
				Actions action = new Actions(driver);		
				action.sendKeys(accessName);
				action.build().perform();
				Utility.pause(5);
				WebElement accessValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct')]//li[contains(text(),'Admin User Role')]"));
				action.moveToElement(accessValue).click();
				action.build().perform();
				logger.log(LogStatus.INFO, "Access Value selected");
				Utility.pause(2);
		
				WebElement ele=driver.findElement(By.xpath("//span[text()='Description']"));
				ele.click();
		
				WebElement validFromDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[1]"));
				action.moveToElement(validFromDate).click();
				action.build().perform();
				String validFromDt="(//div[contains(@id,'baseDateTime')]//input[@placeholder='Select Valid From'])[2]";
				ByAttribute.setText("xpath", validFromDt, validFrom, "Enter Valid From");
				Utility.pause(2);
				logger.log(LogStatus.INFO, "Entered valid from");
		
				WebElement validToDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[2]"));
				action.moveToElement(validToDate).click();
				action.build().perform();
				String validToDt =	"(//div[contains(@id,'baseDateTime')]//input[@placeholder='Select Valid To'])[2]";
				ByAttribute.setText("xpath", validToDt, validTo, "Enter Valid TO");
				Utility.pause(2);
				logger.log(LogStatus.INFO, "Entered valid to");
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
				String systemName= null,srcId= null,validTo = null,validFrom = null,header,temp;
				Date date;
		
				String EmployeeCreationSystemTemplateFile = testDataDirectory + "/Systems_Template.csv";
				String EmployeeCreationSystemDataFile=testDataDirectory+ "/Systems.csv";
				TestDataInterface.compileTwoRowDataTemplate(EmployeeCreationSystemTemplateFile, EmployeeCreationSystemDataFile);
				ArrayList<String> headers = Utility.getCSVRow(EmployeeCreationSystemDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(EmployeeCreationSystemDataFile, 0);
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
				ByAttribute.click("xpath",  IdentityObjects.addRecordsIconSystemsTab, "click on add icon to insert new System");
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
        
				WebElement validFromDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[3]"));
				action.moveToElement(validFromDate).click();
				action.build().perform();
				String validFromDt="(//input[@placeholder='Select Valid From'])[2]";
				ByAttribute.setText("xpath", validFromDt, validFrom, "Enter Valid From");
				Utility.pause(2);
				logger.log(LogStatus.INFO, "Entered valid from");
		
				WebElement validToDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[4]"));
				action.moveToElement(validToDate).click();
				action.build().perform();
				String validToDt =	"(//input[@placeholder='Select Valid To'])[2]";
				ByAttribute.setText("xpath", validToDt, validTo, "Enter Valid TO");
				Utility.pause(2);
				logger.log(LogStatus.INFO, "Entered valid to");
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
				String assetCode= null,validTo = null,validFrom = null,header,temp;
				Date date;
		
				String EmployeeCreationAssetTemplateFile = testDataDirectory + "/Assets_Template.csv";
				String EmployeeCreationAssetDataFile=testDataDirectory+ "/Assets.csv";
				TestDataInterface.compileTwoRowDataTemplate(EmployeeCreationAssetTemplateFile, EmployeeCreationAssetDataFile);
		
				ArrayList<String> headers = Utility.getCSVRow(EmployeeCreationAssetDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(EmployeeCreationAssetDataFile, 0);
				int len = headers.size();
		
				for (int i=0;i<len;i++){
					header= headers.get(i);
					System.out.println("heading "+ (i+1) +" "+ header);
					int index = Utility.getIndex(headers,header);
					for(ArrayList<String> userData : usersData) {
				
						switch (header.toLowerCase()) {
						case "assetcode":
							assetCode = Utility.getIndexValue(userData, index);
							break;
						case "validfrom":
							temp=Utility.getIndexValue(userData, index);
							if(!Utility.compareStringValues(temp, header)){
								date = new SimpleDateFormat("MM-dd-yy").parse(temp);
								validFrom = new SimpleDateFormat("MM/d/yy hh:mm a").format(date);
							}
							break;
						case "validto":
							temp=Utility.getIndexValue(userData, index);
							if(!Utility.compareStringValues(temp, header)){
								date = new SimpleDateFormat("MM-dd-yy").parse(temp);
								validTo = new SimpleDateFormat("MM/d/yy hh:mm a").format(date);
							}
							break;
						default: 
							logger.log(LogStatus.ERROR, "Failed: Field {" +header+"} Not Found ");
							throw new UnsupportedOperationException();
						}
					}
				}
		
				/*
				 * Verify Cancel button on Assets screen
				 */
		
				String newRadioButton = "(//label[text()='New']//preceding::span[contains(@class,'x-form-radio x-form-radio-default')]//input[@class='x-form-cb-input'])[1]";
				ByAttribute.click("xpath", newRadioButton, "click on New radio button");
				Utility.pause(2);
				logger.log(LogStatus.INFO, "By Clicking on Cancel button , we will be navigated to Existing asset screen");
				ByAttribute.click("xpath", IdentityObjects.cancelAssetButtonLnk, "click on cancel button");
				logger.log(LogStatus.INFO, "Navigated to Existing asset screen");
				Utility.verifyElementPresent("//div[contains(@class,'x-column-header-text')]//span[text()='Asset']", "Existing asset screen", true, false);
		
				/*
				 * verify Reset button on New asset screen
				 */
				ByAttribute.click("xpath", newRadioButton, "click on New radio button");
				Utility.pause(2);
				String dropdownArrow="(//span[text()='Asset Code']//ancestor::label//following::div[contains(@class,' x-form-trigger-default x-form-arrow-trigger')])[1]";
				ByAttribute.click("xpath", dropdownArrow, "click on dropdown to select asset code");
				Utility.pause(40);
		
				for(int i=0;i<10;i++){
					if(driver.findElements(By.xpath("//div[text()='"+assetCode+"']")).size()>0){
						break;
					}
					else
						Utility.pause(10);
				}
				String asstCode="//div[text()='"+assetCode+"']";
				ByAttribute.click("xpath", asstCode, " select asset code");
				Utility.pause(2);
				Utility.verifyElementPresent("//input[contains(@class,'x-form-required-field x-form-text x-form-text-default ') and @placeholder='Select Asset']", "Asset Code", true, false);
				System.out.println("Asset Code is filled");
			
				ByAttribute.click("xpath", IdentityObjects.resetButtonAssetScreen, " Click on Reset button on add new asset screen");
				Utility.verifyElementPresent("//input[contains(@class,'x-form-required-field x-form-text x-form-text-default ') and @placeholder='Select Asset']", "Asset Code", true, false);
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
				ByAttribute.setText("xpath", validFromDt, validFrom, "Enter Valid From");
				Utility.pause(2);
				ByAttribute.setText("xpath", validToDt, validTo, "Enter Valid TO");
				Utility.pause(2);
			
				/*
				 * Add notes while adding new asset
				 */
	//			String notes = "//span[contains(@id,'button') and text()='Notes']";
//				String comments = "New Asset assignment";
//				ByAttribute.click("xpath",notes, " Click notes button");
//				Utility.pause(2);
//				if(driver.findElements(By.xpath("//div[contains(@id,'header-title-textEl') and text()='Add Comment']")).size()>0){
//					ByAttributeAngular.setText("xpath", "//body[contains(@class,'mce-content-body ')]//p",comments , "Adding comments while assigning new asset");
//					ByAttributeAngular.click("xpath", IdentityObjects.addCommentsButtonLnk, "Click on Add Comments button");
//					ByAttributeAngular.click("xpath", IdentityObjects.closeNotesWindowLnk, "close notes window");
//				}
//				else{
//					logger.log(LogStatus.INFO,"window not opened to add notes");
//				}
			
				
				ByAttribute.click("xpath", IdentityObjects.confirmButton, " Click confirm ");
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
			
			System.out.println("**************Fill prerequisite info***********");
			try{
				String type= null,prerequisite=null,validFrom= null,validTo = null,header;
		
				String prerequisiteTemplateFile = testDataDirectory + "/Prerequisite_Template.csv";
				String prerequisiteDataFile=testDataDirectory+ "/Prerequisite.csv";
				TestDataInterface.compileTwoRowDataTemplate(prerequisiteTemplateFile, prerequisiteDataFile);
		
				ArrayList<String> headers = Utility.getCSVRow(prerequisiteDataFile, 1);
				ArrayList<ArrayList<String>> usersData = Utility.getCSVData(prerequisiteDataFile, 0);
				int len = headers.size();
				for (int i=0;i<len;i++){
					header= headers.get(i);
					System.out.println("heading "+ (i+1) +" "+ header);
					int index = Utility.getIndex(headers,header);
					for(ArrayList<String> userData : usersData) {
				
						switch (header.toLowerCase()) {
						case "type":
							type = Utility.getIndexValue(userData, index);
							break;
						case "prerequisite":
							prerequisite = Utility.getIndexValue(userData, index);
							break;
						case "validfrom":
							validFrom=Utility.getIndexValue(userData, index);
							break;
						case "validto":
							validTo=Utility.getIndexValue(userData, index);
							break;
						default: 
							logger.log(LogStatus.ERROR, "Failed: Field {" +header+"} Not Found ");
							throw new UnsupportedOperationException();
						}
					}
				}
		
//				ByAttribute.click("xpath", IdentityObjects.addRecordsIconPrerequisiteTab, "click on add icon to insert prerequisite");
//				Utility.pause(5);
		
				Actions action = new Actions(driver);
				WebElement addIcon = driver.findElement(By.xpath(IdentityObjects.addRecordsIconPrerequisiteTab));
				action.moveToElement(addIcon).click();
				action.sendKeys(type);
				action.build().perform();
				Utility.pause(10);
				WebElement typeValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct x-unselectable x-scroller')]//li[text()='"+type+"']"));
				action.moveToElement(typeValue).click();
				action.build().perform();
				logger.log(LogStatus.INFO, "prerequisite type Value selected");
				Utility.pause(2);
		
				WebElement prerequisiteType=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseComboColumn')])[2]//div"));
				action.moveToElement(prerequisiteType).click();
				action.sendKeys(prerequisite);
				action.build().perform();
               
				WebElement prerequisiteTyp=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseComboColumn')])[2]//div"));
				action.moveToElement(prerequisiteTyp).click();
				action.sendKeys(prerequisite);
				action.build().perform();
				Utility.pause(5);
				WebElement prerequisiteValue=driver.findElement(By.xpath("//div[contains(@class,'x-boundlist-list-ct x-unselectable x-scroller')]//li[text()='"+prerequisite+"']"));
				action.moveToElement(prerequisiteValue).click();
				action.build().perform();
				logger.log(LogStatus.INFO, "Entered the Prerequisite");
		
				WebElement validFromDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[1]"));
				action.moveToElement(validFromDate).click();
				action.build().perform();
        
				String validFromDt="(//div[contains(@id,'baseDateTime')]//input[@placeholder='Select Valid From'])[3]";
				ByAttribute.setText("xpath", validFromDt, validFrom, "Enter Valid From");
				Utility.pause(2);
				logger.log(LogStatus.INFO, "Entered valid from");
		
		
//				WebElement validToDate=driver.findElement(By.xpath("(//td[contains(@class,'x-grid-cell-baseDateTimeColumn')])[2]"));
//				action.moveToElement(validToDate).click();
//        		action.build().perform();
//				String validToDt =	"(//div[contains(@id,'baseDateTime')]//input[@placeholder='Select Valid To'])[3]";
		
//				action.sendKeys(Keys.TAB).build().perform();
//				String validToDt = "//div[contains(@class,'x-form-trigger-default x-form-date-trigger x-form-date-trigger-default  x-form-trigger-focus x-form-trigger-over')]/preceding-sibling::div//input";
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


	private static void verifyReconData() throws Throwable{
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
			
				logger.log(LogStatus.INFO, "Navigating to Access tab to verify recon data on UI");
				verifyReconDataFromUI(description);
		
				logger.log(LogStatus.INFO, "verify recon data from DB");
				verifyReconDataFromDB(description);
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}
	}

	public static void verifyReconDataFromDB(String description) throws Throwable {
		if(unhandledException==false)
		{
			
			System.out.println("**********Verify Recon Data from DB*********");
			try{
					String query = "select count(*) from aehsc.stg_role_data where int_status = '0'";
					ArrayList<ArrayList<Object>> rs = MsSql.getResultsFromDatabase(query);
					for (int i=0 ; i < rs.size();i++){
						System.out.println("data obtained from db : " + rs.get(i));
					}
					logger.log(LogStatus.PASS, "total number of active records after role recon was completed were: " +activeRoleReconRecords);
					logger.log(LogStatus.PASS, "total number of records fetched from DB are: " + rs.get(0));
				}
				catch(Exception e)	{
					String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
					Utility.recoveryScenario(nameofCurrMethod, e);
				}
		}
	}

	public static void verifyReconDataFromUI(String description) throws Throwable {
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
					ByAttribute.click("xpath", AccessObjects.addFilterLnk, "Click on Add icon to enter the filter");
					Utility.pause(2);
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
								ByAttribute.click("xpath", AccessObjects.enterFieldNameToFilter, "click to enter the value");
								Utility.pause(2);
								ByAttribute.setText("xpath", AccessObjects.enterFieldValue1,srcId, "Enter the value for Filtering");
								Utility.pause(2);
								
								action = new Actions(driver);
								action.sendKeys(Keys.ENTER);
								action.build().perform();
								Utility.pause(2);
								flag = true;
								logger.log(LogStatus.PASS, "Recon data is present on UI");
								Utility.verifyElementPresent("//div[text()='"+description+"']", "Role Name", true, false);
								System.out.println("Recon data is present on UI");
							}
						}
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

	public static void deleteReconRecord() throws Throwable {
		if(unhandledException==false)
		{
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
				action.sendKeys("Role Data").build().perform();
				Utility.pause(5);
							
			//	searching for recon job to be deleted
			
				WebElement searchBar = driver.findElement(By.xpath(ReconObjects.searchBarInRecon));
				searchBar.click();
				Utility.pause(20);
				ByAttribute.setText("xpath", ReconObjects.searchBarInRecon, AGlobalComponents.jobName, "Searching recon job on recon set up screen to delete ");
				action.sendKeys(Keys.ENTER).build().perform();
				String jobNameLocator = "//div[text()='"+AGlobalComponents.jobName+"']";
			
				if(Utility.verifyElementPresentReturn(jobNameLocator, AGlobalComponents.jobName, true, false) && ByAttribute.verifyCheckBox("xpath",ReconObjects.checkboxLnkOfReconJob)){
					logger.log(LogStatus.INFO,"Recon job found for deletion");
					ByAttribute.click("xpath", ReconObjects.deleteIconReconSetup, "click on minus icon to delete the record");
					ByAttribute.click("xpath", ReconObjects.submitButtonLnk, "Click submit after deleting the record");
					Utility.pause(5);
					ByAttribute.click("xpath", ReconObjects.confirmPopUpLnk,"click confirm button on popup");
					Utility.pause(10);
					if(!(Utility.verifyElementPresentReturn(jobNameLocator, AGlobalComponents.jobName, true, false))){
						logger.log(LogStatus.PASS,"Record deleted");
					}
					else{
						logger.log(LogStatus.FAIL,"Unable to delete the record");
					}
				}
				else{
					logger.log(LogStatus.INFO,"Recon job not found for deletion");
				}
			}
			catch(Exception e)
			{		
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
				ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.reconSetUpLnk, "Click on Recon Setup ");
						
				Utility.pause(40);
				
				ByAttribute.click("xpath",ReconObjects.addReconRowLnk,"Click on Add icon to initiate Recon");
				Utility.pause(20);
				while(!ByAttribute.verifyCheckBox("xpath",ReconObjects.checkboxLnk)){
					ByAttribute.click("xpath",ReconObjects.addReconRowLnk,"Click on Add icon to initiate Recon");
					Utility.pause(10);
				}
						
				if(ByAttribute.verifyCheckBox("xpath",ReconObjects.checkboxLnk))
				{
					initiateReconJob();
					checkJobInReconMonitor();		
				}
				else{
					logger.log(LogStatus.FAIL, "Checkbox not visible ");
				}
				
				
			}
			catch(Exception e)
			{		
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}	
		}	
		
	}

	public static void deleteMultipleReconRecords() throws Throwable {
		if(unhandledException==false)
		{
			System.out.println("***************************Deleting multiple recon jobs *********************************");
			try
			{
				ByAttribute.mouseHover("xpath", ReconObjects.reconTabLnk, "Mouse Hover on Recon tab");
				Utility.pause(2);
				ByAttribute.click("xpath", ReconObjects.reconSetUpLnk, "Click on Recon Setup ");
						
				Utility.pause(40);
				for (int i=0 ; i<2 ; i++){
					ByAttribute.click("xpath",ReconObjects.addReconRowLnk,"Click on Add icon to initiate Recon");
					Utility.pause(20);
					while(!ByAttribute.verifyCheckBox("xpath",ReconObjects.checkboxLnk)){
						ByAttribute.click("xpath",ReconObjects.addReconRowLnk,"Click on Add icon to initiate Recon");
						Utility.pause(10);
					}
						
					if(ByAttribute.verifyCheckBox("xpath",ReconObjects.checkboxLnk))
					{
						initiateReconJob();
						jobNames.add(i, AGlobalComponents.jobName);
					}
					else{
						logger.log(LogStatus.FAIL, "Checkbox not visible ");
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
				logger.log(LogStatus.PASS, "deleted the selected identities");
			}
			catch(Exception e){
				String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
				Utility.recoveryScenario(nameofCurrMethod, e);
			}
		}
		
	}

}
