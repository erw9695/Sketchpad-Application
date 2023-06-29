package com.mycompany.sketchpad;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import java.text.*;

/**
 * Ethan Wong
 * Spring 2023
 * GraphicsPanel.java
 */
public class GraphicsPanel extends JPanel {
    // List of all Shape objects in the drawing.
    ArrayList<Shape> shapes;
    // Configuration
    int penSize = 5;
    Color penColor = Color.black;
    String penShape = "Circle";
    
    GraphicsPanel() {       
        shapes = new ArrayList();
        
        // Add a listener to detect when the pen size has been changed.
        Sketchpad.drawWin.penSize.addChangeListener(new ChangeListener() 
        {
            @Override
            public void stateChanged(ChangeEvent event) {
                penSize = Sketchpad.drawWin.penSize.getValue();
            }
        });
    }
    
    
    // Create a new object on the canvas.
    public void addObject(int xCoord, int yCoord) {
        if (penShape.equals("Circle")) {
            shapes.add(new Circle(shapes.size(),xCoord,yCoord,penSize,penColor));
        } else if (penShape.equals("Square")) {
            shapes.add(new Square(shapes.size(),xCoord,yCoord,penSize,penColor));
        } else if (penShape.equals("Triangle")) { 
            shapes.add(new Triangle(shapes.size(),xCoord,yCoord,penSize,penColor));
        } else if (penShape.equals("Semicircle")) { 
            shapes.add(new Semicircle(shapes.size(),xCoord,yCoord,penSize,penColor));
        } else if (penShape.equals("Squircle")) {
            shapes.add(new Squircle(shapes.size(),xCoord,yCoord,penSize,penColor));
        }
    }
    
    // Go through all shapes and create them.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape x : shapes) {
            x.create(g);
        }
    }
    
    public void loadDrawing(String drawingName) {
        // SQL Declarations
        Connection conn = null;
        Statement findDrawState = null;
        ResultSet rs = null;
        
        try {
            String url = "jdbc:mariadb://localhost:3306/drawdb";
            conn = DriverManager.getConnection(url,"",""); 
            
            // Query to get all locally saved drawings.
            String findDraw = "select * from shapes where username = '"+Sketchpad.username+"' and drawingName = '"+drawingName+"'";
            
            findDrawState = conn.createStatement();
            
            rs = findDrawState.executeQuery(findDraw);
            
            // Go through all saved shapes and load them.
            while (rs.next()) {
                String id = rs.getString("id");
                String shapeType = rs.getString("type");
                int xCoord = rs.getInt("xCoord");
                int yCoord = rs.getInt("yCoord");
                int shapeSize = rs.getInt("size");
                String color = rs.getString("color");
                
                // Split an RGB string of the format "R,G,B" and create a Color object.
                String[] arrOfColor = color.split(",",3);
                Color shapeColor = new Color(Integer.parseInt(arrOfColor[0]),Integer.parseInt(arrOfColor[1]),Integer.parseInt(arrOfColor[2])); 
                
                if (shapeType.equals("circle")) {
                    shapes.add(new Circle(Integer.parseInt(id),xCoord,yCoord,shapeSize,shapeColor));
                } else if (shapeType.equals("square")) {
                    shapes.add(new Square(Integer.parseInt(id),xCoord,yCoord,shapeSize,shapeColor));
                } else if (shapeType.equals("triangle")) { 
                    shapes.add(new Triangle(Integer.parseInt(id),xCoord,yCoord,shapeSize,shapeColor));
                } else if (shapeType.equals("semicircle")) {
                    shapes.add(new Semicircle(Integer.parseInt(id),xCoord,yCoord,shapeSize,shapeColor));
                } else if (shapeType.equals("squircle")) {
                    shapes.add(new Squircle(Integer.parseInt(id),xCoord,yCoord,shapeSize,shapeColor));    
                }
            }
            
            // Load the drawing name.
            Sketchpad.drawWin.drawingName.setText(drawingName);
            
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            // Close SQL connection.
            if (rs != null) try { rs.close(); } catch (Exception e2) {}
            if (findDrawState != null) try { findDrawState.close(); } catch (Exception e3) {}
            if (conn != null) try { conn.close(); } catch (Exception e4) {}
        }
    }
    
    public void saveDrawing(String drawingName) {
        // SQL Declarations
        Connection conn = null;
        Statement doesDrawState = null;
        Statement newDrawState = null;
        Statement newShapeState = null;
        Statement doesShapeState = null;
        ResultSet doesDrawSet = null;
        ResultSet doesShapeSet = null;
        
        try {
            String url = "jdbc:mariadb://localhost:3306/drawdb";
            conn = DriverManager.getConnection(url,"",""); 
            
            // Check if the drawing already exists.
            String doesDrawExist = "select * from drawings where username = '"+Sketchpad.username+"' and drawingName = '"+drawingName+"'";
            doesDrawState = conn.createStatement();
            doesDrawSet = doesDrawState.executeQuery(doesDrawExist);
            
            // If it doesn't exist yet.
            if (!(doesDrawSet.next())) {
                // Query to create a new drawing.
                String newDrawing = "insert into drawings values ('"+Sketchpad.username+"','"+drawingName+"');";
                newDrawState = conn.createStatement();
                newDrawState.executeQuery(newDrawing);
            } 
            
            newShapeState = conn.createStatement();
            
            // Save all shapes that don't yet exist.
            for (Shape x : shapes) {
                // Query if the shape exists yet.
                String doesShapeExist = "select * from shapes where id = '"+x.id+"' and username = '"+Sketchpad.username+"' and drawingName = '"+drawingName+"'";
                doesShapeState = conn.createStatement();
                doesShapeSet = doesShapeState.executeQuery(doesShapeExist);
                
                // If the shape doesn't exist yet ...
                if (!(doesShapeSet.next())) {
                    // Query to inset a new shape.
                    String newShape = "insert into shapes values ("+x.getDetails(Sketchpad.username,drawingName)+");";
                    newShapeState.executeQuery(newShape);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (doesDrawSet != null) try { doesDrawSet.close(); } catch (Exception e2) {}
            if (doesShapeSet != null) try { doesShapeSet.close(); } catch (Exception e3) {}
            if (doesDrawState != null) try { doesDrawState.close(); } catch (Exception e4) {}
            if (newDrawState != null) try { newDrawState.close(); } catch (Exception e5) {}
            if (newShapeState != null) try { newShapeState.close(); } catch (Exception e5) {}
            if (doesShapeState != null) try { doesShapeState.close(); } catch (Exception e6) {}
            if (conn != null) try { conn.close(); } catch (Exception e7) {}
        }
        
        System.out.println("Save success.");
        Sketchpad.drawWin.saveStatus.setText("Last Saved: "+(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date())));
    }
}
