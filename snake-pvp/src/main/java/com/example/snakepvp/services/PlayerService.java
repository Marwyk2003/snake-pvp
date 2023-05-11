package com.example.snakepvp.services;

import com.example.snakepvp.core.Direction;
import com.example.snakepvp.core.Player;

public interface PlayerService extends ViewerService {
    Player getPlayer();
    boolean makeMove(Direction dir);
}
