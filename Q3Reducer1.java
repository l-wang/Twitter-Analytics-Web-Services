import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Q3Reducer1{
	
	/**
	 * Reducer 1 for q3 [final]
	 * @param args
	 * @throws IOException
	 */
	
	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/phase2/testForReducer.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		String key = null;
		String value;
		String currentKey = null;

		HashSet<String> valueSet = new HashSet<String>();
	
		while ((input = br.readLine()) != null) {		
			input = input.trim();
			String[] parts = input.split("\t");
			key = parts[0];
			value = parts[1];
			
			// merge values
			if (currentKey == null) {
				// for first line
				currentKey = key;
				valueSet.add(value);
				
			} else if (currentKey.equals(key)) {
				valueSet.add(value);
				
			} else if (!currentKey.equals(key)) {
				// change to new key, print output of the previous key + valueSet
				if (valueSet.size() == 2 || valueSet.contains("2")) {
					// retweeted each other
					System.out.println(currentKey + "\t" + "1:1");
				} else if (valueSet.contains("0")) {
					// only the first uid is source tweet
					System.out.println(currentKey + "\t" + "1:0");
				} else if (valueSet.contains("1")) {
					// only the second uid is source tweet
					System.out.println(currentKey + "\t" + "0:1");
				}
								
				// reset valueSet and currentKey
				valueSet.clear();				
				currentKey = key;
				valueSet.add(value);
			} 
		}
		// don't forget last line if it has the same key as the second last line
		if (currentKey.equals(key)) {			
			
			// merge results
			if (valueSet.size() == 2 || valueSet.contains("2")) {
				// retweeted each other
				System.out.println(currentKey + "\t" + "1:1");
			} else if (valueSet.contains("0")) {
				// only the first uid is source tweet
				System.out.println(currentKey + "\t" + "1:0");
			} else if (valueSet.contains("1")) {
				// only the second uid is source tweet
				System.out.println(currentKey + "\t" + "0:1");
			}
					
		}
	}
}