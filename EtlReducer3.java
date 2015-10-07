import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class EtlReducer3 {
	// sort tweetIds and merge
	// time + "\t" + uid + tweetId1:score1:text1\ntweetId2:score2:text2
	// "20140321151031	1604939345	447027477496733696:-1:No se que le pasa :::asdf:::(:_:)_"
	
	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/test.txt"));
		//FileWriter out = new FileWriter("/Users/Lei/15619/15619Project/out30.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String test = "こんにちは";
		
		String input;
		String time_uid = null;
		String time;
		String uid;
		String results;
		String tweetId;	
		
		String currentTimeUid = null;
		ArrayList<Long> tweetIdList = new ArrayList<Long>();
		ArrayList<String> resultsList = new ArrayList<String>();
			
		while ((input=br.readLine()) != null) {
			input = input.trim();
			String[] parts = input.split("\t");
			time_uid = parts[0].trim();
			results = parts[1].trim();
			tweetId = results.split(":")[0];
			long tweetId_long = Long.parseLong(tweetId);
			if (currentTimeUid == null) {
				// for first line
				currentTimeUid = time_uid;
				tweetIdList.add(tweetId_long);
				resultsList.add(results);
			} else if (currentTimeUid.equals(time_uid)) {
				// same time_uid as the previous line
				if (tweetId_long < tweetIdList.get(0)) {
					tweetIdList.add(0, tweetId_long);
					resultsList.add(0, results);
				} else if (tweetId_long > tweetIdList.get(tweetIdList.size()-1)) {
					tweetIdList.add(tweetId_long);
					resultsList.add(results);
				} else {
					for (int i = 0; i < tweetIdList.size()-1; i++) { 
						if (tweetIdList.get(i) < tweetId_long && tweetId_long < tweetIdList.get(i+1)) {
							tweetIdList.add(i+1, tweetId_long);
							resultsList.add(i+1, results);
						}
					}
				}
			} else if (!currentTimeUid.equals(time_uid)) {
				// change to new time_uid, print output of the previous time_uid
				
				// merge results for MySQL
				StringBuilder sb = new StringBuilder();
				time = currentTimeUid.split(":")[0];
				uid = currentTimeUid.split(":")[1];
				sb.append(time + "\t" + uid + "\t");
				for (int j = 0; j < tweetIdList.size()-1; j++) {
					sb.append(resultsList.get(j));
					sb.append("\\n");
				}
				sb.append(resultsList.get(resultsList.size()-1));
				new PrintStream(System.out, true, "UTF-8").println(sb);
				
				/*
				// seperate results for HBase
				for (int j = 0; j < tweetIdList.size(); j++) {
					// print sorted output in different lines
					time = currentTimeUid.split(":")[0];
					uid = currentTimeUid.split(":")[1];
					String output = time + uid + "\t" + resultsList.get(j);
				    byte[] array = output.getBytes("UTF-8");
				    String s = new String(array, Charset.forName("UTF-8"));
				    new PrintStream(System.out, true, "UTF-8").println(s);
				}
				*/
				
				// reset arraylists and currentTimeUid
				tweetIdList.clear();
				resultsList.clear();
				
				currentTimeUid = time_uid;
				tweetIdList.add(tweetId_long);
				resultsList.add(results);
			}
		}
		// don't forget last line if it has the same time_uid as the second last line
		if (currentTimeUid.equals(time_uid)) {		
			
			
			// merge results for MySQL
			StringBuilder sb = new StringBuilder();
			
			time = currentTimeUid.split(":")[0];
			uid = currentTimeUid.split(":")[1];
			sb.append(time + "\t" + uid + "\t");
			for (int j = 0; j < tweetIdList.size()-1; j++) {
				sb.append(resultsList.get(j));
				sb.append("\\n");
			}
			sb.append(resultsList.get(resultsList.size()-1));
			new PrintStream(System.out, true, "UTF-8").println(sb);
			
			/* seperated results for HBase
			for (int j = 0; j < tweetIdList.size(); j++) {
				// print sorted output in different lines
				time = currentTimeUid.split(":")[0];
				uid = currentTimeUid.split(":")[1];
				String output = time + uid + "\t" + resultsList.get(j);
			    byte[] array = output.getBytes("UTF-8");
			    String s = new String(array, Charset.forName("UTF-8"));
			    new PrintStream(System.out, true, "UTF-8").println(s);
			}
			*/
		}
					
	}
}