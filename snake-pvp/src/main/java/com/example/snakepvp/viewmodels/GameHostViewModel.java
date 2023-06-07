package com.example.snakepvp.viewmodels;

import com.example.snakepvp.SceneController;
import com.example.snakepvp.services.GameHostService;
import de.saxsys.mvvmfx.ViewModel;

public class GameHostViewModel implements ViewModel {
    private final GameHostService gameHostService;
    private final SingleGameViewModel[] singleGameVMs;
    private SceneController sceneController;

    public GameHostViewModel(GameHostService gameHostService, SingleGameViewModel[] singleGameVMs) {
        this.gameHostService = gameHostService;
        this.singleGameVMs = singleGameVMs;
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }


    public SingleGameViewModel getSingleGameVM(int i) {
        return singleGameVMs[i];
    }

    public void startGame() {
        gameHostService.start();
    }
}
