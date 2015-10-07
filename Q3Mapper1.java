import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Q3Mapper1 {
	/**
	 * Mapper 1 for q3 [final]
	 * @param args
	 * @throws IOException
	 */

	@SuppressWarnings("finally")
	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/phase2/testfiles/15619f14twitter-partb-kd"));
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/phase2/sample.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		while ((input=br.readLine()) != null) {
			input = input.trim();
			if (!input.equals("")) {
				JsonElement jelement = new JsonParser().parse(input);
				JsonObject  jobject = jelement.getAsJsonObject();
				JsonObject userObject = jobject.getAsJsonObject("user"); 
				String currentUid = userObject.get("id").toString(); // uid of this tweet
				
				try {
				JsonObject retweetedStatusObject = jobject.getAsJsonObject("retweeted_status");
				JsonObject originalUserObject = retweetedStatusObject.getAsJsonObject("user"); 
				String originalUid = originalUserObject.get("id").toString(); // uid of the original tweet
				
				// compare uids the way mapper sort their keys
				if (originalUid.compareTo(currentUid) < 0) {
					// 0 means original uid in the front
					System.out.println(originalUid + ":" + currentUid + "\t" + 0);
				} else if (originalUid.compareTo(currentUid) > 0) {
					// 1 means original uid in the back
					System.out.println(currentUid + ":" + originalUid + "\t" + 1);
				} else {
					// originalUid.equals(currentUid)
					System.out.println(currentUid + ":" + originalUid + "\t" + 2);
				}
								
				} finally {
					continue;
				}
			}
		}
	}

}