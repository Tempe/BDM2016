package cleaners;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.*;


public class DataCleaner2 {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		// the final output array - [{obj_0},{obj_1},{...}], where obj_i = uuid, readings
		JSONArray output = new JSONArray();
		
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

		JSONArray devices = (JSONArray) new JSONParser().parse(scanner);
		
		long totalTotalLogon = 0;

    	for(int m = 0; m < devices.size(); m++){
    		JSONObject outputObj = new JSONObject();
			JSONArray outputArray = new JSONArray();	// readings-values for output, temporary holder
		
			JSONObject readingsObj = (JSONObject) devices.get(m); 
			String uuid = (String) readingsObj.get("uuid");
			JSONArray readings = (JSONArray) readingsObj.get("Readings");
			
			// For analysis: 
	    	long[] timestamp = new long[readings.size()+1];
	    	long[] logons = new long[readings.size()+1];
	    	long totalLogons = 0;
	    	
	    	for(int i = 0; i < readings.size(); i++){
	    		JSONArray reading = (JSONArray) readings.get(i);
	    		try{
	    			timestamp[i] = (long) reading.get(0);
	    		} catch(java.lang.ClassCastException e){
	    			timestamp[i] = (long) (double)reading.get(0);
	    			// TODO: Handle.
	    			System.err.println(uuid + " could not be converted:" + e.getStackTrace());
	    		}
	    		try{
	    			logons[i] = (long) reading.get(1);
	    		} catch(java.lang.ClassCastException e){
	    			logons[i] = (long) (double) reading.get(1);
	    		}
	    		
	    		if ((i == 0 || hasValidTimestamp(timestamp[i-1], timestamp[i]))
	    				&& hasValidLogons(logons[i])
	    				) {
	    			totalLogons += logons[i];
	    			outputArray.add(reading);	// add object to output array
	    		}
	    	}
			
	    	totalTotalLogon += totalLogons;
	    	
	    	outputObj.put("Readings", outputArray);
	    	outputObj.put("uuid", uuid);

	    	
	    	output.add(outputObj);
		}
		
		System.out.print(output.toJSONString());
		scanner.close();
	}
	
	private static boolean hasValidLogons(long logons){
		return logons >= 0;
	}
	
	// 60 seconds should have passed since previous.
	private static boolean hasValidTimestamp(long previous, long current){
		return current - previous >= 58000;
	}
}