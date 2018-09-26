package cs601.project1;

import java.io.BufferedReader;
//import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonSyntaxException;

public class BuildingDataStructure 
{
	//private static HashMap<String, List<String>> asinMap = new HashMap<String, List<String>>();
	
	public InvertedIndex readFile(Path pathOfFile, Object obj)
	{
		Gson gson = new Gson();
		ReviewDataFormate rdObj = null;
		QADataFormat qaObj = null;
		InvertedIndex invertedIndex = new InvertedIndex();
		HashMap<String,Integer> termToFrequency;
		String text;
		String[] finalString ;
		try(
				BufferedReader br = Files.newBufferedReader(pathOfFile, Charset.forName("ISO-8859-1")))
		{
			
			String readLineFromFile; 
			while((readLineFromFile = br.readLine()) != null)
			{
				readLineFromFile = readLineFromFile.trim().replace("\\s+", " ");
				//checking file
				if(obj instanceof ReviewDataFormate)
				{
					rdObj = gson.fromJson(readLineFromFile, ReviewDataFormate.class);
					text = rdObj.getReviewText(); //passing reviewtext
					invertedIndex.addasin(rdObj.getAsin().toLowerCase(), rdObj.toString()); //pass asin id and reviewtext
				}
				else 
				{
					try
					{
					qaObj = gson.fromJson(readLineFromFile, QADataFormat.class);
					}catch(JsonSyntaxException jse) {
						 System.out.println("Skipping...");
					}
					
					text = qaObj.getQuestion() + qaObj.getAnswer(); //passing question + answer
					invertedIndex.addasin(qaObj.getAsin().toLowerCase(), qaObj.toString()); //pass asin id and quest + ans
					//asin.addasin(qaObj.getAsin(), qaObj.toString());
				}
				finalString = text.split("\\s+"); //splitting it using space
				//each word in array
				termToFrequency = new HashMap<String,Integer> (); // hashmap of term and frequency
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
				if(obj instanceof ReviewDataFormate)
				{	
					 invertedIndex.create(termToFrequency, rdObj);
					 //System.out.println("adding data..." + invertedIndex);
				}
				else {
					invertedIndex.create(termToFrequency, qaObj);
					//System.out.println("adding data..." + invertedIndex);
				}
				
			}
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invertedIndex;
	}
	//display op
	public static void displayOutput(List<String> output)
	{
		int count = 1;
		if(output.size() == 1)
		{
			System.out.println(output);
		}
		else
		{
		for(String result : output)
		{
		
			System.out.println("\n Record:- " + count + "\n " + result );
			count ++;
		}
		}
	}
}
