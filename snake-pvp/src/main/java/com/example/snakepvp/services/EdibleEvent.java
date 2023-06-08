package com.example.snakepvp.services;

import com.example.snakepvp.core.Edible;

public class EdibleEvent {
    Edible edible;
    int row;
    int col;

    EdibleEvent(Edible edible, int row, int col) {
        this.edible = edible;
        this.row = col;
        this.col = row;
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
}
