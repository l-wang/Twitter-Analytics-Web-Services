import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EtlMapper {

	public static void main(String[] args) throws IOException, ParseException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/15619f14twitter-parta-aa"));
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/testForMapper.txt"));
		
		String test = "こんにちは";
		
		Words words = new Words();
		HashMap<String, Integer> scoreMap = words.loadScoreMap();
		HashSet<String> censoredSet = words.loadCensoredSet();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		while ((input=br.readLine()) != null) {
			input = input.trim();
			if (!input.equals("")) {
				new PrintStream(System.out, true, "UTF-8").println(parse(input, scoreMap, censoredSet));
			}
		}
	}
	
	 public static String parse(String jsonLine, HashMap<String, Integer> map, HashSet<String> set) throws ParseException, UnsupportedEncodingException {
		    JsonElement jelement = new JsonParser().parse(jsonLine);
		    JsonObject  jobject = jelement.getAsJsonObject();
		    String time = jobject.get("created_at").toString();
		    
		    JsonObject userObject = jobject.getAsJsonObject("user");
		    String uid = userObject.get("id").toString();
		    String tweetId = jobject.get("id").toString();		    
		    String text = jobject.get("text").toString().substring(1,jobject.get("text").toString().length()-1);
		    
		    int score = getScore(text, map);
		    String newText = getNewText(text, set);
		    
		    Date oldTime = new SimpleDateFormat("\"EEE MMM dd HH:mm:ss +0000 yyyy\"").parse(time);
		    String newTime = new SimpleDateFormat("yyyyMMddHHmmss").format(oldTime);

		    String output = newTime + ":" + uid + "\t" + tweetId + ":" + score + ":" + newText;
		    return output;
		    /*
		    byte[] array = output.getBytes("UTF-8");
		    String s = new String(array, Charset.forName("UTF-8"));
		    return s;
		    */
		    	    
		}	
	 
	 public static int getScore(String sentence, HashMap<String, Integer> map) {
		 sentence = sentence.toLowerCase().replace("\\n", "\n");
		 int score = 0;
		 
		 String[] words = sentence.split("(?<=[^a-zA-Z0-9]+)|(?=[^a-zA-Z0-9]+)");
		 String word;
		 for (int i = 0; i < words.length; i++) {
			 word = words[i].toLowerCase();			 
			 if (map.get(word) != null) {
				 score = score + map.get(word);
			 }
		 }	 			 	 
		 return score;
	 }
	 
	 public static String getNewText(String sentence, HashSet<String> set) {
		 StringBuilder sb = new StringBuilder();
		 
		 String[] words = sentence.split("(?<=[^a-zA-Z0-9]+)|(?=[^a-zA-Z0-9]+)"); // include underscore
		 //String[] words = sentence.split("(?<=\\W+)|(?=\\W+)"); //split by non alphanumeric
		 String word;
		 for (int i = 0; i < words.length; i++) {
			 word = words[i];
			 if (!set.contains(word.toLowerCase())) {
				 sb.append(word);
			 } else {
				 sb.append(word.charAt(0));
				 for (int j = 1; j < word.length() - 1; j++) {
					 sb.append("*");
				 }
				 sb.append(word.substring(word.length() -1));				 
			 }
		 }		 
		 return sb.toString();
	 }
}