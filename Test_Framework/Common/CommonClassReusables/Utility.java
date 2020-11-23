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
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.relevantcodes.extentreports.LogStatus;

public class Utility extends BrowserSelection {

	
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
		System.out.println("\n******************** RECOVERY SCENARIO INVOKED FOR METHOD :       { " +methodName+ " } ********************");
		System.out.println("******************** RECOVERY SCENARIO INVOKED FOR EXCEPTION :       { " +exception+ " } ********************");
		logger.log(LogStatus.FAIL, "\n******************** RECOVERY SCENARIO INVOKED FOR METHOD :       { " +methodName+ " } ********************");	
		logger.log(LogStatus.FAIL, "******************** RECOVERY SCENARIO INVOKED FOR EXCEPTION :       { " +exception+ " } ********************");
		
		unhandledException = true;	
		Utility.takeScreenshot("bug screnshot1");
		logger.log(LogStatus.ERROR, "Screenshort of page where Exception Invoked : " + imgeHtmlPath);
		WebDriver driver2=Utility.openPrivateBrowser();
		default_driver=driver2;
		driver.close();
		driver=default_driver;  
		driver.get(AGlobalComponents.applicationURL);
		
		if(ByAttribute.getListSize("xpath",".//h3[text()='Sign In']","Recovery Scenario : Sign In page is present")==1)
		{
			driver.get(AGlobalComponents.applicationURL);
			System.out.println("Successfully: open url-"+AGlobalComponents.applicationURL);
			dynamicwait.WAitUntilPageLoad();
			driver.findElement(By.xpath(".//*[contains(@placeholder,'Username')]")).sendKeys(AGlobalComponents.username);
			System.out.println("Successfully: enter username: "+AGlobalComponents.username);
			dynamicwait.WAitUntilPageLoad();
			driver.findElement(By.xpath(".//*[contains(@placeholder,'Password')]")).sendKeys(AGlobalComponents.recoveryPassword);
			System.out.println("Successfully: enter password: "+AGlobalComponents.recoveryPassword);
			dynamicwait.WAitUntilPageLoad();
			driver.findElement(By.xpath("//*[@type='button' and text()='Sign In']")).click();
			Thread.sleep(1000);
			try{
				driver.findElement(By.xpath(".//*[@id='myModalLabel' and contains(text(),'Prevent Multiple Logins')]")).isDisplayed();
				driver.findElement(By.xpath(".//*[@id='btnContnueLogin']")).click();
				Thread.sleep(2000);
			}catch(Exception e)
			{
				System.out.println("No {Prevent multiple login} pop-up appears.");
			}
			System.out.println("RECOVERY SCENARIO : Successfully new Driver launched and Logged In.");
			logger.log(LogStatus.INFO, "RECOVERY SCENARIO : Successfully new Driver launched and Logged In.");
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
			logger.log(LogStatus.FAIL, "Failed: "+fieldName+" is not present. Screenshort of BUGG :" + e + imgeHtmlPath);
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
	* @modified 
	* @version 	1.0
	* @since   	04-04-2020
	* @param   	String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass,boolean stopExecution
	* @return  	none
	**/
	
	public static void verifyElementPresent(String verifyElementXpath,String fieldName,boolean takeScreenshotIfPass,boolean stopExecution) throws Exception
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

    	autoWorkbook = new XSSFWorkbook(inputStream);

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
   	* @return  	none
   	**/    
    
    public void writeExcel(String filePath,String fileName,String sheetName,String[] dataToWrite) throws IOException{

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

        	autoWorkbook = new XSSFWorkbook(inputStream);

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




}