package com.example.snakepvp.viewmodels;

import com.example.snakepvp.core.*;
import com.example.snakepvp.services.CellEvent;
import com.example.snakepvp.services.EdibleEvent;
import com.example.snakepvp.services.GameService;
import com.example.snakepvp.services.SimpleViewerService;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.concurrent.TimeUnit;

public class SingleGameViewModel implements ViewModel {
    final SimpleBooleanProperty gameOver;
    private final VMCell[][] cells;
    private final int height, width;
    private final GameService gameService;
    private final SimpleViewerService viewerService;

    public SingleGameViewModel(GameService gameService) {//TODO add viewerService to constructor
        this.gameService = gameService;
        this.viewerService = gameService.viewerService;
        BoardState boardState = gameService.getBoardState();
        this.height = boardState.getHeight();
        this.width = boardState.getWidth();
        this.gameOver = new SimpleBooleanProperty(this, "gameOver", boardState.isGameOver());
        this.cells = new VMCell[height][width];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                Cell cell = boardState.getCell(row, col);
                this.cells[row][col] = new VMCell(row, col, cell.isGoThrough());
                this.cells[row][col].setEdible(cell.getEdible());
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

    public void growSnakeEvent(EdibleEvent event) {
        gameService.grow(event.getEdible());
    }

    public void generateEdibleEvent(EdibleEvent event) {
        SingleGameViewModel.this.cells[event.getCol()][event.getRow()].setEdible(event.getEdible());
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

        private final SimpleObjectProperty<CellContent> cellContent;

        private boolean isGoThrough;
        private boolean isSnake;
        private Edible edible;

        VMCell(int row, int col, boolean isGoThrough) {
            this.cellContent = new SimpleObjectProperty<>(SingleGameViewModel.this, row + ":" + col, CellContent.EMPTY);
            setIsGoThrough(isGoThrough);
        }

        public SimpleObjectProperty<CellContent> cellContentProperty() {
            return cellContent;
        }

        public void setIsGoThrough(boolean isGoThrough) {
            this.isGoThrough = isGoThrough;
            updateContent();
        }

        public void setIsSnake(boolean isSnake) {
            if (isSnake) setEdible(null);
            this.isSnake = isSnake;
            updateContent();
        }

        public void setEdible(Edible edible) {
            this.edible = edible;
            updateContent();
        }

        private void updateContent() {
            if (isSnake) cellContent.set(CellContent.SNAKE);
            else if (edible != null) cellContent.set(CellContent.EDIBLE_GROW);
            else if (!isGoThrough) cellContent.set(CellContent.WALL);
            else cellContent.set(CellContent.EMPTY);
        }
    }
}
