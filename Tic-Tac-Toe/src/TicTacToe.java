/**
 * Class that contains the methods necessary to play Tic-Tac-Toe.
 * Evaluates the game board, maintains the dictionary and
 * holds information about the board.
 * @author Bryan J Muscedere
 */
public class TicTacToe {
	//Variable Definitions.
	private int boardSize = 0; //Holds the size of the board (n).
	private int depth = 0; //The maximum number of levels in the program.
	private int toWin = 0; //The number of consecutive x's or o's to win (k).
	private char[][] gameBoard; //The value of the game board.

	//Constant Definitions.
	private final int DICT_SIZE = 4001; //Default size of the dictionary.
	private final int DRAW = 2; //Value assigned to a draw move.
	private final int UNDECIDED = 1; //Value assigned to an undecided move.
	private final int COMPUTER_WINS = 3; //Value assigned to a computer win.
	private final int HUMAN_WINS = 0; //Value assigned to a human win.
	
	/**
	 * Constructor that sets up the board and the important variables
	 * associated with the program.
	 * @param board_size Size of the board.
	 * @param inline The number of consecutive x's and o's to win.
	 * @param max_levels The maximum number of recursive levels.
	 */
	public TicTacToe(int board_size, int inline, int max_levels){
		//First, assigns values to the board size, win and depth variables.
		this.boardSize = board_size;
		this.toWin = inline;
		this.depth = max_levels;
		
		//Next creates an empty game board of size NxN.
		gameBoard = new char[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++){
			for (int j = 0; j < boardSize; j++){
				gameBoard[i][j] = ' ';
			}
		}
	}
	
	/**
	 * Gets the size of the board (n).
	 * @return The size of the board (n).
	 */
	public int getBoardSize(){
		return boardSize;
	}
	/**
	 * Gets the amount of consecutive x's or o's to win (k).
	 * @return The number of consecutive x's or o's to win.
	 */
	public int getWinAmount(){
		return toWin;
	}
	/**
	 * Gets the depth of the game tree that the computer creates.
	 * @return The depth of the game tree.
	 */
	public int getDepth(){
		return depth;
	}
	
	/**
	 * Sets the size of the board (n).
	 * @param size The new size of the board (n).
	 */
	public void setBoardSize(int size){
		this.boardSize = size;
	}
	/**
	 * Sets the number of consecutive x's and o's needed to win (k).
	 * @param win The number of consecutive x's and o's needed to win (k).
	 */
	public void setWinAmount(int win){
		this.toWin = win;
	}
	/**
	 * Sets the depth of the game tree.
	 * @param depth The new depth for the game tree.
	 */
	public void setDepth(int depth){
		this.depth = depth;
	}
	
	/**
	 * Sees if the square is empty at the desired row and column.
	 * @param row The desired row to be analyzed.
	 * @param col The desired column to be analyzed.
	 * @return A boolean indicating if the space is empty or full.
	 */
	public boolean squareIsEmpty(int row, int col){
		//Looks at the desired square on the board.
		if (gameBoard[row][col] == ' '){
			//Empty.
			return true;
		}
		
		//Full.
		return false;
	}

	/**
	 * Stores either an X or O in a desired square on the board.
	 * @param row The row of the desired square.
	 * @param col The column of the desired square.
	 * @param symbol Either an X or an O.
	 */
	public void storePlay(int row, int col, char symbol){
		gameBoard[row][col] = symbol;
	}

	/**
	 * Method that determines if a certain player won.
	 * @param symbol Either an X or an O indicating the player.
	 * @return A boolean indicating whether that player won or not.
	 */
	public boolean wins(char symbol){
		//Sees if either the human or computer has won.
		if (symbol == 'X' && evalBoard() == HUMAN_WINS){
			//The human player has won.
			return true;
		} else if (symbol == 'O' && evalBoard() == COMPUTER_WINS){
			//The computer has won.
			return true;
		}
		
		//The game is still ongoing/nobody has won.
		return false;
	}
	
	/**
	 * Sees if the current game board is full
	 * and nobody has won.
	 * @return A boolean indicating if the game has been tied.
	 */
	public boolean isDraw(){
		//Calls the evalBoard method and stores the result.
		int result = evalBoard();
		
		//Looks to see if a draw was found.
		if (result == DRAW){
			return true;
		}
		
		//Since no draw was found, returns false.
		return false;
	}

	/**
	 * Evaluates the current situation on the board
	 * and sees if the human/computer have won, if the
	 * game is a draw or if the game is still ongoing.
	 * @return An integer indicating the status of the game.
	 */
	public int evalBoard() {
		String winStringPlayer = "";
		String winStringComp = "";

		//Sets up two strings, each with the number of consecutive X's or O's needed to win.
		for (int i = 1; i <= toWin; i++){
			winStringPlayer = winStringPlayer + 'X';
			winStringComp = winStringComp + 'O';
		}
		
		//Now performs checks.
		//First, looks to see if player/computer won horizontally.
		if (horizontalCheck(winStringPlayer)){
			return HUMAN_WINS;
		} else if (horizontalCheck(winStringComp)){
			return COMPUTER_WINS;
		}
		
		//Now sees if the player/computer won vertically.
		if (verticalCheck(winStringPlayer)){
			return HUMAN_WINS;
		} else if (verticalCheck(winStringComp)){
			return COMPUTER_WINS;
		}
		
		//Lastly, checks if the player/computer won diagonally.
		if (diagonalCheck(winStringPlayer)){
			return HUMAN_WINS;
		} else if (diagonalCheck(winStringComp)){
			return COMPUTER_WINS;
		}
		
		//Finally, if this all fails, checks to see if the game is tied.
		if (boardFull()){
			return DRAW;
		}
		
		//After all these checks, if nothing was triggered, the game is still ongoing.
		return UNDECIDED;
	}
	
	/**
	 * Helper method that checks to see whether a certain player has
	 * won diagonally.
	 * @param winString The number of consecutive X's or O's needed to win the game.
	 * @return A boolean indicating whether or not that player has won diagonally.
	 */
	private boolean diagonalCheck(String winString){
		//Creates 4 empty strings that hold the board's diagonal setup.
		String boardDia = "";
		String boardDia2 = "";
		String boardDia3 = "";
		String boardDia4 = "";
		
		/*
		 * These integers determine which of the squares on the
		 * board are inserted into each boardDia string.
		 */
		int j = 0;
		int k = 0; 
		int l = boardSize - 1;
		int m = 0;
		
		//Loops through the board.
		while (j < boardSize){
			//Resets variables k and m to the values of j and l.
			k = j;
			m = l;
			//Loops through the diagonal positions of the board.
			for (int i = 0; k < boardSize; i++){
				//Adds the current square into each of the four boardDia variables.
				boardDia = boardDia + gameBoard[k][i];
				boardDia2 = boardDia2 + gameBoard[i][k];
				boardDia3 = boardDia3 + gameBoard[i][m];
				boardDia4 = boardDia4 + gameBoard[k][(boardSize - 1) - i];
				//Increments k and decrements m so that the next proper square on the board can be represented.
				k++;
				m--;
			}
			
			//Now sees if any of these current strings contains the winString.
			if ((boardDia.contains(winString)) || (boardDia2.contains(winString)) || 
					(boardDia3.contains(winString)) || (boardDia4.contains(winString))){
				//The player has won diagonally.
				return true;
			}
			
			//Increments j and decrements l.
			j++;
			l--;
			
			//Resets all of the boardDia strings.
			boardDia="";
			boardDia2="";
			boardDia3="";
			boardDia4="";
		}
		
		//Since nothing was triggered, this player did not win diagonally.
		return false;
	}

	/**
	 * Helper method that determines whether or not the board is full.
	 * @return A boolean indicating whether or not there are empty spaces.
	 */
	private boolean boardFull(){
		//Loops through all the rows of the board.
		for (int i = 0; i < boardSize; i++){
			//Loops through all the columns of the board.
			for (int j = 0; j < boardSize; j++){
				//Sees if an empty space was found at the current position.
				if (gameBoard[i][j] == ' '){
					return false;
				}
			}
		}
		
		//If it didn't find a blank space, returns true.
		return true;
	}
	
	/**
	 * Helper method that indicates whether or not a player has won horizontally.
	 * @param winString The number of consecutive x's or o's required to win the game.
	 * @return A boolean indicating whether or not the player has won horizontally.
	 */
	private boolean horizontalCheck(String winString){
		//Creates an empty string that will hold each row.
		String boardRow = "";
		
		//Loops through each row.
		for (int i = 0; i < boardSize; i++){
			//Loops through each column in each row.
			for (int j = 0; j < boardSize; j++){
				//Stores the current square in a string with the other squares in that row.
				boardRow = boardRow + gameBoard[i][j];
			}
			
			//Checks to see if that current row contains the win string.
			if (boardRow.contains(winString)){
				//It does.
				return true;
			}
			
			//If not, resets the row string and checks the next row.
			boardRow = "";
		}
		
		//Nothing was triggered so the player did not win horizontally.
		return false;
	}
	
	/**
	 * Helper method that indicates whether or not a player has won vertically.
	 * @param winString The number of consecutive x's or or's required to win the game.
	 * @return A boolean indicating whether or not a player has won vertically.
	 */
	private boolean verticalCheck(String winString){		
		//Sets up a string that will store the contents of each column.
		String boardCol = "";
		
		//Loops through each column.
		for (int i = 0; i < boardSize; i++){
			//Loops through each row.
			for (int j = 0; j < boardSize; j++){
				//Stores the current square in a string with other squares from that column.
				boardCol = boardCol + gameBoard[j][i];
			}
			
			//Sees if the player has won in that column.
			if (boardCol.contains(winString)){
				return true;
			}
			
			//Now resets the column string.
			boardCol = "";
		}
		
		//Nothing was triggered so the player did not win vertically.
		return false;
	}
	
	/**
	 * Creates a new, empty dictionary based on the constant size.
	 * @return The new dictionary that was created.
	 */
	public Dictionary createDictionary() {
		//Creates the dictionary of constant size and returns it.
		Dictionary newDict = new Dictionary(DICT_SIZE);
		return newDict;
	}

	/**
	 * Helper method that converts the array gameBoard to a string.
	 * @return The string containing the current game board.
	 */
	private String gameConfig(){
		//Creates an empty string to hold the board.
		String gameBoardConfig = "";
		
		//First, loops through all the rows.
		for (int row = 0; row < boardSize; row++){
			//Loops through all the columns.
			for (int col = 0; col < boardSize; col++){
				//Stores all the squares in the config string.
				gameBoardConfig = gameBoardConfig + gameBoard[row][col]; 
			}
		}
		
		//Returns the config of the current game board.
		return gameBoardConfig;
	}
	
	/**
	 * Sees if a certain configuration is already contained within the dictionary.
	 * @param configurations The dictionary of configurations that will be checked.
	 * @return The score associated with the current config (or -1 if not found).
	 */
	public int repeatedConfig(Dictionary configurations){
		//First gets the current board config.
		String config = gameConfig();
		
		//Now looks in the dictionary for the score and returns the result.
		int result = configurations.find(config);
		return result;
	}

	/**
	 * Method that inserts the current configuration and a score into a dictionary
	 * @param configurations The dictionary that holds all the configurations.
	 * @param score The score associated with the current configuration.
	 */
	public void insertConfig(Dictionary configurations, int score){
		//Gets the current configuration of the board.
		String config = gameConfig();
		
		//Stores the configuration and score in a DictEntry item.
		DictEntry newEntry = new DictEntry(config, score);
		
		try {
			//Inserts the configuration into the dictionary.
			configurations.insert(newEntry);
		} catch (DictionaryException e) {
			//Configuration is already in the dictionary.
			//Prints out error message.
			System.out.println(e);
		}
	}
}
