package application.controllers;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader; 

/**
 * Main JavaFX application class for the Sudoku game.
 * Handles launching the application, initializing the first scene,
 * setting the application icon, and managing exit behavior.
 */
public class MainController extends Application {
	
	/**
     * Entry point for JavaFX application.
     * Initializes and shows the main stage with the size selection scene.
     * @param stage the primary stage provided by JavaFX
     */
	@Override
	public void start(Stage stage) {
		
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/application/fxml/SizeSelectionScene.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/css/application.css").toExternalForm());
			
			stage.setScene(scene);
			stage.setTitle("Sudoku");
			
			Image icon = new Image("sudoku.png");
			stage.getIcons().add(icon);
			
			stage.setOnCloseRequest(event -> {
			    event.consume(); // stop default close
			    
			    try {
			        handleExit(stage);
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			    
			});
			
			stage.setScene(scene);
			stage.setResizable(false);   // disable resizing
			stage.setFullScreen(false);  // ensure not fullscreen
			stage.centerOnScreen();      // center the window
			stage.show();

		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
     * Shows a simple confirmation alert to exit the application.
     * This method is not currently used but can be kept for simple exits.
     * @param stage the stage to close if confirmed
     */
	public void exit(Stage stage) {
		
		stage.setFullScreen(false);
	    stage.setResizable(false);
	    stage.centerOnScreen();
	    
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("You are about to exit!");
		alert.setContentText("Are you sure you want to exit?");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			stage.close();
		}
		
	}
		
	/**
     * Displays a modal exit dialog for the user to confirm exiting the application.
     * @param ownerStage the main application stage to tie the dialog to
     * @throws IOException if the FXML file cannot be loaded
     */
	private void handleExit(Stage ownerStage) throws IOException {
		
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/ExitScene.fxml"));
	    Parent root = loader.load();

	    Stage dialogStage = new Stage();
	    dialogStage.setTitle("Exit");
	    dialogStage.setScene(new Scene(root));
	    dialogStage.initModality(Modality.APPLICATION_MODAL);
	    dialogStage.initOwner(ownerStage); // tie dialog to main window

	    dialogStage.setResizable(false);
	    dialogStage.setFullScreen(false);
	    
	    dialogStage.setOnShown(e -> dialogStage.centerOnScreen());
	    
	    dialogStage.showAndWait();
	}

	/**
	 * Launches the JavaFX UI for the Sudoku game.
	 * This method can be called from other classes (e.g., Sudoku)
	 * to start the graphical interface without a main() method.
	 */
	public static void launchUI() {
	    launch(); // calls JavaFX Application.launch internally
	}
}