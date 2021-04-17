package bomb.modules.c.chords;

import bomb.tools.FacadeFX;
import bomb.tools.HoverHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class ChordController {
    private int counter = 0;
    private final String[] notes = new String[4];
    private StringBuilder track = new StringBuilder();

    @FXML
    private Button a, aSharp, b, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp;

    @FXML
    private TextField inputChord, outputChord;

    public void initialize() {
        HoverHandler<ActionEvent> handler = new HoverHandler<>(createAction());
        FacadeFX.bindHandlerToButtons(handler, a, aSharp, b, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp);
    }

    private Consumer<ActionEvent> createAction() {
        return event -> add(((Button) event.getSource()).getText());
    }

    private void add(String note) {
        notes[counter++] = note;
        track.append(note);
        if (counter == 4) {
            try {
                solve();
            } catch (IllegalArgumentException illegal) {
                FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage(), "", "");
            }
            counter = 0;
            track = new StringBuilder();
        } else
            track.append(", ");
        inputChord.setText(track.toString());
    }

    private void solve() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < notes.length; i++) {
            temp.append(notes[i]);
            if (i != 3)
                temp.append(" ");
        }
        outputChord.setText(ChordQualities.solve(temp.toString()));
    }
}
