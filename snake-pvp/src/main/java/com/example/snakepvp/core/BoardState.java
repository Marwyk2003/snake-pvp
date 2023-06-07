package com.example.snakepvp.core;

public interface BoardState {

    int getWidth();

    int getHeight();

    Player getPlayer();

    Snake getSnake();

    void generateEdible(Edible edible);

    MoveStatus makeMove(SnakeDirection dir);

    boolean invokeEdibleEffect(Edible edible);

    boolean isGameOver();

    Score getScore();

    Cell getCell(int row, int col);
}
