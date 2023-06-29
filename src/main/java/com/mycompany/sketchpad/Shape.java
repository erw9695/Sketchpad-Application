package com.mycompany.sketchpad;

import java.awt.*;

/**
 * Ethan Wong
 * Spring 2023
 * Shape.java
 */
abstract class Shape {
    String type;
    int id,xCoord, yCoord, penSize;
    Color penColor;
    Shape(int idVal, String typeVal, int xVal, int yVal, int penVal, Color colorVal) {
        id = idVal;
        type = typeVal;
        xCoord = xVal;
        yCoord = yVal;
        penSize = penVal;
        penColor = colorVal;
    }
    abstract public void create(Graphics g);
    
    // Return the details of this shape.
    public String getDetails(String username,String drawingName) {
        return "'"+id+"','"+username+"','"+drawingName+"','"+type+"',"+xCoord+","+yCoord+","+penSize+",'"+penColor.getRed()+","+penColor.getGreen()+","+penColor.getBlue()+"'";
    }
}
