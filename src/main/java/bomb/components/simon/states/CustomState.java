package bomb.components.simon.states;

import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;

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
        FacadeFX.fadeAutoReverse(this, 200, 0.6);
    }
}
