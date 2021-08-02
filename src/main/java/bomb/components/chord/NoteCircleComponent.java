package bomb.components.chord;

import bomb.abstractions.Resettable;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class NoteCircleComponent extends Pane implements Resettable {
    public static final byte NOTE_LIMIT = 4;

    private static final String ENABLED_ID = "enabled-light-circle", DISABLED_ID = "disabled-light-circle";

    private final Map<JFXButton, Circle> stateMap;
    private final Set<String> selectedNoteSet;

    private Consumer<Set<String>> externalAction;

    @FXML private Circle noteALight, noteASharpLight, noteBLight, noteCLight, noteCSharpLight, noteDLight,
            noteDSharpLight,noteELight, noteFLight, noteFSharpLight, noteGLight, noteGSharpLight;

    @FXML private JFXButton a, aSharp, b, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp;

    public NoteCircleComponent() {
        super();
        stateMap = new HashMap<>();
        selectedNoteSet = new HashSet<>();
        externalAction = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("wheel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ioex){
            ioex.printStackTrace();
        }
    }

    public void initialize(){
        JFXButton[] buttonArray = {a, aSharp, b, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp};
        Circle[] circleArray = {
                noteALight, noteASharpLight, noteBLight, noteCLight, noteCSharpLight, noteDLight,
                noteDSharpLight,noteELight, noteFLight, noteFSharpLight, noteGLight, noteGSharpLight
        };

        EventHandler<ActionEvent> action = createButtonEvent();

        for (int i = 0; i < buttonArray.length; i++) {
            buttonArray[i].setOnAction(action);
            stateMap.put(buttonArray[i], circleArray[i]);
        }
    }

    private EventHandler<ActionEvent> createButtonEvent(){
        return event -> {
            JFXButton source = (JFXButton) event.getSource();
            Circle current = stateMap.get(source);
            boolean currentlyDisabled = current.getId().equals(DISABLED_ID);
            if (currentlyDisabled && selectedNoteSet.size() < NOTE_LIMIT) {
                current.setId(ENABLED_ID);
                selectedNoteSet.add(source.getText());
                echoChanges();
            } else if (!currentlyDisabled) {
                current.setId(DISABLED_ID);
                selectedNoteSet.remove(source.getText());
                echoChanges();
            }
        };
    }

    private void echoChanges(){
        if (externalAction != null)
            externalAction.accept(selectedNoteSet);
    }

    public void addListenerAction(Consumer<Set<String>> action){
        externalAction = action;
    }

    @Override
    public void reset() {
        selectedNoteSet.clear();
        noteALight.setId(DISABLED_ID);
        noteASharpLight.setId(DISABLED_ID);
        noteBLight.setId(DISABLED_ID);
        noteCLight.setId(DISABLED_ID);
        noteCSharpLight.setId(DISABLED_ID);
        noteDLight.setId(DISABLED_ID);
        noteDSharpLight.setId(DISABLED_ID);
        noteELight.setId(DISABLED_ID);
        noteFLight.setId(DISABLED_ID);
        noteFSharpLight.setId(DISABLED_ID);
        noteGLight.setId(DISABLED_ID);
        noteGSharpLight.setId(DISABLED_ID);
    }
}
