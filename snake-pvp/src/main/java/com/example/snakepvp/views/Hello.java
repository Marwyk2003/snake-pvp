package com.example.snakepvp.views;

import com.example.snakepvp.viewmodels.HelloViewModel;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.application.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Hello implements Initializable, FxmlView<HelloViewModel> {
    @InjectViewModel
    private HelloViewModel viewModel;
    private Stage stage;
    @FXML
    private Polygon spotlight;
    @FXML
    private Label header1, header2, skinChoiceLabel, skinNameLabel;
    String[] names = {"Aqua Worm", "Techno Tangle", "Pinky Python", "Scaly Shrooms"}; // TODO (not for now) move to some config file e.g. json
    @FXML
    private int skin = 0;
    @FXML
    private Button modeButton1, modeButton2, quitButton, skinButton1, skinButton2, skinButton3, approveButton;

    private Map<Node, Double[]> anchors;

    @FXML
    private void mouseAction (MouseEvent event) {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }
    @FXML
    private void modeButtonAction1 (ActionEvent event) {
        // TODO (marwyk) add viewmodel(done) and start the game
        viewModel.getSceneController().addSkin(skin);
        viewModel.getSceneController().addSkin(skin);
        viewModel.startGame(); // TODO don't know if it should work this way
    }

//    @FXML
//    private void modeButtonAction2 (ActionEvent event) {
//        viewModel.startGame();
//    }

    @FXML
    private void chooseSkinAction (ActionEvent event) {
        viewModel.getSceneController().addSkin(skin);
        if (skinChoiceLabel.getText().equals("TAP TO CHANGE SKIN 1")) {
            skinChoiceLabel.setText("TAP TO CHANGE SKIN 2");
        } else {
            approveButton.setVisible(false);
            skinChoiceLabel.setText("ALL SKINS ARE CHOSEN");
        }
        skin = 0;
        skinButton1.setGraphic(new ImageView(new Image("/skin0LH.png")));
        skinButton2.setGraphic(new ImageView(new Image("/skin0L.png")));
        skinButton3.setGraphic(new ImageView(new Image("/skin0L.png")));
        skinNameLabel.setText(names[0]);
    }

    @FXML
    private void quitButtonAction (ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void nextSkinAction (ActionEvent event) {
        // TODO change to binding
        skin = (++skin) % names.length;
        skinButton1.setGraphic(new ImageView(new Image("/skin" + skin + "LH.png")));
        skinButton2.setGraphic(new ImageView(new Image("/skin" + skin + "L.png")));
        skinButton3.setGraphic(new ImageView(new Image("/skin" + skin + "L.png")));
        skinNameLabel.setText(names[skin]);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = viewModel.getSceneController().getStage();
        spotlight.getPoints().setAll(125.0, 0.0, 320.0, 0.0, 320.0, 700.0, 10.0, 700.0);
        anchors = new HashMap<>() {{
            put(header1, new Double[]{100.0, 0.0, 8.0, 0.0});      // left, right, top, bottom
            put(header2, new Double[]{100.0, 0.0, 126.0, 0.0});
            put(modeButton1, new Double[]{0.0, 120.0, 0.0, 340.0});
            put(modeButton2, new Double[]{0.0, 120.0, 0.0, 220.0});
            put(quitButton, new Double[]{0.0, 120.0, 0.0, 100.0});
            put(approveButton, new Double[]{440.0, 0.0, 0.0, 160.0});
            put(skinChoiceLabel, new Double[]{80.0, 0.0, 0.0, 200.0});
            put(skinButton1, new Double[]{170.0, 0.0, 0.0, 160.0});
            put(skinButton2, new Double[]{250.0, 0.0, 0.0, 160.0});
            put(skinButton3, new Double[]{330.0, 0.0, 0.0, 160.0});
            put(skinNameLabel, new Double[]{150.0, 0.0, 0.0, 40.0});
        }};

        skinButton1.setGraphic(new ImageView(new Image("/skin0LH.png")));
        skinButton2.setGraphic(new ImageView(new Image("/skin0L.png")));
        skinButton3.setGraphic(new ImageView(new Image("/skin0L.png")));
        header1.setStyle("header1");

        AtomicInteger fontSizeLabel = new AtomicInteger(50);
        AtomicInteger fontSizeHeader1 = new AtomicInteger(110);
        AtomicInteger fontSizeHeader2 = new AtomicInteger(95);
        ChangeListener<Number> stageHeightListener = (observable, oldValue, newValue) -> {
            double ratio = newValue.doubleValue() / 700.0;
            refreshButtons();
            refreshLabels(ratio, fontSizeLabel, fontSizeHeader1, fontSizeHeader2);
            refreshVerticalAnchors(ratio);
        };
        ChangeListener<Number> stageWidthListener = (observable, oldValue, newValue) -> {
            double ratio = newValue.doubleValue() / 1000.0;
            refreshButtons();
            refreshLabels(ratio, fontSizeLabel, fontSizeHeader1, fontSizeHeader2);
            refreshHorizontalAnchors(ratio);
        };
        stage.widthProperty().addListener(stageWidthListener);
        stage.heightProperty().addListener(stageHeightListener);

        refreshButtons();
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
        spotlight.getPoints().setAll(125.0, 0.0, 320.0, 0.0, 320.0, 700.0 * ratio, 10.0, 700. * ratio);
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
        spotlight.getPoints().setAll(125.0 * ratio, 0.0, 320.0 * ratio, 0.0, 320.0 * ratio, 700.0, 10.0 * ratio, 700.0);
    }

    void refreshButtons() {
        if (stage.getHeight() > 800.0 || stage.getWidth() > 1200.0) {
            modeButton1.setGraphic(new ImageView(new Image("/mode1M.png")));
            modeButton2.setGraphic(new ImageView(new Image("/mode2M.png")));
            quitButton.setGraphic(new ImageView(new Image("/quitM.png")));
        } else {
            modeButton1.setGraphic(new ImageView(new Image("/mode1S.png")));
            modeButton2.setGraphic(new ImageView(new Image("/mode2S.png")));
            quitButton.setGraphic(new ImageView(new Image("/quitS.png")));
        }
        approveButton.setGraphic(new ImageView("/approve.png"));
    }

    void refreshLabels(double ratio, AtomicInteger fontSizeLabel, AtomicInteger fontSizeHeader1, AtomicInteger fontSizeHeader2) {
        if (stage.getHeight() < 720 || stage.getWidth() < 1050) {
            fontSizeLabel.set(50);
            fontSizeHeader1.set(110);
            fontSizeHeader2.set(95);
        } else if (ratio > 1) {
            fontSizeLabel.set((int) Math.max(fontSizeLabel.get(), 50 * ratio));
            fontSizeHeader1.set((int) Math.max(fontSizeHeader1.get(), 110 * ratio));
            fontSizeHeader2.set((int) Math.max(fontSizeHeader2.get(), 95 * ratio));
        } else {
            fontSizeLabel.set((int) Math.min(fontSizeLabel.get(), 50 * ratio));
            fontSizeHeader1.set((int) Math.min(fontSizeHeader1.get(), 110 * ratio));
            fontSizeHeader2.set((int) Math.min(fontSizeHeader2.get(), 95 * ratio));
        }
        skinChoiceLabel.setStyle("-fx-font-size: " + fontSizeLabel + "px");
        skinNameLabel.setStyle("-fx-font-size: " + fontSizeLabel + "px");
        header1.setStyle("-fx-font-size: " + fontSizeHeader1.doubleValue() * 0.8 + "px");
        header2.setStyle("-fx-font-size: " + fontSizeHeader1.doubleValue() * 0.8 + "px");
    }
}