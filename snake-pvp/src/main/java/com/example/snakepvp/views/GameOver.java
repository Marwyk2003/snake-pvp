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

public class GameOver implements Initializable {
    Stage stage = Program.stage;
    @FXML
    private Button returnButton;

    @FXML
    private ImageView spotlight, cup;

    @FXML
    private Label gameOverLabel;

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
        spotlight.setImage(new Image("/spotlightB.png"));
        gameOverLabel.setStyle("-fx-font-size: 60px");
        cup.setImage(new Image("/trashcup-500.png"));

//        AtomicInteger fontSize = new AtomicInteger(80);
//        AtomicInteger imageSize = new AtomicInteger(180);
//        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
//            double ratio = newValue.doubleValue() / 740.0;
//            int newImageSize = 180;
//            if (ratio > 1.8) newImageSize = 300;
//            else if (ratio > 1.6) newImageSize = 250;
//            else if (ratio > 1.4) newImageSize = 230;
//            else if (ratio > 1.2) newImageSize = 200;
//            if (newValue.doubleValue() > oldValue.doubleValue()) {
//                fontSize.set((int) Math.max(fontSize.get(), 80 * ratio));
//                imageSize.set(Math.max(imageSize.get(), newImageSize));
//                AnchorPane.setBottomAnchor(spotlight, Math.max(AnchorPane.getBottomAnchor(spotlight), 50.0 * ratio));
//                AnchorPane.setRightAnchor(spotlight, Math.max(AnchorPane.getRightAnchor(spotlight), 30.0 * ratio));
//                AnchorPane.setBottomAnchor(cup, Math.max(AnchorPane.getBottomAnchor(cup), 40.0 * ratio));
//                AnchorPane.setRightAnchor(cup, Math.max(AnchorPane.getRightAnchor(cup), 100.0 * ratio));
//            } else {
//                fontSize.set((int) Math.min(fontSize.get(), 80 * ratio));
//                imageSize.set(Math.min(imageSize.get(), newImageSize));
//                AnchorPane.setBottomAnchor(spotlight, Math.min(AnchorPane.getBottomAnchor(spotlight), 50.0 * ratio));
//                AnchorPane.setRightAnchor(spotlight, Math.min(AnchorPane.getRightAnchor(spotlight), 30.0 * ratio));
//                AnchorPane.setBottomAnchor(cup, Math.min(AnchorPane.getBottomAnchor(cup), 40.0 * ratio));
//                AnchorPane.setRightAnchor(cup, Math.min(AnchorPane.getRightAnchor(cup), 100.0 * ratio));
//            }
//            gameOverLabel.setStyle("-fx-font-size: " + fontSize + "px");
//            cup.setImage(new Image("/trashcup-" + imageSize + ".png"));
//        };
//        stage.widthProperty().addListener(stageSizeListener);
//        stage.heightProperty().addListener(stageSizeListener);
    }
}