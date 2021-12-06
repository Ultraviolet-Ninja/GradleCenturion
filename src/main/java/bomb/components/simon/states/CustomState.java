package bomb.components.simon.states;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

public class CustomState extends Pane implements Resettable {
    @FXML private Arc button;

    public CustomState(){
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("state.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        FacadeFX.loadComponent(loader);
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

    @Override
    public void reset() {

    }
}
