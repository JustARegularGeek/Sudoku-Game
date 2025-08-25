package core;

import java.util.Scanner;
import terminal.TerminalGame;
import application.controllers.MainController;


/**
 * Represents a Sudoku game.
 * Handles board creation, gameplay mode selection (terminal or UI),
 * and checks for board state and valid moves.
 */
public class Sudoku {
	
    // Scanner to read user input
	private Scanner scan = new Scanner(System.in); 
	
	// Constants representing the type of game
	public static final int TERMINAL_GAME = 1;
	public static final int UI_GAME = 2;
	
	private Board board;    	// The Sudoku board for this game
	private int gameType;	// Selected game type (1 = Terminal, 2 = UI)
	
	/**
     * Default constructor.
     * Initializes a standard 9x9 Sudoku board with default difficulty.
     */
	public Sudoku() {		
		this.board = new Board();
	}

    /**
     * Constructor with custom board size and difficulty.
     * @param size Board size (4 or 9)
     * @param difficulty Difficulty level (1 = Easy, 2 = Medium, 3 = Hard)
     */
	public Sudoku(int size, int difficulty) {
		this.board = new Board(size, difficulty);
	}
	
	/**
     * Returns the board associated with this game.
     * @return Board object
     */
	public Board getBoard() {
		return board;
	}
	
	/**
     * Starts the Sudoku game.
     * Asks the user for the game type and launches the selected mode.
     */
	public void play() {
		
		this.gameType = this.getGameType();
		
		if(this.gameType == TERMINAL_GAME) {
            // Launch terminal game
			TerminalGame game = new TerminalGame();
			game.playTerminalGame(scan);
		}
		
		else if(this.gameType == UI_GAME) {
			 // Launch UI game
			 MainController.launchUI();
		}
	}
	
    /**
     * Checks if a cell is empty or can accept a new value.
     * @param row Row index
     * @param column Column index
     * @param value Value to insert
     * @return true if the cell is empty or the value is 0
     */
	public boolean checkCellEmpty(int row, int column, int value) {
		
		if(value != 0 && this.board.getValue(row, column) != 0) {
			return false;
		}
		
		return true;
	}
	
    /**
     * Checks if the board is completely filled.
     * @return true if all cells are non-zero
     */
	public boolean finished() {
		
		for(int i = 0 ; i < this.board.getSize() ; i++) {
			
			for(int j = 0 ; j < this.board.getSize() ; j++) {
				
				if(this.board.getValue(i, j) == 0) {
					return false;	// Found an empty cell
				}
				
			}
			
		}
		
		return true;		// All cells filled
	}
	
    /**
     * Prompts the user to select the type of game.
     * 1 = Terminal game, 2 = UI game
     * Validates input and ensures only 1 or 2 is accepted.
     * @return Selected game type
     */
	public int getGameType() {
		
		System.out.println("Give me the type of game you want to play: ");
		System.out.println("1. Terminal Game");
		System.out.println("2. UI game");
		
		int type;
		
		do {
			
			  type = scan.nextInt();
			 
			 if(type != TERMINAL_GAME && type != UI_GAME) {
				 System.out.println("Invalid game type, please enter 1 or 2.");
				 System.out.print("Enter game type: ");
		     }
			 
		} while(type != TERMINAL_GAME && type != UI_GAME);
		
		return type;
				
	}

    /**
     * Main entry point for running the Sudoku game.
     * @param args Command-line arguments (not used)
     */
	public static void main(String[] args) {
		
		Sudoku sudoku=new Sudoku();	
		sudoku.play();
		
	}
		
}