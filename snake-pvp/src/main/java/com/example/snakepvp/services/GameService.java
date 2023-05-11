package com.example.snakepvp.services;

import com.example.snakepvp.core.BoardState;
import com.example.snakepvp.core.Direction;
import com.example.snakepvp.core.Player;
import com.example.snakepvp.core.SimpleBoardState;

public class GameService {
    private final Player player;
    private BoardState boardState;

    public GameService(Player player) {
        this.player = player;
    }

    void newGame(int width, int height) {
        // TODO: quit existing game
        this.boardState = new SimpleBoardState(width, height);
    }

    void start() {
        // TODO call makeMove in some fixed time interval
    }

    Player getPlayer() {
        return player;
    }

    boolean makeMove(Direction dir) {
        return boardState.makeMove(dir);
    }
}
