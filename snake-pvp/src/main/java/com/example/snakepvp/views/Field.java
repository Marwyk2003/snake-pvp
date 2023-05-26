package com.example.snakepvp.views;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Field extends Button {
    Field(String image) {
        this.setGraphic(new ImageView(new Image(image)));
        this.setStyle("-fx-background-color: transparent");
        // bind cell to viewmodel cell instead of manually changing image
    }

    void setImage(String image) {
        this.setGraphic(new ImageView(new Image(image)));
    }
}