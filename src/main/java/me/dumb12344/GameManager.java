package me.dumb12344;

import java.util.function.BiConsumer;

import static me.dumb12344.GameData.SIZE_X;
import static me.dumb12344.GameData.SIZE_Y;
import static me.dumb12344.GameData.gameState;
import static me.dumb12344.GameData.remainingTiles;
import static me.dumb12344.GameData.tiles;

public class GameManager {
    public static void reset () {
        Initialize.init();
    }

    public static void forEachTile (BiConsumer<Integer, Integer> consumer) {
        for (int tileX = 0; tileX < GameData.tiles.length; tileX++) {
            for (int tileY = 0; tileY < GameData.tiles[tileX].length; tileY++) {
                consumer.accept(tileX, tileY);
            }
        }
    }

    public static void revealTile (int x, int y, boolean force, boolean allowReset) {
        if (!gameState.equals(GameState.Playing) && !force) {
            if (allowReset) reset();
            return;
        }
        if (tiles[x][y].isRevealed() || tiles[x][y].isFlagged()) return;
        tiles[x][y].reveal();
        if (!tiles[x][y].isMine()) remainingTiles--;
        if (remainingTiles <= 0) {
            gameState = GameState.Win;
            return;
        }
        if (tiles[x][y].isMine()) {
            gameState = GameState.Lose;
            forEachTile((Integer tileX, Integer tileY) -> {
                if (tiles[tileX][tileY].isMine()) revealTile(tileX, tileY, true, false);
            });
            return;
        }
        if (tiles[x][y].getSurroundingMines() == 0) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int absoluteX = x + dx;
                    int absoluteY = y + dy;
                    if (GameData.outOfBounds(absoluteX, absoluteY)) continue;
                    revealTile(absoluteX, absoluteY);
                }
            }
        }
    }

    public static void revealTile(int x, int y) {
        revealTile(x, y, false, true);
    }

    public static void flagTile (int x, int y) {
        if (!gameState.equals(GameState.Playing)) return;
        tiles[x][y].toggleFlag();
    }
}
