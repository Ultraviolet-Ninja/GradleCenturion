package bomb.tools.note;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class NoteController {
    private final ArrayList<Stage> noteWindows = new ArrayList<>(5);

    @FXML
    private void addNoteWindow() {
        if (noteWindows.size() != 5) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("note/note.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Extra Notes");
                stage.setScene(new Scene(root, 600, 400));
                noteWindows.add(stage);
                stage.show();
            } catch (IOException ioException) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("IO Exception");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Max Capacity reached");
            alert.showAndWait();
        }
    }
}
