package cs601.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Loading file and forming it's inverted index Each inverted index contain word
 * as key and list of line numbers in which word occur as it's value
 */
public class InvertedIndex {

	/**
	 * This function will load data from rdOBj or qaObj, load it and call add
	 * element method to add each term into inverted index
	 * 
	 * @param br    - object to read file
	 * @param rdObj - This is an object which stores data from review file
	 * @param qaObj - Object which stores data from QA file
	 */
	public void loadData(BufferedReader br, ReviewDataFormate rdObj, QADataFormat qaObj) {
		String line = "";
		String[] finalString;
		ReviewDataFormate rDf;
		QADataFormat qDf;
		Gson gson;
		int lineCount = 0;

		try {
			while ((line = br.readLine()) != null) {
				try {
					line = line.trim().replace("\\s+", " ");
					if (rdObj != null) {
						gson = new Gson();
						rDf = gson.fromJson(line, ReviewDataFormate.class);
						rdObj.getReviewDataSet().add(rDf);
						finalString = rDf.getReviewText().replaceAll("\\p{Punct}", "").toLowerCase().split("\\s+");
					} else {
						gson = new Gson();
						qDf = gson.fromJson(line, QADataFormat.class);
						qaObj.getQaDataSet().add(qDf);
						String temp = qDf.getQuestion() + " " + qDf.getAnswer();
						finalString = temp.replaceAll("\\p{Punct}", "").toLowerCase().split("\\s+");
					}

					for (String term : finalString) {
						if (!term.equals("")) {
							addElement(term, "" + lineCount, rdObj, qaObj);
						}
					}
					if (lineCount % 5000 == 0) {
						System.out.print(".");
					}
					lineCount = lineCount + 1;
				} catch (JsonSyntaxException e) {
					System.out.print("\nSkipping line no." + (lineCount + 1));
				}
			}
		} catch (IOException io) {
			System.out.println("Invalid file syntax line no. " + lineCount + " \nError :" + io);
		}

	}

	/**
	 * Function to add each word and it's respective line number's
	 * 
	 * @param term      - word to add in inverted index
	 * @param linecount - count of number of records in inverted index.
	 * @param rdObj     - This is an object which stores data from review file
	 * @param qaObj     - Object which stores data from QA file.
	 */

	public void addElement(String term, String lineCount, ReviewDataFormate rdObj, QADataFormat qaObj) {
		HashMap<String, List<String>> indexedValue = new HashMap<String, List<String>>();

		if (rdObj != null) {
			indexedValue = rdObj.getIndexedValue();
		} else {
			indexedValue = qaObj.getIndexedValue();
		}

		if (indexedValue.get(term) != null) {
			// if word already exist add line number in the list
			indexedValue.get(term).add(lineCount);
		} else {
			// if word does not exist add the word with it's respective line number
			List<String> a = new ArrayList<String>();
			a.add(lineCount);
			indexedValue.put(term, a);
		}
	}

}
