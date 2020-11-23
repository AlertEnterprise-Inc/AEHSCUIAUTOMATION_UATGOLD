package CommonClassReusables;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import ObjectRepository.HomeObjects;
import ObjectRepository.BadgingObjects;

public class ByAttributeAngular extends BrowserSelection  {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	static By element;
	static String value = "";
	static Select select = null;
	static WebDriverWait eleWait = new WebDriverWait (driver, 20);

	

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

			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = By.xpath(attributevalue);
	                break;
	            case "id":
	            	element = By.id(attributevalue);
	                break;
	            case "name":
	            	element = By.name(attributevalue);
	                break;
	            case "linktext":
	            	element = By.linkText(attributevalue);
	                break;
	            case "cssselector":
	            	element = By.cssSelector(attributevalue);
	                break;
	            case "classname":
	            	element = By.className(attributevalue);
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
        }

			Jexecutor.highlightAngularElement(element);
			try{
				eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).click();
		
			}catch(ElementNotVisibleException e1){
				    JavascriptExecutor jss= ((JavascriptExecutor)driver);
				    jss.executeScript("arguments[0].scrollIntoView(true);", element);
				    Thread.sleep(1000);
					jss.executeScript("arguments[0].click();", element);
				
			}catch(StaleElementReferenceException e2){
				int count=0;
				while(count <4){
				if(((WebElement) element).isDisplayed()){
					
					eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).click();
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
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = By.xpath(attributevalue);
	                break;
	            case "id":
	            	element = By.id(attributevalue);
	                break;
	            case "name":
	            	element = By.name(attributevalue);
	                break;
	            case "linktext":
	            	element = By.linkText(attributevalue);
	                break;
	            case "cssselector":
	            	element = By.linkText(attributevalue);
	                break;
	            case "classname":
	            	element = By.linkText(attributevalue);
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
    }
			Jexecutor.highlightAngularElement(element);
			try{
			textvalue = eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).getText();
			}catch(ElementNotVisibleException e1){
				
				JavascriptExecutor jss=((JavascriptExecutor)driver);
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
			    textvalue = eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).getText();
					
				}catch(StaleElementReferenceException e2){
					int count=0;
					while(count <4){
					if(eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed()){
						
						textvalue = eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).getText();
						break;
					}
					Thread.sleep(1500);
					count++;
					}
				}catch(WebDriverException e3){

					    JavascriptExecutor jss=((JavascriptExecutor)driver);
					    jss.executeScript("arguments[0].scrollIntoView(true);", element);
					    Thread.sleep(1000);
					    textvalue = eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).getText();
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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = By.xpath(attributevalue);
	                break;
	            case "id":
	            	element = By.id(attributevalue);
	                break;
	            case "name":
	            	element = By.name(attributevalue);
	                break;
	            case "linktext":
	            	element = By.linkText(attributevalue);
	                break;
	            case "cssselector":
	            	element = By.cssSelector(attributevalue);
	                break;
	            case "classname":
	            	element = By.className(attributevalue);
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
    }
			Jexecutor.highlightAngularElement(element);
			try{
			value = eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).getAttribute(attributeName);
			}catch(StaleElementReferenceException e2){
				if(eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed()){
					
					value = eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).getAttribute(attributeName);
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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = By.xpath(attributevalue);
	                break;
	            case "id":
	            	element = By.id(attributevalue);
	                break;
	            case "name":
	            	element = By.name(attributevalue);
	                break;
	            case "linktext":
	            	element = By.linkText(attributevalue);
	                break;
	            case "cssselector":
	            	element = By.cssSelector(attributevalue);
	                break;
	            case "classname":
	            	element = By.className(attributevalue);
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
			}
			
			Jexecutor.highlightAngularElement(element);
			try{
				eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(text);
			}catch(ElementNotVisibleException e1){
				
				    JavascriptExecutor jss=((JavascriptExecutor)driver);
				    jss.executeScript("arguments[0].scrollIntoView(true);", element);
				    Thread.sleep(1000);
				    eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(text);
				
				}catch(StaleElementReferenceException e2){
					int count=0;
					while(count <4){
					if(eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed()){
						
						eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(text);
						break;
					}
					Thread.sleep(1500);
					count++;
					}
				}catch(WebDriverException e3){

					    JavascriptExecutor jss=((JavascriptExecutor)driver);
					    jss.executeScript("arguments[0].scrollIntoView(true);", element);
					    Thread.sleep(1000);
					    eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(text);
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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = By.xpath(attributevalue);
	                break;
	            case "id":
	            	element = By.id(attributevalue);
	                break;
	            case "name":
	            	element = By.name(attributevalue);
	                break;
	            case "linktext":
	            	element = By.linkText(attributevalue);
	                break;
	            case "cssselector":
	            	element = By.cssSelector(attributevalue);
	                break;
	            case "classname":
	            	element = By.className(attributevalue);
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
			}
			
			Jexecutor.highlightAngularElement(element);
			try{
				eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).clear();
				eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(text);
			}catch(ElementNotVisibleException e1){
				 
				   JavascriptExecutor jss=((JavascriptExecutor)driver);
				    jss.executeScript("arguments[0].scrollIntoView(true);", element);
				    Thread.sleep(1000);
				    eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).clear();
				    eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(text);
					
				}catch(StaleElementReferenceException e2){
					int count=0;
					while(count <4){
					if(eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed()){
						eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).clear();
						eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(text);
						break;
					}
					Thread.sleep(1500);
					count++;
					}
				}catch(WebDriverException e3){

					    JavascriptExecutor jss=((JavascriptExecutor)driver);
					    jss.executeScript("arguments[0].scrollIntoView(true);", element);
					    Thread.sleep(1000);
					    eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).clear();
					    eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(text);
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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = By.xpath(attributevalue);
	                break;
	            case "id":
	            	element = By.id(attributevalue);
	                break;
	            case "name":
	            	element = By.name(attributevalue);
	                break;
	            case "linktext":
	            	element = By.linkText(attributevalue);
	                break;
	            case "cssselector":
	            	element = By.cssSelector(attributevalue);
	                break;
	            case "classname":
	            	element = By.className(attributevalue);
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found ");
	            	throw new UnsupportedOperationException();
			}
			
			Jexecutor.highlightAngularElement(element);
			return eleWait.until(ExpectedConditions.visibilityOfElementLocated(element));
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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
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
			    element = By.xpath(attributevalue);
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
				select.selectByIndex(index);
			
		}catch(StaleElementReferenceException e2){
			int count=0;
			element = By.xpath(attributevalue);
			while(count <4){
			if(eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed()){
				
				select.selectByIndex(index);
				break;
			}
			Thread.sleep(1500);
			count++;
			}
		}catch(WebDriverException e3){
		
			    JavascriptExecutor jss= ((JavascriptExecutor)driver);
			    element=By.xpath(attributevalue);
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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
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
			    element=By.xpath(attributevalue);
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
				select.selectByValue(eleValue);
			
		}catch(StaleElementReferenceException e2){
			int count=0;
		    element=By.xpath(attributevalue);
			while(count <4){
			if(eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed()){
				
				select.selectByValue(eleValue);
				break;
			}
			Thread.sleep(1500);
			count++;
			}
		}catch(WebDriverException e3){
		
			    JavascriptExecutor jss= ((JavascriptExecutor)driver);
			    element=By.xpath(attributevalue);
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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
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
			    element=By.xpath(attributevalue);
			    jss.executeScript("arguments[0].scrollIntoView(true);", element);
			    Thread.sleep(1000);
				select.selectByVisibleText(text);
			
		}catch(StaleElementReferenceException e2){
			int count=0;
		    element=By.xpath(attributevalue);
			while(count <4){
			if(eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed()){
				
				select.selectByVisibleText(text);
				break;
			}
			Thread.sleep(1500);
			count++;
			}
		}catch(WebDriverException e3){
		
			    JavascriptExecutor jss= ((JavascriptExecutor)driver);
			    element=By.xpath(attributevalue);
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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = By.xpath(attributevalue);
	                break;
	            case "id":
	            	element = By.id(attributevalue);
	                break;
	            case "name":
	            	element = By.name(attributevalue);
	                break;
	            case "linktext":
	            	element = By.linkText(attributevalue);
	                break;
	            case "cssselector":
	            	element = By.cssSelector(attributevalue);
	                break;
	            case "classname":
	            	element = By.className(attributevalue);
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found ");
	            	throw new UnsupportedOperationException();
			}
			
			List<WebElement> elements = eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).findElements(By.tagName("option"));

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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = By.xpath(attributevalue);
	                break;
	            case "id":
	            	element = By.id(attributevalue);
	                break;
	            case "name":
	            	element = By.name(attributevalue);
	                break;
	            case "linktext":
	            	element = By.linkText(attributevalue);
	                break;
	            case "cssselector":
	            	element = By.cssSelector(attributevalue);
	                break;
	            case "classname":
	            	element = By.className(attributevalue);
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found " + actionInfo);
	            	throw new UnsupportedOperationException();
			}
			
			Jexecutor.setAttValue(eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)), attName, attVal);
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
			
			Utility.connectToLatestFrame(driver);
			WebElement iframe=driver.findElement(By.xpath("//iframe[@class='Invi-frame ng-scope']"));
			driver.switchTo().frame(iframe);
			
//			find the object based on attribute
			switch (attribute.toLowerCase()) {
	            case "xpath":
	            	element = By.xpath(attributevalue);
	                break;
	            case "id":
	            	element = By.id(attributevalue);
	                break;
	            case "name":
	            	element = By.name(attributevalue);
	                break;
	            case "linktext":
	            	element = By.linkText(attributevalue);
	                break;
	            case "cssselector":
	            	element = By.cssSelector(attributevalue);
	                break;
	            case "classname":
	            	element = By.className(attributevalue);
	                break;
	            default: 
	            	logger.log(LogStatus.ERROR, "Failed: Attribute Name {" +attribute+"} Not Found ");
	            	throw new UnsupportedOperationException();
			}
			
			Jexecutor.highlightElement(eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)));
			if (!eleWait.until(ExpectedConditions.visibilityOfElementLocated(element)).isSelected())
				return false;

		} catch (NoSuchElementException e) {
			System.out.println("RUNTIME_ERROR : : Not able to  ");
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Not able to  " + e);

		} catch (Exception e) {

			return false;
		}
		System.out.println("Successfully verify check box ");
		logger.log(LogStatus.INFO, "Successfully verify check box ");
		return true;

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
	
	

}

