

# Pacman Game Application - JavaFX

This project is a **JavaFX-based Pacman Game Application** that includes a main menu, level selection, game rules page, and the Pacman gameplay itself. The application demonstrates modular programming and clean scene management using JavaFX FXML and controllers.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [Code Overview](#code-overview)
  - [Main Class](#main-class)
  - [Controllers](#controllers)
  - [FXML Files](#fxml-files)
- [Future Enhancements](#future-enhancements)
- [Screenshots](#screenshots)
- [License](#license)

---

## Features
- **Main Menu**: Allows users to start the game, view game rules, or exit the application.
- **Level Selection**: Provides options to choose between different levels.
- **Game Rules Page**: Displays rules for the game and provides a navigation button to return to the main menu.
- **Pacman Gameplay**: A functional gameplay interface rendered on a canvas.

---

## Technologies Used
- **JavaFX**: For UI design and scene management.
- **FXML**: To define layouts and bind them to respective controllers.
- **Java**: For implementing logic and application structure.

---

## Project Structure
```
|-- src/
    |-- application/
        |-- Main.java
        |-- MainMenuController.java
        |-- RulesPageController.java
        |-- Model.java
    |-- fxml/
        |-- MainMenu.fxml
        |-- GameRules.fxml
        |-- PacmanGame.fxml
    |-- css/
        |-- application.css
        |-- rulescss.css
    |-- images/
        |-- background.jpg
```

---

## Getting Started

### Prerequisites
- Java JDK 8 or later
- JavaFX SDK (if not included with your JDK)
- An IDE such as IntelliJ IDEA or Eclipse configured with JavaFX support

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/pacman-javafx.git
   ```
2. Open the project in your preferred IDE.
3. Configure JavaFX library paths in your IDE if required.

### Running the Application
1. Compile and run the `Main.java` file.
2. The main menu will appear, allowing you to start the game, view rules, or exit.

---

## Code Overview

### Main Class
The entry point of the application, responsible for:
- Loading the main menu FXML.
- Setting the primary stage and initializing controllers.

### Controllers
- **`MainMenuController`**: Manages the main menu, handles level selection, and transitions to gameplay or game rules.
- **`RulesPageController`**: Manages the rules page and handles navigation back to the main menu.

### FXML Files
- **`MainMenu.fxml`**: Defines the layout for the main menu, including buttons and level selection.
- **`GameRules.fxml`**: Details the rules layout with a back button.
- **`PacmanGame.fxml`**: Specifies the gameplay layout, including canvas elements for rendering.

---

## Future Enhancements
- Add a scoring system to track progress.
- Implement additional levels with increased difficulty.
- Enhance the user interface with animations and sound effects.
- Include a high-score leaderboard.

---

## Screenshots
![Main Menu](https://via.placeholder.com/600x400?text=Main+Menu+Screenshot)
*Main Menu with level selection and navigation options.*

![Game Rules](https://via.placeholder.com/600x400?text=Game+Rules+Screenshot)
*Game Rules page with instructions.*

![Pacman Gameplay](https://via.placeholder.com/600x400?text=Pacman+Gameplay+Screenshot)
*Gameplay interface showing Pacman and the maze.*

---

## License
This project is licensed under the [MIT License](LICENSE).

---

## Acknowledgments
- JavaFX Documentation: [https://openjfx.io/](https://openjfx.io/)
- Inspiration from classic Pacman games.
```

### Notes:
- Replace placeholder URLs (e.g., `https://via.placeholder.com/...`) with actual image URLs or local paths.
- Add your GitHub repository URL in the "Installation" section.
- Replace `your-username` in the clone command with your GitHub username.
- Include a `LICENSE` file if licensing applies.
