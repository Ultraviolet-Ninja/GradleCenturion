package bomb.tools.pattern.factory;

import bomb.Widget;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.filter.Filter;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.control.Alert;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WidgetEventFactory {
    public static Consumer<MFXTextField> createNumbersOnlyTextArea(){
        BiConsumer<MFXTextField, Integer> decideSetter = (jfxTextArea, number) -> {
            if (jfxTextArea.getPromptText().contains("Minutes")) Widget.setStartTime(number);
            else Widget.setNumModules(number);
        };
        return mfxTextField -> {
            String number = mfxTextField.getText();
            Filter.NUMBER_PATTERN.loadText(number);
            if (!Filter.NUMBER_PATTERN.matchesRegex()){
                FacadeFX.setAlert(Alert.AlertType.ERROR, "This is not a number");
                mfxTextField.setText("0");
                decideSetter.accept(mfxTextField, 0);
            } else decideSetter.accept(mfxTextField, Integer.parseInt(number));
        };
    }
}
