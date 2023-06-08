package com.example.snakepvp.viewmodels;

import com.example.snakepvp.SceneController;
import de.saxsys.mvvmfx.ViewModel;

public class GameOverViewModel implements ViewModel {
    private SceneController sceneController;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }


    public SceneController getSceneController() {
        return sceneController;
    }
}
