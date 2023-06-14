package com.example.snakepvp.views;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
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
        anchors = new HashMap<>();
        Node[] nodes = {gameWonLabel1, gameWonLabel2, coin1, coin2, coin3};
        for (Node node : nodes) {
            double l = AnchorPane.getLeftAnchor(node) == null ? 0.0 : AnchorPane.getLeftAnchor(node);
            double r = AnchorPane.getRightAnchor(node) == null ? 0.0 : AnchorPane.getRightAnchor(node);
            double b = AnchorPane.getBottomAnchor(node) == null ? 0.0 : AnchorPane.getBottomAnchor(node);
            double t = AnchorPane.getTopAnchor(node) == null ? 0.0 : AnchorPane.getTopAnchor(node);
            anchors.put(node, new Double[]{l, r, t, b});
        }

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
    void refreshLabels(int fontSize) {
        String format = "-fx-font-size: " + fontSize * 80 / 55 + "px";
        gameWonLabel1.setStyle(format);
        gameWonLabel2.setStyle(format);
        gameWonLabel2.setText("score: " + viewModel.getPoints(0));
    }
}