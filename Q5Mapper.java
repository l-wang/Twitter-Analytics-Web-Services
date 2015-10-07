import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Q5Mapper {

	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/phase2/testfiles/15619f14twitter-partb-ke"));
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q5/sample.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		while ((input=br.readLine()) != null) {
			input = input.trim();
			if (!input.equals("")) {
				JsonElement jelement = new JsonParser().parse(input);
				JsonObject  jobject = jelement.getAsJsonObject();
				JsonObject userObject = jobject.getAsJsonObject("user"); 
				String currentUid = userObject.get("id").toString(); // uid of this tweet
				String tweetId = jobject.get("id").toString();
				// print output as currentUid \t score1:score2:score3:tweetId
				System.out.println(currentUid + "\t" + 1 + ":" + 0 + ":" + 0 + ":" + tweetId);
				
				try {
					// Count score for the originalUid if there is one
					JsonObject retweetedStatusObject = jobject.getAsJsonObject("retweeted_status");
					JsonObject originalUserObject = retweetedStatusObject.getAsJsonObject("user"); 
					String originalUid = originalUserObject.get("id").toString(); // uid of the original tweet
					// print output as originalUid \t score1:score2:currentUid:tweetId
					System.out.println(originalUid + "\t" + 0 + ":" + 3 + ":" + currentUid + ":" + tweetId);								
				} catch (Exception e) {
					continue;						
				}
			}
		}
	}
}