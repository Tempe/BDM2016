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
				DriverService.startCleaningJob(inputPath, outputPath);
				break;
			case 1:
				DriverService.startErrorsBatchJob(inputPath, outputPath);
				break;
			case 2:
				DriverService.startVisitorsBatchJob(inputPath, outputPath);
				break;
			case 3:
				DriverService.startMaxVisitorsBatchJob(inputPath, outputPath);
				break;
			default:
				throw new IOException("Unkown mapper type. Use: 0(clean), 1(errors), 2(visitors) or 3(maxvisitors)");
		}
	}
}