package com.mycompany.sketchpad;

import java.awt.*;

/**
 * Ethan Wong
 * Spring 2023
 * Square.java
 */
public class Square extends Shape {
    Square(int id,int xVal, int yVal, int penVal, Color colorVal) {
        super(id,"square",xVal,yVal,penVal,colorVal);
    }
    
    // Create a square on the graphics.
    @Override
    public void create(Graphics g) {
        g.setColor(penColor);
        g.fillRect(xCoord-(penSize/2), yCoord-(penSize/2),penSize, penSize);
    }
}
