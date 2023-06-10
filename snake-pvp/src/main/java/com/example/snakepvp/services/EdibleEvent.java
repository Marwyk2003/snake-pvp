package com.example.snakepvp.services;

import com.example.snakepvp.core.Edible;

public class EdibleEvent {
    private final Edible newEdible;
    private final Edible oldEdible;
    private final int newRow;
    private final int newCol;
    private final int gameId;

    public EdibleEvent(Edible newEdible, Edible oldEdible, int newRow, int newCol, int gameId) {
        this.newEdible = newEdible;
        this.oldEdible = oldEdible;
        this.newRow = newCol;
        this.newCol = newRow;
        this.gameId = gameId;
    }

    public int getNewRow() {
        return newRow;
    }

    public Edible getOldEdible() {
        return oldEdible;
    }

    public Edible getNewEdible() {
        return newEdible;
    }

    public int getGameId() {
        return gameId;
    }

    public int getNewCol() {
        return newCol;
    }
}
