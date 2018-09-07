package cs601.project1;

public class ReviewDataFormate 
{

	String reviewerID;
	String asin;
	String reviewerName;
	//String helpful;
	String reviewText;
	double overall;
	String summary;
	String unixReviewTime;
	String reviewTime;
	
	
	public String getReviewerID() {
		return reviewerID;
	}
	public String getAsin() {
		return asin;
	}
	public String getReviewerName() {
		return reviewerName;
	}
//	public String getHelpful() {
//		return helpful;
//	}
	public String getReviewText() {
		return reviewText;
	}
	public double getOverall() {
		return overall;
	}
	public String getSummary() {
		return summary;
	}
	public String getUnixReviewTime() {
		return unixReviewTime;
	}
	public String getReviewTime() {
		return reviewTime;
	}
	
}

