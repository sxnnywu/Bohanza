package controller;

// Imports
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import model.*;

// GameController manages the core logic of the game, including initializing the deck, creating cards from a file, setting up players, 
// and handling game-end conditions.
public class GameController {

	// Fields
	private Stack<Card> cards;
	private static Player player1;
	private static Player player2;
	private static DrawDeck drawDeck;

	// Constructor
	public GameController() {
		// Initializes the deck and creates all cards
		cards = new Stack<>();
		createAllCards();
		drawDeck = new DrawDeck(cards);
	}

	// This method ends the game and determines the winner
	public static void endGame() {
		String winner = null;
		String loser = null;
		Player winningPlayer = null;
		Player losingPlayer = null;
		// Compare players' coins to find the winner
		if (player1.getCoins() > player2.getCoins()) {
			winner = "Player1";
			loser = "Player 2";
			winningPlayer = player1;
			losingPlayer = player2;
			ViewController.switchToEndPanel(winner, loser, winningPlayer, losingPlayer, false);
		} else if (player1.getCoins() == player2.getCoins()) {
			// It's a tie
			ViewController.switchToEndPanel(winner, loser, winningPlayer, losingPlayer, true);
		} else {
			winner = "Player2";
			loser = "Player1";
			winningPlayer = player2;
			losingPlayer = player1;
			ViewController.switchToEndPanel(winner, loser, winningPlayer, losingPlayer, false);
		}
	}

	// This method reads card data from a file and creates card objects
	public void createAllCards() {
		try (BufferedReader br = new BufferedReader(new FileReader("Cards.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				// Extract card name and quantity
				String name = parts[0];
				int quantity = Integer.parseInt(parts[1]);
				// Extract selling rates
				int[] sellingRates = new int[4];
				for (int i = 0; i < 4; i++) {
					sellingRates[i] = Integer.parseInt(parts[i + 2]);
				}
				// Load image for the card
				String imagePath = parts[parts.length - 1];
				Image image = new ImageIcon(imagePath).getImage();
				// Create multiple copies of the card based on quantity
				for (int i = 0; i < quantity; i++) {
					cards.push(new Card(name, sellingRates, image));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Shuffle the deck after loading all cards
		Collections.shuffle(cards);
	}

	// Getters and setters
	public static DrawDeck getDrawDeck() {
		return drawDeck;
	}

	public static Player getPlayer1() {
		return player1;
	}

	public static Player getPlayer2() {
		return player2;
	}

	public void setPlayer1(Player player1) {
		GameController.player1 = player1;
	}

	public void setPlayer2(Player player2) {
		GameController.player2 = player2;
	}
}