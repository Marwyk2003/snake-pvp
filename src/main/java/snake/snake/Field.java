package snake.snake;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Field extends Button {
    Field(String image) {
        this.setGraphic(new ImageView(new Image(image)));
        this.setStyle("-fx-background-color: transparent");
    }

    void setImage(String image) {
        this.setGraphic(new ImageView(new Image(image)));
    }
}