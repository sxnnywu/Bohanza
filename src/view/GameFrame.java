package view;

// Imports
import java.awt.*;
import javax.swing.*;

// GameFrame is the main window (JFrame) for the Bohnanza game. It sets the game window's size, layout, title, and includes a menu bar.
@SuppressWarnings("serial")
public class GameFrame extends JFrame {

	// Menu bar
	JMenuBar menuBar = new JMenuBar();
	// Menu items
	JMenuItem helpMenuItem = new JMenuItem("How to Play");
	JMenuItem quitMenuItem = new JMenuItem("Quit");
	
	// Constructor
    public GameFrame() {
    	// Layout
        setLayout(null); 
        setTitle("Bohnaza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(1500, 864);  
        setLocationRelativeTo(null);
        setResizable(false);
        
		// Menu bar layout
		menuBar.setBackground(Color.decode("#f7f911"));
		menuBar.setOpaque(true);
		menuBar.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#e02805")));
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		Font menuFont = new Font("Arial", Font.BOLD, 14);
		
		// Menu items layout
		// Help menu item
		helpMenuItem.setFont(menuFont);
		helpMenuItem.setBackground(Color.decode("#f7f911"));
		helpMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#e02805")));
		// Quit menu item
		quitMenuItem.setFont(menuFont);
		quitMenuItem.setBackground(Color.decode("#f7f911"));
		quitMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#e02805")));
		
		// Add items to the menu bar and set it on the frame
		menuBar.add(helpMenuItem);
		menuBar.add(quitMenuItem);
		setJMenuBar(menuBar);
		
		// Make the frame visible
		setVisible(true);
    }
    
	// Getters and setters
	public JMenuItem getHelpMenuItem() {
		return helpMenuItem;
	}

	public void setHelpMenuItem(JMenuItem helpMenuItem) {
		this.helpMenuItem = helpMenuItem;
	}

	public JMenuItem getQuitMenuItem() {
		return quitMenuItem;
	}

	public void setQuitMenuItem(JMenuItem quitMenuItem) {
		this.quitMenuItem = quitMenuItem;
	}
}