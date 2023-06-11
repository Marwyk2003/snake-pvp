package com.example.snakepvp.viewmodels;

import com.example.snakepvp.SceneController;
import de.saxsys.mvvmfx.ViewModel;

import java.util.List;

public class GameOverViewModel implements ViewModel {
    private List<Integer> points;
    private SceneController sceneController;

    public void changeScore(List<Integer> points) {
        this.points = points;
    }

    public int getPoints(int gameId) {
        return points.get(gameId);
    }

    public SceneController getSceneController() {
        return sceneController;
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
}
