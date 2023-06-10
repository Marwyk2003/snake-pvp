package com.example.snakepvp.views;

import com.example.snakepvp.viewmodels.GameOverViewModel;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameEnd implements Initializable, FxmlView<GameOverViewModel> {
    protected Stage stage;
    @InjectViewModel
    protected GameOverViewModel viewModel;
    @FXML
    protected Label gameWonLabel1, gameWonLabel2, gameLostLabel1, gameLostLabel2;
    @FXML
    protected Button returnButton;
    @FXML
    protected ImageView coin1, coin2, coin3, trash;
    protected Map<Node, Double[]> anchors;

    @FXML
    private void mouseAction (MouseEvent event) {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }
    @FXML
    private void returnButtonAction (ActionEvent event) {
        viewModel.getSceneController().loadHelloScene();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) { }
    void addChangeListener() {
        AtomicInteger fontSize = new AtomicInteger(55);
        ChangeListener<Number> stageHeightListener = (observable, oldValue, newValue) -> {
            double ratio = newValue.doubleValue() / 740.0;
            if (newValue.doubleValue() > oldValue.doubleValue()) {
                fontSize.set((int) Math.max(fontSize.get(), 55 * ratio));
            } else {
                fontSize.set((int) Math.min(fontSize.get(), 55 * ratio));
            }
            refreshVerticalAnchors(ratio);
            refreshImages(ratio);
            refreshShape(ratio);
            setLabels(fontSize.get());
        };
        ChangeListener<Number> stageWidthListener = (observable, oldValue, newValue) -> {
            double ratio = newValue.doubleValue() / 740.0;
            if (newValue.doubleValue() > oldValue.doubleValue()) {
                fontSize.set((int) Math.max(fontSize.get(), 55 * ratio));
            } else {
                fontSize.set((int) Math.min(fontSize.get(), 55 * ratio));
            }
            refreshHorizontalAnchors(ratio);
            refreshImages(ratio);
            refreshShape(ratio);
            setLabels(fontSize.get());
        };
        stage.widthProperty().addListener(stageWidthListener);
        stage.heightProperty().addListener(stageHeightListener);
    }
    void refreshVerticalAnchors(double ratio) {
        if (ratio > 1) {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                if (AnchorPane.getTopAnchor(node) != null) AnchorPane.setTopAnchor(node, Math.max(AnchorPane.getTopAnchor(node), original_anchors[2] * ratio));
                if (AnchorPane.getBottomAnchor(node) != null) AnchorPane.setBottomAnchor(node, Math.max(AnchorPane.getBottomAnchor(node), original_anchors[3] * ratio));
            }
        } else {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                if (AnchorPane.getTopAnchor(node) != null) AnchorPane.setTopAnchor(node, Math.min(AnchorPane.getTopAnchor(node), original_anchors[2] * ratio));
                if (AnchorPane.getBottomAnchor(node) != null) AnchorPane.setBottomAnchor(node, Math.min(AnchorPane.getBottomAnchor(node), original_anchors[3] * ratio));
            }
        }
    }
    void refreshHorizontalAnchors(double ratio) {
        if (ratio > 1) {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                if (AnchorPane.getLeftAnchor(node) != null) AnchorPane.setLeftAnchor(node, Math.max(AnchorPane.getLeftAnchor(node), original_anchors[0] * ratio));
                if (AnchorPane.getRightAnchor(node) != null) AnchorPane.setRightAnchor(node, Math.max(AnchorPane.getRightAnchor(node), original_anchors[1] * ratio));
            }
        } else {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                if (AnchorPane.getLeftAnchor(node) != null) AnchorPane.setLeftAnchor(node, Math.min(AnchorPane.getLeftAnchor(node), original_anchors[0] * ratio));
                if (AnchorPane.getRightAnchor(node) != null) AnchorPane.setRightAnchor(node, Math.min(AnchorPane.getRightAnchor(node), original_anchors[1] * ratio));
            }
        }
    }
    void refreshImages(double ratio) { }
    void refreshShape(double ratio) { }
    void runTrashAnimation(String imageSize) {
        trash.setImage(new Image("/trash" + imageSize + ".png"));
        AtomicInteger timeToStart1 = new AtomicInteger(250);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(timeToStart1.get());
        AtomicReference<Double> angle = new AtomicReference<>(0.5);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.02), e -> {
            timeToStart1.decrementAndGet();
            if (trash.getRotate() == -5 || trash.getRotate() == 5) angle.set(angle.get() * -1);
            trash.setRotate(trash.getRotate() + angle.get());
        }));
        timeline.play();
        timeline.setOnFinished(e -> trash.setRotate(0));
    }
    void runCoinAnimation(String imageSize) {
        AtomicInteger timeToStart = new AtomicInteger(20);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(timeToStart.get());
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.35), e -> {
            timeToStart.decrementAndGet();
            if (timeToStart.get() % 2 == 0) {
                setCoins("/coin2" + imageSize + ".png", timeToStart.get());
            } else {
                setCoins("/coin1" + imageSize + ".png", timeToStart.get());
            }
        }));

        timeline.play();
        timeline.setOnFinished(e -> setCoins("/coin1" + imageSize + ".png", 0));
    }
    void setCoins(String image, int time) {
        System.out.println("set coins " + image + " " + time);
        coin1.setImage(new Image(image));
        if (time <= 15) {
            coin2.setImage(new Image(image));
            coin3.setImage(new Image(image));
        } else if (time <= 17) {
            coin2.setImage(new Image(image));
        }
    }
    void setLabels(int fontSize) { }
}