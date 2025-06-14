package view;

// Imports
import java.awt.*;
import java.util.*;
import javax.swing.*;
import model.*;

// DrawPanel is a scrollable UI panel that displays drawn cards. It uses a custom wrapping layout to organize cards in rows,
// and provides an inventory-like view for the player's drawn cards.
@SuppressWarnings("serial")
public class DrawPanel extends JPanel {

	// Fields
	JScrollPane inventoryScroll;
	JPanel inventoryPanel;
	ArrayList<Card> cards = new ArrayList<Card>();
	
	// Constructor
	public DrawPanel (DrawDeck drawDeck) {
		// Layout
		setLayout(null);
		setOpaque(true);
		
		// Panel layouts
		inventoryPanel = new JPanel();
		inventoryPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 5, 5));
		inventoryPanel.setOpaque(false);
		setPreferredSize(new Dimension(510, (int) Math.ceil(cards.size() / 5.0) * 157));
		 
		// Scroll setup
		inventoryScroll = new JScrollPane(inventoryPanel);
		inventoryScroll.setBounds(0, 0, 510, 167);
		inventoryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inventoryScroll.getVerticalScrollBar().setUnitIncrement(10);
		inventoryScroll.setHorizontalScrollBar(null);
		add(inventoryScroll);
	}
	
	// Getters and setters
	public ArrayList<Card> getCards(){
		return cards;
	}

	public JPanel getDrawInventory() {
		return inventoryPanel;
	}
}