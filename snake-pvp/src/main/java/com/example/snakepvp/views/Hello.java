package com.example.snakepvp.views;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.snakepvp.viewmodels.HelloViewModel;
import com.example.snakepvp.viewmodels.SingleGameViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.application.*;


public class Hello implements Initializable, FxmlView<HelloViewModel> {
    @InjectViewModel
    private HelloViewModel viewModel;

    @FXML
    private Label skinChoiceLabel, skinNameLabel;
    String[] names = {"Aqua Worm", "Pinky Python", "Scaly Shrooms", "Mighty Puff"}; // TODO (not for now) move to some config file e.g. json

    @FXML
    private int skin = 0, noSkins = 4;
    @FXML
    private Button modeButton1, modeButton2, quitButton, skinButton, gameNameButton;

    @FXML
    private void mouseAction (MouseEvent event) throws IOException {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }
    @FXML
    private void modeButtonAction1 (ActionEvent event) throws IOException {
        // TODO (marwyk) add viewmodel(done) and start the game
        viewModel.startGame(); // TODO don't know if it should work this way
    }

    @FXML
    private void quitButtonAction (ActionEvent event) throws IOException {
        Platform.exit();
    }

    @FXML
    private void nextSkinAction (ActionEvent event) throws IOException {
        // TODO change to binding
        // (am) Should it be bound to Field?
        skin = (++skin) % noSkins;
        skinButton.setGraphic(new ImageView(new Image("/skin" + skin + ".png")));
        skinNameLabel.setText(names[skin]);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modeButton1.setGraphic(new ImageView(new Image("/mode1.png")));
        modeButton2.setGraphic(new ImageView(new Image("/mode2.png")));
        quitButton.setGraphic(new ImageView(new Image("/quit.png")));
        skinButton.setGraphic(new ImageView(new Image("/skin0.png")));
        gameNameButton.setGraphic(new ImageView(new Image("/gameName.png")));
    }
}