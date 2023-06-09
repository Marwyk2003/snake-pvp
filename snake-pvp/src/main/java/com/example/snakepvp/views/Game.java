package com.example.snakepvp.views;

import com.example.snakepvp.core.CellContent;
import com.example.snakepvp.core.Direction;
import com.example.snakepvp.viewmodels.GameHostViewModel;
import com.example.snakepvp.viewmodels.SingleGameViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Game implements FxmlView<GameHostViewModel>, Initializable {
    @FXML
    ImageView pointCounter1, lengthCounter1, pointCounter2, lengthCounter2, powerUp1, powerUp2;
    @InjectViewModel
    private GameHostViewModel viewModel;
    private Stage stage;
    private AnchorPane pane;
    @FXML
    private Label countDownLabel, pointCountLabel1, lengthCountLabel1, pointCountLabel2, lengthCountLabel2, powerUpLabel1, powerUpLabel2;
    private Label[] labels;
    @FXML
    private Polygon arrow1, arrow2, rectangle;
    @FXML
    private ProgressIndicator progressIndicator;
    private Map<ImageView, String[]> images = null;
    private Map<Node, Double[]> anchors = null;
    private Board[] boards;


    @FXML
    private void mouseAction(MouseEvent event) {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }

    public void edibleAction(CellContent cellContent, int gameId) {
        Platform.runLater(() -> {
            if (cellContent == CellContent.EDIBLE_GROW || cellContent == CellContent.EDIBLE_DOUBLE) {
                blink(gameId == 1 ? lengthCounter1 : lengthCounter2);
            } else if (cellContent == CellContent.EDIBLE_SPEED) {
                runPowerUp(gameId == 1 ? powerUp1 : powerUp2, gameId == 1 ? powerUpLabel1 : powerUpLabel2, "speed up!");
            } else if (cellContent == CellContent.EDIBLE_REVERSE) {
                runPowerUp(gameId == 1 ? powerUp1 : powerUp2, gameId == 1 ? powerUpLabel1 : powerUpLabel2, "other way!");
            } else if (cellContent == CellContent.EDIBLE_DIRECTION) {
                runPowerUp(gameId == 1 ? powerUp1 : powerUp2, gameId == 1 ? powerUpLabel1 : powerUpLabel2, "which one is left?");
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = viewModel.getSceneController().getStage();

        arrow1.getPoints().setAll(0.0, 0.0, 50.0, 0.0, 50.0, 200.0, 0.0, 200.0);
        arrow2.getPoints().setAll(0.0, 0.0, 120.0, 100.0, 0.0, 200.0);
        arrow1.toBack();
        arrow2.toBack();
        progressIndicator.setVisible(false);
        progressIndicator.setStyle(" -fx-progress-color: pink;");

        ChangeListener<Number> stageHeightListener = (observable, oldValue, newValue) -> {
            refreshVerticalAnchors(newValue.doubleValue() / 700.0);
        };
        ChangeListener<Number> stageWidthListener = (observable, oldValue, newValue) -> {
            refreshHorizontalAnchors(newValue.doubleValue() / 1000.0);
        };
        stage.widthProperty().addListener(stageWidthListener);
        stage.heightProperty().addListener(stageHeightListener);

        viewModel.initGame();
        boards = new Board[2];
        for (int i = 0; i < 2; ++i) {
            SingleGameViewModel singleGameVM = viewModel.getSingleGameVM(i);
            singleGameVM.lengthProperty().addListener((o, oldVal, newVal) -> {
                int length = (int) newVal;
                Label label = singleGameVM.getId() == 0 ? lengthCountLabel1 : lengthCountLabel2;
                Platform.runLater(() -> label.setText(String.valueOf(length)));
            });
            singleGameVM.scoreProperty().addListener((o, oldVal, newVal) -> {
                int points = (int) newVal;
                Label label = singleGameVM.getId() == 0 ? pointCountLabel1 : pointCountLabel2;
                Platform.runLater(() -> label.setText(String.valueOf(points)));
                blink(singleGameVM.getId() == 0 ? pointCounter1 : pointCounter2);
            });
            boards[i] = new Board(singleGameVM.getHeight(), singleGameVM.getWidth(), singleGameVM.getSkin(), i, this);
            for (int row = 0; row < singleGameVM.getHeight(); ++row) {
                for (int col = 0; col < singleGameVM.getWidth(); ++col) {
                    boards[i].getField(row, col).bind(singleGameVM.getCell(row, col));
                }
            }
        }
        pane = new AnchorPane();
        for (int i = 0; i < 2; ++i) {
            boards[i].refreshBoard();
            pane.getChildren().add(boards[i]);
        }
        AnchorPane.setLeftAnchor(boards[0], 50.0);
        AnchorPane.setBottomAnchor(boards[0], 50.0);
        AnchorPane.setRightAnchor(boards[1], 50.0);
        AnchorPane.setBottomAnchor(boards[1], 50.0);

        images = new HashMap<>() {{
            put(pointCounter1, new String[]{"/points1.png", "/points2.png"});
            put(pointCounter2, new String[]{"/points1.png", "/points2.png"});
            put(lengthCounter1, new String[]{"/length1.png", "/length2.png"});
            put(lengthCounter2, new String[]{"/length1.png", "/length2.png"});
            put(powerUp1, new String[]{"/powerUp.png"});
            put(powerUp2, new String[]{"/powerUp.png"});
        }};
        anchors = new HashMap<>() {{
            put(arrow1, new Double[]{400.0, 280.0});      // left, top
            put(arrow2, new Double[]{500.0, 280.0});
            put(countDownLabel, new Double[]{450.0, 310.0});
            put(progressIndicator, new Double[]{470.0, 420.0});
        }};
        runTimer();
    }

    private void startGame() throws IOException {
        stage.setFullScreen(true);
        Scene scene = new Scene(pane, 1450, 900);
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

        setCounters();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        viewModel.startGame();
    }

    public void runTimer() {
        AtomicInteger timeToStart = new AtomicInteger(161);
        AtomicInteger timeValue = new AtomicInteger(4);
        AtomicReference<Double> fontSize = new AtomicReference<>(80.0);
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(timeToStart.get());
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(0.016), e -> {
            timeToStart.decrementAndGet();
            if (timeToStart.get() == 40) {
                countDownLabel.setText("Play!");
                progressIndicator.setVisible(true);
                AnchorPane.setLeftAnchor(countDownLabel, 390.0);
            } else if (timeToStart.get() % 40 == 0) countDownLabel.setText(String.valueOf(timeValue.decrementAndGet()));
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

    public void runPowerUp(ImageView powerUp, Label message, String messageText) {
        AtomicInteger timeToStart = new AtomicInteger(60);
        AtomicReference<Double> fontSize = new AtomicReference<>(40.0);
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(timeToStart.get());
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), e -> {
            timeToStart.decrementAndGet();
            if (timeToStart.get() == 59) {
                if (AnchorPane.getRightAnchor(message) != null) {
                    AnchorPane.setRightAnchor(message, (double) (455 + messageText.length()));
                } else {
                    AnchorPane.setLeftAnchor(message, (double) (455 + messageText.length()));
                }
                message.setText(messageText);
                message.setVisible(true);
                powerUp.setVisible(true);
                message.setStyle("-fx-font-size: " + fontSize.get() + "px;" + "-fx-font-family: Gaegu;");
            } else if (timeToStart.get() <= 36) {
                message.setEffect(null);
            }
        }));

        timeLine.play();
        timeLine.setOnFinished(e -> {
            message.setVisible(false);
            powerUp.setVisible(false);
        });
    }

    void refreshVerticalAnchors(double ratio) {
        if (ratio > 1) {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                AnchorPane.setTopAnchor(node, Math.max(AnchorPane.getTopAnchor(node), original_anchors[1] * ratio));
            }
        } else {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                AnchorPane.setTopAnchor(node, Math.min(AnchorPane.getTopAnchor(node), original_anchors[1] * ratio));
            }
        }
    }

    void refreshHorizontalAnchors(double ratio) {
        if (ratio > 1) {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                AnchorPane.setLeftAnchor(node, Math.max(AnchorPane.getLeftAnchor(node), original_anchors[0] * ratio));
            }
        } else {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                AnchorPane.setLeftAnchor(node, Math.min(AnchorPane.getLeftAnchor(node), original_anchors[0] * ratio));
            }
        }
    }

    public void blink(ImageView imageView) {
        AtomicInteger timeToStart = new AtomicInteger(2);
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(timeToStart.get());
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(0.3), e -> {
            timeToStart.decrementAndGet();
            imageView.setImage(new Image(images.get(imageView)[1]));
        }));
        timeLine.play();
        timeLine.setOnFinished(e -> {
            imageView.setImage(new Image(images.get(imageView)[0]));
        });
    }

    private void setCounters() {
        labels = new Label[]{pointCountLabel1, pointCountLabel2, lengthCountLabel1, lengthCountLabel2, powerUpLabel1, powerUpLabel2};
        for (int i = 0; i < labels.length; i++) {
            labels[i].setStyle("-fx-font-size: 40px;" + "-fx-font-family: Gaegu;");
            if (i < 2) labels[i].setText("0");
            else labels[i].setText("3");
        }
        for (Map.Entry<ImageView, String[]> entry : images.entrySet()) {
            entry.getKey().setImage(new Image(entry.getValue()[0]));
        }
        powerUp1.setVisible(false);
        powerUp2.setVisible(false);
        powerUpLabel1.setVisible(false);
        powerUpLabel2.setVisible(false);
        pane.getChildren().addAll(labels);
        pane.getChildren().addAll(images.keySet());
    }
}
