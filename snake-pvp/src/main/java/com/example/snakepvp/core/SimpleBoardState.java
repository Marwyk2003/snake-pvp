package com.example.snakepvp.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleBoardState implements BoardState {
    static private final int TIMEOUT_NORMAL = 500;
    static private final int TIMEOUT_FAST = 250;
    static private final int TIMEOUT_COOLDOWN = 20;

    private final Board board;
    private final Snake snake;
    private final Score score;
    boolean isEnded;
    private int growCounter;

    private int timeout = TIMEOUT_NORMAL;
    private int timeoutCooldown;

    public SimpleBoardState(int width, int height) {
        this.isEnded = false;
        this.board = new Board(width, height);
        //TODO change snake generation
        this.score = new Score();
        this.growCounter = 0;
        this.snake = new Snake(Stream.of(1, 2, 3)
                .map(x -> board.getCell(board.getHeight() / 2, x))
                .collect(Collectors.toCollection(LinkedList<Cell>::new)));
        generateEdible(Edible.SIMPLE_GROWING);
        generateEdible(Edible.SPEED_UP);
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
    public Snake getSnake() {
        return snake;
    }

    @Override
    public Cell generateEdible(Edible edible) {
        List<Cell> emptyCells = new ArrayList<>();
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (board.getCell(row, col).getEdible() == null && board.getCell(row, col).isGoThrough())
                    emptyCells.add(board.getCell(row, col));
            }
        }

        Random random = new Random();
        int index = random.nextInt(emptyCells.size());

        emptyCells.get(index).setEdible(edible);
        System.out.println("new edible " + emptyCells.get(index).getRow() + " " + emptyCells.get(index).getCol());
        return emptyCells.get(index);
    }

    @Override
    public MoveStatus makeMove(Direction dir) {
        if (timeoutCooldown > 0) timeoutCooldown--;
        else if (timeoutCooldown == 0) timeout = TIMEOUT_NORMAL;

        int nextRow = snake.getRowDirection();
        int nextCol = snake.getColDirection();

        if (nextRow != 0 && (dir == Direction.UP || dir == Direction.DOWN)) {
            nextRow = 0;
            switch (dir) {
                case UP -> nextCol = -1;
                case DOWN -> nextCol = 1;
            }
        }
        if (nextCol != 0 && (dir == Direction.LEFT || dir == Direction.RIGHT)) {
            nextCol = 0;
            switch (dir) {
                case RIGHT -> nextRow = 1;
                case LEFT -> nextRow = -1;
            }
        }
        nextRow += snake.getHead().getRow();
        nextCol += snake.getHead().getCol();

        if (!board.isValid(nextRow, nextCol) || !board.getCell(nextRow, nextCol).isGoThrough()) {
            isEnded = true;
            return new MoveStatus(false, null, null, null, null); // TODO
        }
        Cell tail;
        Cell head;
        Cell neck;
        if (growCounter > 0) {
            growCounter--;
            tail = null;
            snake.moveWithGrowToCell(board.getCell(nextRow, nextCol));
        } else {
            tail = snake.moveToCell(board.getCell(nextRow, nextCol));
            tail.setGoThrough(true);// resets tail to normal cell
        }
        neck = snake.getNeck();
        head = snake.getHead();
        head.setGoThrough(false);
        Edible eaten = head.getEdible();
        snake.getHead().removeEdible();
        if (eaten != null)
            this.score.increment(1); //TODO change score system

        if (tail == null) {
            return new MoveStatus(true, eaten, null,
                    new MoveStatus.Cell(head.getRow(), head.getCol(), !head.isGoThrough()),
                    new MoveStatus.Cell(neck.getRow(), neck.getCol(), !neck.isGoThrough()));

        }

        return new MoveStatus(true, eaten,
                new MoveStatus.Cell(tail.getRow(), tail.getCol(), !tail.isGoThrough()),
                new MoveStatus.Cell(head.getRow(), head.getCol(), !head.isGoThrough()),
                new MoveStatus.Cell(neck.getRow(), neck.getCol(), !neck.isGoThrough())); // TODO
    }

    @Override
    public boolean invokeEdibleEffect(Edible edible) {
        //TODO implement more edible effects
        if (edible == null) return false;
        if (edible == Edible.SIMPLE_GROWING) {
            growCounter++;
        } else if (edible == Edible.SPEED_UP) {
            timeout = TIMEOUT_FAST;
            timeoutCooldown = TIMEOUT_COOLDOWN;
            System.out.println("speed up!");
        } else {
            return false;
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

    @Override
    public int getTimeout() {
        return timeout;
    }

    public int getTimeoutCooldown() {
        return timeoutCooldown;
    }


    public Cell getCell(int row, int col) {
        return board.getCell(row, col);
    }
}
