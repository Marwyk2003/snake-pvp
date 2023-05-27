package com.example.snakepvp.services;

import com.example.snakepvp.core.*;

import java.util.concurrent.BrokenBarrierException;

public class GameService {
    private final Player player;
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
        return boardState.makeMove(dir);
    }
}
