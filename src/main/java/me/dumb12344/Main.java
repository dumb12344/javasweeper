package me.dumb12344;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static final JFrame frame = new JFrame("Javasweeper");
    public static Dimension size = new Dimension(1920, 1080);
    static void main () {
        DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        size.setSize(displayMode.getWidth(), displayMode.getHeight());
        Initialize.init();
        GameScreen screen = new GameScreen();
        frame.setPreferredSize(size);
        frame.setUndecorated(true);
        screen.setTileSize(size);
        screen.setPreferredSize(new Dimension(GameData.SIZE_X * GameData.TILE_SIZE, GameData.SIZE_Y * GameData.TILE_SIZE));
        frame.setLayout(new java.awt.GridBagLayout());
        frame.add(screen);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(screen.keyListener);
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
    }
}
