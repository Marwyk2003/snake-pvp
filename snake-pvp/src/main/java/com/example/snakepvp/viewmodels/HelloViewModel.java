package com.example.snakepvp.viewmodels;

import com.example.snakepvp.SceneController;
import de.saxsys.mvvmfx.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HelloViewModel implements ViewModel {
    private final List<Integer> skins = new ArrayList<>();
    private SceneController sceneController;

    public SceneController getSceneController() {
        return sceneController;
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void startGame() {
        sceneController.loadGameScene(skins);
    }

    public void addSkins(List<Integer> skins) {
        this.skins.clear();
        this.skins.addAll(skins);
    }
}
