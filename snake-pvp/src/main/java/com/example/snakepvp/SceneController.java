package com.example.snakepvp;

import com.example.snakepvp.viewmodels.GameHostViewModel;
import com.example.snakepvp.viewmodels.HelloViewModel;
import com.example.snakepvp.views.Game;
import com.example.snakepvp.views.Hello;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    private final HelloViewModel helloVM;
    private final GameHostViewModel gameHostVM;
    private final Stage stage;


    SceneController(Stage stage, HelloViewModel helloVM, GameHostViewModel gameHostVM) {
        this.stage = stage;
        this.helloVM = helloVM;
        this.gameHostVM = gameHostVM;

        helloVM.setSceneController(this);
        gameHostVM.setSceneController(this);
    }

    public void loadHelloScene() {
        ViewTuple<Hello, HelloViewModel> viewTuple = FluentViewLoader.fxmlView(Hello.class).viewModel(helloVM).load();
        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loadGameScene() {
        ViewTuple<Game, GameHostViewModel> viewTuple = FluentViewLoader.fxmlView(Game.class).viewModel(gameHostVM).load();
        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
