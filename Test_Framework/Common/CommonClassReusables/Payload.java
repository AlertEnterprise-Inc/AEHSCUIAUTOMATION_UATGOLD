package CommonClassReusables;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


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

	
	public static String createIdentityJson(String firstName, String lastName, String email, String city, String workerType, String sysCode, String position) throws Throwable {
		String payload="";
		String identityPayload="";
		String identitySystemPayload="";
		try {
			ArrayList<String> identityHeaders = new ArrayList<String>();
			String h1 ="firstName";
			String h2 ="lastName";
			String h3 ="workerType";
			String h4 = "masterIdentityId";
			String h5 = "validFrom";
			String h6 = "validTo";
			String h7 = "email";
			String h8 = "city";
			String h9 = "gbId";
			String h10 = "position";
			identityHeaders.add(h1);
			identityHeaders.add(h2);
			identityHeaders.add(h3);
			identityHeaders.add(h4);
			identityHeaders.add(h5);
			identityHeaders.add(h6);
			identityHeaders.add(h7);
			identityHeaders.add(h8);
			identityHeaders.add(h9);
			identityHeaders.add(h10);
			HashMap<String, String> map = new HashMap<>();
			 map.put("firstName", firstName);
			 map.put("lastName", lastName);
			 map.put("workerType", workerType);
			 map.put("masterIdentityId", ""+Utility.getRandomNumber(6));
			 map.put("validFrom", "");
			 map.put("validTo", "");
			 map.put("email", email);
			 map.put("city", city);
			 map.put("gbId", ""+Utility.getRandomNumber(2));
			 map.put("position", position);
			
			identityPayload="{\r\n" + 
					"  \"identity\": {";
			String payloadI1="";
			for(int i=0;i<identityHeaders.size();i++) {
				if(identityHeaders.get(i).equalsIgnoreCase("validFrom")) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDateTime now = LocalDateTime.now();
					String validFrom = dtf.format(now);
					/*Calendar c = Calendar.getInstance();
					DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
					Date date = new Date();
					c.setTime(dateFormat.parse(validFrom));
					validFrom = new SimpleDateFormat("dd-MM-yyyy").format(date); */
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
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDateTime now = LocalDateTime.now();
					String validTo = dtf.format(now);
					/*Calendar c = Calendar.getInstance();
					DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
					Date date = new Date();
					c.setTime(dateFormat.parse(validTo));
					validTo = new SimpleDateFormat("dd-MM-yyyy").format(date); */
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
							"     \""+identityHeaders.get(i)+"\": \""+map.get(identityHeaders.get(i))+"\"";
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
		
			ArrayList<String> identitySystemHeaders = new ArrayList<String>();
			String l1="systemIdentifier";
			String l2="validFrom";
			String l3="antiPassBack";
			identitySystemHeaders.add(l1);
			identitySystemHeaders.add(l2);
			identitySystemHeaders.add(l3);
			HashMap<String, String> map1 = new HashMap<>();
			 map1.put("systemIdentifier", sysCode);
			 map1.put("validFrom", "");
			 map1.put("antiPassBack", "false");
			identitySystemPayload=",\r\n" + 
					"  \"identitySystem\": [\r\n" + 
					"    {";
			String payloadIS1="";
			for(int i=0;i<identitySystemHeaders.size();i++) {
				
				if(identitySystemHeaders.get(i).equalsIgnoreCase("validFrom")) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDateTime now = LocalDateTime.now();
					String validFrom = dtf.format(now);
					/*Calendar c = Calendar.getInstance();
					DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
					Date date = new Date();
					c.setTime(dateFormat.parse(validFrom));
					validFrom = new SimpleDateFormat("dd-MM-yyyy").format(date); */
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
						"       \""+identitySystemHeaders.get(i)+"\": \""+map1.get(identitySystemHeaders.get(i))+"\"";
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
			
			
			
			

		}
	
		catch(Exception e) {
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
			Utility.recoveryScenario(nameofCurrMethod, e);
		}
		return payload;
	}
}
