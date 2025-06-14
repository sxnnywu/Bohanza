package view;

// Imports
import java.awt.*;
import javax.swing.*;
import controller.*;

// FieldPanel represents a player's bean field in the game. It visually displays the field background and stores information about
// the type of bean planted, the number of beans, and the associated beanometer.
@SuppressWarnings("serial")
public class FieldPanel extends JPanel {

	// Fields
	String type;
	int amount = 0;
	PlayerController playerController;
	private int[] beanometer;
	
	// Constructor
	public FieldPanel(PlayerController playerController) {
		setLayout(null);
		this.playerController = playerController;	
	}
	
	// Getters and setters
	public int[] getBeanometer() {
		return beanometer;
	}

	public void setBeanometer(int[] beanometer) {
		this.beanometer = beanometer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public PlayerController getPlayerController() {
		return playerController;
	}

	public void setPlayerController(PlayerController playerController) {
		this.playerController = playerController;
	}

	// This method draws the background image of the bean field
	public void paintComponent(Graphics g) {
		g.drawImage(new ImageIcon("images/field.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
	}	
}