package com.example.snakepvp.core;

import java.util.List;

public interface BoardState {

    int getWidth();

    int getHeight();

    Snake getSnake();

    Cell generateEdible();

    MoveStatus makeMove(Direction dir);

    boolean invokeEdibleEffect(Edible edible);

    boolean isGameOver();

    Score getScore();

    int getTimeout();

    Cell getCell(int row, int col);

    List<Cell> getHoles();
}
