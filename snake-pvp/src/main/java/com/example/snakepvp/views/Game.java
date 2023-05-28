package com.example.snakepvp.views;

import com.example.snakepvp.core.Edible;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Game implements FxmlView<SingleGameViewModel>, Initializable {

    Board board;
    int oldDirection = 0; // 0: UP, 1: RIGHT, 2: DOWN, 3: LEFT
    @InjectViewModel
    private SingleGameViewModel viewModel;
    @FXML
    private Label countDownLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board(viewModel.getHeight(), viewModel.getWidth());
        for (int row = 0; row < viewModel.getHeight(); ++row) {
            for (int col = 0; col < viewModel.getWidth(); ++col) {
                board.getField(row, col).bind(viewModel.getCell(row, col));
                // TODO remove, debug only!
//                if (row == 5 && (col == 6 || col == 5))
//                    viewModel.getCell(row, col).setIsGoThrough(false); // (am) just checking
            }
        }
        // TODO remove, debug only!
        viewModel.getCell(5, 5).setIsGoThrough(true);  // (am) (5, 6) should change once, (5, 5) twice hence no effect
        viewModel.getCell(6, 8).setEdible(Edible.SIMPLE_GROWING);
        board.setMaxSize(600, 740);
        board.setAlignment(Pos.CENTER);
        board.refreshBoard();
        runTimer();
    }

    @FXML
    private void mouseAction(MouseEvent event) throws Exception {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }

    private void startGame() throws IOException {
        Stage stage = (Stage) countDownLabel.getScene().getWindow();
        Scene scene = new Scene(board);
        System.out.println("PREPARING FOR EVENTS");
        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int newDirection = 0;                       // 0: UP, 1: RIGHT, 2: DOWN, 3: LEFT
                switch (event.getCharacter()) {
                    case "w" -> newDirection = 0;
                    case "d" -> newDirection = 1;
                    case "s" -> newDirection = 2;
                    case "a" -> newDirection = 3;
                }
                if (newDirection == (4 + oldDirection - 1) % 4) viewModel.noticeDirectionChange("left");
                else if (newDirection == (oldDirection + 1) % 4) viewModel.noticeDirectionChange("right");
                if (newDirection != (oldDirection + 2) % 4) oldDirection = newDirection; // zabrania się zawracania
                System.out.println("NEW DIRECTION " + oldDirection);
            }
        });
        stage.setScene(scene);
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