package cs601.project1;

import java.nio.file.Path;
import java.nio.file.Paths;
//import java.sql.Time;
/**
 * 
 * @author dhartimadeka
 *Main method . it will call all other methods to build invertedindex
 */

public class AmazonSearch 
{
	//public static AddAsin asin = new AddAsin(); //bcoz we r passing same instance to both review nd qa file
	public static void main(String[] args) 
	{
		BuildingDataStructure bds = new BuildingDataStructure();
		ReviewDataFormate rdobj = new ReviewDataFormate();
		QADataFormat qaObj = new QADataFormat();
		InvertedIndex invertedIndexforReview;
		InvertedIndex invertedIndexforQA;	
		if(UICommands.checkarguments(args))
		{
		// TODO Auto-generated method stub
		Path reviewPath = Paths.get(args[1]); //passing file path
		Path qaPath = Paths.get(args[3]); //passing filepath
		System.out.println("Loading Data....");
		//Path path2 = Paths.get("sampleQA.json");
		invertedIndexforReview = bds.readFile(reviewPath,rdobj); //create inverted index for review
		invertedIndexforQA = bds.readFile(qaPath, qaObj); //create inverted index for QA.
		
		System.out.println("Data loaded successfully");
		UICommands.getUserData(invertedIndexforReview, invertedIndexforQA); //calling menu 
		}
		else
		{
			System.out.println("\nTerminating Program!! Please Try again!!");
			System.exit(0);
		}
	}

}
