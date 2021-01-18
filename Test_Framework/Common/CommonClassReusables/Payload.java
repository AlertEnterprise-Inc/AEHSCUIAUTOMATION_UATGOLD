package CommonClassReusables;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.relevantcodes.extentreports.LogStatus;

public class Payload {
	public static String accessTokenJson(String userName,String password) {
		String payload="\r\n" + 
				"{\r\n" + 
				"    \"username\": \""+userName+"\",\r\n" + 
				"    \"password\": \""+password+"\"\r\n" + 
				"}";
		return payload;
	}

	public static String createIdentityJson(String identityDataFile,String identitySystemDataFile) throws Throwable {
		String payload="";
		String identityPayload="";
		String identitySystemPayload="";
		try {
			ArrayList<ArrayList<String>> identityCsvData = TestDataEngine.getCSVData(identityDataFile, 0);
			ArrayList<String> identityHeaders = identityCsvData.get(0);
			identityPayload="{\r\n" + 
					"  \"identity\": {";
			String payloadI1="";
			for(int i=0;i<identityHeaders.size();i++) {
				ArrayList<String> identityList=TestDataEngine.getCSVColumnPerHeader(identityDataFile, identityHeaders.get(i));
				if(identityHeaders.get(i).equalsIgnoreCase("validFrom")) {
					String validFrom=identityList.get(0);
					Calendar c = Calendar.getInstance();
					DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
					Date date = new Date();
					c.setTime(dateFormat.parse(validFrom));
					validFrom = new SimpleDateFormat("dd-MM-yyyy").format(date); 
					String payload2="\r\n" + 
							"     \""+identityHeaders.get(i)+"\": \""+validFrom+"\"";
					if(i!=identityHeaders.size()-1) {
						payloadI1=payloadI1+payload2+",";
					}
					else {
						payloadI1=payloadI1+payload2;
					}
				}
				else if(identityHeaders.get(i).equalsIgnoreCase("validTo")) {
					String validTo=identityList.get(0);
					Calendar c = Calendar.getInstance();
					DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
					Date date = new Date();
					c.setTime(dateFormat.parse(validTo));
					validTo = new SimpleDateFormat("dd-MM-yyyy").format(date); 
					String payload2="\r\n" + 
							"     \""+identityHeaders.get(i)+"\": \""+validTo+"\"";
					if(i!=identityHeaders.size()-1) {
						payloadI1=payloadI1+payload2+",";
					}
					else {
						payloadI1=payloadI1+payload2;
					}
				}
				else {
					String payload2="\r\n" + 
							"     \""+identityHeaders.get(i)+"\": \""+identityList.get(0)+"\"";
					if(i!=identityHeaders.size()-1) {
						payloadI1=payloadI1+payload2+",";
					}
					else {
						payloadI1=payloadI1+payload2;
					}
				}
			}
			identityPayload=identityPayload+payloadI1+"\r\n" + 
					"  }";
		
			ArrayList<ArrayList<String>> identitySystemsvData = TestDataEngine.getCSVData(identitySystemDataFile, 0);
			ArrayList<String> identitySystemHeaders = identitySystemsvData.get(0);
			identitySystemPayload=",\r\n" + 
					"  \"identitySystem\": [\r\n" + 
					"    {";
			String payloadIS1="";
			for(int i=0;i<identitySystemHeaders.size();i++) {
				ArrayList<String> identitySystemList=TestDataEngine.getCSVColumnPerHeader(identitySystemDataFile, identitySystemHeaders.get(i));
				if(identitySystemHeaders.get(i).equalsIgnoreCase("validFrom")) {
					String validFrom=identitySystemList.get(0);
					Calendar c = Calendar.getInstance();
					DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
					Date date = new Date();
					c.setTime(dateFormat.parse(validFrom));
					validFrom = new SimpleDateFormat("dd-MM-yyyy").format(date); 
					String payloadIS2="\r\n" + 
							"     \""+identitySystemHeaders.get(i)+"\": \""+validFrom+"\"";
					if(i!=identitySystemHeaders.size()-1) {
						payloadIS1=payloadIS1+payloadIS2+",";
					}
					else {
						payloadIS1=payloadIS1+payloadIS2;
					}
				}
				else {
				String payloadIS2=" \r\n" + 
						"       \""+identitySystemHeaders.get(i)+"\": \""+identitySystemList.get(0)+"\"";
				if(i!=identitySystemHeaders.size()-1) {
					payloadIS1=payloadIS1+payloadIS2+",";
				}
				else {
					payloadIS1=payloadIS1+payloadIS2;
				}
				}
			}
			identitySystemPayload=identitySystemPayload+payloadIS1+"\r\n" + 
					"    }\r\n" + 
					"  ]}";
			payload=identityPayload+identitySystemPayload;
			
//			payload=payload+"\r\n" + 
//					"   \"requestorId\": \"Steven.Smith847276\",\r\n" + 
//					"   \"requestUuid\": \"RequestUuid9080\"\r\n" + 
//							"}";
		}
	
		catch(Exception e) {
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
			Utility.recoveryScenario(nameofCurrMethod, e);
		}
		return payload;
	}
}
