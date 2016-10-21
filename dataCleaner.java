import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import java.io.FileWriter;
import java.io.IOException;

import java.io.*;
import java.util.Iterator;


// for output look at https://www.mkyong.com/java/json-simple-example-read-and-write-json/

public class dataCleaner {

	public static void main(String[] args) {
		
		boolean skipTime = false;
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
		
		int totalTotalLogon = 0;
		Iterator<String> iterator1 = device.iterator();
		
		int m = 0;
		while (iterator1.hasNext() && m < device.size()) {
			
			JSONArray outputArray = new JSONArray();
			JSONObject outputUuid = new JSONObject();
		
			JSONObject readingsObj = (JSONObject) device.get(m); 
			
			String uuid = (String) readingsObj.get("uuid");
			JSONArray readings = (JSONArray) readingsObj.get("Readings");
			
			outputUuid.put("uuid", uuid);
			
			// JSONObject uuid = (JSONObject) device.get(1);
			
			System.out.println("Readings = " + readings);
			System.out.println("uuid = " + uuid);
			
			JSONArray values;
	    	
	    	long[] timestamp = new long[readings.size()+1];
	    	long[] logons = new long[readings.size()+1];
	    	
	    	long totalLogons = 0;
	    	
	    	Iterator<String> iterator2 = readings.iterator();
	    	
	    	int i = 0;
	    	while(iterator2.hasNext() && i < readings.size()) {
	    		values = (JSONArray) readings.get(i);
	    		
	    		timestamp[i] = (long) values.get(0);
	    		logons[i] = (long) values.get(1);
	    		
	    		//System.out.println("Values " + values);
	    		//System.out.println("Timestamp " + timestamp[i] + " logons " + logons[i]);
	    		
	    		if (i > 0) {				
		    		if (timestamp[i] - timestamp[i-1] == 0) {
		    			// they are equal, skip timestamp
		    			skipTime = true;
		    		}
		    											// 1 minute
		    		if (timestamp[i] - timestamp[i-1] < 60000) {
		    			// there is less than 1 minute between the two timestamps
		    			skipTime = true;
		    		}
	    		}
	    		
	    		if (!skipTime) {
	    			totalLogons += logons[i];
	    			
	    			outputArray.add(values);	// add object to output array
	    		}
	    		
	    		skipTime = false; // reset
	    		i += 1;
	    	}
	    	
	    	System.out.println("Total logons today = " + totalLogons + " on " + uuid);
			
	    	totalTotalLogon += totalLogons;
	    	
			m += 1;
			
			System.out.println("Output " + outputArray);
			//writeToFile
		}
		
		System.out.println("Done with " + totalTotalLogon);
	}
}