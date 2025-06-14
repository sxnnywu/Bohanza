/*
 * Names & Responsibilities: 
 * - Sunny Wu (33%) --> Main Controller, Game Controller, Turn Interface, Deck Model, DrawDeck Model, Player Model, Card Model, BeanField Model
 * - Anahat Chhatwal (33%) --> Main Controller, View Controller, AI Controller, Game Frame, Home Panel, Option Panel, Bohnanza Application
 * - Tristan Pinzari (33%) --> Game Panel, Discard Panel, Draw Panel, End Panel, Field Panel, Harvest Panel, Player Hand Panel, Rotated Label, Wrap Layout
 * 
 * Date: Wednesday, April 30, 2025
 * 
 * Course Code: ICS4U1-03
 * Teacher: Mr. Fernandes
 * 
 * Title: Bohnanza - "To Bean or Not to Bean?" - GROUP 1
 * 
 * Description:
 * This project is a two-player (Player vs. Player or Player vs. AI) digital recreation of the popular German card game; Bohnanza. 
 * Players take turns planting, harvesting, and trading beans with the goal of collecting the most coins.
 * The game features a custom-built GUI, interactive controls, and a rule-based AI opponent.
 * 
 * Features:
 * - Fully interactive turn-based gameplay with GUI drag-and-drop mechanics
 * - Player vs AI mode with basic trade logic and field management
 * - Visual representations of fields, hands, harvests, and coin counts
 * - Step-by-step turn system with visual feedback and progress indicators
 * - Dynamic card deck and discard pile logic
 * - Game end condition detection and winner announcement
 * - Home screen to start the game
 * - Option screen to choose to play with another player or AI
 * - Menu bar to provide users with instructions for the game and a quit option
 * 
 * Major Skills:
 * - Java object-oriented programming (OOP) and MVC format
 * - GUI development using Java Swing and custom layout management
 * - Game logic implementation including turn flow, AI decisions, and card interactions
 * 
 * Areas of Concerns:
 * - Additional tips or tutorial pop-ups could improve user understanding of game mechanics
 * - The current AI lacks advanced decision-making and trading strategies, which could be enhanced for a more challenging experience
 * - Adding sound effects or music would improve player engagement and overall atmosphere
 * - Implementing a leaderboard feature could encourage competition and replayability
 */ 

package application;

// Imports
import controller.MainController;

// This class creates an instance of MainController, which sets up the game window and starts the application
public class BohnanzaApplication {
    public static void main(String[] args) {
    	new MainController();
    }
}