package com.example.snakepvp.views;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameWon1P extends GameEnd {
    @FXML
    protected Circle spotlight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = viewModel.getSceneController().getStage();

        returnButton.setGraphic(new ImageView(new Image("/return.png")));
        spotlight.setRadius(250.0);
        spotlight.setFill(Color.WHITE);
        anchors = new HashMap<>() {{
            put(gameWonLabel1, new Double[]{0.0, 160.0, 100.0, 0.0});      // left, right, top, bottom
            put(gameWonLabel2, new Double[]{0.0, 160.0, 170.0, 0.0});
            put(coin1, new Double[]{80.0, 0.0, 0.0, 140.0});
            put(coin2, new Double[]{200.0, 0.0, 0.0, 140.0});
            put(coin3, new Double[]{320.0, 0.0, 0.0, 140.0});
        }};

        addChangeListener();
        runCoinAnimation("L");
    }
    void refreshImages(double ratio) {
        String imageSize = "L";
        if (stage.getHeight() > 850 || stage.getWidth() > 1250) imageSize = "XL";
        else {
            AnchorPane.setLeftAnchor(coin1, 50.0);
            AnchorPane.setLeftAnchor(coin2, 170.0);
            AnchorPane.setLeftAnchor(coin3, 290.0);
        }
        coin1.setImage(new Image("/coin1" + imageSize + ".png"));
        coin2.setImage(new Image("/coin1" + imageSize + ".png"));
        coin3.setImage(new Image("/coin1" + imageSize + ".png"));
    }
    void setLabels(int fontSize) {
        String format = "-fx-font-size: " + fontSize * 80 / 55 + "px";
        gameWonLabel1.setStyle(format);
        gameWonLabel2.setStyle(format);
    }
}