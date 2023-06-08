package com.example.snakepvp.services;

import com.example.snakepvp.core.Player;

import java.util.ArrayList;
import java.util.List;


public class GameHostService {
    private final SimpleEventEmitter<GameStatusEvent> gameEmitter = new SimpleEventEmitter<>();
    public SimpleViewerService viewerService = new SimpleViewerService(gameEmitter, null, null);
    int WIDTH = 10; // TODO Move somewhere
    int HEIGHT = 10;
    List<GameService> gameList;


    public GameHostService() {
        gameList = new ArrayList<>();
    }

    public GameService connectPlayer(Player player) {
        GameService game = new GameService(player);
        this.gameList.add(game);
        return game;
    }

    public void start() {
        for (GameService game : gameList) {
            game.newGame(WIDTH, HEIGHT);
        }
        gameEmitter.emit(new GameStartedEvent());
    }

    private void endGame() {
        gameEmitter.emit(new GameEndedEvent());
    }
}
