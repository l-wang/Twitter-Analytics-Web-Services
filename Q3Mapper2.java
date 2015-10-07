import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Q3Mapper2 {
	
	/**
	 * Mapper 2 for q3 [final]
	 */

	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/phase2/testForMapper2"));
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/phase2/testForMR2"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		String key = null;
		String value;
		String originalUid;
		String retweetedUid;
		
		while ((input=br.readLine()) != null) {
			input = input.trim();
			String[] parts = input.split("\t");
			key = parts[0];
			value = parts[1];
			String[] uidPair = key.split(":");
			
			if (value.equals("0:1")) {
				originalUid = uidPair[1];
				retweetedUid = uidPair[0];
				System.out.println(originalUid + "\t" + retweetedUid);
				
			} else if (value.equals("1:0")) {
				originalUid = uidPair[0];
				retweetedUid = uidPair[1];
				System.out.println(originalUid + "\t" + retweetedUid);
				
			} else {
				// value.equals("1:1")  retweeted each other 
				System.out.println(uidPair[0] + "\t" + "(" + uidPair[1] + ")");
				System.out.println(uidPair[1] + "\t" + "(" + uidPair[0] + ")");
			}
					
		}
	}

}