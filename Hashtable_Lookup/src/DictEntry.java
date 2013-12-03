/**
 * Class that contains all the information about each definition. Defines
 * the word and definition of the word as well as the media type.
 * @author Bryan J. Muscedere
 */
public class DictEntry {
	private String word;
	private String definition;
	private int type;
	
	/**
	 * Constructor method that initially sets up the DictEntry object.
	 * @param word The word of the object.
	 * @param definition The definition of the object
	 * @param type The type of the object
	 */
	public DictEntry(String word, String definition, int type){
		//Sets the parameters to the variables in this class.
		this.word = word;
		this.definition = definition;
		this.type = type;
	}
	
	/**
	 * Returns the word of this DictEntry object.
	 * @return The word of the object.
	 */
	public String word(){
		return this.word;
	}
	
	/**
	 * Returns the definition of the DictEntry object
	 * @return The definition associated with the word of this object.
	 */
	public String definition(){
		return this.definition;
	}
	
	/**
	 * Returns the type of this DictEntry object.
	 * @return The media type of the DictEntry object.
	 */
	public int type(){
		return this.type;
	}
}