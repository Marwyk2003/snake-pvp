package com.example.snakepvp.core;

public interface BoardState {

    int getWidth();

    int getHeight();

    Player getPlayer();

    Snake getSnake();

    void generateEdible();

    boolean makeMove(Direction dir);

    boolean isGameOver();

    Score getScore();
}
