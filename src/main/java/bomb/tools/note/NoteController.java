package bomb.tools.note;

import bomb.abstractions.Resettable;
import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class NoteController implements Resettable {
    private static final int SIZE_LIMIT = 5;

    private final List<NotePageController> extraNotes = new ArrayList<>(SIZE_LIMIT);

    @FXML
    private void addNoteWindow() {
        if (extraNotes.size() != SIZE_LIMIT) {
            extraNotes.add(new NotePageController(extraNotes));
        } else {
            FacadeFX.setAlert(Alert.AlertType.ERROR, "Max Capacity reached");
        }
    }

    @Override
    public void reset() {
        for (int i = extraNotes.size() - 1; i >= 0; i--){
            extraNotes.get(i).close();
        }
        extraNotes.clear();
    }
}
