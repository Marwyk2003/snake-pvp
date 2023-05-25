module snake.snake {
    requires javafx.controls;
    requires javafx.fxml;


    opens snake.snake to javafx.fxml;
    exports snake.snake;
}