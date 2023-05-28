package com.example.snakepvp.services;

import com.example.snakepvp.core.*;

import java.util.concurrent.BrokenBarrierException;

public class GameService {
    private final Player player;
    private final SimpleEventEmitter<CellEvent> cellEvents = new SimpleEventEmitter<>();
    public SimpleViewerService viewerService = new SimpleViewerService(null, cellEvents, null);
    Direction direction; // TODO
    GameHostService gameHostService;
    private BoardState boardState;
    private Snake snake;

    public GameService(Player player, GameHostService gameHostService) {
        this.player = player;
        this.gameHostService = gameHostService;
        this.boardState = new SimpleBoardState(10, 10); // TODO temporary fix
    }

    void newGame(int width, int height) {
        // TODO: quit existing game
        this.boardState = new SimpleBoardState(width, height);
    }

    void run() {
        // TODO: while !gameOver
        while (true) {
            try {
                System.out.println("Game await cyclic barrier...");
                gameHostService.cyclicBarrier.await();
                System.out.println("Game done");
                makeMove();
            } catch (InterruptedException | BrokenBarrierException e) {
                // TODO
            }
        }
    }

    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    public Player getPlayer() {
        return player;
    }

    public BoardState getBoardState() {
        return boardState;
    }

    MoveStatus makeMove() {
        Direction dir = Direction.FORWARD; // TODO this.direction;
        this.direction = Direction.FORWARD; // reset direction
        MoveStatus moveStatus = boardState.makeMove(dir);
        if (moveStatus.getTail() != null)
            cellEvents.emit(new CellEvent(moveStatus.getTail().getCol(), moveStatus.getTail().getRow(), moveStatus.getTail().isSnake()));
        if (moveStatus.getHead() != null)
            cellEvents.emit(new CellEvent(moveStatus.getHead().getCol(), moveStatus.getHead().getRow(), moveStatus.getHead().isSnake()));
        return moveStatus;
    }
}
