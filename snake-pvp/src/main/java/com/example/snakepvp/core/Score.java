package com.example.snakepvp.core;

public class Score {
    private int points;
    Score(){
        this.points = 0;
    }
    public int getPoints() {
        return points;
    }

    public int increment(int count) {
        points += count;
        return points;
    }
}
