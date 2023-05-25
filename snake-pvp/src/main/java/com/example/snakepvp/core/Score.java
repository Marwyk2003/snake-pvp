package com.example.snakepvp.core;

public class Score {
    private int points;

    public int getPoints() {
        return points;
    }

    public int increment(int count) {
        points += count;
        return points;
    }
}
