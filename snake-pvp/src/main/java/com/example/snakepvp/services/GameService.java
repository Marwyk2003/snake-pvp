package com.example.snakepvp.services;

import com.example.snakepvp.core.*;

public class GameService {
    public final int timeout = 500;
    public final SimpleViewerService viewerService;
    private final int gameId;
    private final SimpleEventEmitter<CellEvent> cellEvents = new SimpleEventEmitter<>();
    private final SimpleEventEmitter<EdibleEvent> edibleEvents;
    private final BoardState boardState;
    Direction direction = Direction.DOWN;

    public GameService(int gameId, SimpleEventEmitter<EdibleEvent> edibleEvents) {
        this.gameId = gameId;
        this.boardState = new SimpleBoardState(10, 10); // TODO temporary fix
        this.edibleEvents = edibleEvents;
        this.viewerService = new SimpleViewerService(null, cellEvents, edibleEvents);
    }

    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public void grow(Edible edible) {
        boardState.invokeEdibleEffect(edible);//TODO inform gameHostService
    }

    public MoveStatus makeMove() {
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
        return moveStatus;
    }
}
