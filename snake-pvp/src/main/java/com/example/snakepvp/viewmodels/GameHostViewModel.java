package com.example.snakepvp.viewmodels;

import com.example.snakepvp.SceneController;
import com.example.snakepvp.services.*;
import de.saxsys.mvvmfx.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameHostViewModel implements ViewModel {
    private final List<SingleGameViewModel> singleGameVMs;
    private final SimpleViewerService viewerService;
    private SceneController sceneController;

    public GameHostViewModel(GameHostService gameHostService) {
        this.singleGameVMs = new ArrayList<>();
        this.viewerService = gameHostService.viewerService;

        this.viewerService.statusEvents().subscribe(this::processGameStatusEvents);
        this.viewerService.edibleEvents().subscribe(this::processEdibleEvent);
    }

    public void connectSingleGameVM(SingleGameViewModel viewModel) {
        singleGameVMs.add(viewModel);
    }

    void processGameStatusEvents(GameStatusEvent event) {
        if (event instanceof GameEndedEvent e) {
            ArrayList<Integer> score = new ArrayList<>();
            for (SingleGameViewModel sg : singleGameVMs) {
                sg.endGame();
                score.add(sg.scoreProperty().get());
            }
            sceneController.loadGameOverScene(e.getGameId(), score);
        }
    }

    public void setSkins(List<Integer> skins) {
        for (int i = 0; i < skins.size(); ++i) {
            singleGameVMs.get(i).setSkin(skins.get(i));
        }
    }

    private void processEdibleEvent(EdibleEvent event) {
        int gameId = event.getGameId();
        int nextGameId = (gameId + 1) % singleGameVMs.size();
        singleGameVMs.get(gameId).generateEdibleEvent(event);
        singleGameVMs.get(nextGameId).growSnakeEvent(event);
    }

    public SingleGameViewModel getSingleGameVM(int i) {
        return singleGameVMs.get(i);
    }

    public void startGame() {
        for (SingleGameViewModel game : singleGameVMs) {
            Thread t = new Thread(game::run);
            t.start();
        }
    }

    public void initGame() {
        for (SingleGameViewModel sg : singleGameVMs) {
            sg.initialize();
        }
    }

    public SceneController getSceneController() {
        return sceneController;
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
}
