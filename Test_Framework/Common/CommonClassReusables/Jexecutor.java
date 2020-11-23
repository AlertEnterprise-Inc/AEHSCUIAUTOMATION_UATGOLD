package CommonClassReusables;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Jexecutor extends BrowserSelection  {
	
	
	/**
	* <h1>highlightElement</h1>
	* This is a Method to Highlight a Web Element
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-17-2020
	* @param   	WebElement element
	* @return  	none
	*/
	
	public static void highlightElement(WebElement element) throws InterruptedException {
		 JavascriptExecutor jse=(JavascriptExecutor) driver;
		try {
			if (AGlobalComponents.jsExecutor) {
			//	JavascriptExecutor js = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"color: brown; border: 4px solid black;");
				Thread.sleep(100);
			}
		} catch (Exception e) {
		//	ExtentReport.testStep("Caught exception in " + Thread.currentThread().getStackTrace()[1].getMethodName() + "Due to: "+e.getMessage());
//			 e.getMessage();
		}
	}

	/**
	* <h1>highlightElement</h1>
	* This is a Method to Highlight a Web Element
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-17-2020
	* @param   	WebElement element
	* @return  	none
	*/
	
	public static void highlightAngularElement(By element) throws InterruptedException {
		 JavascriptExecutor jse=(JavascriptExecutor) driver;
		try {
			if (AGlobalComponents.jsExecutor) {
			//	JavascriptExecutor js = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"color: brown; border: 4px solid black;");
				Thread.sleep(100);
			}
		} catch (Exception e) {
		//	ExtentReport.testStep("Caught exception in " + Thread.currentThread().getStackTrace()[1].getMethodName() + "Due to: "+e.getMessage());
//			 e.getMessage();
		}
	}
	
	
	/**
	* <h1>hidePopup</h1>
	* This is a Method to Hide a Popup
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-17-2020
	* @param   	WebElement element
	* @return  	none
	*/
	
	public static void hidePopup(WebElement element) throws InterruptedException {
		 JavascriptExecutor jse=(JavascriptExecutor) driver;
		// js.executeScript("arguments[0].setAttribute(style.visibility='visible';
		// ");
		try {
			if (AGlobalComponents.jsExecutor) {
				jse.executeScript("arguments[0].style.visibility='hidden';", element);
				// ((JavascriptExecutor)
				// driver).executeScript("arguments[0].style.display='none';",element);
			}
		} catch (Exception e) {
			//ExtentReport.testStep("Caught exception in " + Thread.currentThread().getStackTrace()[1].getMethodName() + "Due to: "+e.getMessage());
	        e.getMessage();
		}
	}

	
	/**
	* <h1>showPopup</h1>
	* This is a Method to Show a Popup
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-17-2020
	* @param   	WebElement element
	* @return  	none
	*/
	
	public static void showPopup(WebElement element) throws InterruptedException {
		 JavascriptExecutor jse=(JavascriptExecutor) driver;
		// js.executeScript("arguments[0].setAttribute(style.visibility='visible';
		// ");
		try {
			if (AGlobalComponents.jsExecutor) {
				jse.executeScript("arguments[0].style.visibility='visible';", element);
				// ((JavascriptExecutor)
				// driver).executeScript("arguments[0].style.display='none';",element);
			}
		} catch (Exception e) {
			//ExtentReport.testStep("Caught exception in " + Thread.currentThread().getStackTrace()[1].getMethodName() + "Due to: "+e.getMessage());
			 e.getMessage();
		}
	}
	
	
	/**
	* <h1>setAttValue</h1>
	* This is a Method to Set Attribute Value for a Web Element
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-1-2020
	* @param   	WebElement element,String AtributeName, String value
	* @return  	none
	*/
	
	public static void setAttValue(WebElement element,String AtributeName, String value) throws InterruptedException {
		 JavascriptExecutor jse=(JavascriptExecutor) driver;
		// js.executeScript("arguments[0].setAttribute(style.visibility='visible';
		// ");
		try {
			
				jse.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",element,AtributeName,value);
			}
		 catch (Exception e) {
		//	ExtentReport.testStep("Caught exception in " + Thread.currentThread().getStackTrace()[1].getMethodName() + "Due to: "+e.getMessage());
		      e.getMessage();
		 }
	}
	
	
}
