import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

/**
 * The HBase back end main class for Q4
 * 
 * @author Justin
 * 
 */
public class HashtagHBase {
	/**
	 * The class which implements Mapper
	 * 
	 * @author Justin
	 *
	 */
	public static class HBaseMapper extends Mapper<Object, Text, Text, Text> {
		@Override
		public void map(Object keyIn, Text valueIn, Context context) throws IOException,
				InterruptedException {
			String valueInAsStr = valueIn.toString().trim();

			if (!valueInAsStr.equals("")) {
				String[] line = valueInAsStr.split("\t");

				if (line.length != 3) {
					System.out.println("length: " + line.length);
					System.out.println(line[0] + line[1]);
					System.out.println();
				}

				Text timeAndLocationAndRank = new Text(line[0] + ":" + String.format("%07d", Integer.parseInt(line[1])));
				Text tagAndTweetIDs = new Text(line[2]);
				context.write(timeAndLocationAndRank, tagAndTweetIDs);
			}
		}
	}
	
	/**
	 * The class which implements Reducer
	 * 
	 * @author Justin
	 *
	 */
	public static class HBaseReducer extends TableReducer<Text, Text, Object> {
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException,
				InterruptedException {
			String timeAndLocationAndRank = key.toString();
			
			if (!values.iterator().hasNext()) {
				System.out.println("Error: not found!");
				System.out.println(timeAndLocationAndRank);
				System.out.println();
			}
			
			String tagAndTweetIDs = values.iterator().next().toString();
			
			if (values.iterator().hasNext()) {
				System.out.println("Error: duplicated!");
				System.out.println(timeAndLocationAndRank);
				System.out.println();
			}
			
			Put put = new Put(Bytes.toBytes(timeAndLocationAndRank));
			put.add(Bytes.toBytes("content"), Bytes.toBytes("tagID"),
					Bytes.toBytes(tagAndTweetIDs));
			context.write(null, put);
		}
	}
	
	/**
	 * The main method executed
	 * 
	 * @param args the command line arguments which has one element: the path of the file to be parsed
	 */
	public static void main(String[] args) {
		Configuration configuration = HBaseConfiguration.create();

		try {
			Job job = Job.getInstance(configuration);
			job.setJarByClass(HashtagHBase.class);
			job.setMapperClass(HBaseMapper.class);
			
			// maybe unnecessary
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			
			TableMapReduceUtil.initTableReducerJob("tweet4", HBaseReducer.class, job);

			// maybe unnecessary
			job.setNumReduceTasks(1);

			System.out.println("OK!");

			FileInputFormat.addInputPath(job, new Path(args[0]));

			boolean successful = job.waitForCompletion(true);
			if (!successful) {
				throw new IOException("Error with I/O");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
