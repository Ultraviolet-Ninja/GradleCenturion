package bomb.modules.c.chords;

import bomb.abstractions.Resettable;
import bomb.components.chord.NoteCircleComponent;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.Set;
import java.util.function.Consumer;

public final class ChordController implements Resettable {
    @FXML
    private MFXTextField outputField;

    @FXML
    private NoteCircleComponent noteCircleComponent;

    public void initialize() {
        noteCircleComponent.addListenerAction(createListener());
    }

    private Consumer<Set<String>> createListener() {
        return set -> {
            if (set.size() == NoteCircleComponent.SELECTED_NOTE_LIMIT) {
                String output = String.join(" ", set);

                try {
                    outputField.setText(ChordQualities.solve(output.trim()));
                } catch (IllegalArgumentException illegal) {
                    FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
                    FacadeFX.clearText(outputField);
                }
            }
        };
    }

    @Override
    public void reset() {
        FacadeFX.clearText(outputField);
        noteCircleComponent.reset();
    }
}
