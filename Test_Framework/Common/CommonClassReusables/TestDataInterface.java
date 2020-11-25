package CommonClassReusables;					
					
import java.util.ArrayList;

import org.testng.log4testng.Logger;					

					// this class defines Test Data Source connections
public class TestDataInterface extends TestDataEngine {
		private static Logger _log = Logger.getLogger(TestDataInterface.class);
		private static String dataSourceLocation = "Test_Data/DataSource/";
		private static String dataSourceFilePath = dataSourceLocation + "UserDataDB.csv";
		private static String roleDataSourceFilePath = dataSourceLocation + "RoleDataDB.csv";
		
		private static String reconTestDataDirPath = "TestData/ae/products/iam/recon";
					
		private static String dataDropLocation = "TestData/DataSet";
		private static String templatePath = null;
		private static ArrayList<String> templateFieldTypes= new ArrayList<String>();
		private static ArrayList<String> udsColumns = new ArrayList<String>();
				
		public static void setUdsColumns(ArrayList<String> udsColumns) {
			TestDataInterface.udsColumns = udsColumns;
		}
					
		public static String getTemplatePath() {
			return templatePath;
		}
				
		public static ArrayList<String> getUdsColumns() {
			return udsColumns;
		}
	
		public static Logger get_log() {
			return _log;
		}
					
						public static void setTemplatePath(String templatePath) {
							TestDataInterface.templatePath = templatePath;
						}
					
						public static void setTemplateFieldTypes(ArrayList<String> templateFieldTypes) {
							TestDataInterface.templateFieldTypes = templateFieldTypes;
						}

						public static ArrayList<String> getTemplateFieldTypes() {
							return templateFieldTypes;
						}

						public static String getDataSourceLocation() {
							return dataSourceLocation;
						}
					
						public static String getDataSourceFilePath() {
							return dataSourceFilePath;
						}
					
						public static String getDataDropLocation() {
							return dataDropLocation;
						}
					
						public static void set_log(Logger _log) {
							TestDataInterface._log = _log;
						}
					
						public static void setDataSourceLocation(String dataSourceLocation) {
							TestDataInterface.dataSourceLocation = dataSourceLocation;
						}
					
						public static void setDataSourceFilePath(String dataSourceFilePath) {
							TestDataInterface.dataSourceFilePath = dataSourceFilePath;
						}
					
						public static void setDataDropLocation(String dataDropLocation) {
							TestDataInterface.dataDropLocation = dataDropLocation;
						}
					
						public static ArrayList<String> getUDSDatabyColumnNo(int columnNumber) {
					
							ArrayList<String> udsColData = null;
							udsColData = TestDataEngine.getCSVColumn(dataSourceFilePath, columnNumber);
							return udsColData;
						}
					
						public static ArrayList<String> getUDSDatabyHeaderName(String header) {
					
							ArrayList<String> udsColData = null;
					
							udsColData = TestDataEngine.getCSVColumnPerHeader(dataSourceFilePath, header);
							return udsColData;
						}
					
						public static void compileTestDataTemplate(String templatePath, String dropFilePath) {
					
							TestDataInterface.setTemplatePath(templatePath);
							TestDataInterface.compileDataTemplate(templatePath, getDataSourceFilePath(), dropFilePath);
						}
					
						public static void dropTestDataFile(String fileLocation) {
					
						}

						public static String getRoleDataSourceFilePath() {
							return roleDataSourceFilePath;
						}
						
						public static String getReconTestDataDirectory(){
							return reconTestDataDirPath;
						}
						
						public static boolean compileTwoRowDataTemplate(String templatePath, String dropFilePath) {
							
							TestDataInterface.setTemplatePath(templatePath);
							return TestDataInterface.compileTwoRowDataTemplate(templatePath, getDataSourceFilePath(), dropFilePath);
						}
						
						public static void compileTwoRowDataTemplate(String templatePath, String dropFilePath, int rowIndex) {
							
							TestDataInterface.setTemplatePath(templatePath);
							TestDataInterface.compileTwoRowDataTemplate(templatePath, getDataSourceFilePath(), dropFilePath, rowIndex);
						}
						
					
					}
