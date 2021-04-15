package CommonClassReusables;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.relevantcodes.extentreports.LogStatus;

public class ReadDataFromPropertiesFile {

	/**
	 * @param args
	 * @throws IOException
	 */

	static String var =null;
	static boolean var2;

	
	/**
	* <h1>deleteFiles</h1>
	* This is a Method to Delete Files in CSV Download Folder
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	none
	* @return  	none
	*/
	
	public static void deleteFiles()
	{
		/** delete all exisitng files**/
    	String csvDownloadFolder = System.getProperty("user.dir")+ "\\csv Download";
    	File[] files = new File(csvDownloadFolder).listFiles();
		for (File file : files)
		{
		    if (file.isFile())
		    {
//		    	System.out.println(file.getName());
		    	file.delete();
		    }
		}
	}
	 
	
	/**
	* <h1>readCSV</h1>
	* This is a Method to read CSV File
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	int noOfRows, int noOfColumns
	* @return  	csvMatrix
	*/
	
	public static String[][] readCSV(int noOfRows, int noOfColumns)
	    {
	    	String csvDownloadFolder = System.getProperty("user.dir")+ "\\csv Download\\";
	    	File[] files = new File(csvDownloadFolder).listFiles();
			csvDownloadFolder = csvDownloadFolder+files[0].getName();
			String line = "";
			String cvsSplitBy = "\t";
			String csvMatrix[][] = new String[noOfRows][noOfColumns];
			int j=0;
			try (BufferedReader br = new BufferedReader(new FileReader(csvDownloadFolder)))
			{
				/** storing values from file to 2D matrix**/
				while ((line = br.readLine()) != null)
				{
					String[] country = line.split(cvsSplitBy);
					for(int w=0; w<noOfColumns; w++)
					{
						csvMatrix[j][w]=country[w];
					}
					j++;
				}

			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return csvMatrix;
	    }
 
	
	/**
	* <h1>deleteDir</h1>
	* This is a Method to Delete Directory
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	File dir
	* @return  	boolean
	*/
	
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
           String[] children = dir.list();
           for (int i = 0; i < children.length; i++) {
              boolean success = deleteDir (new File(dir, children[i]));
              
              if (!success) {
                 return false;
              }
           }
        }
        return dir.delete();
     }
	    	    
    
    /**
	* <h1>readDataLineByLine</h1>
	* This is a Method to Read Data from File
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	String file
	* @return  	List<String> expectedFields
	*/
    
	public static List<String> readDataLineByLine(String file) 
		{ 
			
		  List<String> expectedFields = new ArrayList<String>();
		  
		    try { 
		  
		        // Create an object of filereader 
		        // class with CSV file as a parameter. 
		        FileReader filereader = new FileReader(file); 
		  
		        // create csvReader object passing 
		        // file reader as a parameter 
		        CSVReader csvReader = new CSVReader(filereader); 
		        String[] nextRecord; 
		  
		        // we are going to read data line by line 
		        while ((nextRecord = csvReader.readNext()) != null) { 
		            for (String cell : nextRecord) { 
		                System.out.print(cell + "\t"); 
		                expectedFields.add((cell + "\t").trim());
		                
		            } 
		            System.out.println(); 
		        } 
		        csvReader.close();
		        filereader.close();
		    } 
		    catch (Exception e) { 
		        e.printStackTrace(); 
		    } 
		    return expectedFields;
		} 
	       
	
	/**
	* <h1>readTxtFile</h1>
	* This is a Method to Read Data from Text File
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	String varname,String FilePath
	* @return  	String variable
	*/
	
	public static String readTxtFile(String varname,String FilePath) {
		
		try {
			Properties properties = new Properties();
			FileInputStream fis = new FileInputStream(FilePath);
			properties.load(fis);
			var = properties.getProperty(varname);
		} catch (Exception e) {
			return null;
		}
		return var;
	}
	
	
	/**
	* <h1>readTxtFileBoolean</h1>
	* This is a Method to Read Data from Text File
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	String varname,String FilePath
	* @return  	boolean variable
	*/
	
	public static boolean readTxtFileBoolean(String varname,String FilePath) {
		
		try {
			Properties properties = new Properties();
			FileInputStream fis = new FileInputStream(FilePath);
			properties.load(fis);
			var = properties.getProperty(varname);
			if(var.toLowerCase().contains("yes")){
				var2=true;
			}else{
				var2=false;
			}

		} catch (Exception e) {
			
			return false;
		}
		return var2;
	}

	
	/**
	* <h1>WriteDataFile</h1>
	* This is a Method to Write Data to Text File
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	String filePath,String key ,String value,boolean Append
	* @return  	none
	*/
	
  	public static void WriteDataFile(String filePath,String key ,String value,boolean Append) throws IOException {
		
		  FileWriter mynewfile = new FileWriter(filePath, Append);
		//  mynewfile.write(key+"="+value+"\n");
		  mynewfile.write(key+"\n"+value+"\n");
	      mynewfile.close();
		
	}
	
  	
  	/**
	* <h1>WriteDataFile</h1>
	* This is an Override Method to Write Data to Text File
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	String filePath,String key ,String value1,String value2,boolean Append
	* @return  	none
	*/
  	
	public static void WriteDataFile(String filePath,String key ,String value1,String value2,boolean Append) throws IOException {
		
		  FileWriter mynewfile = new FileWriter(filePath, Append);
		  mynewfile.write(key+"\n"+value1+"\n"+value2+"\n");
	      mynewfile.close();
		
	}
	
	
	/**
	* <h1>WriteDataFileMultipleRows</h1>
	* This is a Method to Write Data to Text File in Multiple Rows
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-2-2020
	* @param   	String filePath,String key ,String value,boolean Append
	* @return  	none
	*/
	
	public static void WriteDataFileMultipleRows(String filePath,String key ,String value,boolean Append) throws IOException {
		
		String writeText = key;
		  FileWriter mynewfile = new FileWriter(filePath, Append);
		  
		  String[] values = value.split(";");
	  
	  for(int i=0;i<values.length;i++)
	  {
		  writeText = writeText+"\n"+values[i];
		  }
		  mynewfile.write(writeText);
	      mynewfile.close();
		
	}
	
	
	/**
	* <h1>downloadFileS3Bucket</h1>
	* This is a Method to Download FIle from S3 bucket
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-2-2020
	* @param   	String downloadFileFromBucket,String copyToFile
	* @return  	none
	*/
	
	public static void downloadFileS3Bucket(String downloadFileFromBucket,String copyToFile) {
		
		String bucketName = "";
		String AccessKey = "";
		String SecretKey = "";
		downloadFileFromBucket = "";
		copyToFile=System.getProperty("user.dir")+"\\Browser_Files\\geckodriver.exe";
		
		AWSCredentials credentials = new BasicAWSCredentials(AccessKey,SecretKey);
		
//		 create a client connection based on credentials
		AmazonS3 s3client = new AmazonS3Client(credentials);
		
			
//		 upload file to folder and set it to public
		String fileName = "";
//		s3client.putObject(new PutObjectRequest(bucketName, fileName, new File("fileLocation.txt")).withCannedAcl(CannedAccessControlList.PublicRead));
		
		try{
//       download file
		S3Object object = s3client.getObject(new GetObjectRequest(bucketName, downloadFileFromBucket));
		InputStream reader = new BufferedInputStream(object.getObjectContent());
		File file = new File(copyToFile);      
		OutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
		int read = -1;
		while ( ( read = reader.read() ) != -1 ) {
				    writer.write(read);
				}
				writer.flush();
				writer.close();
				reader.close();
				System.out.println("Successfully download and copy file from S3 bucket");
	}catch(Throwable e){
		throw  new RuntimeException(e);
	}

}

	
	/**
	* <h1>writeColumnDataCSV</h1>
	* This is a Method to Write Column Data to CSV File
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-2-2020
	* @param   	String columnName,String replaceByColumn,String filePath
	* @return  	none
	*/
	
	public static void writeColumnDataCSV(String columnName,String replaceByColumn,String filePath) throws Throwable{
		String[] column = null;
		String[] column2 = null;
		String temp="";
		String line;
		String line2;
		int columnPosition=0;
		int count=0;
		String[] replaceData= replaceByColumn.split("\n");
	try{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		while (( line = br.readLine()) != null) {
	
	    	column = line.split("\t");
	        for(int i=0;i<column.length;i++)
	         {
	       	  if(column[i].toString().contains(columnName))
	       	  {
	        	 columnPosition=i;
	        	 count++;
	     	     break;
	          }
	     }
	     
	    }
		 br.close();
		if(count==0){
			throw  new RuntimeException("Column name is not present in file please update column name----->"+columnName);
		}
		count=-1;
		BufferedReader br2 = new BufferedReader(new FileReader(filePath));
		while (( line2 = br2.readLine()) != null) {
	    count++;
			column2 = line2.split("\t");
	        for(int i=0;i<column2.length;i++){
	        	 
	        	 if(column2[i].toString().equals(columnName) ){
	        		 
	        		 temp=temp+column2[i].toString().replaceAll(column2[i].toString(),columnName)+"\t";
	        		 
	        	 }else{ 
	        	 
	        		 if(i==columnPosition){
	        			 
	        			 temp=temp+column2[i].toString().replaceAll(column2[i].toString(),replaceData[count].toString())+"\t";
	        		 }else{
	        			 temp=temp+column2[i].toString()+"\t";
	        		 }
	        	 
	         }
	  
	    }
	        temp=temp+"\t\n";
		
		}	
		 br2.close();
	     FileWriter fw = new FileWriter(filePath);
	     BufferedWriter bw = new BufferedWriter(fw);
	     bw.write(temp);
	     bw.flush();
	
		}catch(Throwable e){
			throw  new RuntimeException(e);
		}
		
	}
	
	
	/**
	* <h1>clearExistingDownloadFiles</h1>
	* This is a Method to clear already downloaded files
	* @author  	Vishal Gupta
	* @modified 
	* @version 	1.0
	* @since   	03-12-2020
	* @param   	String partialName
	* @return  	none
	*/
	public static void clearExistingDownloadFiles(String partialName) throws Throwable {
		try {
		 String folderName = System.getProperty("user.dir")+ "\\csv Download\\";
		 File[] listFiles = new File(folderName).listFiles();
		 for (int i = 0; i < listFiles.length; i++) {

		     if (listFiles[i].isFile()) {
		         String fileName = listFiles[i].getName();
		         if (fileName.startsWith(partialName)
		                 && fileName.endsWith(".xlsx")) {
		        	 listFiles[i].delete();
		             System.out.println("deleted existing files");
		             
		         }
		     }
		 }
		
		}
		
		catch(Throwable e){
			throw  new RuntimeException(e);
		}
	}
	
	
	/**
	* <h1>checkWhetherFileIsDownloaded</h1>
	* This is a Method to check whether a particular file is downloaded
	* @author  	Vishal Gupta
	* @modified 
	* @version 	1.0
	* @since   	03-12-2020
	* @param   	String partialName
	* @return  	boolean
	*/
	public static boolean checkWhetherFileIsDownloaded(String partialName) throws Throwable{
		try {
		 String folderName = System.getProperty("user.dir")+ "\\csv Download\\";
		 File[] listFiles = new File(folderName).listFiles();
		 boolean flag = false;
		 String fileName = "None";

		 for (int i = 0; i < listFiles.length; i++) {

		     if (listFiles[i].isFile()) {
		         fileName = listFiles[i].getName();
		         if (fileName.startsWith(partialName)
		                 && fileName.endsWith(".xlsx")) {
		             System.out.println("found file" + " " + fileName);
		             flag =  true;
		         }
		     }
		 }
		return flag;
	}
		
	catch(Throwable e){
		throw  new RuntimeException(e);
	}
}
	
	
	/**
	* <h1>countLine</h1>
	* This is a Method to count the total number of lines in xlsx file
	* @author  	Vishal Gupta
	* @modified 
	* @version 	1.0
	* @since   	04-12-2020
	* @param   	String path
	* @return  	integer
	*/
	public static int countLine(String path) throws Throwable {

		try {
		FileInputStream ExcelFile = new FileInputStream(path); 
		  
		// Access the required test data sheet 
		  
		XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile); 
		  
		XSSFSheet ExcelWSheet = ExcelWBook.getSheet("Sheet1"); 
		  
		int totalRows = ExcelWSheet.getPhysicalNumberOfRows() - 1; 
		  
		System.out.println("Total number of Rows :::"+totalRows); 
		return totalRows;
		}	catch(Throwable e){
			throw  new RuntimeException(e);
		}
	}


	public static boolean writeTestDataInFile(String badgesDataFile, ArrayList<ArrayList<String>> badgesData, boolean appendMode) {


		boolean dataWrittenSuccessfully = false;
		if(Utility.checkIfFileExists(badgesDataFile)) {
			if(Utility.checkIfListIsNotNull(badgesData)) {
		        List<String[]> writeContents = new ArrayList<String[]>();
		        try {
		               for (ArrayList<String> row : badgesData) {
		                     writeContents.add(row.toArray(new String[row.size()]));
		               }
		               CSVWriter csvWriter = new CSVWriter(new FileWriter(badgesDataFile, appendMode), ',',
		                            CSVWriter.NO_QUOTE_CHARACTER);
		               csvWriter.writeAll(writeContents);
		               dataWrittenSuccessfully = true;
		               csvWriter.close();
		        }
		        	catch(Throwable e){
		    			throw  new RuntimeException(e);
		        	}
			}
		}		
        return dataWrittenSuccessfully;
	}


	public static boolean updateCSVCellValue(String badgesDataFile, String columnHeader, int rowNum, String newCellValue) {

		boolean cellUpdated = false;
		if(Utility.checkIfStringIsNotNullAndNotEmpty(badgesDataFile) && Utility.checkIfFileExists(badgesDataFile)) {
			if(Utility.checkIfStringIsNotNullAndNotEmpty(columnHeader)) {
				if(Utility.checkIfStringIsNotNullAndNotEmpty(newCellValue) && rowNum > 0) {
					try {
						List<String[]> updatedData = new ArrayList<String[]>();
						List<String[]> contents = null;
						String[] row = null;
						CSVReader reader = new CSVReader(new FileReader(badgesDataFile));
						int colNum = Utility.getColumnNumberOfHeader(badgesDataFile, columnHeader);
						contents = reader.readAll();
						reader.close();
						row = contents.get(rowNum);
						row[colNum] = newCellValue;
						contents.set(rowNum, row);
						for (String[] els : contents) {
							updatedData.add(els);
						}
						CSVWriter writer = new CSVWriter(new FileWriter(badgesDataFile), ',', CSVWriter.NO_QUOTE_CHARACTER);
						writer.writeAll(updatedData);
						cellUpdated = true;
						writer.close();
						System.out.println("TestData file successfully updated!!");
					}
						catch(Throwable e){
			    			throw  new RuntimeException(e);
			        	}
				}
			}
		}
		return cellUpdated;
	
	}

}
