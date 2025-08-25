package terminal;

import java.util.Scanner;
import core.Board;

/**
 * Handles user input for the terminal-based Sudoku game.
 * Stores the current move (row, column, value) and provides
 * methods to validate moves and detect exit commands.
 */
public class UserChoice {
	
	// Move values
	private int value;
	private int row;
	private int column;
	
	/**
     * Default constructor initializes move values to zero.
     */
	public UserChoice() {
		this.value = 0;
		this.row = 0;
		this.column = 0;
	}
	
	// Setters
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	// Getters
	public int getValue() {
		return this.value;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	 /**
     * Checks whether the move is within the board's bounds.
     * @param size size of the board
     * @return true if row, column, and value are within range
     */
	public boolean isMoveWithinBounds(int size) {
				
		if(this.row < 0 || this.row >= size || this.column < 0 || this.column >= size || this.value < 0 || this.value > size) {
			System.out.println("Error: i,j or val are outside the allowed range [1.." + size + "]!");
			return false;
		}
		
		return true;
	}
	
	/**
     * Detects if the user entered the exit command (0,0=0).
     * Prompts for confirmation before exiting.
     * @param row entered row
     * @param column entered column
     * @param value entered value
     * @param scan Scanner to read user confirmation
     * @return true if the user confirms exit
     */
	public boolean isExitCommand(int row, int column, int value, Scanner scan) {
					
		if(value == 0 && row == 0 && column == 0) {
			
			System.out.println("Are you sure you want to exit? (enter 'Yes' or 'No')");
			String answer;
			
			do {
				
        	 		answer = scan.nextLine().trim();
							
				if(answer.equalsIgnoreCase("yes")) {
		        		return true;
		        }
		        
		        else if(answer.equalsIgnoreCase("no")){
		            System.out.println("Exit canceled, continue playing.");
		        }
				
		        else {
		        	 	System.out.println("Invalid input. Please type 'Yes' or 'No'.");
		        	}
			 
			} while(!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no"));
	        
		}
		
		return false;
		
	}
	
	/**
     * Reads the user's move from terminal input.
     * Expected format: row,column=value
     * Special cases:
     *  - row,column=0 → clears the cell
     *  - 0,0=0 → exit command
     * @param scan Scanner for input
     * @param board Sudoku board for validation
     * @return true if the user wants to exit
     */
	public boolean getUserChoice(Scanner scan, Board board) {
				
		int row, column, value;
		
		System.out.println("Enter your command in the following format:");
		System.out.println("+ row,column=value : enter 'value' at position (row,column), where row and column start from 1");
		System.out.println("+ row,column=0   : clear the cell at position (row,column)");
		System.out.println("+ 0,0=0   : exit the game"); 
		
		String input;
	    String[] parts;

	    do {
            
            do{
            		System.out.print("Enter input (row,column=value): ");
            		input = scan.nextLine();
            } while(input.length() != 5);
            
            parts = input.split("[,=]");
        } while (parts.length != 3);

        row = Integer.parseInt(parts[0]);
        column = Integer.parseInt(parts[1]);
        value = Integer.parseInt(parts[2]);
		
        // Handle exit command
		if(isExitCommand(row, column, value, scan)) {
			System.out.println("Exit!");
		    return true; 
		}
		
		else if(row > 0 && column > 0) {
		
			this.row = row - 1;
			this.column = column - 1;
			this.value = value;
	
			// Handle clearing a cell
			if(value == 0) {
				
				if (board.isCellFixed(this.row, this.column)) {
					System.out.println("Error: cell is fixed and it cannot be modified.");
				    return false;
				}
				
				if(board.getValue(this.row, this.column) == 0) {
					System.out.println("Error: cell is already empty.");
				    return false;
				}
				
				System.out.println("Clearing the cell");
			}
			
		}
		
		return false;
		
	}
	
}