package me.dumb12344;

import javax.swing.*;

public class Main {
    public static final JFrame frame = new JFrame("Javasweeper");
    static void main () {
        Initialize.init();
        GameScreen screen = new GameScreen();
        screen.setBorder(null);
        frame.add(screen);
        frame.pack();
        frame.setSize(1920, 1080);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(screen.keyListener);
    }
}
