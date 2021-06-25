package bomb.tools.note;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class NotePageController extends Stage {
    private static final int HEIGHT = 400, WIDTH = 600;

    private final List<NotePageController> internalReference;

    public NotePageController(List<NotePageController> internalReference){
        super();
        this.internalReference = internalReference;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("note.fxml"));
        loader.setController(this);
        setTitle("Extra Note");
        try {
            setScene(new Scene(loader.load(), WIDTH, HEIGHT));
            setOnCloseRequest(event -> close());
            show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(){
        internalReference.remove(this);
        super.close();
    }
}
