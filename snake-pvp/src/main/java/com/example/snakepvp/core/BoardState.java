package com.example.snakepvp.core;

public interface BoardState {

    int getWidth();

    int getHeight();

    Player getPlayer();

    Snake getSnake();

    void generateEdible(Edible edible);

    MoveStatus makeMove(Direction dir);

    boolean invokeEdibleEffect(Edible edible);

    boolean isGameOver();

    Score getScore();
}
