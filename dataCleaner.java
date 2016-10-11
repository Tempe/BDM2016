import org.json.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;


public class dataCleaner {}
	public static void JSON() {
		// Read the time series data 
		JSONObject timeSeries = new JSONObject(" ... address for JSON-file ");
		String readings = timeSeries.getJSONObject("readings").getString("");
		String uuid = timeSeries.getJSONObject("uuid").getString("....");

		// array with the arrays from readings
		JSONArray arr = timeSeries.getJSONArray("Readings");
		Long readingsTime = new Long[]; // timestamp
		Int readingsNumb = new Int[]; // number of logins
		for (int i = 0; i < arr.length(); i++)
		{
			readingsTime[i] = timeSeries.getJSONObject(i)(1); // get timestamp
			readingsNumb[i] = timeSeries.getJSONObject(i)(2); // get number of logins
			
			// if readingsTime[i-1] - readingsTime[i] < 30 sec then OK else !OK
		}
	}

	public static void main(String[] args) {
		// read file then call JSON for parsing
	}
}