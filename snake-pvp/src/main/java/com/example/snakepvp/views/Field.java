package com.example.snakepvp.views;

import com.example.snakepvp.core.Edible;
import com.example.snakepvp.viewmodels.SingleGameViewModel.VMCell;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Field extends Button {
    // TODO make snake size equal to tile size
    int snakeSkin;
    private boolean isGoThrough;
    private boolean isSnake;
    private Edible edible;

    Field() {
        this.setGraphic(new ImageView(new Image("/empty.png")));
        this.setStyle("-fx-background-color: transparent");
    }

    void bind(VMCell cell) {
        cell.isGoThroughProperty().addListener((o, oldVal, newVal) -> {
            isGoThrough = newVal;
            refreshImage();
        });
        cell.isSnakeProperty().addListener((o, oldVal, newVal) -> {
            isSnake = newVal;
            refreshImage();
        });
        cell.edibleProperty().addListener((o, oldVal, newVal) -> {
            edible = newVal;
            refreshImage();
        });

        isGoThrough = cell.isGoThroughProperty().get();
        isSnake = cell.isSnakeProperty().get();
        edible = cell.edibleProperty().get();

        refreshImage();
    }

    void refreshImage() {
        Platform.runLater(() -> setGraphic(new ImageView(new Image(getImage()))));
    }

    private String getImage() {
        if (isSnake) return "/skin2s.png";  // s for smaller versions (50x50)
        if (!isGoThrough) return "/empty.png";
        if (edible != null) {
            String newImage;
            switch (edible) {
                case SIMPLE_GROWING -> { return "/shroom.png"; }
            }
            edible = null;
            return "/shroom.png";
        }
        return "/field.png";
    }
}