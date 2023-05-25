package snake.snake;

import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class Board extends GridPane {
    int rows;
    int columns;

    Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        for (int r = 0; r < rows ; r++) {
            RowConstraints rconstraint = new RowConstraints();
            rconstraint.setFillHeight(false);
            rconstraint.setVgrow(Priority.NEVER);
            this.getRowConstraints().add(rconstraint);
        }
        for (int c = 0 ; c < columns; c++ ) {
            ColumnConstraints cconstraint = new ColumnConstraints();
            cconstraint.setFillWidth(false);
            cconstraint.setHgrow(Priority.NEVER);
            this.getColumnConstraints().add(cconstraint);
        }
        setBoard();
    }

    void setBoard() {
        for (int i = 0; i < rows * columns; i++) {
            Field field;
            if (i == 13) field = new Field("file:src/shroom.png");
            else field = new Field("file:src/empty.png");
            field.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            this.add(field, i % columns, i / rows);
        }
    }
}
