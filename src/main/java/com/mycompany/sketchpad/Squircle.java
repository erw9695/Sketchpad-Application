package com.mycompany.sketchpad;

import java.awt.*;

/**
 * Ethan Wong
 Spring 2023
 Squircle.java
 */
public class Squircle extends Shape {
    Squircle(int id,int xVal, int yVal, int penVal, Color colorVal) {
        super(id,"squircle",xVal,yVal,penVal,colorVal);
    }
    
    // Create a squircle on the graphics.
    @Override
    public void create(Graphics g) {
        g.setColor(penColor);
        g.fillRoundRect(xCoord-(penSize/2), yCoord-(penSize/2),penSize, penSize,30,30);
    }
}
