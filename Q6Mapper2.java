import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Q6Mapper2{
	
	public static void main(String[] args) throws IOException {
		
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q6/testForMapper2.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String input;
		String uid;
		String photoNum;
		String paddedUid;
	
		while ((input = br.readLine()) != null) {		
			input = input.trim();
			String[] parts = input.split("\t");
			uid = parts[0];
			photoNum = parts[1];
			
			// pad the uid string to the length of 10 with "0"s in front
			int lengthOfUid = uid.length();
			int lengthOfPad = 10 - lengthOfUid;
			String pad = "";
			for (int i = 0; i < lengthOfPad; i++) {
				pad += "0";
			}
			paddedUid = pad + uid;
			
			System.out.println(paddedUid + "\t" + photoNum);
		}
	}
}