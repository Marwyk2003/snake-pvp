package com.example.snakepvp.services;

import com.example.snakepvp.core.*;

public class GameService {
    public final int timeout = 500;
    private final Player player;
    private final SimpleEventEmitter<CellEvent> cellEvents = new SimpleEventEmitter<>();
    private final SimpleEventEmitter<EdibleEvent> edibleEvents = new SimpleEventEmitter<>();
    public SimpleViewerService viewerService = new SimpleViewerService(null, cellEvents, edibleEvents);
    Direction direction = Direction.DOWN; // TODO
    private final BoardState boardState;

    public GameService(Player player) {
        this.player = player;
        this.boardState = new SimpleBoardState(10, 10); // TODO temporary fix
        boardState.generateEdible(Edible.SIMPLE_GROWING); //TODO
    }

    void newGame(int width, int height) {
        // TODO: quit existing game
        //this.boardState = new SimpleBoardState(width, height);
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
        Edible eaten = moveStatus.getEdible();
        if (eaten != null) {
            System.out.println("just ate");
            Cell cell = boardState.generateEdible(eaten);//TODO randomize edibles
            boardState.invokeEdibleEffect(eaten);//TODO inform gameHostService
            edibleEvents.emit(new EdibleEvent(eaten, cell.getRow(), cell.getCol()));
        }
        if (moveStatus.getTail() != null)
            cellEvents.emit(new CellEvent(moveStatus.getTail().getCol(), moveStatus.getTail().getRow(), moveStatus.getTail().isSnake()));
        if (moveStatus.getHead() != null)
            cellEvents.emit(new CellEvent(moveStatus.getHead().getCol(), moveStatus.getHead().getRow(), moveStatus.getHead().isSnake()));
        return moveStatus;
    }
}
