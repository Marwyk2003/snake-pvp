package com.example.snakepvp.services;

import com.example.snakepvp.core.Player;

public interface GameService {
    void newGame(int width, int height);
    PlayerService connectPlayer(Player player);
    ViewerService connectViewer();
}
