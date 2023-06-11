package com.example.snakepvp.viewmodels;

import com.example.snakepvp.core.*;
import com.example.snakepvp.services.CellEvent;
import com.example.snakepvp.services.EdibleEvent;
import com.example.snakepvp.services.GameService;
import com.example.snakepvp.services.SimpleViewerService;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.concurrent.TimeUnit;

public class SingleGameViewModel implements ViewModel {
    private final GameService gameService;
    private final SimpleViewerService viewerService;
    private final SimpleIntegerProperty length;
    private final SimpleObjectProperty<Score> score;
    private final int id;
    private VMCell[][] cells;
    private int height, width;
    private Thread gameThread;
    private int skin;

    public SingleGameViewModel(int id, GameService gameService) {//TODO add viewerService to constructor
        this.id = id;
        this.gameService = gameService;
        this.viewerService = gameService.viewerService;
        this.viewerService.cellEvents().subscribe(this::processCellEvent);
        this.length = new SimpleIntegerProperty(this, "length", 0);
        this.score = new SimpleObjectProperty<>(SingleGameViewModel.this, "score", null);
    }

    public int getId() {
        return id;
    }

    public SimpleIntegerProperty lengthProperty() {
        return length;
    }

    public SimpleObjectProperty<Score> scoreProperty() {
        return score;
    }

    public void initialize() {
        gameService.initGame();
        length.set(gameService.getSnakeLength());
        score.set(gameService.getScore());
        BoardState boardState = gameService.getBoardState();
        this.height = boardState.getHeight();
        this.width = boardState.getWidth();
        this.cells = new VMCell[height][width];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                this.cells[row][col] = new VMCell(row, col, false);
            }
        }
        Cell head = boardState.getSnake().getHead();
        for (Cell cell : boardState.getSnake().getCellList()) {
            System.out.println(cell.getCol() + " " + cell.getRow() + " - setup snake");
            this.cells[cell.getRow()][cell.getCol()].setIsSnake(true, cell == head);
        }
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                Cell cell = boardState.getCell(row, col);
                this.cells[row][col].setIsGoThrough(cell.isGoThrough() || this.cells[row][col].isSnake);
                this.cells[row][col].setEdible(cell.getEdible());
            }
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
        SingleGameViewModel.this.cells[event.getCol()][event.getRow()].setIsSnake(event.isSnake(), event.isSnakeHead());
        length.set(gameService.getSnakeLength());
        score.set(gameService.getScore());
    }

    public void growSnakeEvent(EdibleEvent event) {
        gameService.grow(event.getOldEdible());
    }

    public void generateEdibleEvent(EdibleEvent event) {
        SingleGameViewModel.this.cells[event.getNewCol()][event.getNewRow()].setEdible(event.getNewEdible());
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
        private boolean isSnakeHead;
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

        public void setIsSnake(boolean isSnake, boolean isSnakeHead) {
            if (isSnake) setEdible(null);
            this.isSnake = isSnake;
            this.isSnakeHead = isSnakeHead;
            updateContent();
        }

        public void setEdible(Edible edible) {
            this.edible = edible;
            updateContent();
        }

        private void updateContent() {
            if (isSnakeHead) cellContent.set(CellContent.SNAKE_HEAD);
            else if (isSnake) cellContent.set(CellContent.SNAKE);
            else if (edible == Edible.SIMPLE_GROWING) cellContent.set(CellContent.EDIBLE_GROW);
            else if (edible == Edible.SPEED_UP) cellContent.set(CellContent.EDIBLE_SPEED);
            else if (edible == Edible.GROW_TWICE) cellContent.set(CellContent.EDIBLE_DOUBLE);
            else if (edible == Edible.REVERSE) cellContent.set(CellContent.EDIBLE_REVERSE);
            else if (edible == Edible.REVERSE_DIR) cellContent.set(CellContent.EDIBLE_DIRECTION);
            else if (!isGoThrough) cellContent.set(CellContent.WALL);
            else cellContent.set(CellContent.EMPTY);
        }
    }
}
