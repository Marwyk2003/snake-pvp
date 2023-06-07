package com.example.snakepvp.core;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public Direction rotateClockwise() {
        Direction rotated = null;
        switch (this) {
            case UP -> rotated = RIGHT;
            case RIGHT -> rotated = DOWN;
            case DOWN -> rotated = LEFT;
            case LEFT -> rotated = UP;
        }
        return rotated;
    }

    public Direction rotateAnticlockwise() {
        Direction rotated = null;
        switch (this) {
            case UP -> rotated = LEFT;
            case RIGHT -> rotated = UP;
            case DOWN -> rotated = RIGHT;
            case LEFT -> rotated = DOWN;
        }
        return rotated;
    }
}
