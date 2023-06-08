package com.example.snakepvp.viewmodels;

import com.example.snakepvp.core.BoardState;
import com.example.snakepvp.core.Cell;
import com.example.snakepvp.core.Direction;
import com.example.snakepvp.core.Edible;
import com.example.snakepvp.services.*;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.concurrent.TimeUnit;

public class SingleGameViewModel implements ViewModel {
    final SimpleBooleanProperty gameOver;
    private final VMCell[][] cells;
    private final int height, width;
    GameService gameService;
    SimpleViewerService viewerService;

    public SingleGameViewModel(GameService gameService, SimpleViewerService viewerService) {//TODO add viewerService to constructor
        this.gameService = gameService;
        this.viewerService = viewerService;
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
            System.out.println(cell.getCol() + " " + cell.getRow() + " - setup snake");
            this.cells[cell.getRow()][cell.getCol()].setIsSnake(true);
        }
        this.viewerService.cellEvents().subscribe(this::processCellEvent);
        //TODO subscribe to chosen emitters via viewerService
    }

    public void run() {
        while (true) {
            try {
                gameService.makeMove();
                TimeUnit.MILLISECONDS.sleep(gameService.timeout);
            } catch (InterruptedException ignored) {
                // TODO gameover
            }
        }
    }

    private void processCellEvent(CellEvent event) {
        SingleGameViewModel.this.cells[event.getCol()][event.getRow()].setIsSnake(event.isSnake());
    }

    private void processEdibleEvent(EdibleEvent event) {
        //TODO
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

    public void changeDirection(Direction dir) {
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
