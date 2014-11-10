package fantasticfour.magiceight.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
    private final static SimpleDateFormat[] DATE_FORMAT = {
        new SimpleDateFormat("d/M/y"),
        new SimpleDateFormat("d-M-y"),
        new SimpleDateFormat("E")
    };
    
    private final static SimpleDateFormat[] TIME_FORMAT = {
    	new SimpleDateFormat("HH:mm:ss"),
    	new SimpleDateFormat("HH:mm:ss aa"),
    	new SimpleDateFormat("HH:mm"),
    	new SimpleDateFormat("HH:mm aa"),
    	new SimpleDateFormat("HHaa")
    };
    
    public static Calendar parseString(String dates, boolean endTime) {
    	Calendar cal = Calendar.getInstance();
    	
    	Calendar cDate = null;
    	Calendar cTime = null;
    	
    	if(dates.equalsIgnoreCase("current")) {
    		return Calendar.getInstance();
    	} else if (dates.equalsIgnoreCase("none")) {
    		return null;
    	}
    	
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	
    	for(String str : dates.split(",")) {       
    		if (str.equalsIgnoreCase("tomorrow")) {
        		cal.add(Calendar.DATE, 1);
        		break;
        	} else if(str.equalsIgnoreCase("yesterday")) {
        		cal.add(Calendar.DATE, -1);
        		break;
        	} else if(str.equalsIgnoreCase("today")) {
        		break;
        	}
        	
    		for(SimpleDateFormat df : DATE_FORMAT) {
    			try {
    				cDate = Calendar.getInstance();
    				cDate.setTime(df.parse(str));
    				break;
    			} catch (ParseException e) {
    				cDate = null;
    			}    			
    		}
    		
    		for(SimpleDateFormat tf : TIME_FORMAT) {
    			try {
    				cTime = Calendar.getInstance();
    				cTime.setTime(tf.parse(str));
    				break;
    			} catch (ParseException e) {
    				cTime = null;
    			}
    		}
    	}
    	
    	cal.add(Calendar.DATE, 1);
    	
    	if(cDate != null) {
    		if(cDate.get(Calendar.YEAR) != 1970)
    			cal.set(Calendar.YEAR, cDate.get(Calendar.YEAR));
    		if(cDate.get(Calendar.MONTH) != 0)
    			cal.set(Calendar.MONTH, cDate.get(Calendar.MONTH));
    		cal.set(Calendar.DAY_OF_MONTH, cDate.get(Calendar.DAY_OF_MONTH));
    	}
    	
    	if(cTime != null) {
    		if(endTime) {
    			cal.add(Calendar.DATE, -1);
    		}
    		cal.set(Calendar.HOUR_OF_DAY,  cTime.get(Calendar.HOUR_OF_DAY));
    		cal.set(Calendar.MINUTE, cTime.get(Calendar.MINUTE));
    		cal.set(Calendar.SECOND, cTime.get(Calendar.SECOND));
    	}
    	
    	return cal;
    }
}
