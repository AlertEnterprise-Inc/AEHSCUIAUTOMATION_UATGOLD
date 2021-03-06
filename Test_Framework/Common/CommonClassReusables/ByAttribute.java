
package CommonClassReusables;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;

import ObjectRepository.HomeObjects;

public class ByAttribute extends BrowserSelection  {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	static WebElement element;
	static String value = "";
	static Select select = null;

	
	/**
	* <h1>click</h1>
	* This is a Common Function to Click on any Web Element 
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-18-2020
	* @param   	String attribute, String attributevalue, String actionInfo
	* @return  	none
	*/
	
	public static void click(String attribute, String attributevalue, String actionInfo) {
		 
		try {
//			dynamicwait.WAitUntilPageLoad();
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = driver.findElement(By.xpath(attributevalue));
	                break;
	            case "id":
	            	element = driver.findElement(By.id(attributevalue));
	                break;
	            case "name":
	            	element = driver.findElement(By.name(attributevalue));
	                break;
	            case "linktext":
	            	element = driver.findElement(By.linkText(attributevalue));
	                break;
	            case "cssselector":
	            	element = driver.findElement(By.cssSelector(attributevalue));
	                break;
	            case "classname":
	            	element = driver.findElement(By.className(attributevalue));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
        }

		//	Jexecutor.highlightElement(element);
			try{
			element.click();
		
			}catch(ElementNotVisibleException e1){
				    JavascriptExecutor jss= ((JavascriptExecutor)driver);
				    jss.executeScript("arguments[0].scrollIntoView(true);", element);
				    Thread.sleep(1000);
					jss.executeScript("arguments[0].click();", element);
				
			}catch(StaleElementReferenceException e2){
				int count=0;
				while(count <4){
				if(element.isDisplayed()){
					
					element.click();
					break;
				}
				Thread.sleep(1500);
				count++;
				}
			}catch(WebDriverException e3){
			
				    JavascriptExecutor jss= ((JavascriptExecutor)driver);
				    jss.executeScript("arguments[0].scrollIntoView(true);", element);
				    Thread.sleep(1000);
					jss.executeScript("arguments[0].click();", element);
				}
		
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo );
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);

		}

	}
	
	
	/**
	* <h1>getText</h1>
	* This is a Common Function to Get Text from any Web Element
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-18-2020
	* @param   	String attribute, String attributevalue, String actionInfo
	* @return  	textvalue
	*/
	
	public static String getText(String attribute, String attributevalue, String actionInfo) {
		String textvalue = null;
		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = driver.findElement(By.xpath(attributevalue));
	                break;
	            case "id":
	            	element = driver.findElement(By.id(attributevalue));
	                break;
	            case "name":
	            	element = driver.findElement(By.name(attributevalue));
	                break;
	            case "linktext":
	            	element = driver.findElement(By.linkText(attributevalue));
	                break;
	            case "cssselector":
	            	element = driver.findElement(By.cssSelector(attributevalue));
	                break;
	            case "classname":
	            	element = driver.findElement(By.className(attributevalue));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
    }
			Jexecutor.highlightElement(element);
			try{
			textvalue = element.getText();
			}catch(ElementNotVisibleException e1){
				
				JavascriptExecutor jss=((JavascriptExecutor)driver);
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
			    textvalue = element.getText();
					
				}catch(StaleElementReferenceException e2){
					int count=0;
					while(count <4){
					if(element.isDisplayed()){
						
						textvalue = element.getText();
						break;
					}
					Thread.sleep(1500);
					count++;
					}
				}catch(WebDriverException e3){

					    JavascriptExecutor jss=((JavascriptExecutor)driver);
					    jss.executeScript("arguments[0].scrollIntoView(true);", element);
					    Thread.sleep(1000);
					    textvalue = element.getText();
					}
			
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo );
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);
		}
		return textvalue;

	}
	
	
	/**
	* <h1>getAttributeValue</h1>
	* This is a Common Function to fetch Attribute Value from any Web Element
	* @author  	Jiten Khanna
	* @modified
	* @version 	1.0
	* @since  	03-18-2020
	* @param   	String attribute, String attributevalue, String attributeName, String actionInfo
	* @return  	value
	*/
	
	public static String getAttributeValue(String attribute, String attributevalue, String attributeName, String actionInfo) {

		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = driver.findElement(By.xpath(attributevalue));
	                break;
	            case "id":
	            	element = driver.findElement(By.id(attributevalue));
	                break;
	            case "name":
	            	element = driver.findElement(By.name(attributevalue));
	                break;
	            case "linktext":
	            	element = driver.findElement(By.linkText(attributevalue));
	                break;
	            case "cssselector":
	            	element = driver.findElement(By.cssSelector(attributevalue));
	                break;
	            case "classname":
	            	element = driver.findElement(By.className(attributevalue));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
    }
			Jexecutor.highlightElement(element);
			try{
			value = element.getAttribute(attributeName);
			}catch(StaleElementReferenceException e2){
				if(element.isDisplayed()){
					
					value = element.getAttribute(attributeName);
				}}
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo);
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);
		}
		return value;

	}
	
	
	/**
	* <h1>setText</h1>
	* This is a Common Function to Set Text to any Web Element
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-18-2020
	* @param   	String attribute, String attributevalue, String text, String actionInfo
	* @return  	none
	*/
	
	public static void setText(String attribute, String attributevalue, String text, String actionInfo) {
		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = driver.findElement(By.xpath(attributevalue));
	                break;
	            case "id":
	            	element = driver.findElement(By.id(attributevalue));
	                break;
	            case "name":
	            	element = driver.findElement(By.name(attributevalue));
	                break;
	            case "linktext":
	            	element = driver.findElement(By.linkText(attributevalue));
	                break;
	            case "cssselector":
	            	element = driver.findElement(By.cssSelector(attributevalue));
	                break;
	            case "classname":
	            	element = driver.findElement(By.className(attributevalue));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
			}
			
	//		Jexecutor.highlightElement(element);
			Thread.sleep(500);
	//	element.click();
		element.clear();			
			try{
			element.sendKeys(text);
			}catch(ElementNotVisibleException e1){
				
				    JavascriptExecutor jss=((JavascriptExecutor)driver);
				    jss.executeScript("arguments[0].scrollIntoView(true);", element);
				    Thread.sleep(1000);
				    element.sendKeys(text);
				
				}catch(StaleElementReferenceException e2){
					int count=0;
					while(count <4){
					if(element.isDisplayed()){
						
						element.sendKeys(text);
						break;
					}
					Thread.sleep(1500);
					count++;
					}
				}catch(WebDriverException e3){

					    JavascriptExecutor jss=((JavascriptExecutor)driver);
					    jss.executeScript("arguments[0].scrollIntoView(true);", element);
					    Thread.sleep(1000);
					    element.sendKeys(text);
					}
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: "+ actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo);
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Not able to  : " + actionInfo +"--------------->"+ e);
		}
	}
	
	
	/**
	* <h1>clearSetText</h1>
	* This is a Common Function to Clear the contents and then Set Text to any Web Element
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-18-2020
	* @param   	String attribute, String attributevalue, String text,String actionInfo
	* @return  	none
	*/

	public static void clearSetText(String attribute, String attributevalue, String text,String actionInfo) {
		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = driver.findElement(By.xpath(attributevalue));
	                break;
	            case "id":
	            	element = driver.findElement(By.id(attributevalue));
	                break;
	            case "name":
	            	element = driver.findElement(By.name(attributevalue));
	                break;
	            case "linktext":
	            	element = driver.findElement(By.linkText(attributevalue));
	                break;
	            case "cssselector":
	            	element = driver.findElement(By.cssSelector(attributevalue));
	                break;
	            case "classname":
	            	element = driver.findElement(By.className(attributevalue));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
			}
			
			Jexecutor.highlightElement(element);
			try{
			element.clear();
		    element.sendKeys(text);
			}catch(ElementNotVisibleException e1){
				 
				   JavascriptExecutor jss=((JavascriptExecutor)driver);
				    jss.executeScript("arguments[0].scrollIntoView(true);", element);
				    Thread.sleep(1000);
				    element.clear();
				    element.sendKeys(text);
					
				}catch(StaleElementReferenceException e2){
					int count=0;
					while(count <4){
					if(element.isDisplayed()){
						element.clear();
						element.sendKeys(text);
						break;
					}
					Thread.sleep(1500);
					count++;
					}
				}catch(WebDriverException e3){

					    JavascriptExecutor jss=((JavascriptExecutor)driver);
					    jss.executeScript("arguments[0].scrollIntoView(true);", element);
					    Thread.sleep(1000);
					    element.clear();
					    element.sendKeys(text);
					}
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: "+ actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo);
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Not able to  : " + actionInfo +"--------------->"+ e);
		}

	}
	
	
	/**
	* <h1>getWebObject</h1>
	* This is a Common Function to Check Existence of any Web Element
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-18-2020
	* @param   	String attribute, String attributevalue
	* @return  	element
	*/

	public static WebElement getWebObject(String attribute, String attributevalue) {
		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = driver.findElement(By.xpath(attributevalue));
	                break;
	            case "id":
	            	element = driver.findElement(By.id(attributevalue));
	                break;
	            case "name":
	            	element = driver.findElement(By.name(attributevalue));
	                break;
	            case "linktext":
	            	element = driver.findElement(By.linkText(attributevalue));
	                break;
	            case "cssselector":
	            	element = driver.findElement(By.cssSelector(attributevalue));
	                break;
	            case "classname":
	            	element = driver.findElement(By.className(attributevalue));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found ");
	            	throw new UnsupportedOperationException();
			}
			
			Jexecutor.highlightElement(element);
			return element;
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : ");
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Not able to  : " + e);
			// return null;
		}

	}
	
	
	/**
	* <h1>selectIndex</h1>
	* This is a Common Function to Select Value from any Dropdown based on Index
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-18-2020
	* @param   	String attribute, String attributevalue, int index, String actionInfo
	* @return  	none
	*/

	public static void selectIndex(String attribute, String attributevalue, int index, String actionInfo) {
		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	select = new Select(driver.findElement(By.xpath(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.xpath(attributevalue)));
	                break;
	            case "id":
	            	select = new Select(driver.findElement(By.id(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.id(attributevalue)));
	                break;
	            case "name":
	            	select = new Select(driver.findElement(By.name(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.name(attributevalue)));
	                break;
	            case "linktext":
	            	select = new Select(driver.findElement(By.linkText(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.linkText(attributevalue)));
	                break;
	            case "cssselector":
	            	select = new Select(driver.findElement(By.cssSelector(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.cssSelector(attributevalue)));
	                break;
	            case "classname":
	            	select = new Select(driver.findElement(By.className(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.className(attributevalue)));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found ");
	            	throw new UnsupportedOperationException();
			}
			try{
			select.selectByIndex(index);
			}catch(ElementNotVisibleException e1){
			    JavascriptExecutor jss= ((JavascriptExecutor)driver);
			    element=driver.findElement(By.xpath(attributevalue));
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
				select.selectByIndex(index);
			
		}catch(StaleElementReferenceException e2){
			int count=0;
		    element=driver.findElement(By.xpath(attributevalue));
			while(count <4){
			if(element.isDisplayed()){
				
				select.selectByIndex(index);
				break;
			}
			Thread.sleep(1500);
			count++;
			}
		}catch(WebDriverException e3){
		
			    JavascriptExecutor jss= ((JavascriptExecutor)driver);
			    element=driver.findElement(By.xpath(attributevalue));
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
			    select.selectByIndex(index);
			}
	
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo );
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);
		}

	}
	
	
	/**
	* <h1>selectValue</h1>
	* This is a Common Function to Select Value from any Dropdown based on Value
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-19-2020
	* @param   	String attribute, String attributevalue, String eleValue,String actionInfo
	* @return  	none
	*/

	public static void selectValue(String attribute, String attributevalue, String eleValue,String actionInfo) {
		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	select = new Select(driver.findElement(By.xpath(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.xpath(attributevalue)));
	                break;
	            case "id":
	            	select = new Select(driver.findElement(By.id(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.id(attributevalue)));
	                break;
	            case "name":
	            	select = new Select(driver.findElement(By.name(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.name(attributevalue)));
	                break;
	            case "linktext":
	            	select = new Select(driver.findElement(By.linkText(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.linkText(attributevalue)));
	                break;
	            case "cssselector":
	            	select = new Select(driver.findElement(By.cssSelector(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.cssSelector(attributevalue)));
	                break;
	            case "classname":
	            	select = new Select(driver.findElement(By.className(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.className(attributevalue)));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found ");
	            	throw new UnsupportedOperationException();
			}
			try{
			select.selectByValue(eleValue);
			}catch(ElementNotVisibleException e1){
			    JavascriptExecutor jss= ((JavascriptExecutor)driver);
			    element=driver.findElement(By.xpath(attributevalue));
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
				select.selectByValue(eleValue);
			
		}catch(StaleElementReferenceException e2){
			int count=0;
		    element=driver.findElement(By.xpath(attributevalue));
			while(count <4){
			if(element.isDisplayed()){
				
				select.selectByValue(eleValue);
				break;
			}
			Thread.sleep(1500);
			count++;
			}
		}catch(WebDriverException e3){
		
			    JavascriptExecutor jss= ((JavascriptExecutor)driver);
			    element=driver.findElement(By.xpath(attributevalue));
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
				select.selectByValue(eleValue);
			}
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: "+ actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo);
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Not able to  : " + actionInfo +"--------------->"+ e);
		}

	}
	
	
	/**
	* <h1>selectText</h1>
	* This is a Common Function to Select Value from any Dropdown based on Text
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-19-2020
	* @param   	String attribute, String attributevalue, String text, String actionInfo
	* @return  	none
	*/

	public static void selectText(String attribute, String attributevalue, String text, String actionInfo) {
		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	select = new Select(driver.findElement(By.xpath(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.xpath(attributevalue)));
	                break;
	            case "id":
	            	select = new Select(driver.findElement(By.id(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.id(attributevalue)));
	                break;
	            case "name":
	            	select = new Select(driver.findElement(By.name(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.name(attributevalue)));
	                break;
	            case "linktext":
	            	select = new Select(driver.findElement(By.linkText(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.linkText(attributevalue)));
	                break;
	            case "cssselector":
	            	select = new Select(driver.findElement(By.cssSelector(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.cssSelector(attributevalue)));
	                break;
	            case "classname":
	            	select = new Select(driver.findElement(By.className(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.className(attributevalue)));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found ");
	            	throw new UnsupportedOperationException();
			}
			try{
			select.selectByVisibleText(text);
			}catch(ElementNotVisibleException e1){
			    JavascriptExecutor jss= ((JavascriptExecutor)driver);
			    element=driver.findElement(By.xpath(attributevalue));
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
				select.selectByVisibleText(text);
			
		}catch(StaleElementReferenceException e2){
			int count=0;
		    element=driver.findElement(By.xpath(attributevalue));
			while(count <4){
			if(element.isDisplayed()){
				
				select.selectByVisibleText(text);
				break;
			}
			Thread.sleep(1500);
			count++;
			}
		}catch(WebDriverException e3){
		
			    JavascriptExecutor jss= ((JavascriptExecutor)driver);
			    element=driver.findElement(By.xpath(attributevalue));
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
				select.selectByVisibleText(text);
			}
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo );
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);
		}
	}
	
	
	/**
	* <h1>getDropdownDefaultValue</h1>
	* This is a Common Function to Get Default Value from any Dropdown
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-19-2020
	* @param   	String attribute, String attributevalue,String actionInfo
	* @return  	get (default text value)
	*/

	public static String getDropdownDefaultValue(String attribute, String attributevalue,String actionInfo) {
		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	select = new Select(driver.findElement(By.xpath(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.xpath(attributevalue)));
	                break;
	            case "id":
	            	select = new Select(driver.findElement(By.id(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.id(attributevalue)));
	                break;
	            case "name":
	            	select = new Select(driver.findElement(By.name(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.name(attributevalue)));
	                break;
	            case "linktext":
	            	select = new Select(driver.findElement(By.linkText(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.linkText(attributevalue)));
	                break;
	            case "cssselector":
	            	select = new Select(driver.findElement(By.cssSelector(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.cssSelector(attributevalue)));
	                break;
	            case "classname":
	            	select = new Select(driver.findElement(By.className(attributevalue)));
	            	Jexecutor.highlightElement(driver.findElement(By.className(attributevalue)));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found ");
	            	throw new UnsupportedOperationException();
			}
			
			String get=select.getFirstSelectedOption().getText();
			// bResult = true;
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo);
			return get;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo );
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);
		}
	}
	
	
	/**
	* <h1>selectTextContain</h1>
	* This is a Common Function to Select Value from any Dropdown based on text contained within
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-19-2020
	* @param   	String attribute, String attributevalue, String text, String actionInfo
	* @return  	none
	*/

	public static void selectTextContain(String attribute, String attributevalue, String text, String actionInfo) {
		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = driver.findElement(By.xpath(attributevalue));
	                break;
	            case "id":
	            	element = driver.findElement(By.id(attributevalue));
	                break;
	            case "name":
	            	element = driver.findElement(By.name(attributevalue));
	                break;
	            case "linktext":
	            	element = driver.findElement(By.linkText(attributevalue));
	                break;
	            case "cssselector":
	            	element = driver.findElement(By.cssSelector(attributevalue));
	                break;
	            case "classname":
	            	element = driver.findElement(By.className(attributevalue));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found ");
	            	throw new UnsupportedOperationException();
			}
			
			List<WebElement> elements = element.findElements(By.tagName("option"));

			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i).getText().contains(text)) {
					elements.get(i).click();
					break;
				}
			}
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo );
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);
		}
		// return bResult;
	}
	
	
	/**
	* <h1>setAttributeValue</h1>
	* This is a Common Function to Set the Value of any attribute of a Web Element
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-19-2020
	* @param   	String attribute, String attributevalue, String attName, String attVal, String actionInfo
	* @return  	none
	*/

	public static void setAttributeValue(String attribute, String attributevalue, String attName, String attVal, String actionInfo) {

		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = driver.findElement(By.xpath(attributevalue));
	                break;
	            case "id":
	            	element = driver.findElement(By.id(attributevalue));
	                break;
	            case "name":
	            	element = driver.findElement(By.name(attributevalue));
	                break;
	            case "linktext":
	            	element = driver.findElement(By.linkText(attributevalue));
	                break;
	            case "cssselector":
	            	element = driver.findElement(By.cssSelector(attributevalue));
	                break;
	            case "classname":
	            	element = driver.findElement(By.className(attributevalue));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
			}
			
			Jexecutor.setAttValue(element, attName, attVal);
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo);
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);
		}

	}
	
	
	/**
	* <h1>verifyCheckBox</h1>
	* This is a Common Function to Check the State of a Checkbox
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-19-2020
	* @param   	String attribute, String attributevalue
	* @return  	boolean
	*/

	public static boolean verifyCheckBox(String attribute, String attributevalue) {
		try {
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = driver.findElement(By.xpath(attributevalue));
	                break;
	            case "id":
	            	element = driver.findElement(By.id(attributevalue));
	                break;
	            case "name":
	            	element = driver.findElement(By.name(attributevalue));
	                break;
	            case "linktext":
	            	element = driver.findElement(By.linkText(attributevalue));
	                break;
	            case "cssselector":
	            	element = driver.findElement(By.cssSelector(attributevalue));
	                break;
	            case "classname":
	            	element = driver.findElement(By.className(attributevalue));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found ");
	            	throw new UnsupportedOperationException();
			}
			
	//		Jexecutor.highlightElement(element);
			if(driver.findElements(By.xpath(attributevalue)).size()>0){
			
				System.out.println("Successfully verify check box ");
				logger.log(LogStatus.INFO, "Successfully verify check box ");
				return true;
			}
			else{
				return false;
			}

		} catch (NoSuchElementException e) {
			System.out.println("RUNTIME_ERROR : : Not able to  ");
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Not able to  " + e);

		} catch (Exception e) {

			return false;
		}
		

	}
	
	
	/**
	* <h1>getListSize</h1>
	* This is a Common Function to Get the Size of a List
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-19-2020
	* @param   	String attribute, String attributevalue, String actionInfo
	* @return  	listSize
	*/

	public static int getListSize(String attribute, String attributevalue, String actionInfo) {
		int listSize;
		try {

			List<WebElement> options;
	//			find the object based on attribute
		switch (attribute.toLowerCase()) {
	        case "xpath":
	        	options = driver.findElements(By.xpath(attributevalue));
	            break;
	        case "id":
	        	options = driver.findElements(By.id(attributevalue));
	            break;
	        case "name":
	        	options = driver.findElements(By.name(attributevalue));
	            break;
	        case "linktext":
	        	options = driver.findElements(By.linkText(attributevalue));
	            break;
	        case "cssselector":
	        	options = driver.findElements(By.cssSelector(attributevalue));
	            break;
	        case "classname":
	        	options = driver.findElements(By.className(attributevalue));
	            break;
	        default: 
	        	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	        	throw new UnsupportedOperationException();
		}
		listSize = options.size();
		for(int i=0;i<listSize;i++){
			if(listSize!=0)
			{
	    	WebElement ele=options.get(i);
		    Jexecutor.highlightElement(ele);
			}
		}
		System.out.println("Successfully: " + actionInfo);
	} catch (Exception e) {
		System.out.println("RUNTIME_ERROR : : Not able to Get Size : " + actionInfo );
		throw new java.lang.RuntimeException(
				"RUNTIME_ERROR : : Not able to Get Size : " + actionInfo + "------>" + e);
		}
		return listSize;
	}
	
	
	/**
	* <h1>clickHomeTab</h1>
	* This is a Common Function to Click on Home Tab
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-25-2020
	* @param   	String attribute, String attributevalue, String text, String actionInfo
	* @return  	none
	*/
	
	public static void clickHomeTab(){
		 windowSize= driver.manage().window().getSize();
		 try{
		     driver.findElement(By.xpath(HomeObjects.homeTabLnk)).click();
		 }catch (Exception e){
			 driver.navigate().refresh();
			 ByAttribute.click("xpath", HomeObjects.homeTabLnk, "click Home Tab");
	}
	
}
	 
	
	/**
	* <h1>clickOnPod</h1>
	* This is a Common Function to Click on Pods based on Text
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-30-2020
	* @param   	String podName
	* @return  	none
	*/
	
	public static void clickOnPod(String podName){
		for(int i=0;i<5;i++){
			try{
				Utility.connectToLatestFrame(driver);
				if(driver.findElement(By.xpath(".//div[@class='podTitle ng-binding' and text()='"+podName+"']")).isEnabled()){
					driver.findElement(By.xpath(".//div[@class='podTitle ng-binding' and text()='"+podName+"']")).click();
					logger.log(LogStatus.PASS, "Clicked Successfully on Pod { "+podName+" }");
					break;
				}
			}catch(Exception e){
				driver.navigate().refresh();
				Utility.pause(20);
			}
		}
	}


	/**
	* <h1>clickOnPod</h1>
	* This is a Common Function to Click on Pods based on Text
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-30-2020
	* @param   	String podName
	* @return  	none
	*/
	
	public static void clickOnSelfServicePod(String podName){
		for(int i=0;i<5;i++){
			try{
//				Utility.connectToLatestFrame(driver);
				if(driver.findElement(By.xpath(".//div[@class='custom-pod-title' and text()='"+podName+"']")).isEnabled()){
					driver.findElement(By.xpath(".//div[@class='custom-pod-title' and text()='"+podName+"']")).click();
					logger.log(LogStatus.PASS, "Clicked Successfully on Pod { "+podName+" }");
					break;
				}
			}catch(Exception e){
				driver.navigate().refresh();
				Utility.pause(20);
			}
		}
	}

	public static void mouseHover(String attribute, String attributevalue, String actionInfo) {
		 
		try {
//			dynamicwait.WAitUntilPageLoad();
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = driver.findElement(By.xpath(attributevalue));
	                break;
	            case "id":
	            	element = driver.findElement(By.id(attributevalue));
	                break;
	            case "name":
	            	element = driver.findElement(By.name(attributevalue));
	                break;
	            case "linktext":
	            	element = driver.findElement(By.linkText(attributevalue));
	                break;
	            case "cssselector":
	            	element = driver.findElement(By.cssSelector(attributevalue));
	                break;
	            case "classname":
	            	element = driver.findElement(By.className(attributevalue));
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
        }

			Jexecutor.highlightElement(element);
			try{
			//element.click();
				Actions action = new Actions(driver);
				action.moveToElement(element).build().perform();
		
			}catch(ElementNotVisibleException e1){
				    JavascriptExecutor jss= ((JavascriptExecutor)driver);
				    jss.executeScript("arguments[0].scrollIntoView(true);", element);
				    Thread.sleep(1000);
					jss.executeScript("arguments[0].click();", element);
				
			}catch(StaleElementReferenceException e2){
				int count=0;
				while(count <4){
				if(element.isDisplayed()){
					
					element.click();
					break;
				}
				Thread.sleep(1500);
				count++;
				}
			}catch(WebDriverException e3){
			
				    JavascriptExecutor jss= ((JavascriptExecutor)driver);
				    jss.executeScript("arguments[0].scrollIntoView(true);", element);
				    Thread.sleep(1000);
					jss.executeScript("arguments[0].click();", element);
				}
		
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo );
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);

		}

	}
	
	
	
	

}

