package terminal;

import java.util.Scanner;
import core.Board;
import core.Sudoku;

/**
 * Represents a terminal-based interface for playing Sudoku.
 * Provides methods to interact with the user, get board size and difficulty,
 * validate moves, and print the board in a readable format.
 * 
 * Usage example:
 * <pre>
 * Scanner scan = new Scanner(System.in);
 * TerminalGame game = new TerminalGame();
 * game.playTerminalGame(scan);
 * </pre>
 */
public class TerminalGame {
	
	private UserChoice choice;	// Handles user input and choices
	private Sudoku game;			// The Sudoku game instance
	
	/**
     * Default constructor initializing UserChoice and Sudoku.
     */
	public TerminalGame() {
		this.choice = new UserChoice();
		this.game = new Sudoku();
	}

    /**
     * Starts and manages the terminal-based Sudoku game loop.
     * @param scan Scanner object to read user input
     */
	public void playTerminalGame(Scanner scan) {
		
		int N = getSize(scan);
		
		int difficultyLevel = getDifficultyLevel(scan);
		
		game = new Sudoku(N, difficultyLevel);
				
		game.getBoard().initializeBoard();
		
		scan.nextLine();
		
		while(!game.finished()) {
						
			do {
				
				this.print();
				
				if(this.choice.getUserChoice(scan, game.getBoard())) {
					return;
				}
			
			} while(!this.choice.isMoveWithinBounds(game.getBoard().getSize()) || !this.isValidMove(this.choice.getRow(), this.choice.getColumn(), this.choice.getValue()));
			
			if(!game.getBoard().isCellFixed(this.choice.getRow(), this.choice.getColumn())) {
				game.getBoard().setValue(choice.getRow(), choice.getColumn(), choice.getValue());		
			}
		
		}
		
		System.out.println("Congratulations! You have solved the Sudoku.");
		
	}
	
    /**
     * Checks if a user's move is valid according to Sudoku rules.
     * Prints an error message if invalid.
     * @param row row index of the move
     * @param column column index of the move
     * @param value value to insert
     * @return true if the move is valid
     */
	public boolean isValidMove(int row, int column, int value) {
		
		if(value > 0) {
		
			if(!game.checkCellEmpty(row, column, value)) {
				System.out.println("Error: cell is already occupied!");
				return false;
			}
			
			if(!game.getBoard().checkRow(row, value)) {
				System.out.println("Error: Illegal value insertion! Invalid row.");
				return false;
			}
			
			if(!game.getBoard().checkColumn(column, value)) {
				System.out.println("Error: Illegal value insertion! Invalid column.");
				return false;			
			}
			
			if(!game.getBoard().checkBox(row, column, value)) {
				System.out.println("Error: Illegal value insertion! Invalid box.");
				return false;
			}
		
		}
		
		return true;
	}
	
	 /**
     * Prompts the user to choose the size of the Sudoku board.
     * Only allows 4x4 or 9x9 boards.
     * @param scan Scanner object to read user input
     * @return chosen board size
     */
	public int getSize(Scanner scan) {
		
        System.out.print("Give me the size of the sudoku you would like to play (4 or 9): ");
		
		int N;
		
		do {
			
			 N = scan.nextInt();
			 
			 if(N != Board.SIZE_EASY && N != Board.SIZE_STANDARD) {
				 System.out.println("Invalid size, please enter 4 or 9.");
				 System.out.print("Enter size: ");
		     }
			 
		} while(N != Board.SIZE_EASY && N != Board.SIZE_STANDARD);
				
		return N;
	}
	
	/**
     * Prompts the user to choose the difficulty level.
     * @param scan Scanner object to read user input
     * @return chosen difficulty level
     */
	public int getDifficultyLevel(Scanner scan) {
				
		System.out.println("Give me the difficulty level: ");
		System.out.println("1. Easy");
		System.out.println("2. Medium");
		System.out.println("3. Hard");
	    System.out.print("Enter choice: ");
		
		int difficultyLevel;
		
		do {
			
			 difficultyLevel = scan.nextInt();
			 
			 if(difficultyLevel != Board.DIFFICULTY_EASY && difficultyLevel != Board.DIFFICULTY_MEDIUM && difficultyLevel != Board.DIFFICULTY_HARD) {
			    System.out.println("Invalid choice, please select 1, 2, or 3.");
			    System.out.print("Enter choice: ");
			 }
			 
		} while(difficultyLevel != Board.DIFFICULTY_EASY && difficultyLevel != Board.DIFFICULTY_MEDIUM && difficultyLevel != Board.DIFFICULTY_HARD);
		
				
		return difficultyLevel;
	}
	
	/**
     * Prints the current state of the Sudoku board in a readable format.
     * Fixed cells are displayed in parentheses.
     */
	public void print() {
		
		for(int i = 0 ; i < game.getBoard().getSize() ; i++) {
			
			if(i % Math.sqrt(game.getBoard().getSize()) == 0) {
				
				if(game.getBoard().getSize() == Board.SIZE_EASY) {
					System.out.println("+------+------+");
				}
				
				else if(game.getBoard().getSize() == Board.SIZE_STANDARD) {
					System.out.println("+---------+---------+---------+");
				}
			
			}
			
			for(int j = 0 ; j < game.getBoard().getSize() ; j++) {
								
				if(j % Math.sqrt(game.getBoard().getSize()) == 0) {
					
					if(game.getBoard().getValue(i, j) != 0) {
					
						if(game.getBoard().isCellFixed(i, j)) {
							System.out.print("|(" + game.getBoard().getValue(i, j) + ")");
						}
						
						else {
							System.out.print("| " + game.getBoard().getValue(i, j) + " ");
						}
					
					}
					
					else {
						System.out.print("|   ");
					}
					
				}
				
				else {
					
					if(game.getBoard().getValue(i, j) != 0) {

						if(game.getBoard().isCellFixed(i, j)) {
							System.out.print("(" + game.getBoard().getValue(i, j) + ")");
						}
						
						else {						
							System.out.print(" " + game.getBoard().getValue(i, j) + " ");
						}
					
					}
					
					else {
						System.out.print("   ");
					}
					
				}
				
			}
			
			System.out.println("|");
			
		}
		
		if(game.getBoard().getSize() == Board.SIZE_EASY) {
			System.out.println("+------+------+");
		}
		
		else if(game.getBoard().getSize() == Board.SIZE_STANDARD) {
			System.out.println("+---------+---------+---------+");
		}
		
	}

}
