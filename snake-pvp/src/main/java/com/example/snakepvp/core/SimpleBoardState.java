package com.example.snakepvp.core;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleBoardState implements BoardState {
    static private final int TIMEOUT_NORMAL = 500;
    static private final int TIMEOUT_FAST = 250;
    static private final int STANDARD_COOL_DOWN = 20;
    static private final int SHORT_COOL_DOWN = 5;

    private final Map<Edible, Integer> coolDowns = new HashMap<>();
    private final List<Cell> holes = new ArrayList<>();
    private final Board board;
    private final Snake snake;
    private final Score score;
    boolean isEnded;
    private int growCounter;
    private int timeout = TIMEOUT_NORMAL;
    private boolean isReversed = false;
    private boolean reversed = false;

    public SimpleBoardState(int width, int height) {
        this.isEnded = false;
        this.board = new Board(width, height);
        //TODO change snake generation
        this.score = new Score();
        coolDowns.put(Edible.SPEED_UP, 0);
        coolDowns.put(Edible.REVERSE_DIR, 0);
        this.growCounter = 0;
        this.snake = new Snake(Stream.of(1, 2, 3).map(x -> board.getCell(board.getHeight() / 2, x)).collect(Collectors.toCollection(LinkedList<Cell>::new)));
        generateEdible();
        generateEdible();
    }

    @Override
    public int getWidth() {
        return board.getWidth();
    }

    @Override
    public int getHeight() {
        return board.getHeight();
    }

    @Override
    public Snake getSnake() {
        return snake;
    }

    private Edible randomEdible() {
        double[] weights = {Edible.SIMPLE_GROWING.getWeight(), Edible.GROW_TWICE.getWeight(), Edible.SPEED_UP.getWeight(), Edible.REVERSE.getWeight(), Edible.POKE_HOLE.getWeight(), Edible.REVERSE_DIR.getWeight()};
        Edible[] edibles = {Edible.SIMPLE_GROWING, Edible.GROW_TWICE, Edible.SPEED_UP, Edible.REVERSE, Edible.POKE_HOLE, Edible.REVERSE_DIR};
        Random generator = new Random();
        double rand = generator.nextDouble();
        double sum = 0.0;
        double currentSum = 0.0;
        for (int i = 0; i < edibles.length; i++)
            sum += weights[i];
        for (int i = 0; i < edibles.length; i++) {
            currentSum += weights[i] / sum;
            if (rand <= currentSum) return edibles[i];
        }
        return edibles[edibles.length - 1];
    }

    private Cell getRandomUnuesedCell() {
        List<Cell> emptyCells = new ArrayList<>();
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (board.getCell(row, col).getEdible() == null && board.getCell(row, col).isGoThrough() && board.getCell(row, col).getEdible() == null)
                    emptyCells.add(board.getCell(row, col));
            }
        }

        Random random = new Random();
        int index = random.nextInt(emptyCells.size());
        return emptyCells.get(index);
    }

    @Override
    public Cell generateEdible() {
        Cell randCell = getRandomUnuesedCell();
        Edible edible = randomEdible();
        randCell.setEdible(edible);
        return randCell;
    }

    @Override
    public MoveStatus makeMove(Direction dir) {
        for (Map.Entry<Edible, Integer> entry : coolDowns.entrySet()) {
            if (entry.getValue() == 0) endEffect(entry.getKey());
            coolDowns.put(entry.getKey(), entry.getValue() - 1);
        }
        if (isReversed) dir = dir.revers();//revers direction of snake when edible REVERSED_DIR is active

        int nextRow = snake.getRowDirection();
        int nextCol = snake.getColDirection();

        if (nextRow != 0 && (dir == Direction.UP || dir == Direction.DOWN)) {
            nextRow = 0;
            switch (dir) {
                case UP -> nextCol = -1;
                case DOWN -> nextCol = 1;
            }
        }
        if (nextCol != 0 && (dir == Direction.LEFT || dir == Direction.RIGHT)) {
            nextCol = 0;
            switch (dir) {
                case RIGHT -> nextRow = 1;
                case LEFT -> nextRow = -1;
            }
        }
        if (reversed) {
            nextRow = 0;
            nextCol = 0;
        }
        nextRow += snake.getHead().getRow();
        nextCol += snake.getHead().getCol();

        if (!reversed && (!board.isValid(nextRow, nextCol) || !board.getCell(nextRow, nextCol).isGoThrough())) {
            isEnded = true;
            return new MoveStatus(false, null, null, null, null); // TODO
        }
        Cell tail = null;
        Cell head;
        Cell neck;
        if (!reversed) {
            if (growCounter > 0) {
                growCounter--;
                snake.moveWithGrowToCell(board.getCell(nextRow, nextCol));
            } else {
                tail = snake.moveToCell(board.getCell(nextRow, nextCol));
                tail.setGoThrough(true);// resets tail to normal cell
            }
        }
        neck = snake.getNeck();
        head = snake.getHead();
        head.setGoThrough(false);
        Edible eaten = head.getEdible();
        snake.getHead().removeEdible();
        if (eaten != null) this.score.increment(eaten.getScore()); //TODO change score system
        if (reversed) {
            reversed = false;
            snake.reverse();
            head = snake.getHead();
            neck = snake.getTail();
        }
        if (tail == null) {
            return new MoveStatus(true, eaten, null, new MoveStatus.Cell(head.getRow(), head.getCol(), !head.isGoThrough()), new MoveStatus.Cell(neck.getRow(), neck.getCol(), !neck.isGoThrough()));
        }

        return new MoveStatus(true, eaten, new MoveStatus.Cell(tail.getRow(), tail.getCol(), !tail.isGoThrough()),
                new MoveStatus.Cell(head.getRow(), head.getCol(), !head.isGoThrough()),
                new MoveStatus.Cell(neck.getRow(), neck.getCol(), !neck.isGoThrough())); // TODO
    }

    private void endEffect(Edible edible) {
        switch (edible) {
            case SPEED_UP -> timeout = TIMEOUT_NORMAL;
            case REVERSE_DIR -> isReversed = false;
        }
    }

    @Override
    public boolean invokeEdibleEffect(Edible edible) {
        //TODO implement more edible effects
        switch (edible) {
            case SIMPLE_GROWING -> growCounter++;
            case SPEED_UP -> {
                timeout = TIMEOUT_FAST;
                coolDowns.put(Edible.SPEED_UP, STANDARD_COOL_DOWN);
            }
            case REVERSE_DIR -> {
                isReversed = true;
                coolDowns.put(Edible.REVERSE_DIR, SHORT_COOL_DOWN);
            }
            case GROW_TWICE -> growCounter += 2;
            case REVERSE -> {
                reversed = true;
            }
            case POKE_HOLE -> {
                Cell cell = getRandomUnuesedCell();
                cell.setGoThrough(false);
                holes.add(cell);
            }
            default -> {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean isGameOver() {
        return isEnded;
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    public Cell getCell(int row, int col) {
        return board.getCell(row, col);
    }

    public List<Cell> getHoles() {
        return holes;
    }
}
