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
        SnakeDirection sDir = snake.getDirection();
        Cell head = snake.getHead();
        int nextRow = head.getRow();
        int nextCol = head.getCol();
        if (sDir == SnakeDirection.UP) {
            if (dir == Direction.FORWARD)
                nextRow++;
            else if (dir == Direction.RIGHT)
                nextCol++;
            else //LEFT
                nextCol--;
        } else if (sDir == SnakeDirection.DOWN) {
            if (dir == Direction.FORWARD)
                nextRow--;
            else if (dir == Direction.RIGHT)
                nextCol--;
            else //LEFT
                nextCol++;
        } else if (sDir == SnakeDirection.RIGHT) {
            if (dir == Direction.FORWARD)
                nextCol++;
            else if (dir == Direction.RIGHT)
                nextRow--;
            else //LEFT
                nextRow++;
        } else { //LEFT
            if (dir == Direction.FORWARD)
                nextCol--;
            else if (dir == Direction.RIGHT)
                nextRow++;
            else //LEFT
                nextRow--;
        }
        if (!board.isValid(nextRow, nextCol) || !board.getCell(nextRow, nextCol).getGoThrough()) {
            return false;
        }

        snake.moveToCell(board.getCell(nextRow, nextCol));
        snake.getHead().setGoThrough(false);

        snake.getHead().effect();
        snake.getHead().setEdible(null);
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
