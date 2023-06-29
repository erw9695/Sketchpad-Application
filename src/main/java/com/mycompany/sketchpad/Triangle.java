package com.mycompany.sketchpad;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Ethan Wong
 * Spring 2023
 * Triangle.java
 */
public class Triangle extends Shape {
    Triangle(int id,int xVal, int yVal, int penVal,Color colorVal) {
        super(id,"triangle",xVal,yVal,penVal,colorVal);
    }
    
    // Creates a triangle in the provided graphics object.
    @Override
    public void create(Graphics g) {
        g.setColor(penColor);
        g.fillPolygon(new int[]{xCoord,xCoord-penSize,xCoord+penSize},new int[]{yCoord-penSize,yCoord+penSize,yCoord+penSize},3);
    }
}
