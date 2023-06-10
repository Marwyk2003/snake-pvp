package com.example.snakepvp.views;

import com.example.snakepvp.core.Direction;
import com.example.snakepvp.core.Edible;
import com.example.snakepvp.services.EdibleEvent;
import com.example.snakepvp.viewmodels.GameHostViewModel;
import com.example.snakepvp.viewmodels.SingleGameViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.util.Objects;
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

    // TODO add more Edibles and communicate this somehow, maybe here...
    public void edibleAction(EdibleEvent event) {
        if (Objects.requireNonNull(event.getOldEdible()) == Edible.SIMPLE_GROWING) {
            if (event.getGameId() == 1) {
                blink(lengthCounter2);
                lengthCountLabel2.setText(String.valueOf(Integer.valueOf(lengthCountLabel2.getText())) + 1);
            } else {
                blink(lengthCounter1);
                lengthCountLabel1.setText(String.valueOf(Integer.valueOf(lengthCountLabel1.getText())) + 1);
            }
//            case SOME_POINTS_EDIBLE -> {
//                if (event.getGameId() == 1) {
//                    blink(pointCounter1);
//                    pointCountLabel1.setText(String.valueOf(Integer.valueOf(pointCountLabel1.getText())) + 1);
//                }
//                else  {
//                    blink(pointCounter2);
//                    pointCountLabel2.setText(String.valueOf(Integer.valueOf(pointCountLabel2.getText())) + 1);
//                }
//            }
//            case MAKE_FASTER_EDIBLE -> {
//                if (event.getGameId() == 1) {
//                    runPowerUp(powerUp2, powerUpLabel2, "speed up!");
//                }
//                else  {
//                    runPowerUp(powerUp1, powerUpLabel1, "speed up!");
//                }
//            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = viewModel.getSceneController().getStage();

        arrow1.getPoints().setAll(0.0, 0.0, 50.0, 0.0, 50.0, 200.0, 0.0, 200.0);
        arrow2.getPoints().setAll(0.0, 0.0, 120.0, 100.0, 0.0, 200.0);
        arrow1.toBack();
        arrow2.toBack();
        rectangle.toBack();
        progressIndicator.setVisible(false);
        progressIndicator.setStyle(" -fx-progress-color: pink;");

        viewModel.initGame();
        boards = new Board[2];
        for (int i = 0; i < 2; ++i) {
            SingleGameViewModel singleGameVM = viewModel.getSingleGameVM(i);
            boards[i] = new Board(singleGameVM.getHeight(), singleGameVM.getWidth(), singleGameVM.getSkin());
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
        AnchorPane.setLeftAnchor(boards[0], 20.0);
        AnchorPane.setBottomAnchor(boards[0], 50.0);
        AnchorPane.setRightAnchor(boards[1], 20.0);
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
            put(pointCounter1, new Double[]{80.0, 0.0, 80.0, 0.0});      // left, right, top, bottom
            put(pointCounter2, new Double[]{0.0, 80.0, 80.0, 0.0});
            put(lengthCounter1, new Double[]{190.0, 0.0, 80.0, 0.0});
            put(lengthCounter2, new Double[]{0.0, 190.0, 80.0, 0.0});
            put(pointCountLabel1, new Double[]{140.0, 0.0, 80.0, 0.0});
            put(pointCountLabel2, new Double[]{0.0, 140.0, 80.0, 0.0});
            put(lengthCountLabel1, new Double[]{250.0, 0.0, 80.0, 0.0});
            put(lengthCountLabel2, new Double[]{0.0, 250.0, 80.0, 0.0});
//            put(rectangle, new Double[]{50.0, 0.0, 60.0, 0.0});
            put(boards[0], new Double[]{20.0, 0.0, 0.0, 50.0});
            put(boards[1], new Double[]{0.0, 20.0, 0.0, 50.0});
        }};
        runTimer();
    }

    private void startGame() throws IOException {
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> refreshAnchors(newValue.doubleValue() / 1150.0);
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);

        Stage stage = (Stage) countDownLabel.getScene().getWindow();
        Scene scene = new Scene(pane, 1150, 700);
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
        stage.show();
        viewModel.startGame();
        runPowerUp(powerUp2, powerUpLabel2, "speed up!");   // TODO remove this line, it was added just for testing
    }
//    ));
//        timeLine.play();
//        timeLine.setOnFinished(e ->

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
        AtomicInteger timeValue = new AtomicInteger(4);
        AtomicReference<Double> fontSize = new AtomicReference<>(30.0);
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(timeToStart.get());
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), e -> {
            timeToStart.decrementAndGet();
            if (timeToStart.get() == 59) {
                if (AnchorPane.getRightAnchor(message) != null) {
                    AnchorPane.setRightAnchor(message, (double) (360 + messageText.length()));
                } else {
                    AnchorPane.setLeftAnchor(message, (double) (360 + messageText.length()));
                }
                message.setText(messageText);
                message.setVisible(true);
                powerUp.setVisible(true);
                message.setStyle("-fx-font-size: " + fontSize.get() + "px;" + "-fx-font-family: Gaegu;");
                DropShadow glow = new DropShadow();
                glow.setColor(Color.rgb(130, 219, 225));
                glow.setHeight(60.0);
                message.setEffect(glow);
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

    void refreshAnchors(double ratio) {
        if (ratio > 1) {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                if (AnchorPane.getLeftAnchor(node) != null)
                    AnchorPane.setLeftAnchor(node, Math.max(AnchorPane.getLeftAnchor(node), original_anchors[0] * ratio));
                if (AnchorPane.getRightAnchor(node) != null)
                    AnchorPane.setRightAnchor(node, Math.max(AnchorPane.getRightAnchor(node), original_anchors[1] * ratio));
                if (AnchorPane.getTopAnchor(node) != null)
                    AnchorPane.setTopAnchor(node, Math.max(AnchorPane.getTopAnchor(node), original_anchors[2] * ratio));
                if (AnchorPane.getBottomAnchor(node) != null)
                    AnchorPane.setBottomAnchor(node, Math.max(AnchorPane.getBottomAnchor(node), original_anchors[3] * ratio));
            }
        } else {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                if (AnchorPane.getLeftAnchor(node) != null)
                    AnchorPane.setLeftAnchor(node, Math.min(AnchorPane.getLeftAnchor(node), original_anchors[0] * ratio));
                if (AnchorPane.getRightAnchor(node) != null)
                    AnchorPane.setRightAnchor(node, Math.min(AnchorPane.getRightAnchor(node), original_anchors[1] * ratio));
                if (AnchorPane.getTopAnchor(node) != null)
                    AnchorPane.setTopAnchor(node, Math.min(AnchorPane.getTopAnchor(node), original_anchors[2] * ratio));
                if (AnchorPane.getBottomAnchor(node) != null)
                    AnchorPane.setBottomAnchor(node, Math.min(AnchorPane.getBottomAnchor(node), original_anchors[3] * ratio));
            }
        }
//        rectangle.getPoints().setAll(0.0, 0.0, 0.0, 90.0 * ratio, 1050.0 * ratio, 90.0 * ratio, 1050.0 * ratio, 0.0);
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
            labels[i].setStyle("-fx-font-size: 32px;" + "-fx-font-family: Gaegu;");
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

//        rectangle.getPoints().setAll(0.0, 0.0, 0.0, 90.0, 1050.0, 90.0, 1050.0, 0.0);
//        rectangle.setFill(Color.WHITE);

//        pane.getChildren().addAll(rectangle);
        pane.getChildren().addAll(labels);
        pane.getChildren().addAll(images.keySet());
    }
}