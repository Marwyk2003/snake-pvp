package com.example.snakepvp.services;

public class CellEvent {
    private final int row;
    private final int col;
    private final boolean isSnake;
    private final boolean isSnakeHead;

    public CellEvent(int row, int col, boolean isSnake, boolean isSnakeHead) {
        this.row = row;
        this.col = col;
        this.isSnake = isSnake;
        this.isSnakeHead = isSnakeHead;
    }

    public CellEvent(int row, int col, boolean isSnake) {
        this.row = row;
        this.col = col;
        this.isSnake = isSnake;
        this.isSnakeHead = false;
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

    public boolean isSnakeHead() {
        return isSnakeHead;
    }
}
