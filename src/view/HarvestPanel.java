package view;

// Imports
import java.awt.*;
import javax.swing.*;

// HarvestPanel is a transparent panel that displays a stretched tractor image. It is used to visually represent harvesting in the game interface.
@SuppressWarnings("serial")
public class HarvestPanel extends JPanel {

    // Constructor
    public HarvestPanel() {
        // Set panel to be transparent
        setOpaque(false);
    }

    // This method is called when the panel is rendered or needs to be redrawn
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the tractor image to fill the entire panel area
        g.drawImage(new ImageIcon("images/tractor.png").getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}