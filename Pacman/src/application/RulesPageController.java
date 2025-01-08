package application;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RulesPageController {

    // Reference to the primary stage of the application
    private Stage stage;

    // Reference to the main menu scene to switch back to
    private Scene mainMenuScene;
    
 // Method to set the primary stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

 // Method to set the mainMenuScene
    public void setMainMenuScene(Scene mainMenuScene) {
        this.mainMenuScene = mainMenuScene;
    }

    
    // Method called when the "Back" button is pressed
    @FXML
    private void backToMenu() {
         // Check if the stage and main menu scene are not null
        if (stage != null && mainMenuScene != null) {
            // Switch back to the main menu scene
            stage.setScene(mainMenuScene);
        }
    }
}