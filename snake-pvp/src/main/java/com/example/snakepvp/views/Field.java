package com.example.snakepvp.views;

import com.example.snakepvp.viewmodels.SingleGameViewModel.VMCell;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Field extends Button {
    String image;
    final SimpleBooleanProperty isActive;
    Field field = this;
    Field() {
        image = "/empty.png";
        isActive = new SimpleBooleanProperty(true);
        this.setGraphic(new ImageView(new Image(image)));
        this.setStyle("-fx-background-color: transparent");
    }

    void bind(VMCell cell) {
        cell.isGoThroughProperty().addListener(new ChangeListener() {
            Field f = field;
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal) {
                f.image = f.image.equals("/quit.png") ? "/empty.png" : "/quit.png";
                f.setGraphic(new ImageView(new Image(f.image)));
            }
        });
    }
}