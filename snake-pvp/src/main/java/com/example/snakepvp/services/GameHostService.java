package com.example.snakepvp.services;

import com.example.snakepvp.core.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;


public class GameHostService {
    int WIDTH = 10; // TODO Move somewhere
    int HEIGHT = 10;

    List<GameService> gameList;
    List<Thread> threadList;

    CyclicBarrier cyclicBarrier;
    boolean isGameOver;


    public GameHostService() {
        gameList = new ArrayList<>();
        threadList = new ArrayList<>();
    }

    public GameService connectPlayer(Player player) {
        GameService game = new GameService(player);
        this.gameList.add(game);
        return game;
    }

    public void start() {
        for (GameService game : gameList) {
            game.newGame(WIDTH, HEIGHT);
            Thread t = new Thread(game::run);
            threadList.add(t);
            t.start();
        }
    }

    private void endGame() {
        for (Thread t : threadList) {
            t.interrupt();
        }
    }
}
