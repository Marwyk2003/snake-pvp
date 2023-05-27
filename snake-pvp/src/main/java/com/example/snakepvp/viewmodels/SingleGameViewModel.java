package com.example.snakepvp.viewmodels;

import com.example.snakepvp.core.BoardState;
import com.example.snakepvp.core.Cell;
import com.example.snakepvp.core.Direction;
import com.example.snakepvp.core.Edible;
import com.example.snakepvp.services.GameService;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SingleGameViewModel implements ViewModel {
    final SimpleBooleanProperty gameOver;
    private final VMCell[][] cells;
    private final int height, width;
    GameService gameService;

    public SingleGameViewModel(GameService gameService) {
        this.gameService = gameService;
        BoardState boardState = gameService.getBoardState();
        this.height = boardState.getHeight();
        this.width = boardState.getWidth();
        this.gameOver = new SimpleBooleanProperty(this, "gameOver", boardState.isGameOver());
        this.cells = new VMCell[height][width];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                Cell cell = boardState.getCell(row, col);
                this.cells[row][col] = new VMCell(row, col, cell.isGoThrough());
            }
        }
        for (Cell cell : boardState.getSnake().getCellList()) {
            this.cells[cell.getRow()][cell.getCol()].setIsSnake(true);
        }
    }

    public VMCell getCell(int row, int col) {
        return cells[row][col];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ReadOnlyBooleanProperty gameOverProperty() {
        return gameOver;
    }

    protected void changeDirection(Direction dir) {
        gameService.setDirection(dir);
    }

    public class VMCell {
        private final SimpleBooleanProperty isGoThrough; // merge isSnake and isGoThrough into one enum (?)
        private final SimpleBooleanProperty isSnake; // snake should have a direction
        private final SimpleObjectProperty<Edible> edible;

        VMCell(int row, int col, boolean isGoThrough) {
            this.edible = new SimpleObjectProperty<>(SingleGameViewModel.this, row + ":" + col, null);
            this.isGoThrough = new SimpleBooleanProperty(SingleGameViewModel.this, row + ":" + col, isGoThrough);
            this.isSnake = new SimpleBooleanProperty(SingleGameViewModel.this, row + ":" + col, false);
        }

        public SimpleBooleanProperty isGoThroughProperty() {
            return isGoThrough;
        }

        public SimpleBooleanProperty isSnakeProperty() {
            return isSnake;
        }

        public SimpleObjectProperty<Edible> edibleProperty() {
            return edible;
        }

        public void setIsGoThrough(boolean isGoThrough) {
            this.isGoThrough.set(isGoThrough);
        }

        public void setIsSnake(boolean isSnake) {
            this.isSnake.set(isSnake);
        }

        public void setEdible(Edible edible) {
            this.edible.set(edible);
        }
    }
}
