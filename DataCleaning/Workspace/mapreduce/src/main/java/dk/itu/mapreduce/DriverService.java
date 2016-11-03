package dk.itu.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public final class DriverService {
	private DriverService(){} // Hide constructor
	
	public static void startCleaningJob(String inputPath, String outputPath) 
			throws IOException, ClassNotFoundException, InterruptedException { 
		Configuration conf = new Configuration();
		Job job = new Job(conf, "First Mapreduce App");

		// Set driver class
		job.setJarByClass(Driver.class);

		// Set Input & Output Format
		job.setInputFormatClass(TextInputFormat.class);
        //job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// Set Mapper & Reducer Class
		job.setMapperClass(PreprocessingMapper.class);
        job.setReducerClass(PreprocessingReducer.class);


		// No. of reduce tasks, equals no. output file
		job.setNumReduceTasks(1);

		// HDFS input and output path
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));

		job.waitForCompletion(true);
	}
	
	// TODO: Test
	public static void startErrorsBatchJob(String inputPath, String outputPath) 
			throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", "\\s+");
		Job job = new Job(conf, "Error report job.");

		// Set driver class
		job.setJarByClass(Driver.class);

		// Set Input & Output Format
		job.setInputFormatClass(TextInputFormat.class);		
        job.setOutputFormatClass(TextOutputFormat.class);

		// Set Mapper & Reducer Class
		job.setMapperClass(ErrorReportMapper.class);
        job.setReducerClass(ErrorReportReducer.class);
		//job.setOutputKeyClass(Text.class);
		//job.setOutputValueClass(Text.class);

		// No. of reduce tasks, equals no. output file
		job.setNumReduceTasks(1);

		// HDFS input and output path
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));

		job.waitForCompletion(true);
	}
	
	public static void startVisitorsBatchJob(String inputPath, String outputPath) 
			throws IOException, ClassNotFoundException, InterruptedException {
		throw new UnsupportedOperationException("TODO: Not implemented");
	}
	
	public static void startMaxVisitorsBatchJob(String inputPath, String outputPath) 
			throws IOException, ClassNotFoundException, InterruptedException {
		throw new UnsupportedOperationException("TODO: Not implemented");
	}
}
