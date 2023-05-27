package com.example.snakepvp.core;

public class MoveStatus {
    private final Cell tail;
    private final Cell head;
    private final boolean success;
    private final Edible edible;

    MoveStatus(boolean success, Edible edible, Cell tail, Cell head) {
        this.success = success;
        this.edible = edible;
        this.tail = tail;
        this.head = head;
    }

    public Cell getTail() {
        return tail;
    }

    public Cell getHead() {
        return head;
    }

    public boolean isSuccess() {
        return success;
    }

    public Edible getEdible() {
        return edible;
    }

    static class Cell {
        final int row;
        final int col;
        final boolean isSnake;

        Cell(int row, int col, boolean isSnake) {
            this.row = row;
            this.col = col;
            this.isSnake = isSnake;
        }
    }
}
