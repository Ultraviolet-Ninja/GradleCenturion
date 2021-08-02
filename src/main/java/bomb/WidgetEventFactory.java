package bomb;

import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.filter.Filter;
import com.jfoenix.controls.JFXTextArea;
import javafx.scene.control.Alert;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WidgetEventFactory {
    public static Consumer<JFXTextArea> createNumbersOnlyTextArea(){
        BiConsumer<JFXTextArea, Integer> decideSetter = (jfxTextArea, number) -> {
            if (jfxTextArea.getPromptText().contains("Minutes")) Widget.setStartTime(number);
            else Widget.setNumModules(number);
        };
        return jfxTextArea -> {
            String number = jfxTextArea.getText();
            Filter.NUMBER_PATTERN.loadText(number);
            if (!Filter.NUMBER_PATTERN.matchesRegex()){
                FacadeFX.setAlert(Alert.AlertType.ERROR, "This is not a number");
                jfxTextArea.setText("0");
                decideSetter.accept(jfxTextArea, 0);
            } else decideSetter.accept(jfxTextArea, Integer.parseInt(number));
        };
    }
}
