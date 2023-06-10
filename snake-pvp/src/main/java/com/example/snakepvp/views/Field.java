package com.example.snakepvp.views;

import com.example.snakepvp.core.CellContent;
import com.example.snakepvp.viewmodels.SingleGameViewModel.VMCell;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Field extends Button {
    CellContent cellContent;
    final int skin;

    Field(int skin) {
        this.skin = skin;
        this.setGraphic(new ImageView(new Image("/emptyS.png")));
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
        if (cellContent == CellContent.SNAKE) return "/skin" + skin + "S.png";
        if (cellContent == CellContent.WALL) return "/emptyS.png";
        if (cellContent == CellContent.EDIBLE_GROW) return "/doughnutS.png";
//        else if (cellContent == CellContent.POINTS_EDIBLE) return "/appleS.png";
//        else if (cellContent == CellContent.OTHER_EDIBLE) return "/cheeseS.png";
        return "/fieldS.png";
    }
}