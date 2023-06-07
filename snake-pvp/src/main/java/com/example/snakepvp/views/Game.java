package com.example.snakepvp.views;

import com.example.snakepvp.viewmodels.SingleGameViewModel;
import de.saxsys.mvvmfx.FxmlView;
import java.io.IOException;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Game implements FxmlView<SingleGameViewModel>, Initializable {

    HBox hBox;
    Board board1, board2;
    private int snakeSkin;
    @InjectViewModel
    private SingleGameViewModel viewModel;
    @FXML
    private Label countDownLabel;

    @FXML
    private void mouseAction(MouseEvent event) {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board1 = new Board(viewModel.getHeight(), viewModel.getWidth());
        board2 = new Board(viewModel.getHeight(), viewModel.getWidth());
        for (int row = 0; row < viewModel.getHeight(); ++row) {
            for (int col = 0; col < viewModel.getWidth(); ++col) {
                board1.getField(row, col).bind(viewModel.getCell(row, col));
                board2.getField(row, col).bind(viewModel.getCell(row, col));
            }
        }
        board1.setMaxSize(600, 740);
        board1.setAlignment(Pos.BOTTOM_LEFT);
        board1.refreshBoard();
        board2.setMaxSize(600, 740);
        board2.setAlignment(Pos.BOTTOM_RIGHT);
        board2.refreshBoard();
        hBox = new HBox();
        hBox.getChildren().addAll(board1, board2);
        runTimer();
    }

    private void startGame() throws IOException {
        Stage stage = (Stage) countDownLabel.getScene().getWindow();
        Scene scene = new Scene(hBox);
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