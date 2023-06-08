package com.example.snakepvp.viewmodels;

import com.example.snakepvp.SceneController;
import com.example.snakepvp.services.GameHostService;
import com.example.snakepvp.services.GameStartedEvent;
import com.example.snakepvp.services.GameStatusEvent;
import com.example.snakepvp.services.SimpleViewerService;
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


    public SingleGameViewModel getSingleGameVM(int i) {
        return singleGameVMs[i];
    }

    public void startGame() {
        gameHostService.start();
    }
}
