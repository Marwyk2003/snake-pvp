package com.example.snakepvp.core;

public enum Edible { //TODO implement different edible types
    SIMPLE_GROWING(0.7, 1), GROW_TWICE(0.2, 2),
    SPEED_UP(0.05, 2), REVERSE(0.1, 2),
    POKE_HOLE(0.05, 3), REVERSE_DIR(0.05, 3);

    private final double weight;
    private final int score;

    Edible(double weight, int score) {
        this.weight = weight;
        this.score = score;
    }

    public double getWeight() {
        return weight;
    }

    public int getScore() {
        return score;
    }
}
