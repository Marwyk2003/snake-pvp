package com.example.snakepvp.views;

import com.example.snakepvp.viewmodels.SingleGameViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Game1 implements FxmlView<SingleGameViewModel>, Initializable {

    Board board;
    private int snakeSkin;
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
            }
        }
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
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int newDirection = 0;
                switch (event.getCode()) {
                    case UP -> newDirection = 2;
                    case RIGHT -> newDirection = 1;
                    case DOWN -> newDirection = 0;
                    case LEFT -> newDirection = 3;
                }
                viewModel.changeDirection(newDirection);
            }
        });

//        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                int newDirection = 0;
//                switch (event.getCharacter()) {
//                    case "w" -> newDirection = 2;
//                    case "d" -> newDirection = 1;
//                    case "s" -> newDirection = 0;
//                    case "a" -> newDirection = 3;
//                }
//                viewModel.changeDirection(newDirection);
//            }
//        });

        stage.setScene(scene);
        stage.show();
    }

    public void runTimer() {
        AtomicInteger timeToStart = new AtomicInteger(21);
        AtomicInteger timeValue = new AtomicInteger(4);
        AtomicInteger fontSize = new AtomicInteger(80);
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(timeToStart.get());
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), e -> {
            timeToStart.decrementAndGet();
            if (timeToStart.get() % 5 == 0) {
                fontSize.set(80);
                countDownLabel.setText(String.valueOf(timeValue.decrementAndGet()));
            } else {
                fontSize.set(fontSize.get() + 5);
            }
            countDownLabel.setStyle("-fx-font-size: " + fontSize.get() + "px");
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


// 15 3(120)
// 14 3(110)
// 13 3(100)
// 12 3(90)
// 11 3(80)
// 10 2(120)
// 9 2(110)
// 8 2(100)
// 7 2(90)
// 6 2(80)
// 5 1(120)
// 4 1(110)
// 3 1(100)
// 2 1(90)
// 1 1(80)