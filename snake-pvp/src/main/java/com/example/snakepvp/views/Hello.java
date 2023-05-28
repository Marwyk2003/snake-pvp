package com.example.snakepvp.views;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.snakepvp.viewmodels.HelloViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.*;
import javafx.application.*;


public class Hello implements Initializable, FxmlView<HelloViewModel> {
    @InjectViewModel
    private HelloViewModel viewModel;

    @FXML
    private Label skinChoiceLabel, skinNameLabel;
    String[] names = {"Aqua Worm", "Techno Tangle", "Pinky Python", "Scaly Shrooms", "Mighty Puff", "Stainless Speed"}; // TODO (not for now) move to some config file e.g. json

    @FXML
    private int skin = 0, noSkins = 5;
    @FXML
    private Button modeButton1, modeButton2, quitButton, skinButton1, skinButton2, skinButton3, gameNameButton;

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
        skin = (++skin) % noSkins;
        skinButton1.setGraphic(new ImageView(new Image("/skin" + skin + ".png")));
        skinButton2.setGraphic(new ImageView(new Image("/skin" + skin + ".png")));
        skinButton3.setGraphic(new ImageView(new Image("/skin" + skin + ".png")));
        skinNameLabel.setText(names[skin]);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modeButton1.setGraphic(new ImageView(new Image("/mode1.png")));
        modeButton2.setGraphic(new ImageView(new Image("/mode2.png")));
        quitButton.setGraphic(new ImageView(new Image("/quit.png")));
        skinButton1.setGraphic(new ImageView(new Image("/skin0.png")));
        skinButton2.setGraphic(new ImageView(new Image("/skin0.png")));
        skinButton3.setGraphic(new ImageView(new Image("/skin0.png")));
        gameNameButton.setGraphic(new ImageView(new Image("/gameName.png")));
    }
}