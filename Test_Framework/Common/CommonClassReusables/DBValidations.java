package CommonClassReusables;

import java.util.ArrayList;
import com.relevantcodes.extentreports.LogStatus;


;

public class DBValidations extends BrowserSelection{
	
	public static String returnApplicationDateFormat() {
		String dateFormat = "";
		try {
			ArrayList<ArrayList<Object>> _dateFormatResults = MsSql.getResultsFromDatabase("select config_param_value from AFBMAPP_CONFIG where CONFIG_PARAM_NAME = 'DATE_FORMAT_LABEL'");
			dateFormat = String.valueOf(_dateFormatResults.get(0).get(0));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.log(LogStatus.ERROR, e);
		}
		return dateFormat;

	}

	public static String returnApplicationUIDateFormat() {
		String dateFormat = "";
		try {
			ArrayList<ArrayList<Object>> _dateFormatResults = MsSql
					.getResultsFromDatabase("select CUSTOM_VALUE, RESOURCE_VALUE, CASE WHEN CUSTOM_VALUE is NULL THEN RESOURCE_VALUE ELSE CUSTOM_VALUE END AS DATEFORMAT from AFBCRES_BUNDLE where resource_key like 'DATE.FORMAT.JAVA'");
			dateFormat = String.valueOf(_dateFormatResults.get(0).get(2));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.log(LogStatus.ERROR, e);
		}
		return dateFormat;

	}


	
	public static String returnApplicationTimeFormat() {
 		String timeFormat = "";
 		try {
 			ArrayList<ArrayList<Object>> _timeFormatResults = MsSql
 					.getResultsFromDatabase("select config_param_value from AFBMAPP_CONFIG where CONFIG_PARAM_NAME = 'TIME_FORMAT_LABEL'");
 			if(_timeFormatResults != null && !_timeFormatResults.isEmpty()){
 				timeFormat = String.valueOf(_timeFormatResults.get(0).get(0));
 			}
 		} catch (Exception e) {
 			System.err.println(e.getMessage());
 			logger.log(LogStatus.ERROR, e);
 		}
 		return timeFormat;

 	}
	

}
