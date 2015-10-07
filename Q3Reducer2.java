import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Q3Reducer2 {
	/**
	 * Mapper 2 for q3 final
	 */
	
	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("/Users/Lei/15619/15619Project/phase2/testForReducer2"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input;
		String originalUid = null;
		String retweetedUid;
		String currentOriginalUid = null;
		long retweetedUid_long;
		boolean beenRetweetedFlag;

		ArrayList<Long> retweetedUidList = new ArrayList<Long>();
		ArrayList<Boolean> flagList = new ArrayList<Boolean>();

		while ((input = br.readLine()) != null) {	
			beenRetweetedFlag = false;
			input = input.trim();
			String[] parts1 = input.split("\t");
			originalUid = parts1[0];
			retweetedUid = parts1[1];
			
			if (retweetedUid.contains("(")) {
				beenRetweetedFlag = true;
				retweetedUid_long = Long.parseLong(retweetedUid.substring(1, retweetedUid.length() - 1));
			} else {
				retweetedUid_long = Long.parseLong(retweetedUid);
			}
				
			if (currentOriginalUid == null) {
				// for first line
				currentOriginalUid = originalUid;
				retweetedUidList.add(retweetedUid_long);
				flagList.add(beenRetweetedFlag);
				
			} else if (currentOriginalUid.equals(originalUid)) {
				if (retweetedUid_long < retweetedUidList.get(0)) {
					retweetedUidList.add(0, retweetedUid_long );	
					flagList.add(0, beenRetweetedFlag);
				} else if (retweetedUid_long > retweetedUidList.get(retweetedUidList.size()-1)) {
					retweetedUidList.add(retweetedUid_long);
					flagList.add(beenRetweetedFlag);
				} else {
					for (int i = 0; i < retweetedUidList.size()-1; i++) { 
						if (retweetedUidList.get(i) < retweetedUid_long && retweetedUid_long < retweetedUidList.get(i+1)) {
							retweetedUidList.add(i+1, retweetedUid_long);
							flagList.add(i+1, beenRetweetedFlag);
						}
					}
				}
			} else if (!currentOriginalUid.equals(originalUid)) {
				// change to new orignalUid, print output of the previous originalUid + retweetedUidList	
				// merge results
				StringBuilder sb = new StringBuilder();
				sb.append(currentOriginalUid + "\t");
				for (int j = 0; j < retweetedUidList.size()-1; j++) {
					if (flagList.get(j) == true) {
						sb.append("("+ retweetedUidList.get(j) + ")");
					} else {
						sb.append(retweetedUidList.get(j));
					}
					sb.append("\\n");
				}
				if (flagList.get(flagList.size()-1) == true) {
					sb.append("(" + retweetedUidList.get(retweetedUidList.size()-1) + ")");
				} else {
					sb.append(retweetedUidList.get(retweetedUidList.size()-1));
				}
				
				System.out.println(sb);		
								
				// reset arraylists and currentOriginalUid
				retweetedUidList.clear();
				flagList.clear();				
				currentOriginalUid = originalUid;
				retweetedUidList.add(retweetedUid_long);
				flagList.add(beenRetweetedFlag);
			} 
		}
		// don't forget last line if it has the same time_uid as the second last line
		if (currentOriginalUid.equals(originalUid)) {			
			
			// merge results
			StringBuilder sb = new StringBuilder();
			sb.append(currentOriginalUid + "\t");
			for (int j = 0; j < retweetedUidList.size()-1; j++) {
				if (flagList.get(j) == true) {
					sb.append("("+ retweetedUidList.get(j) + ")");
				} else {
					sb.append(retweetedUidList.get(j));
				}
				sb.append("\\n");
			}
			if (flagList.get(flagList.size()-1) == true) {
				sb.append("(" + retweetedUidList.get(retweetedUidList.size()-1) + ")");
			} else {
				sb.append(retweetedUidList.get(retweetedUidList.size()-1));
			}
			
			System.out.println(sb);
					
		}
	}
}