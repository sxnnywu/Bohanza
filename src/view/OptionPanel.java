package view;

// Imports
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

// OptionPanel displays the mode selection screen, allowing the user to choose between playing with a friend or the computer.
@SuppressWarnings("serial")
public class OptionPanel extends JPanel {

	// GUI components
	JButton friendButton = new JButton("Play With Friend");
	JButton computerButton = new JButton("Play With Computer");
	
	// Constructor
	public OptionPanel() {
		// Layout
		setSize(1486, 827);
		setLayout(null);
	
		//Friend button layout --> allows user to play with friend
		friendButton.setBounds(530, 450, 250, 80);
		friendButton.setOpaque(true);
		friendButton.setBorder(new LineBorder(Color.BLACK, 3));
		friendButton.setBackground(Color.decode("#e02805"));
		friendButton.setForeground(Color.decode("#000000"));
		friendButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
		add(friendButton);
		
		//Computer button layout --> allows user to play with computer
		computerButton.setBounds(530, 550, 250, 80);
		computerButton.setOpaque(true);
		computerButton.setBorder(new LineBorder(Color.BLACK, 3));
		computerButton.setBackground(Color.decode("#e02805"));
		computerButton.setForeground(Color.decode("#000000"));
		computerButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
		add(computerButton);
		
		// Make the panel visible
		setVisible(true);
	}
	
	// This method overrides paintComponent to draw background image
	public void paintComponent(Graphics g) {
		g.drawImage(new ImageIcon("images/OptionBackground.png").getImage(), 0, 0, getWidth(), getHeight(), this);
	}

	// Getters and setters
	public JButton getFriendButton() {
		return friendButton;
	}

	public JButton getComputerButton() {
		return computerButton;
	}
}