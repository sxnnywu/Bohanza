package view;

// Imports
import javax.swing.*;
import model.*;

// DiscardPanel is a UI component that visually represents the discard pile in the game. It displays the top discarded card
// and two rotated card backs to create a layered effect.
@SuppressWarnings("serial")
public class DiscardPanel extends JPanel {

	// Fields
	private RotatedLabel coinIconLabel1 = new RotatedLabel(15, new ImageIcon("images/Back.png"));
	private RotatedLabel coinIconLabel2 = new RotatedLabel(30, new ImageIcon("images/Back.png"));
	private JLabel topCard = new JLabel(new ImageIcon("images/Back.png"));
	DrawDeck drawDeck;
	
	// Constructor
	public DiscardPanel (DrawDeck drawDeck) {
		// Layout
		setLayout(null);
		setOpaque(false);
		this.drawDeck = drawDeck;
		// Image layouts
		topCard.setBounds(0, 15, new ImageIcon("images/Back.png").getIconWidth(),
				new ImageIcon("images/Back.png").getIconHeight());
		add(topCard);
		
		coinIconLabel1.setBounds(0, 0, new ImageIcon("images/Back.png").getIconWidth() + 80,
				new ImageIcon("images/Back.png").getIconHeight() + 30);
		add(coinIconLabel1);
		
		coinIconLabel2.setBounds(40, 0, new ImageIcon("images/Back.png").getIconWidth() + 80,
				new ImageIcon("images/Back.png").getIconHeight() + 30);
		add(coinIconLabel2);
	}
	
	// Getters and setters
	public DrawDeck getDrawDeck() {
		return drawDeck;
	}

	// This method updates the top card icon to match the current top of the discard pile
	public void setTopCardImageIcon() {
		if (drawDeck.getDiscardCards().size() > 0) {
			topCard.setIcon(new ImageIcon(drawDeck.getDiscardCards().peek().getCardImage()));
		} else {
			topCard.setIcon(new ImageIcon("images/Back.png"));
		}
	}
}