package com.example.snakepvp.core;

public enum Edible { //TODO implement different edible types
    SIMPLE_GROWING(0.7), GROW_TWICE(0.2), SPEED_UP(0.05), REVERSE(0.05);

    private final double weight;

    Edible(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
