package com.example.snakepvp;

import com.example.snakepvp.core.Player;
import com.example.snakepvp.services.GameHostService;
import com.example.snakepvp.services.GameService;
import com.example.snakepvp.viewmodels.GameHostViewModel;
import com.example.snakepvp.viewmodels.HelloViewModel;
import com.example.snakepvp.viewmodels.SingleGameViewModel;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Program extends Application {
    static final int PLAYER_COUNT = 2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMinHeight(600);
        stage.setMinWidth(740);
        stage.getIcons().add(new Image("/icon.png"));
        stage.setTitle("SnakePvP");

        GameHostService gameHostService = new GameHostService();
        Player[] players = new Player[PLAYER_COUNT];
        GameService[] gameServices = new GameService[PLAYER_COUNT];
        SingleGameViewModel[] gameVMs = new SingleGameViewModel[PLAYER_COUNT];
        for (int i = 0; i < PLAYER_COUNT; ++i) {
            players[i] = new Player();
            gameServices[i] = gameHostService.connectPlayer(players[i]);
            gameVMs[i] = new SingleGameViewModel(gameServices[i], gameServices[i].viewerService);
        }
        GameHostViewModel gameHostVM = new GameHostViewModel(gameHostService, gameVMs);

        HelloViewModel helloVM = new HelloViewModel();
        SceneController sceneController = new SceneController(stage, helloVM, gameHostVM);
        sceneController.loadHelloScene();
    }
}