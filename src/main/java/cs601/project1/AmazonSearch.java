package cs601.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import cs601.project1.ReviewDataFormate;
import cs601.project1.QADataFormat;
import cs601.project1.InvertedIndex;

/**
 * Amazon QA and Review Dictionary
 * 
 * Searches for word in QA and Review data, Searches Product by it's Asin Id
 * 
 */

public class AmazonSearch {
	
	public static ReviewDataFormate rdObj = new ReviewDataFormate();
	public static QADataFormat qaObj = new QADataFormat();

	public static void main(String[] args) {

		final String enterValidCmdInfo = " is not a valid Command. Please enter valid command!!!\ne.g.: '<command> <term_to_search>'";
		final String exiting = "\nTerminating Program!! Please Try again!!";
		final String thankYou = "Thank you for using our program.....!!!\nBuh-bye !!!";
		final String fileSyntax = "Please specify proper json file type with it's path properly like\n-reviews <review_file_path> -qa <qa_file_path>";

		AmazonSearch as = new AmazonSearch();
		UIOperations ui = new UIOperations();
		Scanner sc = new Scanner(System.in);

		String reviewFileName = "", qaFileName = "", cmd = "", methodname = "", inputword = "";
		ArrayList<String> result = new ArrayList<String>();

		// Check for correct parameters passed or not
		if (args.length == 4) {
			if (args[0].toLowerCase().equals("-reviews") && args[2].toLowerCase().equals("-qa")) 
			{
				if(args[1].endsWith(".json"))
				{
					reviewFileName = args[1];
					qaFileName = args[3];
				}
				else
				{//proper json file
					System.out.println(fileSyntax + exiting);
					System.exit(0);
				}
				//System.out.println("review file : " + reviewFileName + " , qa file : " + qaFileName);
			} else if (args[0].toLowerCase().equals("-qa") && args[2].toLowerCase().equals("-reviews")) {
				if(args[1].endsWith(".json"))
				{
					qaFileName = args[1];
					reviewFileName = args[3];
				}
				else
				{
					System.out.println(fileSyntax + exiting);
					System.exit(0);
				}
				System.out.println("review file : " + reviewFileName + " , qa file : " + qaFileName);
			}
		} else {
			System.out.println(fileSyntax + exiting);
			System.exit(0);
		}
		System.out.print("Loading Data");

		// load data and form data structure to improve user experience
		Initilizedata(reviewFileName, qaFileName);

		System.out.println("\nData loaded succesfully!");

		do {
			ui.DisplayMenu();
			cmd = "";
			cmd = sc.nextLine().toLowerCase();

			if ((cmd.trim().split(" ").length < 2) && (!cmd.trim().equals("exit"))) {
				System.out.println(cmd.trim() + enterValidCmdInfo);
				continue;
				//System.exit(0);
			} else if (cmd.trim().split(" ").length > 2) {
				// Checking if command is of valid length or not
				System.out.println(cmd.trim() + enterValidCmdInfo);
				continue;
				//System.exit(0);
			}
			if (cmd.trim().equals("exit")) {
				System.out.println(thankYou);
				System.exit(0);
			}
			methodname = cmd.trim().split(" ")[0];
			inputword = cmd.trim().split(" ")[1].replaceAll("\\p{Punct}", "");
			System.out.println("OutPut :");
			switch (methodname) {
			case "find":
				// find by asin
				result = ui.findAsin(inputword, rdObj, qaObj);
				as.displayOutput(result);
				continue;
			case "reviewsearch":
				// search word == term
				as.displayReviewOutput(ui.searchTerm(inputword, rdObj, null, rdObj.getSearchType()), rdObj);
				continue;
			case "qasearch":
				// search word == term
				as.displayQAOutput(ui.searchTerm(inputword, null, qaObj, qaObj.getSearchType()), qaObj);
				continue;
			case "reviewpartialsearch":
				// search like %term%
				as.displayReviewOutput(ui.partialSearch(inputword, rdObj, null, rdObj.getSearchType()), rdObj);
				continue;
			case "qapartialsearch":
				// search like %term%
				as.displayQAOutput(ui.partialSearch(inputword, null, qaObj, qaObj.getSearchType()), qaObj);
				continue;
			case "exit":
				System.out.println(thankYou);
				System.exit(0);
			default:
				System.out.println(cmd.trim() + enterValidCmdInfo);
				continue;
			}
		} while (cmd != "exit");

		sc.close();
	}

	/**
	 * Display output on terminal.
	 * 
	 * @param result - List of data asin.
	 */

	public void displayOutput(ArrayList<String> result) {
		int linecount = 1;
		for (String i : result) {
			System.out.println("Record:- " + linecount + " " + i);
			linecount = linecount + 1;
		}
		System.out.println();
	}

	/**
	 * Display data for review type.
	 * 
	 * @param linesInOrder - sorted list of line number.
	 */
	public void displayReviewOutput(ArrayList<Integer> linesInOrder, ReviewDataFormate rd) {

		ArrayList<ReviewDataFormate> dataSet = rd.getReviewDataSet();
		int linecount = 1;
		for (int line : linesInOrder) {
			System.out.print("\n Record:- " + linecount);
			rd = dataSet.get(line);
			System.out.println("\n ASIN:- " + rd.getAsin() + "\n REVIEWER ID:- " + rd.getReviewerID()
					+ "\n REVIEWTEXT:- " + rd.getReviewText() + "\n Overall Score:- " + rd.getOverall());
			linecount = linecount + 1;
		}
		System.out.println();
	}

	/**
	 * Display data for QA type.
	 * 
	 * @param linesInOrder - sorted list of line number.
	 */
	public void displayQAOutput(ArrayList<Integer> linesInOrder, QADataFormat qa) {

		ArrayList<QADataFormat> dataSet = qa.getQaDataSet();
		int linecount = 1;
		for (int line : linesInOrder) {
			System.out.print("\n Record:- " + linecount);
			qa = dataSet.get(line);
			System.out.println("\n ASIN:- " + qa.getAsin() + "\n QUESTION:- " + qa.getQuestion() + "\n ANSWER:- "
					+ qa.getAnswer());
			linecount = linecount + 1;
		}
		System.out.println();
	}

	/**
	 * Load data from files in to data set and create it's inverted index
	 * 
	 * @param reviewFilePath - file path of review file.
	 * @param qaFilePath     - file path of qa file.
	 */
	private static void Initilizedata(String reviewFilePath, String qaFilePath) {

		InvertedIndex invertedIndex = new InvertedIndex();
		BufferedReader br;
		Path pathReview;
		// to generalize it storing filename and path into hashmap
		HashMap<String, String> paths = new HashMap<String, String>();

		// storing file name and it's type
		paths.put(reviewFilePath, rdObj.getSearchType());
		paths.put(qaFilePath, qaObj.getSearchType());

		try {
			for (String path : paths.keySet()) {
				pathReview = Paths.get(path);
				br = Files.newBufferedReader(pathReview, Charset.forName("ISO-8859-1"));

				// load data as per it's type
				if (paths.get(path).equals(rdObj.getSearchType())) {
					invertedIndex.loadData(br, rdObj, null);
				} else {
					invertedIndex.loadData(br, null, qaObj);
				}
			}

		} catch (IOException e) {
			System.out.println("Unable to load file!\nError :" + e);
			System.exit(0);
		}
	}
}