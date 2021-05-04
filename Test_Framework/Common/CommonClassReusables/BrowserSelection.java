package CommonClassReusables;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;

public class BrowserSelection  {
	
	protected static WebDriver driver;
	public static WebDriver default_driver;
	public static WebDriver PrivateBrowser_driver2;
	public static WebDriver PrivateBrowser_driver3;
	public static WebDriver globalSeleniumInstance;
	public static boolean unhandledException = false;  
	public static ExtentReports report = new ExtentReports(AGlobalComponents.extentReportPath,true);
	public static ExtentTest logger;
	public static String imgeHtmlPath;
	public static Dimension windowSize;
	public static int punchDay = 0;
	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static Calendar currentDate = Calendar.getInstance();
	public static SimpleDateFormat calendarDate = new SimpleDateFormat("MMM dd, yyyy");
	public static String entryCalendarDate = calendarDate.format(currentDate.getTime());
	protected static JavascriptExecutor js = (JavascriptExecutor) driver;
	public static int DBCaseNo = 1;
	
	

	@BeforeClass
	public static WebDriver beforeSuite() throws IOException {
    	
//		logger = report.startTest("Browser initialization", "initialization of browser and open browser");
		FileUtils.deleteDirectory(new File(System.getProperty("user.dir") + "\\ExtentReport\\Output\\Screenshots"));

		String gerkoPath = System.getProperty("user.dir");
		gerkoPath = gerkoPath + "\\Browser_Files\\geckodriver.exe";
		System.setProperty("webdriver.firefox.marionette", gerkoPath);
		System.out.println("Browser select " + AGlobalComponents.browserName);
	//	logger.log(LogStatus.INFO, "Browser select : "+AGlobalComponents.browserName);
		
		switch (AGlobalComponents.browserName)
		{
		case "MF":
			System.out.println("Running FireFox");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().window().setSize(new Dimension(1550, 838));
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			System.out.println("Mozilla firefox open sucessfully");
	//		logger.log(LogStatus.PASS, "Mozilla firefox open sucessfully");
			break;

		case "GC":
			System.setProperty("webdriver.chrome.driver", "Browser_Files/chromedriver.exe");
	//		System.setProperty("webdriver.chrome.driver", "Browser_Files/chromedriver");
			String downloadFilepath = System.getProperty("user.dir")+ "\\csv Download";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();			
		    ChromeOptions options = new ChromeOptions();
		    options.setExperimentalOption("prefs", chromePrefs);
		    options.addArguments("incognito");
		    options.addArguments("chrome.switches","--disable-extensions");
			options.addArguments("--no-sandbox");
			options.addArguments("test-type");
	//		options.addArguments("headless");
		    options.addArguments("--start-maximized");
		    
		    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		    driver = new ChromeDriver(capabilities);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			System.out.println("Google chrome open sucessfully");
			break;
	
		case "IE":
			System.setProperty("webdriver.ie.driver", "Browser_Files/IEDriverServer.exe");
			DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
			dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(dc);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			System.out.println("Internet explorer open sucessfully");
			logger.log(LogStatus.PASS, "Internet explorer open sucessfully");
			break;

		default:
			System.out.println("Running FireFox");
			System.setProperty("webdriver.firefox.marionette", gerkoPath);
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.manage().window().setSize(new Dimension(1550, 840));
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			System.out.println("Mozilla firefox open sucessfully");
		//	logger.log(LogStatus.PASS, "Mozilla firefox open sucessfully");
			break;
		}
	//	report.endTest(logger);
	//	report.flush();
		return driver;
	}

	
	/**
	* <h1>afterMethod</h1>
	* This is the After Method that will be executed after Every method
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-24-2020
	* @param   	ITestResult result
	* @return  	none
	*/
	
	@AfterMethod
	protected void afterMethod(ITestResult result) throws IOException 
	{
		String ErrMsg = "";
		String actionInfo = "";
		if(unhandledException==true)
		{
			result.setStatus(2);
			System.out.println("FAIL-------- Test failed due to Exception. Recovery Scenario Invoked ---------");
			logger.log(LogStatus.FAIL, "-------- Test failed due to Exception. Recovery Scenario Invoked ---------");	
		}
		else
		{
			switch (result.getStatus())
				{
					case 1:// Pass
						System.out.println("PASSED---"+result.getMethod().getMethodName());
						logger.log(LogStatus.PASS, "Test passed Successfully");
						break;
			
					case 2:// Fail
						ErrMsg = result.getThrowable().getMessage();
						actionInfo = result.getName();
						if (ErrMsg.contains("expected [true]")) {
							System.out.println("FAILED---"+result.getMethod().getMethodName()+"------------->"+ErrMsg);
							logger.log(LogStatus.FAIL, "BUGGG >> " + ErrMsg);
							if(ErrMsg.contains("ASSERTIONERROR")){
								logger.log(LogStatus.FAIL, "Screenshort of BUGG :" + imgeHtmlPath);
							}else{
							Utility.takeScreenshot(actionInfo.replaceAll(" ", "_"));
							logger.log(LogStatus.FAIL, "Screenshort of BUGG :" + imgeHtmlPath);
							}
							break;
						}
						else if (ErrMsg.contains("RUNTIME_ERROR")){
							System.out.println("ERROR---"+result.getMethod().getMethodName()+"------------->"+ErrMsg);
							logger.log(LogStatus.ERROR, "" + ErrMsg);
							Utility.takeScreenshot(actionInfo.replaceAll(" ", "_"));
							logger.log(LogStatus.ERROR, "Screenshort of error :" + imgeHtmlPath);
							System.out.println("ERROR--Test error successfully"+result.getTestName());
							break;
						}
						else{
							System.out.println("ERROR---"+result.getMethod().getMethodName()+"------------->"+ErrMsg);
							logger.log(LogStatus.ERROR, "RUNTIME ERROR >> " + ErrMsg);
							break;
						}
				}
			}
		report.endTest(logger);
		report.flush();
	}
	
	
	/**
	* <h1>quitDriver</h1>
	* This Method will be executed after the Suite is completed
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-24-2020
	* @param   	none
	* @return  	none
	*/
	
	@AfterSuite
	public void quitDriver() throws IOException, Throwable
	{
		System.out.println("Successfully: Quit Browser");
		driver.get("file://"+AGlobalComponents.extentReportPath) ;
		driver.findElement(By.xpath("//nav/ul/li[@class='theme-selector']")).click();
		File source = new File(System.getProperty("user.dir") + "\\ExtentReport\\Output");
		File dest = new File(System.getProperty("user.dir") + "\\ExtentReport\\Output"+AGlobalComponents.currentDateLoggr);
	    FileUtils.copyDirectory(source, dest);
//		driver.quit();

	}
}
