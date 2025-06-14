package controller;

// Imports
import java.awt.event.*;
import java.util.*;
import model.*;
import view.*;

// This class handles the turn logic and behaviour for the AI player. It automates the AI's decisions for planting, harvesting, and taking cards.
public class AIController {

	// Fields
	private ViewController viewController;
	private static PlayerController aiPlayerController;
	private static Player aiPlayer;
	private static Player opponent;

	// Array of FieldPanel objects to represent the AI player's fields on the game panel
	static FieldPanel[] aiPlayerFields = { GamePanel.player2field1, GamePanel.player2field2 };

	// Constructor
	public AIController(PlayerController aiPlayerController, ViewController viewController) {
		// Initializing all references to other controllers and players
		AIController.aiPlayerController = aiPlayerController;
		AIController.aiPlayer = aiPlayerController.getPlayer();
		AIController.opponent = MainController.currentPlayer.getPlayer();
		this.viewController = viewController;
	}

	// Simulates a turn switch by triggering the "Switch Turn" button
	public void simulateTurnSwitch() {
		ActionEvent turnSwitchEvent = new ActionEvent(GamePanel.turnSwitchButton, ActionEvent.ACTION_PERFORMED,
				"turnSwitchButton");
		viewController.actionPerformed(turnSwitchEvent);
	}

	// Simulates a step switch to move through different phases of the turn
	public void simulateStepSwitch() {
		ActionEvent nextStepEvent = new ActionEvent(GamePanel.nextStepButton, ActionEvent.ACTION_PERFORMED,
				"nextStepButton");
		viewController.actionPerformed(nextStepEvent);
	}

	// Main method that handles the AI's turn
	public void takeTurn() {
		// Step 0: Take offered beans if any
		if (GamePanel.drawPanel.getCards().size() != 0) {
			takeOfferedBeans();
		}
		// Move to next step
		simulateStepSwitch();
		// Step 1: Plant first card from AI's hand
		if (!aiPlayer.getDeck().isEmpty()) {
			Card firstCard = aiPlayer.getDeck().getCards().peek();
			// Harvest if no field can accept the card
			harvestIfNeeded(firstCard);
			// Choose field to plant card
			FieldPanel fieldToPlant = chooseFieldToPlant(firstCard);
			if (fieldToPlant != null) {
				// Plant the  card
				aiPlayerController.plantCard(firstCard, fieldToPlant);
			} else {
				// If no field is available, choose the best one to plant in
				if (aiPlayerFields[0].getAmount() < aiPlayerFields[1].getAmount() && aiPlayerFields[0].getAmount() != 1) {
					aiPlayerController.plantCard(firstCard, aiPlayerFields[0]);
				} else {
					aiPlayerController.plantCard(firstCard, aiPlayerFields[1]);
				}
			}
			// Remove the card from deck
			aiPlayer.getDeck().getCards().poll();
		}
		// Step 2: Plant second card if it matches a field and opponent doesn't have it
		if (!aiPlayer.getDeck().isEmpty()) {
			Card secondCard = aiPlayer.getDeck().getCards().peek();
			if (!opponentHasBeanType(secondCard.getBeanType())) {
				// Harvest if needed
				harvestIfNeeded(secondCard);
				// Choose field to plant second card
				FieldPanel fieldToPlant = chooseFieldToPlant(secondCard);
				if (fieldToPlant != null) {
					// Plant the second card
					aiPlayerController.plantCard(secondCard, fieldToPlant);
				}
				aiPlayer.getDeck().getCards().poll();
			}
		}
		// Move to next step
		simulateStepSwitch();
		// Step 3: AI decides what to do with offered beans
		takeOfferedBeans();
		// Switch the turn
		simulateTurnSwitch();
	}

	// This method handles taking offered beans from the draw panel and planting them
	private void takeOfferedBeans() {
		// Get offered beans
		List<Card> offeredCards = new ArrayList<>(GamePanel.drawPanel.getCards());
		for (Card card : offeredCards) {
			// Check if the bean matches the type in any of the fields and plant accordingly
			if (aiPlayerFields[0].getAmount() != 0 && aiPlayerFields[0].getType().equals(card.getBeanType())) {
				aiPlayerController.plantCard(card, aiPlayerFields[0]);
			} else if (aiPlayerFields[1].getAmount() != 0 && aiPlayerFields[1].getType().equals(card.getBeanType())) {
				aiPlayerController.plantCard(card, aiPlayerFields[1]);
			}
		}
	}

	// This method chooses the best field to plant a card
	private static FieldPanel chooseFieldToPlant(Card card) {
		for (FieldPanel field : aiPlayerFields) {
			if (canPlantInField(card, field)) {
				// Return field if the card can be planted
				return field;
			}
		}
		// No suitable field found
		return null;
	}

	// This method checks if the card can be planted in the given field
	private static boolean canPlantInField(Card card, FieldPanel field) {
		if (field.getAmount() == 0) {
			// If the field is empty, the card can be planted
			return true;
		} else if (field.getAmount() < 3 && field.getType().equals(card.getBeanType())) {
			// If the field already contains cards of the same type and has space
			return true;
		}
		// Can't plant here
		return false;
	}

	//This method harvests a field if needed (when no field can accept the current card)
	private static void harvestIfNeeded(Card card) {
		if (!canPlantInAnyField(card)) {
			// Find a field to harvest
			FieldPanel fieldToHarvest = findFieldToHarvest();
			if (fieldToHarvest != null) {
				// Harvest the field
				PlayerController.harvestField(fieldToHarvest);
			}
		}
	}

	// This method checks if the card can be planted in any of the fields
	private static boolean canPlantInAnyField(Card card) {
		for (FieldPanel field : aiPlayerFields) {
			if (canPlantInField(card, field)) {
				// If a field can accept the card
				return true;
			}
		}
		// No field available for planting
		return false;
	}

	// This method finds the field with the least number of cards for harvesting
	private static FieldPanel findFieldToHarvest() {
		FieldPanel bestField = null;
		int minCards = Integer.MAX_VALUE;
		// Find the field with the fewest cards
		for (FieldPanel field : aiPlayerFields) {
			if (field.getAmount() != 0 && field.getAmount() < minCards) {
				bestField = field;
				minCards = field.getAmount();
			}
		}
		// Return the best field to harvest
		return bestField;
	}

	// This method checks if the opponent has a certain type of bean in their deck
	private static boolean opponentHasBeanType(String beanType) {
		for (Card card : opponent.getDeck().getCards()) {
			if (card.getBeanType().equals(beanType)) {
				// The opponent has the bean type
				return true;
			}
		}
		// The opponent doesn't have the bean type
		return false;
	}
}