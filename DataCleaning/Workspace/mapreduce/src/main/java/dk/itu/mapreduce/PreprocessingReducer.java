package dk.itu.mapreduce;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PreprocessingReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		for (Text value : values) {
			String[] readings = value.toString().split(",");
			String[] timestamps = new String[readings.length];
			long[] logons = new long[readings.length];
			int[] dirtyBit = new int[readings.length];
			
			for(int i = 0; i < readings.length; i++) {
				// [0] = Timestamp, [1] = Logons, [2] = dirtyBit
				String[] reading = readings[i].split(";");
				timestamps[i] = reading[0];
				logons[i] = Long.parseLong(reading[1]);
				if(reading.length > 2 ){
					dirtyBit[i] = Integer.parseInt(reading[2]);
				}
			}

			// Flush the variables.
			int outlierRange = 2;
			int variance = 5;
			for (int i = 0; i < timestamps.length; i++) {
				String output;
				int rangeSum = 0;
				int arrayStart = i - outlierRange < 0 ? 0 : i - outlierRange;
				int arrayEnd = i + outlierRange <= timestamps.length ? i + outlierRange : timestamps.length;
				for (int j = arrayStart; j < arrayEnd; j++) {
					rangeSum += logons[j];
				}

				int mean = rangeSum / (arrayEnd - arrayStart);
				// Outlier
				if (variance * mean - logons[i] < 0) {
					output = String.format("%1$s; %2$d; %3$d", timestamps[i], logons[i], 2);
				} else {
					output = String.format("%1$s; %2$d; %3$d", timestamps[i], logons[i], dirtyBit[i]+2);
				}
				context.write(key, new Text(output));
			}
		}
	}
}