package de.metro.im.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class provied some custom utility methods for creating dates and times. 
 * @author Christoph Zabinski
 */
public abstract class Time   {
	
	public static boolean isBeforeDateTime(String _timestampA){
		
	DateFormat timeParser = new SimpleDateFormat("dd.MM.yyy HH:mm");
	Date time1, timeNow;
	
	try {
	    
		time1 = timeParser.parse(_timestampA.trim());
		String timeNowStr = new SimpleDateFormat("dd.MM.yyy HH:mm").format(new Date());
		timeNow = timeParser.parse(timeNowStr);
	    
	    if (timeNow.before(time1)) {
	        return true;
	    }
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return false;
	}

	/**
	 * Checks if the current time is between thosw two dates and times. Please provide the time in the format "dd.mm.yyy hh:mm:ss".
	 * @param _timestampA Minimum time. 
	 * @param _timestampB Maximum time.
	 * @return returns true if between _timeA and _timeB.
	 */
	public static boolean isBetweenDateTime(String _timestampA, String _timestampB){
		
	DateFormat timeParser = new SimpleDateFormat("dd.MM.yyy HH:mm");
	Date time1, time2, timeNow;
	
	try {
	    
		time1 = timeParser.parse(_timestampA.trim());
		time2 = timeParser.parse(_timestampB.trim());
		String timeNowStr = new SimpleDateFormat("dd.MM.yyy HH:mm").format(new Date());
		timeNow = timeParser.parse(timeNowStr);
	    
	    if (timeNow.after(time1) && timeNow.before(time2)) {
//	    	System.out.println(timeNow + " liegt zwischen " + time1 + " und " + time2);
	        return true;
	    }
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return false;
	}
	
	public static boolean isBetweenExactTime(String _timeA, String _timeB){
		/**
		 * Info für diese Methode geholt von:
		 * http://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
		 * http://stackoverflow.com/questions/17697908/check-if-a-given-time-lies-between-two-times-regardless-of-date
		 * http://stackoverflow.com/questions/2654025/how-to-get-year-month-day-hours-minutes-seconds-and-milliseconds-of-the-cur
		 * http://stackoverflow.com/questions/2309558/time-comparison
		 */
		DateFormat timeParser = new SimpleDateFormat("HH:mm:ss");
		
		Date time1, time2, timeNow;
		
		try {
		    
			time1 = timeParser.parse(_timeA.trim());
			time2 = timeParser.parse(_timeB.trim());
			String timeNowStr = new SimpleDateFormat("HH:mm:ss").format(new Date());
			timeNow = timeParser.parse(timeNowStr);
		    
		    if (timeNow.after(time1) && timeNow.before(time2)) {
//		    	System.out.println(timeNow + " liegt zwischen " + time1 + " und " + time2);
		        return true;
		    }
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isBetweenMinutes(String timeA, String timeB){
		/**
		 * Info für diese Methode geholt von:
		 * http://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
		 * http://stackoverflow.com/questions/17697908/check-if-a-given-time-lies-between-two-times-regardless-of-date
		 * http://stackoverflow.com/questions/2654025/how-to-get-year-month-day-hours-minutes-seconds-and-milliseconds-of-the-cur
		 */
		try {
//			DateFormat dateFormat = new SimpleDateFormat("mm");
			String now = new SimpleDateFormat("mm").format(new Date()); 
			
		    Date time1 = new SimpleDateFormat("mm").parse(timeA);
		    Calendar ctimeA = Calendar.getInstance();
		    
		    ctimeA.setTime(time1);

		    Date time2 = new SimpleDateFormat("mm").parse(timeB);
		    Calendar ctimeB = Calendar.getInstance();
		    
		    ctimeB.setTime(time2);
		    ctimeB.add(Calendar.DATE, 1);

		    
		    Date d = new SimpleDateFormat("mm").parse(now);
		    Calendar calendar3 = Calendar.getInstance();
		    calendar3.setTime(d);
		    calendar3.add(Calendar.DATE, 1);
		    
		    Date time = calendar3.getTime();
		    
		    if (time.after(ctimeA.getTime()) && time.before(ctimeB.getTime())) {
		        return true;
		    }
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Creates a string representing the current date with dd.MM.yyyy . 
	 * @return Returns the current date as string.
	 */
	public static String getDate(){
		return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
	}
	
	/**
	 * Creates a string representing the current time with hours and minutes in format HHmm. 
	 * @return Returns the current time as string.
	 */
	public static String getTime(){
		return new SimpleDateFormat("HHmm").format(new Date());
	}
	
	/**
	 * Returns a timestamp representing the current time and date in a human readable format:
	 * @return Date format:  dd.MM.yyyy hh:mm:ss
	 */
	public static String readableTimestamp(boolean _stripDotsAndSpaces){
		if(_stripDotsAndSpaces) {return new SimpleDateFormat("ddMMyyyy-hhmmss").format(new Date());} else {
		return new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(new Date());}
	}
}
