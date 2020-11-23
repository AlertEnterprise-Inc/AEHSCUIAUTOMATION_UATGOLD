package CommonFunctions;

import org.openqa.selenium.By;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.ByAttribute;
import CommonClassReusables.Utility;
import CommonClassReusables.dynamicwait;
import ObjectRepository.LoginObjects;

public class LoginPage extends BrowserSelection
{
	
	
	/**
	* <h1>login</h1>
	* This is Method to Login to Application
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-06-2020
	* @param   	String userName, String passWord
	* @return  	boolean
	**/
	
	public static boolean login(String userName, String passWord) throws Exception
	{
			System.out.println("**************************** login **********************************");
		
			driver.get(AGlobalComponents.applicationURL);
			System.out.println("Successfully: open url-"+AGlobalComponents.applicationURL);
			dynamicwait.WAitUntilPageLoad();
			driver.findElement(By.xpath(LoginObjects.loginUsernameTxt)).sendKeys(userName);
			System.out.println("Successfully: enter username");
			dynamicwait.WAitUntilPageLoad();
			driver.findElement(By.xpath(LoginObjects.loginPasswordTxt)).sendKeys(passWord);
			System.out.println("Successfully: enter password");
			dynamicwait.WAitUntilPageLoad();
			try{
				Thread.sleep(1000);
				ByAttribute.selectTextContain("xpath", LoginObjects.loginTimeZoneDdn, AGlobalComponents.timezone, "Select Timezone");
			}catch(Exception e){
				System.out.println("Time Zone selection not required");
			}
			driver.findElement(By.xpath(LoginObjects.loginSigninBtn)).click();
			System.out.println("Successfully: click login button");
			Thread.sleep(5000);
			try{
				if(driver.findElement(By.xpath(LoginObjects.loginSigninBtn)).isDisplayed())
				{
					System.out.println("Re-Login Required");
					driver.findElement(By.xpath(LoginObjects.loginUsernameTxt)).sendKeys(userName);
					System.out.println("Successfully: enter username");
					dynamicwait.WAitUntilPageLoad();
					driver.findElement(By.xpath(LoginObjects.loginPasswordTxt)).sendKeys(passWord);
					System.out.println("Successfully: enter password");
					dynamicwait.WAitUntilPageLoad();
					try{
						Thread.sleep(1000);
						ByAttribute.selectTextContain("xpath", LoginObjects.loginTimeZoneDdn, AGlobalComponents.timezone, "Select Timezone");
					}catch(Exception e){
						System.out.println("Time Zone selection not required");
					}
					driver.findElement(By.xpath(LoginObjects.loginSigninBtn)).click();
					System.out.println("Successfully: click login button");
				}
			}catch(Exception e){
				System.out.println("Login Successful");
			}

		return true;
	}


	/**
	* <h1>logout</h1>
	* This is Method to Logout from Application
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	04-06-2020
	* @param   	none
	* @return  	boolean
	**/
	
	public static boolean logout() throws Exception
	{
			System.out.println("**************************** logout **********************************");
		
			driver.findElement(By.xpath(".//*[@id='fabric-template-header-section']//a[@class='sf-with-ul']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(".//*[@id='banner_signout']/a/span[text()='Sign Out']")).click();
			Thread.sleep(3000);
			Utility.verifyElementPresent(".//*[@id='id7']/ul/li/span[@class='feedbackPanelERROR']", "Logout Message", false, false);

		return true;
	}

	
	/**
	* <h1>loginSelfService</h1>
	* This is Method to Login to Self Service Portal
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-09-2020
	* @param   	String userName, String passWord
	* @return  	boolean
	**/
	
	public static boolean loginSelfService(String userName, String passWord) throws Exception
	{
			System.out.println("**************************** loginSelfService **********************************");
		
			driver.get(AGlobalComponents.selfServicePortalURL);
			System.out.println("Successfully: open url-"+AGlobalComponents.selfServicePortalURL);
			dynamicwait.WAitUntilPageLoad();
			Utility.pause(2);
			driver.findElement(By.xpath(LoginObjects.selfServiceSigninWithOKTABtn)).click();
			System.out.println("Click Sign-in with OKTA Button");
			Utility.pause(3);
			driver.findElement(By.xpath(LoginObjects.selfServiceOKTAUsernameTxt)).sendKeys(userName);
			System.out.println("Enter Self-Service Username");
			driver.findElement(By.xpath(LoginObjects.selfServiceOKTAPasswordTxt)).sendKeys(passWord);
			System.out.println("Enter Self-Service Password");
			driver.findElement(By.xpath(LoginObjects.selfServiceOKTASigninBtn)).click();
			System.out.println("Click Sign-in Button");
			Utility.pause(15);
			dynamicwait.WAitUntilPageLoad();
			logger.log(LogStatus.PASS, "Logged In to Self Service Portal");
			try{
				if(driver.findElement(By.xpath(LoginObjects.selfServiceOKTASigninBtn)).isDisplayed())
				{
					System.out.println("Re-Login Required");
					driver.findElement(By.xpath(LoginObjects.selfServiceOKTAUsernameTxt)).sendKeys(userName);
					System.out.println("Enter Self-Service Username");
					driver.findElement(By.xpath(LoginObjects.selfServiceOKTAPasswordTxt)).sendKeys(passWord);
					System.out.println("Enter Self-Service Password");
					driver.findElement(By.xpath(LoginObjects.selfServiceOKTASigninBtn)).click();
					System.out.println("Click Sign-in Button");
					Utility.pause(15);
				}
			}catch(Exception e){
				System.out.println("Login Successful");
			}

		return true;
	}

	
	/**
	* <h1>logoutSelfService</h1>
	* This is Method to Logout from Self Service Application
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	07-09-2020
	* @param   	none
	* @return  	boolean
	**/
	
	public static boolean logoutSelfService() throws Exception
	{
			System.out.println("**************************** logoutSelfService **********************************");
		
			driver.findElement(By.xpath(".//button[@class='mat-menu-trigger mat-button mat-button-base ng-star-inserted']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(".//button[@class='mat-menu-item ng-star-inserted' and text()='Sign Out']")).click();
			Thread.sleep(3000);
			Utility.verifyElementPresent(".//h2[text()='You are successfully logged out from Self Service portal.']", "Logout Message", false, false);

		return true;
	}

	
}
