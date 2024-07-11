package bomb.tools.note;

import bomb.Main;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public final class NotePageController extends Stage {
    private static final short HEIGHT = 400, WIDTH = 600;

    private final List<NotePageController> internalReference;

    public NotePageController(List<NotePageController> internalReference) {
        super();
        this.internalReference = internalReference;
        var loader = new FXMLLoader(getClass().getResource("note.fxml"));
        loader.setController(this);
        setTitle("Extra Note");
        getIcons().add(Main.getGameIcon());

        var parent = FacadeFX.load(loader);
        setScene(new Scene(parent, WIDTH, HEIGHT));
        setOnCloseRequest(event -> close());
        show();
    }

    @Override
    public void close() {
        internalReference.remove(this);
        super.close();
    }
}
