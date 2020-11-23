package CommonClassReusables;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class dynamicwait extends BrowserSelection {

	
	/**
	* <h1>PageLoad</h1>
	* This is a Dynamic Wait Method
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-22-2020
	* @param   	ITestResult result
	* @return  	none
	*/
	
	public static void PageLoad() {

		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);

		// TODO Auto-generated method stub
	}

	
	/**
	* <h1>WaitUntilElement</h1>
	* This is a Dynamic Wait Method to Wait until Element is Clickable
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-22-2020
	* @param   	ITestResult result
	* @return  	none
	*/
	
	public static void WaitUntilElement(By by,int second) {
		WebDriverWait wait = new WebDriverWait(driver, second);;
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
		//	ExtentReport.testStep("Caught exception in " + Thread.currentThread().getStackTrace()[1].getMethodName() + "Due to: "+e.getMessage());
		
		}

	}
	
	
	/**
	* <h1>WAitUntilPageLoad</h1>
	* This is a Dynamic Wait Method to Wait until Page Loads
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-22-2020
	* @param   	ITestResult result
	* @return  	none
	*/
	
    public static void WAitUntilPageLoadOld(){
         WebDriverWait wait = new WebDriverWait(driver, 10);
         JavascriptExecutor jse=(JavascriptExecutor) driver;
    	try{
         wait.until(new ExpectedCondition<Boolean>() 
          {
            public Boolean apply(WebDriver d)
            {
              return   jse.executeScript("return document.readyState").equals("complete");
            }
          }  );
         WAitUntilPageLoadPanel();
    	}catch(Exception e){
    		
    		throw new RuntimeException("PAGE LOAD Exception : page not load on given interval of time");
    	}
    }
  
    
    /**
	* <h1>WAitUntilPageLoadPanel</h1>
	* This is a Dynamic Wait Method to Wait until Page Loads
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-22-2020
	* @param   	ITestResult result
	* @return  	none
	*/
    
    public static void WAitUntilPageLoadPanel() throws InterruptedException{
    	
    	for(int i=1;i<1000;i++)
    	{
    	Thread.sleep(10);
       	int getSize=driver.findElements(By.xpath("//*[@id='loading-pane' and @style='display: none;']")).size();
    	if(getSize > 0){
    		break;
    	}
    	}
    }
    

	/**
	* <h1>WAitUntilPageLoad</h1>
	* This is a Dynamic Wait Method to Wait until Page Loads
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-22-2020
	* @param   	ITestResult result
	* @return  	none
	*/
	
    public static void WAitUntilPageLoad(){
        WebDriverWait wait = new WebDriverWait(driver, 15);
        
   	try{
        wait.until(new ExpectedCondition<Boolean>() 
         {
           public Boolean apply(WebDriver d)
           { JavascriptExecutor jse=(JavascriptExecutor) driver;
             return   jse.executeScript("return document.readyState").equals("complete");
           }
         }  );
       
   	}catch(Exception e){
   		
   		throw new RuntimeException("PAGE LOAD Exception : page not load on given interval of time");
   	}
   }
}
