package cs601.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
*
* Contains fields for storing relevant information when reading the QA text file
*/
public class QADataFormat {
	String questionType;
	String asin;
	String question;
	String answer;
	//final String searchType = "qa";
	
//	public String getSearchType() {
//		return searchType;
//	}

	ArrayList<QADataFormat> qaDataSet = new ArrayList<QADataFormat>();
	HashMap<String, List<String>> indexedValue = new HashMap<String, List<String>>();
	
	public HashMap<String, List<String>> getIndexedValue() {
		return indexedValue;
	}

	public void setIndexedValue(HashMap<String, List<String>> indexedValue) {
		this.indexedValue = indexedValue;
	}

	public ArrayList<QADataFormat> getQaDataSet() {
		return qaDataSet;
	}
	public void setQaDataSet(ArrayList<QADataFormat> qaDataSet) {
		this.qaDataSet = qaDataSet;
	}
	public void setQuestionType(String questionType) 
	{
		this.questionType = questionType;
	}
	public String getQuestionType() {
		return questionType;
	}

	public String getAsin() {
		return asin;
	}
	public String getQuestion() {
		return question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public String toString()
 	{
 		return "\nASIN:-"+this.asin+" \nQuestion:"+this.question+" \nAnswer:"+this.answer;
 	}

}