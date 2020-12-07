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
	
	public static String identityCode="";
	public static String jobName="";
	
	public static String browserName ="GC";
//	public static String applicationURL  = "http://192.168.194.122/";
	public static String applicationURL  = "http://192.168.193.169:60/qarecon";

	public static String selfServicePortalURL  = "https://airportmit.cloud.alertenterprise.com/AlertSelfService/";
	public static String username = "admin";
	public static String password = "Alert1234";
	public static String dbUsername = "AEQA14740";
	public static String dbPassword = "Alert@1234";
	public static String dbName = "AEQA14740";
	public static String timezone = "Calcutta";
	public static String dbIP="192.168.192.163";
	public static String dbUrl = "jdbc:sqlserver://"+ dbIP;
	public final static String recoveryPassword = password;
	public static String dbConString =dbUrl+";user="+dbUsername+";password="+dbPassword+";database="+dbName;	
	
	public static String applicantFirstName = "";
//	public static String applicantFirstName = "AUTOUJES";
	public static String applicantSSN = "";
	public static int  RANDOM_DATA_GEN_LEN = 10;
	public static boolean RANDOM_DATA_GEN_FLAG = true;
	
}
