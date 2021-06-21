package bomb.components.simon.states;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

import java.io.IOException;

public class CustomState extends Pane {
    @FXML
    private Arc button;

    public CustomState(){
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("state.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setOnMouseClicked(event -> press());
    }

    @FXML
    private void press(){
        FadeTransition fade = new FadeTransition(Duration.millis(200), this);
        fade.setFromValue(0.6);
        fade.setToValue(1);
        fade.setCycleCount(1);
        fade.setAutoReverse(true);
        fade.play();
    }
}
