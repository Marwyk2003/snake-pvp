module com.example.snakepvp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;
    requires de.saxsys.mvvmfx;

    opens com.example.snakepvp to javafx.fxml, de.saxsys.mvvmfx;
    exports com.example.snakepvp;
    opens com.example.snakepvp.views to javafx.fxml, de.saxsys.mvvmfx;
    exports com.example.snakepvp.views;
}