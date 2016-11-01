package dk.itu.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Driver {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		int mapperType = Integer.parseInt(args[0]);
		String inputPath = args[0];
		String outputPath = args[0];
		switch(mapperType){
			case 0:
				startJob(FirstMapper.class, FirstReducer.class, TextInputFormat.class, TextOutputFormat.class, inputPath, outputPath);
				break;
			default:
				throw new IOException("Unkown mapper type");
		}
	}
	
	public static void startJob(Class<? extends Mapper> mapper,
			Class<? extends Reducer> reducer,
			Class<? extends InputFormat> inputFormat, 
			Class<? extends OutputFormat> outputFormat,
			String inputPath,
			String outputPath) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();
		Job job = new Job(conf, "First Mapreduce App");

		// Set driver class
		job.setJarByClass(Driver.class);

		// Set Input & Output Format
		job.setInputFormatClass(inputFormat);
		job.setOutputFormatClass(outputFormat);

		// Set Mapper & Reducer Class
		job.setMapperClass(mapper);
		job.setReducerClass(reducer);

		// No. of reduce tasks, equals no. output file
		job.setNumReduceTasks(1);

		// HDFS input and output path
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));

		job.waitForCompletion(true);
	}
}