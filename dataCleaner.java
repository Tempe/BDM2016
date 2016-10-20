import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;


public class dataCleaner {

	public static void main(String[] args) {
		
		JSONParser parser = new JSONParser();
		JSONArray device = new JSONArray();
		
		try{
			try {
				device = (JSONArray) parser.parse(new FileReader("C:/Users/BA9931/Desktop/2016-10-04.json"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			
		}
		
		Iterator<String> iterator1 = device.iterator();
		
		int m = 0;
		while (iterator1.hasNext() && m < device.size()) {
		
			JSONObject readingsObj = (JSONObject) device.get(m); 
			
			String uuid = (String) readingsObj.get("uuid");
			JSONArray readings = (JSONArray) readingsObj.get("Readings");
			
			// JSONObject uuid = (JSONObject) device.get(1);
			
			System.out.println("Readings = " + readings);
			System.out.println("uuid = " + uuid);
			
			JSONArray values;
	    	
	    	long[] timestamp = new long[readings.size()+1];
	    	long[] logons = new long[readings.size()+1];
	    	
	    	int totalLogons = 0;
	    	
	    	Iterator<String> iterator2 = readings.iterator();
	    	
	    	int i = 0;
	    	while(iterator2.hasNext() && i < readings.size()) {
	    		values = (JSONArray) readings.get(i);
	    		
	    		timestamp[i] = (long) values.get(0);
	    		logons[i] = (long) values.get(1);
	    		
	    		//System.out.println("Values " + values);
	    		//System.out.println("Timestamp " + timestamp[i] + " logons " + logons[i]);
	    		
	    		if (i > 0) {							// 30 sec
		    		if (timestamp[i] - timestamp[i-1] < 30000) {
		    			System.out.println("Input " + i + " should be erased");
		    		}
	    		}
	    		
	    		totalLogons += logons[i];
	    		
	    		i += 1;
	    	}
	    	
	    	System.out.println("Total logons today = " + totalLogons + " on " + uuid);
			
			m += 1;
		}
		
		System.out.println("Done");
	}
	
}