package com.example.snakepvp.services;

import com.example.snakepvp.core.*;

public class GameService {
    public final int timeout = 500;
    private final Player player;
    private final SimpleEventEmitter<CellEvent> cellEvents = new SimpleEventEmitter<>();
    public SimpleViewerService viewerService = new SimpleViewerService(null, cellEvents, null);
    Direction direction = Direction.DOWN; // TODO
    private BoardState boardState;

    public GameService(Player player) {
        this.player = player;
        this.boardState = new SimpleBoardState(10, 10); // TODO temporary fix
    }

    void newGame(int width, int height) {
        // TODO: quit existing game
        this.boardState = new SimpleBoardState(width, height);
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

    public MoveStatus makeMove() {
        MoveStatus moveStatus = boardState.makeMove(direction);
        if (moveStatus.getTail() != null)
            cellEvents.emit(new CellEvent(moveStatus.getTail().getCol(), moveStatus.getTail().getRow(), moveStatus.getTail().isSnake()));
        if (moveStatus.getHead() != null)
            cellEvents.emit(new CellEvent(moveStatus.getHead().getCol(), moveStatus.getHead().getRow(), moveStatus.getHead().isSnake()));
        return moveStatus;
    }
}
