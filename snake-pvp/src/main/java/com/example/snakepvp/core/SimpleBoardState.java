package com.example.snakepvp.core;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleBoardState implements BoardState {
    private final Board board;
    private final Snake snake;
    private final Player player;
    private final Score score;
    private int growCounter;

    public SimpleBoardState(int width, int height) {
        this.board = new Board(width, height);
        this.snake = new Snake(Stream.of(1, 2, 3)
                .map(x -> board.getCell(board.getHeight() / 2, x))
                .collect(Collectors.toCollection(LinkedList<Cell>::new)));
        this.player = new Player();
        this.score = new Score();
        this.growCounter = 0;
    }

    @Override
    public int getWidth() {
        return board.getWidth();
    }

    @Override
    public int getHeight() {
        return board.getHeight();
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Snake getSnake() {
        return snake;
    }

    @Override
    public void generateEdible() {
        // TODO
    }

    @Override
    public boolean makeMove(Direction dir) {
        //TODO return status instead of boolean (gameOver, eatenEdible(type), success)
        int nextRow = snake.getRowDirection();
        int nextCol = snake.getColDirection();
        if (dir != Direction.FORWARD) {
            int temp = nextRow;
            nextRow = nextCol;
            nextCol = temp;
        }
        switch (dir) {
            case RIGHT -> nextCol *= -1;
            case LEFT -> nextRow *= -1;
        }
        nextRow += snake.getHead().getRow();
        nextCol += snake.getHead().getCol();

        if (!board.isValid(nextRow, nextCol) || !board.getCell(nextRow, nextCol).isGoThrough()) {
            return false;
        }

        if (growCounter > 0) {
            growCounter--;
            snake.moveWithGrowToCell(board.getCell(nextRow, nextCol));
        } else
            snake.moveToCell(board.getCell(nextRow, nextCol)).setGoThrough(true);// resets tail to normal cell
        snake.getHead().setGoThrough(false);

        snake.getHead().effect();
        return true;
    }

    public void grow() {
        growCounter++;
    }


    @Override
    public boolean isGameOver() {
        // TODO
        return false;
    }

    @Override
    public Score getScore() {
        return score;
    }
}
