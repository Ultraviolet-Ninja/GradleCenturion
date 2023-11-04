package bomb.modules.t.translated;

import bomb.annotation.DisplayComponent;
import bomb.modules.t.translated.solutions.button.ButtonComponent;
import bomb.modules.t.translated.solutions.gas.VentGasComponent;
import bomb.modules.t.translated.solutions.password.PasswordComponent;

import java.util.List;

@DisplayComponent(resource = "translated_vanilla_modules.fxml", buttonLinkerName = "Translated Vanilla Modules")
public sealed interface TranslationComponent permits ButtonComponent, PasswordComponent, VentGasComponent {
    void setContent(List<String> languageContent);
}
