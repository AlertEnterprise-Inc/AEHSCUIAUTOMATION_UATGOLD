package CommonClassReusables;

import java.io.File;

import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Date;
import java.util.Date; 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.testng.log4testng.Logger;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.relevantcodes.extentreports.LogStatus;

/**
 * This class contains all methods useful to interact with CSV files
 * 
 * @author Rohit Pant
 */
public class TestDataEngine extends BrowserSelection{
	private static Logger _log = Logger.getLogger(TestDataEngine.class);
	public static String dataSourceFilePath = TestDataInterface.getDataSourceFilePath();
	public static String roleDataSourceFilePath = TestDataInterface.getRoleDataSourceFilePath();
	public static String reconTestDataDirectory = TestDataInterface.getReconTestDataDirectory();
	private static List<String[]> CSVData=new ArrayList<String[]>();

	
	public static Logger get_log() {
		return _log;
	}

	public static void set_log(Logger _log) {
		TestDataEngine._log = _log;
	}

	public static String getDataSourceFilePath() {
		return dataSourceFilePath;
	}

	public static void setDataSourceFilePath(String dataSourceFilePath) {
		TestDataEngine.dataSourceFilePath = dataSourceFilePath;
	}

	public static List<String[]> loadCSVData() {
		return CSVData;
	}
	public void addCSVrow(String[] CSVRow) {
		
		TestDataEngine.CSVData.add(CSVRow);
	}
	
	public void addCSVRow(int index,String[] CSVRow) {
		
		TestDataEngine.CSVData.add(index, CSVRow);
	}

	public static void setCSVData(List<String[]> cSVData) {
		CSVData = cSVData;
	}
	public static void flushCSVData(){
		TestDataEngine.CSVData.clear();
	}

	public static boolean writeDataToCSV(String filePath){
		boolean dataWrittenSuccessfully = false;	
		try {
		    	
			   File file = new File(filePath);
	    		if (file.createNewFile()) {
				System.out.println("File is created!");
		     	} else {
				System.out.println("File already exists.");
			}
			CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
			if (TestDataEngine.CSVData != null) {
			    writer.writeAll(TestDataEngine.CSVData);
			    dataWrittenSuccessfully = true;
				writer.close();
				TestDataEngine.CSVData.clear();
			} 
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return dataWrittenSuccessfully;
	}
	/**
	 * Function to get CSV data
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file, int number of lines to skip
	 * @return ArrayList object containing entire CSV data
	 */
	public static ArrayList<ArrayList<String>> getCSVData(String filePath, int skipLines) {
		List<String[]> contents = null;
		String[] rowContents = null;
		ArrayList<String> row = null;
		ListIterator<String[]> listIterate = null;
		ArrayList<ArrayList<String>> csvData = null;
		try {
			CSVReader csvReader = new CSVReader(new FileReader(filePath));
			if (csvReader != null) {
				System.out.println("Processing Test Data from => " + filePath);
			}
			while (skipLines > 0) {
				csvReader.readNext();
				skipLines--;
			}
			contents = csvReader.readAll();
			csvData = new ArrayList<ArrayList<String>>();
			listIterate = contents.listIterator();
			while (listIterate.hasNext()) {
				rowContents = listIterate.next();
				row = new ArrayList<String>(Arrays.asList(rowContents));
				csvData.add(row);
			}
			csvReader.close();
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.FAIL,fne);
			System.out.println("Failed: "+fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.FAIL,ioe);
			System.out.println("Failed: "+ioe);
		}
		return csvData;
	}

	/**
	 * Function to get CSV row from the file
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to file, int row number
	 * @return ArrayList object having one row from the file
	 */
	public static ArrayList<String> getCSVRow(String filePath, int rowNumber) {
		String[] rowContents = {};
		ArrayList<String> row = new ArrayList<String>();
		int increment = 0;
		try {
			CSVReader csvReader = new CSVReader(new FileReader(filePath));
			if (rowNumber > 0) {
				while (rowNumber != increment) {
					rowContents = csvReader.readNext();
					increment++;
				}
//				if(rowContents != null) {
					row = new ArrayList<String>(Arrays.asList(rowContents));
//				}
			}	
			csvReader.close();
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.FAIL,fne);
			System.out.println("Failed: "+fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.FAIL,ioe);
			System.out.println("Failed: "+ioe);
		}
		catch(Exception e) {
			logger.log(LogStatus.FAIL,e);
			System.out.println("Failed: "+e);
		}
		return row;
	}

	/**
	 * Function to get one column from CSV file as per the column number
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to file, int column number
	 * @return ArrayList object containing one column from file
	 */
	public static ArrayList<String> getCSVColumn(String filePath, int colNumber) {
		ArrayList<String> colContents = null;
		String[] rowContents = null;
		List<String[]> contents = null;
		ListIterator<String[]> listIterate = null;
		try {
			CSVReader csvReader = new CSVReader(new FileReader(filePath));
			if (colNumber > 0) {
				contents = csvReader.readAll();
				listIterate = contents.listIterator();
				colContents = new ArrayList<String>();
				while (listIterate.hasNext()) {
					rowContents = listIterate.next();
					colContents.add(rowContents[colNumber - 1]);
				}
			}
			csvReader.close();
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.FAIL,fne);
			System.out.println("Failed: "+fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.FAIL,ioe);
			System.out.println("Failed: "+ioe);
		}
		return colContents;
	}

	/**
	 * Function to get one column from CSV file as per the column number
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to file, int column number, int no. of lines to skip
	 * @return ArrayList object containing one column from file
	 */
	public static ArrayList<String> getCSVColumn(String filePath, int colNumber, int skipLines) {
		ArrayList<String> colContents = null;
		String[] rowContents = null;
		List<String[]> contents = null;
		ListIterator<String[]> listIterate = null;
		try {
			CSVReader csvReader = new CSVReader(new FileReader(filePath));
			if (colNumber > 0) {
				while (skipLines > 0) {
					csvReader.readNext();
					skipLines--;
				}
				contents = csvReader.readAll();
				listIterate = contents.listIterator();
				colContents = new ArrayList<String>();
				while (listIterate.hasNext()) {
					rowContents = listIterate.next();
					colContents.add(rowContents[colNumber - 1]);
				}
			}
			csvReader.close();
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.FAIL,fne);
			System.out.println("Failed: "+fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.FAIL,ioe);
			System.out.println("Failed: "+ioe);
		}
		return colContents;
	}

	/**
	 * Function to get CSV column as per header
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to file, String header
	 * @return ArrayList object containing one column of data as per header
	 *         passed
	 */
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
			logger.log(LogStatus.FAIL,fne);
			System.out.println("Failed: "+fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.FAIL,ioe);
			System.out.println("Failed: "+ioe);
		} catch(Exception e) {
			logger.log(LogStatus.FAIL,e);
			System.out.println("Failed: "+e);
		}
		return colContents;
	}

	public static int getColumnNumberOfHeader(String filePath, String header) {
		ArrayList<String> row = TestDataEngine.getCSVRow(filePath, 1);
		int count = 0;
		for (String cell : row) {
			if (cell.equalsIgnoreCase(header)) {
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
	
	/**
	 * Function to get CSV column as per header
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to file, int columnNumber, String header
	 * @return ArrayList object containing one row of data as per header passed
	 */
	public static ArrayList<String> getCSVRowAsPerHeader(String filePath, int columnNumber, String Header) {
		
		ArrayList<String> rowContents = null;
		logger.log(LogStatus.INFO, "Entered inside method getCSVRowAsPerHeader");
		logger.log(LogStatus.INFO, "Going to read file : " + filePath);
		ArrayList<String> columns = TestDataEngine.getCSVColumn(filePath, columnNumber);
		logger.log(LogStatus.INFO, "Data present in Column Number : " + columnNumber + " is : " + columns);
		int rowNumber = 0;
		boolean found = false;
		try {
			for (String column : columns) {

				String colmn = column.toString();
				System.out.println(colmn);
				if (colmn.equalsIgnoreCase(Header)) {
					found = true;
					break;
				}
				rowNumber++;
			}
			if (found == true) {
				rowContents = TestDataEngine.getCSVRow(filePath, rowNumber + 1);
			} else {
				System.out.println("No Such Data Found");
				return null;
			}
			System.out.println(rowContents);
		} catch (NullPointerException npe) {
			logger.log(LogStatus.FAIL,npe);
			System.out.println("Failed: "+npe);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error" + e.getMessage());

		}

		logger.log(LogStatus.INFO, "Row Data Returned as per Header : '" + Header + "' is : " + rowContents);
		return rowContents;
	}
	
	/*
	 * Author Aman Garg
	 * 04FEB2016 
	 * Function to get row number
	 */
	public static int getRowNoAsPerHeader(String filePath, int columnNumber, String Header) {
		
		ArrayList<String> rowContents = null;
		logger.log(LogStatus.INFO, "Entered inside method getCSVRowAsPerHeader");
		logger.log(LogStatus.INFO, "Going to read file : " + filePath);
		ArrayList<String> columns = TestDataEngine.getCSVColumn(filePath, columnNumber);
		logger.log(LogStatus.INFO, "Data present in Column Number : " + columnNumber + " is : " + columns);
		int rowNo = 0;
		int rowNumber = 0;
		boolean found = false;
		try {
			for (String column : columns) {

				String colmn = column.toString();
				System.out.println(colmn);
				if (colmn.equalsIgnoreCase(Header)) {
					found = true;
					rowNo = rowNumber+1;
					break;
				}
				rowNumber++;
			}
			
			System.out.println(rowContents);
		} catch (NullPointerException npe) {
			logger.log(LogStatus.FAIL,npe);
			System.out.println("Failed: "+npe);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error" + e.getMessage());

		}

		logger.log(LogStatus.INFO, "Row Number as per Header : '" + Header + "' is : " + rowNo);
		return rowNo;
	}

	/**
	 * Function to get CSV column as per header
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to file, int columnNumber, Strng Header, int
	 *            numberOfRows
	 * @return ArrayList object containing multiple rows of data as per header
	 *         passed
	 */
	public static ArrayList<ArrayList<String>> getMultipleCSVRowsAsPerHeader(String filePath, int columnNumber,
			String Header, int numberOfRows) {
		
		ArrayList<ArrayList<String>> rowContents = new ArrayList<ArrayList<String>>();
		logger.log(LogStatus.INFO, "Entered inside method getCSVRowAsPerHeader");
		logger.log(LogStatus.INFO, "Going to read file : " + filePath);
		ArrayList<String> columns = TestDataEngine.getCSVColumn(filePath, columnNumber);
		logger.log(LogStatus.INFO, "Data present in Column Number : " + columnNumber + " is : " + columns);
		int rowNumber = -1, count = 0;
		boolean found = false;
		try {
			for (String column : columns) {
				rowNumber++;
				if (column.equalsIgnoreCase(Header)) {
					found = true;
					break;
				}
			}
			if (found == true) {
				while (count < numberOfRows) {
					rowContents.add(TestDataEngine.getCSVRow(filePath, rowNumber + 1 + count));
					count++;
				}
			} else {
				System.out.println("No Such Data Found");
				return null;
			}
			System.out.println(rowContents);
		} catch (NullPointerException npe) {
			logger.log(LogStatus.FAIL,npe);
			System.out.println("Failed: "+npe);
		}
		logger.log(LogStatus.INFO, "Row Data Returned as per Header : '" + Header + "' is : " + rowContents);
		return rowContents;
	}

	public static List<String[]> updateCSVRowByEvaluatingDataTypes(String filePath,
			HashMap<Integer, String> authfieldDataTypeMap, int[] columnPositions, int rowNum) {

		List<String[]> rowToBeUpdated = new ArrayList<String[]>();
		ArrayList<String> contents = null;
		try {

			CSVReader csvReader = new CSVReader(new FileReader(filePath));
			List<String[]> csvData = csvReader.readAll();
			csvReader.close();

			contents = TestDataEngine.getCSVRow(filePath, rowNum);

			CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);

			for (int pos : columnPositions) {

				if (authfieldDataTypeMap.get(pos).equalsIgnoreCase("String")) {
					contents.set(pos, Utility.getRandomString(8).toUpperCase());

				} else if (authfieldDataTypeMap.get(pos).equalsIgnoreCase("Integer")) {

					contents.set(pos, String.valueOf(Utility.getRandomNumber(1, 20)));

				} else if (authfieldDataTypeMap.get(pos).equalsIgnoreCase("Long")) {

					contents.set(pos, String.valueOf(Utility.getRandomNumber(9)));
				}
			}
			String[] row = new String[contents.size()];
			row = contents.toArray(row);
			csvData.set(rowNum, row);
			writer.writeAll(csvData);
			System.out.println("Updated Data Row => " + row);
			writer.close();
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.FAIL,fne);
			System.out.println("Failed: "+fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.FAIL,ioe);
			System.out.println("Failed: "+ioe);
		}

		return rowToBeUpdated;
	}

	public static String getRandomDatafromList(String list, String splitCharacter) {

		String[] data = new String[list.split(splitCharacter).length];
		int index = Utility.getRandomNumber(0, list.split(splitCharacter).length - 1);
		data = list.split(splitCharacter);
		while (data[index] == null || data[index] == "|" || data[index] == " ") {
			index = Utility.getRandomNumber(0, list.split(splitCharacter).length - 1);
		}

		String cellData = data[index];

		return cellData;
	}

	public static String getRandomIntegerDatafromList(String list, String splitCharacter) {

		String[] data = new String[list.split(splitCharacter).length];
		data = list.split(splitCharacter);
		int index = Utility.getRandomNumber(0, list.split(splitCharacter).length - 1);

		while (data[index] == null || data[index] == "|" || data[index] == " ") {
			index = Utility.getRandomNumber(0, list.split(splitCharacter).length - 1);
		}

		String cellData = data[index];

		return cellData;
	}
	
	/**
	 * Generates data for the given template.
	 * 
	 *  Author : Garima Babbar, Date : 07th Feb 2018.
	 * 
	 **/
	
	public static String generateDataByGivenTemplate(String attributeTemplate) {
		String templateValue = "";
		if(Utility.checkIfStringIsNotNullAndNotEmpty(attributeTemplate)) {
			String field = null;
			String dataType = null;
			String isAutoGenerate = null;
			String fieldLength = null; 
			String constantValue = null;
			String[] templateTypes = attributeTemplate.split("\\|");
			if(templateTypes.length == 1) {
				field = "";
				constantValue = attributeTemplate;
			}
			else if(templateTypes.length == 3) {
				isAutoGenerate = templateTypes[0];
				if(isAutoGenerate.equalsIgnoreCase("AUTO") || isAutoGenerate.equalsIgnoreCase("UDS")) {
					isAutoGenerate = "yes";				
				}
				field = templateTypes[1];
				dataType = templateTypes[2];		
			}
			else if(templateTypes.length == 4) {
				isAutoGenerate = templateTypes[0];
				if(isAutoGenerate.equalsIgnoreCase("AUTO") || isAutoGenerate.equalsIgnoreCase("UDS")) {
					isAutoGenerate = "yes";				
				}
				field = templateTypes[1];
				dataType = templateTypes[2];	
				fieldLength = templateTypes[3];
			}
			else if(templateTypes.length == 5) {
				isAutoGenerate = templateTypes[0];
				if(isAutoGenerate.equalsIgnoreCase("AUTO") || isAutoGenerate.equalsIgnoreCase("UDS")) {
					isAutoGenerate = "yes";				
				}
				field = templateTypes[1];
				dataType = templateTypes[2];	
				fieldLength = templateTypes[3];
				constantValue = templateTypes[4];
			}
			templateValue = generateDataByDataTypeEvaluation(field, dataType, isAutoGenerate, fieldLength, constantValue, 1);
		}
		return templateValue;
	}

	/**
	 * Function to Generate CSV Row via Auto Test Data Generation if the
	 * DataTypes and Field Lengths and Constants are known CHAR,STRING, DATE
	 * 
	 * @author Vikas Chauhan
	 * 
	 * @Modified Author : Surender, Date : 24th July 2017.
	 * Added Code for splitting cell value from csv file based on index.
	 * 
	 */

	public static String generateDataByDataTypeEvaluation(String field, String dataType, String isAutoGenerate,
			String fieldLength, String constantValue, int fieldIndex) {


		ArrayList<String> data = null;
		String cellData = null;
		Calendar c = Calendar.getInstance();
//		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy hh:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String currentDate= dateFormat.format(date);
		System.out.println(currentDate);
		
		try{
			   //Setting the date to the given date
			   c.setTime(dateFormat.parse(currentDate));
			}catch(Exception e){
				e.printStackTrace();
			 }
			   
			//Number of Days to add
			c.add(Calendar.DAY_OF_MONTH, 1);  
			//Date after adding the days to the given date
			String newDate = dateFormat.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
		
		System.out.println("Receive Field Details => " + field + " ," + dataType + " ," + isAutoGenerate + ", "
				+ fieldLength + ", " + constantValue);
		logger.log(LogStatus.INFO, "Receive Field Details => " + field + " ," + dataType + " ," + isAutoGenerate
				+ ", " + fieldLength + ", " + constantValue);
		
		/**
		 * To check if the data is to be taken from a file. 
		 * 
		 **/
		
		if(dataType != null && dataType.equalsIgnoreCase("file")){
			String fileName = constantValue.split(":")[0];
			
			if(StringUtils.contains(fileName, ".csv")){
				String columnName = constantValue.split(":")[1];
				String rowNumber = constantValue.split(":")[2];
				try{
					cellData = TestDataEngine.getCSVColumnPerHeader(fileName, columnName).get(Integer.parseInt(rowNumber)-1);
//					try{
//						String indexOfFirstSplitCellValue = constantValue.split(":")[3];
//						if(indexOfFirstSplitCellValue.matches("\\d+")){
//							cellData = cellData.split(" ")[Integer.parseInt(indexOfFirstSplitCellValue)];
//						}
//					}
//					catch(Exception ex){
//						logger.log(LogStatus.ERROR,"Exception is : "+ ex);
//					}
				}
				catch(IndexOutOfBoundsException indexOutOfBoundsException){
					logger.log(LogStatus.ERROR,"Exception is : "+ indexOutOfBoundsException);
				}
				catch(Exception exception){
					logger.log(LogStatus.ERROR,"Exception is : "+ exception);
				}
			}
			else if(StringUtils.contains(fileName, ".properties")){
				String propertyName = constantValue.split(":")[1];
				
				try{
					Properties propertyObj = Utility.loadPropertyFile(fileName);
					cellData = propertyObj.getProperty(propertyName);
				}
				catch(Exception exception){
					cellData = "Failed to generate data from the properties file. Please check the syntax.";
					logger.log(LogStatus.ERROR, exception.getMessage());
				}
				
			}
			
			return cellData;
		}
		
		if(dataType != null && dataType.equalsIgnoreCase("FileToDatabase")){
			String fileName = constantValue.split(":")[0];
			
			if(StringUtils.contains(fileName, ".csv")){
				String columnName = constantValue.split(":")[1];
				String rowNumber = constantValue.split(":")[2];
				String query = constantValue.split(":")[3];
				
				try{
					String value = TestDataEngine.getCSVColumnPerHeader(fileName, columnName).get(Integer.parseInt(rowNumber)-1);
					query = query.replace("$temp", value);
				//	ArrayList<ArrayList<Object>> result = Database.getResultsFromDatabase(query);
					ArrayList<ArrayList<Object>> result =(ArrayList<ArrayList<Object>>) MsSql.readTableWithReturnValue(query);
					
					if(result != null && !result.isEmpty()){
						ArrayList<ArrayList<String>> resultStr = Utility.objectToStringConversion(result);
						try{
							cellData = resultStr.get(0).get(0);
						}
						catch(IndexOutOfBoundsException indexOutOfBoundsException){
							logger.log(LogStatus.ERROR, indexOutOfBoundsException.getMessage());
						}
						catch(Exception exception){
							logger.log(LogStatus.ERROR, exception.getMessage());
						}
					}
				}
				catch(IndexOutOfBoundsException indexOutOfBoundsException){
					logger.log(LogStatus.ERROR,"Exception is : "+ indexOutOfBoundsException);
				}
				catch(Exception exception){
					logger.log(LogStatus.ERROR,"Exception is : "+ exception);
				}
			}
			else if(StringUtils.contains(fileName, ".properties")){
				String propertyName = constantValue.split(":")[1];
				
				try{
					Properties propertyObj = Utility.loadPropertyFile(fileName);
					cellData = propertyObj.getProperty(propertyName);
				}
				catch(Exception exception){
					cellData = "Failed to generate data from the properties file. Please check the syntax.";
					logger.log(LogStatus.ERROR, exception.getMessage());
				}
				
			}
			
			return cellData;
		}
		
		
		
		
		
		/**
		 * To check if the data is to be taken from database. 
		 * 
		 * 
		 **/
		
		if(dataType != null && dataType.equalsIgnoreCase("database")){
			String query = constantValue;
	//		ArrayList<ArrayList<Object>> result = Database.getResultsFromDatabase(query);
			ArrayList<ArrayList<Object>> result =(ArrayList<ArrayList<Object>>) MsSql.readTableWithReturnValue(query);
			if(result != null && !result.isEmpty()){
				ArrayList<ArrayList<String>> resultStr = Utility.objectToStringConversion(result);
				try{
					cellData = resultStr.get(0).get(0);
				}
				catch(IndexOutOfBoundsException indexOutOfBoundsException){
					logger.log(LogStatus.ERROR, indexOutOfBoundsException.getMessage());
				}
				catch(Exception exception){
					logger.log(LogStatus.ERROR, exception.getMessage());
				}
			}
			return cellData;
		}

		if(isAutoGenerate != null && isAutoGenerate.equalsIgnoreCase("yes") && dataType.equalsIgnoreCase("String")){
			ArrayList<String> udsColsRequested = TestDataInterface.getUdsColumns();
			// Check if Data source has Data Requested if the given Field Exists,
			// Check for the Match in UDS with header Name

			data = TestDataEngine.getCSVColumnPerHeader(TestDataInterface.getDataSourceFilePath(), field);
	
		
			if (data != null) {
				int index = Utility.getRandomNumber(0, data.size() - 1);
				if(field.equalsIgnoreCase("RoleName")) {
					String str = Utility.getRandomString(5);
					cellData = data.get(index) + " " + str.toUpperCase();
				}
				else if(field.equalsIgnoreCase("FirstName")) {
					cellData = DataGenNew.autoFirstName();
					cellData = cellData.replaceAll(" ", "");
				}
				else {
					cellData = data.get(index);
				}
			} else if (!udsColsRequested.isEmpty()) {

				logger.log(LogStatus.ERROR, "Requested Column " + field + " from Header not Exist in the UDS");
				// Check for Match in UDS with Request UDS ColumnName from Template
				// file
				String fieldName = udsColsRequested.get(fieldIndex);
				data = TestDataEngine.getCSVColumnPerHeader(TestDataEngine.dataSourceFilePath, fieldName);
				if (data != null) {
					int index = Utility.getRandomNumber(0, data.size() - 1);
					cellData = data.get(index);
				} else {
					System.out.println("Requested Column " + field + " Mapped in Template File  not Exist in the UDS ");
					logger.log(LogStatus.ERROR,
							"Requested Column " + field + " Mapped in Template File  not Exist in the UDS");
				}
			
			}
		}

		// If still Data is null then Regenration Logic for Data.

		if (data == null && field != null) {
			if (dataType == null && constantValue != null && isAutoGenerate == null && fieldLength == null) {
				cellData = constantValue;
			} 
			else if (dataType.equalsIgnoreCase(constantValue) && constantValue.equalsIgnoreCase(fieldLength)) {
				cellData = constantValue;
			}
			else {
				if (dataType.equalsIgnoreCase("String") && isAutoGenerate.equalsIgnoreCase("Yes") && (constantValue == null || constantValue.isEmpty())) {
					cellData = "U" + Utility.getRandomString(Integer.parseInt(fieldLength)-1).toUpperCase();
				}
				else if(dataType.equalsIgnoreCase("String") && isAutoGenerate.equalsIgnoreCase("Yes") && (constantValue != null && !constantValue.isEmpty())){
					cellData = constantValue;
				}
				else if (dataType.equalsIgnoreCase("Long") && isAutoGenerate.equalsIgnoreCase("Yes") && (constantValue == null || constantValue.isEmpty())) {
					cellData = Long.toString(Utility.getRandomNumber(Integer.parseInt(fieldLength)));
				} 
				else if (dataType.equalsIgnoreCase("Integer") && isAutoGenerate.equalsIgnoreCase("Yes") && (constantValue == null || constantValue.isEmpty())) {
					cellData = Long.toString(Utility.getRandomNumber(Integer.parseInt(fieldLength)));
				}
				else if ((dataType.equalsIgnoreCase("Integer") || dataType.equalsIgnoreCase("Long")) && isAutoGenerate.equalsIgnoreCase("UserId")) {

					while (true) {
						int regenrate = 0;
						cellData = Long.toString(Utility.getRandomNumber(Integer.parseInt(fieldLength)));
						if (MsSql.doesUserIdExist(cellData)) {
							regenrate++;
						} else {
							break;
						}
					}

				}
				else if (dataType.equalsIgnoreCase("String") && isAutoGenerate.equalsIgnoreCase("UserId")) {
					cellData = "U" + Utility.getRandomString(Integer.parseInt(fieldLength) - 1).toUpperCase();
				} 
				
				if (dataType.equalsIgnoreCase("String") && isAutoGenerate.equalsIgnoreCase("List") && (constantValue != null && !constantValue.isEmpty())) {
					if(constantValue.contains("\\|")){
						cellData = TestDataEngine.getRandomDatafromList(constantValue, "\\|");
					}
					else if(constantValue.contains(";")){
						cellData = TestDataEngine.getRandomDatafromList(constantValue, ";");
					}
				}

				if (dataType.equalsIgnoreCase("Integer") && isAutoGenerate.equalsIgnoreCase("List")  && (constantValue != null && !constantValue.isEmpty())) {
					if(constantValue.contains("\\|")){
						cellData = TestDataEngine.getRandomDatafromList(constantValue, "\\|");
					}
					else if(constantValue.contains(";")){
						cellData = TestDataEngine.getRandomDatafromList(constantValue, ";");
					}
				}

				if (dataType.equalsIgnoreCase("Integer") && isAutoGenerate.equalsIgnoreCase("List")  && (constantValue != null && !constantValue.isEmpty())) {
					if(constantValue.contains("\\|")){
						cellData = TestDataEngine.getRandomDatafromList(constantValue, "\\|");
					}
					else if(constantValue.contains(";")){
						cellData = TestDataEngine.getRandomDatafromList(constantValue, ";");
					}
				}
				else if (dataType.equalsIgnoreCase("String") && isAutoGenerate.equalsIgnoreCase("email")
						&& constantValue.toString() != null){
					cellData = constantValue.toString();
				} 
				else if (dataType.equalsIgnoreCase("Date") && isAutoGenerate.equalsIgnoreCase("No")
						&& (constantValue.toString().isEmpty() || constantValue == null )){
					cellData = "null";
				}
				else if (dataType.equalsIgnoreCase("Date") && isAutoGenerate.equalsIgnoreCase("Yes")
						&& (constantValue == null || constantValue.isEmpty() )) {
				
				//	cellData = Utility.getDate(DBValidations.returnApplicationUIDateFormat(), 0, "days");
					if(Utility.compareStringValues(field, "ValidFrom"))
						cellData = currentDate;
					if(Utility.compareStringValues(field, "ValidTo"))
						cellData = newDate;
				} 
				else if (dataType.equalsIgnoreCase("Date") && isAutoGenerate.equalsIgnoreCase("Yes")
						&& constantValue != null) {
					
					/**
					 *  If constant value contains any numeric value, then add those number of days to the current date.
					 * */
					if(constantValue.matches("^-?\\d+$")){
	//					String applicationDateFormat = DBValidations.returnApplicationUIDateFormat();
						String applicationDateFormat ="";
						if(applicationDateFormat != null && !applicationDateFormat.isEmpty()){
							cellData = Utility.getDate(applicationDateFormat, Integer.parseInt(constantValue), "days");
						}
					}
					else{
						cellData = constantValue.toString();
					}
					
				} 
				else if(dataType.equalsIgnoreCase("DateTime") && isAutoGenerate.equalsIgnoreCase("No")
						&& (constantValue == null || constantValue.isEmpty())){
					cellData = "";
				}
				else if(dataType.equalsIgnoreCase("DateTime") && isAutoGenerate.equalsIgnoreCase("No")
						&& (constantValue != null && !constantValue.isEmpty())){
					cellData = constantValue;
				}
				else if (dataType.equalsIgnoreCase("DateTime") && isAutoGenerate.equalsIgnoreCase("Yes")
						&& (constantValue == null || constantValue.isEmpty())){
					
					cellData = Utility.getDate(DBValidations.returnApplicationUIDateFormat() + " " + DBValidations.returnApplicationTimeFormat(), 0, "days");
				}
				else if (dataType.equalsIgnoreCase("DateTime") && isAutoGenerate.equalsIgnoreCase("Yes")
						&& constantValue != null && !constantValue.isEmpty()) {
					
					if(constantValue.split(" ").length == 1 && constantValue.matches("^-?\\d+$")){
						String applicationDateFormat = DBValidations.returnApplicationUIDateFormat();
						String applicationTimeFormat = DBValidations.returnApplicationTimeFormat();
						if(applicationDateFormat != null && !applicationDateFormat.isEmpty()){
							cellData = Utility.getDate(applicationDateFormat + " " + applicationTimeFormat, Integer.parseInt(constantValue), "days");
						}
					}
					else if(constantValue.split(" ").length == 2){
						String dateValue = "";
						int noOfDays = Integer.parseInt(constantValue.split(" ")[0]);
						String applicationDateFormat = DBValidations.returnApplicationUIDateFormat();
						if(applicationDateFormat != null && !applicationDateFormat.isEmpty()){
							dateValue = Utility.getDate(applicationDateFormat, noOfDays, "days");
						}
						
						String timeValue = constantValue.split(" ")[1];
						cellData = dateValue + " " + timeValue; 
						
					}
					else{
						cellData = constantValue.toString();
					}
					
				}
				else if(dataType.equalsIgnoreCase("StartDateTime")&& isAutoGenerate.equalsIgnoreCase("Yes")) {
					cellData = Utility.getDate(DBValidations.returnApplicationUIDateFormat(), Integer.parseInt(constantValue) , "days") + " 00:00:00";
				}
				
				else if(dataType.equalsIgnoreCase("EndDateTime")&& isAutoGenerate.equalsIgnoreCase("Yes")) {
					cellData = Utility.getDate(DBValidations.returnApplicationUIDateFormat(), Integer.parseInt(constantValue), "days") + " 23:59:59";
				}
				else if(dataType.equalsIgnoreCase("EndDateTime0Secs") && isAutoGenerate.equalsIgnoreCase("Yes")) {
					cellData = Utility.getDate(DBValidations.returnApplicationUIDateFormat(), Integer.parseInt(constantValue), "days") + " 23:59:00";
				}
				else if (dataType.equalsIgnoreCase("Timestamp") && isAutoGenerate.equalsIgnoreCase("Yes")
						&& (constantValue == null || constantValue.isEmpty())) {
					
					cellData = Utility.getDate(DBValidations.returnApplicationUIDateFormat() + " HH:mm:ss", 0, "days");
					
				} else if (isAutoGenerate.equalsIgnoreCase("No") && constantValue != null) {
					cellData = constantValue;
				}				
			}
		}
		try {

			logger.log(LogStatus.INFO,
					"Generated Field Data =>" + field + "," + cellData + "," + "," + cellData.length());
			System.out.println("Generated Field Data =>" + field + "," + cellData + "," + "," + cellData.length());
		} catch (NullPointerException nlp) {

			logger.log(LogStatus.ERROR, "Null Pointer Exception in Field" + field);
		}

		return cellData;
	
		/*

		String cellData = null;

		System.out.println("Receive Field Details => " + field + " ," + dataType + " ," + isAutoGenerate + ", "
				+ fieldLength + ", " + constantValue);

		AELogger.log(Globals.Log.INFO, "Receive Field Details => " + field + " ," + dataType + " ," + isAutoGenerate
				+ ", " + fieldLength + ", " + constantValue);

		ArrayList<String> udsColsRequested = TestDataInterface.getUdsColumns();

		// Check if Data source has Data Requested if the given Field Exists,
		// Check for the Match in UDS with header Name

		ArrayList<String> data = TestDataEngine.getCSVColumnPerHeader(TestDataEngine.dataSourceFilePath, field);
	
		
		if (data != null) {
			int index = CommonFunctions.getRandomNumber(0, data.size() - 1);
			cellData = data.get(index);
			
		} else if (!udsColsRequested.isEmpty()) {

			AELogger.log(Globals.Log.ERROR, "Requested Column " + field + " from Header not Exist in the UDS");
			// Check for Match in UDS with Request UDS ColumnName from Template
			// file
			String fieldName = udsColsRequested.get(fieldIndex);
			data = TestDataEngine.getCSVColumnPerHeader(TestDataEngine.dataSourceFilePath, fieldName);
			if (data != null) {
				int index = CommonFunctions.getRandomNumber(0, data.size() - 1);
				cellData = data.get(index);
			} else {
				System.out.println("Requested Column " + field + " Mapped in Template File  not Exist in the UDS ");
				AELogger.log(Globals.Log.ERROR,
						"Requested Column " + field + " Mapped in Template File  not Exist in the UDS");
			}
			
		}

		// If still Data is null then Regenration Logic for Data.

		if (data == null && field != null) {

			if (dataType == null && constantValue != null && isAutoGenerate == null && fieldLength == null) {
				cellData = constantValue;
			} else if (dataType.equalsIgnoreCase(constantValue) && constantValue.equalsIgnoreCase(fieldLength)) {

				cellData = constantValue;
			}

			else {
				if (dataType.equalsIgnoreCase("String") && isAutoGenerate.equalsIgnoreCase("Yes")) {
					cellData = CommonFunctions.getRandomString(Integer.parseInt(fieldLength)).toUpperCase();

				} else if (dataType.equalsIgnoreCase("Long") && isAutoGenerate.equalsIgnoreCase("Yes")) {

					cellData = Long.toString(CommonFunctions.getRandomNumber(Integer.parseInt(fieldLength)));

				} else if (dataType.equalsIgnoreCase("Integer") && isAutoGenerate.equalsIgnoreCase("Yes")) {

					cellData = Long.toString(CommonFunctions.getRandomNumber(Integer.parseInt(fieldLength)));

				}

				else if ((dataType.equalsIgnoreCase("Integer") || dataType.equalsIgnoreCase("Long")) && isAutoGenerate.equalsIgnoreCase("UserId")) {

					while (true) {
						int regenrate = 0;
						cellData = Long.toString(CommonFunctions.getRandomNumber(Integer.parseInt(fieldLength)));
						if (Database.doesUserIdExist(cellData)) {
							regenrate++;
						} else {
							break;
						}
					}

				} else if (dataType.equalsIgnoreCase("String") && isAutoGenerate.equalsIgnoreCase("UserId")) {

					cellData = CommonFunctions.getRandomString(Integer.parseInt(fieldLength));

				} 
				if (dataType.equalsIgnoreCase("String") && isAutoGenerate.equalsIgnoreCase("List")) {
					cellData = TestDataEngine.getRandomDatafromList(constantValue, "\\|");

				}

				if (dataType.equalsIgnoreCase("Integer") && isAutoGenerate.equalsIgnoreCase("List")) {
					cellData = TestDataEngine.getRandomDatafromList(constantValue, "\\|");

				}

				if (dataType.equalsIgnoreCase("Integer") && isAutoGenerate.equalsIgnoreCase("List")) {
					cellData = TestDataEngine.getRandomDatafromList(constantValue, "\\|");

				} else if (dataType.equalsIgnoreCase("String") && isAutoGenerate.equalsIgnoreCase("email")
						&& constantValue.toString() != null)

				{
					cellData = constantValue.toString();

				} else if (dataType.equalsIgnoreCase("Date") && isAutoGenerate.equalsIgnoreCase("No")
						&& (constantValue.toString().isEmpty() || constantValue == null ))
				{
					cellData = "null";

				} else if (dataType.equalsIgnoreCase("Date") && isAutoGenerate.equalsIgnoreCase("Yes")
						&& (constantValue == null || constantValue.isEmpty() )) {
					
					cellData = Page.getDate(DBValidations.returnApplicationDateFormat(), 0, "days");
					
					if(HRDBReconInterface.HRDBRECON_DBTYPE.equalsIgnoreCase("Oracle")){
						cellData = Page.getDate("dd-MMM-yy", 0, "days");
					}
					else if(HRDBReconInterface.HRDBRECON_DBTYPE.equalsIgnoreCase("mySql")){
						cellData = Page.getDate("yyyy-MM-dd", 0, "days");
					}
					else{
						cellData = Page.getDate("yyyy-MM-dd", 0, "days");
					}
				} else if (dataType.equalsIgnoreCase("Date") && isAutoGenerate.equalsIgnoreCase("Yes")
						&& constantValue != null) {
					
					*//**
					 *  If constant value contains any numeric value, then add those number of days to the current date.
					 * *//*
					if(constantValue.matches("^-?\\d+$")){
						cellData = Page.getDate(DBValidations.returnApplicationDateFormat(), Integer.parseInt(constantValue), "days");
						if(HRDBReconInterface.HRDBRECON_DBTYPE.equalsIgnoreCase("Oracle")){
							cellData = Page.getDate("dd-MMM-yy", Integer.parseInt(constantValue), "days"); 
						}
						else if(HRDBReconInterface.HRDBRECON_DBTYPE.equalsIgnoreCase("mySql")){
							cellData = Page.getDate("yyyy-MM-dd", Integer.parseInt(constantValue), "days");
						}
						else{
							cellData = Page.getDate("yyyy-MM-dd", Integer.parseInt(constantValue), "days");
						}
					}
					else{
						cellData = constantValue.toString();
					}
					
				} else if (dataType.equalsIgnoreCase("DateTime") && isAutoGenerate.equalsIgnoreCase("Yes")
						&& (constantValue == null || constantValue.isEmpty())) {
					
					cellData = Page.getDate(DBValidations.returnApplicationDateFormat() + " HH:mm:ss", 0, "days");

					if (HRDBReconInterface.HRDBRECON_DBTYPE.equalsIgnoreCase("Oracle")) {

						cellData = Page.getDate("dd-MMM-YY", 0, "days");

					} else if (HRDBReconInterface.HRDBRECON_DBTYPE.equalsIgnoreCase("mySql")) {

						cellData = Page.getDate("dd/MM/YYYY HH:mm:ss", 0, "days");

					} else {

						cellData = Page.getDate("YYYY-MM-DD HH:mm:ss", 0, "days");
					}

				}

				else if (dataType.equalsIgnoreCase("DateTime") && isAutoGenerate.equalsIgnoreCase("Yes")
						&& constantValue != null) {

					cellData = constantValue.toString();
				} else if (dataType.equalsIgnoreCase("Timestamp") && isAutoGenerate.equalsIgnoreCase("Yes")
						&& (constantValue == null || constantValue.isEmpty())) {
					
					cellData = Page.getDate(DBValidations.returnApplicationDateFormat() + " HH:mm:ss", 0, "days");
					
					if (HRDBReconInterface.HRDBRECON_DBTYPE.equalsIgnoreCase("Oracle")) {
						cellData = Page.getDate("MM/dd/yyyy HH:mm:ss", 0, "days");
					}
				} else if (isAutoGenerate.equalsIgnoreCase("No") && constantValue != null) {
					cellData = constantValue;

					if (constantValue.equalsIgnoreCase("Now()")) {

						cellData = Page.getDate("YYYY-mm-dd HH:mm:ss", 0, "days");
					} else {
						cellData = constantValue;
					}
				}
			}
		}
		try {

			AELogger.log(Globals.Log.INFO,
					"Generated Field Data =>" + field + "," + cellData + "," + "," + cellData.length());
			System.out.println("Generated Field Data =>" + field + "," + cellData + "," + "," + cellData.length());
		} catch (NullPointerException nlp) {

			AELogger.log(Globals.Log.ERROR, "Null Pointer Exception in Field" + field);
		}

		return cellData;
	*/}

	public static int getNumOfRowsInCSV(String filePath, int colNumber, int skipLines) {
		ArrayList<String> col = TestDataEngine.getCSVColumn(filePath, colNumber, skipLines);
		int noofRows = col.size();
		return noofRows;
	}

	/*
	 * Author: Vikas Chauhan Method to Compute the Test Data Template and
	 * Generate Test Data for Form based on Data Types and Max Length This
	 * Method Works by Generating Test Data based on UDS File Supplied
	 */

	public static void compileDataTemplate(String templatePath, String dataSourceFilePath, String dropFilePath) {

		File templateFile = new File(templatePath);
		File dataSourceFile = new File(dataSourceFilePath);
		if (!templateFile.exists()) {
			System.out.println("The Template File Does not Exist at Path : " + templatePath);
		}
		if (!dataSourceFile.exists()) {

			System.out.println("The Data Source file does not exist at path : " + dataSourceFilePath);
		}

		ArrayList<String> headers = TestDataEngine.getCSVRow(templatePath, 1);
		ArrayList<String> fieldsRow = TestDataEngine.getCSVRow(templatePath, 2);
		TestDataInterface.setTemplateFieldTypes(fieldsRow);

		int index = 0, fieldsColumnStart = 0;
		for (String string : fieldsRow) {
			if (!string.isEmpty()) {
				fieldsColumnStart = index;
				break;
			} else {
				index++;
			}
		}
		
		fieldsColumnStart=fieldsColumnStart+1;
		int skiplines = 2, rowIndex = 0;
		ArrayList<String> col = TestDataEngine.getCSVColumn(templatePath, fieldsColumnStart);
		String[] angularTypes = TestDataInterface.getTemplateFieldTypes()
				.toArray(new String[TestDataInterface.getTemplateFieldTypes().size()]);
		String[] headerArray= headers.toArray(new String[headers.size()]);
		TestDataEngine.CSVData.add(headerArray);
		TestDataEngine.CSVData.add(angularTypes);
		while (rowIndex <= col.size()) {
			if (rowIndex <= skiplines) {
				
				rowIndex++;
			} else {
						ArrayList<String> templateDataRow = TestDataEngine.getCSVRow(templatePath, rowIndex);
						ArrayList<String> fieldLengths = getLengthsFromTemplate(templateDataRow, fieldsColumnStart-1);
						ArrayList<String> dataTypes = getDataTypesFromTemplate(templateDataRow, fieldsColumnStart-1);
						ArrayList<String> constants = getConstantsFromTemplate(templateDataRow, fieldsColumnStart-1);
						ArrayList<String> autoDataGenrateFieldList = getAutoGenrateFields(templateDataRow, fieldsColumnStart-1);
						ArrayList<String> udsCols = getUDSColsFromTemplate(templateDataRow, fieldsColumnStart-1);
						TestDataInterface.setUdsColumns(udsCols);
						TestDataInterface.setTemplateFieldTypes(fieldsRow);
						createDataFilefromTemplate(dropFilePath, headers, dataTypes, autoDataGenrateFieldList,
								fieldLengths, constants, 1);
						templateDataRow.clear();
						rowIndex++;
			}

		}
		TestDataEngine.writeDataToCSV(dropFilePath);
	}

	private static ArrayList<String> getAutoGenrateFields(ArrayList<String> templateRow, int fieldIndex) {
        // TODO Auto-generated method stub
        ArrayList<String> autoGenMap = new ArrayList<String>();
        for (int index = 0; index < templateRow.size(); index++) {

               if (index < fieldIndex) {

                     if (templateRow.get(index) != null) {
                            autoGenMap.add(index, templateRow.get(index));
                     }
               } else if (index >= fieldIndex) {

                     if (index >= fieldIndex) {

                            String pipedData = templateRow.get(index);
                            logger.log(LogStatus.INFO, " Extracting Auto Generation Fields from Template => " + pipedData);
                            if (pipedData.contains("AUTO") || pipedData.contains("UDS")) {
                                   autoGenMap.add(index, "Yes");
                            }
                            else if (pipedData.startsWith("$") && pipedData.contains("@")){
                                   autoGenMap.add(index,"email");
                            }else if (pipedData.startsWith("$")){
                                   autoGenMap.add(null);
/*                                       autoGenMap.add(index, pipedData);
*/                                } 
                            else {
                                   autoGenMap.add(null);
                            }
                     }

               }
        }

        return autoGenMap;
 }

	public static ArrayList<String> getDataTypesFromTemplate(ArrayList<String> templateRow, int fieldIndex) {
		// TODO Auto-generated method stub

		ArrayList<String> dataTypes = new ArrayList<String>();
		for (int index = 0; index < templateRow.size(); index++) {
			if (index < fieldIndex) {

				if (templateRow.get(index) != null) {
					dataTypes.add(index, templateRow.get(index));
				}
			} else if (index >= fieldIndex) {

				String pipedData = templateRow.get(index);
				if (pipedData.toUpperCase().startsWith("AUTO") || pipedData.toUpperCase().startsWith("UDS")) {

					logger.log(LogStatus.INFO, " Extracting DataTypes from Template => " + pipedData);
					String data[] = pipedData.split("\\|");

					if (data.length - 1 >= 2) {
						dataTypes.add(index, data[2].toString());
					}
					else{	
						dataTypes.add(null);
						System.out.println("The Template File does not have DataType Defined for Attribute");
					}

				}  else if(pipedData.startsWith("$") && pipedData.contains("@"))	{
					dataTypes.add(index,"String");
				}else {
					dataTypes.add(null);
				}
			}

		}

		return dataTypes;

	}

	public static ArrayList<String> getLengthsFromTemplate(ArrayList<String> templateRow, int fieldIndex) {

		ArrayList<String> lengths = new ArrayList<String>();

		for (int index = 0; index < templateRow.size(); index++) {

			if (index < fieldIndex) {

				if (templateRow.get(index) != null) {
					lengths.add(index, templateRow.get(index).toString());
				}
			} else if (index >= fieldIndex) {

				String pipedData = templateRow.get(index);
				if (pipedData.toUpperCase().startsWith("AUTO") || pipedData.toUpperCase().startsWith("UDS")) {

					String data[] = pipedData.split("\\|");

					logger.log(LogStatus.INFO, " Extracting Field Length Data => " + pipedData);
					if (data.length - 1 >= 3) {
						lengths.add(index, data[3].toString());
					} else {
						System.out.println("The Template File does not have Data Length Defined for Attribute");
						lengths.add(null);
					}

				} else {
					lengths.add(null);
				}

			}

		}

		return lengths;
	}

	public static ArrayList<String> getUDSColsFromTemplate(ArrayList<String> templateRow, int fieldIndex) {

		ArrayList<String> udsCols = new ArrayList<String>();

		for (int index = 0; index < templateRow.size(); index++) {
			if (index < fieldIndex) {

				if (templateRow.get(index) != null) {
					udsCols.add(index, templateRow.get(index));
				}
			} else if (index >= fieldIndex) {

				String pipedData = templateRow.get(index);
				if (pipedData.toUpperCase().startsWith("UDS")) {
					logger.log(LogStatus.INFO, " Extracting UDS Columns Data => " + pipedData);
					String data[] = pipedData.split("\\|");
					if (data.length - 1 >= 1) {
						udsCols.add(index, data[1]);
					} else {
						udsCols.add(null);
						System.out.println("The Template File does not have UDS Column requested for Attribute");

					}

				} else {
					udsCols.add(null);
				}
			}

		}

		return udsCols;
	}

	public static ArrayList<String> getConstantsFromTemplate(ArrayList<String> templateRow, int fieldIndex) {

		ArrayList<String> constants = new ArrayList<String>();

		for (int index = 0; index < templateRow.size(); index++) {
			if (index < fieldIndex) {
				if (templateRow.get(index) != null) {
					constants.add(index, templateRow.get(index));
				}
			} else if (index >= fieldIndex) {

				String data = templateRow.get(index);
				if (!data.contains("|")) {
					logger.log(LogStatus.INFO, " Extracting Constant Data => " + data);
					constants.add(index, data);
				}
				 else if(data.startsWith("$") && data.contains("@")){
					constants.add(index,data);
				}
				 else if(data.contains("|")){
					 String[] singleData = data.split("\\|");
					 if(singleData.length >= 5){
						 constants.add(index, singleData[4]);
					 }
					 else{
						 constants.add(null);
					 }
				 }
				else {
					constants.add(null);
				}
			}
		}
	
		return constants;
	}

	/**
	 * Function to update test data in CSV file By Field Types, INT,
	 * CHAR,STRING, DATE
	 * 
	 * @author Vikas Chauhan
	 * @param String
	 *            - path to the file, int[] column positions, int no. of lines
	 *            to skip
	 */

	public static void createRandomTestDataFilebyDataTypes(String filePath, ArrayList<String> headers,
			ArrayList<String> dataTypes, ArrayList<String> autoDataGenrateFieldList, ArrayList<String> fieldLengths,
			ArrayList<String> constantsData, int totalRows) {
		try {
			
			File file = new File(filePath);
			if (file.createNewFile()) {
				System.out.println("File is created!");
			} else {
				System.out.println("File already exists.");
			}
			CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
			ArrayList<String> row = null;
			String[] rowContents = null;
			String[] headersContent = null;
			String[] dataTypesContent = null;
			int index = 0;
			if (headers.size() == dataTypes.size()) {
				List<String[]> contents = new ArrayList<String[]>();
				headersContent = headers.toArray(new String[headers.size()]);
				dataTypesContent = dataTypes.toArray(new String[dataTypes.size()]);

				// Add Headers from Table
				contents.add(headersContent);
				// Add DataTypes
				contents.add(dataTypesContent);
				while (totalRows > 0) {
					row = new ArrayList<String>();
					// for (String dataType : dataTypes) {

					for (index = 0; index < dataTypes.size(); index++) {

						String dataType = dataTypes.get(index);
						String isAutoGenerate = autoDataGenrateFieldList.get(index);
						String fieldLength = fieldLengths.get(index);
						String constant = constantsData.get(index);
						String field = headers.get(index);

						row.add(TestDataEngine.generateDataByDataTypeEvaluation(field, dataType, isAutoGenerate,
								fieldLength, constant, index));

					}

					// Drive fields on Row if contains $FieldName
					int iterator = 0;
					for (String cellData : row) {

						if (autoDataGenrateFieldList.get(iterator) == null) {

						} else if (autoDataGenrateFieldList.get(iterator).contains("$")
								&& constantsData.get(iterator).isEmpty()) {
							row.set(iterator, row.get(row.indexOf(cellData.substring(1, cellData.length()))));

							logger.log(LogStatus.INFO, "Data found with $ " + cellData);
							logger.log(LogStatus.INFO, "Updated Row" + row);
						}

						// Calculate Email from Generated UserId Field for the
						// Row
						else if (autoDataGenrateFieldList.get(iterator).equalsIgnoreCase("email")
								&& (constantsData.get(iterator).startsWith("$"))) {
							row.set(iterator, row.get(headers.indexOf(cellData.substring(1, cellData.indexOf('@'))))
									+ constantsData.get(iterator).substring(constantsData.get(iterator).indexOf('@'),
											constantsData.get(iterator).length()));

							logger.log(LogStatus.INFO, "Data found with $ " + cellData);
							logger.log(LogStatus.INFO, "Updated Row" + row);
						}

						iterator++;
					}

					rowContents = row.toArray(new String[row.size()]);
					contents.add(rowContents);

					totalRows--;
					row.clear();
				}

				if (contents != null) {

						writer.writeAll(contents);
						writer.close();
						contents.clear();
					}
				
				} else {
				System.err.println("Length Mismatch : Length of headers and DataTypes are not same");
				logger.log(LogStatus.ERROR, "Length Mismatch : Length of headers and DataTypes are not same");
			}
		} catch (

		Exception e)

		{
			logger.log(LogStatus.ERROR, e);
		}

	}

		
	public static void createDataFilefromTemplate(String filePath, ArrayList<String> headers,
			ArrayList<String> dataTypes, ArrayList<String> autoDataGenrateFieldList, ArrayList<String> fieldLengths,
			ArrayList<String> constantsData, int totalRows) {

		

		ArrayList<String> row = null;
		String[] rowContents = null;

		int index = 0;
		if (headers.size() == dataTypes.size()) {

			while (totalRows > 0) {
				row = new ArrayList<String>();
				// for (String dataType : dataTypes) {

				for (index = 0; index < dataTypes.size(); index++) {
					String dataType = dataTypes.get(index);
					String isAutoGenerate = autoDataGenrateFieldList.get(index);
					String fieldLength = fieldLengths.get(index);
					String constant = constantsData.get(index);
					String field = headers.get(index);
					row.add(TestDataEngine.generateDataByDataTypeEvaluation(field, dataType, isAutoGenerate,
					fieldLength, constant, index));
				}

				// Drive fields on Row if contains $FieldName
				int iterator = 0;
				for (String cellData : row) {
					if (autoDataGenrateFieldList.get(iterator) == null) {
					} else if (autoDataGenrateFieldList.get(iterator).contains("$")
							&& constantsData.get(iterator).isEmpty()) {
						row.set(iterator, row.get(row.indexOf(cellData.substring(1, cellData.length()))));
						logger.log(LogStatus.INFO, "Data found with $ " + cellData);
						logger.log(LogStatus.INFO, "Updated Row" + row);
					}
					// Calculate Email from Generated UserId Field for the
					// Row
					else if (autoDataGenrateFieldList.get(iterator).equalsIgnoreCase("email")
							&& (constantsData.get(iterator).startsWith("$"))) {
						row.set(iterator,
								row.get(headers.indexOf(cellData.substring(1, cellData.indexOf('@')))) + constantsData
										.get(iterator).substring(constantsData.get(iterator).indexOf('@'),
												constantsData.get(iterator).length()));

						logger.log(LogStatus.INFO, "Data found with $ " + cellData);
						logger.log(LogStatus.INFO, "Updated Row" + row);
					}
					iterator++;
				}
				rowContents = row.toArray(new String[row.size()]);
				TestDataEngine.CSVData.add(rowContents);
				totalRows--;
				row.clear();
			}

		} else {
			System.err.println("Length Mismatch : Length of headers and DataTypes are not same");
			logger.log(LogStatus.ERROR, "Length Mismatch : Length of headers and DataTypes are not same");
		}
	}
	
		
	
	
	/**
	 * Function to create random testdata file
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file, String[] headers, Class[] data types, int
	 *            no. of rows
	 */
	public static void createRandomTestDataFile(String filePath, String[] headers, Class<?>[] dataTypes,
			int totalRows) {
		try {
			
			CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
			ArrayList<String> row = null;
			String[] rowContents = null;
			if (headers.length == dataTypes.length) {
				List<String[]> contents = new ArrayList<String[]>();
				contents.add(headers);
				while (totalRows > 0) {
					row = new ArrayList<String>();
					for (Class<?> element : dataTypes) {
						if (element == String.class) {
							row.add(Utility.getRandomString(11).toUpperCase());
						} else if (element == Long.class || element == Integer.class) {
							row.add(Long.toString(Utility.getRandomNumber(11)));
						} else if (element == Date.class) {
							row.add(Utility.getDate("MM/dd/yyyy", 0, "days"));
						}
					}
					rowContents = row.toArray(new String[row.size()]);
					contents.add(rowContents);
					totalRows--;
					row.clear();
				}
				if (contents != null) {
					writer.writeAll(contents);
					writer.close();
					contents.clear();
				}
			} else {
				System.err.println("Length Mismatch : Length of headers and DataTypes are not same");
				logger.log(LogStatus.ERROR, "Length Mismatch : Length of headers and DataTypes are not same");
			}
		} catch (Exception e) {
			logger.log(LogStatus.ERROR, e);
		}
	}

	/**
	 * Function to update test data in CSV file
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file, int[] column positions, int no. of lines
	 *            to skip
	 */
	public static void updateTestDataInFile(String filePath, int[] columnPositions, int skipLines) {
		try {
			CSVReader csvReader = new CSVReader(new FileReader(filePath));
			ArrayList<String> randomArray = null;
			List<String[]> updatedData = new ArrayList<String[]>();
			int increment;
			while (skipLines > 0) {
				updatedData.add(csvReader.readNext());
				skipLines--;
			}
			List<String[]> contents = csvReader.readAll();
			csvReader.close();
			CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
			for (String[] row : contents) {
				randomArray = Utility.getArrayOfRandomString(columnPositions.length, 11);
				increment = 0;
				for (int pos : columnPositions) {
					row[pos] = randomArray.get(increment);
					increment++;
				}
				updatedData.add(row);
			}
			writer.writeAll(updatedData);
			writer.close();
		} catch (FileNotFoundException fne) {
			
			logger.log(LogStatus.ERROR, fne);
		} catch (IOException ioe) {
			
			logger.log(LogStatus.ERROR, ioe);
		}
	}

	/**
	 * Function to update test data in CSV file
	 * 
	 * @author Vikas Chauhan
	 * @param String
	 *            - path to the file, int[] column positions, int Row Number The
	 *            UserID duplication is checked and uniqure random/ unused
	 *            userid is returned.
	 */
	public static boolean updateTestDataInFileRowWise(String filePath, int[] columnPositions, int rowNumber,
			int totalRows) {
		int regenerate = 1;
		boolean uidRegenration = false;
		String uId = null;
		try {
			String[] rowN = null;
			int rowPending = rowNumber;
			int i = 1;
			CSVReader csvReader = null;
			List<String[]> updatedData = new ArrayList<String[]>();
			csvReader = new CSVReader(new FileReader(filePath));
			int headerCount = 0;
			while ((rowN = csvReader.readNext()) != null && headerCount < 2) {
				System.out.println(rowN[i]);
				updatedData.add(rowN);
				headerCount++;
			}
			while (i <= totalRows) {
				csvReader = new CSVReader(new FileReader(filePath));
				ArrayList<String> randomArray = null;
				String[] row = null;
				int increment = 0;
				while (rowNumber >= 1) {
					row = csvReader.readNext();
					rowNumber--;
				}
				// updatedData.add(csvReader.readNext());
				// row = csvReader.readNext();
				System.out.println(row[i]);
				if (columnPositions.length <= row.length) {
					randomArray = Utility.getArrayOfRandomString(columnPositions.length,
							AGlobalComponents.RANDOM_DATA_GEN_LEN);
					uId = randomArray.get(0);
					for (int pos : columnPositions) {
						// if column Position is User ID & Generated UserId
						// Already
						// Exists
						// Then Generate Random String and Check
						if (pos == 4) {

							while (regenerate < 10) {

								if (MsSql.doesUserIdExist(uId)) {
									uId = Utility.getRandomString(AGlobalComponents.RANDOM_DATA_GEN_LEN);
									uidRegenration = true;
									regenerate++;
								} else {

									logger.log(LogStatus.INFO,
											"UserID Generated in : " + regenerate + " attempt(s) .. ");
									System.out.println(
											"UserID (" + uId + ") Generated in : " + regenerate + " attempts .. ");
									randomArray.set(0, uId);
									uidRegenration = true;
									row[pos] = randomArray.get(0);
									increment++;
									break;
								}

							}

						} else {

							row[pos] = randomArray.get(increment);
							increment++;
						}

					}
					updatedData.add(row);
				} else {
					System.err.println("Column Postions mentioned doesn't exists in CSV file");
				}
				i++;
				rowNumber = rowPending + 1;
			}
			csvReader.close();
			for (String[] data : updatedData) {
				for (int j = 0; j < data.length; j++) {
					System.out.println(data[j]);
				}
			}
			String currentDir = System.getProperty("user.dir");
			// OPening file with Writer to udpate the data in CSV file
			CSVWriter writer = new CSVWriter(new FileWriter(currentDir + "/" + filePath, false));
			if (!writer.equals(null)) {
				// writer.writeNext(data);
				writer.writeAll(updatedData);
				System.out.println("Updated Test Data in File => " + filePath);
				writer.close();
			} else {
				System.out.println("Could not open file..");
			}

		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.ERROR, fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.ERROR, ioe);
		}

		return uidRegenration;
	}

	/**
	 * Function to update test data in CSV file
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file, int[] column positions, int Row Number
	 */
	public static void updateTestDataInFileRowWise(String filePath, ArrayList<String> rowData, int[] columnPositions,
			int rowNumber) {
		try {
			CSVReader csvReader = new CSVReader(new FileReader(filePath));
			List<String[]> updatedData = new ArrayList<String[]>();
			String[] row = null;
			int increment = 0;
			while (rowNumber > 1) {
				updatedData.add(csvReader.readNext());
				rowNumber--;
			}
			row = csvReader.readNext();
			if (columnPositions.length == rowData.size()) {
				for (int pos : columnPositions) {
					row[pos] = rowData.get(increment);
					increment++;
				}
				updatedData.add(row);
				List<String[]> contents = csvReader.readAll();
				csvReader.close();
				CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
				for (String[] rows : contents) {
					updatedData.add(rows);
				}
				writer.writeAll(updatedData);
				writer.close();
			}
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.ERROR, fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.ERROR, ioe);
		}
	}

	/**
	 * Function to update test data in CSV file with configurations handled by
	 * Properties file with pre-known data to be updated
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file
	 * @param int[]
	 *            - containing positions of column to be updated
	 * @param int
	 *            - number of lines to be skipped
	 * @return ArrayList - Data updated in the file
	 */

	public static ArrayList<String> updateTestDataInFileWithConfig(String filePath, int[] columnPositions,
			int skipLines) {
		
		logger.log(LogStatus.INFO, "Entered inside updateTestDataInFileWithConfig method");
		ArrayList<String> randomDataUpdated = new ArrayList<String>();
		String randomString = null, colPos = "";
		boolean randomDataGenFlag = AGlobalComponents.RANDOM_DATA_GEN_FLAG;
		int randomDataGenLen = AGlobalComponents.RANDOM_DATA_GEN_LEN;
		logger.log(LogStatus.INFO, "RANDOM_DATA_GEN_FLAG configuration is set to : " + randomDataGenFlag);
		logger.log(LogStatus.INFO, "RANDOM_DATA_GEN_LEN configuration is set to : " + randomDataGenLen);
		logger.log(LogStatus.INFO, "Going to update file : " + filePath);
		for (int colPons : columnPositions) {
			colPos += colPons + "  ";
		}
		logger.log(LogStatus.INFO, "Column Positions to be Updated : " + colPos);
		logger.log(LogStatus.INFO, "Number of lines to skip : " + skipLines);
		if (randomDataGenFlag) {
			try {
				CSVReader csvReader = new CSVReader(new FileReader(filePath));
				List<String[]> updatedData = new ArrayList<String[]>();
				while (skipLines > 0) {
					updatedData.add(csvReader.readNext());
					skipLines--;
				}
				List<String[]> contents = csvReader.readAll();
				csvReader.close();
				CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
				for (String[] row : contents) {
					randomString = Utility.getRandomString(randomDataGenLen);
					randomDataUpdated.add(randomString.toUpperCase());
					for (int pos : columnPositions) {
						if (row[pos].contains("$")) {
							row[pos] = row[pos].replace("$", randomString.toUpperCase());
						} else {
							row[pos] = randomString.toUpperCase();
						}
					}
					updatedData.add(row);
				}
				writer.writeAll(updatedData);
				writer.close();
			} catch (FileNotFoundException fne) {
				logger.log(LogStatus.ERROR, fne);
			} catch (IOException ioe) {
				logger.log(LogStatus.ERROR, ioe);
			}
		}
		logger.log(LogStatus.INFO, "Random Data Updated in each row is : " + randomDataUpdated);
		System.out.println("Random Data Updated in each row is : " + randomDataUpdated);
		return randomDataUpdated;
	}

	/**
	 * Function to update test data in CSV file with configurations handled by
	 * Properties file with pre-known data to be updated
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file
	 * @param ArrayList
	 *            - data to be updated in file
	 * @param int[]
	 *            - containing positions of column to be updated
	 * @param int
	 *            - number of lines to be skipped
	 */

	public static void updateTestDataInFileWithConfig(String filePath, ArrayList<String> randomData, int[] colPos,
			int skipLines) {
		if (AGlobalComponents.RANDOM_DATA_GEN_FLAG) {
			
			logger.log(LogStatus.INFO, "Entered inside updateTestDataInFileWithConfig method");
			logger.log(LogStatus.INFO, "Going to update file : " + filePath);
			logger.log(LogStatus.INFO, "Random Data To be updated : " + randomData);
			String colPosStr = "", randomString = "";
			int count = 0;
			for (int colPons : colPos) {
				colPosStr += colPons + "  ";
			}
			logger.log(LogStatus.INFO, "Column Positions to be Updated : " + colPosStr);
			logger.log(LogStatus.INFO, "Number of lines to skip : " + skipLines);
			ArrayList<ArrayList<String>> entireData = TestDataEngine.getCSVData(filePath, skipLines);
			if (entireData.size() == randomData.size()) {
				try {
					CSVReader csvReader = new CSVReader(new FileReader(filePath));
					List<String[]> updatedData = new ArrayList<String[]>();
					while (skipLines > 0) {
						updatedData.add(csvReader.readNext());
						skipLines--;
					}
					List<String[]> contents = csvReader.readAll();
					csvReader.close();
					CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
					for (String[] row : contents) {
						randomString = randomData.get(count);
						count++;
						for (int pos : colPos) {
							if (row[pos].contains("$")) {
								row[pos] = row[pos].replace("$", randomString.toUpperCase());
							} else {
								row[pos] = randomString.toUpperCase();
							}
						}
						updatedData.add(row);
					}
					writer.writeAll(updatedData);
					writer.close();
				} catch (FileNotFoundException fne) {
					logger.log(LogStatus.ERROR, fne);
				} catch (IOException ioe) {
					logger.log(LogStatus.ERROR, ioe);
				}
			} else {
				System.err.println("No. of rows are not equal to Size of Random ArrayList Passed");
				logger.log(LogStatus.ERROR,
						"No. of rows are not equal to Size of Random ArrayList Passed. Data not updated in file.");
			}
		}
	}

	/**
	 * Function to update test data in CSV file as per row header
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file
	 * @param int
	 *            - column number
	 * @param String
	 *            - Header for the row
	 * @param int[]
	 *            - containing positions of column to be updated
	 * @param ArrayList
	 *            - data to be updated in file
	 */

	public static void updateTestDataInFileAsPerRowHeader(String filePath, int columnNumber, String Header,
			int[] columnPos, ArrayList<String> data) {
		
		String pos = "";
		String[] line = null, updatedLine = null;
		for (int col : columnPos) {
			pos += col + " ";
		}
		logger.log(LogStatus.INFO,
				"TestDataEngine: Entered inside method updateTestDataInFileAsPerRowHeader method with Parameters : "
						+ filePath + ", " + columnNumber + ", " + Header + ", " + pos + ", " + data);
		ArrayList<String> rowContents = null;
		logger.log(LogStatus.INFO, "Going to read file : " + filePath);
		ArrayList<String> columns = TestDataEngine.getCSVColumn(filePath, columnNumber);
		logger.log(LogStatus.INFO, "Data present in Column Number : " + columnNumber + " is : " + columns);
		int rowNumber = -1;
		boolean found = false;
		try {
			for (String column : columns) {
				rowNumber++;
				if (column.equalsIgnoreCase(Header)) {
					found = true;
					break;
				}
			}
			if (found == true) {
				if (columnPos.length == data.size()) {
					rowContents = TestDataEngine.getCSVRow(filePath, rowNumber + 1);
					for (int i = 0; i < columnPos.length; i++) {
						rowContents.set(columnPos[i], data.get(i));
					}
					CSVReader reader = new CSVReader(new FileReader(filePath));
					ArrayList<String[]> updatedData = new ArrayList<String[]>();
					while (rowNumber > 0) {
						updatedData.add(reader.readNext());
						rowNumber--;
					}
					updatedLine = new String[rowContents.size()];
					updatedLine = rowContents.toArray(updatedLine);
					updatedData.add(updatedLine);
					reader.readNext();
					while ((line = reader.readNext()) != null) {
						updatedData.add(line);
					}
					reader.close();
					CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
					writer.writeAll(updatedData);
					writer.close();
				} else {
					System.out.println("MISMATCH : Length of Column Positions & Data to be updated");
					logger.log(LogStatus.ERROR, "MISMATCH : Length of Column Positions & Data to be updated");
				}
			} else {
				System.out.println("No Such Data Found");
				logger.log(LogStatus.INFO, "Data with Header - " + Header + " is not present");
			}
		} catch (NullPointerException npe) {
			logger.log(LogStatus.ERROR, npe);
		} catch (ArrayIndexOutOfBoundsException aie) {
			logger.log(LogStatus.ERROR, aie);
		} catch (FileNotFoundException fnfe) {
			logger.log(LogStatus.ERROR, fnfe);
		} catch (IOException ioe) {
			logger.log(LogStatus.ERROR, ioe);
		}
		logger.log(LogStatus.INFO, "TestDataEngine: Exiting method updateTestDataInFileAsPerRowHeader");
	}

	/**
	 * Function to update test data in CSV file with integer value
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file
	 * @param int[]
	 *            - containing positions of column to be updated
	 * @param int
	 *            - number of lines to be skipped
	 * @param int
	 *            - length of random number to be updated
	 * @return ArrayList - random data updated in file
	 */

	public static ArrayList<Long> updateTestDataInFileInteger(String filePath, int[] columnPos, int skipLines,
			int length) {
		
		String pos = "";
		Long randomNumber;
		for (int p : columnPos) {
			pos += p + " ";
		}
		logger.log(LogStatus.INFO,
				"TestDataEngine: Entered inside method updateTestDataInFileInteger with Parameters : " + filePath + ", "
						+ pos + ", " + skipLines);
		ArrayList<Long> randomDataUpdated = new ArrayList<Long>();
		List<String[]> contents = null, updatedData = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(filePath));
			CSVWriter writer = null;
			while (skipLines > 0) {
				updatedData.add(reader.readNext());
				skipLines--;
			}
			if (length > 0) {
				contents = reader.readAll();
				reader.close();
				for (String[] row : contents) {
					randomNumber = Utility.getRandomNumber(length);
					for (int p : columnPos) {
						row[p] = String.valueOf(randomNumber);
					}
					updatedData.add(row);
					randomDataUpdated.add(randomNumber);
				}
				writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
				writer.writeAll(updatedData);
				writer.close();
				System.out.println("TestData file successfully updated!!");
				logger.log(LogStatus.INFO, "TestData file successfully updated!!");
			}
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.ERROR, fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.ERROR, ioe);
		} catch (ArrayIndexOutOfBoundsException ae) {
			logger.log(LogStatus.ERROR, ae);
		} catch (NullPointerException npe) {
			logger.log(LogStatus.ERROR, npe);
		}
		logger.log(LogStatus.INFO,
				"TestDataEngine: Exiting method updateTestDataInFileInteger - Returned : " + randomDataUpdated);
		return randomDataUpdated;
	}

	/**
	 * Function to update test data in CSV file with integer : Pre-known values
	 * to be udpated
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file
	 * @param int[]
	 *            - containing positions of column to be updated
	 * @param ArrayList
	 *            - data to be updated in file
	 * @param int
	 *            - number of lines to be skipped
	 */

	public static void updateTestDataInFileInteger(String filePath, int[] columnPos, ArrayList<Long> data,
			int skipLines) {
		
		String pos = "";
		int count = 0;
		for (int p : columnPos) {
			pos += p + " ";
		}
		logger.log(LogStatus.INFO,
				"TestDataEngine: Entered inside method updateTestDataInFileInteger with Parameters : " + filePath + ", "
						+ pos + ", " + data + ", " + skipLines);
		logger.log(LogStatus.INFO, "TestDataEngine: Reading file " + filePath);
		try {
			List<String[]> updatedData = new ArrayList<String[]>();
			List<String[]> contents = null;
			CSVReader reader = new CSVReader(new FileReader(filePath));
			while (skipLines > 0) {
				updatedData.add(reader.readNext());
				skipLines--;
			}
			contents = reader.readAll();
			reader.close();
			if (contents.size() == data.size()) {
				for (String[] row : contents) {
					for (int p : columnPos) {
						row[p] = String.valueOf(data.get(count));
					}
					count++;
					updatedData.add(row);
				}
				CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
				writer.writeAll(updatedData);
				writer.close();
				System.out.println("TestData file successfully updated!!");
				logger.log(LogStatus.INFO, "TestData file successfully updated!!");
			} else {
				System.err.println(
						"MISMATCH: Number of rows in TestData File is not equal to number of elements to be updated!!");
				logger.log(LogStatus.ERROR,
						"MISMATCH: Number of rows in TestData File is not equal to number of elements to be updated!!");
			}
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.ERROR, fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.ERROR, ioe);
		} catch (NullPointerException npe) {
			logger.log(LogStatus.ERROR, npe);
		} catch (ArrayIndexOutOfBoundsException ae) {
			logger.log(LogStatus.ERROR, ae);
		}
		logger.log(LogStatus.INFO, "TestDataEngine: Exiting method updateTestDataInFileInteger");
	}

	/**
	 * Function to update test data in CSV file in a particular cell location
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file
	 * @param String
	 *            - content to be updated
	 * @param int
	 *            row number
	 * @param int
	 *            - column number
	 * @param int
	 *            - number of lines to skip
	 */

	public static void updateStringInCellOfFile(String filePath, String content, int rowNum, int colNum,
			int skipLines) {
		
		logger.log(LogStatus.INFO,
				"TestDataEngine: Entered inside method updateStringInCellOfFile with Parameters : " + filePath + ", "
						+ content + ", " + rowNum + ", " + colNum + ", " + skipLines);
		logger.log(LogStatus.INFO, "TestDataEngine: Reading file " + filePath);
		try {
			List<String[]> updatedData = new ArrayList<String[]>();
			List<String[]> contents = null;
			String[] row = null;
			CSVReader reader = new CSVReader(new FileReader(filePath));
			while (skipLines > 0) {
				updatedData.add(reader.readNext());
				skipLines--;
			}
			contents = reader.readAll();
			reader.close();
			row = contents.get(rowNum);
			row[colNum] = content;
			contents.set(rowNum, row);
			for (String[] els : contents) {
				updatedData.add(els);
			}
			CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
			writer.writeAll(updatedData);
			writer.close();
			System.out.println("TestData file successfully updated!!");
			logger.log(LogStatus.INFO, "TestData file successfully updated!!");
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.ERROR, fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.ERROR, ioe);
		} catch (NullPointerException npe) {
			logger.log(LogStatus.ERROR, npe);
		} catch (ArrayIndexOutOfBoundsException ae) {
			logger.log(LogStatus.ERROR, ae);
		}
		logger.log(LogStatus.INFO, "TestDataEngine: Exiting method updateTestDataInFileInteger");
	}

	/**
	 * Function to write test data in CSV file with with pre-known contents
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - path to the file
	 * @param ArrayList
	 *            - object containing contents to be written
	 * @param Boolean
	 *            - if true appends the data else over-writes data in file
	 */

	public static boolean writeTestDataInFile(String filePath, ArrayList<ArrayList<String>> contents, boolean appendMode) {
		boolean dataWrittenSuccessfully = false;
		if(Utility.checkIfFileExists(filePath)) {
			if(Utility.checkIfListIsNotNull(contents)) {
		        List<String[]> writeContents = new ArrayList<String[]>();
		        try {
		               for (ArrayList<String> row : contents) {
		                     writeContents.add(row.toArray(new String[row.size()]));
		               }
		               CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, appendMode), ',',
		                            CSVWriter.NO_QUOTE_CHARACTER);
		               csvWriter.writeAll(writeContents);
		               dataWrittenSuccessfully = true;
		               csvWriter.close();
		        } catch (FileNotFoundException fne) {
		               logger.log(LogStatus.ERROR, fne);
		        } catch (IOException ioe) {
		               logger.log(LogStatus.ERROR, ioe);
		        } catch (NullPointerException npe) {
		               logger.log(LogStatus.ERROR, npe);
		        } catch (ArrayIndexOutOfBoundsException ae) {
		               logger.log(LogStatus.ERROR, ae);
		        }
			}
		}		
        return dataWrittenSuccessfully;
	}


	public static void writeTestDataInFile(String filePath, List<String[]> writeContents, boolean appendMode) {
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, appendMode), ',',
					CSVWriter.NO_QUOTE_CHARACTER);
			csvWriter.writeAll(writeContents);
			csvWriter.close();
		} catch (FileNotFoundException fne) {
			logger.log(LogStatus.ERROR, fne);
		} catch (IOException ioe) {
			logger.log(LogStatus.ERROR, ioe);
		} catch (NullPointerException npe) {
			logger.log(LogStatus.ERROR, npe);
		} catch (ArrayIndexOutOfBoundsException ae) {
			logger.log(LogStatus.ERROR, ae);
		}
	}

	/**
	 * Function to create execution files for new package
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - package name
	 */
	public static void createExcutionFilesForNewPackage(String packageName) {
		CSVWriter writer = null;
		String[] testSuitsHeader = { "Test Case Id (M)", "Description (O)", "Complete Classname (M-CS)",
				"Method's Name (M-CS)", "Parameter Types (M-IA)", "Parameter Values (M-IA)", "Priority (M)",
				"GTS Case Ids" };
		try {
			File testSuits = new File("Execution/" + packageName.replace('.', '/') + "/TestCases.csv");
			testSuits.getParentFile().mkdirs();
			if (!testSuits.exists()) {
				writer = new CSVWriter(new FileWriter("Execution/" + packageName.replace('.', '/') + "/TestCases.csv"),
						',', CSVWriter.NO_QUOTE_CHARACTER);
				writer.writeNext(testSuitsHeader);
				writer.close();
			} else {
				System.out.println("TestSuits.csv File already exists for package : " + packageName);
			}
			File testCases = new File("Execution/" + packageName.replace('.', '/') + "/TestSuits.csv");
			String[] testCasesHeader = {};
			if (!testCases.exists()) {
				writer = new CSVWriter(new FileWriter("Execution/" + packageName.replace('.', '/') + "/TestSuits.csv"),
						',', CSVWriter.NO_QUOTE_CHARACTER);
				writer.writeNext(testCasesHeader);
				writer.close();
			} else {
				System.out.println("TestCases.csv File already exists for package : " + packageName);
			}
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
			logger.log(LogStatus.ERROR, ioe);
		} catch (NullPointerException npe) {
			System.err.println(npe.getMessage());
			logger.log(LogStatus.ERROR, npe);
		}
	}

	/**
	 * Function to create folder structure for test data
	 * 
	 * @author Rohit Pant
	 * @param String
	 *            - package name
	 */
	public static void createFolderStructureForTestData(String packageName) {
		
		if (Utility.doesPackageExists(packageName)) {
			try {
				new File("TestData/" + packageName.replace('.', '/')).getCanonicalFile().mkdirs();
			} catch (IOException ioe) {
				System.err.println(ioe.getMessage());
				logger.log(LogStatus.ERROR, ioe);
			}
		} else {
			System.err.println("The package : " + packageName + " does not exists");
			logger.log(LogStatus.ERROR, "The package : " + packageName + " does not exists");
		}
	}

	/**
	 * Author: Garima Babbar, Date: 23rd September.
	 * 
	 * To update the column of a given csv file with a particular value.
	 * 
	 */
	public static void updateCSVColumnWithValue(String filepath, String content, int columnNo, int skipLines,
			String columnName) {
		int endIndex = 0;
		ArrayList<ArrayList<String>> csvData = TestDataEngine.getCSVData(filepath, skipLines);
		if(csvData != null && !csvData.isEmpty()){
			for (; endIndex < csvData.size(); endIndex++) {
				TestDataEngine.updateStringInCellOfFile(filepath, content, endIndex, columnNo, skipLines);
				logger.log(LogStatus.PASS, csvData.get(endIndex).get(0) + " : Updated column <b>" + columnName
						+ "</b> with value <b>" + content + "</b> in the " + filepath + "<br/>");
			}
		}
		else{
			logger.log(LogStatus.INFO, "No CSV data in file : " + filepath);
		}
	}
	
	/** 
	 * To add a column ahead of all the columns in the given CSV file.
	 * 
	 * Author : Garima Babbar, Date : 12th October 2015
	 * 
	 * @modified: Akhilesh Kumar, Date: 8th May, 2018
	 * 
	 **/
	
	public static ArrayList<ArrayList<String>> addColumnToFile(String entityDetailsFilePath, ArrayList<String> columnData){
		ArrayList<ArrayList<String>> newCsvData = new ArrayList<ArrayList<String>>();
		
		ArrayList<ArrayList<String>> csvData = TestDataEngine.getCSVData(entityDetailsFilePath, 0);
		if(Utility.checkIfListIsNotNullAndNotEmpty(csvData)) {
			if(Utility.compareListSize(csvData, columnData)){
				newCsvData = Utility.appendColumnDataAtEnd(csvData, columnData);
			}
		}
		else {
			newCsvData = Utility.convert1DTo2D(columnData);
		}
		TestDataEngine.writeTestDataInFile(entityDetailsFilePath, newCsvData, false);
		return newCsvData;
	}

	
	/** 
	 * To add a column at specified column location in the given CSV file.
	 * 
	 * Author : Garima Babbar, Date : 02th February 2016
	 * 
	 **/
	public static ArrayList<ArrayList<String>> addColumnToFileAtSpecificLocation(String entityDetailsFilePath, ArrayList<String> columnData, int columnLocation){
		ArrayList<ArrayList<String>> newCsvData = new ArrayList<ArrayList<String>>();
		
		ArrayList<ArrayList<String>> csvData = TestDataEngine.getCSVData(entityDetailsFilePath, 0);
		try{
			if(csvData.size() == columnData.size()){
				int index = 0;
				for(ArrayList<String> csvRow : csvData){
					ArrayList<String> newCsvRow = new ArrayList<String>();
					for(int column = 0; column < columnLocation; column++){
						String existingData = csvRow.get(column);
						newCsvRow.add(existingData);
					}
					newCsvRow.add(columnData.get(index));
					for(int column = columnLocation; column < csvRow.size(); column++){
						String existingData = csvRow.get(column);
						newCsvRow.add(existingData);
					}
					newCsvData.add(newCsvRow);
					index++;
				}
				TestDataEngine.writeTestDataInFile(entityDetailsFilePath, newCsvData, false);
			}
		}
		catch(Exception exception){
			logger.log(LogStatus.ERROR, exception.getMessage());
		}
		return newCsvData;
	}
	
	/** 
	 * To remove a column with specific header in the given CSV file.
	 * 
	 * Author : Garima Babbar, Date : 04th April 2016
	 * 
	 **/
	public static ArrayList<ArrayList<String>> removeColumnWithHeader(String entityDetailsFilePath, String header){
		ArrayList<ArrayList<String>> newCsvData = new ArrayList<ArrayList<String>>();
		
		ArrayList<ArrayList<String>> csvData = TestDataEngine.getCSVData(entityDetailsFilePath, 0);
		int totalColumnHeaders = TestDataEngine.getCSVRow(entityDetailsFilePath, 1).size();
		int columnLocationToBeRemoved = TestDataEngine.getColumnNumberOfHeader(entityDetailsFilePath, header);
		try{
			if(columnLocationToBeRemoved > -1 && columnLocationToBeRemoved < totalColumnHeaders ){
				for(ArrayList<String> csvRow : csvData){
					ArrayList<String> newCsvRow = new ArrayList<String>();
					for(int column = 0; column < columnLocationToBeRemoved; column++){
						String existingData = csvRow.get(column);
						newCsvRow.add(existingData);
					}
					for(int column = columnLocationToBeRemoved + 1; column < csvRow.size(); column++){
						String existingData = csvRow.get(column);
						newCsvRow.add(existingData);
					}
					newCsvData.add(newCsvRow);
				}
				TestDataEngine.writeTestDataInFile(entityDetailsFilePath, newCsvData, false);
			}
		}
		catch(Exception exception){
			logger.log(LogStatus.ERROR, exception.getMessage());
		}
		return newCsvData;
	}

	/**
	 * To process headers and constants of template as per the given UDS Mapping properties
	 * file.
	 * 
	 * @param - String - UDS Column Heading Mapping Properties file,
	 *        ArrayList - given headers or columns, ArrayList -
	 *        constants
	 * 
	 * Author : Garima Babbar, Date : 15th October 2015
	 * 
	 **/
	public static ArrayList<ArrayList<String>> processHeadersAndConstantsAsPerUDSMapping(
			String udsColumnMappingsFileName, ArrayList<String> headers,
			ArrayList<String> constants) {
		
		ArrayList<ArrayList<String>> processedHeadersAndValues = new ArrayList<ArrayList<String>>();

		ArrayList<String> processedHeaders = new ArrayList<String>();
		ArrayList<String> processedConstants = constants;

		/**
		 * Properties file containing mappings of given headers with UDS Columns.
		 *  
		 **/
		Properties rUDSHRDBColumnMappingsProperty = Utility.loadPropertyFile(udsColumnMappingsFileName);
		
		/**
		 * Iterate through each header and check if it can be replaced with UDS Column.
		 * 
		 **/
		for (String header : headers) {
			String udsColumnForHeader = rUDSHRDBColumnMappingsProperty
					.getProperty(header);

			/**
			 * If mapping is found for given header, then replace the header with the UDS column.
			 * 
			 **/

			if (udsColumnForHeader != null
					&& !udsColumnForHeader.isEmpty()) {
				logger.log(LogStatus.INFO,
						"Processing Headers -> Replacing header : " + header
								+ " with UDS Column : " + udsColumnForHeader);
				processedHeaders.add(udsColumnForHeader);

				/**
				 * To replace the header with the UDS Column, if it exists in
				 * the Constants value list.
				 **/

				for (String constantValue : constants) {
					if (StringUtils.contains(constantValue, header)) {

						logger.log(LogStatus.INFO, "Replacing header : "
								+ header + " with " + udsColumnForHeader
								+ " in the constant value " + constantValue);

						int index = constants.indexOf(constantValue);
						String processedConstantValue = constantValue.replace(
								header, udsColumnForHeader);
						processedConstants.set(index, processedConstantValue);

						logger.log(LogStatus.INFO,
								"The new processed value is "
										+ processedConstantValue);
					}
				}

			} else {
				processedHeaders.add(header);
			}
		}
		
		processedHeadersAndValues.add(processedHeaders);
		processedHeadersAndValues.add(processedConstants);
		
		return processedHeadersAndValues;
	}
	
	/**
	 * Function to update the complete row of a CSV file.
	 * 
	 * @param String
	 *            - CSV file, ArrayList - list of values to be updated in a row,
	 *            int - row number that should be greater than zero.
	 * 
	 * Author : Garima Babbar, Date : 16th October 2015.
	 * 
	 * **/
	public static void updateRowInCSV(String filepath,
			ArrayList<String> rowValues, int rowNo) {
		
		int[] colPositions = new int[rowValues.size()];
		for (int index = 0; index < rowValues.size(); index++) {
			colPositions[index] = index;
		}
		logger.log(LogStatus.INFO, "Updating row values : " + rowValues);
		TestDataEngine.updateTestDataInFileRowWise(filepath, rowValues,
				colPositions, rowNo);
		
	}
	
	 /**
	  * Function to get latest file from a directory.
	  * @param String - substring of the file name, String - directory of the file.
	  * 
	  * Date : 21st October 2015
	  * 
	  **/
	 public static String getLatestDroppedFileFromDirectory(String substrFileName, String fileDirectory){
		String filePath = "";
		if(Utility.checkIfStringIsNotNullAndNotEmpty(fileDirectory)) {
			if(Utility.checkIfStringIsNotNullAndNotEmpty(substrFileName)) {
				if(Utility.checkIfDirectoryExists(fileDirectory)) {
					logger.log(LogStatus.INFO, "Entered inside method getLatestDroppedFileFromDirectory");
					 
					 File reconFilesPath= new File(fileDirectory);
					 /*
					  * Modified By: Pulkit Mittal
					  * Date : 06/10/2016
					  * 
					  */
					 File[] reconFiles=reconFilesPath.listFiles((FileFilter) FileFileFilter.FILE);
					 Arrays.sort(reconFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
					 displayFiles(reconFiles);
					 File[] caseFiles=new File[reconFiles.length];
					 int i=0,j=0;
					 for (File file : reconFiles) {
						
						       if(file.getName().contains(substrFileName)){
						    	 caseFiles[i++]=file; 
						    }
						 }
					 
					 String[] caseFileNames= new String[i];
					 
					 for(j=0; j<i;j++)
					 {
						 caseFileNames[j]=caseFiles[j].getName();
					 }
				//	 Arrays.sort(caseFileNames);
					 filePath=reconFilesPath+"//"+caseFileNames[0];
				}
				else {
					logger.log(LogStatus.ERROR, "Failed to get the latest file from the directory. Reason : the directory does not exist.");
					logger.log(LogStatus.FAIL, "Failed to get the latest file from the directory. Reason : the directory does not exist.");
				}
			}
			else {
				logger.log(LogStatus.ERROR, "Failed to get the latest file from the directory. Reason : the given substring for the file name is null or empty.");
				logger.log(LogStatus.FAIL, "Failed to get the latest file from the directory. Reason : the given substring for the file name is null or empty.");
			}
		}
		else {
			logger.log(LogStatus.ERROR, "Failed to get the latest file from the directory. Reason : the given directory is null or empty.");
			logger.log(LogStatus.FAIL, "Failed to get the latest file from the directory. Reason : the given directory is null or empty.");
		}
		return filePath;
	 }
	 
	 public static void displayFiles(File[] files) {
			for (File file : files) {
				System.out.printf("File: %-20s Last Modified:" + new Date(file.lastModified()) + "\n", file.getName());
			}
		}
	 
	public static File createDirectory(String directory) {
			File _dir = new File(directory);
			if(!_dir.exists()) {
				_dir.mkdirs();
			}
			return _dir;
	}
		
	public static void copyCSVFileToDirectory(String sourceFile, String destinationDirectory) {
			File _dir = createDirectory(destinationDirectory);
			File _source = new File(sourceFile);
			try {
				FileUtils.copyFileToDirectory(_source, _dir);
			} 
			catch(IOException ioe) {
				logger.log(LogStatus.ERROR, ioe);
			}
	}
	
	/**
	 * Add CSV file to report as attachment.
	 * 
	 * @author garima.babbar, Date : 07th April 2016.
	 * 
	 **/
	
	public static void addCSVToReport(String filePath){
		String fileName = "";
		int fileSeperatorLastIndex = filePath.lastIndexOf("/");
		fileName = fileSeperatorLastIndex >= 0?filePath.substring(fileSeperatorLastIndex + 1) : filePath;
		TestDataEngine.copyCSVFileToDirectory(filePath, "Report/CSVFiles");
		
//		Report.addAttachmentToReport("CSVFiles", fileName);
	}
	
	/**
	 * To compile test data template. This template file has only headers row and data rows.
	 * 
	 * Author : Garima Babbar, Date : 6th May 2016.
	 * 
	 **/
	
	public static boolean compileTwoRowDataTemplate(String templatePath, String dataSourceFilePath, String dropFilePath) {
		boolean compilationSuccessfully = false;
		if(Utility.checkIfStringIsNotNullAndNotEmpty(templatePath)) {
			if(Utility.checkIfStringIsNotNullAndNotEmpty(dataSourceFilePath)) {
				if(Utility.checkIfStringIsNotNullAndNotEmpty(dropFilePath)) {
					if(Utility.checkIfFileExists(templatePath)) {
						ArrayList<String> headers = TestDataEngine.getCSVRow(templatePath, 1);
						ArrayList<ArrayList<String>> csvData = TestDataEngine.getCSVData(templatePath, 0);
						
						int rowIndex = 2;
						TestDataEngine.CSVData.add(headers.toArray(new String[headers.size()]));
						while (rowIndex <= csvData.size()) {
							ArrayList<String> templateDataRow = TestDataEngine.getCSVRow(templatePath, rowIndex);
							ArrayList<String> fieldLengths = getLengthsFromTemplate(templateDataRow, 0);
							ArrayList<String> dataTypes = getDataTypesFromTemplate(templateDataRow, 0);
							ArrayList<String> constants = getConstantsFromTemplate(templateDataRow, 0);
							ArrayList<String> autoDataGenrateFieldList = getAutoGenrateFields(templateDataRow, 0);
							ArrayList<String> udsCols = getUDSColsFromTemplate(templateDataRow, 0);
							TestDataInterface.setUdsColumns(udsCols);
							
							createDataFilefromTemplate(dropFilePath, headers, dataTypes, autoDataGenrateFieldList,
												fieldLengths, constants, 1);
							templateDataRow.clear();
							rowIndex++;

						}
						boolean dataWrittenSuccessfully = TestDataEngine.writeDataToCSV(dropFilePath);
						if(dataWrittenSuccessfully) {
							compilationSuccessfully = true;
						}
					}
				}
			}
		}
		return compilationSuccessfully;
	}
	
	/**
	 * Merged from Google branch. Date : 25th May 2017.
	 * 
	 * Compile template file available in a given directory.
	 * 
	 * Author : Garima Babbar, Date : 30th May 2016.
	 * 
	 **/
	
	public static void compileTwoRowTestDataInGivenDirectory(String directory){
		if(Utility.checkIfStringIsNotNullAndNotEmpty(directory)) {
			if(Utility.checkIfDirectoryExists(directory)) {
				try{
					List<String> templateFiles = getAllTemplateFiles("Template", directory);
					for(String templateFile : templateFiles ) {
						//String dataFile = CommonFunctions.getFileDirectory(templateFile) + CommonFunctions.getFileName(templateFile).replace("Template","");
						String dataFile = Utility.getFileName(templateFile).replace("Template","");
						TestDataInterface.compileTwoRowDataTemplate(templateFile, dataFile, 2);
					}
				}
				catch(Exception exception){
					logger.log(LogStatus.INFO, "There is no template file to compile in this directory " + directory);
				}
			}
		}
	}
	
	/**
	 * Merged it from Google branch.
	 * 
	 * Returns ArrayList of Template File Path in given Directory
	 * @author shivam.thakur
	 * @param substrFileName
	 * @param fileDirectory
	 * @return 
	 * 
	 */
	@SuppressWarnings("null")
	public static List<String> getAllTemplateFiles(String substrFileName, String fileDirectory) {
		
		logger.log(LogStatus.INFO, "Entered inside method getAllTemplateFiles ");
		
		//int counter = 0;
		File filePath = new File(fileDirectory);
		File[] allFiles=filePath.listFiles();
		List<String> templateFilesPath = new ArrayList<String>();
		String templateFileName = null;
		for(File eachFile : allFiles) {
			if(eachFile.getName().contains(substrFileName)) {
				templateFileName = eachFile.getPath();
				templateFilesPath.add(templateFileName);
			}
		}
		
		return templateFilesPath;
	}
	
	/**
	 * Merged it from Google branch.
	 * 
	 * rowIndex - Row number form which your test data starts(Next to Headers or Data Types).
	 * @author shivam.thakur
	 * @param templatePath
	 * @param dataSourceFilePath
	 * @param dropFilePath
	 * @param rowIndex
	 */
	public static void compileTwoRowDataTemplate(String templatePath, String dataSourceFilePath, String dropFilePath, int rowIndex) {

		File templateFile = new File(templatePath);
		File dataSourceFile = new File(dataSourceFilePath);
		if (!templateFile.exists()) {
			System.out.println("The Template File Does not Exist at Path : " + templatePath);
		}
		if (!dataSourceFile.exists()) {

			System.out.println("The Data Source file does not exist at path : " + dataSourceFilePath);
		}
		
		ArrayList<ArrayList<String>> csvData = TestDataEngine.getCSVData(templatePath, 0);
		
		ArrayList<String> headers = TestDataEngine.getCSVRow(templatePath, 1);
		TestDataEngine.CSVData.add(headers.toArray(new String[headers.size()]));

		for(int count = 2; count < rowIndex; count++){
			ArrayList<String> metadata = TestDataEngine.getCSVRow(templatePath, count);
			TestDataEngine.CSVData.add(metadata.toArray(new String[metadata.size()]));
		}
		
		while (rowIndex <= csvData.size()) {
			ArrayList<String> templateDataRow = TestDataEngine.getCSVRow(templatePath, rowIndex);
			ArrayList<String> fieldLengths = getLengthsFromTemplate(templateDataRow, 0);
			ArrayList<String> dataTypes = getDataTypesFromTemplate(templateDataRow, 0);
			ArrayList<String> constants = getConstantsFromTemplate(templateDataRow, 0);
			ArrayList<String> autoDataGenrateFieldList = getAutoGenrateFields(templateDataRow, 0);
			ArrayList<String> udsCols = getUDSColsFromTemplate(templateDataRow, 0);
			TestDataInterface.setUdsColumns(udsCols);
			
			createDataFilefromTemplate(dropFilePath, headers, dataTypes, autoDataGenrateFieldList,
								fieldLengths, constants, 1);
			templateDataRow.clear();
			rowIndex++;

		}
		TestDataEngine.writeDataToCSV(dropFilePath);
	}

	/**
	 * Generates a file name with given file path and given sub-file name. 
	 * 
	 * Author : Garima Babbar, Date : 23rd June 2017.
	 * 
	 **/
	
	public static String generateGivenFileName(
			String filePath, String subFileName) {
		String fullFilePath = "";
		if(filePath != null && !filePath.isEmpty()){
			if(subFileName != null && !subFileName.isEmpty()){
				String fileName = subFileName + ".csv";
				fullFilePath = filePath + "/" + fileName ;
			}
			else{
				System.out.println("Not able to generate file name. Reason - Input parameter subFileName is not provided. It is either empty or null.");
				logger.log(LogStatus.FAIL, "Not able to generate file name. Reason - Input parameter subFileName is not provided. It is either empty or null.");
			}
		}
		else{
			System.out.println("Not able to generate file name. Reason - Input parameter filePath is not provided. It is either empty or null.");
			logger.log(LogStatus.FAIL, "Not able to generate file name. Reason - Input parameter filePath is not provided. It is either empty or null.");
		}
		return fullFilePath;
	}
	
	/**
	 * Generates a file name with given file path and given sub-file name. 
	 * 
	 * Author : Garima Babbar, Date : 23rd June 2017.
	 * 
	 **/
	
	public static String generateFileNameWithCurrentTimestamp(
			String filePath, String subFileName) {
		String fullFilePath = "";
		if(filePath != null && !filePath.isEmpty()){
			if(subFileName != null && !subFileName.isEmpty()){
				String currentTimestamp = Utility.getDate("MMM_dd_HH_mm_ss", 0, "days");
				String fileName = subFileName + currentTimestamp + ".csv";
				fullFilePath = filePath + fileName ;
			}
			else{
				System.out.println( "Not able to generate file name. Reason - Input parameter subFileName is not provided. It is either empty or null.");
				logger.log(LogStatus.FAIL, "Not able to generate file name. Reason - Input parameter subFileName is not provided. It is either empty or null.");
			}
		}
		else{
			System.out.println("Not able to generate file name. Reason - Input parameter filePath is not provided. It is either empty or null.");
			logger.log(LogStatus.FAIL, "Not able to generate file name. Reason - Input parameter filePath is not provided. It is either empty or null.");
		}
		return fullFilePath;
	}
	
	/**
	 * Get specific 
	 * 
	 * Author : Vanshaj Dhar.
	 * **/
	
	public static String getCSVCellValue(String filePath, String columnHeader, int rowNumber){
		String userId = "";
		if(Utility.checkIfStringIsNotNullAndNotEmpty(filePath)){
			if(Utility.checkIfStringIsNotNullAndNotEmpty(columnHeader)){
				try{
					ArrayList<String> Columns = getCSVColumnPerHeader(filePath, columnHeader);
					userId = Columns.get(rowNumber-1);
				}
				catch(Exception exception){
					System.out.println( exception.getMessage());
					logger.log(LogStatus.FAIL, exception.getMessage());
				}
			}
		}
		return userId;
	}
	
	/**
	 * Function to get CSV column as per header
	 * 
	 * Author : Rahul Bali.
	 
	 */

	public static ArrayList<String> getCSVColumn(String filepath ,String ColumnHeader, int SkipLines )
	{
		int ColumnIndex = TestDataEngine.getColumnNumberOfHeader(filepath,ColumnHeader);
        ArrayList<String> List = TestDataEngine.getCSVColumn(filepath, ColumnIndex+1, SkipLines);
        
        return List;
		
	}
	
	/**
	 * Updates the given value for the given cell.
	 * 
	 *   Author : Garima Babbar, Date : 07th Feb 2018.
	 *   
	 *   Untested.
	 * 
	 **/
	
	public static boolean updateCSVCellValue(String fileName, String columnHeader, int rowNum, String newCellValue) {
		boolean cellUpdated = false;
		if(Utility.checkIfStringIsNotNullAndNotEmpty(fileName) && Utility.checkIfFileExists(fileName)) {
			if(Utility.checkIfStringIsNotNullAndNotEmpty(columnHeader)) {
				if(Utility.checkIfStringIsNotNullAndNotEmpty(newCellValue) && rowNum > 0) {
					try {
						List<String[]> updatedData = new ArrayList<String[]>();
						List<String[]> contents = null;
						String[] row = null;
						CSVReader reader = new CSVReader(new FileReader(fileName));
						int colNum = TestDataEngine.getColumnNumberOfHeader(fileName, columnHeader);
						contents = reader.readAll();
						reader.close();
						row = contents.get(rowNum);
						row[colNum] = newCellValue;
						contents.set(rowNum, row);
						for (String[] els : contents) {
							updatedData.add(els);
						}
						CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',', CSVWriter.NO_QUOTE_CHARACTER);
						writer.writeAll(updatedData);
						cellUpdated = true;
						writer.close();
						System.out.println("TestData file successfully updated!!");
						logger.log(LogStatus.INFO, "TestData file successfully updated!!");
					} catch (FileNotFoundException fne) {
						System.out.println("Error :" + fne);
						logger.log(LogStatus.ERROR, fne);
					} catch (IOException ioe) {
						System.out.println("Error :" + ioe);
						logger.log(LogStatus.ERROR, ioe);
					} catch (NullPointerException npe) {
						System.out.println("Error :" + npe);
						logger.log(LogStatus.ERROR, npe);
					} catch (ArrayIndexOutOfBoundsException ae) {
						System.out.println("Error :" + ae);
						logger.log(LogStatus.ERROR, ae);
					}
				}
			}
		}
		return cellUpdated;
	}	
	
	/**
	 * Author : Akhilesh Kumar, Date : 05th May 2018.
	 * 
	 **/
//	public static boolean isGivenTemplateConstant(String testCaseDataDirectoryLocation, String templateValue) {
//		boolean isTemplateValueConstant = true;
//		if(Utility.checkIfStringIsNotNullAndNotEmpty(templateValue)) {
//			if(templateValue.contains("|")) {
//				isTemplateValueConstant = false;
//			}
//			else {
//				String attributeValue = AttributesTestDataGenerator.generateAttributeValue(testCaseDataDirectoryLocation, templateValue);
//				if(Utility.checkIfStringIsNotNullAndNotEmpty(attributeValue)) {
//					isTemplateValueConstant = false;
//				}
//			}
//		}
//		return isTemplateValueConstant;
//	}
//	
	
	/**@author akhilesh.kumar
	Added to append time as 0000:00:00 as default to any given date **/
	
	public static void appendDefaultTimeToDate(String dataFile, String attribute) {
		if(Utility.checkIfStringIsNotNullAndNotEmpty(dataFile) && Utility.checkIfFileExists(dataFile)) {
			if(Utility.checkIfStringIsNotNullAndNotEmpty(attribute)) {
				String dateValue = TestDataEngine.getCSVCellValue(dataFile, attribute, 1);
				String updatedDateValue = dateValue + " 0000:00:00";
				TestDataEngine.updateCSVCellValue(dataFile, attribute, 1, updatedDateValue);
			}
		}
	}
	
	
	/**@author akhilesh.kumar
	 * 
	 * Added to convert any given date format say YYYY/MM/DD to YYYY-MM-DD etc.
	 * 
	 * @param dataFile
	 * @param attribute
	 * @param requestedFormat
	 */
	
//	public static void convertDateFormatToGivenFormat(String dataFile, String attribute, String requestedFormat) {
//		String newDateValue = "";
//		if(Utility.checkIfStringIsNotNullAndNotEmpty(dataFile) && Utility.checkIfFileExists(dataFile)) {
//			if(Utility.checkIfStringIsNotNullAndNotEmpty(attribute)) {
//				if(Utility.checkIfStringIsNotNullAndNotEmpty(requestedFormat)) {
//					String givenDate = TestDataEngine.getCSVCellValue(dataFile, "ValidFrom", 1);
//					String dateComponents[] = new String[2];
//					if(givenDate.contains("/")){
//						dateComponents = givenDate.split("/");
//					}
//					int dayIdx = -1, monthIdx = -1, yearIdx = -1;
//					if("YYYY-MM-DD".equalsIgnoreCase(requestedFormat)) {
//						String applicationDateFormat = DBValidations.returnApplicationDateFormat();
//						String appDateFormatComps[] = new String[2];
//						appDateFormatComps = applicationDateFormat.split("/");
//						ArrayList<String> componentPosition = new ArrayList<String>(Arrays.asList(appDateFormatComps));
//						if(Utility.checkIfListContains(componentPosition, "DD") || Utility.checkIfListContains(componentPosition, "dd")) {
//							dayIdx = Utility.getIndex(componentPosition, "DD");
//							if(dayIdx == -1) {
//								dayIdx = Utility.getIndex(componentPosition, "dd");
//							}
//						}
//						if(Utility.checkIfListContains(componentPosition, "MM") || Utility.checkIfListContains(componentPosition, "mm")) {
//							monthIdx = Utility.getIndex(componentPosition, "MM");
//							if(monthIdx == -1) {
//								monthIdx = Utility.getIndex(componentPosition, "mm");
//							}
//						}
//						if(Utility.checkIfListContains(componentPosition, "YYYY") || Utility.checkIfListContains(componentPosition, "yyyy")) {
//							yearIdx = Utility.getIndex(componentPosition, "YYYY");
//							if(yearIdx == -1) {
//								yearIdx = Utility.getIndex(componentPosition, "yyyy");
//							}
//						}
//						if(yearIdx != -1 && monthIdx != -1 && dayIdx != -1) {
//							newDateValue = dateComponents[yearIdx] + "-" + dateComponents[monthIdx] + "-" + dateComponents[dayIdx];
//						}	
//					}		
//				TestDataEngine.updateCSVCellValue(dataFile, "ValidFrom", 1, newDateValue);		
//				}
//			}
//		}		
//	}
	
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
	
	public static void writeStringToFile(String fileLocation, String text) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(fileLocation);
			pw.println(text);
	        pw.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
}	