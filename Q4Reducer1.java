import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;

public class Q4Reducer1{
	
	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q4/testForReducer1.txt"));
		String test = "こんにちは";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		String key = null;
		String tweetId;
		long tweetId_long;
		int index_int;
		String index;
		String currentKey = null;

		ArrayList<Long> tweetIdList = new ArrayList<Long>();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
	
		while ((input = br.readLine()) != null) {		
			input = input.trim();
			String[] parts = input.split("\t");
			key = parts[0];
			tweetId = parts[1].split(":")[0];
			index = parts[1].split(":")[1];
			tweetId_long = Long.parseLong(tweetId);
			index_int = Integer.parseInt(index);
			
			// merge values			
			if (currentKey == null) {
				// for first line
				currentKey = key;
				tweetIdList.add(tweetId_long);
				indexList.add(index_int);
			} else if (currentKey.equals(key)) {
				if (tweetIdList.contains(tweetId_long)) {
					// use the first occurence of a hashtag in the same tweet // forget it. No need to do it at all.
					int pos = tweetIdList.indexOf(tweetId_long);
					if (index_int < indexList.get(pos)) {
						indexList.set(pos, index_int);
					}				
				} else if  (tweetId_long < tweetIdList.get(0)) {
					tweetIdList.add(0, tweetId_long );
					indexList.add(0, index_int);
				} else if (tweetId_long > tweetIdList.get(tweetIdList.size() - 1)) {
					tweetIdList.add(tweetId_long);
					indexList.add(index_int);
				} else {
					int size = tweetIdList.size();
					for (int i = 0; i < size - 1; i++) {
						if (tweetIdList.get(i) < tweetId_long && tweetId_long < tweetIdList.get(i + 1)) {
							tweetIdList.add(i+1, tweetId_long);
							indexList.add(i+1, index_int);
							break;
						}
					}
				}
			} else if (!currentKey.equals(key)) {
				// change to new key, print output as currentKey + count + lowestId + tweetIdList + index
				printOutput(tweetIdList, indexList, currentKey);			
				// reset arraylists and currentKey
				tweetIdList.clear();
				indexList.clear();				
				currentKey = key;
				tweetIdList.add(tweetId_long);
				indexList.add(index_int);
			}			
		}
		// don't forget last line if it has the same key as the second last line
		if (currentKey.equals(key)) {
			printOutput(tweetIdList, indexList, currentKey);
		}
	}
	
	private static void printOutput(ArrayList<Long> tweetIdList, ArrayList<Integer> indexList, String currentKey) throws UnsupportedEncodingException {
		int count = tweetIdList.size();
		Long lowestTweetId = tweetIdList.get(0);
		int indexInNeed = indexList.get(0);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tweetIdList.size() - 1; i++) {
			sb.append(tweetIdList.get(i) + ",");
		}
		sb.append(tweetIdList.get(tweetIdList.size() - 1));
		String idList = sb.toString();
		String output = currentKey + "\t" + count + ":" + lowestTweetId + ":" + indexInNeed + ":" + idList;
		new PrintStream(System.out, true, "UTF-8").println(output);	
	}
}