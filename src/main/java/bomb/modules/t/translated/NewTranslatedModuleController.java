package bomb.modules.t.translated;

import bomb.abstractions.Resettable;
import bomb.modules.t.translated.solutions.button.ButtonComponent;
import bomb.tools.pattern.facade.FacadeFX;
import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.util.List;

public class NewTranslatedModuleController implements Resettable {
    @FXML
    private ButtonComponent buttonUI;

    @FXML
    private Tab translatedModuleTab;

    @FXML
    private ToggleGroup flagGroup;

    public void initialize() {
        for (Toggle toggle : flagGroup.getToggles()) {
            RadioButton source = (RadioButton) toggle;
            source.setOnAction(createButtonAction());
            if (translatedModuleTab.isDisabled()) translatedModuleTab.setDisable(false);
        }
    }

    private EventHandler<ActionEvent> createButtonAction() {
        return event -> {
            RadioButton source = (RadioButton) event.getSource();
            try {
                Language currentLanguage = Language.translateText(source.getText());
                List<String> languageContent = NewTranslationCenter.getLanguageContent(currentLanguage);

            } catch (CsvValidationException | IOException e) {
                FacadeFX.setAlert(Alert.AlertType.ERROR, e.getMessage());
            }
        };
    }

    @Override
    public void reset() {
        translatedModuleTab.setDisable(true);
        FacadeFX.resetToggleGroup(flagGroup);
        buttonUI.reset();
    }
}
