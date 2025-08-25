package application.controllers;

import core.Sudoku;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Main controller for the JavaFX Sudoku application.
 * Handles scene switching, game settings, and navigation
 * between size selection, difficulty selection and the game scene.
 */
public class SceneContoller {
	
    /**
     * Nested class to store game settings that persist across scenes.
     */
	public class GameSettings {
	    public static int size;			// Sudoku board size (4 or 9)
	    public static int difficulty;	// Difficulty level (1=Easy, 2=Medium, 3=Hard)
	    public static Sudoku sudoku;		// Current Sudoku game instance
	}
	
	/** Navigate to size selection scene */
	public void goToSizeSelection(ActionEvent event) throws IOException {
	    switchSceneFixed(event, "/application/fxml/SizeSelectionScene.fxml");
	}
	
	/** Select 4x4 Sudoku size and go to difficulty selection */
	@FXML
	private void selectSizeFour(ActionEvent event) throws IOException {
		GameSettings.size = 4;
	    goToDifficultySelection(event);
	}

	/** Select 9x9 Sudoku size and go to difficulty selection */
	@FXML
	private void selectSizeNine(ActionEvent event) throws IOException {
		GameSettings.size = 9;
	    goToDifficultySelection(event);
	}
	
	/** Navigate to difficulty selection scene */
	public void goToDifficultySelection(ActionEvent event) throws IOException {
	    switchSceneFixed(event, "/application/fxml/DifficultySelectionScene.fxml");
	}
	
	/** Select difficulty level 1 (Easy) and go to game */
	@FXML	
	private void selectDifficultyLevelOne(ActionEvent event) throws IOException {
		GameSettings.difficulty = 1;
	    goToGame(event);
	}
	
    /** Select difficulty level 2 (Medium) and go to game */
	@FXML
	private void selectDifficultyLevelTwo(ActionEvent event) throws IOException {
		GameSettings.difficulty = 2;
	    goToGame(event);
	}
	
	 /** Select difficulty level 3 (Hard) and go to game */
	@FXML
	private void selectDifficultyLevelThree(ActionEvent event) throws IOException {
		GameSettings.difficulty = 3;
	    goToGame(event);
	}

    /**
     * Initializes the Sudoku game with selected size and difficulty,
     * then navigates to the appropriate game scene.
     */
	public void goToGame(ActionEvent event) throws IOException {
		
		GameSettings.sudoku = new Sudoku(GameSettings.size, GameSettings.difficulty);
		
		if(GameSettings.size == 4) {
		    switchSceneFixed(event, "/application/fxml/GameScene4.fxml");
		}
		
		else if(GameSettings.size == 9) {
		    switchSceneFixed(event, "/application/fxml/GameScene9.fxml");
		}
		
	}
	
	/**
	 * Switches to a new FXML scene while fixing the window size and position.
	 *
	 * @param event The ActionEvent (used to get the current stage)
	 * @param fxmlPath Path to the FXML file
	 * @throws IOException If the FXML cannot be loaded
	 *
	 * Steps:
	 * 1. Load FXML layout.
	 * 2. Get stage from event source.
	 * 3. Disable resizing and full-screen.
	 * 4. Center the stage on screen.
	 * 5. Set new scene with CSS and show it.
	 *
	 * Note: This only works after the first scene is shown, not for initial startup.
	 */
	private void switchSceneFixed(ActionEvent event, String fxmlPath) throws IOException {
		
		// Load FXML
	    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
	    Parent root = loader.load();

	    // Get stage from the event
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.setResizable(false);   // disable resizing
	    stage.setFullScreen(false);  // ensure it's not fullscreen
	    stage.centerOnScreen();      // center window

	    Scene scene = new Scene(root);
	    scene.getStylesheets().add(getClass().getResource("/application/css/application.css").toExternalForm());
	    stage.setScene(scene);
	    stage.centerOnScreen();
	    stage.show();
	}

}