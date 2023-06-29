package com.mycompany.sketchpad;

import java.awt.*;

/**
 * Ethan Wong
 Spring 2023
 Semicircle.java
 */
public class Semicircle extends Shape {
    Semicircle(int id,int xVal, int yVal, int penVal, Color colorVal) {
        super(id,"semicircle",xVal,yVal,penVal,colorVal);
    }
    
    // Create a semicircle on the graphics.
    @Override
    public void create(Graphics g) {
        g.setColor(penColor);
        g.fillArc(xCoord-(penSize/2), yCoord-(penSize/2),penSize, penSize,180,-180);
    }
}
