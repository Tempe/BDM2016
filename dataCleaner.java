import org.json.*;

import java.io.*;
import java.util.Iterator;


public class dataCleaner {

	public static void main(String[] args) throws JSONException {

        	String input = "{\"uuid\":\"e5c91e0d-6515-5b25-adc0-55743c5d371d\",\"Readings\":[[1475479748000,1],[1475479748000,1],[1475479808000,1],[1475479808000,1],[1475479868000,0],[1475480335000,1],[1475480395000,1],[1475480455000,1],[1475480515000,2],[1475480575000,2],[1475480635000,2],[1475480695000,2],[1475480755000,2],[1475480816000,1],[1475480875000,1],[1475480935000,1],[1475480995000,1],[1475481055000,3],[1475481115000,2],[1475481175000,3],[1475481235000,4],[1475481295000,4],[1475481355000,4],[1475481415000,7],[1475481475000,14],[1475482953000,37],[1475482954000,38],[1475483421000,38],[1475483519000,40],[1475484505000,42],[1475484579000,44],[1475484639000,45],[1475484699000,45],[1475484759000,45],[1475484819000,44],[1475484879000,44],[1475484939000,33],[1475484999000,31],[1475485059000,24],[1475485119000,23],[1475485179000,23],[1475485239000,23],[1475485299000,22],[1475485359000,23],[1475485359000,23],[1475488022000,7],[1475488023000,7],[1475488259000,11],[1475488260000,11],[1475488775000,36],[1475489001000,55],[1475489116000,64],[1475489177000,66],[1475489237000,66],[1475489297000,67],[1475489357000,65],[1475489417000,67],[1475489477000,66],[1475489537000,63],[1475489597000,63],[1475493357000,61]]}";
        			//new JSONObject("/Users/BA9931/Desktop/dataTest.txt");
        	
        	JSONObject timeSeries = new JSONObject(input);
        	
        	String uuid = timeSeries.getString("uuid");
        	
        	System.out.println("uuid = " + uuid);
   
    		JSONArray readings = timeSeries.getJSONArray("Readings");
    		
    		System.out.println("Readings = " + readings);
    		
    		JSONArray values;
    		
    		long[] timestamp = new long[readings.length()];
    		int[] logons = new int[readings.length()];
    		
    		int totalLogons = 0;
    		
    		for (int i = 0; i < readings.length(); i++) {
    			values = readings.getJSONArray(i);
    			
    			timestamp[i] = values.getLong(0);
    			logons[i] = values.getInt(1);
    			
    			System.out.println("Values " + values);
    			System.out.println("Timestamp " + timestamp[i] + " logons " + logons[i]);
    			
    			totalLogons += logons[i]; 
    		}
    		
    		System.out.println("Total logons today = " + totalLogons);
	}
	
}