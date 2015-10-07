import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Q6Reducer2{
	
	public static void main(String[] args) throws IOException {
		
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/Q6/testForReducer2.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
		 new FileOutputStream(new File("/Users/Lei/15619/15619Project/Q6/output.txt"))));
		
		String input;
		long uid;
		int photoNum;
		int photoNumAccu = 0; // accumulative photos numbers
	
		while ((input = br.readLine()) != null) {		
			input = input.trim();
			String[] parts = input.split("\t");
			uid = Long.parseLong(parts[0]);
			photoNum = Integer.parseInt(parts[1]);
			photoNumAccu += photoNum;	
			
			String out = uid + "\t" + photoNumAccu + "\n";
			System.out.println(uid + "\t" + photoNumAccu);
			wr.append(out);			
		}
		wr.close();
	}
}