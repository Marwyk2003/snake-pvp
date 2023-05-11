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
        // TODO
        return false;
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
