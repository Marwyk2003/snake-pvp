package com.example.snakepvp.viewmodels;

import com.example.snakepvp.SceneController;
import com.example.snakepvp.services.*;
import de.saxsys.mvvmfx.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameHostViewModel implements ViewModel {
    private final GameHostService gameHostService;
    private final List<SingleGameViewModel> singleGameVMs;
    private final SimpleViewerService viewerService;
    private SceneController sceneController;
    private boolean isStarted;
    private int activeGames;

    public GameHostViewModel(GameHostService gameHostService) {
        this.gameHostService = gameHostService;
        this.singleGameVMs = new ArrayList<>();
        this.viewerService = gameHostService.viewerService;

        this.viewerService.statusEvents().subscribe(this::processGameStatusEvents);
        this.viewerService.edibleEvents().subscribe(this::processEdibleEvent);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void connectSingleGameVM(SingleGameViewModel viewModel) {
        singleGameVMs.add(viewModel);
    }

    void processGameStatusEvents(GameStatusEvent event) {
        if (event instanceof GameEndedEvent e && isStarted) {
            singleGameVMs.get(e.getGameId()).endGame();
            activeGames--;
            if (activeGames == 0) sceneController.loadGameOverScene();
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
            isStarted = true;
        }
    }

    public void initGame() {
        activeGames = singleGameVMs.size();
        for (SingleGameViewModel sg : singleGameVMs) {
            sg.initialize();
        }
    }
}
