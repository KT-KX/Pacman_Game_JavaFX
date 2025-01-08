package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenuController {
	// This field tracks the selected level, defaulting to 1, pass it to model.java
    public int tolevel = 1;
    @FXML
    private ImageView backgroundImageView;

    @FXML
    private StackPane root;
    
    @FXML
    private ComboBox<String> levelComboBox;
    
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @FXML
    public void initialize() {
        // Populate the ComboBox with level options
        levelComboBox.getItems().addAll("Level 1", "Level 2", "Level 3");
        levelComboBox.setValue("Level 1"); // Default value
        levelComboBox.setPromptText("Select Level");
        
        // Bind the background image size to the size of the root StackPane
        backgroundImageView.fitWidthProperty().bind(root.widthProperty());
        backgroundImageView.fitHeightProperty().bind(root.heightProperty());
    }
    
    private Stage primaryStage;
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    @FXML
    private void startGame(ActionEvent event) {
    	 // Get the selected level from the ComboBox
        String selectedLevel = levelComboBox.getValue();
        int level = 1; // Default to level 1

     // Determine the level based on the selected option
        switch (selectedLevel) {
            case "Level 1":
                level = 1;
                tolevel = level;
                break;
            case "Level 2":
                level = 2;
                tolevel = level;
                break;
            case "Level 3":
                level = 3;
                tolevel = level;
                break;
        }
        tolevel = level; // Update the tolevel field

        // Start the game with the selected level
        Stage stage = (Stage) levelComboBox.getScene().getWindow();
        startGame(stage, level);
    }
    @FXML
    private void exitApplication() {
    	// Exit the application
        Platform.exit();
    }

    @FXML
    private void checkRules() {
        try {
        	// Load the game rules FXML layout
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameRules.fxml"));
            Parent rulesRoot = fxmlLoader.load();
            RulesPageController rulesController = fxmlLoader.getController();

         // Ensure stage is set before setting scene
            if (stage == null) {
                System.err.println("Stage is null in MainMenuController.checkRules()");
                return;
            }


            rulesController.setStage(stage);
            rulesController.setMainMenuScene(stage.getScene());
            Scene rulesScene = new Scene(rulesRoot);
            stage.setScene(rulesScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startGame(Stage stage, int level) {
        try {
        	 // Load the Pacman game FXML layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PacmanGame.fxml"));
            AnchorPane root = loader.load();

            // Pass the selected level to the game controller
            Model controller = loader.getController();
            controller.setLevel(level); // Pass the selected level to the game controller

         // Create a new scene with the Pacman game layout and set it to the stage
            Scene scene = new Scene(root,450,800);
            scene.setOnKeyPressed(controller);

            stage.setScene(scene);
            stage.setTitle("Pacman Game - Level " + level);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}