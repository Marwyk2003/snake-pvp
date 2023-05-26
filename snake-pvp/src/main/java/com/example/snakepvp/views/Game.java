package com.example.snakepvp.views;

import com.example.snakepvp.viewmodels.SingleGameViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Game implements FxmlView<SingleGameViewModel>, Initializable {

    @InjectViewModel
    private SingleGameViewModel viewModel; // hope it works

    @FXML
    private Label countDownLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize game board from model etc.
        runTimer();
    }

    @FXML
    private void mouseAction(MouseEvent event) throws Exception {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }

    private void startGame() throws IOException {
        // Maybe move initialization to initialize and leave only what needs to be here for starting game? (e.g. stage.show())
        Stage stage = (Stage) countDownLabel.getScene().getWindow();
        Board grid = new Board(viewModel.getHeight(), viewModel.getWidth()); // The data comes from viewmodel
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
}