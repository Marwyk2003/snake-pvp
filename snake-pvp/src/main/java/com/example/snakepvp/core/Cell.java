package com.example.snakepvp.core;

public class Cell {
    private final int row, col;
    private boolean goThrough;
    private Edible edible;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        goThrough = false;
        edible = null;
    }

    public Cell(int row, int col, boolean goThrough) {
        this(row, col);
        this.goThrough = goThrough;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    boolean setEdible(Edible edible) {
        if (this.edible != null) return false;
        this.edible = edible;
        return true;
    }

    void effect() {
        if (edible != null) {
            edible.takeEffect();
            edible = null;
            //TODO sent event generate edible
        }
    }

    boolean removeEdible() {
        if (this.edible == null) return false;
        this.edible = null;
        return true;
    }

    boolean setGoThrough(boolean goThrough) {
        this.goThrough = goThrough;
        return true;
    }

    boolean getGoThrough() {
        return goThrough;
    }
}