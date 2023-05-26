package com.example.snakepvp.core;

public class MoveStatus {
    boolean success;
    Edible edible;
    MoveStatus(boolean success, Edible edible){
        this.success = success;
        this.edible = edible;
    }

    public boolean isSuccess() {
        return success;
    }

    public Edible getEdible() {
        return edible;
    }
}
