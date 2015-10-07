import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Q4Mapper1 {
	
	public static void main(String[] args) throws IOException, ParseException {
		System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q4/testfiles/15619f14twitter-parta-aa"));
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q4/sample.txt"));
		String test = "こんにちは";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
//		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
//				 new FileOutputStream(new File("r.txt"))));
		
		String input;
		while ((input=br.readLine()) != null) {
			input = input.trim();			
			if (!input.equals("")) {
				JsonElement jelement = new JsonParser().parse(input);
				JsonObject  jobject = jelement.getAsJsonObject();
				JsonObject userObject = jobject.getAsJsonObject("user"); 
				
				// extract date
				String time = jobject.get("created_at").toString();
				Date oldTime = new SimpleDateFormat("\"EEE MMM dd HH:mm:ss +0000 yyyy\"").parse(time);
				String date = new SimpleDateFormat("yyyyMMdd").format(oldTime);
				
				// extract tweeteId
				String tweetId = jobject.get("id").toString();
				
				// find place
				String place = null;
				try {					
					JsonObject placeObject = jobject.getAsJsonObject("place");
					if (placeObject == null) {
						place = findPlaceByTimezone(userObject);				
					} else {
						String placeName = placeObject.get("name").toString();
						if (placeName.isEmpty() || placeName == null) {
							// find place from [user][time_zone]
							place = findPlaceByTimezone(userObject);
						} else {
							// use this place from [place][name]
							place = placeName;
							//System.out.println(place);//
						}
					}
				} catch (Exception e) {
					// this happens when the entity "place" does not exist
					place = findPlaceByTimezone(userObject);
					//System.out.println(place);//
				} 
				
				if (place != null && !place.equals("null")) {
					// find hashtag if place exists
					try {
						JsonObject entitiesObject = jobject.getAsJsonObject("entities");
						JsonArray hashtagsArray = entitiesObject.getAsJsonArray("hashtags"); 
						if (hashtagsArray.size() == 0) {
							//System.out.println("no hashtag");
							continue; // ignore this tweet
						} else {			
							ArrayList<String> hashtagTextList = new ArrayList<String>(); // for duplicate hashtagTexts
							for (int i = 0; i < hashtagsArray.size(); i++) {									
								JsonElement hashtagElement = hashtagsArray.get(i);
								JsonObject  hashtagObject = hashtagElement.getAsJsonObject();
								String hashtagText = hashtagObject.get("text").toString();
								String hashtagIndices = hashtagObject.get("indices").toString();
								String hashtagIndex = hashtagIndices.replaceAll("\\[", "").replaceAll("\\]", "").split(",")[0];
																
								if (!hashtagTextList.contains(hashtagText)) {
									// print output
									String output = date + ":" + place + ":" + hashtagText + "\t" + tweetId + ":" + hashtagIndex;
									// wr.append(output + "\n");
									new PrintStream(System.out, true, "UTF-8").println(output);	
									hashtagTextList.add(hashtagText);
								}																												
							}
						}					
					} catch (Exception e) {
						System.out.println("This probably won't happen, unless 'entities' or 'hashtags' doesn't exist");
						break;
					} 
				}				
			}
		}
		// wr.close();
	}
	private static String findPlaceByTimezone (JsonObject userObject) {	
		String timezone = userObject.get("time_zone").toString();	
		Pattern TzPattern = Pattern.compile("\\btime\\b");
	    Matcher matcher = TzPattern.matcher(timezone.toLowerCase());
	    if (matcher.find() == true) {							
	    	return null;						
	    } else {
	    	return timezone;
	    }
	}
}