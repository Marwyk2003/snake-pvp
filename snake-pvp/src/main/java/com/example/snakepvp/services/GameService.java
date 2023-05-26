package com.example.snakepvp.services;

import com.example.snakepvp.core.*;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class GameService {
    private final Player player;
    Direction direction;
    CyclicBarrier cyclicBarrier;
    private BoardState boardState;
    private Snake snake;

    public GameService(Player player, CyclicBarrier cyclicBarrier) {
        this.player = player;
        this.cyclicBarrier = cyclicBarrier;
    }

    void newGame(int width, int height) {
        // TODO: quit existing game
        this.boardState = new SimpleBoardState(width, height);
    }

    void run() {
        try {
            cyclicBarrier.await();
            makeMove();
        } catch (InterruptedException | BrokenBarrierException e) {
            // TODO
        }
    }

    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    public Player getPlayer() {
        return player;
    }

    public BoardState getBoardState() {return boardState;}

    MoveStatus makeMove() {
        Direction dir = this.direction;
        this.direction = Direction.FORWARD; // reset direction
        return boardState.makeMove(dir);
    }
}
