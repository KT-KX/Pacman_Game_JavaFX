# Pac-Man_CST210_Java_Assignment
Pac-Man Game

Overview
Pac-Man is a classic arcade game where the player navigates Pac-Man through a maze, eating pellets and avoiding ghosts. This version of Pac-Man is implemented using JavaFX, providing a modern graphical interface while retaining the timeless gameplay.

Features

Classic Gameplay: Navigate Pac-Man through the maze, eating all the pellets while avoiding the ghosts.
Levels: Progress through multiple levels by clearing all the pellets.
Modern Interface: Built with JavaFX for a smooth and visually appealing experience.
Intuitive Controls: Use the arrow keys to control Pac-Man.

Installation
Prerequisites: Ensure you have Java JDK 22 and JavaFX SDK installed on your machine.

Set Up JavaFX: Make sure to configure your IDE to include JavaFX libraries. Refer to JavaFX Setup for detailed instructions.

Usage
Running the Game:

Open the project in your IDE.
Run the Main.java file located in the application package.

Game Controls:

Use the arrow keys to move Pac-Man:
Up Arrow: Move up
Down Arrow: Move down
Left Arrow: Move left
Right Arrow: Move right

Project Structure

Pac-Man-Game/
├── src/
│   ├── application/
│   │   ├── Main.java
│   │   ├── MainMenuController.java
│   │   ├── RulesPageController.java
│   │   ├── Model.java
│   ├── fxml/
│   │   ├── MainMenu.fxml
│   │   ├── GameRules.fxml
│   │   ├── PacmanGame.fxml
│   ├── css/
│   │   ├── application.css
│   │   ├── rulescss.css
├── resources/
│   ├── images/
│   │   ├── background.jpg
│   │   ├── up.gif
│   │   ├── down.gif
│   │   ├── left.gif
│   │   ├── right.gif
│   │   ├── heart.png
│   │   ├── ghost.png
├── README.md

FXML Files
MainMenu.fxml: Defines the layout of the main menu.
GameRules.fxml: Defines the layout of the game rules page.
PacmanGame.fxml: Defines the layout of the game screen.

Controllers
MainMenuController.java: Handles the logic for the main menu.
RulesPageController.java: Handles the logic for the game rules page.
GameController.java: Handles the logic for the game screen.

Styles
application.css: Styles for the main application.
rulescss.css: Styles for the game rules page.
Credits

Developed by: Eng Kuan Tian AIT2209937, Choo Qie Sheng AIT2209935, Sow Wei Thao AIT2209950

Special Thanks to: 
License
This project is licensed under the AIT License. See the LICENSE file for more details.
