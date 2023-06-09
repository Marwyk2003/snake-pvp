package com.example.snakepvp.core;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final List<Cell> snake;

    Snake(LinkedList<Cell> snake) {
        this.snake = snake;
        for (Cell cell: snake)
            cell.setGoThrough(false);
    }

    public List<Cell> getCellList() {
        return List.copyOf(snake);
    }

    int getRowDirection() {
        return getHead().getRow() - getNeck().getRow();
    }

    int getColDirection() {
        return getHead().getCol() - getNeck().getCol();
    }

    Cell moveToCell(Cell next) {
        snake.add(next);
        return snake.remove(0);
    }

    void moveWithGrowToCell(Cell next) {
        snake.add(next);
    }

    public Cell getNeck() {
        return snake.get(snake.size() - 2);
    }

    public Cell getHead() {
        return snake.get(snake.size() - 1);
    }
}
