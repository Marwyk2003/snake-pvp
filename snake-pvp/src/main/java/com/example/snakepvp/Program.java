package com.example.snakepvp;

import com.example.snakepvp.services.GameHostService;
import com.example.snakepvp.services.GameService;
import com.example.snakepvp.viewmodels.GameHostViewModel;
import com.example.snakepvp.viewmodels.GameOverViewModel;
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
        stage.setMinHeight(700);
        stage.setMinWidth(1000);
        stage.getIcons().add(new Image("/icon.png"));
        stage.setTitle("SnakePvP");

        GameHostService gameHostService = new GameHostService();
        GameHostViewModel gameHostVM = new GameHostViewModel(gameHostService);

        for (int i = 0; i < PLAYER_COUNT; ++i) {
            GameService gameService = gameHostService.connectNewGame();
            SingleGameViewModel singleGameVM = new SingleGameViewModel(gameService);
            gameHostVM.connectSingleGameVM(singleGameVM);
        }

        HelloViewModel helloVM = new HelloViewModel();
        GameOverViewModel gameOverVM = new GameOverViewModel();
        SceneController sceneController = new SceneController(stage, helloVM, gameHostVM, gameOverVM);
        sceneController.loadHelloScene();
    }
}