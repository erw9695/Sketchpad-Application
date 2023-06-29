package com.mycompany.sketchpad;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

/**
 * Ethan Wong
 * Spring 2023
 * PendingConnection.java
 */
public class PendingConnection extends Thread {
    Scanner sIn;
    PrintStream sOut;
    DefaultTableModel tabModel;
    
    @Override
    public void run() {
        try {
            // Create a new socket, scanner, and print stream.
            Socket sock = new Socket(Sketchpad.server,3000);
            
            // Create a new table model and reset the table (table might have items already on it if the window was opened before).
            tabModel = (DefaultTableModel) Sketchpad.downloadWin.pendingTable.getModel();
            tabModel.setRowCount(0);
            
            sIn = new Scanner(sock.getInputStream());
            sOut = new PrintStream(sock.getOutputStream());
            
            sOut.println(Sketchpad.username);
            
            // Get all drawings waiting to be shared with this user and add them to the table for viewing.
            while (sIn.hasNext()) {
                String drawingName = sIn.next();
                String sender = sIn.next();
                
                tabModel.addRow(new Object[]{drawingName, sender});
            }
            
        } catch (IOException e) {
            System.out.println(e.toString());
        } 
    }
}
