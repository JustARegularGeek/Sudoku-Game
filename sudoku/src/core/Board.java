package core;

import java.util.Random;

/**
 * Represents a Sudoku board of variable size (4x4 or 9x9).
 * Provides methods to generate a valid Sudoku solution,
 * hide cells based on difficulty, and track fixed cells.
 * 
 * Features:
 * - Recursive backtracking to fill the board
 * - Validation of rows, columns, and boxes
 * - Shuffling values to randomize boards
 * - Easy, Medium, and Hard difficulty levels
 * 
 * Usage example:
 * Board board = new Board(Board.SIZE_STANDARD, Board.DIFFICULTY_MEDIUM);
 * board.initializeBoard();
 */

public class Board {
	
	// Constants for board sizes
	public static final int SIZE_EASY = 4;		// 4x4 Sudoku
	public static final int SIZE_STANDARD = 9;	// 9x9 Sudoku

    // Constants for difficulty levels
	public static final int DIFFICULTY_EASY = 1;
	public static final int DIFFICULTY_MEDIUM = 2;
	public static final int DIFFICULTY_HARD = 3;
	
	private int size;				// Size of the board (e.g., 4 or 9)
	private int tableau[][];			// Sudoku board values
	private int difficultyLevel;		// Chosen difficulty level
	private boolean fixed[][];		// Marks which cells are fixed (given clues)
	
	
    /**
     * Default constructor: 9x9 board with easy difficulty.
     */
	public Board() {
		this(9, 1);
	}
	
    /**
     * Constructor with custom board size and difficulty.
     * @param N size of the board (e.g., 4 or 9)
     * @param difficultyLevel difficulty level (DIFFICULTY_EASY, DIFFICULTY_MEDIUM, DIFFICULTY_HARD)
     */
	public Board(int size, int difficultyLevel) {
		this.size = size;
		this.tableau = new int[size][size];		 // Initialize empty board
		this.difficultyLevel = difficultyLevel;
		this.fixed = new boolean[size][size];	 // Initialize fixed-cell tracking
	}
	
	/**
     * Returns the size of the board.
     * @return board size
     */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Returns the value at a specific cell.
	 * @param row Row index (0-based)
	 * @param column Column index (0-based)
	 * @return Value at the specified cell
	 */
	public int getValue(int row, int column) {
		return this.tableau[row][column];
	}
	
    /**
     * Sets a value at a specific cell.
     * @param row row index (0-based)
     * @param column column index (0-based)
     * @param value value to set
     */
	public void setValue(int row, int column, int value) {
		this.tableau[row][column] = value;
	}
	
	/**
     * Alias for isCellFixed.
     * @param row row index
     * @param column column index
     * @return true if the cell is fixed
     */
	public boolean isCellFixed(int row, int column) {
		return this.fixed[row][column];
	}
	
    /**
     * Recursive method to fill the board with a valid Sudoku solution.
     * Uses backtracking algorithm.
     * @return true if the board was successfully filled
     */
	private boolean fillBoard() {
		
		for(int i = 0 ; i < this.size ; i++) {
			
			for(int j = 0 ; j < this.size ; j++) {
				
				if(this.tableau[i][j] == 0) {
					
					int array[] = new int[this.size];
					
					for(int value = 1 ; value <= this.size ; value++) {
						array[value-1] = value;
					}
					
					shuffle(array);
					
					for (int value : array) {
						
	                    if (isValidMove(i, j, value)) {
	                    	
	                        this.tableau[i][j] = value;

	                        if (fillBoard()) {
	                            return true;
	                        }

	                        this.tableau[i][j] = 0;
	                    }
	                    
	                }
					
					return false;
				}
				
			}
			
		}
		
		return true;
		
	}
	
	/**
     * Checks if a value is not already in the given row.
     * @param row row index
     * @param value value to check
     * @return true if value can be placed in the row
     */	
	public boolean checkRow(int row, int value) {
		
		for(int j = 0 ; j < this.size ; j++) {
			
			if(value == this.tableau[row][j]) {
				return false;
			}
			
		}
		
		return true;
		
	}
	
	/**
     * Checks if a value is not already in the given column.
     * @param column column index
     * @param value value to check
     * @return true if value can be placed in the column
     */
	public boolean checkColumn(int column, int value) {
		
		for(int i = 0 ; i < this.size ; i++) {
			
			if(value == this.tableau[i][column]) {
				return false;
			}
			
		}
		
		return true;
		
	}
	
	 /**
     * Checks if a value is not already in the sub-box containing the cell.
     * @param row row index
     * @param column column index
     * @param value value to check
     * @return true if value can be placed in the box
     */
	public boolean checkBox(int row, int column, int value) {
		
		int boxSize = (int)Math.sqrt(this.size);
		int startRow = row - (row % boxSize);
		int startColumn = column - (column % boxSize);
	
		for(int i = startRow ; i < startRow + Math.sqrt(this.size) ; i++) {
			
			for(int j = startColumn ; j < startColumn + Math.sqrt(this.size) ; j++) {
				
				if(value == this.tableau[i][j]) {
					return false;
				}
				
			}
			
		}
		
		return true;
		
	}
	
	/**
     * Checks if a value can be placed in a cell considering row, column, and box constraints.
     * @param row row index
     * @param column column index
     * @param value value to check
     * @return true if valid
     */
	public boolean isValidMove(int row, int column, int value) {
		return checkRow(row, value) && checkColumn(column, value) && checkBox(row, column, value);
	}
	
	/**
     * Shuffles an array of integers using Fisher–Yates algorithm.
     * @param array array to shuffle
     */
	private void shuffle(int array[]) {
		
		Random rand = new Random();
		
	    for (int i = array.length - 1; i > 0; i--) {
	    	
	        int j = rand.nextInt(i + 1);
	        int temp = array[i];
	        array[i] = array[j];
	        array[j] = temp;
	        
	    }
		
	}
	

    /**
     * Hides cells in the board based on difficulty level.
     * The higher the difficulty, the more cells are hidden.
     */
	private void hide() {
		
		int hide = 0;
		Random rand = new Random();
		
        // Determine number of cells to hide
		if(this.difficultyLevel == DIFFICULTY_EASY) {
			
			if(this.size == SIZE_EASY) {
				hide = 8;
			}
			
			else if(this.size == SIZE_STANDARD) {
				hide = 34;
			}
			
		}
		
		else if(this.difficultyLevel == DIFFICULTY_MEDIUM) {

			if(this.size == SIZE_EASY) {
				hide = 10;
			}
			
			else if(this.size == SIZE_STANDARD) {
				hide = 40;
			}
			
		}
		
		else if(this.difficultyLevel == DIFFICULTY_HARD) {
			
			if(this.size == SIZE_EASY) {
				hide = 12;
			}
			
			else if(this.size == SIZE_STANDARD) {
				hide = 45;
			}

		}
		
        // Randomly set chosen cells to 0
		for(int i = 0 ; i < hide ; i++) {
			
			int randomRow, randomColumn;
			
			 do {
				 
				 randomRow = rand.nextInt(this.size);  
				 randomColumn = rand.nextInt(this.size);
				 
			 } while(this.tableau[randomRow][randomColumn]==0);
			
			 this.tableau[randomRow][randomColumn]=0;
					 
		}
		
	}
	
    /**
     * Marks non-empty cells as fixed (clues) and empty cells as non-fixed.
     */
	private void markFixedCells() {
		
		for(int i = 0 ; i < size ; i++) {
			for(int j = 0 ; j < size ; j++) {
				
				if(this.tableau[i][j] != 0) {
					fixed[i][j]=true;
				}
				
				else {
					fixed[i][j]=false;
				}
				
			}
		}
		
	}
	
	/**
     * Initializes the board by generating a complete solution,
     * hiding cells according to difficulty, and marking fixed cells.
     */
	public void initializeBoard() {
		
		  this.fillBoard();       // Fill board with valid solution
	      this.hide();            // Hide some cells based on difficulty
	      this.markFixedCells();  // Mark remaining numbers as fixed
	}

}