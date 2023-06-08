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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    boolean setEdible(Edible edible) {
        if (this.edible != null) return false;
        this.edible = edible;
        return true;
    }

    public Edible getEdible() {
        return this.edible;
    }

    boolean removeEdible() {
        if (this.edible == null) return false;
        this.edible = null;
        return true;
    }

    public boolean isGoThrough() {
        return goThrough;
    }

    void setGoThrough(boolean goThrough) {
        this.goThrough = goThrough;
    }
}