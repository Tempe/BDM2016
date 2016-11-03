package dk.itu.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ErrorReportMapper extends Mapper<Text, Text, Text, Text> {
	public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
		StringTokenizer iterator = new StringTokenizer(value.toString(), ",");
		Long timestamp = Long.parseLong(iterator.nextToken());
		Long readings = Long.parseLong(iterator.nextToken());
		
		if(iterator.hasMoreTokens()){
			boolean isDirty = iterator.nextToken().equals("1");
			if(isDirty){
				Long hourIntervalStart = timestamp - (timestamp % 3600);
				Long hourIntervalEnd = hourIntervalStart + 3600;
				context.write(new Text("k"), new Text(hourIntervalStart + "-" + hourIntervalEnd + " " + readings));
			}
		}
	}
}
