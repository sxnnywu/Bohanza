package model;

// This class represents a player in the game. It stores the player's deck, coin count, turn status, and action progress,
// and tracks whether the player is an AI.
public class Player {

	// Fields
	Deck deck;
	int coins;
	int plantedTurn;
	int discardedTurn;
	int step = 1;
	boolean isTurn = false;
	boolean isAI = false;

	// Constructor
	public Player() {
		deck = new Deck();
		coins = 0;
		plantedTurn = 0;
		discardedTurn = 0;
	}

	// Getters and setters
	public boolean isAI() {
		return isAI;
	}

	public void setIsAI(boolean isAI) {
		this.isAI = isAI;
	}
	
	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public int getDiscardedTurn() {
		return discardedTurn;
	}

	public void setDiscardedTurn(int discardedTurn) {
		this.discardedTurn = discardedTurn;
	}

	public int getPlantedTurn() {
		return plantedTurn;
	}

	public void setPlantedTurn(int plantedTurn) {
		this.plantedTurn = plantedTurn;
	}

	public Deck getDeck() {
		return deck;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	// This method resets player state at the end of a turn
	public void resetPlayer() {
		plantedTurn = 0;
		discardedTurn = 0;
		isTurn = false;
		step = 1;
	}	
}