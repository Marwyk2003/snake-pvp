package com.example.snakepvp.views;

import com.example.snakepvp.viewmodels.GameOverViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class GameOver1P implements Initializable, FxmlView<GameOverViewModel> {
    private Stage stage;
    @InjectViewModel
    private GameOverViewModel viewModel;
    @FXML
    private Button returnButton;
    @FXML
    private ImageView spotlight, cup;
    @FXML
    private Label gameOverLabel;
    private Map<Node, Double[]> anchors;

    @FXML
    private void mouseAction(MouseEvent event) {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }

    @FXML
    private void returnButtonAction(ActionEvent event) {
        viewModel.getSceneController().loadHelloScene();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = viewModel.getSceneController().getStage();

        returnButton.setGraphic(new ImageView(new Image("/return.png")));
        spotlight.setImage(new Image("/spotlightB.png"));
        gameOverLabel.setStyle("-fx-font-size: 60px");
        cup.setImage(new Image("/trashcup-300.png"));
        anchors = new HashMap<>() {{
            put(spotlight, new Double[]{0.0, 30.0, 0.0, 50.0});      // left, right, top, bottom
            put(cup, new Double[]{0.0, 100.0, 0.0, 40.0});
        }};

        AtomicInteger fontSize = new AtomicInteger(80);
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double ratio = newValue.doubleValue() / 1000.0;
            refreshAnchors(ratio);
            refreshLabel(ratio, fontSize);
            refreshImage();
        };
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
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
    void refreshImage() {
        int imageSize = 180;
        if (stage.getHeight() > 900 || stage.getWidth() > 1500) imageSize = 300;
        else if (stage.getHeight() > 850 || stage.getWidth() > 1400) imageSize = 250;
        else if (stage.getHeight() > 800 || stage.getWidth() > 1250) imageSize = 230;
        else if (stage.getHeight() > 750 || stage.getWidth() > 1100) imageSize = 200;
        cup.setImage(new Image("/trashcup-" + imageSize + ".png"));
    }

    void refreshLabel(double ratio, AtomicInteger fontSize) {
        System.out.println(fontSize.get() + " " + ratio);
        if (stage.getHeight() > 900 || stage.getWidth() > 1500) fontSize.set(120);
        else if (ratio > 1) fontSize.set((int) Math.max(fontSize.get(), 80 * ratio));
        else fontSize.set((int) Math.min(fontSize.get(), 80 * ratio));
        gameOverLabel.setStyle("-fx-font-size: " + fontSize.get() + "px");
    }
}