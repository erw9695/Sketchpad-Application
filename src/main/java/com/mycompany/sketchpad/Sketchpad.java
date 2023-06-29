package com.mycompany.sketchpad;
import com.mycompany.sketchpad.ColorScreen;
import com.mycompany.sketchpad.DownloadScreen;
import com.mycompany.sketchpad.DrawScreen;
import com.mycompany.sketchpad.HomeScreen;
import com.mycompany.sketchpad.LoadScreen;
import com.mycompany.sketchpad.MenuScreen;
import com.mycompany.sketchpad.ShareScreen;
import java.awt.*;

/**
 * Ethan Wong
 Spring 2023
 Sketchpad.java
 */
public class Sketchpad {

    // Client's username.
    static String username;
    // Windows for the application.
    static HomeScreen homeWin;
    static MenuScreen menuWin;
    static DrawScreen drawWin;
    static ColorScreen colorWin;
    static LoadScreen loadWin;
    static ShareScreen shareWin;
    static DownloadScreen downloadWin;
    // Drawing canvas.
    static GraphicsPanel canvas;
    static String server;
    
    public static void main(String[] args) {
        System.out.println("CLIENT - RUNNING"); 
        
        homeWin = new HomeScreen();
        homeWin.setVisible(true);
        
        menuWin = new MenuScreen();
        
        loadWin = new LoadScreen();
        
        shareWin = new ShareScreen();
        
        drawWin = new DrawScreen();
        
        colorWin = new ColorScreen();
        
        downloadWin = new DownloadScreen();
        
        // Create the canvas.
        canvas = new GraphicsPanel();
        canvas.addMouseMotionListener(new MouseListener(canvas));
        canvas.addMouseListener(new ClickListener(canvas));
      
        // Set the drawPanel size.
        drawWin.drawPanel.setPreferredSize(new Dimension(900,488));
        
        // Set the drawPanel layout and add the canvas (GraphicsPanel object).
        drawWin.drawPanel.setLayout(new GridLayout(1,1));
        drawWin.drawPanel.add(canvas);
    }
}
