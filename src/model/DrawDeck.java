package model;

// Imports
import java.util.*;
import controller.*;

// This class represents the main draw and discard decks used in the game. It manages drawing cards from the draw pile, discarding cards, and ending
// the game when the draw pile runs out.
public class DrawDeck {
	
	// Fields
	private static Stack<Card> drawCards;
	private Stack<Card> discardCards;
	
	// Constructor
	public DrawDeck(Stack<Card> cards) {
		drawCards = cards;
		discardCards = new Stack<>();
	}
	
	// This method adds a card to the discard pile
	public void discardCard(Card cardPanel) {
		discardCards.add(cardPanel);
	}
	
	// This method returns the discard pile
	public Stack<Card> getDiscardCards(){
		return discardCards;
	}

	// This method draws a card from the draw deck; ends game if it's the last card
	public static Card drawCard() {
		if (drawCards.size() == 1) {
			GameController.endGame();
		}
		return drawCards.pop();
	}
	
	// This method returns the current draw deck
	public Stack<Card> getCards() {
		return drawCards;
	}
}