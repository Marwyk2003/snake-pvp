package com.example.snakepvp.core;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final List<Cell> snake;

    Snake() {
        snake = new LinkedList<>();
    }

    SnakeDirection getDirection() {
        Cell head = getHead();
        Cell neck = snake.get(snake.size() - 2);
        if (head.getRow() > neck.getRow())
            return SnakeDirection.RIGHT;
        if (head.getRow() < neck.getRow())
            return SnakeDirection.LEFT;
        if (head.getCol() > neck.getCol())
            return SnakeDirection.UP;
        return SnakeDirection.DOWN;
    }

    Cell moveToCell(Cell next) {
        snake.add(next);
        return snake.remove(1);
    }

    Cell getHead() {
        return snake.get(snake.size() - 1);
    }
}
