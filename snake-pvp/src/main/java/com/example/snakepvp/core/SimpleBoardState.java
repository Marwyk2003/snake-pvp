package com.example.snakepvp.core;

public class SimpleBoardState implements BoardState {
    private final Board board;
    private final Snake snake;
    private final Player player;
    private final Score score;

    public SimpleBoardState(int width, int height) {
        this.board = new Board(width, height);
        this.snake = new Snake();
        this.player = new Player();
        this.score = new Score();
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

        if (!board.isValid(nextRow, nextCol) || !board.getCell(nextRow, nextCol).getGoThrough()) {
            return false;
        }

        snake.moveToCell(board.getCell(nextRow, nextCol)).setGoThrough(true);
        snake.getHead().setGoThrough(false);

        snake.getHead().effect();
        return true;
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
