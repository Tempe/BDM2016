package dk.itu.preprocessing.PreProcessing.Cleaning;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import dk.itu.preprocessing.PreProcessing.Entities.Reading;
import dk.itu.preprocessing.PreProcessing.Entities.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.io.*;


public class DataCleaner {
	/*public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		// the final output array - [{obj_0},{obj_1},{...}], where obj_i = uuid, readings
		JSONArray output = new JSONArray();
		
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

		JSONArray devices = (JSONArray) new JSONParser().parse(scanner);
		
		long totalTotalLogon = 0;
		//Read values into ArrayList.
    	for(int m = 0; m < devices.size(); m++){

			JSONObject readingsObj = (JSONObject) devices.get(m); 
			String uuid = (String) readingsObj.get("uuid");
			JSONArray readings = (JSONArray) readingsObj.get("Readings");
			
			ArrayList<Reading> tempReadings = new ArrayList<Reading>();

	    	long totalLogons = 0;
	    	
	    	for(int i = 0; i < readings.size(); i++){
	    		JSONArray readingInput = (JSONArray) readings.get(i);
	    		Reading reading = new Reading();
	    		try{
	    			reading.Timestamp = ((Long)readingInput.get(0)).longValue();
	    		} catch(java.lang.ClassCastException e){
	    			reading.Timestamp = (long) ((Double)readingInput.get(0)).doubleValue();
	    			// TODO: Handle.
	    			System.err.println(uuid + " could not be converted:" + e.getStackTrace());
	    		}
	    		try{
	    			reading.Logons = ((Long)readingInput.get(1)).longValue(); 
	    		} catch(java.lang.ClassCastException e){
	    			reading.Logons = (long) ((Double)readingInput.get(1)).doubleValue();
	    		}
	    		tempReadings.add(reading);
	    		if ((i == 0 || hasValidTimestamp(tempReadings.get(i-1).Timestamp, tempReadings.get(i).Timestamp))
	    				&& hasValidLogons(tempReadings.get(i).Logons)
	    				) {
	    			totalLogons += tempReadings.get(i).Logons;
	    			
	    		}

	    	}
	    	
	    	int outlierRange = 2;
	    	int variance = 5;
			//Check for outliers
	    	for(int i = 0; i < tempReadings.size(); i++) {
	    		int rangeSum = 0;
	    		int arrayStart = i - outlierRange < 0 ? 0 : i - outlierRange; 
	    		int arrayEnd = i + outlierRange <= tempReadings.size() ? i + outlierRange : tempReadings.size(); 
	    		
	    		for(int j = arrayStart; j < arrayEnd; j++) {
	    			rangeSum += tempReadings.get(j).Logons;
	    		}
	    		int mean = rangeSum / (arrayEnd - arrayStart);
	    		if(variance * mean - tempReadings.get(i).Logons < 0) {
	    			tempReadings.get(i).status = Status.OUTLIER;
	    		}
	    	}
	    	
	    	//Check for missing values. Write into jsonarray simultaneously?
	    	ArrayList<Reading> finalReadings = new ArrayList<Reading>();
	    	finalReadings.add(tempReadings.get(0));
	    	for(int k = 1; k < tempReadings.size(); k++) {
	    		long timestampDiff = finalReadings.get(k-1).Timestamp - finalReadings.get(k).Timestamp;
	    		if(timestampDiff > 120_000) {
	    			for(long d = finalReadings.get(k-1).Timestamp; d < finalReadings.get(k).Timestamp; d+= 60_000) {
	    				Reading missingReading = new Reading();
	    				missingReading.Logons = 0;
	    				missingReading.Timestamp = d;
	    				missingReading.status = Status.MISSING;
	    				finalReadings.add(missingReading);
	    			}
	    			
	    		}
	    		finalReadings.add(tempReadings.get(k));
	    	}
	    	totalTotalLogon += totalLogons;
	    	
	    	
    		
    		JSONObject outputObj = new JSONObject();
			JSONArray outputArray = new JSONArray();	// readings-values for output, temporary holder
    		
			
    		add object to output array
    		outputArray.add(reading);
	    	
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
	}*/
}