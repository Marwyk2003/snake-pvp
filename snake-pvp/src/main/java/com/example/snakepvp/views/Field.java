package com.example.snakepvp.views;

import com.example.snakepvp.core.Edible;
import com.example.snakepvp.viewmodels.SingleGameViewModel.VMCell;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Field extends Button {
    final SimpleBooleanProperty isActive;
    String image;

    private boolean isGoThrough;
    private boolean isSnake;
    private Edible edible;

    Field() {
        image = "/empty.png";
        isActive = new SimpleBooleanProperty(true);
        this.setGraphic(new ImageView(new Image(image)));
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
        image = getImage();
        Platform.runLater(() -> setGraphic(new ImageView(new Image(image))));
    }

    private String getImage() {
        if (isSnake) return "/skin22.png";  // 2-digit for smaller versions
        if (!isGoThrough) return "/empty.png";
        if (edible != null) {
            String newImage;
            switch (edible) {
                case SIMPLE_GROWING -> image = "/shroom.png";
            }
            edible = null;
            return "/shroom.png";
        }
        return "/field.png";
    }
}