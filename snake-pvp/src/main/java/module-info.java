module com.example.snakepvp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;

    opens com.example.snakepvp to javafx.fxml;
    exports com.example.snakepvp;
}