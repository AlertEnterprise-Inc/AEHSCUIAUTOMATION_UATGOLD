package CommonClassReusables;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AGlobalComponents {


	public static String FilePath =System.getProperty("user.dir")+"\\Browser_Files\\ConfigFile.txt";
	public static String commonData = "properties\\commonData.properties";
	public static String extentDate = new SimpleDateFormat("dd_MM_yyyy hh_mm_ss").format(new Date());
	public static String currentDateLoggr = new SimpleDateFormat("dd_MM_yyyy_hhmmss").format(new Date());
	public static String extentReportScreenshotFolder = System.getProperty("user.dir") + "\\ExtentReport\\Output\\Screenshots\\";
	public static String extentReportPath = System.getProperty("user.dir") + "\\ExtentReport\\Output\\Report.html";
	public static boolean takeScreenshotIfPass = false;
	public static boolean jsExecutor = true;
	public static String AttachmentPic = System.getProperty("user.dir") + "\\Browser_Files\\Automation.png";
	public static String AttachmentFile = System.getProperty("user.dir") + "\\Browser_Files\\upload.csv";
	public static boolean s3BucketUpload = false;
	public static String s3BucketScript = System.getProperty("user.dir") + "\\ExtentReport\\";
	
	public static String identityCode="";
	public static String jobName="";
	public static String syncId="";
	public static boolean trialReconJob=false;
	public static boolean rerunReconJob=false;
	public static boolean CCUREUserRecon =false;
	public static boolean CCURERoleRecon=false;
	public static boolean DBUserRecon=false;
	public static boolean userRecon=false;
	public static boolean roleRecon=false;
	public static boolean deleteSingleIdentityFlag=false;
	public static int statusIndex,activeIndex,errorIndex,messageIndex,descriptionIndex,userIdIndex,firstNameIndex,syncIdIndex;
	public static Date validToDate = new Date();
	public static boolean wellnessCheckActivate =false;
	public static boolean wellnessCheckDeActivate =false;
	public static boolean RequestSubmit =false;
	public static boolean ManagerLogin = false;
	public static boolean createIdentityPermanent = false;
	public static boolean createIdentityContractor = false;
	public static boolean badgeAdminLogin = false;
	public static boolean modifyIdentity = false;
	public static boolean updatePhoto = false;
	public static boolean wellnessCheckCase=false;
	public static boolean contractorToPermanentEmployeeConversion = false;
	public static boolean tempWorkerOnboarding=false;
	public static boolean tempWorkerModificationDepartment=false;
	public static boolean tempWorkerModificationLastName=false;
	public static boolean tempWorkerOffboardingRehire=false;
	public static boolean emergencyTermination=false;
	public static boolean requestLocationAccessOthers = false;
	public static String badgeId="";
	public static String assetName="";
	public static String systemNameOfAsset="";
	
	public static String amagDbName = "multiMaxExport";
	public static String amagDbUsername = "sa";
	public static String amagDbPassword = "Alert1234";
	public static String amagDbIP="66.2.49.189:1433";
	public static String amagDbUrl = "jdbc:sqlserver://"+ amagDbIP;
	public static String amagDbConString =amagDbUrl+";user="+amagDbUsername+";password="+amagDbPassword+";database="+amagDbName+";portNumber=1433";	
		
	public static String browserName ="GC";
	public static int newBrowserDriver=0;
//	public static String applicationURL  = "http://hscpartner.alertenterprise.com/";
//	public static String applicationURL  = "http://192.168.193.169:60/qarecon";
	public static String applicationURL  = "http://autodevhsc.alertenterprise.com";


	public static String selfServicePortalURL  = "https://airportmit.cloud.alertenterprise.com/AlertSelfService/";
	public static String username = "admin";
	public static String password = "Alert@783";
	public static String dbUsername = "AEQA15191";
	public static String dbPassword = "Alert#1234";
	public static String dbName = "AEQA15191";
	public static String timezone = "Calcutta";
	public static String dbIP="192.168.194.124";
	public static String dbUrl = "jdbc:sqlserver://"+ dbIP;
	public final static String recoveryPassword = password;
	public static String dbConString =dbUrl+";user="+dbUsername+";password="+dbPassword+";database="+dbName;	
	
	public static String ccureDbName = "ACVSCore";
	public static String ccureDbUsername = "sa";
	public static String ccureDbPassword = "Alert1234";
	public static String ccureDbIP="AEDEVW2CCURE29";
	public static String ccureDbUrl = "jdbc:sqlserver://"+ ccureDbIP;
	public static String ccureDbConString =ccureDbUrl+";user="+ccureDbUsername+";password="+ccureDbPassword+";database="+ccureDbName+";portNumber=1433";	
	
	public static String postgresqlDbName="aehscdb";
	public static String postgresqlDbUserName="alert";
	public static String postgresqlDbPassword="alert123";
	public static String postgresqlDbIP="35.81.218.186";
	public static String postgresqlDbUrl="jdbc:postgresql://"+ postgresqlDbIP+":5432/"+postgresqlDbName;
	
	public static String lenelDbName = "AccessControl";
	public static String lenelDbUsername = "sa";
	public static String lenelDbPassword = "Alert1234";
	public static String lenelDbIP="172.16.34.195";
	public static String lenelDbUrl = "jdbc:sqlserver://"+ lenelDbIP;
	public static String lenelConString =lenelDbUrl+";user="+lenelDbUsername+";password="+lenelDbPassword+";database="+lenelDbName+";portNumber=1433";
	
	
	public static String applicantFirstName = "";
//	public static String applicantFirstName = "AUTOUJES";
	public static String applicantSSN = "";
	public static int  RANDOM_DATA_GEN_LEN = 10;
	public static boolean RANDOM_DATA_GEN_FLAG = true;
//	public static String baseURI="http://aepdemo.alertenterprise.com/";
	public static String baseURI="http://autouathsc.alertenterprise.com";
	public static String access_token="";
	public static String assetCode="";
	public static String reviewName="";
	public static boolean createRequest=false;
	public static String badgeName="";
	public static String systemName="";
	public static boolean EmpOnboardingthroughHRDb=false;
	public static boolean EmpChangeJobTitlethroughHRDb=false;
	public static String userId="";
	
}

