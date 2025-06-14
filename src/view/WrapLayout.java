package view;

// Imports
import java.awt.*;

/*
 * Source: https://tips4java.wordpress.com/2008/11/06/wrap-layout/
 * Written by Rob Camick. Used and slightly adapted by Tristan for this project.
 */

// WrapLayout is a custom layout manager that extends FlowLayout to allow components to wrap to the next line if they exceed the width of the container
@SuppressWarnings("serial")
public class WrapLayout extends FlowLayout {

	// Constructor
    public WrapLayout() {
        super();
    }

    // Constructor with alignment parameter
    public WrapLayout(int align) {
        super(align);
    }

    // Constructor with alignment and custom horizontal/vertical gaps
    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    // This method returns preferred size of the container, accounting for wrapped components
    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    // This method returns minimum size of the container, accounting for wrapped components
    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension minimum = layoutSize(target, false);
        minimum.width -= (getHgap() + 1);
        return minimum;
    }

    // This method calculates the layout size based on whether preferred or minimum sizes are requested
    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getSize().width;
            // If target width is 0 (not yet displayed), allow infinite width to prevent wrapping
            if (targetWidth == 0) {
                targetWidth = Integer.MAX_VALUE;
            }
            // Account for container insets and horizontal gaps
            Insets insets = target.getInsets();
            int maxWidth = targetWidth - (insets.left + insets.right + getHgap() * 2);
            // Final size to return
            Dimension dim = new Dimension(0, 0);
            // Current row width
            int rowWidth = 0;
            // Current row height
            int rowHeight = 0;
            int nmembers = target.getComponentCount();
            // Loop through all components
            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);
                if (m.isVisible()) {
                    Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                    // If the component doesn't fit in current row, move to next row
                    if (rowWidth + d.width > maxWidth) {
                        dim.width = Math.max(dim.width, rowWidth);
                        dim.height += rowHeight;
                        rowWidth = 0;
                        rowHeight = 0;
                    }
                    // Add component to current row
                    rowWidth += d.width + getHgap();
                    rowHeight = Math.max(rowHeight, d.height);
                }
            }
            // Add last row size
            dim.width = Math.max(dim.width, rowWidth);
            dim.height += rowHeight;
            // Add insets and vertical gaps to total size
            dim.width += insets.left + insets.right + getHgap() * 2;
            dim.height += insets.top + insets.bottom + getVgap() * 2;
            return dim;
        }
    }
}