import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Q4Mapper2{
	
	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q4/part-00000"));
		String test = "こんにちは";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		String key; // time:place
		String hashtag;
		String comparator; // count:lowestTweetId:indexInNeed
		String idsList;	
		String results; // hash:idsList
	
		while ((input = br.readLine()) != null) {		
			input = input.trim();
			String[] parts = input.split("\t");
			key = parts[0].split("\":\"")[0].replace(":\"", ":");;
			hashtag = parts[0].split("\":\"")[1].substring(0, parts[0].split("\":\"")[1].length()-1);
			comparator = parts[1].substring(0, parts[1].lastIndexOf(":"));
			idsList = parts[1].substring(parts[1].lastIndexOf(":") + 1, parts[1].length());
			results = hashtag + ":" + idsList;
			String output = key + "\t" + comparator + "#" + results;
			new PrintStream(System.out, true, "UTF-8").println(output);		 			
		}		
	}	
}