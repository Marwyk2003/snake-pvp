package snake.snake;
import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.application.*;
import javafx.beans.value.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class HelloController implements Initializable {
    @FXML
    private Label skinChoiceLabel, skinNameLabel;
    String[] names = {"Aqua Worm", "Pinky Python", "Scaly Shrooms", "Mighty Puff"};

    @FXML
    private int skin = 0, noSkins = 4;
    @FXML
    private Button modeButton1, modeButton2, quitButton, skinButton, gameNameButton;

    @FXML
    private void mouseAction (MouseEvent event) throws IOException {
        ((Button) event.getSource()).setCursor(Cursor.HAND);
    }
    @FXML
    private void modeButtonAction1 (ActionEvent event) throws IOException {
        Stage stage = (Stage) modeButton1.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("get-ready-view.fxml")));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void quitButtonAction (ActionEvent event) throws IOException {
        Platform.exit();
    }

    @FXML
    private void nextSkinAction (ActionEvent event) throws IOException {
        skin = (++skin) % noSkins;
        skinButton.setGraphic(new ImageView(new Image("file:src/skin" + skin + ".png")));
        skinNameLabel.setText(names[skin]);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modeButton1.setGraphic(new ImageView(new Image("file:src/mode1.png")));
        modeButton2.setGraphic(new ImageView(new Image("file:src/mode2.png")));
        quitButton.setGraphic(new ImageView(new Image("file:src/quit.png")));
        skinButton.setGraphic(new ImageView(new Image("file:src/skin0.png")));
        gameNameButton.setGraphic(new ImageView(new Image("file:src/gameName.png")));
    }

}