package com.example.snakepvp;

import com.example.snakepvp.core.Player;
import com.example.snakepvp.services.GameHostService;
import com.example.snakepvp.services.GameService;
import com.example.snakepvp.viewmodels.HelloViewModel;
import com.example.snakepvp.viewmodels.SingleGameViewModel;
import com.example.snakepvp.views.Game;
import com.example.snakepvp.views.Hello;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Program extends Application {
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        HelloViewModel helloVM = new HelloViewModel(this); // TODO create multiple single game viewmodels
        ViewTuple<Hello, HelloViewModel> viewTuple = FluentViewLoader.fxmlView(Hello.class).viewModel(helloVM).load();
        Parent root = viewTuple.getView();
        this.stage = stage;
        stage.setMinHeight(600);
        stage.setMinWidth(740);
        stage.getIcons().add(new Image("/icon.png"));
        stage.setTitle("SnakePvP");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loadGame() {
        // TODO load game host instead
        Player player = new Player();
        GameHostService gameHostService = new GameHostService();
        GameService gameService = gameHostService.connectPlayer(player);
        SingleGameViewModel singleGameVM = new SingleGameViewModel(gameService); // TODO create multiple single game viewmodels
        ViewTuple<Game, SingleGameViewModel> viewTuple = FluentViewLoader.fxmlView(Game.class).viewModel(singleGameVM).load();
        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();
        gameHostService.start(); // TODO: move somewhere (start when all players connected)
    }
}