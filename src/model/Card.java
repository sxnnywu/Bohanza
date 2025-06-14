package model;

// Imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import view.*;

// This class represents a single card in the game. Each card has a bean type, a beanometer (points system), an image, and 
// metadata about its source, player, and game state.
@SuppressWarnings("serial")
public class Card extends JPanel {

	// Fields
	private String beanType;
	private int[] beanometer;
	private Image cardImage;
	private String source = null;
	private GamePanel gamePanel;
	private Player player = null;
	private MouseAdapter mouseAdapter;
	private MouseMotionAdapter mouseMotionAdapter;
	
	// Constructor for generic card without game context
	public Card(String beanType, int[] beanometer, Image cardImage) {
	    init(beanType, beanometer, cardImage);
	}

	// Constructor for a card associated with a player and game context
	public Card(String beanType, int[] beanometer, Image cardImage, Player player, GamePanel gamePanel, String source) {
	    this.player = player;
	    this.source = source;
	    this.gamePanel = gamePanel;
	    init(beanType, beanometer, cardImage);
	}
	
	// Initialization
	private void init(String beanType, int[] beanometer, Image cardImage) {
	    setPreferredSize(new Dimension(100, 157));
	    this.beanType = beanType;
	    this.beanometer = beanometer;
	    this.cardImage = cardImage;
	}

	// This method disables drag-and-drop listeners for the card
	public void cripple() {
		removeMouseListener(mouseAdapter);
		removeMouseMotionListener(mouseMotionAdapter);
	}

	// Getters and setters
	public String getBeanType() {
		return beanType;
	}

	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}

	public int[] getBeanometer() {
		return beanometer;
	}

	public void setBeanometer(int[] beanometer) {
		this.beanometer = beanometer;
	}

	public Image getCardImage() {
		return cardImage;
	}

	public void setCardImage(Image cardImage) {
		this.cardImage = cardImage;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public String getSource() {
		return source;
	}

	public MouseAdapter getMouseAdapter() {
		return mouseAdapter;
	}

	public void setMouseAdapter(MouseAdapter mouseAdapter) {
		this.mouseAdapter = mouseAdapter;
	}

	public MouseMotionAdapter getMouseMotionAdapter() {
		return mouseMotionAdapter;
	}

	public void setMouseMotionAdapter(MouseMotionAdapter mouseMotionAdapter) {
		this.mouseMotionAdapter = mouseMotionAdapter;
	}

	// Paints the card image onto the panel
	public void paintComponent(Graphics g) {
		g.drawImage(new ImageIcon(cardImage).getImage(), 0, 0, getWidth(), getHeight(), this);
	}
}