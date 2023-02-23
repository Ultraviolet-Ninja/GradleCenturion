package bomb.tools.pattern.factory;

import bomb.Widget;
import bomb.tools.filter.RegexFilter;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.control.Alert;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class WidgetEventFactory {
    public static Consumer<MFXTextField> createNumbersOnlyTextArea() {
        BiConsumer<MFXTextField, Integer> decideSetter = (mfxTextArea, number) -> {
            if (mfxTextArea.getPromptText().contains("Minutes")) Widget.setStartTime(number);
            else Widget.setNumModules(number);
        };
        return mfxTextField -> {
            String number = mfxTextField.getText();
            RegexFilter.NUMBER_PATTERN.loadText(number);
            if (!RegexFilter.NUMBER_PATTERN.matchesRegex()) {
                FacadeFX.setAlert(Alert.AlertType.ERROR, "This is not a number");
                mfxTextField.setText("0");
                decideSetter.accept(mfxTextField, 0);
            } else decideSetter.accept(mfxTextField, Integer.parseInt(number));
        };
    }
}
