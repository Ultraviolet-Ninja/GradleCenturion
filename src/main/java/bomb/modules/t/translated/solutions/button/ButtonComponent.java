package bomb.modules.t.translated.solutions.button;

import bomb.abstractions.Resettable;
import bomb.modules.t.translated.solutions.TranslationComponent;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

import static bomb.modules.t.translated.LanguageCSVReader.LanguageRow.BUTTON_LABEL_ROW;
import static bomb.modules.t.translated.solutions.button.Button.COLOR_INDEX;
import static bomb.modules.t.translated.solutions.button.Button.LABEL_INDEX;

public class ButtonComponent extends Pane implements Resettable, TranslationComponent {
    private final ButtonProperty[] properties;

    @FXML
    private ToggleGroup colorGroup, labelGroup;

    @FXML
    private JFXRadioButton redButton, yellowButton, blueButton, whiteButton, detonateButton, abortButton,
            pressButton, holdButton;

    @FXML
    private MFXTextField outputField;

    public ButtonComponent() {
        super();
        properties = new ButtonProperty[2];
        FXMLLoader loader = new FXMLLoader(getClass().getResource("button.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public void initialize() {
        redButton.setOnAction(createButtonAction(ButtonProperty.RED, COLOR_INDEX));
        yellowButton.setOnAction(createButtonAction(ButtonProperty.YELLOW, COLOR_INDEX));
        blueButton.setOnAction(createButtonAction(ButtonProperty.BLUE, COLOR_INDEX));
        whiteButton.setOnAction(createButtonAction(ButtonProperty.WHITE, COLOR_INDEX));
        detonateButton.setOnAction(createButtonAction(ButtonProperty.DETONATE, LABEL_INDEX));
        abortButton.setOnAction(createButtonAction(ButtonProperty.ABORT, LABEL_INDEX));
        pressButton.setOnAction(createButtonAction(ButtonProperty.PRESS, LABEL_INDEX));
        holdButton.setOnAction(createButtonAction(ButtonProperty.HOLD, LABEL_INDEX));
    }

    private EventHandler<ActionEvent> createButtonAction(ButtonProperty property, int index) {
        return event -> {
            properties[index] = property;
            if (FacadeFX.hasSelectedToggle(colorGroup) && FacadeFX.hasSelectedToggle(labelGroup))
                outputField.setText(Button.evaluate(properties));
        };
    }

    @Override
    public void reset() {
        FacadeFX.resetToggleGroup(colorGroup);
        FacadeFX.resetToggleGroup(labelGroup);
        FacadeFX.clearText(outputField);
    }

    @Override
    public void setContent(List<String> languageContent) {
        String[] buttonLabels = languageContent.get(BUTTON_LABEL_ROW.getRowIndex()).split("\\|");
        redButton.setText(buttonLabels[0]);
        blueButton.setText(buttonLabels[1]);
        yellowButton.setText(buttonLabels[2]);
        whiteButton.setText(buttonLabels[3]);
        holdButton.setText(buttonLabels[4]);
        pressButton.setText(buttonLabels[5]);
        detonateButton.setText(buttonLabels[6]);
        abortButton.setText(buttonLabels[7]);
    }
}
