package bomb.modules.t.translated.solutions;

import bomb.modules.t.translated.solutions.button.ButtonComponent;
import bomb.modules.t.translated.solutions.gas.VentGasComponent;
import bomb.modules.t.translated.solutions.password.PasswordComponent;

import java.util.List;

public sealed interface TranslationComponent permits ButtonComponent, PasswordComponent, VentGasComponent {
    void setContent(List<String> languageContent);
}
