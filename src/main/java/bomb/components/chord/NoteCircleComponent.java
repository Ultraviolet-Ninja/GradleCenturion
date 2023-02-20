package bomb.components.chord;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class NoteCircleComponent extends Pane implements Resettable {
    public static final byte SELECTED_NOTE_LIMIT = 4;

    private static final String ENABLED_ID = "enabled-light-circle", DISABLED_ID = "disabled-light-circle";

    private final Map<MFXButton, Circle> stateMap;
    private final Set<String> selectedNoteSet;

    private Consumer<Set<String>> externalAction;

    @FXML
    private Circle noteALight, noteASharpLight, noteBLight, noteCLight, noteCSharpLight, noteDLight,
            noteDSharpLight, noteELight, noteFLight, noteFSharpLight, noteGLight, noteGSharpLight;

    @FXML
    private MFXButton a, aSharp, b, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp;

    public NoteCircleComponent() {
        super();
        stateMap = new HashMap<>();
        selectedNoteSet = new HashSet<>();
        externalAction = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("wheel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        FacadeFX.loadComponent(loader);
    }

    public void initialize() {
        MFXButton[] buttonArray = {a, aSharp, b, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp};
        Circle[] circleArray = {
                noteALight, noteASharpLight, noteBLight, noteCLight, noteCSharpLight, noteDLight,
                noteDSharpLight, noteELight, noteFLight, noteFSharpLight, noteGLight, noteGSharpLight
        };

        EventHandler<ActionEvent> action = createButtonEvent();

        for (int i = 0; i < buttonArray.length; i++) {
            buttonArray[i].setOnAction(action);
            stateMap.put(buttonArray[i], circleArray[i]);
        }
    }

    private EventHandler<ActionEvent> createButtonEvent() {
        return event -> {
            MFXButton source = (MFXButton) event.getSource();
            Circle associatedCircle = stateMap.get(source);
            boolean currentlyDisabled = associatedCircle.getId().equals(DISABLED_ID);
            if (currentlyDisabled && selectedNoteSet.size() < SELECTED_NOTE_LIMIT) {
                associatedCircle.setId(ENABLED_ID);
                selectedNoteSet.add(source.getText());
                echoChanges();
            } else if (!currentlyDisabled) {
                associatedCircle.setId(DISABLED_ID);
                selectedNoteSet.remove(source.getText());
                echoChanges();
            }
        };
    }

    private void echoChanges() {
        if (externalAction != null)
            externalAction.accept(selectedNoteSet);
    }

    public void addListenerAction(Consumer<Set<String>> action) {
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
