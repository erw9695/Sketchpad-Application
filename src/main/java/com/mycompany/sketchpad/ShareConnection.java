package com.mycompany.sketchpad;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Ethan Wong
 * Spring 2023
 * ShareConnection.java
 */
public class ShareConnection extends Thread {
    
    String recipientName, drawingName;
    
    ShareConnection(String recipientNameVal, String drawingNameVal) {
        recipientName = recipientNameVal;
        drawingName = drawingNameVal;
    }

    @Override
    public void run() {
        try {
            
            // Create a new socket, scanner, and print stream.
            Socket sock = new Socket(Sketchpad.server,5000);
            
            Scanner sIn = new Scanner(sock.getInputStream());
            PrintStream sOut = new PrintStream(sock.getOutputStream());
            
            // Send the sender, recipient, and drawingName to the server.
            sOut.println("('"+Sketchpad.username+"','"+recipientName+"','"+drawingName+"')");
            
            // SQL Declaration
            Connection conn = null;
            Statement findDrawState = null;
            ResultSet rs = null;
            
            // After sending the data to store in the drawing table, send all of the shapes.
            try {
                String url = "jdbc:mariadb://localhost:3306/drawdb";
                conn = DriverManager.getConnection(url,"","");
                
                // Query to get all locally saved shapes.
                String findDraw = "select * from shapes where username = '"+Sketchpad.username+"' and drawingName = '"+drawingName+"'";
                findDrawState = conn.createStatement();
                rs = findDrawState.executeQuery(findDraw);
                
                // Send the shape to the server.
                while (rs.next()) {
                    String id = rs.getString("id");
                    String shapeType = rs.getString("type");
                    int xCoord = rs.getInt("xCoord");
                    int yCoord = rs.getInt("yCoord");
                    int shapeSize = rs.getInt("size");
                    String color = rs.getString("color");
                    
                    sOut.println("('"+id+"','"+Sketchpad.username+"','"+recipientName+"','"+drawingName+"','"+shapeType+"',"+xCoord+","+yCoord+","+shapeSize+",'"+color+"')");
                }
            } catch (Exception ex) {
                System.out.println("ShareConnection:"+ex.toString());
            } finally {
                // Close SQL connection.
                if (rs != null) try { rs.close(); } catch (Exception e2) {}
                if (findDrawState != null) try { findDrawState.close(); } catch (Exception e3) {}
                if (conn != null) try { conn.close(); } catch (Exception e4) {}
            }
            
            // Get the share status.
            if (sIn.hasNext()) {
                Sketchpad.shareWin.shareStatus.setText(sIn.next());
            }
            
            sock.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        } 
    }
}
