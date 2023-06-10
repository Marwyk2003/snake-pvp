package com.example.snakepvp.views;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        anchors = new HashMap<>() {{
            put(gameLostLabel1, new Double[]{160.0, 0.0, 100.0, 0.0});      // left, right, top, bottom
            put(gameLostLabel2, new Double[]{160.0, 0.0, 150.0, 0.0});
            put(trash, new Double[]{120.0, 0.0, 0.0, 60.0});
            put(spotlight, new Double[]{0.0, 50.0, 0.0, 80.0});
        }};

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
    void setLabels(int fontSize) {
        gameLostLabel1.setStyle("-fx-font-size: " + 80 * fontSize/55 + "px");
        gameLostLabel2.setStyle("-fx-font-size: " + 80 * fontSize/55 + "px");
    }
}