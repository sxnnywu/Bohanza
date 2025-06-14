package view;

// Imports
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

// HomePanel displays the main home screen of the game, including a background image and a "Play Now" button.
@SuppressWarnings("serial")
public class HomePanel extends JPanel {

	// GUI components
	JButton playButton = new JButton("Play Now");

	// Constructor
	public HomePanel() {
		// Layout
		setSize(1486, 807);
		setLayout(null);
		// Play button layout
		playButton.setBounds(1270, 690, 160, 60);
		playButton.setOpaque(true);
		playButton.setBorder(new LineBorder(Color.BLACK, 3));
		playButton.setBackground(Color.decode("#e02805"));
		playButton.setForeground(Color.decode("#000000"));
		playButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
		add(playButton);	
		// Make the panel visible
		setVisible(true);
	}
	
	// This method overrides the paintComponent method to draw the background image
	public void paintComponent(Graphics g) {
		g.drawImage(new ImageIcon("images/HomeBackground.png").getImage(), 0, 0, getWidth(), getHeight(), this);
	}

	// Getters and setters
	public JButton getPlayButton() {
		return playButton;
	}
}