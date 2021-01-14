package CommonClassReusables;


import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.github.javafaker.Faker;

public class DataGenNew {
	private static Faker faker =  new Faker();
    public static String autoFirstName() {
    	Faker faker;
    	HashMap <Integer, String> locale= new HashMap<Integer,String>();
	    locale.put(0,"en-AU");
	    locale.put(1,"en-CA");
	    locale.put(2,"en-BORK");
	    locale.put(3,"en-CA");
        locale.put(4,"en-GB");
        locale.put(5,"en-IND");
        locale.put(6,"en-MS");
        locale.put(7,"en-NG");
        locale.put(8,"en-NZ");
        locale.put(9,"en-SG");
        locale.put(10,"en-UG");
        locale.put(11,"en-US");
        locale.put(12,"en-ZA");
        Random r = new Random();
        int ran = r.nextInt(locale.size());
		faker = new Faker(new Locale(locale.get(ran))); 
		return faker.name().firstName(); 
    }

    public String autoLastName() {
    	Faker faker = new Faker(new Locale("EN-IND"));
    	return faker.name().lastName();
    }

    public static String autoUserId(int len) {
    	Faker faker = new Faker();
    	long n = faker.number().randomNumber(len-1, true);
    	String num = Long.toString(n);
    	String id = "U" + num;
    	return id;
    }
    
    public static void test() {
    	System.out.println("Testing");
    }

    public static String autoPhoneNumber() {
    	Faker faker = new Faker();
    	return faker.phoneNumber().phoneNumber();
    }

    public static long autoNumber(int digits) {
    	Faker faker = new Faker();
    	return faker.number().randomNumber(digits, true);
    }
    
    public String autoCity() {
    	Faker faker = new Faker();
    	return faker.address().city();
    }

    public static String autoCountry() {
    	Faker faker = new Faker();
    	return faker.address().country();
    }

    public static String autoStreetName() {
    	Faker faker = new Faker();
    	return faker.address().streetName();
    }
    
    public static String autoSentence() {
    	int n = (int) autoNumber(1);
    	System.out.println(n);
    	String s="null";
    	switch (n) {
    		case 0:
    			s= faker.shakespeare().hamletQuote();
    			break;
    		case 1:
    			s= faker.shakespeare().asYouLikeItQuote();
    			break;
    		case 2:
    			s= faker.shakespeare().kingRichardIIIQuote();
    			break;
    		case 3:
    			s= faker.hitchhikersGuideToTheGalaxy().quote();
    		default:
    			s= faker.yoda().quote();
    			break;
    	}
    	return s;
    }
    
    public static String epochNow() {
    	return Long.toString(System.currentTimeMillis());	
    }

    public static String convertEpochToDateFormat(String epoch, String format, String timezone) {
    	long timeInMillisL = Long.parseLong(epoch);
    	DateTime _date = new DateTime( timeInMillisL );

    	DateTimeZone ToTimeZone = DateTimeZone.forID(timezone);

    	DateTimeFormatter fmt = DateTimeFormat.forPattern(format).withZone(ToTimeZone);
    	
    	String str = fmt.print(_date);
    	System.out.println(str);
    	return str;
    }
    
    public static String epochXDaysAhead(int n) {
    	long epoch_now = System.currentTimeMillis();
    	DateTime _date = new DateTime( epoch_now );
    	_date = _date.plusDays(n);
    	
    	long epoch_n_days_ahead = _date.getMillis();
    	return Long.toString(epoch_n_days_ahead);
    }
    
    public String epochXDaysAhead(int n, String epoch) {
    	long millis = Long.parseLong(epoch);
    	DateTime _date = new DateTime(millis);
    	_date = _date.plusDays(n);
    	
    	long epoch_n_days_ahead = _date.getMillis();
    	return Long.toString(epoch_n_days_ahead);
    }
    
    public String epochCustomDateTimeGivenTZ(String inTimezone,int hrs, int mins, int secs, int ms) {
    	
    	DateTimeZone zone = DateTimeZone.forID(inTimezone);
    	DateTime dt = new DateTime(zone);
    	int day = dt.getDayOfMonth();
    	int year = dt.getYear();
    	int month = dt.getMonthOfYear();
    	
    	DateTimeZone epochZone = DateTimeZone.forID("GMT");
    	DateTime desiredDate = new DateTime(year, month, day, hrs, mins, secs, ms, zone);
    	
        DateTime epochTs = desiredDate.withZone(epochZone);    	
    	long epochTime = epochTs.getMillis();
    	return Long.toString(epochTime);
    }
    
    public static String epochCustomTimeGivenTZ(String inTimezone,int day, int month, int year, int hrs, int mins, int secs, int ms) {
    	
    	DateTimeZone zone = DateTimeZone.forID(inTimezone);
    	
    	DateTimeZone epochZone = DateTimeZone.forID("GMT");
    	DateTime desiredDate = new DateTime(year, month, day, hrs, mins, secs, ms, zone);
    	
        DateTime epochTs = desiredDate.withZone(epochZone);    	
    	long epochTime = epochTs.getMillis();
    	return Long.toString(epochTime);
    }
}

