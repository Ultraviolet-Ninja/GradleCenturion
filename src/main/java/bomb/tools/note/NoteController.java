package bomb.tools.note;

import bomb.interfaces.Resettable;
import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.HashSet;
import java.util.Set;

public class NoteController implements Resettable {
    private static final int SIZE_LIMIT = 5;

    private final Set<NotePageController> extraNotes = new HashSet<>(SIZE_LIMIT);

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
        for (NotePageController page : extraNotes)
            page.close();
        extraNotes.clear();
    }
}
