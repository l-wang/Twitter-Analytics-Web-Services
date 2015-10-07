import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Q6Reducer{
	private static String currentUid = null;
	private static int photoNumTotal = 0;
	
	private static HashSet<String> valueSet = new HashSet<String>();
	
	public static void main(String[] args) throws IOException {
		
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q6/testForReducer.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		String uid = null;
		String value;
		int photoNum;
	
		while ((input = br.readLine()) != null) {		
			input = input.trim();
			String[] parts = input.split("\t");
			uid = parts[0];
			value = parts[1];
			photoNum = Integer.parseInt(value.split(":")[0]);
			
			// merge values
			if (currentUid == null) {
				// for first line
				currentUid = uid;
				updatePhotoNum(photoNum, value);
				
			} else if (currentUid.equals(uid)) {
				updatePhotoNum(photoNum, value);
				
			} else if (!currentUid.equals(uid)) {
				// change to new key, print output of the previous uid \t photoNumTotal
				System.out.println(currentUid + "\t" + photoNumTotal);				
				// reset currentUid and photoNumTotal, and then update currentUid and phtotNumTotal	
				photoNumTotal = 0;	
				valueSet.clear();
				currentUid = uid;
				updatePhotoNum(photoNum, value);
			} 
		}
		// don't forget last line if it has the same uid as the second last line
		if (currentUid.equals(uid)) {			
			System.out.println(currentUid + "\t" + photoNumTotal);						
		}
	}
	
	public static void updatePhotoNum(int photoNum, String value) {
		if (!valueSet.contains(value)) {
			// we only count the same tweetId once
			photoNumTotal += photoNum;			
			valueSet.add(value);
		}	
	}
}