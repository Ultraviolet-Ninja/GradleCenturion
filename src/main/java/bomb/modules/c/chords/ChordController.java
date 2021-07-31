package bomb.modules.c.chords;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.event.HoverHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class ChordController implements Resettable {
    private final StringBuilder userNoteFeedback;

    @FXML private Button a, aSharp, b, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp;

    @FXML private TextField inputChordField, outputChordField;

    public ChordController(){
        userNoteFeedback = new StringBuilder();
    }

    public void initialize() {
        HoverHandler<ActionEvent> handler = new HoverHandler<>(createAction());
        FacadeFX.bindHandlerToButtons(handler, a, aSharp, b, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp);
    }

    private Consumer<ActionEvent> createAction() {
        return event -> add(((Button) event.getSource()).getText());
    }

    private void add(String note) {
        userNoteFeedback.append(note);
        if (userNoteFeedback.toString().split(", ").length == 4) {
            try {
                solve();
            } catch (IllegalArgumentException illegal) {
                FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
            }
            userNoteFeedback.setLength(0);
        } else
            userNoteFeedback.append(", ");
        inputChordField.setText(userNoteFeedback.toString());
    }

    private void solve() {
        outputChordField.setText(ChordQualities.solve(userNoteFeedback.toString().replace(", ", " ")));
    }

    @Override
    public void reset() {
        userNoteFeedback.setLength(0);
        FacadeFX.clearMultipleTextFields(inputChordField, outputChordField);
    }
}
