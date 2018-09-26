package cs601.project1;

import java.util.Scanner;
/**
 * Class to print ui menu and accept user data and return result.
 * @author dhartimadeka
 *
 */
public class UICommands 
{
	//String reviewFileName = "", qaFileName = "", cmd = "", methodname = "", inputword = "" ;
	/**
	 * 
	 * @param args - It takes arguments from terminal and checks it validation.
	 * @return flag - returns true when terminal arguments are valid or else false.
	 */
	public static boolean checkarguments(String[] args)
	{	
		boolean flag = false;
		
		final String exiting = "\nTerminating Program!! Please Try again!!";
		final String fileSyntax = "Please specify proper json file type with it's path properly like\n-reviews <review_file_path> -qa <qa_file_path>";
		if(args.length == 4)
		{
			if (args[0].toLowerCase().equals("-reviews") && args[2].toLowerCase().equals("-qa")) 
			{
				if(args[1].endsWith(".json") && args[3].endsWith(".json"))
				{
					//					reviewFileName = args[1];
					flag = true;
					//					qaFileName = args[3];
				}
				else
				{//proper json file
					System.out.println(fileSyntax + exiting);
					System.exit(0);
				}
				//System.out.println("review file : " + reviewFileName + " , qa file : " + qaFileName);
			} 
		}
		else
		{
			System.out.println(fileSyntax + exiting);
			System.exit(0);
		}
		return flag;
	}
	/**
	 * This function will accept user data using scanner. it will run through do-while loop unless exit cmd is executed.
	 * 
	 * @param invertIndexReview - It is an indices of review file
	 * @param invertIndexQA - it is an indices of QA file
	 */
	public static void getUserData(InvertedIndex invertIndexReview, InvertedIndex invertIndexQA)
	{
		final String enterValidCmdInfo = "It is not a valid Command. Please enter valid command!!!\ne.g.: '<command> <term_to_search>'";
		final String thankYou = "Thank you for using our program.....!!!\nBuh-bye !!!";
		String cmd ="";
		Scanner sc = new Scanner(System.in);
		do
		{
			displayMenu();
			cmd = sc.nextLine().toLowerCase();
			if((cmd.trim().split(" ").length < 2) && (!cmd.trim().equals("exit")) )
			{
				System.out.println(enterValidCmdInfo);
				continue;
			}
			else if (cmd.trim().split(" ").length > 2) {
				// Checking if command is of valid length or not
				System.out.println(cmd.trim() + enterValidCmdInfo);
				continue;
			}
			if (cmd.trim().equals("exit")) 
			{
					System.out.println(thankYou);
					System.exit(0);
			}
			String methodname = cmd.trim().split(" ")[0];
			String inputword = cmd.trim().split(" ")[1].replaceAll("\\p{Punct}", "");
			
			switch (methodname) 
			{
			case "find":
				// find by asin
				BuildingDataStructure.displayOutput(invertIndexReview.findAsin(inputword));
				continue;
			case "reviewsearch":
				//reviewsearch
				BuildingDataStructure.displayOutput(invertIndexReview.searchterm(inputword));
				continue;
			case "qasearch":
				// search word == term
				BuildingDataStructure.displayOutput(invertIndexQA.searchterm(inputword));
				continue;
			case "reviewpartialsearch":
				// search like %term%
				BuildingDataStructure.displayOutput(invertIndexReview.partialSearch(inputword));
				continue;
			case "qapartialsearch":
				// search like %term%
				BuildingDataStructure.displayOutput(invertIndexQA.partialSearch(inputword));
				continue;
			case "exit":
				System.out.println(thankYou);
				System.exit(0);
			default:
				System.out.println(cmd.trim() + enterValidCmdInfo);
				continue;
			}
		}while(cmd != "exit");
		
		sc.close();
	}
	/**
	 * DisplayMenu will display menu of command lines.
	 */
	public static void displayMenu()
	{
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
}
