public class Cmptor {

	private long count;
	private long lowestTweetId;
	private long index;
	
	// constructor
	public Cmptor(String comparator) {
		this.count = Long.parseLong(comparator.split(":")[0]);
		this.lowestTweetId = Long.parseLong(comparator.split(":")[1]);
		this.index = Long.parseLong(comparator.split(":")[2]); // though it's int, we set it as long for compareTo
	}
	
	public long getCount() {
		return count;
	}
	
	public long getIndex() {
		return index;
	}
	
	public String toString() {
		return count + ":" + lowestTweetId + ":" + index;
	}
	
	public long compare2(Cmptor o) {
		// the smaller Cmptor is more popular
		if (this.count != o.count) {			
			return o.count - this.count;
			
		} else if (this.lowestTweetId != o.lowestTweetId) {
			return this.lowestTweetId - o.lowestTweetId;	
			
		} else {
			return this.index - o.index;
		}
	}
}
