package CommonFunctions;

import static io.restassured.RestAssured.given;

import com.relevantcodes.extentreports.LogStatus;
import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.Payload;
import CommonClassReusables.TestDataInterface;
import CommonClassReusables.Utility;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApiMethods extends BrowserSelection{
	
	public static String reconTestDataDirectory= "Test_Data/Recon";
	public static boolean generateAccessToken() {
		
		RestAssured.baseURI=AGlobalComponents.baseURI;
		String requestBody=Payload.accessTokenJson("admin", "Alert1234");
		if(requestBody!=null) {
			logger.log(LogStatus.PASS, "Generate access token json: "+requestBody);
			Response response=given().log().all().queryParam("grant_type", "password").header("Content-Type","application/Json")
					.body(requestBody).when().post("/api/auth/token").then().log().all().extract().response();
			System.out.println("Response "+response);
			String responseBody=response.getBody().asString();
			int statusCode=response.getStatusCode();
			if(statusCode==200) {
				JsonPath js= new JsonPath(responseBody);
				String accessToken=js.getString("access_token");
				logger.log(LogStatus.PASS, "access-token: "+accessToken);
				AGlobalComponents.access_token=accessToken;
				return true;
			}
			else {
				logger.log(LogStatus.FAIL, "status code is: "+statusCode);
				logger.log(LogStatus.FAIL, "Error is: "+responseBody);
				return false;
			}	
		}
		else {
			logger.log(LogStatus.FAIL, "Unable to get generate access token json ");
			return false;
		}
	}
	
public static boolean createIdentityThroughAPI() throws Throwable {
	
	try
	{
		String identityTemplateFile = reconTestDataDirectory + "/Identity_Template.csv";
		String identityDataFile = reconTestDataDirectory + "/Identity.csv";
		TestDataInterface.compileTwoRowDataTemplate(identityTemplateFile, identityDataFile);
			
		String identitySystemTemplateFile = reconTestDataDirectory + "/IdentitySystem_Template.csv";
		String identitySystemDataFile = reconTestDataDirectory + "/IdentitySystem.csv";
		TestDataInterface.compileTwoRowDataTemplate(identitySystemTemplateFile, identitySystemDataFile);
			
		RestAssured.baseURI=AGlobalComponents.baseURI;
		String requestBody=Payload.createIdentityJson(identityDataFile,identitySystemDataFile);
		if(requestBody!=null) {
			logger.log(LogStatus.INFO, "Create Identity request json "+requestBody);
		
			Response response=given().log().all().header("Authorization","Bearer " + AGlobalComponents.access_token).header("Content-Type","application/Json")
					.body(requestBody).when().post("/api/identity/external/save").then().log().all().extract().response();
			int statusCode=response.getStatusCode();
			if(statusCode==200) {
				logger.log(LogStatus.PASS, "Status code is:"+statusCode);
				JsonPath js= new JsonPath(response.getBody().asString());
				String messageText=js.getString("messages[0].messageDisplayText");
				if(messageText.equalsIgnoreCase("Identity saved successfully.")) {
					logger.log(LogStatus.PASS, "Identity saved successfully");
					return true;
				}
				else {
					logger.log(LogStatus.FAIL, messageText);
					return false;
				}			
			}
			else {
				logger.log(LogStatus.FAIL, "Status code is:"+statusCode);
				return false;
			}
		}
		else {
			logger.log(LogStatus.FAIL, "Unable to get Create Identity request json");
			return false;
		}
	}		
	catch(Exception e)
	{		
		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
		Utility.recoveryScenario(nameofCurrMethod, e);
		return false;
	}	
}
}
