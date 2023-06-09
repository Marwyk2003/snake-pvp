package com.example.snakepvp.views;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.example.snakepvp.viewmodels.GameOverViewModel;
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

public class GameWon1P implements Initializable {
    private Stage stage;
    @InjectViewModel
    private GameOverViewModel viewModel;
    @FXML
    private Label gameWonLabel1, gameWonLabel2, gameLostLabel1, gameLostLabel2;
    @FXML
    private Button returnButton;
    @FXML
    private ImageView coin1, coin2, coin3, trash;
    private Map<Node, Double[]> anchors;

    @FXML
    private void mouseAction (MouseEvent event) {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }
    @FXML
    private void returnButtonAction (ActionEvent event) {
        viewModel.getSceneController().loadHelloScene();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = viewModel.getSceneController().getStage();

        returnButton.setGraphic(new ImageView(new Image("/return.png")));
        anchors = new HashMap<>() {{
            put(gameWonLabel1, new Double[]{0.0, 60.0, 100.0, 0.0});      // left, right, top, bottom
            put(gameWonLabel2, new Double[]{0.0, 60.0, 145.0, 0.0});
            put(gameLostLabel1, new Double[]{60.0, 00.0, 100.0, 00.0});
            put(gameLostLabel2, new Double[]{60.0, 0.0, 145.0, 0.0});
            put(coin1, new Double[]{0.0, 50.0, 0.0, 0.0});
            put(coin2, new Double[]{0.0, 150.0, 0.0, 0.0});
            put(coin3, new Double[]{0.0, 250.0, 0.0, 0.0});
            put(trash, new Double[]{60.0, 0.0, 0.0, 0.0});
        }};

        AtomicInteger fontSize = new AtomicInteger(55);
        ChangeListener<Number> stageHeightListener = (observable, oldValue, newValue) -> {
            double ratio = newValue.doubleValue() / 740.0;
            if (newValue.doubleValue() > oldValue.doubleValue()) {
                fontSize.set((int) Math.max(fontSize.get(), 55 * ratio));
            } else {
                fontSize.set((int) Math.min(fontSize.get(), 55 * ratio));
            }
            refreshAnchors(ratio);
            setLabels(fontSize.get());
        };
        stage.widthProperty().addListener(stageHeightListener);
        stage.heightProperty().addListener(stageHeightListener);

        runAnimation();
    }

    void refreshAnchors(double ratio) {
        if (ratio > 1) {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                if (AnchorPane.getLeftAnchor(node) != null) AnchorPane.setLeftAnchor(node, Math.max(AnchorPane.getLeftAnchor(node), original_anchors[0] * ratio));
                if (AnchorPane.getRightAnchor(node) != null) AnchorPane.setRightAnchor(node, Math.max(AnchorPane.getRightAnchor(node), original_anchors[1] * ratio));
                if (AnchorPane.getTopAnchor(node) != null) AnchorPane.setTopAnchor(node, Math.max(AnchorPane.getTopAnchor(node), original_anchors[2] * ratio));
                if (AnchorPane.getBottomAnchor(node) != null) AnchorPane.setBottomAnchor(node, Math.max(AnchorPane.getBottomAnchor(node), original_anchors[3] * ratio));
            }
        } else {
            for (Map.Entry<Node, Double[]> entry : anchors.entrySet()) {
                Node node = entry.getKey();
                Double[] original_anchors = entry.getValue();
                if (AnchorPane.getLeftAnchor(node) != null) AnchorPane.setLeftAnchor(node, Math.min(AnchorPane.getLeftAnchor(node), original_anchors[0] * ratio));
                if (AnchorPane.getRightAnchor(node) != null) AnchorPane.setRightAnchor(node, Math.min(AnchorPane.getRightAnchor(node), original_anchors[1] * ratio));
                if (AnchorPane.getTopAnchor(node) != null) AnchorPane.setTopAnchor(node, Math.min(AnchorPane.getTopAnchor(node), original_anchors[2] * ratio));
                if (AnchorPane.getBottomAnchor(node) != null) AnchorPane.setBottomAnchor(node, Math.min(AnchorPane.getBottomAnchor(node), original_anchors[3] * ratio));
            }
        }
    }

    public void runAnimation() {
        trash.setImage(new Image("/trash.png"));
        AtomicInteger timeToStart1 = new AtomicInteger(300);
        Timeline timeLine1 = new Timeline();
        timeLine1.setCycleCount(timeToStart1.get());
        AtomicReference<Double> angle = new AtomicReference<>(0.5);
        timeLine1.getKeyFrames().add(new KeyFrame(Duration.seconds(0.02), e -> {
            timeToStart1.decrementAndGet();
            if (trash.getRotate() == -5 || trash.getRotate() == 5) angle.set(angle.get() * -1);
            trash.setRotate(trash.getRotate() + angle.get());
        }));

        AtomicInteger timeToStart2 = new AtomicInteger(16);
        Timeline timeLine2 = new Timeline();
        timeLine2.setCycleCount(timeToStart2.get());
        timeLine2.getKeyFrames().add(new KeyFrame(Duration.seconds(0.35), e -> {
            timeToStart2.decrementAndGet();
            if (timeToStart2.get() % 2 == 0) {
                setCoins("/coin2.png", timeToStart2.get());
            } else {
                setCoins("/coin1.png", timeToStart2.get());
            }
        }));
        timeLine2.setOnFinished(e -> setCoins("/coin1.png", 0));

        timeLine1.play();
        timeLine1.setOnFinished(e -> {
            trash.setRotate(0);
            timeLine2.play();
        });
    }

    void setCoins(String image, int time) {
        coin1.setImage(new Image(image));
        if (time <= 15) {
            coin2.setImage(new Image(image));
            coin3.setImage(new Image(image));
        } else if (time <= 17) {
            coin2.setImage(new Image(image));
        }
    }

    void setLabels(int fontSize) {
        String format = "-fx-font-size: " + fontSize + "px";
        gameWonLabel1.setStyle(format);
        gameWonLabel2.setStyle(format);
        gameLostLabel1.setStyle(format);
        gameLostLabel2.setStyle(format);
    }
}