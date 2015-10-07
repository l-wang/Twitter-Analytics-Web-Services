import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Q6Mapper {

	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q5/15619f14twitter-parta-aa"));
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q5/sample.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		int photoNum;
		
		while ((input=br.readLine()) != null) {	
			photoNum = 0;
			input = input.trim();
			if (!input.equals("")) {
				JsonElement jelement = new JsonParser().parse(input);
				JsonObject  jobject = jelement.getAsJsonObject();
				JsonObject userObject = jobject.getAsJsonObject("user"); 
				String uid = userObject.get("id").toString(); 
				String tweetId = jobject.get("id").toString();

				try {
					JsonObject entitiesObject = jobject.getAsJsonObject("entities");
					JsonArray mediaArray = entitiesObject.getAsJsonArray("media"); 
					if (mediaArray.size() == 0) {
						continue;
					} else {					
						for (int i = 0; i < mediaArray.size(); i++) {
							JsonElement mediaElement = mediaArray.get(i);
							JsonObject  mediaObject = mediaElement.getAsJsonObject();
							String mediaType = mediaObject.get("type").toString().replaceAll("\"", "");
							if (mediaType.equals("photo")) {
								photoNum++;
							}
						}
						if (photoNum != 0) {
							System.out.println(uid + "\t" + photoNum + ":" + tweetId);
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
	}
}