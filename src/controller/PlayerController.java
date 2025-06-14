package controller;

// Imports
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import model.*;
import view.*;

// PlayerController handles logic for player actions such as planting, harvesting, drawing cards, and loading their hand. 
// It also connects player actions to updates in the view.
public class PlayerController {

	// Fields
	private Player player;

	// Constructor
	public PlayerController(Player player) {
		super();
		this.player = player;
	}

	// This method plants a card into a field panel and updates its display and player state
	public void plantCard(Card card, FieldPanel component) {
		component.add(card);
		card.setBounds(9, 180 - 10 * component.getAmount(), 100, 157);
		component.setAmount(component.getAmount() + 1);
		component.setType(card.getBeanType());
		component.setBeanometer(card.getBeanometer());
		GamePanel.updateFieldLabels();
		// Disables further interaction
		card.cripple();
		if (card.getSource().equals("PlayerHand")) {
			// Removes from deck
			player.getDeck().getCards().remove();
			// Tracks planting
			player.setPlantedTurn(player.getPlantedTurn() + 1);
		} else {
			// Removes from draw panel
			removeInventoryCard(card.getBeanType());
		}
		component.revalidate();
		component.repaint();
	}
	
	// This method removes a card of the given type from the draw panel
	public void removeInventoryCard(String type) {
		for (int i = 0; i < GamePanel.drawPanel.getCards().size(); i++) {
			if (GamePanel.drawPanel.getCards().get(i).getBeanType().equals(type)) {
				GamePanel.drawPanel.getCards().remove(i);
				break;
			}
		}
	}

	// This method harvests a field and gives the player coins based on the number of beans
	public static void harvestField(FieldPanel component) {
		if (component.getAmount() != 0) {
			if (checkAllFields(component.getPlayerController().getPlayer()) || component.getAmount() > 1) {
				int result = JOptionPane.NO_OPTION;
				if (!component.getPlayerController().getPlayer().isAI()) {
					result = JOptionPane
							.showConfirmDialog(null,
									"Are you sure you want to harvest your " + component.getType() + "field for "
											+ harvestProfit(component) + " coins?",
									"Confirm", JOptionPane.YES_NO_OPTION);
				}
				if (result == JOptionPane.YES_OPTION || component.getPlayerController().getPlayer().isAI()) {
					if (harvestProfit(component) >= 1) {
						// Discard excess cards after earning coins
						for (int j = component.getAmount()
								- component.getBeanometer()[harvestProfit(component) - 1]; j > 0; j--) {
							GamePanel.discardPanel.getDrawDeck().getDiscardCards()
									.add(new Card(component.getType(), component.getBeanometer(),
											new ImageIcon("images/" + component.getType() + ".png").getImage()));
							GamePanel.discardPanel.setTopCardImageIcon();
						}
					}
					// Add coins to player and reset the field
					component.getPlayerController().getPlayer()
							.setCoins(component.getPlayerController().getPlayer().getCoins() + harvestProfit(component));
					component.removeAll();
					component.setAmount(0);
					GamePanel.updateCoinLabels();
					GamePanel.updateFieldLabels();
				}
			}
		}
	}

	// This method checks if all fields have 1 or fewer cards (required before forced harvest)
	public static boolean checkAllFields(Player player) {
		for (FieldPanel currentPanel : GamePanel.playerfields) {
			if (currentPanel != null) {
				if (currentPanel.getPlayerController().getPlayer() == player && currentPanel.getAmount() > 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	// This method lets a player buy a third field if they have 3 or more coins
	public static boolean buyField(Player player) {
		int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to buy this field for 3 coins?",
				"Confirm", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			if (player.getCoins() >= 3) {
				player.setCoins(player.getCoins() - 3);
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "You do not have enough to purchase this field.", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		return false;
	}
	
	// This method calculates how many coins a player should get when harvesting a field
	public static int harvestProfit(FieldPanel fieldPanel) {
		try (BufferedReader br = new BufferedReader(new FileReader("Cards.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				String name = parts[0];
				if (name.equals(fieldPanel.getType())) {
					int[] sellingRates = new int[4];
					for (int i = 0; i < 4; i++) {
						sellingRates[i] = Integer.parseInt(parts[i + 2]);
					}
					int coins = 0;
					while (fieldPanel.getAmount() >= sellingRates[coins]) {
						coins++;
					}
					return coins;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// This method loads and displays the player’s hand on screen
	public void loadDeck(PlayerHandPanel handPanel) {
		handPanel.removeAll();
		Player thisPlayer = handPanel.getPlayerController().getPlayer();
		Queue<Card> playersCards = new LinkedList<>(thisPlayer.getDeck().getCards());
		int size = playersCards.size();
		for (int i = 0; i < size; i++) {
			Card cardPanel = playersCards.remove();
			// Set the card source and interaction depending on the game step
			if (i == 0 && thisPlayer.getPlantedTurn() <= 1 && thisPlayer.getDiscardedTurn() == 0 && thisPlayer.isTurn() && thisPlayer.getStep() == 2) {
				cardPanel.setSource("PlayerHand");
				cardPanel.cripple();
				ViewController.initializeDragListeners(cardPanel);
			} else if(thisPlayer.getPlantedTurn() >= 1 && thisPlayer.getDiscardedTurn() == 0 && thisPlayer.isTurn() && thisPlayer.getStep() == 2) {
				cardPanel.setSource("DiscardOnly");
				cardPanel.cripple();
				ViewController.initializeDragListeners(cardPanel);
			} else {
				cardPanel.setSource("PlayerHand");
				cardPanel.cripple();
			}
			// Link card to player and panel
			cardPanel.setPlayer(thisPlayer);
			cardPanel.setGamePanel(handPanel.getGamePanel());
			handPanel.add(cardPanel);
		}
		// Resize the panel to fit the number of cards
		handPanel.setPreferredSize(new Dimension(510,(int) Math.ceil(player.getDeck().getCards().size() / 5.0) * 157));
		handPanel.revalidate();
		handPanel.repaint();
	}
	
	// This method adds a card to the player’s hand
	public void drawCardToHand(Card card) {
		player.getDeck().getCards().add(card);
	}
	
	// Getters and setters
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}