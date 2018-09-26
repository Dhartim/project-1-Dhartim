package cs601.project1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
/**
 * 
 * @author dhartimadeka
 *Inverted index data structure with add, search, sort, partialsearch, addasin and find asin functionalities
 */

public class InvertedIndex 
{
	private HashMap<String, List<Wrapper>> invertedIndex = new HashMap<>();
	private static HashMap<String, List<String>> asinMap = new HashMap<>();
	//to create inverted index
	/**
	 * Create will take one term and object at a time. It will check term in data structure,
	 * If not present add it to inverted index, else it will increment word frequency of that particular word.
	 * Put data into inverted after calculating it.
	 * @param term - pass term to add from file
	 * @param obj - pass object either of review or qa file
	 */
	public void create(HashMap<String,Integer> termToFrequency, Object obj)
	{
		List<Wrapper> objectsToFrequency;
//		int entered = 0;
//		long lStartTime = System.currentTimeMillis();
		//wen it is empty
		
		for(String term:termToFrequency.keySet()) 
		{
			Wrapper wrap = new Wrapper(obj,termToFrequency.get(term));//passing obj and frequency
			//wen empty
			if(invertedIndex.containsKey(term)==false) 
			{
			objectsToFrequency =  new ArrayList<Wrapper>(); //list of obj
			objectsToFrequency.add(wrap); //add obj and count
			invertedIndex.put(term, objectsToFrequency); //put term into inverted index
		}
			else {
				objectsToFrequency = invertedIndex.get(term); //list of obj
				objectsToFrequency.add(wrap); //word is there, append wrapper list
				invertedIndex.put(term, objectsToFrequency); //add this obj to inverted index
			}
		}
	}
	
	//this is for reviewsearch and partial search
	/**
	 *  It will search element from review or QA file.
	 * @param term - term to be search
	 * @return result - List of String data with matched searches.
	 */
	public List<String> searchterm(String term)
	{
		List<Wrapper> objectToFrequency; //
		List<String> result = new ArrayList<>();
		if(invertedIndex.containsKey(term))
		{
			objectToFrequency = invertedIndex.get(term); //fetching arraylist of each term
			objectToFrequency = sort(objectToFrequency); //sorting it
			for(Wrapper wrap : objectToFrequency)
			{
				result.add(wrap.getObj().toString()); //add element into result
			}
		}
		else
		{
			result.add("Term not found !!");
		}
		return result;
		
	}
	//partial search 
	/**
	 * 
	 * @param term - term to be searched
	 * @return partialResult - List of string data that matches partial search.
	 */
	public List<String> partialSearch(String term)
	{
		int flag =0;
		List<String> partialResult = new ArrayList<>();
		//get term from inverted index
		for(String searchPartialTerm : invertedIndex.keySet())
		{
			// see that partial term is present inside invertedindex term.
			if(searchPartialTerm.contains(term))
			{
				partialResult.addAll(searchterm(searchPartialTerm)); //search that partial term in each element in inverted index
				flag = 1;
			}
		}
		if(flag == 0)
		{
			partialResult.add("Term not found !!!");
		}
		return partialResult;
	}
	/**
	 *  Sort method will sort list of wrapper objects using comparator.
	 * @param unsortedMap - pass unsorted map of type wrapper
	 * @return - A sortedMap
	 */
	public static List<Wrapper> sort(List<Wrapper> unsortedMap)
	{
 		Collections.sort(unsortedMap, new Comparator<Wrapper>(){
 			public int compare(Wrapper object1, //check value of word's frequency with another word's frequency
 					Wrapper object2) {
 				return Integer.valueOf(object1.getFrequency()).compareTo(object2.getFrequency());
 			}
 		});
 		return unsortedMap;
 	}		
	/**
	 * It will add asin elements into asinmap when called depending on it's type
	 * 
	 * @param asinId - Asin ID to be searched
	 * @param asinText - text of matched asin from review file or qa file
	 */
	public void addasin(String asinId, String asinText)
	{
		List<String> listOfAsinText;
		//wen empty
		if(asinMap.containsKey(asinId) == false)
		{
			listOfAsinText = new ArrayList<>();
			listOfAsinText.add(asinText);
		}
		else
		{
			listOfAsinText = asinMap.get(asinId);
			listOfAsinText.add(asinText);
		}
		asinMap.put(asinId.toLowerCase(), listOfAsinText);
	}
	/**
	 * FindAsin will search for an asin id into asinhashmap and display it's output.
	 * @param asinid - pass id to be searched
	 * @return asinResult - list of asinResult that matches with asinid of string type.
	 */
	public List<String> findAsin(String asinid)
	{
		List<String> asinResult = new ArrayList<>();
		if(asinMap.containsKey(asinid))
		{
			asinResult = asinMap.get(asinid);
		}
		else
		{
			//asinResult = new ArrayList<>(); 
			asinResult.add("Term not found !!!");
		}
		return asinResult;
	}
}
