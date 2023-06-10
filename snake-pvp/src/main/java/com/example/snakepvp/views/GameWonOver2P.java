package com.example.snakepvp.views;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GameWonOver2P extends GameEnd {
    @FXML
    protected Polygon spotlight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = viewModel.getSceneController().getStage();

        returnButton.setGraphic(new ImageView(new Image("/return.png")));
        spotlight.getPoints().setAll(0.0, 0.0, 425.0, 0.0, 540.0, 700.0, 0.0, 700.0);
        spotlight.setFill(Color.WHITE);
        anchors = new HashMap<>() {{
            put(gameWonLabel1, new Double[]{60.0, 0.0, 100.0, 0.0});      // left, right, top, bottom
            put(gameWonLabel2, new Double[]{60.0, 0.0, 145.0, 0.0});
            put(gameLostLabel1, new Double[]{0.0, 60.0, 100.0, 00.0});
            put(gameLostLabel2, new Double[]{0.0, 60.0, 145.0, 0.0});
            put(coin1, new Double[]{50.0, 0.0, 0.0, 140.0});
            put(coin2, new Double[]{140.0, 0.0, 0.0, 140.0});
            put(coin3, new Double[]{230.0, 0.0, 0.0, 140.0});
            put(trash, new Double[]{0.0, 60.0, 0.0, 70.0});
        }};

        addChangeListener();
        runCoinAnimation("M");
        runTrashAnimation("M");
    }
    void refreshImages(double ratio) {
        String imageSize = "M";
        if (stage.getHeight() > 900 || stage.getWidth() > 1400) imageSize = "XL";
        else if (stage.getHeight() > 800 || stage.getWidth() > 1200) imageSize = "L";
        else {
            AnchorPane.setLeftAnchor(coin1, 50.0);
            AnchorPane.setLeftAnchor(coin2, 140.0);
            AnchorPane.setLeftAnchor(coin3, 230.0);
        }
        trash.setImage(new Image("/trash" + imageSize + ".png"));
        coin1.setImage(new Image("/coin1" + imageSize + ".png"));
        coin2.setImage(new Image("/coin1" + imageSize + ".png"));
        coin3.setImage(new Image("/coin1" + imageSize + ".png"));
    }
    void refreshShape(double ratio) {
        spotlight.getPoints().setAll(0.0, 0.0, 425.0 * ratio, 0.0, 540.0 * ratio, 700.0 * ratio, 0.0, 700.0 * ratio);
    }
    void setLabels(int fontSize) {
        String format = "-fx-font-size: " + fontSize + "px";
        gameWonLabel1.setStyle(format);
        gameWonLabel2.setStyle(format);
        gameLostLabel1.setStyle(format);
        gameLostLabel2.setStyle(format);
    }
}