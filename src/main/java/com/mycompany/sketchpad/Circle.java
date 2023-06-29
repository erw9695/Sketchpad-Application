package com.mycompany.sketchpad;

import java.awt.*;

/**
 * Ethan Wong
 * Spring 2023
 * Circle.java
 */
public class Circle extends Shape {
    Circle(int id,int xVal, int yVal, int penVal,Color colorVal) {
        super(id,"circle",xVal,yVal,penVal,colorVal);
    }
    
    // Creates a circle in the provided graphics object.
    @Override
    public void create(Graphics g) {
        g.setColor(penColor);
        g.fillOval(xCoord-(penSize/2), yCoord-(penSize/2), penSize, penSize);
    }
}
