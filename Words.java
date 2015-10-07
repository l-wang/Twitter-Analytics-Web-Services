import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

public class Words {
	private HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
	private HashSet<String> censoredSet = new HashSet<String>();
	
	public Words() {
		scoreMap = new HashMap<String, Integer>();
		censoredSet = new HashSet<String>();
	}
	
	public HashMap<String, Integer> loadScoreMap() throws IOException {
		BufferedReader br = new BufferedReader(new 
				InputStreamReader(Words.class.getResourceAsStream("AFINN.txt")));
		
		String input;
		String[] parts;
		String keyWord;
		int score;
		
		while ((input=br.readLine()) != null) {
			input = input.trim();
			parts = input.split("\t");
			keyWord = parts[0].trim();
			score = Integer.parseInt(parts[1].trim());
			scoreMap.put(keyWord, score);		
		}
		return scoreMap;
	}
	
	public HashSet<String> loadCensoredSet() throws IOException {
		BufferedReader br = new BufferedReader(new 
				InputStreamReader(Words.class.getResourceAsStream("banned.txt")));
		
		String input;
		String bannedWord;
		
		while ((input=br.readLine()) != null) {

			StringBuilder sb = new StringBuilder();
			input = input.trim();

			for (int i = 0; i < input.length(); i++) {
				if (!Character.isAlphabetic(input.charAt(i))) {
					sb.append(input.charAt(i));

				} else {
					// 97-122 -> a-z. All the words in banned.txt are lowcase. 
					char oldLetter = input.charAt(i);
					int oldAscii = (int) oldLetter;					
					int ascii = 97 + ((oldAscii-97 + 13) % 26);				
					char newLetter = (char) ascii;
			
					sb.append(newLetter);
				}
			}
			bannedWord = sb.toString();
			censoredSet.add(bannedWord);
		}
		return censoredSet;
	}
		
}