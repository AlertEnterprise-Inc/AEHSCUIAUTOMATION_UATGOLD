package CommonFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

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
	 * @throws Throwable 
	**/
	
	public static boolean loginAEHSC(String userName, String passWord) throws Throwable
	{
			System.out.println("**************************** login **********************************");
		
			driver.get(AGlobalComponents.applicationURL);
			System.out.println("Successfully: open url-"+AGlobalComponents.applicationURL);
			dynamicwait.WAitUntilPageLoad();
						
			boolean flg=true;
			for (int i=1;i<10 && (flg);i++){
				if(driver.findElements(By.xpath(LoginObjects.loginUserName)).size()>0){
					flg=false;
					System.out.println("Logged In to the Portal");
					logger.log(LogStatus.PASS, "Logged In to the Portal");
					break;
				}
				else
					Utility.pause(10);
			}
			
			driver.findElement(By.xpath(LoginObjects.loginUserName)).sendKeys(userName);
			System.out.println("Enter Username");
			driver.findElement(By.xpath(LoginObjects.pswdTxt)).sendKeys(passWord);
			System.out.println("Enter Password");
			driver.findElement(By.xpath(LoginObjects.loginButtonLnk)).click();
			System.out.println("Click Sign-in Button");
			Utility.pause(30);
			dynamicwait.WAitUntilPageLoad();
			boolean flag = false;
			try{
				
				for(int j=0;j<12 && (!flag);j++){
					if(driver.findElements(By.xpath(LoginObjects.loginButtonLnk)).size()>0)
					{
						System.out.println("Re-Login Required");
						driver.navigate().refresh();
						driver.findElement(By.xpath(LoginObjects.loginUserName)).sendKeys(userName);
						System.out.println("Enter Username");
						driver.findElement(By.xpath(LoginObjects.pswdTxt)).sendKeys(passWord);
						System.out.println("Enter Password");
						driver.findElement(By.xpath(LoginObjects.loginButtonLnk)).click();
						System.out.println("Click Sign-in Button");
						Utility.pause(15);
					}
					else
						flag=true;
				}
				if(!flag){
					logger.log(LogStatus.FAIL, "Unable to login to the application");
				}
			}
			catch(Exception e){
				System.out.println("login successful");
			}

		return flag;
	}
	
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
		
			driver.findElement(By.xpath("//span[contains(text(),'admin user')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[contains(text(),'Logout')]")).click();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Logout from Appplication");
			Utility.verifyElementPresent("//div[contains(text(),'Welcome Back')]", "Login Page", false, false);

		return true;
	}

}
