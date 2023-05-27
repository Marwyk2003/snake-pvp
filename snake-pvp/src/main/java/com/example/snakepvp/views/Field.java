package com.example.snakepvp.views;

import com.example.snakepvp.core.Edible;
import com.example.snakepvp.viewmodels.SingleGameViewModel.VMCell;
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
        // TODO bind edibles
    }

    private void refreshImage() {
        image = getImage();
        setGraphic(new ImageView(new Image(image)));
    }

    private String getImage() {
        if (isSnake) return "/shroom.png";
        if (!isGoThrough) return "/quit.png";
        return "/empty.png";
    }
}