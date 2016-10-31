package dk.itu.mapreduce;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FirstMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// send key and value to reducer
		context.write(key, value);
	}

}