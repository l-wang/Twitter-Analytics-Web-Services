import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Q5Reducer{
	private static String currentUid = null;
	private static int score1Total = 0;
	private static int score2Total = 0;
	private static int score3Total;
	private static int scoreTotal;
	
	private static HashSet<String> valueSet = new HashSet<String>();
	private static HashSet<String> retweeterSet = new HashSet<String>();
	
	public static void main(String[] args) throws IOException {
		
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q5/testForReducer.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		String uid = null;
		String value;
		int score1;
		int score2;
		String retweeter; //uid of the retweet. For score3
	
		while ((input = br.readLine()) != null) {		
			input = input.trim();
			String[] parts = input.split("\t");
			uid = parts[0];
			value = parts[1];
			score1 = Integer.parseInt(value.split(":")[0]);
			score2 = Integer.parseInt(value.split(":")[1]);
			retweeter = value.split(":")[2];
			
			// merge values
			if (currentUid == null) {
				// for first line
				currentUid = uid;
				updateScores(score1, score2, retweeter, value);
				
			} else if (currentUid.equals(uid)) {
				updateScores(score1, score2, retweeter, value);
				
			} else if (!currentUid.equals(uid)) {
				// change to new key, print output of the previous uid \t scores
				score3Total = retweeterSet.size() * 10; // 10 points for each unique retweeter
				scoreTotal = score1Total + score2Total + score3Total;
				System.out.println(currentUid + "\t" + score1Total + "\t" + score2Total + "\t" + score3Total + "\t" + scoreTotal);				
				// reset currentUid and scores, and then update currentUid and scores	
				score1Total = 0;
				score2Total = 0;
				retweeterSet.clear();	
				valueSet.clear();
				currentUid = uid;
				updateScores(score1, score2, retweeter, value);
			} 
		}
		// don't forget last line if it has the same uid as the second last line
		if (currentUid.equals(uid)) {			
			score3Total = retweeterSet.size() * 10; // 10 points for each unique retweeter
			scoreTotal = score1Total + score2Total + score3Total;
			System.out.println(currentUid + "\t" + score1Total + "\t" + score2Total + "\t" + score3Total + "\t" + scoreTotal);						
		}
	}
	
	public static void updateScores(int score1, int score2, String retweeter, String value) {
		if (!valueSet.contains(value)) {
			// we only count score for the same uid with the same tweetId once
			score1Total += score1;
			score2Total += score2;
			if (!retweeter.equals("0")) {
				retweeterSet.add(retweeter);
			}				
			valueSet.add(value);
		}	
	}
}