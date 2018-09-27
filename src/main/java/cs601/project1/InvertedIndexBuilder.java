package cs601.project1;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class InvertedIndexBuilder 
{	
	/**
	 * 
	 * @param pathOfFile - passing file path
	 * @param obj - passing rdobj or qaobj
	 * @return inverteidindex = it will return inverted index back
	 */
	public InvertedIndex readFile(Path pathOfFile, typeOfFile fileType)
	{
		//System.out.println(obj.toString());
		Gson gson = new Gson();
		String text = null;
		String[] finalString ;
		InvertedIndex invertedIndex = new InvertedIndex();
		Review rdObj = new Review();
		QA qaObj = new QA();
		HashMap<String,Integer> termToFrequency;
		try(
				BufferedReader br = Files.newBufferedReader(pathOfFile, Charset.forName("ISO-8859-1")))
		{
			String readLineFromFile; 
			while((readLineFromFile = br.readLine()) != null)
			{
				readLineFromFile = readLineFromFile.trim().replace("\\s+", " ");
//				String filecheck = rdObj.getClass().getSimpleName();
//				System.out.println(filecheck);
//				System.out.println("FT\t" + fileType);
				if(fileType == (typeOfFile.Review))
				{
					rdObj = gson.fromJson(readLineFromFile, Review.class);
					text = rdObj.getReviewText(); //passing reviewtext
					invertedIndex.addasin(rdObj.getAsin().toLowerCase(), rdObj.toString()); //pass asin id and reviewtext
				}
				else 
				{
					try
					{
						qaObj = gson.fromJson(readLineFromFile, QA.class);
					}catch(JsonSyntaxException jse) {
						System.out.println("Skipping...");
					}

					text = qaObj.getQuestion() + qaObj.getAnswer(); //passing question + answer
					invertedIndex.addasin(qaObj.getAsin().toLowerCase(), qaObj.toString()); //pass asin id and quest + ans
					//asin.addasin(qaObj.getAsin(), qaObj.toString());
				}
				finalString = text.split("\\s+"); //splitting it using space
				//each word in array
				termToFrequency = termToFrequencyHashMap(finalString);
				//if(fileType.getClass().isInstance(qaObj))
				//if(obj.getClass().isInstance(rdObj))
				if(fileType == typeOfFile.Review)
				{	
					invertedIndex = updatingInvertedIndex(rdObj, termToFrequency,invertedIndex);
					//System.out.println("adding data..." + invertedIndex);
				}
				else 
				{
					invertedIndex = updatingInvertedIndex(qaObj, termToFrequency,invertedIndex);
					//System.out.println("adding data..." + invertedIndex);
				}

			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invertedIndex;
	}
	/** 
	 * Taking each word from string and storing it into inverted index with it's frequency 
	 * 
	 * @param finalString - pass list of String[]
	 * @return termToFrequency - will return hashmap of termtoFrequency
	 */
	public HashMap<String, Integer> termToFrequencyHashMap(String[] finalString)
	{
		HashMap<String, Integer>termToFrequency = new HashMap<String,Integer> (); // hashmap of term and frequency
		for(String eachword : finalString)
		{
			eachword = eachword.replaceAll("\\p{Punct}", "").toLowerCase();
			//wen empty
			if(termToFrequency.containsKey(eachword)==false) 
			{
				termToFrequency.put(eachword, 1); 
			}
			else 
			{
				termToFrequency.put(eachword,termToFrequency.get(eachword)+1); //passing term and frequency of that word + 1;
			}

		}
		return termToFrequency;
	}
/**
 * Calling create function 
 * 
 * @param obj - pass object of review or QA
 * @param termToFrequency - pass hashmap of term and frequency
 * @param invertedIndex - pass inverted index to be updated
 * @return invertedIndex - return updated invertedIndex 
 */
	public InvertedIndex updatingInvertedIndex(Object obj, HashMap<String, Integer> termToFrequency,InvertedIndex invertedIndex)
	{	
		invertedIndex.create(termToFrequency, obj);
		return invertedIndex; 
	}
}
