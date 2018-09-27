package cs601.project1;
/**
 *
 * Contains fields for storing relevant information when reading the QA text file
 */
public class QA extends Amazon
{
	private String questionType;
	//private String asin;
	private String question;
	private String answer;
	public void setQuestionType(String questionType) 
	{
		this.questionType = questionType;
	}
	public String getQuestionType() {
		return questionType;
	}

//	public String getAsin() {
//		return asin;
//	}
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