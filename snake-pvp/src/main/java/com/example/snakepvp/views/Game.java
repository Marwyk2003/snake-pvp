package com.example.snakepvp.views;

import com.example.snakepvp.core.Cell;
import com.example.snakepvp.viewmodels.SingleGameViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableBooleanValue;
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
    private SingleGameViewModel viewModel;
    Board board;

    @FXML
    private Label countDownLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board(viewModel.getHeight(), viewModel.getWidth());
        for (int row = 0; row < viewModel.getHeight(); ++row) {
            for (int col = 0; col < viewModel.getWidth(); ++col) {
                board.getField(row, col).bind(viewModel.getCell(row, col));
                if (row == 5 && (col == 6 || col == 5)) viewModel.getCell(row, col).isGoThrough.set(false); // (am) just checking
            }
        }
        viewModel.getCell(5, 5).isGoThrough.set(true);  // (am) (5, 6) should change once, (5, 5) twice hence no effect
        board.setMaxSize(600, 740);
        board.setAlignment(Pos.CENTER);
        runTimer();
    }

    @FXML
    private void mouseAction(MouseEvent event) throws Exception {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }

    private void startGame() throws IOException {
        Stage stage = (Stage) countDownLabel.getScene().getWindow();
        stage.setScene(new Scene(board));
        stage.show();
    }

    public void runTimer() {
        AtomicInteger timeToStart = new AtomicInteger(3);
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(timeToStart.get());
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(0.7), e -> {
            timeToStart.decrementAndGet();
            if (timeToStart.get() == 0) countDownLabel.setText("Play!");
            else countDownLabel.setText(timeToStart.toString());
        }));
        timeLine.play();
        timeLine.setOnFinished(e -> {
            try {
                startGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}