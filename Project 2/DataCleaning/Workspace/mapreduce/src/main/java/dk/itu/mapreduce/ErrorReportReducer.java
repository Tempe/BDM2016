package dk.itu.mapreduce;

import java.util.Map;
import java.util.Map.Entry;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ErrorReportReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		Map<String, Long> results = new HashMap<String, Long>();
		
		for(Text value : values){
			StringTokenizer iterator = new StringTokenizer(value.toString(), "\\s+");
			String hour = iterator.nextToken();
			Long readings = Long.parseLong(iterator.nextToken());
			
			Long sum = results.putIfAbsent(hour, readings);
			if(sum != null){
				results.put(hour, sum + readings);
			}
		}
		
		Iterator iterator = results.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, Long> pair = (Entry) iterator.next();
			context.write(new Text(key + "_" + pair.getKey()), new Text(""+pair.getValue()));
		}
	}

}
