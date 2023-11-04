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

import java.util.List;

import static bomb.modules.t.translated.LanguageCSVReader.LanguageRow.BUTTON_LABEL_ROW;
import static bomb.modules.t.translated.solutions.button.Button.COLOR_INDEX;
import static bomb.modules.t.translated.solutions.button.Button.LABEL_INDEX;
import static bomb.tools.pattern.facade.FacadeFX.loadComponent;

public final class ButtonComponent extends Pane implements Resettable, TranslationComponent {
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
        var loader = new FXMLLoader(getClass().getResource("button.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loadComponent("The Button", loader);
    }

    public void initialize() {
        var colorButtons = new JFXRadioButton[]{redButton, yellowButton, blueButton, whiteButton};
        var labelButtons = new JFXRadioButton[]{detonateButton, abortButton, pressButton, holdButton};

        int counter = 0;
        var colors = ButtonProperty.getColors();
        for (var colorButton : colorButtons) {
            colorButton.setOnAction(createButtonAction(colors[counter++], COLOR_INDEX));
        }

        counter = 0;
        var labels = ButtonProperty.getLabels();
        for (var labelButton : labelButtons) {
            labelButton.setOnAction(createButtonAction(labels[counter++], LABEL_INDEX));
        }
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
        //Given in the order of Red Blue Yellow White Hold Press Detonate Abort
        var buttonLabels = languageContent.get(BUTTON_LABEL_ROW.getRowIndex())
                .split("\\|");
        var buttons = new JFXRadioButton[]{redButton, blueButton, yellowButton, whiteButton,
                holdButton, pressButton, detonateButton, abortButton};
        int i = 0;
        for (var buttonLabel : buttonLabels) {
            buttons[i++].setText(buttonLabel);
        }
    }
}
