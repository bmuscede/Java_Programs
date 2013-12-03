import java.util.Random;
import java.util.LinkedList;

/**
 * This class implements the dictionary ADT. This is created for a hash table that
 * holds Tic-Tac-Toe configurations as well as their associated scores.
 * @author Bryan J Muscedere
 */
public class Dictionary implements DictionaryADT{
	//Instance Variables.
	private LinkedList<DictEntry> hashDict[]; //The Linked List array that holds the table.
	private int numElements; //The number of elements in the table.
	private int scale; //The scale at which to multiply the key (used in compression).
	private int shift; //The amount to shift the key (used in compression).
	private int polyValue; //The polynomial multiplication value (used in key generation).
	
	//Constant Variables.
	private final int MAX_VAL = 2147483647; //The maximum size of an integer.
	
	/**
	 * Constructor that creates an empty dictionary of a set size.
	 * @param size The desired size of the new dictionary.
	 */
	public Dictionary(int size){
		//Creates a Linked List array of the desired size.
		hashDict= new LinkedList[size];
		//Sets the number of elements in the list to 0.
		numElements = 0;
		
		//Generates random numbers that are used for key and compression generation.
		Random generator = new Random();
		scale = generator.nextInt(MAX_VAL - 1) + 1;
		shift = generator.nextInt(MAX_VAL);
		polyValue = generator.nextInt(9998) + 2;
	}
	
	/**
	 * Helper method that generates the key from the sequence of the game board.
	 * @param config The game board configuration that is wanted to be stored.
	 * @return The key that has been generated.
	 */
	private int keyGen(String config){
		//Initializes the variable that holds the key.
		int total=0;
		
		//Loops through all the characters in the configuration string.
		for (int i = 0; i < config.length()-1; i++){
			//Using Horner's method, adds the integer value of the character.
			total += (int)config.charAt(i);
			//Multiplies by the total by the generated value.
			total *= polyValue;
		}
		//Finally, adds the last character of the string.
		total += (int)config.charAt(config.length()-1);
		
		//Returns the generated key.
		return total;
	}
	
	/**
	 * This helper method takes the generated key and compresses it using the MSB method.
	 * @param key The key that needs to be compressed.
	 * @return The new key that is compressed to fit in the dictionary.
	 */
	private int compressionGen(int key){
		//First, gets the length of the dictionary.
		int length = hashDict.length;
		
		//Now uses the MSB method to compress the key.
		key = (((scale*key) + shift) % length);
		//Ensures the value is positive.
		key = Math.abs(key);
		
		//Returns the new compressed key.
		return key;
	}
	
	/**
	 * The method that inserts a new DictEntry into the dictionary. Creates a key, compresses it,
	 * and inserts in. As well, this method deals with collisions when they arise.
	 * @param pair Contains the game board configuration and the score.
	 * @return An integer that indicates whether or not there was a configuration. 
	 */
	public int insert(DictEntry pair) throws DictionaryException {
		//Gets the configuration and stores it in a string.
		String config = pair.getConfig();
		
		//Throws an error if the item is already in the dictionary
		if (find(config) != -1) throw new DictionaryException("already present.");
		
		//Generates a key based on the current config.
		//First generates the key.
		int key = keyGen(config);
		//Next compresses the key.
		key = compressionGen(key);
		
		//Now sees if the array is empty at the key position.
		if (hashDict[key] == null){
			//Since it is empty, creates a new linked list.
			hashDict[key] = new LinkedList<DictEntry>();
			//Adds the DictEntry to the list and increases the number of elements.
			hashDict[key].add(pair);
			numElements +=1;
			return 0;
		} else {
			//There is a collision so adds it to the linked list.
			hashDict[key].add(pair);
			numElements +=1;
		}
		
		//Since there was a collision, returns 1.
		return 1;
	}

	/**
	 * Removes a DictEntry object that contains a specified configuration. If it cannot be found,
	 * the method will throw a DictionaryException.
	 * @param config The configuration that is needed to be removed.
	 */
	public void remove(String config) throws DictionaryException {
		//Generates a key from the configuration that is being searched for.
		int key = keyGen(config);
		//Compresses that new key.
		key = compressionGen(key);

		//DictEntry that will be used to hold entries in the dictionary.
		DictEntry temp = null;
		
		//Keeps track of whether or not the item was removed
		boolean removed = false;
		if (hashDict[key] == null) throw new DictionaryException("not present.");
		
		//Now iterates through the LinkedList at the position in the dictionary specified by the key.
		for (int i = 0; i < hashDict[key].size(); i++){
			//Gets the ith item in the linked list.
			temp = hashDict[key].get(i);
			
			//Checks if the configurations is equal to the one in the table.
			if (config.equals(temp.getConfig())){
				//Removes the DictEntry from the list and decrements the numElements in the list.
				hashDict[key].remove(i);
				numElements -= 1;
				
				//Sees if it was the only element at that key.
				if (hashDict[key].isEmpty()) 
					hashDict[key] = null;
				
				//Sets the removed boolean to true.
				removed = true;
				break;
			}
		}
		
		//If the item hasn't been removed/found, throws an exception.
		if (removed == false) throw new DictionaryException("not present.");
	}

	/**
	 * Method that finds the desired configuration in the dictionary and returns its associated score.
	 * @param config The desired configuration that is wanted to be found in the dictionary.
	 * @return The score of the associated configuration (or -1 if not found).
	 */
	public int find(String config) {
		//Generates a key from the configuration that is being searched for. 
		int key = keyGen(config);
		//Compresses the new key.
		key = compressionGen(key);
		
		//DictEntry that will be used to hold entries in the dictionary.
		DictEntry temp = null;
		
		//Sees if the dictionary at that key is empty. If it is, returns -1.
		if (hashDict[key] == null){
			return -1;
		}
		
		//Now iterates through the collection to find the element.
		for (int i = 0; i < hashDict[key].size(); i++){
			//Stores the ith item in the temporary DictEntry.
			temp = hashDict[key].get(i);
			
			//Now sees if the ith DictEntry equals the searched config.
			if (config.equals(temp.getConfig())){
				//It is so gets its score.
				return temp.getScore();
			}
		}
		
		//Not found so returns -1.
		return -1;
	}

	/**
	 * Returns the number of elements in the dictionary.
	 * @return The number of elements.
	 */
	public int numElements() {
		return numElements;
	}
}
