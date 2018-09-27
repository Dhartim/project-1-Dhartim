package cs601.project1;
/**
 * 
 * @author dhartimadeka
 *Wrapper classs will hold object and frequency of word in that particular ibject.
 *obj - it's an object of type review or qa file type
 *frequency - frequency of word in that json element.
 */
public class WrapperFrequency 
{
	private Object obj;
	private int frequency;

	public WrapperFrequency(Object obj, int frequency) 
	{
		this.obj = obj;
		this.frequency = frequency;
	}
	public Object getObj() {
		return obj;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
}
