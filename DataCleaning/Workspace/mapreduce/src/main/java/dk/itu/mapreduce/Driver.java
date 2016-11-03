package dk.itu.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
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
		String inputPath = args[0];
		String outputPath = args[1];
		int mapperType = Integer.parseInt(args[2]);
		
		switch(mapperType){
			case 0:
				startCleanJob(inputPath, outputPath);
				break;
			case 1:
				startBatchErrorJob(inputPath, outputPath);
				break;
			default:
				throw new IOException("Unkown mapper type. Use {0,1}");
		}
	}
	
	public static void startCleanJob(String inputPath, String outputPath) throws IOException, ClassNotFoundException, InterruptedException {
		
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
	
	public static void startBatchErrorJob(String inputPath, String outputPath) throws IOException, ClassNotFoundException, InterruptedException {
		
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
}