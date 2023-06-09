package com.example.snakepvp.viewmodels;

import com.example.snakepvp.core.*;
import com.example.snakepvp.services.CellEvent;
import com.example.snakepvp.services.EdibleEvent;
import com.example.snakepvp.services.GameService;
import com.example.snakepvp.services.SimpleViewerService;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleObjectProperty;

import java.util.concurrent.TimeUnit;

public class SingleGameViewModel implements ViewModel {
    private final GameService gameService;
    private final SimpleViewerService viewerService;
    private VMCell[][] cells;
    private int height, width;
    private Thread gameThread;
    private int skin;

    public SingleGameViewModel(GameService gameService) {//TODO add viewerService to constructor
        this.gameService = gameService;
        this.viewerService = gameService.viewerService;
        this.viewerService.cellEvents().subscribe(this::processCellEvent);
    }

    public void initialize() {
        gameService.initGame();
        BoardState boardState = gameService.getBoardState();
        this.height = boardState.getHeight();
        this.width = boardState.getWidth();
        this.cells = new VMCell[height][width];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                this.cells[row][col] = new VMCell(row, col, false);
            }
        }
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                Cell cell = boardState.getCell(row, col);
                this.cells[row][col].setIsGoThrough(cell.isGoThrough());
                this.cells[row][col].setEdible(cell.getEdible());
            }
        }
        for (Cell cell : boardState.getSnake().getCellList()) {
            System.out.println(cell.getCol() + " " + cell.getRow() + " - setup snake");
            this.cells[cell.getRow()][cell.getCol()].setIsSnake(true);
        }
    }

    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public void run() {
        gameThread = Thread.currentThread();
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(gameService.getTimeout());
                gameService.makeMove();
            }
        } catch (InterruptedException ignored) {
        }
    }

    public void endGame() {
        gameThread.interrupt();
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
            else if (edible == Edible.SIMPLE_GROWING) cellContent.set(CellContent.EDIBLE_GROW);
            else if (edible == Edible.SPEED_UP) cellContent.set(CellContent.EDIBLE_SPEED);
            else if (!isGoThrough) cellContent.set(CellContent.WALL);
            else cellContent.set(CellContent.EMPTY);
        }
    }
}
