package application.controllers;

import java.io.IOException;
import core.Sudoku;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controller class for the Sudoku UI game.
 * Handles initialization of the board, user interaction with cells,
 * number button input, cell highlighting, and game completion.
 */
public class GameController {

	// UI elements from FXML
    @FXML private GridPane gridPane; 
    @FXML private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    @FXML private Button btnClear;

    // Internal representation of the grid buttons
    private Button[][] cells;
    private Button selectedCell;		// currently selected cell
    private Sudoku game;				// current Sudoku game instance

    /**
     * Initializes the game UI and board.
     * Called automatically by JavaFX after FXML loading.
     */
    public void initialize() {
    	
        // Get Sudoku game instance from settings
    	 	game = SceneContoller.GameSettings.sudoku;

        // Initialize the board with values and hidden cells
        game.getBoard().initializeBoard();     
        cells = new Button[game.getBoard().getSize()][game.getBoard().getSize()];

        // Loop through all nodes in the GridPane and assign buttons
        for (Node node : gridPane.getChildren()) {
        	
            if (node instanceof Button) {
                // Get row/col from grid constraints
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer colIndex = GridPane.getColumnIndex(node);

                // Null = default is 0
                int row = (rowIndex == null) ? 0 : rowIndex;
                int col = (colIndex == null) ? 0 : colIndex;

                Button btn = (Button) node;
                cells[row][col] = btn;

                final int r = row, c = col;
                
                btn.setOnAction(e -> {
                    selectedCell = btn;

                    // find the row/col of this button
                    Integer rIndex = GridPane.getRowIndex(btn);
                    Integer cIndex = GridPane.getColumnIndex(btn);
                    int selRow = (rIndex == null) ? 0 : rIndex;
                    int selCol = (cIndex == null) ? 0 : cIndex;

                    highlightRowAndColumn(selRow, selCol);
                });

                // Pre-fill values from Board
                int val = game.getBoard().getValue(r, c);
                if (val != 0) {
                    btn.setText(String.valueOf(val));
                    btn.setUserData("fixed");
                    btn.setStyle("-fx-background-color: white; -fx-font-size: 16px; -fx-font-weight: bold;");
                } 
                
                else {
                    btn.setText(" ");
                    btn.setUserData("editable");
                    btn.setStyle("-fx-background-color: #F0FCF8; -fx-font-size: 16px;");                    
                }

                
            }
            
        }

        setupNumberButtons();
    }

    /**
     * Sets up the number buttons and clear button.
     * Shows only the relevant buttons for the board size.
     */
    private void setupNumberButtons() {
    	
        // Collect number buttons into an array
    	    Button[] allButtons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
    	    btnClear.setVisible(true);
    	    btnClear.setOnAction(e -> handleClearCell());

    	    for (int i = 0 ; i < game.getBoard().getSize() ; i++) {
    	        int value = i + 1;

    	        if (allButtons[i] != null) {  // safeguard
    	            allButtons[i].setVisible(true);
    	            allButtons[i].setOnAction(e -> handleNumberSelection(value));
    	        }
    	    }

        // Hide extra buttons if board size < 9
    	    for (int i = game.getBoard().getSize() ; i < allButtons.length ; i++) {
    	        if (allButtons[i] != null) {
    	            allButtons[i].setVisible(false);
    	        }
    	    }
    	    
    	}

    /**
     * Handles inserting a number into the selected cell.
     * Validates the move, updates the board and cell UI,
     * and checks for game completion.
     * @param value number to insert
     */
    private void handleNumberSelection(int value) {
    	
        if (selectedCell == null) {
            return; // nothing selected
        }

        if ("fixed".equals(selectedCell.getUserData())) {
            return; // cannot change fixed cell
        }

        Integer rowIndex = GridPane.getRowIndex(selectedCell);
        Integer colIndex = GridPane.getColumnIndex(selectedCell);
        int row = (rowIndex == null) ? 0 : rowIndex;
        int col = (colIndex == null) ? 0 : colIndex;

        if (game.getBoard().isValidMove(row, col, value)) {
        	
            game.getBoard().setValue(row, col, value);
            selectedCell.setText(String.valueOf(value));
            selectedCell.setStyle("-fx-background-color: #F0FCF8; -fx-font-size: 16px;");		// clear previous red highlight

            if (game.finished()) {
            	
                try {
                	
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/EndingScene.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) gridPane.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setOnCloseRequest(null); // remove the confirmation handler
                    stage.centerOnScreen();
                    stage.show();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        } 
        
        else {
            // Highlight invalid move
            selectedCell.setStyle("-fx-background-color: red;");
        }
        
    }

    /**
     * Clears the value of the selected cell.
     * Does nothing if the cell is fixed or nothing is selected.
     */
    private void handleClearCell() {
    	
        if (selectedCell == null) {
            return; // nothing selected
        }

        if ("fixed".equals(selectedCell.getUserData())) {
            return; // cannot clear fixed cell
        }

        Integer rowIndex = GridPane.getRowIndex(selectedCell);
        Integer colIndex = GridPane.getColumnIndex(selectedCell);
        int row = (rowIndex == null) ? 0 : rowIndex;
        int col = (colIndex == null) ? 0 : colIndex;

        game.getBoard().setValue(row, col, 0); // reset value in board
        selectedCell.setText(" ");             // clear button label
        selectedCell.setStyle("");             // reset styling
        
    }

    /**
     * Highlights the selected row, column, and cell.
     * Resets all other cells to default styling.
     * @param row row index of selected cell
     * @param col column index of selected cell
     */
    private void highlightRowAndColumn(int row, int col) {
    	
        // Reset all styles first
        for (int r = 0 ; r < game.getBoard().getSize() ; r++) {
            for (int c = 0 ; c < game.getBoard().getSize() ; c++) {
                if (!cells[r][c].isDisabled()) {
	                	 if ("fixed".equals(cells[r][c].getUserData())) {
	                     // Fixed = white
	                     cells[r][c].setStyle("-fx-background-color: white; -fx-font-size: 16px; -fx-font-weight: bold;");
	                 } 
	                	 
	                	 else {
	                     // Editable = light green
	                     cells[r][c].setStyle("-fx-background-color: #F0FCF8; -fx-font-size: 16px;");
	                 }
                }
            }  
        }

        // Highlight row
        for (int c = 0 ; c < game.getBoard().getSize() ; c++) {
        		if ("fixed".equals(cells[row][c].getUserData())) {
                cells[row][c].setStyle("-fx-background-color: #C8E6DB; -fx-font-size: 16px; -fx-font-weight: bold;");
            } 
           	 
        		else if (!cells[row][c].isDisabled()) {
                cells[row][c].setStyle("-fx-background-color: #C8E6DB; -fx-font-size: 16px;");
            }
        }

        // Highlight column
        for (int r = 0 ; r < game.getBoard().getSize() ; r++) {
        		if ("fixed".equals(cells[r][col].getUserData())) {
                cells[r][col].setStyle("-fx-background-color: #C8E6DB; -fx-font-size: 16px; -fx-font-weight: bold;");
            } 
        		
        		else if (!cells[r][col].isDisabled()) {
                cells[r][col].setStyle("-fx-background-color: #C8E6DB; -fx-font-size: 16px;");
            }
        }

        // Highlight the selected cell stronger
        if ("fixed".equals(cells[row][col].getUserData())) {
            cells[row][col].setStyle("-fx-background-color: #A7D1C2; -fx-font-size: 16px; -fx-font-weight: bold;");
        } 
        
        else {
            cells[row][col].setStyle("-fx-background-color: #A7D1C2; -fx-font-size: 16px;");
        }

    }
  
}