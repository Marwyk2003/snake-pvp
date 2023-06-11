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
        this.setGraphic(new ImageView(new Image("/emptyM.png")));
        this.setStyle("-fx-background-color: transparent");
    }

    void bind(VMCell cell) {
        cell.cellContentProperty().addListener((o, oldVal, newVal) -> {
            cellContent = newVal;
            refreshImage();
            game.edibleAction(oldVal, gameId);
        });
        cellContent = cell.cellContentProperty().get();
        refreshImage();
    }

    void refreshImage() {
        Platform.runLater(() -> setGraphic(new ImageView(new Image(getImage()))));
    }

    private String getImage() {
        if (cellContent == CellContent.SNAKE) return "/skin" + skin + "M.png";
        if (cellContent == CellContent.SNAKE_HEAD) return "/skin" + skin + "MH.png";
        if (cellContent == CellContent.WALL) return "/emptyM.png";
        if (cellContent == CellContent.HOLE) return "/holeM.png";
        if (cellContent == CellContent.EDIBLE_GROW) return "/doughnut1M.png";
        if (cellContent == CellContent.EDIBLE_SPEED) return "/chiliM.png";
        if (cellContent == CellContent.EDIBLE_DOUBLE) return "/doughnut2M.png";
        if (cellContent == CellContent.EDIBLE_REVERSE) return "/mushroomM.png";
        if (cellContent == CellContent.EDIBLE_DIRECTION) return "/grapeM.png";
        if (cellContent == CellContent.EDIBLE_HOLE) return "/cheeseM.png";
        return "/fieldM.png";
    }
}