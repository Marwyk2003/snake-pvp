package com.example.snakepvp.views;

import com.example.snakepvp.core.Direction;
import com.example.snakepvp.viewmodels.GameHostViewModel;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Game implements FxmlView<GameHostViewModel>, Initializable {

    HBox hBox;
    Board board1, board2;
    private int snakeSkin;
    @InjectViewModel
    private GameHostViewModel viewModel;
    @FXML
    private Label countDownLabel;

    @FXML
    private void mouseAction(MouseEvent event) {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel.initGame();
        Board[] boards = new Board[2];
        for (int i = 0; i < 2; ++i) {
            SingleGameViewModel singleGameVM = viewModel.getSingleGameVM(i);
            boards[i] = new Board(singleGameVM.getHeight(), singleGameVM.getWidth());
            for (int row = 0; row < singleGameVM.getHeight(); ++row) {
                for (int col = 0; col < singleGameVM.getWidth(); ++col) {
                    boards[i].getField(row, col).bind(singleGameVM.getCell(row, col));
                }
            }
            boards[i].setMaxSize(600, 740);
        }
        boards[0].setAlignment(Pos.BOTTOM_LEFT);
        boards[1].setAlignment(Pos.BOTTOM_RIGHT);
        hBox = new HBox();

        for (int i = 0; i < 2; ++i) {
            boards[i].refreshBoard();
            hBox.getChildren().add(boards[i]);
        }
        runTimer();
    }

    private void startGame() throws IOException {
        Stage stage = (Stage) countDownLabel.getScene().getWindow();
        Scene scene = new Scene(hBox);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            Direction newDirection = null;
            switch (event.getCode()) {
                case UP -> newDirection = Direction.UP;
                case RIGHT -> newDirection = Direction.RIGHT;
                case DOWN -> newDirection = Direction.DOWN;
                case LEFT -> newDirection = Direction.LEFT;
            }
            if (newDirection != null) viewModel.getSingleGameVM(0).changeDirection(newDirection);
        });

        scene.setOnKeyTyped(event -> {
            Direction newDirection = null;
            switch (event.getCharacter()) {
                case "w" -> newDirection = Direction.UP;
                case "d" -> newDirection = Direction.RIGHT;
                case "s" -> newDirection = Direction.DOWN;
                case "a" -> newDirection = Direction.LEFT;
            }
            if (newDirection != null) viewModel.getSingleGameVM(1).changeDirection(newDirection);
        });

        stage.setScene(scene);
        stage.show();
        viewModel.startGame();
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