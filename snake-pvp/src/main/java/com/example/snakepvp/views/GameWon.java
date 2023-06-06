package com.example.snakepvp.views;

import com.example.snakepvp.Program;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameWon implements Initializable {
    Stage stage = Program.stage;

    @FXML
    private Label gameWonLabel1, gameWonLabel2;
    @FXML
    private Button returnButton;

    @FXML
    private ImageView spotlight, coin1, coin2, coin3, coin4, coin5;

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
        returnButton.setGraphic(new ImageView(new Image("file:src/return.png")));
        spotlight.setImage(new Image("file:src/spotlightB.png"));
        coin1.setImage(new Image("file:src/coin100.png"));
        coin2.setImage(new Image("file:src/coin100.png"));
        coin3.setImage(new Image("file:src/coin85.png"));
        coin4.setImage(new Image("file:src/coin65.png"));
        coin5.setImage(new Image("file:src/coin50.png"));

        AtomicInteger fontSize = new AtomicInteger(70);
        ChangeListener<Number> stageHeightListener = (observable, oldValue, newValue) -> {
            double ratio = newValue.doubleValue() / 740.0;
            if (newValue.doubleValue() > oldValue.doubleValue()) {
                fontSize.set((int) Math.max(fontSize.get(), 70 * ratio));
                AnchorPane.setTopAnchor(gameWonLabel1, Math.max(AnchorPane.getTopAnchor(gameWonLabel1), 80.0 * ratio));
                AnchorPane.setLeftAnchor(gameWonLabel1, Math.max(AnchorPane.getLeftAnchor(gameWonLabel1), 100.0 * ratio));
                AnchorPane.setTopAnchor(gameWonLabel2, Math.max(AnchorPane.getTopAnchor(gameWonLabel2), 125.0 * ratio));
                AnchorPane.setLeftAnchor(gameWonLabel2, Math.max(AnchorPane.getLeftAnchor(gameWonLabel2), 100.0 * ratio));
            } else {
                fontSize.set((int) Math.min(fontSize.get(), 70 * ratio));
                AnchorPane.setTopAnchor(gameWonLabel1, Math.min(AnchorPane.getTopAnchor(gameWonLabel1), 80.0 * ratio));
                AnchorPane.setLeftAnchor(gameWonLabel1, Math.min(AnchorPane.getLeftAnchor(gameWonLabel1), 100.0 * ratio));
                AnchorPane.setTopAnchor(gameWonLabel2, Math.min(AnchorPane.getTopAnchor(gameWonLabel2), 125.0 * ratio));
                AnchorPane.setLeftAnchor(gameWonLabel2, Math.min(AnchorPane.getLeftAnchor(gameWonLabel2), 100.0 * ratio));
            }
            gameWonLabel1.setStyle("-fx-font-size: " + fontSize + "px");
            gameWonLabel2.setStyle("-fx-font-size: " + fontSize + "px");
        };
        stage.widthProperty().addListener(stageHeightListener);
        stage.heightProperty().addListener(stageHeightListener);
    }
}