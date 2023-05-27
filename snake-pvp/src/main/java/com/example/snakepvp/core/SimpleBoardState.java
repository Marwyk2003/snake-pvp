package com.example.snakepvp.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleBoardState implements BoardState {
    private final Board board;
    private final Snake snake;
    private final Player player;
    private final Score score;
    boolean isEnded;
    private int growCounter;

    public SimpleBoardState(int width, int height) {
        this.isEnded = false;
        this.board = new Board(width, height);
        //TODO change snake generation
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
    public void generateEdible(Edible edible) {
        List<Cell> emptyCells = new ArrayList<>();
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (board.getCell(row, col).getEdible() == null)
                    emptyCells.add(board.getCell(row, col));
            }
        }

        Random random = new Random();
        int index = random.nextInt(emptyCells.size());

        emptyCells.get(index).setEdible(edible);
    }

    @Override
    public MoveStatus makeMove(Direction dir) {
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
            isEnded = true;
            return new MoveStatus(false, null, null, null); // TODO
        }

        if (growCounter > 0) {
            growCounter--;
            snake.moveWithGrowToCell(board.getCell(nextRow, nextCol));
        } else
            snake.moveToCell(board.getCell(nextRow, nextCol)).setGoThrough(true);// resets tail to normal cell
        snake.getHead().setGoThrough(false);

        Edible eaten = snake.getHead().getEdible();
        snake.getHead().removeEdible();
        if (eaten != null)
            this.score.increment(1); //TODO change score system

        return new MoveStatus(true, eaten, null, null); // TODO
    }

    @Override
    public boolean invokeEdibleEffect(Edible edible) {
        //TODO implement more edible effects
        switch (edible) {
            case SIMPLE_GROWING -> growCounter++;
            default -> {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean isGameOver() {
        return isEnded;
    }

    @Override
    public Score getScore() {
        return score;
    }

    public Cell getCell(int row, int col) {
        return board.getCell(row, col);
    }
}
