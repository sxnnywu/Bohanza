package model;

// Imports
import java.util.*;

// This class represents a deck of cards used in the game. It uses a queue to manage the order of cards and provides methods to draw cards, 
// add cards, and check the deck's status.
public class Deck {
	
	// Fields
	private Queue<Card> cards;
	
	// Constructor
	public Deck() {
		cards = new LinkedList<Card>();
		for (int i = 0;i<5;i++) {
			Card card = DrawDeck.drawCard();
			addCard(card);
		}
	}

	// Getters & Setters
	public Queue<Card> getCards() {
		return cards;
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	// toString method
	@Override
	public String toString() {
		return "Deck [cards=" + cards + "]";
	}
	
	// This method removes and returns the top card from the deck
	public Card drawCard() {
		return cards.remove();
	}
	
	// This method returns true if the deck is empty
	public boolean isEmpty() {
		if(cards.size() == 0)
			return true;
		return false;
	}
	
	// This method returns the number of cards in the deck
	public int getDeckSize() {
		return cards.size();
	}
}