package com.example.snakepvp.core;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final List<Cell> snake;

    Snake() {
        snake = new LinkedList<>();
    }

    int getRowDirection() {
        return getHead().getRow() - snake.get(snake.size() - 2).getRow();
    }

    int getColDirection() {
        return getHead().getCol() - snake.get(snake.size() - 2).getCol();
    }

    Cell moveToCell(Cell next) {
        snake.add(next);
        return snake.remove(1);
    }

    Cell getHead() {
        return snake.get(snake.size() - 1);
    }
}
