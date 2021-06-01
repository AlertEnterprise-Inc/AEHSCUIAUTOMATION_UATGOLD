package CommonClassReusables;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.sql.ResultSetMetaData;
import java.sql.Clob;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.relevantcodes.extentreports.LogStatus;





public class Utility extends BrowserSelection {
	
	public static String testDataDirectory= "Test_Data/IdentityManagement";
	private static SecureRandom random = new SecureRandom();
	
	/**
	* <h1>recoveryScenario</h1>
	* This is Recovery Scenario for all Unhandled Exceptions
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String methodName, Exception exception
	* @return  	none
	*/
	
	public static void recoveryScenario (String methodName, Exception exception) throws Throwable
	{
		System.out.println("\n******************* RECOVERY SCENARIO INVOKED FOR METHOD :       { " +methodName+ " } *******************");
		System.out.println("******************* RECOVERY SCENARIO INVOKED FOR EXCEPTION :       { " +exception+ " } *******************");
		logger.log(LogStatus.FAIL, "\n******************* RECOVERY SCENARIO INVOKED FOR METHOD :       { " +methodName+ " } *******************");	
		logger.log(LogStatus.FAIL, "******************* RECOVERY SCENARIO INVOKED FOR EXCEPTION :       { " +exception+ " } *******************");
		
		unhandledException = true;	
		Utility.takeScreenshot("bug screnshot1");
		logger.log(LogStatus.ERROR, "Screenshort of page where Exception Invoked : " + imgeHtmlPath);
		
		switchToDefaultBrowserDriver();
	
		WebDriver driver2=Utility.openPrivateBrowser();
		default_driver=driver2;
		driver.close();
		driver=default_driver;  
		driver.get(AGlobalComponents.applicationURL);
		
		if(ByAttribute.getListSize("xpath","//input[@placeholder='Enter Username']","Recovery Scenario : Sign In page is present")==1)
		{
			System.out.println("RECOVERY SCENARIO : Successfully new Driver launched and Application Opened.");
			logger.log(LogStatus.INFO, "RECOVERY SCENARIO : Successfully new Driver launched and Application Opened.");
		} else	{
			System.out.println("Recovery Scenario : Sign In is not present.");
			logger.log(LogStatus.FAIL, "RECOVERY SCENARIO : Sign In is not present.");		
		}	
		exception = null;	
	}

	
	/**
	* <h1>verifyElementEqualsReturn</h1>
	* This is Mathod to Compare Expected and Actual Results
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String actualResult, String expectedResult
	* @return  	boolean
	*/
	
	public static boolean verifyElementEqualsReturn(String actualResult, String expectedResult) throws Exception
	{
		if(actualResult.equals(expectedResult)) {
			return true;
		}else{
			return false;
		}
	}

	
	/**
	* <h1>copyTextUsingKeyboard</h1>
	* This is Mathod to Copy text to Clipboard using ctrl+c
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String fieldXpath
	* @return  	String copyvalue
	*/
	
	public static String copyTextUsingKeyboard(String fieldXpath) throws UnsupportedFlavorException, IOException, InterruptedException
	{
		ByAttribute.click("xpath",fieldXpath,"Click on text field");
		driver.findElement(By.xpath(fieldXpath)).sendKeys(Keys.LEFT_CONTROL+"a");
		Thread.sleep(2000);
		driver.findElement(By.xpath(fieldXpath)).sendKeys(Keys.LEFT_CONTROL+"c");
		Toolkit toolkit1 = Toolkit.getDefaultToolkit();
		Clipboard clipboard1 = toolkit1.getSystemClipboard();
		String copyValue1 = (String) clipboard1.getData(DataFlavor.stringFlavor);
		return copyValue1;
	}
	
	
	/**
	* <h1>verifyTextField</h1>
	* This is Mathod to Verify Text Field
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String xpath
	* @return  	none
	**/
	
	public static void verifyTextField(String xpath){
		for (int g=0;g<5;g++){
			switch(g){
			case 0:
				ByAttribute.clearSetText("xpath",xpath, Utility.UniqueString(10),"Enter String in text field");
				break;
			case 1:
				ByAttribute.clearSetText("xpath",xpath, Utility.UniqueString(5)+"@"+Utility.UniqueNumber(4),"Enter alphanumeric and a special character in text field");
				break;
			case 2:
				ByAttribute.clearSetText("xpath",xpath,Utility.UniqueNumber(7)+"."+Utility.UniqueNumber(2),"Enter decimal value in text field");
				break;
			case 3:
				ByAttribute.clearSetText("xpath",xpath,"-"+Utility.UniqueNumber(9),"Enter 9 digit numeric value in text field");
				break;
			case 4:
				ByAttribute.clearSetText("xpath",xpath,"!@#$)(*%^*","Enter Special Characters in text field");
				break;
			}
		}
	}
	
	
	/**
	* <h1>verifyElementPresentReturn</h1>
	* This is Mathod to Verify Element Presence
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass,boolean stopExecution
	* @return  	boolean
	**/
	
	public static boolean verifyElementPresentReturn(String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass,boolean stopExecution) throws Exception
	{
		try{
			Assert.assertTrue(driver.findElement(By.xpath(verifyElementXpath)).isDisplayed());
			WebElement element = driver.findElement(By.xpath(verifyElementXpath));
			Jexecutor.highlightElement(element);
			System.out.println("Successfully: "+fieldName+" is present.");
			if(takeScreenshotIfPass)
			{
				Utility.takeScreenshot(Utility.UniqueNumber(5));
				logger.log(LogStatus.PASS,"Successfully: "+fieldName+" is present."+ imgeHtmlPath);
			}
			else
			{
				logger.log(LogStatus.PASS,"Successfully: "+fieldName+" is present.");	
			}		
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Failed: "+fieldName+" is not present.");
			Utility.takeScreenshot(Utility.UniqueNumber(5));
			logger.log(LogStatus.INFO, "Failed: "+fieldName+" is not present. Screenshort of BUGG :" + e + imgeHtmlPath);
			if(stopExecution)
			{
				throw (e);
			}
			return false;
		}
	}
	
	
	/**
	* <h1>verifyElementNotPresent</h1>
	* This is Mathod to Verify Element Absence
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass
	* @return  	none
	**/
	
	public static void verifyElementNotPresent(String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass) throws Exception
	{
		try{
			Assert.assertTrue(driver.findElement(By.xpath(verifyElementXpath)).isDisplayed());
			WebElement element = driver.findElement(By.xpath(verifyElementXpath));
			Jexecutor.highlightElement(element);
			System.out.println("Failed: "+fieldName+" is present.");
			Utility.takeScreenshot(Utility.UniqueNumber(5));
			logger.log(LogStatus.FAIL, "Failed: "+fieldName+" is present. Screenshort of BUGG :"+ imgeHtmlPath);
		}catch(Exception e){
			System.out.println("Successfully: "+fieldName+" is not present.");
			if(takeScreenshotIfPass)
			{
				Utility.takeScreenshot(Utility.UniqueNumber(5));
				logger.log(LogStatus.PASS,"Successfully: "+fieldName+" is not present."+ imgeHtmlPath);
			}
			else
			{
				logger.log(LogStatus.PASS,"Successfully: "+fieldName+" is not present.");	
			}		    	
		}
	}
	
	
	/**
	* <h1>verifyElementPresent</h1>
	* This is Mathod to Verify Element Presence
	* @author  	Jiten Khanna
	* @modified 05-29-2020
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass,boolean stopExecution
	* @return  	none
	**/
	
	public static boolean verifyElementPresent(String verifyElementXpath, String fieldName, boolean stopExecution) throws Exception {
		boolean status=false;
		try {
			Assert.assertTrue(driver.findElement(By.xpath(verifyElementXpath)).isDisplayed());
			WebElement element = driver.findElement(By.xpath(verifyElementXpath));
			Jexecutor.highlightElement(element);
			System.out.println("Successfully: " + fieldName + " is present.");
			if (AGlobalComponents.takeScreenshotIfPass) {
				Utility.takeScreenshot(Utility.UniqueNumber(5));
				logger.log(LogStatus.PASS, "Successfully: " + fieldName + " is present." + imgeHtmlPath);
				status=true;
			} else {
				logger.log(LogStatus.PASS, "Successfully: " + fieldName + " is present.");
				status=true;
			}
		} catch (Exception e) {
			System.out.println("Failed: " + fieldName + " is not present.");
			Utility.takeScreenshot(Utility.UniqueNumber(5));
			logger.log(LogStatus.FAIL,
					"Failed: " + fieldName + " is not present. Screenshort of BUGG :" + e + imgeHtmlPath);
			if (stopExecution) {
				throw (e);
			}
		}
		return status;
	}
		
	
	/**
	* <h1>verifyElementEnabled</h1>
	* This is Mathod to Verify Element is Enabled
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass,boolean stopExecution
	* @return  	none
	**/
	
	public static void verifyElementEnabled(String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass,boolean stopExecution) throws Exception
	{
		try{
			Assert.assertTrue(driver.findElement(By.xpath(verifyElementXpath)).isEnabled());
			WebElement element = driver.findElement(By.xpath(verifyElementXpath));
			Jexecutor.highlightElement(element);
			System.out.println("Successfully: "+fieldName+" is Enabled.");
			if(takeScreenshotIfPass)
			{
				Utility.takeScreenshot(Utility.UniqueNumber(5));
				logger.log(LogStatus.PASS,"Successfully: "+fieldName+" is Enabled."+ imgeHtmlPath);
			}
			else
			{
				logger.log(LogStatus.PASS,"Successfully: "+fieldName+" is Enabled.");	
			}		    	
		}
		catch(Exception e)
		{
			System.out.println("Failed: "+fieldName+" is not present.");
			Utility.takeScreenshot(Utility.UniqueNumber(5));
			logger.log(LogStatus.FAIL, "Failed: "+fieldName+" is not Enabled. Screenshort of BUGG :" + e + imgeHtmlPath);
			if(stopExecution)
			{
				throw (e);
			}
		}
	}
		
	
	/**
	* <h1>verifyElementPresentByScrollView</h1>
	* This is Mathod to Verify Element is Present
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass,boolean stopExecution
	* @return  	none
	**/
	
	public static void verifyElementPresentByScrollView(String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass,boolean stopExecution) throws Throwable
	{
		try{
			WebElement element = driver.findElement(By.xpath(verifyElementXpath));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			System.out.println("Successfully: "+fieldName+" is present.");
			if(takeScreenshotIfPass)
			{
				Utility.takeScreenshot(Utility.UniqueNumber(5));
				logger.log(LogStatus.PASS,"Successfully: "+fieldName+" is present."+ imgeHtmlPath);
			}
			else
			{
				logger.log(LogStatus.PASS,"Successfully: "+fieldName+" is present.");	
			}		    	
		}
		catch(Exception e)
		{
			System.out.println("Failed: "+fieldName+" is not present.");
			Utility.takeScreenshot(Utility.UniqueNumber(5));
			logger.log(LogStatus.FAIL, "Failed: "+fieldName+" is not present. Screenshort of BUGG :" + e + imgeHtmlPath);
			if(stopExecution)
			{
				throw (e);
			}
		}
	}

	
	/**
	* <h1>takeScreenshot</h1>
	* This is Mathod to Take Screenshot
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String SSname
	* @return  	none
	**/
	
	public static void takeScreenshot(String SSname) {
		
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			
			//String screenShotPath=System.getProperty("user.dir") +"\\ExtentReport\\Output\\Screenshots\\";
			FileUtils.copyFile(scrFile, new File(AGlobalComponents.extentReportScreenshotFolder+SSname+".png")); 
			imgeHtmlPath=logger.addScreenCapture(AGlobalComponents.extentReportScreenshotFolder+SSname+".png").replace(AGlobalComponents.extentReportScreenshotFolder, "Screenshots\\").replace("file:///", "").replace("<img", "<img width=\"150\" height=\"70\"");                                    
		} catch (IOException e) {
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Exception occur during take ScreenShot: "+SSname);
		}
	}
	
	
	/**
	* <h1>getCurrentDateTime</h1>
	* This is Mathod to get Current Date Time in specified format
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String format
	* @return  	String timeStamp
	**/
	
	public static String getCurrentDateTime (String format){
	    
		String timeStamp = new SimpleDateFormat(format).format(new Date());
	    return timeStamp;
	}
	
	
	/**
	* <h1>getCurrentWorkingDirectory</h1>
	* This is Mathod to get Current Directory
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	none
	* @return  	String workingDir
	**/
	
	public static String getCurrentWorkingDirectory(){
		
		String workingDir = System.getProperty("user.dir");
		return workingDir;
		}

	
	/**
	* <h1>getTitle</h1>
	* This is Mathod to get Page Title
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	none
	* @return  	String getTitle
	**/
	
	public static String getTitle(){
		
		dynamicwait.WAitUntilPageLoad();
		String getTitle=driver.getTitle();
		return getTitle;
	}
	
	
	/**
	* <h1>openUrl</h1>
	* This is Mathod to Launch URL
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String URL
	* @return  	none
	**/
	
	public static void openUrl(String URL){
		
		dynamicwait.WAitUntilPageLoad();
		driver.get(URL);
	}
	
	
	/**
	* <h1>UniqueString</h1>
	* This is Mathod to generate a Unique String of specified length
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	int length
	* @return  	String
	**/
	
	public static String UniqueString(int length){
		
		String UniqueString=RandomStringUtils.randomAlphabetic(length);
		return UniqueString;
	}
	
	
	/**
	* <h1>pressTabKeyUsingRobot</h1>
	* This is Method to Press Tab Key Using Robot Class
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	06-26-2020
	* @param   	NA
	* @return  	NA
	* @throws   AWTException 
	**/
	
	public static void pressTabKeyUsingRobot() throws AWTException, InterruptedException{	
    	dynamicwait.WAitUntilPageLoad();
    	Thread.sleep(1000);
    	Robot rb=new Robot();
    	rb.keyPress(KeyEvent.VK_TAB);
    	Thread.sleep(1000);
    	rb.keyRelease(KeyEvent.VK_TAB);
    	Thread.sleep(200);
    } 
	
	
	/**
	* <h1>UniqueNumber</h1>
	* This is Mathod to generate a Unique Number of specified length
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	int length
	* @return  	String
	**/
	
    public static String UniqueNumber(int length){
		
    	String UniqueNumber=null;
    	if(length==1) {
    		do{
    		UniqueNumber=RandomStringUtils.randomNumeric(length);
    		}while(UniqueNumber=="0");
    	}else {
    		UniqueNumber=RandomStringUtils.randomNumeric(length);
    	}
		return UniqueNumber;
	}
    
    
    /**
	* <h1>getClassName</h1>
	* This is Mathod to get Class Name
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	none
	* @return  	String
	**/
    
    public String getClassName()
    {      
    	Class<?> enclosingClass = getClass().getEnclosingClass();
          if (enclosingClass != null) {
            System.out.println(enclosingClass.getName());
            return enclosingClass.getName();
            } else {
              System.out.println(getClass().getName());
              return getClass().getName();
	    }
	}	
    
    
    /**
	* <h1>pageScrollDown</h1>
	* This is Mathod to scroll down in a page
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	int length
	* @return  	none
	**/
    
    public static void pageScrollDown(int length) throws InterruptedException{
    	JavascriptExecutor jse=(JavascriptExecutor) driver;
    	dynamicwait.WAitUntilPageLoad();
    	jse.executeScript("window.scrollBy(0,"+length+")", "");
    	dynamicwait.WAitUntilPageLoad();
    	Thread.sleep(1000);
    }
   
    
    /**
	* <h1>pageScrollUp</h1>
	* This is Mathod to scroll Up in a page
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	int length
	* @return  	none
	**/
    
    public static void pageScrollUp(int length) throws InterruptedException{
    	JavascriptExecutor jse=(JavascriptExecutor) driver;
    	dynamicwait.WAitUntilPageLoad();
    	jse.executeScript("window.scrollBy(0,"+"-"+length+")", "");
    	dynamicwait.WAitUntilPageLoad();
    	Thread.sleep(1000);
    } 
    
    
    /**
	* <h1>pageScrollDownUsingRobot</h1>
	* This is Mathod to scroll Down in a page using Robot Class
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	none
	* @return  	none
	**/
    
    public static void pageScrollDownUsingRobot() throws AWTException, InterruptedException{	
    	dynamicwait.WAitUntilPageLoad();
    	Thread.sleep(1000);
    	Robot rb=new Robot();
    	rb.keyPress(KeyEvent.VK_PAGE_DOWN);
    	Thread.sleep(3000);
    	rb.keyRelease(KeyEvent.VK_PAGE_DOWN);
    	Thread.sleep(200);
    } 

    
    /**
	* <h1>pageScrollUpUsingRobot</h1>
	* This is Mathod to scroll Up in a page using Robot Class
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	none
	* @return  	none
	**/
    
    public static void pageScrollUpUsingRobot() throws AWTException, InterruptedException{
    	
    	dynamicwait.WAitUntilPageLoad();
    	Thread.sleep(1000);
    	Robot rb=new Robot();
    	rb.keyPress(KeyEvent.VK_PAGE_UP);
    	Thread.sleep(3000);
    	rb.keyRelease(KeyEvent.VK_PAGE_UP);
    	Thread.sleep(200);
    } 
    
    
    /**
	* <h1>openPrivateBrowser</h1>
	* This is Mathod to Open a Private Browser
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	none
	* @return  	Webdriver
	**/
    
    public static WebDriver openPrivateBrowser(){
    	
    	switch (AGlobalComponents.browserName) {
    	
    	case "MF":
       		FirefoxProfile firefoxProfile = new FirefoxProfile();    
    		firefoxProfile.setPreference("browser.privatebrowsing.autostart", true);
//    		PrivateBrowser_driver2 = new FirefoxDriver(firefoxProfile); 
    		PrivateBrowser_driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			PrivateBrowser_driver2.manage().window().maximize();
			PrivateBrowser_driver2.manage().deleteAllCookies();
			break;

		case "GC":
			System.setProperty("webdriver.chrome.driver", "Browser_Files/chromedriver.exe");		
			String downloadFilepath = System.getProperty("user.dir")+ "\\csv Download";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);			
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		    ChromeOptions options = new ChromeOptions();		    
		    options.setExperimentalOption("prefs", chromePrefs);   
		    options.addArguments("incognito");
	
		    options.addArguments("--start-maximized");
	//	    options.addArguments("headless");
		    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		    PrivateBrowser_driver2 = new ChromeDriver(capabilities);
		    PrivateBrowser_driver2.get("chrome://extensions-frame");
			PrivateBrowser_driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		   	break;
			
		default:
			FirefoxProfile firefoxProfile2 = new FirefoxProfile();    
    		firefoxProfile2.setPreference("browser.privatebrowsing.autostart", true);
//    		PrivateBrowser_driver2 = new FirefoxDriver(firefoxProfile2); 
    		PrivateBrowser_driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			PrivateBrowser_driver2.manage().window().maximize();
			PrivateBrowser_driver2.manage().deleteAllCookies();
			break;
			
    	}	
    	return PrivateBrowser_driver2;
    }
    
    
    /**
	* <h1>uploadFile</h1>
	* This is Mathod to Upload a File
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	String fileLocation
	* @return  	none
	**/
    
    public static void uploadFile(String fileLocation) throws AWTException, InterruptedException {
      
        	
            StringSelection stringSelection = new StringSelection(fileLocation);
  		    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        
            Robot robot = new Robot();
            Thread.sleep(3000);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(2000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(1000);
          
    }
    
    
    /**
	* <h1>webElementColor</h1>
	* This is Mathod to Get Web Element Color
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	String xpath
	* @return  	String
	**/
    
    public static String webElementColor(String xpath){
    	String Color = driver.findElement(By.xpath(xpath)).getCssValue("color");
		String[] hexValue = Color.replace("rgba(","").replace(")","").split(",");                           
		hexValue[0] = hexValue[0].trim();
		int hexValue1 = Integer.parseInt(hexValue[0]);                   
		hexValue[1] = hexValue[1].trim();
		int hexValue2 = Integer.parseInt(hexValue[1]);                   
		hexValue[2] = hexValue[2].trim();
		int hexValue3 = Integer.parseInt(hexValue[2]);                   
		String  actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
		return actualColor;
    }

    
    /**
	* <h1>pause</h1>
	* This is Mathod to Pause Execution
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	long timeInSeconds
	* @return  	none
	**/
    
    public static void pause(long timeInSeconds){
		try{
		    logger.log(LogStatus.INFO, "Pausing execution for secs:"+timeInSeconds);
			Thread.sleep(timeInSeconds*1000);
		}
		catch(InterruptedException e){
			System.out.println(e.getMessage());
		}
	}

    
    /**
	* <h1>connectToLatestFrame</h1>
	* This is Mathod to Connect To Latest Frame
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-05-2020
	* @param   	WebDriver driver
	* @return  	none
	**/
    
    public static void connectToLatestFrame(WebDriver driver){
		driver.switchTo().defaultContent();
		
		List<WebElement> frameSet = driver.findElements(By.tagName("iframe"));
		
		int retry = -1;
		while(retry < 10 && frameSet.size()<1){
		    driver.navigate().refresh();
		    Utility.pause(5);
		    retry++;
		    frameSet = driver.findElements(By.tagName("iframe"));
		    logger.log(LogStatus.INFO, "Waiting for frameset to load retry:" + retry);
		}
		
		driver.switchTo().frame(frameSet.size()-1);
	}


    
    public static void connectToLatestFrame2() {
		Utility.pause(2);
		long iCurrentTime = new Date().getTime();
//		int iTimeout = FabricBeans.getGlobalTimeout();
//		System.out.println("iTimeout " + iTimeout);
//		WebDriver _driver = FabricBeans.getDriver();
		if(driver != null){
			driver.switchTo().defaultContent();
			while (true) {
				try {
					long iExecutionTime = new Date().getTime();
					System.out.println("Execution Time " + iExecutionTime);
//					System.out.println("Global Time " + iTimeout * 1000);
//					if ((iExecutionTime - iCurrentTime) > iTimeout * 1000) {
//						System.out.println("timeout occured");
//						break;
//					}
					driver.manage()
					.timeouts()
					.implicitlyWait(4,
							TimeUnit.SECONDS);
					
					List<WebElement> frameset = driver
							.findElements(By.tagName("iframe"));
					driver.switchTo().frame(frameset.size() - 1);
					System.out.println("Connected to frame "
							+ (frameset.size() - 1));
					break;
				} catch (Exception e) {
					System.out.println("exception in framewait");
					Utility.pause(1);
				}
			}
			long timeTaken = new Date().getTime()-iCurrentTime;
			System.out.println(timeTaken);
		}
		else{
//			AELogger.log(Globals.Log.ERROR, "No Web Driver instance found.");
		}
		
	}
    
    
//    public static WebElement waitTillElementPresent(By property) {
////		AELogger.setLoggerObject(Driver._log);
//		int attempts = 0;
//		while (attempts < 2) {
//			try {
//				WebDriverWait _wait = FabricBeans.getWebDriverWait();
//				return _wait.until(ExpectedConditions.visibilityOfElementLocated(property));
//				
//			} catch (StaleElementReferenceException e) {
//				System.err.println(e.getMessage());
//				System.out.println("Attempt number : " + attempts);
////				AELogger.log(Globals.Log.ERROR, e);
//			}
//			catch (Exception e) {
//				System.err.println(e.getMessage());
////				AELogger.log(Globals.Log.ERROR, e);
//			}
//			attempts++;
//		}
//		return null;
//	}
    
    
    /**
	* <h1>readExcel</h1>
	* This is Mathod to Read Data from Excel Sheet
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	05-01-2020
	* @param   	String filePath,String fileName,String sheetName
	* @return  	none
	**/
    
    public void readExcel(String filePath,String fileName,String sheetName) throws IOException{

    //Create an object of File class to open xlsx file

    File file =    new File(filePath+"\\"+fileName);

    //Create an object of FileInputStream class to read excel file

    FileInputStream inputStream = new FileInputStream(file);

    Workbook autoWorkbook = null;

    //Find the file extension by splitting file name in substring  and getting only extension name

    String fileExtensionName = fileName.substring(fileName.indexOf("."));

    //Check condition if the file is xlsx file

    if(fileExtensionName.equals(".xlsx")){

    //If it is xlsx file then create object of XSSFWorkbook class

//    	autoWorkbook = new XSSFWorkbook(inputStream);

    }

    //Check condition if the file is xls file

    else if(fileExtensionName.equals(".xls")){

        //If it is xls file then create object of HSSFWorkbook class

    	autoWorkbook = new HSSFWorkbook(inputStream);

    }

    //Read sheet inside the workbook by its name

    Sheet autoSheet = autoWorkbook.getSheet(sheetName);

    //Find number of rows in excel file

    int rowCount = autoSheet.getLastRowNum()-autoSheet.getFirstRowNum();

    //Create a loop over all the rows of excel file to read it

    for (int i = 0; i < rowCount+1; i++) {

        Row row = autoSheet.getRow(i);

        //Create a loop to print cell values in a row

        for (int j = 0; j < row.getLastCellNum(); j++) {

            //Print Excel data in console

            System.out.print(row.getCell(j).getStringCellValue()+"|| ");

        }

        System.out.println();
    } 

    }  


    /**
   	* <h1>writeExcel</h1>
   	* This is Method to Write Data to Excel Sheet
   	* @author  	Jiten Khanna
   	* @modified 
   	* @version 	1.0
   	* @since   	05-01-2020
   	* @param   	String filePath,String fileName,String sheetName,String[] dataToWrite
     * @return 
   	* @return  	none
   	**/    
    
    public static void writeExcel(String filePath,String fileName,String sheetName,String[] dataToWrite) throws IOException{

        //Create an object of File class to open xlsx file

        File file =    new File(filePath+"\\"+fileName);

        //Create an object of FileInputStream class to read excel file

        FileInputStream inputStream = new FileInputStream(file);

        Workbook autoWorkbook = null;

        //Find the file extension by splitting  file name in substring and getting only extension name

        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file

        if(fileExtensionName.equals(".xlsx")){

        //If it is xlsx file then create object of XSSFWorkbook class

//        	autoWorkbook = new XSSFWorkbook(inputStream);

        }

        //Check condition if the file is xls file

        else if(fileExtensionName.equals(".xls")){

            //If it is xls file then create object of XSSFWorkbook class

        	autoWorkbook = new HSSFWorkbook(inputStream);

        }    

    //Read excel sheet by sheet name    

    Sheet sheet = autoWorkbook.getSheet(sheetName);

    //Get the current count of rows in excel file

    int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

    //Get the first row from the sheet

    Row row = sheet.getRow(0);

    //Create a new row and append it at last of sheet

    Row newRow = sheet.createRow(rowCount+1);

    //Create a loop over the cell of newly created Row

    for(int j = 0; j < row.getLastCellNum(); j++){

        //Fill data in row

        Cell cell = newRow.createCell(j);

        cell.setCellValue(dataToWrite[j]);

    }

    //Close input stream

    inputStream.close();

    //Create an object of FileOutputStream class to create write data in excel file

    FileOutputStream outputStream = new FileOutputStream(file);

    //write data in the excel file

    autoWorkbook.write(outputStream);

    //close output stream

    outputStream.close();
	
    }
    
    
    
    
    public static void updateSelfServiceEmail(String firstName,String dbIP,String username,String password,String dbName) throws Exception{
    	
    	try
    	{
	    	//SQL UPDATE Query to update record.
	    	String emailUpdate = "au"+Utility.UniqueNumber(10);
	    	String query1 =  "update  alnt_applicant set email='"+emailUpdate+"tomation.alertuser04@gmail.com' where first_name='"+firstName+"'";
	
	    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	
//	    	Connection con = DriverManager.getConnection("jdbc:sqlserver://192.168.193.197;user=AEQASS12887;password=aeqa@887;database=AEQASS12887");
	    	Connection con = DriverManager.getConnection("jdbc:sqlserver://"+dbIP+";user="+username+";password="+password+";database="+dbName);
	    	Statement stmt = con.createStatement();
	
	
	    	//Executing SQL UPDATE query using executeUpdate()  method of Statement object.
	    	int count = stmt.executeUpdate(query1);
	    	System.out.println("Number of rows updated by executing Update query =  " + count);
	    	con.close();
    	}
    	catch(SQLException e)
    	{
    		System.out.println(e);
    	}
    	
    	
    }
    
    
public static void updateGuardianUserEmail(String dbIP,String username,String password,String dbName) throws Exception{
    	
    	try
    	{
	    	//SQL UPDATE Query to update record.
	    	String emailUpdate = "au"+Utility.UniqueNumber(10);
	    	String query1 =  "update aaxt_idmuser_details set column6='"+emailUpdate+"tomation.alertuser04@gmail.com'where column6='automation.alertuser04@gmail.com'";
	
	
	    	//Second SQL UPDATE Query to update record.
	    	String query2 =  "update afbcusrmap_master set email_id='"+emailUpdate+"tomation.alertuser04@gmail.com'where email_id='automation.alertuser04@gmail.com'";
	
	    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	
//	    	Connection con = DriverManager.getConnection("jdbc:sqlserver://192.168.193.197;user=AEQA13115;password=aeqa@15;database=AEQA13115");
	    	Connection con = DriverManager.getConnection("jdbc:sqlserver://"+dbIP+";user="+username+";password="+password+";database="+dbName);
	    	Statement stmt = con.createStatement();
	
	
	    	//Executing first SQL UPDATE query using executeUpdate()  method of Statement object.
	    	int count = stmt.executeUpdate(query1);
	    	System.out.println("Number of rows updated by executing query1 =  " + count);
	
	
	
	    	//Executing second SQL UPDATE query using executeUpdate()  method of Statement object.
	    	count = stmt.executeUpdate(query2);
	    	System.out.println("Number of rows updated by executing query2 =  " + count);
	    	con.close();
    	}
    	catch(SQLException e)
    	{
    		System.out.println(e);
    	}
    	
    	
    }
    


public static String validateApplicantCreatedDB(String firstName,String dbIP,String username,String password,String dbName,String typeRDBMS) throws Exception{
	
	String uniqueID = "";
	try
	{
    	//SQL Select Query to Validate Record Created.
    	String query =  "select * from AAXT_IDMUSER_DETAILS where COLUMN4='"+firstName+"' and column5='TEST'";
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

    	//looping through the number of row/rows retrieved after executing SELECT query3
    	while(rs.next()) 
    	{
    		logger.log(LogStatus.PASS, "**********RECORD CREATED IN DB. APPLICANT IS ACTIVE !!!**********");
    		System.out.println(rs.getString("ORG_ID") + "\t");
	    	logger.log(LogStatus.INFO, "DB UPDATED ORGANIZATION ID : "+rs.getString("ORG_ID"));
	    	System.out.println(rs.getString("COLUMN1") + "\t" + "\t");
	    	logger.log(LogStatus.INFO, "DB UPDATED USER ID : "+rs.getString("COLUMN1"));
	    	uniqueID = rs.getString("COLUMN1");
    	}
    	con.close();
	}
	catch(SQLException e)
	{
		System.out.println(e);
	}
	return uniqueID.trim();
	
	
}
    
    
	public static void  testMySQLDB() throws  ClassNotFoundException, SQLException {													
		//Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"		
	    String dbUrl = "jdbc:mysql://35.200.166.156:3306/gdn_db_ps";					
	
		//Database Username		
		String username = "root";	
	    
		//Database Password		
		String password = "Alert1234";				
	
		//Query to Execute		
		String query = "select * from AAXT_IDMUSER_DETAILS where COLUMN4='AUTOHXFK' and column5='TEST';";	
	    
	    //Load mysql jdbc driver		
	    Class.forName("com.mysql.jdbc.Driver");			

		//Create Connection to DB		
		Connection con = DriverManager.getConnection(dbUrl,username,password);
	
		//Create Statement Object		
	   Statement stmt = con.createStatement();					
	
		// Execute the SQL Query. Store results in ResultSet		
		ResultSet rs= stmt.executeQuery(query);							

		// While Loop to iterate through all data and print results		
		while (rs.next()){
	        		String myID = rs.getString(1);								        
	                String myUserID = rs.getString(5);					                               
	                System. out.println(myID+" : "+myUserID);		
	        }		
			 // closing DB Connection		
			con.close();			
	}

	public static boolean compareStringValues(String string1, String string2) {
		boolean matched = false;
		if(checkIfStringIsNotNull(string1)) {
			if(checkIfStringIsNotNull(string2)) {
				if(string1.equalsIgnoreCase(string2)) {
					matched = true;
				}
			}
		}
		return matched;
	}
	public static boolean checkIfStringIsNotNull(String givenStr) {
		boolean stringIsNotNull = false;
		if((givenStr != null)||(givenStr != "")) {
			stringIsNotNull = true;
		}
		return stringIsNotNull;
	}
	
	public static boolean checkIfStringIsNotNullAndNotEmpty(String givenStr) {
		boolean stringIsNotEmptyOrNull = false;
		if(givenStr != null && !givenStr.isEmpty()) {
			stringIsNotEmptyOrNull = true;
		}
		return stringIsNotEmptyOrNull;
	}
	

	
	

   
    
	public static int getIndex(ArrayList<String> list, String value) {
		int index = -1;
		if(checkIfListIsNotNullAndNotEmpty(list)) {
			if(checkIfStringIsNotNullAndNotEmpty(value)) {
				index = list.indexOf(value);
			}
		}
		return index;
	}

	public static boolean checkIfListIsNotNullAndNotEmpty(List<?> list){
		boolean isNotNullAndNotEmpty = false;
		if(list != null && !list.isEmpty()){
			isNotNullAndNotEmpty = true;
		}
		return isNotNullAndNotEmpty;
	}
	
	public static boolean checkIfListIsNotNull(List<?> list) {
		boolean isNotNull = false;
		if(list != null){
			isNotNull = true;
		}return isNotNull;
	}

    
    public static String getIndexValue(ArrayList<String> list, int index) {
		String value = "";
		if(checkIfListIsNotNullAndNotEmpty(list)) {
			try {
				value = list.get(index);
			}
			catch(Exception exception) {
				logger.log(LogStatus.ERROR, exception);
			}
		}
		return value;
	}
    
   

    public static ArrayList<ArrayList<String>> getCSVData(String filePath, int skipLines) {
		List<String[]> contents = null;
		String[] rowContents = null;
		ArrayList<String> row = null;
		ListIterator<String[]> listIterate = null;
		ArrayList<ArrayList<String>> csvData = null;
		try {
			CSVReaderBuilder csvReader = new CSVReaderBuilder(new FileReader(filePath));
			if (csvReader != null) {
				System.out.println("Processing Test Data from => " + filePath);
			}
			while (skipLines > 0) {
				csvReader.build().readNext();
				skipLines--;
			}
			contents = csvReader.build().readAll();
			csvData = new ArrayList<ArrayList<String>>();
			listIterate = contents.listIterator();
			while (listIterate.hasNext()) {
				rowContents = listIterate.next();
				row = new ArrayList<String>(Arrays.asList(rowContents));
				csvData.add(row);
			}
			csvReader.build().close();
		} catch (FileNotFoundException fne) {
			
			logger.log(LogStatus.ERROR, fne);
		} catch (IOException ioe) {
			
			logger.log(LogStatus.ERROR, ioe);
		}
		return csvData;
	}
    
    public static ArrayList<String> getCSVRow(String filePath, int rowNumber) {
		String[] rowContents = {};
		ArrayList<String> row = new ArrayList<String>();
		int increment = 0;
		try {
			CSVReaderBuilder csvReader = new CSVReaderBuilder(new FileReader(filePath));
			if (rowNumber > 0) {
				while (rowNumber != increment) {
					rowContents = csvReader.build().readNext();
					increment++;
				}
//				if(rowContents != null) {
					row = new ArrayList<String>(Arrays.asList(rowContents));
//				}
			}	
			csvReader.build().close();
		} catch (FileNotFoundException fne) {
			
			logger.log(LogStatus.ERROR, fne);
		} catch (IOException ioe) {
			
			logger.log(LogStatus.ERROR, ioe);
		}
		catch(Exception e) {
			logger.log(LogStatus.ERROR, e);
		}
		return row;
	}
    
    public static String getCSVCellValue(String filePath, String columnHeader, int rowNumber){
		String value = "";
		if(checkIfStringIsNotNullAndNotEmpty(filePath)){
			if(checkIfStringIsNotNullAndNotEmpty(columnHeader)){
				try{
					ArrayList<String> Columns = getCSVColumnPerHeader(filePath, columnHeader);
					value = Columns.get(rowNumber-1);
				}
				catch(Exception exception){
					logger.log(LogStatus.ERROR, exception.getMessage());
					
				}
			}
		}
		return value;
	}
    
    public static ArrayList<String> getCSVColumnPerHeader(String filePath, String Header) {
		ArrayList<String> colContents = null;
		String[] rowContents = null;
		List<String[]> contents = null;
		ListIterator<String[]> listIterate = null;
		String[] headers = null;
		int colNumber = -1;
		try {
			CSVReader csvReader = new CSVReader(new FileReader(filePath));
			headers = csvReader.readNext();
			for (int i = 0; i < headers.length; i++) {
				if (headers[i].equalsIgnoreCase(Header)) {
					colNumber = i;
					break;
				}
			}
			if (colNumber != -1) {
				contents = csvReader.readAll();
				listIterate = contents.listIterator();
				colContents = new ArrayList<String>();
				while (listIterate.hasNext()) {
					rowContents = listIterate.next();
					colContents.add(rowContents[colNumber]);
				}
			}
			csvReader.close();
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.ERROR, fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.ERROR, ioe);
		} catch(Exception e) {
			logger.log(LogStatus.ERROR, e);
		}
		return colContents;
	}


    public static int getRandomNumber(int min, int max) {
		Random randomGenerator = new Random();
		int value = randomGenerator.nextInt((max - min) + 1) + min;
		return value;
	}
    
    public static String getRandomString(int length) {
		String result = new BigInteger(Long.SIZE * length, random).toString(32);
		return result.substring(0, length);
	}
    
    public static long getRandomNumber(int numberOfDigits) {
		Random randomGenerator = new Random();
		long accumulator = 1 + randomGenerator.nextInt(9);
		for (int i = 0; i < (numberOfDigits - 1); i++) {
			accumulator *= 10L;
			accumulator += randomGenerator.nextInt(10);
		}
		return accumulator;
	}
    
    public static int getRandomIntNumber(int numberOfDigits) {
		Random randomGenerator = new Random();
		int accumulator = 1 + randomGenerator.nextInt(9);
		for (int i = 0; i < (numberOfDigits - 1); i++) {
			accumulator *= 10L;
			accumulator += randomGenerator.nextInt(10);
		}
		return accumulator;
	}

    public static Properties loadPropertyFile(String path) {
		Properties _props = new Properties();
		try {
			_props.load(new FileInputStream(path));
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
			logger.log(LogStatus.ERROR, ioe);
		}
		return _props;
	}
    
    public static ArrayList<ArrayList<String>> objectToStringConversion(
			ArrayList<ArrayList<Object>> objDataLists) {
		ArrayList<ArrayList<String>> strDataLists = new ArrayList<ArrayList<String>>();
		if(objDataLists != null){
			for (ArrayList<Object> objDataList : objDataLists) {
				ArrayList<String> strDataList = new ArrayList<String>();
				for (Object objData : objDataList) {
					if(objData != null){
						strDataList.add(objData.toString());
					}
					else{
						strDataList.add("");
					}
				}
				strDataLists.add(strDataList);
			}
		}
		return strDataLists;
	}
    
 
    
    public static ArrayList<String> getArrayOfRandomString(int lengthOfArray,
			int lengthOfElements) {
		ArrayList<String> randomArray = new ArrayList<String>();
		for (int i = 0; i < lengthOfArray; i++) {
			randomArray
					.add(new BigInteger(Long.SIZE * lengthOfElements, random)
							.toString(32).substring(0, lengthOfElements)
							.toUpperCase());
		}
		return randomArray;
	}
    
    public static boolean checkIfFileExists(String fileName) {
		boolean exists = false;
		if(checkIfStringIsNotNullAndNotEmpty(fileName)) {
			File obj = new File(fileName);
			if(obj.exists() && !obj.isDirectory()){
				exists = true;
			}
		}
		return exists;
	}
    
    public static boolean doesPackageExists(String packageName) {
		if (!(new File("bin/" + packageName.replace('.', '/'))).exists()) {
			return false;
		} else {
			return true;
		}
	}
    
    public static boolean compareListSize(
			ArrayList<?> list1,
			ArrayList<?> list2) {

		boolean listSizeMatched = false;
		if(list1 != null && !list1.isEmpty()){
			if(list2 != null && !list2.isEmpty()){
				int list1Length = list1.size();
				int list2Length = list2.size();
				if(list1Length == list2Length){
					listSizeMatched = true;
				}
			}
			else{
				logger.log(LogStatus.ERROR, "Failed to compare list sizes. Reason : Second list is either null or empty.");
			}
		}
		else{
			logger.log(LogStatus.ERROR, "Failed to compare list sizes. Reason : First list is either null or empty.");
		}
		return listSizeMatched;
	}

    public static ArrayList<ArrayList<String>> appendColumnDataAtEnd(ArrayList<ArrayList<String>> existingData, ArrayList<String> columnData){
		ArrayList<ArrayList<String>> appendedData = new ArrayList<ArrayList<String>>();
		if(checkIfListIsNotNullAndNotEmpty(existingData)) {
			if(checkIfListIsNotNullAndNotEmpty(columnData)) {
				if((checkIfListIsNotEmpty(existingData) && compareListSize(existingData, columnData))) {
					int index = 0;
					for(ArrayList<String> eachExistingRow : existingData){
						ArrayList<String> newRow = new ArrayList<String>();
						newRow.addAll(eachExistingRow);
						newRow.add(columnData.get(index));
						appendedData.add(newRow);
						index++;
					}
				}
				else {
					logger.log(LogStatus.ERROR, "Failed to append list. Reason : existing data list " + existingData + " and column data list " + columnData + " is null or empty.");
					logger.log(LogStatus.ERROR, "Failed to append list. Reason : existing data list " + existingData + " and column data list " + columnData + " is null or empty.");
				}
			}
			else {
				appendedData = existingData;
			}
		}
		else {
			appendedData = convert1DTo2D(columnData);
		}
		return appendedData;
	}
    
    public static boolean checkIfListIsNotEmpty(List<?> list){
		boolean isNotEmpty = false;
		if(!list.isEmpty()){
			isNotEmpty = true;
		}
		return isNotEmpty;
	}
    
    public static ArrayList<ArrayList<String>> convert1DTo2D(ArrayList<String> OneDList){
		ArrayList<ArrayList<String>> twoDList = new ArrayList<ArrayList<String>>();
		if(checkIfListIsNotNullAndNotEmpty(OneDList)) {
			for(String cell : OneDList) {
				ArrayList<String> eachColumn = new ArrayList<String>();
				if(checkIfStringIsNotNull(cell))
					eachColumn.add(cell);
				else
					eachColumn.add("");
				twoDList.add(eachColumn);
			}
		}
		return twoDList;
	}
    
    public static boolean checkIfDirectoryExists(String directoryName) {
		boolean exists = false;
		if(checkIfStringIsNotNullAndNotEmpty(directoryName)) {
			File obj = new File(directoryName);
			if(obj.exists() && obj.isDirectory()){
				exists = true;
			}
		}
		return exists;
	}
    
    public static String getFileName(String filePath){
		String fileName = "";
		int fileSeperatorLastIndex = filePath.lastIndexOf("/");
		fileName = fileSeperatorLastIndex >= 0?filePath.substring(fileSeperatorLastIndex + 1) : filePath;
		return fileName;
	}
    
    public static boolean checkIfListContains(ArrayList<String> list,
			String str) {
		boolean exist = false;
		if(list != null && !list.isEmpty()){
			if(checkIfStringIsNotNullAndNotEmpty(str)){
				if(list.indexOf(str) != -1){
					exist = true;
				}
			}
			else{
				logger.log(LogStatus.ERROR, "Input string is empty.");
			}
		}
		else{
			logger.log(LogStatus.ERROR, "Input list is null or empty.");
		}
		return exist;
	}
    
    public static String getDate(String dateFormat, int difference, String type) {
		Calendar cal = Calendar.getInstance();
		try {
			if (difference != 0) {
				if (type.equalsIgnoreCase("days")) {
					cal.add(Calendar.DATE, difference);
				} else if (type.equalsIgnoreCase("months")) {
					cal.add(Calendar.MONTH, difference);
				} else if (type.equalsIgnoreCase("years")) {
					cal.add(Calendar.YEAR, difference);
				}
			}
			Date updatedDate = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			format.setLenient(false);
			return format.format(updatedDate);
		} catch (IllegalArgumentException e) {
			logger.log(LogStatus.ERROR, e);
			return null;
		}
	}
    
    /**
	 * <h1>switchToNewBrowserDriver</h1>
	 * This is Method to Switch to New Browser
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 01-11-2021
	 * @param String fileLocation
	 * @return none
	 **/

	public static void switchToNewBrowserDriver()
	{

		System.out.println(
				"******************************* switchToNewBrowserDriver  ****************************************");

		WebDriver driver2 = Utility.openPrivateBrowser();
		default_driver = driver;
		driver = driver2;
		AGlobalComponents.newBrowserDriver=1;
		logger.log(LogStatus.INFO, "New Private Browser Launched and Control Switched");
	}

	/**
	 * <h1>switchToNewBrowserDriver</h1>
	 * This is Method to Switch to New Browser
	 * @author Jiten Khanna
	 * @modified
	 * @version 1.0
	 * @since 01-11-2021
	 * @param String fileLocation
	 * @return none
	 **/

	public static void switchToDefaultBrowserDriver()
	{

		System.out.println(
				"******************************* switchToDefaultBrowserDriver  ****************************************");

		if(AGlobalComponents.newBrowserDriver==1)
		{
			WebDriver driver2 = driver;
			driver2.quit();
			driver = default_driver;
			AGlobalComponents.newBrowserDriver=0;
			logger.log(LogStatus.INFO, "Switched Back to Default Browser Driver");
			driver.navigate().refresh();
		}
	}
	

	 /**
	 * <h1>getDataFromDatasource</h1>
	 * This is a Method to Get Data from Database Table for Script Execution
	 * @author  	Jiten Khanna
	 * @modified 
	 * @version 	1.0
	 * @since   	03-03-2021
	 * @param   	String scriptName
	 * @return  	HashMap
	 **/

	 public static HashMap<String, Comparable> getDataFromDatasource(String scriptName) throws ClassNotFoundException, SQLException
	 {
		 logger.log(LogStatus.INFO, "Getting data from Source for Script: " + scriptName);
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSetMetaData resmd = null;
			HashMap<String, Comparable> resRecord = null;
			try {
				String columnType = null;
				String className = null;
				
				String dbURL = "jdbc:postgresql://44.236.46.102:5432/aehscdb";
				String dbUsername = "alert";
				String dbPassword = "alert123";
				con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
				String dbQuery = "select * from public.hscautomation_uat where script_name = '"+scriptName+"'";
				Class.forName("org.postgresql.Driver");
				
				if (con != null) {
					st = con.createStatement();
					rs = st.executeQuery(dbQuery);
					resmd = rs.getMetaData();
					int columns = resmd.getColumnCount();
					while (rs.next()) {
						resRecord = new HashMap<String, Comparable>();
						for (int i = 1; i <= columns; i++) {
							columnType = resmd.getColumnClassName(i);
							className = resmd.getColumnTypeName(i);
							if (columnType.toLowerCase().indexOf("string") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getString(i));
							} else if (columnType.toLowerCase().indexOf("integer") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getInt(i));
							} else if (columnType.toLowerCase().indexOf("date") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getDate(i));
							} else if (columnType.toLowerCase().indexOf("bigdecimal") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getBigDecimal(i));
							} else if (columnType.toLowerCase().indexOf("timestamp") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getTimestamp(i));
							} else if (columnType.toLowerCase().indexOf("boolean") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getBoolean(i));
							} else if (columnType.toLowerCase().indexOf("byte") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getByte(i));
							} else if (columnType.toLowerCase().indexOf("double") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getDouble(i));
							} else if (columnType.toLowerCase().indexOf("float") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getFloat(i));
							} else if (columnType.toLowerCase().indexOf("long") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getLong(i));
							} else if (columnType.toLowerCase().indexOf("short") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getShort(i));
							} else if (className.toLowerCase().indexOf("image") >= 0) {
								resRecord.put(resmd.getColumnName(i), rs.getString(i));
							} else if (columnType.toLowerCase().indexOf("clob") >= 0) {
								Clob clob = rs.getClob(i);
								Reader r = clob.getCharacterStream();
						        StringBuffer buffer = new StringBuffer();
						        int ch;
						         try {
									while ((ch = r.read())!=-1) {
									    buffer.append(""+(char)ch);
									 }
								resRecord.put(resmd.getColumnName(i), buffer.toString());
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							
						}
					}
					st.close();
					rs.close();
				} else {
					System.err.println("Database connection not set");
				}
			} catch (SQLException se) {
				System.out.println(se);
				logger.log(LogStatus.ERROR, se);
			}
			System.out.println("Data retrieved from Data Source :  " + resRecord);
			return resRecord;
	 }

	 

	 /**
	 * <h1>getDataFromDatasource</h1>
	 * This is a Method to Update Data in Database Table for Script Execution
	 * @author  	Jiten Khanna
	 * @modified 
	 * @version 	1.0
	 * @since   	03-15-2021
	 * @param   	String scriptName
	 * @return  	HashMap
	 **/

	 public static boolean updateDataInDatasource(String scriptName, String columnName, String columnValue) throws ClassNotFoundException, SQLException
	 {
		 logger.log(LogStatus.INFO, "Updating data into Source for Script: " + scriptName);
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			boolean flag = false;
			
			try {

					String dbURL = "jdbc:postgresql://44.236.46.102:5432/aehscdb";
					String dbUsername = "alert";
					String dbPassword = "alert123";
					con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
					String dbQuery = "update public.hscautomation_uat set "+columnName+" ='"+columnValue+"' where script_name ='"+scriptName+"'";
					Class.forName("org.postgresql.Driver");
					
					if (con != null) {
						st = con.createStatement();
						st.executeQuery(dbQuery);
						flag = true;
						st.close();
					} else {
						System.err.println("Database connection not set");
					}
			} catch (SQLException se) {
				System.out.println(se);
			}
			return flag;
	 }
	 

/**
* <h1>verifyMail</h1>
* This is Method to Verify Email Notifications
* @author Jiten Khanna
* @modified
* @version 1.0
* @since 02-15-2021
* @param String userName, String password, String mailSubject, String mailbody
* @return boolean
**/

public static boolean verifyMail(String userName, String password, String mailSubject, String mailBody, String reqNum) { 
	Folder folder = null; 
	Store store = null; 
	System.out.println("***READING MAILBOX..."); 
	logger.log(LogStatus.INFO, "***READING MAILBOX...");
	try { 
		Properties props = new Properties(); 
		props.put("mail.store.protocol", "imaps"); 
		Session session = Session.getInstance(props); 
		store = session.getStore("imaps"); 
		store.connect("imap.gmail.com", userName, password); 
		folder = store.getFolder("INBOX"); 
		folder.open(Folder.READ_WRITE); 
		Message[] messages = folder.getMessages(); //CHANGE
		
//		Message[] messages = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
//
//        // Sort messages from recent to oldest
//        Arrays.sort( messages, ( m1, m2 ) -> {
//	          try {
//	            return m2.getSentDate().compareTo( m1.getSentDate() );
//	          } catch ( MessagingException e ) {
//	            throw new RuntimeException( e );
//	          }
//        	} );


		System.out.println("No of Messages : " + folder.getMessageCount()); 
		logger.log(LogStatus.INFO, "No of Messages : " + folder.getMessageCount());
		System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount()); 
		logger.log(LogStatus.INFO, "No of Unread Messages : " + folder.getUnreadMessageCount());
		for (int i = 8100; i < messages.length; i++) { 
			System.out.println("Reading MESSAGE # " + (i + 1) + "..."); 
			Message msg = messages[i]; 
			String strMailSubject = "", strMailBody = ""; 
			// Getting mail subject 
			Object subject = msg.getSubject(); 
			// Casting objects of mail subject into String 
			strMailSubject = (String) subject; 
			// Getting mail body and Casting objects of mail body into String
		    if (msg.isMimeType("text/plain")) {
		    	strMailBody = msg.getContent().toString();
		    } else if (msg.isMimeType("multipart/*")) {
		        MimeMultipart mimeMultipart = (MimeMultipart) msg.getContent();
		        strMailBody = getTextFromMimeMultipart(mimeMultipart);
		    }
	
			if (strMailSubject.contains(mailSubject)) {
				if(strMailBody.contains(reqNum) && strMailBody.contains(mailBody)){
					System.out.println("Mail Found in Inbox with Subject: "+strMailSubject);
					logger.log(LogStatus.PASS, "Mail Found in Inbox with Subject: "+strMailSubject);
					System.out.println("Mail Found in Inbox for Request Number: "+reqNum);
					logger.log(LogStatus.PASS, "Mail Found in Inbox for Request Number: "+reqNum);
					System.out.println("Mail Found in Inbox with Mail Content: "+mailBody);
					logger.log(LogStatus.PASS, "Mail Found in Inbox with Mail Content: "+mailBody);
					break; 
				}
			} 
		} 
		return true; 
	} catch (MessagingException messagingException) { 
		messagingException.printStackTrace(); 
	} catch (IOException ioException) { 
		ioException.printStackTrace(); 
	} finally { 
		if (folder != null) { 
			try { 
				folder.close(true); 
			} catch (MessagingException e) { 
				e.printStackTrace(); 
			} 
		} 
		if (store != null) { 
			try { 
				store.close(); 
			} catch (MessagingException e) { 
				e.printStackTrace(); 
			} 
		} 
	} 
	return false; 
} 


/**
* <h1>getTextFromMimeMultipart</h1>
* This is a Method to Get Text from Mime Multipart
* @author  	Jiten Khanna
* @modified 
* @version 	1.0
* @since   	03-02-2021
* @param   	String scriptName
* @return  	String
**/

private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws IOException, MessagingException 
{

    int count = mimeMultipart.getCount();
    if (count == 0)
        throw new MessagingException("Multipart with no body parts not supported.");
    boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
    if (multipartAlt)
        // alternatives appear in an order of increasing 
        // faithfulness to the original content. Customize as req'd.
        return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
    String result = "";
    for (int i = 0; i < count; i++) {
        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
        result += getTextFromBodyPart(bodyPart);
    }
    return result;
}


/**
* <h1>getTextFromBodyPart</h1>
* This is a Method to Get Text from Email Body
* @author  	Jiten Khanna
* @modified 
* @version 	1.0
* @since   	03-02-2021
* @param   	String scriptName
* @return  	String
**/

private static String getTextFromBodyPart(BodyPart bodyPart) throws IOException, MessagingException {
    
    String result = "";
    if (bodyPart.isMimeType("text/plain")) {
        result = (String) bodyPart.getContent();
    } else if (bodyPart.isMimeType("text/html")) {
        String html = (String) bodyPart.getContent();
        result = org.jsoup.Jsoup.parse(html).text();
    } else if (bodyPart.getContent() instanceof MimeMultipart){
        result = getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
    }
    return result;
}

/**
* <h1>handle announcement popup</h1>
* This is a Method to close the announcement popup 
* @author  	Jiten Khanna
* @modified 
* @version 	1.0
* @since   	13-04-2021
* @param   	
* @return  	
**/

public static void handleAnnouncementPopup() throws InterruptedException {
	
	if(driver.findElements(By.xpath(".//div[contains(@id,'header-title-textEl') and text()='Announcement']")).size()>0)
	{
		driver.findElement(By.xpath(".//div[contains(@id,'header-title-textEl') and text()='Announcement']//parent::div//following-sibling::div[contains(@id,'tool') and @data-qtip='Close Dialog']")).click();
		Thread.sleep(1000);
	}
	
}

public static String convertDateFormatToGivenFormat(String Date, String requestedFormat) {
	if(Utility.checkIfStringIsNotNullAndNotEmpty(Date)) {
		if(Utility.checkIfStringIsNotNullAndNotEmpty(requestedFormat)) {
			String dateComponents[] = new String[2];
			if(Date.contains("/")){
				dateComponents = Date.split("/");
			}
			int dayIdx = -1, monthIdx = -1, yearIdx = -1;
			if("MM-dd-yyyy".equalsIgnoreCase(requestedFormat)) {
				String appDateFormatComps[] = new String[2];
				appDateFormatComps = requestedFormat.split("-");
				ArrayList<String> componentPosition = new ArrayList<String>(Arrays.asList(appDateFormatComps));
				if(Utility.checkIfListContains(componentPosition, "DD") || Utility.checkIfListContains(componentPosition, "dd")) {
					dayIdx = Utility.getIndex(componentPosition, "DD");
					if(dayIdx == -1) {
						dayIdx = Utility.getIndex(componentPosition, "dd");
					}
				}
				if(Utility.checkIfListContains(componentPosition, "MM") || Utility.checkIfListContains(componentPosition, "mm")) {
					monthIdx = Utility.getIndex(componentPosition, "MM");
					if(monthIdx == -1) {
						monthIdx = Utility.getIndex(componentPosition, "mm");
					}
				}
				if(Utility.checkIfListContains(componentPosition, "YYYY") || Utility.checkIfListContains(componentPosition, "yyyy")) {
					yearIdx = Utility.getIndex(componentPosition, "YYYY");
					if(yearIdx == -1) {
						yearIdx = Utility.getIndex(componentPosition, "yyyy");
					}
				}
				if(yearIdx != -1 && monthIdx != -1 && dayIdx != -1) {
					if(dateComponents[dayIdx].length()!=2) {
						dateComponents[dayIdx]="0"+dateComponents[dayIdx];
					}
					if(dateComponents[monthIdx].length()!=2) {
						dateComponents[monthIdx]="0"+dateComponents[monthIdx];
					}
					if(dateComponents[yearIdx].length()!=4) {
						dateComponents[yearIdx]="20"+dateComponents[yearIdx];
					}
					Date = dateComponents[monthIdx] + "-" + dateComponents[dayIdx] + "-" + dateComponents[yearIdx];	
				}	
			}	
		}
	}
	return Date;
}


public static int getColumnNumberOfHeader(String badgesDataFile, String columnHeader) {
	ArrayList<String> row = Utility.getCSVRow(badgesDataFile, 1);
	int count = 0;
	for (String cell : row) {
		if (cell.equalsIgnoreCase(columnHeader)) {
			break;
		}else {
			count++;
		}
	}
	if(count==row.size()){
		return -1;
	}
	return count;
}

}