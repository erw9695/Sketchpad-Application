package com.mycompany.sketchpad;

import java.awt.event.*;

/**
 * Ethan Wong
 * Spring 2023
 * MouseListener.java
 */
public class MouseListener implements MouseMotionListener {
    GraphicsPanel graphP;
    int count = 0;
    
    MouseListener(GraphicsPanel newPanel) {
        graphP = newPanel;
    }
    
    // When a mouse is dragged, create a new object.
    @Override
    public void mouseDragged(MouseEvent e) {
        count++;
        int xCoord = e.getX();
        int yCoord = e.getY();
        graphP.addObject(xCoord,yCoord);
        graphP.repaint();
    }
    
    // Must override, but left blank intentionally.
    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
}
