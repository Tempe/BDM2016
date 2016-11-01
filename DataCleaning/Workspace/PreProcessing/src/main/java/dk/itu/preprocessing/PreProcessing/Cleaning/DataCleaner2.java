package dk.itu.preprocessing.PreProcessing.Cleaning;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.*;

public class DataCleaner2 {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		JSONArray devices = (JSONArray) new JSONParser().parse(scanner);
    	for(int m = 0; m < devices.size(); m++){
			JSONObject readingsObj = (JSONObject) devices.get(m); 
			System.out.println(readingsObj.toJSONString());
		}
		scanner.close();
	}
}
