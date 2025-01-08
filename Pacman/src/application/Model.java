package application;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;

public class Model implements EventHandler<KeyEvent> {

    @FXML
    private Canvas gameCanvas; //Canvas to draw the game

    private GraphicsContext gc; //Graphic context for drawing on the canvas
    private boolean inGame = false;//Game state flag
    private boolean dying = false;//Flag for dying

    private final int BLOCK_SIZE = 24;//size of each block in grid
    private final int N_BLOCKS = 15;//number of blocks in one dimension of the grid
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;//total screen size
    private final int MAX_GHOSTS = 12;//maximum number of ghost
    private final int PACMAN_SPEED = 3;//pacman speed

    private int N_GHOSTS = 6;//number of ghosts
    private int lives, score, temp;
    private int[] dx, dy;//direction arrays for ghosts
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;//ghosts position and movement 

    private Image heart, ghost = null;
    private Image up = null, down = null, left = null, right = null;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;//pacman position and movement 
    private int req_dx, req_dy;//requested direction for pacmen movement
    private int currentLevel =2;//current level
    

    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};//valid speed for ghosts
    private final int maxSpeed = 6;//maximum speed

    private int currentSpeed = 3;//current speed
    private short[] screenData;//screen data for current level
    private short[] levelData;//data for the current level
    
    private final short level1Data[] = {
        	19, 18, 22,  0, 19, 18, 18, 18, 18, 22,  0, 19, 18, 18, 22,
            17, 16, 28,  0, 25, 24, 16, 16, 16, 20,  0, 17, 16, 16, 20,
            17, 20,  0,  0,  0,  0, 17, 16, 16, 20,  0, 17, 16, 16, 20,
            17, 16, 18, 18, 18, 18, 16, 16, 16, 20,  0, 17, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 20,  0, 17, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 20,  0, 17, 16, 24, 28,
            25, 24, 24, 24, 24, 24, 16, 16, 16, 16, 18, 16, 20,  0,  0,
             0,  0,  0,  0,  0,  0, 17, 16, 16, 24, 16, 16, 16, 18, 22,
            19, 18, 18, 18, 18, 18, 16, 16, 20,  0, 17, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 20,  0, 17, 16, 16, 16, 20,
            17, 16, 24, 24, 24, 24, 16, 16, 20,  0, 25, 24, 24, 16, 20,
            17, 20,  0,  0,  0,  0, 17, 16, 20,  0,  0,  0,  0, 17, 20,
            17, 20,  0, 19, 22,  0, 17, 16, 16, 18, 18, 18, 18, 16, 20,
            17, 20,  0, 17, 16, 18, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            25, 28,  0, 25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
        };
    
    
    private final short level2Data[] = {
        	19, 22,  0, 19, 18, 18, 26, 26, 26, 26, 18, 22,  0, 19, 22,
            17, 20,  0, 17, 16, 20,  0,  0,  0,  0, 17, 20,  0, 17, 20,
            17, 20,  0, 25, 24, 16, 18, 18, 18, 18, 16, 20,  0, 17, 20,
            17, 20,  0,  0,  0, 17, 16, 16, 24, 16, 16, 20,  0, 17, 20,
            17, 16, 26, 30,  0, 25, 16, 20,  0, 17, 16, 16, 18, 16, 20,
            17, 20,  0,  0,  0,  0, 17, 20,  0, 17, 16, 24, 16, 24, 20,
            17, 16, 18, 18, 18, 18, 16, 20,  0, 17, 20,  0, 21,  0, 21,
            25, 24, 24, 16, 16, 16, 16, 16, 18, 16, 20,  0, 21,  0, 21,
             0,  0,  0, 17, 24, 24, 16, 16, 16, 16, 20,  0, 21,  0, 21,
            19, 22,  0, 21,  0,  0, 17, 16, 24, 16, 20,  0, 21,  0, 21,
            17, 20,  0, 29,  0, 19, 16, 20,  0, 17, 16, 18, 16, 18, 20,
            17, 20,  0,  0,  0, 17, 16, 20,  0, 17, 16, 24, 24, 24, 28,
            17, 16, 18, 26, 18, 16, 16, 20,  0, 17, 20,  0,  0,  0,  0,
            17, 16, 20,  0, 17, 16, 16, 16, 18, 16, 16, 18, 18, 18, 22,
            25, 24, 28,  0, 25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
        };
    
    
    private final short level3Data[] = {
    	    19, 22,  0, 19, 18, 18, 18, 18, 18, 18, 18, 26, 26, 18, 22,  
    	    17, 20,  0, 25, 24, 16, 16, 16, 16, 16, 20,  0,  0, 17, 20,  
    	    17, 20,  0,  0,  0, 25, 24, 16, 16, 24, 16, 22,  0, 25, 28,  
    	    17, 16, 18, 22,  0,  0,  0, 17, 20,  0, 17, 20,  0,  0,  0,  
    	    17, 16, 16, 24, 26, 26, 18, 16, 20,  0, 17, 16, 18, 18, 22, 
    	    17, 16, 20,  0,  0,  0, 25, 16, 28,  0, 17, 16, 24, 24, 20,  
    	    17, 16, 16, 18, 18, 22,  0, 29,  0, 19, 16, 20,  0,  0, 21, 
    	    25, 24, 24, 16, 16, 16, 30,  0, 27, 16, 16, 16, 18, 18, 20, 
    	     0,  0,  0, 17, 16, 28,  0, 23,  0, 25, 24, 24, 16, 16, 20, 
    	    19, 22,  0, 17, 20,  0, 19, 16, 22,  0,  0,  0, 17, 16, 20, 
    	    17, 20,  0, 17, 20,  0, 17, 16, 16, 18, 18, 18, 24, 16, 20, 
    	    17, 16, 18, 16, 20,  0, 17, 16, 16, 24, 24, 28,  0, 25, 28, 
    	    17, 16, 24, 16, 16, 18, 16, 16, 20,  0,  0,  0,  0,  0,  0, 
    	    17, 20,  0, 17, 16, 16, 16, 16, 20,  0, 19, 18, 18, 18, 22, 
    	    25, 28,  0, 25, 24, 24, 24, 24, 24, 26, 24, 24, 24, 24, 28  
    	};


    public void initialize() {
    	//initialize graphics context
        gc = gameCanvas.getGraphicsContext2D();
        //initialize screen data array
        screenData = new short[N_BLOCKS * N_BLOCKS];
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];
        
        //load images
        loadImages();
        MainMenuController newlevel = new MainMenuController();
        //set level from main menu controller
        setLevel(newlevel.tolevel);
        currentLevel = newlevel.tolevel;
        //initialize the game
        initGame();

        //animation timer to control the game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (inGame) {
                    playGame();
                } else {
                    showIntroScreen();
                }
                drawScore();
            }
        };
        timer.start();
    }
    
    //function to set level and copy maze
    public void setLevel(int level) {
    	currentLevel = level;
        switch (level) {
            case 1:
                levelData = level1Data;
                break;
            case 2:
                levelData = level2Data;
                break;
            case 3:
                levelData = level3Data;
                break;
            default:
                levelData = level1Data;
                break;
        }
        initLevel();
    }
    
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
        	//handle left arrow key press
            case LEFT -> {
                req_dx = -1;
                req_dy = 0;
            }
            //handle right arrow key press
            case RIGHT -> {
                req_dx = 1;
                req_dy = 0;
            }
            //handle up arrow key press
            case UP -> {
                req_dx = 0;
                req_dy = -1;
            }
            //handle down arrow key press
            case DOWN -> {
                req_dx = 0;
                req_dy = 1;
            }
            //handle space bar key press to start or restart
            case SPACE -> {
                if (!inGame) {
                    inGame = true;
                    initGame();
                }
            }
            //pause the game on escape key
            case ESCAPE -> inGame = false;
        }
    }
    
    //function to load need images
    private void loadImages() {
        try {
        	//load images for pacman and ghosts
            down = new Image(getClass().getResource("images/down.gif").toExternalForm());
            up = new Image(getClass().getResource("images/up.gif").toExternalForm());
            left = new Image(getClass().getResource("images/left.gif").toExternalForm());
            right = new Image(getClass().getResource("images/right.gif").toExternalForm());
            ghost = new Image(getClass().getResource("images/ghost.gif").toExternalForm());
            heart = new Image(getClass().getResource("images/heart.png").toExternalForm());
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

    private void playGame() {
    	//clear the canvas
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        //draw maze
        drawMaze();
        if (dying) {
            death();
        } else {
        	//move Pacman
            movePacman();
            //draw Pacman
            drawPacman();
            //move ghosts
            moveGhosts();
            //check the maze for completion
            checkMaze();
        }
    }
    //function to show the introduction screen
    private void showIntroScreen() {
    	//fill the screen background color as black
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        gc.setFill(Color.YELLOW);
        gc.setFont(new Font("Arial", 24));

        String fullText = "Press Space  to Start";
        gc.fillText(fullText, 50, 150);

        String wordToBorder ="Space";
        Text text = new Text(wordToBorder);
        text.setFont(gc.getFont());

        double wordWidth = text.getLayoutBounds().getWidth();
        double wordHeight = text.getLayoutBounds().getHeight();

        String precedingText = "Press ";
        Text precedingTextNode = new Text(precedingText);
        precedingTextNode.setFont(gc.getFont());
        double precedingTextWidth = precedingTextNode.getLayoutBounds().getWidth();

        double textX = 50; 
        double textY = 150; 
        double wordX = textX + precedingTextWidth; 


        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2); 
        gc.strokeRect(wordX - 5, textY - wordHeight, wordWidth + 10, wordHeight + 10);
    }



    //function to draw the score for visualization 
    private void drawScore() {
        gc.setFill(new Color(5 / 255.0, 181 / 255.0, 79 / 255.0, 1.0));
        gc.setFont(new Font("Arial", 20));
        String s = "Score: " + score;
        gc.fillText(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (int i = 0; i < lives; i++) {
            gc.drawImage(heart, i * 28 + 8, SCREEN_SIZE + 1);
        }
    }
    
    //function to check if the level is completed
    private void checkMaze() {
        int number_of_dots;
        boolean finished = true;
        
        int number_of_non_dots = countNonDots(levelData);
        //check the number of dots in the particular map
        number_of_dots = (N_BLOCKS*N_BLOCKS) - number_of_non_dots;
        if(temp == number_of_dots) {
        	score += 50;
        	if (currentLevel < 3) {
                currentLevel++;
                //go to next level
                setLevel(currentLevel);
                //reset the temporary value
                temp=0;
            } else {
            	inGame = false;
            	//show winning screen if all level are completed
                showWinningScreen();
                currentLevel=1;
            }
        }
    }
    
    //function to count the number of non-dots
    private int countNonDots(short[] levelData) {
        int count = 0;
        for (int i = 0; i < levelData.length; i++) {
            if (levelData[i] == 0) { 
                count++;
            }
        }
        //count the number of non-dot cells
        return count;
    }
        
    //function to show after losing the game
    private void death() {
    	//minus the lives by 1 
        lives--;
        if (lives == 0) {
            inGame = false;
            //show game over screen if no lives are left
            showGameOverScreen();
        } else {
        	//continue the level if lives are left
            continueLevel();
        }
    }	
    
    //function to move the ghosts
    private void moveGhosts() {
        int pos;
        int count;
        //loop of each ghosts
        for (int i = 0; i < N_GHOSTS; i++) {
        	// Check if the ghost is aligned to the grid
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
            	// Calculate the position index in the screen data array
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);
                
                // Reset the direction count
                count = 0;
                //Check possible directions and update the directions arrays
                //Check left direction
                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }
                // Check up direction
                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }
                // Check right direction
                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }
                // Check down direction
                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }
                //if no valid direction is found, reverse direction or stop
                if (count == 0) {
                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                    	// Reverse the direction
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }
                } else {
                	//randomly select a new direction from the possible directions
                    count = (int) (Math.random() * count);
                    if (count > 3) {
                        count = 3;
                    }
                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }
            }
            //update ghosts positions
            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            drawGhost(ghost_x[i] + 1, ghost_y[i] + 1);
            //check for collision with pacman
            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame) {
                dying = true;
            }
        }
    }
    
    //draw ghost at specific location
    private void drawGhost(int x, int y) {
        gc.drawImage(ghost, x, y);
    }
    
    
    //function to move pacman based on current and requested direction
    private void movePacman() {
        int pos;
        short ch;
        
        //check if pacman is aligned with the grid
        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
            ch = screenData[pos];
            //check if pacman has encountered a dot
            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score++;
                temp++;
            }
            //update direction if requested direction is not blocked
            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                }
            }
            //check if the current direction is blocked and stop movement if it is
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
            	//remain stationary
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }
        //update pacmen position
        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
        drawPacman();
    }
    
    //function to draw the pacman based on direction of movement
    private void drawPacman() {
        if (req_dx == -1) {
            gc.drawImage(left, pacman_x + 1, pacman_y + 1);
        } else if (req_dx == 1) {
            gc.drawImage(right, pacman_x + 1, pacman_y + 1);
        } else if (req_dy == -1) {
            gc.drawImage(up, pacman_x + 1, pacman_y + 1);
        } else {
            gc.drawImage(down, pacman_x + 1, pacman_y + 1);
        }
    }
    
    //function to draw the maze
    private void drawMaze() {
        int x, y;
        short i = 0;
        // Loop through each block position on the screen
        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {
            	
            	// Set the fill color to black and draw a black rectangle at the current block position
                gc.setFill(Color.BLACK);
                gc.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                
                // Check if the current block is empty based on level data
                if ((levelData[i] == 0)) {
                    gc.setFill(Color.rgb(0, 72, 251));
                    gc.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                }
                
                // Set the stroke color to the same blue and set the line width for drawing the maze walls
                gc.setStroke(Color.rgb(0, 72, 251));
                gc.setLineWidth(5);
                
                // Check if there is a wall on the left side of the current block and draw it if present
                if ((screenData[i] & 1) != 0) { 
                	//draw left line
                    gc.strokeLine(x, y, x, y + BLOCK_SIZE - 1);
                }
                
                // Check if there is a wall on the top side of the current block and draw it if present
                if ((screenData[i] & 2) != 0) { 
                	//draw top line
                    gc.strokeLine(x, y, x + BLOCK_SIZE - 1, y);
                }
                
                // Check if there is a wall on the right side of the current block and draw it if present
                if ((screenData[i] & 4) != 0) { 
                	//draw right line
                    gc.strokeLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }
                
                // Check if there is a wall on the bottom side of the current block and draw it if present
                if ((screenData[i] & 8) != 0) {
                	//draw bottom line
                    gc.strokeLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }
                
                // Check if there is a dot in the current block and draw it if present
                if ((screenData[i] & 16) != 0) { 
                	//draw a dot
                    gc.setFill(Color.WHITE);
                    gc.fillOval(x + 10, y + 10, 6, 6);
                }
                // Move to the next block in the level data
                i++;
            }
        }
    }
    
    //function to initialize the game state
    private void initGame() {
        lives = 3;
        score = 0;
        N_GHOSTS = 1;
        currentSpeed = 1;
        initLevel();
    }

    //function to initialize the level
    private void initLevel() {
    	//check if the screenData is null or not
        if (screenData == null) {
            screenData = new short[N_BLOCKS * N_BLOCKS]; 
        }

        System.out.println("Initializing level with screenData size: " + screenData.length);
        
        for (int i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
        	//copy level data to screen data
            screenData[i] = levelData[i];
        }
        System.out.println("Level data copied to screenData");
        
        //continue will additional level setup
        continueLevel();
    }
    
    //function for continue level
    private void continueLevel() {
        int dx = 1;
        int random;

        for (int i = 0; i < N_GHOSTS; i++) {
            ghost_y[i] = 4 * BLOCK_SIZE;
            ghost_x[i] = 4 * BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }
            //set ghost speed
            ghostSpeed[i] = validSpeeds[random];
        }

        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 11 * BLOCK_SIZE;
        pacmand_x = 0;
        pacmand_y = 0;
        req_dx = 0;
        req_dy = 0;	
        dying = false;
    }
    
    
    //pop out alert show game over and try again
    private void showGameOverScreen() {
    	 System.out.println("You lose");
         Platform.runLater(() -> {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Game Over");
             alert.setHeaderText(null);
             alert.setContentText("Try Again, You Lose");
             alert.showAndWait();
             inGame = false;

         });
    }

    //pop out alert to show winning and congratulations
    private void showWinningScreen() {
        System.out.println("You win");
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations! You won!");
            alert.showAndWait();
            inGame = false;

        });
    }
}
