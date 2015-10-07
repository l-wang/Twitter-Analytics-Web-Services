import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class Q4Reducer2{
	
	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q4/testForReducer2.txt"));
		String test = "こんにちは";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		String key = null; // time:place
		Cmptor comparator; // count:lowestTweetId:indexInNeed
		String currentKey = null;	
		String results = null; // hash:idsList

		ArrayList<Cmptor> comparatorList = new ArrayList<Cmptor>();
		HashMap<Cmptor, String> map = new HashMap<Cmptor, String>();
	
		while ((input = br.readLine()) != null) {		
			input = input.trim();
			String[] parts = input.split("\t");
			key = parts[0];
			comparator = new Cmptor(parts[1].split("#")[0]);
			results = parts[1].split("#")[1];
			
			if (currentKey == null) {
				// for first line
				currentKey = key;
				comparatorList.add(comparator);
				map.put(comparator, results);
			} else if (currentKey.equals(key)) {				
				if (comparator.compare2(comparatorList.get(0)) < 0) {
					comparatorList.add(0, comparator);	
					map.put(comparator, results);
				} else if (comparator.compare2(comparatorList.get(comparatorList.size()-1)) >= 0) {
					// "=" case probably won't happen
					comparatorList.add(comparator);
					map.put(comparator, results);
				} else {
					int size = comparatorList.size();
					for (int i = 0; i < size-1; i++) { 
						if (comparator.compare2(comparatorList.get(i)) >= 0 && comparator.compare2(comparatorList.get(i+1)) < 0) {
							comparatorList.add(i+1, comparator);
							map.put(comparator, results);
							break;
						}
					}
				}				
			} else if (!currentKey.equals(key)) {
				// change to new key, print output of the previous list of key + rank + results				
				printOutput(comparatorList, map, currentKey);	
				// reset arraylists and currentKey
				comparatorList.clear();
				map.clear();				
				currentKey = key;
				comparatorList.add(comparator);
				map.put(comparator, results);
			}	 			
		}
		
		// don't forget last line if it has the same key as the second last line
		if (currentKey.equals(key)) {
			printOutput(comparatorList, map, currentKey);
		}
	}
	
	public static void printOutput(ArrayList<Cmptor> comparatorList, HashMap<Cmptor, String> map, String currentKey) throws UnsupportedEncodingException {
		for (int i = 0; i < comparatorList.size(); i++) {
			String output = currentKey + "\t" + (i+1) + "\t" + map.get(comparatorList.get(i)); // (i+1) is rank
			//String output = currentKey + "\t" + (i+1) + "\t" + comparatorList.get(i).toString() + "\t" + map.get(comparatorList.get(i));
			new PrintStream(System.out, true, "UTF-8").println(output);						
		}	
	}	
}