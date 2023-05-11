package com.example.snakepvp.services;

import com.example.snakepvp.core.Player;

import java.util.ArrayList;
import java.util.List;

public class GameHostService {
    List<GameService> gameList;

    public GameHostService() {
        gameList = new ArrayList<>();
    }

    GameService connectPlayer(Player player) {
        GameService game = new GameService(player);
        this.gameList.add(game);
        return game;
    }

    void startAll() {
        for (GameService game : gameList) game.start();
        // TODO: Should each game start as individual thread?
    }
}
