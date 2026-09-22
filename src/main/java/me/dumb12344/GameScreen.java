package me.dumb12344;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static me.dumb12344.GameData.TILE_SIZE;
import static me.dumb12344.GameData.gameState;
import static me.dumb12344.GameData.outOfBounds;
import static me.dumb12344.GameData.tiles;
import static me.dumb12344.GameManager.forEachTile;

public class GameScreen extends JPanel {
    Font google_sans;
    Image flag_icon;

    public GameScreen () {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent event) {
                int tileX = (int) Math.floor((double) event.getX() / TILE_SIZE);
                int tileY = (int) Math.floor((double) event.getY() / TILE_SIZE);
                if (outOfBounds(tileX, tileY)) return;
                if (event.getButton() == MouseEvent.BUTTON1) {
                    GameManager.revealTile(tileX, tileY);
                    paintComponent(getGraphics());
                }
                else if (event.getButton() == MouseEvent.BUTTON3) {
                    GameManager.flagTile(tileX, tileY);
                    paintComponent(getGraphics());
                }
            }
        });

        keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyChar() == 'm') {
                    forEachTile((Integer x, Integer y) -> {
                        if (!tiles[x][y].isMine()) GameManager.revealTile(x, y, false, false);
                    });
                }

                if (event.getKeyChar() == 'r') {
                    GameManager.reset();
                }

                paintComponent(getGraphics());
            }
        };

        try {
            google_sans = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/google_sans.otf"));
            flag_icon = ImageIO.read(new File("src/main/resources/flag_icon.png"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final KeyListener keyListener;

    // https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
    public static void drawStringCentered(String text, Rectangle rect, Graphics g, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    @Override
    protected void paintComponent (Graphics graphics) {
        super.paintComponent(graphics);
        drawRevealed(graphics);
        drawOutline(graphics);
        drawUnrevealed(graphics);
        drawExtras(graphics);
    }

    public static void drawRevealed (Graphics graphics) {
        forEachTile((Integer tileX, Integer tileY) -> {
            TileState tile = GameData.tiles[tileX][tileY];
            if (!tile.isRevealed()) return;
//            graphics.setColor(new Color(20 * tileX, 20 * tileY, 127));
            graphics.setColor(tile.getColor());
            graphics.fillRect(tileX * TILE_SIZE, tileY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        });
    }

    public static void drawOutline (Graphics graphics) {
        forEachTile((Integer tileX, Integer tileY) -> {
            TileState tile = GameData.tiles[tileX][tileY];
            if (tile.isRevealed()) return;
            graphics.setColor(Color.decode("#87af3a"));
            double expanded = TILE_SIZE / 7.5;
            graphics.fillRect((int) (tileX * TILE_SIZE - expanded / 2), (int) (tileY * TILE_SIZE - expanded / 2), (int) (TILE_SIZE + expanded), (int) (TILE_SIZE + expanded));
        });
    }

    public static void drawUnrevealed (Graphics graphics) {
        forEachTile((Integer tileX, Integer tileY) -> {
            TileState tile = GameData.tiles[tileX][tileY];
            if (tile.isRevealed()) return;
            graphics.setColor(tile.getColor());
            graphics.fillRect(tileX * TILE_SIZE, tileY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        });
    }

    public void drawExtras (Graphics graphics) {
        Font font = google_sans.deriveFont((float) TILE_SIZE);
        graphics.setFont(font);
        Font loopFont = font;
        forEachTile((Integer tileX, Integer tileY) -> {
            TileState tile = GameData.tiles[tileX][tileY];
            if (tile.isRevealed() && tile.getSurroundingMines() > 0 && !tile.isMine()) {
                graphics.setColor(GameData.NUMBER_COLORS.get(tile.getSurroundingMines()));
                //                    drawStringCentered(String.valueOf(tile.getSurroundingMines()), (int) ((tileX + 0.5) * TILE_SIZE), (int) ((tileY + 0.5) * TILE_SIZE), graphics, TILE_SIZE);
                drawStringCentered(String.valueOf(tile.getSurroundingMines()), new Rectangle(tileX * TILE_SIZE, tileY * TILE_SIZE, TILE_SIZE, TILE_SIZE), graphics, loopFont);
            }
            if (tile.isFlagged()) {
                graphics.drawImage(flag_icon, tileX * TILE_SIZE, tileY * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
        });
        float fontSize = TILE_SIZE * 2.0F;
        font = google_sans.deriveFont(fontSize);
        graphics.setFont(font);
        if (gameState == GameState.Lose) {
            graphics.setColor(Color.RED);
            drawStringCentered("You lose!", new Rectangle(0, 0, getWidth(), getHeight()), graphics, font);
        }
        if (gameState == GameState.Win) {
            graphics.setColor(Color.decode("#885500"));
            drawStringCentered("You win!", new Rectangle(0, 0, getWidth(), getHeight()), graphics, font);
        }
    }
}
