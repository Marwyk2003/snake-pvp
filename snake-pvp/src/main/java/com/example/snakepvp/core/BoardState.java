package com.example.snakepvp.core;

public interface BoardState {

    int getWidth();

    int getHeight();

    Snake getSnake();

    Cell generateEdible(Edible edible);

    MoveStatus makeMove(Direction dir);

    boolean invokeEdibleEffect(Edible edible);

    boolean isGameOver();

    Score getScore();

    Cell getCell(int row, int col);
}
