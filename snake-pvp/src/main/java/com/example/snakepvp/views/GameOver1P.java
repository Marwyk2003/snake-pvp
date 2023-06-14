package com.example.snakepvp.views;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameOver1P extends GameEnd {
    @FXML
    private Circle spotlight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = viewModel.getSceneController().getStage();

        returnButton.setGraphic(new ImageView(new Image("/return.png")));
        spotlight.setRadius(250.0);
        spotlight.setFill(Color.WHITE);
        anchors = new HashMap<>();
        Node[] nodes = {gameLostLabel1, gameLostLabel2,trash, spotlight};
        for (Node node : nodes) {
            double l = AnchorPane.getLeftAnchor(node) == null ? 0.0 : AnchorPane.getLeftAnchor(node);
            double r = AnchorPane.getRightAnchor(node) == null ? 0.0 : AnchorPane.getRightAnchor(node);
            double b = AnchorPane.getBottomAnchor(node) == null ? 0.0 : AnchorPane.getBottomAnchor(node);
            double t = AnchorPane.getTopAnchor(node) == null ? 0.0 : AnchorPane.getTopAnchor(node);
            anchors.put(node, new Double[]{l, r, t, b});
        }

        addChangeListener();
        runTrashAnimation("XL");
    }
    void refreshShape(double ratio) {
        spotlight.setRadius(spotlight.getRadius() * ratio);
    }
    void refreshImages(double ratio) {
        String imageSize = "XL";
        if (stage.getHeight() > 850 || stage.getWidth() > 1200) imageSize = "XXL";
        trash.setImage(new Image("/trash" + imageSize + ".png"));
    }
    void refreshLabels(int fontSize) {
        gameLostLabel1.setStyle("-fx-font-size: " + 80 * fontSize/55 + "px");
        gameLostLabel2.setStyle("-fx-font-size: " + 80 * fontSize/55 + "px");
        gameLostLabel2.setText("score: " + viewModel.getPoints(0));
    }
}