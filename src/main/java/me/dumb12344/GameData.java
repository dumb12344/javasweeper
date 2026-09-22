package me.dumb12344;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class GameData {
    public static final int SIZE_X = 10;
    public static final int SIZE_Y = 8;
    public static final int MINE_COUNT = 10;
    public static final int TILE_SIZE = 100;
    public static int remainingTiles;
    public static GameState gameState = GameState.Initializing;
    public static TileState[][] tiles = {{}};
    public static final List<Color> NUMBER_COLORS = Arrays.stream(new String[]{
        "#ffffff",
        "#1976d2",
        "#388e3c",
        "#d32f2f",
        "#7b1fa2",
        "#ff8f00",
        "#0097a7",
        "#424242",
        "#9e9e9e"
    }).map(Color::decode).toList();
    public static boolean outOfBounds (int x, int y) {
        return x < 0 || x >= GameData.SIZE_X || y < 0 || y >= GameData.SIZE_Y;
    }
}
