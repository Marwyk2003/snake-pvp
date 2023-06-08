package com.example.snakepvp.viewmodels;

import com.example.snakepvp.SceneController;
import com.example.snakepvp.services.*;
import de.saxsys.mvvmfx.ViewModel;

public class GameHostViewModel implements ViewModel {
    private final GameHostService gameHostService;
    private final SingleGameViewModel[] singleGameVMs;
    private final SimpleViewerService viewerService;
    private SceneController sceneController;
    private boolean isStarted;

    public GameHostViewModel(GameHostService gameHostService, SingleGameViewModel[] singleGameVMs) {
        this.gameHostService = gameHostService;
        this.singleGameVMs = singleGameVMs;
        this.viewerService = gameHostService.viewerService;

        this.viewerService.statusEvents().subscribe(this::processGameStatusEvents);
        this.viewerService.edibleEvents().subscribe(this::processEdibleEvent);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    void processGameStatusEvents(GameStatusEvent event) {
        if (event instanceof GameStartedEvent && !isStarted) {
            for (SingleGameViewModel game : singleGameVMs) {
                Thread t = new Thread(game::run);
                t.start();
                isStarted = true;
            }
        }
    }

    private void processEdibleEvent(EdibleEvent event) {
        int gameId = event.getGameId();
        int nextGameId = (gameId + 1) % singleGameVMs.length;
        singleGameVMs[gameId].generateEdibleEvent(event);
        singleGameVMs[nextGameId].growSnakeEvent(event);
    }


    public SingleGameViewModel getSingleGameVM(int i) {
        return singleGameVMs[i];
    }

    public void startGame() {
        gameHostService.start();
    }
}
