package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the main menu FXML layouts
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            
            // Create a scene with the main menu layout, setting the initial dimensions to 450x800
            Scene scene = new Scene(root,450,800);
            primaryStage.setScene(scene);
            
            // Get the controller for the main menu and set the primary stage in it
            MainMenuController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setStage(primaryStage);
            
            // Set the scene, title, and other properties of the primary stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("Main Menu");
            primaryStage.setResizable(false);
            primaryStage.show();
            
            // Load the game rules FXML layout
            FXMLLoader rulesLoader = new FXMLLoader(getClass().getResource("gameRules.fxml"));
            AnchorPane gameRulesRoot = rulesLoader.load();

            Scene gameRulesScene = new Scene(gameRulesRoot, 600, 800);
         // The gameRulesScene can be used later for switching to the game rules screen
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}