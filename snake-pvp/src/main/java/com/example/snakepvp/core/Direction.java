package com.example.snakepvp.core;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public Direction revers() {
        switch (this) {
            case UP -> {
                return DOWN;
            }
            case DOWN -> {
                return UP;
            }
            case LEFT -> {
                return RIGHT;
            }
            case RIGHT -> {
                return LEFT;
            }
            default -> {
                throw new IllegalStateException();
            }
        }
    }
}
