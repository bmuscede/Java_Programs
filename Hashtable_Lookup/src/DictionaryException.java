/**
 * Exception that gets thrown when there is a duplicate element in the
 * dictionary or if a non-existent element is attempted to be removed.
 * @author Bryan J. Muscedere
 */
public class DictionaryException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * The main constructor that prints the contents of the error message.
	 * @param element String containing information on whether the element
	 * is already present or cannot be found.
	 */
	public DictionaryException(String e){
		super("Element in dictionary is " + e);
	}
}	
