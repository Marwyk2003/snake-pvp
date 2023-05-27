package com.example.snakepvp.views;

import com.example.snakepvp.core.Edible;
import com.example.snakepvp.viewmodels.SingleGameViewModel.VMCell;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Field extends Button {
    String image;

    private boolean isGoThrough;
    private boolean isSnake;
    private Edible edible = null;

    Field() {
        image = "/empty.png";
        this.setGraphic(new ImageView(new Image(image)));
        this.setStyle("-fx-background-color: transparent");
    }

    void bind(VMCell cell) {
        isGoThrough = cell.isGoThroughProperty().get();
        cell.isGoThroughProperty().addListener((o, oldVal, newVal) -> {
            System.out.println(newVal);
            isGoThrough = newVal;
            refreshImage();
        });
        isSnake = cell.isSnakeProperty().get();
        cell.isSnakeProperty().addListener((o, oldVal, newVal) -> {
            isSnake = newVal;
            refreshImage();
        });
        edible = cell.edibleProperty().get();
        cell.edibleProperty().addListener((o, oldVal, newVal) -> {
            edible = newVal;
            refreshImage();
        });
        refreshImage();
    }

    private void refreshImage() {
        image = getImage();
        setGraphic(new ImageView(new Image(image)));
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