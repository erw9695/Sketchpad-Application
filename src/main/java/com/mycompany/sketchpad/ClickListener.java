package com.mycompany.sketchpad;

import java.awt.event.*;

/**
 * Ethan Wong
 * Spring 2023
 * ClickListener.java
 */
public class ClickListener extends MouseAdapter {
    GraphicsPanel graphP;
    
    ClickListener(GraphicsPanel newPanel) {
        graphP = newPanel;
    }
    
    // Add a shape when the mouse is clicked.
    @Override
    public void mouseClicked(MouseEvent e) {
        int xCoord = e.getX();
        int yCoord = e.getY();
        graphP.addObject(xCoord,yCoord);
        graphP.repaint();
    }
}
