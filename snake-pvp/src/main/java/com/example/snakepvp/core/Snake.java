package com.example.snakepvp.core;

import java.util.List;

public class Snake {
    List<Cell> snake;

    Direction getDirection() {
        // TODO
        return null;
    }

    Cell getHead() {
        return snake.get(snake.size() - 1);
    }
}
