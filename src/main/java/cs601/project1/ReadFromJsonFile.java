package cs601.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
public class ReadFromJsonFile 
{
	//used to store words or terms once splits using split function
	String[] separatewords;
	ReviewDataFormate df;
	InvertedIndex invertedIndex = new InvertedIndex();
	ReadFromJsonFile()
	{
		//Creating gson object
		 Gson gson = new Gson();
		 
		 //creating json parser (not needed as we are creating class and stating everything into it.
		 //JsonParser parser = new JsonParser();

        //Read the json file
		try(BufferedReader br = Files.newBufferedReader(Paths.get("sample.json")))
		{
			//int ctr=0 ;
			String line;
			//searching through file
			while((line = br.readLine()) != null)
			{
				try
				{
					//converts json object to java obj class
					df = gson.fromJson(line, ReviewDataFormate.class);
					//removing space and punctuations from review text and splitting it with space
					separatewords = df.getReviewText().replaceAll("\\p{Punct}", "").toLowerCase().split(" ");
				//	System.out.println(df.getAsin().toString());
				}catch(JsonSyntaxException jse)
				{
					System.out.println("Skipping line...." +line);
				}
				//pass value to inverted index
				
				
//				System.out.println(df.getReviewText());
				for(String term : separatewords )
				{
					if(term.equals("") == false)
						invertedIndex.insert(term, df.getReviewerID());
						//System.out.println(term);
				}
				
			}
			//System.out.println(ctr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
