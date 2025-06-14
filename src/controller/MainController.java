package controller;

// Imports
import java.awt.event.*;
import javax.swing.*;
import model.*;
import view.*;

// MainController handles the main flow of the game, sets up players, connects UI buttons to actions, and handles player type selection (human vs AI)
public class MainController implements ActionListener {

    // Fields
    public static PlayerController currentPlayer;
    public static PlayerController opponentPlayer;
    private GameController gameController;
    private ViewController viewController;
    public static AIController aiController;

    // Player objects
    Player player1;
    Player player2;
    
    // Constructor
    public MainController() {
    	// Initializes game, players, view, and sets up button listeners
        gameController = new GameController();
        player1 = new Player();
        player2 = new Player();
        
        // Set player1 to start the game
        player1.setTurn(true);
        player1.setStep(2);
        
        // Link players to the game controller
        gameController.setPlayer1(player1);
        gameController.setPlayer2(player2);
        
        // Create player controllers
        currentPlayer = new PlayerController(player1);
        opponentPlayer = new PlayerController(player2);
        
        // Initialize view	
        viewController = new ViewController();
        
        // Action Listeners
        HomePanel homePanel = viewController.getHomePanel();
        homePanel.getPlayButton().addActionListener(this);
        OptionPanel optionPanel = viewController.getOptionPanel();
        optionPanel.getFriendButton().addActionListener(this);
        optionPanel.getComputerButton().addActionListener(this);
        ViewController.endPanel.getExitButton().addActionListener(this);
        viewController.getGameFrame().getHelpMenuItem().addActionListener(this);
        viewController.getGameFrame().getQuitMenuItem().addActionListener(this);
    }

    // This method handles button clicks and menu actions
    @Override
    public void actionPerformed(ActionEvent event) {
    	// If "Play button" is clicked on the home panel
        if (event.getSource() == viewController.getHomePanel().getPlayButton()) {
            // Switch panel to option panel
        	viewController.switchToOption();
        }
        // If "Friend button" is clicked on the option panel
        else if (event.getSource() == viewController.getOptionPanel().getFriendButton()) {
            // Switch to the game panel and start the 2-player game
        	viewController.switchToGame();
        }
        // If "Computer button" is clicked on the option panel
        else if (event.getSource() == viewController.getOptionPanel().getComputerButton()) {
            // Switch to game panel and setup AI opponent
        	viewController.switchToGame();
            // Mark opponent as an AI
            opponentPlayer.getPlayer().setIsAI(true);
            // Initialize AI controller
            aiController = new AIController(opponentPlayer, viewController);
            // Update UI to show player 2 as "AI"
            GamePanel.player2Label.setText("AI");
        }
        // If "Exit button" is clicked on end panel
        else if (event.getSource() == EndPanel.exitButton) {
            // Exit the application
        	System.exit(0);
        } 
        // If "Help Menu Item" is clicked on the menu bar
        else if(event.getSource() == viewController.getGameFrame().getHelpMenuItem()) {
        	// Show help instructions
        	viewController.helpScreen();
        }
        // If "Quit Menu item" is clicked on the menu bar
        else if(event.getSource() == viewController.getGameFrame().getQuitMenuItem()) {
            // Ask user if they want to quit the game
        	int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to quit?",
                    "Confirm Quit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
        		// If yes, exit the application
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
        }
    }
}