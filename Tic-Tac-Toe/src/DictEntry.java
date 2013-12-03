/**
 * Class that holds the configuration for a game board and 
 * the associated score for the game board.
 * @author Bryan J Muscedere
 */
public class DictEntry {
	//Instance variables.
	private String boardSetup; //Holds the board configuration.
	private int boardScore; //Holds the associated score.
	
	/**
	 * Constructor method that sets the current board configuration and the score
	 * associated with that configuration.
	 * @param config The board configuration to be stored.
	 * @param score The associated score to be stored.
	 */
	public DictEntry(String config, int score){
		//Stores the board configuration and its score.
		boardSetup = config;
		boardScore = score;
	}
	
	/**
	 * Gets the configuration of the board.
	 * @return The configuration of the board.
	 */
	public String getConfig(){
		return boardSetup;
	}
	/**
	 * Gets the score associated with the configuration of the board.
	 * @return The score of the stored configuration.
	 */
	public int getScore(){
		return boardScore;
	}
}
