package cs601.project1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Operation {

	/**
	 * It searches a word in index valued list and fetches the line number of it's
	 * occurrence and return the line number list
	 * 
	 * @param word  - term to be searched
	 * @param rdObj - Object which stores data from review file.
	 * @param qaObj - Object which stores data from QA file
	 * @return linesInOrder - list of line number in descending order of frequency
	 *
	 */

	public ArrayList<Integer> searchAndFetch(String word, ReviewDataFormate rdObj, QADataFormat qaObj, String searchType) {
		HashMap<String, List<String>> indexedValue = new HashMap<String, List<String>>();
		Map<String, Long> wordCount;
		ArrayList<Integer> linesInOrder;

		if (rdObj != null && searchType.equals(rdObj.getSearchType())) {
			indexedValue = rdObj.getIndexedValue();
		} else {
			indexedValue = qaObj.getIndexedValue();
		}
		wordCount = wordCount(indexedValue.get(word)); // count words
		linesInOrder = sortPages(wordCount);// sorting
		if (rdObj != null && searchType.equals(rdObj.getSearchType())) {
			indexedValue = rdObj.getIndexedValue();
		} else {
			indexedValue = qaObj.getIndexedValue();
		}
		return linesInOrder;
	}

	/**
	 * sort the page number on the basis of it's occurence frequency in descending
	 * order
	 * 
	 * @param wordCount - List of words with it's frequency
	 * @return sortedLines - List of sorted data in descending
	 *         order.
	 */
	public ArrayList<Integer> sortPages(Map<String, Long> wordCount) {
		ArrayList<Integer> sortedLines = new ArrayList<Integer>();
		Set<Long> valueSet = new HashSet<Long>();

		Collection<Long> values = wordCount.values(); // it will give word count in each document
		valueSet.addAll(values); // sorting word count in ascending order independent of doc

		for (Long i : valueSet) {
			for (String j : wordCount.keySet()) // document_id/line_number as keyset
			{
				/*
				 * comparing the sorted word count with word count for each document and store
				 * it in list
				 */
				if (i == wordCount.get(j)) {
					sortedLines.add(Integer.parseInt(j)); //
				}
			}
		}
		/*
		 * List was sorted in ascending order reversing it's to get it in descending
		 * order
		 */
		Collections.reverse(sortedLines);
		return sortedLines;
	}

	/**
	 * forming key value pair of word and it's respective count
	 * 
	 * @param list - list of data passed.
	 * @return wordCount - Map with word as key and it's count as value.
	 */
	public Map<String, Long> wordCount(List<String> list) {
		/*
		 * using stream to iterate through each element in list and grouping identical
		 * words and get it's count.
		 * 
		 * Similar to SQL Query like 'select count(*) from <table_name> group by
		 * <column_name>'
		 * 
		 * stream() - iterates through each element in list collect - making collection
		 * of terms Collectors.groupingBy - perform group by on specified words
		 * Function,identity - Collectors.counting() - get the count of the given
		 * array/list
		 */
		Map<String, Long> wordCount = list.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		return wordCount;
	}

}
