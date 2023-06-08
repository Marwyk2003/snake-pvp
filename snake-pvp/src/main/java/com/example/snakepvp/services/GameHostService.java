package com.example.snakepvp.services;

import java.util.ArrayList;
import java.util.List;


public class GameHostService {
    private final SimpleEventEmitter<GameStatusEvent> statusEmitter = new SimpleEventEmitter<>();
    private final SimpleEventEmitter<EdibleEvent> edibleEmitter = new SimpleEventEmitter<>();
    public SimpleViewerService viewerService = new SimpleViewerService(statusEmitter, null, edibleEmitter);
    List<GameService> gameList;

    public GameHostService() {
        gameList = new ArrayList<>();
    }

    public GameService connectNewGame() {
        GameService game = new GameService(gameList.size(), statusEmitter, edibleEmitter);
        this.gameList.add(game);
        return game;
    }
}
