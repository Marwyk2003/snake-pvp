package com.example.snakepvp.views;

import com.example.snakepvp.core.CellContent;
import com.example.snakepvp.viewmodels.SingleGameViewModel.VMCell;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Field extends Button {
    final int skin;
    private final Game game;
    private final int gameId;
    CellContent cellContent;

    Field(int skin, int gameId, Game game) {
        this.skin = skin;
        this.gameId = gameId;
        this.game = game;
        this.setGraphic(new ImageView(new Image("/emptyS.png")));
        this.setStyle("-fx-background-color: transparent");
    }

    void bind(VMCell cell) {
        cell.cellContentProperty().addListener((o, oldVal, newVal) -> {
            cellContent = newVal;
            refreshImage();
            game.edibleAction(newVal, gameId);
        });
        cellContent = cell.cellContentProperty().get();
        refreshImage();
    }

    void refreshImage() {
        Platform.runLater(() -> setGraphic(new ImageView(new Image(getImage()))));
    }

    private String getImage() {
        if (cellContent == CellContent.SNAKE) return "/skin" + skin + "S.png";
        if (cellContent == CellContent.SNAKE_HEAD) return "/skin" + skin + "SH.png";
        if (cellContent == CellContent.WALL) return "/emptyS.png";
        if (cellContent == CellContent.EDIBLE_GROW) return "/cheeseS.png";
        if (cellContent == CellContent.EDIBLE_SPEED) return "/appleS.png";
        if (cellContent == CellContent.EDIBLE_DOUBLE) return "/doughnutS.png";
        if (cellContent == CellContent.EDIBLE_REVERSE) return "/skin0S.png";
        if (cellContent == CellContent.EDIBLE_DIRECTION) return "/skin1S.png";
        return "/fieldS.png";
    }
}