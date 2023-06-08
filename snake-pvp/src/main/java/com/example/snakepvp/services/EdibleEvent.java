package com.example.snakepvp.services;

import com.example.snakepvp.core.Edible;

public class EdibleEvent {
    private final Edible edible;
    private final int row;
    private final int col;
    private final int gameId;

    EdibleEvent(Edible edible, int row, int col, int gameId) {
        this.edible = edible;
        this.row = col;
        this.col = row;
        this.gameId = gameId;
    }

    public Edible getEdible() {
        return edible;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getGameId() {
        return gameId;
    }
}
