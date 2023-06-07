package com.example.snakepvp.viewmodels;

import com.example.snakepvp.SceneController;
import de.saxsys.mvvmfx.ViewModel;

public class HelloViewModel implements ViewModel {
    SceneController sceneController;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void startGame() {
        sceneController.loadGameScene();
    }
}
