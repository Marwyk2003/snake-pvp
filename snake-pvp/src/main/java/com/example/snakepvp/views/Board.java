package com.example.snakepvp.views;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class Board extends GridPane {
    int rows;
    int columns;

    Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

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
        // Moved to viewmodel
        // Instead create binding with e.g. lambda Cell -> picture
        // What if field content changes? Should we create a new field or change the picture in existing one?
        for (int i = 0; i < rows * columns; i++) {
            Field field;
            if (i == 13) field = new Field("/shroom.png");
            else field = new Field("/empty.png");
            field.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            this.add(field, i % columns, i / rows);
        }
    }
}
