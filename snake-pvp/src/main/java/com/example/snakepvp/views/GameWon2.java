package com.example.snakepvp.views;

import com.example.snakepvp.Program;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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

public class GameWon2 implements Initializable {
    Stage stage = Program.stage;

    @FXML
    private Label gameWonLabel1, gameWonLabel2, gameLostLabel1, gameLostLabel2;
    @FXML
    private Button returnButton;

    @FXML
    private ImageView coin1, coin2, coin3, trash;

    @FXML
    private void mouseAction (MouseEvent event) {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }
    @FXML
    private void returnButtonAction (ActionEvent event) {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Hello.fxml")));
//        stage.setScene(new Scene(root));
//        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        returnButton.setGraphic(new ImageView(new Image("/return.png")));

        AtomicInteger fontSize = new AtomicInteger(55);
        ChangeListener<Number> stageHeightListener = (observable, oldValue, newValue) -> {
            double ratio = newValue.doubleValue() / 740.0;
            if (newValue.doubleValue() > oldValue.doubleValue()) {
                fontSize.set((int) Math.max(fontSize.get(), 55 * ratio));
                refreshAnchors(ratio, true);
            } else {
                fontSize.set((int) Math.min(fontSize.get(), 55 * ratio));
                refreshAnchors(ratio, false);
            }
            String format = "-fx-font-size: " + fontSize + "px";
            gameWonLabel1.setStyle(format);
            gameWonLabel2.setStyle(format);
            gameLostLabel1.setStyle(format);
            gameLostLabel2.setStyle(format);
        };
        stage.widthProperty().addListener(stageHeightListener);
        stage.heightProperty().addListener(stageHeightListener);

        runAnimation();
    }

    void refreshAnchors(double ratio, boolean increasing) {
        if (increasing) {
            AnchorPane.setTopAnchor(gameWonLabel1, Math.max(AnchorPane.getTopAnchor(gameWonLabel1), 100.0 * ratio));
            AnchorPane.setRightAnchor(gameWonLabel1, Math.max(AnchorPane.getRightAnchor(gameWonLabel1), 60.0 * ratio));
            AnchorPane.setTopAnchor(gameWonLabel2, Math.max(AnchorPane.getTopAnchor(gameWonLabel2), 145.0 * ratio));
            AnchorPane.setRightAnchor(gameWonLabel2, Math.max(AnchorPane.getRightAnchor(gameWonLabel2), 60.0 * ratio));
            AnchorPane.setTopAnchor(gameLostLabel1, Math.max(AnchorPane.getTopAnchor(gameLostLabel1), 100.0 * ratio));
            AnchorPane.setLeftAnchor(gameLostLabel1, Math.max(AnchorPane.getLeftAnchor(gameLostLabel1), 60.0 * ratio));
            AnchorPane.setTopAnchor(gameLostLabel2, Math.max(AnchorPane.getTopAnchor(gameLostLabel2), 145.0 * ratio));
            AnchorPane.setLeftAnchor(gameLostLabel2, Math.max(AnchorPane.getLeftAnchor(gameLostLabel2), 60.0 * ratio));
            AnchorPane.setRightAnchor(coin1, Math.max(AnchorPane.getRightAnchor(coin1), 50.0 * ratio));
            AnchorPane.setRightAnchor(coin2, Math.max(AnchorPane.getRightAnchor(coin2), 150.0 * ratio));
            AnchorPane.setRightAnchor(coin3, Math.max(AnchorPane.getRightAnchor(coin3), 250.0 * ratio));
            AnchorPane.setLeftAnchor(trash, Math.max(AnchorPane.getLeftAnchor(trash), 60.0 * ratio));
        } else {
            AnchorPane.setTopAnchor(gameWonLabel1, Math.min(AnchorPane.getTopAnchor(gameWonLabel1), 100.0 * ratio));
            AnchorPane.setRightAnchor(gameWonLabel1, Math.min(AnchorPane.getRightAnchor(gameWonLabel1), 60.0 * ratio));
            AnchorPane.setTopAnchor(gameWonLabel2, Math.min(AnchorPane.getTopAnchor(gameWonLabel2), 145.0 * ratio));
            AnchorPane.setRightAnchor(gameWonLabel2, Math.min(AnchorPane.getRightAnchor(gameWonLabel2), 60.0 * ratio));
            AnchorPane.setTopAnchor(gameLostLabel1, Math.min(AnchorPane.getTopAnchor(gameLostLabel1), 100.0 * ratio));
            AnchorPane.setLeftAnchor(gameLostLabel1, Math.min(AnchorPane.getLeftAnchor(gameLostLabel1), 60.0 * ratio));
            AnchorPane.setTopAnchor(gameLostLabel2, Math.min(AnchorPane.getTopAnchor(gameLostLabel2), 145.0 * ratio));
            AnchorPane.setLeftAnchor(gameLostLabel2, Math.min(AnchorPane.getLeftAnchor(gameLostLabel2), 60.0 * ratio));
            AnchorPane.setRightAnchor(coin1, Math.min(AnchorPane.getRightAnchor(coin1), 50.0 * ratio));
            AnchorPane.setRightAnchor(coin2, Math.min(AnchorPane.getRightAnchor(coin2), 150.0 * ratio));
            AnchorPane.setRightAnchor(coin3, Math.min(AnchorPane.getRightAnchor(coin3), 250.0 * ratio));
            AnchorPane.setLeftAnchor(trash, Math.min(AnchorPane.getLeftAnchor(trash), 60.0 * ratio));
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
}