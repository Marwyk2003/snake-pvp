package com.example.snakepvp.services;

public class GameEndedEvent implements GameStatusEvent {
    private final int gameId;

    public GameEndedEvent(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
