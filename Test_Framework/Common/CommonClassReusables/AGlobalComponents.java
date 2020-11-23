package CommonClassReusables;

import java.text.SimpleDateFormat;
import java.util.Date;

import CommonClassReusables.ReadDataFromPropertiesFile;

public class AGlobalComponents {


	public static String FilePath =System.getProperty("user.dir")+"\\Browser_Files\\ConfigFile.txt";
	public static String commonData = "properties\\commonData.properties";
	public static String extentDate = new SimpleDateFormat("dd_MM_yyyy hh_mm_ss").format(new Date());
	public static String currentDateLoggr = new SimpleDateFormat("dd_MM_yyyy_hhmmss").format(new Date());
	public static String extentReportScreenshotFolder = System.getProperty("user.dir") + "\\ExtentReport\\Output\\Screenshots\\";
	public static String extentReportPath = System.getProperty("user.dir") + "\\ExtentReport\\Output\\Report.html";
	public static boolean jsExecutor = true;
	public static String AttachmentPic = System.getProperty("user.dir") + "\\Browser_Files\\Automation.png";
	public static String AttachmentFile = System.getProperty("user.dir") + "\\Browser_Files\\upload.csv";
	public static boolean s3BucketUpload = false;
	public static String s3BucketScript = System.getProperty("user.dir") + "\\ExtentReport\\";
	
	public static String browserName ="GC";
//	public static String applicationURL  = "https://airportdev.cloud.alertenterprise.com/AlertEnterprise/";
	public static String applicationURL  = "https://airportmit.cloud.alertenterprise.com/AlertEnterprise/";
//	public static String applicationURL  = "https://automationtest.cloud.alertenterprise.com/AlertEnterprise/";
//	public static String selfServicePortalURL  = "https://airportdev.cloud.alertenterprise.com/AlertSelfService/";
	public static String selfServicePortalURL  = "https://airportmit.cloud.alertenterprise.com/AlertSelfService/";
	public static String username = "David";
	public static String password = "Alert1234";
	public static String timezone = "Calcutta";
	public static String airportPriviledge = "";
	public final static String recoveryPassword = password;
	public static String dbConString ="";	
	
	public static String applicantFirstName = "";
	public static String applicantSSN = "";
	public static String applicantOrganization = "BRITISH AIRWAYS-LEO";
	
}
