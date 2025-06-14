package view;

// Imports
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import model.*;

// EndPanel is the final screen displayed at the end of the game. It shows the game result, including the winner or if there was a tie,
// and provides a button to exit the game.
@SuppressWarnings("serial")
public class EndPanel extends JPanel {

	// Fields
	private JLabel endLabel;
	public static JButton exitButton = new JButton("Exit");

	// Constructor
	public EndPanel() {
		// Layout
		setSize(1486, 827);
		setLayout(null);

		// Panel Layout
		endLabel = new JLabel();
		endLabel.setFont(new Font("Cooper Black", Font.BOLD, 30));
		endLabel.setForeground(Color.black);
		endLabel.setBounds(300, 450, 500, 100);
		
		// Button layout
		exitButton.setBounds(400, 600, 250, 80);
		exitButton.setOpaque(true);
		exitButton.setBorder(new LineBorder(Color.BLACK, 3));
		exitButton.setBackground(Color.decode("#e02805"));
		exitButton.setForeground(Color.decode("#000000"));
		exitButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
		
		add(exitButton);
		add(endLabel);
	}
	
	// Getters and setters
	public JButton getExitButton() {
		return exitButton;
	}

	// This method draws the background image for the panel
	@Override
	protected void paintComponent(Graphics g) {
	    g.drawImage(new ImageIcon("images/OptionBackground.png").getImage(), 0, 0, getWidth(), getHeight(), this);
	}
	
	// This method updates the label text with winner/loser information or a tie message
	public void editText(String winner, String loser, Player winningPlayer, Player losingPlayer, boolean isTie) {
		String text;
		if (isTie) {
			// Display tie message
			text = "<html><div style='width: 350px; text-align: center;'>" + "Tie! Both players had the same amount of coins." + "</div></html>";
		} else {
			// Display winner and their coin count
			text = "<html><div style='width: 350px; text-align: center;'>" + winner + " won with "
					+ winningPlayer.getCoins() + " coins, with " + loser + " trailing with " + losingPlayer.getCoins()
					+ " coins." + "</div></html>";
		}
		// Set the label text
		endLabel.setText(text);
		// Refresh layout
		revalidate();
		// Redraw the panel
		repaint();
	}
}