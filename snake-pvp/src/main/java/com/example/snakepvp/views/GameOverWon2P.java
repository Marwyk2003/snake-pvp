package com.example.snakepvp.views;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameOverWon2P extends GameEnd {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = viewModel.getSceneController().getStage();

        returnButton.setGraphic(new ImageView(new Image("/return.png")));
        spotlight.getPoints().setAll(125.0, 0.0, 450.0, 0.0, 450.0, 700.0, 10.0, 700.0);
        spotlight.setFill(Color.WHITE);
        anchors = new HashMap<>();
        Node[] nodes = {gameWonLabel1, gameWonLabel2, gameLostLabel1, gameLostLabel2, coin1, coin2, coin3, trash};
        for (Node node : nodes) {
            double l = AnchorPane.getLeftAnchor(node) == null ? 0.0 : AnchorPane.getLeftAnchor(node);
            double r = AnchorPane.getRightAnchor(node) == null ? 0.0 : AnchorPane.getRightAnchor(node);
            double b = AnchorPane.getBottomAnchor(node) == null ? 0.0 : AnchorPane.getBottomAnchor(node);
            double t = AnchorPane.getTopAnchor(node) == null ? 0.0 : AnchorPane.getTopAnchor(node);
            anchors.put(node, new Double[]{l, r, t, b});
        }

        addChangeListener();
        runCoinAnimation("M");
        runTrashAnimation("M");
    }

    void refreshImages(double ratio) {
        String imageSize = "M";
        if (stage.getHeight() > 900 || stage.getWidth() > 1400) imageSize = "XL";
        else if (stage.getHeight() > 800 || stage.getWidth() > 1200) imageSize = "L";
        else {
            AnchorPane.setRightAnchor(coin1, 50.0);
            AnchorPane.setRightAnchor(coin2, 140.0);
            AnchorPane.setRightAnchor(coin3, 230.0);
        }
        trash.setImage(new Image("/trash" + imageSize + ".png"));
        coin1.setImage(new Image("/coin1" + imageSize + ".png"));
        coin2.setImage(new Image("/coin1" + imageSize + ".png"));
        coin3.setImage(new Image("/coin1" + imageSize + ".png"));
    }

    void refreshShape(double ratio) {
        spotlight.getPoints().setAll(125.0 * ratio, 0.0, 450.0 * ratio, 0.0, 450.0 * ratio, 700.0 * ratio, 10.0, 700.0 * ratio);
    }

    void refreshLabels(int fontSize) {
        String format = "-fx-font-size: " + fontSize + "px";
        gameWonLabel1.setStyle(format);
        gameWonLabel2.setStyle(format);
        gameLostLabel1.setStyle(format);
        gameLostLabel2.setStyle(format);
        gameWonLabel2.setText("score: " + viewModel.getPoints(1));
        gameLostLabel2.setText("score: " + viewModel.getPoints(0));
    }
}