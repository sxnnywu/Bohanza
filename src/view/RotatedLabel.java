package view;

// Imports
import java.awt.*;
import javax.swing.*;

/*
 * Source: 
 * https://www.daniweb.com/programming/software-development/threads/390060/rotate-jlabel-or-image-in-label
 * Developed by anonymous users on Java forums. Modified by Tristan to support image rotation as well.
 */

// RotatedLabel is a custom JLabel that allows you to rotate text or icons by a specified angle
@SuppressWarnings("serial")
public class RotatedLabel extends JLabel {
    
	// Fields
	private double angle;

	// Constructor for rotating text
    public RotatedLabel(String text, double angle) {
        super(text);
        this.angle = angle;
        // Make label background transparent
        setOpaque(false);
    }
    
    // Constructor for rotating an icon/image
    public RotatedLabel(double angle, ImageIcon icon) {
        super(icon);
        this.angle = angle;
        // Make label background transparent
        setOpaque(false);
    }

    // This method overrides paintComponent to handle custom drawing and rotation
    @Override
    protected void paintComponent(Graphics g) {
    	// Create a Graphics2D object for advanced drawing
    	Graphics2D g2 = (Graphics2D) g.create();
    	// Calculate the center of the label
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        // Apply rotation around the center	
        g2.rotate(Math.toRadians(angle), x, y);
        // Draw icon if one is set
        Icon icon = getIcon();
        if (icon != null) {
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            int iconX = x - iconWidth / 2;
            int iconY = y - iconHeight / 2;
            icon.paintIcon(this, g2, iconX, iconY);
        }
        // Draw text if present
        String text = getText();
        if (text != null && !text.isEmpty()) {
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();
            g2.setColor(getForeground());
            g2.drawString(text, x - textWidth / 2, y + textHeight / 2 - 4);
        }
        // Dispose the graphics object to free resources
        g2.dispose();
    }
}