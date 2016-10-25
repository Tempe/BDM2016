import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import java.io.FileWriter;
import java.io.IOException;

import java.io.*;
import java.util.Iterator;


public class DataCleaner {
	
	static JSONArray output = new JSONArray();				// the final output array - [{obj_0},{obj_1},{...}], where obj_i = uuid, readings
	static JSONObject outputObj = new JSONObject();

	public static void main(String[] args) {
		
		boolean skipTime = false;
		JSONParser parser = new JSONParser();
		JSONArray device = new JSONArray();
		if(args.length != 2) {
			System.out.println("Must have input and output params.");
			return;
		}
		String inputFile = args[0];
		String outputFile = args[1];
		try{
			try {
				device = (JSONArray) parser.parse(new FileReader(inputFile));
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
		
		long totalTotalLogon = 0;
		Iterator<String> iterator1 = device.iterator();
		
		int m = 0;
		while (iterator1.hasNext() && m < device.size()) {
			
			JSONArray outputArray = new JSONArray();	// readings-values for output, temporary holder
		
			JSONObject readingsObj = (JSONObject) device.get(m); 
			String uuid = (String) readingsObj.get("uuid");
			JSONArray readings = (JSONArray) readingsObj.get("Readings");
			
			JSONArray values;
	    	
			// For analysis: 
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
	    		//TODO: Should problably be sorted first? Might not be sorted. Maybe.
	    		if (i > 0) {				
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
	    	
	    	outputObj.put("uuid", uuid);
	    	outputObj.put("Readings", outputArray);
	    	
	    	output.add(outputObj);
			m += 1;
		}
		
		System.out.println("Done with " + totalTotalLogon);
		
		try {
			FileWriter file = new FileWriter(outputFile);
			file.write(output.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}