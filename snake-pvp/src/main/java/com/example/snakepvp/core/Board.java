package com.example.snakepvp.core;

public class Board {
    private final int width, height;
    private final Cell[][] board;

    public Board(int width, int height) {
        if (width < 6 || height < 6) throw new IllegalArgumentException();
        this.width = width;
        this.height = height;
        board = new Cell[height][width];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; col++) {
                boolean isWall = row == 0 || row == height - 1 || col == 0 || col == width - 1;
                board[row][col] = new Cell(row, col, !isWall);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isValid(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    public Cell getCell(int row, int col) {
        if (!isValid(row, col)) throw new IllegalArgumentException();
        return board[row][col];
    }
}
