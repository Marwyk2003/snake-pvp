package com.example.snakepvp.services;

public class CellEvent {
    private final int row;
    private final int col;
    private final boolean isSnake;

    public CellEvent(int row, int col, boolean isSnake) {
        this.row = row;
        this.col = col;
        this.isSnake = isSnake;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isSnake() {
        return isSnake;
    }
}
