import java.io.*;
import java.util.*;

/**
 * Main method that utilizes commands to perform actions on an ordered dictionary. Loads
 * a file into memory and then sets up the tree with that file.
 * @author Bryan J. Muscedere
 */
public class Query {
	//The ordered dictionary that contains all the elements of the dictionary.
	private static OrderedDictionary smartDict;
	
	/**
	 * Main method that loads a text file, sets up the dictionary and then
	 * continues to accept commands. These commands are operations on the 
	 * tree.
	 * @param args The path of the text file to be loaded.
	 */
	public static void main(String[] args){
			//First, checks if the arguments are correct.
			if (args.length != 1){
				//Invalid path.
				System.out.println("Error: Invalid arguments entered.\n\tA path must be specified.");
				return;
			}
			
			//Creates a new dictionary and then loads the tree from the file.
			System.out.println("Loading tree from \"" + args[0] + "\"");
			smartDict = new OrderedDictionary();
			dictLoader(args[0]);
			
			//Now moves into the commands section.
			boolean end = false;
			
			//Continues looping until the end command is encountered.
			do{
				System.out.println();
				
				//Runs the command entry method.
				end = commandEntry();
			} while (end == false);
	}
	
	/**
	 * Helper method that prompts the user to enter a command and then interprets that
	 * command accordingly.
	 * @return A boolean indicating whether the program should keep accepting commands.
	 */
	private static boolean commandEntry(){
		String command = "", argument = "";
		
		//Gets the user's input.
		StringReader keyboard = new StringReader();
		String line = keyboard.read("Enter next command: ");
		
		//Splits the user's input into commands/arguments.
		StringTokenizer tokenizer= new StringTokenizer(line, " ");
		
		//Now checks to see if the command entered was correct.
		if((tokenizer.countTokens() == 0) || (tokenizer.countTokens() > 2)) {
			System.out.println("Error: Invalid entry.");
			return false;
		}
		
		//Sets the command as the first word.
		command = tokenizer.nextToken();
		if (tokenizer.hasMoreTokens()){
			//Sets the next word as the argument.
			argument = tokenizer.nextToken();
			argument = argument.toLowerCase();
		}
		
		//Looks up the command and matches the argument.
		if (command.equals("define")){
			if (argument == ""){
				//If there is no argument, there is an invalid command.
				System.out.println("Error: The command \"" + command + "\" needs an argument.");
				return false;
			}
			
			//Otherwise, gets the definition.
			defineCom(argument);
		} else if (command.equals("delete")){
			if (argument == ""){
				//If there is no argument, there is an invalid command.
				System.out.println("Error: The command \"" + command + "\" needs an argument.");
				return false;
			}
			
			//Otherwise, removes the word.
			removeCom(argument);
		} else if (command.equals("list")){
			if (argument == ""){
				//If there is no argument, there is an invalid command.
				System.out.println("Error: The command \"" + command + "\" needs an argument.");
				return false;
			}
			
			//Otherwise, lists all the words related to the prefix.
			listCom(argument);
		} else if (command.equals("next")){
			if (argument == ""){
				//If there is no argument, there is an invalid command.
				System.out.println("Error: The command \"" + command + "\" needs an argument.");
				return false;
			}
			
			//Otherwise, gets the successor.
			successorCom(argument);
		} else if (command.equals("previous")){
			if (argument == ""){
				//If there is no argument, there is an invalid command.
				System.out.println("Error: The command \"" + command + "\" needs an argument.");
				return false;
			}
			
			//Otherwise, gets the predecessor.
			predecessorCom(argument);
		} else if ((command.equals("end")) && (argument == "")){
			//Returns true to tell the program to stop accepting input.
			return true;
		} else {
			//Command isn't part of the list.
			System.out.print("Error: Command \"" + command + "\" is not valid");
			
			//Decides on the correct message to display.
			if (argument != ""){
				 System.out.println(" with the argument \"" + argument + "\".");
			} else {
				System.out.println(".");
			}
		}
		
		return false;
	}
	
	/**
	 * Method to get the definition of a word in the dictionary.
	 * @param word The word to be looked up.
	 */
	private static void defineCom(String word){
		//Gets the definition of the word.
		String definition = smartDict.findWord(word);
		//Gets the type of the word.
		int type = smartDict.findType(word);
		
		//Determines what to do with the definition.
		switch (type){
		case -1:
			//The word is not in the dictionary.
			System.out.println("Error: Definition \"" + word + "\" not found.");
			break;
		case 1:
			//Simply prints the definition.
			System.out.println(definition);
			break;
		case 2:
			//Plays the sound related to the word.
			SoundPlayer player = new SoundPlayer();
			try {
				//Tries to play the sound.
				player.play(definition);
			} catch (MultimediaException e) {
				//On error, tells the user of such.
				System.out.println("Error: Cannot play media file.");
			}
			break;
		case 3:
			//Shows the picture related to the word.
			PictureViewer view = new PictureViewer();
			try {
				//Tries to show the picture.
				view.show(definition);
			} catch (MultimediaException e) {
				//On error, tells the user of such.
				System.out.println("Error: Cannot load picture.");
			}
			break;
		}
	}
	
	/**
	 * Removes the associated word from the dictionary. Tells the user if the word
	 * is not in the dictionary.
	 * @param word The word that is desired to be removed.
	 */
	private static void removeCom(String word){
		try {
			//Tries to remove the word from the dictionary.
			smartDict.remove(word);
		} catch (DictionaryException e) {
			//If there is a dictionary exception, tells the user that the word cannot be found.
			System.out.println("Error: Item \"" + word + "\" is not in the dictionary.");
			return;
		}
		
		//Tells the user it was removed.
		System.out.println("Item \"" + word + "\" was removed.");
	}
	
	/**
	 * Lists all the elements in the dictionary beginning with an associated prefix.
	 * @param prefix The prefix to be used to find all those elements.
	 */
	private static void listCom(String prefix){
		//Prints a title for the list command.
		System.out.println("The elements that match the prefix are:");
		String word = prefix;
		
		//Booleans to indicate status of loop.
		boolean elements = true, added = false; 
		while (elements){
			//Gets the successor to a word.
			word = smartDict.successor(word);
			elements = false;
			
			//Sees if the word either contains or equals the prefix.
			if (word.startsWith(prefix)){
				//Prints the word and sets elements and added to true.
				System.out.print(word + "\t");
				elements = true;
				added = true;
			}
		}
		
		//If no elements were found from that prefix, prints <NONE>
		if (added == false){
			System.out.print("<None>");
		}
		System.out.println();
	}
	
	/**
	 * Prints the successor to a valid word in the dictionary.
	 * @param word The word to find the successor to.
	 */
	private static void successorCom(String word){
		//Finds the current word in the dictionary.
		String search = smartDict.findWord(word);
		
		//If there is a null string, the word is not in the dictionary.
		if (search == ""){
			//Tells the user of such.
			System.out.println("There is no such element \"" + word + "\" in the dictionary.");
			return;
		}
		
		//Otherwise, gets the successor.
		String successor = smartDict.successor(word);
		
		if (successor == ""){
			//If there is no successor, this is the greatest element.
			System.out.println("There is no successor to \"" + word + "\".");
		} else {
			//Otherwise, prints the successor.
			System.out.println("The successor is \"" + successor + "\".");
		}
	}
	
	/**
	 * Prints the predecessor to a valid word in the dictionary.
	 * @param word The word to find the predecessor to.
	 */
	private static void predecessorCom(String word){
		//Sees if the word passed is a valid word in the dictionary.
		String search = smartDict.findWord(word);
		
		//Looks to see if it was found.
		if (search == ""){
			//If it wasn't found, tells the user of such.
			System.out.println("There is no such element \"" + word + "\" in the dictionary.");
			return;
		}
		
		//Otherwise, gets the predecessor.
		String predecessor = smartDict.predecessor(word);
		
		if (predecessor == ""){
			//Sees if there is no predecessor. Tells the user of such.
			System.out.println("There is no predecessor to \"" + word + "\".");
		} else {
			//Prints the predecessor.
			System.out.println("The predecessor is \"" + predecessor + "\".");
		}
	}
	
	/**
	 * Method that accepts a path from the calling method and then loads all the elements
	 * into the dictionary from that path.
	 * @param path The path of the file containing the dictionary elements.
	 */
	private static void dictLoader(String path){
		//Creates a new buffered reader and string array of size 2.
		BufferedReader fileRead = null;
		String[] entry = new String[2];
		
		try {
			//Loads the file and sets up the buffered reader as such.
			fileRead = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			//If there is an error, tells the user and exits the program.
			System.out.println("Error: File not found.");
			System.exit(0);
		}
		 
		boolean addComplete = false;
		
		//Loops while all the elements haven't been added.
		while (addComplete == false){
			try {
				//Reads a line of the text file.
				entry[0] = fileRead.readLine();
				if (entry[0] == null){
					//If this line was null, then we've added all the elements
					addComplete = true;
					continue;
				}
				
				//Reads another line of the text file.
				entry[1] = fileRead.readLine();
				if (entry[1] == null){
					//If this line was null, then the text file was improperly formatted.
					System.out.println("Error: File contains odd number of lines.");
					System.exit(0);
				}
			} catch (IOException e){
				//If there is an error, tells the user that there is such and exits.
				System.out.println("Error: Problem reading file.");
				System.exit(0);
			}
			
			//Lowercases the word and then adds it to the dictionary.
			entry[0] = entry[0].toLowerCase();
			addItem(entry[0], entry[1]);
		}
	}
	
	/**
	 * Adds the current item into the dictionary. Creates a DictEntry object and
	 * adds it in.
	 * @param word The word to be added in to the dictionary.
	 * @param definition The definition of the corresponding word.
	 */
	private static void addItem(String word, String definition){
		//Splits the definition into parts based on '.'.
		StringTokenizer split = new StringTokenizer(definition, ".");
		int type = 1;
		
		//Sees if there is only one '.'.
		if (split.countTokens() == 2){
			//If so, goes to the next token as stores it as it's extension.
			split.nextToken();
			String extension = split.nextToken();
		
			//Determines if the extension is a media file or a picture file.
			//Sets the type accordingly.
			if (extension.equals("wav") || extension.equals("mid")){
				type = 2;
			} else if (extension.equals("gif") || extension.equals("jpg")){
				type = 3;
			}
		}
		
		try {
			//Now inserts the word, definition and type into the dictionary.
			smartDict.insert(word, definition, type);
		} catch (DictionaryException e) {
			//If there is an error, tells the user as such.
			System.out.println(e);
		}
	}
}
