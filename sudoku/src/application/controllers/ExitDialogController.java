package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller for the Exit confirmation dialog in the JavaFX Sudoku application.
 * Provides actions for "Yes" (exit the application) and "No" (cancel exit) buttons.
 */
public class ExitDialogController {

    @FXML
    private Button yesButton;	// Button for confirming exit

    @FXML
    private Button noButton;		// Button for canceling exit

    /**
     * Handles the "Yes" button click.
     * Closes both the main application window and the dialog window.
     */
    @FXML
    private void handleYes() {
        Stage dialogStage = (Stage) yesButton.getScene().getWindow();
        Stage mainStage = (Stage) dialogStage.getOwner();
        mainStage.close();   // close the main application window
        dialogStage.close(); // close the dialog
    }

    /**
     * Handles the "No" button click.
     * Closes only the dialog window, leaving the application running.
     */
    @FXML
    private void handleNo() {
        Stage dialogStage = (Stage) noButton.getScene().getWindow();
        dialogStage.close();
    }
}