package com.example.snakepvp.services;

import com.example.snakepvp.core.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;


public class GameHostService {
    int WIDTH = 10; // TODO Move somewhere
    int HEIGHT = 10;

    List<GameService> gameList;
    List<Thread> threadList;

    Thread gameHostThread;
    CyclicBarrier cyclicBarrier;
    boolean isGameOver;


    public GameHostService() {
        gameList = new ArrayList<>();
    }

    public GameService connectPlayer(Player player) {
        GameService game = new GameService(player, cyclicBarrier);
        this.gameList.add(game);
        return game;
    }

    void start() {
        isGameOver = false;
        cyclicBarrier = new CyclicBarrier(gameList.size() + 1);
        gameHostThread = new Thread(this::run);
        for (GameService game : gameList) {
            game.newGame(WIDTH, HEIGHT);
            Thread t = new Thread(game::run);
            threadList.add(t);
        }
        gameHostThread.start();
    }

    void run() {
        for (Thread t : threadList)
            t.start();
        try {
            while (!isGameOver) {
                TimeUnit.SECONDS.sleep(1);
                cyclicBarrier.await();
                // TODO check if game is over etc
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            isGameOver = true;
        }
    }
}
