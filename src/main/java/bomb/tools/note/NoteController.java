package bomb.tools.note;

import bomb.abstractions.Resettable;
import bomb.annotation.DisplayComponent;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

@DisplayComponent(resource = "extra_notes.fxml", buttonLinkerName = "Extra Notes")
public final class NoteController implements Resettable {
    @FXML
    private JFXTextArea firstNote, secondNote, thirdNote, fourthNote, fifthNote;

    private static final byte SIZE_LIMIT = 5;

    private final List<NotePageController> extraNotes;

    public NoteController() {
        extraNotes = new ArrayList<>(SIZE_LIMIT);
    }

    @FXML
    private void addNoteWindow() {
        if (extraNotes.size() != SIZE_LIMIT)
            extraNotes.add(new NotePageController(extraNotes));
        else
            FacadeFX.setAlert(Alert.AlertType.ERROR, "Max Capacity reached");
    }

    @Override
    public void reset() {
        for (var notePageController : extraNotes.reversed()) {
            notePageController.close();
        }
        extraNotes.clear();
        FacadeFX.clearMultipleTextFields(firstNote, secondNote, thirdNote, fourthNote, fifthNote);
    }
}
