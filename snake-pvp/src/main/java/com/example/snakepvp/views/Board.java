package com.example.snakepvp.views;

import com.example.snakepvp.viewmodels.SingleGameViewModel;
import de.saxsys.mvvmfx.ViewModel;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class Board extends GridPane {
    int skin;
    int rows;
    int columns;
    private final Field[][] fields;

    Board(int rows, int columns, int skin) {
        this.rows = rows;
        this.columns = columns;
        this.skin = skin;
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

        for (int i = 0; i < rows * columns; i++) {
            Field field = new Field(skin);
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