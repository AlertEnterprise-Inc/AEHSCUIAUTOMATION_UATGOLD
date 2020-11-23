package CommonClassReusables;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class isAlertPresent extends BrowserSelection {
	
	
	/**
	* <h1>checkAlertPresent</h1>
	* This is a Method to Check Presence of Alert Popup
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-31-2020
	* @param   	none
	* @return  	boolean
	*/
	
	public static boolean checkAlertPresent() {

		boolean presentFlag = false;

		try {

			driver.switchTo().alert();

			presentFlag = true;
		} catch (NoAlertPresentException e) {

//			ExtentReport.testStep("Caught exception in " + Thread.currentThread().getStackTrace()[1].getMethodName()
//					+ "Due to: " + e.getMessage());
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Not able to switch : " + e);

		}
		return presentFlag;
	}

	
	/**
	* <h1>acceptAlert</h1>
	* This is a Method to Check Presence of Alert Popup and Consume it
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-31-2020
	* @param   	none
	* @return  	boolean
	*/
	
	public static boolean acceptAlert() throws InterruptedException {
	boolean presentFlag = false;
		Thread.sleep(2000);
		try {
			// Check the presence of alert
			Alert alert = driver.switchTo().alert();
			// Alert present; set the flag
			presentFlag = true;
			// if present consume the alert
			alert.accept();
			System.out.println("Alert Accepted");
		} catch (NoAlertPresentException e) {

//			ExtentReport.testStep("Caught exception in " + Thread.currentThread().getStackTrace()[1].getMethodName()
//					+ "Due to: " + e.getMessage());
			throw new java.lang.RuntimeException("RUNTIME_ERROR : :  " +"--------------->"+ e);
			
		}
		Thread.sleep(5000);
		return presentFlag;
	}

	
	/**
	* <h1>rejectAlert</h1>
	* This is a Method to Check Presence of Alert Popup and Reject it
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-31-2020
	* @param   	none
	* @return  	boolean
	*/
	
	public static boolean rejectAlert() throws InterruptedException, IOException {

		boolean presentFlag = false;
		Thread.sleep(2000);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		FileUtils.copyFile(scrFile, new File("Results\\modaldialogue.jpeg"));
		System.out.println("Screenshot has been generated for modaldialogue");
		System.out.println("Screenshot taken");
		try {

			Alert alert = driver.switchTo().alert();

			presentFlag = true;

			alert.dismiss();
			System.out.println("Alert Rejected");
		} catch (NoAlertPresentException e) {
			// Alert not present
//			ExtentReport.testStep("Caught exception in " + Thread.currentThread().getStackTrace()[1].getMethodName()
//					+ "Due to: " + e.getMessage());
			throw new java.lang.RuntimeException("RUNTIME_ERROR : :  " +"--------------->"+ e);
		}
		Thread.sleep(5000);
		return presentFlag;
	}

}
