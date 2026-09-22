package me.dumb12344;

import java.util.Random;

public class Initialize {
    public static void init () {
        GameData.gameState = GameState.Initializing;
        initializeTiles();
        initializeMines();
        initializeSurroundingMines();
        GameData.gameState = GameState.Playing;
    }

    public static void init (int clickX, int clickY) {
        GameData.gameState = GameState.Initializing;
        initializeTiles();
        initializeMines(clickX, clickY);
        initializeSurroundingMines();
        GameData.gameState = GameState.Playing;
    }

    private static void initializeTiles () {
        GameData.tiles = new TileState[GameData.SIZE_X][GameData.SIZE_Y];
        for (int x = 0; x < GameData.SIZE_X; x++) {
            GameData.tiles[x] = new TileState[GameData.SIZE_Y];
            for (int y = 0; y < GameData.SIZE_Y; y++) {
                GameData.tiles[x][y] = new TileState(TileState.State.BLANK_TILE, 0, TileState.getAltColor(x, y));
            }
        }
    }

    private static void initializeMines () {
        initializeMines(0, 0);
    }

    private static void initializeMines (int clickX, int clickY) {
        GameData.remainingTiles = (GameData.SIZE_X * GameData.SIZE_Y) - GameData.MINE_COUNT;
        int remainingMines = GameData.MINE_COUNT;
        Random rand = new Random();
        while (remainingMines > 0) {
            int mineX = rand.nextInt(GameData.SIZE_X);
            int mineY = rand.nextInt(GameData.SIZE_Y);
            if (
                    !GameData.tiles[mineX][mineY].getState().equals(TileState.State.BLANK_MINE) &&
                    !((clickX - 1 <= mineX && mineX <= clickX + 1) && (clickY - 1 <= mineY && mineY <= clickY + 1))
            ) {
                GameData.tiles[mineX][mineY].setState(TileState.State.BLANK_MINE);
                remainingMines--;
            }
        }
    }

    private static void initializeSurroundingMines () {
        int mineCount;
        for (int tileX = 0; tileX < GameData.SIZE_X; tileX++) { for (int tileY = 0; tileY < GameData.SIZE_Y; tileY++) {
            mineCount = 0;
            for (int dx = -1; dx <= 1; dx++) { for (int dy = -1; dy <= 1; dy++) {
                int absoluteX = tileX + dx;
                int absoluteY = tileY + dy;
                if (GameData.outOfBounds(absoluteX, absoluteY)) continue;
                if (GameData.tiles[absoluteX][absoluteY].isMine()) mineCount++;
            }}
            GameData.tiles[tileX][tileY].setSurroundingMines(mineCount);
        }}
    }
}
