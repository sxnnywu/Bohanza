package view;

// Imports
import java.awt.*;
import javax.swing.*;
import controller.*;
import model.*;

// GamePanel is the main panel for the game's GUI. It organizes and displays all the key components such as player fields, decks, coin counters, 
// draw/discard piles, and action buttons. It also handles the updating of dynamic labels and panels during gameplay.
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	// Fields
	// Rotated labels to indicate player names and turn indicators
	private RotatedLabel player1Label = new RotatedLabel("PLAYER 1", 270);
	public static RotatedLabel player2Label = new RotatedLabel("PLAYER 2", 270);
	private RotatedLabel indicatorLabel1 = new RotatedLabel(">", 270);
	private RotatedLabel indicatorLabel2 = new RotatedLabel(">", 90);
	private RotatedLabel discardLabel = new RotatedLabel("Discard Pile", 270);
	private RotatedLabel harvesterLabel = new RotatedLabel("Harvester", 270);
	private JLabel fieldsLabel = new JLabel("FIELDS");
	// Static labels for various UI components
	private JLabel deckLabel = new JLabel("DECK");
	private JLabel coinsLabel = new JLabel("COINS");
	// Group of key labels to apply consistent style
	private JLabel[] labelArray = { indicatorLabel1, indicatorLabel2, player1Label, player2Label, fieldsLabel,
			deckLabel, coinsLabel, discardLabel, harvesterLabel };
	// Coin icons beside the coin counters
	private JLabel coinIconLabel1 = new JLabel(new ImageIcon("images/Back.png"));
	private JLabel coinIconLabel2 = new JLabel(new ImageIcon("images/Back.png"));
	// Field panels for both players
	public static FieldPanel player1field1;
	public static FieldPanel player1field2;
	public static FieldPanel player1field3;
	public static FieldPanel player2field1;
	public static FieldPanel player2field2;
	public static FieldPanel player2field3;
	public static FieldPanel[] playerfields;
	// Harvest, draw, and discard panels for card mechanics
	public static HarvestPanel harvestPanel;
	public static DrawPanel drawPanel;
	public static DiscardPanel discardPanel;
	// Player decks (hand of cards)
	private static PlayerHandPanel player1Deck;
	private static PlayerHandPanel player2Deck;
	// Buttons to buy third bean fields
	public static JButton buyButton1;
	public static JButton buyButton2;
	// Field labels to show how many cards are planted
	private static JLabel field1Label1 = new JLabel("X 0");
	private static JLabel field1Label2 = new JLabel("X 0");
	private static JLabel field1Label3 = new JLabel("X 0");
	private static JLabel field2Label1 = new JLabel("X 0");
	private static JLabel field2Label2 = new JLabel("X 0");
	private static JLabel field2Label3 = new JLabel("X 0");
	private static JLabel[] field1Labels = { field1Label1, field1Label2, field1Label3 };
	private static JLabel[] field2Labels = { field2Label1, field2Label2, field2Label3 };
	// Coin count labels for both players
	private static JLabel coinLabel1 = new JLabel("X 0");
	private static JLabel coinLabel2 = new JLabel("X 0");
	// Scrollable panes for players' hands
	private JScrollPane inventoryScroll1;
	private JScrollPane inventoryScroll2;
	// Buttons to switch turns or move to next step in game
	public static JButton turnSwitchButton;
	public static JButton nextStepButton;
	// Labels to show current turn, game step, and cards left
	private static JLabel turnLabel = new JLabel("Turn: Player1");
	private static JLabel stepLabel = new JLabel("Step: 2");
	private static JLabel cardsLeft = new JLabel ("Cards Left: "+GameController.getDrawDeck().getCards().size());

	// Constructor
	public GamePanel() {
		// Layout
		setSize(1486, 827);
		setBackground(Color.red);
		setLayout(null);
		for (JLabel currentLabel : labelArray) {
			currentLabel.setFont(new Font("Cooper Black", Font.BOLD, 23));
			currentLabel.setForeground(Color.white);
		}

		// Label layouts
		player2Label.setFont(player2Label.getFont().deriveFont(40f));
		player2Label.setBounds(5, 50, 50, 300);

		player1Label.setFont(player1Label.getFont().deriveFont(40f));
		player1Label.setBounds(5, 425, 50, 300);
		
		discardLabel.setFont(discardLabel.getFont().deriveFont(25f));
		discardLabel.setBounds(560, 225, 100, 200);
		add(discardLabel);
		
		harvesterLabel.setFont(discardLabel.getFont().deriveFont(25f));
		harvesterLabel.setBounds(560, 410, 100, 200);
		add(harvesterLabel);

		indicatorLabel1.setBounds(684, 770, 25, 25);
		indicatorLabel2.setBounds(684, 25, 25, 25);

		coinIconLabel1.setBounds(1260, 605, new ImageIcon("images/Back.png").getIconWidth(),
				new ImageIcon("images/Back.png").getIconHeight());

		coinIconLabel2.setBounds(1260, 50, new ImageIcon("images/Back.png").getIconWidth(),
				new ImageIcon("images/Back.png").getIconHeight());

		// Create and add scrollable player 1 hand panel
		player1Deck = new PlayerHandPanel(MainController.currentPlayer, this);
		inventoryScroll1 = new JScrollPane(player1Deck);
		inventoryScroll1.setBounds(640, 600, 510, 167);
		inventoryScroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inventoryScroll1.getVerticalScrollBar().setUnitIncrement(10);
		inventoryScroll1.setHorizontalScrollBar(null);

		// Create and add scrollable player 2 hand panel
		player2Deck = new PlayerHandPanel(MainController.opponentPlayer, this);
		inventoryScroll2 = new JScrollPane(player2Deck);
		inventoryScroll2.setBounds(640, 50, 510, 167);
		inventoryScroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inventoryScroll2.getVerticalScrollBar().setUnitIncrement(10);
		inventoryScroll2.setHorizontalScrollBar(null);
		
		// Initialize player field panel array
		playerfields = new FieldPanel[6];

		// Set up field panels for player 1
		player1field1 = new FieldPanel(MainController.currentPlayer);
		player1field1.setBounds(100, 425, 118, 340);
		playerfields[0] = player1field1;

		player1field2 = new FieldPanel(MainController.currentPlayer);
		player1field2.setBounds(275, 425, 118, 340);
		playerfields[1] = player1field2;

		player1field3 = new FieldPanel(MainController.currentPlayer);
		player1field3.setBounds(450, 425, 118, 340);

		// Set up field panels for player 2
		player2field1 = new FieldPanel(MainController.opponentPlayer);
		player2field1.setBounds(100, 50, 118, 340);
		playerfields[3] = player2field1;

		player2field2 = new FieldPanel(MainController.opponentPlayer);
		player2field2.setBounds(275, 50, 118, 340);
		playerfields[4] = player2field2;

		player2field3 = new FieldPanel(MainController.opponentPlayer);
		player2field3.setBounds(450, 50, 118, 340);

		// Create and style "Buy" button for player 1
		buyButton1 = new JButton("BUY");
		buyButton1.setBounds(450, 575, 118, 50);
		buyButton1.setFocusPainted(false);
		buyButton1.setBackground(new Color(0, 100, 0));
		buyButton1.setOpaque(true);
		buyButton1.setContentAreaFilled(true);
		buyButton1.setBorderPainted(false);
		buyButton1.setForeground(Color.white);

		// Create and style "Buy" button for player 2
		buyButton2 = new JButton("BUY");
		buyButton2.setBounds(450, 200, 118, 50);
		buyButton2.setFocusPainted(false);
		buyButton2.setBackground(new Color(0, 100, 0));
		buyButton2.setOpaque(true);
		buyButton2.setContentAreaFilled(true);
		buyButton2.setBorderPainted(false);
		buyButton2.setForeground(Color.white);

		// Create and style turn switch button
		turnSwitchButton = new JButton("Switch Turn");
		turnSwitchButton.setBounds(890, 500, 118, 50);
		turnSwitchButton.setFocusPainted(false);
		turnSwitchButton.setBackground(new Color(0, 100, 0));
		turnSwitchButton.setOpaque(true);
		turnSwitchButton.setContentAreaFilled(true);
		turnSwitchButton.setBorderPainted(false);
		turnSwitchButton.setForeground(Color.white);

		// Create and style next step button
		nextStepButton = new JButton("Next Step");
		nextStepButton.setBounds(1020, 500, 118, 50);
		nextStepButton.setFocusPainted(false);
		nextStepButton.setBackground(new Color(0, 100, 0));
		nextStepButton.setOpaque(true);
		nextStepButton.setContentAreaFilled(true);
		nextStepButton.setBorderPainted(false);
		nextStepButton.setForeground(Color.white);

		// Configure and position field labels for both players
		for (int i = 0; i < 3; i++) {
			field1Labels[i].setFont(new Font("Cooper Black", Font.BOLD, 25));
			field1Labels[i].setForeground(Color.white);
			field1Labels[i].setBounds(100 + 175 * i, 765, 118, 25);
			add(field1Labels[i]);
			field2Labels[i].setFont(new Font("Cooper Black", Font.BOLD, 25));
			field2Labels[i].setForeground(Color.white);
			field2Labels[i].setBounds(100 + 175 * i, 25, 118, 25);
			add(field2Labels[i]);
		}

		// Set coin label for player 1
		coinLabel1.setFont(new Font("Cooper Black", Font.BOLD, 25));
		coinLabel1.setForeground(Color.white);
		coinLabel1.setBounds(1260, 762, 118, 25);
		add(coinLabel1);

		// Set coin label for player 2
		coinLabel2.setFont(new Font("Cooper Black", Font.BOLD, 25));
		coinLabel2.setForeground(Color.white);
		coinLabel2.setBounds(1260, 22, 118, 25);
		add(coinLabel2);

		// Set label to show whose turn it is
		turnLabel.setFont(new Font("Cooper Black", Font.BOLD, 25));
		turnLabel.setForeground(Color.white);
		turnLabel.setBounds(890, 280, 200, 25);
		add(turnLabel);

		// Set label to show current game step
		stepLabel.setFont(new Font("Cooper Black", Font.BOLD, 25));
		stepLabel.setForeground(Color.white);
		stepLabel.setBounds(1140, 280, 150, 25);
		add(stepLabel);
		
		// Set label to show how many cards are left in the deck
		cardsLeft.setFont(new Font("Cooper Black", Font.BOLD, 25));
		cardsLeft.setForeground(Color.white);
		cardsLeft.setBounds(890, 250, 300, 25);
		add(cardsLeft);

		// Create and position panel for harvesting beans
		harvestPanel = new HarvestPanel();
		harvestPanel.setBounds(650, 410, 200, 200);

		// Create and position panel for drawing cards
		drawPanel = new DrawPanel(GameController.getDrawDeck());
		drawPanel.setBounds(890, 320, 510, 167);

		// Create and position panel for discard pile
		discardPanel = new DiscardPanel(GameController.getDrawDeck());
		discardPanel.setBounds(650, 240, 220, 200);

		//Add all major components to the panel
		add(inventoryScroll1);
		add(inventoryScroll2);
		add(buyButton1);
		add(buyButton2);
		add(turnSwitchButton);
		add(nextStepButton);
		add(player1field1);
		add(player1field2);
		add(player1field3);
		add(player2field1);
		add(player2field2);
		add(player2field3);
		add(harvestPanel);
		add(drawPanel);
		add(discardPanel);
		add(coinIconLabel1);
		add(coinIconLabel2);
		add(player1Label);
		add(player2Label);
		add(indicatorLabel1);
		add(indicatorLabel2);
	}

	// This method updates the turn and step labels based on the current player
	public static void updateCoordinationLabels(Player turnPlayer) {
		stepLabel.setText("Step: " + turnPlayer.getStep());
		if (turnPlayer == GameController.getPlayer1()) {
			turnLabel.setText("Turn: Player1");
		} else {
			turnLabel.setText("Turn: Player2");
		}
		cardsLeft.setText("Cards Left: "+GameController.getDrawDeck().getCards().size());
	}

	// This method reloads both player decks to reflect card changes
	public static void updateDecks() {
		MainController.currentPlayer.loadDeck(player1Deck);
		MainController.opponentPlayer.loadDeck(player2Deck);
	}

	// This method updates the coin counter labels for both players
	public static void updateCoinLabels() {
		coinLabel1.setText("X " + GameController.getPlayer1().getCoins());
		coinLabel2.setText("X " + GameController.getPlayer2().getCoins());
	}

	// This method updates the field labels to show how many cards are planted in each field
	public static void updateFieldLabels() {
		field1Label1.setText("X " + player1field1.getAmount());
		field1Label2.setText("X " + player1field2.getAmount());
		field1Label3.setText("X " + player1field3.getAmount());
		field2Label1.setText("X " + player2field1.getAmount());
		field2Label2.setText("X " + player2field2.getAmount());
		field2Label3.setText("X " + player2field3.getAmount());
	}

	// This method paints the wood background image for the board
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon("images/wood.png").getImage(), 0, 0, getWidth(), getHeight(), this);
	}

	// This method refreshes the draw panel with the latest drawn cards and enables dragging
	public static void updateDrawPanel() {
		drawPanel.getDrawInventory().removeAll();
		for (Card currentCard : drawPanel.getCards()) {
			currentCard.cripple();
			ViewController.initializeDragListeners(currentCard);
			drawPanel.getDrawInventory().add(currentCard);
		}
		drawPanel.getDrawInventory().revalidate();
		drawPanel.getDrawInventory().repaint();
	}
}