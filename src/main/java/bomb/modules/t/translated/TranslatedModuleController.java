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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

import java.util.List;

import static bomb.tools.pattern.facade.FacadeFX.BUTTON_NAME_FROM_EVENT;

public final class TranslatedModuleController implements Resettable {
    private static final String HOVERED_IMAGE_ID = "image-hover";
    private static final String STANDARD_BUTTON_ID = "standard-radio-button";

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

    @FXML
    private ImageView brazilianView, czechView, danishView, dutchView, americanView,
            esperantoView, italianView, estonianView, norwegianView, finnishView,
            polishView, frenchView, germanView, spanishView, swedishView;

    public void initialize() {
        var buttons = flagGroup.getToggles()
                .stream()
                .map(t -> (RadioButton)t)
                .peek(button -> button.setOnAction(createButtonAction()))
                .toList();

        connectImageViewClickToButton(buttons);
    }

    private void connectImageViewClickToButton(List<RadioButton> buttons) {
        int counter = 0;
        var views = new ImageView[]{brazilianView, czechView, danishView, dutchView, americanView,
                esperantoView, italianView, estonianView, norwegianView, finnishView,
                polishView, frenchView, germanView, spanishView, swedishView};
        for (var button : buttons) {
            var view = views[counter++];
            view.setOnMouseClicked(e -> button.fire());
            view.setOnMouseEntered(e -> button.setId(HOVERED_IMAGE_ID));
            view.setOnMouseExited(e -> button.setId(STANDARD_BUTTON_ID));
        }
    }

    private EventHandler<ActionEvent> createButtonAction() {
        return event -> {
            var buttonText = BUTTON_NAME_FROM_EVENT.apply(event);
            try {
                var currentLanguageColumn = LanguageColumn.valueOf(buttonText.toUpperCase());
                var languageContent = LanguageCSVReader.getLanguageContent(currentLanguageColumn);
                loadLanguageContent(languageContent);
                if (translatedModuleTab.isDisabled()) translatedModuleTab.setDisable(false);
            } catch (IllegalArgumentException | IllegalStateException e) {
                FacadeFX.setAlert(e.getMessage());
                translatedModuleTab.setDisable(true);
            }
        };
    }

    private void loadLanguageContent(TranslationResults languageContent) {
        var frontEndComponents = new TranslationComponent[]{buttonUI, passwordUI, ventGasUI};
        for (var component : frontEndComponents) {
            component.setContent(languageContent);
        }
    }

    @Override
    public void reset() {
        translatedModuleTab.setDisable(true);
        FacadeFX.resetToggleGroup(flagGroup);
        buttonUI.reset();
        passwordUI.reset();
        ventGasUI.reset();
    }
}
