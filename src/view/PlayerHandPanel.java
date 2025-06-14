package view;

// Imports
import java.awt.*;
import javax.swing.*;
import controller.*;

// PlayerHandPanel is a custom panel that displays a player's hand (cards). It uses a wrap layout and adjusts its size based on the number of cards.
@SuppressWarnings("serial")
public class PlayerHandPanel extends JPanel {
	
	// Fields
	GamePanel gamePanel;
	private PlayerController playerController;

	// Constructor
	public PlayerHandPanel(PlayerController playerController, GamePanel gamePanel) {
		// Layout
		setLayout(new WrapLayout(FlowLayout.LEFT, 0, 0));
		setOpaque(false);
		setPreferredSize(new Dimension(510, (int) Math.ceil(playerController.getPlayer().getDeck().getCards().size() / 5.0) * 157));
		
		this.gamePanel = gamePanel;
		this.playerController = playerController;
		
		// Load the player's deck into this panel
		playerController.loadDeck(this);
	}

	// Getters and setters
	public PlayerController getPlayerController() {
		return playerController;
	}

	public void setPlayerController(PlayerController playerController) {
		this.playerController = playerController;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
}