package com.example.snakepvp.views;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class Board extends GridPane {
    private final Field[][] fields;
    int rows;
    int columns;
    // Alternatively, not nice:
    // for (Node node : this.getChildren()) {
    //    if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
    //      result = node;
    //      break;
    //    }
    //  }
    // There probably are better ways to do it

    Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.fields = new Field[rows][columns];

        for (int r = 0; r < rows; r++) {
            RowConstraints rconstraint = new RowConstraints();
            rconstraint.setFillHeight(false);
            rconstraint.setVgrow(Priority.NEVER);
            this.getRowConstraints().add(rconstraint);
        }
        for (int c = 0; c < columns; c++) {
            ColumnConstraints cconstraint = new ColumnConstraints();
            cconstraint.setFillWidth(false);
            cconstraint.setHgrow(Priority.NEVER);
            this.getColumnConstraints().add(cconstraint);
        }
        setBoard();
    }

    void setBoard() {
        for (int i = 0; i < rows * columns; i++) {
            Field field = new Field();
            field.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            this.add(field, i % columns, i / rows);
            fields[i % columns][i / rows] = field;
        }
    }

    void refreshBoard() {
        for (int i = 0; i < rows * columns; i++) {
            fields[i % columns][i / rows].refreshImage();
        }
    }

    Field getField(int row, int column) {
        return fields[row][column];
    }
}