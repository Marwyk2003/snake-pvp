package com.example.snakepvp.core;

public enum Edible { //TODO implement different edible types
    SIMPLE_GROWING(0.7, 10), GROW_TWICE(0.2, 20),
    SPEED_UP(0.075, 20), REVERSE(0.05, 20),
    POKE_HOLE(0.1, 30), REVERSE_DIR(0.025, 50);

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
