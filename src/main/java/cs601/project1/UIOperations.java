package cs601.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//commands or functionalities class
/**
 *
 * Contains methods for processing the input data and getting the desired
 * output.
 */
public class UIOperations {

	/**
	 * Display Menu to choose from
	 */

	final String sorryText = "Sorry! Unable to find token : ";

	public void DisplayMenu() {
		System.out.println("\n*********************************List of Commands ******************************");
		System.out.println("1) Find Asin (e.g: find <ASIN_NUMBER>)");
		System.out.println("2) Review & Search Term (e.g: reviewsearch <term_to_search>)");
		System.out.println("3) QA Search (e.g: qasearch <term_to_search>)");
		System.out.println("4) Review Partial Search (e.g: reviewpartialsearch <term_to_search>)");
		System.out.println("5) QA Partial Search (e.g: qapartialsearch <term_to_search>)");
		System.out.println("********************************************************************************");
		System.out.println("To Exit the program type 'exit' without quotes and hit enter!");
		System.out.println("Enter Commands :");
	}

	/**
	 * Traverse through Inverted Index and search for data which matches asin and
	 * returns list of review text or Question and answer with similar asin number
	 * 
	 * @param asin2 String - ASIN number input from user.
	 * @param rdObj ReviewDataFormate - This is an object which stores data from
	 *              review file
	 * @param qaObj QADataFormat - Object which stores data from QA file.
	 * @return result ArrayList<String> - Stores and returns list of data with same
	 *         asin number.
	 * 
	 **/
	public ArrayList<String> findAsin(String asin2, ReviewDataFormate rdObj, QADataFormat qaObj) {
		int flag = 0;
		ArrayList<String> result = new ArrayList<String>();

		for (ReviewDataFormate rd : rdObj.getReviewDataSet()) {
			if (rd.getAsin().trim().toLowerCase().equals(asin2.trim().toLowerCase())) {
				result.add("\n REVIEW TEXT:- " + rd.getReviewText());
			//	flag = 1;
			}
		}
		for (QADataFormat qa : qaObj.getQaDataSet()) {
			if (qa.getAsin().toLowerCase().equals(asin2)) {
				result.add("\n Question : " + qa.getQuestion() + "\n Answer : " + qa.getAnswer());
				//flag = 1;
			}
		}
		if (flag == 0) 
		{
			System.out.println(sorryText + asin2);
		}
		return result;
	}

	/**
	 * Search for the word in index value list and returns line number
	 * 
	 * @param term  - term to be search
	 * @param rdObj - This is an object which stores data from review file
	 * @param qaObj - Object which stores data from QA file.
	 * @return result - List of data that has term in it.
	 */

	public ArrayList<Integer> searchTerm(String term, ReviewDataFormate rdObj, QADataFormat qaObj, String searchType) {
		Operation op = new Operation();
		ArrayList<Integer> res = new ArrayList<Integer>();
		HashMap<String, List<String>> indexedValue = new HashMap<String, List<String>>();

		if (rdObj != null && searchType.equals(rdObj.getSearchType())) {
			indexedValue = rdObj.getIndexedValue();
		} else {
			indexedValue = qaObj.getIndexedValue();
		}

		if (indexedValue.get(term.trim().toLowerCase()) != null) {
			res = op.searchAndFetch(term, rdObj, qaObj, searchType);
		} else {
			System.out.println(sorryText + term);
		}
		return res;
	}

	/**
	 * Traverse through inverted index and find partial match of term entered by
	 * user.
	 * 
	 * @param term  String - term for partial search.
	 * @param rdObj ReviewDataFormate - This is an object which stores data from
	 *              review file
	 * @param qaObj QADataFormat - Object which stores data from QA file.
	 */

	public ArrayList<Integer> partialSearch(String term, ReviewDataFormate rdObj, QADataFormat qaObj, String searchType) {
		AmazonSearch as = new AmazonSearch();
		HashMap<String, List<String>> indexedValue = new HashMap<String, List<String>>();
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<Integer> linesInorder = new ArrayList<Integer>();
		Operation op = new Operation();
		//int flag = 0;

		if (rdObj != null && searchType.equals(rdObj.getSearchType())) {
			indexedValue = rdObj.getIndexedValue();
		} else {
			indexedValue = qaObj.getIndexedValue();
		}

		for (String i : indexedValue.keySet()) {
			// matches the partial term with each word in index value key set
			if (i.indexOf(term.trim().toLowerCase()) > -1) {
			//	flag = 1;
				if (result != null) {
					linesInorder.addAll(op.searchAndFetch(i, rdObj, qaObj, searchType));
					as.displayOutput(result);
				} else {
					linesInorder = op.searchAndFetch(i, rdObj, qaObj, searchType);
				}
			}
		}
		/*if (flag == 0) {
			System.out.println(sorryText + term);
		}*/
		return linesInorder;
	}
}
