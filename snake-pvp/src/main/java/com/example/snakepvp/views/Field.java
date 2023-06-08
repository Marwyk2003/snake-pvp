package com.example.snakepvp.views;

import com.example.snakepvp.core.CellContent;
import com.example.snakepvp.viewmodels.SingleGameViewModel.VMCell;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Field extends Button {
    // TODO make snake size equal to tile size
    CellContent cellContent;

    Field() {
        this.setGraphic(new ImageView(new Image("/empty.png")));
        this.setStyle("-fx-background-color: transparent");
    }

    void bind(VMCell cell) {
        cell.cellContentProperty().addListener((o, oldVal, newVal) -> {
            cellContent = newVal;
            refreshImage();
        });
        cellContent = cell.cellContentProperty().get();
        refreshImage();
    }

    void refreshImage() {
        Platform.runLater(() -> setGraphic(new ImageView(new Image(getImage()))));
    }

    private String getImage() {
        if (cellContent == CellContent.SNAKE) return "/skin2s.png";  // s for smaller versions (50x50)
        if (cellContent == CellContent.WALL) return "/empty.png";
        if (cellContent == CellContent.EDIBLE_GROW) return "/shroom.png";
        return "/field.png";
    }
}