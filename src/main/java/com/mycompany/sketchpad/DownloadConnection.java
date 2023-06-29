package com.mycompany.sketchpad;

import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

/**
 * Ethan Wong
 * Spring 2023
 * DownloadConnection.java
 */
public class DownloadConnection extends Thread {
    Scanner sIn;
    PrintStream sOut;
    DefaultTableModel tabModel;
    Boolean download;
    
    DownloadConnection(Boolean downloadVal) {
        download = downloadVal;
    }
    
    // Method to download the selected drawing from the table.
    public void downloadDrawing() {
        // Figure out what drawing was selected from the table.
        int row = Sketchpad.downloadWin.pendingTable.getSelectedRow();
        String drawingSel = (String) Sketchpad.downloadWin.pendingTable.getValueAt(row, 0);
        String senderSel = (String) Sketchpad.downloadWin.pendingTable.getValueAt(row,1);
        
        // Send the drawing we selected to the server as well as the type of action we want to perform.
        sOut.println(drawingSel);
        sOut.println(senderSel);
        sOut.println("download");
        
        // SQL Declarations
        Statement newDrawingState = null;
        Statement newShapeState = null;
        Connection conn = null;
        
        try {
            // Connect to the local database.
            String url = "jdbc:mariadb://localhost:3306/drawdb";
            conn = DriverManager.getConnection(url,"","");
            
            // Save the drawing to the drawing table.
            String newDrawingQuery = "insert into drawings values ('"+Sketchpad.username+"','"+drawingSel+"')";
            newDrawingState = conn.createStatement();
            newDrawingState.execute(newDrawingQuery);
            
            newShapeState = conn.createStatement();
            
            // Get the shapes from the server.
            while (sIn.hasNext()) {
                // newShape is pre-formatted to insert into the shapes table.
                String newShape = sIn.next();
                String newShapeQuery = "insert into shapes values "+newShape;
                newShapeState.execute(newShapeQuery);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            // Close SQL connection.
            if (newDrawingState != null) try { newDrawingState.close(); } catch (Exception e2) {}
            if (newShapeState != null) try { newShapeState.close(); } catch (Exception e3) {}
            if (conn != null) try { conn.close(); } catch (Exception e4) {}
        }
        
        // Remove the drawing we downloaded from the table of pending drawings shared.
        tabModel.removeRow(row);
        Sketchpad.downloadWin.statusLabel.setText("Status: Successfully downloaded drawing.");
    }
    
    // Method to reject a drawing shared with the user.
    public void deleteDrawing() {
        // Get the drawing selected from the table.
        int row = Sketchpad.downloadWin.pendingTable.getSelectedRow();
        String drawingSel = (String) Sketchpad.downloadWin.pendingTable.getValueAt(row, 0);
        String senderSel = (String) Sketchpad.downloadWin.pendingTable.getValueAt(row,1);
        
        // Send the drawing and action type to the server.
        sOut.println(drawingSel);
        sOut.println(senderSel);
        sOut.println("delete");
        
        // Remove the drawing we deleted from the table of pending drawings shared.
        tabModel.removeRow(row);
        Sketchpad.downloadWin.statusLabel.setText("Status: Successfully deleted drawing.");
    }
  
    @Override
    public void run() {
        try {
            // Create a new socket, scanner, and print stream.
            Socket sock = new Socket(Sketchpad.server,4000);
            sIn = new Scanner(sock.getInputStream());
            sOut = new PrintStream(sock.getOutputStream());
            
            // Get the default table model for the pending table, used to get selections by the user.
            tabModel = (DefaultTableModel) Sketchpad.downloadWin.pendingTable.getModel();
            
            // Send the username to the server.
            sOut.println(Sketchpad.username);
            
            // Determine the action depending on the request.
            if (download) {
                this.downloadDrawing();
            } else {
                this.deleteDrawing();
            }
            
            sock.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
