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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Game implements FxmlView<GameHostViewModel>, Initializable {

    private AnchorPane pane;
    private int snakeSkin;
    @InjectViewModel
    private GameHostViewModel viewModel;
    @FXML
    private Label countDownLabel, pointCountLabel1, lengthCountLabel1, pointCountLabel2, lengthCountLabel2;
    @FXML
    private Polygon arrow1, arrow2;
    @FXML
    ImageView pointCounter1, lengthCounter1, pointCounter2, lengthCounter2;

    @FXML
    private void mouseAction(MouseEvent event) {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        arrow1.getPoints().setAll(0.0, 0.0, 50.0, 0.0, 50.0, 200.0, 0.0, 200.0);
        arrow2.getPoints().setAll(0.0, 0.0, 120.0, 100.0, 0.0, 200.0);
        arrow1.toBack();
        arrow2.toBack();

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
        }
        AnchorPane.setLeftAnchor(boards[0], 50.0);
        AnchorPane.setBottomAnchor(boards[0], 50.0);
        AnchorPane.setRightAnchor(boards[1], 50.0);
        AnchorPane.setBottomAnchor(boards[1], 50.0);
        pane = new AnchorPane();
        for (int i = 0; i < 2; ++i) {
            boards[i].refreshBoard();
            pane.getChildren().add(boards[i]);
        }

        runTimer();
    }

    private void startGame() throws IOException {
        setCounters();

        Stage stage = (Stage) countDownLabel.getScene().getWindow();
        Scene scene = new Scene(pane, 1300, 700);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            Direction newDirection = null;
            switch (event.getCode()) {
                case UP -> newDirection = Direction.UP;
                case RIGHT -> newDirection = Direction.RIGHT;
                case DOWN -> newDirection = Direction.DOWN;
                case LEFT -> newDirection = Direction.LEFT;
            }
            if (newDirection != null) viewModel.getSingleGameVM(1).changeDirection(newDirection);
        });

        scene.setOnKeyTyped(event -> {
            Direction newDirection = null;
            switch (event.getCharacter()) {
                case "w" -> newDirection = Direction.UP;
                case "d" -> newDirection = Direction.RIGHT;
                case "s" -> newDirection = Direction.DOWN;
                case "a" -> newDirection = Direction.LEFT;
            }
            if (newDirection != null) viewModel.getSingleGameVM(0).changeDirection(newDirection);
        });

        stage.setScene(scene);
        stage.show();
        viewModel.startGame();
    }

    public void runTimer() {
        AtomicInteger timeToStart = new AtomicInteger(161);
        AtomicInteger timeValue = new AtomicInteger(4);
        AtomicReference<Double> fontSize = new AtomicReference<>(60.0);
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(timeToStart.get());
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(0.016), e -> {
            timeToStart.decrementAndGet();
            if (timeToStart.get() == 40) { countDownLabel.setText("Play!"); AnchorPane.setLeftAnchor(countDownLabel, 390.0); }
            else if (timeToStart.get() % 40 == 0) countDownLabel.setText(String.valueOf(timeValue.decrementAndGet()));
            AnchorPane.setTopAnchor(countDownLabel, AnchorPane.getTopAnchor(countDownLabel) - 0.15);
            fontSize.set(fontSize.get() + 0.25);
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

    private void setCounters() {
        Label[] labels = { pointCountLabel1, pointCountLabel2, lengthCountLabel1, lengthCountLabel2 };
        for (int i = 0; i < 4; i++) {
            labels[i].setStyle("-fx-font-size: 32px");
            if (i < 2) {
                labels[i].setText("0");
                AnchorPane.setTopAnchor(labels[i], 60.0);
            } else {
                labels[i].setText("3");
                AnchorPane.setTopAnchor(labels[i], 100.0);
            }

        }

        pointCounter1.setImage(new Image("/points.png"));
        pointCounter2.setImage(new Image("/points.png"));
        lengthCounter1.setImage(new Image("/length.png"));
        lengthCounter2.setImage(new Image("/length.png"));

        pane.getChildren().addAll(labels);
        pane.getChildren().addAll(pointCounter1, pointCounter2, lengthCounter1, lengthCounter2);
    }
}