package cs601.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 * Contains fields for storing relevant information when reading the review text
 * file
 */
public class ReviewDataFormate {

	String reviewerID;
	String asin;
	String reviewText;
	double overall;
	//final String searchType = "review";
	
	ArrayList<ReviewDataFormate> reviewDataSet = new ArrayList<ReviewDataFormate>();
	HashMap<String, List<String>> indexedValue = new HashMap<String, List<String>>();

	public void setReviewerID(String reviewerID) {
		this.reviewerID = reviewerID;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public void setOverall(double overall) {
		this.overall = overall;
	}

	public void setReviewDataSet(ArrayList<ReviewDataFormate> reviewDataSet) {
		this.reviewDataSet = reviewDataSet;
	}

//	public String getSearchType() {
//		return searchType;
//	}

	public HashMap<String, List<String>> getIndexedValue() {
		return indexedValue;
	}

	public void setIndexedValue(HashMap<String, List<String>> indexedValue) {
		System.out.println("check object r empty : "+(indexedValue.size()));
		this.indexedValue = indexedValue;
	}

	public ArrayList<ReviewDataFormate> getReviewDataSet() {
		return reviewDataSet;
	}

	public String getReviewerID() {
		return reviewerID;
	}

	public String getAsin() {
		return asin;
	}

	public String getReviewText() {
		return reviewText;
	}

	public double getOverall() {
		return overall;
	}
	public String toString()
 	{
 		return "\nASIN:-"+this.asin+" \nREVIEWER ID:"+this.reviewerID+" \nREVIEW TEXT:"+this.reviewText;
 	}

}
