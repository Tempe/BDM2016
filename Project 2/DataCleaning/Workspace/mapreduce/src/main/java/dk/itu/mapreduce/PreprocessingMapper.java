package dk.itu.mapreduce;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PreprocessingMapper extends Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// send key and value to reducer
		String json = value.toString();
		ArrayList<String> outputArray = new ArrayList<String>();
		
		JSONObject device = null;
		try {
			device = (JSONObject) new JSONParser().parse(json);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			throw new IOException("[ERROR] Could not parse: "+ key +" VALUE:" + json + "\n\r" + e1);
		}
		String uuid = (String) device.get("uuid");
		JSONArray readings = (JSONArray) device.get("Readings");
		
		// For analysis: 
    	long[] timestamp = new long[readings.size()+1];
    	long[] logons = new long[readings.size()+1];
    	
    	for(int i = 0; i < readings.size(); i++){
    		JSONArray reading = (JSONArray) readings.get(i);
    		try{
    			timestamp[i] = (Long) reading.get(0);
    		} catch(java.lang.ClassCastException e){
    			timestamp[i] = ((Double)reading.get(0)).longValue();
    		}
    		try{
    			logons[i] = (Long) reading.get(1);
    		} catch(java.lang.ClassCastException e){
    			logons[i] = ((Double) reading.get(1)).longValue();
    		}
    		
    		// Mark dirty readings.
    		String readingResult = timestamp[i]+";"+logons[i]; 
    		if (!(i == 0 || hasValidTimestamp(timestamp[i-1], timestamp[i]))
    				|| !hasValidLogons(logons[i])
    				) {
    			readingResult += ";1";
    		}
    		outputArray.add(readingResult);
    	}
    	String output = "";
    	for(String r : outputArray){
    		output += r + ",";
    	}
		context.write(new Text(uuid), new Text(output.substring(0, output.length()-1)));
	}
	
	private static boolean hasValidLogons(long logons){
		return logons >= 0;
	}
	
	// 60 seconds should have passed since previous.
	private static boolean hasValidTimestamp(long previous, long current){
		return current - previous >= 58000;
	}
}