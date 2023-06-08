package com.example.snakepvp.services;

import com.example.snakepvp.core.*;

public class GameService {
    public final SimpleViewerService viewerService;
    private final int gameId;

    private final SimpleEventEmitter<GameStatusEvent> statusEvents;
    private final SimpleEventEmitter<CellEvent> cellEvents = new SimpleEventEmitter<>();
    private final SimpleEventEmitter<EdibleEvent> edibleEvents;

    private Direction direction;
    private int timeout;
    private BoardState boardState;

    public GameService(int gameId, SimpleEventEmitter<GameStatusEvent> statusEvents, SimpleEventEmitter<EdibleEvent> edibleEvents) {
        this.gameId = gameId;
        this.edibleEvents = edibleEvents;
        this.statusEvents = statusEvents;
        this.viewerService = new SimpleViewerService(statusEvents, cellEvents, edibleEvents);
    }

    public void initGame() {
        this.boardState = new SimpleBoardState(10 + 2, 10 + 2); // TODO temporary fix
        direction = Direction.DOWN;
        timeout = 500;
    }

    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public int getTimeout() {
        return timeout;
    }

    public void grow(Edible edible) {
        boardState.invokeEdibleEffect(edible);//TODO inform gameHostService
    }

    public void makeMove() {
        MoveStatus moveStatus = boardState.makeMove(direction);
        Edible eaten = moveStatus.getEdible();
        if (eaten != null) {
            System.out.println("just ate");
            Cell cell = boardState.generateEdible(eaten);//TODO randomize edibles
            edibleEvents.emit(new EdibleEvent(eaten, cell.getRow(), cell.getCol(), gameId));
        }
        if (moveStatus.getTail() != null)
            cellEvents.emit(new CellEvent(moveStatus.getTail().getCol(), moveStatus.getTail().getRow(), moveStatus.getTail().isSnake()));
        if (moveStatus.getHead() != null)
            cellEvents.emit(new CellEvent(moveStatus.getHead().getCol(), moveStatus.getHead().getRow(), moveStatus.getHead().isSnake()));
        if (!moveStatus.isSuccess()) statusEvents.emit(new GameEndedEvent(gameId));
    }
}
