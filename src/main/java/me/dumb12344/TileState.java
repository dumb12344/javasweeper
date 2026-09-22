package me.dumb12344;

import java.awt.*;

public class TileState {
    public enum State {
        BLANK_TILE,
        BLANK_MINE,
        FLAGGED_TILE,
        FLAGGED_MINE,
        REVEALED_TILE,
        REVEALED_MINE
    }

    private int surroundingMines;
    private State state;
    private final boolean altColor;

    public static boolean getAltColor (int x, int y) {
        return (x % 2 == 0) ^ (y % 2 == 0);
    }

    public TileState (State state, int surroundingMines, boolean altColor) {
        this.surroundingMines = surroundingMines;
        this.state = state;
        this.altColor = altColor;
    }

    public int getSurroundingMines () {
        return this.surroundingMines;
    }

    public void setSurroundingMines (int newSurroundingMines) {
        this.surroundingMines = newSurroundingMines;
    }

    public void reveal () {
        setState(switch (getState()) {
            case BLANK_TILE -> State.REVEALED_TILE;
            case BLANK_MINE -> State.REVEALED_MINE;
            default -> getState();
        });
    }

    public void toggleFlag () {
        setState(switch (getState()) {
            case BLANK_MINE -> State.FLAGGED_MINE;
            case BLANK_TILE -> State.FLAGGED_TILE;
            case FLAGGED_MINE -> State.BLANK_MINE;
            case FLAGGED_TILE -> State.BLANK_TILE;
            default -> getState();
        });
    }

    public boolean isMine () {
        return switch (getState()) {
            case BLANK_MINE, FLAGGED_MINE, REVEALED_MINE -> true;
            default -> false;
        };
    }

    public boolean isFlagged () {
        return switch (getState()) {
            case FLAGGED_TILE , FLAGGED_MINE -> true;
            default -> false;
        };
    }

    public boolean isRevealed () {
        return switch (getState()) {
            case REVEALED_MINE, REVEALED_TILE -> true;
            default -> false;
        };
    }

    public void setState (State newState) {
        this.state = newState;
    }

    public State getState () {
        return this.state;
    }

    public Color getColor () {
        return switch(getState()) {
            case BLANK_MINE, BLANK_TILE, FLAGGED_MINE, FLAGGED_TILE -> Color.decode(this.altColor ? "#a2d149" : "#aad751");
            case REVEALED_TILE -> Color.decode(this.altColor ? "#d7b899" : "#e5c29f");
            case REVEALED_MINE -> Color.RED;
        };
    }
}
