package com.example.snakepvp.view;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.animation.*;
import javafx.util.Duration;

public class GetReadyController implements Initializable {
    @FXML
    private Label countDownLabel;

    @FXML
    private void mouseAction (MouseEvent event) throws Exception {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }
    private void startGame() throws IOException {
        Stage stage = (Stage) countDownLabel.getScene().getWindow();
        Board grid = new Board(10, 10);
        grid.setMaxSize(700, 700);
        grid.setAlignment(Pos.CENTER);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

    public void runTimer() {
        AtomicInteger timeToStart = new AtomicInteger(3);
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(timeToStart.get());
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            timeToStart.decrementAndGet();
            if (timeToStart.get() == 0) countDownLabel.setText("Play!");
            else countDownLabel.setText(timeToStart.toString());
        }));
        timeLine.play();
        timeLine.setOnFinished(e -> {
            try {
                countDownLabel.setText("Play!");
                startGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runTimer();
    }
}