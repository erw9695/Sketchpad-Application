package com.mycompany.sketchpad;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Ethan Wong
 * Spring 2023
 * ServerConnection.java
 */
public class ServerConnection extends Thread {
    String server,username,password,signInAttempt;
    
    ServerConnection(String serverText, String userText, String passText, String signInBool) {
        server = serverText;
        username = userText;
        password = passText;
        signInAttempt = signInBool;
    }
    
    @Override
    public void run() {
        try {
            // Create a new socket, scanner, and print stream.
            Socket sock = new Socket(server,5190);
            
            Scanner sIn = new Scanner(sock.getInputStream());
            PrintStream sOut = new PrintStream(sock.getOutputStream());
            
            // Send the username, password, and attempt type to the server.
            sOut.println(username);
            sOut.println(password);
            sOut.println(signInAttempt);
            
            // Get the status of the connection attempt.
            String authenticationStatus = sIn.nextLine();
            
            // Success, close the home window and open the menu window.
            if (authenticationStatus.equals("SUCCESS")) {
                System.out.println("USER AUTHENTICATED"); 
                Sketchpad.server = server;
                Sketchpad.homeWin.setVisible(false);
                Sketchpad.menuWin.setVisible(true);
                Sketchpad.username = username;
                
            } else { // Failure, set the status label in the home window indicating failure.
                System.out.println("USER AUTHENTICATION FAILURE"); // Delete.
                Sketchpad.homeWin.statusLabel.setText("Status: Invalid server, username, or password.");
            }
            
            sock.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        } 
    }
}
