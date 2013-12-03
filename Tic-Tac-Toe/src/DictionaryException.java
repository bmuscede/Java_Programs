/**
 * Class that defines an exception that is thrown when an element
 * is already present in a dictionary or when an element that is not
 * present in a dictionary is attempted to be removed.
 * @author Bryan J Muscedere
 */
public class DictionaryException extends Exception  {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The main constructor that prints the contents of the error message.
	 * @param element String containing information on whether the element
	 * was being removed or inserted.
	 */
	public DictionaryException(String element){
		super ("Element is " + element);
	}
}
