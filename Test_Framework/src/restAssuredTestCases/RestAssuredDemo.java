package restAssuredTestCases;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.AGlobalComponents;
import CommonClassReusables.BrowserSelection;
import CommonClassReusables.Utility;
import CommonFunctions.Airport_Badging_CommonMethods;
import CommonFunctions.LoginPage;
import ae.jira.common.AlntRestClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;


public class RestAssuredDemo extends BrowserSelection {

	
	String testName;
	
	@BeforeClass
	public void Login() throws Exception {
//		LoginPage.login(AGlobalComponents.username,AGlobalComponents.password);
	}
	
	@BeforeMethod
	public void commonPage(java.lang.reflect.Method method) throws Throwable
	{
		unhandledException = false;	
		testName = method.getName();
		driver.navigate().refresh();
	}
	
	public static void isConditionX(boolean condition) {
		if(condition==false){
			throw new SkipException("skipp");
		}
	}
			
	
@Test(priority=1)
public void API_AirportBadging_TC001() throws Throwable 
	{
	
		logger =report.startTest("API_AirportBadging_TC001", "Create Appointment - Admin");
		System.out.println("[INFO]--> API_AirportBadging_TC001 - TestCase Execution Begins");
		
		logger.log(LogStatus.INFO, "*******API AUTOMATION STARTS*******");
		String testCookie = "CE1DCB09062C167A8C6BA2E0EDDC7B2D";
		String csrfTokenID = "fa24bd26-4b52-4e84-895e-cd95003a0567";
		Airport_Badging_CommonMethods.createAppointmentTypeAPI(testCookie, csrfTokenID);
	
		
//		String jiraFile=System.getProperty("user.dir") + "/Jira";	
//        AlntRestClient.setConfiugration(jiraFile);
//        AlntRestClient.executeTestCase("tcId", "Pass");

		
	}

@Test(priority=4)
public void GetWeatherDetails()
{   
	logger =report.startTest("GetWeatherDetails", "Get Weather Details");
	System.out.println("[INFO]--> GetWeatherDetails - TestCase Execution Begins");
	
	// Specify the base URL to the RESTful web service
	RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

	// Get the RequestSpecification of the request that you want to sent
	// to the server. The server is specified by the BaseURI that we have
	// specified in the above step.
	RequestSpecification httpRequest = RestAssured.given();

	// Make a request to the server by specifying the method Type and the method URL.
	// This will return the Response from the server. Store the response in a variable.
	Response response = httpRequest.request(Method.GET, "/Amritsar");

	// Now let us print the body of the message to see what response
	// we have received from the server
	String responseBody = response.getBody().asString();
	System.out.println("Response Body is =>  " + responseBody);
	logger.log(LogStatus.INFO, "Response Body is =>  " + responseBody);
	
	// Get the status code from the Response. In case of 
	 // a successful interaction with the web service, we
	 // should get a status code of 200.
	 int statusCode = response.getStatusCode();
	 System.out.println("Status Code : {"+statusCode+"} returned");
	 logger.log(LogStatus.INFO, "Status Code : {"+statusCode+"} returned");
	 
	 // Assert that correct status code is returned.
	 Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
	 logger.log(LogStatus.PASS, "Correct Status Code {"+statusCode+"} returned");
	 
	// Get the status line from the Response and store it in a variable called statusLine
	 String statusLine = response.getStatusLine();
	 Assert.assertEquals(statusLine /*actual value*/, "HTTP/1.1 200 OK" /*expected value*/, "Correct status code returned");
	 logger.log(LogStatus.PASS, "Correct Status Code {"+statusCode+"} returned");
	 
	// Reader header of a give name. In this line we will get
	 // Header named Content-Type
	 String contentType = response.header("Content-Type");
	 System.out.println("Content-Type value: " + contentType);
	 Assert.assertEquals(contentType /* actual value */, "application/json" /* expected value */);
	 logger.log(LogStatus.PASS, "Correct Content Type {"+contentType+"} returned");
	 
	 // Reader header of a give name. In this line we will get
	 // Header named Server
	 String serverType =  response.header("Server");
	 System.out.println("Server value: " + serverType);
	 Assert.assertEquals(serverType /* actual value */, "nginx" /* expected value */);
	 logger.log(LogStatus.PASS, "Correct Server Type {"+serverType+"} returned");
	 
	// Reader header of a give name. In this line we will get
	 // Header named Content-Encoding
	 String contentEncoding = response.header("Content-Encoding");
	 Assert.assertEquals(contentEncoding /* actual value */, "gzip" /* expected value */);
	 logger.log(LogStatus.PASS, "Correct Content-Encoding {"+contentEncoding+"} returned");

	 // Reader header of a give name. In this line we will get
	 // Header named Content-Encoding
	 String acceptLanguage = response.header("Content-Encoding");
	 System.out.println("Content-Encoding: " + acceptLanguage);
	 
	// Get all the headers. Return value is of type Headers.
	 // Headers class implements Iterateable interface, hence we
	 // can apply an advance for loop to go through all Headers
	 // as shown in the code below
	 Headers allHeaders = response.headers();
	 
	 // Iterate over all the Headers
	 for(Header header : allHeaders)
	 {
	 System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
	 logger.log(LogStatus.INFO, "Key: " + header.getName() + " Value: " + header.getValue());
	 }
	 
	 
	// Retrieve the body of the Response
	 ResponseBody body = response.getBody();
	 
	 // By using the ResponseBody.asString() method, we can convert the  body
	 // into the string representation.
	 System.out.println("Response Body is: " + body.asString());
	 logger.log(LogStatus.INFO, "Response Body is: " + body.asString());
	 String bodyAsString = body.asString();
	 Assert.assertEquals(bodyAsString.contains("Amritsar") /*Expected value*/, true /*Actual Value*/, "Response body contains Amritsar");
	 logger.log(LogStatus.PASS, bodyAsString+" contains Amritsar");
	// convert the body into lower case and then do a comparison to ignore casing.
	 Assert.assertEquals(bodyAsString.toLowerCase().contains("amritsar") /*Expected value*/, true /*Actual Value*/, "Response body contains Amritsar");

	 
	// First get the JsonPath object instance from the Response interface
	JsonPath jsonPathEvaluator = response.jsonPath();

	// Then simply query the JsonPath object to get a String value of the node
	// specified by JsonPath: City (Note: You should not put $. in the Java code)
	String city = jsonPathEvaluator.get("City");

	// Let us print the city variable to see what we got
	System.out.println("City received from Response " + city);

	// Validate the response
	Assert.assertEquals(city, "Amritsar", "Correct city name received in the Response");
	logger.log(LogStatus.PASS, "Correct city name received in the Response");
	
	// Let us print the city variable to see what we got
	System.out.println("City received from Response " + jsonPathEvaluator.get("City"));
	logger.log(LogStatus.INFO, "City received from Response " + jsonPathEvaluator.get("City"));
	 
	// Print the temperature node
	System.out.println("Temperature received from Response " + jsonPathEvaluator.get("Temperature"));
	logger.log(LogStatus.INFO, "Temperature received from Response " + jsonPathEvaluator.get("Temperature"));
	 
	// Print the humidity node
	System.out.println("Humidity received from Response " + jsonPathEvaluator.get("Humidity"));
	logger.log(LogStatus.INFO, "Humidity received from Response " + jsonPathEvaluator.get("Humidity"));
	 
	// Print weather description
	System.out.println("Weather description received from Response " + jsonPathEvaluator.get("Weather"));
	logger.log(LogStatus.INFO, "Weather description received from Response " + jsonPathEvaluator.get("Weather"));
	 
	// Print Wind Speed
	System.out.println("WindSpeed received from Response " + jsonPathEvaluator.get("WindSpeed"));
	logger.log(LogStatus.INFO, "WindSpeed received from Response " + jsonPathEvaluator.get("WindSpeed"));
	 
	// Print Wind Direction Degree
	System.out.println("WindDirectionDegree received from Response " + jsonPathEvaluator.get("WindDirectionDegree"));
	logger.log(LogStatus.INFO, "WindDirectionDegree received from Response " + jsonPathEvaluator.get("WindDirectionDegree"));


}
	
	
@Test(priority=2)
public void GetWeatherDetails002()
{   
	// Specify the base URL to the RESTful web service
	RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

	// Get the RequestSpecification of the request that you want to sent
	// to the server. The server is specified by the BaseURI that we have
	// specified in the above step.
	RequestSpecification httpRequest = RestAssured.given();

	// Make a request to the server by specifying the method Type and the method URL.
	// This will return the Response from the server. Store the response in a variable.
	Response response = httpRequest.request(Method.GET, "/78789798798");

	// Now let us print the body of the message to see what response
	// we have received from the server
	String responseBody = response.getBody().asString();
	System.out.println("Response Body is =>  " + responseBody);
	
	// Get the status code from the Response. In case of 
	 // a successful interaction with the web service, we
	 // should get a status code of 200.
	 int statusCode = response.getStatusCode();
	 System.out.println("Status Code : {"+statusCode+"} returned");
	 
	 // Assert that correct status code is returned.
	 Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
	 

}
	
	
 @Test(priority=3)
public void queryParameter() {

		RestAssured.baseURI ="https://samples.openweathermap.org/data/2.5/"; 
		RequestSpecification request = RestAssured.given();
		
		Response response = request.queryParam("q", "London,UK").queryParam("appid", "2b1fd2d7f77ccf1b7de9b441571b39b8").get("/weather");
		
		String jsonString = response.asString();
		System.out.println(response.getStatusCode()); 
		Assert.assertEquals(jsonString.contains("London"), true);

}


@Test
public void createEmployee(){ 
	
	 RestAssured.baseURI ="http://192.168.194.148:8080/AlertEnterprise/rest/appointment/type";
	 RequestSpecification request = RestAssured.given();
	 
	 JSONObject requestParams = new JSONObject();
	 requestParams.put("name", "Hiielll"); // Cast
	 requestParams.put("description", "It is an API Test");
	 requestParams.put("advDays", "5");
	 requestParams.put("label", "Creating Through API Test");
//	 requestParams.put("appointmentTypeIdentityAction", [{"identityActionName":"Activate AS local user","identityActionId":66}]);
	 requestParams.put("identityActionId", 66);
	 requestParams.put("slotMinutes", "20");
	 
	// requestParams.put("Email",  "sample2ee26d899@gmail.com");
	 request.body(requestParams.toJSONString());
	 Response response = request.post("/create");
	 
	 int statusCode = response.getStatusCode();
	 Assert.assertEquals(statusCode, 200);
	 
	// String successCode = response.jsonPath().get("SuccessCode");
	// Assert.assertEquals(successCode, "Correct Success code was returned", "OPERATION_SUCCESS");
	 
	 System.out.println("Response :" + response.asString());
	 System.out.println("Status Code :" + statusCode);
	 System.out.println("Does Reponse contains 'Rammy'? :" + response.asString().contains("Rammy"));
 
}


@Test
public void createEmployee002() {

    RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";

    String requestBody = "{\n" +
        "  \"name\": \"tammy\",\n" +
        "  \"salary\": \"5000\",\n" +
        "  \"age\": \"20\"\n" +
        "}";


    Response response = null;

    try {
        response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/create");
    } catch (Exception e) {
        e.printStackTrace();
    }

    System.out.println("Response :" + response.asString());
    System.out.println("Status Code :" + response.getStatusCode());
    System.out.println("Does Reponse contains 'tammy'? :" + response.asString().contains("tammy"));
    Assert.assertEquals(200, response.getStatusCode());
}


@Test
public void createEmployee003() {

//    RestAssured.baseURI = "http://192.168.194.148:8080/AlertEnterprise/rest/appointment/type";
//
//    String requestBody = "{\"name\":\"Hiielll\",\"description\":\"It is an API Test\",\"advDays\":\"5\",\"label\":\"Creating Through API Test\",\"appointmentTypeIdentityAction\":[{\"identityActionName\":\"Activate AS local user\",\"identityActionId\":66}],\"slotMinutes\":\"20\"}";
//
//    RestAssured.requestSpecification = new RequestSpecBuilder().
//            setBaseUri("api").
//            setContentType(ContentType.JSON).
//            build().
//            log().all();
//
//    Response response = null;
//
//    try {
//    	response = RestAssured.given().header("X-CSRF-TOKEN", "c8558810-bd87-4d22-a44a-6a9f9e505439").cookie("BD5E25CA489C6FB7413A8A12C0B585F7").contentType("application/json").body(requestBody).post("http://192.168.194.148:8080/AlertEnterprise/rest/appointment/type");
//    }catch (Exception e) {
//        e.printStackTrace();
//    }
//
//    System.out.println("Response :" + response.asString());
//    System.out.println("Status Code :" + response.getStatusCode());
//    System.out.println("Does Reponse contain 'Hiielll'? :" + response.asString().contains("Hiielll"));
//    Assert.assertEquals(200, response.getStatusCode());
    
	RestAssured.baseURI = "http://192.168.194.148:8080/AlertEnterprise/rest/appointment/type";

    String requestBody = "{\"name\":\"Hiiermm\",\"description\":\"It is an API Test\",\"advDays\":\"5\",\"label\":\"Creating Through API Test\",\"appointmentTypeIdentityAction\":[{\"identityActionName\":\"Activate AS local user\",\"identityActionId\":66}],\"slotMinutes\":\"20\"}";
HashMap<String, String> jsessionIdAndCSRFToken=new HashMap<String, String>();
              jsessionIdAndCSRFToken.put("Cookie", "JSESSIONID=49CCA705980B78CC717DF9E0BD59257D");
              jsessionIdAndCSRFToken.put("X-CSRF-TOKEN", "4d1a8ce1-1b4b-46ff-91d0-a1f917ed168b");

    Response response = null;

    try {
        response = RestAssured.given().headers(jsessionIdAndCSRFToken).contentType("application/json").body(requestBody).post("http://192.168.194.148:8080/AlertEnterprise/rest/appointment/type");
    } catch (Exception e) {
        e.printStackTrace();
    }

    System.out.println("Response :" + response.asString());
    System.out.println("Status Code :" + response.getStatusCode());
    System.out.println("Does Reponse contain 'Hiiemmm'? :" + response.asString().contains("Hiiemmm"));
    Assert.assertEquals(response.getStatusCode(),200);

    
    
    
}







@Test
public void UpdateRecords(){
	int empid = 15410;

	RestAssured.baseURI ="http://dummy.restapiexample.com/api/v1";
	RequestSpecification request = RestAssured.given();
	
	JSONObject requestParams = new JSONObject();
	requestParams.put("name", "Zion"); // Cast
	requestParams.put("age", 23);
	requestParams.put("salary", 12000);

	request.body(requestParams.toJSONString());
	Response response = request.put("/update/"+ empid);

	int statusCode = response.getStatusCode();
	System.out.println(response.asString());
	Assert.assertEquals(statusCode, 200); 

}


@Test
public void deleteEmpRecord() {
 
 int empid = 15410;
 
 RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
 RequestSpecification request = RestAssured.given(); 
 
 // Add a header stating the Request body is a JSON
 request.header("Content-Type", "application/json"); 
 
       // Delete the request and check the response
 Response response = request.delete("/delete/"+ empid); 
 
 int statusCode = response.getStatusCode();
 System.out.println(response.asString());
 Assert.assertEquals(statusCode, 200);
 
 String jsonString =response.asString();
 Assert.assertEquals(jsonString.contains("successfully! deleted Records"), true);
 }

@Test
public void postExample()
{
//	String myJson = "{\"name\":\"Jimi Hendrix\"}";
	String myJson = "{\"name\":\"Hiielll\",\"description\":\"It is a API Test\",\"advDays\":\"5\",\"label\":\"Creating Through API Test\",\"appointmentTypeIdentityAction\":[{\"identityActionName\":\"Activate AS local user\",\"identityActionId\":66}],\"slotMinutes\":\"20\"}";
	RestAssured.baseURI  = "http://192.168.194.148:8080/AlertEnterprise/rest/appointment/type";	

	Response r = RestAssured.given().contentType("application/json").
	body("{\"name\":\"Hiielll\"}").
    when().
    post("");

	String body = r.getBody().asString();
	System.out.println(r);
	System.out.println(body);

}













	
}