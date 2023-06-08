package com.example.snakepvp;

import com.example.snakepvp.viewmodels.GameHostViewModel;
import com.example.snakepvp.viewmodels.GameOverViewModel;
import com.example.snakepvp.viewmodels.HelloViewModel;
import com.example.snakepvp.views.Game;
import com.example.snakepvp.views.GameWon1P;
import com.example.snakepvp.views.Hello;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class SceneController {

    private final HelloViewModel helloVM;
    private final Integer[] snakeSkins = new Integer[]{null, null}; // TODO pas trop elegant, mais c'est ainsi
    private final GameHostViewModel gameHostVM;
    private final GameOverViewModel gameOverVM;
    private final Stage stage;


    SceneController(Stage stage, HelloViewModel helloVM, GameHostViewModel gameHostVM, GameOverViewModel gameOverVM) {
        this.stage = stage;
        this.helloVM = helloVM;
        this.gameHostVM = gameHostVM;
        this.gameOverVM = gameOverVM;

        helloVM.setSceneController(this);
        gameHostVM.setSceneController(this);
        gameOverVM.setSceneController(this);
    }

    public Stage getStage() {
        return stage;
    }

    public void loadHelloScene() {
        ViewTuple<Hello, HelloViewModel> viewTuple = FluentViewLoader.fxmlView(Hello.class).viewModel(helloVM).load();
        loadScene(viewTuple);
    }

    public void loadGameScene(List<Integer> skins) {
        gameHostVM.setSkins(skins);
        ViewTuple<Game, GameHostViewModel> viewTuple = FluentViewLoader.fxmlView(Game.class).viewModel(gameHostVM).load();
        loadScene(viewTuple);
    }

    public void loadGameOverScene() {
        ViewTuple<GameWon1P, GameOverViewModel> viewTuple = FluentViewLoader.fxmlView(GameWon1P.class).viewModel(gameOverVM).load();
        loadScene(viewTuple);
    }

    private void loadScene(ViewTuple<?, ?> viewTuple) {
        Parent root = viewTuple.getView();
        Platform.runLater(() -> {
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    public void addSkin(int skin) {
        for (int i = 0; i < snakeSkins.length; i++) {
            if (snakeSkins[i] == null) {
                snakeSkins[i] = skin;
                return;
            }
        }
    }

    public void resetSkins() {
        Arrays.fill(snakeSkins, null);
    }

    public int getSkin(int i) {
        return snakeSkins[i];
    }
}
