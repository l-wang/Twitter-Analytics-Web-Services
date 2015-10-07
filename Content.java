/**
 * The Tweet content class for Q2 which implements Comparable
 * 
 * @author Justin
 * 
 */
public class Content implements Comparable<Content> {
	private String tweetIDAndScoreAndText;
	private String tweetID;

	/**
	 * Class constructor
	 * 
	 * @param tweetIDAndScoreAndText
	 *            the Tweet ID, the score, and the text which are split by
	 *            colons
	 */
	public Content(String tweetIDAndScoreAndText) {
		this.tweetIDAndScoreAndText = tweetIDAndScoreAndText;
		tweetID = tweetIDAndScoreAndText.split(":")[0];
	}

	@Override
	public int compareTo(Content other) {
		return tweetID.compareTo(other.getTweetID());
	}

	/**
	 * Gets the Tweet ID, the score, and the text
	 * 
	 * @return the content
	 */
	public String gettweetIDAndScoreAndText() {
		return tweetIDAndScoreAndText;
	}

	/**
	 * Gets the Tweet ID
	 * 
	 * @return the ID
	 */
	public String getTweetID() {
		return tweetID;
	}
}
