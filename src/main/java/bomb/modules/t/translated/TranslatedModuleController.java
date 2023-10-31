package bomb.modules.t.translated;

import bomb.abstractions.Resettable;
import bomb.modules.t.translated.LanguageCSVReader.LanguageColumn;
import bomb.modules.t.translated.solutions.button.ButtonComponent;
import bomb.modules.t.translated.solutions.gas.VentGasComponent;
import bomb.modules.t.translated.solutions.morse.MorseCodeComponent;
import bomb.modules.t.translated.solutions.password.PasswordComponent;
import bomb.modules.t.translated.solutions.wof.WOFComponent;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.util.List;

public final class TranslatedModuleController implements Resettable {
    @FXML
    private ButtonComponent buttonUI;

    @FXML
    private MorseCodeComponent morseCodeUI;

    @FXML
    private PasswordComponent passwordUI;

    @FXML
    private VentGasComponent ventGasUI;

    @FXML
    private WOFComponent wofUI;

    @FXML
    private Tab translatedModuleTab;

    @FXML
    private ToggleGroup flagGroup;

    public void initialize() {
        for (Toggle toggle : flagGroup.getToggles()) {
            RadioButton source = (RadioButton) toggle;
            source.setOnAction(createButtonAction());
        }
    }

    private EventHandler<ActionEvent> createButtonAction() {
        return event -> {
            String buttonText = ((RadioButton)event.getSource()).getText();
            try {
                LanguageColumn currentLanguageColumn = LanguageColumn.valueOf(buttonText.toUpperCase());
                List<String> languageContent = LanguageCSVReader.getLanguageContent(currentLanguageColumn);
                buttonUI.setContent(languageContent);
                passwordUI.setContent(languageContent);
                ventGasUI.setContent(languageContent);
                if (translatedModuleTab.isDisabled()) translatedModuleTab.setDisable(false);
            } catch (IllegalArgumentException | IllegalStateException e) {
                FacadeFX.setAlert(e.getMessage());
            }
        };
    }

    @Override
    public void reset() {
        translatedModuleTab.setDisable(true);
        FacadeFX.resetToggleGroup(flagGroup);
        buttonUI.reset();
        passwordUI.reset();
    }
}
