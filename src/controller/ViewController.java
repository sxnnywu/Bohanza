package controller;

// Imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.*;
import view.*;

// ViewController handles transitions between different GUI panels in the game. It manages user interactions, updates the game view based on player actions,
// and handles drag-and-drop logic for card and harvest interactions.
public class ViewController implements ActionListener {

	// Fields
	private static GameFrame gameFrame;
	private static HomePanel homePanel;
	private OptionPanel optionPanel;
	private static GamePanel gamePanel;
	public static EndPanel endPanel;

	// Constructor
	public ViewController() {
		// Initializes all GUI panels
		gameFrame = new GameFrame();
		homePanel = new HomePanel();
		optionPanel = new OptionPanel();
		gamePanel = new GamePanel();
		endPanel = new EndPanel();

		// Set the initial screen to the home panel
		gameFrame.setContentPane(homePanel);
		gameFrame.setVisible(true);

		// Add action listeners for core game buttons
		GamePanel.buyButton1.addActionListener(this);
		GamePanel.buyButton2.addActionListener(this);
		GamePanel.turnSwitchButton.addActionListener(this);
		GamePanel.nextStepButton.addActionListener(this);
		// Enable mouse interaction for harvesting beans
		setupMouseHandlers(GamePanel.harvestPanel);
	}

	// This method switches the screen to Home Panel
	public void switchToHome() {
		gameFrame.setContentPane(homePanel);
		gameFrame.revalidate();
		gameFrame.repaint();
	}

	// This method switches the screen to Option Panel
	public void switchToOption() {
		gameFrame.setContentPane(optionPanel);
		gameFrame.revalidate();
		gameFrame.repaint();
	}

	// This method switches the screen to Game Panel
	public void switchToGame() {
		gameFrame.setContentPane(gamePanel);
		gameFrame.revalidate();
		gameFrame.repaint();
	}

	// This method displays the game instructions using an option pane
	@SuppressWarnings("static-access")
	public void helpScreen() {
		String instructions = """
				🎮 HOW TO PLAY BOHNANZA 🎮

				Welcome to Bohnanza! Here's how to play:

				STARTING THE GAME:
				1. Each player starts with 5 cards in their hand and 2 bean fields.
				2. You must plant cards in the exact order they appear in your hand.
				   - The first card is marked with an arrow (↓) on the screen.

				YOUR TURN (Player Actions):
				Step 1: Plant Beans
				- You must plant the first card in your hand by dragging it to an empty or matching bean field.
				- You may plant the second card in your hand if you choose (optional).

				Step 2: Offer Phase
				- Click "Next Step" to reveal 3 new cards to the shared Offer Area.
				- You can choose to plant any of these offered cards on your own fields.

				Step 3: Draw Phase
				- Click "Switch Turn" to draw 3 new cards; they will be added to the back of your hand in order.

				End of Turn
				- Once you click "Switch Turn" it will also let the other player take their turn.
				- Players alternate turns by repeating the above steps.
				
				✨ Special Rule:
				- After the first turn, the next player may take any remaining cards in the Offer Area before planting their own beans. This is repeated throughout the game.

				🔚 ENDING THE GAME:
				- The game ends once the draw pile is empty after going through the deck once.
				- The player with the most coins wins!

				🌾 HARVESTING BEANS:
				- You can harvest a bean field at any time by dragging the "Harvester" (green tractor) to the desired field.
				- Coins are earned based on the number of cards in the field (see card bottom for coin thresholds).
				- After harvesting, the field becomes empty and ready for planting again.

				💡 PRO TIPS:
				- Plan ahead: the order of cards in your hand matters - you can't rearrange them!
				- Harvest wisely: harvesting too early earns fewer coins, but waiting too long may force a harvest.

				Good luck, and may your beans be bountiful! 🌱
				""";
		// Option pane layout and formatting
		UIManager UI = new UIManager();
		UI.put("OptionPane.background", Color.decode("#f7f911"));
		UI.put("Panel.background", Color.decode("#f7f911"));
		UI.put("Panel.foreground", Color.decode("#f7f911"));
		JOptionPane.showMessageDialog(gameFrame, instructions, "How to Play", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// This method draws 3 cards from the draw deck and adds them to the shared Offer Area
	public static void drawAndOfferBeans() {
		for (int i = 0; i < 3; i++) {
			GameController.getDrawDeck();
			Card currentCard = DrawDeck.drawCard();
			currentCard.cripple();
			// Make draggable
			initializeDragListeners(currentCard);
			currentCard.setGamePanel(gamePanel);
			// Marked as offered card
			currentCard.setSource("DrawInventory");
			GamePanel.drawPanel.getCards().add(currentCard);
			// Check for matching bean on discard pile and add if matching
			if (GamePanel.discardPanel.getDrawDeck().getDiscardCards().size() >= 1 && GamePanel.discardPanel
					.getDrawDeck().getDiscardCards().peek().getBeanType().equals(currentCard.getBeanType())) {
				Card otherCurrentCard = GamePanel.discardPanel.getDrawDeck().getDiscardCards().pop();
				otherCurrentCard.cripple();
				initializeDragListeners(otherCurrentCard);
				otherCurrentCard.setGamePanel(gamePanel);
				otherCurrentCard.setSource("DrawInventory");
				GamePanel.drawPanel.getCards().add(otherCurrentCard);
				GamePanel.discardPanel.setTopCardImageIcon();
			}
		}
	}

	// This method handles game actions like buying a field, ending a turn, or moving to the next step
	@Override
	public void actionPerformed(ActionEvent e) {
		Player player;
		Player turnPlayer;
		Player nonTurnPlayer;
		if (GameController.getPlayer1().isTurn()) {
			turnPlayer = GameController.getPlayer1();
			nonTurnPlayer = GameController.getPlayer2();
		} else {
			turnPlayer = GameController.getPlayer2();
			nonTurnPlayer = GameController.getPlayer1();
		}
		// Handle "Buy Field" for player 1
		if (e.getSource() == GamePanel.buyButton1) {
			player = GameController.getPlayer1();
			if (PlayerController.buyField(player)) {
				GamePanel.playerfields[2] = GamePanel.player1field3;
				gamePanel.remove(GamePanel.buyButton1);
				GamePanel.updateCoinLabels();
			}
		// Handle "Buy Field" for player 2
		} else if (e.getSource() == GamePanel.buyButton2) {
			player = GameController.getPlayer2();
			if (PlayerController.buyField(player)) {
				GamePanel.playerfields[5] = GamePanel.player2field3;
				gamePanel.remove(GamePanel.buyButton2);
				GamePanel.updateCoinLabels();
			}
		// Handle turn switching and drawing cards
		} else if (e.getSource() == GamePanel.turnSwitchButton) {
			if (turnPlayer.getStep() == 3) {
				// Draw 2 cards to back of hand
				GamePanel.discardPanel.getDrawDeck();
				turnPlayer.getDeck().getCards().add(DrawDeck.drawCard());
				GamePanel.discardPanel.getDrawDeck();
				turnPlayer.getDeck().getCards().add(DrawDeck.drawCard());
				// Switch turns
				Player tempPlayer = turnPlayer;
				turnPlayer = nonTurnPlayer;
				nonTurnPlayer = tempPlayer;
				nonTurnPlayer.resetPlayer();
				turnPlayer.setTurn(true);
				// Skip planting if no cards are in draw panel
				if (GamePanel.drawPanel.getCards().size() == 0) {
					turnPlayer.setStep(2);
				}
				// Update UI
				GamePanel.updateDecks();
				GamePanel.updateCoordinationLabels(turnPlayer);
				// If AI's turn, let AI take action
				if (turnPlayer.isAI()) {
					MainController.aiController.takeTurn();
				}
			}
		// Handle "Next Step" logic for phase progression
		} else if (e.getSource() == GamePanel.nextStepButton) {
			// Step 1 --> Step 2: clear offered cards
			if (turnPlayer.getStep() == 1) {
				for (int i = GamePanel.drawPanel.getCards().size() - 1; i >= 0; i--) {
					Card currentCard = GamePanel.drawPanel.getCards().get(i);
					GamePanel.discardPanel.getDrawDeck().discardCard(currentCard);
					GamePanel.drawPanel.getCards().remove(i);
				}
				turnPlayer.setStep(2);
				GamePanel.updateDrawPanel();
			// Step 2 --> Step 3: draw and offer new beans
			} else if (turnPlayer.getStep() == 2 && turnPlayer.getPlantedTurn() >= 1) {
				turnPlayer.setStep(3);
				drawAndOfferBeans();
				GamePanel.updateDrawPanel();
			} 
			// Update UI
			GamePanel.updateDecks();
			GamePanel.updateCoordinationLabels(turnPlayer);
			GamePanel.discardPanel.setTopCardImageIcon();
		}
		// Refresh panel
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	/// This method adds mouse listeners to allow dragging the harvest panel and performing a harvest action when released over a valid field.
	private void setupMouseHandlers(HarvestPanel harvestPanel) {
		final Point[] offset = new Point[1];
		MouseAdapter mouseAdapter;
		MouseMotionAdapter mouseMotionAdapter;
		mouseAdapter = new MouseAdapter() {
			Point locationInGamePanel;
			@Override
			public void mousePressed(MouseEvent e) {
				offset[0] = e.getPoint();
				locationInGamePanel = SwingUtilities.convertPoint(harvestPanel, new Point(0, 0), gamePanel);
				gamePanel.add(harvestPanel, JLayeredPane.DRAG_LAYER);
				gamePanel.setComponentZOrder(harvestPanel, 0);
				harvestPanel.setLocation(locationInGamePanel);
				gamePanel.repaint();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				Point pointInGamePanel = SwingUtilities.convertPoint(harvestPanel, e.getPoint(), gamePanel);
				harvestPanel.setVisible(false);
				Component hovered = SwingUtilities.getDeepestComponentAt(gamePanel, pointInGamePanel.x,
						pointInGamePanel.y);
				harvestPanel.setVisible(true);

				while (hovered != null && !(hovered instanceof FieldPanel)) {
					hovered = hovered.getParent();
				}
				for (int i = 0; i < GamePanel.playerfields.length; i++) {
					FieldPanel component = GamePanel.playerfields[i];
					if (component != null) {
						if (hovered == component && component.getPlayerController().getPlayer().isTurn()) {
							component.getPlayerController();
							PlayerController.harvestField(component);
						}
					}
				}
				harvestPanel.setBounds(650, 410, 200, 200);
				gamePanel.repaint();
			}
		};
		mouseMotionAdapter = new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point mouse = SwingUtilities.convertPoint(harvestPanel, e.getPoint(), gamePanel);
				harvestPanel.setLocation(mouse.x - offset[0].x, mouse.y - offset[0].y);
				gamePanel.repaint();
			}
		};
		harvestPanel.addMouseListener(mouseAdapter);
		harvestPanel.addMouseMotionListener(mouseMotionAdapter);
	}
	
	// This method adds mouse listeners to a Card to allow drag-and-drop interaction, with logic for planting, discarding, or 
	// reverting the card based on where it's dropped
	public static void initializeDragListeners(Card card) {
		final Point[] offset = new Point[1];
		MouseAdapter mouseAdapter;
		MouseMotionAdapter mouseMotionAdapter;
		mouseAdapter = new MouseAdapter() {
			Point locationInGamePanel;
			public void mousePressed(MouseEvent e) {
				offset[0] = e.getPoint();
				locationInGamePanel = SwingUtilities.convertPoint(card, new Point(0, 0), gamePanel);
				gamePanel.add(card, JLayeredPane.DRAG_LAYER);
				gamePanel.setComponentZOrder(card, 0);
				card.setLocation(locationInGamePanel);
				gamePanel.repaint();
			}
			public void mouseReleased(MouseEvent e) {
				boolean onField = false;
				for (int i = 0; i < GamePanel.playerfields.length; i++) {
					FieldPanel component = GamePanel.playerfields[i];
					if (component != null && !onField) {
						if (component.getBounds().intersects(card.getBounds())
								&& component.getPlayerController().getPlayer() == card.getPlayer()
								&& card.getPlayer().getPlantedTurn() != 2 && card.getSource().equals("PlayerHand")
								&& card.getPlayer().getStep() == 2) {
							onField = fieldCheck(component, card);
							break;
						} else if (component.getBounds().intersects(card.getBounds())
								&& component.getPlayerController().getPlayer().isTurn()
								&& card.getSource().equals("DrawInventory")
								&& component.getPlayerController().getPlayer().getStep() != 2) {
							onField = fieldCheck(component, card);
							break;
						}
					}
				}
				if (GamePanel.discardPanel.getBounds().intersects(card.getBounds())
						&& !card.getSource().equals("DrawInventory") && card.getPlayer().isTurn()
						&& card.getPlayer().getPlantedTurn() >= 1 && card.getPlayer().getDiscardedTurn() == 0
						&& card.getPlayer().getStep() == 2) {
					GamePanel.discardPanel.getDrawDeck().discardCard(card);
					GamePanel.discardPanel.setTopCardImageIcon();
					card.getPlayer().setDiscardedTurn(1);
					card.getPlayer().getDeck().getCards().remove(card);
				}
				if (!onField) {
					gamePanel.remove(card);
					card.cripple();
				}
				GamePanel.updateDrawPanel();
				GamePanel.updateDecks();
				gamePanel.revalidate();
				gamePanel.repaint();
			}
		};
		mouseMotionAdapter = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point mouse = SwingUtilities.convertPoint(card, e.getPoint(), gamePanel);
				card.setLocation(mouse.x - offset[0].x, mouse.y - offset[0].y);
				gamePanel.repaint();
			}
		};
		card.setMouseAdapter(mouseAdapter);
		card.setMouseMotionAdapter(mouseMotionAdapter);
		card.addMouseListener(mouseAdapter);
		card.addMouseMotionListener(mouseMotionAdapter);
	}

	// This method validates if the card can be planted in the given field. If the field is empty, prompts for confirmation. 
	// If types match, plants directly. Shows warning if invalid.
	private static boolean fieldCheck(FieldPanel component, Card card) {
		if (component.getAmount() == 0) {
			int result = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to dedicate this field to only " + card.getBeanType() + " beans?", "Confirm",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				component.getPlayerController().plantCard(card, component);
				component.setBeanometer(card.getBeanometer());
				return true;
			}
		} else if (component.getType() == card.getBeanType()) {
			component.getPlayerController().plantCard(card, component);
			return true;
		} else {
			JOptionPane.showMessageDialog(null,
					"You can't plant a " + card.getBeanType() + " bean in a " + component.getType() + " bean field.",
					"Warning", JOptionPane.WARNING_MESSAGE);
		}
		return false;
	}
	
	// This method replaces the current view with the end panel and displays the final results of the game, including win/loss or tie information.
	public static void switchToEndPanel(String winner, String loser, Player winningPlayer, Player losingPlayer, boolean isTie) {
		gameFrame.getContentPane().removeAll();
		endPanel.editText(winner, loser, winningPlayer, losingPlayer, isTie);
		gameFrame.add(endPanel);
		gameFrame.revalidate();
		gameFrame.repaint();
	}
	
	// Getters and setters
	public GameFrame getGameFrame() {
		return gameFrame;
	}
	public HomePanel getHomePanel() {
		return homePanel;
	}
	public OptionPanel getOptionPanel() {
		return optionPanel;
	}
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	@SuppressWarnings("static-access")
	public void setHomePanel(HomePanel homePanel) {
		this.homePanel = homePanel;
	}
	public void setOptionPanel(OptionPanel optionPanel) {
		this.optionPanel = optionPanel;
	}
	@SuppressWarnings("static-access")
	public void setGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}
}